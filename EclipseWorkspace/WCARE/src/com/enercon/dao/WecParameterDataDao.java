package com.enercon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.YearMonth;

import weblogic.utils.collections.TreeMap;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.parameter.wec.WecParameterVo;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;
import com.enercon.model.table.WecReadingVo;
import com.enercon.model.utility.WecDate;
import com.enercon.service.WecMasterService;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class WecParameterDataDao implements WcareConnector{
	final int WEC_SPLIT_CUT_OFF = 990;
	
	private WecParameterDataDao(){}
	
	private static class SingletonHelper{
		public final static WecParameterDataDao INSTANCE = new WecParameterDataDao();
	}
	
	public static WecParameterDataDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private final static Logger logger = Logger.getLogger(WecParameterDataDao.class);
	
	/*public <T extends Number> T getGeneration(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getGeneration(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}*/
	
	/*public <T extends Number> T getGeneration(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.GENERATION);
	}*/
	
	/*public <T extends Number> T getOperatingHour(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getOperatingHour(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}*/
	
	/*public <T extends Number> T getOperatingHour(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.OPERATING_HOUR);
	}*/
	
	/*public <T extends Number> T getMA(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getMA(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}*/
	
	/*public <T extends Number> T getMA(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.MA);
	}*/
	
	/*private <T extends Number> T getParameter(Set<String> wecIds, String fromDate, String toDate, Parameter parameter) throws SQLException, ParseException{

		check(fromDate, toDate);
		
//		System.out.println(fromDate + " <=> " + toDate);

		T parameterValue = null;
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			query = 
					"Select round(" + parameter.getOperationType() + "(" + parameter.getColumnName() + "), 2) as ParameterValue " +
//					"Select " + parameter.getOperationType() + "(" + parameter.getColumnName() + ") as ParameterValue " +
					"from tbl_reading_summary " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"and D_reading_date between ? and ? "; 

			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				parameterValue = (T) resultSet.getObject("ParameterValue");
			}
			
			return parameterValue;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);

		}
	}*/
	
	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, Month month, Year year, Set<Parameter> parameters) throws SQLException, ParseException{
		return getTotal(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year), parameters);
	}
	
	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, FiscalYear fiscalYear, Set<Parameter> parameters) throws SQLException, ParseException{
		return getTotal(wecIds, fiscalYear.getFirstDate(), fiscalYear.getLastDate(), parameters);
	}
	
	private boolean isDataPresent(Set<String> wecIds, String fromDate, String toDate) throws SQLException{

		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, 80)){
				query = 
						"Select count(1) as Count " +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? ";
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					return (resultSet.getInt("Count") > 0);
				}
			}
			return false;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) throws SQLException, ParseException{
		return getTotal(wecIds, fromDate, toDate, parameters, false);
	}
	
	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters, boolean checkData) throws SQLException, ParseException{

		check(fromDate, toDate);
		if(checkData && !isDataPresent(wecIds, fromDate, toDate)) return null;
		IWecParameterVo parameterVo = null;
		IWecParameterVo result = new WecParameterVo();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				
				parameterVo = new WecParameterVo();
				int noOfWecs = wecIds.size();
				parameterVo.size(noOfWecs);
				query = 
						"Select " + getSelectionPartofQuery(parameters) +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? ";
				
				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					for (Parameter parameter : parameters) {
						switch(parameter){
						/*case AVG_GENERATION_PER_WEC:
						case AVG_OPERATING_HOUR_PER_WEC:
							Double value = NumberUtility.round(resultSet.getDouble(parameter.getParamterName()) / noOfWecs, 1);
							parameterVo.value(parameter, value);break;*/
							default:parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));	
						}
					}
					if(wecIds.size() <= WEC_SPLIT_CUT_OFF) return (T)parameterVo;
					logger.debug(parameterVo);
					result.add(parameterVo);
					logger.debug("Result: " + result);
				}
			}
			return (T) result;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<String, IWecParameterVo>> T  getDateWise(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws SQLException{

		IWecParameterVo parameterVo = new WecParameterVo();
		Map<String, IWecParameterVo> data = new LinkedHashMap<String, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select D_reading_date, " + getSelectionPartofQuery(parameters) +
					"from tbl_reading_summary " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"and D_reading_date between ? and ? " +
					"group by D_reading_date ";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				for (Parameter parameter : parameters) {
					parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
				}
				data.put(resultSet.getString("D_reading_date"), parameterVo);
				parameterVo = new WecParameterVo();
			}
			
			return (T) data;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<String, IWecParameterVo>> T getWecWise(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws SQLException{

		IWecParameterVo parameterVo = new WecParameterVo();
		Map<String, IWecParameterVo> data = new LinkedHashMap<String, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select S_wec_id, " + getSelectionPartofQuery(parameters) +
					"from tbl_reading_summary " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"and D_reading_date between ? and ? " +
					"group by S_Wec_id ";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
			logger.debug(query);
//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
			while(resultSet.next()){
				for (Parameter parameter : parameters) {
					parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
				}
//				logger.debug(parameterVo);
				data.put(resultSet.getString("S_wec_id"), parameterVo);
				parameterVo = new WecParameterVo();
			}
			
			return (T) data;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<IWecMasterVo, IWecParameterVo>> T getWecWise(WecParameterData parameterData) throws SQLException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<String> wecIds = WecMasterUtility.getWecIds(parameterData.getWecs());
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		int publish = parameterData.getPublish();
		
		List<String> wecDataNotAvailable = new ArrayList<String>(wecIds); 
		WecMasterService wecService = WecMasterService.getInstance();
		
		IWecParameterVo parameterVo = new WecParameterVo();
		Map<IWecMasterVo, IWecParameterVo> data = new LinkedHashMap<IWecMasterVo, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String wecId = null;
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select S_wec_id, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by S_Wec_id ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
				
				while(resultSet.next()){
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					parameterVo.size(1);
					wecId = resultSet.getString("S_wec_id");
					data.put(wecService.get(wecId), parameterVo);
					parameterVo = new WecParameterVo();
					
					if(dataCheck) wecDataNotAvailable.remove(wecId);
				}
			}
			if(wecIds.size() <= WEC_SPLIT_CUT_OFF);
			
			if(dataCheck) for(String we: wecDataNotAvailable) data.put(wecService.get(we), null);
			return (T) data;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<WecDate, IWecParameterVo>> T getWecDateWise(WecParameterData parameterData) throws SQLException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<String> wecIds = WecMasterUtility.getWecIds(parameterData.getWecs());
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		int publish = parameterData.getPublish();
		
		logger.debug("WecSize:" + parameterData.getWecs().size());
		List<WecDate> wecDatesNotAvailable = WecDate.get(parameterData.getWecs(), fromDate, toDate);
		logger.debug("WecDate:" + wecDatesNotAvailable.size());
		
		WecMasterService wecService = WecMasterService.getInstance();
		
		IWecParameterVo parameterVo = new WecParameterVo();
		Map<WecDate, IWecParameterVo> data = new LinkedHashMap<WecDate, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String wecId = null;
		String readingDate = null;
		
		WecDate wecDate = null;
		
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select S_wec_id, D_reading_date, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by S_Wec_id, D_reading_date ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
				
				while(resultSet.next()){
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					parameterVo.size(1);
					wecId = resultSet.getString("S_wec_id");
					readingDate = DateUtility.sqlDateToStringDate(resultSet.getDate("D_reading_Date"), "dd-MMM-yyyy").toUpperCase();
					
					wecDate = new WecDate(wecService.get(wecId), readingDate);
					
					data.put(wecDate, parameterVo);
					parameterVo = new WecParameterVo();
					
					if(dataCheck) wecDatesNotAvailable.remove(wecDate);
				}
			}
			
			if(dataCheck) for(WecDate we: wecDatesNotAvailable) data.put(we, null);
			return (T) data;
			
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<YearMonth, IWecParameterVo>> T getYearMonthWise(WecParameterData parameterData) throws SQLException, ParseException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		logger.debug(toDate);
		if(DateUtility.getDaysDifferenceInclusive(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), toDate) > 0){
			toDate = DateUtility.getBackwardDateInStringWRTDays(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", -1);
			logger.debug(toDate);
		}
		Set<String> wecIds = WecMasterUtility.getWecIds(parameterData.getWecs());
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		
		Set<YearMonth> wecDatesNotAvailable = DateUtility.getYearMonth(fromDate, toDate);

		IWecParameterVo parameterVo = new WecParameterVo();
		Map<YearMonth, IWecParameterVo> data = new HashMap<YearMonth, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		YearMonth yearMonth = null;
		
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select to_char(D_reading_date, 'YYYY-MM') as yearmonth, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by to_char(D_reading_date, 'YYYY-MM') ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
				DateTime from = DateUtility.getJoda(fromDate);
				DateTime to = DateUtility.getJoda(toDate);
				while(resultSet.next()){
					
					//Populating Parameter Vo
					parameterVo = new WecParameterVo();
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					
					String[] ym = resultSet.getString("yearmonth").split("-");
					yearMonth = new YearMonth(Integer.parseInt(ym[0]), Integer.parseInt(ym[1]));
					int noOfDays = getNoOfDays(from, to, yearMonth);
					//logger.debug(noOfDays);
					parameterVo.size(wecIdss.size() * noOfDays);
					
					if(data.containsKey(yearMonth)){
						
						IWecParameterVo vo = data.get(yearMonth);
						logger.debug(vo);
						vo.add(parameterVo);
						
					} else {
						data.put(yearMonth, parameterVo);
					}
					
					if(dataCheck) wecDatesNotAvailable.remove(yearMonth);
				}
			}
			
			if(dataCheck) for(YearMonth we: wecDatesNotAvailable) data.put(we, null);
			return (T) data;
			
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<IWecMasterVo, Map<DateTime, IWecParameterVo>>> T getWecWiseDateWise(WecParameterData parameterData) throws SQLException, ParseException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		logger.debug(toDate);
		if(DateUtility.getDaysDifferenceInclusive(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), toDate) > 0){
			toDate = DateUtility.getBackwardDateInStringWRTDays(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", -1);
			logger.debug(toDate);
		}
		Set<IWecMasterVo> wecs = parameterData.getWecs();
		
		
		Map<String, IWecMasterVo> wecsM = Maps.uniqueIndex(
				wecs, new Function<IWecMasterVo, String>() {
					
					public String apply(IWecMasterVo from) {
						return from.getId();
					}
					
				});
		
		Set<String> wecIds = wecsM.keySet();
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		
		IWecParameterVo parameterVo = new WecParameterVo();
		Map<IWecMasterVo, Map<DateTime, IWecParameterVo>> data = new TreeMap<IWecMasterVo, Map<DateTime, IWecParameterVo>>();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		IWecMasterVo wec = null;
		String wecId = null;
		String readingDate = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select S_wec_id, D_reading_Date, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by S_wec_id, D_reading_Date ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);

				while(resultSet.next()){
					
					//Populating Parameter Vo
					parameterVo = new WecParameterVo();
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					
					parameterVo.size(1);
					wecId = resultSet.getString("S_WEC_ID");
					wec = wecsM.get(wecId);
					readingDate = DateUtility.sqlDateToStringDate(resultSet.getDate("D_READING_DATE"), "dd-MMM-yyyy");
//					logger.debug(readingDate);
					
					if(data.containsKey(wec)){
						Map<DateTime, IWecParameterVo> dateWiseData = data.get(wec);
						dateWiseData.put(DateUtility.getJoda(readingDate), parameterVo);
					} else {
						Map<DateTime, IWecParameterVo> dateWiseData = new TreeMap<DateTime, IWecParameterVo>();
						dateWiseData.put(DateUtility.getJoda(readingDate), parameterVo);
						data.put(wec, dateWiseData);
					}
					
//					if(dataCheck) wecDatesNotAvailable.remove(yearMonth);
				}
			}
			
