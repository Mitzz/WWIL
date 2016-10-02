package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.CustomerLoginVo;
import com.enercon.service.CustomerMasterService;

public class ManageCustomerLogin extends Action{

	private final static Logger logger = Logger.getLogger(ManageCustomerLogin.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		CustomerLoginVo customerLoginVo = (CustomerLoginVo) form;
		
		logger.debug(customerLoginVo);
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			
			loginId = MiscUtility.getLoginId(request);					
			
			update = CustomerMasterService.getInstance().updateLogin(customerLoginVo, loginId);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				//loginVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Updation"));
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
