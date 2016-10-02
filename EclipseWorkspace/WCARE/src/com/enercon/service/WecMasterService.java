package com.enercon.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.WecMasterDao;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.graph.WecMasterVo;

public class WecMasterService {

	private static Logger logger = Logger.getLogger(WecMasterService.class);
	private WecMasterDao dao = WecMasterDao.getInstance();
	
	private WecMasterService(){}
	
	private static class SingletonHelper{
		public final static WecMasterService INSTANCE = new WecMasterService();
	}
	
	public static WecMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public IWecMasterVo get(String id){
		return dao.get(id);
	}
	
	/****************** Start: All Wecs ******************/
	
	public List<IWecMasterVo> getAll(List<ICustomerMasterVo> customers){
		return dao.getAll(customers);
	}
	
	/****************** End: All Wecs ******************/
	
	/****************** Start: Active Wecs ******************/
	
	public List<IWecMasterVo> getActiveWecs(String wecType) {
		return dao.getActive(wecType);
		
	}
	
	public List<IWecMasterVo> getActive(ICustomerMasterVo customer){
		return dao.getActive(customer);
	}
	
	public List<IWecMasterVo> getActive(IEbMasterVo eb){
		return dao.getActive(eb);
	}
	
	public List<IWecMasterVo> getActive(ISiteMasterVo site){
		return dao.getActive(site);
	}
	
	public List<IWecMasterVo> getActive(IEbMasterVo eb, ICustomerMasterVo customer){
		List<IWecMasterVo> wecs = dao.getActive(eb, customer);
		return wecs;
	}
	
	public List<IWecMasterVo> getActive(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer){
		List<IWecMasterVo> wecs = dao.getActive(site, state, customer);
		return wecs;
	}
	
	/****************** End: Active Wecs ******************/
	
	/****************** Start: Active Transferred Wecs ******************/
	
	public List<IWecMasterVo> getActiveTransferred(String wecType){
		return dao.getActiveTransferred(wecType);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IEbMasterVo eb){
		return dao.getActiveTransferred(eb);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state){
		return dao.getActiveTransferred(state);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, String wecType){
		return dao.getActiveTransferred(state, wecType);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, ISiteMasterVo site){
		return dao.getActiveTransferred(state, site);
	}
	
	public List<IWecMasterVo> getActiveTransferred(IStateMasterVo state, ISiteMasterVo site, String wecType){
		return dao.getActiveTransferred(state, site, wecType);
	}
	
	/****************** End: Active Transferred Wecs ******************/
	
	/****************** Start: Display Active Wecs ******************/
	public List<IWecMasterVo> getDisplayActive(ICustomerMasterVo customer) {
		return dao.getDisplayActive(customer);
	}

	public List<IWecMasterVo> getDisplayActive(ISiteMasterVo site) {
		return dao.getDisplayActive(site);
	}
	
	public List<IWecMasterVo> getDisplayActive(IEbMasterVo eb, ICustomerMasterVo customer){
		List<IWecMasterVo> wecs = dao.getDisplayActive(eb, customer);
		return wecs;
	}
	
	public List<IWecMasterVo> getDisplayActive(ICustomerMasterVo customer, IStateMasterVo state) {
		return dao.getDisplayActive(customer, state);
	}
	
	public List<IWecMasterVo> getDisplayActive(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer){
		List<IWecMasterVo> wecs = dao.getDisplayActive(site, state, customer);
		return wecs;
	}
	
	/****************** End: Display Active Wecs ******************/
	
	/****************** Start: Trial Check ******************/
	public boolean isTrial(IWecMasterVo wec) throws ParseException{
		return dao.isTrial(wec);
	}
	
	public boolean isTrial(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer) throws ParseException{
		boolean trial = dao.isTrial(site, state, customer);
		return trial;
	}
	
	public boolean isTrial(List<IWecMasterVo> wecs) {
		return dao.isTrial(wecs);
	}
	
	/****************** End: Trial Check ******************/

	public double getCapacity(List<IWecMasterVo> wecs){
		return dao.getCapacity(wecs);
	}

	

	public boolean create(WecMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_WEC_MASTER");
		vo.setId(id);
		
		return dao.create(vo);
	}

	public boolean update(IWecMasterVo vo) throws SQLException {
		
		return dao.update(vo);
	}
	
	public boolean check(IWecMasterVo vo) throws SQLException {
		
		return dao.check(vo);
	}
	
	
}
