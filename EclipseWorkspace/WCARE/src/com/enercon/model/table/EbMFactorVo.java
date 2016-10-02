package com.enercon.model.table;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.EbMasterVo;
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("ebMFactorVo")
public class EbMFactorVo extends CommonFormVo {
	
	private String id;
	private String ebId;
	private String fromDate;
	private String toDate;
	private Double multiFactor;
	private String type;
	private String subType;
	private String capacity;
	
	private IEbMasterVo eb;
	
	public EbMFactorVo() {
		
	}

	public EbMFactorVo(EbMFactorBuilder builder){
		
		this.id = builder.id;
		this.ebId = builder.ebId;
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
		
		setEb(builder.eb);
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
	
	public IEbMasterVo getEb() {
		return eb;
	}

	public void setEb(IEbMasterVo eb) {
		eb.addEbMf(this);
		this.eb = eb;
	}
	
	public static class EbMFactorBuilder extends MasterVo {
		
		private String id;
		private String ebId;
		private String fromDate;
		private String toDate;
		private Double multiFactor;
		private String type;
		private String subType;
		private String capacity;
		
		private IEbMasterVo eb;
		
		public EbMFactorBuilder id(String id) {
			this.id = id;
			return this;
		}
		public EbMFactorBuilder ebId(String ebId) {
			this.ebId = ebId;
			return this;
		}
		public EbMFactorBuilder fromDate(String fromDate) {
			this.fromDate = fromDate;
			return this;
		}
		public EbMFactorBuilder toDate(String toDate) {
			this.toDate = toDate;
			return this;
		}
		public EbMFactorBuilder multiFactor(Double multiFactor) {
			this.multiFactor = multiFactor;
			return this;
		}
		public EbMFactorBuilder type(String type) {
			this.type = type;
			return this;
		}
		public EbMFactorBuilder subType(String subType) {
			this.subType = subType;
			return this;
		}
		public EbMFactorBuilder capacity(String capacity) {
			this.capacity = capacity;
			return this;
		}
		public EbMFactorBuilder eb(IEbMasterVo eb) {
			this.eb = eb;
			return this;
		}
		
		public EbMFactorBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public EbMFactorBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public EbMFactorBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public EbMFactorBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		
		public EbMFactorVo build() {
			EbMFactorVo vo = new EbMFactorVo(this);

			return vo;
		}	
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setEbId(null);
		 setFromDate(null);
		 setToDate(null);
		 setMultiFactor(0.0);
		 setType(null);
		 setSubType(null);
		 setCapacity(null);

	}

	
}
