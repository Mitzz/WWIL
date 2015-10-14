package com.enercon.ajax.ajaxDataRetriver;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class IredaVoListRetrieverBasedOnFiscalYear extends AjaxDataRetriverImp {

	public IredaVoListRetrieverBasedOnFiscalYear(List<String> ajaxRequestParameterNames) {
		super(ajaxRequestParameterNames);
	}
	public List<AjaxVo> getAjaxVo(List<String> requestParameterValues) {
		String projectNos = new String();
		projectNos = requestParameterValues.get(0);
		String fiscalYear = requestParameterValues.get(1);
		//System.out.println(requestParameterValues);
		try {
			
			return AjaxDao.getIredaVoListRetrieverBasedOnFiscalYearWithThreading(projectNos, fiscalYear);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
