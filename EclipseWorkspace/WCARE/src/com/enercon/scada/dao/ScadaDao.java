package com.enercon.scada.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.DynaBean;

public class ScadaDao implements WcareConnector {

    private static Logger logger = Logger.getLogger(ScadaDao.class);

    public ScadaDao() {
    	
    }  
    
    private static class SingletonHelper{
        private static final ScadaDao INSTANCE = new ScadaDao();
    }   
    public static ScadaDao getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public String uploadScadaData(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		//JDBCUtils conmanager = new JDBCUtils();
	    Connection conn = null;
	    PreparedStatement prepStmt = null;
	    CallableStatement cs = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sqlQuery = "";
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    try{
	    	conn = wcareConnector.getConnectionFromPool();
	    	
	    	String locationNo = dynaBean.getProperty("scadaLocationNo").toString();
	    	String plantNo = dynaBean.getProperty("scadaPlantNo").toString();
	    	String uploadDate = dynaBean.getProperty("uploadDate").toString();
	    	
	    	java.util.Date fdt = format.parse(uploadDate);
    		java.sql.Date fdate = new java.sql.Date(fdt.getTime());
					
    		if(!locationNo.equalsIgnoreCase("all")){

    			
				cs = conn.prepareCall("{call SCADADW.UPLD_LOCWISE_SCADADATA_TOECARE(?,?)}");
				
				//cs = conn.prepareCall("{? = call GET_RATEDAVGGEN(?, ?, ?)}");
				//cs.registerOutParameter(1, oracle.jdbc.driver.OracleTypes.NUMBER);
				
				cs.setObject(1, fdate);
				cs.setObject(2, locationNo);
				//cs.setObject(3, plantNo);
    		}
    		else{

    			cs = conn.prepareCall("{call SCADADW.UPLOAD_SCADA_CUM_DATA_TOECARE(?)}");
				cs.setObject(1, fdate);
    		}
			cs.executeUpdate();
			
            cs.close();
            msg = "<font class='sucessmsgtext'>Data Uploaded for Location - '"+locationNo+"' Successfully!</font>";				
			
		}catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);	
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt, ps, cs), Arrays.asList(rs), conn);
	}
	return msg;
	}
    
    public String getScadaAjaxDetails(String item,String action,String UserId) throws Exception{
    	StringBuffer xml = new StringBuffer();
    	//JDBCUtils conmanager = new JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String sqlQuery = "";
        String msg="";
        try{
        	if (action.equals("SCADA_plantByLocation"))
        	{        		
        		sqlQuery = ScadaSQLC.GET_LOCATIONWISE_PLANT_NO;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1, item);
        		rs = ps.executeQuery();
        		xml.append("<?xml version=\"1.0\"?>\n");
			    xml.append("<scadaLocationNo generated=\""+System.currentTimeMillis()+"\">\n");
        		while (rs.next())
        		{
        			xml.append("<scadaPlantNo>");
	        			xml.append("<scadaPlantID>");
	        				xml.append(rs.getObject("S_PLANT_NO").toString());
	        			xml.append("</scadaPlantID>\n"); 
	        			xml.append("<scadaPlantDESC>");
        				xml.append(rs.getObject("S_WEC_NAME").toString());
        			xml.append("</scadaPlantDESC>\n");
        			xml.append("</scadaPlantNo>\n");
        		}
        		xml.append("</scadaLocationNo>\n");
        		rs.close();
        		ps.close();
        	}
        	
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps,ps1,ps2) , Arrays.asList(rs,rs1,rs2) , conn);
        }
        return xml.toString();
    }
    
    public String escape(String s) {
		s = s.replace("&", " amp ");
		s = s.replace("'", " apos ");
		s = s.replace("<", " ltthan ");
		s = s.replace(">", " gtthan ");
		s = s.replace("<BR>", "  ");
		// s = s.replace('"', "quot");

		return s;
	}
    
    public void mergeWindSpeed(String date) throws SQLException{
    	Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		List<String> wecIds = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery =  "SELECT wsd.d_date,wm.S_WEC_ID " +
						"FROM SCADADW.TBL_WSD_INSERTUPDATE_DETAIL wsd," +
						"     SCADADW.TBL_PLANT_MASTER pm," +
						"     TBL_WEC_MASTER wm " +
						"WHERE wsd.S_LOCATION_NO = pm.S_LOCATION_NO " +
						"AND pm.S_SERIAL_NO = wm.S_TECHNICAL_NO " +
						"AND pm.S_WEC_NAME = wm.S_WECSHORT_DESCR " +
						"AND wm.s_scada_flag = '1' " +
						"AND wsd.T_UPDATE_TIME >= To_TIMESTAMP('"+date+" 00:00:00','dd-MON-yyyy HH24:MI:SS') " +
						"AND wsd.T_UPDATE_TIME <= To_TIMESTAMP('"+date+" 23:59:59','dd-MON-yyyy HH24:MI:SS') " +
						"ORDER BY wsd.D_date ";
         
			prepStmt = conn.prepareStatement(sqlQuery);
		    //prepStmt.setObject(1, date);
			rs = prepStmt.executeQuery();
			String wecId = null;
			while(rs.next()){
				wecId = rs.getString("S_WEC_ID");
				wecIds.add(rs.getString("S_WEC_ID"));
				logger.debug("In Progress: " + wecId);
				cs = conn.prepareCall("{call reading_summary.populate_WS_DATE_WEC(?,?)}");
				cs.setObject(1, rs.getDate("D_Date"));
				cs.setObject(2, wecId);
				cs.executeUpdate();
	            cs.close();
				
			}
			logger.debug(wecIds);
			logger.debug("count :: " + wecIds.size());
			
		}finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt, cs), Arrays.asList(rs), conn);
	    }
    
    }
    
    public void mergeWindSpeedDate(String date) throws SQLException{
    	Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		
		List<String> wecIds = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery =  "SELECT wsd.d_date,wm.S_WEC_ID " +
						"FROM SCADADW.TBL_WSD_INSERTUPDATE_DETAIL wsd," +
						"     SCADADW.TBL_PLANT_MASTER pm," +
						"     TBL_WEC_MASTER wm " +
						"WHERE wsd.S_LOCATION_NO = pm.S_LOCATION_NO " +
						"AND pm.S_SERIAL_NO = wm.S_TECHNICAL_NO " +
						"AND pm.S_WEC_NAME = wm.S_WECSHORT_DESCR " +
						"AND wm.s_scada_flag = '1' " +
						"AND D_DATE= ? " +						
						"ORDER BY wsd.D_date ";
         
			prepStmt = conn.prepareStatement(sqlQuery);
		    prepStmt.setObject(1, date);
			rs = prepStmt.executeQuery();
			String wecId = null;
			while(rs.next()){
				wecId = rs.getString("S_WEC_ID");
				wecIds.add(rs.getString("S_WEC_ID"));
				logger.debug("In Progress: " + wecId);
				cs = conn.prepareCall("{call reading_summary.populate_WS_DATE_WEC(?,?)}");
				cs.setObject(1, rs.getDate("D_Date"));
				cs.setObject(2, wecId);
				cs.executeUpdate();
	            cs.close();
				
			}
			logger.debug(wecIds);
			logger.debug("count :: " + wecIds.size());
			
		}finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt, cs), Arrays.asList(rs), conn);
	    }
    
    }
    
    public static void main(String args[]) throws Exception{
    	
    	ScadaDao dao = null;
    	dao = new ScadaDao();
    	 
    	dao.mergeWindSpeedDate("04-FEB-2016");
    	
    }
    
}