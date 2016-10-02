package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.master.MpMasterVo;
import com.enercon.service.SiteMasterService;

import com.enercon.service.master.MpMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class DeleteWecDataRP extends Action {

	private static Logger logger = Logger.getLogger(DeleteWecDataRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		List<MpMasterVo> meaPoint = MpMasterService.getInstance().getAllByWec();
		List<ISiteMasterVo> sites = SiteMasterService.getInstance().getAll();
		
		ObjectMapper objectMapper = new ObjectMapper();
		 
		
		SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "wecs");
		SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
		
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
	
		propertyFilter.addFilter("siteMasterVo", siteFilter);
		propertyFilter.addFilter("wecMasterVo", wecFilter);
		
	    FilterProvider filters = propertyFilter;

		String sitesJson = objectMapper.writer(filters).writeValueAsString(sites);
		//logger.debug(""+sitesJson);
		request.setAttribute("sites", sitesJson);
		request.setAttribute("meaPoint", meaPoint);
		
		return mapping.findForward("success");
	}
}
