package com.enercon.admin.bean;

import java.io.Serializable;

import java.util.Date;

public class RoleTransMappingBean implements Serializable {

    private Integer iRoleTransMappId;
    private Integer iRoleId;
    private Integer iTransactionId;
    private String sTransactionName;
    private Date dCreatedDate;
    private Date dLastModifiedDate;
    private String sCreatedBy;
    private String sLastModifiedBy;

    public RoleTransMappingBean() {
    }


    public void setIRoleTransMappId(Integer iRoleTransMappId) {
        this.iRoleTransMappId = iRoleTransMappId;
    }


    public Integer getIRoleTransMappId() {
        return iRoleTransMappId;
    }


    public void setIRoleId(Integer iRoleId) {
        this.iRoleId = iRoleId;
    }


    public Integer getIRoleId() {
        return iRoleId;
    }


    public void setITransactionId(Integer iTransactionId) {
        this.iTransactionId = iTransactionId;
    }


    public Integer getITransactionId() {
        return iTransactionId;
    }


    public void setSTransactionName(String sTransactionName) {
        this.sTransactionName = sTransactionName;
    }


    public String getSTransactionName() {
        return sTransactionName;
    }


    public void setDCreatedDate(Date dCreatedDate) {
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
}
