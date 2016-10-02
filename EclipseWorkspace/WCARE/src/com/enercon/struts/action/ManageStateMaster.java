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

import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.graph.StateMasterVo;
import com.enercon.service.StateMasterService;

public class ManageStateMaster extends DispatchAction{
	private final static Logger logger = Logger.getLogger(ManageStateMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		StateMasterVo formVo = (StateMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(formVo);
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			
			if(!StateMasterService.getInstance().exist(formVo)){
				loginId = MiscUtility.getLoginId(request);
				formVo.setCreatedBy(loginId);
				formVo.setModifiedBy(loginId);
				create = StateMasterService.getInstance().create(formVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "State Created"));
					formVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "State Creation"));
				}
			} else {
				messages.add("error", new ActionMessage("err.master.exist", "State"));
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Creation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		StateMasterVo stateMasterVo = (StateMasterVo) form;
		logger.debug("Enter");
		logger.debug(stateMasterVo);
        String loginId = null;
		boolean update = false;
		
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			stateMasterVo.setModifiedBy(loginId);
			
			update = StateMasterService.getInstance().updateForMaster(stateMasterVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				stateMasterVo.reset(mapping, request);
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
