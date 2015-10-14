package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class AreaDataRetriverBasedOnCustomerId extends AjaxDataRetriverImp{

	public AreaDataRetriverBasedOnCustomerId(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}
	
	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String customerIds = "";
		for (String string : requestParameterValues) {
			customerIds = string;
		}
		return AjaxDao.getAreaVoBasedOnCustomerIds(customerIds);
	}

}
