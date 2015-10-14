package com.enercon.admin.machineGridAutomation;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.JDBCUtilsTest;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.GetColumnNames;
import com.enercon.global.utils.GlobalUtils;

public class MachineGridAvailabilityRetriever {

	private static Logger logger = Logger.getLogger(MachineGridAvailabilityRetriever.class);
	public static void getMGA() {

		JDBCUtilsTest conmanager = new JDBCUtilsTest();
		
		Connection scadaDevConnection = null;
		Connection wcareConneetion = null;
		Connection localConneetion = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String sqlQuery1 = "";
		PreparedStatement prepStmt1 = null;
		ResultSet rs1 = null;
			
		java.sql.Date currentDateInSQL = DateUtility.getTodaysDateInSQL();
		String systemCreated = "System";
		
		java.sql.Date sqlDate = null;
		long machineAvailabilityInSecond = 0;
		long gridAvailabilityInSecond = 0;
		long machineShutdown = 0;
		String serialNo = "";
		
		String wecID = "";
		String ebId = "";
		String customerId = "";
		
		try{
			scadaDevConnection = conmanager.getScadaConnection();
			wcareConneetion = conmanager.getWcareConnection();
			localConneetion = conmanager.getTestingConnection();
			sqlQuery = 	"Select D_DATE,N_MACHINE_AVAIL_IN_SEC,N_GRID_AVAIL_IN_SEC,N_MACHINE_SHUTDOWN,S_SERIAL_NO " +
						"From Tbl_Machine_Grid_Availability " +
						"where D_date = ? ";
			prepStmt = scadaDevConnection.prepareStatement(sqlQuery);
			prepStmt.setObject(1, "29-AUG-13");
			rs = prepStmt.executeQuery();
			//String[] columnNames = GlobalUtils.getColumnNames(rs);
			System.out.println(currentDateInSQL + ":" + systemCreated);
			while (rs.next()) {
				serialNo = rs.getString("S_SERIAL_NO") == null ? null : rs.getString("S_SERIAL_NO");
				if(serialNo == null){
					continue;
				}
				
				/*for (String columnName : columnNames) {
					logger.info(columnName + ":" + rs.getString(columnName) + "   ");
					System.out.print(columnName + ":" + rs.getString(columnName) + "   ");
				}*/
				
				sqlDate = rs.getDate("D_Date");
				machineAvailabilityInSecond = new BigDecimal(rs.getString("N_MACHINE_AVAIL_IN_SEC") == null? "-2" : rs.getString("N_MACHINE_AVAIL_IN_SEC")).longValue();
				
				gridAvailabilityInSecond = new BigDecimal(rs.getString("N_GRID_AVAIL_IN_SEC") == null? "-2" : rs.getString("N_GRID_AVAIL_IN_SEC")).longValue();
				machineShutdown = new BigDecimal(rs.getString("N_MACHINE_SHUTDOWN") == null? "-2" : rs.getString("N_MACHINE_SHUTDOWN")).longValue();
				
				
				//System.out.println(sqlDate + ":" + machineAvailabilityInSecond + ":" + gridAvailabilityInSecond + ":" + machineShutdown + ":" + serialNo);
				sqlQuery1 = 
								"Select S_wec_id, S_eb_id, S_customer_id " + 
								"From Customer_Meta_Data " + 
								"Where S_technical_no = ? ";
				
				prepStmt1 = wcareConneetion.prepareStatement(sqlQuery1);
				prepStmt1.setString(1, serialNo);
				rs1 = prepStmt1.executeQuery();
				
				while(rs1.next()){
					wecID = rs1.getString("S_wec_id");
					ebId = rs1.getString("S_eb_id");
					customerId = rs1.getString("S_customer_id");
				}
				System.out.println(sqlDate + ":" + machineAvailabilityInSecond + ":" + gridAvailabilityInSecond + ":" + machineShutdown + ":" + serialNo + ":" + wecID + ":" + ebId + ":" + customerId); 
				prepStmt1.close();
				rs1.close();
				
				sqlQuery1 = 
						"Merge Into TBL_WEC_READING L " + 
						"USING (SELECT ? AS S_WEC_ID,? AS S_MP_ID,? AS D_READING_DATE FROM DUAL) S " + 
						"On (L.S_WEC_ID = S.S_WEC_ID And L.S_MP_ID = S.S_MP_ID AND L.D_READING_DATE = S.D_READING_DATE) " + 
						"When Matched Then Update Set " + 
						"L.N_VALUE = ?, L.S_LAST_MODIFIED_BY = ?,L.D_LAST_MODIFIED_DATE = ?, L.N_ACTUAL_VALUE = ? " + 
						"WHEN NOT MATCHED THEN " +
						"INSERT(S_READING_ID, D_READING_DATE, S_MP_ID, S_WEC_ID, S_EB_ID, S_CUSTOMER_ID, N_VALUE, " +
						"S_CREATED_BY, D_CREATED_DATE, S_LAST_MODIFIED_BY, D_LAST_MODIFIED_DATE, " +
						"N_ACTUAL_VALUE, S_READING_TYPE, N_PUBLISH, S_REMARKS, N_PUBLISH_FACT) " +
						"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;
				
				prepStmt1 = localConneetion.prepareStatement(sqlQuery1);
				
				/*Source Data Parameter*/
				prepStmt1.setObject(1, wecID);
				prepStmt1.setObject(2, "0808000026");
				prepStmt1.setObject(3, sqlDate);
				
				/*Update Data Parameter*/
				prepStmt1.setObject(4, machineShutdown);
				prepStmt1.setObject(5, "NA");
				prepStmt1.setObject(6, currentDateInSQL);
				prepStmt1.setObject(7, machineShutdown);
				
				/*Insert Data Parameter*/
				prepStmt1.setObject(8, "NA");
				prepStmt1.setObject(9, sqlDate);
				prepStmt1.setObject(10, "0808000026");
				prepStmt1.setObject(11, wecID);
				prepStmt1.setObject(12, ebId);
				prepStmt1.setObject(13, customerId);
				prepStmt1.setObject(14, machineShutdown);
				prepStmt1.setObject(15, systemCreated);
				prepStmt1.setObject(16, currentDateInSQL);
				prepStmt1.setObject(17, systemCreated);
				prepStmt1.setObject(18, currentDateInSQL);
				prepStmt1.setObject(19, machineShutdown);
				prepStmt1.setObject(20, "D");
				prepStmt1.setObject(21, 0);
				prepStmt1.setObject(22, null);
				prepStmt1.setObject(23, 0);
				
				prepStmt1.executeUpdate();
				if(prepStmt1 != null){
					prepStmt1.close();
				}
			}
			//return null;
		}
		catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(scadaDevConnection != null){
					scadaDevConnection.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}
}
