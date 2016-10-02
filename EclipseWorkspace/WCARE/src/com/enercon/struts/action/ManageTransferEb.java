package com.enercon.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.enercon.dao.parameter.EbParameterDataDao;
import com.enercon.model.transfer.TransferEbVo;
import com.enercon.service.master.EbMasterService;

public class ManageTransferEb extends Action{
	
	private static Logger logger = Logger.getLogger(ManageTransferEb.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		logger.debug(form);
		TransferEbVo vo = (TransferEbVo) form;
		EbMasterService service = EbMasterService.getInstance();
		EbParameterDataDao ebParameterDao = EbParameterDataDao.getInstance();
		ActionMessages messages = new ActionMessages();
		try {
			if(service.transfer(vo)){
				ebParameterDao.transferEb(EbMasterService.getInstance().get(vo.getFromEbId()), EbMasterService.getInstance().get(vo.getToEbId()), vo.getDate());
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
			} else {
				throw new Exception("Eb not transferred");
			}
				
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Eb Transfer"));
		}
		
		return mapping.findForward("success");
	}
}
