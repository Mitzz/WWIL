package com.enercon.model.summaryreport;

import java.io.IOException;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.model.parameter.wec.Parameter;

public class SummaryReport /*implements Comparable<SummaryReport>*/{

	private String customerName;
	private String stateName;
	private String areaName;
	private String siteName;
	private String wecType;
	
	private Set<String> wecIds = new HashSet<String>();
	private String fromDate;
	private String toDate;
	
	private Map<Year, Map<Month, IWecParameterVo>> yearWiseMonthlyWecParameterVo = null;
	private Map<FiscalYear, Map<Month, IWecParameterVo>> fiscalYearWiseMonthlyWecParameterVo = null;
	private Map<FiscalYear, IWecParameterVo> fiscalYearWiseWecParameterVo = null;
	
	private Set<Parameter> parameters = new LinkedHashSet<Parameter>();
	
	public final static Comparator<SummaryReport> CUSTOMER_WECTYPE = new Comparator<SummaryReport>(){

		public int compare(SummaryReport v, SummaryReport w) {
			
			if(v.customerName.compareTo(w.customerName) < 0) 	return -1;
			if(v.customerName.compareTo(w.customerName) > 0) 	return 1;
			if(v.wecType.compareTo(w.wecType) < 0) 		return -1;
			if(v.wecType.compareTo(w.wecType) > 0) 		return 1;
			
			return 0;
		}
		
	};
	
	public final static Comparator<SummaryReport> CUSTOMER_STATE = new Comparator<SummaryReport>(){

		public int compare(SummaryReport v, SummaryReport w) {
			
			if(v.customerName.compareTo(w.customerName) < 0) 	return -1;
			if(v.customerName.compareTo(w.customerName) > 0) 	return 1;
			if(v.stateName.compareTo(w.stateName) < 0) 		return -1;
			if(v.stateName.compareTo(w.stateName) > 0) 		return 1;
			
			return 0;
		}
		
	};
	
	public final static Comparator<SummaryReport> CUSTOMER_STATE_AREA = new Comparator<SummaryReport>(){

		public int compare(SummaryReport v, SummaryReport w) {
			
			if(v.customerName.compareTo(w.customerName) < 0) 	return -1;
			if(v.customerName.compareTo(w.customerName) > 0) 	return 1;
			if(v.stateName.compareTo(w.stateName) < 0) 		return -1;
			if(v.stateName.compareTo(w.stateName) > 0) 		return 1;
			if(v.areaName.compareTo(w.areaName) < 0) 			return -1;
			if(v.areaName.compareTo(w.areaName) > 0) 			return 1;
			
			return 0;
		}
		
	};
	
	public final static Comparator<SummaryReport> CUSTOMER_STATE_SITE = new Comparator<SummaryReport>(){

		public int compare(SummaryReport v, SummaryReport w) {
			
			if(v.customerName.compareTo(w.customerName) < 0) 	return -1;
			if(v.customerName.compareTo(w.customerName) > 0) 	return 1;
			if(v.stateName.compareTo(w.stateName) < 0) 		return -1;
			if(v.stateName.compareTo(w.stateName) > 0) 		return 1;
			if(v.siteName.compareTo(w.siteName) < 0) 			return -1;
			if(v.siteName.compareTo(w.siteName) > 0) 			return 1;
			
			return 0;
		}
		
	};
	
	public SummaryReport(String customerName, String stateName, String siteName, Set<String> wecIds, String fromDate, String toDate) {
		this.stateName = stateName;
		this.siteName = siteName;
		this.customerName = customerName;
		this.wecIds = wecIds;
		this.fromDate = fromDate;
		this.toDate = toDate;
		initializeDefaultParameter();
	}
	
	public SummaryReport(String customerName, String stateName, String siteName, Set<String> wecIds, String fromDate, String toDate, boolean isWindspeed) {
		this(customerName, stateName, siteName, wecIds, fromDate, toDate);
		if(isWindspeed){
			addWindspeedParameter();
		}
	}
	
	public SummaryReport() {
	}

	private SummaryReport addWindspeedParameter() {
		parameters.add(Parameter.WS);
		return this;
	}

	/*Should be called from only one constructor which is responsible for initializing the object*/
	private void initializeDefaultParameter() {
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.MA);
		parameters.add(Parameter.GA);
		parameters.add(Parameter.FM);
	}

	public void populateParameters() throws ParseException{
		yearWiseMonthlyWecParameterVo = WecParameterEvaluator.getInstance().getYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, parameters);
	}
	
	public SummaryReport fiscalYearDetails() throws ParseException, IOException{
		fiscalYearWiseMonthlyWecParameterVo = WecParameterEvaluator.getInstance().getFiscalYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, parameters);
		return this;
	}
	
	public SummaryReport fiscalYearTotal() throws ParseException, IOException{
		fiscalYearWiseWecParameterVo = WecParameterEvaluator.getInstance().getFiscalYearWiseWecParameterVo(fromDate, toDate, wecIds, parameters);
		return this;
	}

	/*public void populateGeneration() throws ParseException, SQLException{
		generations = new ParameterEvaluator().getGeneration(fromDate, toDate, wecIds);
	}
	
	public void populateOperatingHour() throws ParseException, SQLException{
		operatingHour = new ParameterEvaluator().getOperatingHour(fromDate, toDate, wecIds);
	}	
	
	public void populateMA() throws ParseException, SQLException{
		ma = new ParameterEvaluator().getMA(fromDate, toDate, wecIds);
	}*/
	
	
	

	public String getStateName() {
		return stateName;
	}
	
	
	
	@Override
	public String toString() {
		return "SummaryReport [customerName=" + customerName + ", stateName="
				+ stateName + ", areaName=" + areaName + ", siteName="
				+ siteName + ", wecType=" + wecType + ", wecIds=" + wecIds.size()
				+ "]";
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public String getSiteName() {
		return siteName;
	}
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public Set<String> getWecIds() {
		return wecIds;
	}
	
	public void setWecIds(Set<String> wecIds) {
		this.wecIds = wecIds;
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
	
	public Map<Year, Map<Month, IWecParameterVo>> getYearsDetails() {
		return yearWiseMonthlyWecParameterVo;
	}
	
	public void setYearsDetails(Map<Year, Map<Month, IWecParameterVo>> yearsDetails) {
		this.yearWiseMonthlyWecParameterVo = yearsDetails;
	}
	
	public Map<FiscalYear, Map<Month, IWecParameterVo>> getFiscalYearsDetails() {
		return fiscalYearWiseMonthlyWecParameterVo;
	}
	
	public void setFiscalYearsDetails(
			Map<FiscalYear, Map<Month, IWecParameterVo>> fiscalYearsDetails) {
		this.fiscalYearWiseMonthlyWecParameterVo = fiscalYearsDetails;
	}
	
	public Set<Parameter> getParameters() {
		return parameters;
	}
	
	public SummaryReport setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
		return this;
	}
	
	public Map<FiscalYear, IWecParameterVo> getFiscalYearsTotal() {
		return fiscalYearWiseWecParameterVo;
	}
	
	public void setFiscalYearsTotal(Map<FiscalYear, IWecParameterVo> fiscalYearsTotal) {
		this.fiscalYearWiseWecParameterVo = fiscalYearsTotal;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getWecType() {
		return wecType;
	}

	public void setWecType(String wecType) {
		this.wecType = wecType;
	}
	
}

