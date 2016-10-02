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
import com.enercon.model.master.RemarkMasterVo;
import com.enercon.model.master.WecTypeMasterVo;

import com.enercon.service.master.WecTypeMasterService;
import com.enercon.spring.service.master.RemarkMasterService;

import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class RemarkMasterController {

	private static Logger logger = Logger.getLogger(RemarkMasterController.class);
	private RemarkMasterService remarkMasterService;
	
	@Autowired
	public void setRemarkMasterService(RemarkMasterService service){
		this.remarkMasterService = service;
	}
	
	@RequestMapping("/remarkMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("remark", new RemarkMasterVo());
		List<WecTypeMasterVo> wecType = null;
		List<RemarkMasterVo> remarks = null;
		try {
			 wecType = WecTypeMasterService.getInstance().getAll();
			 remarks = remarkMasterService.getAll();				
			
			model.addAttribute("wecType", wecType);
			model.addAttribute("remarks", remarks);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageRemarks";		
	}
	
	@RequestMapping("/remarkMaster/create")
	public ModelAndView create(RemarkMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/remarkMaster");
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("type :: "+vo.getType());
		logger.debug("wecType :: "+vo.getWecType());
		
		try {
			boolean exist = remarkMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = remarkMasterService.create(vo);
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
	
	@RequestMapping("/remarkMaster/update")
	public ModelAndView update(HttpServletRequest request, RemarkMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/remarkMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDescription());
		logger.debug("type :: "+vo.getType());
		logger.debug("wecType :: "+vo.getWecType());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = remarkMasterService.updateForMaster(vo);
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
