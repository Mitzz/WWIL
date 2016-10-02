package com.enercon.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.enercon.global.controller.InitServlet;

public class WcareDataSourceConnector {

	private static Logger logger = Logger.getLogger(WcareDataSourceConnector.class);
	private javax.sql.DataSource datasource;
	private int connectionCount;
	
	{
		logger.debug("DataSource Initializing....");
		
		Hashtable<String, String> ht = new Hashtable<String, String>();
		Context context = null;
		
		InitServlet servlet = new InitServlet();
//		String appserver = servlet.getDatabaseProperty("appserver"); //Uncomment for Production
		String appserver = "localhost"; //Uncomment for Local
//		String jndiname = servlet.getDatabaseProperty("jndiname"); //Uncomment for Production
		String jndiname = "ecareDS"; //Uncomment for Local
		ht.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
	    ht.put(Context.PROVIDER_URL,"t3://"+appserver+":7001");

	    try {
			context = new InitialContext(ht);
			datasource = (javax.sql.DataSource) context.lookup (jndiname);
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private WcareDataSourceConnector(){
		logger.debug("DataSource Initialize Successfully");
	}

	private static class SingletonHelper{
        private static final WcareDataSourceConnector INSTANCE = new WcareDataSourceConnector();
    }
     
    public static WcareDataSourceConnector getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public Connection getConnection() throws SQLException {
    	//logger.debug("No of Connection: " + (++connectionCount));
        return datasource.getConnection();
    }
    
    public void shutdown(){
    	datasource = null;
    }
}
