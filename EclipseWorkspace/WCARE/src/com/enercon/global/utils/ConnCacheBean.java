package com.enercon.global.utils;

import com.enercon.global.controller.InitServlet;

import java.sql.SQLException;

import java.util.Properties;

import oracle.jdbc.pool.OracleConnectionCacheManager;
import oracle.jdbc.pool.OracleDataSource;


/*
 * Author : Enercon
 * Application : Intranet Portal
 * Created Date: 27th April 2007
 */

/* Oracle JDBC Classes */


/* Java IO Classe */

/* Java Sql Classe */

/* Java Utility Classes */

public class ConnCacheBean {

    /* The name used to identify the cache uniquely */
    private static final String CACHE_NAME = "ConCacheEnercon";

    /* Maximum number of connection instaces */
    private static int MAX_LIMIT = 15;

    /* Variable pointing to this instance */
    private static ConnCacheBean thisInstance = null;

    /* Connection cache manager */
    private OracleConnectionCacheManager connMgr = null;

    /* Data Source Variable */
    private OracleDataSource ods = null;


    /**
     * Private Constructor : This approach makes it easy to implement this class
     * as a Singleton Class. This method initializes Cache if not already
     * initialized.
     *
     * @throws Exception
     */
    private ConnCacheBean() throws Exception {
        if (ods == null) {
            initializeConnectionCache();
        }
    }


    /**
     * This method returns a single instance of this bean.
     *
     * @return - ConnCacheBean Instance
     *
     * @throws Exception - Exception while creating the instance
     */
    public static ConnCacheBean getInstance() throws Exception {
        if (thisInstance == null) {
            thisInstance = new ConnCacheBean();
        }
        return thisInstance;
    }

    /**
     * This method returns active size of the Cache.
     *
     * @return int - Number of active conncetions
     *
     * @throws Exception - Any exception while getting the active size
     */
    public int getActiveSize() throws Exception {
        try {
            return connMgr.getNumberOfActiveConnections(CACHE_NAME);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new Exception("SQL Error while getting the no of active " + 
                                " connections");
        }
    }


    /**
     * This method returns the number of connections available in the cache.
     * @return int - Available connections in the cache
     * @throws Exception - Exception while getting the available connections
     */
    public int getAvailableConnections() throws Exception {
        return connMgr.getNumberOfAvailableConnections(CACHE_NAME);
    }

    /**
     * This method returns connection cache size.
     *
     * @return int - Cache size
     *
     * @throws Exception - Exception while getting the cache size
     */
    public int getCacheSize() throws Exception {
        return connMgr.getNumberOfActiveConnections(CACHE_NAME) + 
            connMgr.getNumberOfAvailableConnections(CACHE_NAME);
    }


    /**
     * This method returns the instance of OracleDataSource
     * @return OracleDataSource instance
     */
    public OracleDataSource getDataSource() {
        return ods;
    }

    /**
     * This method returns the MaxLimit of the connection cache
     * @return MAX_LIMIT - Cache max limit
     */
    public int getCacheMaxLimit() {
        return MAX_LIMIT;
    }

    /**
     * This method reintializes the cache with the new properites
     * 
     * @param properties - Properties object containing connecion cache properties
     * @throws SQLException - SQLException while setting the properties
     */
    public void setCacheProperties(Properties properties) throws SQLException {
        connMgr.reinitializeCache(CACHE_NAME, properties);

        /* Get the MaxLimit from the properties object */
        String maxLimit = properties.getProperty("MaxLimit");
        /* Set the maximum cache limit, used while closing the connection */
        if (maxLimit != null && maxLimit.trim().length() > 0) {
            MAX_LIMIT = new Integer(maxLimit).intValue();
        }

    }


    /**
     * This method reintializes the cache with the new properites
     *
     * @return Properties - Connection cache properties
     * @throws SQLException - SQLException
     */
    public Properties getCacheProperties() throws SQLException {
        return ods.getConnectionCacheProperties();
    }

    /**
     * This method closes the connection cache. This is called to close the 
     * connection cache when the application closes.
     *
     * @throws SQLException - SQLException
     */
    public void closeConnCache() throws SQLException {
        if (ods != null) {
            ods.close();
        }
    }

