package com.enercon.admin.bean;

import java.io.Serializable;

public class CreateRoleBean implements Serializable {
    private String sRoleId;
    private String sRoleName;
    private String sRoleDescription;
    /*private Date dCreatedDate;
    private Date dLastModifiedDate;
    private String sCreatedBy;
    private String sLastModifiedBy;
*/
    public CreateRoleBean() {
    }


    public void setsRoleId(String sRoleId) {
        this.sRoleId = sRoleId;
    }


    public String getsRoleId() {
        return sRoleId;
    }


    public void setSRoleName(String sRoleName) {
        this.sRoleName = sRoleName;
    }


    public String getSRoleName() {
        return sRoleName;
    }


    public void setSRoleDescription(String sRoleDescription) {
        this.sRoleDescription = sRoleDescription;
    }


    public String getSRoleDescription() {
        return sRoleDescription;
    }


    /*public void setDCreatedDate(Date dCreatedDate) {
        this.dCreatedDate = dCreatedDate;
    }


    public Date getDCreatedDate() {
        return dCreatedDate;
    }


    public void setDLastModifiedDate(Date dLastModifiedDate) {
        this.dLastModifiedDate = dLastModifiedDate;
    }


    public Date getDLastModifiedDate() {
        return dLastModifiedDate;
    }


    public void setSCreatedBy(String sCreatedBy) {
        this.sCreatedBy = sCreatedBy;
    }


    public String getSCreatedBy() {
        return sCreatedBy;
    }


    public void setSLastModifiedBy(String sLastModifiedBy) {
        this.sLastModifiedBy = sLastModifiedBy;
    }


    public String getSLastModifiedBy() {
        return sLastModifiedBy;
    }
*/
}
