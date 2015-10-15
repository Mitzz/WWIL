package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.WecLocationData;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.master.AreaMasterVo;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.EbMasterVo;
import com.enercon.model.master.PlantMasterVo;
import com.enercon.model.master.SiteMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.master.WecMasterVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class WecMasterDao implements WcareConnector {
	
	private static Logger logger = Logger.getLogger(WecMasterDao.class);

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
			return wecIds;
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
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
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
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
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return cap;
	}

	public static List<String> getWecTypeCapacityBasedOnSiteId(String siteId) {
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

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecTypeCapacity.add(rs.getString("WECCAPACITY"));
			}
			return wecTypeCapacity;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
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
			resultSet = prepare.executeQuery();
			while (resultSet.next()) {
				wecDescription = resultSet.getString("S_WECSHORT_DESCR");
			}
			return wecDescription;
		} catch (Exception e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} finally {
			try {
				if (prepare != null) {
					prepare.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
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
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			try {
				if (preparedStatement != null) {
					preparedStatement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
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
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			new WecMasterDao().getWecMasterVo(getActiveWecIds());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Set<String> getWecIdsBasedOnCustomerIds(Set<String> customerIds)
			throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		Set<String> wecIds = new LinkedHashSet<String>();

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
			wcareConnector.returnConnectionToPool(connection);
			try {
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
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
				logger.debug("Done");
				
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
}

