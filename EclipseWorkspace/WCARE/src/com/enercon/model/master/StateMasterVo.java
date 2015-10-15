package com.enercon.model.master;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class StateMasterVo implements Comparable<StateMasterVo>, WecsPresent{

	private String name;
	private String id;
	private List<AreaMasterVo> areas = new ArrayList<AreaMasterVo>();
	private List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
	private List<CustomerMasterVo> customers;
	
	private final static Logger logger = Logger.getLogger(StateMasterVo.class);
	
	public StateMasterVo(String id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "StateMasterVo [name=" + name + "]";
	}

	public int compareTo(StateMasterVo that) {
		if(this.id.compareTo(that.id) < 0) return -1;
		if(this.id.compareTo(that.id) > 0) return +1;
		return 0;
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
		StateMasterVo other = (StateMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<AreaMasterVo> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaMasterVo> areas) {
		this.areas = areas;
	}
	
	public void addArea(AreaMasterVo area){
		areas.add(area);
	}
	
	public List<CustomerMasterVo> getCustomers() {
		if(customers == null || customers.size() == 0){
			Set<CustomerMasterVo> customersSet = new HashSet<CustomerMasterVo>();
			List<WecMasterVo> wecs = getWecs();
			
			for(WecMasterVo wec : wecs){
				CustomerMasterVo customer = wec.getCustomer();
				customersSet.add(customer);
			}
			
			logger.debug(customersSet);
			customers = new ArrayList<CustomerMasterVo>(customersSet);
		}
		return customers;
	}
	
	public List<WecMasterVo> getWecs(){
		if(wecs == null || wecs.size() == 0){
			List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
			for (AreaMasterVo area : getAreas()) {
				wecs.addAll(area.getWecs());
			}
			this.wecs = wecs;
		}
		return wecs;
	}
}
