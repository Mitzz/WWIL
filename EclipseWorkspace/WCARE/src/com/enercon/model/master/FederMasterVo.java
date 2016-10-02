package com.enercon.model.master;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.table.FederMFactorVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("federMasterVo")
public class FederMasterVo extends CommonFormVo {

	private String id;
	private String name;
	private String description;
	private String type;
	private String subType;
	private String workingStatus;
	private String siteId;
	private String capacity;
	private double multiFactor;
	private ISiteMasterVo site;

	private List<FederMFactorVo> federMfs = new ArrayList<FederMFactorVo>();
	
	public FederMasterVo() {
	}

	public List<FederMFactorVo> getFederMfs() {
		return federMfs;
	}

	public void setFederMfs(List<FederMFactorVo> federMfs) {
		this.federMfs = federMfs;
	}
	public FederMasterVo addFederMf(FederMFactorVo federMf){
		federMfs.add(federMf);
		return this;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public double getMultiFactor() {
		return multiFactor;
	}

	public void setMultiFactor(double multiFactor) {
		this.multiFactor = multiFactor;
	}

	public ISiteMasterVo getSite() {
		return site;
	}

	public void setSite(ISiteMasterVo site) {
		this.site = site;
		site.addFeder(this);
	}

	@Override
	public String toString() {
		return "FederMasterVo [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type + ", subType=" + subType + ", workingStatus=" + workingStatus + ", siteId=" + siteId + ", capacity=" + capacity + ", multiFactor=" + multiFactor + "]";
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setId(null);
		setName(null);
		setDescription(null);
		setType(null);
		setSubType(null);
		setWorkingStatus(null);
		setSiteId(null);
		setCapacity(null);		
	}
	
}
