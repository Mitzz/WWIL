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

public class ManageCustomerMasterRP extends Action {

	private static Logger logger = Logger.getLogger(ManageCustomerMasterRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		try {
			List<ICustomerMasterVo> customerMasterVos = CustomerMasterService.getInstance().getAll();
			request.setAttribute("customerMasterVos", customerMasterVos);

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mapping.findForward("success");
	}

}
