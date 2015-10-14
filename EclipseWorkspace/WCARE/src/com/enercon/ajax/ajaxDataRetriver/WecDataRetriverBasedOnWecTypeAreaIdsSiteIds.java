package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class WecDataRetriverBasedOnWecTypeAreaIdsSiteIds extends AjaxDataRetriverImp {

	public WecDataRetriverBasedOnWecTypeAreaIdsSiteIds(
			List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String wecType = requestParameterValues.get(0);
		String areaIds = requestParameterValues.get(1);
		String siteIds = requestParameterValues.get(2);
		return AjaxDao.getWecSelectVoBasedOnWecTypeAreaIdsSiteIds(wecType, areaIds, siteIds);
	}

}
