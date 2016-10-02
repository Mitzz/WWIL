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

	public PlantMasterVo setSerialNo(String serialNo) {
		this.serialNo = serialNo;
		return this;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public PlantMasterVo setLocationNo(String locationNo) {
		this.locationNo = locationNo;
		return this;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public PlantMasterVo setPlantNo(String plantNo) {
		this.plantNo = plantNo;
		return this;
	}

	public WecMasterVo getWec() {
		return wec;
	}

	public PlantMasterVo setWec(WecMasterVo wec) {
		wec.setPlant(this);
		this.wec = wec;
		return this;
	}

	@Override
	public String toString() {
		return "PlantMasterVo [locationNo=" + locationNo + ", plantNo="
				+ plantNo + "]";
	}
	
}