    /**
     * This Method initializes the variable 'ods' with value of valid Connection
     * Cache Data Source.
     *
     * @throws Exception 
     */
    private void initializeConnectionCacheDataSrc() throws Exception {
        try {

            /* Initialize the Datasource */
            ods = new OracleDataSource();

            /* Configure the Datasource with proper values of
         * Host Name, Sid, Port, Driver type, User Name and Password
         */
            this.configDSConnection();

            /* Enable cahcing */
            ods.setConnectionCachingEnabled(true);

            /* Set the cache name */
            ods.setConnectionCacheName(CACHE_NAME);

        } catch (SQLException sqlEx) { /* Catch SQL Errors */
            sqlEx.printStackTrace();
            throw new Exception("SQL Errors = " + sqlEx.toString());
        } catch (Exception ex) { /* Catch Generic Errors */
            ex.printStackTrace();
            throw new Exception("Generic Errors = " + ex.toString());
        }
    }

    /**
     * This Method initializes Connection Cache by associating a data soruce 
     * with the cache. Also the properties of the cache are set while creating
     * the connection cache.
     * 
     *
     * @throws Exception
     */
    private void initializeConnectionCache() throws Exception {
        if (ods == null) {
            try {
                this.initializeConnectionCacheDataSrc();

                /* Initialize the Connection Cache */
                connMgr = 
                        OracleConnectionCacheManager.getConnectionCacheManagerInstance();


                /* This object holds the properties of the cache and is passed to the
            * ConnectionCacheManager while creating the cache. Based on these
            * properties the connection cache manager created the connection
            * cache.
            */
                Properties properties = new Properties();

                /* Set Min Limit for the Cache.
            * This sets the minimum number of PooledConnections that the cache
            * maintains. This guarantees that the cache will not shrink below
            * this minimum limit.
            */
                properties.setProperty("MinLimit", "1");

                /* Set Max Limit for the Cache.
            * This sets the maximum number of PooledConnections the cache
            * can hold. There is no default MaxLimit assumed meaning connections
            * in the cache could reach as many as the database allows.
            */
                properties.setProperty("MaxLimit", "500");

                /* Set the Initial Limit.
            * This sets the size of the connection cache when the cache is
            * initially created or reinitialized. When this property is set to
            * a value greater than 0, that many connections are pre-created and
            * are ready for use.
            */
                properties.setProperty("InitialLimit", "10");

                /* Create the cache by passing the cache name, data source and the
           * cache properties
           */
                if (connMgr.existsCache(CACHE_NAME))
                {
                	connMgr.removeCache(CACHE_NAME, 0);
                }
                connMgr.createCache(CACHE_NAME, ods, properties);
            } catch (java.sql.SQLException ex) { /* Catch SQL Errors */
                throw new Exception("SQL Error while Instantiating Connection Cache : \n" + 
                                    ex.toString());
            } catch (java.lang.Exception ex) { /* Catch other generic errors */
                throw new Exception("Exception : \n" + ex.toString());
            }
        }
    }


    /**
     * This method configures the Datasource with appropriate values of Host
     * Name, User Name, Password etc. Note that the configuration parameters are
     * stored in Connection.properties file.
     *
     * @param ods - OracleDataSource
     */
    private void configDSConnection() throws Exception {
        try {
            /* Load the properties file to get the connection information
         * from the Connection.properties file
         */


            InitServlet servlet = new InitServlet();


            /* Set Host name */
            ods.setServerName(servlet.getDatabaseProperty("servername"));

            /* Set Database SID */
            ods.setServiceName(servlet.getDatabaseProperty("servicename"));

            /* Set Port number */
            ods.setPortNumber(new Integer(servlet.getDatabaseProperty("portnumber")).intValue());

            /* Set Driver type  */
            ods.setDriverType(servlet.getDatabaseProperty("drivertype"));

            /* Set User name */
            ods.setUser(servlet.getDatabaseProperty("username"));

            /* Set Password */
            ods.setPassword(servlet.getDatabaseProperty("password"));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
