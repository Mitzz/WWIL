package com.enercon.global.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.NumberUtility;
import com.enercon.model.DataVo;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.reports.pojo.GenerationReport;


public class CallSchedulerForSendingMail{
	private SendMail sm = new SendMail();
	private String blankSpace = "&nbsp;";
//	private final String[] receiverEmailIDsForGenerationReport = {"Generation.Report@windworldindia.com"};
	private static final String[] receiverEmailIDsForGenerationReport = {"mithul.bhansali@windworldindia.com"};
	
//	private static final String[] receiverEmailIDsForIppGroup= {"Ipp.Report@windworldindia.com"};
	private static final String[] receiverEmailIDsForIppGroup= {"mithul.bhansali@windworldindia.com"};

	private final String senderEmailID = "WindWorldCare@windworldindia.com";
	
	public String getPath() throws UnsupportedEncodingException {
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		//System.out.println("this.getClass().getClassLoader().getResource(\"/\").getPath() <-> " + fullPath);
		String pathArr[] = fullPath.split("/WEB-INF/classes/");
		
//		System.out.println(pathArr[0]);
		fullPath = pathArr[0];
		
		String reponsePath = "";
		// to read a file from webcontent
		reponsePath = new File(fullPath).getPath() + File.separatorChar + "spring\\bean-config.xml";
		return reponsePath;
	}
	
