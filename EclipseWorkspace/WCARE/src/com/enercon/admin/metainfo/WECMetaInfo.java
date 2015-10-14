package com.enercon.admin.metainfo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.DebuggerUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.sqlQuery.AllQueries;

public class WECMetaInfo extends DebuggerUtility implements AllQueries,WcareConnector{
	private static DecimalFormat df2 = new DecimalFormat("###.##");
	
	private static String S_WEC_ID = ""  ;
	private static String S_WECSHORT_DESCR =  "" ;
	private static String S_EB_ID = "" ;
	private static String WECTYPE =  "" ;
	private static int CAPACITY =  0 ;
	private static java.sql.Date D_READING_DATE =  null ;
	private static long GENERATION = 0  ;
	private static long TOTAL_GENERATION =  0 ;
	private static long OPERATINGHRS =  0 ;
	private static long TOTAL_OPERATINGHRS = 0  ;
	private static String S_REMARKS =  new String() ;
	private static java.sql.Date D_COMMISION_DATE =  null ;
	private static long LULLHRS =  0 ;
	private static double MAVIAL =  0.0 ;
	private static double GAVIAL =  0.0 ;
	private static double CFACTOR =  0.0 ;
	private static double GIAVIAL =  0.0 ;
	private static double MIAVIAL =  0.0 ;
	private static int TRIALRUN =  0 ;
	private static long MACHINEFAULT = 0  ;
	private static long MACHINESD =  0 ;
	private static long EBLOADRST =  0 ;
	private static long INTERNALFAULT = 0  ;
	private static long INTERNALSD =  0 ;
	private static long EXTERNALFAULT = 0  ;
	private static long EXTERNALSD =  0 ;
	private static long WECLOADRST = 0 ;
	private static long CUSTOMERSCOPE=0;
	public static List<Object> getManyWECsTotalForBetweenDays(ArrayList<String> wecIds, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
	
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		
		boolean firstWECId = true;
		StringBuffer wecIdsInString = new StringBuffer();
		
		int recordCount = 0;
		
		wecIdsInString.append("(");
		for (String eachWECId : wecIds) {
			if(firstWECId){
				wecIdsInString.append("'" + eachWECId + "'");
				firstWECId = false;
			}
			else{
				wecIdsInString.append(",'" + eachWECId + "'");
			}
		}
		wecIdsInString.append(") ");
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE " +
					"From Meta_Data " +
					"Where S_Wec_Id In " + wecIdsInString + 
					"And D_Reading_Date between ? and ?  " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, fromReadingDate);
			prepStmt.setString(2, toReadingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();;
				//S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				//S_EB_ID	=	 rs.getString("S_EB_ID");
				//WECTYPE	=	 rs.getString("WECTYPE");
				//CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				//TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				//TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//remarks.append(rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS"));
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				CUSTOMERSCOPE= new BigDecimal(rs.getString("CUSTOMERSCOPE")).longValueExact()/60;
			}
			
			S_WEC_ID = new String(wecIdsInString);
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("From " + fromReadingDate + " to " + toReadingDate);
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(CUSTOMERSCOPE);
			
			return wecTotalInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
					 
					 
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	public static List<Object> getManyWECsTotalForBetweenDaysMPR(ArrayList<String> wecIds, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
	
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		
		boolean firstWECId = true;
		StringBuffer wecIdsInString = new StringBuffer();
		
		int recordCount = 0;
		
		wecIdsInString.append("(");
		for (String eachWECId : wecIds) {
			if(firstWECId){
				wecIdsInString.append("'" + eachWECId + "'");
				firstWECId = false;
			}
			else{
				wecIdsInString.append(",'" + eachWECId + "'");
			}
		}
		wecIdsInString.append(") ");
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE " +
					"From Meta_Data_MPR " +
					"Where S_Wec_Id In " + wecIdsInString + 
					"And D_Reading_Date between ? and ?  " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, fromReadingDate);
			prepStmt.setString(2, toReadingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();;
				//S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				//S_EB_ID	=	 rs.getString("S_EB_ID");
				//WECTYPE	=	 rs.getString("WECTYPE");
				//CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				//TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60;
				//TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//remarks.append(rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS"));
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				CUSTOMERSCOPE	=	 new BigDecimal(rs.getString("CUSTOMERSCOPE")).longValueExact()/60;
			}
			
