package com.enercon.ajax.ajaxVo;

import com.enercon.ajax.interfaces.AjaxVo;

public class SelectVo implements AjaxVo {

	private String textValue;
	private String optionValue;

	public SelectVo(String optionValue, String textValue) {
		this.textValue = textValue;
		this.optionValue = optionValue;
	}

	public SelectVo() {}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	@Override
	public String toString() {
		return "SelectVo [textValue=" + textValue + ", optionValue="
				+ optionValue + "]";
	}

}
