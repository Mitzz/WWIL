package com.enercon.struts.requestProcessor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FeederMasterService;
import com.enercon.service.master.SubstationMasterService;
import com.enercon.service.master.WecTypeMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageWecMasterRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageWecMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
		
		List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
		for(IStateMasterVo state: states){
			areas.addAll(state.getAreas());
		}
		
		List<SubstationMasterVo> substations = SubstationMasterService.getInstance().associateAreas(areas);
		FeederMasterService.getInstance().associateSubstations(substations);
		ObjectMapper objectMapper = new ObjectMapper();
		 
//		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
		SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites", "substations");
		SimpleBeanPropertyFilter substationFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feeders");
		SimpleBeanPropertyFilter feederFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
		SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "customers");
		SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
		SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "wecs");
		SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "type" , "capacity" , "status" ,"multiFactor" ,"genComm","technicalNo","foundationNo","costPerUnit","machineAvailability","extGridAvailability","intGridAvailability");
		
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("stateMasterVo", stateFilter);
		propertyFilter.addFilter("areaMasterVo", areaFilter);
		propertyFilter.addFilter("substationMasterVo", substationFilter);
		propertyFilter.addFilter("feederMasterVo", feederFilter);
		propertyFilter.addFilter("siteMasterVo", siteFilter);
		propertyFilter.addFilter("customerMasterVo", customerFilter);
		propertyFilter.addFilter("ebMasterVo", ebFilter);
		propertyFilter.addFilter("wecMasterVo", wecFilter);
		
	    FilterProvider filters = propertyFilter;

		String statesJson = objectMapper.writer(filters).writeValueAsString(states);
	//	logger.debug(""+statesJson);
		
		List<WecTypeMasterVo> wecType = WecTypeMasterService.getInstance().getAll();
		
		request.setAttribute("wecType", wecType);
		request.setAttribute("states", statesJson);
	
		return mapping.findForward("success");
	}
	
	public static void main(String[] args) {
		try{
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
	
			List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
			for(IStateMasterVo state: states){
				areas.addAll(state.getAreas());
			}
			
			List<SubstationMasterVo> substations = SubstationMasterService.getInstance().associateAreas(areas);
			FeederMasterService.getInstance().associateSubstations(substations);
			ObjectMapper objectMapper = new ObjectMapper();
			 
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites", "substations");
			SimpleBeanPropertyFilter substationFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feeders");
			SimpleBeanPropertyFilter feederFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "customers");
			SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
			SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "wecs");
			SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "type" , "capacity" , "status" ,"multiFactor" ,"genComm","technicalNo","foundationNo");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("substationMasterVo", substationFilter);
			propertyFilter.addFilter("feederMasterVo", feederFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("customerMasterVo", customerFilter);
			propertyFilter.addFilter("ebMasterVo", ebFilter);
			propertyFilter.addFilter("wecMasterVo", wecFilter);
			
		    FilterProvider filters = propertyFilter;
	
			String statesJson = objectMapper.writer(filters).writeValueAsString(states);
			logger.debug(statesJson);
		
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
}