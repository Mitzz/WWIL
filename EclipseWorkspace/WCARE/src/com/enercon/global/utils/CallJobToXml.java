package com.enercon.global.utils;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



import java.util.Date;

public class CallJobToXml implements Job {

	private static Logger logger = Logger.getLogger(CallJobToXml.class);
  public void execute(JobExecutionContext arg0) throws JobExecutionException{   
    
    System.out.println("Hello World Quartz Scheduler: " + new Date());
    
    CallSchedulerToXml cl=new CallSchedulerToXml();
	try
	{
		cl.CallTimer();
	}
	catch(Exception e)
	{
		logger.debug("Scheduler exception", e);
		e.printStackTrace();
	}
    
  }
}
