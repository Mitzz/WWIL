package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.spring.dao.master.SiteMasterDao;

@Service
public class SiteMasterService {

	private SiteMasterDao siteMasterDao;
	
	@Autowired
	public void setSiteMasterDao(SiteMasterDao siteMasterDao) {
		this.siteMasterDao = siteMasterDao;
	}
	
	public List<SiteMasterVo> getRemarks() throws SQLException {
		return siteMasterDao.getRemarks();
	}
	
	public List<ISiteMasterVo> getAll() throws SQLException {
		return siteMasterDao.getAll();
	}
	
	public boolean create(SiteMasterVo site) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_SITE_MASTER");
		site.setId(id);
		return siteMasterDao.create(site);
	}
	
	public boolean updateForMaster(SiteMasterVo site) throws SQLException, CloneNotSupportedException {
		
		return siteMasterDao.updateForMaster(site);
	}
	
	public boolean exist(SiteMasterVo site){
		return siteMasterDao.exist(site);
	}
	
	/*FOR SITE REMARK*/
	public boolean remarkUpdateForMaster(SiteMasterVo site) throws SQLException, CloneNotSupportedException {
		
		return siteMasterDao.remarkUpdateForMaster(site);
	}
	
	public boolean remarkExist(SiteMasterVo site){
		return siteMasterDao.remarkExist(site);
	}
	
}
