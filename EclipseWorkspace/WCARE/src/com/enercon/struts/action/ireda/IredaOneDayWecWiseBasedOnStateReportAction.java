package com.enercon.struts.action.ireda;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.ireda.IredaProject;
import com.enercon.struts.form.IredaOneDayWecWiseBasedOnStateReportForm;

public class IredaOneDayWecWiseBasedOnStateReportAction extends Action {
	private final static Logger logger = Logger.getLogger(IredaOneDayWecWiseBasedOnStateReportAction.class);
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		IredaOneDayWecWiseBasedOnStateReportForm formVo = (IredaOneDayWecWiseBasedOnStateReportForm)form;
		//System.out.println(formVo);
		IredaProject iredaProject = new IredaProject(formVo.getIredaProjectNo());
		try {
			iredaProject.populateWecWiseDetailsForManyDaysBasedOnState(formVo.getStateId(), 
					DateUtility.convertDateFormats(formVo.getDate(),"dd/MM/yyyy", "dd-MMM-yyyy"), 
					DateUtility.convertDateFormats(formVo.getDate(),"dd/MM/yyyy", "dd-MMM-yyyy"));
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		request.setAttribute("ireda", iredaProject);
		request.setAttribute("date", DateUtility.convertDateFormats(formVo.getDate(),"dd/MM/yyyy", "dd-MMM-yyyy"));
		request.setAttribute("state", iredaProject.getStateIdNameMapping().get(formVo.getStateId()));
		return mapping.findForward("success");
	}
}
