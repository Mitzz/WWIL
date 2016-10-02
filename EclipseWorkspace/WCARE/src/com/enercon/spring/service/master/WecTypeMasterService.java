package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.AreaMasterDao;
import com.enercon.spring.dao.master.WecTypeMasterDao;
@Service
public class WecTypeMasterService {

	private WecTypeMasterDao wecTypeMasterDao;
	
	@Autowired
	public void setWecTypeMasterDao(WecTypeMasterDao wecTypeMasterDao) {
		this.wecTypeMasterDao = wecTypeMasterDao;
	}
	
	public List<WecTypeMasterVo> getAll() throws SQLException{
		return wecTypeMasterDao.getAll();
	}
	
	public boolean create(WecTypeMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_WEC_TYPE");
		vo.setId(id);
		return wecTypeMasterDao.create(vo);
	}
	
	public boolean updateForMaster(WecTypeMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return wecTypeMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(WecTypeMasterVo vo){
		return wecTypeMasterDao.exist(vo);
	}
}