			S_WEC_ID = new String(wecIdsInString);
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("From " + fromReadingDate + " to " + toReadingDate);
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(CUSTOMERSCOPE);
			
			
			return wecTotalInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
					 
					 
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	public static List<Object> getOneWECTotalForBetweenDaysMPR(String wecId, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
	
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		initialiseToZero();
		S_WEC_ID = wecId;
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECTotalForBetweenDaysMPRQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromReadingDate);
			prepStmt.setString(3, toReadingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				if(recordCount == 0){
					S_WECSHORT_DESCR = WecMasterDao.getWECDescriptionBasedOnWECId(wecId);
					wecTotalInfo.add(S_WECSHORT_DESCR);
					if(isWECTranferred(wecId, conn)){
						String remark = getWECRemarkPresentInWECMaster(wecId, conn);
						wecTotalInfo.add(remark);
					}
					else{
						wecTotalInfo.add("Data Not Available");
					}
					return wecTotalInfo;
				}
				else{
					recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();
					S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR") == null ? "NA" : rs.getString("S_WECSHORT_DESCR");
					S_EB_ID	=	 rs.getString("S_EB_ID") == null ? "NA" : rs.getString("S_EB_ID");
					WECTYPE	=	 rs.getString("WECTYPE") == null ? "NA" : rs.getString("WECTYPE");
					CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY") == null ? "0" : rs.getString("CAPACITY")).intValue();
					//D_COMMISION_DATE	=	 (Date) (rs.getDate("D_COMMISION_DATE") == null ? "NA" : rs.getDate("D_COMMISION_DATE"));
					//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
					GENERATION = new BigDecimal(rs.getString("generation") == null? "NA" : rs.getString("generation")).longValue();
					TOTAL_GENERATION	=	 new BigDecimal(rs.getString("total_generation") == null? "NA" : rs.getString("total_generation")).longValue();
					OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs") == null? "NA" : rs.getString("operatinghrs")).longValue()/60;
					TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("total_operatinghrs") == null? "NA" : rs.getString("total_operatinghrs")).longValue()/60;
					//remarks.append(rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS"));
					LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "NA" : rs.getString("LULLHRS")).longValue()/60;
					MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "NA" : rs.getString("MAVIAL")).doubleValue();
					GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "NA" : rs.getString("GAVIAL")).doubleValue();
					CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR") == null ? "0" : rs.getString("CFACTOR")).doubleValue();
					CFACTOR = new BigDecimal(rs.getString("CFACTOR") == null? "NA" : rs.getString("CFACTOR")).doubleValue();
					GIAVIAL = new BigDecimal(rs.getString("GIAVIAL") == null? "NA" : rs.getString("GIAVIAL")).doubleValue();
					MIAVIAL = new BigDecimal(rs.getString("MIAVIAL") == null? "NA" : rs.getString("MIAVIAL")).doubleValue();
					TRIALRUN = new BigDecimal(rs.getString("TRIALRUN") == null? "NA" : rs.getString("TRIALRUN")).intValue();
					MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT") == null? "NA" : rs.getString("MACHINEFAULT")).longValue()/60;
					MACHINESD = new BigDecimal(rs.getString("MACHINESD") == null? "NA" : rs.getString("MACHINESD")).longValue()/60;
					INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT") == null? "NA" : rs.getString("INTERNALFAULT")).longValue()/60;
					INTERNALSD = new BigDecimal(rs.getString("INTERNALSD") == null? "NA" : rs.getString("INTERNALSD")).longValue()/60;
					EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT") == null? "NA" : rs.getString("EXTERNALFAULT")).longValue()/60;
					EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD") == null? "NA" : rs.getString("EXTERNALSD")).longValue()/60;
					WECLOADRST = new BigDecimal(rs.getString("WECLOADRST") == null? "NA" : rs.getString("WECLOADRST")).longValue()/60;
					EBLOADRST = new BigDecimal(rs.getString("EBLOADRST") == null? "NA" : rs.getString("EBLOADRST")).longValue()/60;
					CUSTOMERSCOPE = new BigDecimal(rs.getString("CUSTOMERSCOPE") == null? "NA" : rs.getString("CUSTOMERSCOPE")).longValue()/60;
				}
			}
			
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add(S_WECSHORT_DESCR);
			wecTotalInfo.add(S_EB_ID);
			wecTotalInfo.add(WECTYPE);
			wecTotalInfo.add(CAPACITY);
			wecTotalInfo.add("From " + fromReadingDate + " to " + toReadingDate);
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add(TOTAL_GENERATION);
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add(TOTAL_OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(D_COMMISION_DATE);
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(CUSTOMERSCOPE);
			
			return wecTotalInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
			
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				 
				 
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	public static List<Object> getManyWECTotalForBetweenDaysMeta(ArrayList<String> wecIds, String fromReadingDate, String toReadingDate){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForBetweenDaysQuery = 
							"Select count(*) as total_wec, " + 
							"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString + 
							"and D_reading_date between ? and ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
			prepStmt.setString(1, fromReadingDate);
			prepStmt.setString(2, toReadingDate);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(checkManyWECTranferredStatus(wecIds, conn)){
						wecInfo.add("9");
						wecInfo.add("Machine Tranferred");
					}
					else{
						wecInfo.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{0,1,2,3,4,5,7,9,10,11,18});
					S_WEC_ID = wecIdsInString;
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	/**
	 * 27 : WEC Count
	 * @param wecIds
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getManyWECsWECWiseTotalForBetweenDaysMeta(ArrayList<String> wecIds, String fromReadingDate, String toReadingDate){
		ArrayList<Object> oneWECTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> manyWECsWECWiseTotal = new ArrayList<ArrayList<Object>>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		
		Connection conn = null;
		String manyWECsWECWiseTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			manyWECsWECWiseTotalForBetweenDaysQuery = 
					"Select S_Wec_Id,Count(*) As Total_Wec,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
					"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
					"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, " + 
					"Max(D_Commision_Date) As D_Commision_Date, " + 
					"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
					"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(CUSTOMER_SCOPE) as CUSTOMERSCOPE " + 
					"From Meta_Data " + 
					"Where S_Wec_Id In " + wecIdsInString + 
					"And D_Reading_Date Between ? And ? " + 
					"Group By S_Wec_Id " + 
					"Order By S_Wecshort_Descr " ; 

			prepStmt = conn.prepareStatement(manyWECsWECWiseTotalForBetweenDaysQuery);
			prepStmt.setString(1, fromReadingDate);
			prepStmt.setString(2, toReadingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(S_WEC_ID, conn)){
						oneWECTotal.add("9");
						oneWECTotal.add("Machine Transferred");
					}
					else{
						oneWECTotal.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					manyWECsWECWiseTotal.add(oneWECTotal);
				}
				else{
					initialiseSpecial26(rs,new Integer[]{5,10});
					addSpecial26ToList(oneWECTotal);
					oneWECTotal.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					manyWECsWECWiseTotal.add(oneWECTotal);
				}
				oneWECTotal = new ArrayList<Object>();
			}

			return manyWECsWECWiseTotal;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);  
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return manyWECsWECWiseTotal;
	}
	
	public static List<Object> getOneWECTotalForBetweenDays(String wecId, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
	
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		initialiseToZero();
		S_WEC_ID = wecId;
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECTotalForBetweenDaysQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromReadingDate);
			prepStmt.setString(3, toReadingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				if(recordCount == 0){
					S_WECSHORT_DESCR = WecMasterDao.getWECDescriptionBasedOnWECId(wecId);
					wecTotalInfo.add(S_WECSHORT_DESCR);
					if(isWECTranferred(wecId, conn)){
						String remark = getWECRemarkPresentInWECMaster(wecId, conn);
						wecTotalInfo.add(remark);
					}
					else{
						wecTotalInfo.add("Data Not Available");
					}
					return wecTotalInfo;
				}
				else{
					recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();
					S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR") == null ? "NA" : rs.getString("S_WECSHORT_DESCR");
					S_EB_ID	=	 rs.getString("S_EB_ID") == null ? "NA" : rs.getString("S_EB_ID");
					WECTYPE	=	 rs.getString("WECTYPE") == null ? "NA" : rs.getString("WECTYPE");
					CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY") == null ? "0" : rs.getString("CAPACITY")).intValue();
					//D_COMMISION_DATE	=	 (Date) (rs.getDate("D_COMMISION_DATE") == null ? "NA" : rs.getDate("D_COMMISION_DATE"));
					//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
					GENERATION = new BigDecimal(rs.getString("generation") == null? "NA" : rs.getString("generation")).longValue();
					TOTAL_GENERATION	=	 new BigDecimal(rs.getString("total_generation") == null? "NA" : rs.getString("total_generation")).longValue();
					OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs") == null? "NA" : rs.getString("operatinghrs")).longValue()/60;
					TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("total_operatinghrs") == null? "NA" : rs.getString("total_operatinghrs")).longValue()/60;
					//remarks.append(rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS"));
					LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "NA" : rs.getString("LULLHRS")).longValue()/60;
					MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "NA" : rs.getString("MAVIAL")).doubleValue();
					GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "NA" : rs.getString("GAVIAL")).doubleValue();
					CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR") == null ? "0" : rs.getString("CFACTOR")).doubleValue();
					CFACTOR = new BigDecimal(rs.getString("CFACTOR") == null? "NA" : rs.getString("CFACTOR")).doubleValue();
					GIAVIAL = new BigDecimal(rs.getString("GIAVIAL") == null? "NA" : rs.getString("GIAVIAL")).doubleValue();
					MIAVIAL = new BigDecimal(rs.getString("MIAVIAL") == null? "NA" : rs.getString("MIAVIAL")).doubleValue();
					TRIALRUN = new BigDecimal(rs.getString("TRIALRUN") == null? "NA" : rs.getString("TRIALRUN")).intValue();
					MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT") == null? "NA" : rs.getString("MACHINEFAULT")).longValue()/60;
					MACHINESD = new BigDecimal(rs.getString("MACHINESD") == null? "NA" : rs.getString("MACHINESD")).longValue()/60;
					INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT") == null? "NA" : rs.getString("INTERNALFAULT")).longValue()/60;
					INTERNALSD = new BigDecimal(rs.getString("INTERNALSD") == null? "NA" : rs.getString("INTERNALSD")).longValue()/60;
					EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT") == null? "NA" : rs.getString("EXTERNALFAULT")).longValue()/60;
					EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD") == null? "NA" : rs.getString("EXTERNALSD")).longValue()/60;
					WECLOADRST = new BigDecimal(rs.getString("WECLOADRST") == null? "NA" : rs.getString("WECLOADRST")).longValue()/60;
					EBLOADRST = new BigDecimal(rs.getString("EBLOADRST") == null? "NA" : rs.getString("EBLOADRST")).longValue()/60;
					CUSTOMERSCOPE = new BigDecimal(rs.getString("CUSTOMERSCOPE") == null? "NA" : rs.getString("CUSTOMERSCOPE")).longValue()/60;
				}
			}
			
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add(S_WECSHORT_DESCR);
			wecTotalInfo.add(S_EB_ID);
			wecTotalInfo.add(WECTYPE);
			wecTotalInfo.add(CAPACITY);
			wecTotalInfo.add("From " + fromReadingDate + " to " + toReadingDate);
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add(TOTAL_GENERATION);
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add(TOTAL_OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(D_COMMISION_DATE);
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(CUSTOMERSCOPE);
			
			return wecTotalInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
			
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				 
				 
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	public static List<String> getWECTypeCapacityBasedOnSiteId(String siteId) {
		List<String> wecTypeCapacity = new ArrayList<String>();
		Connection connection = null;
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			prepStmt = connection.prepareStatement(getWECTypeCapacityBasedOnSiteIdQuery);
			prepStmt.setObject(1, siteId);
			
			rs = prepStmt.executeQuery();
			while(rs.next()){
				wecTypeCapacity.add(rs.getString("WECCAPACITY"));
			}
			return wecTypeCapacity;
		}
		catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTypeCapacity;
	}
	
	/**
	 * 27 : WEC Count
	 * @param wecIds
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getManyWECTotalForOneDayMeta(ArrayList<String> wecIds, String readingDate){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForBetweenDaysQuery = 
					"Select count(*) as total_wec, " + 
					"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
					"sum(Lullhrs) as Lullhrs,  sum(TRIALRUN) as TRIALRUN, " + 
					"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
					"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
					"From Meta_Data " + 
					"Where S_Wec_Id In " + wecIdsInString + 
					"and D_reading_date = ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
			prepStmt.setString(1, readingDate);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(checkManyWECTranferredStatus(wecIds, conn)){
						wecInfo.add("9");
						wecInfo.add("Machine Transferred");
					}
					else{
						wecInfo.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{0,1,2,3,4,5,7,9,10,11,27});
					S_WEC_ID = wecIdsInString;
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	/*------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------------------------*/
	/*------------------------------------------------------------------------------------------------------*/
	
	public static List<Object> getOneWECInfoForOneDay(String wecId, String readingDate) {
		ArrayList<Object> wecInfo = new ArrayList<Object>();
	
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECInfoForOneDayQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
			}
			
			wecInfo.add(S_WEC_ID);
			wecInfo.add(S_WECSHORT_DESCR);
			wecInfo.add(S_EB_ID);
			wecInfo.add(WECTYPE);
			wecInfo.add(CAPACITY);
			wecInfo.add(D_READING_DATE);
			wecInfo.add(GENERATION);
			wecInfo.add(TOTAL_GENERATION);
			wecInfo.add(OPERATINGHRS);
			wecInfo.add(TOTAL_OPERATINGHRS);
			wecInfo.add(S_REMARKS == null ? "" : S_REMARKS);
			wecInfo.add(D_COMMISION_DATE);
			wecInfo.add(LULLHRS);
			wecInfo.add(df2.format(MAVIAL));
			wecInfo.add(df2.format(GAVIAL));
			wecInfo.add(df2.format(CFACTOR));
			wecInfo.add(df2.format(GIAVIAL));
			wecInfo.add(df2.format(MIAVIAL));
			wecInfo.add(TRIALRUN);
			wecInfo.add(MACHINEFAULT);
			wecInfo.add(MACHINESD);
			wecInfo.add(EBLOADRST);
			wecInfo.add(INTERNALFAULT);
			wecInfo.add(INTERNALSD);
			wecInfo.add(EXTERNALFAULT);
			wecInfo.add(EXTERNALSD);
			wecInfo.add(WECLOADRST);
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	
	}

	public static ArrayList<ArrayList<Object>> getOneWECInfoForOneMonth(String wecId, int month, int year) {
		ArrayList<Object> wecOneDayInfo = new ArrayList<Object>();
	
		ArrayList<ArrayList<Object>> wecDateWiseInfo = new ArrayList<ArrayList<Object>>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECInfoForOneMonthQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setInt(2, year);
			prepStmt.setInt(3, month);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				
				wecOneDayInfo.add(S_WEC_ID);
				wecOneDayInfo.add(S_WECSHORT_DESCR);
				wecOneDayInfo.add(S_EB_ID);
				wecOneDayInfo.add(WECTYPE);
				wecOneDayInfo.add(CAPACITY);
				wecOneDayInfo.add(D_READING_DATE);
				wecOneDayInfo.add(GENERATION);
				wecOneDayInfo.add(TOTAL_GENERATION);
				wecOneDayInfo.add(OPERATINGHRS);
				wecOneDayInfo.add(TOTAL_OPERATINGHRS);
				wecOneDayInfo.add(S_REMARKS == null ? "":S_REMARKS);
				wecOneDayInfo.add(D_COMMISION_DATE);
				wecOneDayInfo.add(LULLHRS);
				wecOneDayInfo.add(df2.format(MAVIAL));
				wecOneDayInfo.add(df2.format(GAVIAL));
				wecOneDayInfo.add(df2.format(CFACTOR));
				wecOneDayInfo.add(df2.format(GIAVIAL));
				wecOneDayInfo.add(df2.format(MIAVIAL));
				wecOneDayInfo.add(TRIALRUN);
				wecOneDayInfo.add(MACHINEFAULT);
				wecOneDayInfo.add(MACHINESD);
				wecOneDayInfo.add(EBLOADRST);
				wecOneDayInfo.add(INTERNALFAULT);
				wecOneDayInfo.add(INTERNALSD);
				wecOneDayInfo.add(EXTERNALFAULT);
				wecOneDayInfo.add(EXTERNALSD);
				wecOneDayInfo.add(WECLOADRST);
				wecOneDayInfo.add("Month:" + DateUtility.getMonthForInt(month));
				wecOneDayInfo.add("Year:" + year);
				
				wecDateWiseInfo.add(wecOneDayInfo);
				wecOneDayInfo = new ArrayList<Object>();
			}
			
			
			
			return wecDateWiseInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecDateWiseInfo;
	
	}

	public static ArrayList<ArrayList<Object>> getOneWECInfoForBetweenDays(String wecId, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecOneDayInfo = new ArrayList<Object>();
	
		ArrayList<ArrayList<Object>> wecDateWiseInfo = new ArrayList<ArrayList<Object>>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECInfoForBetweenDaysQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromReadingDate);
			prepStmt.setString(3, toReadingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				
				wecOneDayInfo.add(S_WEC_ID);
				wecOneDayInfo.add(S_WECSHORT_DESCR);
				wecOneDayInfo.add(S_EB_ID);
				wecOneDayInfo.add(WECTYPE);
				wecOneDayInfo.add(CAPACITY);
				wecOneDayInfo.add(D_READING_DATE);
				wecOneDayInfo.add(GENERATION);
				wecOneDayInfo.add(TOTAL_GENERATION);
				wecOneDayInfo.add(OPERATINGHRS);
				wecOneDayInfo.add(TOTAL_OPERATINGHRS);
				wecOneDayInfo.add(S_REMARKS == null ? "":S_REMARKS);
				wecOneDayInfo.add(D_COMMISION_DATE);
				wecOneDayInfo.add(LULLHRS);
				wecOneDayInfo.add(df2.format(MAVIAL));
				wecOneDayInfo.add(df2.format(GAVIAL));
				wecOneDayInfo.add(df2.format(CFACTOR));
				wecOneDayInfo.add(df2.format(GIAVIAL));
				wecOneDayInfo.add(df2.format(MIAVIAL));
				wecOneDayInfo.add(TRIALRUN);
				wecOneDayInfo.add(MACHINEFAULT);
				wecOneDayInfo.add(MACHINESD);
				wecOneDayInfo.add(EBLOADRST);
				wecOneDayInfo.add(INTERNALFAULT);
				wecOneDayInfo.add(INTERNALSD);
				wecOneDayInfo.add(EXTERNALFAULT);
				wecOneDayInfo.add(EXTERNALSD);
				wecOneDayInfo.add(WECLOADRST);
				
				wecDateWiseInfo.add(wecOneDayInfo);
				wecOneDayInfo = new ArrayList<Object>();
			}
			return wecDateWiseInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecDateWiseInfo;
	
	}

	public static List<Object> getOneWECTotalForOneMonth(String wecId, int month, int year){
	
		initialiseToZero();
		
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECTotalForOneMonthQuery;
			/*"Select Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr,Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, "
			+ "Max(Capacity) As Capacity,Max(D_Commision_Date) As D_Commision_Date, "
			+ "Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, "
			+ "max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS, "
			+ "sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, "
			+ "sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, "
			+ "sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, "
			+ "sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST "
			+ "From Meta_Data "
			+ "Where S_Wec_Id = ? "
			+ "And Extract(Year From D_Reading_Date) = ? "
			+ "and extract(month from D_reading_date) = ? "
			+ "order by S_WECSHORT_DESCR ";*/
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			prepStmt.setObject(2, year);
			prepStmt.setObject(3, month);
	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				//System.out.println(rs.getString("S_WECSHORT_DESCR"));
				recordCount = rs.getInt("TOTAL_WEC");
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
			}
			
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add(S_WECSHORT_DESCR);
			wecTotalInfo.add(S_EB_ID);
			wecTotalInfo.add(WECTYPE);
			wecTotalInfo.add(CAPACITY);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add(TOTAL_GENERATION);
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add(TOTAL_OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(D_COMMISION_DATE);
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(DateUtility.getMonthForInt(month));
			wecTotalInfo.add(year);
			
			return wecTotalInfo;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
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
			catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	
	}
	
	public static List<Object> getoneWECTotalForOneMonthButLessThanCurrentReadingDate(String wecId, String readingDate, int month, int year){
		
		initialiseToZero();
		
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECTotalForOneMonthButLessThanCurrentReadingDateQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			prepStmt.setObject(2, year);
			prepStmt.setObject(3, month);
			prepStmt.setObject(4, readingDate);
	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("TOTAL_WEC");
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
			}
			
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add(S_WECSHORT_DESCR);
			wecTotalInfo.add(S_EB_ID);
			wecTotalInfo.add(WECTYPE);
			wecTotalInfo.add(CAPACITY);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add(TOTAL_GENERATION);
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add(TOTAL_OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(D_COMMISION_DATE);
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(DateUtility.getMonthForInt(month));
			wecTotalInfo.add(year);
			
			return wecTotalInfo;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("Exception In getOneWECTotalForOneMonth");
			e.printStackTrace();
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
			catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	
	}

	public static List<Object> getOneWECTotalForOneFiscalYear(String wecId, int fiscalYear){
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();

		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		initialiseToZero();
		S_WEC_ID = wecId;
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneWECTotalForOneFiscalYearQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setObject(2, fiscalYear + 1);
			prepStmt.setObject(3, fiscalYear);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = rs.getInt("TOTAL_WEC");
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValueExact();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//S_REMARKS	=	 rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
			}
			
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add(S_WECSHORT_DESCR);
			wecTotalInfo.add(S_EB_ID);
			wecTotalInfo.add(WECTYPE);
			wecTotalInfo.add(CAPACITY);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add(TOTAL_GENERATION);
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add(TOTAL_OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(D_COMMISION_DATE);
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add((fiscalYear - 1) + "-" + fiscalYear);
			
			return wecTotalInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
			System.out.println("------------");
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	public static String getWECCapacityFromType(String wecType){
		
		
		Connection conn = null;
		PreparedStatement prepare = null;
		ResultSet resultSet = null;
		String wecDescription = "";
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepare = conn.prepareStatement(getWECTypeCapacityBasedOnSiteIdQuery);
			prepare.setString(1, wecType);
			resultSet = prepare.executeQuery();
			while(resultSet.next()){
				wecDescription = resultSet.getString("S_WECSHORT_DESCR");
			}
			return wecDescription;
		}
		catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepare != null) {
					prepare.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} 
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecDescription;
	}
	
	private static String getWECRemarkPresentInWECMaster(String wecId, Connection conn) {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String remark = "";
		try {
			prepStmt = conn.prepareStatement(getOneWECDataFromWECMasterBasedOnWECIdQuery);
			prepStmt.setString(1, wecId);
			
			rs = prepStmt.executeQuery();
			if(rs.next()){
				remark = rs.getString("S_Remarks") == null ? "Machine Tranferred" : rs.getString("S_Remarks");
			}
			else{
				remark = "WEC Not Present";
			}
			return remark;
			
		} catch (SQLException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
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
		return remark;
	}

	private static boolean isWECTranferred(String wecId, Connection conn) {
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = conn.prepareStatement(getWECTranferStatus);
			prepStmt.setString(1, wecId);
			int status = -1;
			rs = prepStmt.executeQuery();
			while(rs.next()){
				status = Integer.parseInt(rs.getString("S_STATUS"));
			}
			if(status == 9){
				return true;
			}
			else{
				return false;
			}
			
		} catch (SQLException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
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
		return false;
	}

	public static ArrayList<ArrayList<Object>> getManyWECDetailsHavingMachineAvailabilityLessThan95WECWiseForOneDay(
			Collection<String> wecIdsBasedOnAreaIdCustomerId, String readingDate) {

		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
		
		ArrayList<ArrayList<Object>> w = new ArrayList<ArrayList<Object>>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIdsBasedOnAreaIdCustomerId);
		
		int recordCount = 0;
		
		
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
							"Select S_wec_id,max(S_Wecshort_Descr) as S_Wecshort_Descr,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
							"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " + 
							"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"Sum(Ebloadrst) As Ebloadrst, Sum(Internalfault) As Internalfault, Sum(Internalsd) As Internalsd, Sum(Externalfault) As Externalfault, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,avg(Generation) As AverageGeneration, Max(S_remarks) as S_remarks " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString +  
							"And D_Reading_Date = ? " + 
							"Group By S_Wec_Id " + 
							"order by mavial " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, readingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				if(MAVIAL > 95){
					break;
				}
				//recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();;
				S_WEC_ID = rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				//S_EB_ID	=	 rs.getString("S_EB_ID");
				//WECTYPE	=	 rs.getString("WECTYPE");
				//CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				//TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				//TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				String remarks = rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				
				wecTotalInfo.add(S_WEC_ID);
				wecTotalInfo.add(S_WECSHORT_DESCR);
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add(readingDate);
				wecTotalInfo.add(GENERATION);
				wecTotalInfo.add("NA");
				wecTotalInfo.add(OPERATINGHRS);
				wecTotalInfo.add("NA");
				wecTotalInfo.add(remarks);
				wecTotalInfo.add("NA");
				wecTotalInfo.add(LULLHRS);
				wecTotalInfo.add(df2.format(MAVIAL));
				wecTotalInfo.add(df2.format(GAVIAL));
				wecTotalInfo.add(df2.format(CFACTOR));
				wecTotalInfo.add(df2.format(GIAVIAL));
				wecTotalInfo.add(df2.format(MIAVIAL));
				wecTotalInfo.add(TRIALRUN);
				wecTotalInfo.add(MACHINEFAULT);
				wecTotalInfo.add(MACHINESD);
				wecTotalInfo.add(EBLOADRST);
				wecTotalInfo.add(INTERNALFAULT);
				wecTotalInfo.add(INTERNALSD);
				wecTotalInfo.add(EXTERNALFAULT);
				wecTotalInfo.add(EXTERNALSD);
				wecTotalInfo.add(WECLOADRST);
				
				w.add(wecTotalInfo);
				wecTotalInfo = new ArrayList<Object>();
			}
			return w;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
					 
					 
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return w;
	}
	
	public static List<Object> getManyWECsTotalForOneDay(Collection<String> wecIds, String readingDate) {
		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
	
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		
		boolean firstWECId = true;
		StringBuffer wecIdsInString = new StringBuffer();
		
		int recordCount = 0;
		
		wecIdsInString.append("(");
		for (String eachWECId : wecIds) {
			if(firstWECId){
				wecIdsInString.append("'" + eachWECId + "'");
				firstWECId = false;
			}
			else{
				wecIdsInString.append(",'" + eachWECId + "'");
			}
		}
		wecIdsInString.append(") ");
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
					"From Meta_Data " +
					"Where S_Wec_Id In " + wecIdsInString + 
					"And D_Reading_Date = ?  " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, readingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();;
				if(recordCount == 0){
					wecTotalInfo.add("Data Not Available");
					return wecTotalInfo;
				}
				//S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				//S_EB_ID	=	 rs.getString("S_EB_ID");
				//WECTYPE	=	 rs.getString("WECTYPE");
				//CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				//TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				//TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//remarks.append(rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS"));
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
			}
			
			S_WEC_ID = new String(wecIdsInString);
			wecTotalInfo.add(S_WEC_ID);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add(readingDate);
			wecTotalInfo.add(GENERATION);
			wecTotalInfo.add("NA");
			wecTotalInfo.add(OPERATINGHRS);
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add("NA");
			wecTotalInfo.add(LULLHRS);
			wecTotalInfo.add(df2.format(MAVIAL/recordCount));
			wecTotalInfo.add(df2.format(GAVIAL/recordCount));
			wecTotalInfo.add(df2.format(CFACTOR/recordCount));
			wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
			wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
			wecTotalInfo.add(TRIALRUN);
			wecTotalInfo.add(MACHINEFAULT);
			wecTotalInfo.add(MACHINESD);
			wecTotalInfo.add(EBLOADRST);
			wecTotalInfo.add(INTERNALFAULT);
			wecTotalInfo.add(INTERNALSD);
			wecTotalInfo.add(EXTERNALFAULT);
			wecTotalInfo.add(EXTERNALSD);
			wecTotalInfo.add(WECLOADRST);
			wecTotalInfo.add(recordCount);
			return wecTotalInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn); 
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecTotalInfo;
	}
	
	

	public static ArrayList<ArrayList<Object>> getOneWECTotalMonthWiseForOneFiscalYear(String wecId,int fiscalYear){
		
		ArrayList<ArrayList<Object>> monthWiseTotalByFiscalYear = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> monthWiseTotal = new ArrayList<Object>();
		String dateFormat = "ddMMyyyy";
		String todaysDate = DateUtility.getTodaysDateInGivenFormat(dateFormat);
		int currentMonth = DateUtility.gettingMonthNumberFromStringDate(todaysDate, dateFormat);
		int currentYear = DateUtility.gettingYearFromStringDate(todaysDate, dateFormat); 
		
		if(currentYear == fiscalYear){
			for(int month = 4; month <= currentMonth; month++){
				monthWiseTotal = (ArrayList<Object>) getOneWECTotalForOneMonth(wecId, month, fiscalYear);
				monthWiseTotalByFiscalYear.add(monthWiseTotal);
				monthWiseTotal = new ArrayList<Object>();
			}
		}
		else{
			for(int month = 4; month < 13; month++){
				monthWiseTotal = (ArrayList<Object>) getOneWECTotalForOneMonth(wecId, month, fiscalYear);
				monthWiseTotalByFiscalYear.add(monthWiseTotal);
				monthWiseTotal = new ArrayList<Object>();
			}
			if(currentMonth < 4 && (currentYear == (fiscalYear + 1))){
				for(int month = 1; month <= currentMonth; month++){
					monthWiseTotal = (ArrayList<Object>) getOneWECTotalForOneMonth(wecId, month, fiscalYear + 1);
					monthWiseTotalByFiscalYear.add(monthWiseTotal);
					monthWiseTotal = new ArrayList<Object>();
				}
			}
			else{
				for(int month = 1; month <= 3; month++){
					monthWiseTotal = (ArrayList<Object>) getOneWECTotalForOneMonth(wecId, month, fiscalYear + 1);
					monthWiseTotalByFiscalYear.add(monthWiseTotal);
					monthWiseTotal = new ArrayList<Object>();
				}
			}
		}
		return monthWiseTotalByFiscalYear;
	}
	
	private static void initialiseToZero() {
		S_WEC_ID = ""  ;
		S_WECSHORT_DESCR =  "" ;
		S_EB_ID = "" ;
		WECTYPE =  "" ;
		CAPACITY =  0 ;
		D_READING_DATE =  null ;
		GENERATION = 0  ;
		TOTAL_GENERATION =  0 ;
		OPERATINGHRS =  0 ;
		TOTAL_OPERATINGHRS = 0  ;
		S_REMARKS =  "";
		D_COMMISION_DATE =  null ;
		LULLHRS =  0 ;
		MAVIAL =  0.0 ;
		GAVIAL =  0.0 ;
		CFACTOR =  0.0 ;
		GIAVIAL =  0.0 ;
		MIAVIAL =  0.0 ;
		TRIALRUN =  0 ;
		MACHINEFAULT = 0  ;
		MACHINESD =  0 ;
		EBLOADRST =  0 ;
		INTERNALFAULT = 0  ;
		INTERNALSD =  0 ;
		EXTERNALFAULT = 0  ;
		EXTERNALSD =  0 ;
		WECLOADRST = 0 ;
	}

	

	public static ArrayList<ArrayList<Object>> getManyWECsDetailsHavingMachineAvailabilityLessThan98WECWiseForBetweenDays(
			Collection<String> wecIdsBasedOnAreaIdCustomerId, String fromDate, String toDate) {

		ArrayList<Object> wecTotalInfo = new ArrayList<Object>();
		
		ArrayList<ArrayList<Object>> w = new ArrayList<ArrayList<Object>>();
		
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		initialiseToZero();
		
		boolean firstWECId = true;
		StringBuffer wecIdsInString = new StringBuffer();
		
		int recordCount = 0;
		
		wecIdsInString.append("(");
		for (String eachWECId : wecIdsBasedOnAreaIdCustomerId) {
			if(firstWECId){
				wecIdsInString.append("'" + eachWECId + "'");
				firstWECId = false;
			}
			else{
				wecIdsInString.append(",'" + eachWECId + "'");
			}
		}
		wecIdsInString.append(") ");
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select S_wec_id,S_Wecshort_Descr, Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " + 
							"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(TRIALRUN) as TRIALRUN, " + 
							"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"Sum(Ebloadrst) As Ebloadrst, Sum(Internalfault) As Internalfault, Sum(Internalsd) As Internalsd, Sum(Externalfault) As Externalfault, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString +
							"And D_Reading_Date Between ? And ? " + 
							"Group By S_Wec_Id,S_Wecshort_Descr " + 
							"order by mavial, S_Wecshort_Descr " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, fromDate);
			prepStmt.setString(2, toDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("TOTAL_WEC")).intValueExact();
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue()/recordCount;
				if(MAVIAL > 98){
					break;
				}
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				//S_EB_ID	=	 rs.getString("S_EB_ID");
				//WECTYPE	=	 rs.getString("WECTYPE");
				//CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				//D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				//D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				//TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValueExact();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValueExact()/60;
				//TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValueExact()/60;
				//String remarks = rs.getString("S_REMARKS") == null ? "" : rs.getString("S_REMARKS");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValueExact()/60;
				
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValueExact()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValueExact()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValueExact()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValueExact()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValueExact()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValueExact()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValueExact()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValueExact()/60;
				
				S_WEC_ID = rs.getString("S_wec_id");
				wecTotalInfo.add(S_WEC_ID);
				wecTotalInfo.add(S_WECSHORT_DESCR);
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add(fromDate + " to " + toDate);
				wecTotalInfo.add(GENERATION);
				wecTotalInfo.add("NA");
				wecTotalInfo.add(OPERATINGHRS);
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add("NA");
				wecTotalInfo.add(LULLHRS);
				wecTotalInfo.add(df2.format(MAVIAL));
				wecTotalInfo.add(df2.format(GAVIAL/recordCount));
				wecTotalInfo.add(df2.format(CFACTOR/recordCount));
				wecTotalInfo.add(df2.format(GIAVIAL/recordCount));
				wecTotalInfo.add(df2.format(MIAVIAL/recordCount));
				wecTotalInfo.add(TRIALRUN);
				wecTotalInfo.add(MACHINEFAULT);
				wecTotalInfo.add(MACHINESD);
				wecTotalInfo.add(EBLOADRST);
				wecTotalInfo.add(INTERNALFAULT);
				wecTotalInfo.add(INTERNALSD);
				wecTotalInfo.add(EXTERNALFAULT);
				wecTotalInfo.add(EXTERNALSD);
				wecTotalInfo.add(WECLOADRST);
				
				w.add(wecTotalInfo);
				wecTotalInfo = new ArrayList<Object>();
			}
			return w;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return w;
	}
	
	private static int recordCount = 0;
	
	public static List<Object> getOneWECInfoForOneDayMeta(String wecId, String readingDate){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String oneWECInfoForOneDayQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECInfoForOneDayQuery = 
							"Select count(*) as total_wec,Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity,max(D_READING_DATE) as D_READING_DATE, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"max(S_REMARKS) as S_REMARKS,Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id = ? " + 
							"And D_Reading_Date = ?  " ; 
			prepStmt = conn.prepareStatement(oneWECInfoForOneDayQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(wecId, conn)){
						wecInfo.add("9");
					}
					else{
						wecInfo.add("1");
					}
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{-1});
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	public static List<Object> getOneWECTotalForOneMonthMeta(String wecId, int month, int year){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String oneWECInfoForOneMonthQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECInfoForOneMonthQuery = 
							"Select count(*) as total_wec,Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id = ? " + 
							"And Extract(Month From D_Reading_Date) = ? " +
							"and extract(Year from D_reading_date) = ? " ;
			prepStmt = conn.prepareStatement(oneWECInfoForOneMonthQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setInt(2, month);
			prepStmt.setInt(3, year);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(wecId, conn)){
						wecInfo.add("9");
					}
					else{
						wecInfo.add("1");
					}
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{5,10});
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	public static List<Object> getOneWECTotalForOneYearMeta(String wecId, int year){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String oneWECTotalForOneYearQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForOneYearQuery = 
							"Select count(*) as total_wec,Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"MAX(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id = ? " + 
							"and extract(Year from D_reading_date) = ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForOneYearQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setInt(2, year);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(wecId, conn)){
						wecInfo.add("9");
					}
					else{
						wecInfo.add("1");
					}
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{5,10});
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	/**
	 * 27 : WEC Count
	 * @param wecId
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static List<Object> getOneWECTotalForBetweenDaysMeta(String wecId, String fromReadingDate, String toReadingDate){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		
		
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForBetweenDaysQuery = 
							"Select count(*) as total_wec,Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"MAX(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id = ? " + 
							"and D_reading_date between ? and ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setString(2, fromReadingDate);
			prepStmt.setString(3, toReadingDate);
			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(wecId, conn)){
						wecInfo.add("9");
						wecInfo.add("Machine Transferred");
					}
					else{
						wecInfo.add("1");
					}
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{5,10});
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	
	
	/**
	 * 27:WEC Total
	 * @param wecIds
	 * @param month
	 * @param year
	 * @return
	 */
	public static List<Object> getManyWECTotalForOneMonthMeta(ArrayList<String> wecIds, int month, int year){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForBetweenDaysQuery = 
							"Select count(*) as total_wec, " + 
							"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"sum(Lullhrs) as Lullhrs,  Max(TrialRun) as trialrun, " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString + 
							"and Extract(month from D_reading_date) = ? " +
							"and Extract(Year from D_reading_date) = ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
			prepStmt.setInt(1, month);
			prepStmt.setInt(2, year);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(checkManyWECTranferredStatus(wecIds, conn)){
						wecInfo.add("9");
						wecInfo.add("Machines Transferred");
					}
					else{
						wecInfo.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{0,1,2,3,4,5,7,9,10,11});
					S_WEC_ID = wecIdsInString;
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	public static List<Object> getManyWECTotalForOneYearMeta(ArrayList<String> wecIds, int year){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		Connection conn = null;
		String oneWECTotalForBetweenDaysQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECTotalForBetweenDaysQuery = 
							"Select count(*) as total_wec, " + 
							"Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs,  " + 
							"sum(Lullhrs) as Lullhrs,  " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial,  " + 
							"sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString + 
							"and Extract(Year from D_reading_date) = ? " ;
			prepStmt = conn.prepareStatement(oneWECTotalForBetweenDaysQuery);
			prepStmt.setInt(1, year);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(checkManyWECTranferredStatus(wecIds, conn)){
						wecInfo.add("9");
					}
					else{
						wecInfo.add("1");
					}
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
				else{
					initialiseSpecial26(rs, new Integer[]{0,1,2,3,4,5,7,9,10,11,18});
					S_WEC_ID = wecIdsInString;
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					GlobalUtils.displayVectorMember(wecInfo);
					return wecInfo;
				}
			}
			
			return wecInfo;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wecInfo;
	}
	
	
	/**
	 * 
	 * @param wecIds
	 * @param readingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getManyWECDayWiseInfoForOneDayMeta(ArrayList<String> wecIds, String readingDate){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> w = new ArrayList<ArrayList<Object>>();
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		
		Connection conn = null;
		String manyWECDayWiseInfoForOneDayQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			manyWECDayWiseInfoForOneDayQuery = 
							"Select S_Wec_Id,D_reading_date ,Count(*) As Total_Wec,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, max(S_remarks) as S_remarks, " + 
							"Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs, " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
							"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id In " + wecIdsInString +
							"and D_Reading_Date = ? " + 
							"Group By S_Wec_Id,D_Reading_Date " + 
							"Order By S_WECSHORT_DESCR " ; 

			prepStmt = conn.prepareStatement(manyWECDayWiseInfoForOneDayQuery);
			prepStmt.setString(1, readingDate);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(S_WEC_ID, conn)){
						wecInfo.add("9");
						wecInfo.add("Machine Transferred");
					}
					else{
						wecInfo.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					w.add(wecInfo);
				}
				else{
					initialiseSpecial26(rs);
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					w.add(wecInfo);
				}
				wecInfo = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(w);
			//System.out.println("s:--------" + w.size());
			return w;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);  
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return w;
	}
	
	public static ArrayList<ArrayList<Object>> getOneWECInfoDayWiseForOneMonthMeta(String wecId, int month, int year){
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> w = new ArrayList<ArrayList<Object>>();
		
		
		
		Connection conn = null;
		String oneWECInfoForOneMonthQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			oneWECInfoForOneMonthQuery = 
							"Select D_reading_date ,Count(*) As Total_Wec,Max(S_Wec_Id) As S_Wec_Id,Max(S_Wecshort_Descr) As S_Wecshort_Descr, " + 
							"Max(S_Eb_Id) As S_Eb_Id,Max(Wectype) As Wectype, Max(Capacity) As Capacity, " + 
							"Sum(Generation) As Generation,Max(Total_Generation) As Total_Generation,Sum(Operatinghrs) As Operatinghrs, max(S_remarks) as S_remarks, " + 
							"Max(D_Commision_Date) As D_Commision_Date, " + 
							"max(TOTAL_OPERATINGHRS) as TOTAL_OPERATINGHRS,sum(Lullhrs) as Lullhrs, " + 
							"sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, sum(Giavial) as Giavial, sum(Miavial) as Miavial, " + 
							"sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " + 
							"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " + 
							"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " + 
							"From Meta_Data " + 
							"Where S_Wec_Id = ? " + 
							"And Extract(Month From D_Reading_Date) = ? " + 
							"And Extract(Year From D_Reading_Date) = ? " + 
							"Group By D_Reading_Date " + 
							"order by D_Reading_Date " ; 

			prepStmt = conn.prepareStatement(oneWECInfoForOneMonthQuery);
			prepStmt.setString(1, wecId);
			prepStmt.setInt(2, month);
			prepStmt.setInt(3, year);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = rs.getInt("Total_WEC");
				if(recordCount == 0){
					if(isWECTranferred(wecId, conn)){
						wecInfo.add("9");
					}
					else{
						wecInfo.add("1");
					}
					//GlobalUtils.displayVectorMember(wecInfo);
					w.add(wecInfo);
				}
				else{
					initialiseSpecial26(rs, new Integer[]{-1});
					addSpecial26ToList(wecInfo);
					wecInfo.add(recordCount);
					//GlobalUtils.displayVectorMember(wecInfo);
					w.add(wecInfo);
				}
				wecInfo = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(w);
			//System.out.println("s:--------" + w.size());
			return w;
		} catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return w;
	}
	
	
	
	private static boolean checkManyWECTranferredStatus(ArrayList<String> wecIds, Connection conn) {
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		ArrayList<Integer> wecStatus = new ArrayList<Integer>();
		String checkManyWETranferredStatusQuery = 
				"Select S_STATUS From Tbl_Wec_master " + 
				"where S_wec_id in " + wecIdsInString +
				"group by S_status";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = conn.prepareStatement(checkManyWETranferredStatusQuery);
			rs = prepStmt.executeQuery();
			while(rs.next()){
				wecStatus.add(rs.getInt("S_STATUS"));
			}
			
			for (Integer integer : wecStatus) {
				if(integer == 1){
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
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
		return false;
	}
	
	private static void initialiseSpecial26(ResultSet rs, Integer[] integers) {
		List<Integer> accountNumbers = Arrays.asList(integers);
	try{	
		if(accountNumbers.contains(0))S_WEC_ID = "NA";
		else S_WEC_ID = rs.getString("S_WEC_ID") == null? "NULL" : rs.getString("S_WEC_ID");

		if(accountNumbers.contains(1))S_WECSHORT_DESCR	="NA";
		else S_WECSHORT_DESCR =	rs.getString("S_WECSHORT_DESCR") == null ? "NULL" : rs.getString("S_WECSHORT_DESCR");

		if(accountNumbers.contains(2))S_EB_ID = "NA";
		else S_EB_ID =	rs.getString("S_EB_ID") == null ? "NULL" : rs.getString("S_EB_ID");

		if(accountNumbers.contains(3))WECTYPE	=	 "NA";
		else WECTYPE =	rs.getString("WECTYPE") == null ? "NULL" : rs.getString("WECTYPE");

		if(accountNumbers.contains(4))CAPACITY = -1;
		else CAPACITY =	rs.getString("CAPACITY") == null ? -2 : rs.getInt("CAPACITY");

		if(accountNumbers.contains(5))D_READING_DATE = null;
		else D_READING_DATE =	rs.getDate("D_READING_DATE") == null ? null : rs.getDate("D_READING_DATE");

		if(accountNumbers.contains(6))GENERATION = -1;
		else GENERATION = new BigDecimal(rs.getString("generation") == null? "-2" : rs.getString("generation")).longValue();

		if(accountNumbers.contains(7))TOTAL_GENERATION = -1;
		else TOTAL_GENERATION = new BigDecimal(rs.getString("total_generation") == null? "-2" : rs.getString("total_generation")).longValue();

		if(accountNumbers.contains(8))OPERATINGHRS = -1;
		else OPERATINGHRS = new BigDecimal(rs.getString("OPERATINGHRS") == null? "-120" : rs.getString("OPERATINGHRS")).longValue()/60;

		if(accountNumbers.contains(9))TOTAL_OPERATINGHRS = -1;
		else TOTAL_OPERATINGHRS = new BigDecimal(rs.getString("TOTAL_OPERATINGHRS") == null? "-120" : rs.getString("TOTAL_OPERATINGHRS")).longValue()/60;

		if(accountNumbers.contains(10))S_REMARKS = "NA";
		else S_REMARKS =	rs.getString("S_REMARKS") == null ? "NULL" : rs.getString("S_REMARKS");

		if(accountNumbers.contains(11))D_COMMISION_DATE = null;
		else D_COMMISION_DATE =	rs.getDate("D_COMMISION_DATE") == null ? null : rs.getDate("D_COMMISION_DATE");

		if(accountNumbers.contains(12))LULLHRS = -1;
		else LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "-120" : rs.getString("LULLHRS")).longValue()/60;

		if(accountNumbers.contains(13))MAVIAL = -1;
		else MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "-2" : rs.getString("MAVIAL")).doubleValue();

		if(accountNumbers.contains(14))GAVIAL = -1;
		else GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "-2" : rs.getString("GAVIAL")).doubleValue();

		if(accountNumbers.contains(15))CFACTOR = -1;
		else CFACTOR = new BigDecimal(rs.getString("CFACTOR") == null? "-2" : rs.getString("CFACTOR")).doubleValue();

		if(accountNumbers.contains(16))GIAVIAL = -1;
		else GIAVIAL = new BigDecimal(rs.getString("GIAVIAL") == null? "-2" : rs.getString("GIAVIAL")).doubleValue();

		if(accountNumbers.contains(17))MIAVIAL = -1;
		else MIAVIAL = new BigDecimal(rs.getString("MIAVIAL") == null? "-2" : rs.getString("MIAVIAL")).doubleValue();

		if(accountNumbers.contains(18))TRIALRUN = -1;
		else TRIALRUN = new BigDecimal(rs.getString("TRIALRUN") == null? "-2" : rs.getString("TRIALRUN")).intValue();

		if(accountNumbers.contains(19))MACHINEFAULT = -1;
		else MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT") == null? "120" : rs.getString("MACHINEFAULT")).longValue()/60;

		if(accountNumbers.contains(20))MACHINESD = -1;
		else MACHINESD = new BigDecimal(rs.getString("MACHINESD") == null? "120" : rs.getString("MACHINESD")).longValue()/60;

		if(accountNumbers.contains(21))EBLOADRST = -1;
		else EBLOADRST = new BigDecimal(rs.getString("EBLOADRST") == null? "120" : rs.getString("EBLOADRST")).longValue()/60;

		if(accountNumbers.contains(22))INTERNALFAULT = -1;
		else INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT") == null? "120" : rs.getString("INTERNALFAULT")).longValue()/60;

		if(accountNumbers.contains(23))INTERNALSD = -1;
		else INTERNALSD = new BigDecimal(rs.getString("INTERNALSD") == null? "120" : rs.getString("INTERNALSD")).longValue()/60;

		if(accountNumbers.contains(24))EXTERNALFAULT = -1;
		else EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT") == null? "-120" : rs.getString("EXTERNALFAULT")).longValue()/60;

		if(accountNumbers.contains(25))EXTERNALSD = -1;
		else EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD") == null? "-120" : rs.getString("EXTERNALSD")).longValue()/60;

		if(accountNumbers.contains(26))WECLOADRST = -1;
		else WECLOADRST = new BigDecimal(rs.getString("WECLOADRST") == null? "-120" : rs.getString("WECLOADRST")).longValue()/60;
//		
		if(accountNumbers.contains(27))CUSTOMERSCOPE = -1;
		else CUSTOMERSCOPE = new BigDecimal(rs.getString("CUSTOMERSCOPE") == null? "-120" : rs.getString("CUSTOMERSCOPE")).longValue()/60;
	}
	catch(Exception e){
		MethodClass.displayMethodClassName();
		e.printStackTrace();
	}

	}
	
	private static void initialiseSpecial26(ResultSet rs) {
		
	try{	
		S_WEC_ID = rs.getString("S_WEC_ID") == null? "NULL" : rs.getString("S_WEC_ID");

		S_WECSHORT_DESCR =	rs.getString("S_WECSHORT_DESCR") == null ? "NULL" : rs.getString("S_WECSHORT_DESCR");

		S_EB_ID =	rs.getString("S_EB_ID") == null ? "NULL" : rs.getString("S_EB_ID");

		WECTYPE =	rs.getString("WECTYPE") == null ? "NULL" : rs.getString("WECTYPE");

		CAPACITY =	rs.getString("CAPACITY") == null ? -2 : rs.getInt("CAPACITY");

		D_READING_DATE =	rs.getDate("D_READING_DATE") == null ? null : rs.getDate("D_READING_DATE");

		GENERATION = new BigDecimal(rs.getString("generation") == null? "-2" : rs.getString("generation")).longValue();

		TOTAL_GENERATION = new BigDecimal(rs.getString("total_generation") == null? "-2" : rs.getString("total_generation")).longValue();

		OPERATINGHRS = new BigDecimal(rs.getString("OPERATINGHRS") == null? "-120" : rs.getString("OPERATINGHRS")).longValue()/60;

		TOTAL_OPERATINGHRS = new BigDecimal(rs.getString("TOTAL_OPERATINGHRS") == null? "-120" : rs.getString("TOTAL_OPERATINGHRS")).longValue()/60;

		S_REMARKS =	rs.getString("S_REMARKS") == null ? "NULL" : rs.getString("S_REMARKS");

		D_COMMISION_DATE =	rs.getDate("D_COMMISION_DATE") == null ? null : rs.getDate("D_COMMISION_DATE");

		LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "-120" : rs.getString("LULLHRS")).longValue()/60;

		MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "-2" : rs.getString("MAVIAL")).doubleValue();

		GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "-2" : rs.getString("GAVIAL")).doubleValue();

		CFACTOR = new BigDecimal(rs.getString("CFACTOR") == null? "-2" : rs.getString("CFACTOR")).doubleValue();

		GIAVIAL = new BigDecimal(rs.getString("GIAVIAL") == null? "-2" : rs.getString("GIAVIAL")).doubleValue();

		MIAVIAL = new BigDecimal(rs.getString("MIAVIAL") == null? "-2" : rs.getString("MIAVIAL")).doubleValue();

		TRIALRUN = new BigDecimal(rs.getString("TRIALRUN") == null? "-2" : rs.getString("TRIALRUN")).intValue();

		MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT") == null? "120" : rs.getString("MACHINEFAULT")).longValue()/60;

		MACHINESD = new BigDecimal(rs.getString("MACHINESD") == null? "120" : rs.getString("MACHINESD")).longValue()/60;

		EBLOADRST = new BigDecimal(rs.getString("EBLOADRST") == null? "120" : rs.getString("EBLOADRST")).longValue()/60;

		INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT") == null? "120" : rs.getString("INTERNALFAULT")).longValue()/60;

		INTERNALSD = new BigDecimal(rs.getString("INTERNALSD") == null? "120" : rs.getString("INTERNALSD")).longValue()/60;

		EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT") == null? "-120" : rs.getString("EXTERNALFAULT")).longValue()/60;

		EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD") == null? "-120" : rs.getString("EXTERNALSD")).longValue()/60;

		WECLOADRST = new BigDecimal(rs.getString("WECLOADRST") == null? "-120" : rs.getString("WECLOADRST")).longValue()/60;
		
		CUSTOMERSCOPE = new BigDecimal(rs.getString("CUSTOMERSCOPE") == null? "-120" : rs.getString("CUSTOMERSCOPE")).longValue()/60;
		
	}
	catch(Exception e){
		MethodClass.displayMethodClassName();
		e.printStackTrace();
	}

	}

	private static void addSpecial26ToList(List<Object> ebTotalInfo) {
		ebTotalInfo.add(S_WEC_ID);
		ebTotalInfo.add(S_WECSHORT_DESCR);
		ebTotalInfo.add(S_EB_ID);
		ebTotalInfo.add(WECTYPE);
		ebTotalInfo.add(CAPACITY);
		ebTotalInfo.add(D_READING_DATE);
		ebTotalInfo.add(GENERATION);
		ebTotalInfo.add(TOTAL_GENERATION);
		ebTotalInfo.add(OPERATINGHRS);
		ebTotalInfo.add(TOTAL_OPERATINGHRS);
		ebTotalInfo.add(S_REMARKS);
		ebTotalInfo.add(D_COMMISION_DATE);
		ebTotalInfo.add(LULLHRS);
		ebTotalInfo.add(df2.format(MAVIAL/recordCount));
		ebTotalInfo.add(df2.format(GAVIAL/recordCount));
		ebTotalInfo.add(df2.format(CFACTOR/recordCount));
		ebTotalInfo.add(df2.format(GIAVIAL/recordCount));
		ebTotalInfo.add(df2.format(MIAVIAL/recordCount));
		ebTotalInfo.add(TRIALRUN);
		ebTotalInfo.add(MACHINEFAULT);
		ebTotalInfo.add(MACHINESD);
		ebTotalInfo.add(EBLOADRST);
		ebTotalInfo.add(INTERNALFAULT);
		ebTotalInfo.add(INTERNALSD);
		ebTotalInfo.add(EXTERNALFAULT);
		ebTotalInfo.add(EXTERNALSD);
		ebTotalInfo.add(WECLOADRST);
		ebTotalInfo.add(CUSTOMERSCOPE);

	}

	
}
