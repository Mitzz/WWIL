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
import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.spring.service.master.AreaMasterService;
import com.enercon.spring.service.master.StateMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
@Controller
public class AreaMasterController {

	private static Logger logger = Logger.getLogger(AreaMasterController.class);
	private AreaMasterService areaMasterService;
	
	@Autowired
	public void setAreaMasterService(AreaMasterService service){
		this.areaMasterService = service;
	}
	
	@RequestMapping("/areaMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("area", new AreaMasterVo());
		List<IStateMasterVo> stateMasterVos = null;
		
		try {
			stateMasterVos = StateMasterService.getInstance().getAll();
			ObjectMapper objectMapper = new ObjectMapper();
			 
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "code", "inCharge");
			SimpleFilterProvider propertyFilter = 
					new SimpleFilterProvider()
						.addFilter("stateMasterVo", stateFilter)
						.addFilter("areaMasterVo", areaFilter);
			
			String statesJson = objectMapper.writer(propertyFilter).writeValueAsString(stateMasterVos);

			model.addAttribute("states", statesJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageAreaMaster";		
	}
	
	@RequestMapping("/areaMaster/create")
	public ModelAndView create(AreaMasterVo area, HttpServletRequest request){
		logger.debug(area);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/areaMaster");
		try {
			boolean exist = areaMasterService.exist(area);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			area.setCreatedBy(loginId);
			area.setModifiedBy(loginId);
			boolean create = areaMasterService.create(area);
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
	
	@RequestMapping("/areaMaster/update")
	public ModelAndView update(HttpServletRequest request, AreaMasterVo area, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/areaMaster");
		String loginId = MiscUtility.getLoginId(request);
		logger.debug("id : "+ area.getId());
		logger.debug("state id : "+ area.getStateId());
		logger.debug("code : "+ area.getCode());
		logger.debug("incharge : "+ area.getInCharge());
		
		try {
			area.setModifiedBy(loginId);
			boolean update = areaMasterService.updateForMaster(area);
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
