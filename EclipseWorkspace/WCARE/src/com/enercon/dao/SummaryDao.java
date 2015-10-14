package com.enercon.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.ExcelWriter;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.SummaryReport;
import com.enercon.struts.form.GridBifurcationReportForm;

public class SummaryDao implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(SummaryDao.class);
	
	public List<SummaryReport> getSummaryReportVo(GridBifurcationReportForm f) throws Exception {
		
		List<SummaryReport> summaryReportVos = getSummaryReportVo(new LinkedHashSet<String>(Arrays.asList(f.getWecSelected())), f.getFromDateInOracleFormat(), f.getToDateInOracleFormat(), f.isWindspeed());
		return summaryReportVos;
	}

	//Returns List of SummaryReport
	//Summary Report has 2d-Map which contains YearlyMonthWise Parameter Data(Year mapped to month and month mapped to ParameterVo)   
	//ParameterVo contains all parameter value
	public List<SummaryReport> getSummaryReportVo(Set<String> wecIds, String fromDate, String toDate, boolean isWindspeed) throws Exception {
		
		List<SummaryReport> summaryReportVos = getSummaryReportVoN(wecIds, fromDate, toDate, isWindspeed);
		for (SummaryReport summaryReport : summaryReportVos) {
			summaryReport.fiscalYearDetails().fiscalYearTotal();
		}
		return summaryReportVos;
	}

	//List of SummaryReport
	//Each SummaryReport will be given input data
	private List<SummaryReport> getSummaryReportVoN(Set<String> wecIds, String fromDate, String toDate, boolean isWindspeed) throws Exception {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<SummaryReport> summaryReportVo = new ArrayList<SummaryReport>();
		
		//For Avoiding Duplication of Same Summary Report
		Set<SummaryReport> summaryReportVoSet = new TreeSet<SummaryReport>();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> wecIdsSplit : GlobalUtils.splitArrayList(wecIds, 800)){
				query = 
						"select  S_customer_name, S_customer_id, S_state_name, S_state_id, S_site_name,S_site_id, count(1) " + 
						"from customer_meta_data  " + 
						"where s_wec_id in " + GlobalUtils.getStringFromArrayForQuery(wecIdsSplit) + 
						"group by  S_customer_name, S_customer_id, S_state_name, S_state_id, S_site_name,S_site_id " +
						"order by S_customer_name, S_state_name, S_site_name ";
				
				prepStmt = conn.prepareStatement(query);
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					Set<String> selectedWecIds = new HashSet<String>(); 
					Set<String> allWecIdsBasedOnCustomerStateSite = WecMasterDao.getWecIdsBasedOnCustomerStateSite(rs.getString("S_customer_id"), rs.getString("S_state_id"), rs.getString("S_site_id"));
					
					//Doing Intersection
					selectedWecIds = GlobalUtils.intersection(allWecIdsBasedOnCustomerStateSite, wecIdsSplit);
					SummaryReport reportWithAllWecIds = new SummaryReport(rs.getString("S_customer_name"), rs.getString("S_state_name"), rs.getString("S_site_name"), selectedWecIds, fromDate, toDate, isWindspeed);
					
					if(summaryReportVoSet.contains(reportWithAllWecIds)){
						Set<String> alreadySelectedWecIds = null;
						for (SummaryReport alreadyPresentSummaryReport : summaryReportVoSet) {
							if(alreadyPresentSummaryReport.equals(reportWithAllWecIds)){
								alreadySelectedWecIds = alreadyPresentSummaryReport.getWecIds();
								break;
							}
						}
						summaryReportVoSet.remove(reportWithAllWecIds);
						selectedWecIds.addAll(alreadySelectedWecIds);
						
						summaryReportVoSet.add(new SummaryReport(rs.getString("S_customer_name"), rs.getString("S_state_name"), rs.getString("S_site_name"), selectedWecIds, fromDate, toDate, isWindspeed));
					} else {
						summaryReportVoSet.add(reportWithAllWecIds);
					}
					
				}	
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				
			}
			summaryReportVo.addAll(summaryReportVoSet);
			return summaryReportVo;
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}

	

	public XSSFWorkbook generateParameterWiseExcelView(List<SummaryReport> summaryReports) throws IOException {
		logger.debug("Excel Started");
		
		String sheetName = "Parameter Wise";//name of sheet
		
		ExcelWriter excelWriter = new ExcelWriter(sheetName);
		
		int offsetRowNo = 1;
		int offsetColumn = 3;
		int currentRow = 0;
		
		int stateTextWidth = 2;
		int siteTextWidth = 4;
		int totalWecsTextWidth = 2;
		int periodTextWidth = 4;

		XSSFCellStyle headerStyle =   excelWriter.CENTERALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleLeftAlign = excelWriter.LEFTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleRightAlign = excelWriter.RIGHTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		
		XSSFCellStyle parameterStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle fiscalYearStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		
		XSSFCellStyle greyBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_WHITEBACKGROUND;
		XSSFCellStyle greyBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_WHITEBACKGROUND;
		
//		List<FiscalYear> fiscalYears = new ArrayList<FiscalYear>();
//		List<Parameter> parameters = new ArrayList<Parameter>();
		
		for (SummaryReport summaryReport : summaryReports) {

			logger.debug(summaryReport.getCustomerName() + ":" + summaryReport.getSiteName());
			
			int currentColumn = 0;
			int noOfFiscalYear = summaryReport.getFiscalYearsDetails().size();
			int noOfParameters = summaryReport.getParameters().size();
			int totalColumn = noOfParameters * noOfFiscalYear + 1;
			offsetColumn = 10 - (totalColumn/2);
			offsetColumn = (offsetColumn > 0 ? offsetColumn : 1);
			int start = 0;
			int end = 0;
			
			currentRow = offsetRowNo + currentRow;
			currentColumn = offsetColumn;
			excelWriter.createRow(currentRow);
			
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, summaryReport.getCustomerName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyle);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalColumn - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalColumn - 1, CellStyle.BORDER_THICK);
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "State: " + summaryReport.getStateName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + stateTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + stateTextWidth - 1, CellStyle.BORDER_THIN);
//			
			currentColumn = offsetColumn + totalColumn - siteTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Site: " + summaryReport.getSiteName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + siteTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + siteTextWidth - 1, CellStyle.BORDER_THIN);
			
			start = offsetColumn + stateTextWidth;
			end = offsetColumn + totalColumn - (siteTextWidth + 1);
			if(end - start >= 0){
				currentColumn = start;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "Start");
				excelWriter.data(currentRow, currentColumn, "");
				
				
				currentColumn = end;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "End");
				excelWriter.data(currentRow, currentColumn, "");
			
			
				excelWriter.mergeColumnWithRow(currentRow, start, end);
				excelWriter.styleCell(currentRow, start, headerStyle);
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THIN);
			}
			
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Total Wecs : " + summaryReport.getWecIds().size());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1, CellStyle.BORDER_THIN);
//			
			currentColumn = offsetColumn + totalColumn - periodTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Period : " + summaryReport.getFromDate() + " to " + summaryReport.getToDate());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + periodTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + periodTextWidth - 1, CellStyle.BORDER_THIN);
			
			start = offsetColumn + totalWecsTextWidth;
			end = offsetColumn + totalColumn - (periodTextWidth + 1);
			if(end - start >= 0){
				currentColumn = start;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "Start");
				excelWriter.data(currentRow, currentColumn, "");
				
				
				currentColumn = end;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "End");
				excelWriter.data(currentRow, currentColumn, "");
			
			
				excelWriter.mergeColumnWithRow(currentRow, start, end);
				excelWriter.styleCell(currentRow, start, headerStyle);
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THIN);
			}
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			
			currentColumn = 1 + offsetColumn;
			
			for (Parameter parameter : summaryReport.getParameters()) {
				int i = 0;
				for (i = 0; i < noOfFiscalYear; currentColumn += noOfFiscalYear,i += noOfFiscalYear ) {
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, parameter.getParameterCode());
					excelWriter.styleCell(currentRow, currentColumn, parameterStyle);
					
				}
				excelWriter.mergeColumnWithRow(currentRow, currentColumn - noOfFiscalYear, currentColumn - 1);
				excelWriter.doBorder(currentRow, currentRow, currentColumn - noOfFiscalYear, currentColumn - 1, CellStyle.BORDER_THICK);

			}
