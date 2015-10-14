package com.enercon.model.missingScadaData;

import com.enercon.model.master.WecMasterVo;

public class MissingScadaDataVo {

	private String date;
	private WecMasterVo masterVo;
	private int noOfDaysMissing;
	
	public MissingScadaDataVo(String date, WecMasterVo masterVo) {
		this.date = date;
		this.masterVo = masterVo;
	}

	public MissingScadaDataVo() {
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public WecMasterVo getMasterVo() {
		return masterVo;
	}

	public void setMasterVo(WecMasterVo masterVo) {
		this.masterVo = masterVo;
	}

	public int getNoOfDaysMissing() {
		return noOfDaysMissing;
	}

	public void setNoOfDaysMissing(int noOfDaysMissing) {
		this.noOfDaysMissing = noOfDaysMissing;
	}

}
