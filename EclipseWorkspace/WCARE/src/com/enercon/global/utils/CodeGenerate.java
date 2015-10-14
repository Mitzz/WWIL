package com.enercon.global.utils;

/*  Author : Sachin Sisodiya
 *  Company: Enercon India Limited
 *  Date : 10 Aug 2007
 * */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.transaction.SystemException;

import com.enercon.global.utils.GlobalSQLC;

public class CodeGenerate {
    /** Log4j
     */
    //private static Logger log = Logger.getLogger(CodeGenerate.class);

    /** Logical connection to an Oracle database.
     */
    //private Connection conn = null;
    /** Creates a connection to the database (if not done so already)
     * and then returns a logical connection from a pool of connections.
     * 
     * @exception SystemException A JDBC SQLException error
     * @return Logical connection to the database
     */
    //public Connection getConnection() throws Exception {
     //   try {

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
            //conn = ods.getConnection();


        //} catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("System Exception (JDBC Error) : " + 
                               //e.getMessage());
            //log.error("System Exception (JDBC Error) : " + e.getMessage(), e);
        //}
        //return conn;
    //}

    /** Closes the connection if it is open.
     * 
     * @exception SystemException A JDBC SQLException error
     */
    /*public void closeConnection() throws SystemException {
        if (conn != null) {
            try {
                conn.close();
                //System.out.println("JDBCUtil: Closing the connection object.");
            } catch (SQLException e) {
                logSQLError(e);
            }
        }
    }*/

    /** Logs a SQLException using Log4J.
     * 
     * @exception SystemException Always throws a SystemException after logging  
     *     the SQLException
     * @param e SQLException exception object
     */
    /*public static void logSQLError(SQLException e) throws SystemException {
        while (e != null) {
            log.error("\nSQL Exception:");
            log.error(e.getMessage());
            log.error("ANSI-92 SQL State: " + e.getSQLState());
            log.error("Vendor Error Code: " + e.getErrorCode());
            e = e.getNextException();
        }
        throw new SystemException();
    }*/


    public static String NewCodeGenerate(String TableName) throws SQLException, 
                                                            Exception
    {
        //Connection con = null;
    	JDBCUtils conmanager = new JDBCUtils();
    	Connection conn = conmanager.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        String seqNo = "";
        try 
        {
            /* if(con == null) {
              con = getConnection();
          }*/
        	SimpleDateFormat getFormatDate = new SimpleDateFormat("yyMM");
        	Date date = new Date();
        	String sCode = getFormatDate.format(date);
        	//String sCode = "0609";
        	int newRec = 0;
        	//ps.clearParameters();
        	//GlobalSQLC CodeSQl = new GlobalSQLC();
            String sql = GlobalSQLC.GET_CODE_GENERATE;
            ps = conn.prepareStatement(sql);
            ps.setObject(1, TableName);
            ps.setObject(2, sCode);
        
            rs = ps.executeQuery();
            
            while (rs.next()) 
            {
            		//System.out.println("N_no : " + rs.getInt("N_NO")); 
                    newRec = rs.getInt("N_NO") + 1;
                    sql = GlobalSQLC.UPDATE_CODE_GENERATE;
                    ps1 = conn.prepareStatement(sql);
                    ps1.setObject(1, String.valueOf(newRec));
                    ps1.setObject(2, TableName);
                    ps1.setObject(3, sCode);                    
                    int iInserteddRows = ps1.executeUpdate();
                    //conn.commit();
                    if (iInserteddRows != 1)
                        throw new Exception("DB_UPDATE_ERROR", null);
                    ps1.close();
            }
            rs.close();
            ps.close();
            if (newRec == 0)
            {
        		newRec = 1;
        		sql = GlobalSQLC.INSERT_CODE_GENERATE;
                ps = conn.prepareStatement(sql);
                ps.setObject(1, TableName);
                ps.setObject(2, sCode);
                ps.setObject(3, String.valueOf(newRec));                    
                int iInserteddRows = ps.executeUpdate();
                //conn.commit();
                if (iInserteddRows != 1)
                    throw new Exception("DB_INSERT_ERROR", null);
                ps.close();
            }
            
            String s = Integer.toString(newRec);
            if ( s.length() < 6 )
               { // pad on left with zeros
               s = "000000".substring( 0, 6 - s.length()) + s;
               } 
            seqNo = sCode + s;
            //System.out.println("New Code Generated for " + TableName + " Code : " + sCode + ". Seqno : " + seqNo);
            //con.commit();
        } 
        catch (SQLException sqlExp) 
        {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        }
        finally 
        {
	        try {
	        	if (ps != null) {
	                ps.close();
	            }
	            if (ps1 != null) {
	                ps1.close();
	            }
	            if (rs != null) {
	                rs.close();
	            }
	            if (conn != null) {
	            	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;	                
	            }
	        } catch (Exception e) {
	            ps = null;
	            ps1 = null;
	            rs = null;
	            if (conn != null) {
	            	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
	            }
	        }
            
        /*
        if (con != null)
        {
          con.close();
        }*/
        }
        return seqNo;
    }


}//end of class