//			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			excelWriter.mergeColumn(currentRow - 1, currentRow, currentColumn, currentColumn);
			excelWriter.doBorder(currentRow - 1, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THICK);
			
			currentColumn = 1 + currentColumn;
			
			for(int i = 0; i < summaryReport.getParameters().size();i++){
				int j = 1;
				for (FiscalYear fy : summaryReport.getFiscalYearsDetails().keySet()) {
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, fy.getReportValue());
					excelWriter.styleCell(currentRow, currentColumn, fiscalYearStyle);
					if(j++ % noOfFiscalYear > 0) excelWriter.doRightBorder(currentRow, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THIN);
					currentColumn++;
				}
				excelWriter.doBorder(currentRow, currentRow, currentColumn - noOfFiscalYear, currentColumn - 1, CellStyle.BORDER_THICK);
			}
//			
			
			for(Month m : Month.fiscalYearValues(4, 3)){
				currentRow++;
				excelWriter.createRow(currentRow);
				
				currentColumn = offsetColumn;
				excelWriter.createColumn(currentRow, currentColumn);
				excelWriter.data(currentRow, currentColumn, Month.getShortHandMonthName(m));
//				excelWriter.styleCell(currentRow, currentColumn, CellStyle.ALIGN_CENTER, IndexedColors.BLACK.getIndex(), IndexedColors.GREY_25_PERCENT.getIndex());
				if(m.getValue() % 2 == 0){
					excelWriter.styleCell(currentRow, currentColumn, whiteBackgroundCenterAlignStyle);
				} else {
					excelWriter.styleCell(currentRow, currentColumn, greyBackgroundCenterAlignStyle);
				}
//				excelWriter.doBottomBorder(currentRow, currentRow, currentColumn , currentColumn, CellStyle.BORDER_THIN);
				
				currentColumn = currentColumn + 1;
				XSSFCellStyle dataStyle = null;
				for(Parameter parameter : summaryReport.getParameters()){
					for(FiscalYear fy : summaryReport.getFiscalYearsDetails().keySet()){
						excelWriter.createColumn(currentRow, currentColumn);
						if(!summaryReport.getFiscalYearsDetails().get(fy).containsKey(m)){
							excelWriter.data(currentRow, currentColumn, "-");
							dataStyle = (m.getValue() % 2 == 0 ? whiteBackgroundCenterAlignStyle : greyBackgroundCenterAlignStyle);  
						} else {
							excelWriter.data(currentRow, currentColumn, Double.parseDouble(summaryReport.getFiscalYearsDetails().get(fy).get(m).value(parameter).toString()));
							dataStyle = (m.getValue() % 2 == 0 ? whiteBackgroundRightAlignStyle : greyBackgroundRightAlignStyle);
						}
						excelWriter.styleCell(currentRow, currentColumn, dataStyle);
						currentColumn = currentColumn + 1;
					}
					
				}
				
			}

			excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1, currentColumn - 1, CellStyle.BORDER_THICK);
			excelWriter.doBorder(currentRow - 11, currentRow, offsetColumn, offsetColumn , CellStyle.BORDER_THICK);
			
			for(int j = 0; j < noOfParameters; j++){
				
				for (int i = 0; i < noOfFiscalYear; i++) {
					excelWriter.doRightBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, CellStyle.BORDER_THIN);
				}
				excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear), currentColumn - 1, CellStyle.BORDER_THICK);
			}
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Total");
			excelWriter.styleCell(currentRow, currentColumn, excelWriter.LEFTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THICK);
			
			for (Parameter parameter : summaryReport.getParameters()) {
				for (FiscalYear fy : summaryReport.getFiscalYearsTotal().keySet()) {
					IWecParameterVo vo = summaryReport.getFiscalYearsTotal().get(fy);
					currentColumn = currentColumn + 1;
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, vo.value(parameter));
					excelWriter.styleCell(currentRow, currentColumn, excelWriter.RIGHTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND);
				}
			}
			currentColumn = currentColumn + 1;
			for(int j = 0; j < noOfParameters; j++){
				
				for (int i = 0; i < noOfFiscalYear; i++) {
					excelWriter.doRightBorder(currentRow, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, CellStyle.BORDER_THIN);
				}
				excelWriter.doBorder(currentRow, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear), currentColumn - 1, CellStyle.BORDER_THICK);
			}
			
			currentRow = currentRow + 1;

		}
