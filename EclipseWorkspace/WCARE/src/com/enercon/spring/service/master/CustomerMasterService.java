package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.CustomerMasterDao;

@Service
public class CustomerMasterService {

	private CustomerMasterDao customerMasterDao;
	
	@Autowired
	public void setCustomerMasterDao(CustomerMasterDao customerMasterDao) {
		this.customerMasterDao = customerMasterDao;
	}
	
	public List<ICustomerMasterVo> getAll() throws SQLException {
		return customerMasterDao.getAll();
	}
	
	public boolean create(CustomerMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_CUSTOMER_MASTER");
		vo.setId(id);
		return customerMasterDao.create(vo);
	}
	
	public boolean updateForMaster(CustomerMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return customerMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(CustomerMasterVo vo){
		return customerMasterDao.exist(vo);
	}
	
}
