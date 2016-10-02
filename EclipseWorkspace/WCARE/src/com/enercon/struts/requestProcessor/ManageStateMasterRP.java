package com.enercon.struts.requestProcessor;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.comparator.StateMasterVoComparator;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.service.StateMasterService;

public class ManageStateMasterRP extends Action{

	private static Logger logger = Logger.getLogger(ManageStateMasterRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		try{
			List<IStateMasterVo> stateMasterVos =  StateMasterService.getInstance().getAll();
			Collections.sort(stateMasterVos, StateMasterVoComparator.BY_NAME);
			request.setAttribute("stateMasterVos", stateMasterVos);
			
		} catch (Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mapping.findForward("success");
	}
	
}
