package com.enercon.model.master;

import java.util.ArrayList;
import java.util.List;

public class EbMasterVo implements Comparable<EbMasterVo>, WecsPresent{

	private String name;
	private String id;
	private SiteMasterVo site;
	private List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
	
	public EbMasterVo(String id){
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
	public SiteMasterVo getSite() {
		return site;
	}
	public void setSite(SiteMasterVo site) {
		site.addEb(this);
		this.site = site;
	}

	@Override
	public String toString() {
		return "EbMasterVo [name=" + name + "]";
	}

	public List<WecMasterVo> getWecs() {
		return wecs;
	}

	public void setWecs(List<WecMasterVo> wecs) {
		this.wecs = wecs;
	}
	
	public void addWec(WecMasterVo wec){
		wecs.add(wec);
	}
	
	public int compareTo(EbMasterVo that) {
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
		EbMasterVo other = (EbMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
