package com.enercon.reports.pojo;

import java.util.List;

public class StateAreaWiseMGSAvailabilityVo {
	private List<AreaWiseMGSAvailabilityReportVo> oneAreaInfo;
	private double totalWecCountForState=0;
	private double totalMAForState=0;
	private double totalGAForState=0;
	private double totalSACountForState=0;
	
		
	public StateAreaWiseMGSAvailabilityVo(
			List<AreaWiseMGSAvailabilityReportVo> oneAreaInfo,
			double totalWecCountForState, double totalMAForState,
			double totalGAForState, double totalSACountForState) {
		super();
		this.oneAreaInfo = oneAreaInfo;
		this.totalWecCountForState = totalWecCountForState;
		this.totalMAForState = totalMAForState;
		this.totalGAForState = totalGAForState;
		this.totalSACountForState = totalSACountForState;
	}

	@Override
	public String toString() {
		return "StateAreaWiseMGSAvailabilityVo [oneAreaInfo=" + oneAreaInfo
				+ ", totalWecCountForState=" + totalWecCountForState
				+ ", totalMAForState=" + totalMAForState + ", totalGAForState="
				+ totalGAForState + ", totalSACountForState="
				+ totalSACountForState + "]";
	}

	public StateAreaWiseMGSAvailabilityVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public List<AreaWiseMGSAvailabilityReportVo> getOneAreaInfo() {
		return oneAreaInfo;
	}
	public void setOneAreaInfo(List<AreaWiseMGSAvailabilityReportVo> oneAreaInfo) {
		this.oneAreaInfo = oneAreaInfo;
	}
	public double getTotalWecCountForState() {
		return totalWecCountForState;
	}
	public void setTotalWecCountForState(double totalWecCountForState) {
		this.totalWecCountForState = totalWecCountForState;
	}
	public double getTotalMAForState() {
		return totalMAForState;
	}
	public void setTotalMAForState(double totalMAForState) {
		this.totalMAForState = totalMAForState;
	}
	public double getTotalGAForState() {
		return totalGAForState;
	}
	public void setTotalGAForState(double totalGAForState) {
		this.totalGAForState = totalGAForState;
	}
	public double getTotalSACountForState() {
		return totalSACountForState;
	}
	public void setTotalSACountForState(double totalSACountForState) {
		this.totalSACountForState = totalSACountForState;
	}
	
	
	
	
	

}
