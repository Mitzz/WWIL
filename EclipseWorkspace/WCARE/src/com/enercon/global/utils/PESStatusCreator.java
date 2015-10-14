package com.enercon.global.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.admin.dao.AdminDao;

public final class PESStatusCreator extends Action{
	//	private static final long serialVersionUID = 1L;
	//	public void service(HttpServletRequest req,HttpServletResponse res)
	//	throws IOException, ServletException
	//	{
	public ActionForward execute(ActionMapping mapping,	ActionForm form,HttpServletRequest request,	HttpServletResponse response) 
			throws IOException, ServletException{
		DynaBean dynaBean = GlobalUtils.getDynaBean(request);
		if (dynaBean == null) {
			dynaBean = GlobalUtils.getAttDynaBean(request);
		}
		//System.out.println("Data Validation:" + dynaBean);
		String msg="";
		//String NUM="";
		//List translist=new ArrayList();

		HttpSession session = request.getSession(true);

		try{	

			request.setAttribute("PESDataStorage", new AdminDao().getPESStatusWECWise());
			//session.setAttribute("translist",translist);
			session.setAttribute("msg","");
			return mapping.findForward("success");
		}
		catch(Exception e){
			System.out.println("error:"+e);
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