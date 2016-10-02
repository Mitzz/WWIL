package com.enercon.global.utils;

import java.io.IOException;
import javax.servlet.*;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.util.*;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.siteuser.dao.*;

public final class ExcelUpload extends Action
{
	private final static Logger logger = Logger.getLogger(ExcelUpload.class);
//	private static final long serialVersionUID = 1L;
//	public void service(HttpServletRequest req,HttpServletResponse res)
//	throws IOException, ServletException
//	{
	@Override
	public ActionForward execute(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response) 
	throws IOException, ServletException
	{
		DynaBean dynaBean = GlobalUtils.getDynaBean(request);
		if (dynaBean == null) {
			 dynaBean = GlobalUtils.getAttDynaBean(request);
		}
		String msg="";
		HttpSession session = request.getSession(true);
		try
		{	
			String sUrl = dynaBean.getProperty("Exceltxt").toString();
			String Sheetname = dynaBean.getProperty("Sheettxt").toString();
			String uploadtype = dynaBean.getProperty("Admin_Input_Type").toString();
			File catalogExcel=new File(sUrl);
			String name = catalogExcel.getName();
			if (name.toLowerCase().endsWith("xls"))
			{
				FileInputStream inputStream=new FileInputStream(catalogExcel);
				System.out.println("uploaded input stream available size is "+inputStream.available());
				POIFSFileSystem fileSystem=new POIFSFileSystem(inputStream); 
				HSSFWorkbook wb=new HSSFWorkbook(fileSystem);
				HSSFSheet sheet1=wb.getSheet(Sheetname);				
				Iterator rows = sheet1.rowIterator();
				SiteUserDao sd = new SiteUserDao();
				if(uploadtype.equals("UploadFederData"))
				{					
					msg = sd.UploadFederData(rows,session.getAttribute("loginID").toString(),dynaBean);
					session.removeAttribute("dynabean");
				}
				else if(uploadtype.equals("UploadEBData"))
				{					
					msg = sd.UploadEBData(rows,session.getAttribute("loginID").toString(),dynaBean);
					session.removeAttribute("dynabean");
				}
				else if(uploadtype.equals("UploadWECData"))
				{					
					msg = sd.UploadWECData(rows,session.getAttribute("loginID").toString(),dynaBean);
					session.removeAttribute("dynabean");
				}
				else if(uploadtype.equals("UploadWECRemarks"))
				{					
					msg = sd.UploadWECRemarks(rows,session.getAttribute("loginID").toString(),dynaBean);
					session.removeAttribute("dynabean");
				}
	            session.setAttribute("msg",msg);
	            //this.servlet.getServletContext().setAttribute("dynabean", dynaBean);
				return mapping.findForward("success");
			}
			else{
				msg = "<font class='errormsgtext'>Please select Excel File</font>";
				session.setAttribute("msg",msg);
				return mapping.findForward("failure");
			}			
			//req.setAttribute("msg", msg);			
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			msg = "<font class='errormsgtext'>"+e.toString()+"</font>";
			session.setAttribute("msg",msg);
			return mapping.findForward("failure");
		}
		//finally{
			//HttpSession session = req.getSession(false);
            //session.setAttribute("msg", msg);            
			//req.getRequestDispatcher("/Admin/MonthSalaryUpload.jsp").include(req,res);
		//}
	}
}