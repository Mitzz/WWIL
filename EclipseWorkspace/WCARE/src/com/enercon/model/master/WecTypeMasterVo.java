package com.enercon.model.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.struts.form.CommonFormVo;

public class WecTypeMasterVo extends CommonFormVo {

	private String id;
	private String description;
	private double capacity;

	public WecTypeMasterVo() {
	}

	public WecTypeMasterVo(WecTypeMasterBuilder builder){
		this.id = builder.id;
		this.description = builder.description;
		this.capacity = builder.capacity;
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

	public double getCapacity() {
		return capacity;
	}

	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "WecTypeMaster [id=" + id + ", description=" + description + ", capacity=" + capacity + "]";
	}
	
	public static class WecTypeMasterBuilder extends MasterVo{
		
		private String id;
		private String description;
		private double capacity;
		
		public WecTypeMasterBuilder id(String id) {
			this.id = id;
			return this;
		}
		public WecTypeMasterBuilder description(String description) {
			this.description = description;
			return this;
		}
		public WecTypeMasterBuilder capacity(double capacity) {
			this.capacity = capacity;
			return this;
		}
		
		
		public WecTypeMasterBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public WecTypeMasterBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public WecTypeMasterBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public WecTypeMasterBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public WecTypeMasterVo build() {
			WecTypeMasterVo vo = new WecTypeMasterVo(this);

			return vo;
		}	
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setDescription(null);
		 setCapacity(0);
		
	}
}
