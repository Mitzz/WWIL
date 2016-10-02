package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.AreaMasterDao;
import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.service.CodeGenerateService;


public class AreaMasterService {

	private static Logger logger = Logger.getLogger(AreaMasterService.class);
	private AreaMasterDao areaDao = AreaMasterDao.getInstance(); 
	
	private AreaMasterService(){}
	
	private static class SingletonHelper{
		public final static AreaMasterService INSTANCE = new AreaMasterService();
	}
	
	public static AreaMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public boolean create(AreaMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_AREA_MASTER");
		vo.setId(id);
		
		return areaDao.create(vo);
	}

	public boolean update(AreaMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return areaDao.update(vo);
	}

	public List<IAreaMasterVo> getAll() {
		return areaDao.getAll();
	}

	public IAreaMasterVo get(String id) {
		return areaDao.get(id);
	}
}
