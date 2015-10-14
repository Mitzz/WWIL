package com.enercon.model.master;

import java.util.ArrayList;
import java.util.List;

public class LoginMasterVo extends MasterVo {

	private String id;
	private String userId;
	private String password;
	private String loginType;
	private RoleMasterVo role;
	private String loginDescription;
	private String active;
	private String remarks;
	private String emailId;
	private String emailStatus;
	private List<CustomerMasterVo> customers = new ArrayList<CustomerMasterVo>();

	private LoginMasterVo(LoginMasterVoBuilder builder) {
		super();
		this.id = builder.id;
		this.userId = builder.userId;
		this.password = builder.password;
		this.loginType = builder.loginType;
		this.role = builder.role;
		this.loginDescription = builder.loginDescription;
		this.active = builder.active;
		this.remarks = builder.remarks;
		this.emailId = builder.emailId;
		this.emailStatus = builder.emailStatus;
		this.createdBy = builder.createdBy;
		this.createdAt = builder.createdAt;
		this.modifiedBy = builder.modifiedBy;
		this.modifiedAt = builder.modifiedAt;
		this.customers = builder.customers;
	}

	@Override
	public String toString() {
		return "LoginMasterVo [id=" + id + ", userId=" + userId + ", password="
				+ password + ", loginType=" + loginType + ", role=" + role
				+ ", loginDescription=" + loginDescription + ", active="
				+ active + ", remarks=" + remarks + ", emailId=" + emailId
				+ ", emailStatus=" + emailStatus + ", createdBy=" + createdBy
				+ ", createdAt=" + createdAt + ", modifiedBy=" + modifiedBy
				+ ", modifiedAt=" + modifiedAt + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public RoleMasterVo getRole() {
		return role;
	}

	public void setRole(RoleMasterVo role) {
		this.role = role;
	}

	public String getLoginDescription() {
		return loginDescription;
	}

	public void setLoginDescription(String loginDescription) {
		this.loginDescription = loginDescription;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public List<CustomerMasterVo> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerMasterVo> customers) {
		this.customers = customers;
	}

	public static class LoginMasterVoBuilder extends MasterVo {
		private String id;
		private String userId;
		private String password;
		private String loginType;
		private RoleMasterVo role;
		private String loginDescription;
		private String active;
		private String remarks;
		private String emailId;
		private String emailStatus;
		private List<CustomerMasterVo> customers = new ArrayList<CustomerMasterVo>();

		public LoginMasterVoBuilder(String id) {
			this.id = id;
		}
		
		public LoginMasterVoBuilder customers(List<CustomerMasterVo> customers) {
			this.customers = customers;
			return this;
		}

		public LoginMasterVoBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public LoginMasterVoBuilder password(String password) {
			this.password = password;
			return this;
		}

		public LoginMasterVoBuilder loginType(String loginType) {
			this.loginType = loginType;
			return this;
		}

		public LoginMasterVoBuilder role(RoleMasterVo role) {
			this.role = role;
			return this;
		}

		public LoginMasterVoBuilder loginDescription(String loginDescription) {
			this.loginDescription = loginDescription;
			return this;
		}

		public LoginMasterVoBuilder active(String active) {
			this.active = active;
			return this;
		}

		public LoginMasterVoBuilder remarks(String remarks) {
			this.remarks = remarks;
			return this;
		}

		public LoginMasterVoBuilder emailId(String emailId) {
			this.emailId = emailId;
			return this;
		}

		public LoginMasterVoBuilder emailStatus(String emailStatus) {
			this.emailStatus = emailStatus;
			return this;
		}

		public LoginMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public LoginMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public LoginMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public LoginMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public LoginMasterVo build() {
			LoginMasterVo vo = new LoginMasterVo(this);

			return vo;
		}
	}

	public void addCustomer(CustomerMasterVo customer){
		customers.add(customer);
	}
	
}