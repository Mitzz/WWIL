package com.enercon.struts.requestProcessor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.master.StandardMessageMasterVo;
import com.enercon.service.MessageDetailService;
import com.enercon.service.StdMessageService;
import com.enercon.struts.form.MessageDetailVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;

public class PostMessageRP extends Action{
	private final static Logger logger = Logger.getLogger(PostMessageRP.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		logger.debug("enter");
		try{
			List<StandardMessageMasterVo> standardMessageMasterVos =  StdMessageService.getInstance().getAll();
			List<MessageDetailVo> messageDetailVos = MessageDetailService.getInstance().getAll();
			
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//			objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
//			Object json = objectMapper.readValue(objectMapper.writeValueAsString(standardMessageMasterVos), Object.class);
			
			//Converting List of Java Object to List of Javascript Object
			String jsonInString = objectMapper.writeValueAsString(standardMessageMasterVos);
			logger.debug(jsonInString);
			
			logger.debug("Standard Messages List Size:: " + standardMessageMasterVos.size());
			request.setAttribute("standardMessageVos", standardMessageMasterVos);
			request.setAttribute("messageVos", messageDetailVos);
			request.setAttribute("standardMessageVosJson", jsonInString);
//			logger.debug("json:" + json.toString().replace("=", ":"));
		} catch (Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mapping.findForward("success");
	}

}
