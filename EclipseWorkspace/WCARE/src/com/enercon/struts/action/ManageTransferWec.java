package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.transfer.TransferWecVo;

public class ManageTransferWec extends Action{
	
	private static Logger logger = Logger.getLogger(ManageTransferWec.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		TransferWecVo vo = (TransferWecVo) form;
		logger.debug(vo);
		logger.debug("left");
		return mapping.findForward("success");
	}
}
