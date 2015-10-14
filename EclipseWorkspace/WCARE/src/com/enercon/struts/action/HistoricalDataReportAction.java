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
import com.enercon.global.utils.GlobalUtils;
import com.enercon.reports.dao.ReportDao;
import com.enercon.reports.pojo.GridBifurcationReportVo;
import com.enercon.struts.form.HistoricalDataReportForm;
public class HistoricalDataReportAction extends Action{


	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		HistoricalDataReportForm reportForm = (HistoricalDataReportForm) form;
		String oracleDateFormat = "dd-MMM-yyyy";
		String calendarDateFormat = "dd/MM/yyyy";
		String fromDate = DateUtility.convertDateFormats(reportForm.getFromDate(), calendarDateFormat, oracleDateFormat);
		String toDate = DateUtility.convertDateFormats(reportForm.getToDate(), calendarDateFormat, oracleDateFormat);
		String wecIdInStringFormatForQuery=GlobalUtils.getStringFromArrayForQuery(Arrays.asList(reportForm.getWecSelected()));
		
		List<GridBifurcationReportVo> historicalDataReportVos = new ArrayList<GridBifurcationReportVo>();
		Map<String, String> period = null;
		String key = "";
		String value = "";
		String sqlqueryForVW1=ReportDao.getSqlQueryForHistoricalDataView1();
		
		sqlqueryForVW1 = sqlqueryForVW1.replace("param1", fromDate);
		sqlqueryForVW1 = sqlqueryForVW1.replace("param2", toDate);
		sqlqueryForVW1 = sqlqueryForVW1.replace("param3", wecIdInStringFormatForQuery);
		
		//System.out.println(sqlqueryForVW1);
		ReportDao.createViewForHistoricalData(sqlqueryForVW1);
//		String sqlqueryForVW2=ReportDao.getSqlQueryForHistoricalDataView2();
//		ReportDao.createView1(sqlqueryForVW2);
		if(reportForm.getReportTypeSelection().equals("D")){
 			historicalDataReportVos.addAll(ReportDao.getWecInfoWecWiseBetweenDaysforHistoricalData());
 		}
 		if(reportForm.getReportTypeSelection().equals("M")){
 			try {
 				period = DateUtility.getPeriodMonthWise(fromDate, toDate, oracleDateFormat, DateUtility.getMonthDifferenceBetweenTwoDates(fromDate, toDate, oracleDateFormat));
 				for (Map.Entry<String, String> entry : period.entrySet()) {
 					key = entry.getKey();
 				    value = entry.getValue();
// 				    /String fromDate, String toDate
 				    historicalDataReportVos.addAll(ReportDao.getWecTotalWecWiseBetweenDaysforHistoricalData(key, value)); 
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
 				    historicalDataReportVos.addAll(ReportDao.getWecTotalWecWiseBetweenDaysforHistoricalData( key, value)); 
 				}
			} catch (ParseException e) {
 				e.printStackTrace();
 			}
 		}
	request.setAttribute("ReportData", historicalDataReportVos);
		return mapping.findForward("success");
	}

}
