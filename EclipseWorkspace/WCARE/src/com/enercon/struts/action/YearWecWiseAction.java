package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class YearWecWiseAction extends Action{
	
	private static Logger logger = Logger.getLogger(YearWecWiseAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		request.setAttribute("Type", "YR");
		return mapping.findForward("success");
	}
}