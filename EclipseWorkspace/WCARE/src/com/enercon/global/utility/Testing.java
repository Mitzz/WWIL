package com.enercon.global.utility;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.enercon.dao.SummaryDao;
import com.enercon.dao.master.WecMasterDao;
import com.enercon.model.DataVoService;
import com.enercon.model.WecLocationData;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.ParameterEvaluator;
import com.enercon.reports.pojo.GenerationReport;

public class Testing{
	
	public static void main(String[] args) throws Exception{
		
	}

	

	private static void testingForGenerationReport() throws SQLException, ParseException {

		final ApplicationContext ac = new ClassPathXmlApplicationContext("com/enercon/spring/bean/bean-config.xml");
		StringBuffer htmlMessage = new StringBuffer();
		GenerationReport generationReport = (GenerationReport) ac.getBean("generationReport2");
		
		int srNo = 0;
		
		WecMasterDao wecMasterDao = new WecMasterDao();
		Set<String> wecIds = wecMasterDao.getWecIdsBasedOnCustomerIds(generationReport.getCustomerIds());
		String date = "27-SEP-2015";
		
		generationReport.setTotalWecCapacity(wecMasterDao.getTotalWecCapacityBasedOnWecIds(wecIds));
		
		htmlMessage.append( 
						" <html> " +
						"     <head> " +
						"         <meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\" /> " +
						"         <style type=\"text/css\"> " +
						"             .font-family-arial{ " +
						"                 font-family: arial; " +
						"             } " +
						"             .font-weight-bold{ " +
						"                 font-weight: bold; " +
						"             } " +
						"             .center-align{ " +
						"                 text-align: center; " +
						"             } " +
						"             .common{ " +
						"                 font-family: arial; " +
						" 		font-weight: bold; " +
						"                 text-align: center; " +
						"             } " +
						"             .title{ " +
						"                 font-size: 20px; " +
						"             } " +
						"              " +
						"             .header{ " +
						"                 font-size: 17px; " +
						"             } " +
						"             .normal-data{ " +
						"                 font-size: 12px; " +
						"             } " +
						"              " +
						"             .loss-data{ " +
						"                 background-color: #DAA520; " +
						"             } " +
						"         </style> " +
						"          " +
						"     </head> " +
						"     <body> " +
						"         <table width='100%' border=1 cellspacing=0 cellpadding=0 class='common title' style='border: 1px solid #D1DBDD;'> " +
						"             <tr bgcolor='#ADD2D3' class='common'> " +
						"                 <td colspan='16'>Generation Report</td> " +
						"             </tr> " +
						"             <tr bgcolor='#ADD2D3' class='common header'> " +
						"                 <td colspan='16'>" + generationReport.getCustomerFullName() + "</td> " +
						"             </tr> " +
						"             <tr class='common normal-data'> " +
						"                 <td colspan='8'>Total Capacity: " + generationReport.getTotalWecCapacity()/1000 + " MW</td> " +
						"                 <td colspan='8'>Report Date: " + date + "</td> " +
						"             </tr> " +
						"             <tr class='common normal-data'> " +
						"                 <td colspan='3'>&nbsp;</td> " +
						"                 <td colspan='8'>Daily</td> " +
						"                 <td>FY:2014-15</td> " +
						"                 <td colspan='4'>Cummulative FY:2015-16</td> " +
						"             </tr> " +
						"             <tr bgcolor='#BBB87E' class='common normal-data'> " +
						"                 <td><b>SN</b></td> " +
						"                 <td><b>State Name</b></td> " +
						"                 <td><b>Total WEC Commisioned</b></td> " +
						"                 <td><b>Avg KWH</b></td> " +
						"                 <td><b>Total KWH</b></td> " +
						"                 <td><b> Revenue(Rs In Lacs) with LL @ 3%</b></td> " +
						"                 <td class='loss-data'><b> Revenue(Rs) Loss Due To FM</b></td> " +
						"                 <td class='loss-data'><b>Revenue(Rs) Loss Due To LR</b></td> " +
						"                 <td><b>CF(%)</b></td> " +
						"                 <td><b>MA(%)</b></td> " +
						"                 <td><b>GA(%)</b></td> " +
						"                 <td><b>MWH</b></td> " +
						"                 <td><b>MWH</b></td> " +
						"                 <td><b> Revenue(Rs In Lacs)</b></td> " +
						"                 <td class='loss-data'><b> Revenue Loss Due To FM(Rs In Lacs)</b></td> " +
						"                 <td class='loss-data'><b> Revenue Loss Due To LR(Rs In Lacs)</b></td> " +
						"             </tr> ") ;
		
		System.out.println("---------------------------------");
		Map<String, IWecParameterVo> dailyDate = getStateWiseWecParamterVo(wecIds, date);
		Map<String, IWecParameterVo> currentFiscalYearData = getCurrentFiscalYearData(wecIds, date);
		Map<String, IWecParameterVo> previousFiscalYearData = getPreviousFiscalYearData(wecIds, date);
		
		long totalSize = 0;
		double totalRevenue = 0;
		
		double totalFmLoss = 0;
		double totalLrLoss = 0;
		
		double fyTotalRevenue = 0;
		double fyTotalFmLoss = 0;
		double fyTotalLrLoss = 0;
		
		
		srNo = 0;
		for(String stateId: dailyDate.keySet()){
			
			long size = dailyDate.get(stateId).size();
			long generation = dailyDate.get(stateId).generation();
			long averageGeneration = generation/size;
			double revenue = NumberUtility.round(dailyDate.get(stateId).revenue()/100000.0, 2);
			double fmLoss = dailyDate.get(stateId).fmLoss();
			double lrLoss = dailyDate.get(stateId).lrLoss();
			double cf = dailyDate.get(stateId).cf();
			double ma = dailyDate.get(stateId).ma();
			double ga = dailyDate.get(stateId).ga();
			
			long previousFYGeneration = previousFiscalYearData.get(stateId).generation()/1000;
			
			long currentFYGeneration = currentFiscalYearData.get(stateId).generation()/1000;
			double currentFYRevenue = NumberUtility.round(currentFiscalYearData.get(stateId).revenue()/100000.0, 2);
			double currentFYFmLoss = NumberUtility.round(currentFiscalYearData.get(stateId).fmLoss()/100000.0, 2);
			double currentFYLrLoss = NumberUtility.round(currentFiscalYearData.get(stateId).lrLoss()/100000.0, 2);
			
			totalSize += size;
			System.out.println(totalRevenue);
			totalRevenue += revenue;
			System.out.println(totalRevenue);
			totalFmLoss += fmLoss;
			totalLrLoss += lrLoss;
			
			fyTotalRevenue += currentFYRevenue;
			fyTotalFmLoss += currentFYFmLoss;
			fyTotalLrLoss += currentFYLrLoss;
			
			System.out.println(stateId + ":" + size + ":" + averageGeneration + ":" + generation + ":" + revenue + ":" + fmLoss + ":" + lrLoss + ":" + cf + ":" + ma + ":" + ga + ":" + previousFYGeneration + ":" + currentFYGeneration + ":" + currentFYRevenue + ":" + currentFYFmLoss + ":" + currentFYLrLoss);
			
			htmlMessage.append( 
							" <tr class='common normal-data'> " +
							"                 <td> " + (++srNo)+ "</td> " +
							"                 <td>" + "data" + "</td> " +
							"                 <td>" + size + "</td> " +
							"                 <td>" + averageGeneration + "</td> " +
							"                 <td>" + generation + "</td> " +
							"                 <td>" + revenue + "</td> " +
							"                 <td>" + fmLoss + "</td> " +
							"                 <td>" + lrLoss + "</td> " +
							"                 <td>" + cf + "</td> " +
							"                 <td>" + ma + "</td> " +
							"                 <td>" + ga + "</td> " +
							"                 <td>" + previousFYGeneration + "</td> " +
							"                 <td>" + currentFYGeneration + "</td> " +
							"                 <td>" + currentFYRevenue + "</td> " +
							"                 <td>" + currentFYFmLoss + "</td> " +
							"                 <td>" + currentFYLrLoss + "</td> " +
							"             </tr> ") ;
		}
		
		htmlMessage.append( 
						" <tr bgcolor='#BBB87E' class='common normal-data'> " +
						"                 <td colspan='2' >Total</td> " +
						"                 <td>" + wecIds.size() + "</td> " +
						"                 <td colspan='2' >&nbsp;</td> " +
						"                 <td>" + NumberUtility.round(totalRevenue, 2) + "</td> " +
						"                 <td class='loss-data'>" + NumberUtility.round(totalFmLoss, 2) + "</td> " +
						"                 <td class='loss-data'>" + NumberUtility.round(totalLrLoss, 2) + "</td> " +
						"                 <td colspan='5' >&nbsp;</td> " +
						"                 <td>" + fyTotalRevenue + "</td> " +
						"                 <td class='loss-data'>" + NumberUtility.round(fyTotalFmLoss, 2) + "</td> " +
						"                 <td class='loss-data'>" + NumberUtility.round(fyTotalLrLoss, 2) + "</td> " +
						"             </tr> " +
						"         </table> ") ;
		//System.out.println("Less than 95");
		htmlMessage.append("<br />") ;
		htmlMessage.append(
				" <table width='100%' border=1 cellspacing=0 cellpadding=0 class='common' style='border: 1px solid #D1DBDD;'> " +
				"             <tr bgcolor='#ADD2D3' class='common header'> " +
				"                 <td class='center-align' colspan='9'>Daily Details of WECs for which MA is less than 95% </td> " +
				"             </tr> " +
				"             <tr bgcolor='#BBB87E' class='common normal-data'> " +
				"                 <td><b>SN</b></td> " +
				"                 <td><b>Area Name</b></td> " +
				"                 <td colspan='2' ><b>WEC Name</b></td> " +
				"                 <td><b>Generation</b></td> " +
				"                 <td><b>Operating Hour</b></td> " +
				"                 <td><b>MA(%)</b></td> " +
				"                 <td colspan='2' ><b>Remarks</b></td> " +
				"             </tr> ") ;
		
		System.out.println(totalSize + ":" + totalRevenue + ":" + totalFmLoss + ":" + totalLrLoss + ":" + fyTotalRevenue + ":" + fyTotalFmLoss + ":" + fyTotalLrLoss);
		
		System.out.println("-------------------------------------------------");
		
		Map<String, Map<WecLocationData, String>> wecLocationData = new WecMasterDao().getActiveWecLocationData();
		
		System.out.println("-------------------------------------------------");
		Map<String, IWecParameterVo> dailyMALessThan95 = dailyMALessThan95(wecIds, date);
		srNo = 0;
		for(String wecId: dailyMALessThan95.keySet()){
			IWecParameterVo wecParameterVo = dailyMALessThan95.get(wecId);
			
			Map<WecLocationData, String> locationData = wecLocationData.get(wecId);
			String areaName = locationData.get(WecLocationData.AREANAME);
			String wecName = locationData.get(WecLocationData.WECNAME);
			
			long generation = wecParameterVo.generation();
			long operatingHour = wecParameterVo.operatingHour();
			double ma = wecParameterVo.ma();
			String remark = wecParameterVo.remark();
			
			System.out.println(areaName + ":" + wecName + ":" + generation + ":" + operatingHour + ":" + ma + ":" + remark);
			
			htmlMessage.append(
							" <tr class='common normal-data'> " +
							"                 <td>" + (++srNo) + "</td> " +
							"                 <td>" + areaName + "</td> " +
							"                 <td colspan='2' >" + wecName + "</td> " +
							"                 <td>" + generation + "</td> " +
							"                 <td>" + operatingHour/60 + "</td> " +
							"                 <td>" + ma + "</td> " +
							"                 <td colspan='2' align='left'>" + remark + "</td> " +
							"             </tr> " ) ;
		}
		
		htmlMessage.append( "</table>");
		htmlMessage.append("<br />");
		htmlMessage.append(
						" <table width='100%' border=1 cellspacing=0 cellpadding=0 class='common' style='border: 1px solid #D1DBDD;'> " +
						"             <tr bgcolor='#ADD2D3' class='common header'> " +
						"                 <td colspan='7'> Monthly Details of WECs for which MA is less than 98%</td> " +
						"             </tr> " +
						"             <tr bgcolor='#BBB87E' class='common normal-data'> " +
						"                 <td><b>SN</b></td> " +
						"                 <td><b>State Name</b></td> " +
						"                 <td><b>Site Name</b></td> " +
						"                 <td><b>WEC Name</b></td> " +
						"                 <td><b>Generation</b></td> " +
						"                 <td><b>Operating Hour</b></td> " +
						"                 <td><b>MA(%)</b></td> " +
						"             </tr> ") ;
		System.out.println("-------------------------------------------------");
		
		System.out.println("-------------------------------------------------");
		
		Map<String, IWecParameterVo> monthlyMALessThan98 = monthlyMALessThan98(wecIds, date);
		srNo = 0;
		for(String wecId: monthlyMALessThan98.keySet()){
			IWecParameterVo wecParameterVo = monthlyMALessThan98.get(wecId);
			Map<WecLocationData, String> locationData = wecLocationData.get(wecId);
			String stateName = locationData.get(WecLocationData.STATENAME);
			String siteName = locationData.get(WecLocationData.SITENAME);
			String wecName = locationData.get(WecLocationData.WECNAME);
			
			long generation = wecParameterVo.generation();
			long operatingHour = wecParameterVo.operatingHour();
			double ma = wecParameterVo.ma();
			
			System.out.println(stateName + ":" + siteName + ":" + wecName + ":" + generation + ":" + operatingHour + ":" + ma);
			
			htmlMessage.append(
							" <tr class='common normal-data'> " +
							"                 <td>" + (++srNo) + "</td> " +
							"                 <td>" + stateName + "</td> " +
							"                 <td>" + siteName + "</td> " +
							"                 <td>" + wecName + "</td> " +
							"                 <td>" + generation + "</td> " +
							"                 <td>" + operatingHour/60 + "</td> " +
							"                 <td>" + ma + "</td> " +
							"             </tr> ") ;
		}
		htmlMessage.append( "</table> </body> " + " </html> ");
		System.out.println("-------------------------------------------------");
		System.out.println(htmlMessage);
		((ClassPathXmlApplicationContext)ac).close();
	}

