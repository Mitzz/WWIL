package com.enercon.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.event.EbMasterEvent;
import com.enercon.model.graph.event.PlantMasterEvent;
import com.enercon.model.graph.event.SiteMasterEvent;
import com.enercon.model.graph.event.WecMasterEvent;
import com.enercon.struts.form.CommonFormVo;
import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("stateMasterVo")
public class StateMasterVo extends CommonFormVo implements IStateMasterVo, Comparable<IStateMasterVo>, Serializable{
	
	private static final long serialVersionUID = 342412557633223808L;
	
	private List<IAreaMasterVo> areas = new ArrayList<IAreaMasterVo>();
	private List<ISiteMasterVo> sites;
	
	private List<IEbMasterVo> ebs;
	private List<IWecMasterVo> wecs;
	private List<ICustomerMasterVo> customers;
	private List<IPlantMasterVo> plants;
	
	private String id;
	private String name;
	private String sapCode;
	
	private boolean staleSite;
	private boolean staleEb;
	private boolean staleWec;
	private boolean staleCustomer;
	private boolean stalePlant;

	public StateMasterVo(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public StateMasterVo() {
	}
	
	public IStateMasterVo addArea(IAreaMasterVo area) {
		areas.add(area);
		return this;
	}
	
	public List<IAreaMasterVo> getAreas() {
		return areas;
	}
	
	public List<ISiteMasterVo> getSites() {
		if(sites == null || staleSite){
			sites = MasterUtility.getSitesByAreas(getAreas());
			staleSite = false;
		}
		return sites;
	}
	
	public List<IEbMasterVo> getEbs() {
		if(ebs == null || staleEb){
			ebs = MasterUtility.getEbsBySites(getSites());
			staleEb = false;
		}
		return ebs;
	}
	
	public List<IWecMasterVo> getWecs() {
		if(wecs == null || staleWec){
			wecs = MasterUtility.getWecsByEbs(getEbs());
			staleWec = false;
		}
		return wecs;
	}
	
	public List<ICustomerMasterVo> getCustomers() {
		if(customers == null || staleCustomer ){
			customers = MasterUtility.getCustomersByWecs(getWecs());
			staleCustomer = false;
		}
		return customers;
	}
	
	public List<IPlantMasterVo> getPlants() {
		if(plants == null){
			plants =  MasterUtility.getPlantsByWecs(getWecs());
			stalePlant = false;
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
		StateMasterVo other = (StateMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public int compareTo(IStateMasterVo that) {
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

	@Override
	public String toString() {
		return "StateMasterVo [name=" + name + "]";
	}

	public String getSapCode() {
		return sapCode;
	}
	
	public void setSapCode(String sapCode) {
		this.sapCode = sapCode;
		
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		setId(null);
		setName(null);
		setSapCode(null);

	}
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void handler(SiteMasterEvent event) {
		if(event.isCreate()) staleSite = true;
	}

	public void handler(EbMasterEvent event) {
		if(event.isCreate()) staleEb = true;
	}

	public void handler(WecMasterEvent event) {
		if(event.isCreate()) staleWec = true;
	}

	public void handler(CustomerMasterEvent event) {
		if(event.isCreate()) staleCustomer = true;
	}

	public void handler(PlantMasterEvent event) {
		if(event.isCreate()) stalePlant = true;
	}
	
}
