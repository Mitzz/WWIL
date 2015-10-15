package com.enercon.model.summaryreport;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.enercon.model.report.IWecParameterVo;

public class SummaryReport implements Comparable<SummaryReport>{

	private String stateName;
	private String siteName;
	private String customerName;
	private Set<String> wecIds = new HashSet<String>();
	private String fromDate;
	private String toDate;
	
	private Map<Year, Map<Month, IWecParameterVo>> yearWiseMonthlyWecParameterVo = null;
	private Map<FiscalYear, Map<Month, IWecParameterVo>> fiscalYearWiseMonthlyWecParameterVo = null;
	private Map<FiscalYear, IWecParameterVo> fiscalYearWiseWecParameterVo = null;
	
	private Set<Parameter> parameters = new LinkedHashSet<Parameter>();
	
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
		yearWiseMonthlyWecParameterVo = new ParameterEvaluator().getYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, parameters);
	}
	
	public SummaryReport fiscalYearDetails() throws ParseException, IOException{
		if(yearWiseMonthlyWecParameterVo == null){
			yearWiseMonthlyWecParameterVo = new ParameterEvaluator().getYearWiseMonthlyWecParameterVo(fromDate, toDate, wecIds, parameters);
		}
		fiscalYearWiseMonthlyWecParameterVo = new LinkedHashMap<FiscalYear, Map<Month,IWecParameterVo>>();
		for(Year year : yearWiseMonthlyWecParameterVo.keySet()){
			Map<Month, IWecParameterVo> months = yearWiseMonthlyWecParameterVo.get(year);
			
			for (Month month : months.keySet()) {
				
				FiscalYear fy = FiscalYear.fiscalYear(year, month);
				IWecParameterVo data = null;
				
				if(!fiscalYearWiseMonthlyWecParameterVo.containsKey(fy)){
					data = yearWiseMonthlyWecParameterVo.get(year).get(month);
					Map<Month, IWecParameterVo> fiscalMonthMap = new LinkedHashMap<Month, IWecParameterVo>();
					fiscalMonthMap.put(month, data);
					fiscalYearWiseMonthlyWecParameterVo.put(fy, fiscalMonthMap);
					
				} else {
					data = yearWiseMonthlyWecParameterVo.get(year).get(month);
					Map<Month, IWecParameterVo> fiscalMonthMap = fiscalYearWiseMonthlyWecParameterVo.get(fy);
					fiscalMonthMap.put(month, data);
					fiscalYearWiseMonthlyWecParameterVo.put(fy, fiscalMonthMap);
				}
			}
		}
		return this;
	}
	
	public SummaryReport fiscalYearTotal() throws ParseException, IOException{
		fiscalYearWiseWecParameterVo = new ParameterEvaluator().getFiscalYearWiseWecParameterVo(fromDate, toDate, wecIds, parameters);
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
	
	
	

//	@Override
//	public String toString() {
//		return "SummaryReport [stateName=" + stateName + ", siteName="
//				+ siteName + ", customerName=" + customerName + ", fromDate="
//				+ fromDate + ", toDate=" + toDate + ", \nyearsDetails="
//						+ yearsDetails.toString().replace("],", "],\n").replace("},", "},\n") + ", \nfiscalYearsDetails="
//				+ fiscalYearsDetails.toString().replace("],", "],\n").replace("},", "},\n") + "]";
//	}
	
public String getStateName() {
	return stateName;
}

@Override
public String toString() {
	return "SummaryReport [stateName=" + stateName + ", siteName=" + siteName
			+ ", customerName=" + customerName + "]";
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

public void setParameters(Set<Parameter> parameters) {
	this.parameters = parameters;
}

public Map<FiscalYear, IWecParameterVo> getFiscalYearsTotal() {
	return fiscalYearWiseWecParameterVo;
}

public void setFiscalYearsTotal(Map<FiscalYear, IWecParameterVo> fiscalYearsTotal) {
	this.fiscalYearWiseWecParameterVo = fiscalYearsTotal;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
	result = prime * result + ((siteName == null) ? 0 : siteName.hashCode());
	result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	SummaryReport other = (SummaryReport) obj;
	if (customerName == null) {
		if (other.customerName != null)
			return false;
	} else if (!customerName.equals(other.customerName))
		return false;
	if (stateName == null) {
		if (other.stateName != null)
			return false;
	} else if (!stateName.equals(other.stateName))
		return false;
	if (siteName == null) {
		if (other.siteName != null)
			return false;
	} else if (!siteName.equals(other.siteName))
		return false;
	
	return true;
}

public int compareTo(SummaryReport that) {
	if(this.customerName.compareTo(that.customerName) < 0) 	return -1;
	if(this.customerName.compareTo(that.customerName) > 0) 	return 1;
	if(this.stateName.compareTo(that.stateName) < 0) 		return -1;
	if(this.stateName.compareTo(that.stateName) > 0) 		return 1;
	if(this.siteName.compareTo(that.siteName) < 0) 			return -1;
	if(this.siteName.compareTo(that.siteName) > 0) 			return 1;
	
	return 0;
}


	
}

