package com.enercon.security.dao;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utils.Diff;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.global.utils.SendMail;
import com.enercon.security.bean.LoginMasterBean;
import com.enercon.global.utils.CodeGenerate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;


public class SecurityDao implements WcareConnector{

    private static Logger logger = Logger.getLogger(SecurityDao.class);

    public SecurityDao() {
    }


    /**
     * 
     * @throws java.lang.Exception
     * @return 
     * @param LoginID
     */
	public String getPwd(String UserId,String email)throws Exception {
		String msg = "";
		String activateMsg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		
		try {
			     
				sqlQuery = SecuritySQLC.GET_PWD;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, UserId.toLowerCase());
				prepStmt.setObject(2, email.toLowerCase());
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					if(rs.getString("ACTIVATED").equals("1"))
					{
    				  msg = rs.getString("s_password");
					}
					else
					{
						msg = "Your account is not activated,You will get an email once it is activated";
					}
				
    			}else{
    				msg = "Sorry,you had enetred the wrong information,try again with right information";
    			}
				rs.close();
				prepStmt.close();
		
	} catch (SQLException sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return msg;
	}
   
	public String sentMsg(String email,String UserId,String pwd)throws Exception {
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SendMail sm=new SendMail();
		try {
			    
			 msg ="<table width='630' border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Dear Sir/Madam,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Please find the mentioned Login Id and Password....</b></font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Login ID is  : "+ UserId.toUpperCase() +"</b></font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Password is : "+ pwd +"</b></font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='200' align='left' ><font class='sucessmsgtext'><b>Regards,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='200' align='left'><font class='sucessmsgtext'><b>Enercon(India) Ltd.,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='200' align='left'><font class='sucessmsgtext'><b>Customer Support Team.</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+

	        	"</table>";
	    	  
	      		//sm.sendMailWithAttachement("Hardikndixit@yahoo.com","abhishek.thakur@windworldindia.com","DGR For The Date - "+rdate,filename,msg);
						
	         	sm.sendMail(email,"EnerconCare@windworldindia.com","Login Details - By Wind World (India) Ltd",msg);
			
	    	  
				
		
	   } catch (Exception sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EMAIL_SENDING_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return msg;
	}

	public String sentActivateMsg(String email,String UserId,String pwd)throws Exception {
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SendMail sm=new SendMail();
		try {
			    
			 msg ="<table width='630' border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Dear Sir/Madam,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Please find the mentioned Login Id and Password</font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Login Id is: "+ UserId +".</font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Password is: "+ pwd +".</font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left' ><font class='sucessmsgtext'><b>Regards,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>" +
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>Enercon(India) Ltd.,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+
	        	
	        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>Customer Support Team.</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+

	        	"</table>";
	    	  
	      		//sm.sendMailWithAttachement("Hardikndixit@yahoo.com","abhishek.thakur@windworldindia.com","DGR For The Date - "+rdate,filename,msg);
						
	         	sm.sendMail(email,"EnerconCare@windworldindia.com","Login Details - By Wind World (India) Ltd",msg);
			
	    	  
				
		
	   } catch (Exception sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EMAIL_SENDING_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return msg;
	}

	
	public String getCustinf(String UserId)throws Exception {
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try {
				sqlQuery = SecuritySQLC.SELECT_LOGIN_DETAILSINF;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, UserId);
				rs = prepStmt.executeQuery();
				if (rs.next()) {
    				msg = "Informationexist";
    			}else{
    				msg = "false";
    			}
				rs.close();
				prepStmt.close();
		
	} catch (SQLException sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return msg;
	}
   
	
	public String getCustfeedback(String UserId)throws Exception {
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement prepStmt1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sqlQuery = "";
		String sqlQuery1 = "";
		try {
			    sqlQuery1 =  SecuritySQLC.SELECT_CUSTOMER_FEEDBACK1;
			    prepStmt1 =  conn.prepareStatement(sqlQuery1);
			    prepStmt1.setObject(1, UserId);
			    rs1 = prepStmt1.executeQuery();
			    if (rs1.next()){
			    	String cnt1=rs1.getString("cnt1");
			    	if(!cnt1.equals("0"))
			    	{				
			    		sqlQuery =   SecuritySQLC.SELECT_CUSTOMER_FEEDBACK;
			    		prepStmt = conn.prepareStatement(sqlQuery);
			    		prepStmt.setObject(1, UserId);
			    		rs = prepStmt.executeQuery();
			    		if (rs.next()) {
			    			String cnt=rs.getString("cnt");
			    			if(!cnt.equals("0"))
			    				msg = "Informationexist";
			    			else
			    				msg = "false";
			    		}else{
			    			msg = "false";
			    		} rs.close();
				    	prepStmt.close();
			    	}else {
			    		msg = "Informationexist";
			    	}
			    	
		
			    } else {
			    	msg = "false";
			    }rs1.close();
		    	prepStmt1.close();
			    
		} catch (SQLException sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return msg;
	}
   
	
	public boolean isPasswordChange(String UserId)throws Exception {
		
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = "";
		boolean isPasswordChange = true;
		int count = -1;
		try {
				sqlQuery = SecuritySQLC.SELECT_LOGIN_PASSWORD;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, UserId);
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					count = rs.getInt("CNT");
					
    			}
				isPasswordChange = (count == 1) ? true : false;
				rs.close();
				prepStmt.close();
		
	} catch (SQLException sqlExp) {
		sqlExp.printStackTrace();
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		try {
			if (prepStmt != null)
				prepStmt.close();
			if (rs != null)
				rs.close();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();
				conmanager = null;
			}
		} catch (Exception e) {
			prepStmt = null;
			rs = null;
			if (conn != null) {
				conn.close();
				conn = null;
				conmanager.closeConnection();
				conmanager = null;
			}
		}
	}
	return isPasswordChange;
	}
   
