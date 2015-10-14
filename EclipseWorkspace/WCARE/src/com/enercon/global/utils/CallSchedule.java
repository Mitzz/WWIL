package com.enercon.global.utils;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import com.enercon.admin.job.InsertPESData;
import com.enercon.admin.job.IppGroupReport;

public class CallSchedule {
	// private static Logger logger = Logger.getLogger(CallSchedule.class);
	public CallSchedule() throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		sched.start();

		JobDetail jd = new JobDetail("myjob", Scheduler.DEFAULT_GROUP,
				CallJob.class);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 14);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date startTime = cal.getTime();
		SimpleTrigger job_trig = new SimpleTrigger("mytrigger",
				Scheduler.DEFAULT_GROUP, startTime, null,
				SimpleTrigger.REPEAT_INDEFINITELY, 24L * 60L * 60L * 1000L);

		// SimpleTrigger st=new
		// SimpleTrigger("mytrigger",sched.DEFAULT_GROUP,new Date(),
		// null,SimpleTrigger.REPEAT_INDEFINITELY,0);
		// SimpleTrigger st = new SimpleTrigger("mytrigger",
		// sched.DEFAULT_GROUP,
		// startTime,null,SimpleTrigger.REPEAT_INDEFINITELY, 24L*60L*60L*1000L);
		// SimpleTrigger st = new SipleTrigger("mytrigger", sched.DEFAULT_GROUP,
		// startTime,null,SimpleTrigger.REPEAT_INDEFINITELY, 24L*60L*1000L);
		// SimpleTrigger st=new
		// SimpleTrigger("mytrigger",sched.DEFAULT_GROUP,startTime,
		// null,SimpleTrigger.REPEAT_INDEFINITELY,24L*60L*60L*1000L);

		sched.scheduleJob(jd, job_trig);

		/**************** Call Proc to Push Scada Data To ECARE *************************/

		JobDetail scadaECAREJobDetail = new JobDetail("scadaEcareJob",
				Scheduler.DEFAULT_GROUP, CallJobToPushScadaData.class);
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		startTime = cal.getTime();
		SimpleTrigger scadaJobTriger = new SimpleTrigger("scadaEcareJobTriger",
				Scheduler.DEFAULT_GROUP, startTime, null,
				SimpleTrigger.REPEAT_INDEFINITELY, 24L * 60L * 60L * 1000L);

		sched.scheduleJob(scadaECAREJobDetail, scadaJobTriger);

		/**************** To Sent Mail on Missing Scada Data In ECARE *************************/

		JobDetail missingScadaDataJobDetail = new JobDetail(
				"missingScadaDataInEcareJob", Scheduler.DEFAULT_GROUP,
				CallJobToSentMissingScadaData.class);
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		startTime = cal.getTime();
		SimpleTrigger missingScadaDataJobTrigger = new SimpleTrigger(
				"missingScadaDataInEcareTrigger", Scheduler.DEFAULT_GROUP,
				startTime, null, SimpleTrigger.REPEAT_INDEFINITELY,
				24L * 60L * 60L * 1000L);

		sched.scheduleJob(missingScadaDataJobDetail, missingScadaDataJobTrigger);

		/**************** To Push Scada PES Data In ECARE *************************/

		JobDetail insertPESDataJobDetail = new JobDetail(
				"insertPESDataInEcareJob", Scheduler.DEFAULT_GROUP,
				InsertPESData.class);
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		startTime = cal.getTime();
		SimpleTrigger insertPESDataJobTrigger = new SimpleTrigger(
				"insertPESDataInEcareTrigger", Scheduler.DEFAULT_GROUP,
				startTime, null, SimpleTrigger.REPEAT_INDEFINITELY,
				24L * 60L * 60L * 1000L);

		sched.scheduleJob(insertPESDataJobDetail, insertPESDataJobTrigger);

		/**************** To Send Ipp Group Report Mail *************************/

		JobDetail sendIppGroupMailJobDetail = new JobDetail(
				"sendIppGroupReportJob", Scheduler.DEFAULT_GROUP,
				IppGroupReport.class);
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MINUTE, 45);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		startTime = cal.getTime();
		SimpleTrigger sendIppGroupMailJobTrigger = new SimpleTrigger(
				"sendIppGroupMailTrigger", Scheduler.DEFAULT_GROUP, startTime,
				null, SimpleTrigger.REPEAT_INDEFINITELY,
				24L * 60L * 60L * 1000L);

		sched.scheduleJob(sendIppGroupMailJobDetail, sendIppGroupMailJobTrigger);

		/******************************************************************************/

		JobDetail jd1 = new JobDetail("myjobnew", Scheduler.DEFAULT_GROUP,
				CallJobTo.class);
		cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 2);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		startTime = cal.getTime();
		SimpleTrigger job_trig1 = new SimpleTrigger("mytriggernew",
				Scheduler.DEFAULT_GROUP, startTime, null,
				SimpleTrigger.REPEAT_INDEFINITELY, 1L * 60L * 60L * 1000L);

		sched.scheduleJob(jd1, job_trig1);

		/*
		 * JobDetail jd2=new
		 * JobDetail("myjobnew1",Scheduler.DEFAULT_GROUP,CallJobToXml.class);
		 * cal = Calendar.getInstance(); cal.set(Calendar.HOUR_OF_DAY,14);
		 * cal.set(Calendar.MINUTE,41); cal.set(Calendar.SECOND, 0);
		 * cal.set(Calendar.MILLISECOND, 0);
		 * 
		 * startTime = cal.getTime(); System.out.print(startTime); SimpleTrigger
		 * job_trig2 = new SimpleTrigger("mytriggernew1",
		 * Scheduler.DEFAULT_GROUP,
		 * startTime,null,SimpleTrigger.REPEAT_INDEFINITELY, 2L*60L*60L*1000L);
		 * 
		 * sched.scheduleJob(jd2, job_trig2);
		 */

	}

	public static void main(String args[]) {
		try {
			new CallSchedule();
		} catch (Exception e) {
		}
	}
}