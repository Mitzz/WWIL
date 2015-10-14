package com.enercon.global.utility;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class TimeFormatter extends SimpleTagSupport{
	private long minutes;
	
	public void doTag() throws JspException, IOException {
		getJspContext().getOut().write(getCustomFormat());
	}

	private String getCustomFormat() {
		return TimeUtility.convertMinutesToTimeStringFormat(minutes, ":");
	}

	public long getMinutes() {
		return minutes;
	}



	public void setMinutes(long minutes) {
		this.minutes = minutes;
	}
}


