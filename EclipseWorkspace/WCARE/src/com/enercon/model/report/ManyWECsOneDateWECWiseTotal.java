package com.enercon.model.report;

import java.util.List;
import java.util.Set;

//3A
public class ManyWECsOneDateWECWiseTotal extends ReportAbstract {
	
	private Set<String> wecIds;
	private String readingDate;
	private List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList;
	
	public Set<String> getWecIds() {
		return wecIds;
	}

	public void setWecIds(Set<String> wecIds) {
		this.wecIds = wecIds;
	}

	public String getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}
	
	public ManyWECsOneDateWECWiseTotal() {
	}

	public ManyWECsOneDateWECWiseTotal(Set<String> wecIds, String readingDate, List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList,long generation,
			long operatingHour, long lullHours, double mavial, double gavial,
			double capacityFactor, double giavail, double miavail,
			int trialRun, long machineFault, long machineShutdown,
			long loadRestriction, long internalFault, long internalShutdown,
			long externalGridFault, long externalGridShutdown,
			long wecSpecialShutdown) {
		super(generation, operatingHour, lullHours, mavial, gavial, capacityFactor,
				giavail, miavail, trialRun, machineFault, machineShutdown,
				loadRestriction, internalFault, internalShutdown, externalGridFault,
				externalGridShutdown, wecSpecialShutdown);
		this.wecIds = wecIds;
		this.readingDate = readingDate;
		this.setOneWECOneDayInfoOrTotalList(oneWECOneDayInfoOrTotalList);
	}

	@Override
	public String toString() {
		return "ManyWECsOneDayWECWiseInfoOrTotal [wecIds=" + wecIds
				+ ",\n oneWECOneDayInfoOrTotalList="
				+ getOneWECOneDayInfoOrTotalList() + ", \nreadingDate=" + readingDate
				+ ", generation=" + generation + ", operatingHour="
				+ operatingHour + ", lullHours=" + lullHours + ", mavial="
				+ mavial + ", gavial=" + gavial + ", capacityFactor="
				+ capacityFactor + ", giavail=" + giavail + ", miavail="
				+ miavail + ", trialRun=" + trialRun + ", machineFault="
				+ machineFault + ", machineShutdown=" + machineShutdown
				+ ", loadRestriction=" + loadRestriction + ", internalFault="
				+ internalFault + ", internalShutdown=" + internalShutdown
				+ ", externalGridFault=" + externalGridFault
				+ ", externalGridShutdown=" + externalGridShutdown
				+ ", wecSpecialShutdown=" + wecSpecialShutdown + "]";
	}

	public List<OneWECOneDayInfoOrTotal> getOneWECOneDayInfoOrTotalList() {
		return oneWECOneDayInfoOrTotalList;
	}

	public void setOneWECOneDayInfoOrTotalList(
			List<OneWECOneDayInfoOrTotal> oneWECOneDayInfoOrTotalList) {
		this.oneWECOneDayInfoOrTotalList = oneWECOneDayInfoOrTotalList;
	}

	
}

