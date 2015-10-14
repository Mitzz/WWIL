package com.enercon.struts.form;

import java.util.Arrays;

import org.apache.struts.action.ActionForm;

public class AreaWiseMGSAvailabilityReportForm extends ActionForm{
	
	private String[] areaSelected;
	private String toDate;
	private String fromDate;
	
	public AreaWiseMGSAvailabilityReportForm() {
		super();
	
	}

	public AreaWiseMGSAvailabilityReportForm(String[] areaSelected,
			String toDate, String fromDate) {
		super();
		this.areaSelected = areaSelected;
		this.toDate = toDate;
		this.fromDate = fromDate;
	}

	public String[] getAreaSelected() {
		return areaSelected;
	}

	public void setAreaSelected(String[] areaSelected) {
		this.areaSelected = areaSelected;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	@Override
	public String toString() {
		return "AreaWiseMGSAvailabilityReportForm [areaSelected="
				+ Arrays.toString(areaSelected) + ", toDate=" + toDate
				+ ", fromDate=" + fromDate + "]";
	}
	
	

}
