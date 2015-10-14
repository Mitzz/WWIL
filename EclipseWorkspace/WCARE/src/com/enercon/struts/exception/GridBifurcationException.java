package com.enercon.struts.exception;

public class GridBifurcationException extends Exception {

	private String message;
	private int recordNo;
	
	public GridBifurcationException(String message, int recordNo) {
		super();
		this.setRecordNo(recordNo);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}
}
