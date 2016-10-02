package com.enercon.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.event.PlantMasterEvent;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("areaMasterVo")
public class AreaMasterVo extends CommonFormVo implements IAreaMasterVo, Serializable, Comparable<AreaMasterVo>{

	private static final long serialVersionUID = -4186074471204297136L;
	private List<IPlantMasterVo> plants;
	private List<ICustomerMasterVo> customers;
	private List<IWecMasterVo> wecs;
	private List<IEbMasterVo> ebs;
	private List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
	private List<SubstationMasterVo> substations = new ArrayList<SubstationMasterVo>();
	private IStateMasterVo state;
	
	private String id;
	private String name;
	private String code;
	private String inCharge;
	private String stateId;
	
	private boolean ebStale;
	private boolean wecStale;
	private boolean customerStale;
	private boolean plantStale;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public List<IPlantMasterVo> getPlants() {
		if(plants == null || plantStale){
			plants = MasterUtility.getPlantsByWecs(getWecs());
			plantStale = false;
		}
		return plants;
	}

	public List<ICustomerMasterVo> getCustomers() {
		if(customers == null || customerStale){
			customers = MasterUtility.getCustomersByWecs(getWecs());
			customerStale = false;
		}
		return customers;
	}

	
	public List<IWecMasterVo> getWecs() {
		if(wecs == null || wecStale){
			wecs = MasterUtility.getWecsByEbs(getEbs());
			wecStale = false;
		}
		return wecs;
	}

	
	public List<IEbMasterVo> getEbs() {
		if(ebs == null || ebStale){
			ebs = MasterUtility.getEbsBySites(getSites());
			ebStale = false;
		}
		return ebs;
	}

	
	public List<ISiteMasterVo> getSites() {
		return sites;
	}

	
	public IAreaMasterVo addSite(ISiteMasterVo site) {
		sites.add(site);
		return this;
	}

	
	public IStateMasterVo getState() {
		return state;
	}
	
	public IAreaMasterVo setState(IStateMasterVo state) {
		this.state = state;
		this.state.addArea(this);
		return this;
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
		AreaMasterVo other = (AreaMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public int compareTo(AreaMasterVo that) {
		if(this.id.compareTo(that.getId()) < 0) return -1;
		if(this.id.compareTo(that.getId()) > 0) return +1;
		return 0;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInCharge() {
		return inCharge;
	}

	public void setInCharge(String inCharge) {
		this.inCharge = inCharge;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		 setId(null);
		 setName(null);
		 setCode(null);
		 setInCharge(null);
		 setStateId(null);
	}

	public void handler(EbMasterEvent event) {
		if(event.isCreate()) ebStale = true;
	}

	public void handler(WecMasterEvent event) {
		if(event.isCreate()) wecStale = true;
	}

	public void handler(CustomerMasterEvent event) {
		if(event.isCreate()) customerStale = true;
	}

	public void handler(PlantMasterEvent event) {
		if(event.isCreate()) plantStale = true;
	}

	public List<SubstationMasterVo> getSubstations() {
		return substations;
	}

	public IAreaMasterVo addSubstation(SubstationMasterVo substation) {
		substations.add(substation);
		return this;
	}
}
