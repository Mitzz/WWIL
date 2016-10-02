package com.enercon.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.global.utility.ExcelWriter;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.SummaryReport;
import com.enercon.struts.form.GridBifurcationReportForm;

public class SummaryDao implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(SummaryDao.class);
	
	public List<SummaryReport> getSummaryReportVo(GridBifurcationReportForm f) throws Exception {
		
		Set<Parameter> parameters = new LinkedHashSet<Parameter>();
		
		
		//Required Parameter from Client
		for(String parameter: f.getParameterSelected()){
			
			if(parameter.equals("gen")){
				parameters.add(Parameter.AVG_GENERATION_PER_WEC);
			} else if(parameter.equals("ophr")){
				parameters.add(Parameter.AVG_OPERATING_HOUR_PER_WEC);
			} else if(parameter.equals("ma")){
				parameters.add(Parameter.MA);
			} else if(parameter.equals("ga")){
				parameters.add(Parameter.GA);
			} else if(parameter.equals("fm")){
				parameters.add(Parameter.FM);
			} else if(parameter.equals("ws")){
				parameters.add(Parameter.WS);
			}  
		}

		//Determining Structure Type
		String structure = f.getStructureType();
		int structureType = -1;
		if(structure.equals("state"))
			structureType = 0;
		else if(structure.equals("area"))
			structureType = 1;
		else if(structure.equals("site"))
			structureType = 2;
		else if(structure.equals("wecType"))
			structureType = 3;
		
		
		logger.debug("From Date: " + f.getFromDateInOracleFormat() + ", To Date: " + f.getToDateInOracleFormat());
		List<SummaryReport> summaryReportVos = getSummaryReportVo(new LinkedHashSet<String>(Arrays.asList(f.getWecSelected())), f.getFromDateInOracleFormat(), f.getToDateInOracleFormat(), parameters, structureType);
		return summaryReportVos;
	}

	//Returns List of SummaryReport
	//Summary Report has 2d-Map which contains YearlyMonthWise Parameter Data(Year mapped to month and month mapped to ParameterVo)   
	//ParameterVo contains all parameter value
	public List<SummaryReport> getSummaryReportVo(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters, int structureType) throws Exception {
		
		List<SummaryReport> summaryReportVos = getSummaryReportWithInputData(wecIds, fromDate, toDate, parameters, structureType);
		
		for (SummaryReport summaryReport : summaryReportVos) {
			logger.debug(summaryReport);
			summaryReport.fiscalYearDetails().fiscalYearTotal();
			
			/*Map<FiscalYear, Map<Month, IWecParameterVo>> fys = summaryReport.getFiscalYearsDetails();
			
			for(FiscalYear fy: fys.keySet()){
				logger.debug("FY: " + fy.getValue());
				Map<Month, IWecParameterVo> months = fys.get(fy);
				for(Month month: months.keySet()){
					logger.debug("Month: " + month.getValue());
				}
			}*/
		}
		return summaryReportVos;
	}

	//List of SummaryReport
	//Each SummaryReport will be given input data
	private List<SummaryReport> getSummaryReportWithInputData(Set<String> wecIds, String fromDate, String toDate, Set<Parameter> parameters, int structureType) throws Exception {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<SummaryReport> summaryReportVo = new ArrayList<SummaryReport>();
		
		SortedSet<SummaryReport> summaryReportVoSet = new TreeSet<SummaryReport>(SummaryReport.CUSTOMER_STATE_SITE);		//Switch-case
		
		
		switch(structureType){
			case 0: 
				summaryReportVoSet = new TreeSet<SummaryReport>(SummaryReport.CUSTOMER_STATE);
				break;
			case 1: 
				summaryReportVoSet = new TreeSet<SummaryReport>(SummaryReport.CUSTOMER_STATE_AREA);
				break;
			case 2: 
				summaryReportVoSet = new TreeSet<SummaryReport>(SummaryReport.CUSTOMER_STATE_SITE);
				break;
			case 3: 
				summaryReportVoSet = new TreeSet<SummaryReport>(SummaryReport.CUSTOMER_WECTYPE);
				break;
			default: throw new IllegalArgumentException("Report Type Undefined");
		}
		//For Avoiding Duplication of Same Summary Report
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> selectedWecIds : GlobalUtils.splitArrayList(wecIds, 950)){
				switch(structureType){
					case 0: 
						query = 
								"select  S_customer_name, S_customer_id, S_state_name, S_state_id, count(1) " + 
								"from customer_meta_data  " + 
								"where s_wec_id in " + GlobalUtils.getStringFromArrayForQuery(selectedWecIds) + 
								"group by  S_customer_name, S_customer_id, S_state_name, S_state_id " +
								"order by S_customer_name, S_state_name ";
						break;
					case 1: 
						query = 
								"select  S_customer_name, S_customer_id, S_state_name, S_state_id, S_area_name, S_area_id, count(1) " + 
								"from customer_meta_data  " + 
								"where s_wec_id in " + GlobalUtils.getStringFromArrayForQuery(selectedWecIds) + 
								"group by  S_customer_name, S_customer_id, S_state_name, S_state_id, S_area_name, S_area_id " +
								"order by S_customer_name, S_state_name, S_area_name ";
						break;
					case 2: 
						query = 
								"select  S_customer_name, S_customer_id, S_state_name, S_state_id, S_site_name,S_site_id, count(1) " + 
								"from customer_meta_data  " + 
								"where s_wec_id in " + GlobalUtils.getStringFromArrayForQuery(selectedWecIds) + 
								"group by  S_customer_name, S_customer_id, S_state_name, S_state_id, S_site_name,S_site_id " +
								"order by S_customer_name, S_state_name, S_site_name ";
						break;
					case 3: 
						query = 
								"select  S_customer_name, S_customer_id, S_wec_type, count(1) " + 
								"from customer_meta_data  " + 
								"where s_wec_id in " + GlobalUtils.getStringFromArrayForQuery(selectedWecIds) + 
								"group by  S_customer_name, S_customer_id, S_wec_type " +
								"order by S_customer_name, S_wec_type ";
						break;
					default: throw new IllegalArgumentException("Report Type Undefined");
				}
				
				prepStmt = conn.prepareStatement(query);
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					Set<String> actualWecIds = null;
					Set<String> allWecIdsBasedOnCustomerStateSite = null;
					//Switch-case
					switch(structureType){
						case 0: 
							allWecIdsBasedOnCustomerStateSite = WecMasterDao.getWecIdsBasedOnCustomerState(rs.getString("S_customer_id"), rs.getString("S_state_id"));
							break;
						case 1: 
							allWecIdsBasedOnCustomerStateSite = WecMasterDao.getWecIdsBasedOnCustomerStateArea(rs.getString("S_customer_id"), rs.getString("S_state_id"), rs.getString("S_area_id"));
							break;
						case 2: 
							allWecIdsBasedOnCustomerStateSite = WecMasterDao.getWecIdsBasedOnCustomerStateSite(rs.getString("S_customer_id"), rs.getString("S_state_id"), rs.getString("S_site_id"));
							break;
						case 3: 
							allWecIdsBasedOnCustomerStateSite = WecMasterDao.getWecIdsBasedOnCustomerWecType(rs.getString("S_customer_id"), rs.getString("S_wec_type"));
							break;
						default: throw new IllegalArgumentException("Report Type Undefined");
					}
					
					//Doing Intersection
					actualWecIds = GlobalUtils.intersection(allWecIdsBasedOnCustomerStateSite, selectedWecIds);
					
					SummaryReport newSummaryReport = null;
					
					String customerName = rs.getString("S_customer_name");
					String stateName = null;
					String areaName = null;
					String siteName = null;
					String wecType = null;
					//Switch-case
					newSummaryReport = new SummaryReport();
					newSummaryReport.setCustomerName(customerName);
					newSummaryReport.setWecIds(actualWecIds);
					newSummaryReport.setFromDate(fromDate);
					newSummaryReport.setToDate(toDate);
					switch(structureType){
						case 0:
							stateName = rs.getString("S_STATE_NAME");
							newSummaryReport.setStateName(stateName);
							
							break;
						case 1:
							stateName = rs.getString("S_STATE_NAME");
							areaName = rs.getString("S_AREA_NAME");
							
							newSummaryReport.setStateName(stateName);
							newSummaryReport.setAreaName(areaName);
							
							break;
						case 2:
							stateName = rs.getString("S_STATE_NAME");
							siteName = rs.getString("S_SITE_NAME");
							
							newSummaryReport.setStateName(stateName);
							newSummaryReport.setSiteName(siteName);
							break;
						case 3: 
							wecType = rs.getString("S_WEC_TYPE");
							
							newSummaryReport.setWecType(wecType);
							break;
						default: throw new IllegalArgumentException("Report Type Undefined");
					}
//					logger.debug(newSummaryReport);
					if(summaryReportVoSet.contains(newSummaryReport)){
						logger.debug(newSummaryReport);
						Set<String> presentWecIds = null;
						/*for (SummaryReport alreadyPresentSummaryReport : summaryReportVoSet) {
							if(alreadyPresentSummaryReport.equals(newSummaryReport)){
								presentWecIds = alreadyPresentSummaryReport.getWecIds();
								break;
							}
						}*/
						presentWecIds = (summaryReportVoSet.tailSet(newSummaryReport)).first().getWecIds();
						//logger.debug(presentWecIds);
						
						summaryReportVoSet.remove(newSummaryReport);
						actualWecIds.addAll(presentWecIds);
						
						newSummaryReport = new SummaryReport();
						newSummaryReport.setCustomerName(customerName);
						newSummaryReport.setWecIds(actualWecIds);
						newSummaryReport.setFromDate(fromDate);
						newSummaryReport.setToDate(toDate);
						switch(structureType){
							case 0:
								stateName = rs.getString("S_STATE_NAME");
								newSummaryReport.setStateName(stateName);
								
								break;
							case 1:
								stateName = rs.getString("S_STATE_NAME");
								areaName = rs.getString("S_AREA_NAME");
								
								newSummaryReport.setStateName(stateName);
								newSummaryReport.setAreaName(areaName);
								
								break;
							case 2:
								stateName = rs.getString("S_STATE_NAME");
								siteName = rs.getString("S_SITE_NAME");
								
								newSummaryReport.setStateName(stateName);
								newSummaryReport.setSiteName(siteName);
								break;
							case 3: 
								wecType = rs.getString("S_WEC_TYPE");
								
								newSummaryReport.setWecType(wecType);
								break;
							default: throw new IllegalArgumentException("Report Type Undefined");
						}
						//Switch-case
						logger.debug(newSummaryReport);
						summaryReportVoSet.add(newSummaryReport.setParameters(parameters));
					} else {
						summaryReportVoSet.add(newSummaryReport.setParameters(parameters));
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
//			logger.debug(summaryReportVo);
			return summaryReportVo;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public XSSFWorkbook generateExcelView(List<SummaryReport> summaryReports, int viewType, int reportType) throws IOException{
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

//		XSSFCellStyle headerStyle =   excelWriter.CENTERALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyle =   excelWriter.CENTERALIGN_BLACKFONT_LIGHTBLUEBACKGROUND;
//		XSSFCellStyle headerStyleLeftAlign = excelWriter.LEFTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleLeftAlign = excelWriter.LEFTALIGN_BLACKFONT_LIGHTBLUEBACKGROUND;
//		XSSFCellStyle headerStyleRightAlign = excelWriter.RIGHTALIGN_WHITEFONT_DARKGREYBACKGROUND;
		XSSFCellStyle headerStyleRightAlign = excelWriter.RIGHTALIGN_BLACKFONT_LIGHTBLUEBACKGROUND;
		
		XSSFCellStyle parameterStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle fiscalYearStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		
		XSSFCellStyle greyBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundCenterAlignStyle = excelWriter.CENTERALIGN_BLACKFONT_WHITEBACKGROUND;
		XSSFCellStyle greyBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_LIGHTGREYBACKGROUND;
		XSSFCellStyle whiteBackgroundRightAlignStyle = excelWriter.RIGHTALIGN_BLACKFONT_WHITEBACKGROUND;
		
//		List<FiscalYear> fiscalYears = new ArrayList<FiscalYear>();
//		List<Parameter> parameters = new ArrayList<Parameter>();
		
		String columnData = "";
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
			
			//Row 1
			currentRow = offsetRowNo + currentRow;
			totalColumn = (totalColumn < 6 ? 6 : totalColumn);
			
			currentColumn = offsetColumn;
			
			excelWriter.createRow(currentRow);
			
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, summaryReport.getCustomerName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyle);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalColumn - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalColumn - 1, CellStyle.BORDER_THICK);
			
			//Row 2
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			
			switch (reportType) {
			case 0:
			case 1:
			case 2:
				columnData = "State: " + summaryReport.getStateName();
				break;
			case 3:
				columnData = "Wec Type: " + summaryReport.getWecType();
				break;
			default:
				break;
			}
			
			excelWriter.data(currentRow, currentColumn, columnData);
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + stateTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + stateTextWidth - 1, CellStyle.BORDER_THICK);
//			
			
			currentColumn = offsetColumn + totalColumn - siteTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			
			switch (reportType) {
			case 0:
				columnData = "";
				break;
			case 1:
				columnData = "Area: " + summaryReport.getAreaName();
				break;
			case 2:
				columnData = "Site: " + summaryReport.getSiteName();
				break;
			case 3:
				columnData = "";
				break;
			default:
				break;
			}
			
			excelWriter.data(currentRow, currentColumn, columnData);

			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + siteTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + siteTextWidth - 1, CellStyle.BORDER_THICK);
			
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
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THICK);
			}
			
			//Row 3
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			columnData = "Total Wecs : " + summaryReport.getWecIds().size();
			excelWriter.data(currentRow, currentColumn, columnData);
			excelWriter.styleCell(currentRow, currentColumn, headerStyleLeftAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalWecsTextWidth - 1, CellStyle.BORDER_THICK);
