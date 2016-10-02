package com.enercon.spring.service.master;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.WecMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.WecMasterDao;

@Service
public class WecMasterService {

	private WecMasterDao wecMasterDao;
	
	@Autowired
	public void setWecMasterDao(WecMasterDao wecMasterDao) {
		this.wecMasterDao = wecMasterDao;
	}

	public boolean create(WecMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_WEC_TYPE");
		vo.setId(id);
		return wecMasterDao.create(vo);
	}
	
	public boolean updateForMaster(IWecMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return wecMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(IWecMasterVo vo){
		return wecMasterDao.exist(vo);
	}
}
