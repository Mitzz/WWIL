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
import com.enercon.model.master.MpMasterVo;
import com.enercon.service.master.MpMasterService;


public class ManageMeaPoint extends DispatchAction {

private static Logger logger = Logger.getLogger(ManageMeaPoint.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MpMasterVo mpVo = (MpMasterVo) form;
		if(mpVo.getStatus() == null){
			mpVo.setStatus("1");
		}
		if(mpVo.getCumulative() == null){
			mpVo.setCumulative("2");
		}
		
		logger.debug("id :: "+mpVo.getId());
		logger.debug("desc :: "+mpVo.getDesc());
		logger.debug("type :: "+mpVo.getType());
		logger.debug("show in :: "+mpVo.getShow());
		logger.debug("readtypwe :: "+mpVo.getReadType());
		logger.debug("unit :: "+mpVo.getUnit());
		logger.debug("seqNo :: "+mpVo.getSeqNo());
		logger.debug("status :: "+mpVo.getStatus());
		logger.debug("cumulative :: "+mpVo.getCumulative());
			
		String loginId = null;
		boolean create = false;
		boolean check = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			mpVo.setCreatedBy(loginId);
			mpVo.setModifiedBy(loginId);
			
			check = MpMasterService.getInstance().check(mpVo);	
			if(check){							
				messages.add("exists", new ActionMessage("err.mp.exists"));
			}else{					
				create = MpMasterService.getInstance().create(mpVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Measure Point Created"));
					mpVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Measure Point Creation"));
				}
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Measure Point Creation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		MpMasterVo mpVo = (MpMasterVo) form;
		if(mpVo.getStatus() == null){
			mpVo.setStatus("1");
		}
		if(mpVo.getCumulative() == null){
			mpVo.setCumulative("2");
		}
		
		logger.debug("id :: "+mpVo.getId());
		logger.debug("desc :: "+mpVo.getDesc());
		logger.debug("type :: "+mpVo.getType());
		logger.debug("show in :: "+mpVo.getShow());
		logger.debug("readtypwe :: "+mpVo.getReadType());
		logger.debug("unit :: "+mpVo.getUnit());
		logger.debug("seqNo :: "+mpVo.getSeqNo());
		logger.debug("status :: "+mpVo.getStatus());
		logger.debug("cumulative :: "+mpVo.getCumulative());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			mpVo.setModifiedBy(loginId);
			
			update = MpMasterService.getInstance().update(mpVo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Measure Point Data Updated"));
				mpVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Measure Point Data Updation"));
			}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Measure Point Data Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	
}

