package com.enercon.struts.form;
import java.util.Arrays;

import org.apache.struts.action.ActionForm;
public class HistoricalDataReportForm extends ActionForm{
	private String[] wecSelected;
	private String toDate;
private String fromDate;
private String reportTypeSelection;


public HistoricalDataReportForm() {
	super();
	// TODO Auto-generated constructor stub
}

public HistoricalDataReportForm(String[] wecSelected, String toDate,
		String fromDate, String reportTypeSelection) {
	super();
	this.wecSelected = wecSelected;
	this.toDate = toDate;
	this.fromDate = fromDate;
	this.reportTypeSelection = reportTypeSelection;
}

@Override
public String toString() {
	return "HistoricalDataReportForm [wecSelected="
			+ Arrays.toString(wecSelected) + ", toDate=" + toDate
			+ ", fromDate=" + fromDate + ", reportTypeSelection="
			+ reportTypeSelection + "]";
}

public String[] getWecSelected() {
	return wecSelected;
}

public void setWecSelected(String[] wecSelected) {
	this.wecSelected = wecSelected;
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

public String getReportTypeSelection() {
	return reportTypeSelection;
}

public void setReportTypeSelection(String reportTypeSelection) {
	this.reportTypeSelection = reportTypeSelection;
}




}
