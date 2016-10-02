package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.WecLocationData;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.model.graph.listener.WecMasterListener;
import com.enercon.model.master.AreaMasterVo;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.EbMasterVo;
import com.enercon.model.master.PlantMasterVo;
import com.enercon.model.master.SiteMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecMasterVo;
import com.enercon.model.transfer.TransferWecVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class WecMasterDao  {
	
	private static Logger logger = Logger.getLogger(WecMasterDao.class);
	
	private List<WecMasterListener> listeners = new ArrayList<WecMasterListener>();
	
	public static enum WorkingStatus{
		ACTIVE("1"),
		DEACTIVE("2"),
		TRANSFER("9");
		
		private String code;

		private WorkingStatus(String code){
			this.code = code;
		}
		
		public String getCode(){
			return code;
		}
		
		@Override
		public String toString() {
			return getCode();
		}
	};
	
	private static enum Display{
		SHOW("0"),
		NO_SHOW("1");

		private String code;

		private Display(String code){
			this.code = code;
		}
		
		public String getCode(){
			return code;
		}
		
		@Override
		public String toString() {
			return this.getCode();
		}
	};
	
	private static enum ScadaStatus{
		CONNECTED("1"),
		NOT_CONNECTED("0");

		private String code;

		private ScadaStatus(String code){
			this.code = code;
		}
		
		public String getCode(){
			return code;
		}
		
		@Override
		public String toString() {
			return this.getCode();
		}
	};
	
	//private Graph G = Graph.getInstance();
	
	private WecMasterDao(){
		
	}
	
	private static class SingletonHelper{
		public final static WecMasterDao INSTANCE = new WecMasterDao();
	}
	
	public static WecMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}

	public static Set<String> getWecIdsBasedOnCustomerStateSite(
			String customerId, String stateId, String siteId) throws Exception {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIds = new LinkedHashSet<String>();

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_wec_id " + "from customer_meta_data "
					+ "where S_customer_id = ? " + "and S_state_id = ? "
					+ "and S_site_id = ? "
					+ "order by S_customer_name, S_State_name, S_site_name ";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, customerId);
			prepStmt.setObject(2, stateId);
			prepStmt.setObject(3, siteId);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
				
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}

	public static Map<String, Set<String>> getWecIdsStatewise(Set<String> wecIds)
			throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Map<String, Set<String>> stateWiseWecIds = new HashMap<String, Set<String>>();

		String wecId = null;
		String stateId = null;

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_state_id, S_wec_id " + "from customer_meta_data "
					+ "where S_wec_id in "
					+ GlobalUtils.getStringFromArrayForQuery(wecIds)
					+ "order by S_state_id ";

			prepStmt = conn.prepareStatement(query);

			rs = prepStmt.executeQuery();

			while (rs.next()) {
				wecId = rs.getString("S_WEC_ID");
				stateId = rs.getString("S_State_ID");
				merge(stateWiseWecIds, stateId, wecId);
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return stateWiseWecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
	}
	
	public static Set<String> getWecIdsBasedOnStateIdCustomerIds(
			String stateId, Set<String> customerIds) throws SQLException {

		Set<String> wecIds = new HashSet<String>();
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = "select S_Wec_id " + "from customer_meta_data "
					+ "where S_status in ('1') " + "and S_state_id = ? "
					+ "and S_Customer_Id In "
					+ GlobalUtils.getStringFromArrayForQuery(customerIds)
					+ "order by S_wec_id ";

			prepStmt = connection.prepareStatement(sqlQuery);
			prepStmt.setString(1, stateId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			logger.debug("Wecs Size: " + wecIds.size());
			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, connection);

		}

	}

	public static double getWecCapacityBasedOnCustomerIds(
			Collection<String> customerIds) {
		double cap = 0;

		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select Sum(N_Wec_Capacity) " + "From Tbl_Wec_Master "
					+ "where S_wec_id in (Select S_Wec_Id "
					+ "From Tbl_Wec_Master " + "Where S_Customer_Id In "
					+ GlobalUtils.getStringFromArrayForQuery(customerIds)
					+ "And S_Status = 1 " + "group by S_wec_id) ";
			prepStmt = connection.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();

			if (rs.next()) {
				cap = rs.getDouble(1) / 1000;
			}
			return cap;

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, connection);

		}
		return cap;
	}

	public double getTotalWecCapacityBasedOnWecIds(Collection<String> wecIds) {
		double totalWecCapacity = 0;

		Connection connection = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try {
			connection = wcareConnector.getConnectionFromPool();

			for (List<String> splitWecIds : GlobalUtils.splitArrayList(
					new ArrayList<String>(wecIds), 800)) {

				sqlQuery = "Select Sum(N_Wec_Capacity) as Total_Capacity "
						+ "From Tbl_Wec_Master " + "where S_wec_id in "
						+ GlobalUtils.getStringFromArrayForQuery(splitWecIds);

				prepStmt = connection.prepareStatement(sqlQuery);
				rs = prepStmt.executeQuery();

				if (rs.next()) {
					totalWecCapacity += rs.getDouble("Total_Capacity");
				}

			}

			return totalWecCapacity;

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, connection);

		}
		return totalWecCapacity;
	}

	public static Set<String> getWecIdsBasedOnStateIds(Set<String> stateIds)
			throws SQLException {
		Set<String> wecIds = new HashSet<String>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = wcareConnector.getConnectionFromPool();
			query = "Select S_wec_id " + "from customer_meta_data "
					+ "where S_state_id in "
					+ GlobalUtils.getStringFromArrayForQuery(stateIds)
					+ "and S_Status in ('1') " + "group by S_wec_ID ";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				wecIds.add(resultSet.getString("S_WEC_ID"));
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}

	}

	public static double getWECCapacityBasedOnCustomerIds(
			Collection<String> customerIds) {
		// System.out.println(customerIdsInString);
		double cap = 0;

		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select Sum(N_Wec_Capacity) " + "From Tbl_Wec_Master "
					+ "where S_wec_id in (Select S_Wec_Id "
					+ "From Tbl_Wec_Master " + "Where S_Customer_Id In "
					+ GlobalUtils.getStringFromArrayForQuery(customerIds)
					+ "And S_Status = 1 " + "group by S_wec_id) ";
			prepStmt = connection.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();

			if (rs.next()) {
				cap = rs.getDouble(1) / 1000;
			}
			return cap;

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, connection);
		}
		return cap;
	}

	public static List<String> getWecTypeCapacityBasedOnSiteId(String siteId) {
		logger.debug("enter");
		List<String> wecTypeCapacity = new ArrayList<String>();
		Connection connection = null;

		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			connection = wcareConnector.getConnectionFromPool();
			prepStmt = connection
					.prepareStatement("select S_WEC_TYPE||'/'||N_WEC_CAPACITY AS WECCAPACITY "
							+ "From Customer_Meta_Data "
							+ "Where S_Site_Id = ? "
							+ "group by S_WEC_TYPE||'/'||N_WEC_CAPACITY ");
			prepStmt.setObject(1, siteId);
            DaoUtility.displayQueryWithParameter(46, "select S_WEC_TYPE||'/'||N_WEC_CAPACITY AS WECCAPACITY "
							+ "From Customer_Meta_Data "
							+ "Where S_Site_Id = ? "
							+ "group by S_WEC_TYPE||'/'||N_WEC_CAPACITY ", siteId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(46, rs);
				wecTypeCapacity.add(rs.getString("WECCAPACITY"));
			}
			return wecTypeCapacity;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			  DaoUtility.releaseResources(prepStmt, rs, connection);
		}
		return wecTypeCapacity;
	}

	public static String getWECDescriptionBasedOnWECId(String wecId) {

		Connection conn = null;
		PreparedStatement prepare = null;
		ResultSet resultSet = null;
		String wecDescription = "";
		try {
			conn = wcareConnector.getConnectionFromPool();
			prepare = conn.prepareStatement("Select * From Tbl_Wec_master "
					+ "where S_wec_id = ? ");
			prepare.setString(1, wecId);
			DaoUtility.displayQueryWithParameter(33, "Select * From Tbl_Wec_master "
					+ "where S_wec_id = ? ", wecId);
			resultSet = prepare.executeQuery();
			while (resultSet.next()) {
				DaoUtility.getRowCount(33, resultSet);
				wecDescription = resultSet.getString("S_WECSHORT_DESCR");
			}
			return wecDescription;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepare, resultSet, conn);
		}
		return wecDescription;
	}

	public static Set<String> getWecIdsHavingLoadRestrictionForOneDayBasedOnGivenWecIds(
			String readingDate, Set<String> wecIds) throws SQLException {

		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIdsHavingLoadRestriction = new HashSet<String>();
		List<String> wecIdsList = new ArrayList<String>(wecIds);
		try {
			conn = wcareConnector.getConnectionFromPool();
			for (List<String> wecIdsSplit : GlobalUtils.splitArrayList(
					wecIdsList, 800)) {
				sqlQuery = "Select S_wec_id " + "from meta_data_gr "
						+ "Where S_Wec_Id In "
						+ GlobalUtils.getStringFromArrayForQuery(wecIdsSplit)
						+ "And D_Reading_Date = ? "
						+ "and Meta_Data_GR.Ebloadrst <> 0 ";

				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, readingDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					wecIdsHavingLoadRestriction.add(rs.getString("S_WEC_ID"));
				}
			}
			return wecIdsHavingLoadRestriction;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public static double getAverageCostOfGivenWecIds(Collection<String> wecIds)
			throws SQLException {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		if (wecIds.size() == 0) {
			return 0;
		}
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<String> wecIdsList = new ArrayList<String>(wecIds);
		double cost = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			for (List<String> wecIdsSplit : GlobalUtils.splitArrayList(
					wecIdsList, 800)) {
				sqlQuery = "select round(avg(N_Cost_Per_Unit),2) as Cost "
						+ "from tbl_wec_master " + "where S_wec_id in "
						+ GlobalUtils.getStringFromArrayForQuery(wecIdsSplit);

				prepStmt = conn.prepareStatement(sqlQuery);
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					cost += rs.getDouble("COST");
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			}
			return cost;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public Map<String, Map<WecLocationData, String>> getActiveWecLocationData()
			throws SQLException {
		Map<String, Map<WecLocationData, String>> wecIdWECDataMapping = new HashMap<String, Map<WecLocationData, String>>();
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
		try {
			connection = wcareConnector.getConnectionFromPool();
			query = "Select S_wec_id, S_Customer_Name ,S_state_name, S_Area_Name, S_Site_Name, S_Wecshort_Descr "
					+ "from customer_meta_data " + "where S_Status in ('1') ";

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
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
		} finally {
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}

		return wecIdWECDataMapping;
	}

	public static Set<String> getWecIdsHavingSpecialShutdownForOneDayBasedOnGivenWecIds(
			String readingDate, Set<String> wecIds) throws SQLException {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'

		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIdsHavingSpecialShutdowm = new HashSet<String>();
		List<String> wecIdsList = new ArrayList<String>(wecIds);
		try {
			conn = wcareConnector.getConnectionFromPool();
			for (List<String> wecIdsSplit : GlobalUtils.splitArrayList(
					wecIdsList, 800)) {
				sqlQuery = "Select S_wec_id " + "from meta_data_GR "
						+ "Where S_Wec_Id In "
						+ GlobalUtils.getStringFromArrayForQuery(wecIdsSplit)
						+ "And D_Reading_Date = ? "
						+ "and Meta_Data_GR.Wecloadrst <> 0 ";

				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, readingDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					wecIdsHavingSpecialShutdowm.add(rs.getString("S_WEC_ID"));
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			}
			return wecIdsHavingSpecialShutdowm;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	private static void merge(Map<String, Set<String>> map, String key,
			String value) {
		if (map.containsKey(key)) {
			Set<String> presentWecIds = map.get(key);
			presentWecIds.add(value);
			map.put(key, presentWecIds);
		} else {
			Set<String> newWecIds = new HashSet<String>();
			newWecIds.add(value);
			map.put(key, newWecIds);
		}
	}

	private static <T extends Collection<String>> void instantiate(
			Class<T> clazz) {
		try {
			Collection<String> col = clazz.newInstance();
			col.add("1");
			col.add("1");
			col.add("2");
			col.add("4");
			col.add("9");
			col.add("1");
			col.add("5");
			for (String string : col) {
				System.out.println(string);
			}
		} catch (InstantiationException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (IllegalAccessException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	/*public static void main(String[] args) {
		try {
			new WecMasterDao().getWecMasterVo(getActiveWecIds());
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}*/
	
	public double getTotalCapacity(Collection<IWecMasterVo> wecs) {
		double totalWecCapacity = 0;
		for(IWecMasterVo wec: wecs)
			totalWecCapacity += wec.getCapacity();
		return totalWecCapacity;
	}

	public List<IWecMasterVo> getActiveWecs(Set<String> customerIds)
			throws SQLException {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		try{
			for(String customerId: customerIds){
				for(IWecMasterVo wec: Graph.getInstance().getCustomersM().get(customerId).getWecs())
					if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()))
						wecs.add(wec);
			}
			return wecs;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
		
	}
	
	public Set<String> getWecIdsBasedOnCustomerIds(Set<String> customerIds) throws SQLException {
		Set<String> wecIds = new LinkedHashSet<String>();
		
		/*try{
			for(String customerId: customerIds){
				for(IWecMasterVo wec: G.getCustomersM().get(customerId).getWecs())
					if(wec.getStatus().equals("1"))
						wecIds.add(wec.getId());
			}
			return wecIds;
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;*/
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_wec_id " + "from customer_meta_data "
					+ "where S_status in ('1') " + "and S_customer_id in "
					+ GlobalUtils.getStringFromArrayForQuery(customerIds);

			prepStmt = conn.prepareStatement(query);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public Map<String, WecMasterVo> getWecMasterVo() throws SQLException {
		return getWecMasterVo(getActiveWecIds());
	}

	public static Set<String> getActiveWecIds() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIds = new LinkedHashSet<String>();

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_wec_id " + 
					"from customer_meta_data " + 
					"where S_status in ('1')" ;
//					"and S_wec_id < '0809001483' ";

			prepStmt = conn.prepareStatement(query);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public Map<String, WecMasterVo> getWecMasterVo(Set<String> wecIds)
			throws SQLException {
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

			for (List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)) {
				query = "Select S_state_name, S_state_id, S_area_name, S_area_id, S_site_name, S_site_id, s_ebshort_descr, S_eb_id, s_wecshort_descr, s_wec_id "
						+ "from customer_meta_data "
						+ "where S_status in ('1')"
						+ "and S_wec_id in "
						+ GlobalUtils.getStringFromArrayForQuery(splitWecIds);

				prepStmt = conn.prepareStatement(query);

				rs = prepStmt.executeQuery();
				System.out.println("Done");
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

					updateStateMasterVo(stateMasterVos, stateId, stateName);
					updateAreaMasterVo(areaMasterVos, stateMasterVos, areaId, areaName, stateId);
					updateSiteMasterVo(siteMasterVos, areaMasterVos, siteId, siteName, areaId);
					updateEbMasterVo(ebMasterVos, siteMasterVos, ebId, ebName, siteId);
					updateWecMasterVo(wecMasterVos, ebMasterVos, wecId, wecName, ebId);
				}
			}
			wecMasterVos = GlobalUtils.sortByValue(wecMasterVos, WecMasterVoComparator.SASEW_M);
	    	
			// stateMasterVos.get("1000000067").setName("T");
			// System.out.println(wecMasterVos.get("0809000647").getStateName());
			// System.out.println(wecMasterVos.get("0809000648").getStateName());

			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecMasterVos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}

	}

	private void updateWecMasterVo(Map<String, WecMasterVo> wecMasterVos,
			Map<String, EbMasterVo> ebMasterVos, String wecId, String wecName,
			String ebId) {
		if (!wecMasterVos.containsKey(wecId)) {
			WecMasterVo wecMasterVo = new WecMasterVo(wecId);
			wecMasterVo.setName(wecName);
			wecMasterVo.setEb(ebMasterVos.get(ebId));

			wecMasterVos.put(wecId, wecMasterVo);
		}
	}

	private void updateEbMasterVo(Map<String, EbMasterVo> ebMasterVos,
			Map<String, SiteMasterVo> siteMasterVos, String ebId,
			String ebName, String siteId) {
		if (!ebMasterVos.containsKey(ebId)) {
			EbMasterVo ebMasterVo = new EbMasterVo(ebId);
			ebMasterVo.setName(ebName);
			ebMasterVo.setSite(siteMasterVos.get(siteId));

			ebMasterVos.put(ebId, ebMasterVo);
		}
	}

	private void updateSiteMasterVo(Map<String, SiteMasterVo> siteMasterVos,
			Map<String, AreaMasterVo> areaMasterVos, String siteId,
			String siteName, String areaId) {
		if (!siteMasterVos.containsKey(siteId)) {
			SiteMasterVo siteMasterVo = new SiteMasterVo(siteId);
			siteMasterVo.setName(siteName);
			siteMasterVo.setArea(areaMasterVos.get(areaId));

			siteMasterVos.put(siteId, siteMasterVo);
		}
	}

	private void updateAreaMasterVo(Map<String, AreaMasterVo> areaMasterVos,
			Map<String, StateMasterVo> stateMasterVos, String areaId,
			String areaName, String stateId) {
		if (!areaMasterVos.containsKey(areaId)) {
			AreaMasterVo areaMasterVo = new AreaMasterVo(areaId);
			areaMasterVo.setName(areaName);
			areaMasterVo.setState(stateMasterVos.get(stateId));

			areaMasterVos.put(areaId, areaMasterVo);
		}
	}

	private void updateStateMasterVo(Map<String, StateMasterVo> stateMasterVos,
			String stateId, String stateName) {
		if (!stateMasterVos.containsKey(stateId)) {
			StateMasterVo stateMasterVo = new StateMasterVo(stateId);
			stateMasterVo.setName(stateName);

			stateMasterVos.put(stateId, stateMasterVo);
		}
	}
	
	public List<WecMasterVo> getWecMasterVos(CustomerMasterVo customerMasterVo) throws SQLException{
		
		List<WecMasterVo> wecMasterVos = new ArrayList<WecMasterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = 
				"Select S_wec_id " + 
				"from tbl_wec_master " + 
				"where S_customer_id = ? " + 
				"and s_status in ('1', '9') " ; 

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, customerMasterVo.getId());
		
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()){
				wecMasterVos.add(new WecMasterVo(resultSet.getString("S_wec_id")));
			}
			populateGraph(wecMasterVos);
		
			return wecMasterVos;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}

	public List<WecMasterVo> populateGraph(List<WecMasterVo> wecMasterVos) throws SQLException {

		Map<String, EbMasterVo> ebMasterVos = new HashMap<String, EbMasterVo>();
		Map<String, SiteMasterVo> siteMasterVos = new HashMap<String, SiteMasterVo>();
		Map<String, AreaMasterVo> areaMasterVos = new HashMap<String, AreaMasterVo>();
		Map<String, StateMasterVo> stateMasterVos = new HashMap<String, StateMasterVo>();

		Map<String, WecMasterVo> mappedWecMasterVos = Maps.uniqueIndex(
				wecMasterVos, new Function<WecMasterVo, String>() {

					public String apply(WecMasterVo from) {
						return from.getId();
					}
					
				});
		
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
		String plantNo = null;
		String locationNo = null;
		String serialNo = null;
		
		List<String> wecIds = WecMasterUtility.getWecIds(wecMasterVos);
		
		try {
			conn = wcareConnector.getConnectionFromPool();

			for (List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)) {
				query = 
						"Select metadata.S_state_name, metadata.S_state_id, metadata.S_area_name, metadata.S_area_id,  " + 
						"            metadata.S_site_name, metadata.S_site_id, metadata.s_ebshort_descr, metadata.S_eb_id,  " + 
						"            metadata.s_wecshort_descr, metadata.s_wec_id, metadata.s_technical_no, " + 
						"            plantmas.s_location_no, plantmas.s_plant_no " + 
						"from customer_meta_data metadata, scadadw.tbl_plant_master plantmas " + 
						"where metadata.s_technical_no = plantmas.s_serial_no " + 
						"and metadata.S_status in ('1') " +
						"and metadata.S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(splitWecIds);

				prepStmt = conn.prepareStatement(query);

				rs = prepStmt.executeQuery();
				logger.debug("Result Set Acquired.");
				
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
					serialNo = rs.getString("S_technical_no");
					plantNo = rs.getString("S_plant_no");
					locationNo = rs.getString("S_location_no");

					if (!stateMasterVos.containsKey(stateId)) {
						StateMasterVo stateMasterVo = new StateMasterVo(stateId);
						stateMasterVo.setName(stateName);

						stateMasterVos.put(stateId, stateMasterVo);
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
					
					PlantMasterVo plantMasterVo = new PlantMasterVo(serialNo);
					plantMasterVo.setPlantNo(plantNo);
					plantMasterVo.setLocationNo(locationNo);
					
					WecMasterVo wecMasterVo = mappedWecMasterVos.get(wecId);
					wecMasterVo.setName(wecName);
					wecMasterVo.setTechnicalNo(serialNo);
					
					wecMasterVo.setEb(ebMasterVos.get(ebId));
					wecMasterVo.setPlant(plantMasterVo);

					wecMasterVos.add(wecMasterVo);
					
				}
			}
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecMasterVos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}

	
	}

	public CustomerMasterVo get(CustomerMasterVo customer) throws SQLException{

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String wecId = null;
		String wecName = null;
		
		try {
			conn = wcareConnector.getConnectionFromPool();

			
			query = 
					"Select S_wec_id, s_wecshort_descr " + 
					"from tbl_wec_master " + 
					"where S_customer_id = ? " + 
					"and s_status in ('1', '9')  " ; 

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, customer.getId());
			
			rs = prepStmt.executeQuery();
			logger.debug("Done");
			
			while (rs.next()) {
				
				wecId = rs.getString("S_WEC_ID");
				wecName = rs.getString("S_Wecshort_descr");

				WecMasterVo wecMasterVo = new WecMasterVo(wecId);
				wecMasterVo.setName(wecName);
				wecMasterVo.setCustomer(customer);
			}
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return customer;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public static Set<String> getWecIdsBasedOnCustomerState(String customerId,
			String stateId) throws SQLException {
		return getWecIdsBasedOnStateIdCustomerIds(stateId, new HashSet<String>(Arrays.asList(customerId)));
	}

	public static Set<String> getWecIdsBasedOnCustomerStateArea(String customerId, String stateId, String areaId) throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIds = new LinkedHashSet<String>();

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_wec_id " + 
					"from customer_meta_data "
					+ "where S_customer_id = ? " + "and S_state_id = ? "
					+ "and S_area_id = ? "
					+ "order by S_customer_name, S_State_name, S_area_name ";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, customerId);
			prepStmt.setObject(2, stateId);
			prepStmt.setObject(3, areaId);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
				
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public static Set<String> getWecIdsBasedOnCustomerWecType(String customerId,
			String wecType) throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIds = new LinkedHashSet<String>();

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "Select S_wec_id " + 
					"from customer_meta_data "
					+ "where S_customer_id = ? " + "and S_wec_type = ? "
					+ "order by S_customer_name, S_wec_type ";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, customerId);
			prepStmt.setObject(2, wecType);
			

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecIds.add(rs.getString("S_WEC_ID"));
				
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return wecIds;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public Map<IStateMasterVo, Set<IWecMasterVo>> getWecsStateWise(Collection<IWecMasterVo> wecs) {
		Map<IStateMasterVo, Set<IWecMasterVo>> wecsStatewise = new HashMap<IStateMasterVo, Set<IWecMasterVo>>();
		for(IWecMasterVo wec: wecs){
			IStateMasterVo state = wec.getState();
			if(wecsStatewise.containsKey(state)){
				Set<IWecMasterVo> wecsP = wecsStatewise.get(state);
				wecsP.add(wec);
				wecsStatewise.put(state, wecsP);
			} else {
				Set<IWecMasterVo> wecsP = new HashSet<IWecMasterVo>();
				wecsP.add(wec);
				wecsStatewise.put(state, wecsP);
			}
		}
		return wecsStatewise;
	}
	
	public IWecMasterVo get(String id){
		return Graph.getInstance().getWecsM().get(id);
	}

	private List<IWecMasterVo> getAll(IEbMasterVo eb) {
		return Graph.getInstance().getEbsM().get(eb.getId()).getWecs();
	}
	
	public List<IWecMasterVo> getAll(ISiteMasterVo site){
		return site.getWecs();
	}
	
	public List<IWecMasterVo> getAll(IStateMasterVo state){
		return state.getWecs();
	}
	
