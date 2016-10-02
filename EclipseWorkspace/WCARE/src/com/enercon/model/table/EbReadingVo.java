package com.enercon.model.table;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;

public class EbReadingVo extends CommonFormVo {
	
	private String id;
	private String readingDate;
	private String fromDate;
	private String toDate;
	private String mpId;	
	private String ebId;

	public EbReadingVo() {
		
	}

	public EbReadingVo(EbReadingVoBuilder builder){
		
		this.id = builder.id;
		this.readingDate = builder.readingDate;
		this.fromDate = builder.fromDate;	
		this.toDate = builder.toDate;	
		this.mpId = builder.mpId;	
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

	
	public static class EbReadingVoBuilder extends MasterVo {
		
		private String id;
		private String readingDate;
		private String fromDate;
		private String toDate;
		private String mpId;	
		private String ebId;
		
		
		public EbReadingVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public EbReadingVoBuilder readingDate(String readingDate) {
			this.readingDate = readingDate;
			return this;
		}
		public EbReadingVoBuilder fromDate(String fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		public EbReadingVoBuilder toDate(String toDate) {
			this.toDate = toDate;
			return this;
		}
		public EbReadingVoBuilder mpId(String mpId) {
			this.mpId = mpId;
			return this;
		}
		
		
		public EbReadingVoBuilder ebId(String ebId) {
			this.ebId = ebId;
			return this;
		}
		
		public EbReadingVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public EbReadingVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public EbReadingVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public EbReadingVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public EbReadingVo build() {
			EbReadingVo vo = new EbReadingVo(this);

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
		 setMpId(null);

	}

	
}

