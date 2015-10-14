package com.enercon.admin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.enercon.Time24HoursValidator;
import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.struts.exception.GridBifurcationException;

public class WcareDao implements WcareConnector{

	public void validateData(ArrayList<ArrayList<String>> excelData) throws GridBifurcationException, ParseException, SQLException, Exception {

		checkSize(excelData);
		checkDataInRange(excelData);
		//checkDataAlreadyPresent(excelData);
	}
	
	public static List<Integer> getFiscalYear(){
		int startingFiscalYear=2012;
		List<Integer> fiscalYears=new ArrayList<Integer>();
        Date dt=new Date();	
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int year = cal.get(Calendar.YEAR);
        
        for(;year>=startingFiscalYear;year--)
        {
        	fiscalYears.add(year);
        }
        return fiscalYears;
	}


	public static List<Integer> getFinancialYears(){
		int startingFinancialcalYear=2012;
		List<Integer> financialYears=new ArrayList<Integer>();
        Date dt=new Date();	
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int year = cal.get(Calendar.YEAR);
        Calendar calEndingDate = Calendar.getInstance();
        calEndingDate.set(year, Calendar.MARCH, 31); //Year, month and day of month
        Date fiscalEndingDate = calEndingDate.getTime();
     
        if(dt.before(fiscalEndingDate))
        	{
        		year=year-1;        		
        	}
        for(;year>=startingFinancialcalYear;year--)
        {
        	financialYears.add(year);
        }
        
        return financialYears;
	}
	
	
	public void insertIntoWECReading(ArrayList<ArrayList<String>> excelData,String loginId) throws SQLException , Exception{
		int recordNo = 0;
		
		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";
		String customerScopeShutdown = "";
		String gridTripCount = "";
		
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
//			System.out.println("-------9090909090---" + excelData);
			dateValue = DateUtility.convertDateFormats(eachRecord.get(0).trim(), "dd.MM.yyyy", "dd-MMM-yyyy");
			
//			System.out.println("WEC Name:" + eachRecord.get(1).trim());
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());
//			System.out.println("WEC ID:" + wecId);
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
//			System.out.println("Date Value : " + dateValue + ",WEC ID:" + wecId + ", Remark Length : " + remark.length());
			
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();
			customerScopeShutdown = eachRecord.get(20).trim();
			gridTripCount = eachRecord.get(21).trim();
			 
