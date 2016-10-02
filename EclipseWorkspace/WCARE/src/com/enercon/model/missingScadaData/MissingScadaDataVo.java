package com.enercon.model.missingScadaData;

import com.enercon.model.graph.IWecMasterVo;

public class MissingScadaDataVo {

	private String date;
	private IWecMasterVo wec;
	private int noOfDaysMissing;
	private MissingScadaDataReason reason;
	
	public MissingScadaDataVo(String date, IWecMasterVo wec) {
		this.date = date;
		this.wec = wec;
	}

	public MissingScadaDataVo() {
	}

	public String getDate() {
		return date;
	}

	public MissingScadaDataVo setDate(String date) {
		this.date = date;
		return this;
	}

	public IWecMasterVo getWec() {
		return wec;
	}

	public MissingScadaDataVo setMasterVo(IWecMasterVo wec) {
		this.wec = wec;
		return this;
	}

	public int getNoOfDaysMissing() {
		return noOfDaysMissing;
	}

	public MissingScadaDataVo setNoOfDaysMissing(int noOfDaysMissing) {
		this.noOfDaysMissing = noOfDaysMissing;
		return this;
	}

	public MissingScadaDataReason getReason() {
		return reason;
	}

	public MissingScadaDataVo setReason(MissingScadaDataReason reason) {
		this.reason = reason;
		return this;
	}

	@Override
	public String toString() {
		return "MissingScadaDataVo [date=" + date + ", wec=" + String.format("(%s, %s) ", wec.getPlant().getLocationNo(), wec.getPlant().getPlantNo()) + wec.getName() 
				+ ", noOfDaysMissing=" + noOfDaysMissing + ", reason=" + reason
				+ "]";
	}

	
}
