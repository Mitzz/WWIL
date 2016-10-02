package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.FeederMasterDao;

@Service
public class FeederMasterService {

	
	private FeederMasterDao feederMasterDao;
	
	@Autowired
	public void setFeederMasterDao(FeederMasterDao feederMasterDao) {
		this.feederMasterDao = feederMasterDao;
	}
	
	public List<FeederMasterVo> associateSubstations(List<SubstationMasterVo> substations) throws SQLException {
		return feederMasterDao.associateSubstations(substations);	
	}
	
	public boolean create(FeederMasterVo feeder) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_FEEDER_MASTER");
		feeder.setId(id);
		return feederMasterDao.create(feeder);
	}
	
	public boolean updateForMaster(FeederMasterVo feeder) throws SQLException, CloneNotSupportedException  {
		
		return feederMasterDao.updateForMaster(feeder);
	}
	
	public boolean exist(FeederMasterVo feeder){
		return feederMasterDao.exist(feeder);
	}
	
	
}
