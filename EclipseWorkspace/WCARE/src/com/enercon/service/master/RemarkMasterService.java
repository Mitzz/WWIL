package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.RemarkMasterDao;
import com.enercon.model.master.RemarkMasterVo;
import com.enercon.service.CodeGenerateService;

public class RemarkMasterService{

	private static Logger logger = Logger.getLogger(RemarkMasterService.class);
	private RemarkMasterDao dao = RemarkMasterDao.getInstance();
	
	private RemarkMasterService(){}
	
	private static class SingletonHelper{
		public final static RemarkMasterService INSTANCE = new RemarkMasterService();
	}
	
	public static RemarkMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<RemarkMasterVo> getAll() throws SQLException{
		return dao.getAll();
	}
	
	public boolean create(RemarkMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_REMARKS");
		vo.setId(id);
		
		return dao.create(vo);
	}

	public boolean update(RemarkMasterVo vo) throws SQLException {
		
		return dao.update(vo);
	}
	public boolean check(RemarkMasterVo vo) throws SQLException {
		
		return dao.check(vo);
	}
}

