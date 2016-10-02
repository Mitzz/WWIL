package com.enercon.model.master;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("substationMasterVo")
public class SubstationMasterVo extends CommonFormVo implements Cloneable, Comparable<SubstationMasterVo> {

	private String id;
	private String name;
	private String owner;
	private String capacity;
	private String mva;
	private String highVoltage;
	private String lowVoltage;
	private int transformerCount;
	private String remark;
	
	private IAreaMasterVo area;
	private List<FeederMasterVo> feeders = new ArrayList<FeederMasterVo>();
	private String areaId;
	
	public SubstationMasterVo() {
		super();
	}

	public SubstationMasterVo(SubstationMasterVoBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.owner = builder.owner;
		this.capacity = builder.capacity;
		this.mva = builder.mva;
		this.highVoltage = builder.highVoltage;
		this.lowVoltage = builder.lowVoltage;
		this.transformerCount = builder.transformerCount;
		this.remark = builder.remark;
		this.area = builder.area;
		setCreatedBy(builder.getCreatedBy());
		setCreatedAt(builder.getCreatedAt());
		setModifiedBy(builder.getModifiedBy());
		setModifiedAt(builder.getModifiedAt());
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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getMva() {
		return mva;
	}

	public void setMva(String mva) {
		this.mva = mva;
	}

	public String getHighVoltage() {
		return highVoltage;
	}

	public void setHighVoltage(String highVoltage) {
		this.highVoltage = highVoltage;
	}

	public String getLowVoltage() {
		return lowVoltage;
	}

	public void setLowVoltage(String lowVoltage) {
		this.lowVoltage = lowVoltage;
	}

	public int getTransformerCount() {
		return transformerCount;
	}

	public void setTransformerCount(int transformerCount) {
		this.transformerCount = transformerCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IAreaMasterVo getArea() {
		return area;
	}

	public void setArea(IAreaMasterVo area) {
		if(area != null)
			area.addSubstation(this);
		this.area = area;
		
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public static class SubstationMasterVoBuilder extends MasterVo {

		private String id;
		private String name;
		private String owner;
		private String capacity;
		private String mva;
		private String highVoltage;
		private String lowVoltage;
		private int transformerCount;
		private String remark;
		private IAreaMasterVo area;

		public SubstationMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}

		public SubstationMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}

		public SubstationMasterVoBuilder owner(String owner) {
			this.owner = owner;
			return this;
		}

		public SubstationMasterVoBuilder capacity(String capacity) {
			this.capacity = capacity;
			return this;
		}

		public SubstationMasterVoBuilder mva(String mva) {
			this.mva = mva;
			return this;
		}

		public SubstationMasterVoBuilder highVoltage(String highVoltage) {
			this.highVoltage = highVoltage;
			return this;
		}

		public SubstationMasterVoBuilder lowVoltage(String lowVoltage) {
			this.lowVoltage = lowVoltage;
			return this;
		}

		public SubstationMasterVoBuilder transformerCount(int transformerCount) {
			this.transformerCount = transformerCount;
			return this;
		}

		public SubstationMasterVoBuilder remark(String remark) {
			this.remark = remark;
			return this;
		}

		public SubstationMasterVoBuilder area(IAreaMasterVo area) {
			this.area = area;
			return this;
		}

		public SubstationMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}

		public SubstationMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}

		public SubstationMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public SubstationMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public SubstationMasterVo build() {
			SubstationMasterVo vo = new SubstationMasterVo(this);
			return vo;
		}
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setId(null);
		setName(null);
		setOwner(null);
		setCapacity(null);
		setMva(null);
		setHighVoltage(null);
		setLowVoltage(null);
		setTransformerCount(0);
		setRemark(null);
		setArea(null);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public List<FeederMasterVo> getFeeders() {
		return feeders;
	}

	public void setFeeders(List<FeederMasterVo> feeders) {
		this.feeders = feeders;
	}
	
	public SubstationMasterVo addFeeder(FeederMasterVo feeder){
		feeders.add(feeder);
		return this;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubstationMasterVo other = (SubstationMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public static Set<String> getIds(List<SubstationMasterVo> substations) {
		Set<String> ids = new HashSet<String>();
		
		for(SubstationMasterVo substation: substations){
			ids.add(substation.getId());
		}
		
		return ids;
	}

	public int compareTo(SubstationMasterVo that) {
		if(this.id.compareTo(that.id) < 0) return -1;
		if(this.id.compareTo(that.id) > 0) return +1;
		return 0;
	}
	
	
}
