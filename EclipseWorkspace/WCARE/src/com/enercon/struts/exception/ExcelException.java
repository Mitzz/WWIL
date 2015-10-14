package com.enercon.struts.exception;

public class ExcelException extends Exception {

	private String msg;
	
	public ExcelException() {}
	
	public ExcelException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
