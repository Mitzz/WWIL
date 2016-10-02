package com.enercon.dao.parameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.master.EbMasterUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.parameter.eb.EbParameter;
import com.enercon.model.parameter.eb.EbParameterData;
import com.enercon.model.parameter.eb.EbParameterVo;
import com.enercon.model.parameter.eb.IEbParameterVo;
import com.enercon.model.table.EbReadingVo;
import com.enercon.model.utility.EbDate;
import com.enercon.service.master.EbMasterService;

public class EbParameterDataDao implements WcareConnector{

	private static Logger logger = Logger.getLogger(EbParameterDataDao.class);
	final int EB_SPLIT_CUT_OFF = 990;
	
	private EbParameterDataDao(){}
	
	private static class SingletonHelper{
		public final static EbParameterDataDao INSTANCE = new EbParameterDataDao();
	}
	
	public static EbParameterDataDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private boolean isDataPresent(Set<String> ebIds, String fromDate, String toDate) throws SQLException{

		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			for(List<String> ebIdss : GlobalUtils.splitArrayList(ebIds, 80)){
				query = 
						"Select count(1) as Count " +
						"from tbl_eb_reading " + 
						"where S_eb_id in " + GlobalUtils.getStringFromArrayForQuery(ebIdss) + 
						"and D_reading_date between ? and ? ";
				
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					if(resultSet.getInt("Count") > 0) return true;
				}
			}
			return false;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends IEbParameterVo> T getTotal(EbParameterData ebParameterData) throws SQLException, ParseException{

		Set<String> ebIds = EbMasterUtility.getEbIds(ebParameterData.getEbs());
		String fromDate = ebParameterData.getFromDate();
		String toDate = ebParameterData.getToDate();
		Set<EbParameter> parameters = ebParameterData.getParameters();
		boolean checkData = ebParameterData.isDataCheck();
		check(fromDate, toDate);
		if(checkData && !isDataPresent(ebIds, fromDate, toDate)) return null;
		IEbParameterVo parameterVo = null;
		IEbParameterVo result = new EbParameterVo();
		
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			for(List<String> ebIdss : GlobalUtils.splitArrayList(ebIds, EB_SPLIT_CUT_OFF)){
				
				parameterVo = new EbParameterVo();
				int noOfEbs = ebIds.size();
				parameterVo.size(noOfEbs);
				query = 
						"Select " + getSelectionPartofQuery(parameters) +
						"FROM TBL_EB_READING ER, TBL_EB_MASTER EM " + 
						"where er.S_eb_id in " + GlobalUtils.getStringFromArrayForQuery(ebIdss) + 
						"and er.D_reading_date between ? and ? " +
						"AND EM.S_EB_ID = ER.S_EB_ID " +
						"AND EM.S_SHOW = '0' " +
						"AND ER.N_PUBLISH IN (1,9) " +
						"AND ER.S_READING_TYPE IN ('D','I')";
				
				preparedStatement = connection.prepareStatement(query);

				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()){
					for (EbParameter parameter : parameters) {
						switch(parameter){
							default:parameterVo.value(parameter, resultSet.getObject(parameter.getAlias()));	
						}
					}
					if(ebIds.size() <= EB_SPLIT_CUT_OFF) return (T)parameterVo;
					result.add(parameterVo);
				}
			}
			return (T) result;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<IEbMasterVo, IEbParameterVo>> T getEbWise(EbParameterData parameterData) throws SQLException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<String> ebIds = EbMasterUtility.getEbIds(parameterData.getEbs());
		Set<EbParameter> parameters = parameterData.getParameters();
		boolean dataCheck = parameterData.isDataCheck();
		
		List<String> ebDataNotAvailable = new ArrayList<String>(ebIds); 
		EbMasterService ebService = EbMasterService.getInstance();
		
		IEbParameterVo parameterVo = new EbParameterVo();
		Map<IEbMasterVo, IEbParameterVo> data = new LinkedHashMap<IEbMasterVo, IEbParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String ebId = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select er.S_eb_id, " + getSelectionPartofQuery(parameters) +
					"FROM TBL_EB_READING ER, TBL_EB_MASTER EM " + 
					"where er.S_eb_id in " + GlobalUtils.getStringFromArrayForQuery(ebIds) + 
					"and er.D_reading_date between ? and ? " +
					"AND EM.S_EB_ID = ER.S_EB_ID " +
					"AND EM.S_SHOW = '0' " +
					"AND ER.N_PUBLISH IN (1,9) " +
					"AND ER.S_READING_TYPE IN ('D','I') " +
					"group by er.S_eb_id ";
			
			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setObject(1, fromDate);
			preparedStatement.setObject(2, toDate);
			resultSet = preparedStatement.executeQuery();
//			DaoUtility.displayQueryWithParameter(query, fromDate, toDate);
//			logger.debug(query);
			while(resultSet.next()){
				for (EbParameter parameter : parameters) {
					parameterVo.value(parameter, resultSet.getObject(parameter.getAlias()));
				}
				parameterVo.size(1);
				ebId = resultSet.getString("S_Eb_id");
				data.put(ebService.get(ebId), parameterVo);
				parameterVo = new EbParameterVo();
				
				if(dataCheck) ebDataNotAvailable.remove(ebId);
			}
			
