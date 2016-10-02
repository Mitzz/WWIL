package com.enercon.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.SiteMasterDao;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.model.master.FederMasterVo;

public class SiteMasterService {

	private static Logger logger = Logger.getLogger(SiteMasterService.class);
	private SiteMasterDao siteDao = SiteMasterDao.getInstance();
	
	private SiteMasterService(){}
	
	private static class SingletonHelper{
		public final static SiteMasterService INSTANCE = new SiteMasterService();
	}
	
	public static SiteMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<ISiteMasterVo> getAll(ICustomerMasterVo customer, IStateMasterVo state){
		List<ISiteMasterVo> sites = siteDao.getAll(customer, state);
		
		return sites;
	}
	
	public List<ISiteMasterVo> getActive(ICustomerMasterVo customer, IStateMasterVo state){
		List<ISiteMasterVo> sites = siteDao.getActive(customer, state);
		
		return sites;
	}
	
	public ISiteMasterVo get(String id){
		return siteDao.get(id);
	}

	public List<ISiteMasterVo> getAll() {
		return siteDao.getAll();
	}
	
	public boolean create(SiteMasterVo vo) throws SQLException, CloneNotSupportedException{
		String id = CodeGenerateService.getInstance().getId("TBL_SITE_MASTER");
		vo.setId(id);
		return siteDao.create(vo);
	}
	
	public List<ISiteMasterVo> populate(List<FederMasterVo> feders){
		return siteDao.populate(feders);
	}

	public boolean update(SiteMasterVo vo) throws SQLException, CloneNotSupportedException {
		return siteDao.update(vo);
	}

	public List<ISiteMasterVo> getAll(String userId) throws SQLException {
		return siteDao.getAll(userId);
	}
	
	public List<SiteMasterVo> getRemarks() throws SQLException {
		return siteDao.getRemarks();
	}
	
	public boolean checkRemark(SiteMasterVo vo) throws SQLException {
		return siteDao.checkRemark(vo);
	}
	public boolean updateRemark(SiteMasterVo vo) throws SQLException{
		return siteDao.updateRemark(vo);
	}
	
}
