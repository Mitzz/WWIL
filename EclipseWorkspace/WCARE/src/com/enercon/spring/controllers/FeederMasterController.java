package com.enercon.spring.controllers;

import java.sql.SQLException;
import java.util.Collections;
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
import com.enercon.model.comparator.AreaMasterVoComparator;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;
import com.enercon.service.master.SubstationMasterService;
import com.enercon.spring.service.master.FeederMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class FeederMasterController {

	private static Logger logger = Logger.getLogger(FeederMasterController.class);
	private FeederMasterService feederMasterService;
	
	@Autowired
	public void setFeederMasterService(FeederMasterService service){
		this.feederMasterService = service;
	}
	
	@RequestMapping("/feederMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("feeder", new FeederMasterVo());
		
		try {
			
			List<SubstationMasterVo> substations = SubstationMasterService.getInstance().getAll();
			feederMasterService.associateSubstations(substations);

			ObjectMapper objectMapper = new ObjectMapper();
			 
			SimpleBeanPropertyFilter substationFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "feeders");
			SimpleBeanPropertyFilter feederFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("substationMasterVo", substationFilter);
			propertyFilter.addFilter("feederMasterVo", feederFilter);
			
		    FilterProvider filters = propertyFilter;
		    
			// Converting List of Java Object to List of Javascript Object
			String substationsJson = objectMapper.writer(filters).writeValueAsString(substations);
	
			model.addAttribute("substations", substationsJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageFeederMaster";		
	}
	
	@RequestMapping("/feederMaster/create")
	public ModelAndView create(FeederMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/feederMaster");
		try {
			boolean exist = feederMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = feederMasterService.create(vo);
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
	
	@RequestMapping("/feederMaster/update")
	public ModelAndView update(HttpServletRequest request, FeederMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/feederMaster");
		String loginId = MiscUtility.getLoginId(request);
		logger.debug("id : "+ vo.getId());
		logger.debug("state id : "+ vo.getName());
		logger.debug("Substation Id: " + vo.getSubstationId());
//		logger.debug("code : "+ vo.getRemark());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = feederMasterService.updateForMaster(vo);
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
