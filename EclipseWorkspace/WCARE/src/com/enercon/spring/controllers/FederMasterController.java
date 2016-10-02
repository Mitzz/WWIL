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
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.spring.service.master.FederMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class FederMasterController {

	private static Logger logger = Logger.getLogger(FederMasterController.class);
	private FederMasterService federMasterService;
	
	@Autowired
	public void setFederMasterService(FederMasterService service){
		this.federMasterService = service;
	}
	
	@RequestMapping("/federMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("feder", new FederMasterVo());
		List<FederMasterVo> feders = null;
		try {
			feders = federMasterService.getAll();
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
			
			model.addAttribute("states", stateJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageFederMaster";
		
	}
	
	@RequestMapping("/federMaster/create")
	public ModelAndView create(FederMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/federMaster");
		
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		
		logger.debug(vo);
		logger.debug("id :"+vo.getId()+" :name :: "+vo.getName()+" :desc::  "+vo.getDescription()+" :status::"+vo.getWorkingStatus()+" :siteId:: "+vo.getSiteId());
		
		try {
			boolean exist = federMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = federMasterService.create(vo);
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
	
	@RequestMapping("/federMaster/update")
	public ModelAndView update(HttpServletRequest request, FederMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/federMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		
		logger.debug(vo);
		logger.debug("id :"+vo.getId()+" :name :: "+vo.getName()+" :desc::  "+vo.getDescription()+" :status::"+vo.getWorkingStatus()+" :siteId:: "+vo.getSiteId());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = federMasterService.updateForMaster(vo);
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
