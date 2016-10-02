package com.enercon.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.TransactionMasterDao;
import com.enercon.model.master.RoleMasterVo;
import com.enercon.model.master.TransactionMasterVo;

public class TransactionMasterService {

	private static Logger logger = Logger.getLogger(TransactionMasterService.class);
	private TransactionMasterDao dao = TransactionMasterDao.getInstance();
	
	private TransactionMasterService(){}
	
	private static class SingletonHelper{
		public final static TransactionMasterService INSTANCE = new TransactionMasterService();
	}
	
	public static TransactionMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<TransactionMasterVo> get(RoleMasterVo role) throws SQLException{
		return dao.get(role);
	}
	
	
}
