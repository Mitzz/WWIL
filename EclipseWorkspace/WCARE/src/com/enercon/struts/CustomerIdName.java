package com.enercon.struts;

public class CustomerIdName {
	private String customerName;
	private String customerId;
	
	public CustomerIdName(String customerId, String customerName) {
		this.customerId = customerId;
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
