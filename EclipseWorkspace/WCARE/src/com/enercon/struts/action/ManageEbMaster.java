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
import com.enercon.model.graph.EbMasterVo;
import com.enercon.service.master.EbMasterService;


public class ManageEbMaster extends DispatchAction {

private static Logger logger = Logger.getLogger(ManageEbMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EbMasterVo ebVo = (EbMasterVo) form;
		if(ebVo.getWorkingStatus() == null){
			ebVo.setWorkingStatus("1");
		}
		
		logger.debug("id :: "+ebVo.getId());
		logger.debug("name :: "+ebVo.getName());
		logger.debug("desc :: "+ebVo.getDescription());
		logger.debug("status :: "+ebVo.getWorkingStatus());
		logger.debug("site id :: "+ebVo.getSiteId());
		logger.debug("feder id :: "+ebVo.getFederId());
		logger.debug("customer Id :: "+ebVo.getCustomerId());
		
			
		String loginId = null;
		boolean create = false;
		boolean check = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			ebVo.setCreatedBy(loginId);
			ebVo.setModifiedBy(loginId);
			
			check = EbMasterService.getInstance().check(ebVo);	
			if(check){							
				messages.add("exists", new ActionMessage("err.master.exist", "EB"));
			}else{					
				create = EbMasterService.getInstance().create(ebVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "EB Data Created"));
					ebVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "EB Data Creation"));
				}
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "EB Data Creation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		EbMasterVo ebVo = (EbMasterVo) form;
		if(ebVo.getWorkingStatus() == null){
			ebVo.setWorkingStatus("1");
		}
		logger.debug("id :: "+ebVo.getId());
		logger.debug("name :: "+ebVo.getName());
		logger.debug("desc :: "+ebVo.getDescription());
		logger.debug("status :: "+ebVo.getWorkingStatus());
		logger.debug("site id :: "+ebVo.getSiteId());
		logger.debug("feder id :: "+ebVo.getFederId());
		logger.debug("customer Id :: "+ebVo.getCustomerId());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			ebVo.setModifiedBy(loginId);
			
			update = EbMasterService.getInstance().partialUpdate(ebVo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "EB Data Updated"));
				ebVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "EB Data Updation"));
			}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "EB Data Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	
}


