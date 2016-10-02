package com.enercon.struts.action;

import java.sql.SQLException;

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
import com.enercon.service.MessageDetailService;
import com.enercon.struts.form.MessageDetailVo;

public class MessageDetailAction extends DispatchAction{
	private final static Logger logger = Logger.getLogger(MessageDetailAction.class);

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageDetailVo formVo = (MessageDetailVo) form;
		logger.debug("Enter");
		logger.debug(formVo);
		
		String loginId = null;
		boolean create = false;
		ActionMessages messages = new ActionMessages();
		try {
			formVo.setFromDate(DateUtility.convertDateFormats(formVo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
			formVo.setToDate(DateUtility.convertDateFormats(formVo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
			
			loginId = MiscUtility.getLoginId(request);
			
			formVo.setCreatedBy(loginId);
			formVo.setModifiedBy(loginId);
			
			create = MessageDetailService.getInstance().create(formVo);
			if(create){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Created"));
				formVo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Creation"));
			}
		} catch (SQLException e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Creation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		MessageDetailVo vo = (MessageDetailVo) form;
		logger.debug("Enter");
		logger.debug(vo);
        String loginId = null;
		boolean update = false;
		
		ActionMessages messages = new ActionMessages();
		try {
			vo.setFromDate(DateUtility.convertDateFormats(vo.getFromDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
			vo.setToDate(DateUtility.convertDateFormats(vo.getToDate(), "dd/MM/yyyy", "dd-MMM-yyyy"));
			
			loginId = MiscUtility.getLoginId(request);
			vo.setModifiedBy(loginId);
			
			update = MessageDetailService.getInstance().update(vo);
			if(update){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Updated"));
				vo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Updation"));
			}
		} catch (SQLException e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Updation"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MessageDetailVo vo = (MessageDetailVo) form;
		logger.debug("Enter");
		logger.debug(vo);
		boolean delete = false;
		ActionMessages messages = new ActionMessages();
		try {
			delete = MessageDetailService.getInstance().delete(vo);
			if(delete){
				messages.add("success", new ActionMessage("success.stdmessage.crudOperation", "Deleted"));
				vo.reset(mapping, request);
			} else {
				messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Deletion"));
			}
		} catch (SQLException e) {
			messages.add("error", new ActionMessage("err.stdmessage.crudOperation", "Deletion"));
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
