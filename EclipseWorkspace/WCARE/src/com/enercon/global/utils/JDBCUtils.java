package com.enercon.global.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

import javax.naming.*;

import javax.transaction.SystemException;

import org.apache.log4j.Logger;

import com.enercon.global.controller.InitServlet;


public class JDBCUtils {
    /** Log4j
     */
    private static Logger logger = Logger.getLogger(JDBCUtils.class);

    /** Logical connection to an Oracle database.
     */
    private Connection conn = null;
    Context ctx = null;
    InitServlet servlet = new InitServlet();
    /** Creates a connection to the database (if not done so already)
     * and then returns a logical connection from a pool of connections.
     * 
     * @exception SystemException A JDBC SQLException error
     * @return Logical connection to the database
     */
    public Connection getConnection() throws Exception {    	
    	
		Hashtable ht = new Hashtable();
		
		String appserver = servlet.getDatabaseProperty("appserver");
		String jndiname = servlet.getDatabaseProperty("jndiname");
		ht.put(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
	    ht.put(Context.PROVIDER_URL,"t3://"+appserver+":7001");
	    
	    
    	
        try {

        	/* JNDI Lookup for database connection poool */
        	
        	
        	ctx = new InitialContext(ht);
            javax.sql.DataSource ds = (javax.sql.DataSource) ctx.lookup (jndiname);
            conn = ds.getConnection();
        	    
           	/* Changes End */
            
        	
        	
        	
        	
        	//conn = ConnectionPoolManager.getInstance().getConnection("enerconpool");


            //System.out.println("JDBCUtil: Got a connection object.");

            /* Create an instance of ConnCacheBean */
            //ConnCacheBean connCacheBean = ConnCacheBean.getInstance();

            /* Get OracleDataSource from the ConnCacheBean */
            //OracleDataSource ods = connCacheBean.getDataSource();

            /* If Connection Cache is not properly initialized, then throw an error */
            //if (ods == null) {
            //    throw new Exception("Connection Cache Not Properly Initialized");
            //}


            /* Get Connection from the Connection Cache. */
            // conn = ods.getConnection();
           

        } catch (Exception e) {
            e.printStackTrace();
            
            logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
        }
       
        return conn;
        
    }

    /** Closes the connection if it is open.
     * 
     * @exception SystemException A JDBC SQLException error
     * @throws NamingException 
     */
    public void closeConnection() throws SystemException, NamingException {
        if (conn != null) {
            try {
            	ctx.close();
            	ctx = null;
                conn.close();
                conn = null;
                //System.out.println("ABC" + conn.isClosed());
            } catch (SQLException e) {
            	logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
            }
        }
    }

    /** Logs a SQLException using Log4J.
     * 
     * @exception SystemException Always throws a SystemException after logging  
     *     the SQLException
     * @param e SQLException exception object
     */
    public static void logSQLError(SQLException e) throws SystemException {
        while (e != null) {
            logger.error("\nSQL Exception:");
            logger.error(e.getMessage());
            logger.error("ANSI-92 SQL State: " + e.getSQLState());
            logger.error("Vendor Error Code: " + e.getErrorCode());
            e = e.getNextException();
        }
        throw new SystemException();
    }


    public static int getSequenceNumber(Connection con, 
                                        String type) throws SQLException, 
                                                            Exception
    //public int getSequenceNumber(Connection con, String type) throws SQLException, Exception
    {
        //Connection con = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        int seqNo = 0;
        try {
            /* if(con == null) {
              con = getConnection();
          }*/
            String sql = "select " + type + ".nextval from dual ";

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                throw new RuntimeException("Invalid sequence schema " + type);
            }
            seqNo = rs.getInt(1);
            rs.close();
            stmt.close();
            //con.commit();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (stmt != null) {
                stmt.close();
            } /*
        if (con != null)
        {
          con.close();
        }*/
        }
        return seqNo;
    }


}//end of class
