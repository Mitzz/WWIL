package com.enercon.struts.form;

import org.apache.struts.action.ActionForm;

public class IredaFiscalYearWecWiseBasedOnStateReportForm extends ActionForm {

	private String stateId;
	private String iredaProjectNo;
	private Integer fiscalYear;
	
	public IredaFiscalYearWecWiseBasedOnStateReportForm() { }

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

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
}