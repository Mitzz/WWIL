package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.LoginMasterDao;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.model.table.LoginDetailVo;

public class LoginMasterService {

	private static Logger logger = Logger.getLogger(LoginMasterService.class);
	private LoginMasterDao loginDao = LoginMasterDao.getInstance();
	private LoginMasterService(){}
	
	private static class SingletonHelper{
		public final static LoginMasterService INSTANCE = new LoginMasterService();
	}
	
	public static LoginMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<LoginMasterVo> getAll() throws SQLException {
		return loginDao.getAll();
	}
	
	public LoginMasterVo get(String userId, String password)
			throws SQLException{
		return loginDao.get(userId, password);
	}
	
	public List<LoginMasterVo> populate(List<LoginDetailVo> loginDetails) throws SQLException{ 
		return loginDao.populate(loginDetails);
	}
}
