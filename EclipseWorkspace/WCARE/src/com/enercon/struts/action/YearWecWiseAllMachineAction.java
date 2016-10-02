package com.enercon.struts.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.WecDateWise;
import com.enercon.model.dgr.WecMonthWise;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.WecMasterService;

public class YearWecWiseAllMachineAction extends Action{
	
	private static Logger logger = Logger.getLogger(YearWecWiseAllMachineAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
		String stateId = request.getParameter("stateId");
		String siteId = request.getParameter("siteId");
		String customerId = request.getParameter("customerId");
		int fiscalYear = Integer.parseInt(request.getParameter("fiscalYear"));
		
		FiscalYear fy = FiscalYear.valueOf(fiscalYear);
		IStateMasterVo state = StateMasterService.getInstance().get(stateId);
		ISiteMasterVo site = SiteMasterService.getInstance().get(siteId);
		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		
		List<IWecMasterVo> wecs = WecMasterService.getInstance().getDisplayActive(site, state, customer);
		logger.debug("Wec Size: " + wecs.size());
		String fromDate = fy.getFirstDate();
		String toDate = fy.getLastDate();
		
		logger.debug(String.format("fromdate: %s, todate: %s", fromDate, toDate));
		
		Set<Parameter> parameters = getWecParameter();
		List<WecMonthWise> wecMonthwises = new ArrayList<WecMonthWise>();
		
		WecParameterData parameterData = new WecParameterData();
		parameterData.setFromDate(fromDate);
		parameterData.setToDate(toDate);
		parameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		parameterData.setPublish(1);
		parameterData.setParameters(parameters);
		Map<IWecMasterVo, Map<YearMonth, IWecParameterVo>> wecWiseMonthWise = WecParameterEvaluator.getInstance().getWecWiseYearMonthWise(parameterData);
		
		for(IWecMasterVo wec: wecWiseMonthWise.keySet()){
			
			Map<YearMonth, IWecParameterVo> data = wecWiseMonthWise.get(wec);
			Map<YearMonth, IWecParameterVo> monthWiseData = new TreeMap<YearMonth, IWecParameterVo>();
			
			for(YearMonth month: data.keySet()){
				IWecParameterVo iWecParameterVo = data.get(month);
				monthWiseData.put(month, iWecParameterVo);
			}
			
			WecMonthWise wecMonthwise = new WecMonthWise(fromDate, toDate, wec, parameters);
			
			wecMonthwise.setDatesData(monthWiseData);
			wecMonthwise.populateTotal();
			wecMonthwises.add(wecMonthwise);
		}
		
		Collections.sort(wecMonthwises, WecMonthWise.BY_WEC_NAME);
		
		request.setAttribute("wecMonthwises", wecMonthwises);
		request.setAttribute("fiscalYear", fiscalYear);
		request.setAttribute("stateId", stateId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("customerId", customerId);
		request.setAttribute("firstDate", DateUtility.getJoda(fromDate).plusMonths(1).toString(DateTimeFormat.forPattern("dd/MM/yyyy")));
		logger.debug("left");
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
