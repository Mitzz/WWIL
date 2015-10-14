package com.enercon.admin.metainfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.DebuggerUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.sqlQuery.AllQueries;

public class CustomerMetaInfo extends DebuggerUtility implements AllQueries,WcareConnector{
	
	/**
	 * No Addition
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getOverallTotalForOneDayBasedOnCustomerIdMeta(String customerId, String readingDate){
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> wecIds = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getWECIdsBasedOnCustomerIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, customerId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));	
			}
			return WECMetaInfo.getManyWECTotalForOneDayMeta(wecIds, readingDate);
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
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
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	public static ArrayList<ArrayList<String>> getWECCountSiteNameStateNameBasedOnCustomerIdsSiteIds(String customerIds, String siteIds){
		/*System.out.println("CustIds:" + customerIds);
		System.out.println("SiteIds:" + siteIds);*/
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> columnData = new ArrayList<String>();
		ArrayList<ArrayList<String>> recordData = new ArrayList<ArrayList<String>>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select S_customer_name,count(S_wec_id), S_Site_Id , S_Site_Name,S_Customer_Id,S_State_Name " +
					"From Customer_Meta_Data " +
					"where S_Customer_Id In (" + customerIds + ") " +
					"And S_Site_Id In (" + siteIds + ") " +
					"Group By S_CUSTOMER_NAME,S_Customer_Id, S_State_Name, S_Site_Name, S_Site_Id  " +
					"order by S_CUSTOMER_NAME,S_SITE_NAME " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			String[] columnNames = GlobalUtils.getColumnNames(rs);
			
			while (rs.next()) {
				for (String columnName : columnNames) {
					columnData.add(rs.getString(columnName));
				}
				recordData.add(columnData);
				columnData = new ArrayList<String>();
			}
			return recordData;
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
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
				System.out.println(e.getMessage());
			}
		}
		return recordData;
	
	}
	
	public static List<String> getCustomerMetaInfoBasedOnWECId(String wecId){
		
		Connection conn = null;
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<String> tranList = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getCustomerMetaInfoBasedOnWECId);
			prepStmt.setObject(1, wecId);
			
			rs = prepStmt.executeQuery();
			String[] columnNames = GlobalUtils.getColumnNames(rs);
			
			while (rs.next()) {
				for (String columnName : columnNames) {
					tranList.add(rs.getString(columnName));
				}
			}
			return tranList;
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
	return tranList;
}
	
	public static List<Object> getCustomerInfoBasedOnSiteIdWECType(String siteId, String wecType) {
		
		Connection conn = null;
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<Object> tranList = new ArrayList<Object>();
		
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getCustomerInfoBasedonSiteIdWECTypeQuery);
			prepStmt.setObject(1, siteId);
			prepStmt.setObject(2, wecType);
			//DatabaseUtility.getSQLQueryResultInHTMLFile(getCustomerInfoBasedonSiteIdWECTypeQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(),siteId, wecType);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				Vector<Object> v = new Vector<Object>();
				
				v.add(rs.getObject("S_CUSTOMER_NAME"));
				v.add(rs.getObject("S_SITE_NAME"));
				v.add(rs.getObject("S_STATE_NAME"));
				v.add(rs.getObject("S_SITE_ID"));
				v.add(rs.getObject("S_CUSTOMER_ID"));				
				v.add(rs.getObject("cnt"));
				v.add(rs.getObject("lcapacity"));
				
				tranList.add(v);
			}
			
			return tranList;
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	public static List<String> getCustomerMetaInfoBasedOnEbId(String ebId) {
		
		Connection conn = null;
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<String> tranList = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getCustomerMetaInfoBasedOnEbId);
			prepStmt.setObject(1, ebId);
			
			rs = prepStmt.executeQuery();
			String[] columnNames = GlobalUtils.getColumnNames(rs);
			
			if (rs.next()) {
				for (String columnName : columnNames) {
					tranList.add(rs.getString(columnName));
				}
			}
			return tranList;
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
	return tranList;
	}
	
	public static ArrayList<ArrayList<String>> getCustomerMetaInfoBasedOnWECTypeStateIdWithLoadCapacity(String wecType, String stateId) {
		
		Connection conn = null;
		ArrayList<ArrayList<String>> customersMetaInfo = new ArrayList<ArrayList<String>>();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<String> tranList = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			if(wecType.equalsIgnoreCase("ALL")){
				prepStmt = conn.prepareStatement(getCustomerMetaInfoBasedOnStateIdWithLoadCapacityQuery);
				prepStmt.setObject(1, stateId);
			}
			else{
				prepStmt = conn.prepareStatement(getCustomerMetaInfoBasedOnWECTypeStateIdWithLoadCapacityQuery);
				prepStmt.setObject(1, wecType);
				prepStmt.setObject(2, stateId);
			}
			
			rs = prepStmt.executeQuery();
			String[] columnNames = GlobalUtils.getColumnNames(rs);
			
			while (rs.next()) {
				for (String columnName : columnNames) {
					tranList.add(rs.getString(columnName));
				}
				customersMetaInfo.add(tranList);
				tranList = new ArrayList<String>();
			}
			return customersMetaInfo;
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
	return customersMetaInfo;
	}
	
	public static ArrayList<ArrayList<String>> getCustomerMetaInfoBasedOnWECTypeWithLoadCapacity(String wecType) {
		
		Connection conn = null;
		ArrayList<ArrayList<String>> customersMetaInfo = new ArrayList<ArrayList<String>>();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<String> tranList = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			prepStmt = conn.prepareStatement(getCustomerMetaInfoBasedOnWECTypeWithLoadCapacityQuery);
			prepStmt.setObject(1, wecType);
			
			rs = prepStmt.executeQuery();
			String[] columnNames = GlobalUtils.getColumnNames(rs);
			
			while (rs.next()) {
				for (String columnName : columnNames) {
					tranList.add(rs.getString(columnName));
				}
				customersMetaInfo.add(tranList);
				tranList = new ArrayList<String>();
			}
			return customersMetaInfo;
		}
		catch (Exception e) {
			MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
	return customersMetaInfo;
	}
	/*-------------------------------------------------------------------------------------------*/
	
	
	/**
	 * 27 : WEC Count
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static List<Object> getOverallGenerationTotalForOneDayBasedOnCustomerId(String customerId, String readingDate){
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		ArrayList<String> ebIds = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = getEbIdsBasedOnCustomerIdQuery;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, customerId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ebIds.add(rs.getString(1));	
			}
			
			return EBMetaInfo.getManyEBTotalForOneday(ebIds, readingDate);
		}
		catch (Exception e) {MethodClass.displayMethodClassName();
			System.out.println(e.getMessage());
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
				System.out.println(e.getMessage());
			}
		}
		return null;
	}
	
	
	
	
	
	

	
	
	
	
	
}
