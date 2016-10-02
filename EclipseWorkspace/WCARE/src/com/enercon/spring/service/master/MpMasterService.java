package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.master.MpMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.MpMasterDao;

@Service
public class MpMasterService {

	private MpMasterDao mpMasterDao;
	
	@Autowired
	public void setMpMasterDao(MpMasterDao mpMasterDao) {
		this.mpMasterDao = mpMasterDao;
	}
	public List<MpMasterVo> getAll() throws SQLException{
		return mpMasterDao.getAll();
	}
	
	public boolean create(MpMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_MP_MASTER");
		vo.setId(id);
		return mpMasterDao.create(vo);
	}
	
	public boolean updateForMaster(MpMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return mpMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(MpMasterVo vo){
		return mpMasterDao.exist(vo);
	}
}
