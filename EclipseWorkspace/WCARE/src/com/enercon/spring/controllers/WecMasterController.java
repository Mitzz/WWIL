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
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.WecMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.FeederMasterService;
import com.enercon.service.master.SubstationMasterService;
import com.enercon.service.master.WecTypeMasterService;
import com.enercon.spring.service.master.WecMasterService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@Controller
public class WecMasterController {

	private static Logger logger = Logger.getLogger(WecMasterController.class);
	private WecMasterService wecMasterService;
	
	@Autowired
	public void setWecMasterService(WecMasterService service){
		this.wecMasterService = service;
	}
	
	@RequestMapping("/wecMaster")
	public String requestHandler(Model model) throws JsonProcessingException{
		model.addAttribute("wec", new WecMasterVo());
		
		try {
			
			List<IStateMasterVo> states = StateMasterService.getInstance().getAll();
			
			List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
			for(IStateMasterVo state: states){
				areas.addAll(state.getAreas());
			}
			
			List<SubstationMasterVo> substations = SubstationMasterService.getInstance().associateAreas(areas);
			FeederMasterService.getInstance().associateSubstations(substations);
			ObjectMapper objectMapper = new ObjectMapper();
			 
//			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			SimpleBeanPropertyFilter stateFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "areas");
			SimpleBeanPropertyFilter areaFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "sites", "substations");
			SimpleBeanPropertyFilter substationFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name", "id", "feeders");
			SimpleBeanPropertyFilter feederFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
			SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "customers");
			SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
			SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "wecs");
			SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name" , "id", "type" , "capacity" , "status" ,"multiFactor" ,"genComm","technicalNo","foundationNo","costPerUnit","machineAvailability","extGridAvailability","intGridAvailability");
			
			SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
			propertyFilter.addFilter("stateMasterVo", stateFilter);
			propertyFilter.addFilter("areaMasterVo", areaFilter);
			propertyFilter.addFilter("substationMasterVo", substationFilter);
			propertyFilter.addFilter("feederMasterVo", feederFilter);
			propertyFilter.addFilter("siteMasterVo", siteFilter);
			propertyFilter.addFilter("customerMasterVo", customerFilter);
			propertyFilter.addFilter("ebMasterVo", ebFilter);
			propertyFilter.addFilter("wecMasterVo", wecFilter);
			
		    FilterProvider filters = propertyFilter;

			String statesJson = objectMapper.writer(filters).writeValueAsString(states);
		//	logger.debug(""+statesJson);
			
			List<WecTypeMasterVo> wecType = WecTypeMasterService.getInstance().getAll();
			
			model.addAttribute("wecType", wecType);
			model.addAttribute("states", statesJson);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return "master/ManageWecMaster";		
	}
	
	@RequestMapping("/wecMaster/create")
	public ModelAndView create(WecMasterVo wecVo, HttpServletRequest request){
		logger.debug(wecVo);
		String loginId = MiscUtility.getLoginId(request);
		ModelAndView mv = new ModelAndView("forward:/spring/wecMaster");
		
		if(wecVo.getStatus() == null){
			wecVo.setStatus("2");
		}
		if(wecVo.getShow() == null){
			wecVo.setShow("1");
		}
		wecVo.setCommissionDate(DateUtility.convertDateFormats(wecVo.getCommissionDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setStartDate(DateUtility.convertDateFormats(wecVo.getStartDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setEndDate(DateUtility.convertDateFormats(wecVo.getEndDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		logger.debug("S_WEC_ID : "+wecVo.getId());
		logger.debug("S_WECSHORT_DESCR : "+wecVo.getName());
		logger.debug("S_CUSTOMER_ID : "+wecVo.getCustomerId());
		logger.debug("S_EB_ID : "+wecVo.getEbId());
		logger.debug("S_FOUND_LOC : "+wecVo.getFoundationNo());
		logger.debug("S_WEC_TYPE : "+wecVo.getType());
		logger.debug("N_MULTI_FACTOR : "+wecVo.getMultiFactor());
		logger.debug("N_WEC_CAPACITY : "+wecVo.getCapacity());
		logger.debug("D_COMMISION_DATE : "+wecVo.getCommissionDate());
		logger.debug("S_STATUS : "+wecVo.getStatus());
		logger.debug("N_GEN_COMM : "+wecVo.getGenComm());
		logger.debug("N_MAC_AVA : "+wecVo.getMachineAvailability());
		logger.debug("N_EXT_AVA : "+wecVo.getExtGridAvailability());
		logger.debug("N_INT_AVA : "+wecVo.getIntGridAvailability());
		logger.debug("D_START_DATE : "+wecVo.getStartDate());
		logger.debug("D_END_DATE : "+wecVo.getEndDate());
		logger.debug("S_FORMULA_NO : "+wecVo.getFormula());
		logger.debug("S_SHOW : "+wecVo.getShow());
		logger.debug("N_COST_PER_UNIT : "+wecVo.getCostPerUnit());
		logger.debug("S_TECHNICAL_NO : "+wecVo.getTechnicalNo());
		logger.debug("S_GUARANTEE_TYPE : "+wecVo.getGuaranteeType());
		logger.debug("S_CUSTOMER_TYPE : "+wecVo.getCustomerType());
		logger.debug("S_SCADA_FLAG : "+wecVo.getScadaStatus());
		logger.debug("S_FEEDER_ID : "+wecVo.getFeederId());
		logger.debug("N_PES_SCADA_STATUS : "+wecVo.getScadaStatus());
		
		try {
			/*boolean exist = wecMasterService.exist(wecVo);
			if(exist){
				mv.addObject("failure", "Already Exists");
				return mv;
			}
			wecVo.setCreatedBy(loginId);
			wecVo.setModifiedBy(loginId);
			boolean create = wecMasterService.create(wecVo);*/
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
	
	@RequestMapping("/wecMaster/update")
	public ModelAndView update(HttpServletRequest request, WecMasterVo wecVo, @RequestParam("id") String id){
		ModelAndView mv = new ModelAndView("forward:/spring/wecMaster");
		String loginId = MiscUtility.getLoginId(request);
		
		if(wecVo.getStatus() == null){
			wecVo.setStatus("2");
		}
		if(wecVo.getShow() == null){
			wecVo.setShow("1");
		}
		wecVo.setCommissionDate(DateUtility.convertDateFormats(wecVo.getCommissionDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setStartDate(DateUtility.convertDateFormats(wecVo.getStartDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setEndDate(DateUtility.convertDateFormats(wecVo.getEndDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		logger.debug("S_WEC_ID : "+wecVo.getId());
		logger.debug("S_WECSHORT_DESCR : "+wecVo.getName());
		logger.debug("S_CUSTOMER_ID : "+wecVo.getCustomerId());
		logger.debug("S_EB_ID : "+wecVo.getEbId());
		logger.debug("S_FOUND_LOC : "+wecVo.getFoundationNo());
		logger.debug("S_WEC_TYPE : "+wecVo.getType());
		logger.debug("N_MULTI_FACTOR : "+wecVo.getMultiFactor());
		logger.debug("N_WEC_CAPACITY : "+wecVo.getCapacity());
		logger.debug("D_COMMISION_DATE : "+wecVo.getCommissionDate());
		logger.debug("S_STATUS : "+wecVo.getStatus());
		logger.debug("N_GEN_COMM : "+wecVo.getGenComm());
		logger.debug("N_MAC_AVA : "+wecVo.getMachineAvailability());
		logger.debug("N_EXT_AVA : "+wecVo.getExtGridAvailability());
		logger.debug("N_INT_AVA : "+wecVo.getIntGridAvailability());
		logger.debug("D_START_DATE : "+wecVo.getStartDate());
		logger.debug("D_END_DATE : "+wecVo.getEndDate());
		logger.debug("S_FORMULA_NO : "+wecVo.getFormula());
		logger.debug("S_SHOW : "+wecVo.getShow());
		logger.debug("N_COST_PER_UNIT : "+wecVo.getCostPerUnit());
		logger.debug("S_TECHNICAL_NO : "+wecVo.getTechnicalNo());
		logger.debug("S_GUARANTEE_TYPE : "+wecVo.getGuaranteeType());
		logger.debug("S_CUSTOMER_TYPE : "+wecVo.getCustomerType());
		logger.debug("S_SCADA_FLAG : "+wecVo.getScadaStatus());
		logger.debug("S_FEEDER_ID : "+wecVo.getFeederId());
		logger.debug("N_PES_SCADA_STATUS : "+wecVo.getScadaStatus());
		
		try {
			wecVo.setModifiedBy(loginId);
			//boolean update = wecMasterService.updateForMaster(wecVo);
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
