package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.FederMasterDao;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.CodeGenerateService;

public class FederMasterService {
	
	private static Logger logger = Logger.getLogger(FederMasterService.class);
	private final FederMasterDao dao = FederMasterDao.getInstance();

	private FederMasterService(){}
	
	private static class SingletonHelper{
		public final static FederMasterService INSTANCE = new FederMasterService();
	}
	
	public static FederMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<FederMasterVo> getAll() throws SQLException{
		return dao.getAll();
	}
	
	public boolean create(FederMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_FEDER_MASTER");
		vo.setId(id);
		
		return dao.create(vo);
	}

	public boolean update(FederMasterVo vo) throws SQLException {
		
		return dao.update(vo);
	}
	
	public static void main(String[] args) {
		try {
			List<FederMasterVo> feders = FederMasterService.getInstance().getAll();
			logger.debug(feders.size());
			logger.debug("Test Successful");
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
}
