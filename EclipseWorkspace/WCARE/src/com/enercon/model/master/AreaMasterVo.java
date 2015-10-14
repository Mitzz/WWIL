package com.enercon.model.master;

import java.util.ArrayList;
import java.util.List;

public class AreaMasterVo implements Comparable<AreaMasterVo>{

	private String name;
	private String id;
	private StateMasterVo state;
	private List<SiteMasterVo> sites = new ArrayList<SiteMasterVo>();
	
	public AreaMasterVo(String id){
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

	public StateMasterVo getState() {
		return state;
	}

	public void setState(StateMasterVo state) {
		state.addArea(this);
		this.state = state;
	}

	public int compareTo(AreaMasterVo that) {
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
		AreaMasterVo other = (AreaMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<SiteMasterVo> getSites() {
		return sites;
	}

	public void setSites(List<SiteMasterVo> sites) {
		this.sites = sites;
	}

	public void addSite(SiteMasterVo site){
		sites.add(site);
	}
	
	public List<WecMasterVo> getWecs(){
		List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
		for (SiteMasterVo site : getSites()) {
			wecs.addAll(site.getWecs());
		}
		return wecs;
	}
	
}
