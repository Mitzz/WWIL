package com.enercon.dao.module;

import org.apache.log4j.Logger;

import com.enercon.model.master.StandardMessageMasterVo;
import com.enercon.service.StdMessageService;

public class StdMessageDao {

	private final static Logger logger = Logger.getLogger(StdMessageService.class);
	
	private StdMessageDao(){}
    
    private static class SingletonHelper{
        private static final StdMessageDao INSTANCE = new StdMessageDao();
    }
     
    public static StdMessageDao getInstance(){
        return SingletonHelper.INSTANCE;
    }

	public boolean create(StandardMessageMasterVo vo) {
		return true;
	}
}
