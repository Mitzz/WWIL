package com.enercon.connection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;

public class ApplicationShutdown implements ServletContextListener {
	
	private final static Logger logger = Logger.getLogger(ApplicationShutdown.class);

    public ApplicationShutdown() {
    }

	public void contextInitialized(ServletContextEvent arg0) {
		logger.warn("Context Initialized");
    }

	public void contextDestroyed(ServletContextEvent arg0) {
		
		WcareConnector.wcareConnector.shutDown();
    	DaoUtility.closeWriter();
    	logger.warn("Context Destroyed");
    }
	
}