//			if(dataCheck) for(YearMonth we: wecDatesNotAvailable) data.put(we, null);
			return (T) data;
			
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<IWecMasterVo, Map<YearMonth, IWecParameterVo>>> T getWecWiseYearMonthWise(WecParameterData parameterData) throws SQLException, ParseException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		logger.debug(toDate);
		if(DateUtility.getDaysDifferenceInclusive(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), toDate) > 0){
			toDate = DateUtility.getBackwardDateInStringWRTDays(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"), "dd-MMM-yyyy", -1);
			logger.debug(toDate);
		}
		Set<IWecMasterVo> wecs = parameterData.getWecs();
		
		
		Map<String, IWecMasterVo> wecsM = Maps.uniqueIndex(
				wecs, new Function<IWecMasterVo, String>() {
					
					public String apply(IWecMasterVo from) {
						return from.getId();
					}
					
				});
		
		Set<String> wecIds = wecsM.keySet();
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		
		IWecParameterVo parameterVo = new WecParameterVo();
		Map<IWecMasterVo, Map<YearMonth, IWecParameterVo>> data = new TreeMap<IWecMasterVo, Map<YearMonth, IWecParameterVo>>();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		IWecMasterVo wec = null;
		String wecId = null;
		YearMonth yearMonth = null;
		DateTime from = DateUtility.getJoda(fromDate);
		DateTime to = DateUtility.getJoda(toDate);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select S_wec_id, to_char(D_reading_date, 'YYYY-MM') as yearmonth, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by S_wec_id, to_char(D_reading_date, 'YYYY-MM') ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);

				while(resultSet.next()){
					
					//Populating Parameter Vo
					parameterVo = new WecParameterVo();
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					
					
					wecId = resultSet.getString("S_WEC_ID");
					String[] ym = resultSet.getString("yearmonth").split("-");
					yearMonth = new YearMonth(Integer.parseInt(ym[0]),Integer.parseInt(ym[1]));
					int noOfDays = getNoOfDays(from, to, yearMonth);
					wec = wecsM.get(wecId);
					parameterVo.size(noOfDays * wecIdss.size());
//					yearMonth = DateUtility.sqlDateToStringDate(resultSet.getDate("D_READING_DATE"), "dd-MMM-yyyy");
//					logger.debug(readingDate);
					
					if(data.containsKey(wec)){
						Map<YearMonth, IWecParameterVo> dateWiseData = data.get(wec);
						dateWiseData.put(yearMonth, parameterVo);
					} else {
						Map<YearMonth, IWecParameterVo> dateWiseData = new TreeMap<YearMonth, IWecParameterVo>();
						dateWiseData.put(yearMonth, parameterVo);
						data.put(wec, dateWiseData);
					}
					
//					if(dataCheck) wecDatesNotAvailable.remove(yearMonth);
				}
			}
			
