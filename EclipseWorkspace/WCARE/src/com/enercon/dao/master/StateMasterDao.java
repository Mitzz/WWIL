package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.master.AreaMasterVo;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.EbMasterVo;
import com.enercon.model.master.SiteMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecMasterVo;

public class StateMasterDao implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(StateMasterDao.class);
	
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
			try{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public static String getStateNameBasedOnStateId(
			String stateId) throws SQLException {
		String stateName = new String();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = "Select S_state_name " + 
					"from tbl_state_master " + 
					"where S_state_id = ?" ; 

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, stateId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				stateName = resultSet.getString("S_STATE_NAME");
			}
			return stateName;
		}
		finally{
			try{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public static Map<String, String> getStateIdNameMappingBasedOnWECIds(
			Set<String> wecIds) throws SQLException {
		Map<String, String> stateIdNameMapping = new TreeMap<String, String>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = "select S_state_id, S_State_Name " + 
					"from Customer_Meta_Data " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"group by S_state_id " ; 

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				stateIdNameMapping.put(resultSet.getString("S_STATE_ID"), resultSet.getString("S_STATE_NAME"));
			}
			return stateIdNameMapping;
		}
		finally{
			try{
				if(preparedStatement != null){
					preparedStatement.close();
				}
				if(resultSet != null){
					resultSet.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

	public static Set<String> getStateIdsBasedOnCustomerIds(
			Set<String> customerIds) throws SQLException {

		Set<String> stateIds = new TreeSet<String>();
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Meta.S_State_Name,Meta.S_State_Id " + 
					"From Customer_Meta_Data Meta, Tbl_Wec_Master Wecmaster " + 
					"Where Meta.S_Wec_Id = Wecmaster.S_Wec_Id " + 
					"And Meta.S_Customer_Id In " + GlobalUtils.getStringFromArrayForQuery(customerIds) +
					"And Wecmaster.S_Status In ('1') " + 
					"Group By Meta.S_State_Name,Meta.S_State_Id " + 
					"order by Meta.S_State_Name "; 
			prepStmt = connection.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				stateIds.add(rs.getString("S_STATE_ID"));
			}
			return stateIds;
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
				e.printStackTrace();
			}
		}
	}
	
	public static List<String> getStateIdsBasedOnAreaIDs(String[] areaIds) {

		List<String> areaStateVo = new ArrayList<String>();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try {
			conn = wcareConnector.getConnectionFromPool();

			sqlQuery = 
					"select s_state_id,s_state_name " + 
					"from customer_meta_data " + 
					"where s_area_id in " + GlobalUtils.getStringFromArrayForQuery(Arrays.asList(areaIds)) + 
					"group by s_state_id,s_state_name " + 
					"order by s_state_name";

			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {

				areaStateVo.add(rs.getString("S_STATE_ID"));
			}

			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return areaStateVo;
		} catch (Exception e) {
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
				System.out.println(e.getMessage());
			}
		}
		return areaStateVo;

	}
	
	public List<StateMasterVo> getStateMasterVos() throws SQLException{
		List<StateMasterVo> stateMasterVos = new ArrayList<StateMasterVo>();

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

//		String wecId = null;
//		String wecName = null;
//		String ebId = null;
//		String ebName = null;
//		String siteId = null;
//		String siteName = null;
//		String areaId = null;
//		String areaName = null;
		String stateId = null;
		String stateName = null;
		try {
			conn = wcareConnector.getConnectionFromPool();

			
				query = "Select S_state_name, S_state_id " +
						"from tbl_state_master " ;
//						"from customer_meta_data " + 
//						"where S_status in ('1')" ;
				prepStmt = conn.prepareStatement(query);
				rs = prepStmt.executeQuery();
				
				System.out.println("Done");
				while (rs.next()) {
					stateId = rs.getString("S_state_ID");
					stateName = rs.getString("S_state_name");

					StateMasterVo vo = new StateMasterVo(stateId);
					vo.setName(stateName);
					stateMasterVos.add(vo);
				}
	    	
			

			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return stateMasterVos;
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

	}
	
	public List<StateMasterVo> get(CustomerMasterVo customer) throws SQLException{
		List<StateMasterVo> stateMasterVosList = new ArrayList<StateMasterVo>();
		
		Map<String, WecMasterVo> wecMasterVos = new HashMap<String, WecMasterVo>();
		Map<String, EbMasterVo> ebMasterVos = new HashMap<String, EbMasterVo>();
		Map<String, SiteMasterVo> siteMasterVos = new HashMap<String, SiteMasterVo>();
		Map<String, AreaMasterVo> areaMasterVos = new HashMap<String, AreaMasterVo>();
		Map<String, StateMasterVo> stateMasterVos = new HashMap<String, StateMasterVo>();

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String wecId = null;
		String wecName = null;
		String ebId = null;
		String ebName = null;
		String siteId = null;
		String siteName = null;
		String areaId = null;
		String areaName = null;
		String stateId = null;
		String stateName = null;
		try {
			
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select wecmaster.s_wec_id, wecmaster.s_wecshort_descr, ebmaster.S_eb_id, ebmaster.s_ebshort_descr, sitemaster.S_site_id, sitemaster.S_site_name, areamaster.S_area_id, areamaster.S_area_name, statemaster.S_state_id, statemaster.S_state_name " + 
					"from tbl_wec_master wecmaster, tbl_eb_master ebmaster, tbl_site_master sitemaster, tbl_area_master areamaster, tbl_state_master statemaster " + 
					"where wecmaster.S_eb_id = ebmaster.S_eb_id " + 
					"and ebmaster.S_site_id = sitemaster.S_site_id " + 
					"and sitemaster.S_area_id = areamaster.s_area_id " + 
					"and areamaster.S_state_id = statemaster.S_state_id " + 
					"and wecmaster.S_customer_id = ? " + 
					"and wecmaster.s_status in ('1', '9') " ;

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, customer.getId());
			
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				wecId = rs.getString("S_WEC_ID");
				wecName = rs.getString("S_Wecshort_descr");
				ebId = rs.getString("S_EB_ID");
				ebName = rs.getString("S_ebshort_descr");
				siteId = rs.getString("S_site_ID");
				siteName = rs.getString("S_site_name");
				areaId = rs.getString("S_area_ID");
				areaName = rs.getString("S_area_name");
				stateId = rs.getString("S_state_ID");
				stateName = rs.getString("S_state_name");

				if (!stateMasterVos.containsKey(stateId)) {
					StateMasterVo stateMasterVo = new StateMasterVo(stateId);
					stateMasterVo.setName(stateName);

					stateMasterVos.put(stateId, stateMasterVo);
					stateMasterVosList.add(stateMasterVo);
				}
				if (!areaMasterVos.containsKey(areaId)) {
					AreaMasterVo areaMasterVo = new AreaMasterVo(areaId);
					areaMasterVo.setName(areaName);
					areaMasterVo.setState(stateMasterVos.get(stateId));

					areaMasterVos.put(areaId, areaMasterVo);
				}
				if (!siteMasterVos.containsKey(siteId)) {
					SiteMasterVo siteMasterVo = new SiteMasterVo(siteId);
					siteMasterVo.setName(siteName);
					siteMasterVo.setArea(areaMasterVos.get(areaId));

					siteMasterVos.put(siteId, siteMasterVo);
				}
				if (!ebMasterVos.containsKey(ebId)) {
					EbMasterVo ebMasterVo = new EbMasterVo(ebId);
					ebMasterVo.setName(ebName);
					ebMasterVo.setSite(siteMasterVos.get(siteId));

					ebMasterVos.put(ebId, ebMasterVo);
				}
				if (!wecMasterVos.containsKey(wecId)) {
					WecMasterVo wecMasterVo = new WecMasterVo(wecId);
					wecMasterVo.setName(wecName);
					wecMasterVo.setEb(ebMasterVos.get(ebId));
					wecMasterVo.setCustomer(customer);

					wecMasterVos.put(wecId, wecMasterVo);
				}
			}
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return stateMasterVosList;
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

	}
	
	public static void main(String[] args) {
		CustomerMasterVo customer = new CustomerMasterVo("0905000002");
		
		try {
			new StateMasterDao().get(customer);
			
			List<StateMasterVo> states = customer.getStates();
			logger.debug("Start");
			for (StateMasterVo stateMasterVo : states) {
				logger.debug("State Name: " + stateMasterVo.getName());
				List<WecMasterVo> wecs = stateMasterVo.getWecs();
				logger.debug(WecMasterUtility.getWecIds(wecs).size());
			}
			logger.debug("End");

			wcareConnector.shutDown();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
