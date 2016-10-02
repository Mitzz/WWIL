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
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;
import com.enercon.service.master.SubstationMasterService;

public class ManageSubstationMaster extends DispatchAction{


	private final static Logger logger = Logger.getLogger(ManageAreaMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SubstationMasterVo substationVo = (SubstationMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(substationVo);
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			substationVo.setCreatedBy(loginId);
			substationVo.setModifiedBy(loginId);
			logger.debug("Area Id: " + substationVo.getAreaId());
//			IAreaMasterVo area = AreaMasterService.getInstance().get(substationVo.getAreaId());
//			substationVo.setArea(area);
			
			create = SubstationMasterService.getInstance().create(substationVo);
			
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				substationVo.reset(mapping, request);
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
		SubstationMasterVo substationVo = (SubstationMasterVo) form;
		
		logger.debug("Enter");
		logger.debug(substationVo);
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			substationVo.setModifiedBy(loginId);
			logger.debug("Area Id: " + substationVo.getAreaId());
//			IAreaMasterVo area = AreaMasterService.getInstance().get(substationVo.getAreaId());
//			substationVo.setArea(area);
			
			update = SubstationMasterService.getInstance().update(substationVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				substationVo.reset(mapping, request);
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
