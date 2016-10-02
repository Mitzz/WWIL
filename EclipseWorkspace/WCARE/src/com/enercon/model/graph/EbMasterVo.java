package com.enercon.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.event.PlantMasterEvent;
import com.enercon.model.master.MasterVo;
import com.enercon.model.table.EbMFactorVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("ebMasterVo")
public class EbMasterVo extends CommonFormVo implements IEbMasterVo, Serializable, Comparable<EbMasterVo>{
	
	private static final long serialVersionUID = 7818566592352094011L;
	private String id;
	private String name;
	private String workingStatus;
	private String description;
	private String type;
	private String subType;
	private int factor;
	private String capacity;
	private String transferDate;
	private String transferToEbId;
	private String remark;
	private String display;
	
	private String siteId; 
	private String federId;
	private String customerId;
	
	private IStateMasterVo state;
	private IAreaMasterVo area;
	private ISiteMasterVo site;
	
	private List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
	private List<ICustomerMasterVo> customers;
	private List<IPlantMasterVo> plants;
	private boolean plantStale;
	private boolean customerStale;
	
	private List<EbMFactorVo> ebMfs = new ArrayList<EbMFactorVo>();
	
	public EbMasterVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public EbMasterVo() {
	}
	
	
	public ISiteMasterVo getSite() {
		return site;
	}

	
	public IEbMasterVo setSite(ISiteMasterVo site) {
		this.site = site;
		this.site.addEb(this);
		return this;
	}
	
	
	public IAreaMasterVo getArea() {
		if(area == null)
			area = MasterUtility.getAreaBySite(getSite());
		return area;
	}

	
	public IStateMasterVo getState() {
		if(state == null)
			state = MasterUtility.getStateByArea(getArea());
		return state;
	}
	
	
	public List<IWecMasterVo> getWecs() {
		return wecs;
	}

	
	public IEbMasterVo addWec(IWecMasterVo wec) {
		wecs.add(wec);
		return this;
	}

	
	public List<IPlantMasterVo> getPlants() {
		if(plants == null || plantStale){
			plants =  MasterUtility.getPlantsByWecs(getWecs());
			plantStale = false;
		}
		return plants;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EbMasterVo other = (EbMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getWorkingStatus() {
		return workingStatus;
	}

	public void setWorkingStatus(String workingStatus) {
		this.workingStatus = workingStatus;
	}

	public List<ICustomerMasterVo> getCustomers() {
		if(customers == null || customerStale){
			customers = MasterUtility.getCustomersByWecs(getWecs());
			customerStale = false;
		}
		return customers;
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

	public int compareTo(EbMasterVo that) {
		if(this.id.compareTo(that.getId()) < 0) return -1;
		if(this.id.compareTo(that.getId()) > 0) return +1;
		return 0;
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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public int getFactor() {
		return factor;
	}

	public void setFactor(int factor) {
		this.factor = factor;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(String transferDate) {
		this.transferDate = transferDate;
	}

	public String getTransferToEbId() {
		return transferToEbId;
	}

	public void setTransferToEbId(String transferToEbId) {
		this.transferToEbId = transferToEbId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getFederId() {
		return federId;
	}

	public void setFederId(String federId) {
		this.federId = federId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public List<EbMFactorVo> getEbMfs() {
		return ebMfs;
	}

	public void setEbMfs(List<EbMFactorVo> ebMfs) {
		this.ebMfs = ebMfs;
	}
	public IEbMasterVo addEbMf(EbMFactorVo ebmf){
		ebMfs.add(ebmf);
		return this;
	}
	
	public EbMasterVo(EbMasterVoBuilder builder){
		
		this.id = builder.id;
		this.name = builder.name;
		this.workingStatus = builder.workingStatus;
		this.description = builder.description;
		
		this.siteId = builder.siteId;
		this.federId = builder.federId;
		this.customerId = builder.customerId;
		
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
		
	}
	
	public static class EbMasterVoBuilder extends MasterVo{
		
		private String id;
		private String name;
		private String workingStatus;
		private String description;
		
		private String siteId; 
		private String federId;
		private String customerId;
		
		public EbMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public EbMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}
		public EbMasterVoBuilder workingStatus(String workingStatus) {
			this.workingStatus = workingStatus;
			return this;
		}
		public EbMasterVoBuilder description(String description) {
			this.description = description;
			return this;
		}
		public EbMasterVoBuilder siteId(String siteId) {
			this.siteId = siteId;
			return this;
		}
		public EbMasterVoBuilder federId(String federId) {
			this.federId = federId;
			return this;
		}
		public EbMasterVoBuilder customerId(String customerId) {
			this.customerId = customerId;
			return this;
		}
		
		public EbMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}
		public EbMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}
		public EbMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public EbMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}
		public EbMasterVo build() {
			EbMasterVo vo = new EbMasterVo(this);
			return vo;
		}
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setName(null);
		 setWorkingStatus(null);
		 setDescription(null);
		 setSiteId(null);
		 setFederId(null);
		 setCustomerId(null);
		 
	}

	public void handler(CustomerMasterEvent event) {
		if(event.isCreate()) customerStale = true;
	}

	public void handler(PlantMasterEvent event) {
		if(event.isCreate()) plantStale = true;
	}
	
}
