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
import com.enercon.model.master.FederMasterVo;
import com.enercon.service.master.FederMasterService;


public class ManageFederMaster extends DispatchAction{
	
	private static Logger logger = Logger.getLogger(ManageFederMaster.class);

	public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		FederMasterVo vo = (FederMasterVo) form;		
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		
		logger.debug(vo);
		logger.debug("id :"+vo.getId()+" :name :: "+vo.getName()+" :desc::  "+vo.getDescription()+" :status::"+vo.getWorkingStatus()+" :siteId:: "+vo.getSiteId());
		
		String loginId = null;
		boolean create = false;
		
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			vo.setCreatedBy(loginId);
			vo.setModifiedBy(loginId);
						
				create = FederMasterService.getInstance().create(vo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Feder Data Inserted"));
					vo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Feder Data Creation"));
				}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Feder Data Addition"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		FederMasterVo vo = (FederMasterVo) form;
		if(vo.getWorkingStatus() == null){
			vo.setWorkingStatus("1");
		}
		logger.debug(vo);
		logger.debug("id :"+vo.getId()+" :name :: "+vo.getName()+" :desc::  "+vo.getDescription()+" :status::"+vo.getWorkingStatus()+" :siteId:: "+vo.getSiteId());
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			vo.setModifiedBy(loginId);
			
			update = FederMasterService.getInstance().update(vo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Feder Updated"));
				vo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Feder Updation"));
			}	
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Feder Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		return mapping.findForward("success");
	}
}


