package com.enercon.model.transfer;

import org.apache.struts.action.ActionForm;

import com.enercon.global.utility.DateUtility;

public class TransferWecVo extends ActionForm {

	private String fromCustomerId;
	private String fromEbId;
	private String fromWecId;
	private String toCustomerId;
	private String toEbId;
	private String toWecId;
	private String transferDate;
	private String transferRemark;

	public TransferWecVo() {}

	public String getFromCustomerId() {
		return fromCustomerId;
	}

	public void setFromCustomerId(String fromCustomerId) {
		this.fromCustomerId = fromCustomerId;
	}

	public String getFromEbId() {
		return fromEbId;
	}

	public void setFromEbId(String fromEbId) {
		this.fromEbId = fromEbId;
	}

	public String getFromWecId() {
		return fromWecId;
	}

	public void setFromWecId(String fromWecId) {
		this.fromWecId = fromWecId;
	}

	public String getToCustomerId() {
		return toCustomerId;
	}

	public void setToCustomerId(String toCustomerId) {
		this.toCustomerId = toCustomerId;
	}

	public String getToEbId() {
		return toEbId;
	}

	public void setToEbId(String toEbId) {
		this.toEbId = toEbId;
	}

	public String getToWecId() {
		return toWecId;
	}

	public void setToWecId(String toWecId) {
		this.toWecId = toWecId;
	}

	public String getTransferDate() {
		return transferDate;
	}
	
	public String getTransferDateOracleFormat() {
		return DateUtility.convertDateFormats(transferDate, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getTransferRemark() {
		return transferRemark;
	}

	public void setTransferRemark(String transferRemark) {
		this.transferRemark = transferRemark;
	}

	@Override
	public String toString() {
		return "TransferWecVo [fromCustomerId=" + fromCustomerId + ", fromEbId=" + fromEbId + ", fromWecId=" + fromWecId + ", toCustomerId=" + toCustomerId + ", toEbId=" + toEbId + ", toWecId=" + toWecId + ", transferDate=" + transferDate + ", transferRemark=" + transferRemark + "]";
	}

	
}
