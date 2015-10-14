package com.enercon.ajax;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.ajax.interfaces.AjaxVo;
import com.enercon.dao.IredaDAO;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.model.ireda.IredaProject;

public class AjaxDao {

	public String getWECIdNameBasedOnCustomerId(String customerId) {

		StringBuffer xmlData = new StringBuffer();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
			
		try{
			conn = conmanager.getConnection();
			sqlQuery = "Select S_wec_id, S_Wecshort_Descr " + 
					"From Customer_Meta_Data " + 
					"Where S_Customer_Id = ? " + 
					"order by S_Wecshort_Descr " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, customerId);	
			rs = prepStmt.executeQuery();
			xmlData.append("<xml>\n");
			while (rs.next()) {
				xmlData.append("\t<wec>\n");	
				xmlData.append("\t\t<wecId>" + rs.getString("S_WEC_ID") + "</wecId>\n");
				xmlData.append("\t\t<wecName>" + rs.getString("S_WECSHORT_DESCR") + "</wecName>\n");
				xmlData.append("\t</wec>\n");
			}
			xmlData.append("</xml>");
			////System.out.println(xmlData);
			return new String(xmlData);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return new String(xmlData);

	}

	public String getPlantNoBasedOnLocationNo(String locationNo) {

		StringBuffer xmlData = new StringBuffer();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
			
		try{
			conn = conmanager.getConnection();
			sqlQuery = "Select Plantmas.S_Plant_No, Plantmas.S_Plant_No || ' (' ||  Wecmas.S_Wecshort_Descr || ')' as S_Plant_No_WEC_Name " + 
					"From Scadadw.Tbl_Plant_Master Plantmas, Tbl_Wec_Master Wecmas " + 
					"Where Plantmas.S_Serial_No = Wecmas.S_Technical_No " + 
					"and S_Location_No = ? " + 
					"order by S_Plant_No " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, locationNo);	
			rs = prepStmt.executeQuery();
			xmlData.append("<xml>\n");
			while (rs.next()) {
				xmlData.append("\t<plantData>\n");	
				xmlData.append("\t\t<plantNo>" + rs.getString("S_PLANT_NO") + "</plantNo>\n");
				xmlData.append("\t\t<plantDescription>" + rs.getString("S_Plant_No_WEC_Name") + "</plantDescription>\n");
				xmlData.append("\t</plantData>\n");
			}
			xmlData.append("</xml>");
			////System.out.println(xmlData);
			return new String(xmlData);
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return new String(xmlData);

	}
	
	public static List<AjaxVo> getAjaxVo(String sqlQuery){
		return null;
	}

