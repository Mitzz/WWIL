package com.enercon.model.table;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;

public class WecReadingVo extends CommonFormVo {
	
	private String id;
	private String readingDate;
	private String fromDate;
	private String toDate;
	private String mpId;
	private String wecId;
	private String ebId;

	public WecReadingVo() {
		
	}

	public WecReadingVo(WecReadingVoBuilder builder){
		
		this.id = builder.id;
		this.readingDate = builder.readingDate;
		this.fromDate = builder.fromDate;	
		this.toDate = builder.toDate;	
		this.mpId = builder.mpId;
		this.wecId = builder.wecId;
		this.ebId = builder.ebId;	
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
			
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEbId() {
		return ebId;
	}

	public void setEbId(String ebId) {
		this.ebId = ebId;
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

	public String getReadingDate() {
		return readingDate;
	}

	public void setReadingDate(String readingDate) {
		this.readingDate = readingDate;
	}

	public String getMpId() {
		return mpId;
	}

	public void setMpId(String mpId) {
		this.mpId = mpId;
	}

	public String getWecId() {
		return wecId;
	}

	public void setWecId(String wecId) {
		this.wecId = wecId;
	}

	public static class WecReadingVoBuilder extends MasterVo {
		
		private String id;
		private String readingDate;
		private String fromDate;
		private String toDate;
		private String mpId;
		private String wecId;
		private String ebId;
		
		
		public WecReadingVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public WecReadingVoBuilder readingDate(String readingDate) {
			this.readingDate = readingDate;
			return this;
		}
		public WecReadingVoBuilder fromDate(String fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		public WecReadingVoBuilder toDate(String toDate) {
			this.toDate = toDate;
			return this;
		}
		public WecReadingVoBuilder mpId(String mpId) {
			this.mpId = mpId;
			return this;
		}
		
		public WecReadingVoBuilder wecId(String wecId) {
			this.wecId = wecId;
			return this;
		}
		
		public WecReadingVoBuilder ebId(String ebId) {
			this.ebId = ebId;
			return this;
		}
		
		public WecReadingVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public WecReadingVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public WecReadingVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public WecReadingVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public WecReadingVo build() {
			WecReadingVo vo = new WecReadingVo(this);

			return vo;
		}	
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setEbId(null);
		 setReadingDate(null);
		 setFromDate(null);
		 setToDate(null);
		 setWecId(null);
		 setMpId(null);

	}

	
}

