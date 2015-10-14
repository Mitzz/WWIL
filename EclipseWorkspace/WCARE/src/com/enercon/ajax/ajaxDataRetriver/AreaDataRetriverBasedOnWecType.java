package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class AreaDataRetriverBasedOnWecType extends AjaxDataRetriverImp {
	
	public AreaDataRetriverBasedOnWecType(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String wecType = new String();
		for (String string : requestParameterValues) {
			wecType = string;
		}
		//System.out.println("Wec Type:" + wecType);
		return AjaxDao.getAreaSelectVoBasedOnWecType(wecType);
	}

}
