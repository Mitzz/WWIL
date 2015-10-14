package com.enercon.model.report;

//1A/1B
public class OneWECOneDayInfoOrTotal extends ReportAbstract {

	
	private String wecId;
	private String readingDate;
	private String remark;
	
	public String getWecId() {
		return wecId;
	}
	public void setWecId(String wecId) {
		this.wecId = wecId;
	}
	public String getReadingDate() {
		return readingDate;
	}
	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}
	public OneWECOneDayInfoOrTotal() {
		super();
	}
	
	public OneWECOneDayInfoOrTotal(String wecId, String readingDate, String remark, long generation, long operatingHour,
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
		
		setWecId(wecId);
		setReadingDate(readingDate);
		setRemark(remark);
	}
	
	@Override
	public String toString() {
		return "\nOneWECOneDayInfoOrTotal [wecId=" + wecId + ", readingDate="
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
