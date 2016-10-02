package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.IStateMasterVo;

public class StateMasterEvent extends CUDEvent{

	private IStateMasterVo state;
	private List<String> columns;

	public IStateMasterVo getState() {
		return state;
	}

	public void setState(IStateMasterVo state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "StateMasterEvent [state=" + state + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
}
