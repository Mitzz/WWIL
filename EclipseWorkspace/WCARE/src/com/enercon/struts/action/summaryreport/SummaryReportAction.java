package com.enercon.struts.action.summaryreport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.dao.SummaryDao;
import com.enercon.model.summaryreport.SummaryReport;
import com.enercon.struts.form.GridBifurcationReportForm;

public class SummaryReportAction extends Action{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridBifurcationReportForm f = (GridBifurcationReportForm) form;
		SummaryDao dao = new SummaryDao();
		
		List<SummaryReport> summaryReports = dao.getSummaryReportVo(f);
		XSSFWorkbook wb = null;
		if(f.getReportTypeSelection().equals("P")){
			wb = dao.generateParameterWiseExcelView(summaryReports);
		} else if(f.getReportTypeSelection().equals("Y")){
			wb = dao.generateYearWiseExcelView(summaryReports);
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=SummaryReport.xlsx");
		ServletOutputStream out = null;
		
		try {
			out = response.getOutputStream();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			wb.write(byteArrayOutputStream);
			out.write(byteArrayOutputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		
		
		return mapping.findForward("succes");
	}
	
}
