package com.enercon.struts.requestProcessor;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.master.LoginMasterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageCustomerLoginRP extends Action{
	
	private static Logger logger = Logger.getLogger(ManageCustomerLoginRP.class);
	private static Graph G = Graph.getInstance();
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		List<LoginMasterVo> logins = LoginMasterService.getInstance().getAll();
		List<ICustomerMasterVo> customers = CustomerMasterService.getInstance().populate(logins);

		ObjectMapper objectMapper = new ObjectMapper();
		 
		SimpleBeanPropertyFilter loginFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","userId", "loginDescription","customers", "loginType");
		SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id","wecs");
		SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id","scadaStatus");
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("loginMasterVo", loginFilter);
		propertyFilter.addFilter("customerMasterVo", customerFilter);
		propertyFilter.addFilter("wecMasterVo", wecFilter);
		
	    FilterProvider filters = propertyFilter;
	    
		// Converting List of Java Object to List of Javascript Object
		String loginJson = objectMapper.writer(filters).writeValueAsString(logins);
		
		customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
		SimpleFilterProvider propertyFilter2 = new SimpleFilterProvider();
		propertyFilter2.addFilter("customerMasterVo", customerFilter);
		
		filters = propertyFilter2;
		String customerJson = objectMapper.writer(filters).writeValueAsString(customers);
		
		request.setAttribute("logins", loginJson);
		request.setAttribute("customers", customerJson);

		return mapping.findForward("success");
	}
	
	public static void main(String args[]) throws SQLException, JsonProcessingException{
		
		/*List<LoginMasterVo> logins = LoginMasterService.getInstance().getAll();
		CustomerMasterService.getInstance().populate(logins);

		ObjectMapper objectMapper = new ObjectMapper();
		 
		SimpleBeanPropertyFilter loginFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id","userId", "loginDescription","customers");
		SimpleBeanPropertyFilter customerFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id","wecs");
		SimpleBeanPropertyFilter wecFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id","scadaStatus","status");
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("loginMasterVo", loginFilter);
		propertyFilter.addFilter("customerMasterVo", customerFilter);
		propertyFilter.addFilter("wecMasterVo", wecFilter);
		
	    FilterProvider filters = propertyFilter;
	    
		// Converting List of Java Object to List of Javascript Object
		String loginJson = objectMapper.writer(filters).writeValueAsString(logins);
		logger.debug(loginJson);*/
		
		/*IWecMasterVo wec = WecMasterService.getInstance().get("0809000028");
		logger.debug("wecId : "+wec.getId()+" :scadaStatus: "+wec.getScadaStatus()+" :wecName: "+wec.getName());*/
	

	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Date date = new Date();
		logger.debug(dateFormat.format(date));
	}
		
}
