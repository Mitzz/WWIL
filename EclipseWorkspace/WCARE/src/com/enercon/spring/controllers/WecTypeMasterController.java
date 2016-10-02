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
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.spring.service.master.AreaMasterService;
import com.enercon.spring.service.master.WecTypeMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class WecTypeMasterController {
	
	private static Logger logger = Logger.getLogger(WecTypeMasterController.class);
	private WecTypeMasterService wecTypeMasterService;
	
	@Autowired
	public void setWecTypeMasterService(WecTypeMasterService service){
		this.wecTypeMasterService = service;
	}
	
	@RequestMapping("/wecTypeMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("wecType", new WecTypeMasterVo());
		List<WecTypeMasterVo> wecType = null;
		try {
			
			wecType = wecTypeMasterService.getAll();
			
			model.addAttribute("wecTypeVo", wecType);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageWecType";		
	}
	
	@RequestMapping("/wecTypeMaster/create")
	public ModelAndView create(WecTypeMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/wecTypeMaster");
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("cap :: "+vo.getCapacity());
		
		try {
			/*boolean exist = wecTypeMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = wecTypeMasterService.create(vo);*/
			if(true)
				mv.addObject("success", "Created");
			else
				mv.addObject("failure", "Not Created");
		} catch (Exception e) {
			mv.addObject("failure", "Not Created");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mv;
	}
	
	@RequestMapping("/wecTypeMaster/update")
	public ModelAndView update(HttpServletRequest request, WecTypeMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/wecTypeMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("cap :: "+vo.getCapacity());
		
		try {
			vo.setModifiedBy(loginId);
			//boolean update = wecTypeMasterService.updateForMaster(vo);
			if(true)
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
