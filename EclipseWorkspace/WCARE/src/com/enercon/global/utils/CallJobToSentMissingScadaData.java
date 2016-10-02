package com.enercon.global.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.HttpURLConnectionExample;
import com.enercon.model.graph.Graph;

public class CallJobToSentMissingScadaData implements Job {

	private static Logger logger = Logger.getLogger(CallJobToSentMissingScadaData.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		missingScadaData();
	}

	private void missingScadaData() {
		String reportDate = DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy");
		sendMissingScadaDataReport(reportDate);
	}
	
	public String sendMissingScadaDataReport(String reportDate){
		logger.info("Missing Scada Data for date " + reportDate);
		String url = "http://localhost:7001/WCARE/missingScadaData";
		Map<String, String> requestParams = new LinkedHashMap<String, String>();
		requestParams.put("date", reportDate);
		
		HttpURLConnectionExample http = new HttpURLConnectionExample(url, requestParams);
		String data = null;
		try {
//			Graph G = Graph.getInstance().initialize();
			logger.debug("Sending Request.");
			data = http.sendRequest();
//			String[] receiverEmailIDs = {"mithul.bhansali@windworldindia.com"};
			String[] receiverEmailIDs = {"missing.scadadata@windworldindia.com"};
			String senderEmailID = "WindWorldCare@windworldindia.com";
			logger.debug("Data Received.");
			SendMail sm =new SendMail();
			reportDate = DateUtility.convertDateFormats(reportDate, "dd-MMM-yyyy", "dd/MM/yyyy");
			String subject= "Missing Scada Data for " + reportDate + ":Total WECs Statewise (Report as per data availability in SCADA database @ 10:00 hours on " + DateUtility.getTodaysDateInGivenFormat("dd/MM/yyyy") + ")";
			
		    for (int i = 0; i < receiverEmailIDs.length; i++) {
		    	sm.sendMail(receiverEmailIDs[i],senderEmailID,subject,data);
			}
		    logger.info("Missing Scada Data for date " + reportDate + " send successfully");
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		new CallJobToSentMissingScadaData().sendMissingScadaDataReport(DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy"));
	}
}
