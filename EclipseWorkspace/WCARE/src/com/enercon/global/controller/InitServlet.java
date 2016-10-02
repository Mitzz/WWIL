package com.enercon.global.controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;


public class InitServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    private static Properties props = new Properties();
    private static Properties dbprops = new Properties();
    private static Properties sapprops = new Properties();
    private static final Logger logger = Logger.getLogger(InitServlet.class);
    private static final Map propsMap = new HashMap();
    private ServletConfig config;
    public static String realMappingPath = "";
    ResourceBundle rb;

    public InitServlet() {
        super();
    }

    /**
     * 
     * @throws javax.servlet.ServletException
     * @param config
     */
    @Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            this.config = config;
            //configureLogging();
            //configureProperties();
            //configureDatabase();
            //configureSAP();
        } catch (Exception e) {
        	logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
            throw new ServletException(e.getMessage(), e);
        }
    }

    /**
     * 
     * @throws java.lang.Exception
     */
   /* private synchronized void configureLogging() throws Exception {
        try {
            //added for configuring log4j.
            String log4jConfigFile = getInitParameter("log4j-init-file");
            System.out.println(log4jConfigFile);
            if (log4jConfigFile != null) {
                
            	/*String realLog4jPath = 
                    config.getServletContext().getRealPath(log4jConfigFile);
                System.out.println(realLog4jPath);
                
            	rb = ResourceBundle.getBundle("Logger");
                PropertyConfigurator.configure(log4jConfigFile);
                logger.debug("log4j system initialised.");
                System.out.println("log4j initialized!!");
            } else {
                System.out.println("log4j did not initialized !!");
            }
        } catch (Exception ex) {
            logger.error("Error in initializing log4j");
            throw ex;
        }
    }
*/
    /**
     * 
     * @throws java.lang.Exception
     */
    private synchronized void configureProperties() throws Exception {
        try {
            //added for configuring system properties.
            String propertyFile = getInitParameter("system-properties-file");
            if (propertyFile != null) {
                String propertyFilePath =   config.getServletContext().getRealPath(propertyFile);
                FileInputStream propFile =  new FileInputStream(propertyFilePath);
                props.load(propFile);
              //  System.out.println("System properties initialised.");
                logger.debug("System properties initialised.");
            } else {
              //  System.out.println("System properties did not initialise.");
                logger.debug("System properties did not initialize !!");
            }

        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
            throw ex;
        }
    }

    /**
     * 
     * @throws java.lang.Exception
     */
    private synchronized void configureDatabase() throws Exception {
        try {
            //added for configuring database properties.
            String propertyFile = getInitParameter("database-config-file");
            if (propertyFile != null) {
                String propertyFilePath = 
                    config.getServletContext().getRealPath(propertyFile);
                FileInputStream propFile = 
                    new FileInputStream(propertyFilePath);
                dbprops.load(propFile);
               // System.out.println("Database properties initialised.");
                logger.debug("Database properties initialised.");
            } else {
               // System.out.println("Database properties did not initialise.");
                logger.debug("Database properties did not initialize !!");
            }

        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
            throw ex;
        }
    }

    /**
     * 
     * @throws java.lang.Exception
     */
    private synchronized void configureSAP() throws Exception {
        try {
            //added for configuring database properties.
            String propertyFile = getInitParameter("SAP-config-file");
            if (propertyFile != null) {
                String propertyFilePath = 
                    config.getServletContext().getRealPath(propertyFile);
                FileInputStream propFile = 
                    new FileInputStream(propertyFilePath);
                sapprops.load(propFile);
                //System.out.println("SAP properties initialised.");
                logger.debug("SAP properties initialised.");
            } else {
               // System.out.println("SAP properties did not initialise.");
                logger.debug("SAP properties did not initialize !!");
            }

        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
            throw ex;
        }
    }

    /**
     * 
     * @throws java.lang.Exception
     * @return 
     * @param key
     */
    public String getPropertyFileValue(String key) throws Exception {
        String value = new String();
        try {
            value = (String)props.get(key);
        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
            throw ex;
        }
        return value;
    }

    /**
     * 
     * @throws java.lang.Exceptionz
     * @return 
     * @param key
     */
    public String getDatabaseProperty(String key) {
        String value = new String();
        try {
        	rb = ResourceBundle.getBundle("DBConfig");
        	
            value = rb.getString(key);
        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
        }
        return value;
    }

    /**
     * 
     * @throws java.lang.Exception
     * @return 
     * @param key
     */
    public String getSAPProperty(String key) {
        String value = new String();
        try {
        	rb = ResourceBundle.getBundle("SAPConfig");
        	value = rb.getString(key);
        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
        }
        return value;
    }    
    
    
}
