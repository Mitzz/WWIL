package com.enercon.service;

import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.LoginDetailDao;
import com.enercon.model.table.LoginDetailVo;

public class LoginDetailService {

	private static Logger logger = Logger.getLogger(LoginDetailService.class);
	private LoginDetailDao dao = LoginDetailDao.getInstance();
	
	private LoginDetailService(){}
	
	private static class SingletonHelper{
		public final static LoginDetailService INSTANCE = new LoginDetailService();
	}
	
	public static LoginDetailService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<LoginDetailVo> getAll(){
		return dao.getAll();
	}
}
