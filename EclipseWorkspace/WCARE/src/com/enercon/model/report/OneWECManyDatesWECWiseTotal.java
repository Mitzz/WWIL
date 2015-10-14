package com.enercon.model.report;

//2A
public class OneWECManyDatesWECWiseTotal extends ReportAbstract{

	private String wecId;
	private String fromDate;
	private String toDate;
	
	public OneWECManyDatesWECWiseTotal() {
	}

	public OneWECManyDatesWECWiseTotal(String wecId, String toDate, String fromDate, long generation, long operatingHour,
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
		
	}
	
	@Override
	public String toString() {
		return "OneWECManyDatesWECWiseTotal";
	}

	/*@Override
	public String toString() {
		return "\nOneWECManyDatesWECWiseTotal [wecId=" + wecId + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", generation="
				+ generation + ", operatingHour=" + operatingHour
				+ ", lullHours=" + lullHours + ", mavial=" + mavial
				+ ", gavial=" + gavial + ", capacityFactor=" + capacityFactor
				+ ", giavail=" + giavail + ", miavail=" + miavail
				+ ", trialRun=" + trialRun + ", machineFault=" + machineFault
				+ ", machineShutdown=" + machineShutdown + ", loadRestriction="
				+ loadRestriction + ", internalFault=" + internalFault
				+ ", internalShutdown=" + internalShutdown
				+ ", externalGridFault=" + externalGridFault
				+ ", externalGridShutdown=" + externalGridShutdown
				+ ", wecSpecialShutdown=" + wecSpecialShutdown + "]";
	}*/

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
	
	
	
	
}