	private static Map<String, IWecParameterVo> monthlyMALessThan98(Set<String> wecIds, String date)
			throws SQLException, ParseException {
		Set<Parameter> maLessThan98Parameters = new LinkedHashSet<Parameter>();
		maLessThan98Parameters.add(Parameter.GENERATION);
		maLessThan98Parameters.add(Parameter.OPERATING_HOUR);
		maLessThan98Parameters.add(Parameter.MA);
		
		
		
		Map<String, IWecParameterVo> wecWiseWecParameterVo = new ParameterEvaluator().getWecWiseWecParameterVo(DateUtility.getFirstDateOfMonth(date), date, wecIds, maLessThan98Parameters);
		new DataVoService().periodMALessThan98(wecIds, DateUtility.getFirstDateOfMonth(date), date, maLessThan98Parameters);
		Map<String, IWecParameterVo> wecWiseWecParameterVoLessThan98 = new HashMap<String, IWecParameterVo>();
		
		System.out.println("----MA < 98----");
		for(String wecId: wecWiseWecParameterVo.keySet()){
			IWecParameterVo vo = wecWiseWecParameterVo.get(wecId);
			
			if(vo.ma() < 98){
				wecWiseWecParameterVoLessThan98.put(wecId, vo);
			}
			
		}
		
		return wecWiseWecParameterVoLessThan98;
	}