//    public LoginMasterBean getEmployeeByLoginID(String LoginID, 
//                                                   String Password) throws Exception {
//        //JDBCUtils conmanager = new JDBCUtils();
//        Connection conn = wcareConnector.getConnectionFromPool();
//        PreparedStatement prepStmt = null;
//        ResultSet rs = null;
//        String sqlQuery = SecuritySQLC.GET_LOGIN_BY_ID;
//        LoginMasterBean EmployeeBean = new LoginMasterBean();
//        try {
//            prepStmt = conn.prepareStatement(sqlQuery);
//            prepStmt.setObject(1, LoginID);
//            prepStmt.setObject(2, Password);
//            //System.out.println("LoginPPP:" + LoginID + ":" + Password);
//            rs = prepStmt.executeQuery();
//            while (rs.next()) {
//
//                /*Getting Employee Code from DB*/
//                if (rs.getString("S_USER_ID") != null)
//                    EmployeeBean.setSUserId(rs.getString("S_USER_ID"));
//
//                /*Getting Employee First Name from DB*/
//                if (rs.getString("S_LOGIN_DESCRIPTION") != null)
//                    EmployeeBean.setSName(rs.getString("S_LOGIN_DESCRIPTION"));
//
//                /*Getting Employee PASSWORD from DB*/
//                if (rs.getString("S_PASSWORD") != null)
//                    EmployeeBean.setsPassword(rs.getString("S_PASSWORD"));
//                
//                /*Getting Employee ROLE ID from DB*/
//                if (rs.getString("S_ROLE_ID") != null)
//                    EmployeeBean.setsRoleID(rs.getString("S_ROLE_ID"));
//                
//                /*Getting Employee ROLE NAME from DB*/
//                if (rs.getString("S_ROLE_NAME") != null)
//                    EmployeeBean.setsRoleName(rs.getString("S_ROLE_NAME")); 
//                
//                if (rs.getString("S_LOGIN_TYPE") != null)
//                    EmployeeBean.setsLoginType(rs.getString("S_LOGIN_TYPE")); 
//                
//                if (rs.getString("S_ACTIVE") != null)
//                    EmployeeBean.setsActive(rs.getString("S_ACTIVE"));
//                if (rs.getString("S_REMARKS") != null)
//                    EmployeeBean.setsRemarks(rs.getString("S_REMARKS"));
//               
//            }
//            //System.out.println("---24324-----" + EmployeeBean);
//            rs.close();
//            prepStmt.close();
//        } catch (SQLException sqlExp) {
//            sqlExp.printStackTrace();
//            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
//            throw exp;
//        } finally {
//            try {
//                if (prepStmt != null)
//                    prepStmt.close();
//                if (rs != null)
//                    rs.close();
//                if (conn != null) {
//    				wcareConnector.returnConnectionToPool(conn);
//    			}                
//            } catch (Exception e) {
//                prepStmt = null;
//                rs = null;
//                if (conn != null) {
//    				wcareConnector.returnConnectionToPool(conn);
//    			}
//            }
//        }
//        return EmployeeBean;
//    }
    
    public String insertLoginHistory(String loginID, String password, 
                                      String sroleID,String ipadd,String iphost) throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String loginRoleHistoryId = null;
        try {
            String tblSeq = "TBL_LOGIN_HISTORY";
            String LOGRoleHistoryId = CodeGenerate.NewCodeGenerate(tblSeq);
            loginRoleHistoryId = LOGRoleHistoryId;

            String sqlQuery_InsertLoginRoleHistory = SecuritySQLC.INSERT_LOGIN_ROLE_HISTORY;
            prepStmt = conn.prepareStatement(sqlQuery_InsertLoginRoleHistory);

            prepStmt.setObject(1, loginRoleHistoryId);
            prepStmt.setObject(2, loginID);
            prepStmt.setObject(3, ipadd);
            prepStmt.setObject(4, iphost);
            int iInserteddRows = prepStmt.executeUpdate();
            //conn.commit();
            if (iInserteddRows != 1)
                throw new Exception("DB_INSERT_ERROR", null);
            prepStmt.close();
            //            conmanager.closeConnection();conmanager = null;
            //             conn = null;

        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
                
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return loginRoleHistoryId;
    }
    //sa
    public List getAllcustomer(String sRoleID) throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List tranList = new ArrayList();
        String sqlQuery = SecuritySQLC.GET_LOGIN_BY_CUSID;
        try {
            prepStmt = conn.prepareStatement(sqlQuery);
            prepStmt.setObject(1, sRoleID);
            rs = prepStmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                Vector tranVector = new Vector();
                tranVector.add(rs.getString("N_SHOW_WEC"));
                tranVector.add(rs.getString("N_SHOW_EB"));  
                tranVector.add(rs.getString("S_CUSTOMER_ID"));
                tranVector.add(rs.getString("S_CUSTOMER_NAME"));
                tranList.add(i, tranVector);
                i++;
            }
            prepStmt.close();
            rs.close();
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return tranList;
    }
    
    public String getNewsList() throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String newsList = "";
        String sqlQuery = SecuritySQLC.News_List;
        try {
            prepStmt = conn.prepareStatement(sqlQuery);
            rs = prepStmt.executeQuery();
                        while (rs.next()) {
                 
                //tranVector.add(rs.getString("S_DESCR") == null ? "" : rs.getString("S_DESCR"));
                        	newsList +=rs.getString("S_DESCR")+"*****" ;
              
                            }
            prepStmt.close();
            rs.close();
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return newsList;
    }
    
    public List getAllTransactions(String sRoleID) throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        List tranList = new ArrayList();
        String sqlQuery = SecuritySQLC.GET_TRANSACTION_DETAILS;
        try {
            prepStmt = conn.prepareStatement(sqlQuery);
            prepStmt.setObject(1, sRoleID);
            rs = prepStmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                Vector tranVector = new Vector();
                tranVector.add(rs.getString("S_TRANSACTION_ID"));
                tranVector.add(rs.getString("S_TRANSACTION_NAME"));
                tranVector.add(rs.getString("S_URL"));
                tranVector.add(rs.getString("S_UNDER_1"));
                tranVector.add(rs.getString("S_UNDER_2"));
                tranVector.add(rs.getString("S_UNDER_3"));
                tranVector.add(rs.getString("S_TRANSACTION_DESCRIPTION"));
                tranVector.add(rs.getString("S_DISPLAY"));
                tranVector.add(rs.getString("S_UNDER1_IMAGE_NORMAL"));
                tranVector.add(rs.getString("S_UNDER1_IMAGE_MOUSE"));
                tranVector.add(rs.getString("S_UNDER1_IMAGE_EXPAND"));
                tranVector.add(rs.getString("S_UNDER2_IMAGE_NORMAL"));
                tranVector.add(rs.getString("S_UNDER2_IMAGE_MOUSE"));
                tranVector.add(rs.getString("S_UNDER2_IMAGE_EXPAND"));
                tranVector.add(rs.getString("S_UNDER3_IMAGE_NORMAL"));
                tranVector.add(rs.getString("S_UNDER3_IMAGE_MOUSE"));
                tranVector.add(rs.getString("S_UNDER3_IMAGE_EXPAND"));
                tranVector.add(rs.getString("S_LINK_NORMAL"));
                tranVector.add(rs.getString("S_LINK_MOUSE"));
                tranVector.add(rs.getString("S_LINK_EXPAND"));
                tranList.add(i, tranVector);
                i++;
            }
            prepStmt.close();
            rs.close();
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return tranList;
    }
    
	public static String getHTMLTransaction(String roleID){
    	//mcn.inClassMethod();
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = null;
        PreparedStatement sUnder1PrepareStmt = null;
        ResultSet sUnder1ResultSet = null;
        PreparedStatement sUnder2PrepareStmt = null;
        ResultSet sUnder2ResultSet = null;
        PreparedStatement sUnder2NullPrepare = null;
        ResultSet sUnder2NullResult = null;
        PreparedStatement myPS3 = null;
        ResultSet myrs3 = null;
		try {
			conn = conmanager.getConnection();
			/*String myQ1 = "SELECT distinct(s_under_1) FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID";*/
			
			/*String myQ1 = "SELECT A.s_under_1 FROM TBL_ROLE_TRAN_MAPPING B left join TBL_TRANSACTION A on A.S_TRANSACTION_ID = B.S_TRANSACTION_ID where B.S_ROLE_ID = ?" +
					" AND A.N_SEQ_NO in (select min(N_SEQ_NO) from TBL_TRANSACTION where A.S_UNDER_1 = TBL_TRANSACTION.S_UNDER_1) ORDER BY A.s_transaction_id";*/
			/*String myQ1 = "SELECT A.s_under_1 FROM TBL_ROLE_TRAN_MAPPING B left join TBL_TRANSACTION A on A.S_TRANSACTION_ID = B.S_TRANSACTION_ID where B.S_ROLE_ID = ? AND A.N_SEQ_NO in (select min(N_SEQ_NO) from TBL_TRANSACTION where A.S_UNDER_1 = TBL_TRANSACTION.S_UNDER_1) ORDER BY A.s_under_1";*/
			String sUnder1Query = "SELECT DISTINCT A.S_UNDER_1, MIN(A.N_SEQ_NO) " +
									"FROM TBL_TRANSACTION A, TBL_ROLE_TRAN_MAPPING B WHERE A.S_TRANSACTION_ID = B.S_TRANSACTION_ID AND B.S_ROLE_ID = ? " +
									"GROUP BY A.S_UNDER_1 " +
									"ORDER BY MIN(A.N_SEQ_NO), A.S_UNDER_1";
	        sUnder1PrepareStmt = conn.prepareStatement(sUnder1Query);
	        sUnder1PrepareStmt.setObject(1, roleID);
	        sUnder1ResultSet = sUnder1PrepareStmt.executeQuery();
	        int iter = 0;
	        StringBuffer htmlDirectContent = new StringBuffer();
	        while(sUnder1ResultSet.next()){
	        	iter++;
	        	String sUnder1 = sUnder1ResultSet.getString("S_UNDER_1");
	        	/*Space is important in href*/
	        	htmlDirectContent.append("<li><a href =\"#\">" + sUnder1 + "</a>\n\t<ul>\n");
	        	//System.out.println("------------------Sunder1:" + sUnder1);
	        	/*String mysql2 = "SELECT distinct(s_under_2) FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID and s_under_1 = ? ";*/
	        	String sUnder2Query= "SELECT DISTINCT A.S_UNDER_2, MIN(A.N_SEQ_NO) " +
	        			"FROM TBL_TRANSACTION A, TBL_ROLE_TRAN_MAPPING B WHERE A.S_TRANSACTION_ID = B.S_TRANSACTION_ID AND B.S_ROLE_ID = ? AND A.S_UNDER_1 = ? " +
	        			"GROUP BY A.S_UNDER_2 " +
	        			"ORDER BY MIN(A.N_SEQ_NO), A.S_UNDER_2";
	        	sUnder2PrepareStmt = conn.prepareStatement(sUnder2Query);
	        	sUnder2PrepareStmt.setObject(1, roleID);
	        	sUnder2PrepareStmt.setObject(2, sUnder1);
	            sUnder2ResultSet = sUnder2PrepareStmt.executeQuery();
	            while(sUnder2ResultSet.next()){
	            	iter++;
	            	String sUnder2 =  sUnder2ResultSet.getString("S_UNDER_2");
	            	//System.out.println("----------------------------------Sunder2:" + sUnder2);
	            	if(sUnder2 == null){
	            		String sUnder2Null = "SELECT s_transaction_name,s_url FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A " +
	            				"WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID and " +
	            				"s_under_1 = ? and s_under_2 is null order by A.N_SEQ_NO";
	            		
	            		/*String sUnder2Null = "SELECT A.s_transaction_name,A.s_url FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A" +
	            				"WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID and" +
	            				"s_under_1 = ? and s_under_2 is null" +
	            				"order by A.N_SEQ_NO";*/
	            		sUnder2NullPrepare = conn.prepareStatement(sUnder2Null);
	            		sUnder2NullPrepare.setObject(1, roleID);
	            		sUnder2NullPrepare.setObject(2, sUnder1);
	            		sUnder2NullResult = sUnder2NullPrepare.executeQuery();
	            		while(sUnder2NullResult.next()){
	            			iter++;
	            			String transactionName = sUnder2NullResult.getString(1);
	            			
	            			String link = sUnder2NullResult.getString(2);
	            			System.out.println(link);
	            			//System.out.println("-------------------------------Transaction Name:" + transName + " Link:" + link);
	            			htmlDirectContent.append("\t\t<li><a href=\"" + link + "\">" + transactionName + "</a></li>\n");
	            			/*htmlDirectContent.append("\t\t<li><a href=\"" + "/" + link + "\">" + content + "</a></li>\n");*/
	            			/*htmlDirectContent.append("\t\t<li><a href=\"" + "<%= request.getContextPath() %>/" + link + "\">" + content + "</a></li>\n");*/
	            		}
	            		continue;
	            	}
	            	/*Space is important in href*/
	            	htmlDirectContent.append("\t\t<li><a href =\"#\">" + sUnder2 + "</a>\n");
	            	htmlDirectContent.append("\t\t\t<ul>\n");
	            	String mysql3 = "SELECT s_transaction_name,s_url " +
	            					"FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A " +
	            					"WHERE B.S_ROLE_ID = ? " +
	            					"AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID " +
	            					"and s_under_1 = ? and s_under_2 = ? order by A.N_SEQ_NO";
	            	/*String mysql3 = "SELECT s_transaction_name,s_url FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A" +
	            			"WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID and" +
	            			"A.s_under_1 = ? and A.s_under_2 = ?" +
	            			"order by A.N_SEQ_NO";*/
	            	myPS3 = conn.prepareStatement(mysql3);
	            	myPS3.setObject(1, roleID);
	            	myPS3.setObject(2, sUnder1);
	            	myPS3.setObject(3, sUnder2);
	                myrs3 = myPS3.executeQuery();
	                while(myrs3.next()){
	                	iter++;
	                	String transactionName = myrs3.getString(1);
	                	String hrf = myrs3.getString(2);
	                	System.out.println(hrf);
	                	//System.out.println("-----------------------------------------------------------Transaction Name:" + transactionName + " Link:" + hrf);
	                	htmlDirectContent.append("\t\t\t\t<li><a href=\"" + hrf + "\">" + transactionName + "</a></li>\n");
	                	/*htmlDirectContent.append("\t\t\t\t<li><a href=\"" + "/" + hrf + "\">" + transactionName + "</a></li>\n");*/
	                	/*htmlDirectContent.append("\t\t\t\t<li><a href=\"" + "<%= request.getContextPath() %>/" + hrf + "\">" + transactionName + "</a></li>\n");*/
	                }
	                htmlDirectContent.append("\t\t\t</ul>\n\t\t</li>\n");
	            }
	            htmlDirectContent.append("\t</ul>\n</li>\n");
	        }
	        htmlDirectContent.append("");
	        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
	        System.out.println("htmlcontent:" + htmlDirectContent);
	        return new String(htmlDirectContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(sUnder1PrepareStmt != null){
				try {
					sUnder1PrepareStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder2PrepareStmt != null){
				try {
					sUnder2PrepareStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder1PrepareStmt != null){
				try {
					sUnder2PrepareStmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder2NullPrepare != null){
				try {
					sUnder2NullPrepare.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder1ResultSet != null){
				try {
					sUnder1ResultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder2ResultSet != null){
				try {
					sUnder2ResultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(myrs3 != null){
				try {
					myrs3.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(sUnder2NullResult != null){
				try {
					sUnder2NullResult.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//mcn.outClass();
		}
		return null;
        
	}
	
    
    public String UpdatePwd() throws Exception {
    	String msg="";
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Diff epwd = new Diff();
        String sqlQuery = SecuritySQLC.GET_ALL_EMPLOYEE;
        try {
            prepStmt = conn.prepareStatement(sqlQuery);
            rs = prepStmt.executeQuery();
            while (rs.next()) {
                String encpwd = epwd.encrpt(rs.getString("s_password"));
                String sqlqString = SecuritySQLC.UPDATE_EMPLOYEE_PASSWORD;
            	ps = conn.prepareStatement(sqlqString);
            	ps.setObject(1, encpwd);
            	ps.setObject(2, rs.getString("s_employee_id"));
            	int iInserteddRows = ps.executeUpdate();
            	//conn.commit();
            	ps.close();
            	ps = null;
            }
        	msg = "update success";
        	prepStmt.close();
        	rs.close();
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return msg;
    }
   
    public String getSecAjaxDetails(String item,String action,String UserId) throws Exception{
    	StringBuffer xml = new StringBuffer();
    	JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String sqlQuery = "";
        String msg="";
        try{
        	if (action.equals("SEC_TAXFORECAST"))
        	{        		
        		//sqlQuery = SecuritySQLC.GET_TAX_FORECAST_BY_EMPLOYEE;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1, UserId);
        		rs = ps.executeQuery();
        		xml.append("<?xml version=\"1.0\"?>\n");
			    xml.append("<taxforecast generated=\""+System.currentTimeMillis()+"\">\n");
        		while (rs.next())
        		{
        			xml.append("<seqno>");
        			xml.append("<pstartdate>");
        			//xml.append();
        			xml.append("</pstartdate>\n");
        			xml.append("<penddate>");        			
        			xml.append("</seqno>\n");
        		}
        		xml.append("</taxforecast>\n");
        		rs.close();
        		ps.close();
        	}
        	else if (action.equals("SEC_LOGINDETAIL"))
        	{        		

    			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        		sqlQuery = SecuritySQLC.SELECT_LOGIN_DETAILS;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1, UserId);
        		rs = ps.executeQuery();
        		xml.append("<?xml version=\"1.0\"?>\n");
			    xml.append("<logindetail generated=\""+System.currentTimeMillis()+"\">\n");
			    xml.append("<lcode>");
        		if (rs.next())
        		{ 
        			xml.append("<sactive>");
        			xml.append(rs.getObject("ACTIVATED"));
        			xml.append("</sactive>");
        			
        			xml.append("<slogid>");
        			xml.append(rs.getObject("S_LOGIN_DETAIL_ID"));
        			xml.append("</slogid>");
        			
        			
        			
        			xml.append("<soname>");
        			xml.append(escape(rs.getObject("S_OWNER_NAME").toString()));
        			xml.append("</soname>");
        			
        			xml.append("<soemail>");
        			xml.append(escape(rs.getObject("S_OEMAIL")==null?"0":rs.getObject("S_OEMAIL").toString()));
        			xml.append("</soemail>");
        			
        			xml.append("<sophone>");
        			xml.append(escape(rs.getObject("S_OPHONE_NUMBER")==null?"0":rs.getObject("S_OPHONE_NUMBER").toString()));
            		xml.append("</sophone>");
        			
        			xml.append("<socell>");
        			xml.append(escape(rs.getObject("S_OCELL_NUMBER")==null?"0":rs.getObject("S_OCELL_NUMBER").toString()));
            		xml.append("</socell>");
        			
        			xml.append("<sofax>");
        			xml.append(escape(rs.getObject("S_OFAX_NUMBER")==null?"0":rs.getObject("S_OFAX_NUMBER").toString()));
        			xml.append("</sofax>");
        			
        			xml.append("<odoab>");
        			
        			if(rs.getObject("D_ODOB_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_ODOB_DATE")));
        			}
        			xml.append("</odoab>");
        			
        			xml.append("<odoad>");
        			if(rs.getObject("D_ODOA_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_ODOA_DATE")));
        			}
        			xml.append("</odoad>");
        			xml.append("<sperson>");
        			xml.append(escape(rs.getObject("S_CONTACT_PERSON_NAME").toString()));
        			xml.append("</sperson>");
        			xml.append("<saddr>");
        			xml.append(escape(rs.getObject("S_CORRES_ADDRES").toString().replace("<BR>", ",")));
        			xml.append("</saddr>");
        			xml.append("<scity>");
        			xml.append(escape(rs.getObject("S_CITY").toString()));
        			xml.append("</scity>");
        			xml.append("<szip>");
        			xml.append(escape(rs.getObject("S_ZIP")==null?"0":rs.getObject("S_ZIP").toString()));
        			xml.append("</szip>");
        			xml.append("<semail>");
        			xml.append(escape(rs.getObject("S_EMAIL")==null?"0":rs.getObject("S_EMAIL").toString()));
        			xml.append("</semail>");
        			xml.append("<sphone>");
        			xml.append(escape(rs.getObject("S_PHONE_NUMBER")==null?"0":rs.getObject("S_PHONE_NUMBER").toString()));
        			xml.append("</sphone>");
        			xml.append("<scell>");
        			xml.append(escape(rs.getObject("S_CELL_NUMBER")==null?"0":rs.getObject("S_CELL_NUMBER").toString()));
        			xml.append("</scell>");
        			xml.append("<sfax>");
        			xml.append(escape(rs.getObject("S_FAX_NUMBER")==null?"0":rs.getObject("S_FAX_NUMBER").toString()));
        			xml.append("</sfax>");
        			xml.append("<dobd>");
        			
        			if(rs.getObject("D_DOB_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_DOB_DATE")));
        			}
        			
        			xml.append("</dobd>");
        			
        			xml.append("<doad>");
        			if(rs.getObject("D_DOA_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_DOA_DATE")));
        			}
        			
        			
        			xml.append("</doad>");
        			
        			      			
        			
        		}
        		
        		xml.append("</lcode>\n");
        		xml.append("</logindetail>\n");
        		rs.close();
        		ps.close();
        	}
        	
        	
        	
        }catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (ps != null) ps.close();
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (rs != null) rs.close();
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (conn != null) 
                {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                ps = null;
                ps1 = null;
                ps2 = null;
                rs = null;
                rs1 = null;
                rs2 = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return xml.toString();
    }
    
    public String escape(String s) {
		s = s.replace("&", " amp ");
		s = s.replace("'", " apos ");
		s = s.replace("<", " ltthan ");
		s = s.replace(">", " gtthan ");
		s = s.replace("<BR>", "  ");
		// s = s.replace('"', "quot");

		return s;
	}
    public String MonthSalaryTransfer() throws Exception {
    	String msg="";
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Diff epwd = new Diff();
        String sqlQuery = "";
        try {
        	sqlQuery = "SELECT * FROM ebmeterreading ORDER BY EBFROMDATE,S_CUSTOMER_ID";
            prepStmt = conn.prepareStatement(sqlQuery);
            rs = prepStmt.executeQuery();
            while(rs.next()) {
            	//String SalId = CodeGenerate.NewCodeGenerate("TBL_EB_READING");
                String sqlqString = "UPDATE TBL_EB_READING SET S_CUSTOMER_ID = ? " +
                					"WHERE d_reading_date=? and s_eb_id=?";
                ps = conn.prepareStatement(sqlqString);  
                ps.setObject(1, rs.getObject("S_CUSTOMER_ID"));
                ps.setDate(2, rs.getDate("EBFROMDATE"));
                ps.setObject(3, rs.getObject("S_EB_ID") == null ? "" : rs.getObject("S_EB_ID"));
                ps.executeUpdate();
            	ps.close();
            	ps = null;
            }
            rs.close();
            prepStmt.close();
        	msg = "update success";
        } catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (prepStmt != null) {
                    prepStmt.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
        return msg;
    }
    
}