//			if(dataCheck) for(YearMonth we: wecDatesNotAvailable) data.put(we, null);
			return (T) data;
			
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	private int getNoOfDays(DateTime from, DateTime to, YearMonth yearMonth) throws ParseException {
		int fromDateMonth = from.getMonthOfYear();
		int fromDateYear = from.getYear();
		
		int toDateMonth = to.getMonthOfYear();
		int toDateYear = to.getYear();
		
		int year = yearMonth.getYear();
		int month = yearMonth.getMonthOfYear();
		
		int noOfDays = -1;
		if(year == fromDateYear && month == fromDateMonth){
			noOfDays = from.dayOfMonth().withMaximumValue().getDayOfMonth() - from.getDayOfMonth();
		} else if (year == toDateYear && month == toDateMonth){
			noOfDays = to.getDayOfMonth() - to.dayOfMonth().withMinimumValue().getDayOfMonth();
		} else {
			noOfDays = yearMonth.toDateTime(null).dayOfMonth().getMaximumValue();
		}
		return noOfDays;
	}

	public <T extends IWecParameterVo> T getTotal(WecParameterData parameterData) throws SQLException, ParseException{

		Set<IWecMasterVo> wecs = parameterData.getWecs();
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean checkData = parameterData.isDataCheck();
		check(fromDate, toDate);
		
		Set<String> wecIds = WecMasterUtility.getWecIds(wecs);
		if(checkData && !isDataPresent(wecIds, fromDate, toDate)) return null;
		IWecParameterVo parameterVo = null;
		IWecParameterVo result = new WecParameterVo();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				
				parameterVo = new WecParameterVo();
				int noOfWecs = wecIds.size();
				
				//Important
				parameterVo.size(noOfWecs);
				
				query = 
						"Select " + getSelectionPartofQuery(parameters) +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) +
						parameterData.getPublishQuery() +
						"and D_reading_date between ? and ? ";
				
				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					for (Parameter parameter : parameters) {
						switch(parameter){
						/*case AVG_GENERATION_PER_WEC:
						case AVG_OPERATING_HOUR_PER_WEC:
							Double value = NumberUtility.round(resultSet.getDouble(parameter.getParamterName()) / noOfWecs, 1);
							parameterVo.value(parameter, value);break;*/
							default:parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));	
						}
					}
					if(wecIds.size() <= WEC_SPLIT_CUT_OFF) return (T)parameterVo;
					logger.debug(parameterVo);
					result.add(parameterVo);
					logger.debug("Result: " + result);
				}
			}
			return (T) result;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public static void main(String[] args) throws ParseException {
		testingWecWiseYearMonthWise();
	}
	
	private static void testingWecWiseYearMonthWise() {
		WecMasterService wecService = WecMasterService.getInstance();
		
		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wecService.get("0910000030"));
		wecs.add(wecService.get("1009000031"));
