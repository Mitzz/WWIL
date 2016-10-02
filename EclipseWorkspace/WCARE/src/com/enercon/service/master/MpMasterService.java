package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import com.enercon.dao.master.MpMasterDao;
import com.enercon.model.master.MpMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.CodeGenerateService;

public class MpMasterService {
	
	private static Logger logger = Logger.getLogger(MpMasterService.class);
	private MpMasterDao dao = MpMasterDao.getInstance();
	
	private MpMasterService(){}
	
	private static class SingletonHelper{
		public final static MpMasterService INSTANCE = new MpMasterService();
	}
	
	public static MpMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<MpMasterVo> getAll() throws SQLException{
		return dao.getAll();
	}
	
	public boolean create(MpMasterVo vo) throws SQLException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_MP_MASTER");
		vo.setId(id);
		
		return dao.create(vo);
	}

	public boolean update(MpMasterVo vo) throws SQLException {
		
		return dao.update(vo);
	}
	
	public boolean check(MpMasterVo vo) throws SQLException {
		
		return dao.check(vo);
	}
	
	public List<MpMasterVo> getAllByWec() throws SQLException{
		return dao.getAllByWec();
	}
	
	public List<MpMasterVo> getMpByEb() throws SQLException{
		return dao.getMpByEb();
	}
	
}
