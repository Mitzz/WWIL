package com.enercon.model.report;

import java.util.Set;

public class ManyWECsManyDatesTotal extends ReportAbstract{

	private Set<String> wecIds ;
	private String fromDate;
	private String toDate;
	
	public ManyWECsManyDatesTotal() {
	}
	
	public ManyWECsManyDatesTotal(Set<String> wecIds,String fromDate, String toDate,long generation,
			long operatingHour,  long lullHours,
			double mavial, double gavial, double capacityFactor,
			double giavail, double miavail, int trialRun, long machineFault,
			long machineShutdown, long loadRestriction, long internalFault,
			long internalShutdown, long externalGridFault,
			long externalGridShutdown, long wecSpecialShutdown) {
		super(generation, operatingHour, 
				lullHours, mavial, gavial, capacityFactor, giavail, miavail, trialRun,
				machineFault, machineShutdown, loadRestriction, internalFault,
				internalShutdown, externalGridFault, externalGridShutdown,
				wecSpecialShutdown);
		this.wecIds = wecIds;
		this.toDate = toDate;
		this.fromDate = fromDate;
	}

	@Override
	public String toString() {
		return "\nManyWECsTotalManyDays [fromDate=" + fromDate + ", toDate="
				+ toDate + ", generation=" + generation
				+ ", operatingHour=" + operatingHour + ", lullHours=" + lullHours + ", mavial="
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

	
}
