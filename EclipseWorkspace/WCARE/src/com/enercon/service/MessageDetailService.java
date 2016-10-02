package com.enercon.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.module.MessageDetailDao;
import com.enercon.struts.form.MessageDetailVo;

public class MessageDetailService {

	private final static Logger logger = Logger.getLogger(MessageDetailService.class);
	private MessageDetailDao dao = MessageDetailDao.getInstance();
	
	private MessageDetailService(){}
    
    private static class SingletonHelper{
        private static final MessageDetailService INSTANCE = new MessageDetailService();
    }
     
    public static MessageDetailService getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public List<MessageDetailVo> getAll() throws SQLException{
    	logger.debug("Enter");
    	/*List<MessageDetailVo> vos = new ArrayList<MessageDetailVo>();
		MessageDetailVo vo = new MessageDetailVo();
		
		for(int i = 0; i < 10; i++){
			vo.setId("15100000" + i);
			vo.setDescription("Message Description " + i + " Message Description" + " Message Description" + " Message Description");
			vo.setFromDate((i + 1) + "-JAN-2015");
			vo.setToDate((i + 1) + "-FEB-2015");
			vos.add(vo);
			vo = new MessageDetailVo();
		}
		return vos;*/
    	return dao.getAll();
    }
    
    public boolean create(MessageDetailVo vo) throws SQLException {
		logger.debug("Enter");
		return dao.create(vo);
	}

	public boolean update(MessageDetailVo vo) throws SQLException {
		logger.debug("Enter");
		return dao.update(vo);
	}

	public boolean delete(MessageDetailVo vo) throws SQLException {
		logger.debug("Enter");
		return dao.delete(vo);
		
	}
}
