package com.enercon.admin.metainfo;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.sqlQuery.AllQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class StateMetaInfo implements AllQueries,WcareConnector{
	 private final static Logger logger = Logger.getLogger(StateMetaInfo.class);
	private static Connection connection = null; 
	
	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		StateMetaInfo.connection = connection;
	}
	
	public static String getStateNameBasedOnStateId(String stateId) throws SQLException ,Exception{

		String stateName = null;
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select S_state_name " + 
					"from tbl_State_master " + 
					"where S_State_Id = ? "; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, stateId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				stateName = rs.getString("S_STATE_NAME");
			}
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return stateName;
	}
	
	/**
	 * 27 : WEC Count, 28 : CustId, 29 : State Name, 30 : State Id
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getStateWiseTotalForOneDayBasedOnCustomerIdMeta(
			String customerId, String readingDate) {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		String query1 = "";
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		
		String query2 = "";
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> wecIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> wecIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			query1 = getStateIdStateNameBasedOnCustomerIdQuery;
			ps1 = conn.prepareStatement(query1);
			ps1.setObject(1, customerId);
			DaoUtility.displayQueryWithParameter(1, getStateIdStateNameBasedOnCustomerIdQuery, customerId);
			rs1 = ps1.executeQuery();
			
			while (rs1.next()) {
				DaoUtility.getRowCount(1, rs1);
				query2 = getWECIdsBasedOnStateIdCustomerIdQuery;
				ps2 = conn.prepareStatement(query2);
				ps2.setObject(1, rs1.getObject("S_STATE_ID"));
				ps2.setObject(2, customerId);
				DaoUtility.displayQueryWithParameter(2 , getWECIdsBasedOnStateIdCustomerIdQuery, rs1.getObject("S_STATE_ID"), customerId);
				rs2 = ps2.executeQuery();
				
				while(rs2.next()){
					DaoUtility.getRowCount(2, rs2);
					wecIdsStateWiseCustomerWise.add(rs2.getString("S_WEC_ID"));
				}
				
				wecIdsTotalInfo = WECMetaInfo.getManyWECTotalForOneDayMeta(wecIdsStateWiseCustomerWise, readingDate);
				
				for (Object object : wecIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(rs1.getString("S_STATE_NAME"));
				stateInfo.add(rs1.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				wecIdsStateWiseCustomerWise = new ArrayList<String>();
				wecIdsTotalInfo = new ArrayList<Object>();
			}
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(ps1,ps2) , Arrays.asList(rs1,rs2) , conn);
		}
		return stateWiseInfo;
	}
	
	/*-----------------------------------------------------------------------------------------------------------------*/

	/**
	 * 27:WEC Count, 28:Customer Id, 29:State Name, 30:State Id
	 * @param customerId
	 * @param readingDate
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getStateWiseTotalForOneDayBasedOnCustomerId(String customerId, String readingDate){
		Connection conn = null;
		//JDBCUtils conmanager = new JDBCUtils();
		
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		PreparedStatement EBIdBasedOnStateIdCustomerIdPrepStmt = null;
		ResultSet EBIdBasedOnStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(getStateIdStateNameBasedOnCustomerIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setObject(1, customerId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				
				EBIdBasedOnStateIdCustomerIdPrepStmt = conn.prepareStatement(getEBIdsWithoutTransferredBasedOnStateIdCustomerIdQuery);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(1, customerId);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(2, stateIdStateNameBasedOnCustomerIdResultSet.getObject("S_STATE_ID"));
				EBIdBasedOnStateIdCustomerIdResultSet = EBIdBasedOnStateIdCustomerIdPrepStmt.executeQuery();
				
				while(EBIdBasedOnStateIdCustomerIdResultSet.next()){
					ebIdsStateWiseCustomerWise.add(EBIdBasedOnStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				if(ebIdsStateWiseCustomerWise.size() == 0){
					continue;
				}
				
				ebIdsTotalInfo = EBMetaInfo.getManyEBTotalForOneday(ebIdsStateWiseCustomerWise, readingDate);
				
				for (Object object : ebIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_NAME"));
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				ebIdsStateWiseCustomerWise = new ArrayList<String>();
				ebIdsTotalInfo = new ArrayList<Object>();
				
			}
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt,EBIdBasedOnStateIdCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet,EBIdBasedOnStateIdCustomerIdResultSet) , conn);
		}
		return stateWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getStateWiseTotalForOneMonthBasedOnCustomerId(String customerId, int month, int year){
	//	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		//String stateIdStateNameBasedOnCustomerIdQuery = "";
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		//String EBIdBasedOnStateIdCustomerIdQuery = "";
		PreparedStatement EBIdBasedOnStateIdCustomerIdPrepStmt = null;
		ResultSet EBIdBasedOnStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			//stateIdStateNameBasedOnCustomerIdQuery = ;
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(getStateIdStateNameBasedOnCustomerIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setObject(1, customerId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				
				
				//EBIdBasedOnStateIdCustomerIdQuery = ;
				EBIdBasedOnStateIdCustomerIdPrepStmt = conn.prepareStatement(getEBIdsWithoutTransferredBasedOnStateIdCustomerIdQuery);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(1, customerId);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(2, stateIdStateNameBasedOnCustomerIdResultSet.getObject("S_STATE_ID"));
				EBIdBasedOnStateIdCustomerIdResultSet = EBIdBasedOnStateIdCustomerIdPrepStmt.executeQuery();
				
				while(EBIdBasedOnStateIdCustomerIdResultSet.next()){
					ebIdsStateWiseCustomerWise.add(EBIdBasedOnStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				//GlobalUtils.displayVectorMember(ebIdsStateWiseCustomerWise);
				if(ebIdsStateWiseCustomerWise.size() == 0){
					continue;
				}
				ebIdsTotalInfo = EBMetaInfo.getManyEBTotalForOneMonth(ebIdsStateWiseCustomerWise, month, year);
				
				for (Object object : ebIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_NAME"));
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				ebIdsStateWiseCustomerWise = new ArrayList<String>();
				ebIdsTotalInfo = new ArrayList<Object>();
			}
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt,EBIdBasedOnStateIdCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet,EBIdBasedOnStateIdCustomerIdResultSet) , conn);
		}
		return stateWiseInfo;
	}
	
	/**
	 * 27 : WECCount, 28 : Customer Id, 29 : State_Name, 30 : State_Id 
	 * @param customerId
	 * @param month
	 * @param year
	 * @return
	 */
	public static ArrayList<ArrayList<Object>> getStateWiseTotalForOneMonthBasedOnCustomerIdMeta(String customerId, int month, int year){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		
		//String stateIdStateNameBasedOnCustomerIdQuery = "";
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		//String EBIdBasedOnStateIdCustomerIdQuery = "";
		PreparedStatement wecIdBasedOnStateIdCustomerIdPrepStmt = null;
		ResultSet wecIdBasedOnStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> wecIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> wecIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			//stateIdStateNameBasedOnCustomerIdQuery = ;
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(getStateIdStateNameBasedOnCustomerIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setObject(1, customerId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				
				wecIdBasedOnStateIdCustomerIdPrepStmt = conn.prepareStatement(getWECIdsBasedOnStateIdCustomerIdQuery);
				wecIdBasedOnStateIdCustomerIdPrepStmt.setObject(1, stateIdStateNameBasedOnCustomerIdResultSet.getObject("S_STATE_ID"));
				wecIdBasedOnStateIdCustomerIdPrepStmt.setObject(2, customerId);
				
				wecIdBasedOnStateIdCustomerIdResultSet = wecIdBasedOnStateIdCustomerIdPrepStmt.executeQuery();
				
				while(wecIdBasedOnStateIdCustomerIdResultSet.next()){
					wecIdsStateWiseCustomerWise.add(wecIdBasedOnStateIdCustomerIdResultSet.getString("S_WEC_ID"));
				}
				
				wecIdsTotalInfo = WECMetaInfo.getManyWECTotalForOneMonthMeta(wecIdsStateWiseCustomerWise, month, year);
				
				for (Object object : wecIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_NAME"));
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				wecIdsStateWiseCustomerWise = new ArrayList<String>();
				wecIdsTotalInfo = new ArrayList<Object>();
			}
			
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt,wecIdBasedOnStateIdCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet,wecIdBasedOnStateIdCustomerIdResultSet) , conn);
		}
		return stateWiseInfo;
	}

	public static ArrayList<ArrayList<Object>> getStateWiseTotalForOneFiscalYearBasedOnCustomerId(String customerId, int fiscalYear){
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String stateIdStateNameBasedOnCustomerIdQuery = "";
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		String EBIdBasedOnStateIdCustomerIdQuery = "";
		PreparedStatement EBIdBasedOnStateIdCustomerIdPrepStmt = null;
		ResultSet EBIdBasedOnStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			stateIdStateNameBasedOnCustomerIdQuery = getStateIdStateNameBasedOnCustomerIdQuery;
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(stateIdStateNameBasedOnCustomerIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setObject(1, customerId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				
				
				EBIdBasedOnStateIdCustomerIdQuery = getEBIdsBasedOnStateIdCustomerIdQuery;
				EBIdBasedOnStateIdCustomerIdPrepStmt = conn.prepareStatement(EBIdBasedOnStateIdCustomerIdQuery);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(1, customerId);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(2, stateIdStateNameBasedOnCustomerIdResultSet.getObject("S_STATE_ID"));
				EBIdBasedOnStateIdCustomerIdResultSet = EBIdBasedOnStateIdCustomerIdPrepStmt.executeQuery();
				
				while(EBIdBasedOnStateIdCustomerIdResultSet.next()){
					ebIdsStateWiseCustomerWise.add(EBIdBasedOnStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				ebIdsTotalInfo = EBMetaInfo.getManyEBTotalForOneFiscalYear(ebIdsStateWiseCustomerWise, fiscalYear);
				
				for (Object object : ebIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_NAME"));
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				ebIdsStateWiseCustomerWise = new ArrayList<String>();
				ebIdsTotalInfo = new ArrayList<Object>();
			}
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt,EBIdBasedOnStateIdCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet,EBIdBasedOnStateIdCustomerIdResultSet) , conn);
		}
		return stateWiseInfo;
	}
	
	public static ArrayList<ArrayList<Object>> getStateWiseTotalForBetweenDaysBasedOnCustomerId(String customerId, String fromReadingDate, String toReadingDate){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String stateIdStateNameBasedOnCustomerIdQuery = "";
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		String EBIdBasedOnStateIdCustomerIdQuery = "";
		PreparedStatement EBIdBasedOnStateIdCustomerIdPrepStmt = null;
		ResultSet EBIdBasedOnStateIdCustomerIdResultSet = null;
		
		ArrayList<ArrayList<Object>> stateWiseInfo = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> stateInfo = new ArrayList<Object>();
		
		ArrayList<String> ebIdsStateWiseCustomerWise = new ArrayList<String>();
		
		List<Object> ebIdsTotalInfo = new ArrayList<Object>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			stateIdStateNameBasedOnCustomerIdQuery = getStateIdStateNameBasedOnCustomerIdQuery;
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(stateIdStateNameBasedOnCustomerIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setObject(1, customerId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				
				
				EBIdBasedOnStateIdCustomerIdQuery = getEBIdsBasedOnStateIdCustomerIdQuery;
				EBIdBasedOnStateIdCustomerIdPrepStmt = conn.prepareStatement(EBIdBasedOnStateIdCustomerIdQuery);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(1, customerId);
				EBIdBasedOnStateIdCustomerIdPrepStmt.setObject(2, stateIdStateNameBasedOnCustomerIdResultSet.getObject("S_STATE_ID"));
				EBIdBasedOnStateIdCustomerIdResultSet = EBIdBasedOnStateIdCustomerIdPrepStmt.executeQuery();
				
				while(EBIdBasedOnStateIdCustomerIdResultSet.next()){
					ebIdsStateWiseCustomerWise.add(EBIdBasedOnStateIdCustomerIdResultSet.getString("S_EB_ID"));
				}
				
				ebIdsTotalInfo = EBMetaInfo.getManyEBTotalForBetweenDays(ebIdsStateWiseCustomerWise, fromReadingDate, toReadingDate);
				
				for (Object object : ebIdsTotalInfo) {
					stateInfo.add(object);
				}
				stateInfo.add(customerId);
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_NAME"));
				stateInfo.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_STATE_ID"));
				
				stateWiseInfo.add(stateInfo);
				
				stateInfo = new ArrayList<Object>();
				ebIdsStateWiseCustomerWise = new ArrayList<String>();
				ebIdsTotalInfo = new ArrayList<Object>();
			}
			return stateWiseInfo;
		}
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt,EBIdBasedOnStateIdCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet,EBIdBasedOnStateIdCustomerIdResultSet) , conn);
		}
		return stateWiseInfo;
	}
	
	public static List<String> getStateNameStateIdBasedOnWECId(String wecId){
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement stateIdStateNameBasedOnCustomerIdPrepStmt = null;
		ResultSet stateIdStateNameBasedOnCustomerIdResultSet = null;
		
		List<String> stateIdStateName = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			stateIdStateNameBasedOnCustomerIdPrepStmt = conn.prepareStatement(getStateIdStateNameBasedOnWECIdQuery);
			stateIdStateNameBasedOnCustomerIdPrepStmt.setString(1, wecId);	
			stateIdStateNameBasedOnCustomerIdResultSet = stateIdStateNameBasedOnCustomerIdPrepStmt.executeQuery();
			
			while (stateIdStateNameBasedOnCustomerIdResultSet.next()) {
				stateIdStateName.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_State_Name"));
				stateIdStateName.add(stateIdStateNameBasedOnCustomerIdResultSet.getString("S_State_ID"));
			}
			return stateIdStateName;
		}
		
		catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(stateIdStateNameBasedOnCustomerIdPrepStmt) , Arrays.asList(stateIdStateNameBasedOnCustomerIdResultSet) , conn);
		}
		return stateIdStateName;
	}
	
	public static Set<String> getStateIdsBasedOnWECIds(
			Set<String> wecIds) throws SQLException {
		Set<String> stateIds = new HashSet<String>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = "select S_state_id " + 
					"from Customer_Meta_Data " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"group by S_state_id " ; 

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				stateIds.add(resultSet.getString("S_STATE_ID"));
			}
			return stateIds;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement,resultSet, connection);
		}
		
	}

	

	
	
}