	private static Map<String, IWecParameterVo> dailyMALessThan95(Set<String> wecIds, String date)
			throws SQLException, ParseException {
		Set<Parameter> maLessThan95Parameters = new LinkedHashSet<Parameter>();
		maLessThan95Parameters.add(Parameter.GENERATION);
		maLessThan95Parameters.add(Parameter.OPERATING_HOUR);
		maLessThan95Parameters.add(Parameter.MA);
		maLessThan95Parameters.add(Parameter.REMARK);
		
		Map<String, IWecParameterVo> wecWiseWecParameterVo = new ParameterEvaluator().getWecWiseWecParameterVo(date, wecIds, maLessThan95Parameters);
		Map<String, IWecParameterVo> wecWiseWecParameterVoLessThan95 = new HashMap<String, IWecParameterVo>();
		new DataVoService().dailyMALessThan95(wecIds, date, maLessThan95Parameters);
		System.out.println("----MA < 95----");
		for(String wecId: wecWiseWecParameterVo.keySet()){
			IWecParameterVo vo = wecWiseWecParameterVo.get(wecId);
			
			if(vo.ma() < 95){
				wecWiseWecParameterVoLessThan95.put(wecId, vo);
			}
			
		}
		
		return wecWiseWecParameterVoLessThan95;
	}

