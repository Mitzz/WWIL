package com.enercon.struts.action;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.struts.model.SelectVoCallableTask;

public class AreaWiseMGSAvailabiltyReportRequestProcessor extends Action{
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)

	throws Exception {
			
		 List<SelectVo> stateSelectVo = new SelectVoCallableTask("StateMaster").call();		
		request.setAttribute("StateMasterSelectVo", stateSelectVo);
		
		return mapping.findForward("success");

	}
}
