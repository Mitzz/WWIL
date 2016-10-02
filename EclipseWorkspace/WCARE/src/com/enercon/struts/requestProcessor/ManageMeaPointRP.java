package com.enercon.struts.requestProcessor;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.enercon.model.master.MpMasterVo;
import com.enercon.service.master.MpMasterService;

public class ManageMeaPointRP  extends Action {

	private static Logger logger = Logger.getLogger(ManageMeaPointRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");

		List<MpMasterVo> meaPoint = MpMasterService.getInstance().getAll();
		
		request.setAttribute("meaPoint", meaPoint);
		
		return mapping.findForward("success");
	}
}
