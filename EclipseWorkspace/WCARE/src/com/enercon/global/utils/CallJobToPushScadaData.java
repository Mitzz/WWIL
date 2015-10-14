package com.enercon.global.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.enercon.admin.dao.AdminDao;
import com.enercon.global.utility.DateUtility;

public class CallJobToPushScadaData implements Job {

	private static Logger logger = Logger.getLogger(CallJobToPushScadaData.class);

	public void execute(JobExecutionContext arg0) {


		CallScheduler callScadaScheduler = new CallScheduler();
		try {
			callScadaScheduler.pushScadaDataToECARE();
		} catch (Exception e) {
			logger.debug("Scheduler exception", e);
		}
		
		/*System.out.println("LLLLHello World Quartz Scheduler: " + new Date());
		try {
			insertNotInsertedData(DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy"));
		} catch (Exception e) {
			System.out.println("Exception: " + new Date());
			e.printStackTrace();
		}
		System.out.println("fini");*/
		
		
		
//		insertNotInsertedData(DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy"));
		
	}
	
	public void executeTest() throws Exception {

		System.out.println("LLLLHello World Quartz Scheduler: " + new Date());

		String date = DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy");
		for (int i = 0; i < 38; i++, date = DateUtility.getPreviousDateFromGivenDateInString(date, "dd-MMM-yyyy")) {
			System.out.println("Date in process:" + date);
			insertNotInsertedData(date);
		}
		System.out.println("fini");
	}
	

	private void insertNotInsertedData(String dateValue) throws Exception {
		insertLeftOutData(dateValue, getNotInsertedDataWecId(dateValue));
	}

	private void insertLeftOutData(String dateValue, List<String> wecIds) throws Exception {
		AdminDao adminDao = new AdminDao();
		for (String wecId : wecIds) {
			adminDao.insertRecordIntoWECReading(dateValue, wecId, "D", null, "0", "0", "1401000006", "M_Insert");
		}
	}

	private List<String> getNotInsertedDataWecId(String dateValue) throws Exception {

		List<String> wecIds = new ArrayList<String>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"select S_wec_id " + 
					"from tbl_wec_master " + 
					"where S_wec_id not in( " + 
					"                                          select S_wec_id " + 
					"                                          from tbl_wec_reading " + 
					"                                          where D_reading_date = ? " + 
					"                                          group by S_wec_id) " + 
					"and S_status not in ( '9', '2') " +
//					"and S_scada_flag = '1' " +
					"order by S_wecshort_descr " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, dateValue);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString(1));
			}
			return wecIds;
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		
			
	}
}
