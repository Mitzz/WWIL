package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;


import com.enercon.dao.master.FeederMasterDao;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.CodeGenerateService;

public class FeederMasterService {

	private static Logger logger = Logger.getLogger(FeederMasterService.class);
	private FeederMasterDao feederDao = FeederMasterDao.getInstance(); 
	
	private FeederMasterService(){}
	
	private static class SingletonHelper{
		public final static FeederMasterService INSTANCE = new FeederMasterService();
	}
	
	public static FeederMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public boolean create(FeederMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_FEEDER_MASTER");
		vo.setId(id);
		
		return feederDao.create(vo);
	}

	public boolean update(FeederMasterVo vo) throws SQLException {
		
		return feederDao.update(vo);
	}

	public List<FeederMasterVo> getAll() throws SQLException {
		return feederDao.getAll();
	}

	public List<FeederMasterVo> associateSubstations(List<SubstationMasterVo> substations) throws SQLException {
		return feederDao.associateSubstations(substations);
		
	}
	
	
}
