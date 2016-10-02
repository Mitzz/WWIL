package com.enercon.service.master;

import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import com.enercon.dao.master.SubstationMasterDao;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.CodeGenerateService;


public class SubstationMasterService {

	private static Logger logger = Logger.getLogger(SubstationMasterService.class);
	private SubstationMasterDao subStationDao = SubstationMasterDao.getInstance(); 
	
	private SubstationMasterService(){}
	
	private static class SingletonHelper{
		public final static SubstationMasterService INSTANCE = new SubstationMasterService();
	}
	
	public static SubstationMasterService getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public boolean create(SubstationMasterVo vo) throws SQLException, CloneNotSupportedException {
		
		String id = CodeGenerateService.getInstance().getId("TBL_SUBSTATION_MASTER");
		vo.setId(id);
		
		return subStationDao.create(vo);
	}

	public boolean update(SubstationMasterVo vo) throws SQLException {
		
		return subStationDao.update(vo);
	}

	public List<SubstationMasterVo> getAll() throws SQLException {
		return subStationDao.getAll();
	}

	public void associate(List<FeederMasterVo> feeders) throws SQLException {
		SubstationMasterDao.getInstance().associate(feeders);
		
	}
	
	public List<SubstationMasterVo> associateAreas(List<IAreaMasterVo> areas) throws SQLException {
		return SubstationMasterDao.getInstance().associateArea(areas);
	}
	
}
