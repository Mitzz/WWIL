package com.enercon.struts.form;

import org.apache.struts.action.ActionForm;

import com.enercon.global.utility.MethodClass;

public class IredaWecWiseStatewiseMonthlyReportForm extends ActionForm {

	private String stateId;
	private String iredaProjectNo;
	private Integer month;
	private Integer year;
	
	public IredaWecWiseStatewiseMonthlyReportForm() {
		//MethodClass.displayMethodClassName();
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

	@Override
	public String toString() {
		return "IredaWecWiseStatewiseMonthlyReportForm [stateId=" + stateId
				+ ", iredaProjectNo=" + iredaProjectNo + ", month=" + month
				+ ", year=" + year + "]";
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
	
}