			int publishStatus = getPublishStatus(wecId, dateValue);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, cumulativeGeneration, actualGeneration, "0808000022", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, cumulativeOperatingHours, actualOperatingHours, "0808000023", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, machineFault, machineFault, "0808000025", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, machineShutdown, machineShutdown, "0808000026", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultInternal, gridFaultInternal, "0808000027", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmInternal, gridShutdowmInternal, "0808000028", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, remark, gridFaultE1, gridFaultE1, "1401000001", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultE2, gridFaultE2, "1401000003", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultE3, gridFaultE3, "1401000005", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE1, gridShutdowmE1, "1401000002", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE2, gridShutdowmE2, "1401000004", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE3, gridShutdowmE3, "1401000006", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, ebLoad, ebLoad, "0810000001", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, specialShutdown, specialShutdown, "0810000002", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, customerScopeShutdown, customerScopeShutdown, "1408000001", loginId,publishStatus);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridTripCount, gridTripCount, "1408000002", loginId,publishStatus);
		}
	}
	
	public void insertRecordIntoWECReading(String dateValue, String wecId,
			String readType, String remark, String cumulativeValue,
			String actualValue, String mpId, String loginId) throws SQLException ,Exception{

		/*System.out.println("Remark:" + remark);
		System.out.println("Mpid:" +  mpId);
		System.out.println("Val:" + actualValue);
		System.out.println("Loginid:" + loginId);*/
		/*System.out.println("Cus:" + customerID );
		System.out.println("eb:" + ebId);*/
		
		if("0.0".equals(cumulativeValue) || "0".equals(cumulativeValue) || "".equals(cumulativeValue)){
			if(!(mpId.equals("1401000006") || mpId.equals("1401000005") || mpId.equals("1401000004")|| mpId.equals("1401000003")|| mpId.equals("1401000002")|| mpId.equals("1401000001"))){
				return;
			}
		}
		
		Connection conn = null;
		
		String customerID = getCustomerIDFromWECId(wecId);
		String ebId = getEbIdFromWECId(wecId);
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		/*System.out.println("Remark:" + remark);
		System.out.println("Mpid:" +  mpId);
		System.out.println("Val:" + actualValue);
		System.out.println("Loginid:" + loginId);
		System.out.println("Cus:" + customerID );
		System.out.println("eb:" + ebId);
		System.out.println("WEC ID : " + wecId);*/
//		String customerID = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			int publishStatus = getPublishStatus(wecId, dateValue);
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,? As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id) " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = ? , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? , S_Remarks = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, S_Remarks, N_Publish_Fact)  " + 
						"        Values (Scadadw.WEC_SCADA_READING_ID.Nextval ,?, ?, ?, ?, ?, ?, " + 
						"                ?, localtimestamp, ?, localtimestamp, ?, " + 
						"                ?, ?, ?, 0) " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			
			//Source Data
			prepStmt.setObject(1, dateValue);
			prepStmt.setObject(2, mpId);
			prepStmt.setObject(3, wecId);
			
			//Update Data
			prepStmt.setObject(4, cumulativeValue);
			prepStmt.setObject(5, loginId);
			prepStmt.setDouble(6, Double.parseDouble(actualValue));
			prepStmt.setObject(7, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			
			//Insert Data
			prepStmt.setObject(8, dateValue);
			prepStmt.setObject(9, mpId);
			prepStmt.setObject(10, wecId);
			prepStmt.setObject(11, ebId);
			prepStmt.setObject(12, customerID);
			prepStmt.setDouble(13, Double.parseDouble(cumulativeValue));
			
			prepStmt.setObject(14, loginId);
			prepStmt.setObject(15, loginId);
			prepStmt.setDouble(16, Double.parseDouble(actualValue));
			
			prepStmt.setObject(17, readType);
			prepStmt.setObject(18, publishStatus);
			prepStmt.setObject(19, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			//System.out.println("00000000:::" + ((remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark));
			
			prepStmt.executeUpdate();
			
			prepStmt.close();
			
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public void insertRecordIntoWECReading(String dateValue, String wecId,
			String readType, String remark, String cumulativeValue,
			String actualValue, String mpId, String loginId, int publishStatus) throws SQLException ,Exception{

		/*System.out.println("Remark:" + remark);
		System.out.println("Mpid:" +  mpId);
		System.out.println("Val:" + actualValue);
		System.out.println("Loginid:" + loginId);*/
		/*System.out.println("Cus:" + customerID );
		System.out.println("eb:" + ebId);*/
		
		if("0.0".equals(cumulativeValue) || "0".equals(cumulativeValue) || "".equals(cumulativeValue)){
			if(!(mpId.equals("1401000006") || mpId.equals("1401000005") || mpId.equals("1401000004")|| mpId.equals("1401000003")|| mpId.equals("1401000002")|| mpId.equals("1401000001"))){
				return;
			}
		}
		
		Connection conn = null;
		
		String customerID = getCustomerIDFromWECId(wecId);
		String ebId = getEbIdFromWECId(wecId);
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		/*System.out.println("Remark:" + remark);
		System.out.println("Mpid:" +  mpId);
		System.out.println("Val:" + actualValue);
		System.out.println("Loginid:" + loginId);
		System.out.println("Cus:" + customerID );
		System.out.println("eb:" + ebId);
		System.out.println("WEC ID : " + wecId);*/
//		String customerID = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,? As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id) " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = ? , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? , S_Remarks = ?, N_publish = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, S_Remarks, N_Publish_Fact)  " + 
						"        Values (Scadadw.WEC_SCADA_READING_ID.Nextval ,?, ?, ?, ?, ?, ?, " + 
						"                ?, localtimestamp, ?, localtimestamp, ?, " + 
						"                ?, ?, ?, 0) " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			
			//Source Data
			prepStmt.setObject(1, dateValue);
			prepStmt.setObject(2, mpId);
			prepStmt.setObject(3, wecId);
			
			//Update Data
			prepStmt.setObject(4, cumulativeValue);
			prepStmt.setObject(5, loginId);
			prepStmt.setDouble(6, Double.parseDouble(actualValue));
			prepStmt.setObject(7, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			prepStmt.setObject(8, publishStatus);
			
			//Insert Data
			prepStmt.setObject(9, dateValue);
			prepStmt.setObject(10, mpId);
			prepStmt.setObject(11, wecId);
			prepStmt.setObject(12, ebId);
			prepStmt.setObject(13, customerID);
			prepStmt.setDouble(14, Double.parseDouble(cumulativeValue));
			
			prepStmt.setObject(15, loginId);
			prepStmt.setObject(16, loginId);
			prepStmt.setDouble(17, Double.parseDouble(actualValue));
			
			prepStmt.setObject(18, readType);
			prepStmt.setObject(19, publishStatus);
			prepStmt.setObject(20, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			//System.out.println("00000000:::" + ((remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark));
			
			prepStmt.executeUpdate();
			
			prepStmt.close();
			
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private int getPublishStatus(String wecId, String dateValue) throws SQLException {
		int publishStatus = 0;
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String query = 	"Select count(1) as Unpublish_Count " + 
						"from tbl_wec_reading " + 
						"where D_reading_date = ? " + 
						"and S_wec_id = ? " + 
						"and N_publish = 0 ";
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			stmt = connection.prepareStatement(query);
			
			stmt.setString(1, dateValue);
			stmt.setString(2, wecId);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				
				if(rs.getInt("Unpublish_Count") == 0) publishStatus = 1;
				
			}
		}
		finally{
			try{
				if(connection != null) wcareConnector.returnConnectionToPool(connection);
				if(stmt != null) stmt.close();
				if(rs != null) rs.close();
			} catch(Exception e){
				MethodClass.displayMethodClassName();e.printStackTrace();
			}
		}
		return publishStatus;
	}

	public String getEbIdFromWECId(String wecId) throws SQLException , Exception{
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String ebId = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select S_EB_Id " + 
						"From TBL_WEC_MASTER " + 
						"where S_wec_id = ? ";  

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ebId = rs.getString("S_EB_Id");
			}
			return ebId;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getCustomerIDFromWECId(String wecId) throws SQLException, Exception {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String customerId = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select S_Customer_Id " + 
						"From TBL_WEC_MASTER " + 
						"where S_wec_id = ? " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				customerId = rs.getString("S_Customer_Id");
			}
			return customerId;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	private void checkSize(ArrayList<ArrayList<String>> excelData) throws GridBifurcationException {
		int recordNo = 0;
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(!(eachRecord.size() == 22)){
				throw new GridBifurcationException("message.err.gridbifurcation.data.properformat", recordNo);
			}
		}
	}

	private void checkDataInRange(ArrayList<ArrayList<String>> excelData) throws ParseException, GridBifurcationException, SQLException, Exception{
		int recordNo = 0;

		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";

		double previousDayCumulativeGeneration = 0;
		double previousDayCumulativeOperatingHours = 0;
		int totalMinutes = 0;
		ArrayList<Double> previousDayCumulativeGenerationOperatingHr = null;
		int wecScadaStatus = 0;

		double gridTripCount = 0;
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
			dateValue = eachRecord.get(0).trim();
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());

			/*Checking WEC Id for given WEC Description*/
			if(wecId == null){
				throw new GridBifurcationException("message.err.gridbifurcation.wecdata", recordNo);
			}
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();

			/*Checking date in 'dd.MM.yyyy' format*/
			DateUtility.dateValidatorIndd_MM_yyyy(dateValue);

			/*Checking date prior to today*/
			if(DateUtility.compareGivenDateWithTodayInTermsOfDays(dateValue, "dd.MM.yyyy") > -1){
				throw new GridBifurcationException("message.err.gridbifurcation.date.priortoday", recordNo);
			}

			/*Checking Scada Status for Cumulative generation and operating hours*/
			wecScadaStatus = getScadaStatus(wecId);

			if(wecScadaStatus == 0){
				previousDayCumulativeGenerationOperatingHr = getPreviousDayCumulativeGenerationOperatingHr(wecId, dateValue, "dd.MM.yyyy");

				//For no previous date data
				if(!(previousDayCumulativeGenerationOperatingHr.size() == 0)){
					previousDayCumulativeGeneration = previousDayCumulativeGenerationOperatingHr.get(0);
					previousDayCumulativeOperatingHours = previousDayCumulativeGenerationOperatingHr.get(1);

					if(previousDayCumulativeGeneration > Double.parseDouble(cumulativeGeneration)){
						throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative", recordNo);
					}
					if(previousDayCumulativeOperatingHours > Double.parseDouble(cumulativeOperatingHours)){
						throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative", recordNo);
					}
				}
			}
			else{
				if(Double.parseDouble(cumulativeGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(cumulativeOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(actualGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.oneday.scada", recordNo);
				}
				if(Double.parseDouble(actualOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.oneday.scada", recordNo);
				}
			}

			/*Checking for time in 24 hour format and in range 0.0 to 24.0*/
			for(int i = 8; i < eachRecord.size() - 1 ; i++){
//				System.out.println("Row No : " + recordNo);
				if(!Time24HoursValidator.validate(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."))){
					throw new GridBifurcationException("message.err.gridbifurcation.time.properformat", recordNo);
				}
			}

			//Operating hour + Fault hour < 30
			for(int i = 7; i < eachRecord.size() - 2; i++){
				totalMinutes += Time24HoursValidator.getMinutesFrom24HrFormat(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."));
				//System.out.println("aaaaaaaa:" + totalMinutes);
				if(totalMinutes > 30 * 60){
					throw new GridBifurcationException("message.err.gridbifurcation.total.thirtyhour", recordNo);
				}
			}
			totalMinutes = 0;
			
			//To check the Grid Tripped is a numerical Digit 
			//To check the grid trip count greater than or equal to 0
			if(!Time24HoursValidator.validateWholeNumber((eachRecord.get(eachRecord.size() - 1)).trim())){
				gridTripCount = 0;
				throw new GridBifurcationException("message.err.gridbifurcation.gridtrip.lessthanzero", recordNo);
			}
			
		}
	}
	
	public String getWECIdFromDescription(String wecDescription) throws SQLException, Exception {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		Connection conn = null;
		String wecID = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select S_WEC_ID From Customer_meta_data Where S_Wecshort_Descr in (?)";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecDescription);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecID = rs.getString("S_WEC_ID");
			}
			return wecID;
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private int getScadaStatus(String wecId) throws SQLException, Exception {
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		//System.out.println("WEC Id:" + wecId);
		int scadaStatus = -1;
		
		Connection conn = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select S_SCADA_FLAG " + 
					"from tbl_wec_master " + 
					"where S_wec_id = ? ";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				scadaStatus = Integer.parseInt(rs.getString("S_SCADA_FLAG"));
			}
			return scadaStatus;
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<Double>  getPreviousDayCumulativeGenerationOperatingHr(String wecId,
			String dateValue, String dateFormat) throws SQLException, Exception {

		DateTime givenDate = new DateTime(DateUtility.stringDateFormatToUtilDate(dateValue, dateFormat));
		
		Connection conn = null;
		ArrayList<Double> generationOperatingHour = new ArrayList<Double>();
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
//		MethodClass.displayMethodClassName();
		/*System.out.println("Current Date : " + dateValue);
		System.out.println("Date - 7 : " + givenDate.minusDays(7).toString("dd-MMM-yyyy"));
		System.out.println("Date - 1 : " + givenDate.minusDays(1).toString("dd-MMM-yyyy"));
		System.out.println("WEC Id:" + wecId);*/
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"select N_Value " + 
					"from tbl_wec_reading " + 
					"where D_reading_date between ? and ? " + 
					"and S_mp_id in ('0808000022','0808000023') " + 
					"and S_WEC_ID = ? " + 
					"order by D_reading_date desc, S_MP_ID " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, givenDate.minusDays(7).toString("dd-MMM-yyyy"));
			prepStmt.setObject(2, givenDate.minusDays(1).toString("dd-MMM-yyyy"));
			prepStmt.setObject(3, wecId);
			rs = prepStmt.executeQuery();
			if(rs.next()) {
				generationOperatingHour.add(rs.getDouble(1));
				rs.next();
				generationOperatingHour.add(rs.getDouble(1));
			}
			////System.out.println("8778888888888888:" + generationOperatingHour);
			return generationOperatingHour;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public void validateDataForInitialReading(
			ArrayList<ArrayList<String>> excelData) throws ParseException, GridBifurcationException, SQLException, Exception {
		checkSize(excelData);
		checkDataInRangeForInitialReading(excelData);
	}

	private void checkDataInRangeForInitialReading(
			ArrayList<ArrayList<String>> excelData) throws SQLException, GridBifurcationException, ParseException, Exception{
		int recordNo = 0;

		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";

		double previousDayCumulativeGeneration = 0;
		double previousDayCumulativeOperatingHours = 0;
		int totalMinutes = 0;
		ArrayList<Double> previousDayCumulativeGenerationOperatingHr = null;
		int wecScadaStatus = 0;

		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
			dateValue = eachRecord.get(0).trim();
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());

			/*Checking WEC Id for given WEC Description*/
			if(wecId == null){
				throw new GridBifurcationException("message.err.gridbifurcation.wecdata", recordNo);
			}
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();

			/*Checking date in 'dd.MM.yyyy' format*/
			DateUtility.dateValidatorIndd_MM_yyyy(dateValue);

			/*Checking date prior to today*/
			if(DateUtility.compareGivenDateWithTodayInTermsOfDays(dateValue, "dd.MM.yyyy") > -1){
				throw new GridBifurcationException("message.err.gridbifurcation.date.priortoday", recordNo);
			}

			/*Checking Scada Status for Cumulative generation and operating hours*/
			/*wecScadaStatus = getScadaStatus(wecId);

			if(wecScadaStatus == 0){
				previousDayCumulativeGenerationOperatingHr = getPreviousDayCumulativeGenerationOperatingHr(wecId, dateValue, "dd.MM.yyyy");

				//For no previous date data
				if(!(previousDayCumulativeGenerationOperatingHr.size() == 0)){
					previousDayCumulativeGeneration = previousDayCumulativeGenerationOperatingHr.get(0);
					previousDayCumulativeOperatingHours = previousDayCumulativeGenerationOperatingHr.get(1);

					if(previousDayCumulativeGeneration > Double.parseDouble(cumulativeGeneration)){
						throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative", recordNo);
					}
					if(previousDayCumulativeOperatingHours > Double.parseDouble(cumulativeOperatingHours)){
						throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative", recordNo);
					}
				}
			}
			else{
				if(Double.parseDouble(cumulativeGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(cumulativeOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(actualGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.oneday.scada", recordNo);
				}
				if(Double.parseDouble(actualOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.oneday.scada", recordNo);
				}
			}*/

			/*Checking for time in 24 hour format and in range 0.0 to 24.0*/
			/*for(int i = 8; i < eachRecord.size(); i++){
				if(!Time24HoursValidator.validate(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."))){
					throw new GridBifurcationException("message.err.gridbifurcation.time.properformat", recordNo);
				}
			}*/

			//Operating hour + Fault hour < 30
			/*for(int i = 7; i < eachRecord.size() - 2; i++){
				totalMinutes += Time24HoursValidator.getMinutesFrom24HrFormat(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."));
				//System.out.println("aaaaaaaa:" + totalMinutes);
				if(totalMinutes > 30 * 60){
					throw new GridBifurcationException("message.err.gridbifurcation.total.thirtyhour", recordNo);
				}
			}
			totalMinutes = 0;*/
		}
	}

	public void validateDataForScadaJumpData(
			ArrayList<ArrayList<String>> excelData) throws ParseException, SQLException, GridBifurcationException,Exception {
		checkSize(excelData);
		checkDataInRangeForScadaDataJump(excelData);
	}

	private void checkDataInRangeForScadaDataJump(
			ArrayList<ArrayList<String>> excelData) throws ParseException, GridBifurcationException, SQLException, Exception{
		int recordNo = 0;

		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";

		double previousDayCumulativeGeneration = 0;
		double previousDayCumulativeOperatingHours = 0;
		int totalMinutes = 0;
		ArrayList<Double> previousDayCumulativeGenerationOperatingHr = null;
		int wecScadaStatus = 0;

		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
			dateValue = eachRecord.get(0).trim();
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());

			/*Checking WEC Id for given WEC Description*/
			if(wecId == null){
				throw new GridBifurcationException("message.err.gridbifurcation.wecdata", recordNo);
			}
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();

			/*Checking date in 'dd.MM.yyyy' format*/
			DateUtility.dateValidatorIndd_MM_yyyy(dateValue);

			/*Checking date prior to today*/
			if(DateUtility.compareGivenDateWithTodayInTermsOfDays(dateValue, "dd.MM.yyyy") > -1){
				throw new GridBifurcationException("message.err.gridbifurcation.date.priortoday", recordNo);
			}

			/*Checking Scada Status for Cumulative generation and operating hours*/
			wecScadaStatus = getScadaStatus(wecId);

			if(wecScadaStatus == 0 || wecScadaStatus == 1){
//			if(wecScadaStatus == 0){
				previousDayCumulativeGenerationOperatingHr = getPreviousDayCumulativeGenerationOperatingHr(wecId, dateValue, "dd.MM.yyyy");

				//For no previous date data
				if(!(previousDayCumulativeGenerationOperatingHr.size() == 0)){
					previousDayCumulativeGeneration = previousDayCumulativeGenerationOperatingHr.get(0);
					previousDayCumulativeOperatingHours = previousDayCumulativeGenerationOperatingHr.get(1);

					
					if(previousDayCumulativeGeneration > Double.parseDouble(cumulativeGeneration)){
						throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative", recordNo);
					}
					if(previousDayCumulativeOperatingHours > Double.parseDouble(cumulativeOperatingHours)){
						throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative", recordNo);
					}
				}
			}
			else{
				if(Double.parseDouble(cumulativeGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(cumulativeOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(actualGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.oneday.scada", recordNo);
				}
				if(Double.parseDouble(actualOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.oneday.scada", recordNo);
				}
			}

			/*Checking for time in 24 hour format and in range 0.0 to 24.0*/
			for(int i = 8; i < eachRecord.size(); i++){
				if(!Time24HoursValidator.validate(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."))){
					throw new GridBifurcationException("message.err.gridbifurcation.time.properformat", recordNo);
				}
			}

			//Operating hour + Fault hour < 30
			for(int i = 7; i < eachRecord.size() - 2; i++){
				totalMinutes += Time24HoursValidator.getMinutesFrom24HrFormat(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."));
				//System.out.println("aaaaaaaa:" + totalMinutes);
				if(totalMinutes > 30 * 60){
					throw new GridBifurcationException("message.err.gridbifurcation.total.thirtyhour", recordNo);
				}
			}
			totalMinutes = 0;
		}
	}
}

