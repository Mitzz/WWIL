package com.enercon.global.utility;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class NumberFormatter extends SimpleTagSupport{
	private long number;
	
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(getCustomFormat());
	}

	private String getCustomFormat() {
		return NumberUtility.formatNumber(number);
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}


}



