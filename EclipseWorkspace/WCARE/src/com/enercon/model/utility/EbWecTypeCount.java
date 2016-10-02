package com.enercon.model.utility;

public class EbWecTypeCount {

	private int wecCount;
	private String wecType;
	private double capacity;
	
	public EbWecTypeCount(int wecCount, String wecType, double capacity) {
		this.wecCount = wecCount;
		this.wecType = wecType;
		this.capacity = capacity;
	}

	public int getWecCount() {
		return wecCount;
	}

	public String getWecType() {
		return wecType;
	}

	public double getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return "EbWecTypeCount [wecCount=" + wecCount + ", wecType=" + wecType + ", capacity=" + capacity + "]";
	}

	
}
