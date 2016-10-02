package com.enercon.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.NumberUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.WecLocationData;
import com.enercon.model.report.ManyWECsManyDatesDateWiseTotal;
import com.enercon.model.report.ManyWECsManyDatesTotal;
import com.enercon.model.report.ManyWECsManyDatesWECWiseTotal;
import com.enercon.model.report.ManyWECsOneDateDateWiseTotal;
import com.enercon.model.report.ManyWECsOneDateTotal;
import com.enercon.model.report.ManyWECsOneDateWECWiseTotal;
import com.enercon.model.report.OneWECManyDatesDateWiseTotal;
import com.enercon.model.report.OneWECManyDatesWECWiseTotal;
import com.enercon.model.report.OneWECOneDayInfoOrTotal;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;
import com.enercon.sqlQuery.AllQueries;

public class WECParameterVoDao implements WcareConnector, AllQueries {
	 private final static Logger logger = Logger.getLogger(WECParameterVoDao.class);
	protected long generation = 0  ;
	protected long cumulativeGeneration =  0 ;
	protected long operatingHour =  0 ;
	protected long totalOperatingHour = 0  ;
	protected String remark =  new String() ;
	protected long lullHours =  0 ;
	protected double mavial =  0.0 ;
	protected double gavial =  0.0 ;
	protected double capacityFactor =  0.0 ;
	protected double giavail =  0.0 ;
	protected double miavail =  0.0 ;
	protected int trialRun =  0 ;
	protected long machineFault = 0  ;
	protected long machineShutdown =  0 ;
	protected long loadRestriction =  0 ;
	protected long internalFault = 0  ;
	protected long internalShutdown =  0 ;
	protected long externalGridFault = 0  ;
	protected long externalGridShutdown =  0 ;
	protected long wecSpecialShutdown = 0 ;

	//1A-1B
	public OneWECOneDayInfoOrTotal getOneWECOneDayInfoOrTotal(String wecId, String readingDate) throws SQLException{
		Connection conn = null;
		String oneWECOneDayInfoOrTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String remark = "";
		OneWECOneDayInfoOrTotal oneWECOneDayInfoOrTotalModel = null;
		int totalWECsCount = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECOneDayInfoOrTotalQuery = 
				"Select Count(*) As Total_Wec, " + 
				"Max(S_Remarks) as remark, Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
				"sum(Lullhrs) as Lullhrs, " + 
				"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
				"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
				"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
				"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
				"From Meta_Data " + 
				"Where S_Wec_Id = ? " + 
				"And D_Reading_Date = ? "; 

			prepStmt = conn.prepareStatement(oneWECOneDayInfoOrTotalQuery);
			
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				totalWECsCount = rs.getInt("Total_WEC");

				if(totalWECsCount == 0){
					return null;
				}
				else{
					
					generation = new BigDecimal(rs.getString("generation")).longValue();
					operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
					lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
					mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
					gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
					capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
					giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
					miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
					trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
					machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
					machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
					internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
					internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
					externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
					externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
					wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
					loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					remark = (rs.getString("remark") == null ? "NA" : rs.getString("remark")); 
					
					oneWECOneDayInfoOrTotalModel = new OneWECOneDayInfoOrTotal(wecId, readingDate, remark,generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
				}
			}

			return oneWECOneDayInfoOrTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}
	