//			
			currentColumn = offsetColumn + totalColumn - periodTextWidth;
			excelWriter.createColumn(currentRow, currentColumn);
			columnData = "Period : " + summaryReport.getFromDate() + " to " + summaryReport.getToDate();
			excelWriter.data(currentRow, currentColumn, columnData);
			excelWriter.styleCell(currentRow, currentColumn, headerStyleRightAlign);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + periodTextWidth - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + periodTextWidth - 1, CellStyle.BORDER_THICK);
			
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
				excelWriter.doBorder(currentRow, currentRow, start, end, CellStyle.BORDER_THICK);
			}
			
//			Row 4
			currentRow = currentRow + 1;
			totalColumn = noOfParameters * noOfFiscalYear + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
//			logger.debug("First Column: " + currentColumn);
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			
			
			int col = 0;
			if(totalColumn < 6)
				col = 6 - totalColumn;
			
//			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + col);
			
			currentColumn = 1 + currentColumn + col;
			
			
			switch (viewType) {
			case 0:
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
				break;
			case 1:
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
				break;
			default:
				break;
			}
			
//			Row 5
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "");
			
//			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + col);
			excelWriter.mergeColumn(currentRow - 1, currentRow, currentColumn, currentColumn + col);
			
			excelWriter.doBorder(currentRow - 1, currentRow, currentColumn, currentColumn + col, CellStyle.BORDER_THICK);
			
			currentColumn = 1 + currentColumn + col;
			
			switch (viewType) {
			case 0:
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
				break;
			case 1:
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
				break;
			default:
				break;
			}
			
			for(Month m : Month.fiscalYearValues(4, 3)){
				currentRow++;

				//Creating Row and Column
				excelWriter.createRow(currentRow);
				currentColumn = offsetColumn;
				excelWriter.createColumn(currentRow, currentColumn);
				
				//Populating Data
				excelWriter.data(currentRow, currentColumn, Month.getShortHandMonthName(m));
				excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + col);
				
				//Styling Cell
				if(m.getValue() % 2 == 0){
					excelWriter.styleCell(currentRow, currentColumn, whiteBackgroundCenterAlignStyle);
				} else {
					excelWriter.styleCell(currentRow, currentColumn, greyBackgroundCenterAlignStyle);
				}
				
				currentColumn = currentColumn + 1 + col;
				XSSFCellStyle dataStyle = null;
				switch (viewType) {
				case 0:
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
							currentColumn++;
						}
					}
					break;
				case 1:
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
					break;
				default:
					break;
				}
				
			}

			excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1, currentColumn - 1, CellStyle.BORDER_THICK);
			excelWriter.doBorder(currentRow - 11, currentRow, offsetColumn, offsetColumn , CellStyle.BORDER_THICK);
			
			
			switch (viewType) {
			case 0:
				for(int j = 0; j < noOfParameters; j++){
					
					for (int i = 0; i < noOfFiscalYear; i++) {
						excelWriter.doRightBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, currentColumn - totalColumn + 1 + (j * noOfFiscalYear) + i, CellStyle.BORDER_THIN);
					}
					excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfFiscalYear), currentColumn - 1, CellStyle.BORDER_THICK);
				}
				break;
			case 1:
				for(int j = 0; j < noOfFiscalYear; j++){
					
					for (int i = 0; i < noOfParameters; i++) {
						excelWriter.doRightBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, currentColumn - totalColumn + 1 + (j * noOfParameters) + i, CellStyle.BORDER_THIN);
					}
					excelWriter.doBorder(currentRow - 11, currentRow, currentColumn - totalColumn + 1 + (j * noOfParameters), currentColumn - 1, CellStyle.BORDER_THICK);
				}
				break;
			default:
				break;
			}
			
