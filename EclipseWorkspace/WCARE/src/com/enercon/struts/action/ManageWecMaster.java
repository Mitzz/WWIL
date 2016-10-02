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

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.graph.WecMasterVo;
import com.enercon.service.WecMasterService;

public class ManageWecMaster extends DispatchAction{
	
	private static Logger logger = Logger.getLogger(ManageWecMaster.class);
	
	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		WecMasterVo wecVo = (WecMasterVo)form;
		if(wecVo.getStatus() == null){
			wecVo.setStatus("2");
		}
		if(wecVo.getShow() == null){
			wecVo.setShow("1");
		}
		wecVo.setCommissionDate(DateUtility.convertDateFormats(wecVo.getCommissionDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setStartDate(DateUtility.convertDateFormats(wecVo.getStartDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setEndDate(DateUtility.convertDateFormats(wecVo.getEndDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		logger.debug("S_WEC_ID : "+wecVo.getId());
		logger.debug("S_WECSHORT_DESCR : "+wecVo.getName());
		logger.debug("S_CUSTOMER_ID : "+wecVo.getCustomerId());
		logger.debug("S_EB_ID : "+wecVo.getEbId());
		logger.debug("S_FOUND_LOC : "+wecVo.getFoundationNo());
		logger.debug("S_WEC_TYPE : "+wecVo.getType());
		logger.debug("N_MULTI_FACTOR : "+wecVo.getMultiFactor());
		logger.debug("N_WEC_CAPACITY : "+wecVo.getCapacity());
		logger.debug("D_COMMISION_DATE : "+wecVo.getCommissionDate());
		logger.debug("S_STATUS : "+wecVo.getStatus());
		logger.debug("N_GEN_COMM : "+wecVo.getGenComm());
		logger.debug("N_MAC_AVA : "+wecVo.getMachineAvailability());
		logger.debug("N_EXT_AVA : "+wecVo.getExtGridAvailability());
		logger.debug("N_INT_AVA : "+wecVo.getIntGridAvailability());
		logger.debug("D_START_DATE : "+wecVo.getStartDate());
		logger.debug("D_END_DATE : "+wecVo.getEndDate());
		logger.debug("S_FORMULA_NO : "+wecVo.getFormula());
		logger.debug("S_SHOW : "+wecVo.getShow());
		logger.debug("N_COST_PER_UNIT : "+wecVo.getCostPerUnit());
		logger.debug("S_TECHNICAL_NO : "+wecVo.getTechnicalNo());
		logger.debug("S_GUARANTEE_TYPE : "+wecVo.getGuaranteeType());
		logger.debug("S_CUSTOMER_TYPE : "+wecVo.getCustomerType());
		logger.debug("S_SCADA_FLAG : "+wecVo.getScadaStatus());
		logger.debug("S_FEEDER_ID : "+wecVo.getFeederId());
		logger.debug("N_PES_SCADA_STATUS : "+wecVo.getScadaStatus());
		
		String loginId = null;
		boolean create = false;
		boolean check = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);
			wecVo.setCreatedBy(loginId);
			wecVo.setModifiedBy(loginId);
			
			check = WecMasterService.getInstance().check(wecVo);	
			if(check){							
				messages.add("exists", new ActionMessage("err.master.exist", "WEC"));
			}else{					
				create = WecMasterService.getInstance().create(wecVo);
				if(create){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "WEC  Added"));
					wecVo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC  Creation"));
				}
			}
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Addition"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		
		
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		WecMasterVo wecVo = (WecMasterVo)form;
		if(wecVo.getStatus() == null){
			wecVo.setStatus("2");
		}
		if(wecVo.getShow() == null){
			wecVo.setShow("1");
		}
		wecVo.setCommissionDate(DateUtility.convertDateFormats(wecVo.getCommissionDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setStartDate(DateUtility.convertDateFormats(wecVo.getStartDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		wecVo.setEndDate(DateUtility.convertDateFormats(wecVo.getEndDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		logger.debug("S_WEC_ID : "+wecVo.getId());
		logger.debug("S_WECSHORT_DESCR : "+wecVo.getName());
		logger.debug("S_CUSTOMER_ID : "+wecVo.getCustomerId());
		logger.debug("S_EB_ID : "+wecVo.getEbId());
		logger.debug("S_FOUND_LOC : "+wecVo.getFoundationNo());
		logger.debug("S_WEC_TYPE : "+wecVo.getType());
		logger.debug("N_MULTI_FACTOR : "+wecVo.getMultiFactor());
		logger.debug("N_WEC_CAPACITY : "+wecVo.getCapacity());
		logger.debug("D_COMMISION_DATE : "+wecVo.getCommissionDate()); 
		logger.debug("S_STATUS : "+wecVo.getStatus());
		logger.debug("N_GEN_COMM : "+wecVo.getGenComm());
		logger.debug("N_MAC_AVA : "+wecVo.getMachineAvailability());
		logger.debug("N_EXT_AVA : "+wecVo.getExtGridAvailability());
		logger.debug("N_INT_AVA : "+wecVo.getIntGridAvailability());
		logger.debug("D_START_DATE : "+wecVo.getStartDate());
		logger.debug("D_END_DATE : "+wecVo.getEndDate());
		logger.debug("S_FORMULA_NO : "+wecVo.getFormula());
		logger.debug("S_SHOW : "+wecVo.getShow());
		logger.debug("N_COST_PER_UNIT : "+wecVo.getCostPerUnit());
		logger.debug("S_TECHNICAL_NO : "+wecVo.getTechnicalNo());
		logger.debug("S_GUARANTEE_TYPE : "+wecVo.getGuaranteeType());
		logger.debug("S_CUSTOMER_TYPE : "+wecVo.getCustomerType());
		logger.debug("S_SCADA_FLAG : "+wecVo.getScadaStatus());
		logger.debug("S_FEEDER_ID : "+wecVo.getFeederId());
		logger.debug("N_PES_SCADA_STATUS : "+wecVo.getScadaStatus());
		
		String loginId = null;
		boolean update = false;
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);	
			wecVo.setModifiedBy(loginId);
			
			update = WecMasterService.getInstance().update(wecVo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "WEC  Updated"));
				wecVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC  Updation"));
			}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC  Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		
		
		return mapping.findForward("success");
	}
}
