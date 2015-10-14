package com.enercon.admin.job;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.JDBCUtils;

public class InsertPESData implements Job{
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		insertPESData();
		//uploadScadaMissedData();
	}
	
	private void uploadScadaMissedData() {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			uploadMissedDataOfScada(conn);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			e.printStackTrace();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}

	private void uploadMissedDataOfScada(Connection conn) {
		CallableStatement uploadMissedDataOfScadaProcedure = null;

		try{
			uploadMissedDataOfScadaProcedure = conn.prepareCall("{call SCADADW.Upload_WSD_To_Reading_History()}");

			uploadMissedDataOfScadaProcedure.execute();

			if(uploadMissedDataOfScadaProcedure != null){
				uploadMissedDataOfScadaProcedure.close();
				uploadMissedDataOfScadaProcedure = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(uploadMissedDataOfScadaProcedure != null){
					uploadMissedDataOfScadaProcedure.close();
					uploadMissedDataOfScadaProcedure = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void insertPESData() {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		java.sql.Date yesterdayDateValueInSQL = DateUtility.getYesterdayDateInSQL();
		
		try{
			conn = conmanager.getConnection();
			insertParameterDateWiseIntoWECReading(conn, yesterdayDateValueInSQL);
			//insertParameterDateWiseIntoWECReading(conn, yesterdayDateValueInSQL);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			e.printStackTrace();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}
	
	public void insertParameterDateWiseIntoWECReading(Connection conn,java.sql.Date insertingDateInSQL) {
		CallableStatement uploadMachineGridProcedure = null;

		try{
			uploadMachineGridProcedure = conn.prepareCall("{call SCADADW.Upload_Machine_Grid(?)}");

			uploadMachineGridProcedure.setObject(1, insertingDateInSQL);

			uploadMachineGridProcedure.execute();

			if(uploadMachineGridProcedure != null){
				uploadMachineGridProcedure.close();
				uploadMachineGridProcedure = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(uploadMachineGridProcedure != null){
					uploadMachineGridProcedure.close();
					uploadMachineGridProcedure = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void insertParameterDateLocationPlantWiseIntoWECReading(Connection conn,java.sql.Date insertingDateInSQL,
			String locationNo, String plantNo) {
		CallableStatement insertTimeDifferenceProcedure = null;

		try{
			insertTimeDifferenceProcedure = conn.prepareCall("{call SCADADW.MGUpload_Location_Plant_Wise(?, ?, ?)}");

			insertTimeDifferenceProcedure.setObject(1, insertingDateInSQL);
			insertTimeDifferenceProcedure.setObject(2, locationNo);
			insertTimeDifferenceProcedure.setObject(3, plantNo);

			insertTimeDifferenceProcedure.execute();

			if(insertTimeDifferenceProcedure != null){
				insertTimeDifferenceProcedure.close();
				insertTimeDifferenceProcedure = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(insertTimeDifferenceProcedure != null){
					insertTimeDifferenceProcedure.close();
					insertTimeDifferenceProcedure = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void insertParameterDateLocationWiseIntoWECReading(Connection conn,java.sql.Date insertingDateInSQL,
			String locationNo) {
		CallableStatement insertTimeDifferenceProcedure = null;

		try{
			insertTimeDifferenceProcedure = conn.prepareCall("{call SCADADW.MGUpload_Location_Wise(?, ?)}");

			insertTimeDifferenceProcedure.setObject(1, insertingDateInSQL);
			insertTimeDifferenceProcedure.setObject(2, locationNo);



			insertTimeDifferenceProcedure.execute();

			if(insertTimeDifferenceProcedure != null){
				insertTimeDifferenceProcedure.close();
				insertTimeDifferenceProcedure = null;
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(insertTimeDifferenceProcedure != null){
					insertTimeDifferenceProcedure.close();
					insertTimeDifferenceProcedure = null;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
