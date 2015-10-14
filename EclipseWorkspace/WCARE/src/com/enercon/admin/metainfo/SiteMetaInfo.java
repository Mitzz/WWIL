package com.enercon.admin.metainfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.sqlQuery.AllQueries;

public class SiteMetaInfo implements AllQueries{
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
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt = conn.prepareStatement(getSiteIdSiteNameBasedOnStateIdCustomerIdQuery);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(1, customerId);
			siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.setObject(2, stateId);
			siteIdSiteNameBasedOnCustomerIdSateIdResultSet = siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.executeQuery();
			
			while (siteIdSiteNameBasedOnCustomerIdSateIdResultSet.next()) {
				
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt = conn.prepareStatement(getWECIdsBasedOnSiteIdStateIdCustomerIdQuery);
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(1, siteIdSiteNameBasedOnCustomerIdSateIdResultSet.getObject("S_SITE_ID"));
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(2, stateId);
				wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.setObject(3, customerId);
				wecIdBasedOnSiteIdStateIdCustomerIdResultSet = wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.executeQuery();
				
				while(wecIdBasedOnSiteIdStateIdCustomerIdResultSet.next()){
					wecIdsSiteWiseStateWiseCustomerWise.add(wecIdBasedOnSiteIdStateIdCustomerIdResultSet.getString("S_WEC_ID"));
				}
				
				wecTotalInfo = WECMetaInfo.getManyWECTotalForOneDayMeta(wecIdsSiteWiseStateWiseCustomerWise, readingDate);
				
				
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
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
					
					
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(wecIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					wecIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
				
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
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
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			return WECMetaInfo.getManyWECsWECWiseTotalForBetweenDaysMeta(wecIds, fromDate, toDate);
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return null;

	}
	
	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerIdMeta(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		//System.out.println("Site ID:" + siteId);
		//System.out.println("Customer ID:" + customerId);
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> wecIds = new ArrayList<String>();	
		List<Object> wecIdsTotal = null;
		try{
			conn = conmanager.getConnection();
			prepStmt = conn.prepareStatement(getWECIdsBasedOnSiteIdCustomerIdQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));	
				
			}
			wecIdsTotal = WECMetaInfo.getManyWECTotalForBetweenDaysMeta(wecIds, fromReadingDate, toReadingDate);
			return wecIdsTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("SiteMetaInfo:getOneSiteTotalBasedOnSiteIdCustomerId Exception->" + e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return wecIdsTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(String wecType,String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDaysMPR(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			
			return siteWiseWECTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
					
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {
				MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMPR(
			String wecType, String siteId, String customerId,
			String fromDate, String toDate) {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDaysMPR(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
					
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerIdChange(String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnSiteIdCustomerId(String siteId, String customerId,
			String fromDate, String toDate) {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, siteId);
			prepStmt.setString(2, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDays(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteTotal;
	}
	
	public static ArrayList<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(String wecType,String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		ArrayList<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) wecTotal);
				wecTotal = new ArrayList<Object>();
			}
			//GlobalUtils.displayVectorMember(siteWiseWECTotal);
			
			return siteWiseWECTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
					
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {
				MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteWiseWECTotal;

	}
	
	public static List<Object> getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerId(
			String wecType, String siteId, String customerId,
			String fromDate, String toDate) {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		List<Object> siteTotal = new ArrayList<Object>();
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery;
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecType);
			prepStmt.setString(2, siteId);
			prepStmt.setString(3, customerId);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				/*siteTotal = WECMetaInfo.getOneWECTotalForBetweenDays(rs.getString("S_WEC_ID"), fromDate, toDate);
				siteWiseWECTotal.add((ArrayList<Object>) siteTotal);
				siteTotal = new ArrayList<Object>();*/
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			siteTotal = WECMetaInfo.getManyWECsTotalForBetweenDays(wecIds, fromDate, toDate);
			return siteTotal;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
					
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
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
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
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
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					ebIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
				
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return siteWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneMonthBasedOnStateIdCustomerId(String customerId, String stateId,int month, int year){
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					ebIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
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
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("StateWise Exception");
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					wecIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(wecIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					wecIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return siteWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForOneFiscalYearBasedOnCustomerIdStateId(String customerId, String stateId, int fiscalYear) {
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("SiteWise Exception");
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					ebIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return siteWiseInfo;
	}

	public static ArrayList<ArrayList<Object>> getSiteWiseTotalForBetweenDaysBasedOnStateIdCustomerId(String customerId, String stateId,String fromReadingDate, String toReadingDate){
		JDBCUtils conmanager = new JDBCUtils();
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
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("StateWise Exception");
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt != null){
					siteIdSiteNameBasedOnCustomerIdSateIdPrepStmt.close();
				}
				if(siteIdSiteNameBasedOnCustomerIdSateIdResultSet != null){
					siteIdSiteNameBasedOnCustomerIdSateIdResultSet.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt != null){
					ebIdBasedOnSiteIdStateIdCustomerIdPrepStmt.close();
				}
				if(ebIdBasedOnSiteIdStateIdCustomerIdResultSet != null){
					ebIdBasedOnSiteIdStateIdCustomerIdResultSet.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return siteWiseInfo;
	}
	
	public static List<ArrayList<Object>> getOneSiteWECWiseTotalForBetweenDaysBasedOnSiteIdCustomerId(String siteId, String customerId, String fromDate, String toDate){
		/*System.out.println("CustId:" + customerId);
		System.out.println("SiteId:" + siteId);*/
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		List<Object> wecTotal = new ArrayList<Object>();
		List<ArrayList<Object>> siteWiseWECTotal = new ArrayList<ArrayList<Object>>();
		try{
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return siteWiseWECTotal;

	}
	
	
	
	
	
	
	
	
	
	public static List<Object> getOneSiteTotalBasedOnSiteIdCustomerId(String siteId, String customerId, String fromReadingDate, String toReadingDate){
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<String> ebIds = new ArrayList<String>();	
		List<Object> ebIdsTotal = null;
		try{
			conn = conmanager.getConnection();
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
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println("SiteMetaInfo:getOneSiteTotalBasedOnSiteIdCustomerId Exception->" + e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();conmanager.closeConnection();conmanager = null;
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch (Exception e) {MethodClass.displayMethodClassName();
				System.out.println(e.getMessage());
			}
		}
		return ebIdsTotal;
	}
	
	

	
	
	
	
	

	
}
