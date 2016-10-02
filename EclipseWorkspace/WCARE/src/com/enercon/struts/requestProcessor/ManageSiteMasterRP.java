package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.Graph;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.service.StateMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageSiteMasterRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageSiteMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		List<IStateMasterVo> states = StateMasterService.getInstance().getAll();

		ObjectMapper objectMapper = new ObjectMapper();
		 
		SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
		SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "sites");
		SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "code", "incharge", "address");
		
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("stateMasterVo", stateFilter);
		propertyFilter.addFilter("areaMasterVo", areaFilter);
		propertyFilter.addFilter("siteMasterVo", siteFilter);
		
	    FilterProvider filters = propertyFilter;
//	    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		// Converting List of Java Object to List of Javascript Object
		String statesJson = objectMapper.writer(filters).writeValueAsString(states);
//		logger.debug(""+statesJson);
		request.setAttribute("states", statesJson);
	
		return mapping.findForward("success");
	}
	
}
