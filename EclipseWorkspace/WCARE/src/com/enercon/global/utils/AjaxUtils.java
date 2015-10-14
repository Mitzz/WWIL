package com.enercon.global.utils;

//import developerworks.ajax.store.Cart;
import javax.servlet.http.*;
import com.enercon.admin.dao.*;
import com.enercon.security.dao.*;
import com.enercon.scada.dao.*;
import com.enercon.siteuser.dao.SiteUserDao;
import com.enercon.projects.dao.ProjectDao;

import com.enercon.reports.dao.ReportDao;
import com.enercon.customer.dao.CustomerDao;

public class AjaxUtils extends HttpServlet {

  /**
   * Updates Cart, and outputs XML representation of contents
   */
  @Override
public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
	  
    String action = req.getParameter("Admin_Input_Type");
    String item = req.getParameter("AppId");   
    String dataXml = "";
    String UserId="";
    AdminDao ad = new AdminDao();
    
    ProjectDao pr = new ProjectDao();
    ReportDao rt = new ReportDao();
    CustomerDao ct = new CustomerDao();
    
    SecurityDao sd = new SecurityDao();
    SiteUserDao su = new SiteUserDao();
    
    ScadaDao sc = new ScadaDao();
    //if((req.getParameter("UserId")!=null) && (req.getParameter("UserId")!="")){
    //	UserId =req.getParameter("UserId");
    //}else{ 
    	HttpSession session = req.getSession(true);
    	UserId = session.getAttribute("loginID").toString();
    //}
    if ((action != null)&&(item != null))    {
    	try {
    		if (action.startsWith("SEC_")){
    			dataXml = sd.getSecAjaxDetails(item, action,UserId);
    		}
    		else if (action.startsWith("PRJ_")){
    			dataXml = pr.getPRJAjaxDetails(item, action,UserId);
    		}
    		else if (action.startsWith("RPT_")){
    			dataXml = rt.getRPTAjaxDetails(item, action,UserId);
    		}
    		else if (action.startsWith("CUST_")){
    			dataXml = ct.getCUSTAjaxDetails(item, action,UserId);
    		}
    		else if (action.startsWith("SU_")){
    			dataXml = su.getAjaxDetails(item, action,UserId);
    		}
    		else if (action.startsWith("SCADA_")){
    			dataXml = sc.getScadaAjaxDetails(item, action, UserId);
    		}
    		else
    		{
    			
    			dataXml = ad.getAjaxDetails(item, action,UserId);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    res.setContentType("text/xml");
    res.getWriter().write(dataXml);
  }

  @Override
public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
    // Bounce to post, for debugging use
    // Hit this servlet directly from the browser to see XML
    doPost(req,res);
  }
  
}