	private static Map<String, IWecParameterVo> getPreviousFiscalYearData(Set<String> wecIds, String date) throws SQLException, ParseException {
		Set<Parameter> previousFiscalYearParameters = new LinkedHashSet<Parameter>();
		previousFiscalYearParameters.add(Parameter.GENERATION);

		System.out.println(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(date) - 1) + ":" + DateUtility.getBackwardDateInStringWRTMonths(date, -12));
		Map<String, IWecParameterVo> previousFiscalYearWecParameterVo = new ParameterEvaluator().getStateWiseWecParameterVo(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(date) - 1), DateUtility.getBackwardDateInStringWRTMonths(date, -12), wecIds, previousFiscalYearParameters);
		System.out.println("----Previous Fiscal Year Data----");
		for(String stateId: previousFiscalYearWecParameterVo.keySet()){
			System.out.println("State Id: " + stateId);
			System.out.println(previousFiscalYearWecParameterVo.get(stateId));
		}
		
		return previousFiscalYearWecParameterVo;
	}

	private static Map<String, IWecParameterVo> getCurrentFiscalYearData(Set<String> wecIds, String date) throws SQLException, ParseException {
		Set<Parameter> currentFiscalYearParameters = new LinkedHashSet<Parameter>();
		currentFiscalYearParameters.add(Parameter.GENERATION);
		currentFiscalYearParameters.add(Parameter.REVENUE);
		currentFiscalYearParameters.add(Parameter.FM_LOSS);
		currentFiscalYearParameters.add(Parameter.LR_LOSS);
		
		Map<String, IWecParameterVo> currentFiscalYearWecParameterVo = new ParameterEvaluator().getStateWiseWecParameterVo(DateUtility.getFirstDateOfFiscalYear(DateUtility.getFiscalYear(date)), date, wecIds, currentFiscalYearParameters);
		System.out.println("----Current Fiscal Year Data----");
		for(String stateId: currentFiscalYearWecParameterVo.keySet()){
			System.out.println("State Id: " + stateId);
			System.out.println(currentFiscalYearWecParameterVo.get(stateId));
		}
		
		return currentFiscalYearWecParameterVo;
	}

	private static Map<String, IWecParameterVo> getStateWiseWecParamterVo(Set<String> wecIds, String date) throws SQLException {
		Set<Parameter> dailyParameters = new LinkedHashSet<Parameter>();
		dailyParameters.add(Parameter.GENERATION);
		dailyParameters.add(Parameter.REVENUE);
		dailyParameters.add(Parameter.FM_LOSS);
		dailyParameters.add(Parameter.LR_LOSS);
		dailyParameters.add(Parameter.CF);
		dailyParameters.add(Parameter.MA);
		dailyParameters.add(Parameter.GA);
		
		Map<String, IWecParameterVo> dailyWecParameterVo = new ParameterEvaluator().getStateWiseWecParameterVo(date, wecIds, dailyParameters);
		System.out.println("----Daily Data----");
		for(String stateId : dailyWecParameterVo.keySet()){
			System.out.println("State Id: " + stateId);
			System.out.println(dailyWecParameterVo.get(stateId));
		}
		
		return dailyWecParameterVo;
	}

	private static void testingForComprehensiveReport() throws Exception{
		String fromDate = null;
		String toDate = null;
		fromDate = "15-DEC-2012";
		toDate =   "15-JAN-2013";
		
		fromDate = "01-APR-2013";
		toDate =   "30-AUG-2015";
		
	//	fromDate = "15-DEC-2010";
	//	toDate =   "15-APR-2012";
		
	//	fromDate = "15-JAN-2012";
	//	toDate =   "15-APR-2012";
	//	
	//	fromDate = "15-DEC-2011";
	//	toDate =   "16-DEC-2012";
	//	
//		fromDate = "15-NOV-2013";
//		toDate =   "16-DEC-2014";
	//	
//		fromDate = "15-DEC-2014";
//		toDate =   "16-JAN-2015";
	//	
//		fromDate = "15-DEC-2014";
//		toDate =   "16-DEC-2014";
	//	
	//	fromDate = "15-NOV-2014";
	//	toDate =   "16-DEC-2014";
	//	
	//	fromDate = "16-DEC-2014";
	//	toDate =   "16-DEC-2014";
		
	//	fromDate = "01-NOV-2014";
	//	toDate =   "30-NOV-2014";
		System.out.println("Started");
		SummaryDao dao = new SummaryDao();
//		dao.generateParameterWiseExcelView(dao.getSummaryReportVo(partialClpWecIds(), fromDate, toDate));
		dao.generateYearWiseExcelView(dao.getSummaryReportVo(allClpWecIds(), fromDate, toDate, true));
		
	//	dao.generateYearWiseExcelView(dao.getSummaryReportVo(SummaryReport.allClpWecIds(), fromDate, toDate));}
	}
	public static Set<String> allClpWecIds() {
		Set<String> clpWecIds = new HashSet<String>();
		
		clpWecIds.add("0905000035");
		clpWecIds.add("0905000036");
		clpWecIds.add("1102000004");
		clpWecIds.add("1102000006");
		clpWecIds.add("1108000036");
		clpWecIds.add("1109000009");
		clpWecIds.add("1109000010");
		clpWecIds.add("1202000048");
		clpWecIds.add("1202000049");
		clpWecIds.add("1202000051");
		clpWecIds.add("1202000056");
		clpWecIds.add("1102000009");
		clpWecIds.add("1102000010");
		clpWecIds.add("1108000037");
		clpWecIds.add("1203000055");
		clpWecIds.add("1203000006");
		clpWecIds.add("1203000009");
		clpWecIds.add("1203000059");
		clpWecIds.add("1203000060");
		clpWecIds.add("1203000061");
		clpWecIds.add("1203000062");
		clpWecIds.add("1006000013");
		clpWecIds.add("1006000030");
		clpWecIds.add("1202000052");
		clpWecIds.add("1202000053");
		clpWecIds.add("1202000055");
		clpWecIds.add("1203000051");
		clpWecIds.add("1203000052");
		clpWecIds.add("1203000053");
		clpWecIds.add("1203000054");
		clpWecIds.add("1202000050");
		clpWecIds.add("1202000054");
		clpWecIds.add("1203000004");
		clpWecIds.add("1203000005");
		clpWecIds.add("1203000007");
		clpWecIds.add("1203000008");
		clpWecIds.add("1203000010");
		clpWecIds.add("1203000050");
		clpWecIds.add("1203000056");
		clpWecIds.add("1203000057");
		clpWecIds.add("1203000058");
		clpWecIds.add("1305000076");
		clpWecIds.add("1305000077");
		clpWecIds.add("1305000091");
		clpWecIds.add("1305000092");
		clpWecIds.add("0905000037");
		clpWecIds.add("0905000038");
		clpWecIds.add("0905000039");
		clpWecIds.add("1006000032");
		clpWecIds.add("1005000014");
		clpWecIds.add("1005000015");
		clpWecIds.add("1005000016");
		clpWecIds.add("1005000017");
		clpWecIds.add("1005000018");
		clpWecIds.add("1005000019");
		clpWecIds.add("1005000020");
		clpWecIds.add("1005000021");
		clpWecIds.add("1006000006");
		clpWecIds.add("1006000007");
		clpWecIds.add("1006000012");
		clpWecIds.add("1006000014");
		clpWecIds.add("1006000015");
		clpWecIds.add("1006000016");
		clpWecIds.add("1006000018");
		clpWecIds.add("1006000019");
		clpWecIds.add("1006000020");
		clpWecIds.add("1006000021");
		clpWecIds.add("1006000022");
		clpWecIds.add("1006000023");
		clpWecIds.add("1006000024");
		clpWecIds.add("1006000025");
		clpWecIds.add("1006000026");
		clpWecIds.add("1006000027");
		clpWecIds.add("1006000028");
		clpWecIds.add("1006000029");
		clpWecIds.add("1006000031");
		clpWecIds.add("1102000007");
		clpWecIds.add("1110000049");
		clpWecIds.add("1006000008");
		clpWecIds.add("1007000004");
		clpWecIds.add("1007000005");
		clpWecIds.add("1008000008");
		clpWecIds.add("1102000008");
		clpWecIds.add("1108000031");
		clpWecIds.add("1108000033");
		clpWecIds.add("1008000012");
		clpWecIds.add("1008000013");
		clpWecIds.add("1008000014");
		clpWecIds.add("1008000015");
		clpWecIds.add("1008000016");
		clpWecIds.add("1008000017");
		clpWecIds.add("1008000021");
		clpWecIds.add("1008000022");
		clpWecIds.add("1009000045");
		clpWecIds.add("1009000046");
		clpWecIds.add("1109000017");
		clpWecIds.add("1109000019");
		clpWecIds.add("1109000022");
		clpWecIds.add("1109000024");
		clpWecIds.add("1110000123");
		clpWecIds.add("1110000124");
		clpWecIds.add("1110000125");
		clpWecIds.add("1110000128");
		clpWecIds.add("1110000129");
		clpWecIds.add("1110000130");
		clpWecIds.add("1110000131");
		clpWecIds.add("1110000133");
		clpWecIds.add("1003000022");
		clpWecIds.add("1012000002");
		clpWecIds.add("1008000010");
		clpWecIds.add("1108000026");
		clpWecIds.add("1108000030");
		clpWecIds.add("1003000013");
		clpWecIds.add("1003000014");
		clpWecIds.add("1003000015");
		clpWecIds.add("1003000024");
		clpWecIds.add("1003000028");
		clpWecIds.add("1003000030");
		clpWecIds.add("1003000032");
		clpWecIds.add("1008000004");
		clpWecIds.add("1008000005");
		clpWecIds.add("1008000006");
		clpWecIds.add("1008000007");
		clpWecIds.add("1008000009");
		clpWecIds.add("1008000011");
		clpWecIds.add("1108000034");
		clpWecIds.add("1108000045");
		clpWecIds.add("1108000046");
		clpWecIds.add("1109000015");
		clpWecIds.add("1109000016");
		clpWecIds.add("1109000018");
		clpWecIds.add("1109000020");
		clpWecIds.add("1109000021");
		clpWecIds.add("1109000023");
		clpWecIds.add("1110000132");
		clpWecIds.add("1008000003");
		clpWecIds.add("1108000028");
		clpWecIds.add("1009000044");
		clpWecIds.add("1008000018");
		clpWecIds.add("1008000019");
		clpWecIds.add("1008000020");
		clpWecIds.add("1009000041");
		clpWecIds.add("1009000042");
		clpWecIds.add("1009000043");
		clpWecIds.add("1108000025");
		clpWecIds.add("1108000029");
		clpWecIds.add("1108000038");
		clpWecIds.add("1108000039");
		clpWecIds.add("1108000040");
		clpWecIds.add("1108000041");
		clpWecIds.add("1108000042");
		clpWecIds.add("1108000047");
		clpWecIds.add("1108000048");
		clpWecIds.add("1108000049");
		clpWecIds.add("1108000050");
		clpWecIds.add("1108000051");
		clpWecIds.add("1109000011");
		clpWecIds.add("1109000012");
		clpWecIds.add("1003000033");
		clpWecIds.add("1003000034");
		clpWecIds.add("1003000035");
		clpWecIds.add("1003000036");
		clpWecIds.add("1003000037");
		clpWecIds.add("1003000038");
		clpWecIds.add("1003000039");
		clpWecIds.add("1003000040");
		clpWecIds.add("1003000041");
		clpWecIds.add("1003000042");
		clpWecIds.add("1003000043");
		clpWecIds.add("1003000045");
		clpWecIds.add("1003000046");
		clpWecIds.add("1003000047");
		clpWecIds.add("1003000048");
		clpWecIds.add("1003000049");
		clpWecIds.add("1003000050");
		clpWecIds.add("1003000051");
		clpWecIds.add("1003000054");
		clpWecIds.add("1003000059");
		clpWecIds.add("1003000060");
		clpWecIds.add("1003000061");
		clpWecIds.add("1003000063");
		clpWecIds.add("1003000064");
		clpWecIds.add("1003000065");
		clpWecIds.add("1111000028");
		clpWecIds.add("1111000029");
		clpWecIds.add("1003000107");
		clpWecIds.add("1003000044");
		clpWecIds.add("1111000040");
		clpWecIds.add("1112000005");
		clpWecIds.add("1112000006");
		clpWecIds.add("1112000013");
		clpWecIds.add("1112000015");
		clpWecIds.add("1112000014");
		clpWecIds.add("1112000016");
		clpWecIds.add("1112000017");
		clpWecIds.add("1112000018");
		clpWecIds.add("1112000019");
		clpWecIds.add("1112000020");
		clpWecIds.add("1112000021");
		clpWecIds.add("1012000010");
		clpWecIds.add("1005000013");
		clpWecIds.add("1112000022");
		clpWecIds.add("1112000023");
		clpWecIds.add("1112000024");
		clpWecIds.add("1112000026");
		clpWecIds.add("1112000027");
		clpWecIds.add("1112000028");
		clpWecIds.add("1112000029");
		clpWecIds.add("1112000030");
		clpWecIds.add("1112000031");
		clpWecIds.add("1112000032");
		clpWecIds.add("1112000033");
		clpWecIds.add("1112000035");
		clpWecIds.add("1112000036");
		clpWecIds.add("1112000025");
		clpWecIds.add("1112000039");
		clpWecIds.add("1112000040");
		clpWecIds.add("1112000041");
		clpWecIds.add("1112000042");
		clpWecIds.add("1201000022");
		clpWecIds.add("1201000023");
		clpWecIds.add("1201000024");
		clpWecIds.add("1201000025");
		clpWecIds.add("1201000026");
		clpWecIds.add("1102000005");
		clpWecIds.add("1201000027");
		clpWecIds.add("1201000028");
		clpWecIds.add("1201000029");
		clpWecIds.add("1201000033");
		clpWecIds.add("1112000037");
		clpWecIds.add("1112000038");
		clpWecIds.add("1201000017");
		clpWecIds.add("1201000018");
		clpWecIds.add("1201000019");
		clpWecIds.add("1201000020");
		clpWecIds.add("1201000021");
		clpWecIds.add("1201000030");
		clpWecIds.add("1201000031");
		clpWecIds.add("1201000032");
		clpWecIds.add("1004000001");
		clpWecIds.add("1004000002");
		clpWecIds.add("1004000003");
		clpWecIds.add("1102000002");
		clpWecIds.add("1102000003");
		clpWecIds.add("1110000119");
		clpWecIds.add("1110000122");
		clpWecIds.add("1108000027");
		clpWecIds.add("1108000032");
		clpWecIds.add("1108000035");
		clpWecIds.add("1108000043");
		clpWecIds.add("1108000044");
		clpWecIds.add("1109000007");
		clpWecIds.add("1109000008");
		clpWecIds.add("1109000013");
		clpWecIds.add("1202000021");
		clpWecIds.add("1202000022");
		clpWecIds.add("1003000009");
		clpWecIds.add("1003000010");
		clpWecIds.add("1003000011");
		clpWecIds.add("1003000012");
		clpWecIds.add("1003000016");
		clpWecIds.add("1012000003");
		clpWecIds.add("1012000004");
		clpWecIds.add("1012000005");
		clpWecIds.add("1012000006");
		clpWecIds.add("1012000007");
		clpWecIds.add("1012000008");
		clpWecIds.add("1012000009");
		clpWecIds.add("1110000126");
		clpWecIds.add("1110000127");
		clpWecIds.add("1109000014");
		clpWecIds.add("1111000038");
		clpWecIds.add("1111000039");
		clpWecIds.add("1111000041");
		clpWecIds.add("1111000030");
		clpWecIds.add("1111000031");
		clpWecIds.add("1111000032");
		clpWecIds.add("1111000033");
		clpWecIds.add("1110000120");
		clpWecIds.add("1110000121");
		clpWecIds.add("1003000017");
		clpWecIds.add("1003000018");
		clpWecIds.add("1003000019");
		clpWecIds.add("1003000020");
		clpWecIds.add("1003000021");
		clpWecIds.add("1003000023");
		clpWecIds.add("1003000025");
		clpWecIds.add("1003000026");
		clpWecIds.add("1003000027");
		clpWecIds.add("1003000029");
		clpWecIds.add("1003000031");
		clpWecIds.add("1003000052");
		clpWecIds.add("1003000053");
		clpWecIds.add("1003000055");
		clpWecIds.add("1003000056");
		clpWecIds.add("1003000057");
		clpWecIds.add("1003000058");
		clpWecIds.add("1003000062");
		clpWecIds.add("1003000066");
		clpWecIds.add("1003000067");
		clpWecIds.add("1003000068");
		clpWecIds.add("1003000069");
		clpWecIds.add("1003000103");
		clpWecIds.add("1003000105");
		clpWecIds.add("1003000106");
		clpWecIds.add("1003000108");
		clpWecIds.add("1003000109");
		clpWecIds.add("1404000097");
		clpWecIds.add("1305000045");
		clpWecIds.add("1305000046");
		clpWecIds.add("1305000047");
		clpWecIds.add("1305000049");
		clpWecIds.add("1305000050");
		clpWecIds.add("1305000051");
		clpWecIds.add("1305000052");
		clpWecIds.add("1305000053");
		clpWecIds.add("1305000074");
		clpWecIds.add("1305000081");
		clpWecIds.add("1305000082");
		clpWecIds.add("1305000085");
		clpWecIds.add("1305000086");
		clpWecIds.add("1305000087");
		clpWecIds.add("1305000088");
		clpWecIds.add("1305000090");
		clpWecIds.add("1305000094");
		clpWecIds.add("1305000095");
		clpWecIds.add("1305000083");
		clpWecIds.add("1305000084");
		clpWecIds.add("1305000093");
		clpWecIds.add("1305000096");
		clpWecIds.add("1305000097");
		clpWecIds.add("1404000089");
		clpWecIds.add("1404000090");
		clpWecIds.add("1404000091");
		clpWecIds.add("1404000073");
		clpWecIds.add("1404000080");
		clpWecIds.add("1404000081");
		clpWecIds.add("1404000094");
		clpWecIds.add("1404000074");
		clpWecIds.add("1211000003");
		clpWecIds.add("1211000004");
		clpWecIds.add("1410000025");
		clpWecIds.add("1410000027");
		clpWecIds.add("1404000076");
		clpWecIds.add("1404000077");
		clpWecIds.add("1404000075");
		clpWecIds.add("1404000078");
		clpWecIds.add("1404000079");
		clpWecIds.add("1410000026");
		clpWecIds.add("1211000002");
		clpWecIds.add("1211000006");
		clpWecIds.add("1211000007");
		clpWecIds.add("1211000008");
		clpWecIds.add("1211000009");
		clpWecIds.add("1211000015");
		clpWecIds.add("1211000020");
		clpWecIds.add("1211000022");
		clpWecIds.add("1211000023");
		clpWecIds.add("1211000025");
		clpWecIds.add("1301000008");
		clpWecIds.add("1301000009");
		clpWecIds.add("1301000010");
		clpWecIds.add("1301000011");
		clpWecIds.add("1301000012");
		clpWecIds.add("1301000013");
		clpWecIds.add("1301000014");
		clpWecIds.add("1301000015");
		clpWecIds.add("1301000016");
		clpWecIds.add("1301000017");
		clpWecIds.add("1301000018");
		clpWecIds.add("1301000019");
		clpWecIds.add("1301000020");
		clpWecIds.add("1301000021");
		clpWecIds.add("1301000022");
		clpWecIds.add("1303000007");
		clpWecIds.add("1303000026");
		clpWecIds.add("1303000027");
		clpWecIds.add("1303000029");
		clpWecIds.add("1303000030");
		clpWecIds.add("1303000034");
		clpWecIds.add("1303000035");
		clpWecIds.add("1303000040");
		clpWecIds.add("1211000001");
		clpWecIds.add("1211000014");
		clpWecIds.add("1211000016");
		clpWecIds.add("1211000017");
		clpWecIds.add("1211000018");
		clpWecIds.add("1211000019");
		clpWecIds.add("1303000006");
		clpWecIds.add("1303000008");
		clpWecIds.add("1303000009");
		clpWecIds.add("1303000012");
		clpWecIds.add("1303000015");
		clpWecIds.add("1303000016");
		clpWecIds.add("1303000031");
		clpWecIds.add("1303000036");
		clpWecIds.add("1211000005");
		clpWecIds.add("1211000010");
		clpWecIds.add("1211000011");
		clpWecIds.add("1211000013");
		clpWecIds.add("1211000021");
		clpWecIds.add("1301000003");
		clpWecIds.add("1301000005");
		clpWecIds.add("1301000006");
		clpWecIds.add("1301000007");
		clpWecIds.add("1404000082");
		clpWecIds.add("1404000069");
		clpWecIds.add("1404000083");
		clpWecIds.add("1211000024");
		clpWecIds.add("1301000002");
		clpWecIds.add("1301000004");
		clpWecIds.add("1303000011");
		clpWecIds.add("1303000014");
		clpWecIds.add("1211000026");
		clpWecIds.add("1211000027");
		clpWecIds.add("1211000028");
		clpWecIds.add("1301000001");
		clpWecIds.add("1404000084");
		clpWecIds.add("1404000086");
		clpWecIds.add("1404000087");
		clpWecIds.add("1404000070");
		clpWecIds.add("1404000085");
		clpWecIds.add("1404000088");
		clpWecIds.add("1303000004");
		clpWecIds.add("1303000005");
		clpWecIds.add("1303000010");
		clpWecIds.add("1303000013");
		clpWecIds.add("1303000022");
		clpWecIds.add("1303000023");
		clpWecIds.add("1303000024");
		clpWecIds.add("1303000025");
		clpWecIds.add("1303000028");
		clpWecIds.add("1303000032");
		clpWecIds.add("1303000033");
		clpWecIds.add("1303000037");
		clpWecIds.add("1303000038");
		clpWecIds.add("1303000039");
		clpWecIds.add("1305000026");
		clpWecIds.add("1305000048");
		clpWecIds.add("1305000079");
		clpWecIds.add("1305000080");
		clpWecIds.add("1305000089");
		clpWecIds.add("1303000017");
		clpWecIds.add("1303000018");
		clpWecIds.add("1303000019");
		clpWecIds.add("1303000020");
		clpWecIds.add("1303000021");
		clpWecIds.add("1508000001");
		clpWecIds.add("1404000092");
		clpWecIds.add("1404000093");
		clpWecIds.add("1305000028");
		clpWecIds.add("1305000038");
		clpWecIds.add("1305000040");
		clpWecIds.add("1305000041");
		clpWecIds.add("1305000043");
		clpWecIds.add("1305000044");
		clpWecIds.add("1404000071");
		clpWecIds.add("1404000095");
		clpWecIds.add("1404000072");
		clpWecIds.add("1305000024");
		clpWecIds.add("1305000025");
		clpWecIds.add("1305000027");
		clpWecIds.add("1305000029");
		clpWecIds.add("1305000030");
		clpWecIds.add("1305000031");
		clpWecIds.add("1305000033");
		clpWecIds.add("1305000035");
		clpWecIds.add("1305000036");
		clpWecIds.add("1305000037");
		clpWecIds.add("1305000075");
		clpWecIds.add("1305000078");
		clpWecIds.add("1305000023");
		clpWecIds.add("1305000032");
		clpWecIds.add("1305000034");
		clpWecIds.add("1305000039");
		clpWecIds.add("1305000042");
		clpWecIds.add("1404000096");
		return clpWecIds;
	}
	
	public static Set<String> partialClpWecIds() {
		Set<String> clpWecIds = new HashSet<String>();
		
		clpWecIds.add("0905000035");
		clpWecIds.add("0905000036");
		clpWecIds.add("1102000004");
		clpWecIds.add("1102000006");
		
		return clpWecIds;
	}
}

