package com.enercon.model.parameter.eb;

import java.util.Set;

import com.enercon.model.graph.IEbMasterVo;

public class EbParameterData {

	private Set<IEbMasterVo> ebs;
	private String fromDate;
	private String toDate;
	private Set<EbParameter> parameters;
	private boolean dataCheck;

	public EbParameterData() {
		super();
	}

	public Set<IEbMasterVo> getEbs() {
		return ebs;
	}

	public void setEbs(Set<IEbMasterVo> ebs) {
		this.ebs = ebs;
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

	public Set<EbParameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<EbParameter> parameters) {
		this.parameters = parameters;
	}

	public boolean isDataCheck() {
		return dataCheck;
	}

	public void setDataCheck(boolean dataCheck) {
		this.dataCheck = dataCheck;
	}

}
