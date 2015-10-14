package com.enercon.model.report;

import java.util.Set;

public class ManyWECsOneDateTotal extends ReportAbstract{

	private Set<String> wecIds ;
	private String date;
	
	public Set<String> getWecIds() {
		return wecIds;
	}
	
	public void setWecIds(Set<String> wecIds) {
		this.wecIds = wecIds;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "\nManyWECsOneDateTotal [wecIds=" + wecIds + "\nwecIdsSize=" + wecIds.size() + ", date=" + date
				+ ", \ngeneration=" + generation + ", operatingHour="
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
}