//			New Row
			currentRow = currentRow + 1;
			excelWriter.createRow(currentRow);
			currentColumn = offsetColumn;
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, "Total");
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + col);
			excelWriter.styleCell(currentRow, currentColumn, excelWriter.LEFTALIGN_BLACKFONT_MEDIUMGREYBACKGROUND);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + col, CellStyle.BORDER_THICK);
			currentColumn = currentColumn + col;
			switch (viewType) {
			case 0:
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
				break;
			case 1:
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
				break;
			default:
				break;
			}
			
			for(int i = 1; i <= (noOfFiscalYear * noOfParameters); i++)
				excelWriter.setColumnWidth(offsetColumn + col + i, 2300);
			
			currentRow = currentRow + 1;
		}
		
		logger.debug("Excel Ended");
		return excelWriter.getWorkBook();
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
			
			//Row 1
			currentRow = offsetRowNo + currentRow;
			totalColumn = (totalColumn < 6 ? 6 : totalColumn);
			
			currentColumn = offsetColumn;
			
			excelWriter.createRow(currentRow);
			
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, summaryReport.getCustomerName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyle);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalColumn - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalColumn - 1, CellStyle.BORDER_THICK);
			
			//Row 2
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
			
			//Row 3
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
			
//			Row 4
			currentRow = currentRow + 1;
			totalColumn = noOfParameters * noOfFiscalYear + 1;
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
			
