package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class WecDataRetriverBasedOnLocationNos extends AjaxDataRetriverImp {
	
	public WecDataRetriverBasedOnLocationNos(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String locationNos = new String();
		locationNos = requestParameterValues.get(0);
		
		
		return AjaxDao.getWecSelectVoBasedOnLocationIds(locationNos);
	}

}
