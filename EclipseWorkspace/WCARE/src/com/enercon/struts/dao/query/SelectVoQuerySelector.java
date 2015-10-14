package com.enercon.struts.dao.query;

import java.util.Map;

public interface SelectVoQuerySelector {

	public Map<String, String> selectVoQueryResolver = new SelectVoQueryManager().getQueryMapper();
	
}
