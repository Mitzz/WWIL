package com.enercon.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.event.AreaMasterEvent;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.event.PlantMasterEvent;
import com.enercon.model.graph.event.SiteMasterEvent;
import com.enercon.model.graph.event.StateMasterEvent;
import com.enercon.model.master.MasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("customerMasterVo")
public class CustomerMasterVo extends CommonFormVo implements ICustomerMasterVo, Serializable, Comparable<CustomerMasterVo>, Cloneable{
	
	private static final long serialVersionUID = -2717360112584986936L;
	private static Logger logger = Logger.getLogger(CustomerMasterVo.class);
	private String id;
	private String name;
	
	private String sapCode;
	private String telephoneNo;
	private String cellNo;
	private String faxNo;
	private String contactPerson;
	private String marketingPerson;
	private String email;
	private String active;
	private String loginId;
	
	private List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
	
	private List<IPlantMasterVo> plants;
	private List<IEbMasterVo> ebs;
	private List<ISiteMasterVo> sites;
	private List<IAreaMasterVo> areas;
	private List<IStateMasterVo> states;
	private boolean ebStale;
	private boolean siteStale;
	private boolean areaStale;
	private boolean stateStale;
	private boolean plantStale;
	
	public CustomerMasterVo(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CustomerMasterVo() {
	}

	
	
	public List<IWecMasterVo> getWecs() {
		return wecs;
	}

	
	public ICustomerMasterVo addWec(IWecMasterVo wec) {
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

	
	public List<IEbMasterVo> getEbs() {
		if(ebs == null || ebStale){
			ebs = MasterUtility.getEbsByWecs(getWecs());
			ebStale = false;
		}
		return ebs;
	}

	
	public List<ISiteMasterVo> getSites() {
		if(sites == null || siteStale){
			sites = MasterUtility.getSitesByEbs(getEbs());
			siteStale = false;
		}
		return sites;
	}

	
	public List<IStateMasterVo> getStates() {
		if(states == null || stateStale){
			states = MasterUtility.getStatesByAreas(getAreas());
			stateStale = false;
		}
		return states;
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
		CustomerMasterVo other = (CustomerMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	public List<IAreaMasterVo> getAreas() {
		if(areas == null || areaStale){
			areas = MasterUtility.getAreasBySites(getSites());
			areaStale = false;
		}
		return areas;
	}

	public int compareTo(CustomerMasterVo that) {
		if(this.id.compareTo(that.getId()) < 0) return -1;
		if(this.id.compareTo(that.getId()) > 0) return +1;
		return 0;
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

	public String getSapCode() {
		return sapCode;
	}

	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
	}

	public String getTelephoneNo() {
		return telephoneNo;
	}

	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getMarketingPerson() {
		return marketingPerson;
	}

	public void setMarketingPerson(String marketingPerson) {
		this.marketingPerson = marketingPerson;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	
	public CustomerMasterVo(CustomerMasterVoBuilder builder){
		
		this.id = builder.id;
		this.name = builder.name;
		this.sapCode = builder.sapCode;
		this.telephoneNo = builder.telephoneNo;
		this.cellNo = builder.cellNo;
		this.faxNo = builder.faxNo;
		this.contactPerson = builder.contactPerson;
		this.marketingPerson = builder.marketingPerson;
		this.email = builder.email;
		this.active = builder.active;
		this.loginId = builder.loginId;
		this.createdBy = builder.getCreatedBy();
		this.createdAt = builder.getCreatedAt();
		this.modifiedBy = builder.getModifiedBy();
		this.modifiedAt = builder.getModifiedAt();
		
			
	}
	
	public static class CustomerMasterVoBuilder extends MasterVo {
		
		private String id;
		private String name;
		
		private String sapCode;
		private String telephoneNo;
		private String cellNo;
		private String faxNo;
		private String contactPerson;
		private String marketingPerson;
		private String email;
		private String active;
		private String loginId;
		
		public CustomerMasterVoBuilder id(String id) {
			this.id = id;
			return this;
		}
		public CustomerMasterVoBuilder name(String name) {
			this.name = name;
			return this;
		}
		public CustomerMasterVoBuilder sapCode(String sapCode) {
			this.sapCode = sapCode;
			return this;
		}
		public CustomerMasterVoBuilder telephoneNo(String telephoneNo) {
			this.telephoneNo = telephoneNo;
			return this;
		}
		public CustomerMasterVoBuilder cellNo(String cellNo) {
			this.cellNo = cellNo;
			return this;
		}
		public CustomerMasterVoBuilder faxNo(String faxNo) {
			this.faxNo = faxNo;
			return this;
		}
		public CustomerMasterVoBuilder contactPerson(String contactPerson) {
			this.contactPerson = contactPerson;
			return this;
		}
		public CustomerMasterVoBuilder marketingPerson(String marketingPerson) {
			this.marketingPerson = marketingPerson;
			return this;
		}
		public CustomerMasterVoBuilder email(String email) {
			this.email = email;
			return this;
		}
		public CustomerMasterVoBuilder active(String active) {
			this.active = active;
			return this;
		}
		
		public CustomerMasterVoBuilder loginId(String loginId) {
			this.loginId = loginId;
			return this;
		}
		
		public CustomerMasterVoBuilder createdBy(String createdBy) {
			setCreatedBy(createdBy);
			return this;
		}
		public CustomerMasterVoBuilder createdAt(String createdAt) {
			setCreatedAt(createdAt);
			return this;
		}
		public CustomerMasterVoBuilder modifiedBy(String modifiedBy) {
			setModifiedBy(modifiedBy);
			return this;
		}

		public CustomerMasterVoBuilder modifiedAt(String modifiedAt) {
			setModifiedAt(modifiedAt);
			return this;
		}

		public CustomerMasterVo build() {
			CustomerMasterVo vo = new CustomerMasterVo(this);
			return vo;
		}
		
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		setId(null);
		setName(null);
		setSapCode(null);
		setTelephoneNo(null);
		setCellNo(null);
		setFaxNo(null);
		setContactPerson(null);
		setMarketingPerson(null);
		setEmail(null);
		setActive(null);
		setLoginId(null);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void handler(EbMasterEvent event) {
		if(event.isCreate()) ebStale = true;
	}

	public void handler(SiteMasterEvent event) {
		if(event.isCreate()) siteStale = true;
	}

	public void handler(AreaMasterEvent event) {
		if(event.isCreate()) areaStale = true;
	}

	public void handler(StateMasterEvent event) {
		//logger.debug(event);
		if(event.isCreate()) stateStale = true;
	}

	public void handler(PlantMasterEvent event) {
		if(event.isCreate()) plantStale = true;
		
	}
}
