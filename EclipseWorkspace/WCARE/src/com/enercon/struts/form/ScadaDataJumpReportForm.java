package com.enercon.struts.form;

import java.util.Arrays;

import org.apache.struts.action.ActionForm;

public class ScadaDataJumpReportForm extends ActionForm{
	private String[] wecSelected;
	private String fromDate;
	private String toDate;
	
	
	@Override
	public String toString() {
		return "ScadaDataJumpReportForm [wecSelected="
				+ Arrays.toString(wecSelected) + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}

	public String[] getWecSelected() {
		return wecSelected;
	}
	
	
	public void setWecSelected(String[] wecSelected) {
		this.wecSelected = wecSelected;
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
	
	

}
