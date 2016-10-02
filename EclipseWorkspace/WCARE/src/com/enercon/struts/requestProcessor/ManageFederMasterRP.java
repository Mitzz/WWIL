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

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FederMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageFederMasterRP extends Action{
	private static Logger logger = Logger.getLogger(ManageFederMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		List<FederMasterVo> feders = null;
		try {
			
			feders = FederMasterService.getInstance().getAll();
			List<ISiteMasterVo> sites = SiteMasterService.getInstance().populate(feders);
			List<IStateMasterVo> states = StateMasterService.getInstance().populate(sites);
			
			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
			SimpleBeanPropertyFilter federFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "description","workingStatus");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feders", "code");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites");
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "areas", "sapCode");
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("federMasterVo", federFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			
		    FilterProvider statesFilter = propertyFilter;
		    
			// Converting List of Java Object to List of Javascript Object
			String stateJson = objectMapper.writer(statesFilter).writeValueAsString(states);

			request.setAttribute("states", stateJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (JsonProcessingException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mapping.findForward("success");
	}
}
