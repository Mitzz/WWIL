package com.enercon.model.report;

import java.util.List;
import java.util.Set;

//4B
public class ManyWECsManyDatesDateWiseTotal extends ReportAbstract{
	
	private Set<String> wecIds ;
	private String fromDate;
	private String toDate;
	private List<ManyWECsOneDateDateWiseTotal> manyWECsOneDayDateWiseTotalList;
	
	public ManyWECsManyDatesDateWiseTotal() {}
	
	public ManyWECsManyDatesDateWiseTotal(Set<String> wecIds,String fromDate, String toDate, List<ManyWECsOneDateDateWiseTotal> manyWECsOneDayDateWiseTotalList, long generation, 
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
		setWecIds(wecIds);
		setFromDate(fromDate);
		setToDate(toDate);
		setManyWECsOneDayDateWiseTotalList(manyWECsOneDayDateWiseTotalList);
	}

	@Override
	public String toString() {
		return "ManyWECsManyDaysDateWiseTotal [wecIds=" + wecIds
				+ ",\n fromDate=" + fromDate + ", toDate=" + toDate
				+ ",\n manyWECsOneDayDateWiseTotalList="
				+ manyWECsOneDayDateWiseTotalList + ", \ngeneration="
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

	public List<ManyWECsOneDateDateWiseTotal> getManyWECsOneDayDateWiseTotalList() {
		return manyWECsOneDayDateWiseTotalList;
	}

	public void setManyWECsOneDayDateWiseTotalList(
			List<ManyWECsOneDateDateWiseTotal> manyWECsOneDayDateWiseTotalList) {
		this.manyWECsOneDayDateWiseTotalList = manyWECsOneDayDateWiseTotalList;
	}
}
