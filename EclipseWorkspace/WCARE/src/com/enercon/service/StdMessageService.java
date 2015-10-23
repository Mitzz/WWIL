package com.enercon.service;

import org.apache.log4j.Logger;

import com.enercon.dao.module.StdMessageDao;
import com.enercon.model.master.StandardMessageMasterVo;

public class StdMessageService {

	private final static Logger logger = Logger.getLogger(StdMessageService.class);
	private StdMessageDao dao = StdMessageDao.getInstance();
	
	private StdMessageService(){}
    
    private static class SingletonHelper{
        private static final StdMessageService INSTANCE = new StdMessageService();
    }
     
    public static StdMessageService getInstance(){
        return SingletonHelper.INSTANCE;
    }

	public boolean create(StandardMessageMasterVo vo) {
		return dao.create(vo);
		
	}
    
    
}
