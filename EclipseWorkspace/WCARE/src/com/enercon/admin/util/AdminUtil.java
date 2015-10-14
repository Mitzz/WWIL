package com.enercon.admin.util;

import com.enercon.admin.bean.CreateRoleBean;
import com.enercon.admin.bean.AdminBean;
import com.enercon.admin.dao.AdminDao;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.JDBCUtils;

import com.enercon.reports.dao.ReportDao;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import org.apache.log4j.Logger;

/*
 * @Author : Shailendra A Tiwari
 * @Date: 20 May 2007
 */ 

public class AdminUtil {
    /**
     * For Logging Purpose.
     */
    private static Logger logger = Logger.getLogger(AdminUtil.class);

    public AdminUtil() {
    }

    public List getAuthDetail(String roleid) throws Exception {
        try {
        	AdminDao adminDao = new AdminDao();
            List transList = adminDao.getAuthDetail(roleid);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting getAllPendingpunch ");
            throw exp;
        }
    }
    public Hashtable getAllUser() throws Exception {
        logger.debug("ENERCON_DEBUG: AdminUtils: getAllUser: Method Invoked.");
        Hashtable retHash = new Hashtable();
        try {
            AdminDao adminDao = new AdminDao();

            retHash = adminDao.getAllUserDao();
        } catch (Exception exp) {
            logger.error("Error occured while getAllUser");
            throw exp;
        }

        return retHash;
    }
    public List getLoginDetail(String fdt,String ldt) throws Exception {
        try {
        	AdminDao secDAO = new AdminDao();
            List transList = secDAO.getLoginDetail(fdt,ldt); 
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting transactions for Birth Day ");
            throw exp;
        }
    } 
   public List searchempbyfilter(String empcode,String firname,String lastname,String location,String bloodgroup) throws Exception {
   // public List searchempbyfilter(DynaBean dyanbean) throws Exception {
        try {

        	AdminDao adminDAO = new AdminDao();
          	List transList = adminDAO.searchempbyfilter(empcode,firname,lastname,location,bloodgroup);
            return transList;
        	} catch (Exception exp) {
            logger.error("Error  ");
            throw exp;
        }
    }
    public static String fillComboInOrderForRole(Hashtable inputhash) {
        StringBuffer buffer = new StringBuffer();

        TreeMap treemap = new TreeMap(inputhash);
        Iterator iter = treemap.keySet().iterator();

        while (iter.hasNext()) {
            Object key = iter.next();
            String value = treemap.get(key).toString();
            ////System.out.println("Role Name is : " + key);
            ////System.out.println("Role ID is : " + value);
            //if (!(key.equals("VENDOR")) && !(key.equals("CUSTOMER")) && 
            //    !(key.equals("ADMINISTRATOR")))
                buffer.append("<OPTION VALUE='" + key + "'>" + value + "</OPTION>");
        }
        return buffer.toString();
    }
    
