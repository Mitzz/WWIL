package com.enercon.security.utils;

import com.enercon.security.bean.LoginMasterBean;
import com.enercon.security.dao.SecurityDao;
import java.util.List;
import org.apache.log4j.Logger;


public class SecurityUtils {

    private static Logger logger = Logger.getLogger(SecurityUtils.class);

    public SecurityUtils() {

    }
    public String getCustinformation(String userid) throws Exception {
 	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
 	        try {

 	        	SecurityDao secDAO = new SecurityDao();
 	           String email = secDAO.getCustinf(userid);
 	            return email;
 	        	} catch (Exception exp) {
 	            logger.error("Error  ");
 	            throw exp;
 	        }
 	    }
    
    public String getPwd(String userid,String email) throws Exception {
  	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
  	        try {

  	        	SecurityDao secDAO = new SecurityDao();
  	            String msg = secDAO.getPwd(userid,email);
  	            return msg;
  	        	} catch (Exception exp) {
  	            logger.error("Error  ");
  	            throw exp;
  	        }
  	    }
    public String sentMsg(String emailid,String userid,String pwd) throws Exception {
   	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
   	        try {

   	        	SecurityDao secDAO = new SecurityDao();
   	            String msg = secDAO.sentMsg(emailid,userid,pwd);
   	            return msg;
   	        	} catch (Exception exp) {
   	            logger.error("Error  ");
   	            throw exp;
   	        }
   	    }
    public String getCustFeedback(String userid) throws Exception {
  	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
  	        try {

  	        	SecurityDao secDAO = new SecurityDao();
  	           String email = secDAO.getCustfeedback(userid);
  	            return email;
  	        	} catch (Exception exp) {
  	            logger.error("Error  ");
  	            throw exp;
  	        }
  	    }
    public boolean isPasswordChange(String userid) throws Exception {
  	   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
  	        try {

  	        	SecurityDao secDAO = new SecurityDao();
  	           boolean isPasswordChange = secDAO.isPasswordChange(userid);
  	            return isPasswordChange;
  	        	} catch (Exception exp) {
  	            logger.error("Error  ");
  	            throw exp;
  	        }
  	    }

//    public Object validateEmployee(String loginID, 
//                                   String password) throws Exception {
//        try {
//
//            SecurityDao secDAO = new SecurityDao();
//            LoginMasterBean validEmployee = secDAO.getEmployeeByLoginID(loginID, password);
//            //System.out.println("Valid Employee : " + validEmployee);
//            String EmployeePWD = validEmployee.getsPassword();
//            String EmployeeID = validEmployee.getSEmployeeId();
//            if (EmployeeID != null) {
//                String decryptedPwd = "";
//                if (EmployeePWD != null) {
//                    try {
//                        decryptedPwd = password; //DataEncrypt.decryptData(EmployeePWD, null);
//                    } catch (Exception cryptEx) {
//                        cryptEx.printStackTrace();
//                        return null;
//                        //break;
//                    }
//                    if (decryptedPwd != null && decryptedPwd.equals(password))
//                        return validEmployee;
//                    else
//                        return null;
//                } else
//                    return null;
//            } else {
//                return null;
//            }
//        } catch (Exception exp) {
//            logger.error("Error in  validating Employee by : " + loginID);
//            throw exp;
//        }
//
//    }

    /*public List getAllTransactions(String roleID,String tabID) throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();
            List transList = secDAO.getAllTransactions(roleID,tabID);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions by " + roleID);
            throw exp;
        }
    }*/
    public List getAllTransactions(String roleID) throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();
            List transList = secDAO.getAllTransactions(roleID);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions by " + roleID);
            throw exp;
        }
    }
    /*public List getAllTabs(String roleID) throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();
            List transList = secDAO.getAllTabs(roleID);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions by " + roleID);
            throw exp;
        }
    }
    */
   
    public List getcustomerdetails(String roleID) throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();
            List transList = secDAO.getAllcustomer(roleID);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions by " + roleID);
            throw exp;
        }
    }
    
    public String getNewsList() throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();
            String newsList = secDAO.getNewsList();
            return newsList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions for News ");
            throw exp;
        }
    }
    
    public String insertLoginHistory(String logID, String password, 
                                      String sroleID,String ipadd,String iphost) throws Exception {
        try {
            SecurityDao secDAO = new SecurityDao();

            String loginRoleHistoryId = "";

            loginRoleHistoryId = 
                    secDAO.insertLoginHistory(logID, password, sroleID,ipadd,iphost);


            return loginRoleHistoryId;
        } catch (Exception exp) {
            logger.error("Error Inserting Role History" + exp);
            throw exp;
        }

    }


}
