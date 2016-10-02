package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.FeederMasterService;
import com.enercon.service.master.SubstationMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageFeederMasterRP extends Action {

	private static Logger logger = Logger.getLogger(ManageSubstationMasterRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		List<SubstationMasterVo> substations = SubstationMasterService.getInstance().getAll();
		FeederMasterService.getInstance().associateSubstations(substations);

		ObjectMapper objectMapper = new ObjectMapper();
		 
		SimpleBeanPropertyFilter substationFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "feeders");
		SimpleBeanPropertyFilter feederFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("substationMasterVo", substationFilter);
		propertyFilter.addFilter("feederMasterVo", feederFilter);
		
	    FilterProvider filters = propertyFilter;
	    
		// Converting List of Java Object to List of Javascript Object
		String substationsJson = objectMapper.writer(filters).writeValueAsString(substations);
		
		request.setAttribute("substations", substationsJson);
		return mapping.findForward("success");
	}

}
