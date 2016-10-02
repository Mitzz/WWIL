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

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.master.FederMasterVo;
import com.enercon.model.table.FederMFactorVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FederMasterService;

import com.enercon.spring.service.table.FederMFactorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class FederMFactorController {

	private static Logger logger = Logger.getLogger(FederMFactorController.class);
	private FederMFactorService federMFactorService;
	
	@Autowired
	public void setFederMFactorService(FederMFactorService service){
		this.federMFactorService = service;
	}
	
	
	@RequestMapping("/federMFactor")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("federMFactor", new FederMFactorVo());
		List<FederMasterVo> feders = null;
		try {

			feders = FederMasterService.getInstance().getAll();
			List<ISiteMasterVo> sites = SiteMasterService.getInstance().populate(feders);
			List<IStateMasterVo> states = StateMasterService.getInstance().populate(sites);
			federMFactorService.associateFederMfs(feders);
			
			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.enable(SerializationFeature.INDENT_OUTPUT); 
			SimpleBeanPropertyFilter federMfFilter = SimpleBeanPropertyFilter.filterOutAllExcept("multiFactor","id" ,"type","subType","capacity","fromDate","toDate");
			SimpleBeanPropertyFilter federFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id","federMfs" );
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feders", "code");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites");
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "areas", "sites", "sapCode");
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("federMFactorVo", federMfFilter);
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
		
		return "table/ManageFederMFactor";
		
	}
	
	@RequestMapping("/federMFactor/create")
	public ModelAndView create(FederMFactorVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/federMFactor");
		
		vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		
		logger.debug("id :: "+vo.getId());
		logger.debug("Federid :: "+vo.getFederId());
		logger.debug("from date :: "+vo.getFromDate());
		logger.debug("to date :: "+vo.getToDate());
		logger.debug("multifactor :: "+vo.getMultiFactor());
		logger.debug("type :: "+vo.getType());
		logger.debug("subtype :: "+vo.getSubType());
		logger.debug("capacity :: "+vo.getCapacity());
		try {
			boolean exist = federMFactorService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = federMFactorService.create(vo);
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
	
	@RequestMapping("/federMFactor/update")
	public ModelAndView update(HttpServletRequest request, FederMFactorVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/federMFactor");
		String loginId = MiscUtility.getLoginId(request);
		
		vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		
		logger.debug("id :: "+vo.getId());
		logger.debug("Federid :: "+vo.getFederId());
		logger.debug("from date :: "+vo.getFromDate());
		logger.debug("to date :: "+vo.getToDate());
		logger.debug("multifactor :: "+vo.getMultiFactor());
		logger.debug("type :: "+vo.getType());
		logger.debug("subtype :: "+vo.getSubType());
		logger.debug("capacity :: "+vo.getCapacity());
		try {
			vo.setModifiedBy(loginId);
			boolean update = federMFactorService.updateForMaster(vo);
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
