package com.enercon.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.CodeGenerateDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.model.master.CodeGenerateVo;

public class CodeGenerateService {
	
	private final static Logger logger = Logger.getLogger(CodeGenerateService.class);
	
	private CodeGenerateDao dao = CodeGenerateDao.getInstance(); 

	private CodeGenerateService(){}
    
    private static class SingletonHelper{
        private static final CodeGenerateService INSTANCE = new CodeGenerateService();
    }
     
    public static CodeGenerateService getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public String getId(String tableName) throws SQLException {
    	tableName = tableName.toUpperCase();
    	String sequenceCode = null;
    	CodeGenerateVo vo = null;
    	synchronized(this){
    		sequenceCode = DateUtility.getTodaysDateInGivenFormat("yyMM");
	    	vo = dao.get(tableName, sequenceCode);
	    	if(vo == null){
	    		vo = new CodeGenerateVo.CodeGenerateVoBuilder(tableName).sequenceCode(sequenceCode).build();
	    		create(vo);
	    	}
	    	vo = new CodeGenerateVo.CodeGenerateVoBuilder(tableName).sequenceCode(sequenceCode).sequenceNo(vo.getSequenceNo() + 1).build();
			update(vo);
    	}
    	return vo.id();
	}
    
    private boolean update(CodeGenerateVo vo) throws SQLException {
		return dao.update(vo);
	}

	private boolean create(CodeGenerateVo vo) throws SQLException {
    	return dao.create(vo);
	}

	public static void main(String[] args) {
		String table = "TESTING";
		
		try {
			for(int i = 0; i < 103; i++){
				logger.debug(CodeGenerateService.getInstance().getId(table));
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		WcareConnector.wcareConnector.shutDown();
		
	}
    
}
