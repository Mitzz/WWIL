package com.enercon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.model.master.LoginHistoryVo;

public class LoginHistoryDao implements WcareConnector {
	
	private final static Logger logger = Logger.getLogger(LoginHistoryDao.class);
	private LoginHistoryDao(){}
    
    private static class SingletonHelper{
        private static final LoginHistoryDao INSTANCE = new LoginHistoryDao();
    }
     
    public static LoginHistoryDao getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public boolean create(LoginHistoryVo loginHistory) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "INSERT INTO TBL_LOGIN_HISTORY(S_LOGIN_HISTORY_ID,S_TRANSACTION_ID," +
					   "S_USER_ID,S_IP_ADDRESS,S_HOST)" +
					   "values(?, ?, ?, ?, ?)";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, loginHistory.getId());
			prepStmt.setObject(2, loginHistory.getTransactionId());
			prepStmt.setObject(3, loginHistory.getUserId());
			prepStmt.setObject(4, loginHistory.getIpAddress());
			prepStmt.setObject(5, loginHistory.getHost());
			
		
			int rowInserted = prepStmt.executeUpdate();
			logger.debug("rowInserted: " + rowInserted);
			if(rowInserted == 1)
				return true;
			else 
				return false;
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
    
    public int count() throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int count = 0;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "select Count(*) as Count from TBL_LOGIN_HISTORY";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while(rs.next()){
				count= rs.getInt("Count");
			}
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return count;
	}



}
