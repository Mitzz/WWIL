package com.enercon.struts.action.ireda;

import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.ireda.IredaProject;
import com.enercon.struts.form.IredaWecWiseStatewiseMonthlyReportForm;

public class IredaWecWiseStatewiseMonthlyReportAction extends Action {
	private final static Logger logger = Logger.getLogger(IredaWecWiseStatewiseMonthlyReportAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			 {
		
		IredaWecWiseStatewiseMonthlyReportForm formVo = (IredaWecWiseStatewiseMonthlyReportForm)form;
		//System.out.println(formVo);
		IredaProject iredaProject = new IredaProject(formVo.getIredaProjectNo());
		long start = System.currentTimeMillis();
		try {
			iredaProject.populateWecWiseDetailsForManyDaysBasedOnState(formVo.getStateId(), 
					DateUtility.getFirstDateOfTheMonthBasedOnMonthYear(formVo.getMonth(), formVo.getYear(), "dd-MMM-yyyy"), 
					DateUtility.getLastDateOfTheMonthBasedOnMonthYear(formVo.getMonth(), formVo.getYear(), "dd-MMM-yyyy"));
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		long end = System.currentTimeMillis();
		System.out.println("Time Taken : " + (end - start));
		request.setAttribute("ireda", iredaProject);
		request.setAttribute("month", DateUtility.getMonth(formVo.getMonth()));
		request.setAttribute("year", formVo.getYear());
		request.setAttribute("state", iredaProject.getStateIdNameMapping().get(formVo.getStateId()));
		return mapping.findForward("success");
	}
}
