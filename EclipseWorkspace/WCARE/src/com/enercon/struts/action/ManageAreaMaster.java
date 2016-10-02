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
import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.service.StateMasterService;
import com.enercon.service.master.AreaMasterService;

public class ManageAreaMaster extends DispatchAction{
	private final static Logger logger = Logger.getLogger(ManageAreaMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		AreaMasterVo areaVo = (AreaMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(areaVo);
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			areaVo.setCreatedBy(loginId);
			areaVo.setModifiedBy(loginId);
			logger.debug("State Id: " + areaVo.getStateId());
			IStateMasterVo state = StateMasterService.getInstance().get(areaVo.getStateId());
			areaVo.setState(state);
			
			create = AreaMasterService.getInstance().create(areaVo);
			
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				areaVo.reset(mapping, request);
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
		AreaMasterVo areaVo = (AreaMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(areaVo);
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			areaVo.setModifiedBy(loginId);
			logger.debug("State Id: " + areaVo.getStateId());
			IStateMasterVo state = StateMasterService.getInstance().get(areaVo.getStateId());
			areaVo.setState(state);
			
			update = AreaMasterService.getInstance().update(areaVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				areaVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Updation"));
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Creation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	
}
