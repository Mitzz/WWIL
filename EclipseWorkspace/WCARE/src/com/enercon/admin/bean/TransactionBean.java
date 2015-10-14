package com.enercon.admin.bean;

import java.io.Serializable;

public class TransactionBean implements Serializable {
    private String sTransactionId;
    private String sTransactionName;

    public TransactionBean() {
    }


    public void setsTransactionId(String sTransactionId) {
        this.sTransactionId = sTransactionId;
    }


    public String getsTransactionId() {
        return sTransactionId;
    }


    public void setSTransactionName(String sTransactionName) {
        this.sTransactionName = sTransactionName;
    }


    public String getSTransactionName() {
        return sTransactionName;
    }
}
