package com.enercon.spring.service.table;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.master.FederMasterVo;
import com.enercon.model.table.FederMFactorVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.table.FederMFactorDao;

@Service
public class FederMFactorService {

	private FederMFactorDao federMFactorDao;
	
	@Autowired
	public void setFederMFactorDao(FederMFactorDao federMFactorDao) {
		this.federMFactorDao = federMFactorDao;
	}
	
	public void associateFederMfs(List<FederMasterVo> feders) throws SQLException {
		federMFactorDao.associateFederMfs(feders);
		
	}
	
	public boolean create(FederMFactorVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_FEDER_MFACTOR");
		vo.setId(id);
		return federMFactorDao.create(vo);
	}
	
	public boolean updateForMaster(FederMFactorVo vo) throws SQLException, CloneNotSupportedException {
		
		return federMFactorDao.updateForMaster(vo);
	}
	
	public boolean exist(FederMFactorVo vo){
		return federMFactorDao.exist(vo);
	}
	
	
}
