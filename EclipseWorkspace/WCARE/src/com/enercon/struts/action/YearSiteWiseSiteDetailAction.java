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
import com.enercon.model.dgr.WecsMonthWise;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.summaryreport.FiscalYear;
import com.enercon.service.CustomerMasterService;
import com.enercon.service.SiteMasterService;
import com.enercon.service.StateMasterService;

public class YearSiteWiseSiteDetailAction extends Action{
	private static Logger logger = Logger.getLogger(YearSiteWiseSiteDetailAction.class);

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("Enter");
//		stateId=<c:out value="${ siteDetail.state.id }" />&siteId=<c:out value="${ siteDetail.site.id }" />&customerId=<c:out value="${ siteDetail.customer.id }"/>&fiscalYear=<c:out value="${ fiscalYear }" />" target="_blank"> ${ siteDetail.site.name }</a>
		
		String stateId = request.getParameter("stateId");
		String siteId = request.getParameter("siteId");
		String customerId = request.getParameter("customerId");
		int fiscalYear = Integer.parseInt(request.getParameter("fiscalYear"));
		
		FiscalYear fy = FiscalYear.valueOf(fiscalYear);
		ICustomerMasterVo customer = CustomerMasterService.getInstance().get(customerId);
		WecsMonthWise wecsMonthWise = null;
		
		if(siteId != null){
			ISiteMasterVo site = SiteMasterService.getInstance().get(siteId);
			wecsMonthWise = new WecsMonthWise(fy.getFirstDate(), fy.getLastDate(), customer, site, getWecParameter());
			wecsMonthWise.setUpWecsBySiteCustomer();
		} else {
			IStateMasterVo state = StateMasterService.getInstance().get(stateId);
			wecsMonthWise = new WecsMonthWise(fy.getFirstDate(), fy.getLastDate(), customer, state, getWecParameter());
			wecsMonthWise.setUpWecsByStateCustomer();
		}
		
		wecsMonthWise.populateData().populateTotal().populateCapacity();
		
		/*Map<YearMonth, IWecParameterVo> monthData = wecsMonthWise.getMonthData();
		
		for(YearMonth yearMonth: monthData.keySet()){
			IWecParameterVo iWecParameterVo = monthData.get(yearMonth);
			logger.debug(String.format("%s - %s", yearMonth, iWecParameterVo));
		}
		logger.debug(String.format("%s - %s", wecsMonthWise.getCapacity(), wecsMonthWise.getTotal()));*/
		
		request.setAttribute("wecsMonthWise", wecsMonthWise);
		request.setAttribute("customerId", customerId);
		request.setAttribute("fiscalYear", fiscalYear);
		request.setAttribute("stateId", stateId);
		request.setAttribute("siteId", siteId);
		request.setAttribute("type", "YR");
		request.setAttribute("rd", DateUtility.convertDateFormats(DateUtility.getBackwardDateInStringWRTMonths(fy.getFirstDate(), 1), "dd-MMM-yyyy", "dd/MM/yyyy"));
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
}
