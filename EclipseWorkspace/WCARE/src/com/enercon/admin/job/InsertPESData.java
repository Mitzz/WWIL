package com.enercon.admin.job;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;

public class InsertPESData implements Job,WcareConnector{
	private final static Logger logger = Logger.getLogger(InsertPESData.class);
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		insertPESData();
		//uploadScadaMissedData();
	}
	
	private void uploadScadaMissedData() {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			uploadMissedDataOfScada(conn);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(uploadMissedDataOfScadaProcedure != null){
					uploadMissedDataOfScadaProcedure.close();
					uploadMissedDataOfScadaProcedure = null;
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}

	private void insertPESData() {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		java.sql.Date yesterdayDateValueInSQL = DateUtility.getYesterdayDateInSQL();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			insertParameterDateWiseIntoWECReading(conn, yesterdayDateValueInSQL);
			//insertParameterDateWiseIntoWECReading(conn, yesterdayDateValueInSQL);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(uploadMachineGridProcedure != null){
					uploadMachineGridProcedure.close();
					uploadMachineGridProcedure = null;
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(insertTimeDifferenceProcedure != null){
					insertTimeDifferenceProcedure.close();
					insertTimeDifferenceProcedure = null;
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			try{
				if(insertTimeDifferenceProcedure != null){
					insertTimeDifferenceProcedure.close();
					insertTimeDifferenceProcedure = null;
				}
			}
			catch(Exception e){
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
}
