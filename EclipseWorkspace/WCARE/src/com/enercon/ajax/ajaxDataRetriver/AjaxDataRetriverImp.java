package com.enercon.ajax.ajaxDataRetriver;

import java.util.List;

import com.enercon.ajax.interfaces.AjaxDataRetriver;

public abstract class AjaxDataRetriverImp implements AjaxDataRetriver{

	protected List<String> ajaxRequestParameterNames;
	
	public AjaxDataRetriverImp(List<String> ajaxRequestParameterNames) {
		this.ajaxRequestParameterNames = ajaxRequestParameterNames;
	}

	/*public void setAjaxRequestString(String requestString) {
		this.ajaxRequestString = requestString;
	}*/

	public List<String> getAjaxRequestParameterNames() {
		return ajaxRequestParameterNames;
	}
}
