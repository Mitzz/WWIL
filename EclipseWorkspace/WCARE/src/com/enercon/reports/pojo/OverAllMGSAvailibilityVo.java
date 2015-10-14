package com.enercon.reports.pojo;

import java.util.List;

public class OverAllMGSAvailibilityVo {

	private List<StateAreaWiseMGSAvailabilityVo> oneStateInfo;
	private double overAllWecCount=0;
	private double overAllMA=0;
	private double overAllGA=0;
	private double overAllSA=0;
	public OverAllMGSAvailibilityVo() {
		super();
	}
	public OverAllMGSAvailibilityVo(
			List<StateAreaWiseMGSAvailabilityVo> oneStateInfo,
			double overAllWecCount, double overAllMA, double overAllGA,
			double overAllSA) {
		super();
		this.oneStateInfo = oneStateInfo;
		this.overAllWecCount = overAllWecCount;
		this.overAllMA = overAllMA;
		this.overAllGA = overAllGA;
		this.overAllSA = overAllSA;
	}
	public List<StateAreaWiseMGSAvailabilityVo> getOneStateInfo() {
		return oneStateInfo;
	}
	public void setOneStateInfo(List<StateAreaWiseMGSAvailabilityVo> oneStateInfo) {
		this.oneStateInfo = oneStateInfo;
	}
	public double getOverAllWecCount() {
		return overAllWecCount;
	}
	public void setOverAllWecCount(double overAllWecCount) {
		this.overAllWecCount = overAllWecCount;
	}
	public double getOverAllMA() {
		return overAllMA;
	}
	public void setOverAllMA(double overAllMA) {
		this.overAllMA = overAllMA;
	}
	public double getOverAllGA() {
		return overAllGA;
	}
	public void setOverAllGA(double overAllGA) {
		this.overAllGA = overAllGA;
	}
	public double getOverAllSA() {
		return overAllSA;
	}
	public void setOverAllSA(double overAllSA) {
		this.overAllSA = overAllSA;
	}
	@Override
	public String toString() {
		return "OverAllMGSAvailibility [oneStateInfo=" + oneStateInfo
				+ ", overAllWecCount=" + overAllWecCount + ", overAllMA="
				+ overAllMA + ", overAllGA=" + overAllGA + ", overAllSA="
				+ overAllSA + "]";
	}
	
	
}
