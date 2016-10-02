package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.IWecMasterVo;

public class WecMasterEvent extends CUDEvent {
	
	private IWecMasterVo wec;
	private List<String> columns;

	public IWecMasterVo getWec() {
		return wec;
	}

	public void setWec(IWecMasterVo wec) {
		this.wec = wec;
	}

	@Override
	public String toString() {
		return "WecMasterEvent [wec=" + wec + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
}
