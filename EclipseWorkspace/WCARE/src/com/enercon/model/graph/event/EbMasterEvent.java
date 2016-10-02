package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.IEbMasterVo;

public class EbMasterEvent extends CUDEvent{

	private IEbMasterVo eb;
	private List<String> columns;

	public IEbMasterVo getEb() {
		return eb;
	}

	public void setEb(IEbMasterVo eb) {
		this.eb = eb;
	}
	
	@Override
	public String toString() {
		return "EbMasterEvent [eb=" + eb + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
}
