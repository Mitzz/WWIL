package com.enercon.struts.action.summaryreport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.dao.SummaryDao;
import com.enercon.model.summaryreport.SummaryReport;
import com.enercon.struts.form.GridBifurcationReportForm;

public class SummaryReportAction extends Action{
	
	private final static Logger logger = Logger.getLogger(SummaryReportAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GridBifurcationReportForm f = (GridBifurcationReportForm) form;
		/*logger.debug("fromDate"+f.getFromDate());
		logger.debug(f.getToDate());
		logger.debug(f.getStructureType());
		logger.debug(f.getReportTypeSelection());
		for(String parameter: f.getParameterSelected()){
			logger.debug(parameter);
		}*/
		
		SummaryDao dao = new SummaryDao();
		
		List<SummaryReport> summaryReports = dao.getSummaryReportVo(f);
		XSSFWorkbook wb = null;
		String structureType = f.getStructureType();
		int reportType = -1;
		if(structureType.equals("state")){
			reportType = 0;
		} else if(structureType.equals("area")){
			reportType = 1;
		} else if(structureType.equals("site")){
			reportType = 2;
		} else if(structureType.equals("wecType")){
			reportType = 3;
		}
		if(f.getReportTypeSelection().equals("P")){
			wb = dao.generateExcelView(summaryReports, 0, reportType);
		} else if(f.getReportTypeSelection().equals("Y")){
			wb = dao.generateExcelView(summaryReports, 1, reportType);
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			
		} catch (NullPointerException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				
			} catch (NullPointerException e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				
			}
		}
		
		
		return mapping.findForward("succes");
	}
	
}
