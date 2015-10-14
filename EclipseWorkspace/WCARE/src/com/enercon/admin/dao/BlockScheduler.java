package com.enercon.admin.dao;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;

public class BlockScheduler extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		
		String cnt =request.getParameter("totalblockmail") == null ?"" : request.getParameter("totalblockmail").toString();
		String scedate="resendDate"+cnt;
		String schedulerDate = request.getParameter(scedate) == null ?"" : request.getParameter(scedate).toString();
		
		// String schedulerDate = request.getParameter("resendDate").toString();
		// System.out.println("schedulerDate=="+schedulerDate);
		
		DynaBean dynaBean = GlobalUtils.getDynaBean(request);
		if (dynaBean == null) {
			 dynaBean = GlobalUtils.getAttDynaBean(request);
		}
		
		String blockSchedule = dynaBean.getProperty("blockSchedule") == null ?"" : dynaBean.getProperty("blockSchedule").toString();
		
		AdminDao blockScheduledMail = new AdminDao();
		try {
			String msg = blockScheduledMail.blockScheduledMail(blockSchedule,schedulerDate);
			
			if(blockSchedule!="")
				session.setAttribute("msgBlockScheduler", "<font class='sucessmsgtext'>"+msg+"!</font>");
			else
				session.setAttribute("msgBlockScheduler", "<font class='sucessmsgtext'>"+msg+"!</font>");
			forward("/Admin/BlockScheduler.jsp", request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	
	public void forward(String sPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletConfig config = getServletConfig();
		ServletContext context = config.getServletContext();
	
		context.getRequestDispatcher(sPath).include(request, response);
	
	}

}
