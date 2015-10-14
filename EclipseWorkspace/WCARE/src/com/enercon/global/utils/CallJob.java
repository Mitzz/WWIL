package com.enercon.global.utils;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;




import java.util.Date;

public class CallJob implements Job {

	private static Logger logger = Logger.getLogger(CallJob.class);
  public void execute(JobExecutionContext arg0) throws JobExecutionException{   
    
    System.out.println("Generation Report : " + new Date());
    
    CallScheduler cl=new CallScheduler();
	try
	{
		cl.CallTimer();
	}
	catch(Exception e)
	{
		logger.debug("Scheduler exception", e);
	}
	  
	  /*new Thread(new Runnable() {
			
			public void run() {
				try {
					new CallSchedulerForSendingMail().sendIPPGroupMail("24/07/2014");
//					new CallSchedulerForSendingMail().sendMail("24/07/2014"); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();*/
    
  }
}
