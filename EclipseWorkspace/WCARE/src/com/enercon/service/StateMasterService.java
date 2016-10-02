package com.enercon.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.StateMasterDao;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.StateMasterVo;

public class StateMasterService {

	private static Logger logger = Logger.getLogger(StateMasterService.class);
	private StateMasterDao stateDao = StateMasterDao.getInstance(); 
	
	private StateMasterService(){}
	
	private static class SingletonHelper{
		public final static StateMasterService INSTANCE = new StateMasterService();
	}
	
	public static StateMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public IStateMasterVo get(String id){
		logger.debug("id: " + id);
		logger.debug("id: " + stateDao.get(id));
		return stateDao.get(id);
	}
	
	public List<IStateMasterVo> getAll(ICustomerMasterVo customer){
		List<IStateMasterVo> states = stateDao.getAll(customer);

		return states;
	}
	
	public List<IStateMasterVo> getActive(ICustomerMasterVo customer){
		List<IStateMasterVo> states = stateDao.getActive(customer);

		return states;
	}

	public List<IStateMasterVo> getAll() throws SQLException {
		return stateDao.getAll();
	}
	
	public boolean create(StateMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_STATE_MASTER");
		vo.setId(id);
		
		return stateDao.create(vo);
	}

	public boolean updateForMaster(StateMasterVo vo) throws SQLException, CloneNotSupportedException {
		return stateDao.updateForMaster(vo);
	}
	
	public boolean exist(StateMasterVo vo) throws SQLException{
		return stateDao.exist(vo);
	}

	public List<IStateMasterVo> populate(List<ISiteMasterVo> sites) throws SQLException {
		return stateDao.associate(sites);
	}
}
