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
import com.enercon.model.comparator.CustomerMasterVoComparator;
import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.spring.service.master.CustomerMasterService;

@Controller
public class CustomerMasterController {

	
	private static Logger logger = Logger.getLogger(CustomerMasterController.class);
	private CustomerMasterService customerMasterService;
	
	@Autowired
	public void setCustomerMasterService(CustomerMasterService service){
		this.customerMasterService = service;
	}
	

	@RequestMapping("/customerMaster")
	public String requestHandler(Model model){
		model.addAttribute("customer", new CustomerMasterVo());
		List<ICustomerMasterVo> customerMasterVos = null;
		try {
			customerMasterVos = customerMasterService.getAll();
			
			Collections.sort(customerMasterVos, CustomerMasterVoComparator.BY_NAME);
			model.addAttribute("customerMasterVos", customerMasterVos);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageCustomerMaster";
		
	}
	
	@RequestMapping("/customerMaster/create")
	public ModelAndView create(CustomerMasterVo vo, HttpServletRequest request){
		logger.debug(vo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/customerMaster");
		logger.debug("Enter");
		logger.debug("custId :: "+vo.getId() +" :: "+ vo.getName()+" :: "+ vo.getActive()+" :: "+ vo.getCellNo());
		logger.debug(vo.getContactPerson() +" :: "+ vo.getEmail()+" :: "+ vo.getFaxNo()+" :: "+ vo.getMarketingPerson());
		logger.debug(vo.getTelephoneNo()+" :: "+ vo.getSapCode());
		
		 if(vo.getActive() == null){
			 vo.setActive("0");
		 }	
		logger.debug("status :: "+ vo.getActive());
		try {
			boolean exist = customerMasterService.exist(vo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
			boolean create = customerMasterService.create(vo);
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
	
	@RequestMapping("/customerMaster/update")
	public ModelAndView update(HttpServletRequest request, CustomerMasterVo vo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/customerMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		logger.debug("Enter");
		logger.debug("custId :: "+vo.getId() +" :: "+ vo.getName()+" :: "+ vo.getActive()+" :: "+ vo.getCellNo());
		logger.debug(vo.getContactPerson() +" :: "+ vo.getEmail()+" :: "+ vo.getFaxNo()+" :: "+ vo.getMarketingPerson());
		logger.debug(vo.getTelephoneNo()+" :: "+ vo.getSapCode());
		
		 if(vo.getActive() == null){
			 vo.setActive("0");
		 }	
		logger.debug("status :: "+ vo.getActive());
		
		try {
			vo.setModifiedBy(loginId);
			boolean update = customerMasterService.updateForMaster(vo);
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
