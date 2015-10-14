package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.LoginMasterVo;

public class CustomerMasterDao{
	private final static Logger logger = Logger.getLogger(CustomerMasterDao.class);
	
	private final static String LOGIN_MASTER_ID_NULL = "LoginMasterVo 'id' is null";
	private final static String LOGIN_MASTER_NULL = "LoginMasterVo is null";
	private final static String NO_CUSTOMER_LOGIN_TYPE = "Customers are not assigned to User of Login Type 'E'";
	
	public CustomerMasterVo get(String id) throws SQLException{
		
		CustomerMasterVo customerMasterVo = null;
		String query = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select S_customer_id, S_customer_name " + 
					"from tbl_customer_master " + 
					"where S_customer_id = ? "; 
		
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				customerMasterVo = new CustomerMasterVo(id);
				
			}
			new WecMasterDao().get(customerMasterVo);
			
			return customerMasterVo;
		}
		finally{
			
			wcareConnector.returnConnectionToPool(connection);
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(resultSet != null) resultSet.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public List<CustomerMasterVo> get(LoginMasterVo loginMasterVo) throws SQLException{
		logger.info("Start");
		validate(loginMasterVo);
		List<CustomerMasterVo> customers = new ArrayList<CustomerMasterVo>();
		CustomerMasterVo customer = null;
		String query = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select S_customer_id, S_customer_name " + 
					"from tbl_customer_master " + 
					"where s_login_master_id = ? "; 
		
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, loginMasterVo.getId());
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				customer = new CustomerMasterVo(resultSet.getString("S_CUSTOMER_ID"));
				
				customer.setName(resultSet.getString("S_CUSTOMER_NAME"));
				customer.setLogin(loginMasterVo);
				new StateMasterDao().get(customer);
				customers.add(customer);
				
			}
			logger.info("End");
			return customers;
		}
		finally{
			
			wcareConnector.returnConnectionToPool(connection);
			try {
				if(preparedStatement != null) preparedStatement.close();
				if(resultSet != null) resultSet.close();
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
	}

	private void validate(LoginMasterVo loginMasterVo) {
		if(loginMasterVo == null) {
			logger.error(LOGIN_MASTER_NULL);
			throw new NullPointerException(LOGIN_MASTER_NULL);
		}
		String loginType = loginMasterVo.getLoginType();
		if(!loginType.equals("C")) {
			logger.error(NO_CUSTOMER_LOGIN_TYPE);
			throw new IllegalArgumentException(NO_CUSTOMER_LOGIN_TYPE);
		}
		
		if(loginMasterVo.getId() == null){
			logger.error(LOGIN_MASTER_ID_NULL);
			throw new NullPointerException(LOGIN_MASTER_ID_NULL);
		}
	}


	
}
