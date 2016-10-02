package com.enercon.struts.requestProcessor;

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
import com.enercon.service.CustomerMasterService;

//Total of each level in DGR Problem
//Data Present Check in Wec Reading Summary
//Add in WecParameterVo
//Comparator for CUstomerStateDgr
//Only Active Wecs are considered
//If any one wec is in trial than whole bunch is in trial

//Mistakes
//considered all wecs instead of active wecs

//Challenges
//Data present check for Wec Reading
//Modelling for DGR
//Changes in WecDataDao which is very important
//Splitting of wec in WecDataDao becoz of 1000 limit
//Adding MA, GA and so which is the average
//Navigation for one jsp to other jsp

//Learn
//Corner Cases
//1. Data Present check at each level(Customer, State and Site) and using total for data present
//2. Convert String Date to check commission date comparison to current date
//3. Sorting by State and Site using Comparator
//JSP conversion
//1. Input parameter
//2. Output with all corner cases

//Improvement
//1. Comment to understand semantic/intent of the code
public class DGRByDayRP extends Action{
	
	private static Logger logger = Logger.getLogger(DGRByDayRP.class);
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			logger.debug("enter");
//			String customerId = "0905000002";
			String customerId = request.getParameter("id");
			
//			String date = "09/02/2016";
			String reportDate = request.getParameter("rd");
//			String toDate = "29/02/2016";

			String reportDateOracleDateFormat = DateUtility.convertDateFormats(reportDate, "dd/MM/yyyy", "dd-MMM-yyyy");
//			String toDateOracleDateFormat = DateUtility.convertDateFormats(toDate, "dd/MM/yyyy", "dd-MMM-yyyy");
			List<Parameter> parameters = new ArrayList<Parameter>();
			parameters.add(Parameter.GENERATION);
			parameters.add(Parameter.OPERATING_HOUR);
			parameters.add(Parameter.LULL_HOUR);
			parameters.add(Parameter.CF);
			parameters.add(Parameter.GA);
			parameters.add(Parameter.MA);

			ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
			CustomerDGR customerDGR = new CustomerDGR(customer, reportDateOracleDateFormat, reportDateOracleDateFormat, parameters).doStateDGR().doTotal().sortStateByName();
			
			logger.debug(String.format("Customer(%s) '%s' DGR for the date %s: %s", customerDGR.getTrial(), customer.getName(), reportDateOracleDateFormat, customerDGR.getTotal()));
			/*for(StateCustomerDGR stateDGR: customerDGR.getStatesTotal()){
				logger.debug(String.format("State %s, Trial %s, Total %s", stateDGR.getState().getName(), stateDGR.getTrial(), stateDGR.getTotal()));
				
				for(SiteStateCustomerDGR siteDGR: stateDGR.getSitesTotal()){
					logger.debug(String.format("Site %s, Trail %s, Total %s", siteDGR.getSite().getName(), siteDGR.getTrial(), siteDGR.getTotal()));
				}
			}*/
			
			request.setAttribute("diff", -1);
			request.setAttribute("customerdgr", customerDGR);
			request.setAttribute("reportDate", reportDate);
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		return mapping.findForward("success");
	}

}
