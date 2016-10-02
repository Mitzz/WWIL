package com.enercon.resource;

import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.service.WecMasterService;

public class WecMasterResource {
	
	private WecMasterService service = WecMasterService.getInstance();
	private static Logger logger = Logger.getLogger(WecMasterResource.class);
	
	public WecMasterResource(){
		
	}

	public List<IWecMasterVo> getActiveByCustomer(CustomerMasterVo customer){
		return service.getActive(customer);
	}
	
	public List<IWecMasterVo> getActiveByEb(EbMasterVo eb){
		return service.getActive(eb);
	}
}
