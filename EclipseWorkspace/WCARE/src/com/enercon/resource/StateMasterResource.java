package com.enercon.resource;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.graph.IStateMasterVo;
import com.enercon.service.StateMasterService;

public class StateMasterResource {

	private StateMasterService service = StateMasterService.getInstance();
	private static Logger logger = Logger.getLogger(StateMasterResource.class);
	
	public StateMasterResource(){
		
	}
	
	public IStateMasterVo get(String id){
//		logger.debug("id: " + id);
//		logger.debug("id: " + stateDao.get(id));
		return service.get(id);
	}
	
	public List<IStateMasterVo> getAll(){
		try {
			return service.getAll();
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}
	
	/*public List<IStateMasterVo> getAll(CustomerMasterVo customer){
		List<IStateMasterVo> states = service.getAll(customer);

		return states;
	}
	
	public List<IStateMasterVo> getActive(CustomerMasterVo customer){
		List<IStateMasterVo> states = service.getActive(customer);

		return states;
	}

	public List<IStateMasterVo> getAll() throws SQLException {
		return service.getAll();
	}
	
	public boolean create(StateMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_STATE_MASTER");
		vo.setId(id);
		
		return service.create(vo);
	}

	public boolean update(StateMasterVo vo) throws SQLException {
		return service.update(vo);
	}*/
}
