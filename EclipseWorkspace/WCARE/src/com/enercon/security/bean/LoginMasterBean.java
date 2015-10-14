package com.enercon.security.bean;

public class LoginMasterBean {

    @Override
	public String toString() {
		return "LoginMasterBean [sUserId=" + sUserId + ", sName=" + sName
				+ ", sPassword=" + sPassword + ", sRoleID=" + sRoleID
				+ ", sRoleName=" + sRoleName + ", sLoginType=" + sLoginType
				+ ", sActive=" + sActive + ", sRemarks=" + sRemarks + "]";
	}

	public LoginMasterBean() {
    }

    private String sUserId;
    private String sName;
    private String sPassword;
    private String sRoleID;
    private String sRoleName;
    private String sLoginType;
    private String sActive;
    private String sRemarks;
    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }

    public String getsRemarks() {
        return sRemarks;
    }
    
    public void setsActive(String sActive) {
        this.sActive = sActive;
    }

    public String getsActive() {
        return sActive;
    }
    public void setSUserId(String sUserId) {
        this.sUserId = sUserId;
    }

    public String getSEmployeeId() {
        return sUserId;
    }

    public void setSName(String sName) {
        this.sName = sName;
    }

    public String getSName() {
        return sName;
    }
    
    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsPassword() {
        return sPassword;
    }
        
    public void setsRoleID(String sRoleID) {
        this.sRoleID = sRoleID;
    }

    public String getsRoleID() {
        return sRoleID;
    }
    
    public void setsRoleName(String sRoleName) {
        this.sRoleName = sRoleName;
    }

    public String getsRoleName() {
        return sRoleName;
    }
    
    public void setsLoginType(String sLoginType) {
        this.sLoginType = sLoginType;
    }

    public String getsLoginType() {
        return sLoginType;
    }
}
