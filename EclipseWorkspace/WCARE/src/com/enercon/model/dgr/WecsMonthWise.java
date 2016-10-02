package com.enercon.model.dgr;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.joda.time.YearMonth;

import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;
import com.enercon.model.parameter.wec.WecParameterData;
import com.enercon.model.summaryreport.WecParameterEvaluator;
import com.enercon.service.WecMasterService;

public class WecsMonthWise {
	
	private static Logger logger = Logger.getLogger(WecsMonthWise.class);

	//Input
	private String fromDate;
	private String toDate;
	private IStateMasterVo state;
	private ISiteMasterVo site;
	private ICustomerMasterVo customer;
	
	private Set<Parameter> wecParameters;

	// Output
	private Map<YearMonth, IWecParameterVo> monthData = new TreeMap<YearMonth, IWecParameterVo>();
	private IWecParameterVo total;
	private double capacity;
	private Set<IWecMasterVo> wecs;

	// Local
	private WecParameterData wecParameterData;

	public WecsMonthWise(String fromDate, String toDate, ICustomerMasterVo customer, ISiteMasterVo site, Set<Parameter> wecParameters) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.customer = customer;
		this.site = site;
		this.wecParameters = wecParameters;
	}

	public WecsMonthWise(String fromDate, String toDate, ICustomerMasterVo customer, IStateMasterVo state, Set<Parameter> wecParameters) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.customer = customer;
		this.state = state;
		this.wecParameters = wecParameters;
	}

	public WecsMonthWise setUpWecsBySiteCustomer() {
		wecs = new HashSet<IWecMasterVo>(WecMasterService.getInstance().getDisplayActive(site, site.getState(), customer));
		return this;
	}
	
	public WecsMonthWise setUpWecsByStateCustomer() {
		wecs = new HashSet<IWecMasterVo>(WecMasterService.getInstance().getDisplayActive(customer, state));
		return this;
		
	}

	private void setWecParameterData() {
		wecParameterData = new WecParameterData();
		wecParameterData.setDataCheck(true);
		wecParameterData.setFromDate(fromDate);
		wecParameterData.setToDate(toDate);
		wecParameterData.setParameters(wecParameters);
		wecParameterData.setWecs(wecs);
		wecParameterData.setPublish(1);
	}

	public WecsMonthWise populateData() throws SQLException, ParseException {
		setWecParameterData();
		WecParameterEvaluator evaluator = WecParameterEvaluator.getInstance();
		monthData.putAll(evaluator.getYearMonthWise(wecParameterData));
		return this;
	}
	
	public WecsMonthWise populateTotal(){
		total = WecParameterUtility.total(monthData.values());
		return this;
	}
	
	public WecsMonthWise populateCapacity(){
		WecMasterService service = WecMasterService.getInstance();
		capacity = service.getCapacity(new ArrayList<IWecMasterVo>(wecs));
		return this;
	}
	
	public double getCapacity(){
		return capacity;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Set<IWecMasterVo> getWecs() {
		return wecs;
	}

	public void setWec(Set<IWecMasterVo> wecs) {
		this.wecs = wecs;
	}

	public Map<YearMonth, IWecParameterVo> getMonthData() {
		return monthData;
	}

	public void setDatesData(Map<YearMonth, IWecParameterVo> monthData) {
		this.monthData = monthData;
	}
	
	public IWecParameterVo getTotal() {
		return total;
	}

	public void setTotal(IWecParameterVo datesDataTotal) {
		this.total = datesDataTotal;
	}
	
	public final WecParameterData getWecParameterData() {
		return wecParameterData;
	}

	public ISiteMasterVo getSite() {
		return site;
	}
	
	public IStateMasterVo getState() {
		return state;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}

	

	
}
