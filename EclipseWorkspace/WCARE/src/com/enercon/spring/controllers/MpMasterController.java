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
import com.enercon.model.master.MpMasterVo;
import com.enercon.spring.service.master.MpMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class MpMasterController {

	private static Logger logger = Logger.getLogger(MpMasterController.class);
	private MpMasterService mpMasterService;
	
	@Autowired
	public void setMpMasterService(MpMasterService service){
		this.mpMasterService = service;
	}
	
	@RequestMapping("/measuringPoint")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("mp", new MpMasterVo());
		List<MpMasterVo> meaPoint= null;
		try {
			
			meaPoint = mpMasterService.getAll();

			model.addAttribute("meaPoint", meaPoint);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageMeasuringPoint";		
	}
	
	@RequestMapping("/measuringPoint/create")
	public ModelAndView create(MpMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/measuringPoint");
		
		if(vo.getStatus() == null){
			vo.setStatus("1");
		}
		if(vo.getCumulative() == null){
			vo.setCumulative("2");
		}
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDesc());
		logger.debug("type :: "+vo.getType());
		logger.debug("show in :: "+vo.getShow());
		logger.debug("readtypwe :: "+vo.getReadType());
		logger.debug("unit :: "+vo.getUnit());
		logger.debug("seqNo :: "+vo.getSeqNo());
		logger.debug("status :: "+vo.getStatus());
		logger.debug("cumulative :: "+vo.getCumulative());
		
		try {
			/*boolean exist = mpMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = mpMasterService.create(vo);*/
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
	
	@RequestMapping("/measuringPoint/update")
	public ModelAndView update(HttpServletRequest request, MpMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/measuringPoint");
		String loginId = MiscUtility.getLoginId(request);
		
		if(vo.getStatus() == null){
			vo.setStatus("1");
		}
		if(vo.getCumulative() == null){
			vo.setCumulative("2");
		}
		
		logger.debug("id :: "+vo.getId());
		logger.debug("desc :: "+vo.getDesc());
		logger.debug("type :: "+vo.getType());
		logger.debug("show in :: "+vo.getShow());
		logger.debug("readtypwe :: "+vo.getReadType());
		logger.debug("unit :: "+vo.getUnit());
		logger.debug("seqNo :: "+vo.getSeqNo());
		logger.debug("status :: "+vo.getStatus());
		logger.debug("cumulative :: "+vo.getCumulative());
		
		try {
			vo.setModifiedBy(loginId);
			//boolean update = mpMasterService.updateForMaster(vo);
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