//			Row 5
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
			
			for(Month m : Month.fiscalYearValues(4, 3)){
				currentRow++;

				//Creating Row and Column
				excelWriter.createRow(currentRow);
				currentColumn = offsetColumn;
				excelWriter.createColumn(currentRow, currentColumn);
				
				//Populating Data
				excelWriter.data(currentRow, currentColumn, Month.getShortHandMonthName(m));
				
				//Styling Cell
				if(m.getValue() % 2 == 0){
					excelWriter.styleCell(currentRow, currentColumn, whiteBackgroundCenterAlignStyle);
				} else {
					excelWriter.styleCell(currentRow, currentColumn, greyBackgroundCenterAlignStyle);
				}
				
				currentColumn++;
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
						currentColumn++;
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
			
//			New Row
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
			
//			Row 1
			currentRow = offsetRowNo + currentRow;
			currentColumn = offsetColumn;
			excelWriter.createRow(currentRow);
			
			excelWriter.createColumn(currentRow, currentColumn);
			excelWriter.data(currentRow, currentColumn, summaryReport.getCustomerName());
			excelWriter.styleCell(currentRow, currentColumn, headerStyle);
			excelWriter.mergeColumnWithRow(currentRow, currentColumn, currentColumn + totalColumn - 1);
			excelWriter.doBorder(currentRow, currentRow, currentColumn, currentColumn + totalColumn - 1, CellStyle.BORDER_THICK);
			
//			Row 2
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
//			excelWriter.data(currentRow, currentColumn, "Site: " + summaryReport.getSiteName());
			excelWriter.data(currentRow, currentColumn, "Site: " + "All");
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
			
//			Row 3
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
			
//			Row 4
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
			
//			Row 5
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
			
//			New Row
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