package com.enercon.struts.form;

import org.apache.log4j.Logger;
import org.apache.struts.validator.ValidatorForm;

public class StdMessageForm extends ValidatorForm {

	private static final long serialVersionUID = -4944530040589702058L;
	private final static Logger logger = Logger.getLogger(StdMessageForm.class);
	
	private String id;
	private String messageHead;
	private String messageDescription;
	
	public StdMessageForm() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessageHead() {
		return messageHead;
	}

	public void setMessageHead(String messageHead) {
		this.messageHead = messageHead;
	}

	public String getMessageDescription() {
		return messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	
	@Override
	public String toString() {
		return "StdMessageForm [id=" + id + ", messageHead=" + messageHead
				+ ", messageDescription=" + messageDescription + "]";
	}

	public void reset(){
		setMessageHead("");
	}
}
