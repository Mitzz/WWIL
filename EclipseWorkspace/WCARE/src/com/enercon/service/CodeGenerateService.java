package com.enercon.service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.CodeGenerateDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.model.master.CodeGenerateVo;

public class CodeGenerateService {
	
	private final static Logger logger = Logger.getLogger(CodeGenerateService.class);
	private final static List<String> tables = 
			Arrays.asList(
					"TBL_STANDARD_MESSAGE",
					"TBL_MESSAGE_DETAIL",
					"TBL_LOGIN_HISTORY",
					"TBL_CUSTOMER_MASTER",
					"TBL_EB_MASTER",
					"TBL_EB_MFACTOR",
					"TBL_FEDER_MFACTOR",
					"TBL_FEDER_MASTER",
					"TBL_SITE_MASTER",
					"TBL_FEEDER_MASTER",
					"TBL_AREA_MASTER",
					"TBL_MP_MASTER",
					"TBL_REMARKS",
					"TBL_STATE_MASTER",
					"TBL_WEC_TYPE",
					"TBL_LOGIN_MASTER",
					"TBL_NEWS",
					"TBL_WEC_MASTER",
					"TBL_ROLE_MASTER",
					"TBL_ROLE_TRAN_MAPPING",
					"TBL_STATE_SITE_RIGHTS",
					"TBL_SUBSTATION_MASTER");
	
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
    	if(!tables.contains(tableName)){
    		throw new IllegalArgumentException(tableName + " is not defined for Id Generation");
    	}
    	String sequenceCode = null;
    	CodeGenerateVo vo = null;
    	logger.warn("Table Name: " + tableName);
//    	synchronized(tables.get(tables.indexOf(tableName))){
    		sequenceCode = DateUtility.getTodaysDateInGivenFormat("yyMM");
	    	vo = retrieve(tableName, sequenceCode);
	    	if(vo == null){
	    		vo = new CodeGenerateVo.CodeGenerateVoBuilder(tableName).sequenceCode(sequenceCode).build();
	    		create(vo);
	    	}
	    	vo.setSequenceNo(vo.getSequenceNo() + 1);
			update(vo);
//    	}
    	logger.warn("Table Name: " + tableName + " with id: " +  vo.id());
    	return vo.id();
	}
    
    private CodeGenerateVo retrieve(String table, String sequenceCode) throws SQLException{
    	return dao.retrieve(table, sequenceCode);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		WcareConnector.wcareConnector.shutDown();
		
	}
    
}
