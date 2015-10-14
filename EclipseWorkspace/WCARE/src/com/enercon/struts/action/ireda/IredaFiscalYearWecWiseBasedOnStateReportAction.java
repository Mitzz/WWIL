package com.enercon.struts.action.ireda;

import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.model.ireda.IredaProject;
import com.enercon.struts.form.IredaFiscalYearWecWiseBasedOnStateReportForm;

public class IredaFiscalYearWecWiseBasedOnStateReportAction extends Action {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			{
		//MethodClass.displayMethodClassName();
		IredaFiscalYearWecWiseBasedOnStateReportForm formVo = (IredaFiscalYearWecWiseBasedOnStateReportForm)form;
		//System.out.println(formVo);
		IredaProject iredaProject = new IredaProject(formVo.getIredaProjectNo());
		try {
			iredaProject.populateWecWiseDetailsForManyDaysBasedOnState(formVo.getStateId(), 
					DateUtility.getFirstDateOfFiscalYear(formVo.getFiscalYear(), "dd-MMM-yyyy"), 
					DateUtility.getLastDateOfFiscalYear(formVo.getFiscalYear(), "dd-MMM-yyyy"));
		} catch (SQLException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		} catch (ParseException e) {
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		request.setAttribute("ireda", iredaProject);
		request.setAttribute("fiscalYear", formVo.getFiscalYear() + "-" + (formVo.getFiscalYear() + 1));
		request.setAttribute("state", iredaProject.getStateIdNameMapping().get(formVo.getStateId()));
		return mapping.findForward("success");
	}

}
