package com.enercon.model.master;

public class LoginHistoryVo extends MasterVo {
	
	private String id;
	private String transactionId;
	private String userId;
	private String loginDateTime;
	private String logoutDateTime;
	private String ipAddress;
	private String host;
	
	private LoginHistoryVo(LoginHistoryVoBuilder builder) {
		super();
		this.id = builder.id;
		this.transactionId = builder.transactionId;
		this.userId = builder.userId;
		this.loginDateTime = builder.loginDateTime;
		this.logoutDateTime = builder.logoutDateTime;
		this.ipAddress = builder.ipAddress;
		this.host = builder.host;
		setCreatedBy(builder.getCreatedBy());
		setCreatedAt(builder.getCreatedAt());
		setModifiedBy(builder.getModifiedBy());
		setModifiedAt(builder.getModifiedAt());

	}

	@Override
	public String toString() {
		return "LoginHistoryVo [id=" + id + ", transactionId=" + transactionId
				+ ", userId=" + userId + ", loginDateTime=" + loginDateTime
				+ ", logoutDateTime=" + logoutDateTime + ", ipAddress="
				+ ipAddress + ", host=" + host + "]";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(String loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public String getLogoutDateTime() {
		return logoutDateTime;
	}
	public void setLogoutDateTime(String logoutDateTime) {
		this.logoutDateTime = logoutDateTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public static class LoginHistoryVoBuilder extends MasterVo {
	
		private String id;
		private String transactionId;
		private String userId;
		private String loginDateTime;
		private String logoutDateTime;
		private String ipAddress;
		private String host;
		
		public LoginHistoryVoBuilder(String id) {
			this.id = id;
		}

		public LoginHistoryVoBuilder transactionId(String transactionId) {
			this.transactionId = transactionId;
			return this;
		}

		public LoginHistoryVoBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public LoginHistoryVoBuilder loginDateTime(String loginDateTime) {
			this.loginDateTime = loginDateTime;
			return this;
		}

		public LoginHistoryVoBuilder logoutDateTime(String logoutDateTime) {
			this.logoutDateTime = logoutDateTime;
			return this;
		}

		public LoginHistoryVoBuilder ipAddress(String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}

		public LoginHistoryVoBuilder host(String host) {
			this.host = host;
			return this;
		}

		public LoginHistoryVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public LoginHistoryVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public LoginHistoryVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public LoginHistoryVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public LoginHistoryVo build() {
			LoginHistoryVo vo = new LoginHistoryVo(this);

			return vo;
		}		
	}

}
