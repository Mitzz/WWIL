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
import com.enercon.model.master.FeederMasterVo;
import com.enercon.service.master.FeederMasterService;



public class ManageFeederMaster extends DispatchAction{
	private static Logger logger = Logger.getLogger(ManageFeederMaster.class);

	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		FeederMasterVo feederVo = (FeederMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(feederVo);
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			feederVo.setCreatedBy(loginId);
			feederVo.setModifiedBy(loginId);
			logger.debug("Substation Id: " + feederVo.getSubstationId());
			
			
			create = FeederMasterService.getInstance().create(feederVo);
			
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				feederVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Creation"));
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
		FeederMasterVo feederVo = (FeederMasterVo) form;
		
		logger.debug("Enter");
		logger.debug("feeder :: "+feederVo);
		logger.debug("feeder name :: "+feederVo.getName());
		logger.debug("substation :: "+feederVo.getSubstation());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			feederVo.setModifiedBy(loginId);
					
			update = FeederMasterService.getInstance().update(feederVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				feederVo.reset(mapping, request);
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
