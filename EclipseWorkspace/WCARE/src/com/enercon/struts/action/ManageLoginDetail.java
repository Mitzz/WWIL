package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.enercon.model.table.LoginDetailVo;

public class ManageLoginDetail extends DispatchAction{

	private static Logger logger = Logger.getLogger(ManageLoginDetail.class);
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		LoginDetailVo loginDetail = (LoginDetailVo) form;
		
		return mapping.findForward("success");
	}
}
