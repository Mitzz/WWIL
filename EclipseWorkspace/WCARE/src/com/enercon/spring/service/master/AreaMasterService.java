package com.enercon.spring.service.master;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.StateMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.service.StateMasterService;
import com.enercon.spring.dao.master.AreaMasterDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
@Service
public class AreaMasterService {
	
	private AreaMasterDao areaMasterDao;
	
	@Autowired
	public void setAreaMasterDao(AreaMasterDao areaMasterDao) {
		this.areaMasterDao = areaMasterDao;
	}
	
	public boolean create(AreaMasterVo area) throws SQLException{
		String id = CodeGenerateService.getInstance().getId("TBL_AREA_MASTER");
		area.setId(id);
		return areaMasterDao.create(area);
	}
	
	public boolean updateForMaster(AreaMasterVo area) throws SQLException, CloneNotSupportedException {
		
		return areaMasterDao.updateForMaster(area);
	}
	
	public boolean exist(AreaMasterVo area){
		return areaMasterDao.exist(area);
	}
}
