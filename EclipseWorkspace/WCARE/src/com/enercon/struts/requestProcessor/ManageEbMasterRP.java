package com.enercon.struts.requestProcessor;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FederMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageEbMasterRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageEbMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		try {
			
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
			SiteMasterService.getInstance().populate(FederMasterService.getInstance().getAll());
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas","sites","sapCode");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "sites");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id","code", "ebs", "feders");
			SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "wecs", "description", "workingStatus");
			SimpleBeanPropertyFilter federFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "description");
			SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "customer", "status");
			SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("ebMasterVo", ebFilter);
			propertyFilter.addFilter("wecMasterVo", wecFilter);
			propertyFilter.addFilter("customerMasterVo", customerFilter);
			propertyFilter.addFilter("federMasterVo", federFilter);
			
		    FilterProvider filters = propertyFilter;
		    
			// Converting List of Java Object to List of Javascript Object
			String statesJson = mapper.writer(filters).writeValueAsString(states);
			List<ICustomerMasterVo> customer = CustomerMasterService.getInstance().getAll();
			
			
			request.setAttribute("states", statesJson);
			request.setAttribute("customer", customer);
			
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (JsonProcessingException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mapping.findForward("success");
	}
	
	public static void main(String args[]) throws Exception{
		
		
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
			SiteMasterService.getInstance().populate(FederMasterService.getInstance().getAll());
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "sites");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs", "feders");
			SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "wecs", "description", "workingStatus");
			SimpleBeanPropertyFilter federFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "description");
			SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "customer", "status");
			SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("ebMasterVo", ebFilter);
			propertyFilter.addFilter("wecMasterVo", wecFilter);
			propertyFilter.addFilter("customerMasterVo", customerFilter);
			propertyFilter.addFilter("federMasterVo", federFilter);
			
		    FilterProvider filters = propertyFilter;
		    
			// Converting List of Java Object to List of Javascript Object
			String statesJson = mapper.writer(filters).writeValueAsString(states);
			logger.debug(statesJson);
	}
		
	
}

