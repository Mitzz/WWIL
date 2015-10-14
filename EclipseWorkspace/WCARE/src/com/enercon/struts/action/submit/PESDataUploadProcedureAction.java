package com.enercon.struts.action.submit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.admin.dao.AdminDao;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;

public class PESDataUploadProcedureAction extends Action{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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

			new AdminDao().callPESDataInsertionProcedure(dynaBean);
			request.setAttribute("locationNoBean", new AdminDao().getLocationNo());
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
