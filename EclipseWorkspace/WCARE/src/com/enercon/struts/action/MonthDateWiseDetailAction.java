package com.enercon.struts.action;

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
import com.enercon.model.dgr.EbMonthView;
import com.enercon.model.dgr.EbMonthWecWise;
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
import com.enercon.model.utility.EbDate;
import com.enercon.model.utility.WecDate;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.master.EbMasterService;
import com.enercon.service.parameter.EbParameterEvaluator;

public class MonthDateWiseDetailAction extends Action{
	
	private static Logger logger = Logger.getLogger(MonthDateWiseDetailAction.class);

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
		
		//Getting Masters
		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		ISiteMasterVo site = SiteMasterService.getInstance().get(siteId);
		List<IEbMasterVo> ebs = EbMasterService.getInstance().getWecDisplayActive(customer, site);
		
		Set<IWecMasterVo> allWecs = new HashSet<IWecMasterVo>();
		Set<IEbMasterVo> allEbs = new HashSet<IEbMasterVo>();
		
		List<EbMonthWecWise> dgrs = new ArrayList<EbMonthWecWise>();
		for(IEbMasterVo eb: ebs){
			EbMonthWecWise dgr = new EbMonthWecWise();
			dgr.setCustomer(customer);
			dgr.setEb(eb);
			dgr.populateWecs();
			allWecs.addAll(dgr.getWecs());
			allEbs.add(dgr.getEb());
			dgrs.add(dgr);
		}
		
		WecParameterData wecParameterdata = new WecParameterData();
		wecParameterdata.setFromDate(fromDateInOracleFormat);
		wecParameterdata.setToDate(toDateInOracleFormat);
		wecParameterdata.setWecs(allWecs);
		wecParameterdata.setDataCheck(true);
		wecParameterdata.setParameters(getWecParameter());
		wecParameterdata.setPublish(1);
		
		EbParameterData ebParameterdata = new EbParameterData();
		ebParameterdata.setFromDate(fromDateInOracleFormat);
		ebParameterdata.setToDate(toDateInOracleFormat);
		ebParameterdata.setEbs(allEbs);
		ebParameterdata.setDataCheck(true);
		ebParameterdata.setParameters(getEbParameter());
		
		WecParameterEvaluator wecEvaluator = WecParameterEvaluator.getInstance();
		EbParameterEvaluator ebEvaluator = EbParameterEvaluator.getInstance();
		Map<WecDate, IWecParameterVo> dateWiseWecWiseWecParameterVo = wecEvaluator.getWecDateWise(wecParameterdata);
		Map<EbDate, IEbParameterVo> dateWiseEbWiseWecParameterVo = ebEvaluator.getEBDateWise(ebParameterdata);
		
		logger.debug("DGRs Size: " + dgrs.size());
		for(EbMonthWecWise o: dgrs){
			toDateMutated = toDateInOracleFormat;
			List<EbMonthView> viewLists = new ArrayList<EbMonthView>();
			for(; !previousFrom.equalsIgnoreCase(toDateMutated); toDateMutated = DateUtility.getPreviousDateFromGivenDateInString(toDateMutated)){
//				logger.debug(String.format("%s, %s", fromDateInOracleFormat, toDateMutated));
				Map<IWecMasterVo, IWecParameterVo> df = new HashMap<IWecMasterVo, IWecParameterVo>();
				for(IWecMasterVo wec: o.getWecs()){
					IWecParameterVo iWecParameterVo = dateWiseWecWiseWecParameterVo.get(new WecDate(wec, toDateMutated.toUpperCase()));
					df.put(wec, iWecParameterVo);
				}
				IEbParameterVo iEbParameterVo = dateWiseEbWiseWecParameterVo.get(new EbDate(o.getEb(), toDateMutated.toUpperCase()));
				EbMonthView view = new EbMonthView();
				view.setDate(toDateMutated);
				view.setEbVo(null);
				view.setWecsData(df);
				view.setEbVo(iEbParameterVo);
				
				viewLists.add(view);
				
			}
			
			o.setView(viewLists);
			o.populateEbDetails();
			o.sortView();
			logger.debug("Done");
		}
		
		Collections.sort(dgrs, EbMonthWecWise.BY_EB_NAME);
//		logger.debug("DGRs Size: " + dgrs.size());
		
//		for(EbMonthWecWise o: dgrs){
//			List<EbMonthView> view = o.getView();
//			
//			for(EbMonthView monthView: view){
//				String date = monthView.getDate();
//				Map<IWecMasterVo, IWecParameterVo> wecData = monthView.getWecsData();
//				for(IWecMasterVo w: wecData.keySet()){
//					IWecParameterVo iWecParameterVo = wecData.get(w);
//					
//					logger.debug(String.format("%s, %s, %s", date, w.getName(), iWecParameterVo));
//				}
//				
//			}
//		}
		request.setAttribute("dataPresent", true);
		request.setAttribute("monthName",  Month.getFullName(Month.valueOf(month)));
		request.setAttribute("month",  month);
		request.setAttribute("year",  year);
		request.setAttribute("customerId", customerId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("stateId", stateId);
		request.setAttribute("firstDate", DateUtility.convertDateFormats(DateUtility.getFirstDateOfMonth(fromDateInOracleFormat), "dd-MMM-yyyy", "dd/MM/yyyy"));
		
		request.setAttribute("ebsMonthWise", dgrs);
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
	
	private Set<EbParameter> getEbParameter() {
		Set<EbParameter> ebParameters = new HashSet<EbParameter>();
		ebParameters.add(EbParameter.KWHEXPORT);
		ebParameters.add(EbParameter.KWHIMPORT);
		ebParameters.add(EbParameter.REMARK);
		
		return ebParameters;
	}
	
}