	//2A
	//Both the dates in 'dd-MMM-yyyy' format
	public OneWECManyDatesWECWiseTotal getOneWECManyDatesWECWiseTotal(String wecId, String fromDate, String toDate) throws SQLException{
		Connection conn = null;
		String oneWECManyDatesWECWiseTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		OneWECManyDatesWECWiseTotal oneWECManyDatesWECWiseTotalModel = null;
		int totalWECsCount = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECManyDatesWECWiseTotalQuery = 
					"Select Count(*) As Total_Wec, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs)/60 As Operatinghrs, " + 
					"sum(Lullhrs) as Lullhrs, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
					"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					"From Meta_Data " + 
					"Where S_Wec_Id = ? " + 
					"And D_Reading_Date Between ? And ? " ; 
 

			prepStmt = conn.prepareStatement(oneWECManyDatesWECWiseTotalQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromDate);
			prepStmt.setString(3, toDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				totalWECsCount = rs.getInt("Total_WEC");

				if(totalWECsCount == 0){
					return null;
				}
				else{
					generation = new BigDecimal(rs.getString("generation")).longValue();
					operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
					lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
					mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
					gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
					capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
					giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
					miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
					trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
					machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
					machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
					internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
					internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
					externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
					externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
					wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
					loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;

					oneWECManyDatesWECWiseTotalModel = new OneWECManyDatesWECWiseTotal(wecId, fromDate, toDate, generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
				}
			}

			return oneWECManyDatesWECWiseTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}
	
	//2B
	public OneWECManyDatesDateWiseTotal getOneWECManyDatesDateWiseTotal(String wecId, String fromDate, String toDate) throws SQLException{
		long overallgeneration = 0  ;
		long overalloperatingHour =  0 ;
		long overalllullHours =  0 ;
		double overallmavial =  0.0 ;
		double overallgavial =  0.0 ;
		double overallcapacityFactor =  0.0 ;
		double overallgiavail =  0.0 ;
		double overallmiavail =  0.0 ;
		int overalltrialRun =  0 ;
		long overallmachineFault = 0  ;
		long overallmachineShutdown =  0 ;
		long overallloadRestriction =  0 ;
		long overallinternalFault = 0  ;
		long overallinternalShutdown =  0 ;
		long overallexternalGridFault = 0  ;
		long overallexternalGridShutdown =  0 ;
		long overallwecSpecialShutdown = 0 ;
		int overallWecCount = 0;
		String remark = "";

		Connection conn = null;
		String oneWECManyDatesDateWiseTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		java.sql.Date currentReadingDate = null;
		OneWECManyDatesDateWiseTotal oneWECManyDatesDateWiseTotalModel = null;

		List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList = new ArrayList<OneWECOneDayInfoOrTotal>();

		OneWECOneDayInfoOrTotal oneWECOneDayInfoOrTotalModel = new OneWECOneDayInfoOrTotal();
		int totalWECsCount = 0;
		boolean isDataAvailable = false;
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECManyDatesDateWiseTotalQuery = 
					"Select D_reading_date,Count(*) As Total_Wec,max(S_remarks) as remark, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
					"sum(Lullhrs) as Lullhrs, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
					"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					"From Meta_Data " + 
					"Where S_Wec_Id = ? " + 
					"And D_Reading_Date Between ? And ? " + 
					"Group By D_reading_date " + 
					"Order By D_reading_date " ; 
 

			prepStmt = conn.prepareStatement(oneWECManyDatesDateWiseTotalQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromDate);
			prepStmt.setString(3, toDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				totalWECsCount = rs.getInt("Total_WEC");

				if(totalWECsCount == 0){
					continue;
				}
				else{
					isDataAvailable = true;
					overallWecCount += totalWECsCount;
					currentReadingDate = rs.getDate("D_READING_DATE");
					generation = new BigDecimal(rs.getString("generation")).longValue();
					operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
					lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
					mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
					gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
					capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
					giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
					miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
					trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
					machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
					machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
					internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
					internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
					externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
					externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
					wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
					loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					remark = (rs.getString("Remark") == null ? "No Remarks" : rs.getString("Remark"));
					
					overallgeneration += generation;
					overalloperatingHour += operatingHour;
					overalllullHours += lullHours;
					overallmavial += (mavial * totalWECsCount);
					overallgavial += (gavial * totalWECsCount);
					overallcapacityFactor	+=	 (capacityFactor * totalWECsCount);
					overallgiavail += (giavail * totalWECsCount);
					overallmiavail += (miavail * totalWECsCount);
					overalltrialRun += trialRun;
					overallmachineFault += machineFault;
					overallmachineShutdown += machineShutdown;
					overallinternalFault += internalFault;
					overallinternalShutdown += internalShutdown;
					overallexternalGridFault += externalGridFault;
					overallexternalGridShutdown += externalGridShutdown;
					overallwecSpecialShutdown += wecSpecialShutdown;
					overallloadRestriction += loadRestriction;

					oneWECOneDayInfoOrTotalModel = new OneWECOneDayInfoOrTotal(wecId, DateUtility.sqlDateToStringDate(currentReadingDate, "dd-MMM-yyyy"), remark, generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
					oneWECOneDayInfoOrTotalList.add(oneWECOneDayInfoOrTotalModel);
				}
			}
			if(!isDataAvailable){
				return null;
			}
			oneWECManyDatesDateWiseTotalModel = new OneWECManyDatesDateWiseTotal(wecId, fromDate, toDate,  oneWECOneDayInfoOrTotalList, overallgeneration, overalloperatingHour, overalllullHours, overallmavial/overallWecCount, overallgavial/overallWecCount, overallcapacityFactor/overallWecCount, overallgiavail/overallWecCount, overallmiavail/overallWecCount, overalltrialRun, overallmachineFault, overallmachineShutdown, overallloadRestriction, overallinternalFault, overallinternalShutdown, overallexternalGridFault, overallexternalGridShutdown, overallwecSpecialShutdown); 
			return oneWECManyDatesDateWiseTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	//3A
	public ManyWECsOneDateWECWiseTotal getManyWECsOneDateWECWiseTotal(Set<String> wecIds, String readingDate) throws SQLException{
		long overallgeneration = 0  ;
		long overalloperatingHour =  0 ;
		long overalllullHours =  0 ;
		double overallmavial =  0.0 ;
		double overallgavial =  0.0 ;
		double overallcapacityFactor =  0.0 ;
		double overallgiavail =  0.0 ;
		double overallmiavail =  0.0 ;
		int overalltrialRun =  0 ;
		long overallmachineFault = 0  ;
		long overallmachineShutdown =  0 ;
		long overallloadRestriction =  0 ;
		long overallinternalFault = 0  ;
		long overallinternalShutdown =  0 ;
		long overallexternalGridFault = 0  ;
		long overallexternalGridShutdown =  0 ;
		long overallwecSpecialShutdown = 0 ;
		String remark = "";
		
		int overallWecCount = 0;
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		Connection conn = null;
		String manyWECsOneDateWECWiseTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String currentWECId = "";
		OneWECOneDayInfoOrTotal oneWECOneDayInfoOrTotalModel = null;

		List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList = new ArrayList<OneWECOneDayInfoOrTotal>();

		ManyWECsOneDateWECWiseTotal manyWECsOneDateWECWiseTotalModel = new ManyWECsOneDateWECWiseTotal();
		int totalWECsCount = 0;
		boolean isDataAvailable = false;
		try {
			conn = wcareConnector.getConnectionFromPool();
			manyWECsOneDateWECWiseTotalQuery = 
					"Select S_WEC_ID,Count(*) As Total_Wec,max(S_remarks) as remark, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
					"sum(Lullhrs) as Lullhrs, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
					"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					/*"From Meta_Data_GR " +*/
					"From Meta_Data " +
					"Where S_Wec_Id In " + wecIdsInString +  
					"And D_Reading_Date = ? " + 
					"Group By S_WEC_ID " + 
					"Order By S_WEC_ID " ; 

			prepStmt = conn.prepareStatement(manyWECsOneDateWECWiseTotalQuery);
			prepStmt.setString(1, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				totalWECsCount = rs.getInt("Total_WEC");

				if(totalWECsCount == 0){
					continue;
				}
				else{
					isDataAvailable = true;
					overallWecCount += totalWECsCount;
					currentWECId = rs.getString("S_WEC_ID");
					generation = new BigDecimal(rs.getString("generation")).longValue();
					operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
					lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
					mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
					gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
					capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
					giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
					miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
					trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
					machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
					machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
					internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
					internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
					externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
					externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
					wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
					loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					remark = (rs.getString("Remark") == null ? "No Remark" : rs.getString("Remark"));
					
					overallgeneration += generation;
					overalloperatingHour += operatingHour;
					overalllullHours += lullHours;
					overallmavial += (mavial * totalWECsCount);
					overallgavial += (gavial * totalWECsCount);
					overallcapacityFactor	+=	 (capacityFactor * totalWECsCount);
					overallgiavail += (giavail * totalWECsCount);
					overallmiavail += (miavail * totalWECsCount);
					overalltrialRun += trialRun;
					overallmachineFault += machineFault;
					overallmachineShutdown += machineShutdown;
					overallinternalFault += internalFault;
					overallinternalShutdown += internalShutdown;
					overallexternalGridFault += externalGridFault;
					overallexternalGridShutdown += externalGridShutdown;
					overallwecSpecialShutdown += wecSpecialShutdown;
					overallloadRestriction += loadRestriction;

					oneWECOneDayInfoOrTotalModel = new OneWECOneDayInfoOrTotal(currentWECId, readingDate, remark, generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
					oneWECOneDayInfoOrTotalList.add(oneWECOneDayInfoOrTotalModel);
				}
			}
			if(!isDataAvailable){
				return null;
			}
			manyWECsOneDateWECWiseTotalModel = new ManyWECsOneDateWECWiseTotal(wecIds, readingDate, oneWECOneDayInfoOrTotalList, overallgeneration, overalloperatingHour, overalllullHours, overallmavial/overallWecCount, overallgavial/overallWecCount, overallcapacityFactor/overallWecCount, overallgiavail/overallWecCount, overallmiavail/overallWecCount, overalltrialRun, overallmachineFault, overallmachineShutdown, overallloadRestriction, overallinternalFault, overallinternalShutdown, overallexternalGridFault, overallexternalGridShutdown, overallwecSpecialShutdown); 
			return manyWECsOneDateWECWiseTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	//3B
	public ManyWECsOneDateDateWiseTotal getManyWECsOneDateDateWiseTotal(Set<String> wecIds, String readingDate) throws SQLException{
		
		Connection conn = null;
		String manyWECsOneDateDateWiseTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		ManyWECsOneDateDateWiseTotal manyWECsOneDateDateWiseTotalModel = null;
		int totalWECsCount = 0;
		int currentWECsCount = 0;
		List<String> wecIdsList = new ArrayList<String>(wecIds);
		boolean isDataAvailable = false;
		try {
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdsSplit : GlobalUtils.splitArrayList(wecIdsList, 800)){
				manyWECsOneDateDateWiseTotalQuery = 
					"Select count(*) as total_wec, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
					"sum(Lullhrs) as Lullhrs,  sum(TRIALRUN) as TRIALRUN, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
					"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					"From Meta_Data_GR " + 
					"Where S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(wecIdsSplit) + 
					"and D_reading_date = ? " ;
				
				prepStmt = conn.prepareStatement(manyWECsOneDateDateWiseTotalQuery);
				prepStmt.setString(1, readingDate);
				//System.out.println(oneWECTotalForBetweenDaysQuery);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					currentWECsCount = rs.getInt("Total_WEC");
					if(currentWECsCount == 0){
						continue;
					}
					else{
						isDataAvailable = true;
						totalWECsCount += currentWECsCount;
						generation += new BigDecimal(rs.getString("generation")).longValue();
						operatingHour += new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
						lullHours += new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
						mavial += NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue(), 2);
						gavial += NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue(), 2);
						capacityFactor	+=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue(), 2);
						giavail += NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue(), 2);
						miavail += NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue(), 2);
						trialRun += new BigDecimal(rs.getString("TRIALRUN")).intValue();
						machineFault += new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
						machineShutdown += new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
						internalFault += new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
						internalShutdown += new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
						externalGridFault += new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
						externalGridShutdown += new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
						wecSpecialShutdown += new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
						loadRestriction += new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					}
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			}
			if(!isDataAvailable){
				return null;
			}
			mavial = NumberUtility.round(mavial/totalWECsCount, 2);
			gavial = NumberUtility.round(gavial/totalWECsCount, 2);
			capacityFactor	=	 NumberUtility.round(capacityFactor/totalWECsCount, 2);
			giavail = NumberUtility.round(giavail/totalWECsCount, 2);
			miavail = NumberUtility.round(miavail/totalWECsCount, 2);
			manyWECsOneDateDateWiseTotalModel = new ManyWECsOneDateDateWiseTotal(wecIds, readingDate, generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);

			return manyWECsOneDateDateWiseTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}
	
	//4A

		public ManyWECsManyDatesWECWiseTotal getManyWECsManyDatesWECWiseTotal(Set<String> wecIds, String fromDate, String toDate) throws SQLException{
			
			long overallgeneration = 0  ;
			long overalloperatingHour =  0 ;
			long overalllullHours =  0 ;
			double overallmavial =  0.0 ;
			double overallgavial =  0.0 ;
			double overallcapacityFactor =  0.0 ;
			double overallgiavail =  0.0 ;
			double overallmiavail =  0.0 ;
			int overalltrialRun =  0 ;
			long overallmachineFault = 0  ;
			long overallmachineShutdown =  0 ;
			long overallloadRestriction =  0 ;
			long overallinternalFault = 0  ;
			long overallinternalShutdown =  0 ;
			long overallexternalGridFault = 0  ;
			long overallexternalGridShutdown =  0 ;
			long overallwecSpecialShutdown = 0 ;
			int overallWecCount = 0;
			String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
			Connection conn = null;
			String manyWECsManyDatesWECWiseTotalQuery = "";
			PreparedStatement prepStmt = null;
			ResultSet rs = null;

			ManyWECsManyDatesWECWiseTotal manyWECsManyDatesWECWiseTotalModel = null;
			
			String currentWecId = "";
			List<OneWECManyDatesWECWiseTotal> oneWECManyDatesWECWiseTotalList = new ArrayList<OneWECManyDatesWECWiseTotal>();
			OneWECManyDatesWECWiseTotal oneWECManyDatesWECWiseTotalModel = null;
			
			int totalWECsCount = 0;
			try {
				conn = wcareConnector.getConnectionFromPool();
				manyWECsManyDatesWECWiseTotalQuery = 
						"Select S_WEC_ID,Count(*) As Total_Wec, " + 
						"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
						"sum(Lullhrs) as Lullhrs, " + 
						"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
						"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
						"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
						"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
						/*"From Meta_Data_GR " +*/
						"From Meta_Data " + 
						"Where S_Wec_Id In " + wecIdsInString + 
						"And D_Reading_Date between ? and ? " + 
						"Group By S_WEC_ID " + 
						"Order By S_WEC_ID " ; 
 

				prepStmt = conn.prepareStatement(manyWECsManyDatesWECWiseTotalQuery);
				prepStmt.setString(1, fromDate);
				prepStmt.setString(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					totalWECsCount = rs.getInt("Total_WEC");

					if(totalWECsCount == 0){
						continue;
					}
					else{
						overallWecCount += totalWECsCount;
						currentWecId = rs.getString("S_WEC_ID");
						generation = new BigDecimal(rs.getString("generation")).longValue();
						operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
						lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
						mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
						gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
						capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
						giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
						miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
						trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
						machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
						machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
						internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
						internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
						externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
						externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
						wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
						loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;

						overallgeneration += generation;
						overalloperatingHour += operatingHour;
						overalllullHours += lullHours;
						overallmavial += (mavial * totalWECsCount);
						overallgavial += (gavial * totalWECsCount);
						overallcapacityFactor	+=	 (capacityFactor * totalWECsCount);
						overallgiavail += (giavail * totalWECsCount);
						overallmiavail += (miavail * totalWECsCount);
						overalltrialRun += trialRun;
						overallmachineFault += machineFault;
						overallmachineShutdown += machineShutdown;
						overallinternalFault += internalFault;
						overallinternalShutdown += internalShutdown;
						overallexternalGridFault += externalGridFault;
						overallexternalGridShutdown += externalGridShutdown;
						overallwecSpecialShutdown += wecSpecialShutdown;
						overallloadRestriction += loadRestriction;

						oneWECManyDatesWECWiseTotalModel = new OneWECManyDatesWECWiseTotal(currentWecId, fromDate, toDate, generation,operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
						oneWECManyDatesWECWiseTotalList.add(oneWECManyDatesWECWiseTotalModel);
					}
				}
				manyWECsManyDatesWECWiseTotalModel = new ManyWECsManyDatesWECWiseTotal(wecIds, fromDate, toDate, oneWECManyDatesWECWiseTotalList, overallgeneration, overalloperatingHour, overalllullHours, NumberUtility.round(overallmavial/overallWecCount,2), NumberUtility.round(overallgavial/overallWecCount,2), NumberUtility.round(overallcapacityFactor/overallWecCount,2), NumberUtility.round(overallgiavail/overallWecCount,2), NumberUtility.round(overallmiavail/overallWecCount,2), overalltrialRun, overallmachineFault, overallmachineShutdown, overallloadRestriction, overallinternalFault, overallinternalShutdown, overallexternalGridFault, overallexternalGridShutdown, overallwecSpecialShutdown); 
				return manyWECsManyDatesWECWiseTotalModel;
			} finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);

			}
		}
	