//		String excelFileName = "C:/Users/91014863/Desktop/SummaryReport_Test.xlsx";
//		FileOutputStream fileOut = new FileOutputStream(excelFileName);
//		excelWriter.getWorkBook().write(fileOut);
//		fileOut.flush();
//		fileOut.close();
		logger.debug("Excel Ended");
		return excelWriter.getWorkBook();
	}

	public XSSFWorkbook generateYearWiseExcelView(List<SummaryReport> summaryReports) throws IOException {
		logger.debug("Excel Started");
		
		String sheetName = "Year Wise";//name of sheet
		
		ExcelWriter excelWriter = new ExcelWriter(sheetName);
		
		int offsetRowNo = 1;
		int offsetColumn = 0;
		int currentRow = 0;
		
		int stateTextWidth = 2;
		int siteTextWidth = 4;
		int totalWecsTextWidth = 2;
		int periodTextWidth = 4;

		XSSFCellStyle headerStyle =   excelWriter.CENTERALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleLeftAlign = excelWriter.LEFTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleRightAlign = excelWriter.RIGHTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		
		XSSFCellStyle parameterStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle fiscalYearStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		
		XSSFCellStyle greyBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_WHITEBACKGROUND;
		XSSFCellStyle greyBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_WHITEBACKGROUND;
		
		for (SummaryReport summaryReport : summaryReports) {

			logger.debug(summaryReport.getSiteName() + ":" + summaryReport.getCustomerName());
			
			int currentColumn = 0;
			int noOfFiscalYear = summaryReport.getFiscalYearsDetails().size();
			int noOfParameters = summaryReport.getParameters().size();
			int totalColumn = noOfParameters * noOfFiscalYear + 1;
			offsetColumn = 10 - (totalColumn/2);
			offsetColumn = (offsetColumn > 0 ? offsetColumn : 1);
			int start = 0;
			int end = 0;
			
			currentRow = offsetRowNo + currentRow;
			currentColumn = offsetColumn;
			excelWriter.createRow(currentRow);
			
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, summaryReport.getCustomerName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyle);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalColumn - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalColumn - 1, CellStyle.BORDER_THICK);
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "State: " + summaryReport.getStateName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + stateTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + stateTextWidth - 1, CellStyle.BORDER_THIN);
//			
			currentColumn = offsetColumn + totalColumn - siteTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Site: " + summaryReport.getSiteName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + siteTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + siteTextWidth - 1, CellStyle.BORDER_THIN);
			
			start = offsetColumn + stateTextWidth;
			end = offsetColumn + totalColumn - (siteTextWidth + 1);
			if(end - start >= 0){
				currentColumn = start;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "Start");
				excelWriter.data(currentRow, currentColumn, "");
				
				end = offsetColumn + totalColumn - (siteTextWidth + 1);
				currentColumn = end;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "End");
				excelWriter.data(currentRow, currentColumn, "");
			
				excelWriter.mergeColumnWithRow(currentRow, start, end);
				excelWriter.styleCell(currentRow, start, headerStyle);
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THIN);
			}
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Total Wecs : " + summaryReport.getWecIds().size());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1, CellStyle.BORDER_THIN);
//			
			currentColumn = offsetColumn + totalColumn - periodTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Period : " + summaryReport.getFromDate() + " to " + summaryReport.getToDate());
			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + periodTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + periodTextWidth - 1, CellStyle.BORDER_THIN);
			
			start = offsetColumn + totalWecsTextWidth;
			end = offsetColumn + totalColumn - (periodTextWidth + 1);
			if(end - start >= 0){
				currentColumn = start;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "Start");
				excelWriter.data(currentRow, currentColumn, "");
				
				
				currentColumn = end;
				excelWriter.createColumn(currentRow, currentColumn);
	//			excelWriter.data(currentRow, currentColumn, "End");
				excelWriter.data(currentRow, currentColumn, "");
			
			
				excelWriter.mergeColumnWithRow(currentRow, start, end);
				excelWriter.styleCell(currentRow, start, headerStyle);
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THIN);
			}
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			
			currentColumn = 1 + offsetColumn;
			
			
			
			for (FiscalYear fy : summaryReport.getFiscalYearsDetails().keySet()){
				int i = 0;
				for (i = 0; i < noOfParameters; currentColumn += noOfParameters,i += noOfParameters ) {
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, fy.getReportValue());
					excelWriter.styleCell(currentRow, currentColumn, fiscalYearStyle);
					
				}
				excelWriter.mergeColumnWithRow(currentRow, currentColumn - noOfParameters, currentColumn - 1);
				excelWriter.doBorder(currentRow, currentRow, currentColumn - noOfParameters, currentColumn - 1, CellStyle.BORDER_THICK);

			}
