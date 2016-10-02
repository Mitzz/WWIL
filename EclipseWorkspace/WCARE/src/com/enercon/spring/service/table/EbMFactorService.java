package com.enercon.spring.service.table;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.table.EbMFactorVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.table.EbMFactorDao;

@Service
public class EbMFactorService {

	private EbMFactorDao ebMFactorDao;
	
	@Autowired
	public void setEbMFactorDao(EbMFactorDao ebMFactorDao) {
		this.ebMFactorDao = ebMFactorDao;
	}
	
	public void associateEbMfs(List<IEbMasterVo> ebs) throws SQLException {
		ebMFactorDao.associateEbMfs(ebs);
		
	}
	
	public boolean create(EbMFactorVo  vo) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_EB_MFactor");
		vo.setId(id);
		return ebMFactorDao.create(vo);
	}
	
	public boolean updateForMaster(EbMFactorVo  vo) throws SQLException, CloneNotSupportedException {
		
		return ebMFactorDao.updateForMaster(vo);
	}
	
	public boolean exist(EbMFactorVo  vo){
		return ebMFactorDao.exist(vo);
	}
	
}
