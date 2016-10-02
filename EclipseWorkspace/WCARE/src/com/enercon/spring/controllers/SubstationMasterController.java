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
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;
import com.enercon.service.master.FeederMasterService;
import com.enercon.spring.service.master.SubstationMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SubstationMasterController {

	
	private static Logger logger = Logger.getLogger(SubstationMasterController.class);
	private SubstationMasterService substationMasterService;
	
	@Autowired
	public void setSubstationMasterService(SubstationMasterService service){
		this.substationMasterService = service;
	}
	
	@RequestMapping("/substationMaster")
	public String requestHandler(Model model){
		model.addAttribute("substation", new SubstationMasterVo());
		
		try {
			
			List<IAreaMasterVo> areas = AreaMasterService.getInstance().getAll();
			List<SubstationMasterVo> substations = substationMasterService.getAll();
			FeederMasterService.getInstance().associateSubstations(substations);
			Collections.sort(areas, AreaMasterVoComparator.BY_NAME);
			
			model.addAttribute("areas", areas);
			model.addAttribute("substations", substations);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageSubstationMaster";		
	}
	
	@RequestMapping("/substationMaster/create")
	public ModelAndView create(SubstationMasterVo substation, HttpServletRequest request){
		logger.debug(substation);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/substationMaster");
		try {
			boolean exist = substationMasterService.exist(substation);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			substation.setCreatedBy(loginId);
			substation.setModifiedBy(loginId);
			boolean create = substationMasterService.create(substation);
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
	
	@RequestMapping("/substationMaster/update")
	public ModelAndView update(HttpServletRequest request, SubstationMasterVo substation, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/substationMaster");
		String loginId = MiscUtility.getLoginId(request);
		logger.debug("id : "+ substation.getId());
		logger.debug("state id : "+ substation.getName());
		logger.debug("code : "+ substation.getRemark());
		
		try {
			substation.setModifiedBy(loginId);
			boolean update = substationMasterService.updateForMaster(substation);
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
