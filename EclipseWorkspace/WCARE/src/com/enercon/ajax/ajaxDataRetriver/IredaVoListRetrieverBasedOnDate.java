package com.enercon.ajax.ajaxDataRetriver;

import java.sql.SQLException;
import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;
import com.enercon.global.utility.DateUtility;

public class IredaVoListRetrieverBasedOnDate extends AjaxDataRetriverImp {

	public IredaVoListRetrieverBasedOnDate(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}
	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String projectNos = new String();
		projectNos = requestParameterValues.get(0);
		String date = requestParameterValues.get(1);
		date = DateUtility.convertDateFormats(date, "dd/MM/yyyy", "dd-MMM-yyyy");
		//System.out.println(requestParameterValues);
		try {
			return AjaxDao.getIredaVoListRetrieverBasedOnDate(projectNos, date);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

