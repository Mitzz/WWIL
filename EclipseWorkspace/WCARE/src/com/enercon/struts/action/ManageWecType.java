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
import com.enercon.model.master.WecTypeMasterVo;
import com.enercon.service.master.WecTypeMasterService;

public class ManageWecType extends DispatchAction {

private static Logger logger = Logger.getLogger(ManageWecType.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WecTypeMasterVo wecTypeVo = (WecTypeMasterVo) form;

		logger.debug("id :: "+wecTypeVo.getId());
		logger.debug("desc :: "+wecTypeVo.getDescription());
		logger.debug("cap :: "+wecTypeVo.getCapacity());
			
		String loginId = null;
		boolean create = false;
		boolean check = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			wecTypeVo.setCreatedBy(loginId);
			wecTypeVo.setModifiedBy(loginId);
			
			check = WecTypeMasterService.getInstance().check(wecTypeVo);	
			if(check){			
				logger.debug("Exits");
				messages.add("exists", new ActionMessage("err.wecType.exists"));
			}else{	
				logger.debug("NOT Exits");
				create = WecTypeMasterService.getInstance().create(wecTypeVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "WEC Type Added"));
					wecTypeVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Type Creation"));
				}
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Type Addition"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		WecTypeMasterVo wecTypeVo = (WecTypeMasterVo) form;
			
		logger.debug("id :: "+wecTypeVo.getId());
		logger.debug("desc :: "+wecTypeVo.getDescription());
		logger.debug("cap :: "+wecTypeVo.getCapacity());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			wecTypeVo.setModifiedBy(loginId);
			
			update = WecTypeMasterService.getInstance().update(wecTypeVo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "WEC Type Updated"));
				wecTypeVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Type Updation"));
			}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Type Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	
}