//		wecs.add(wecService.get("0910000052"));
//		wecs.add(wecService.get("0809000393"));
		
		String fromDate = "01-APR-2015";
		String toDate = "31-MAR-2016";
		
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.MA);
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setWecs(wecs);
		wecParameterData.setParameters(parameters);
		wecParameterData.setDataCheck(true);
		
		WecParameterDataDao dao = WecParameterDataDao.getInstance();
		
		try {
			Map<IWecMasterVo, Map<YearMonth, IWecParameterVo>> wecWiseDateWise = dao.getWecWiseYearMonthWise(wecParameterData);
			
			for(IWecMasterVo wec: wecWiseDateWise.keySet()){
				Map<YearMonth, IWecParameterVo> map = wecWiseDateWise.get(wec);
				
				for(YearMonth ym: map.keySet()){
					IWecParameterVo iWecParameterVo = map.get(ym);
					logger.debug(String.format("%s - %s - %s", wec.getName(), ym, iWecParameterVo));
				}
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	private static void testingWecWiseDateWise() {
		WecMasterService wecService = WecMasterService.getInstance();
		
		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wecService.get("0809002488"));
		wecs.add(wecService.get("0903000018"));
//		wecs.add(wecService.get("0910000052"));
//		wecs.add(wecService.get("0809000393"));
		
		String fromDate = "01-MAR-2016";
		String toDate = "31-MAR-2016";
		
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.MA);
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setWecs(wecs);
		wecParameterData.setParameters(parameters);
		wecParameterData.setDataCheck(true);
		
		WecParameterDataDao dao = WecParameterDataDao.getInstance();
		
		try {
			Map<IWecMasterVo, Map<DateTime, IWecParameterVo>> wecWiseDateWise = dao.getWecWiseDateWise(wecParameterData);
			
			for(IWecMasterVo wec: wecWiseDateWise.keySet()){
				Map<DateTime, IWecParameterVo> map = wecWiseDateWise.get(wec);
				
				for(DateTime date: map.keySet()){
					IWecParameterVo iWecParameterVo = map.get(date);
					logger.debug(String.format("%s - %s - %s", wec.getName(), date, iWecParameterVo));
				}
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	private static void testingYearMonthWise() throws ParseException {

		WecMasterService wecService = WecMasterService.getInstance();
		
		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wecService.get("0809002488"));
		wecs.add(wecService.get("0903000018"));
//		wecs.add(wecService.get("0910000052"));
//		wecs.add(wecService.get("0809000393"));
		
		String fromDate = "01-APR-2015";
		String toDate = "30-APR-2016";
		
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.MA);
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setWecs(wecs);
		wecParameterData.setParameters(parameters);
		wecParameterData.setDataCheck(true);
		
		WecParameterDataDao dao = WecParameterDataDao.getInstance();
		Map<YearMonth, IWecParameterVo> yearMonthWise = new TreeMap<YearMonth, IWecParameterVo>();
		try {
			yearMonthWise.putAll(dao.getYearMonthWise(wecParameterData));
//			yearMonthWise = dao.getYearMonthWise(wecParameterData);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		for(YearMonth yearMonth: yearMonthWise.keySet()){
			IWecParameterVo parameterVo = yearMonthWise.get(yearMonth);
			logger.debug(String.format("%s - %s", yearMonth, parameterVo));
		}
		
	}

	private static void testingDateWise() {
		String wecId = "0903000018";
		WecMasterService wecService = WecMasterService.getInstance();
		IWecMasterVo wec = wecService.get(wecId);
		String fromDate = "01-MAR-2016";
		String toDate = "05-MAR-2016";
		Set<IWecMasterVo> wecs = new HashSet<IWecMasterVo>();
		wecs.add(wec);
		
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.MA);
		
		WecParameterData wecParameterData = new WecParameterData();
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setWecs(wecs);
		wecParameterData.setParameters(parameters);
		wecParameterData.setDataCheck(false);
		wecParameterData.setPublish(1);
		
		WecParameterDataDao dao = WecParameterDataDao.getInstance();
		Map<String, IWecParameterVo> dateWise = new TreeMap<String, IWecParameterVo>();
		try {
			dateWise.putAll(dao.getDateWise(wecParameterData));
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		for(String date: dateWise.keySet()){
			IWecParameterVo parameterVo = dateWise.get(date);
			logger.debug(String.format("%s - %s", date, parameterVo));
		}
		
	}

	public static String getSelectionPartofQuery(Set<Parameter> parameters){
		return Parameter.getQuery(parameters);
	}
	
	private void check(String fromDate, String toDate) throws ParseException {
//		if(DateUtility.getJoda(fromDate).compareTo(DateUtility.getJoda(toDate)) > 0){
//			throw new IllegalArgumentException("From Date :: " + fromDate + ", To Date :: " + toDate);
//		}
	}

	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, String date, Set<Parameter> parameters) throws SQLException, ParseException {
		return getTotal(wecIds, date, date, parameters);
	}
	
	public <T extends IWecParameterVo> T getTotal(Set<String> wecIds, String date, Set<Parameter> parameters, boolean dataCheck) throws SQLException, ParseException {
		return getTotal(wecIds, date, date, parameters, dataCheck);
	}

	public <T extends Map<String, IWecParameterVo>> T getDateWise(WecParameterData parameterData) throws SQLException {
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<String> wecIds = WecMasterUtility.getWecIds(parameterData.getWecs());
		Set<Parameter> parameters = parameterData.getWecParameters();
		boolean dataCheck = parameterData.isDataCheck();
		int publish = parameterData.getPublish();
		
		//Upper Case
		List<String> dateDataNotAvailable = DateUtility.getDateRange(fromDate, toDate);
		
		IWecParameterVo parameterVo = new WecParameterVo();
		Map<String, IWecParameterVo> data = new HashMap<String, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String readingDate = null;
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdss : GlobalUtils.splitArrayList(wecIds, WEC_SPLIT_CUT_OFF)){
				query = 
						"Select D_reading_date, " + selectionPartofQuery +
						"from tbl_reading_summary " + 
						"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdss) + 
						"and D_reading_date between ? and ? " +
						parameterData.getPublishQuery() +
						"group by D_reading_date ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
	//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
				
				while(resultSet.next()){
					for (Parameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
					}
					parameterVo.size(1);
					readingDate = DateUtility.sqlDateToStringDate(resultSet.getDate("D_reading_date"),"dd-MMM-yyyy").toUpperCase();
					data.put(readingDate, parameterVo);
					parameterVo = new WecParameterVo();
					
					if(dataCheck) dateDataNotAvailable.remove(readingDate);
				}
			}
			if(wecIds.size() <= WEC_SPLIT_CUT_OFF);
			
			if(dataCheck) for(String we: dateDataNotAvailable) data.put(we, null);
			return (T) data;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public boolean transferWec(IWecMasterVo fromWec, IWecMasterVo toWec, String date) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String toCustomerId = null;