//			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			excelWriter.mergeColumn(currentRow - 1, currentRow, currentColumn, currentColumn);
			excelWriter.doBorder(currentRow - 1, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THICK);
			
			currentColumn = 1 + currentColumn;
			
			for(int i = 0; i < noOfFiscalYear;i++){
				int j = 1;
				for (Parameter parameter : summaryReport.getParameters()) {
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, parameter.getParameterCode());
					excelWriter.styleCell(currentRow, currentColumn, parameterStyle);
					if(j++ % noOfParameters > 0) excelWriter.doRightBorder(currentRow, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THIN);
					currentColumn++;
				}
				excelWriter.doBorder(currentRow, currentRow, currentColumn - noOfParameters, currentColumn - 1, CellStyle.BORDER_THICK);
			}
//			
			
			for(Month m : Month.fiscalYearValues(4, 3)){
				currentRow++;
				excelWriter.createRow(currentRow);
				
				currentColumn = offsetColumn;
				excelWriter.createColumn(currentRow, currentColumn);
				excelWriter.data(currentRow, currentColumn, Month.getShortHandMonthName(m));
//				excelWriter.styleCell(currentRow, currentColumn, CellStyle.ALIGN_CENTER, IndexedColors.BLACK.getIndex(), IndexedColors.GREY_25_PERCENT.getIndex());
				if(m.getValue() % 2 == 0){
					excelWriter.styleCell(currentRow, currentColumn, whiteBackgroundCenterAlignStyle);
				} else {
					excelWriter.styleCell(currentRow, currentColumn, greyBackgroundCenterAlignStyle);
				}
//				excelWriter.doBottomBorder(currentRow, currentRow, currentColumn , currentColumn, CellStyle.BORDER_THIN);
				
				currentColumn = currentColumn + 1;
				XSSFCellStyle dataStyle = null;
				for(FiscalYear fy : summaryReport.getFiscalYearsDetails().keySet()){
					for(Parameter parameter : summaryReport.getParameters()){
						excelWriter.createColumn(currentRow, currentColumn);
						if(!summaryReport.getFiscalYearsDetails().get(fy).containsKey(m)){
							excelWriter.data(currentRow, currentColumn, "-");
							dataStyle = (m.getValue() % 2 == 0 ? whiteBackgroundCenterAlignStyle : greyBackgroundCenterAlignStyle);  
						} else {
							excelWriter.data(currentRow, currentColumn, Double.parseDouble(summaryReport.getFiscalYearsDetails().get(fy).get(m).value(parameter).toString()));
							dataStyle = (m.getValue() % 2 == 0 ? whiteBackgroundRightAlignStyle : greyBackgroundRightAlignStyle);
						}
						excelWriter.styleCell(currentRow, currentColumn, dataStyle);
						currentColumn = currentColumn + 1;
					}
					
				}
				
			}

			excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1, currentColumn - 1, CellStyle.BORDER_THICK);
			excelWriter.doBorder(currentRow - 11, currentRow, offsetColumn, offsetColumn , CellStyle.BORDER_THICK);
			
			for(int j = 0; j < noOfFiscalYear; j++){
				
				for (int i = 0; i < noOfParameters; i++) {
					excelWriter.doRightBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, CellStyle.BORDER_THIN);
				}
				excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters), currentColumn - 1, CellStyle.BORDER_THICK);
			}
			
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Total");
			excelWriter.styleCell(currentRow, currentColumn, excelWriter.LEFTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn, CellStyle.BORDER_THICK);
			
			for (FiscalYear fy : summaryReport.getFiscalYearsTotal().keySet()) {
				for (Parameter parameter : summaryReport.getParameters()) {
					IWecParameterVo vo = summaryReport.getFiscalYearsTotal().get(fy);
					currentColumn = currentColumn + 1;
					excelWriter.createColumn(currentRow, currentColumn);
					excelWriter.data(currentRow, currentColumn, vo.value(parameter));
					excelWriter.styleCell(currentRow, currentColumn, excelWriter.RIGHTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND);
				}
			}
			currentColumn = currentColumn + 1;
			for(int j = 0; j < noOfFiscalYear; j++){
				
				for (int i = 0; i < noOfParameters; i++) {
					excelWriter.doRightBorder(currentRow, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, CellStyle.BORDER_THIN);
				}
				excelWriter.doBorder(currentRow, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters), currentColumn - 1, CellStyle.BORDER_THICK);
			}
			
			currentRow = currentRow + 1;

		}

		logger.debug("Excel Ended");
		return excelWriter.getWorkBook();
	}
		
	
}
