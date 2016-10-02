package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.AreaMasterDao;
import com.enercon.spring.dao.master.SubstationMasterDao;
@Service
public class SubstationMasterService {

private SubstationMasterDao substationMasterDao;
	
	@Autowired
	public void setSubstationMasterDao(SubstationMasterDao substationMasterDao) {
		this.substationMasterDao = substationMasterDao;
	}
	
	public List<SubstationMasterVo> getAll() throws SQLException {
		return substationMasterDao.getAll();
	}
	
	public boolean create(SubstationMasterVo substation) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_SUBSTATION_MASTER");
		substation.setId(id);
		return substationMasterDao.create(substation);
	}
	
	public boolean updateForMaster(SubstationMasterVo substation) throws SQLException, CloneNotSupportedException  {
		
		return substationMasterDao.updateForMaster(substation);
	}
	
	public boolean exist(SubstationMasterVo substation){
		return substationMasterDao.exist(substation);
	}
}