//		ICustomerMasterVo customer = toWec.getCustomer();

		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "UPDATE TBL_WEC_READING " +
					"SET S_WEC_ID = ?, S_CUSTOMER_ID = ? " +
					"WHERE S_WEC_ID = ? AND D_READING_DATE >= ?";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, toWec.getId());
			
			prepStmt.setObject(2, toCustomerId);
			prepStmt.setObject(3, fromWec.getId());
			prepStmt.setObject(4, date);
			
			return (prepStmt.executeUpdate() >= 0); 
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public boolean delete(WecReadingVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			if(vo.getMpId().equals("All")){
				logger.debug("ALL");
				sqlQuery =   "DELETE FROM TBL_WEC_READING " +
						    " WHERE  S_WEC_ID = ? " +
						    " AND D_READING_DATE BETWEEN ? AND ?" ;
				
				prepStmt = conn.prepareStatement(sqlQuery);	
				prepStmt.setObject(1, vo.getWecId());
				prepStmt.setObject(2, vo.getFromDate());
				prepStmt.setObject(3, vo.getToDate());

			}else{
				logger.debug("one MPID");
				
				sqlQuery =  " DELETE FROM TBL_WEC_READING" +
						    " WHERE S_MP_ID = ? " +
						    " AND S_WEC_ID = ? " +
						    " AND D_READING_DATE BETWEEN ? AND ?" ;
				
				prepStmt = conn.prepareStatement(sqlQuery);	
				prepStmt.setObject(1, vo.getMpId());
				prepStmt.setObject(2, vo.getWecId());
				prepStmt.setObject(3, vo.getFromDate());
				prepStmt.setObject(4, vo.getToDate());

			}
			
			int rowDeleted = prepStmt.executeUpdate();
			
            return (rowDeleted > 0);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}		
	}
}
