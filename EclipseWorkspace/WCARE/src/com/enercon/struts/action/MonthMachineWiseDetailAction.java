package com.enercon.struts.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.enercon.global.utility.DateUtility;
import com.enercon.model.comparator.EbDgrComparator;
import com.enercon.model.dgr.EbDgr;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.eb.EbParameter;
import com.enercon.model.parameter.eb.EbParameterData;
import com.enercon.model.parameter.eb.IEbParameterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.Month;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.master.EbMasterService;
import com.enercon.service.parameter.EbParameterEvaluator;

public class MonthMachineWiseDetailAction extends Action{
	
	private static Logger logger = Logger.getLogger(MonthMachineWiseDetailAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String customerId = request.getParameter("customerId");
		String siteId = request.getParameter("siteId");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String stateId = request.getParameter("stateId");
		int month = Integer.parseInt(DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "MM"));
		int year = Integer.parseInt(DateUtility.convertDateFormats(fromDate, "dd-MMM-yyyy", "yyyy"));
//		String customerId = "0905000002";
//		String siteId = "1108000001";
//		String fromDate = "01/02/2016";
//		String toDate = "29/02/2016";
//		String stateId = "";
		
		String fromDateInOracleFormat = fromDate.toUpperCase();
		String previousFrom = DateUtility.getBackwardDateInStringWRTDays(fromDateInOracleFormat, "dd-MMM-yyyy", -1);
		
		String toDateInOracleFormat = toDate.toUpperCase();
		String toDateMutated = toDateInOracleFormat;
		
		
		
		request.setAttribute("dataPresent", false);
		
		//No Data Uploaded
//		if(DateUtility.compareGivenDateWithTodayInTermsOfDays(date, "dd/MM/yyyy") >= 0) {
//			request.setAttribute("previousDate", DateUtility.getBackwardDateInStringWRTDays(date, "dd/MM/yyyy", -1));
//			request.setAttribute("nextDate", DateUtility.getBackwardDateInStringWRTDays(date, "dd/MM/yyyy", +1));
//			request.setAttribute("customerId", customerId);
//			request.setAttribute("siteId", siteId);
//			request.setAttribute("stateId", stateId);
//			request.setAttribute("reportDate", date);
//			request.setAttribute("date", dateInOracleFormat);
//			return mapping.findForward("success");
//		}
		
		logger.debug(String.format("StateId:%s, SiteId: %s, CustomerId: %s", stateId, siteId, customerId));
//		logger.debug("Date: " + date);
		
		//Wec Parameters
		Set<Parameter> parameters = getWecParameter();
		
		//Eb Parameters
		Set<EbParameter> ebParameters = getEbParameter();
		
		//Getting Masters
		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		ISiteMasterVo site = SiteMasterService.getInstance().get(siteId);
		List<IEbMasterVo> ebs = EbMasterService.getInstance().getWecDisplayActive(customer, site);

		List<EbDgr> ebDgrs = new ArrayList<EbDgr>();
		
		Set<IWecMasterVo> allWecs = new HashSet<IWecMasterVo>();
		Set<IEbMasterVo> allEbs = new HashSet<IEbMasterVo>();
		
		for(IEbMasterVo eb: ebs){
			EbDgr ebDgr = null;
			
			ebDgr = new EbDgr(fromDate, toDate, customer, eb).populateDetails();
			allWecs.addAll(ebDgr.getWecs());
			allEbs.add(ebDgr.getEb());
			
			ebDgrs.add(ebDgr);
		}
		WecParameterData wecParameterdata = new WecParameterData();
		wecParameterdata.setFromDate(fromDate);
		wecParameterdata.setToDate(toDate);
		wecParameterdata.setWecs(allWecs);
		wecParameterdata.setDataCheck(true);
		wecParameterdata.setParameters(parameters);
		wecParameterdata.setPublish(1);
		
		EbParameterData ebParameterdata = new EbParameterData();
		ebParameterdata.setFromDate(fromDate);
		ebParameterdata.setToDate(toDate);
		ebParameterdata.setEbs(allEbs);
		ebParameterdata.setDataCheck(true);
		ebParameterdata.setParameters(ebParameters);
		
		Map<IWecMasterVo, IWecParameterVo> allWecsParameterData = null;
		Map<IEbMasterVo, IEbParameterVo> allEbsParameterData = null;
		
		try {
			allWecsParameterData = WecParameterEvaluator.getInstance().getWecWise(wecParameterdata);
			allEbsParameterData = EbParameterEvaluator.getInstance().getEbWise(ebParameterdata);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		for (EbDgr ebDgr : ebDgrs) {
			Map<IWecMasterVo, IWecParameterVo> dgrWecsParameterData = new HashMap<IWecMasterVo, IWecParameterVo>();
			for(IWecMasterVo dgrWec: ebDgr.getWecs()){
				dgrWecsParameterData.put(dgrWec, allWecsParameterData.get(dgrWec));
			}
			
			ebDgr.setWecsData(dgrWecsParameterData).setEbData(allEbsParameterData.get(ebDgr.getEb()));
		}
		
		Collections.sort(ebDgrs, EbDgrComparator.BY_EB_NAME);
		
		request.setAttribute("dataPresent", true);
		request.setAttribute("monthName",  Month.getFullName(Month.valueOf(month)));
		request.setAttribute("month",  month);
		request.setAttribute("year",  year);
		request.setAttribute("customerId", customerId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("stateId", stateId);
		request.setAttribute("firstDate", DateUtility.convertDateFormats(DateUtility.getFirstDateOfMonth(fromDateInOracleFormat), "dd-MMM-yyyy", "dd/MM/yyyy"));
		request.setAttribute("ebDgrs", ebDgrs);
		return mapping.findForward("success");
	}
	
	private Set<EbParameter> getEbParameter() {
		Set<EbParameter> ebParameters = new HashSet<EbParameter>();
		ebParameters.add(EbParameter.KWHEXPORT);
		ebParameters.add(EbParameter.KWHIMPORT);
		ebParameters.add(EbParameter.REMARK);
		
		return ebParameters;
	}

	private Set<Parameter> getWecParameter() {
		Set<Parameter> parameters = new HashSet<Parameter>();
		
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.MA);
		parameters.add(Parameter.REMARK);
		parameters.add(Parameter.PUBLISH);
		parameters.add(Parameter.OMNP);
		parameters.add(Parameter.E1F);
		parameters.add(Parameter.E2F);
		parameters.add(Parameter.E3F);
		parameters.add(Parameter.E1S);
		parameters.add(Parameter.E2S);
		parameters.add(Parameter.E3S);
		parameters.add(Parameter.FM);
		parameters.add(Parameter.EB_LOAD);
		parameters.add(Parameter.MF);
		parameters.add(Parameter.MS);
		parameters.add(Parameter.GIF);
		parameters.add(Parameter.GIS);
		
		return parameters;
	}
}
