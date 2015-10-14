package com.enercon.admin.bean;

import java.io.Serializable;

public class AdminBean implements Serializable {
    private String sAdminID;
    private String sAdminPassword;

    public AdminBean() {
    }

    public void setsAdminID(String sAdminID) {
        this.sAdminID = sAdminID;
    }


    public String getsAdminID() {
        return this.sAdminID;
    }
    
    public void setsAdminPassword(String sAdminPassword) {
        this.sAdminPassword = sAdminPassword;
    }


    public String getsAdminPassword() {
        return sAdminPassword;
    }
}
