package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.WecTypeMasterDao;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.CodeGenerateService;

public class WecTypeMasterService {

	private static Logger logger = Logger.getLogger(WecTypeMasterService.class);
	private WecTypeMasterDao dao = WecTypeMasterDao.getInstance();
	
	private WecTypeMasterService(){}
	
	private static class SingletonHelper{
		public final static WecTypeMasterService INSTANCE = new WecTypeMasterService();
	}
	
	public static WecTypeMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<WecTypeMasterVo> getAll() throws SQLException{
		return dao.getAll();
	}
	
	public WecTypeMasterVo get(String id) throws SQLException{
		return dao.get(id);
	}

	public List<WecTypeMasterVo> get(ISiteMasterVo site) throws SQLException {
		return dao.get(site);
	}
	
	public boolean create(WecTypeMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_WEC_TYPE");
		vo.setId(id);
		
		return dao.create(vo);
	}

	public boolean update(WecTypeMasterVo vo) throws SQLException {
		
		return dao.update(vo);
	}
	
	public boolean check(WecTypeMasterVo vo) throws SQLException {
		
		return dao.check(vo);
	}
}
