package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class AreaDataRetriverBasedOnStateIds extends AjaxDataRetriverImp {

	public AreaDataRetriverBasedOnStateIds(
			List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String stateIds = new String();
		for (String string : requestParameterValues) {
			stateIds = string;
		}
		return AjaxDao.getAreaSelectVoBasedOnStateIds(stateIds);
	}

}