	//4B
	public ManyWECsManyDatesDateWiseTotal getmanyWECsManyDatesDateWiseTotal(Set<String> wecIds, String fromDate, String toDate) throws SQLException{
		
		long overallgeneration = 0  ;
		long overalloperatingHour =  0 ;
		long overalllullHours =  0 ;
		double overallmavial =  0.0 ;
		double overallgavial =  0.0 ;
		double overallcapacityFactor =  0.0 ;
		double overallgiavail =  0.0 ;
		double overallmiavail =  0.0 ;
		int overalltrialRun =  0 ;
		long overallmachineFault = 0  ;
		long overallmachineShutdown =  0 ;
		long overallloadRestriction =  0 ;
		long overallinternalFault = 0  ;
		long overallinternalShutdown =  0 ;
		long overallexternalGridFault = 0  ;
		long overallexternalGridShutdown =  0 ;
		long overallwecSpecialShutdown = 0 ;
		int overallWecCount = 0;
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		Connection conn = null;
		String manyWECsManyDatesDateWiseTotalQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		java.sql.Date currentReadingDate = null;
		List<ManyWECsOneDateDateWiseTotal> manyWECsOneDayDateWiseTotalList = new ArrayList<ManyWECsOneDateDateWiseTotal>();
		ManyWECsOneDateDateWiseTotal manyWECsOneDayDateWiseTotalModel = null;
		ManyWECsManyDatesDateWiseTotal manyWECsManyDatesDateWiseTotalModel = null;
		int totalWECsCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			manyWECsManyDatesDateWiseTotalQuery = 
					"Select D_reading_date,Count(*) As Total_Wec, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
					"sum(Lullhrs) as Lullhrs, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
					"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					/*"From Meta_Data_GR " +*/
					"From Meta_Data " +
					"Where S_Wec_Id In " + wecIdsInString +  
					"And D_Reading_Date Between ? And ? " + 
					"Group By D_reading_date " + 
					"Order By D_reading_date " ; 

			prepStmt = conn.prepareStatement(manyWECsManyDatesDateWiseTotalQuery);
			prepStmt.setString(1, fromDate);
			prepStmt.setString(2, toDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				totalWECsCount = rs.getInt("Total_WEC");

				if(totalWECsCount == 0){
					continue;
				}
				else{
					overallWecCount += totalWECsCount;
					currentReadingDate = rs.getDate("D_Reading_date");
					generation = new BigDecimal(rs.getString("generation")).longValue();
					operatingHour = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
					lullHours = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
					mavial = NumberUtility.round(new BigDecimal(rs.getString("MAVIAL")).doubleValue()/totalWECsCount, 2);
					gavial = NumberUtility.round(new BigDecimal(rs.getString("GAVIAL")).doubleValue()/totalWECsCount, 2);
					capacityFactor	=	 NumberUtility.round(new BigDecimal(rs.getString("CFACTOR")).doubleValue()/totalWECsCount, 2);
					giavail = NumberUtility.round(new BigDecimal(rs.getString("GIAVIAL")).doubleValue()/totalWECsCount, 2);
					miavail = NumberUtility.round(new BigDecimal(rs.getString("MIAVIAL")).doubleValue()/totalWECsCount, 2);
					trialRun = new BigDecimal(rs.getString("TRIALRUN")).intValue();
					machineFault = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
					machineShutdown = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
					internalFault = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
					internalShutdown = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
					externalGridFault = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
					externalGridShutdown = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
					wecSpecialShutdown = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
					loadRestriction = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;

					overallgeneration += generation;
					overalloperatingHour += operatingHour;
					overalllullHours += lullHours;
					overallmavial += (mavial * totalWECsCount);
					overallgavial += (gavial * totalWECsCount);
					overallcapacityFactor	+=	 (capacityFactor * totalWECsCount);
					overallgiavail += (giavail * totalWECsCount);
					overallmiavail += (miavail * totalWECsCount);
					overalltrialRun += trialRun;
					overallmachineFault += machineFault;
					overallmachineShutdown += machineShutdown;
					overallinternalFault += internalFault;
					overallinternalShutdown += internalShutdown;
					overallexternalGridFault += externalGridFault;
					overallexternalGridShutdown += externalGridShutdown;
					overallwecSpecialShutdown += wecSpecialShutdown;
					overallloadRestriction += loadRestriction;

					manyWECsOneDayDateWiseTotalModel = new ManyWECsOneDateDateWiseTotal(wecIds, DateUtility.sqlDateToStringDate(currentReadingDate, "dd-MMM-yyy"), generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
					manyWECsOneDayDateWiseTotalList.add(manyWECsOneDayDateWiseTotalModel);
				}
			}
			manyWECsManyDatesDateWiseTotalModel = new ManyWECsManyDatesDateWiseTotal(wecIds, fromDate, toDate, manyWECsOneDayDateWiseTotalList, overallgeneration, overalloperatingHour, overalllullHours, overallmavial/overallWecCount, overallgavial/overallWecCount, overallcapacityFactor/overallWecCount, overallgiavail/overallWecCount, overallmiavail/overallWecCount, overalltrialRun, overallmachineFault, overallmachineShutdown, overallloadRestriction, overallinternalFault, overallinternalShutdown, overallexternalGridFault, overallexternalGridShutdown, overallwecSpecialShutdown); 
			return manyWECsManyDatesDateWiseTotalModel;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	//
	public ManyWECsManyDatesTotal getManyWECsManyDatesTotal(Set<String> wecIds, String fromDate, String toDate) throws SQLException{
		String wecIdsInString = null;
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		boolean isResultAvailable = false;
		initialiseToZero();

		List<String> wecIdsList = new ArrayList<String>(wecIds);
		ManyWECsManyDatesTotal manyWECsTotalmanyDaysReport = null;
		int totalWECsCount = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdsSplit:GlobalUtils.splitArrayList(wecIdsList, 800)){
				wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIdsSplit);
				oneWECTotalForBetweenDaysQuery = 
						"Select count(*) as total_wec, " + 
						"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
						"sum(Lullhrs) as Lullhrs,  sum(TRIALRUN) as TRIALRUN, " + 
						"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
						"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
						"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
						"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
						/*"From Meta_Data_GR " +*/
						"From Meta_Data " + 
						"Where S_Wec_Id In " + wecIdsInString + 
						"and D_reading_date between ? and ? " ;
				prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
				
				prepStmt.setString(1, fromDate);
				prepStmt.setString(2, toDate);

				rs = prepStmt.executeQuery();
				while (rs.next()) {
					totalWECsCount += rs.getInt("Total_WEC");
					if(totalWECsCount == 0){
						continue;
					}
					else{
						isResultAvailable = true;
						generation += new BigDecimal(rs.getString("generation")).longValue();
						//		 			cumulativeGeneration	=	 new BigDecimal(rs.getString("total_generation")).longValue();
						operatingHour += new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
						//					totalOperatingHour	=	 new BigDecimal(rs.getString("total_operatinghrs")).longValue()/60;
						lullHours += new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
						mavial += new BigDecimal(rs.getString("MAVIAL")).doubleValue();
						gavial += new BigDecimal(rs.getString("GAVIAL")).doubleValue();
						capacityFactor	+=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
						giavail += new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
						miavail += new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
						trialRun += new BigDecimal(rs.getString("TRIALRUN")).intValue();
						machineFault += new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
						machineShutdown += new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
						internalFault += new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
						internalShutdown += new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
						externalGridFault += new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
						externalGridShutdown += new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
						wecSpecialShutdown += new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
						loadRestriction += new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					}
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			if(!isResultAvailable){
				return null;
			}
			
			mavial = NumberUtility.round(mavial/totalWECsCount, 2);
			gavial = NumberUtility.round(gavial/totalWECsCount, 2);
			capacityFactor =	 NumberUtility.round(capacityFactor/totalWECsCount, 2);
			giavail = NumberUtility.round(giavail/totalWECsCount, 2);
			miavail = NumberUtility.round(miavail/totalWECsCount, 2);
			
			manyWECsTotalmanyDaysReport = new ManyWECsManyDatesTotal(wecIds, fromDate, toDate, generation, operatingHour, lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
			
			return manyWECsTotalmanyDaysReport;
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}
	
	public ManyWECsOneDateTotal getManyWECsOneDateTotal(Set<String> wecIds, String date) throws SQLException{
		String wecIdsInString = null;
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		boolean isResultAvailable = false;
		initialiseToZero();

		List<String> wecIdsList = new ArrayList<String>(wecIds);
		ManyWECsOneDateTotal manyWECsOneDateTotal = null;
		
		int totalWECsCount = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdsSplit:GlobalUtils.splitArrayList(wecIdsList, 800)){
				wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIdsSplit);
				oneWECTotalForBetweenDaysQuery = 
						"Select count(*) as total_wec, " + 
						"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
						"sum(Lullhrs) as Lullhrs,  sum(TRIALRUN) as TRIALRUN, " + 
						"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
						"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
						"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
						"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
						/*"From Meta_Data_GR " +*/
						"From Meta_Data " + 
						"Where S_Wec_Id In " + wecIdsInString + 
						"and D_reading_date = ? " ;
				prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
				
				prepStmt.setString(1, date);
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					totalWECsCount += rs.getInt("Total_WEC");
					if(totalWECsCount == 0){
						continue;
					}
					else{
						isResultAvailable = true;
						generation += new BigDecimal(rs.getString("generation")).longValue();
						//		 			cumulativeGeneration	=	 new BigDecimal(rs.getString("total_generation")).longValue();
						operatingHour += new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
						//					totalOperatingHour	=	 new BigDecimal(rs.getString("total_operatinghrs")).longValue()/60;
						lullHours += new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
						mavial += new BigDecimal(rs.getString("MAVIAL")).doubleValue();
						gavial += new BigDecimal(rs.getString("GAVIAL")).doubleValue();
						capacityFactor	+=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
						giavail += new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
						miavail += new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
						trialRun += new BigDecimal(rs.getString("TRIALRUN")).intValue();
						machineFault += new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
						machineShutdown += new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
						internalFault += new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
						internalShutdown += new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
						externalGridFault += new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
						externalGridShutdown += new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
						wecSpecialShutdown += new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
						loadRestriction += new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
					}
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				
			}
			if(!isResultAvailable){
				return null;
			}
			
			mavial = NumberUtility.round(mavial/totalWECsCount, 2);
			gavial = NumberUtility.round(gavial/totalWECsCount, 2);
			capacityFactor =	 NumberUtility.round(capacityFactor/totalWECsCount, 2);
			giavail = NumberUtility.round(giavail/totalWECsCount, 2);
			miavail = NumberUtility.round(miavail/totalWECsCount, 2);
			
			manyWECsOneDateTotal = new ManyWECsOneDateTotal();
			manyWECsOneDateTotal.setWecIds(wecIds);
			manyWECsOneDateTotal.setDate(date);
			manyWECsOneDateTotal.setGeneration(generation);
			manyWECsOneDateTotal.setOperatingHour(operatingHour);
			manyWECsOneDateTotal.setLullHours(lullHours);
			manyWECsOneDateTotal.setMavial(mavial);
			manyWECsOneDateTotal.setGavial(gavial);
			manyWECsOneDateTotal.setCapacityFactor(capacityFactor);
			manyWECsOneDateTotal.setGiavail(giavail);
			manyWECsOneDateTotal.setMiavail(miavail);
			manyWECsOneDateTotal.setTrialRun(trialRun);
			manyWECsOneDateTotal.setMachineFault(machineFault);
			manyWECsOneDateTotal.setMachineShutdown(machineShutdown);
			manyWECsOneDateTotal.setLoadRestriction(loadRestriction);
			manyWECsOneDateTotal.setInternalFault(internalFault);
			manyWECsOneDateTotal.setInternalShutdown(internalShutdown);
			manyWECsOneDateTotal.setExternalGridFault(externalGridFault);
			manyWECsOneDateTotal.setExternalGridShutdown(externalGridShutdown);
			manyWECsOneDateTotal.setWecSpecialShutdown(wecSpecialShutdown);
			
			//manyWECsTotalmanyDaysReport = new ManyWECsManyDatesTotal(wecIds, fromDate, toDate, generation, operatingHour, lullHours, mavial, gavial, capacityFactor,
			//giavail, miavail, trialRun, machineFault, machineShutdown, loadRestriction, internalFault, internalShutdown, externalGridFault, externalGridShutdown, wecSpecialShutdown);
			
			return manyWECsOneDateTotal;
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	private void initialiseToZero() {

		generation = 0;
		operatingHour = 0;
		lullHours = 0;
		mavial = 0;
		gavial = 0;
		capacityFactor = 0;
		giavail = 0;
		miavail = 0;
		trialRun = 0;
		machineFault = 0;
		machineShutdown = 0;
		internalFault = 0;
		internalShutdown = 0;
		externalGridFault = 0;
		externalGridShutdown = 0;
		wecSpecialShutdown = 0;
		loadRestriction = 0;
	}

	public Map<String, Map<WecLocationData, String>> getActiveWecLocationData() throws SQLException {
		Map<String, Map<WecLocationData, String>> wecIdWECDataMapping = new HashMap<String, Map<WecLocationData,String>>();
		Map<WecLocationData, String> wecData = null;
		
		String wecId = new String();
		String wecName = new String();
		String customerName = new String();
		String stateName = new String();
		String areaName = new String();
		String siteName = new String();
		
		Connection connection = null;
		String query = new String();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = "Select S_wec_id, S_Customer_Name ,S_state_name, S_Area_Name, S_Site_Name, S_Wecshort_Descr " + 
					"from customer_meta_data " + 
					"where S_Status in ('1') " ; 

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				wecData = new HashMap<WecLocationData, String>();
				wecId = resultSet.getString("S_WEC_ID");
				customerName = resultSet.getString("S_CUSTOMER_NAME");
				stateName = resultSet.getString("S_STATE_NAME");
				areaName = resultSet.getString("S_AREA_NAME");
				siteName = resultSet.getString("S_SITE_NAME");
				wecName = resultSet.getString("S_WECSHORT_DESCR");
				wecData.put(WecLocationData.STATENAME, stateName);
				wecData.put(WecLocationData.CUSTOMERNAME, customerName);
				wecData.put(WecLocationData.AREANAME, areaName);
				wecData.put(WecLocationData.SITENAME, siteName);
				wecData.put(WecLocationData.WECNAME, wecName);
				wecIdWECDataMapping.put(wecId, wecData);
			}
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);

		}
		
		return wecIdWECDataMapping;
	}

	public Set<String> getWecIdsBasedOnStateIds(Set<String> stateIds) throws SQLException {
		Set<String> wecIds = new HashSet<String>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = "Select S_wec_id " + 
					"from customer_meta_data " + 
					"where S_state_id in " + GlobalUtils.getStringFromArrayForQuery(stateIds) + 
					"and S_Status in ('1') " + 
					"group by S_wec_ID " ; 

			preparedStatement = connection.prepareStatement(query);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				wecIds.add(resultSet.getString("S_WEC_ID"));
			}
			
			return wecIds;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
		
	}
	
	public void getGeneration(Set<String> wecIds, ManyWECsManyDatesTotal total, String fromDate, String toDate) throws SQLException {
		
	}
	
	private void populateGeneration(ManyWECsManyDatesTotal total,Set<String> wecIds, FiscalYear fiscalYear){
		
	}
	
	private void populateGeneration(ManyWECsManyDatesTotal total,Set<String> wecIds, FiscalYear fiscalYear, Month month){
		
	}

	public int getGeneration(Set<String> wecIds, Month month, Year year) {
		
		return 0;
	}
}
