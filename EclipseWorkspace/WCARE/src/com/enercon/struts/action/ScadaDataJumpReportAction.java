package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.reports.dao.ReportDao;
import com.enercon.struts.form.ScadaDataJumpReportForm;
import com.enercon.struts.pojo.ScadaDataJump;

public class ScadaDataJumpReportAction extends Action{
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		
		ScadaDataJumpReportForm reportForm=(ScadaDataJumpReportForm)form;
		String oracleDateFormat = "dd-MMM-yyyy";
		String calendarDateFormat = "dd/MM/yyyy";
		String fromDate = DateUtility.convertDateFormats(reportForm.getFromDate(), calendarDateFormat, oracleDateFormat);
		String toDate = DateUtility.convertDateFormats(reportForm.getToDate(), calendarDateFormat, oracleDateFormat);
		List<ScadaDataJump> scadaDataJumpVo=new ArrayList<ScadaDataJump>();
		scadaDataJumpVo.addAll(ReportDao.getScadaDataInfoWecWiseBetweenDays(Arrays.asList(reportForm.getWecSelected()), fromDate, toDate));
		request.setAttribute("ReportData", scadaDataJumpVo);
		return mapping.findForward("success");
	}
}

	
