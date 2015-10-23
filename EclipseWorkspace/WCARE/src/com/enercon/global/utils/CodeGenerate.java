package com.enercon.global.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CodeGenerate {
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

