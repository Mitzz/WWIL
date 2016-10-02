package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.master.WecTypeMasterService;

public class ManageWecTypeRP extends Action{
private static Logger logger = Logger.getLogger(ManageWecTypeRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		List<WecTypeMasterVo> wecType = WecTypeMasterService.getInstance().getAll();
	
		request.setAttribute("wecType", wecType);
		return mapping.findForward("success");
	}
}
