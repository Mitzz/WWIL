package com.enercon.model.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.struts.form.CommonFormVo;

public class MpMasterVo extends CommonFormVo{

	private String id;
	private String desc;
	private String type;
	private String show;
	private String unit;
	private int seqNo;
	private String status;
	private String cumulative;
	private String readType;
	
	public MpMasterVo() {
		
	}
	
	public MpMasterVo(MpMasterVoBuilder builder){
		
		this.id = builder.id;
		this.desc = builder.desc;
		this.type = builder.type;
		this.show = builder.show;
		this.unit = builder.unit;
		this.seqNo = builder.seqNo;
		this.status = builder.status;
		this.cumulative = builder.cumulative;
		this.readType = builder.readType;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShow() {
		return show;
	}
	public void setShow(String show) {
		this.show = show;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(int seqNo) {
		this.seqNo = seqNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCumulative() {
		return cumulative;
	}
	public void setCumulative(String cumulative) {
		this.cumulative = cumulative;
	}
	public String getReadType() {
		return readType;
	}
	public void setReadType(String readType) {
		this.readType = readType;
	}
  
	public static class MpMasterVoBuilder extends MasterVo{
	
		private String id;
		private String desc;
		private String type;
		private String show;
		private String unit;
		private int seqNo;
		private String status;
		private String cumulative;
		private String readType;
		
		public MpMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public MpMasterVoBuilder desc(String desc) {
			this.desc = desc;
			return this;
		}
		public MpMasterVoBuilder type(String type) {
			this.type = type;
			return this;
		}
		public MpMasterVoBuilder show(String show) {
			this.show = show;
			return this;
		}
		public MpMasterVoBuilder unit(String unit) {
			this.unit = unit;
			return this;
		}
		public MpMasterVoBuilder seqNo(int seqNo) {
			this.seqNo = seqNo;
			return this;
		}
		public MpMasterVoBuilder status(String status) {
			this.status = status;
			return this;
		}
		public MpMasterVoBuilder cumulative(String cumulative) {
			this.cumulative = cumulative;
			return this;
		}
		public MpMasterVoBuilder readType(String readType) {
			this.readType = readType;
			return this;
		}
		public MpMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public MpMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public MpMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public MpMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public MpMasterVo build() {
			MpMasterVo vo = new MpMasterVo(this);

			return vo;
		}	
		
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setDesc(null);
		 setType(null);
		 setShow(null);
		 setUnit(null);
		 setSeqNo(0);
		 setStatus(null);
		 setCumulative(null);
		 setReadType(null);
		
	}
	

}