	public static List<AjaxVo> getAreaVoBasedOnCustomerIds(String customerIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_Area_ID, S_AREA_NAME " + 
					"from customer_meta_data " + 
					"where S_CUSTOMER_ID in (" + customerIds + ") " + 
					"group by S_Area_ID, S_AREA_NAME " + 
					"order by S_AREA_NAME " ; 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_AREA_ID"), rs.getString("S_AREA_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getAreaSelectVoBasedOnWecType(String wecType) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_Area_ID, S_AREA_NAME " + 
					"from customer_meta_data " + 
					"where S_WEC_TYPE in (" + wecType +") " + 
					"group by S_Area_ID, S_AREA_NAME " + 
					"order by S_AREA_NAME " ; 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_AREA_ID"), rs.getString("S_AREA_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getAreaSelectVoBasedOnStateIds(String stateIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_Area_ID, S_AREA_NAME " + 
					"from customer_meta_data " + 
					"where S_STATE_ID in (" + stateIds +") " + 
					"group by S_Area_ID, S_AREA_NAME " + 
					"order by S_AREA_NAME " ; 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_AREA_ID"), rs.getString("S_AREA_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getSiteSelectVoBasedOnCustomerIdsAreaIds(
			String customerIds, String areaIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_SITE_ID, S_SITE_NAME " + 
					"from customer_meta_data " + 
					"where S_AREA_ID in (" + areaIds + ") " + 
					"and S_CUSTOMER_ID in (" + customerIds + ") " + 
					"group by S_SITE_ID, S_SITE_NAME " + 
					"order by S_SITE_NAME " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_SITE_ID"), rs.getString("S_SITE_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getSiteSelectVoBasedOnWecTypeAreaIds(
			String wecType, String areaIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_SITE_ID, S_SITE_NAME " + 
					"from customer_meta_data " + 
					"where S_AREA_ID in (" + areaIds + ") " + 
					"and S_WEC_TYPE in (" + wecType + ") " + 
					"group by S_SITE_ID, S_SITE_NAME " + 
					"order by S_SITE_NAME " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_SITE_ID"), rs.getString("S_SITE_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getSiteSelectVoBasedOnStateIdsAreaIds(
			String stateIds, String areaIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_SITE_ID, S_SITE_NAME " + 
					"from customer_meta_data " + 
					"where S_AREA_ID in (" + areaIds + ") " + 
					"and S_STATE_ID in (" + stateIds + ") " + 
					"group by S_SITE_ID, S_SITE_NAME " + 
					"order by S_SITE_NAME " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_SITE_ID"), rs.getString("S_SITE_NAME")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getWecSelectVoBasedOnCustomerIdsAreaIdsSiteIds(
			String customerIds, String areaIds, String siteIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_wec_id, S_WECSHORT_DESCR " + 
					"from CUSTOMER_META_DATA " + 
					"where S_CUSTOMER_ID in (" + customerIds + ") " + 
					"and S_AREA_ID in (" + areaIds + ") " + 
					"and S_SITE_ID in (" + siteIds + ") " + 
					"GROUP by S_WEC_ID, S_WECSHORT_DESCR " + 
					"order by S_WECSHORT_DESCR " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_WEC_ID"), rs.getString("S_WECSHORT_DESCR")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getWecSelectVoBasedOnWecTypeAreaIdsSiteIds(
			String wecType, String areaIds, String siteIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_wec_id, S_WECSHORT_DESCR " + 
					"from CUSTOMER_META_DATA " + 
					"where S_WEC_TYPE in (" + wecType + ") " +  
					"and S_AREA_ID in (" + areaIds + ") " + 
					"and S_SITE_ID in (" + siteIds + ") " + 
					"GROUP by S_WEC_ID, S_WECSHORT_DESCR " + 
					"order by S_WECSHORT_DESCR " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_WEC_ID"), rs.getString("S_WECSHORT_DESCR")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return null;
	
	}

	public static List<AjaxVo> getWecSelectVoBasedOnStateIdsAreaIdsSiteIds(
			String stateIds, String areaIds, String siteIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"Select S_wec_id, S_WECSHORT_DESCR " + 
					"from CUSTOMER_META_DATA " + 
					"where S_STATE_ID in (" + stateIds + ") " +  
					"and S_AREA_ID in (" + areaIds + ") " + 
					"and S_SITE_ID in (" + siteIds + ") " + 
					"GROUP by S_WEC_ID, S_WECSHORT_DESCR " + 
					"order by S_WECSHORT_DESCR " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_WEC_ID"), rs.getString("S_WECSHORT_DESCR")));
			}
			return ajaxVo;
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return null;
	
	}
	
	public static List<AjaxVo> getWecSelectVoBasedOnLocationIds(
			String locationIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"SELECT S_WEC_ID,S_WEC_NAME "+
					"from ecare.TBL_WEC_MASTER WM "+
					"JOIN scadadw.TBL_PLANT_MASTER PM "+
					"ON(WM.S_WECSHORT_DESCR=PM.S_WEC_NAME) "+
					"JOIN scadadw.TBL_LOCATION_MASTER LM "+
					"ON(PM.S_LOCATION_NO=LM.S_LOCATION_NO) "+
					"WHERE S_LOCATION_NO IN(" +locationIds+ ") "+
					"GROUP BY S_WEC_ID,S_WEC_NAME " + 
					"ORDER BY S_WEC_NAME " ; 
 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_WEC_ID"), rs.getString("S_WEC_NAME")));
			}
			
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return ajaxVo;
	}
	
	public static List<AjaxVo> getWecSelectVoBasedOnProjectIds(String projectIds) {

		List<AjaxVo> ajaxVo = new ArrayList<AjaxVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			sqlQuery = 
					"SELECT WM.S_WEC_ID,WM.S_WECSHORT_DESCR "+
					"FROM TBL_WEC_PROJECT_MAPPING PRM,TBL_WEC_MASTER WM "+
					"WHERE PRM.S_WEC_ID=WM.S_WEC_ID "+
					"AND PRM.S_PROJECT_ID IN("+projectIds+")"+
					" ORDER BY WM.S_WECSHORT_DESCR";

 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ajaxVo.add(new SelectVo(rs.getString("S_WEC_ID"), rs.getString("S_WECSHORT_DESCR")));
			}
			
		}
		catch(Exception e){
			//System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return ajaxVo;
	}

	public static List<AjaxVo> getIredaVoListRetrieverBasedOnMonthYear(
			String projectNos, String month, String year) throws ParseException, SQLException {
		long start = System.currentTimeMillis();
		Set<String> iredaProjectNoList = null;
		String fromDate = DateUtility.getFirstDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy");
		String toDate = DateUtility.getLastDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy");
		List<AjaxVo> iredaProjectList = new ArrayList<AjaxVo>();
		IredaDAO iredaDAO = new IredaDAO();
		
		if(projectNos.equalsIgnoreCase("ALL")){
			iredaProjectNoList = iredaDAO.getAllIredaProjectNo();
		}
		else{
			iredaProjectNoList = new LinkedHashSet<String>();
			iredaProjectNoList.add(projectNos);
		}
		////System.out.println("Project No : " + iredaProjectNoList);
		
		for (String projectNo : iredaProjectNoList) {
			iredaProjectList.add(new IredaProject(projectNo).populateStateWiseTotalDataForManyDaysWithThreading(fromDate, toDate).populateGrandTotalForManyDays(fromDate, toDate));
		}
		////System.out.println(iredaProjectList);
		long end = System.currentTimeMillis();
		//System.out.println("Time Taken : " + (end - start));
		return iredaProjectList;
	}
	
	public static List<AjaxVo> getIredaVoListRetrieverBasedOnManyDatesWithThreading(
			String projectNos, String fromDate, String toDate) throws ParseException, SQLException {
		
		Set<String> iredaProjectNoList = null;
		IredaDAO iredaDAO = new IredaDAO();
		
		ListProducer<AjaxVo> listProducer = new ListProducer<AjaxVo>();
		List<ListWorker<AjaxVo>> workers = new ArrayList<ListWorker<AjaxVo>>();
		
		if(projectNos.equalsIgnoreCase("ALL")){
			iredaProjectNoList = iredaDAO.getAllIredaProjectNo();
		}
		else{
			iredaProjectNoList = new LinkedHashSet<String>();
			iredaProjectNoList.add(projectNos);
		}
		////System.out.println("Project No : " + iredaProjectNoList);
		
		for (String projectNo : iredaProjectNoList) {
			workers.add(new Worker(projectNo, fromDate, toDate));
		}
		////System.out.println(iredaProjectList);
		
		return listProducer.submit(workers);
	}
	
	public static List<AjaxVo> getIredaVoListRetrieverBasedOnMonthYearWithThreading(
			String projectNos, String month, String year) throws ParseException, SQLException {
		
		String fromDate = DateUtility.getFirstDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy");
		String toDate = DateUtility.getLastDateOfTheMonthBasedOnMonthYear(month, year, "dd-MMM-yyyy");
		
		return getIredaVoListRetrieverBasedOnManyDatesWithThreading(projectNos, fromDate, toDate);
	}
	
	public static List<AjaxVo> getIredaVoListRetrieverBasedOnFiscalYearWithThreading(
			String projectNos, String fiscalYear) throws ParseException, SQLException {
		
		int year = Integer.parseInt(fiscalYear);
		String fromDate = DateUtility.getFirstDateOfFiscalYear(year, "dd-MMM-yyyy");
		String toDate = DateUtility.getLastDateOfFiscalYear(year, "dd-MMM-yyyy");
		return getIredaVoListRetrieverBasedOnManyDatesWithThreading(projectNos, fromDate, toDate);
	}

	public static List<AjaxVo> getIredaVoListRetrieverBasedOnFiscalYear(
			String projectNos, String fiscalYear) throws ParseException, SQLException {
		long start = System.currentTimeMillis();
		
		Set<String> iredaProjectNoList = null;
		int year = Integer.parseInt(fiscalYear);
		String fromDate = DateUtility.getFirstDateOfFiscalYear(year, "dd-MMM-yyyy");
		String toDate = DateUtility.getLastDateOfFiscalYear(year, "dd-MMM-yyyy");
		List<AjaxVo> iredaProjectList = new ArrayList<AjaxVo>();
		IredaDAO iredaDAO = new IredaDAO();
		
		if(projectNos.equalsIgnoreCase("ALL")){
			iredaProjectNoList = iredaDAO.getAllIredaProjectNo();
		}
		else{
			iredaProjectNoList = new LinkedHashSet<String>();
			iredaProjectNoList.add(projectNos);
		}
		////System.out.println("Project No : " + iredaProjectNoList);
		
		for (String projectNo : iredaProjectNoList) {
			iredaProjectList.add(new IredaProject(projectNo).populateStateWiseTotalDataForManyDays(fromDate, toDate).populateGrandTotalForManyDays(fromDate, toDate));
		}
		////System.out.println(iredaProjectList);
		long end = System.currentTimeMillis();
		
		//System.out.println("AJAX : Yearly Without Threading : " + (end - start));
		
		return iredaProjectList;
	}

	public static List<AjaxVo> getIredaVoListRetrieverBasedOnDate(
			String projectNos, String date) throws SQLException {
		Set<String> iredaProjectNoList = null;
		
		List<AjaxVo> iredaProjectList = new ArrayList<AjaxVo>();
		IredaDAO iredaDAO = new IredaDAO();
		
		if(projectNos.equalsIgnoreCase("ALL")){
			iredaProjectNoList = iredaDAO.getAllIredaProjectNo();
		}
		else{
			iredaProjectNoList = new LinkedHashSet<String>();
			iredaProjectNoList.add(projectNos);
		}
		
		////System.out.println("Project No : " + iredaProjectNoList);
		for (String projectNo : iredaProjectNoList) {
			iredaProjectList.add(new IredaProject(projectNo).populateStateWiseTotalDataForManyDaysWithThreading(date, date).populateGrandTotalForManyDays(date, date));
		}
		////System.out.println(iredaProjectList);
		return iredaProjectList;
	}


}
interface ListWorker<T> extends Callable<T>{
	
}

class Worker implements ListWorker<AjaxVo>{
	private String iredaProjectNo;
	private String fromDate;
	private String toDate;

