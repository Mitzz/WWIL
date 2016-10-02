package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.dgr.CustomerDGR;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.service.CustomerMasterService;

public class YearWecWiseCumulativeAction extends Action{
	
	private static Logger logger = Logger.getLogger(YearWecWiseCumulativeAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		String customerId = request.getParameter("customerId");
		int fiscalYear = Integer.parseInt(request.getParameter("fiscalYear"));
		FiscalYear fy = FiscalYear.valueOf(fiscalYear);
		
		logger.debug(String.format("%s - (%s, %s)", customerId, fy.getFirstDate(), fy.getLastDate()));
		
		String fromDateOracleDateFormat = fy.getFirstDate();
		String toDateOracleDateFormat = fy.getLastDate();
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.MA);

		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		CustomerDGR customerDGR = new CustomerDGR(customer, fromDateOracleDateFormat, toDateOracleDateFormat, parameters).doStateDGR().doTotal().sortStateByName();
		
		request.setAttribute("diff", -1);
		request.setAttribute("customerdgr", customerDGR);
		request.setAttribute("fiscalYear", fiscalYear);
		request.setAttribute("fromDate", fromDateOracleDateFormat);
		request.setAttribute("toDate", toDateOracleDateFormat);
		return mapping.findForward("success");
	}
}
