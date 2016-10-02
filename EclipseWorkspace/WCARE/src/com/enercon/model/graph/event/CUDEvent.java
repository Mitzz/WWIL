package com.enercon.model.graph.event;

public abstract class CUDEvent {

	private boolean create;
	private boolean update;
	private boolean delete;
	
	public boolean isCreate() {
		return create;
	}
	public void setCreate(boolean create) {
		this.create = create;
	}
	public boolean isUpdate() {
		return update;
	}
	public void setUpdate(boolean update) {
		this.update = update;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	
	
	
}