			if(dataCheck) for(String eb: ebDataNotAvailable) data.put(ebService.get(eb), null);
			return (T) data;
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public <T extends Map<EbDate, IEbParameterVo>> T getEbDateWise(EbParameterData parameterData) throws SQLException{
		String fromDate = parameterData.getFromDate();
		String toDate = parameterData.getToDate();
		Set<String> ebIds = EbMasterUtility.getEbIds(parameterData.getEbs());
		Set<EbParameter> parameters = parameterData.getParameters();
		boolean dataCheck = parameterData.isDataCheck();
//		int publish = parameterData.getPublish();
		
		logger.debug("EbSize:" + parameterData.getEbs().size());
		List<EbDate> ebDatesNotAvailable = EbDate.get(parameterData.getEbs(), fromDate, toDate);
		logger.debug("EbDate:" + ebDatesNotAvailable.size());
		
		EbMasterService ebService = EbMasterService.getInstance();
		
		IEbParameterVo parameterVo = new EbParameterVo();
		Map<EbDate, IEbParameterVo> data = new LinkedHashMap<EbDate, IEbParameterVo>();
		String query = "";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		String ebId = null;
		String readingDate = null;
		
		EbDate ebDate = null;
		
		String selectionPartofQuery = getSelectionPartofQuery(parameters);
		try{
			connection = wcareConnector.getConnectionFromPool();
			for(List<String> ebIdss : GlobalUtils.splitArrayList(ebIds, EB_SPLIT_CUT_OFF)){
				query = 
						"Select er.S_eb_id, er.D_reading_date, " + selectionPartofQuery +
						"FROM TBL_EB_READING ER, TBL_EB_MASTER EM " + 
						"where er.S_eb_id in " + GlobalUtils.getStringFromArrayForQuery(ebIdss) + 
						"and er.D_reading_date between ? and ? " +
						"AND EM.S_EB_ID = ER.S_EB_ID " +
						"AND EM.S_SHOW = '0' " +
						"AND ER.N_PUBLISH IN (1,9) " +
						"AND ER.S_READING_TYPE IN ('D','I') " +
						"group by er.S_eb_id, er.D_reading_date ";
				
				preparedStatement = connection.prepareStatement(query);
				
				preparedStatement.setObject(1, fromDate);
				preparedStatement.setObject(2, toDate);
				resultSet = preparedStatement.executeQuery();
				logger.debug(query);
				
				while(resultSet.next()){
					for (EbParameter parameter : parameters) {
						parameterVo.value(parameter, resultSet.getObject(parameter.getAlias()));
					}
					parameterVo.size(1);
					ebId = resultSet.getString("S_eb_id");
					readingDate = DateUtility.sqlDateToStringDate(resultSet.getDate("D_reading_Date"), "dd-MMM-yyyy").toUpperCase();
					
					ebDate = new EbDate(ebService.get(ebId), readingDate);
					
					data.put(ebDate, parameterVo);
					parameterVo = new EbParameterVo();
					
					if(dataCheck) ebDatesNotAvailable.remove(ebDate);
				}
			}
			
			if(dataCheck) for(EbDate left: ebDatesNotAvailable) data.put(left, null);
			return (T) data;
			
		}
		finally{
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}
	
	public boolean transferEb(IEbMasterVo fromEb, IEbMasterVo toEb, String date) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String toCustomerId = null;
		List<ICustomerMasterVo> customers = toEb.getCustomers();
		logger.warn("Customer Size: " + customers.size());
		if(customers.size() != 1){
			return false;
		} else {
			toCustomerId = customers.get(0).getId();
		}
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "UPDATE TBL_EB_READING " +
					"SET S_EB_ID = ?, S_CUSTOMER_ID = ? " +
					"WHERE S_EB_ID = ? AND D_READING_DATE >= ?";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, toEb.getId());
			
			prepStmt.setObject(2, toCustomerId);
			prepStmt.setObject(3, fromEb.getId());
			prepStmt.setObject(4, date);
			
			return (prepStmt.executeUpdate() >= 0); 
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	private String getSelectionPartofQuery(Set<EbParameter> parameters) {
		return EbParameter.getQuery(parameters);
	}

	private void check(String fromDate, String toDate) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean delete(EbReadingVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			if(vo.getMpId().equals("All")){
				logger.debug("ALL");
				sqlQuery =   " DELETE FROM TBL_EB_READING " +
						     " WHERE  S_EB_ID = ? " +
						     " AND D_READING_DATE BETWEEN ? AND ? " ;
				
				prepStmt = conn.prepareStatement(sqlQuery);	
				prepStmt.setObject(1, vo.getEbId());
				prepStmt.setObject(2, vo.getFromDate());
				prepStmt.setObject(3, vo.getToDate());

			}else{
				logger.debug("one MPID");
				
				sqlQuery =  " DELETE FROM TBL_EB_READING " +
						    " WHERE S_MP_ID = ? " +
						    " AND S_EB_ID = ? " +
						    " AND D_READING_DATE BETWEEN ? AND ? " ;
				
				prepStmt = conn.prepareStatement(sqlQuery);	
				prepStmt.setObject(1, vo.getMpId());
				prepStmt.setObject(2, vo.getEbId());
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
