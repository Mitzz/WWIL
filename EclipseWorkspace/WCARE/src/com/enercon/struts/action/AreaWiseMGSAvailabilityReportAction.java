package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.reports.dao.ReportDao;
import com.enercon.reports.pojo.OverAllMGSAvailibilityVo;
import com.enercon.struts.form.AreaWiseMGSAvailabilityReportForm;

public class AreaWiseMGSAvailabilityReportAction extends Action {

	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		AreaWiseMGSAvailabilityReportForm reportForm = (AreaWiseMGSAvailabilityReportForm) form;
		String oracleDateFormat = "dd-MMM-yyyy";
		String calendarDateFormat = "dd/MM/yyyy";
		String fromDate = DateUtility.convertDateFormats(reportForm.getFromDate(), calendarDateFormat, oracleDateFormat);
		String toDate = DateUtility.convertDateFormats(reportForm.getToDate(), calendarDateFormat, oracleDateFormat);
		
		List<OverAllMGSAvailibilityVo> overAllVo = new ArrayList<OverAllMGSAvailibilityVo>();		
		//String areaIds=GlobalUtils.getStringFromArrayForQuery( Arrays.asList(reportForm.getAreaSelected()));
		
		try {	
			overAllVo.addAll(ReportDao.getAreaWiseMGSAvailabilityBetweenDays(reportForm.getAreaSelected(), fromDate, toDate));
		} catch(Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("ReportData", overAllVo);
		return mapping.findForward("success");
	}
}