	public Worker(String iredaProjectNo, String fromDate, String toDate) {
		super();
		this.iredaProjectNo = iredaProjectNo;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public AjaxVo call() throws Exception {
		long start = System.currentTimeMillis();
		final IredaProject iredaProject = new IredaProject(iredaProjectNo);
		Thread t1 = null;
		Thread t2 = null;
		//System.out.println("Start Ireda No : " + iredaProjectNo);
		if(iredaProject.getStateCount() > 1){
			
			t1 = new Thread(new Runnable(){
				public void run(){
					try {
						iredaProject.populateStateWiseTotalDataForManyDaysWithThreading(fromDate, toDate);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			
			
			t2 = new Thread(new Runnable(){
				public void run(){
					try {
						iredaProject.populateGrandTotalForManyDays(fromDate, toDate);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			
			t1.start();t2.start();
			t1.join();t2.join();
		}
		else{
			iredaProject.populateStateWiseTotalDataForManyDaysWithThreading(fromDate, toDate).populateGrandTotalForManyDays(fromDate, toDate);
			
		}
		//System.out.println("End Ireda No : " + iredaProjectNo);
//		final IredaProject iredaProject = new IredaProject(iredaProjectNo).populateStateWiseTotalDataForManyDaysWithThreading(fromDate, toDate).populateGrandTotalForManyDays(fromDate, toDate);
		long end = System.currentTimeMillis();
		//System.out.println("Timmm : " + (end - start));
		return iredaProject;
	}
}

class ListProducer<T> {
	
	public List<T> submit(List<ListWorker<T>> worker){
		long start = System.currentTimeMillis();
		
		int noOfWorker = worker.size();
		ExecutorService factory = Executors.newFixedThreadPool(noOfWorker);
		
		List<T> returnList = new ArrayList<T>();
		List<Future<T>> futureList = new ArrayList<Future<T>>();
		
		for(ListWorker<T> w : worker){
			futureList.add(factory.submit(w));
		}
		
		factory.shutdown();
		
		try {
			factory.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		for (Future<T> element : futureList) {
			try {
				returnList.add(element.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		//System.out.println("Time Taken : " + (end - start));
		return returnList;
	}
}