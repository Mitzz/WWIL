package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.master.RemarkMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.RemarkMasterDao;

@Service
public class RemarkMasterService {

	private RemarkMasterDao remarkMasterDao;
	
	@Autowired
	public void setRemarkMasterDao(RemarkMasterDao remarkMasterDao) {
		this.remarkMasterDao = remarkMasterDao;
	}

	public List<RemarkMasterVo> getAll() throws SQLException{
		return remarkMasterDao.getAll();
	}
	
	public boolean create(RemarkMasterVo vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_WEC_TYPE");
		vo.setId(id);
		return remarkMasterDao.create(vo);
	}
	
	public boolean updateForMaster(RemarkMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		return remarkMasterDao.updateForMaster(vo);
	}
	
	public boolean exist(RemarkMasterVo vo){
		return remarkMasterDao.exist(vo);
	}
	
}
