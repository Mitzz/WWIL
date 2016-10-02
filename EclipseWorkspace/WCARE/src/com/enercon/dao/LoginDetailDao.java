package com.enercon.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.table.LoginDetailVo;

public class LoginDetailDao {

	private static Logger logger = Logger.getLogger(LoginDetailDao.class);
	
	private LoginDetailDao(){}
	
	private static class SingletonHelper{
		public final static LoginDetailDao INSTANCE = new LoginDetailDao();
	}
	
	public static LoginDetailDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<LoginDetailVo> getAll(){
		return null;
	}
}
