package com.enercon.admin.bean;
import java.io.Serializable;

public class RoleMappingBean implements Serializable {

    private String sEmployeeId;
    private String sUserName;
    private String sRoleId;
    private String sRoleName;
    private String sLoginRoleMasterId;
    private String sLoginMasterId;
    /*private Date dCreatedDate;
    private Date dLastModifiedDate;
    private String sCreatedBy;
    private String sLastModifiedBy;
	
	*/
    public RoleMappingBean() {
    }


    public void setsEmployeeId(String sEmployeeId) {
        this.sEmployeeId = sEmployeeId;
    }


    public String getsEmployeeId() {
        return sEmployeeId;
    }


    public void setsUserName(String sUserName) {
        this.sUserName = sUserName;
    }


    public String getsUserName() {
        return sUserName;
    }


    public void setsLoginMasterId(String sLoginMasterId) {
        this.sLoginMasterId = sLoginMasterId;
    }


    public String getsLoginMasterId() {
        return sLoginMasterId;
    }


    public void setsRoleId(String sRoleId) {
        this.sRoleId = sRoleId;
    }


    public String getsRoleId() {
        return sRoleId;
    }


    public void setsRoleName(String sRoleName) {
        this.sRoleName = sRoleName;
    }


    public String getsRoleName() {
        return sRoleName;
    }


    public void setsLoginRoleMasterId(String sLoginRoleMasterId) {
        this.sLoginRoleMasterId = sLoginRoleMasterId;
    }


    public String getsLoginRoleMasterId() {
        return sLoginRoleMasterId;
    }

/*
    public void setdCreatedDate(Date dCreatedDate) {
        this.dCreatedDate = dCreatedDate;
    }


    public Date getdCreatedDate() {
        return dCreatedDate;
    }


    public void setdLastModifiedDate(Date dLastModifiedDate) {
        this.dLastModifiedDate = dLastModifiedDate;
    }


    public Date getdLastModifiedDate() {
        return dLastModifiedDate;
    }


    public void setsCreatedBy(String sCreatedBy) {
        this.sCreatedBy = sCreatedBy;
    }


    public String getsCreatedBy() {
        return sCreatedBy;
    }


    public void setsLastModifiedBy(String sLastModifiedBy) {
        this.sLastModifiedBy = sLastModifiedBy;
    }


    public String getsLastModifiedBy() {
        return sLastModifiedBy;
    }*/
}
