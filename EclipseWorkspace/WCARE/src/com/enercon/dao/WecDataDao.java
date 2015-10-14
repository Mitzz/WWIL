package com.enercon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.report.WecParameterVo;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.Year;

public class WecDataDao implements WcareConnector{
	
	public <T extends Number> T getGeneration(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getGeneration(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}
	
	public <T extends Number> T getGeneration(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.GENERATION);
	}
	
	public <T extends Number> T getOperatingHour(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getOperatingHour(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}
	
	public <T extends Number> T getOperatingHour(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.OPERATING_HOUR);
	}
	
	public <T extends Number> T getMA(Set<String> wecIds, Month month, Year year) throws SQLException, ParseException{
		return getMA(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year));
	}
	
	public <T extends Number> T getMA(Set<String> wecIds, String fromDate, String toDate) throws SQLException, ParseException{
		check(fromDate, toDate);
		return getParameter(wecIds, fromDate, toDate, Parameter.MA);
	}
	
	private <T extends Number> T getParameter(Set<String> wecIds, String fromDate, String toDate, Parameter parameter) throws SQLException, ParseException{

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
	
	public <T extends IWecParameterVo> T getWecParameterVo(Set<String> wecIds, Month month, Year year, Set<Parameter> parameters) throws SQLException, ParseException{
		return getWecParameterVo(wecIds, DateUtility.getFirstDateOfMonth(month, year), DateUtility.getLastDateOfMonth(month, year), parameters);
	}
	
	public <T extends IWecParameterVo> T getWecParameterVo(Set<String> wecIds, FiscalYear fiscalYear, Set<Parameter> parameters) throws SQLException, ParseException{
		return getWecParameterVo(wecIds, fiscalYear.getFirstDate(), fiscalYear.getLastDate(), parameters);
	}
	
	public <T extends IWecParameterVo> T getWecParameterVo(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters) throws SQLException, ParseException{

		check(fromDate, toDate);
		
		IWecParameterVo parameterVo = new WecParameterVo();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select " + getSelectionPartofQuery(parameters) +
					"from tbl_reading_summary " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"and D_reading_date between ? and ? ";
			
			preparedStatement = connection.prepareStatement(query);
//			System.out.println("Select " + getSelectionPartofQuery(parameters) + "\n" +
//					"from tbl_reading_summary " + "\n" +
//					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds));
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				parameterVo.size(wecIds.size());
				for (Parameter parameter : parameters) {
					parameterVo.value(parameter, resultSet.getDouble(parameter.getParamterName()));
				}
			}
			
			return (T) parameterVo;
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
	
	public <T extends IWecParameterVo> T  getDateWiseWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws SQLException{

		IWecParameterVo parameterVo = new WecParameterVo();
		Map<String, IWecParameterVo> data = new LinkedHashMap<String, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select D_reading_date, " + Parameter.getQuery(parameters) +
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
					parameterVo.value(parameter, resultSet.getDouble(parameter.getParamterName()));
				}
				data.put(resultSet.getString("D_reading_date"), parameterVo);
				parameterVo = new WecParameterVo();
			}
			
			return (T) data;
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
	
	public <T extends Map<String, IWecParameterVo>> T getWecWiseWecParameterVo(String fromDate, String toDate, Set<String> wecIds, Set<Parameter> parameters) throws SQLException{

		IWecParameterVo parameterVo = new WecParameterVo();
		Map<String, IWecParameterVo> data = new LinkedHashMap<String, IWecParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select S_wec_id, " + Parameter.getQuery(parameters) +
					"from tbl_reading_summary " + 
					"where S_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"and D_reading_date between ? and ? " +
					"group by S_Wec_id ";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				for (Parameter parameter : parameters) {
					parameterVo.value(parameter, resultSet.getObject(parameter.getParamterName()));
				}
				data.put(resultSet.getString("S_wec_id"), parameterVo);
				parameterVo = new WecParameterVo();
			}
			
			return (T) data;
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
	
	public static void main(String[] args) {
		
		Set<Parameter> parameters = new HashSet<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.MA);
		
		System.out.println(getSelectionPartofQuery(parameters));
		
	}
	
	public static String getSelectionPartofQuery(Set<Parameter> parameters){
		return Parameter.getQuery(parameters);
	}
	
	private void check(String fromDate, String toDate) throws ParseException {
//		if(DateUtility.getJoda(fromDate).compareTo(DateUtility.getJoda(toDate)) > 0){
//			throw new IllegalArgumentException("From Date :: " + fromDate + ", To Date :: " + toDate);
//		}
	}

	public <T extends IWecParameterVo> T getWecParameterVo(Set<String> wecIds, String date, Set<Parameter> parameters) throws SQLException, ParseException {
		return getWecParameterVo(wecIds, date, date, parameters);
	}
}
