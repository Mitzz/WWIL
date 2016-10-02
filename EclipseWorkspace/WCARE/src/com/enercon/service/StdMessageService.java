package com.enercon.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public boolean create(StandardMessageMasterVo vo) throws SQLException {
		return dao.create(vo);
	}
	
	public List<StandardMessageMasterVo> getAll() throws SQLException{
		logger.debug("Enter");
		/*List<StandardMessageMasterVo> vos = new ArrayList<StandardMessageMasterVo>();
		StandardMessageMasterVo vo = new StandardMessageMasterVo();
		
		for(int i = 0; i < 10; i++){
			vo.setId("15100000" + i);
			vo.setMessageHead("messageHead" + i);
			vo.setMessageDescription("Message Description " + i + " Message Description" + " Message Description" + " Message Description");
			vos.add(vo);
			vo = new StandardMessageMasterVo();
		}
		return vos;*/
		return dao.getAll();
	}
    
    
}
