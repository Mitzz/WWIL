package com.enercon.model.master;

public class PlantMasterVo {

	private String serialNo;
	private String locationNo;
	private String plantNo;
	private WecMasterVo wec;
	
	public PlantMasterVo(String serialNo){
		this.serialNo = serialNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
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

	public WecMasterVo getWec() {
		return wec;
	}

	public void setWec(WecMasterVo wec) {
		wec.setPlant(this);
		this.wec = wec;
	}

	
}
