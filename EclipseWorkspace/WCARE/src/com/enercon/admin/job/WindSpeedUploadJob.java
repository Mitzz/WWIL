package com.enercon.admin.job;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.enercon.global.utility.DateUtility;
import com.enercon.scada.WindSpeedUpload;

public class WindSpeedUploadJob implements Job {
	
	private static Logger logger = Logger.getLogger(WindSpeedUploadJob.class);

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		uploadWindspeed();
	}

	private void uploadWindspeed() {
		WindSpeedUpload ws = new WindSpeedUpload();
		String date = DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy");
		ws.setDate(date);
		try {
			logger.warn(date + " : " + "start");
			ws.uploadWindspeed();
			logger.warn(date + " : " + "end");
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}
	
	public static void main(String[] args) {
		WindSpeedUploadJob job = new WindSpeedUploadJob();
		try {
			job.execute(null);
		} catch (JobExecutionException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

}
