package com.enercon.global.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.HttpURLConnectionExample;

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
		String url = "http://localhost:7001/WCARE/missingScadaData.do";
		Map<String, String> requestParams = new LinkedHashMap<String, String>();
		requestParams.put("date", reportDate);
		
		HttpURLConnectionExample http = new HttpURLConnectionExample(url, requestParams);
		String data = null;
		try {
			data = http.sendRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		String[] receiverEmailIDs = {"missing.scadadata@windworldindia.com"};
		String[] receiverEmailIDs = {"mithul.bhansali@windworldindia.com"};
		String senderEmailID = "WindWorldCare@windworldindia.com";
		
		SendMail sm =new SendMail();
		
		String subject= "Missing Scada Data for " + reportDate + ":Total WECs Statewise (Report as per data availability in SCADA database @ 10:00 hours on " + DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy") + ")";
		
	    for (int i = 0; i < receiverEmailIDs.length; i++) {
	    	sm.sendMail(receiverEmailIDs[i],senderEmailID,subject,new String(data));
		}
	    logger.info("Missing Scada Data for date " + reportDate + " send successfully");
		return null;
	}
	
	public static void main(String[] args) {
		new CallJobToSentMissingScadaData().sendMissingScadaDataReport(DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy"));
	}
}