//	private Collection<IWecMasterVo> getAll(ICustomerMasterVo customer) {
//		return customer.getWecs();
//	}
	
	public List<IWecMasterVo> getAll(ICustomerMasterVo customer){
		return Graph.getInstance().getCustomersM().get(customer.getId()).getWecs();
	}
	
	private List<IWecMasterVo> getAll(String wecType) {
		List<IWecMasterVo> wecsByType = new ArrayList<IWecMasterVo>();
		List<IWecMasterVo> allWecs = getAll();
		for(IWecMasterVo wec: allWecs)
			if(wecType.equals(wec.getType().trim())) 
				wecsByType.add(wec);
		
		return wecsByType;
	}
	
	private List<IWecMasterVo> getActiveWecs(List<IWecMasterVo> wecs){
		List<IWecMasterVo> activeWecs = new ArrayList<IWecMasterVo>();
		for(IWecMasterVo wec: wecs)
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode())) activeWecs.add(wec);
		
		return activeWecs;
	}
	
	public List<IWecMasterVo> getActive(IEbMasterVo eb) {
		return getActiveWecs(getAll(eb));
	}
	
	public List<IWecMasterVo> getActive(ISiteMasterVo site) {
		return getActiveWecs(getAll(site));
	}
	
	public List<IWecMasterVo> getActive(ICustomerMasterVo customer){
		return getActiveWecs(getAll(customer));
	}
	
	public List<IWecMasterVo> getActive(IEbMasterVo eb, ICustomerMasterVo customer) {
		List<IWecMasterVo> wecsByCustomer = getAll(customer);
		List<IWecMasterVo> wecsByEb = getAll(eb);
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(IWecMasterVo wec: wecsByCustomer){
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wecsByEb.contains(wec))
				wecs.add(wec);
		}
		return wecs;
	}
	
	public List<IWecMasterVo> getActive(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer) {
		List<IWecMasterVo> wecsBySite = getAll(site);
		List<IWecMasterVo> wecsByState = getAll(state);
		List<IWecMasterVo> wecsByCustomer = getAll(customer);
		
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(IWecMasterVo wec: wecsBySite){
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wecsByState.contains(wec) && wecsByCustomer.contains(wec))
				wecs.add(wec);
		}
		
		return wecs;
	}
	
	//Will Be in Trial if any WEC whose commission date is less than 30 days
	public boolean isTrial(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer) throws ParseException {

		List<IWecMasterVo> wecs = getActive(site, state, customer);
		String commissionDate = null;
		
		for(IWecMasterVo wec: wecs){
			commissionDate = wec.getCommissionDate();
			if(commissionDate != null){
				if(DateUtility.compareGivenDateWithTodayInTermsOfDays(commissionDate, "yyyy-MM-dd hh:mm:ss") >= -30) return true;
			}
		}
		return false;
	}
	
	public boolean isTrial(IWecMasterVo wec) throws ParseException{
		String commissionDate = wec.getCommissionDate();
		return (commissionDate != null && DateUtility.compareGivenDateWithTodayInTermsOfDays(commissionDate, "yyyy-MM-dd hh:mm:ss") >= -30);
	}

	public List<IWecMasterVo> getDisplayActive(IEbMasterVo eb, ICustomerMasterVo customer) {
		List<IWecMasterVo> wecsByCustomer = getAll(customer);
		List<IWecMasterVo> wecsByEb = getAll(eb);
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(IWecMasterVo wec: wecsByCustomer)
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wec.getShow().equals(Display.SHOW.getCode()) && wecsByEb.contains(wec)) wecs.add(wec);
		
		return wecs;
	}
	
	private List<IWecMasterVo> getDisplayActiveWecs(List<IWecMasterVo> wecs){
		List<IWecMasterVo> activeWecs = new ArrayList<IWecMasterVo>();
		for(IWecMasterVo wec: wecs)
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wec.getShow().equals(Display.SHOW.getCode())) activeWecs.add(wec);
		
		return activeWecs;
	}
	
	public List<IWecMasterVo> getDisplayActive(ICustomerMasterVo customer, IStateMasterVo state) {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		List<IWecMasterVo> wecsByState = getAll(state);
		List<IWecMasterVo> getWecsByCustomer = getAll(customer);

		for(IWecMasterVo wec: getWecsByCustomer)
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wec.getShow().equals(Display.SHOW.getCode()) && wecsByState.contains(wec))
				wecs.add(wec);
		
		return wecs;
	}

	public List<IWecMasterVo> getDisplayActive(ICustomerMasterVo customer) {
		return getDisplayActiveWecs(getAll(customer));
	}

	public List<IWecMasterVo> getDisplayActive(ISiteMasterVo site) {
		return getDisplayActiveWecs(getAll(site));
	}

	public List<IWecMasterVo> getDisplayActive(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer) {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		List<IWecMasterVo> wecsBySite = getAll(site);
		List<IWecMasterVo> wecsByState = getAll(state);
		List<IWecMasterVo> getWecsByCustomer = getAll(customer);

		for(IWecMasterVo wec: getWecsByCustomer)
			if(wec.getStatus().equals(WorkingStatus.ACTIVE.getCode()) && wec.getShow().equals(Display.SHOW.getCode()) && wecsByState.contains(wec) && wecsBySite.contains(wec))
				wecs.add(wec);
		
		return wecs;
	}

	public List<IWecMasterVo> getActive(String wecType) {
		List<IWecMasterVo> wecsByType = new ArrayList<IWecMasterVo>();
		List<IWecMasterVo> allWecs = getAll();
		for(IWecMasterVo wec: allWecs)
			if(WorkingStatus.ACTIVE.getCode().equals(wec.getStatus()) && wecType.equals(wec.getType().trim())) 
				wecsByType.add(wec);
		
		return wecsByType;
	}

	private List<IWecMasterVo> getAll() {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		Map<String, IWecMasterVo> wecsM = Graph.getInstance().getWecsM(); 
		
		for(String wecId: wecsM.keySet())
			wecs.add(wecsM.get(wecId));
		
		return wecs;
	}
	
	public static void main(String[] args) {
		logger.debug(WecMasterDao.getInstance().getActiveTransferred("E-53").size());
	}
	
	private List<IWecMasterVo> getActiveTransferredWecs(List<IWecMasterVo> wecs){
		List<IWecMasterVo> activeTransferredWecs = new ArrayList<IWecMasterVo>();
		String workingStatus = null;
		for(IWecMasterVo wec: wecs){
			workingStatus = wec.getStatus();
			if(workingStatus.equals(WorkingStatus.ACTIVE.getCode()) || workingStatus.equals(WorkingStatus.TRANSFER.getCode())) activeTransferredWecs.add(wec);
		}
		
		return activeTransferredWecs;
	}

	public List<IWecMasterVo> getActiveTransferred(String wecType) {
		List<IWecMasterVo> wecsByType = getAll(wecType);
		return getActiveTransferredWecs(wecsByType);
	}

	public List<IWecMasterVo> getActiveTransferred(IEbMasterVo eb) {
		List<IWecMasterVo> wecsByEb = getAll(eb);
		return getActiveTransferredWecs(wecsByEb);
	}

	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state) {
		List<IWecMasterVo> wecsByState = state.getWecs();
		return getActiveTransferredWecs(wecsByState);
	}

	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, ISiteMasterVo site) {
		if(!site.getState().equals(state)) return new ArrayList<IWecMasterVo>();
		List<IWecMasterVo> wecsBySite = getAll(site);
		return getActiveTransferredWecs(wecsBySite);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, String wecType) {
		List<IWecMasterVo> wecsByType = getAll(wecType);
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(IWecMasterVo wec: wecsByType)
			if(wec.getState().equals(state)) wecs.add(wec);
		
		return getActiveTransferredWecs(wecs);
		
	}

	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, ISiteMasterVo site, String wecType) {
		if(!site.getState().equals(state)) return new ArrayList<IWecMasterVo>(); 
		List<IWecMasterVo> wecsByType = getAll(wecType);
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(IWecMasterVo wec: wecsByType)
			if(wec.getSite().equals(site)) wecs.add(wec);
		
		return getActiveTransferredWecs(wecs);
	}

	public double getCapacity(List<IWecMasterVo> wecs) {
		double capacity = 0;
		for(IWecMasterVo wec: wecs) capacity += wec.getCapacity();
		return capacity;
	}

	public List<IWecMasterVo> getAll(List<ICustomerMasterVo> customers) {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		
		for(ICustomerMasterVo customer: customers){
			wecs.addAll( getAll(customer) );
		}
		
		return wecs;
	}

	public boolean isTrial(List<IWecMasterVo> wecs) {
		String commissionDate = null;
		
		for(IWecMasterVo wec: wecs){
			commissionDate = wec.getCommissionDate();
			if(commissionDate != null){
				if(DateUtility.compareGivenDateWithTodayInTermsOfDays(commissionDate, "yyyy-MM-dd hh:mm:ss") >= -30) return true;
			}
		}
		return false;
	}
	
	public boolean transfer(TransferWecVo vo) throws SQLException {
		Map<String, Object> m = new TreeMap<String, Object>();
		String fromWecId = vo.getFromWecId();
		
		com.enercon.model.graph.WecMasterVo fromWec = (com.enercon.model.graph.WecMasterVo) get(fromWecId);
		
		fromWec.setStatus("9");
		m.put("S_STATUS", "9");
		
		fromWec.setTransferWecId(vo.getToWecId());
		m.put("S_WEC_ID_TO", vo.getToWecId());
		
		fromWec.setTransferDate(vo.getTransferDateOracleFormat());
		m.put("D_TRANSFER_DATE", vo.getTransferDateOracleFormat());
		
		fromWec.setRemark(vo.getTransferRemark());
		m.put("S_REMARKS", vo.getTransferRemark());
		
		return partialUpdate(fromWec, m);
	}

	private boolean partialUpdate(IWecMasterVo wec, Map<String, Object> m) throws SQLException {
		logger.debug(m);
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = m.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_WEC_MASTER SET ");
			for(String column: m.keySet()){
				query.append(column + " = ?, ");
			}
			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("WHERE S_WEC_ID = ? ");
			logger.debug(wec.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug(query);
			for(String column: m.keySet()){
				index++;
				prepStmt.setObject(index, m.get(column));
			}
			prepStmt.setObject(paramterCount + 1, wec.getId());
			
			return (prepStmt.executeUpdate() == 1);
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	public void addWecMasterListener(WecMasterListener l){
		listeners.add(l);
	}
	
	public void fireWecMasterEvent(WecMasterEvent event){
		for(WecMasterListener l: listeners)
			l.handler(event);
	}
	
	public boolean create(IWecMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = " Insert into TBL_WEC_MASTER( S_WEC_ID, S_WECSHORT_DESCR, S_CUSTOMER_ID, S_EB_ID, " +
												   " S_FOUND_LOC, S_WEC_TYPE, N_MULTI_FACTOR, N_WEC_CAPACITY, "+
												   " S_CREATED_BY, S_LAST_MODIFIED_BY, D_COMMISION_DATE, S_STATUS, " +
												   " N_GEN_COMM, N_MAC_AVA, N_EXT_AVA, N_INT_AVA, " +
												   " D_START_DATE, D_END_DATE, S_FORMULA_NO, S_SHOW, " +
												   " N_COST_PER_UNIT, S_TECHNICAL_NO, S_GUARANTEE_TYPE, S_CUSTOMER_TYPE, "+
												   " S_SCADA_FLAG, S_FEEDER_ID, N_PES_SCADA_STATUS )" +
										   " values( ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ? , " +
										   		   " ? , ? , ? , ) ";
					   						         			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getCustomerId());
			prepStmt.setObject(4, vo.getEbId());

			prepStmt.setObject(5, vo.getFoundationNo());
			prepStmt.setObject(6, vo.getType());
			prepStmt.setObject(7, vo.getMultiFactor());
			prepStmt.setObject(8, vo.getCapacity());
			
			prepStmt.setObject(9, vo.getCreatedBy());
			prepStmt.setObject(10, vo.getModifiedBy());
			prepStmt.setObject(11, vo.getCommissionDate());
			prepStmt.setObject(12, vo.getStatus());
			
			prepStmt.setObject(13, vo.getGenComm());
			prepStmt.setObject(14, vo.getMachineAvailability());
			prepStmt.setObject(15, vo.getExtGridAvailability());
			prepStmt.setObject(16, vo.getIntGridAvailability());
			
			prepStmt.setObject(17, vo.getStartDate());
			prepStmt.setObject(18, vo.getEndDate());
			prepStmt.setObject(19, vo.getFormula());
			prepStmt.setObject(20, vo.getShow());
			
			prepStmt.setObject(21, vo.getCostPerUnit());
			prepStmt.setObject(22, vo.getTechnicalNo());
			prepStmt.setObject(23, vo.getGuaranteeType());
			prepStmt.setObject(24, vo.getCustomerType());
			
			prepStmt.setObject(25, vo.getScadaStatus());
			prepStmt.setObject(26, vo.getFeederId());
			prepStmt.setObject(27, vo.getScadaStatus());
			
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(IWecMasterVo vo) throws SQLException {
    	
    	Map<String, Object> m = new TreeMap<String, Object>();
    	
    	m.put("S_WECSHORT_DESCR", vo.getName());
    	m.put("S_CUSTOMER_ID", vo.getCustomerId());
    	m.put("S_EB_ID", vo.getEbId());
    	m.put("S_WEC_TYPE", vo.getType());
    	
    	m.put("S_FOUND_LOC", vo.getFoundationNo());
    	m.put("N_MULTI_FACTOR", vo.getMultiFactor());
    	m.put("N_WEC_CAPACITY", vo.getCapacity());	
    	m.put("D_Last_Modified_Date", new java.sql.Timestamp(new Date().getTime()));
    	
    	m.put("S_Last_Modified_By", vo.getModifiedBy());		
    	m.put("D_COMMISION_DATE", vo.getCommissionDate());
    	m.put("S_STATUS", vo.getStatus());
    	m.put("N_GEN_COMM", vo.getGenComm());
    	
    	m.put("N_MAC_AVA", vo.getMachineAvailability());
    	m.put("N_EXT_AVA", vo.getExtGridAvailability());
    	m.put("N_INT_AVA", vo.getIntGridAvailability());
    	m.put("D_START_DATE", vo.getStartDate());
    	
    	m.put("D_END_DATE", vo.getEndDate());
    	m.put("S_FORMULA_NO", vo.getFormula());
    	m.put("S_SHOW", vo.getShow());
    	m.put("N_COST_PER_UNIT", vo.getCostPerUnit());
    	
    	m.put("S_TECHNICAL_NO", vo.getTechnicalNo());
    	m.put("S_GUARANTEE_TYPE", vo.getGuaranteeType());
    	m.put("S_CUSTOMER_TYPE", vo.getCustomerType());
    	m.put("S_SCADA_FLAG", vo.getScadaStatus());
    	
    	m.put("S_FEEDER_ID", vo.getFeederId());
    	m.put("N_PES_SCADA_STATUS", vo.getScadaStatus());
    	 	
    	return partialUpdate(vo, m); 	
    }
	
	public boolean check(IWecMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  "select count(*) as COUNT from TBL_WEC_MASTER where S_WECSHORT_DESCR = ? " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getName());

			rs = prepStmt.executeQuery();
			while (rs.next()) {
			 if( rs.getInt("COUNT") == 1)
				 return true;
			}
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
}