	public void sendIPPGroupMail(String reportDate){

		/*---------------------------------*/
		StringBuffer htmlMessage = new StringBuffer("");
//		String filePath = "";
//		
//		try {
//			filePath = getPath();
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
		//System.out.println("Spring BEan-config path:" + filePath);
		GenerationReport generationReport = null;
		
//		final AbstractApplicationContext ac = new FileSystemXmlApplicationContext(filePath);
		final ApplicationContext ac = new ClassPathXmlApplicationContext("com/enercon/spring/bean/bean-config.xml");
		
		htmlMessage.append("<table width='830' border=1 cellspacing=0 cellpadding=0 style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;>" +
				"	<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:18px;color:#000000';font-weight: bold;'>" + 
				"		<td width='830'align='center' colspan='16'>" + 
				"			<font class='sucessmsgtext'>" + "<b>Generation Report for " + reportDate +  "</b></font>" +
				"		</td>" + 
				"	</tr>" +
				/*"	<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'>" +
				"		<td width='830'align='center' colspan='16'>" +
				"			<font class='sucessmsgtext'><b>" + customerFullName + "</b></font>" +
				"		</td>" +
				"	</tr>" +*/
				/*"	<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
				"		<td colspan='8' align=center><b>Total Capacity:</b>&nbsp;<b>" + totalWECCapacity + " MW</b></td>" +
				"		<td colspan='8' align=center><b>Report Date:</b>&nbsp;<b>" + reportReadingDate + "</b></td> " +
				"	</tr> " +*/
				"	<tr style='font-family:arial;font-size:12px;color:#000000';'> " +
				"		<td colspan='3' align='center'>&nbsp;</td> " +
				"		<td colspan='8' align='center'><b>Daily</b></td> " +
				"		<td colspan='1' align='center' style='font-family:arial;font-size:11px;color:#000000';'><b>FY:2014-15</b></td> " +
				"		<td colspan='4' align='center' style='font-family:arial;font-size:11px;color:#000000';'><b>Cummulative FY:2015-16</b></td> " +
				"	</tr> " +
				"	<tr  bgcolor='#BBB87E' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'> " +
				"		<td colspan='1' align='center'><b>SN</b></td> " +
				"		<td colspan='1'><b>State Name</b></td> " +
				"		<td colspan='1' align='center'><b>Total WEC Commisioned</b></td> " +
				"		<td colspan='1' align='center'><b>Avg KWH</b></td> " +
				"		<td colspan='1' align='center'><b>Total KWH</b></td> " +
				"		<td colspan='1' align='center'><b> Revenue(Rs In Lacs) with LL @ 3%</b></td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue(Rs) Loss Due To FM</b></td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue(Rs) Loss Due To LR</b></td> " +
				"		<td colspan='1' align='center'><b>CF(%)</b></td> " +
				"		<td colspan='1' align='center'><b>MA(%)</b></td> " +
				"		<td colspan='1' align='center'><b>GA(%)</b></td> " +
				"		<td colspan='1' align='center'><b>MWH</b></td> " +
				"		<td colspan='1' align='center'><b>MWH</b></td> " +
				"		<td colspan='1' align='center'><b> Revenue(Rs In Lacs)</b></td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue Loss Due To FM(Rs In Lacs)</b></td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue Loss Due To LR(Rs In Lacs)</b></td> " +
				"	</tr> ") ;
		
		int overallWecCount = 0;
		long overallPreviousFiscalGeneration = 0;
		long overallCurrentFiscalGeneration = 0;
		
		for(int i = 8; i <= 28; i++ ){
			System.out.println("Ge " + i);
			generationReport = (GenerationReport) ac.getBean("generationReport" + i);
			generationReport.setReportDate(reportDate);
			try {
				generationReport.initialiseForIpp(reportDate);
				
				overallWecCount += generationReport.getWecIds().size();
//				overallCurrentFiscalGeneration += grandTotal.getGrandCumulativeGenerationTotalForCurrentFiscalYear();
				overallCurrentFiscalGeneration += generationReport.getCurrentFYTotal().generation();
//				overallPreviousFiscalGeneration += grandTotal.getGrandCumulativeGenerationTotalForPreviousFiscalYear();
				overallPreviousFiscalGeneration += generationReport.getPreviousFYTotal().generation();
//				overallPreviousFiscalGeneration += grandTotal.get
				
			} catch (ParseException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				htmlMessage.append(sendMailForIppGroup(new ArrayList<GenerationReport>(Arrays.asList(generationReport))));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Ge " + i + " e");
		}
		htmlMessage.append( 
				"	<tr bgcolor='#BBB87E' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'> " +
				"		<td colspan='2' align='center'>Total</td> " +
				"		<td colspan='1' align='center'>" + overallWecCount + "</td> " +
				"		<td colspan='5' align='center'>" + blankSpace + "</td> " +
				/*"		<td colspan='1' align='center'>" + NumberUtility.round(overallDailyRevenueGenerated, 2) + "</td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(overallDailyRevenueLossDueToFM, 2) + "</td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(overallDailyRevenueLossDueToLR, 2) + "</td> " +*/
				"		<td colspan='3' align='center'>" + blankSpace + "</td> " +
				"		<td colspan='1' align='center'>" + overallPreviousFiscalGeneration/1000 + "</td> " +
				"		<td colspan='1' align='center'>" + overallCurrentFiscalGeneration/1000 + "</td> " +
				"		<td colspan='4' align='center'>" + blankSpace + "</td> " +
				/*"		<td colspan='1' align='center'>" + NumberUtility.round(overallFiscalRevenueGenerated, 2) + "</td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(overallFiscalRevenueLossDueToFM, 2) + "</td> " +
				"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(overallFiscalRevenueLossDueToLR, 2) + "</td> " +*/
				"	</tr> " );
		htmlMessage.append("</table> ");
		/*htmlMessage.append("</body>\n");
		htmlMessage.append("</html>\n");*/
		System.out.println(htmlMessage);
		for (int i = 0; i < receiverEmailIDsForIppGroup.length; i++) {
			sm.sendMail(receiverEmailIDsForIppGroup[i],senderEmailID,"IPP Group Comprehensive Report for the Date - " + reportDate, new String(htmlMessage));
		}
		
		((ClassPathXmlApplicationContext)ac).close();
	}
	
	private StringBuffer sendMailForIppGroup(ArrayList<GenerationReport> generationReports) throws SQLException, Exception {
		
		String customerFullName = "";
		String reportReadingDate = "";
		double totalWECCapacity = 0;

		StringBuffer htmlMessage = new StringBuffer();

		for (GenerationReport generationReport : generationReports) {
			customerFullName = generationReport.getCustomerFullName(); 
			reportReadingDate = generationReport.getReportDate();
			totalWECCapacity = generationReport.getTotalWecCapacity()/1000;
			
			
			htmlMessage.delete(0, htmlMessage.length());
			
			htmlMessage.append( 
					/*"<table width='830' border=1 cellspacing=0 cellpadding=0 style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;>" +
					"	<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:18px;color:#000000';font-weight: bold;'>" + 
					"		<td width='830'align='center' colspan='16'>" + 
					"			<font class='sucessmsgtext'>" + "<b>Generation Report</b>" + "</font>" + 
					"		</td>" + 
					"	</tr>" +*/
					"	<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'>" +
					"		<td width='830'align='center' colspan='16'>" +
					"			<font class='sucessmsgtext'><b>" + customerFullName + "(" + totalWECCapacity + " MW)</b></font>" +
					"		</td>" +
					"	</tr>" /*+
					"	<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
					"		<td colspan='8' align=center><b>Total Capacity:</b>&nbsp;<b>" + totalWECCapacity + " MW</b></td>" +
					"		<td colspan='8' align=center><b>Report Date:</b>&nbsp;<b>" + reportReadingDate + "</b></td> " +
					"	</tr> "*/) ;
					/*"	<tr  style='font-family:arial;font-size:12px;color:#000000';'> " +
					"		<td colspan='3' align='center'>&nbsp;</td> " +
					"		<td colspan='8' align='center'><b>Daily</b></td> " +
					"		<td colspan='1' align='center' style='font-family:arial;font-size:11px;color:#000000';'><b>FY:2013-14</b></td> " +
					"		<td colspan='4' align='center' style='font-family:arial;font-size:11px;color:#000000';'><b>Cummulative FY:2014-15</b></td> " +
					"	</tr> " +
					"	<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'> " +
					"		<td colspan='1' align='center'><b>SN</b></td> " +
					"		<td colspan='1'><b>State Name</b></td> " +
					"		<td colspan='1' align='center'><b>Total WEC Commisioned</b></td> " +
					"		<td colspan='1' align='center'><b>Avg KWH</b></td> " +
					"		<td colspan='1' align='center'><b>Total KWH</b></td> " +
					"		<td colspan='1' align='center'><b> Revenue(Rs In Lacs) with LL @ 3%</b></td> " +
					"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue(Rs) Loss Due To FM</b></td> " +
					"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue(Rs) Loss Due To LR</b></td> " +
					"		<td colspan='1' align='center'><b>CF(%)</b></td> " +
					"		<td colspan='1' align='center'><b>MA(%)</b></td> " +
					"		<td colspan='1' align='center'><b>GA(%)</b></td> " +
					"		<td colspan='1' align='center'><b>MWH</b></td> " +
					"		<td colspan='1' align='center'><b>MWH</b></td> " +
					"		<td colspan='1' align='center'><b> Revenue(Rs In Lacs)</b></td> " +
					"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue Loss Due To FM(Rs In Lacs)</b></td> " +
					"		<td colspan='1' align='center' bgcolor='#DAA520'><b> Revenue Loss Due To LR(Rs In Lacs)</b></td> " +
					"	</tr> ") ;*/
			
			Map<String, IWecParameterVo> dailyDate = generationReport.getStateWiseReportDateWecParameterVo();
			Map<String, IWecParameterVo> currentFiscalYearData = generationReport.getStateWiseCurrentFiscalYearWecParameterVo();
			Map<String, IWecParameterVo> previousFiscalYearData = generationReport.getStateWisePreviousFiscalYearWecParameterVo();
			int srNo = 0;
			for (String stateName : generationReport.getStateWiseReportDateWecParameterVo().keySet()) {
				
				long size = dailyDate.get(stateName).size();
				long generation = dailyDate.get(stateName).generation();
				long averageGeneration = generation/size;
				
				double revenue = NumberUtility.round(dailyDate.get(stateName).revenue()/100000.0, 2);
				double fmLoss = NumberUtility.round(dailyDate.get(stateName).fmLoss(), 0);
				double lrLoss = NumberUtility.round(dailyDate.get(stateName).lrLoss(), 0);
				double cf = dailyDate.get(stateName).cf();
				double ma = dailyDate.get(stateName).ma();
				double ga = dailyDate.get(stateName).ga();
				
				long previousFYGeneration = previousFiscalYearData.get(stateName).generation()/1000;
				
				long currentFYGeneration = currentFiscalYearData.get(stateName).generation()/1000;
				double currentFYRevenue = NumberUtility.round(currentFiscalYearData.get(stateName).revenue()/100000.0, 2);
				double currentFYFmLoss = NumberUtility.round(currentFiscalYearData.get(stateName).fmLoss()/100000.0, 2);
				double currentFYLrLoss = NumberUtility.round(currentFiscalYearData.get(stateName).lrLoss()/100000.0, 2);
				
				htmlMessage.append( 
						"	<tr style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'> " +
						"		<td colspan='1' align='center'>" + (++srNo) + "</td> " +
						"		<td colspan='1'>" + stateName + "</td> " +
						"		<td colspan='1' align='center'>" + size + "</td> " +
						"		<td colspan='1' align='center'>" + averageGeneration + "</td> " +
						"		<td colspan='1' align='center'>" + generation + "</td> " +
						"		<td colspan='1' align='center'>" + revenue + "</td> " +
						"		<td colspan='1' align='center'>" + fmLoss + "</td> " +
						"		<td colspan='1' align='center'>" + lrLoss + "</td> " +
						"		<td colspan='1' align='center'>" + cf + "</td> " +
						"		<td colspan='1' align='center'>" + ma + "</td> " +
						"		<td colspan='1' align='center'>" + ga + "</td> " +
						"		<td colspan='1' align='center'>" + previousFYGeneration + "</td> " +
						"		<td colspan='1' align='center'>" + currentFYGeneration + "</td> " +
						"		<td colspan='1' align='center'>" + currentFYRevenue + "</td> " +
						"		<td colspan='1' align='center'>" + currentFYFmLoss + "</td> " +
						"		<td colspan='1' align='center'>" + currentFYLrLoss + "</td> " +
						"	</tr> ") ; 
			}
			if(dailyDate.size() > 1){
				htmlMessage.append( 
						"	<tr bgcolor='#BBB87E' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'> " +
						"		<td colspan='2' align='center'>Total</td> " +
						"		<td colspan='1' align='center'>" + generationReport.getWecIds().size() + "</td> " +
						"		<td colspan='2' align='center'>" + blankSpace + "</td> " +
						"		<td colspan='1' align='center'>" + NumberUtility.round(generationReport.getReportDateTotal().revenue()/100000.0, 2) + "</td> " +
						"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(generationReport.getReportDateTotal().fmLoss(), 0) + "</td> " +
						"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(generationReport.getReportDateTotal().lrLoss(), 0) + "</td> " +
						"		<td colspan='5' align='center'>" + blankSpace + "</td> " +
						"		<td colspan='1' align='center'>" + NumberUtility.round(generationReport.getCurrentFYTotal().revenue()/100000.0, 2) + "</td> " +
						"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(generationReport.getCurrentFYTotal().fmLoss()/100000.0, 2) + "</td> " +
						"		<td colspan='1' align='center' bgcolor='#DAA520'>" + NumberUtility.round(generationReport.getCurrentFYTotal().lrLoss()/100000.0, 2) + "</td> " +
						"	</tr> " );
			}
			
					
			//System.out.println("Less than 95");
			
			
			
			/*for (int i = 0; i < receiverEmailIDs.length; i++) {
				sm.sendMail(receiverEmailIDs[i],senderEmailID,"Generation Report Of " + generationReport.getCustomerShortName() + " for the Date - " + reportReadingDate, new String(htmlMessage));
			}*/
		}
//		long end = System.currentTimeMillis();
		return htmlMessage;
		
		//System.out.println("Time Taken : " + ((end - start) / 1000));
		
	}
	
	public void sendMail(String schedulerDate){

		String reportDate = schedulerDate;
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("com/enercon/spring/bean/bean-config.xml");
		
		for(int i = 1; i <= 7; i++ ){
			System.out.println("Ge " + i);
			GenerationReport gr = (GenerationReport) ac.getBean("generationReport" + i);
			try {
				gr.initialise(reportDate);
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				sendMail(new ArrayList<GenerationReport>(Arrays.asList(gr)));
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Ge " + i + " end");
		}
		
		((ClassPathXmlApplicationContext)ac).close();
	}
	
	

	public void sendMail(List<GenerationReport> generationReports) throws SQLException, Exception {
		StringBuffer htmlMessage = new StringBuffer();
		
		for (GenerationReport gr : generationReports) {
			System.out.println("Start::" + gr.getCustomerFullName());
			int srNo = 0;
			Set<String> wecIds = gr.getWecIds();
			
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
							" 				  font-weight: bold; " +
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
							"                 <td colspan='16'>" + gr.getCustomerFullName() + "</td> " +
							"             </tr> " +
							"             <tr class='common normal-data'> " +
							"                 <td colspan='8'>Total Capacity: " + gr.getTotalWecCapacity()/1000 + " MW</td> " +
							"                 <td colspan='8'>Report Date: " + gr.getReportDate() + "</td> " +
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
			Map<String, IWecParameterVo> dailyDate = gr.getStateWiseReportDateWecParameterVo();
			Map<String, IWecParameterVo> currentFiscalYearData = gr.getStateWiseCurrentFiscalYearWecParameterVo();
			Map<String, IWecParameterVo> previousFiscalYearData = gr.getStateWisePreviousFiscalYearWecParameterVo();
			
			long totalSize = wecIds.size();
			
			double totalRevenue = NumberUtility.round(gr.getReportDateTotal().revenue()/100000.0, 2);
			double totalFmLoss = NumberUtility.round(gr.getReportDateTotal().fmLoss(), 2);
			double totalLrLoss = NumberUtility.round(gr.getReportDateTotal().lrLoss(), 2);
			
			double fyTotalRevenue = NumberUtility.round(gr.getCurrentFYTotal().revenue()/100000.0, 2);
			double fyTotalFmLoss = NumberUtility.round(gr.getCurrentFYTotal().fmLoss()/100000.0, 2);
			double fyTotalLrLoss = NumberUtility.round(gr.getCurrentFYTotal().lrLoss()/100000.0, 2);
			
			srNo = 0;
			for(String stateName: dailyDate.keySet()){
				
				long size = dailyDate.get(stateName).size();
				long generation = dailyDate.get(stateName).generation();
				long averageGeneration = generation/size;
				double revenue = NumberUtility.round(dailyDate.get(stateName).revenue()/100000.0, 2);
				double fmLoss = dailyDate.get(stateName).fmLoss();
				double lrLoss = dailyDate.get(stateName).lrLoss();
				double cf = dailyDate.get(stateName).cf();
				double ma = dailyDate.get(stateName).ma();
				double ga = dailyDate.get(stateName).ga();
				
				long previousFYGeneration = previousFiscalYearData.get(stateName).generation()/1000;
				
				long currentFYGeneration = currentFiscalYearData.get(stateName).generation()/1000;
				double currentFYRevenue = NumberUtility.round(currentFiscalYearData.get(stateName).revenue()/100000.0, 2);
				double currentFYFmLoss = NumberUtility.round(currentFiscalYearData.get(stateName).fmLoss()/100000.0, 2);
				double currentFYLrLoss = NumberUtility.round(currentFiscalYearData.get(stateName).lrLoss()/100000.0, 2);
				
//				System.out.println(stateName + ":" + size + ":" + averageGeneration + ":" + generation + ":" + revenue + ":" + fmLoss + ":" + lrLoss + ":" + cf + ":" + ma + ":" + ga + ":" + previousFYGeneration + ":" + currentFYGeneration + ":" + currentFYRevenue + ":" + currentFYFmLoss + ":" + currentFYLrLoss);
				
				htmlMessage.append( 
								" 			<tr class='common normal-data'> " +
								"                 <td> " + (++srNo)+ "</td> " +
								"                 <td>" + stateName + "</td> " +
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
							"                 <td>" + totalRevenue + "</td> " +
							"                 <td class='loss-data'>" + totalFmLoss + "</td> " +
							"                 <td class='loss-data'>" + totalLrLoss + "</td> " +
							"                 <td colspan='5' >&nbsp;</td> " +
							"                 <td>" + fyTotalRevenue + "</td> " +
							"                 <td class='loss-data'>" + fyTotalFmLoss + "</td> " +
							"                 <td class='loss-data'>" + fyTotalLrLoss + "</td> " +
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
			
//			System.out.println(totalSize + ":" + totalRevenue + ":" + totalFmLoss + ":" + totalLrLoss + ":" + fyTotalRevenue + ":" + fyTotalFmLoss + ":" + fyTotalLrLoss);
			
//			System.out.println("-------------------------------------------------");
			
			
			
//			System.out.println("-------------------------------------------------");
			
			
			List<DataVo> dataVos = gr.getDataForMALessThan95();
			srNo = 0;
			for(DataVo dataVo: dataVos){
				IWecParameterVo wecParameterVo = dataVo.getWecParameterVo();
				
				String areaName = dataVo.getAreaName();
				String wecName = dataVo.getWecName();
				
				long generation = wecParameterVo.generation();
				long operatingHour = wecParameterVo.operatingHour()/60;
				double ma = wecParameterVo.ma();
				String remark = wecParameterVo.remark();
				
				htmlMessage.append(
								" <tr class='common normal-data'> " +
								"                 <td>" + (++srNo) + "</td> " +
								"                 <td>" + areaName + "</td> " +
								"                 <td colspan='2' >" + wecName + "</td> " +
								"                 <td>" + generation + "</td> " +
								"                 <td>" + operatingHour + "</td> " +
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
//			System.out.println("-------------------------------------------------");
			
//			System.out.println("-------------------------------------------------");
			dataVos = gr.getDataForMALessThan98();
			srNo = 0;
			for(DataVo dataVo : dataVos){
				IWecParameterVo wecParameterVo = dataVo.getWecParameterVo();
				String stateName = dataVo.getStateName();
				String siteName = dataVo.getSiteName();
				String wecName = dataVo.getWecName();
				
				long generation = wecParameterVo.generation();
				long operatingHour = wecParameterVo.operatingHour()/60;
				double ma = wecParameterVo.ma();
				
//				System.out.println(stateName + ":" + siteName + ":" + wecName + ":" + generation + ":" + operatingHour + ":" + ma);
				
				htmlMessage.append(
								" <tr class='common normal-data'> " +
								"                 <td>" + (++srNo) + "</td> " +
								"                 <td>" + stateName + "</td> " +
								"                 <td>" + siteName + "</td> " +
								"                 <td>" + wecName + "</td> " +
								"                 <td>" + generation + "</td> " +
								"                 <td>" + operatingHour + "</td> " +
								"                 <td>" + ma + "</td> " +
								"             </tr> ") ;
			}
			htmlMessage.append( "</table> </body> " + " </html> ");
			System.out.println(htmlMessage);
			System.out.println("End::" + gr.getCustomerFullName());
	
			for (int i = 0; i < receiverEmailIDsForGenerationReport.length; i++) {
				new SendMail().sendMail(receiverEmailIDsForGenerationReport[i],senderEmailID,"Generation Report Of " + gr.getCustomerShortName() + " for the Date - " + gr.getReportDate(), new String(htmlMessage));
			}
		}
//		long end = System.currentTimeMillis();
		
		//System.out.println("Time Taken : " + ((end - start) / 1000));
		
	}

}