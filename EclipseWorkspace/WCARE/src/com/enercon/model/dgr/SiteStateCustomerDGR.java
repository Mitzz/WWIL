package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.WecMasterService;

public class SiteStateCustomerDGR {
	private static Logger logger = Logger.getLogger(SiteStateCustomerDGR.class);
	
	//Input
	private ISiteMasterVo site;
	private IStateMasterVo state;
	private ICustomerMasterVo customer;
	private String fromDate;
	private String toDate;
	private List<Parameter> parameters;
	
	//Output
	private IWecParameterVo total;
	private boolean trial;
	
	//Local
	private List<IWecMasterVo> wecs;
	private WecParameterData parameterData;
	
	public SiteStateCustomerDGR(ISiteMasterVo site, IStateMasterVo state, ICustomerMasterVo customer, String fromDate, String toDate, List<Parameter> parameters) {
		this.site = site;
		this.state = state;
		this.customer = customer;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.parameters = parameters;
		
		setUp();
	}

	private void setUp() {
		setUpWecs();
		setUpWecParameterData();
	}

	private void setUpWecParameterData() {
		parameterData = new WecParameterData();
		parameterData.setFromDate(fromDate);
		parameterData.setToDate(toDate);
		parameterData.setWecs(new HashSet<IWecMasterVo>(wecs));
		parameterData.setDataCheck(true);
		parameterData.setPublish(1);
		parameterData.setParameters(new HashSet<Parameter>(parameters));
	}

	private void setUpWecs() {
		wecs = WecMasterService.getInstance().getDisplayActive(site, state, customer);
	}

	public SiteStateCustomerDGR doTotal() throws SQLException, ParseException{
		total = WecParameterEvaluator.getInstance().getTotal(parameterData);
		return doTrial();
	}
	
	public SiteStateCustomerDGR doTrial(){
		trial = WecMasterService.getInstance().isTrial(wecs);
		return this;
	}
	
	public IWecParameterVo getTotal(){
		return total;
	}
	
	public ISiteMasterVo getSite(){
		return site;
	}
	
	public boolean getTrial(){
		return trial;
	}

	public IStateMasterVo getState() {
		return state;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}
}
