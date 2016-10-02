package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.YearMonth;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.WecMonthWise;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.Month;
import com.enercon.service.WecMasterService;

public class WecMonthWiseAction  extends Action{
	
	private static Logger logger = Logger.getLogger(WecMonthWiseAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
        String wecId = request.getParameter("wecId");
        IWecMasterVo wec = WecMasterService.getInstance().get(wecId);
		
        
		int fiscalYear = Integer.parseInt(request.getParameter("fiscalYear"));
		FiscalYear fy = FiscalYear.valueOf(fiscalYear);
		
		String fromDateOracleDateFormat = fy.getFirstDate();
		String toDateOracleDateFormat = fy.getLastDate();
		logger.debug("from date : " + fromDateOracleDateFormat + " : to date : "+ toDateOracleDateFormat);
		
		Set<Parameter> parameters = new HashSet<Parameter>();	
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.MA);
		
		WecMonthWise wecMonthWise = new WecMonthWise( fromDateOracleDateFormat, toDateOracleDateFormat, wec, parameters ); 
		
		wecMonthWise.populateData();
		wecMonthWise.populateTotal();
		
		logger.debug("wecMonthwise :: "+ wecMonthWise.getMonthData().keySet());
		logger.debug("wecMonthwise :: "+ wecMonthWise.getTotal());

		request.setAttribute("wecMonthWise", wecMonthWise);
		request.setAttribute("fiscalYear",  fiscalYear);
		request.setAttribute("reportDate",  DateUtility.convertDateFormats(fromDateOracleDateFormat, "dd-MMM-yyyy", "dd/MM/yyyy"));
		
		return mapping.findForward("success");
	}
	
}
