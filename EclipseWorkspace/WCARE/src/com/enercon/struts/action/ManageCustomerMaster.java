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
import com.enercon.model.graph.CustomerMasterVo;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.service.CustomerMasterService;


public class ManageCustomerMaster extends DispatchAction{

	private final static Logger logger = Logger.getLogger(ManageCustomerMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CustomerMasterVo customerVo = (CustomerMasterVo) form;
		
		logger.debug("Enter");
		logger.debug("custId :: "+customerVo.getId() +" :: "+ customerVo.getName()+" :: "+ customerVo.getActive()+" :: "+ customerVo.getCellNo());
		logger.debug(customerVo.getContactPerson() +" :: "+ customerVo.getEmail()+" :: "+ customerVo.getFaxNo()+" :: "+ customerVo.getMarketingPerson());
		logger.debug(customerVo.getTelephoneNo()+" :: "+ customerVo.getSapCode());
		
		 if(customerVo.getActive() == null){
			 customerVo.setActive("0");
		 }	
		logger.debug("status :: "+ customerVo.getActive());
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			customerVo.setCreatedBy(loginId);
			customerVo.setModifiedBy(loginId);
			
			create = CustomerMasterService.getInstance().create(customerVo);
			
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				customerVo.reset(mapping, request);
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
		CustomerMasterVo customerVo = (CustomerMasterVo) form;
		
		logger.debug("Enter");
		logger.debug("custId :: "+customerVo.getId() +" :: "+ customerVo.getName()+" :: "+ customerVo.getActive()+" :: "+ customerVo.getCellNo());
		logger.debug(customerVo.getContactPerson() +" :: "+ customerVo.getEmail()+" :: "+ customerVo.getFaxNo()+" :: "+ customerVo.getMarketingPerson());
		logger.debug(customerVo.getTelephoneNo()+" :: "+ customerVo.getSapCode());
		
		 if(customerVo.getActive() == null){
			 customerVo.setActive("0");
		 }	
		logger.debug("status :: "+ customerVo.getActive());
		
        String loginId = null;
		boolean update = false;
		
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			
			customerVo.setModifiedBy(loginId);

			update = CustomerMasterService.getInstance().update(customerVo);
			
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				customerVo.reset(mapping, request);
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
	
	
	public static void main(String args[]){
		
	ICustomerMasterVo customer  = CustomerMasterService.getInstance().get("1209000007");
	
	//logger.debug("size : " + customer.getSapCode());
		
		
	}
}
