package com.enercon.struts.action;

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
import com.enercon.struts.form.StdMessageForm;

public class StdMessageAction extends DispatchAction{

	private final static Logger logger = Logger.getLogger(StdMessageAction.class);
	
	public ActionForward createStandardMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("Enter");
		logger.debug(form);
		StdMessageForm f = (StdMessageForm) form;
		StandardMessageMasterVo vo = new StandardMessageMasterVo();
		
		StdMessageService service = StdMessageService.getInstance();
		ActionMessages messages = new ActionMessages();
		boolean isCreated = service.create(vo);
		logger.debug("isCreated: " + isCreated);
		if(isCreated){
			messages.add("SuccessfullyCreated", new ActionMessage("success.stdmessage.created"));
			f.reset();
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
