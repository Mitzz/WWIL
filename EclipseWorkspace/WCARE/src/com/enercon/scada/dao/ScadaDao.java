package com.enercon.scada.dao;

import com.enercon.admin.dao.AdminSQLC;
import com.enercon.global.utils.Diff;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.JDBCUtils;

import com.enercon.scada.dao.ScadaSQLC;
import com.enercon.global.utils.CodeGenerate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.*;
import org.apache.log4j.Logger;

public class ScadaDao {

    private static Logger logger = Logger.getLogger(ScadaDao.class);

    public ScadaDao() {
    	
    }
    
    public String uploadScadaData(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
	    Connection conn = null;
	    PreparedStatement prepStmt = null;
	    CallableStatement cs = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    String sqlQuery = "";
	    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	    try{
	    	conn = conmanager.getConnection();
	    	
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
			sqlExp.printStackTrace();			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
		try {
			if (cs != null)
				cs.close();
			if (conn != null) {
				conn.close();
            	conn = null;
            	
                conmanager.closeConnection();conmanager = null;
			}
		} catch (Exception e) {
			cs = null;
			
			if (conn != null) {
				conn.close();
            	conn = null;
            	
                conmanager.closeConnection();conmanager = null;
			}
		}
	}
	return msg;
	}
    
    public String getScadaAjaxDetails(String item,String action,String UserId) throws Exception{
    	StringBuffer xml = new StringBuffer();
    	JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
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
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (ps != null) ps.close();
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (rs != null) rs.close();
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (conn != null) 
                {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                ps = null;
                ps1 = null;
                ps2 = null;
                rs = null;
                rs1 = null;
                rs2 = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
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
    
}