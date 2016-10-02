package com.enercon.admin.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.CallSchedulerForSendingMail;

public class IppGroupReport implements Job {
	private final static Logger logger = Logger.getLogger(IppGroupReport.class);
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {

		final String reportDate = DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy");
		new Thread(new Runnable() {

			public void run() {
				try {
					new CallSchedulerForSendingMail().sendIPPGroupMail(reportDate);
				} catch (Exception e) {
					logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				}
			}
		}).start();
	}
}
