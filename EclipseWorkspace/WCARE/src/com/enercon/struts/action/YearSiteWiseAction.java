package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class YearSiteWiseAction extends Action{
	private static Logger logger = Logger.getLogger(YearSiteWiseAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.debug("Enter");
		request.setAttribute("Type", "Y");
		return mapping.findForward("success");
	}
}
