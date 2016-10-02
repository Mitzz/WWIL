package com.enercon.model;

import org.apache.struts.action.ActionForm;

public class CustomerLoginVo extends ActionForm{

	private String customerId;
	private String loginId;
	
	public CustomerLoginVo() {
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Override
	public String toString() {
		return "ManageCustomerLogin [customerId=" + customerId + ", loginId=" + loginId + "]";
	}
	
	
}
