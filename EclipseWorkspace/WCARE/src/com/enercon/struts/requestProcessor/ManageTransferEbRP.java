package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.service.SiteMasterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ManageTransferEbRP extends Action{

	private static Logger logger = Logger.getLogger(ManageTransferEbRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		logger.debug("enter");
		/*session.setAttribute("loginID", sLoginID);
		session.setAttribute("pwd", sPassWord);
		session.setAttribute("Name", loginMasterVo.getLoginDescription());
		session.setAttribute("RoleID", sroleID);*/
		String roleId = (String) request.getSession().getAttribute("RoleID");
		String userId = (String) request.getSession().getAttribute("loginID");
		logger.debug(String.format("Role: %s, User Id: %s", roleId, userId));
		
		SiteMasterService service = SiteMasterService.getInstance();
		List<ISiteMasterVo> sites = null;
		if(roleId.equals("0000000001") || "1311000001".equals(roleId)){
			sites = service.getAll();
		} else {
			sites = service.getAll(userId);
		}
		
		ObjectMapper mapper = new ObjectMapper();
//		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		SimpleBeanPropertyFilter siteFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id", "ebs");
		SimpleBeanPropertyFilter ebFilter = SimpleBeanPropertyFilter.filterOutAllExcept("name","id");
		
		SimpleFilterProvider propertyFilter = new SimpleFilterProvider();
		propertyFilter.addFilter("siteMasterVo", siteFilter);
		propertyFilter.addFilter("ebMasterVo", ebFilter);
		
	    FilterProvider filters = propertyFilter;
	    
		// Converting List of Java Object to List of Javascript Object
		String sitesJson = mapper.writer(filters).writeValueAsString(sites);
//		logger.debug(sitesJson);
		request.setAttribute("sites", sitesJson);
		return mapping.findForward("success");
	}
}
