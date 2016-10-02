package com.enercon.global.utility;

import java.text.ParseException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.apache.log4j.Logger;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.service.WecMasterService;

public class WecTrial extends ConditionalTagSupport{
	
	private static Logger logger = Logger.getLogger(WecTrial.class);
	private IWecMasterVo wec;
	
	
	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}

	@Override
	protected boolean condition() throws JspTagException {
		boolean result = false;
		try {
			result = WecMasterService.getInstance().isTrial(wec);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return result;
	}

}

