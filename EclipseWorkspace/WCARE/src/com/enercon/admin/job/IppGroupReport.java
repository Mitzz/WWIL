package com.enercon.admin.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.CallSchedulerForSendingMail;

public class IppGroupReport implements Job {

	public void execute(JobExecutionContext jobContext) throws JobExecutionException {

		final String reportDate = DateUtility.getYesterdayDateInGivenStringFormat("dd-MMM-yyyy");
		new Thread(new Runnable() {

			public void run() {
				try {
					new CallSchedulerForSendingMail().sendIPPGroupMail(reportDate);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}
