package com.enercon.admin.metainfo;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.sqlQuery.AllQueries;


public class EBMetaInfo implements AllQueries ,WcareConnector{
	
	private final static Logger logger = Logger.getLogger(EBMetaInfo.class);
	
	private static boolean debug = false;
	private static boolean methodClassName = false;
	private static int queryCount = 0;
	
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
	
	private static Connection connection = null; 
	
	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connectio) {
		connection = connectio;
	}
	
	private static void initialiseToZero() {
		S_WEC_ID = "";
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
	
	private static DecimalFormat df2 = new DecimalFormat("###.##");
	
	/**
	 * All WECs info related to one EB only for one day
	 * @param ebId
	 * @param readingDate
	 * @return 
	 */
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseInfoForOneDay(String ebId,String readingDate) {
		/*if(methodClassName){
			MethodClass.displayMethodClassName();
		}*/
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> ebInfo = new ArrayList<ArrayList<Object>>();
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBWECWiseInfoForOneDayQuery;
			/*if(debug){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(),ebId,readingDate);
			}*/
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setString(2, readingDate);
			DaoUtility.displayQueryWithParameter(14, sqlQuery, ebId, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(14, rs);
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValue();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS") == null ? "": rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				
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
				wecInfo.add(S_REMARKS);
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
				
				ebInfo.add(wecInfo);
				wecInfo = new ArrayList<Object>();
				
				
			}
//			System.out.println("wecIII" + ebInfo);
			return ebInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebInfo;

	}
	
	/**
	 * 27 : WEC Count
	 * @param ebId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getOneEBTotalForOneDay(String ebId, String readingDate) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
		
		initialiseToZero();
		S_EB_ID = ebId;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBTotalForOneDayQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setString(2, readingDate);
			DaoUtility.displayQueryWithParameter(19, sqlQuery, ebId, readingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				DaoUtility.getRowCount(19, rs);
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
			}
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(readingDate);
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add(TOTAL_GENERATION);
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add(TOTAL_OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(recordCount);
			
			return ebTotalInfo;
		} catch (Exception e) {

			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	/**
	 * All WECs Total for a particular period related to one EB
	 * @param ebId
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseTotalForBetweenDays(String ebId, String fromReadingDate, String toReadingDate){
		
	    logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<ArrayList<Object>> wecTotalInfo = new ArrayList<ArrayList<Object>>();	
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnEbIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebId);
			DaoUtility.displayQueryWithParameter(40, sqlQuery, ebId,fromReadingDate,toReadingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(40, rs);
				List<Object> wecInfo = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromReadingDate, toReadingDate);
				wecTotalInfo.add((ArrayList<Object>) wecInfo);
				wecInfo = new ArrayList<Object>();
			}
			return wecTotalInfo;
		}
		catch(Exception e){
          logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;
	
	}
	
	/*-------------------------------------------------------------------------------------------------------------------------------*/
	/*-------------------------------------------------------------------------------------------------------------------------------*/ 
	/*-------------------------------------------------------------------------------------------------------------------------------*/

	/**
	 * 
	 * @param ebId
	 * @param month:1 to 12
	 * @param year:2005 onwards
	 * @return A List:Monthly Total of one EB
	 */
	public static List<Object> getOneEBTotalForOneMonth(String ebId, int month, int year) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBTotalForOneMonthQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setInt(2, month);
			prepStmt.setInt(3, year);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
			}
			S_EB_ID = ebId;
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(recordCount);
			ebTotalInfo.add(DateUtility.getMonthForInt(month) + "-" + year);
	
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	public static List<Object> getOneEBTotalForOneMonthButLessThanCurrentReadingDate(String ebId, String readingDate, int month, int year) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBTotalForOneMonthButLessThanCurrentReadingDateQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setInt(2, month);
			prepStmt.setInt(3, year);
			prepStmt.setString(4, readingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
			}
			S_EB_ID = ebId;
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(recordCount);
			ebTotalInfo.add(DateUtility.getMonthForInt(month) + "-" + year);
	
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	public static List<Object> getOneEBTotalForOneFiscalYear(String ebId,  int fiscalYear) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBTotalForOneFiscalYearQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setInt(2, fiscalYear + 1);
			prepStmt.setInt(3, fiscalYear);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
			}
			S_EB_ID = ebId;
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(recordCount);
			ebTotalInfo.add("Fiscal Year:" + fiscalYear + "-" + fiscalYear + 1);
	
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	/**
	 * One EB Total for a particular period
	 * @param ebId
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static List<Object> getOneEBTotalForBetweenDays(String ebId, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		
	//	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBTotalForBetweenDaysQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setString(2, fromReadingDate);
			prepStmt.setString(3, toReadingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				
			}
			S_EB_ID = ebId;
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("From " + fromReadingDate + " to " + toReadingDate);
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(LULLHRS);
			ebTotalInfo.add(df2.format(MAVIAL/recordCount));
			ebTotalInfo.add(df2.format(GAVIAL/recordCount));
			ebTotalInfo.add(df2.format(CFACTOR/recordCount));
			ebTotalInfo.add(df2.format(GIAVIAL/recordCount));
			ebTotalInfo.add(df2.format(MIAVIAL/recordCount));
			ebTotalInfo.add("NA");
			ebTotalInfo.add(MACHINEFAULT);
			ebTotalInfo.add(MACHINESD);
			ebTotalInfo.add(EBLOADRST);
			ebTotalInfo.add(INTERNALFAULT);
			ebTotalInfo.add(INTERNALSD);
			ebTotalInfo.add(EXTERNALFAULT);
			ebTotalInfo.add(EXTERNALSD);
			ebTotalInfo.add(WECLOADRST);
			ebTotalInfo.add(recordCount);
			
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	public static ArrayList<ArrayList<Object>> getOneEbWECDayWiseInfoForOneDayMeta(String ebId,String readingDate) {
		ArrayList<String> wecIds = new ArrayList<String>();
		ArrayList<ArrayList<Object>> ebInfo = new ArrayList<ArrayList<Object>>();
		
	//	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try {
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getWECIdsBasedOnEbIdQuery);
			prepStmt.setString(1, ebId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			
			ebInfo = WECMetaInfo.getManyWECDayWiseInfoForOneDayMeta(wecIds, readingDate);

			return ebInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebInfo;

	}

	
	
	/**
	 * All WECs info for each day for a particular EB in a particular month
	 * @param ebId
	 * @param month
	 * @param year
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseInfoForOneMonthQuery(String ebId, int month, int year) {
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> ebInfo = new ArrayList<ArrayList<Object>>();
	
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBWECWiseInfoForOneMonthQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			prepStmt.setInt(2, month);
			prepStmt.setInt(3, year);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				S_WEC_ID	=	 rs.getString("S_WEC_ID");
				S_WECSHORT_DESCR	=	 rs.getString("S_WECSHORT_DESCR");
				S_EB_ID	=	 rs.getString("S_EB_ID");
				WECTYPE	=	 rs.getString("WECTYPE");
				CAPACITY	=	 new BigDecimal(rs.getString("CAPACITY")).intValue();
				D_READING_DATE	=	 rs.getDate("D_READING_DATE");
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValue();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS") == null ? "": rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				
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
				wecInfo.add(S_REMARKS == null ? "":S_REMARKS);
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
				
				ebInfo.add(wecInfo);
				wecInfo = new ArrayList<Object>();
			}
	
			return ebInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebInfo;
	
	}
	
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseDayWiseInfoForOneMonthMeta(String ebId, int month, int year) {
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> ebInfo = new ArrayList<ArrayList<Object>>();
	
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> wecIdsBasedOnOneEbId = new ArrayList<String>();
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnEbIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIdsBasedOnOneEbId.add(rs.getString("S_WEC_ID"));
			}
	
			return ebInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebInfo;
	
	}

	/**
	 * All WECs info for each day related to one EB for a particular period
	 * @param ebId
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseInfoForBetweenDays(String ebId,String fromReadingDate, String toReadingDate) {
		ArrayList<Object> wecInfo = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> ebInfo = new ArrayList<ArrayList<Object>>();

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = oneEBWECWiseInfoForBetweenDaysQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, ebId);
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
				GENERATION	=	 new BigDecimal(rs.getString("GENERATION")).longValue();
				TOTAL_GENERATION	=	 new BigDecimal(rs.getString("TOTAL_GENERATION")).longValue();
				OPERATINGHRS	=	 new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60;
				TOTAL_OPERATINGHRS	=	 new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60;
				S_REMARKS	=	 rs.getString("S_REMARKS") == null ? "": rs.getString("S_REMARKS");
				D_COMMISION_DATE	=	 rs.getDate("D_COMMISION_DATE");
				LULLHRS	=	 new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL	=	 new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL	=	 new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR	=	 new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL	=	 new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL	=	 new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN	=	 new BigDecimal(rs.getString("TRIALRUN")).intValueExact();
				MACHINEFAULT	=	 new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD	=	 new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				EBLOADRST	=	 new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
				INTERNALFAULT	=	 new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD	=	 new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT	=	 new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD	=	 new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST	=	 new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				
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
				wecInfo.add(S_REMARKS == null ? "":S_REMARKS);
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
				
				ebInfo.add(wecInfo);
				wecInfo = new ArrayList<Object>();
			}

			return ebInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebInfo;

	}
	
	/**
	 * All WECs Total in a particular month related to one EB
	 * @param ebId
	 * @param month
	 * @param year
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseTotalForOneMonth(String ebId, int month, int year){

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<ArrayList<Object>> wecTotalInfo = new ArrayList<ArrayList<Object>>();	
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnEbIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				List<Object> wecInfo = WECMetaInfo.getOneWECTotalForOneMonth(rs.getString("S_WEC_ID"), month, year);
				GlobalUtils.displayVectorMember(wecInfo);
				wecTotalInfo.add((ArrayList<Object>) wecInfo);
				wecInfo = new ArrayList<Object>();
			}
			return wecTotalInfo;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return wecTotalInfo;

	}
	
	public static ArrayList<ArrayList<Object>> getOneEBWECWiseTotalForOneFiscalYear(String ebId, int fiscalYear){

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<ArrayList<Object>> wecTotalInfo = new ArrayList<ArrayList<Object>>();	
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnEbIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				List<Object> wecInfo = WECMetaInfo.getOneWECTotalForOneFiscalYear(rs.getString("S_WEC_ID"), fiscalYear);
				wecTotalInfo.add((ArrayList<Object>) wecInfo);
				wecInfo = new ArrayList<Object>();
			}
			return wecTotalInfo;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return wecTotalInfo;

	}
	
	

	/**
	 * 27:WEC Count
	 * Many EB Total for one day
	 * @param ebIds
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getManyEBTotalForOneday(ArrayList<String> ebIds, String readingDate) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		StringBuffer ebIdsInString = new StringBuffer();
		boolean firstEBId = true;
		
		ebIdsInString.append("(");
		for(String ebId : ebIds) {
			if(firstEBId){
				ebIdsInString.append("'" + ebId + "'");
				firstEBId = false;
			}else{
				ebIdsInString.append(",'" + ebId + "'");
			}
		}
		ebIdsInString.append(")");
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = connection;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 1;
		
		initialiseToZero();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
					"From Meta_Data " + 
					"Where S_Eb_Id in " + ebIdsInString + " " + 
					"And D_Reading_Date = ? " + 
					"order by S_WECSHORT_DESCR ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, readingDate);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				if(recordCount == 0){
					ebTotalInfo.add("WEC Count Zero");
					return ebTotalInfo;
				}
				else{
					GENERATION = new BigDecimal(rs.getString("generation") == null? "NA" : rs.getString("generation")).longValue();
					OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs") == null? "NA" : rs.getString("operatinghrs")).longValue()/60;
					LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "NA" : rs.getString("LULLHRS")).longValue()/60;
					MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "NA" : rs.getString("MAVIAL")).doubleValue();
					GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "NA" : rs.getString("GAVIAL")).doubleValue();
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
				}
			}
			S_EB_ID = new String(ebIdsInString);
			
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(readingDate);
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(recordCount);
			
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
	
	/**
	 * 27:WEC Count,28:Month-Year
	 * Monthly Total related to more than one EB
	 * @param ebIds
	 * @param month
	 * @param year
	 * @return 
	 */
	public static List<Object> getManyEBTotalForOneMonth(ArrayList<String> ebIds, int month, int year) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		StringBuffer ebIdsInString = new StringBuffer();
		boolean firstEbId = true;
		ebIdsInString.append("(");
		for (String ebId : ebIds) {
			if(firstEbId){
				ebIdsInString.append(ebId);
				firstEbId = false;
			}
			else{
				ebIdsInString.append("," + ebId);
			}
		}
		ebIdsInString.append(")");
		int recordCount = 0;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select Count(S_Wec_Id) As Total_Wec,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
						"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
						"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
						"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
						"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST,sum(TRIALRUN) as TRIALRUN " +
						"From Meta_Data " +
						"Where S_Eb_Id in " + ebIdsInString + " " +
						"And Extract (Month From D_Reading_Date) = ? " +
						"and Extract (Year From D_Reading_Date) = ? " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setInt(1, month);
			prepStmt.setInt(2, year);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				if(recordCount == 0){
					ebTotalInfo.add("WEC Count Zero");
					return ebTotalInfo;
				}
				GENERATION = new BigDecimal(rs.getString("generation") == null? "0" : rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs") == null? "0" : rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS") == null? "0" : rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL") == null? "0" : rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL") == null? "0" : rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR") == null? "0" : rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL") == null? "0" : rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL") == null? "0" : rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN") == null? "0" : rs.getString("TRIALRUN")).intValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT") == null? "0" : rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD") == null? "0" : rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT") == null? "0" : rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD") == null? "0" : rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT") == null? "0" : rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD") == null? "0" : rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST") == null? "0" : rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST") == null? "0" : rs.getString("EBLOADRST")).longValue()/60;
			}
			
			S_EB_ID = new String(ebIdsInString);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(LULLHRS);
			ebTotalInfo.add(df2.format(MAVIAL/(recordCount == 0 ? 1 : recordCount)));
			ebTotalInfo.add(df2.format(GAVIAL/(recordCount == 0 ? 1 : recordCount)));
			ebTotalInfo.add(df2.format(CFACTOR/(recordCount == 0 ? 1 : recordCount)));
			ebTotalInfo.add(df2.format(GIAVIAL/(recordCount == 0 ? 1 : recordCount)));
			ebTotalInfo.add(df2.format(MIAVIAL/(recordCount == 0 ? 1 : recordCount)));
			ebTotalInfo.add(TRIALRUN);
			ebTotalInfo.add(MACHINEFAULT);
			ebTotalInfo.add(MACHINESD);
			ebTotalInfo.add(EBLOADRST);
			ebTotalInfo.add(INTERNALFAULT);
			ebTotalInfo.add(INTERNALSD);
			ebTotalInfo.add(EXTERNALFAULT);
			ebTotalInfo.add(EXTERNALSD);
			ebTotalInfo.add(WECLOADRST);
			ebTotalInfo.add(recordCount);
			ebTotalInfo.add(DateUtility.getMonthForInt(month) + "-" + year);
	
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}

	public static List<Object> getManyEBTotalForOneFiscalYear(ArrayList<String> ebIds, int fiscalYear) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		StringBuffer ebIdsInString = new StringBuffer();
		boolean firstEBId = true;
		
		ebIdsInString.append("(");
		for(String ebId : ebIds) {
			if(firstEBId){
				ebIdsInString.append("'" + ebId + "'");
				firstEBId = false;
			}else{
				ebIdsInString.append(",'" + ebId + "'");
			}
		}
		ebIdsInString.append(")");
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 1;
		
		initialiseToZero();
		
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
					"From Meta_Data " + 
					"Where S_EB_Id in " + ebIdsInString + " " +
					"And ((Extract(Year From D_Reading_Date) = ? " +
					"And Extract(Month From D_Reading_Date) In (1,2,3)) " +
					"Or(Extract(Year From D_Reading_Date) = ? " +
					"And Extract(Month From D_Reading_Date) In (4,5,6,7,8,9,10,11,12))) " +
					"order by D_reading_date " ;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, fiscalYear + 1);
			prepStmt.setObject(2, fiscalYear);
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
			}
			S_EB_ID = new String(ebIdsInString);
			
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			ebTotalInfo.add(fiscalYear);
			
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;
	}

	/**
	 * Many EB Total For a particular period
	 * @param ebIds
	 * @param fromReadingDate
	 * @param toReadingDate
	 * @return
	 */
	public static List<Object> getManyEBTotalForBetweenDays(ArrayList<String> ebIds, String fromReadingDate, String toReadingDate) {
		ArrayList<Object> ebTotalInfo = new ArrayList<Object>();
		StringBuffer ebIdsInString = new StringBuffer();
		boolean firstEBId = true;
		
		ebIdsInString.append("(");
		for(String ebId : ebIds) {
			if(firstEBId){
				ebIdsInString.append("'" + ebId + "'");
				firstEBId = false;
			}else{
				ebIdsInString.append(",'" + ebId + "'");
			}
		}
		ebIdsInString.append(")");
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		int recordCount = 1;
		
		initialiseToZero();
		

		try {
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select count(S_wec_ID) As Total_WEC,Sum(Generation) As Generation,Sum(Operatinghrs) As Operatinghrs, " +
					"sum(Lullhrs) as Lullhrs, sum(Mavial) as Mavial,sum(Gavial) as Gavial, sum(CFActor) as CFActor, " +
					"sum(Giavial) as Giavial, sum(Miavial) as Miavial, sum(TRIALRUN) as TRIALRUN,sum(Machinefault) as Machinefault, sum(Machinesd) as Machinesd, " +
					"sum(EBLOADRST) as EBLOADRST, sum(INTERNALFAULT) as INTERNALFAULT, sum(INTERNALSD) as INTERNALSD, sum(EXTERNALFAULT) as EXTERNALFAULT, " +
					"sum(EXTERNALSD) as EXTERNALSD, sum(WECLOADRST) as WECLOADRST " +
					"From Meta_Data " + 
					"Where S_Eb_Id in " + ebIdsInString + " " + 
					"And D_Reading_Date between ? and ? " + 
					"order by S_WECSHORT_DESCR ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, fromReadingDate);
			prepStmt.setObject(2, toReadingDate);
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				recordCount = new BigDecimal(rs.getString("total_wec")).intValue();
				GENERATION = new BigDecimal(rs.getString("generation")).longValue();
				OPERATINGHRS = new BigDecimal(rs.getString("operatinghrs")).longValue()/60;
				LULLHRS = new BigDecimal(rs.getString("LULLHRS")).longValue()/60;
				MAVIAL = new BigDecimal(rs.getString("MAVIAL")).doubleValue();
				GAVIAL = new BigDecimal(rs.getString("GAVIAL")).doubleValue();
				CFACTOR = new BigDecimal(rs.getString("CFACTOR")).doubleValue();
				GIAVIAL = new BigDecimal(rs.getString("GIAVIAL")).doubleValue();
				MIAVIAL = new BigDecimal(rs.getString("MIAVIAL")).doubleValue();
				TRIALRUN = new BigDecimal(rs.getString("TRIALRUN")).intValue();
				MACHINEFAULT = new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60;
				MACHINESD = new BigDecimal(rs.getString("MACHINESD")).longValue()/60;
				INTERNALFAULT = new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60;
				INTERNALSD = new BigDecimal(rs.getString("INTERNALSD")).longValue()/60;
				EXTERNALFAULT = new BigDecimal(rs.getString("EXTERNALFAULT")).longValue()/60;
				EXTERNALSD = new BigDecimal(rs.getString("EXTERNALSD")).longValue()/60;
				WECLOADRST = new BigDecimal(rs.getString("WECLOADRST")).longValue()/60;
				EBLOADRST = new BigDecimal(rs.getString("EBLOADRST")).longValue()/60;
			}
			S_EB_ID = new String(ebIdsInString);
			
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add(S_EB_ID);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("From" + fromReadingDate + " to " + toReadingDate);
			ebTotalInfo.add(GENERATION);
			ebTotalInfo.add("NA");
			ebTotalInfo.add(OPERATINGHRS);
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
			ebTotalInfo.add("NA");
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
			
			return ebTotalInfo;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebTotalInfo;		
	}
}
