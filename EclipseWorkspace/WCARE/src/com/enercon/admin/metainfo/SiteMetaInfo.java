package com.enercon.admin.metainfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.sqlQuery.AllQueries;

public class SiteMetaInfo implements AllQueries ,WcareConnector{
	 private final static Logger logger = Logger.getLogger(SiteMetaInfo.class);
	private static Connection connection= null; 
	
	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connectio) {
		connection = connectio;
	
	}
	
	/**
	 * 27 : WEC Count, 28 : Customer Id, 29 : State Id, 30 : Site Name, 31 : Site Id
	 * @param customerId
	 * @param stateId
	 * @param readingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneDayBasedOnStateIdCustomerIdMeta(
			String customerId, String stateId, String readingDate) {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		String query = "";
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> wecIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> wecTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			query = getSiteIdSiteNameBasedOnStateIdCustomerIdQuery;
			ps1 = conn.prepareStatement(query);
			ps1.setObject(1, customerId);
			ps1.setObject(2, stateId);
			DaoUtility.displayQueryWithParameter(5, query, customerId, stateId);
			rs1 = ps1.executeQuery();
			
			while (rs1.next()) {
				DaoUtility.getRowCount(5, rs1);
				query = getWECIdsBasedOnSiteIdStateIdCustomerIdQuery;
				ps2 = conn.prepareStatement(query);
				ps2.setObject(1, rs1.getObject("S_SITE_ID"));
				ps2.setObject(2, stateId);
				ps2.setObject(3, customerId);
				DaoUtility.displayQueryWithParameter(6, query, rs1.getObject("S_SITE_ID"), stateId, customerId);
				rs2 = ps2.executeQuery();
				
				while(rs2.next()){
					DaoUtility.getRowCount(6, rs2);
					wecIdsSiteWiseStateWiseCustomerWise.add(rs2.getString("S_WEC_ID"));
				}
				
				wecTotalInfo = WECMetaInfo.getManyWECTotalForOneDayMeta(wecIdsSiteWiseStateWiseCustomerWise, readingDate);
				
				
				for (Object object : wecTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(rs1.getString("S_SITE_NAME"));
				siteInfo.add(rs1.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				wecIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				wecTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(ps1,ps2) , Arrays.asList(rs1,rs2) , conn);
		}
		return siteWiseInfo;
		
	}
	
	/**
	 * 27 : WEC Count
	 * @param siteId
	 * @param customerId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdMeta(String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
			DaoUtility.displayQueryWithParameter(68, sqlQuery, siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(68, rs);
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			return WECMetaInfo.getManyWECsWECWiseTotalForBetweenDaysMeta(wecIds, fromDate, toDate);
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			 DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;

	}
	
	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerIdMeta(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		//System.out.println("Site ID:" + siteId);
		//System.out.println("Customer ID:" + customerId);
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> wecIds = new ArrayList<String>();	
		List<Object> wecIdsTotal = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getWECIdsBasedOnSiteIdCustomerIdQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
			DaoUtility.displayQueryWithParameter(70, getWECIdsBasedOnSiteIdCustomerIdQuery, siteId,customerId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(70, rs);
				wecIds.add(rs.getString("S_WEC_ID"));	
				
			}
			wecIdsTotal = WECMetaInfo.getManyWECTotalForBetweenDaysMeta(wecIds, fromReadingDate, toReadingDate);
			return wecIdsTotal;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

			//System.out.println("SiteMetaInfo:getOneSiteTotalBasedOnSiteIdCustomerId Exception->" + e.getMessage());
		}
		finally{
			 DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return wecIdsTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(String wecType,String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
			DaoUtility.displayQueryWithParameter(31, sqlQuery, wecType, siteId, customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(31, rs);
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDaysMPR(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			
			return siteWiseWECTotal;
		}
		catch (Exception e) {
		 logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(
			String wecType, String siteId, String customerId,
			String fromDate, String toDate) {
		
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
			DaoUtility.displayQueryWithParameter(36, sqlQuery, wecType,siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(36, rs);
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDaysMPR(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			 DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return siteTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdChange(String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
			DaoUtility.displayQueryWithParameter(43, sqlQuery,siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(43, rs);
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			
			return siteWiseWECTotal;
		}
		catch (Exception e) {
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			 DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnSiteIdCustomerId(String siteId, String customerId,
			String fromDate, String toDate) {
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
			DaoUtility.displayQueryWithParameter(44, sqlQuery, siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(44, rs);
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDays(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			  DaoUtility.releaseResources(prepStmt, rs, conn);

		}
		return siteTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(String wecType,String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
			DaoUtility.displayQueryWithParameter(48, sqlQuery, wecType,siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(48, rs);
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			
			return siteWiseWECTotal;
		}
		catch (Exception e) {//MethodClass.displayMethodClassName();
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(
			String wecType, String siteId, String customerId,
			String fromDate, String toDate) {
		logger.debug("enter");
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
			DaoUtility.displayQueryWithParameter(49,sqlQuery, wecType,siteId,customerId);	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(49, rs);
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDays(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return siteTotal;
	}
	
	/*----------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/
	/*----------------------------------------------------------------------------------------------------------*/

	/**
	 * 27:WEC Count, 28:Customer Id, 29:State Id, 30:Site Name, 31:Site Id
	 * @param customerId
	 * @param stateId
	 * @param readingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneDayBasedOnStateIdCustomerId(String customerId, String stateId,String readingDate){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = null;
		ResultSet siteIdSiteNameBasedOnCustomerIdSateIdResultSet = null;
		
		PreparedStatement ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = null;
		ResultSet ebIdBasedOnSiteIdStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(getEBIdsWithoutTransferredBasedOnSiteIdStateIdCustomerIdQuery);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, stateId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(3, customerId);
				ebIdBasedOnSiteIdStateIdCustomerIdResultSet = ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					ebIdsSiteWiseStateWiseCustomerWise.add(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				if(ebIdsSiteWiseStateWiseCustomerWise.size() == 0){
					continue;
				}
				
				ebTotalInfo = EBMetaInfo.getManyEBTotalForOneday(ebIdsSiteWiseStateWiseCustomerWise, readingDate);
				
				for (Object object : ebTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_NAME"));
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				ebTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt,ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt) , Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdResultSet,ebIdBasedOnSiteIdStateIdCustomerIdResultSet) , conn);
		}
		return siteWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneMonthBasedOnStateIdCustomerId(String customerId, String stateId,int month, int year){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = null;
		ResultSet siteIdSiteNameBasedOnCustomerIdSateIdResultSet = null;
		
		PreparedStatement ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = null;
		ResultSet ebIdBasedOnSiteIdStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(getEBIdsWithoutTransferredBasedOnStateIdCustomerIdQuery);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, customerId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, stateId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(3, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				ebIdBasedOnSiteIdStateIdCustomerIdResultSet = ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					ebIdsSiteWiseStateWiseCustomerWise.add(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				ebTotalInfo = EBMetaInfo.getManyEBTotalForOneMonth(ebIdsSiteWiseStateWiseCustomerWise, month, year);
				
				for (Object object : ebTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_NAME"));
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				ebTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt,ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt) , Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdResultSet,ebIdBasedOnSiteIdStateIdCustomerIdResultSet) , conn);
		}
		return siteWiseInfo;
	}
	
	/**
	 * 27: WEC Count, 28 : custId, 29 : StateId, 30 : S_Site_name, 31 : S_Site_Id
	 * @param customerId
	 * @param stateId
	 * @param month
	 * @param year
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneMonthBasedOnStateIdCustomerIdMeta(String customerId, String stateId,int month, int year){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = null;
		ResultSet siteIdSiteNameBasedOnCustomerIdSateIdResultSet = null;
		
		PreparedStatement wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt = null;
		ResultSet wecIdBasedOnSiteIdStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> wecIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> wecTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(getWECIdsBasedOnSiteIdCustomerIdQuery);
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, customerId);
				
				wecIdBasedOnSiteIdStateIdCustomerIdResultSet = wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(wecIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					wecIdsSiteWiseStateWiseCustomerWise.add(wecIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_WEC_ID"));
				}
				
				wecTotalInfo = WECMetaInfo.getManyWECTotalForOneMonthMeta(wecIdsSiteWiseStateWiseCustomerWise, month, year);
				
				for (Object object : wecTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_NAME"));
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				wecIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				wecTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt,wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt) , Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdResultSet,wecIdBasedOnSiteIdStateIdCustomerIdResultSet) , conn);
		}
		return siteWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneFiscalYearBasedOnCustomerIdStateId(String customerId, String stateId, int fiscalYear) {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = null;
		ResultSet siteIdSiteNameBasedOnCustomerIdSateIdResultSet = null;
		
		PreparedStatement ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = null;
		ResultSet ebIdBasedOnSiteIdStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(getEBIdsBasedOnSiteIdStateIdCustomerIdQuery);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, customerId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, stateId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(3, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				ebIdBasedOnSiteIdStateIdCustomerIdResultSet = ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					ebIdsSiteWiseStateWiseCustomerWise.add(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				ebTotalInfo = EBMetaInfo.getManyEBTotalForOneFiscalYear(ebIdsSiteWiseStateWiseCustomerWise, fiscalYear);
				
				for (Object object : ebTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_NAME"));
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				ebTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt,ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt) , Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdResultSet,ebIdBasedOnSiteIdStateIdCustomerIdResultSet) , conn);
		}
		return siteWiseInfo;
	}

	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForBetweenDaysBasedOnStateIdCustomerId(String customerId, String stateId,String fromReadingDate, String toReadingDate){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		PreparedStatement siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = null;
		ResultSet siteIdSiteNameBasedOnCustomerIdSateIdResultSet = null;
		
		String ebIdBasedOnSiteIdStateIdCustomerIdQuery = "";
		PreparedStatement ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = null;
		ResultSet ebIdBasedOnSiteIdStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> siteWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> siteInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				ebIdBasedOnSiteIdStateIdCustomerIdQuery = getEBIdsBasedOnSiteIdStateIdCustomerIdQuery;
						/*"Select S_EB_ID From Customer_Meta_Data " +
						"Where S_Customer_Id = ? " +
						"And S_State_Id = ? " +
						"And S_Site_Id = ? " +
						"Group By S_Eb_Id " +
						"order by S_EB_ID " ;*/
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(ebIdBasedOnSiteIdStateIdCustomerIdQuery);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, customerId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, stateId);
				ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(3, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				ebIdBasedOnSiteIdStateIdCustomerIdResultSet = ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					ebIdsSiteWiseStateWiseCustomerWise.add(ebIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				ebTotalInfo = EBMetaInfo.getManyEBTotalForBetweenDays(ebIdsSiteWiseStateWiseCustomerWise, fromReadingDate, toReadingDate);
				
				for (Object object : ebTotalInfo) {
					siteInfo.add(object);
				}
				
				siteInfo.add(customerId);
				siteInfo.add(stateId);
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_NAME"));
				siteInfo.add(siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getString("S_SITE_ID"));
				
				siteWiseInfo.add(siteInfo);
				
				siteInfo = new ArrayList<Object>();
				ebIdsSiteWiseStateWiseCustomerWise = new ArrayList<String>();
				ebTotalInfo = new ArrayList<Object>();
			}
			return siteWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt,ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt) , Arrays.asList(siteIdSiteNameBasedOnCustomerIdSateIdResultSet,ebIdBasedOnSiteIdStateIdCustomerIdResultSet) , conn);
		}
		return siteWiseInfo;
	}
	
	public static List<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerId(String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		List<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			return siteWiseWECTotal;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return siteWiseWECTotal;

	}
	

	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerId(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> ebIds = new ArrayList<String>();	
		List<Object> ebIdsTotal = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getEBIdsBasedOnSiteIdCustomerIdQuery);
			prepStmt.setString(1, customerId);
			prepStmt.setString(2, siteId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ebIds.add(rs.getString("S_EB_ID"));	
			}
			ebIdsTotal = EBMetaInfo.getManyEBTotalForBetweenDays(ebIds, fromReadingDate, toReadingDate);
			return ebIdsTotal;
		}
		catch (Exception e) {logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);

			//System.out.println("SiteMetaInfo:getOneSiteTotalBasedOnSiteIdCustomerId Exception->" + e.getMessage());
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return ebIdsTotal;
	}
	
	

	
	
	
	
	

	
}
