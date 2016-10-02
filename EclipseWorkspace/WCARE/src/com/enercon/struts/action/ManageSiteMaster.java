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
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.service.SiteMasterService;

public class ManageSiteMaster extends DispatchAction{

	private static Logger logger = Logger.getLogger(ManageSiteMaster.class);

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		SiteMasterVo siteVo = (SiteMasterVo)form;
		
		logger.debug("craete sitreVo :: " + siteVo.getId() +" :: "+ siteVo.getName()+" :: ");
		logger.debug("craete sitreVo :: " + siteVo.getCode() +" :: "+ siteVo.getIncharge()+" :: "+ siteVo.getAddress());
		logger.debug("stateid :: " + siteVo.getStateId());
		logger.debug("areaid :: " + siteVo.getAreaId());
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			siteVo.setCreatedBy(loginId);
			siteVo.setModifiedBy(loginId);

			create = SiteMasterService.getInstance().create(siteVo);
			
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				siteVo.reset(mapping, request);
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
		
		SiteMasterVo siteVo = (SiteMasterVo)form;
		
		logger.debug("update sitreVo :: " + siteVo.getId() +" :: "+ siteVo.getName()+" :: ");
		logger.debug("update sitreVo :: " + siteVo.getCode() +" :: "+ siteVo.getIncharge()+" :: "+ siteVo.getAddress());
		logger.debug("stateid :: " + siteVo.getStateId());
		logger.debug("areaid :: " + siteVo.getAreaId());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			siteVo.setModifiedBy(loginId);
			
		    update = SiteMasterService.getInstance().update(siteVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				siteVo.reset(mapping, request);
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
