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
import com.enercon.model.master.LoginMasterVo;
import com.enercon.model.table.LoginDetailVo;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.LoginDetailService;
import com.enercon.service.master.LoginMasterService;

public class ManageActivateCustomerRP extends Action{

	private static Logger logger = Logger.getLogger(ManageActivateCustomerRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		List<LoginDetailVo> loginDetails = LoginDetailService.getInstance().getAll();
		List<LoginMasterVo> logins = LoginMasterService.getInstance().populate(loginDetails);
		List<ICustomerMasterVo> customers = CustomerMasterService.getInstance().populate(logins);
		return mapping.findForward("success");
	}
}
