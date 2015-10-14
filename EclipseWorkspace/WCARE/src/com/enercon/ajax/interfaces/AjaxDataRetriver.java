package com.enercon.ajax.interfaces;

import java.util.List;

public interface AjaxDataRetriver {

	List<AjaxVo> getAjaxVo(List<String> requestParameterValues);
	/*void setAjaxRequestString(String id);*/
	List<String> getAjaxRequestParameterNames();
	
}
