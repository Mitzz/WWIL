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

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.misc.MiscUtility;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.model.table.WecReadingVo;

public class DeleteWecData  extends Action {

	private static Logger logger = Logger.getLogger(DeleteWecData.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		WecReadingVo vo = (WecReadingVo) form;
		vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
		
		logger.debug("wecId : "+vo.getWecId());
		logger.debug("mpId : "+vo.getMpId());
		logger.debug("fd : "+vo.getFromDate());
		logger.debug("td : "+vo.getToDate());
		
		String loginId = null;
		boolean delete = false;		
		ActionMessages messages = new ActionMessages();
		try {
			loginId = MiscUtility.getLoginId(request);			
			vo.setModifiedBy(loginId);
			
//				delete = WecParameterEvaluator.getInstance().delete(vo);
				if(delete){
					messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "WEC Data Deleted"));
					vo.reset(mapping, request);
				} else {
					messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Deletion"));
				}
			
		} catch (Exception e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "WEC Deletion"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		
		
		return mapping.findForward("success");
	}
	
	
}
