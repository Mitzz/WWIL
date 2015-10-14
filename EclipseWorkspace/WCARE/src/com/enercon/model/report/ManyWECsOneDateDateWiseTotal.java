package com.enercon.model.report;

import java.util.Set;

//3B
public class ManyWECsOneDateDateWiseTotal extends ReportAbstract {
	
	private Set<String> wecIds ;
	private String readingDate;

	public ManyWECsOneDateDateWiseTotal() {
		super();
	}

	public ManyWECsOneDateDateWiseTotal(Set<String> wecIds, String readingDate,long generation, 
			long operatingHour,  
			long lullHours, double mavial, double gavial,
			double capacityFactor, double giavail, double miavail,
			int trialRun, long machineFault, long machineShutdown,
			long loadRestriction, long internalFault, long internalShutdown,
			long externalGridFault, long externalGridShutdown,
			long wecSpecialShutdown ) {
		super(generation, operatingHour, 
				lullHours, mavial, gavial, capacityFactor, giavail, miavail,
				trialRun, machineFault, machineShutdown, loadRestriction,
				internalFault, internalShutdown, externalGridFault,
				externalGridShutdown, wecSpecialShutdown);
		this.wecIds = wecIds;
		this.readingDate = readingDate;
	}

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

	@Override
	public String toString() {
		return "ManyWECsOneDayDateWiseTotal [readingDate="
				+ readingDate + ", generation=" + generation
				+ ", operatingHour=" + operatingHour + ", lullHours="
				+ lullHours + ", mavial=" + mavial + ", gavial=" + gavial
				+ ", capacityFactor=" + capacityFactor + ", giavail=" + giavail
				+ ", miavail=" + miavail + ", trialRun=" + trialRun
				+ ", machineFault=" + machineFault + ", machineShutdown="
				+ machineShutdown + ", loadRestriction=" + loadRestriction
				+ ", internalFault=" + internalFault + ", internalShutdown="
				+ internalShutdown + ", externalGridFault=" + externalGridFault
				+ ", externalGridShutdown=" + externalGridShutdown
				+ ", wecSpecialShutdown=" + wecSpecialShutdown + "]";
	}
}
