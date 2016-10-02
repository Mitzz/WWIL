package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.ISiteMasterVo;

public class SiteMasterEvent extends CUDEvent{

	private ISiteMasterVo site;
	private List<String> columns;
	
	public ISiteMasterVo getSite() {
		return site;
	}

	public void setSite(ISiteMasterVo site) {
		this.site = site;
	}
	
	@Override
	public String toString() {
		return "SieMasterEvent [site=" + site + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
}
