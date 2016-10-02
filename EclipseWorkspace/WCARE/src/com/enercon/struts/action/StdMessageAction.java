package com.enercon.struts.action;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

import com.enercon.model.master.StandardMessageMasterVo;
import com.enercon.service.StdMessageService;

public class StdMessageAction extends DispatchAction{

	private final static Logger logger = Logger.getLogger(StdMessageAction.class);
	
	public ActionForward createStandardMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		logger.debug("Enter");
		logger.debug(form);
		StandardMessageMasterVo vo = (StandardMessageMasterVo) form;
		 
		String loginId = request.getSession().getAttribute("loginID").toString();
		
		vo.setCreatedBy(loginId);
		vo.setModifiedBy(loginId);
		
		StdMessageService service = StdMessageService.getInstance();
		ActionMessages messages = new ActionMessages();
		
		try{
			boolean isCreated = service.create(vo);
			logger.debug("isCreated: " + isCreated);
			if(isCreated){
				messages.add("SuccessfullyCreated", new ActionMessage("success.stdmessage.created"));
			}
			vo.reset(mapping, request);
		} catch (SQLException  e) {
			messages.add("NotCreated", new ActionMessage("err.stdmessage.created"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n");
			
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
