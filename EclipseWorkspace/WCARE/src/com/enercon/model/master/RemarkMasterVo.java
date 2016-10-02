package com.enercon.model.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;


import com.enercon.struts.form.CommonFormVo;

public class RemarkMasterVo extends CommonFormVo {

	private String id;
	private String description;
	private String type;
	private String wecType;
	private String errorNo;

	public RemarkMasterVo() {
	}

	public RemarkMasterVo(RemarkMasterBuilder builder){
		this.id = builder.id;
		this.description = builder.description;
		this.type = builder.type;
		this.wecType = builder.wecType;
		this.errorNo = builder.errorNo;
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWecType() {
		return wecType;
	}

	public void setWecType(String wecType) {
		this.wecType = wecType;
	}

	public String getErrorNo() {
		return errorNo;
	}

	public void setErrorNo(String errorNo) {
		this.errorNo = errorNo;
	}
	
	public static class RemarkMasterBuilder extends MasterVo{
		
		private String id;
		private String description;
		private String type;
		private String wecType;
		private String errorNo;
		
		
		public RemarkMasterBuilder id(String id) {
			this.id = id;
			return this;
		}
		public RemarkMasterBuilder description(String description) {
			this.description = description;
			return this;
		}
		public RemarkMasterBuilder type(String type) {
			this.type = type;
			return this;
		}
		public RemarkMasterBuilder wecType(String wecType) {
			this.wecType = wecType;
			return this;
		}
		public RemarkMasterBuilder errorNo(String errorNo) {
			this.errorNo = errorNo;
			return this;
		}
		
		public RemarkMasterBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public RemarkMasterBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public RemarkMasterBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public RemarkMasterBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public RemarkMasterVo build() {
			RemarkMasterVo vo = new RemarkMasterVo(this);

			return vo;
		}	
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setDescription(null);
		 setType(null);
		 setWecType(null);
		 setErrorNo(null);
		 
		
	}
}
