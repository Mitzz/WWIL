package com.enercon.struts.action;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.WecDateWise;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.Year;
import com.enercon.service.WecMasterService;

public class WecDateWiseAction extends Action{
	
	private static Logger logger = Logger.getLogger(WecDateWiseAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String wecId = request.getParameter("wecId");
		
		int month = Integer.parseInt(request.getParameter("month"));
		int year = Integer.parseInt(request.getParameter("year"));
	   
		String fromDate = DateUtility.getFirstDateOfMonth(Month.valueOf(month), Year.valueOf(year));
		String toDate = DateUtility.getLastDateOfMonth(Month.valueOf(month), Year.valueOf(year));
		
		logger.debug(String.format("fromdate: %s, todate: %s", fromDate, toDate));
		
		Set<Parameter> parameters = getWecParameter();
		IWecMasterVo wec = WecMasterService.getInstance().get(wecId);
		
		WecDateWise wecDatewise = new WecDateWise(fromDate, toDate, wec, parameters);
		
		wecDatewise.populateData();
		wecDatewise.populateTotal();
		
	    logger.debug("wecId :" + wecId + " : month :" + month + "  : year :" + year );
	    logger.debug(wecDatewise.getDatesData());
	    logger.debug(wecDatewise.getTotal());
	   
	    request.setAttribute("wecDateWise", wecDatewise);
	    request.setAttribute("monthName",  Month.getFullName(Month.valueOf(month)));		
		request.setAttribute("year",  year);
		request.setAttribute("date",   fromDate);
		request.setAttribute("reportDate",  DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "dd/MM/yyyy"));
		
		return mapping.findForward("success");
	}
	
	private Set<Parameter> getWecParameter() {
		Set<Parameter> parameters = new HashSet<Parameter>();
		
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.MA);

		return parameters;
	}
		
}