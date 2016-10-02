package com.enercon.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.dao.master.CustomerMasterDao;
import com.enercon.model.CustomerLoginVo;
import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.master.LoginMasterVo;

public class CustomerMasterService {

	private static Logger logger = Logger.getLogger(CustomerMasterService.class);
	private CustomerMasterDao customerDao = CustomerMasterDao.getInstance();
	private CustomerMasterService(){}
	
	private static class SingletonHelper{
		public final static CustomerMasterService INSTANCE = new CustomerMasterService();
	}
	
	public static CustomerMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public ICustomerMasterVo get(String id){
		return customerDao.get(id);
	}
	
	public List<ICustomerMasterVo> get(Set<String> ids){
		return customerDao.get(ids);
	}
	
	public boolean create(CustomerMasterVo vo) throws SQLException, CloneNotSupportedException{
		
		String id = CodeGenerateService.getInstance().getId("TBL_CUSTOMER_MASTER");
		vo.setId(id);
		
		return customerDao.create(vo);
	}

	public boolean update(CustomerMasterVo vo) throws SQLException, CloneNotSupportedException {
		return customerDao.update(vo);
	}
	
	public List<ICustomerMasterVo> getAll() {
		return customerDao.getAll();
	}
	
	public List<ICustomerMasterVo> populate(List<LoginMasterVo> logins) throws SQLException {
		return customerDao.populate(logins);
		
	}
	
	public boolean updateLogin(CustomerLoginVo vo, String loginId) throws SQLException, CloneNotSupportedException {
		return customerDao.updateLogin(vo, loginId);
	}
}
