package com.enercon.resource;

import java.util.List;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.service.SiteMasterService;

public class SiteMasterResource {

	private SiteMasterService service = SiteMasterService.getInstance();
	
	public SiteMasterResource(){
		
	}
	
	public List<ISiteMasterVo> getAll(){
		return service.getAll();
	}
}
