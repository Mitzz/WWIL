package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.StateMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.context.ApplicationContextProvider;
import com.enercon.spring.dao.master.StateMasterDao;

@Service
public class StateMasterService {

	private StateMasterDao stateMasterDao;

	@Autowired
	public void setStateMasterDao(StateMasterDao stateMasteDao) {
		this.stateMasterDao = stateMasteDao;
	}
	
	public static StateMasterService getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("stateMasterService", StateMasterService.class);
	}
	
	public List<IStateMasterVo> getAll() throws SQLException {
		return stateMasterDao.getAll();
	}
	
	public boolean create(StateMasterVo state) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_STATE_MASTER");
		state.setId(id);
		return stateMasterDao.create(state);
	}
	
	public boolean updateForMaster(StateMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return stateMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(StateMasterVo state){
		return stateMasterDao.exist(state);
	}
}
