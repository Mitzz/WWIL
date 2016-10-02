package com.enercon.model.graph.event;

import java.util.List;

import com.enercon.model.graph.ICustomerMasterVo;

public class CustomerMasterEvent extends CUDEvent{

	private ICustomerMasterVo customer;
	private List<String> columns;

	public ICustomerMasterVo getCustomer() {
		return customer;
	}

	public void setCustomer(ICustomerMasterVo customer) {
		this.customer = customer;
	}
	
	@Override
	public String toString() {
		return "CustomerMasterEvent [customer=" + customer + ", isCreate()=" + isCreate() + ", isUpdate()=" + isUpdate() + ", isDelete()=" + isDelete() + "]";
	}
	
	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
}
