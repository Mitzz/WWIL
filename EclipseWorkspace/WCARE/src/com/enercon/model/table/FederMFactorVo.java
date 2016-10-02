package com.enercon.model.table;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.master.FederMasterVo;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;
@JsonFilter("federMFactorVo")
public class FederMFactorVo extends CommonFormVo {
	
	private String id;
	private String federId;
	private String fromDate;
	private String toDate;
	private Double multiFactor;
	private String type;
	private String subType;
	private String capacity;
	
	private FederMasterVo feder;
	
	public FederMFactorVo() {
		
	}

	public FederMFactorVo(FederMFactorVoBuilder builder){
		
		this.id = builder.id;
		this.federId = builder.federId;
		this.fromDate = builder.fromDate;
		this.toDate = builder.toDate;
		this.type = builder.type;
		this.subType = builder.subType;
		this.multiFactor = builder.multiFactor;
		this.capacity = builder.capacity;
		
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
		
		setFeder(builder.feder);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFederId() {
		return federId;
	}

	public void setFederId(String federId) {
		this.federId = federId;
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

	public Double getMultiFactor() {
		return multiFactor;
	}

	public void setMultiFactor(Double multiFactor) {
		this.multiFactor = multiFactor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	
	public FederMasterVo getFeder() {
		return feder;
	}

	public void setFeder(FederMasterVo feder) {
		feder.addFederMf(this);
		this.feder = feder;
	}
	
	public static class FederMFactorVoBuilder extends MasterVo {
		
		private String id;
		private String federId;
		private String fromDate;
		private String toDate;
		private Double multiFactor;
		private String type;
		private String subType;
		private String capacity;
		
		private FederMasterVo feder;
		
		public FederMFactorVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public FederMFactorVoBuilder federId(String federId) {
			this.federId = federId;
			return this;
		}
		public FederMFactorVoBuilder fromDate(String fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		public FederMFactorVoBuilder toDate(String toDate) {
			this.toDate = toDate;
			return this;
		}
		public FederMFactorVoBuilder multiFactor(Double multiFactor) {
			this.multiFactor = multiFactor;
			return this;
		}
		public FederMFactorVoBuilder type(String type) {
			this.type = type;
			return this;
		}
		public FederMFactorVoBuilder subType(String subType) {
			this.subType = subType;
			return this;
		}
		public FederMFactorVoBuilder capacity(String capacity) {
			this.capacity = capacity;
			return this;
		}
		
		public FederMFactorVoBuilder feder(FederMasterVo feder) {
			this.feder = feder;
			return this;
		}
		
		public FederMFactorVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public FederMFactorVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public FederMFactorVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public FederMFactorVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public FederMFactorVo build() {
			FederMFactorVo vo = new FederMFactorVo(this);

			return vo;
		}	
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setFederId(null);
		 setFromDate(null);
		 setToDate(null);
		 setMultiFactor(0.0);
		 setType(null);
		 setSubType(null);
		 setCapacity(null);

	}

	
}
