package com.enercon.reports.pojo;

public class PESDataStorage {
	
	private String locationNo;
	private String plantNo;
	private String wecName;
	private String pesStatus;
	
	public PESDataStorage() {
	}

	public PESDataStorage(String wecName, String locationNo, String plantNo, 
			String pesStatus) {
		
		this.locationNo = locationNo;
		this.plantNo = plantNo;
		this.wecName = wecName;
		this.pesStatus = pesStatus;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getWecName() {
		return wecName;
	}

	public void setWecName(String wecName) {
		this.wecName = wecName;
	}

	public String getPesStatus() {
		return pesStatus;
	}

	public void setPesStatus(String pesStatus) {
		this.pesStatus = pesStatus;
	}
	
	
}
