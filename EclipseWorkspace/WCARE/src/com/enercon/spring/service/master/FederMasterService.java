package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.FederMasterDao;
@Service
public class FederMasterService {

	private FederMasterDao federMasterDao;
	
	@Autowired
	public void setFederMasterDao(FederMasterDao federMasterDao) {
		this.federMasterDao = federMasterDao;
	}
	
	public List<FederMasterVo> getAll() throws SQLException{
		return federMasterDao.getAll();
	}
	
	public boolean create(FederMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_FEDER_MASTER");
		vo.setId(id);
		return federMasterDao.create(vo);
	}
	
	public boolean updateForMaster(FederMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return federMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(FederMasterVo vo){
		return federMasterDao.exist(vo);
	}
	
	
}
