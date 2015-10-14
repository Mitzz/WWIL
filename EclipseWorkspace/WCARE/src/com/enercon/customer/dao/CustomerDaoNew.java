package com.enercon.customer.dao;

import com.enercon.global.utils.JDBCUtils;
import java.sql.*;
import java.text.*;
import java.util.*;

import java.util.List;

// import org.apache.log4j.Logger;

public class CustomerDaoNew {
	
	// private static Logger logger = Logger.getLogger(CustomerDaoNew.class);

	public CustomerDaoNew() {
	}
	
	public List getInitialYearGen(String custid, String fdate, String tdate, String type, String rtype) throws Exception {
		
			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = conmanager.getConnection();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date fd = format.parse(fdate);
			java.sql.Date fromdate = new java.sql.Date(fd.getTime());
			java.util.Date td = format.parse(tdate);
			java.sql.Date toDate = new java.sql.Date(td.getTime());			
		 
			CallableStatement calls = conn.prepareCall("{call PROC_REPORTS_DATA(?,?,?,?,?,?)}");
			calls.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			calls.setObject(2, custid);
			calls.setObject(3, fromdate);
			calls.setObject(4, toDate);
			calls.setObject(5, type);
			calls.setObject(6, rtype);
			calls.execute();
			
			ResultSet rsproc = (ResultSet)calls.getObject(1);
			List<Object> getCustList = new ArrayList<Object>();			
			int i =0;
			try{
				while (rsproc.next())
			    {
					ArrayList<Object> getCustListDetails = new ArrayList<Object>();
					
					getCustListDetails.add(rsproc.getObject(1));
					getCustListDetails.add(rsproc.getObject(2));
					getCustListDetails.add(rsproc.getObject(3));
					getCustListDetails.add(rsproc.getObject(4));
					getCustListDetails.add(rsproc.getObject(5));
					getCustListDetails.add(rsproc.getObject(6));
					getCustListDetails.add(rsproc.getObject(7));
					getCustListDetails.add(rsproc.getObject(8));
					getCustListDetails.add(rsproc.getObject(9));
					getCustListDetails.add(rsproc.getObject(10));
					getCustListDetails.add(rsproc.getObject(11));
					getCustListDetails.add(rsproc.getObject(12));
					getCustListDetails.add(rsproc.getObject(13));
					getCustListDetails.add(rsproc.getObject(14));
					getCustListDetails.add(rsproc.getObject(15));
					getCustListDetails.add(rsproc.getObject(16));
					
					getCustList.add(i,getCustListDetails);
					
					i++;
					
			    }
				rsproc.close();
				calls.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally {
				try {
					if (calls != null) {
						calls.close();
					}
					if (rsproc != null)
						rsproc.close();
					if (conn != null) 
					{
						conn.close();
						conn = null;

						conmanager.closeConnection();
						conmanager = null;
					}
				} catch (Exception e) {
					calls = null;
					rsproc = null;
					if (conn != null) 
					{
						conn.close();
						conn = null;

						conmanager.closeConnection();
						conmanager = null;
					}
				}
			} 
		return getCustList;
	}
	public List getInitialYearDailyGen(String custid, String fdate, String tdate, String type, String rtype) throws Exception {
		
			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = conmanager.getConnection();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date fd = format.parse(fdate);
			java.sql.Date fromdate = new java.sql.Date(fd.getTime());
			java.util.Date td = format.parse(tdate);
			java.sql.Date toDate = new java.sql.Date(td.getTime());			
		 
			CallableStatement calls = conn.prepareCall("{call PROC_REPORTS_DATA(?,?,?,?,?,?)}");
			calls.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			calls.setObject(2, custid);
			calls.setObject(3, fromdate);
			calls.setObject(4, toDate);
			calls.setObject(5, type);
			calls.setObject(6, rtype);
			calls.execute();
			
			ResultSet rsproc = (ResultSet)calls.getObject(1);
			List<Object> getCustList = new ArrayList<Object>();			
			int i =0;
			try{
				while (rsproc.next())
			    {
					ArrayList<Object> getCustListDetails = new ArrayList<Object>();
					
					getCustListDetails.add(rsproc.getObject("S_CUSTOMER_NAME"));
					getCustListDetails.add(rsproc.getObject("S_WEC_ID"));
					getCustListDetails.add(rsproc.getObject("S_WECSHORT_DESCR"));
					getCustListDetails.add(rsproc.getObject("S_WEC_TYPE"));
					getCustListDetails.add(rsproc.getObject("N_WEC_CAPACITY"));
					getCustListDetails.add(rsproc.getObject("S_SITE_NAME"));
					getCustListDetails.add(rsproc.getObject("S_AREA_NAME"));
					getCustListDetails.add(rsproc.getObject("S_STATE_NAME"));
					getCustListDetails.add(rsproc.getObject("N_GENERATION"));
					getCustListDetails.add(rsproc.getObject("N_OPERATING_HRS"));					
					getCustListDetails.add(rsproc.getObject("N_EXCELGEN"));
					getCustListDetails.add(rsproc.getObject("N_EXCELHRS"));
					getCustListDetails.add(rsproc.getObject("N_LULL_HRS"));
					getCustListDetails.add(rsproc.getObject("N_MACHINE_AVAIL"));
					getCustListDetails.add(rsproc.getObject("N_CAPACITY_FACTOR"));
					getCustListDetails.add(rsproc.getObject("N_GRID_EXTERNAL_AVAIL"));
					getCustListDetails.add(rsproc.getObject("D_READING_DATE"));
					getCustListDetails.add(rsproc.getObject("S_REMARKS")==null?"":rsproc.getObject("S_REMARKS").toString());
					getCustListDetails.add(rsproc.getObject("S_EB_ID"));
					
					getCustList.add(i,getCustListDetails);
					
					i++;
					
			    }
				rsproc.close();
				calls.close();
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			finally {
				try {
					if (calls != null) {
						calls.close();
					}
					if (rsproc != null)
						rsproc.close();
					if (conn != null) 
					{
						conn.close();
						conn = null;
	
						conmanager.closeConnection();
						conmanager = null;
					}
				} catch (Exception e) {
					calls = null;
					rsproc = null;
					if (conn != null) 
					{
						conn.close();
						conn = null;
	
						conmanager.closeConnection();
						conmanager = null;
					}
				}
			} 
		return getCustList;
	}
	
	public int getTotalUnpublishedData(String ardate) throws Exception{
		int ttlUnpublishedData = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");	
		
		java.util.Date tillDate = new java.util.Date();
		java.sql.Date toDate;
		tillDate = format.parse(ardate);
		toDate = new java.sql.Date(tillDate.getTime());
		
		try{
		PreparedStatement pst = null;	
		ResultSet rs = null;
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		String sqlQuery = CustomerSQLCNew.GET_TOTAL_UNPUBLISHED_DATA;
		pst = conn.prepareStatement(sqlQuery);
		pst.setDate(1, toDate);		
		System.out.println(sqlQuery);
		rs = pst.executeQuery(sqlQuery);
			
		if(rs.next()){
			ttlUnpublishedData = rs.getInt("CNT");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	return ttlUnpublishedData;
	}	
	
}
