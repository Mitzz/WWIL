package com.enercon.service;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.enercon.dao.LoginHistoryDao;
import com.enercon.model.master.LoginHistoryVo;
import com.enercon.model.master.LoginHistoryVo.LoginHistoryVoBuilder;

public class LoginHistoryService {
 
	private final static Logger logger = Logger.getLogger(LoginHistoryService.class);
	private LoginHistoryDao dao = LoginHistoryDao.getInstance();
	
	private LoginHistoryService(){}
    
    private static class SingletonHelper{
        private static final LoginHistoryService INSTANCE = new LoginHistoryService();
    }
     
    public static LoginHistoryService getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public boolean create(LoginHistoryVo loginHistory) throws SQLException {
		logger.debug("Enter");
		loginHistory.setId(CodeGenerateService.getInstance().getId("TBL_LOGIN_HISTORY"));
		return dao.create(loginHistory);
	}
    
    public int count() throws SQLException {
		logger.debug("Enter");
		return dao.count();
	}
    
    public static void main(String args[]) throws SQLException{
    	
    	LoginHistoryDao dao = LoginHistoryDao.getInstance();
    	
    	/*LoginHistoryVo loginHistory = new LoginHistoryVo();
    	
    	boolean insert; 
    	
    	loginHistory.setTransactionId("12345");
    	loginHistory.setUserId("91016490");
    	loginHistory.setIpAddress("NA");
    	loginHistory.setHost("NA"); 
    	insert = dao.create(loginHistory);
    	if(insert){
    		logger.debug("inserted successfully");
    	}else{
    		logger.debug("insertion failed");
    	}*/
    	
    	int count = dao.count();
    	logger.debug("COUNT :: " + count);
    	
    	
    }
}
