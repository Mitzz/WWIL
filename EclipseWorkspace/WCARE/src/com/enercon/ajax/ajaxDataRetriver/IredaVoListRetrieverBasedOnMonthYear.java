package com.enercon.ajax.ajaxDataRetriver;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class IredaVoListRetrieverBasedOnMonthYear extends AjaxDataRetriverImp {

	public IredaVoListRetrieverBasedOnMonthYear(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}
	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String projectNos = new String();
		projectNos = requestParameterValues.get(0);
		String month = requestParameterValues.get(1);
		String year = requestParameterValues.get(2);
		//System.out.println(requestParameterValues);
		try {
			
//			return AjaxDao.getIredaVoListRetrieverBasedOnMonthYear(projectNos,month, year);
			return AjaxDao.getIredaVoListRetrieverBasedOnMonthYearWithThreading(projectNos,month, year);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