class LevelDetection{
	private String employeeId;
	private Map<Integer, String> reportingHierarchy;
	private int noOfLevels;
	private boolean isDuplicate;
	
	public LevelDetection(String employeeId, Map<Integer, String> reportingHierarchy){
		int size = reportingHierarchy.size();
		if(size <= 2 || size >= 5) throw new IllegalArgumentException();
		this.employeeId = employeeId;
		this.reportingHierarchy = new TreeMap<Integer, String>(reportingHierarchy);
		this.noOfLevels = size;
		System.out.println(reportingHierarchy);
		this.isDuplicate = isDuplicate();
	}
	
	public int get(int level){
		if(!(level >= -1 && level < noOfLevels)) throw new IllegalArgumentException();
		if(isDuplicate){
			if(noOfLevels == 3 && level == -1) 	
				level = level + 2;
			else
				level = level + 1;
			
			if(!isReportingPresentAtHigherLevel(level)) return level;
			else 										return get(level);
		} else {
			if(noOfLevels == 3 && level == -1) 	return level + 2;
			else 								return level + 1;
		}
		
	}

	private boolean isReportingPresentAtHigherLevel(int level) {
		
		if(level == noOfLevels) return false;
		
		String employeeId = reportingHierarchy.get(level);
		for(int currentLevel : reportingHierarchy.keySet()){
			if(currentLevel > level && reportingHierarchy.get(currentLevel).equals(employeeId)) return true; 
		}
		return false;
	}

