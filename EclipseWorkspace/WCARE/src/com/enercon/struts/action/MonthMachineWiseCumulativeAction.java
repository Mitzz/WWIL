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

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.CustomerDGR;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;
import com.enercon.service.CustomerMasterService;

public class MonthMachineWiseCumulativeAction extends Action{
	
	private static Logger logger = Logger.getLogger(MonthMachineWiseCumulativeAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			logger.debug("enter");
//			String customerId = "0905000002";
			String customerId = request.getParameter("customerId");
			int month = Integer.parseInt(request.getParameter("month"));
			int year = Integer.parseInt(request.getParameter("year"));
//			logger.debug(DateUtility.getFirstDateOfMonth(Month.valueOf(month), Year.valueOf(year)));
//			logger.debug(DateUtility.getLastDateOfMonth(Month.valueOf(month), Year.valueOf(year)));
//			logger.debug(String.format("%s-%s", month, year));
			
//			String fromDate = "01/02/2016";
//			String toDate = "01/02/2016";

			String fromDateOracleDateFormat = DateUtility.getFirstDateOfMonth(Month.valueOf(month), Year.valueOf(year));
			String toDateOracleDateFormat = DateUtility.getLastDateOfMonth(Month.valueOf(month), Year.valueOf(year));
			List<Parameter> parameters = new ArrayList<Parameter>();
			parameters.add(Parameter.GENERATION);
			parameters.add(Parameter.OPERATING_HOUR);
			parameters.add(Parameter.LULL_HOUR);
			parameters.add(Parameter.CF);
			parameters.add(Parameter.GA);
			parameters.add(Parameter.MA);

			ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
			CustomerDGR customerDGR = new CustomerDGR(customer, fromDateOracleDateFormat, toDateOracleDateFormat, parameters).doStateDGR().doTotal().sortStateByName();
			
			logger.debug(String.format("Customer(%s) '%s' DGR for the date %s: %s", customerDGR.getTrial(), customer.getName(), fromDateOracleDateFormat, customerDGR.getTotal()));
			/*for(StateCustomerDGR stateDGR: customerDGR.getStatesTotal()){
				logger.debug(String.format("State %s, Trial %s, Total %s", stateDGR.getState().getName(), stateDGR.getTrial(), stateDGR.getTotal()));
				
				for(SiteStateCustomerDGR siteDGR: stateDGR.getSitesTotal()){
					logger.debug(String.format("Site %s, Trail %s, Total %s", siteDGR.getSite().getName(), siteDGR.getTrial(), siteDGR.getTotal()));
				}
			}*/
			
			request.setAttribute("diff", -1);
			request.setAttribute("customerdgr", customerDGR);
			request.setAttribute("fromDate", fromDateOracleDateFormat);
			request.setAttribute("toDate", toDateOracleDateFormat);
			
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mapping.findForward("success");
	}
}
