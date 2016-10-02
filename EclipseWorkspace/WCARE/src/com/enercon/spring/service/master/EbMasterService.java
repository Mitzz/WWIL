package com.enercon.spring.service.master;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.EbMasterDao;

@Service
public class EbMasterService {

	private EbMasterDao ebMasterDao;
	
	@Autowired
	public void setEbMasterDao(EbMasterDao ebMasterDao) {
		this.ebMasterDao = ebMasterDao;
	}
	
	public boolean create(EbMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_FEDER_MASTER");
		vo.setId(id);
		return ebMasterDao.create(vo);
	}
	
	public boolean updateForMaster(IEbMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return ebMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(EbMasterVo vo){
		return ebMasterDao.exist(vo);
	}
	
	
}
