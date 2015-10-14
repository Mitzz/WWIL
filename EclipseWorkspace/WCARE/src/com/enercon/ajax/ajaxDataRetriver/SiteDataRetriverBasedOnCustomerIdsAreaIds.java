package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class SiteDataRetriverBasedOnCustomerIdsAreaIds extends AjaxDataRetriverImp {

	public SiteDataRetriverBasedOnCustomerIdsAreaIds(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}

	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String customerIds = new String();
		String areaIds = new String();
		customerIds = requestParameterValues.get(0);
		areaIds = requestParameterValues.get(1);
		
		return AjaxDao.getSiteSelectVoBasedOnCustomerIdsAreaIds(customerIds, areaIds);
		
	}

}
