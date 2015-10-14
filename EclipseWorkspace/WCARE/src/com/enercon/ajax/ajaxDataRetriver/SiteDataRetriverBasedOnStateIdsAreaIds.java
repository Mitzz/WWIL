package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class SiteDataRetriverBasedOnStateIdsAreaIds extends AjaxDataRetriverImp {

	public SiteDataRetriverBasedOnStateIdsAreaIds(
			List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String stateIds = new String();
		String areaIds = new String();
		stateIds = requestParameterValues.get(0);
		areaIds = requestParameterValues.get(1);
		
		return AjaxDao.getSiteSelectVoBasedOnStateIdsAreaIds(stateIds, areaIds);
	}

}
