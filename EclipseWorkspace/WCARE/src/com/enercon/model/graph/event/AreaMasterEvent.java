package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.IAreaMasterVo;

public class AreaMasterEvent extends CUDEvent{

	private IAreaMasterVo area;
	private List<String> columns;

	public IAreaMasterVo getArea() {
		return area;
	}

	public void setArea(IAreaMasterVo area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "AreaMasterEvent [area=" + area + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	
}
