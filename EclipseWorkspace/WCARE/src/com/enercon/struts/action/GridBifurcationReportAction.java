package com.enercon.struts.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.reports.dao.ReportDao;
import com.enercon.reports.pojo.GridBifurcationReportVo;
import com.enercon.struts.form.GridBifurcationReportForm;

public class GridBifurcationReportAction extends Action{

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		GridBifurcationReportForm reportForm = (GridBifurcationReportForm) form;
		String oracleDateFormat = "dd-MMM-yyyy";
		String calendarDateFormat = "dd/MM/yyyy";
		String fromDate = DateUtility.convertDateFormats(reportForm.getFromDate(), calendarDateFormat, oracleDateFormat);
		String toDate = DateUtility.convertDateFormats(reportForm.getToDate(), calendarDateFormat, oracleDateFormat);
		boolean isWindspeedRequired=reportForm.isWindspeed();
		List<GridBifurcationReportVo> gridBifurcationReportVos = new ArrayList<GridBifurcationReportVo>();
		Map<String, String> period = null;
		String key = "";
		String value = "";
		if(isWindspeedRequired){			
			if(reportForm.getReportTypeSelection().equals("D")){
				gridBifurcationReportVos.addAll(ReportDao.getWecInfoWecWiseWithWindspeedBetweenDays(Arrays.asList(reportForm.getWecSelected()), fromDate, toDate));
			}
			if(reportForm.getReportTypeSelection().equals("M")){
				try {
					period = DateUtility.getPeriodMonthWise(fromDate, toDate, oracleDateFormat, DateUtility.getMonthDifferenceBetweenTwoDates(fromDate, toDate, oracleDateFormat));
					for (Map.Entry<String, String> entry : period.entrySet()) {
						key = entry.getKey();
					    value = entry.getValue();
					    gridBifurcationReportVos.addAll(ReportDao.getWecTotalWecWiseWithWindspeedBetweenDays(Arrays.asList(reportForm.getWecSelected()), key, value)); 
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(reportForm.getReportTypeSelection().equals("Y")){
				try {
					period = DateUtility.getPeriodFiscalYearWise(fromDate, toDate, oracleDateFormat, DateUtility.getFiscalYear(toDate, oracleDateFormat) - DateUtility.getFiscalYear(fromDate, oracleDateFormat));
					for (Map.Entry<String, String> entry : period.entrySet()) {
					    key = entry.getKey();
					    value = entry.getValue();
					    gridBifurcationReportVos.addAll(ReportDao.getWecTotalWecWiseWithWindspeedBetweenDays(Arrays.asList(reportForm.getWecSelected()), key, value)); 
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			
		}
		else{
			if(reportForm.getReportTypeSelection().equals("D")){
				gridBifurcationReportVos.addAll(ReportDao.getWecInfoWecWiseBetweenDays(Arrays.asList(reportForm.getWecSelected()), fromDate, toDate));
			}
			if(reportForm.getReportTypeSelection().equals("M")){
				try {
					period = DateUtility.getPeriodMonthWise(fromDate, toDate, oracleDateFormat, DateUtility.getMonthDifferenceBetweenTwoDates(fromDate, toDate, oracleDateFormat));
					for (Map.Entry<String, String> entry : period.entrySet()) {
						key = entry.getKey();
					    value = entry.getValue();
					    gridBifurcationReportVos.addAll(ReportDao.getWecTotalWecWiseBetweenDays(Arrays.asList(reportForm.getWecSelected()), key, value)); 
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if(reportForm.getReportTypeSelection().equals("Y")){
				try {
					period = DateUtility.getPeriodFiscalYearWise(fromDate, toDate, oracleDateFormat, DateUtility.getFiscalYear(toDate, oracleDateFormat) - DateUtility.getFiscalYear(fromDate, oracleDateFormat));
					for (Map.Entry<String, String> entry : period.entrySet()) {
					    key = entry.getKey();
					    value = entry.getValue();
					    gridBifurcationReportVos.addAll(ReportDao.getWecTotalWecWiseBetweenDays(Arrays.asList(reportForm.getWecSelected()), key, value)); 
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

		}

		request.setAttribute("ReportData", gridBifurcationReportVos);
		request.setAttribute("IsWindspeedRequired", isWindspeedRequired);

		return mapping.findForward("success");
	}
}
