package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.service.CustomerMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageTransferWecRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageTransferWecRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		List<ICustomerMasterVo> customers = CustomerMasterService.getInstance().getAll();
		
		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
		SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "wecs");
		SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "status");
		
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("customerMasterVo", customerFilter);
		propertyFilter.addFilter("ebMasterVo", ebFilter);
		propertyFilter.addFilter("wecMasterVo", wecFilter);
		
	    FilterProvider filters = propertyFilter;
	    
		// Converting List of Java Object to List of Javascript Object
		String customersJson = mapper.writer(filters).writeValueAsString(customers);
//		logger.debug(customersJson);
		
		request.setAttribute("customers", customersJson);
		logger.debug("left");
		return mapping.findForward("success");
	}
}
