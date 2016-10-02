package com.enercon.spring.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
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
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.table.EbMFactorVo;
import com.enercon.service.StateMasterService;
import com.enercon.spring.service.table.EbMFactorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class EbMFactorController {

	private static Logger logger = Logger.getLogger(EbMFactorController.class);
	private EbMFactorService ebMFactorService;
	
	@Autowired
	public void setEbMFactorService(EbMFactorService service){
		this.ebMFactorService = service;
	}
	
	
	@RequestMapping("/ebMFactor")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("ebMFactor", new EbMFactorVo());
		
		try {

			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
			List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
			for(IStateMasterVo state: states){
				sites.addAll(state.getSites());
			} 
			List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
			for(ISiteMasterVo site: sites){
				ebs.addAll(site.getEbs());
			} 
			ebMFactorService.associateEbMfs(ebs);
			ObjectMapper objectMapper = new ObjectMapper();
			 
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "sites");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
			SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebMfs");
			SimpleBeanPropertyFilter ebMfFilter = SimpleBeanPropertyFilter.filterOutAllExcept("multiFactor","id" ,"type","subType","capacity","fromDate","toDate");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("ebMasterVo", ebFilter);
			propertyFilter.addFilter("ebMFactorVo", ebMfFilter);
			
		    FilterProvider filters = propertyFilter;

			String statesJson = objectMapper.writer(filters).writeValueAsString(states);
			
			model.addAttribute("states", statesJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "table/ManageEbMFactor";
		
	}
	
	@RequestMapping("/ebMFactor/create")
	public ModelAndView create(EbMFactorVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/ebMFactor");
		
		vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		
		logger.debug("id :: "+vo.getId());
		logger.debug("EBid :: "+vo.getEbId());
		logger.debug("from date :: "+vo.getFromDate());
		logger.debug("to date :: "+vo.getToDate());
		logger.debug("multifactor :: "+vo.getMultiFactor());
		logger.debug("type :: "+vo.getType());
		logger.debug("subtype :: "+vo.getSubType());
		logger.debug("capacity :: "+vo.getCapacity());
		
		try {
			boolean exist = ebMFactorService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = ebMFactorService.create(vo);
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
	
	@RequestMapping("/ebMFactor/update")
	public ModelAndView update(HttpServletRequest request, EbMFactorVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/ebMFactor");
		String loginId = MiscUtility.getLoginId(request);
		
		vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		
		logger.debug("id :: "+vo.getId());
		logger.debug("EBid :: "+vo.getEbId());
		logger.debug("from date :: "+vo.getFromDate());
		logger.debug("to date :: "+vo.getToDate());
		logger.debug("multifactor :: "+vo.getMultiFactor());
		logger.debug("type :: "+vo.getType());
		logger.debug("subtype :: "+vo.getSubType());
		logger.debug("capacity :: "+vo.getCapacity());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = ebMFactorService.updateForMaster(vo);
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
