package com.enercon.struts.form;

import org.apache.struts.action.ActionForm;

public class IredaOneDayWecWiseBasedOnStateReportForm extends ActionForm {

	private String stateId;
	private String iredaProjectNo;
	private String date;
	
	public IredaOneDayWecWiseBasedOnStateReportForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getIredaProjectNo() {
		return iredaProjectNo;
	}
	public void setIredaProjectNo(String iredaProjectNo) {
		this.iredaProjectNo = iredaProjectNo;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
