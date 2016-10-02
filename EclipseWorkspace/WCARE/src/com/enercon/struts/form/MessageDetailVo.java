package com.enercon.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

public class MessageDetailVo extends CommonFormVo {
	
	private static Logger logger = Logger.getLogger(MessageDetailVo.class);

	private static final long serialVersionUID = 3198806620841341326L;
	

	private String id;
	private String description;
	private String fromDate;
	private String toDate;
	
	public MessageDetailVo() {
//		logger.debug("Constructor");
	}
	
	private MessageDetailVo(MessageDetailVoBuilder builder) {
		super();
		this.id = builder.id;
		this.description = builder.description;
		this.fromDate = builder.fromDate;
		this.toDate = builder.toDate;
		this.createdBy = builder.createdBy;
		this.createdAt = builder.createdAt;
		this.modifiedBy = builder.modifiedBy;
		this.modifiedAt = builder.modifiedAt;

	}

	@Override
	public String toString() {
		return "MessageDetailVo [id=" + id + ", description=" + description
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public static class MessageDetailVoBuilder extends CommonFormVo {
	
		private String id;
		private String description;
		private String fromDate;
		private String toDate;
		
		public MessageDetailVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		
		public MessageDetailVoBuilder description(String description) {
			this.description = description;
			return this;
		}
		
		public MessageDetailVoBuilder fromDate(String fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		
		public MessageDetailVoBuilder toDate(String toDate) {
			this.toDate = toDate;
			return this;
		}

		public MessageDetailVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public MessageDetailVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public MessageDetailVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public MessageDetailVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public MessageDetailVo build() {
			MessageDetailVo vo = new MessageDetailVo(this);

			return vo;
		}
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setId("");
		setDescription("");
		setFromDate("");
		setToDate("");
	}
	
}

