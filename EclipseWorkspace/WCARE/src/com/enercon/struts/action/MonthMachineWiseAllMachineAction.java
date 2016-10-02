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
import org.joda.time.format.DateTimeFormat;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.dgr.WecDateWise;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.model.summaryreport.Year;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;
import com.enercon.service.WecMasterService;

public class MonthMachineWiseAllMachineAction extends Action{
	
	private static Logger logger = Logger.getLogger(MonthMachineWiseAllMachineAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter");
//		onClick="location.href='<c:url value="monthMachineWiseAllMachine.do"/>?stateId=<c:out value="${ stateId }" />&siteId=<c:out value="${ siteId }" />&customerId=<c:out value="${ customerId }" />&month=<c:out value="${ month }" />&year=<c:out value="${ year }" />'" />
		String stateId = request.getParameter("stateId");
		String siteId = request.getParameter("siteId");
		String customerId = request.getParameter("customerId");
		Integer month = Integer.parseInt(request.getParameter("month"));
		Integer year = Integer.parseInt(request.getParameter("year"));
		
		IStateMasterVo state = StateMasterService.getInstance().get(stateId);
		ISiteMasterVo site = SiteMasterService.getInstance().get(siteId);
		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		
		List<IWecMasterVo> wecs = WecMasterService.getInstance().getDisplayActive(site, state, customer);
		logger.debug("Wec Size: " + wecs.size());
		String fromDate = DateUtility.getFirstDateOfMonth(Month.valueOf(month), Year.valueOf(year));
		String toDate = DateUtility.getLastDateOfMonth(Month.valueOf(month), Year.valueOf(year));
		
		logger.debug(String.format("fromdate: %s, todate: %s", fromDate, toDate));
		
		Set<Parameter> parameters = getWecParameter();
		List<WecDateWise> wecDatewises = new ArrayList<WecDateWise>();
		
		WecParameterData parameterData = new WecParameterData();
		parameterData.setFromDate(fromDate);
		parameterData.setToDate(toDate);
		parameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		parameterData.setPublish(1);
		parameterData.setParameters(parameters);
		Map<IWecMasterVo, Map<DateTime, IWecParameterVo>> wecWiseDateWise = WecParameterEvaluator.getInstance().getWecWiseDateWise(parameterData);
		
		for(IWecMasterVo wec: wecWiseDateWise.keySet()){
			
			Map<DateTime, IWecParameterVo> data = wecWiseDateWise.get(wec);
			Map<String, IWecParameterVo> dateWiseData = new TreeMap<String, IWecParameterVo>();
			
			for(DateTime date: data.keySet()){
				IWecParameterVo iWecParameterVo = data.get(date);
				dateWiseData.put(date.toString(DateTimeFormat.forPattern("dd-MMM-yyyy")), iWecParameterVo);
			}
			
			WecDateWise wecDatewise = new WecDateWise(fromDate, toDate, wec, parameters);
			
			wecDatewise.setDatesData(dateWiseData);
			wecDatewise.populateTotal();
			wecDatewises.add(wecDatewise);
		}
		
		Collections.sort(wecDatewises, WecDateWise.BY_WEC_NAME);
		
		request.setAttribute("wecDateWises", wecDatewises);
	    request.setAttribute("monthName",  Month.getFullName(Month.valueOf(month)));		
		request.setAttribute("year",  year);
		request.setAttribute("date",   fromDate);
		request.setAttribute("reportDate",  DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "dd/MM/yyyy"));
		
		request.setAttribute("fromDate", fromDate);
		request.setAttribute("toDate", toDate);
		request.setAttribute("customerId", customerId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("stateId", stateId);
		
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