    public static String fillLocationMaster(String tablename,String default_selected) throws Exception{
    	StringBuffer buffer = new StringBuffer();
        AdminDao ad = new AdminDao();
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String sqlQuery = ad.getMaster(tablename);
        String locationNo = "";
        try{
        	prepStmt = conn.prepareStatement(sqlQuery);
        	rs = prepStmt.executeQuery();
        	while(rs.next()){
        		locationNo = rs.getString(1);
        		if (default_selected.equals(locationNo)){
        			buffer.append("<OPTION VALUE='" + locationNo + "' SELECTED>" + locationNo + "</OPTION>");
        		}else{
        			buffer.append("<OPTION VALUE='" + locationNo + "'>" + locationNo + "</OPTION>");
        		}
        	}
        	rs.close();
        	prepStmt.close();
        }
        catch (Exception exp) {
        	exp.printStackTrace();
            if (conn != null) {
            	conn.close();
            	conn = null;            	
                conmanager.closeConnection();
                conmanager = null;
                
            }
            return ("Insertion Error");
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            } catch (Exception e) {

                prepStmt = null;
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            }
        }
        return buffer.toString();
    }
    
    public static String fillMaster(String tablename, String default_selected) throws Exception{
        StringBuffer buffer = new StringBuffer();
        AdminDao ad = new AdminDao();
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String sqlQuery = ad.getMaster(tablename);
        try{
        	prepStmt = conn.prepareStatement(sqlQuery);
        	rs = prepStmt.executeQuery();
        	while(rs.next())
        	{
        		if (default_selected.equals(rs.getObject(1))){
        			buffer.append("<OPTION VALUE='" + rs.getObject(1) + "' SELECTED>" + rs.getObject(2) + "</OPTION>");
        		}else{
        			buffer.append("<OPTION VALUE='" + rs.getObject(1) + "'>" + rs.getObject(2) + "</OPTION>");
        		}
        	}
        	rs.close();
        	prepStmt.close();
        }
        catch (Exception exp) {
        	exp.printStackTrace();
            if (conn != null) {
            	conn.close();
            	conn = null;            	
                conmanager.closeConnection();
                conmanager = null;
                
            }
            return ("Insertion Error");
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            } catch (Exception e) {

                prepStmt = null;
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            }
        }
        return buffer.toString();
    }
    
    public static String fillWhereMaster(String tablename, String default_selected,String uid) throws Exception{
        StringBuffer buffer = new StringBuffer();
        AdminDao ad = new AdminDao();
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String sqlQuery = ad.getMaster(tablename);
        try{
        	prepStmt = conn.prepareStatement(sqlQuery);        	
        	if (tablename.equals("RIGHTS_EMPLOYEE_DETAIL_FOR_COMBO"))
        	{
        		String comma[]=uid.split(",");
        		String loc=comma[0];
        		String eid=comma[1];
        		prepStmt.setObject(1, loc);
        		prepStmt.setObject(2, eid);
        	}
        	else if (tablename.equals("TBL_SITE_MASTER_BY_RIGHTS"))
        	{
        		String comma[]=uid.split(",");
        		String stid=comma[0];
        		String userid=comma[1];
        		prepStmt.setObject(1, stid);
        		prepStmt.setObject(2, userid);
        	}
        	else if (tablename.equals("TBL_SITE_MASTER_BY_RIGHTS1"))
        	{
        		String comma[]=uid.split(",");
        		String stid=comma[0];
        		String userid=comma[1];
        		prepStmt.setObject(1, stid);
        		//prepStmt.setObject(2, userid);
        	}
        	else
        	{
        		prepStmt.setObject(1, uid);
        	}
        	rs = prepStmt.executeQuery();
        	while(rs.next())
        	{
        		if (default_selected.equals(rs.getObject(1))){
        			buffer.append("<OPTION VALUE='" + rs.getObject(1) + "' SELECTED>" + rs.getObject(2) + "</OPTION>");
        		}else{
        			buffer.append("<OPTION VALUE='" + rs.getObject(1) + "'>" + rs.getObject(2) + "</OPTION>");
        		}
        	}
        	rs.close();
        	prepStmt.close();
        }
        catch (Exception exp) {
        	exp.printStackTrace();
            if (conn != null) {
            	conn.close();
                conmanager.closeConnection();
                conn = null;
            }
            return ("Insertion Error");
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            } catch (Exception e) {


                prepStmt = null;
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            }
        }
        return buffer.toString();
    }
    
    
    public static String fillComboFiscalYear(int selYear)
    {
    	StringBuffer buffer = new StringBuffer();
    	int i = 0;
    	
    	int nYear = 2006;
    	int nYear1;
    	String aYear;
    	for(i = 1; i <= 10; i++ )
    	{ nYear1 = nYear+1;
    	    aYear = nYear + "-" + nYear1;
    		if (nYear == selYear){
    			buffer.append("<OPTION VALUE='" + aYear + "' selected>" + aYear + "</OPTION>");
    		}
			else{
				buffer.append("<OPTION VALUE='" + aYear + "'>" + aYear + "</OPTION>");
			    }    				
    		nYear = nYear + 1;
    	}
    	return buffer.toString();
    }
    
  

    public static String fillComboYear(int selYear)
    {
    	StringBuffer buffer = new StringBuffer();
    	int i = 0;
    	int nYear = 2006;
    	for(i = 1; i <= 10; i++ )
    	{
    		if (nYear == selYear){
    			buffer.append("<OPTION VALUE='" + nYear + "' selected>" + nYear + "</OPTION>");
    		}
			else{
				buffer.append("<OPTION VALUE='" + nYear + "'>" + nYear + "</OPTION>");
			}    				
    		nYear = nYear + 1;
    	}
    	return buffer.toString();
    }
    
    public Hashtable getAllRoles() throws Exception {
        logger.debug("ENERCON_DEBUG: AdminUtils: getAllRoles: Method Invoked.");
        Hashtable retHash = new Hashtable();
        try {
            AdminDao adminDao = new AdminDao();

            retHash = adminDao.getAllRolesDao();
        } catch (Exception exp) {
            logger.error("Error occured while getAllRoles");
            throw exp;
        } 
        return retHash;
    }
    

    public Hashtable getAllTransactionNames() throws Exception {
        logger.debug("ENERCON_DEBUG: AdminUtils: getAllTransactionNames: Method Invoked.");
        Hashtable retHash = new Hashtable();
        try {
            AdminDao adminDao = new AdminDao();

            retHash = adminDao.getAllTransactionNamesDao();
        } catch (Exception exp) {
            logger.error("Error occured while getAllTransactionNames");
            throw exp;
        }

        return retHash;
    }

    public List getWECMasterData(String state,String area,String substation,String feeder,String site,String cust,String eb,String wec,String wectype,String comm, String userid,String comm1, String wecStatus) throws Exception {
        try {
        	ReportDao rd = new ReportDao(); 
            List transList = rd.getWECMasterData(state,area,substation,feeder,site,cust,eb,wec,wectype,comm,userid,comm1,wecStatus);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting getAllPendingLeaves ");
            throw exp;
        }
    } 
    
   public List getCustomerFeedbackData(String custname) throws Exception {
        try {
        	ReportDao rd = new ReportDao(); 
            List transList = rd.getCustomerFeedbackData(custname);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting getAllPendingLeaves ");
            throw exp;
        }
    } 
    
    public List getWECShortFall(String states,String customers,String fiscalyear, int co) throws Exception {
        try {
        	ReportDao rd = new ReportDao(); 
            List transList = rd.getWECShortFall(states,customers,fiscalyear,co);
            return transList;
        } catch (Exception exp) {
            logger.error("Error in  getting getAllPendingLeaves ");
            throw exp;
        }
    } 
    
    public static String GetDashRight(String tablename,String uid) throws Exception{
        String buffer = new String();
        AdminDao ad = new AdminDao();
        JDBCUtils conmanager = new JDBCUtils();
        Connection conn = conmanager.getConnection();
        PreparedStatement prepStmt = null;
        ResultSet rs = null;
        String sqlQuery = ad.getMaster(tablename);
        try{
        	prepStmt = conn.prepareStatement(sqlQuery);        	
        	// prepStmt.setObject(1, uid);
        	
        	rs = prepStmt.executeQuery();
        	while(rs.next())
        	{
        		buffer=rs.getObject("ALLOWED_ID").toString();
        		buffer=buffer+"AND"+rs.getObject("S_LOCATION_RIGHT_TYPE").toString();
        	}
        	rs.close();
        	prepStmt.close();
        }
        catch (Exception exp) {
        	exp.printStackTrace();
            if (conn != null) {
            	conn.close();
                conmanager.closeConnection();
                conn = null;
            }
            return ("Insertion Error");
        } finally {
            try {
                if (prepStmt != null)
                    prepStmt.close();
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            } catch (Exception e) {


                prepStmt = null;
                if (conn != null) {
                	conn.close();
                    conmanager.closeConnection();
                    conn = null;
                }
            }
        }
        return buffer.toString();
    }
    
    
    public static String fillComboInOrder(Hashtable inputhash) {
        StringBuffer buffer = new StringBuffer();
        TreeMap treemap = new TreeMap(inputhash);
        Iterator iter = treemap.keySet().iterator();

        while (iter.hasNext()) {
            Object key = iter.next();
            String value = treemap.get(key).toString();

            buffer.append("<OPTION VALUE='" + key + "'>" + value + "</OPTION>");
            ////System.out.println("<OPTION VALUE='" + key + "'>" + value + 
            //        "</OPTION>");
        }

        return buffer.toString();
    }
    
    public static String getMsgDescription(String msgid) {
    	
    	AdminDao adminDao = new AdminDao();
    	String msg ="";
        try {

            
             msg = adminDao.getMsg(msgid); 
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.error("PWC_ENERCON_ERROR: AdminUtils: checkRoleExists: Exception: " + 
                         exp.toString());
        }
        return msg;
    
    }
    /*
     * 
     * @throws java.lang.Exception
     * @return 
     * @param dynaBean
     */
    public CreateRoleBean populateCreateRoleBean(DynaBean dynaBean) throws Exception {
        logger.debug("PWC_ENERCON_DEBUG: AdminUtils: populateCreateRoleBean: Method Invoked.");
        //System.out.println("Enter in to the Util Method Role " + 
                           //dynaBean.getProperty("role").toString());
        CreateRoleBean createRoleBean = new CreateRoleBean();
        createRoleBean.setSRoleName(dynaBean.getProperty("role").toString());
        createRoleBean.setSRoleDescription(dynaBean.getProperty("description").toString());
        return createRoleBean;
    }

    public AdminBean CheckAdminLogin(DynaBean dynaBean) throws Exception {
        logger.debug("PWC_ENERCON_DEBUG: AdminUtils: CheckAdminLogin: Method Invoked.");
        //System.out.println("Enter in to the Check Admin Login : " + 
                         //  dynaBean.getProperty("sLoginID").toString());
        AdminBean adminbean = new AdminBean();
        adminbean.setsAdminID(dynaBean.getProperty("sLoginID").toString());
        adminbean.setsAdminPassword(dynaBean.getProperty("sPassword").toString());
        return adminbean;
    }

    public boolean checkRoleExists(String roleName) throws Exception {
        logger.debug("ENERCON_DEBUG: AdminUtils: checkRoleExists: Method Invoked.");
        boolean roleExists = false;
        try {

            AdminDao adminDao = new AdminDao();
            roleExists = adminDao.checkRoleExists(roleName);
        } catch (Exception exp) {
            exp.printStackTrace();
            logger.error("PWC_ENERCON_ERROR: AdminUtils: checkRoleExists: Exception: " + 
                         exp.toString());
        }
        return roleExists;
    }
    
    public String getLoginId(String abc){
    	AdminDao ado = new AdminDao();
    	String logindetail = ado.getLoginId(abc);
    return logindetail;
    }
    
    public String getImageDesc(){
    	AdminDao ado = new AdminDao();
    	String imageDesc = "";
    	try{
    	imageDesc = ado.getImageDesc();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return imageDesc;
    }
}
