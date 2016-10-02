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
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.event.StateMasterEvent;
import com.enercon.model.graph.listener.StateMasterListener;
import com.enercon.model.master.AreaMasterVo;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.EbMasterVo;
import com.enercon.model.master.SiteMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecMasterVo;

public class StateMasterDao implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(StateMasterDao.class);
//	private final Graph G = Graph.getInstance();
	
	private List<StateMasterListener> listeners =  new ArrayList<StateMasterListener>();
	
	private static class SingletonHelper{
		public final static StateMasterDao INSTANCE = new StateMasterDao();
	}
	
	public static StateMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private StateMasterDao(){
		
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
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
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
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
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
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
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
			DaoUtility.releaseResources(prepStmt, rs, connection);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
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
				
				logger.debug("Done");
				while (rs.next()) {
					stateId = rs.getString("S_state_ID");
					stateName = rs.getString("S_state_name");

					StateMasterVo vo = new StateMasterVo(stateId);
					vo.setName(stateName);
					stateMasterVos.add(vo);
				}

			return stateMasterVos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}

	}
	
	public List<IStateMasterVo> getAll() throws SQLException{
		List<IStateMasterVo> states = new ArrayList<IStateMasterVo>(Graph.getInstance().getStatesM().values());
		return states;
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
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}

	}
	
	public List<IStateMasterVo> getAll(ICustomerMasterVo customer){
		return Graph.getInstance().getCustomersM().get(customer.getId()).getStates();
	}
	
	public static void main(String[] args) {
		
	}

	public List<IStateMasterVo> getActive(ICustomerMasterVo customer) {
		List<IStateMasterVo> states = getAll(customer);
		
		Set<IStateMasterVo> activeStatesS = new HashSet<IStateMasterVo>();
		for(IStateMasterVo state: states){
			for(IWecMasterVo wec: state.getWecs()){
				if(wec.getStatus().equals("1") && wec.getCustomer().equals(customer)){
					activeStatesS.add(state);
				}
			}
		}
		List<IStateMasterVo> activeStates = new ArrayList<IStateMasterVo>(activeStatesS);
		return activeStates;
	}

	public IStateMasterVo get(String id) {
		return Graph.getInstance().getStatesM().get(id);
	}
	
	public boolean exist(com.enercon.model.graph.StateMasterVo vo) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "SELECT count(1) as Count FROM TBL_STATE_MASTER WHERE S_STATE_NAME = ?";
			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, vo.getName());
			
			rs = prepStmt.executeQuery();
			rs.next();
			return rs.getInt("count") == 1;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(com.enercon.model.graph.StateMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		com.enercon.model.graph.StateMasterVo vo = (com.enercon.model.graph.StateMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_STATE_MASTER(S_STATE_ID,S_STATE_NAME,S_CREATED_BY, " +
					   "S_LAST_MODIFIED_BY,S_SAP_STATE_CODE) " +
					   "values(?,?,?,?,?) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getCreatedBy() );
			prepStmt.setObject(4, vo.getModifiedBy());
			prepStmt.setObject(5, vo.getSapCode());
		
			int rowInserted = prepStmt.executeUpdate();
			
			logger.debug("rowInserted: " + rowInserted);
			if(rowInserted == 1){
				StateMasterEvent event = new StateMasterEvent();
				event.setCreate(true);
				event.setState(vo);
				fireStateMasterEvent(event);
			}
			return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public boolean updateForMaster(IStateMasterVo vo1) throws SQLException, CloneNotSupportedException {
		IStateMasterVo vo = (IStateMasterVo) vo1.clone();
		
		SortedMap<String, Object> data = new TreeMap<String, Object>();
		data.put("S_STATE_NAME", vo.getName());
		data.put("S_LAST_MODIFIED_BY", vo.getModifiedBy());
		data.put("S_SAP_STATE_CODE", vo.getSapCode());
		return partialUpdate(vo, data);
		
	}
	
	private boolean partialUpdate(IStateMasterVo state, SortedMap<String, Object> data) throws SQLException {
		logger.debug(data);
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = data.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_STATE_MASTER SET ");
			
			for(String column: data.keySet()){
				query.append(column + " = ?, ");
			}
			
//			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
			query.append("WHERE S_STATE_ID = ? ");
			logger.debug(state.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug(query);
			for(String column: data.keySet()){
				index++;
				prepStmt.setObject(index, data.get(column));
			}
			prepStmt.setObject(paramterCount + 1, state.getId());
			
			int rowUpdate = prepStmt.executeUpdate();

			if(rowUpdate == 1) {
				StateMasterEvent event = new StateMasterEvent();
				event.setState(state);
				event.setUpdate(true);
//				event.setUpdateData(data);
				fireStateMasterEvent(event);
			}
			return (rowUpdate == 1);
//			return true;
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}

	public List<IStateMasterVo> associate(List<ISiteMasterVo> sites) throws SQLException {
		Set<IStateMasterVo> states = new HashSet<IStateMasterVo>();
		for(ISiteMasterVo site: sites){
			states.add(site.getState());
		}
		return new ArrayList<IStateMasterVo>(states);
	}
	
	public void addStateMasterListener(StateMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireStateMasterEvent(StateMasterEvent event){
		for(StateMasterListener listener: listeners){
			listener.handler(event);
		}
	}

}
