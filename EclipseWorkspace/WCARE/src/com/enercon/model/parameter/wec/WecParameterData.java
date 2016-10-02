package com.enercon.model.parameter.wec;

import java.util.Set;

import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.Parameter;

public class WecParameterData{
	private String fromDate;
	private String toDate;
	private Set<IWecMasterVo> wecs;
	private Set<Parameter> wecParameters;
	private boolean dataCheck;
	private int publish = -1;
	
	public WecParameterData() {
	}

	public WecParameterData(String fromDate, String toDate, Set<IWecMasterVo> wecs, Set<Parameter> wecParameters, boolean dataCheck) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.wecs = wecs;
		this.wecParameters = wecParameters;
		this.dataCheck = dataCheck;
	}

	public String getFromDate() {
		return fromDate;
	}

	public WecParameterData setFromDate(String fromDate) {
		this.fromDate = fromDate;
		return this;
	}

	public String getToDate() {
		return toDate;
	}

	public WecParameterData setToDate(String toDate) {
		this.toDate = toDate;
		return this;
	}

	public Set<IWecMasterVo> getWecs() {
		return wecs;
	}

	public WecParameterData setWecs(Set<IWecMasterVo> wecs) {
		this.wecs = wecs;
		return this;
	}

	public Set<Parameter> getWecParameters() {
		return wecParameters;
	}

	public WecParameterData setParameters(Set<Parameter> wecParameters) {
		this.wecParameters = wecParameters;
		return this;
	}

	public boolean isDataCheck() {
		return dataCheck;
	}

	public WecParameterData setDataCheck(boolean dataCheck) {
		this.dataCheck = dataCheck;
		return this;
	}

	public int getPublish() {
		return publish;
	}

	public void setPublish(int publish) {
		this.publish = publish;
	}
	
	public String getPublishQuery(){
		return publish == -1 ? " " : "and N_publish in ('" + publish + "') ";
	}
	
}
