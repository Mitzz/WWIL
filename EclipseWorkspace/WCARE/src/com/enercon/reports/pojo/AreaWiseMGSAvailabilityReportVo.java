package com.enercon.reports.pojo;

public class AreaWiseMGSAvailabilityReportVo {

	private String stateName;
	private String areaName;
	private double wecCount;
	private double machineAvailability ;
	private double gridAvailability ;
	private double subStationAvailability ;
	public AreaWiseMGSAvailabilityReportVo() {
		super();
		
	}
	public AreaWiseMGSAvailabilityReportVo(String stateName, String areaName,
			double wecCount, double machineAvailability,
			double gridAvailability, double subStationAvailability) {
		super();
		this.stateName = stateName;
		this.areaName = areaName;
		this.wecCount = wecCount;
		this.machineAvailability = machineAvailability;
		this.gridAvailability = gridAvailability;
		this.subStationAvailability = subStationAvailability;
	}
	
	public String getStateName() {
		return stateName;
	}
	
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public double getWecCount() {
		return wecCount;
	}
	public void setWecCount(double wecCount) {
		this.wecCount = wecCount;
	}
	public double getMachineAvailability() {
		return machineAvailability;
	}
	public void setMachineAvailability(double machineAvailability) {
		this.machineAvailability = machineAvailability;
	}
	public double getGridAvailability() {
		return gridAvailability;
	}
	public void setGridAvailability(double gridAvailability) {
		this.gridAvailability = gridAvailability;
	}
	public double getSubStationAvailability() {
		return subStationAvailability;
	}
	public void setSubStationAvailability(double subStationAvailability) {
		this.subStationAvailability = subStationAvailability;
	}
	@Override
	public String toString() {
		return "AreaWiseMGSAvailabilityReportVo [stateName=" + stateName
				+ ", areaName=" + areaName + ", wecCount=" + wecCount
				+ ", machineAvailability=" + machineAvailability
				+ ", gridAvailability=" + gridAvailability
				+ ", subStationAvailability=" + subStationAvailability + "]";
	}
	
	
	
	
}
