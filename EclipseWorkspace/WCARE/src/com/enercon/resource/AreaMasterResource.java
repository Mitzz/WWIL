package com.enercon.resource;

import java.util.List;

import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.service.master.AreaMasterService;

public class AreaMasterResource {

	private AreaMasterService service = AreaMasterService.getInstance();
	
	public List<IAreaMasterVo> getAll(){
		return service.getAll();
	}
}
