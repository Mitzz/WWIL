package com.enercon.spring.controllers;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.spring.service.master.SiteMasterService;
import com.enercon.spring.service.master.StateMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


@Controller
public class SiteMasterController {
	
	private static Logger logger = Logger.getLogger(SiteMasterController.class);
	private SiteMasterService siteMasterService;
	
	@Autowired
	public void setSiteMasterService(SiteMasterService service){
		this.siteMasterService = service;
	}
	
	@RequestMapping("/siteMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("site", new SiteMasterVo());
		
		try {
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();

			ObjectMapper objectMapper = new ObjectMapper();
			 
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "sites");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "code", "incharge", "address");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider()
				.addFilter("stateMasterVo", stateFilter)
				.addFilter("areaMasterVo", areaFilter)
				.addFilter("siteMasterVo", siteFilter);
			
//		    FilterProvider filters = propertyFilter;
//		    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			// Converting List of Java Object to List of Javascript Object
			String statesJson = objectMapper.writer(propertyFilter).writeValueAsString(states);
//			logger.debug(""+statesJson);
			
			
			model.addAttribute("states", statesJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageSiteMaster";		
	}
	
	@RequestMapping("/siteMaster/create")
	public ModelAndView create(SiteMasterVo site, HttpServletRequest request){
		logger.debug(site);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/siteMaster");
		try {
			boolean exist = siteMasterService.exist(site);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			site.setCreatedBy(loginId);
			site.setModifiedBy(loginId);
			boolean create = siteMasterService.create(site);
			if(create)
				mv.addObject("success", "Created");
			else
				mv.addObject("failure", "Not Created");
		} catch (Exception e) {
			mv.addObject("failure", "Not Created");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mv;
	}
	
	@RequestMapping("/siteMaster/update")
	public ModelAndView update(HttpServletRequest request, SiteMasterVo site, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/siteMaster");
		String loginId = MiscUtility.getLoginId(request);
		logger.debug("id : "+ site.getId());
		logger.debug("state id : "+ site.getStateId());
		logger.debug("code : "+ site.getCode());
		logger.debug("incharge : "+ site.getAreaId());
		
		try {
			site.setModifiedBy(loginId);
			boolean update = siteMasterService.updateForMaster(site);
			if(update)
				mv.addObject("success", "Updated");
			else
				mv.addObject("failure", "Not Updated");
		} catch (Exception e) {
			mv.addObject("failure", "Not Updated");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mv;
	}
	

}
