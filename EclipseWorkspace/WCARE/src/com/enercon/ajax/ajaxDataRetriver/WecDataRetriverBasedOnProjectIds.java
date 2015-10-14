package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class WecDataRetriverBasedOnProjectIds extends AjaxDataRetriverImp {

	public WecDataRetriverBasedOnProjectIds(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}
	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String projectIds = new String();
		projectIds = requestParameterValues.get(0);
		
		
		return AjaxDao.getWecSelectVoBasedOnProjectIds(projectIds);
	}
}
