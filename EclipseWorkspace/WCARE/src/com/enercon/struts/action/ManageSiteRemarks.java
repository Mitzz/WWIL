package com.enercon.struts.action;

import java.util.List;

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
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.service.SiteMasterService;
import com.enercon.service.master.WecTypeMasterService;


public class ManageSiteRemarks extends Action{

private static Logger logger = Logger.getLogger(ManageSiteRemarks.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		
		SiteMasterVo siteVo =(SiteMasterVo) form;
		
		logger.debug("id :: " +siteVo.getId());
		logger.debug("remarks :: " + siteVo.getRemark());
		
		String loginId = null;
		boolean update = false;
		boolean checkRemark = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);			
			siteVo.setModifiedBy(loginId);
			
			//checkRemark = SiteMasterService.getInstance().checkRemark(siteVo);	
			if(checkRemark){			
				logger.debug("Exits");
				messages.add("exists", new ActionMessage("err.remarks.exists"));
			}else{	
				logger.debug("NOT Exits");
				//update = SiteMasterService.getInstance().updateRemark(siteVo);
				if(update){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Remarks Added"));
					siteVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remarks Addition"));
				}
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Remarks Addition"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		return mapping.findForward("success");
	}

}
