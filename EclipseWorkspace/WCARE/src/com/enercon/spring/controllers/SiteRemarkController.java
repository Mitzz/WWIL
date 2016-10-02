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
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.spring.service.master.SiteMasterService;


import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
public class SiteRemarkController {

	private static Logger logger = Logger.getLogger(SiteRemarkController.class);
	private SiteMasterService siteMasterService;
	
	@Autowired
	public void setSiteMasterService(SiteMasterService service){
		this.siteMasterService = service;
	}
	
	@RequestMapping("/siteRemark")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("site", new SiteMasterVo());
		
		List<ISiteMasterVo> sites = null;
		List<SiteMasterVo> sitesRemarks = null; 
		try {
			 sites = siteMasterService.getAll();
			
			 sitesRemarks = siteMasterService.getRemarks(); 		
			
			model.addAttribute("sites", sites);
			model.addAttribute("sitesRemarks", sitesRemarks);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageSiteRemarks";		
	}
	
	/*@RequestMapping("/siteRemark/create")
	public ModelAndView create(SiteMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/siteRemark");
		
		logger.debug("id :: " +vo.getId());
		logger.debug("remarks :: " + vo.getRemark());
		
		try {
			boolean exist = siteMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = siteMasterService.create(vo);
			if(true)
				mv.addObject("success", "Created");
			else
				mv.addObject("failure", "Not Created");
		} catch (Exception e) {
			mv.addObject("failure", "Not Created");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mv;
	}*/
	
	@RequestMapping("/siteRemark/update")
	public ModelAndView update(HttpServletRequest request, SiteMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/siteRemark");
		String loginId = MiscUtility.getLoginId(request);
		
		logger.debug("id :: " +vo.getId());
		logger.debug("remarks :: " + vo.getRemark());
		
		try {
			boolean exist = siteMasterService.remarkExist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setModifiedBy(loginId);
			boolean update = siteMasterService.remarkUpdateForMaster(vo);
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
