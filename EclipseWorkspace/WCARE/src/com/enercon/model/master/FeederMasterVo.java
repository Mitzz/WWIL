package com.enercon.model.master;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("feederMasterVo")
public class FeederMasterVo extends CommonFormVo {

	private String id;
	private String name;
	private SubstationMasterVo substation;
	private String substationId;

	public FeederMasterVo() {
	}

	public FeederMasterVo(FeederMasterVoBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
		
		//Important for bidirectional Link
		setSubstation(builder.substation);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubstationMasterVo getSubstation() {
		return substation;
	}

	public void setSubstation(SubstationMasterVo substation) {
		if(substation != null)
			substation.addFeeder(this);
		this.substation = substation;
	}

	public String getSubstationId() {
		return substationId;
	}

	public void setSubstationId(String substationId) {
		this.substationId = substationId;
	}

	public static class FeederMasterVoBuilder extends MasterVo {

		private String id;
		private String name;
		private SubstationMasterVo substation;

		public FeederMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}

		public FeederMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}

		public FeederMasterVoBuilder substation(SubstationMasterVo substation) {
			this.substation = substation;
			return this;
		}

		public FeederMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public FeederMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public FeederMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public FeederMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public FeederMasterVo build() {
			FeederMasterVo vo = new FeederMasterVo(this);

			return vo;
		}

	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setName(null);
		 setSubstationId(null);
		
	}

}
