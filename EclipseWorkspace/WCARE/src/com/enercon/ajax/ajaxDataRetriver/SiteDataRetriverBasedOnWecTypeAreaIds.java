package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class SiteDataRetriverBasedOnWecTypeAreaIds extends AjaxDataRetriverImp {

	public SiteDataRetriverBasedOnWecTypeAreaIds(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String wecType = new String();
		String areaIds = new String();
		wecType = requestParameterValues.get(0);
		areaIds = requestParameterValues.get(1);
		
		return AjaxDao.getSiteSelectVoBasedOnWecTypeAreaIds(wecType, areaIds);
	}

}
