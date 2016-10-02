package com.enercon.ajax.ajaxDataRetriver;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.ajax.AjaxDao;
import com.enercon.ajax.interfaces.AjaxVo;

public class IredaVoListRetrieverBasedOnMonthYear extends AjaxDataRetriverImp {
	private final static Logger logger = Logger.getLogger(IredaVoListRetrieverBasedOnMonthYear.class);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return null;
	}

}
