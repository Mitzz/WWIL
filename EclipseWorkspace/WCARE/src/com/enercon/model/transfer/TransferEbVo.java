package com.enercon.model.transfer;

import org.apache.struts.action.ActionForm;

import com.enercon.global.utility.DateUtility;

public class TransferEbVo extends ActionForm {

	private String fromEbId;
	private String toEbId;
	private String date;
	private String remark;

	public TransferEbVo() {}

	public String getFromEbId() {
		return fromEbId;
	}

	public void setFromEbId(String fromEbId) {
		this.fromEbId = fromEbId;
	}

	public String getToEbId() {
		return toEbId;
	}

	public void setToEbId(String toEbId) {
		this.toEbId = toEbId;
	}

	public String getDate() {
		return date;
	}
	
	public String getDateInOracleFormat() {
		return DateUtility.convertDateFormats(date, "dd/MM/yyyy", "dd-MMM-yyyy");
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TransferEbVo [fromEbId=" + fromEbId + ", toEbId=" + toEbId + ", date=" + date + ", remark=" + remark + "]";
	}

}
