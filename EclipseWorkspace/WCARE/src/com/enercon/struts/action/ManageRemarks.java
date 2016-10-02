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
import com.enercon.model.master.RemarkMasterVo;
import com.enercon.service.master.RemarkMasterService;
import com.enercon.service.master.WecTypeMasterService;


public class ManageRemarks extends DispatchAction {

private static Logger logger = Logger.getLogger(ManageWecType.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RemarkMasterVo remarkVo = (RemarkMasterVo) form;

		logger.debug("id :: "+remarkVo.getId());
		logger.debug("desc :: "+remarkVo.getDescription());
		logger.debug("type :: "+remarkVo.getType());
		logger.debug("wecType :: "+remarkVo.getWecType());
		
			
		String loginId = null;
		boolean create = false;
		boolean check = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			remarkVo.setCreatedBy(loginId);
			remarkVo.setModifiedBy(loginId);
			 
			check = RemarkMasterService.getInstance().check(remarkVo);	
			if(check){							
				messages.add("exists", new ActionMessage("err.master.exist", "Remarks"));
			}else{	
				create = RemarkMasterService.getInstance().create(remarkVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Remark Added"));
					remarkVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remark Creation"));
				}
			}	
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remark Addition"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		RemarkMasterVo remarkVo = (RemarkMasterVo) form;
			
		logger.debug("id :: "+remarkVo.getId());
		logger.debug("desc :: "+remarkVo.getDescription());
		logger.debug("type :: "+remarkVo.getType());
		logger.debug("wecType :: "+remarkVo.getWecType());
		
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			remarkVo.setModifiedBy(loginId);
			
			update = RemarkMasterService.getInstance().update(remarkVo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Remark Updated"));
				remarkVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remark Updation"));
			}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remark Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	
}
