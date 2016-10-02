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
import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FederMasterService;
import com.enercon.spring.service.master.EbMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class EbMasterController {

	private static Logger logger = Logger.getLogger(EbMasterController.class);
	private EbMasterService ebMasterService;
	
	@Autowired
	public void setEbMasterService(EbMasterService service){
		this.ebMasterService = service;
	}
	
	@RequestMapping("/ebMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("eb", new EbMasterVo());
		
		try {
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
			SiteMasterService.getInstance().populate(FederMasterService.getInstance().getAll());
			ObjectMapper mapper = new ObjectMapper();
//			mapper.enable(SerializationFeature.INDENT_OUTPUT);
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
	
			model.addAttribute("states", statesJson);
			model.addAttribute("customer", customer);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageEbMaster";
		
	}
	
	@RequestMapping("/ebMaster/create")
	public ModelAndView create(EbMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/ebMaster");
		
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		
		logger.debug("id :: "+vo.getId());
		logger.debug("name :: "+vo.getName());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("status :: "+vo.getWorkingStatus());
		logger.debug("site id :: "+vo.getSiteId());
		logger.debug("feder id :: "+vo.getFederId());
		logger.debug("customer Id :: "+vo.getCustomerId());
		
			
		try {
			boolean exist = ebMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = ebMasterService.create(vo);
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
	
	@RequestMapping("/ebMaster/update")
	public ModelAndView update(HttpServletRequest request, EbMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/ebMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		
		logger.debug("id :: "+vo.getId());
		logger.debug("name :: "+vo.getName());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("status :: "+vo.getWorkingStatus());
		logger.debug("site id :: "+vo.getSiteId());
		logger.debug("feder id :: "+vo.getFederId());
		logger.debug("customer Id :: "+vo.getCustomerId());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = ebMasterService.updateForMaster(vo);
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
