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
import com.enercon.model.comparator.StateMasterVoComparator;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.StateMasterVo;
import com.enercon.spring.service.master.StateMasterService;

@Controller
public class StateMasterController {
	
	private static Logger logger = Logger.getLogger(StateMasterController.class);
	private StateMasterService stateMasterService;
	
	@Autowired
	public void setStateMasterService(StateMasterService service){
		this.stateMasterService = service;
	}

	@RequestMapping("/stateMaster")
	public String requestHandler(Model model){
		model.addAttribute("state", new StateMasterVo());
		List<IStateMasterVo> stateMasterVos = null;
		try {
			stateMasterVos = stateMasterService.getAll();
			logger.debug(stateMasterVos.size());
			Collections.sort(stateMasterVos, StateMasterVoComparator.BY_NAME);
			model.addAttribute("stateMasterVos", stateMasterVos);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageStateMaster";
		
	}
	
	@RequestMapping("/stateMaster/create")
	public ModelAndView create(StateMasterVo state, HttpServletRequest request){
		logger.debug(state);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/stateMaster");
		try {
			boolean exist = stateMasterService.exist(state);
			if(exist){
				mv.addObject("failure", "State Name already Exists");
				return mv;
			}
			state.setCreatedBy(loginId);
			state.setModifiedBy(loginId);
			boolean create = stateMasterService.create(state);
			if(create)
				mv.addObject("success", "State Created");
			else
				mv.addObject("failure", "State Not Created");
		} catch (Exception e) {
			mv.addObject("failure", "State Not Created");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return mv;
	}
	
	@RequestMapping("/stateMaster/update")
	public ModelAndView update(HttpServletRequest request, StateMasterVo state, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/stateMaster");
		String loginId = MiscUtility.getLoginId(request);
		try {
			state.setModifiedBy(loginId);
			boolean update = stateMasterService.updateForMaster(state);
			if(update)
				mv.addObject("success", "State Updated");
			else
				mv.addObject("failure", "State Not Updated");
		} catch (Exception e) {
			mv.addObject("failure", "State Not Updated");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mv;
	}
	
	
}