	private boolean isDuplicate() {
		Set<String> employeeIds = new LinkedHashSet<String>();
		for(int level : reportingHierarchy.keySet()){
			String reportingEmployeeId = reportingHierarchy.get(level);
			if(employeeIds.contains(reportingEmployeeId)) return true;
			else employeeIds.add(reportingEmployeeId);
		}
		return false;
	}
	
	public static void main(String[] args) {
		testClient();
	}
	
	private static void testClient() {
		Map<Integer, String> m = new HashMap<Integer, String>();
		
//		1
		m.put(1, "91009245");
		m.put(2, "91009355");
		m.put(3, "91009245");
		
//		2
		m.put(1, "9100924");
		m.put(2, "91009355");
		m.put(3, "91009245");
		
//		3
		m.put(0, "91009269");
		m.put(1, "91009256");
		m.put(2, "91009355");
		m.put(3, "91009245");
		
//		4
		m.put(0, "91009269");
		m.put(1, "91009245");
		m.put(2, "91009355");
		m.put(3, "91009245");
		
//		5
		m.put(0, "91009269");
		m.put(1, "91009269");
		m.put(2, "91009245");
		m.put(3, "9100925");
		
//		6
		m.put(0, "91009269");
		m.put(1, "91009269");
		m.put(2, "91009245");
		m.put(3, "91009245");
		
//		7
		m.put(0, "91009269");
		m.put(1, "91009269");
		m.put(2, "91009269");
		m.put(3, "91009269");
		
		LevelDetection detection = new LevelDetection("91014234", m);
		
		System.out.println("-1:" + detection.get(-1));
		System.out.println(" 0:" + detection.get(0));
		System.out.println(" 1:" + detection.get(1));
		System.out.println(" 2:" + detection.get(2));
	}
}