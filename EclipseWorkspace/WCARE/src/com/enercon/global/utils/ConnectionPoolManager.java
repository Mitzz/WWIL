package com.enercon.global.utils;

import com.enercon.global.controller.InitServlet;

import java.sql.Connection;

import java.util.Hashtable;

import javax.transaction.SystemException;

import oracle.jdbc.pool.OracleConnectionCacheImpl;
import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.apache.log4j.Logger;


public class ConnectionPoolManager {

    /** Instance of this class.
     */
    private static ConnectionPoolManager thisInstance = null;

    /** Collection of Oracle Connection Caches.
     */
    private static Hashtable connectionPools = new Hashtable();

    /** Log4J
     */
    private static Logger log = Logger.getLogger(ConnectionPoolManager.class);

    /** Singleton method that ensures that an instance of
     * this class can only be created privately.
     */
    private ConnectionPoolManager() {
    }

    /** Singleton method that ensures that an instance of
     * this class is created only once.
     * 
     * @return A reference to the ConnectionPoolManager
     */
    public static ConnectionPoolManager getInstance() {
        if (thisInstance == null) {
            thisInstance = new ConnectionPoolManager();
        }
        return thisInstance;
    }

    /** Returns a logical connection to the Oracle database.
     * 
     * @param connectionPoolId The id of the pool, pools are configured in the
     *      egfs.properties
     * @exception SystemException If a java.sql.SQLException occurs while 
     *     getting a connection to the database
     * @return A logical connection to the database
     */
    public Connection getConnection(String connectionPoolId) throws SystemException {


        OracleConnectionCacheImpl connectionPool = 
            (OracleConnectionCacheImpl)connectionPools.get(connectionPoolId);
        if (connectionPool == null) {
            connectionPool = initializeConnectionPool(connectionPoolId);
            connectionPools.put(connectionPoolId, connectionPool);
        }
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
        } catch (java.sql.SQLException ex) {
            throw new SystemException();
        }
        return connection;
    }

    /** Creates an Oracle Connection Cache and adds a connection pool to it.
     * Logical connections can then be obtained from the pool.
     * 
     * @param connectionPoolId The id of the pool, pools are configured in the
     *      egfs.properties
     * @exception SystemException If a java.sql.SQLException occurs while 
     *     getting a connection to the database
     * @return Connection Cache of Pooled Connections
     */
    private OracleConnectionCacheImpl initializeConnectionPool(String connectionPoolId) throws SystemException {
        OracleConnectionPoolDataSource connectionData = null;
        OracleConnectionCacheImpl pool = null;
        try {
            connectionData = new OracleConnectionPoolDataSource();

            InitServlet servlet = new InitServlet();
            connectionData.setDriverType(servlet.getDatabaseProperty("drivertype"));
            connectionData.setNetworkProtocol(servlet.getDatabaseProperty("networkprotocol"));
            connectionData.setServerName(servlet.getDatabaseProperty("servername"));
            connectionData.setDatabaseName(servlet.getDatabaseProperty("databasename"));
            connectionData.setPortNumber(new Integer(servlet.getDatabaseProperty("portnumber")).intValue());
            connectionData.setUser(servlet.getDatabaseProperty("username"));
            connectionData.setPassword(servlet.getDatabaseProperty("password"));

            pool = new OracleConnectionCacheImpl(connectionData);
            pool.setMaxLimit(new Integer(servlet.getDatabaseProperty("maxlimit")).intValue());
            pool.setMinLimit(new Integer(servlet.getDatabaseProperty("minlimit")).intValue());
            pool.setCacheScheme(OracleConnectionCacheImpl.FIXED_WAIT_SCHEME);

            if (servlet != null)
                servlet = null;

        } catch (java.sql.SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex);
            log.debug("SQL Error while initializing connection pool : \n" + 
                      ex.toString());
            throw new SystemException();
        }
        return pool;
    }
}
