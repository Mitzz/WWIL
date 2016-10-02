package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.master.EbMasterDao;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.transfer.TransferEbVo;
import com.enercon.model.utility.EbWecTypeCount;
import com.enercon.service.CodeGenerateService;

public class EbMasterService {

	private static Logger logger = Logger.getLogger(EbMasterService.class);
	private EbMasterDao dao = EbMasterDao.getInstance();
	
	private EbMasterService(){}
	
	private static class SingletonHelper{
		public final static EbMasterService INSTANCE = new EbMasterService();
	}
	
	public static EbMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public IEbMasterVo get(String id){
		return dao.get(id);
	}

	public List<IEbMasterVo> getWecActive(ICustomerMasterVo customer, ISiteMasterVo site) {
		return dao.getWecActiveEbs(customer, site);
	}
	
	public List<IEbMasterVo> getWecDisplayActive(ICustomerMasterVo customer, ISiteMasterVo site) {
		return dao.getWecDisplayActiveEbs(customer, site);
	}
	
	public List<EbWecTypeCount> getActiveWecTypeCount(IEbMasterVo eb){
		return dao.getActiveWecTypeCount(eb);
	}

	public boolean transfer(TransferEbVo vo) throws SQLException {
		return dao.transfer(vo);
	}
	
	public boolean create(IEbMasterVo vo) throws SQLException, CloneNotSupportedException{
		String id = CodeGenerateService.getInstance().getId("TBL_EB_MASTER");
		vo.setId(id);
		return dao.create(vo);
	}
	public boolean partialUpdate(IEbMasterVo vo) throws SQLException, CloneNotSupportedException {
		return dao.partialUpdate(vo);
	}
	public boolean check(IEbMasterVo vo) throws SQLException, CloneNotSupportedException {
		return dao.check(vo);
	}
}
