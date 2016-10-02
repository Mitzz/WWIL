package com.enercon.global.utility;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import org.apache.log4j.Logger;

import com.enercon.dao.WecParameterDataDao;
import com.enercon.model.parameter.wec.IWecParameterVo;

public class WecDgrShow extends ConditionalTagSupport {
	private static Logger logger = Logger.getLogger(WecDgrShow.class);
    
	private IWecParameterVo wecParameterVo;
	
	@Override
	protected boolean condition() throws JspTagException {
		return isShow();
	}
	
	public boolean isShow(){
		if(wecParameterVo == null) return false;
		if(!wecParameterVo.isPublish()) return false;
		if(wecParameterVo.isLullHourDash()) return false;
		return true;
	}
	
	public IWecParameterVo getWecParameterVo() {
		return wecParameterVo;
	}

	public WecDgrShow setWecParameterVo(IWecParameterVo parameter) {
		this.wecParameterVo = parameter;
		return this;
	}
	
	
}
