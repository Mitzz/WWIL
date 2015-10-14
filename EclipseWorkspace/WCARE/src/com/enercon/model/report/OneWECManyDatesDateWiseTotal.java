package com.enercon.model.report;

import java.util.List;

//2B
public class OneWECManyDatesDateWiseTotal extends ReportAbstract{
	private String wecId;
	private String fromDate;
	private String toDate;
	private List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList; 
	
	public OneWECManyDatesDateWiseTotal() {
	}

	public OneWECManyDatesDateWiseTotal(String wecId, String toDate, String fromDate, List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList,long generation, long operatingHour,
			long lullHours, double mavial, double gavial,
			double capacityFactor, double giavail, double miavail,
			int trialRun, long machineFault, long machineShutdown,
			long loadRestriction, long internalFault, long internalShutdown,
			long externalGridFault, long externalGridShutdown,
			long wecSpecialShutdown) {
		super(generation, operatingHour, lullHours, mavial, gavial, capacityFactor,
				giavail, miavail, trialRun, machineFault, machineShutdown,
				loadRestriction, internalFault, internalShutdown, externalGridFault,
				externalGridShutdown, wecSpecialShutdown);
		
		this.wecId = wecId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.oneWECOneDayInfoOrTotalList = oneWECOneDayInfoOrTotalList;
		
	}

	public String getWecId() {
		return wecId;
	}

	public void setWecId(String wecId) {
		this.wecId = wecId;
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

	public List<OneWECOneDayInfoOrTotal> getOneWECOneDayInfoOrTotalList() {
		return oneWECOneDayInfoOrTotalList;
	}

	public void setOneWECOneDayInfoOrTotalList(
			List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList) {
		this.oneWECOneDayInfoOrTotalList = oneWECOneDayInfoOrTotalList;
	}
	
	
	
	
	

}
