package com.enercon.model.master;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SiteMasterVo implements Comparable<SiteMasterVo>{

	private String name;
	private String id;
	private AreaMasterVo area;
	private List<EbMasterVo> ebs = new ArrayList<EbMasterVo>();
	
	public SiteMasterVo(String id){
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
	public AreaMasterVo getArea() {
		return area;
	}
	public void setArea(AreaMasterVo area) {
		area.addSite(this);
		this.area = area;
	}

	public int compareTo(SiteMasterVo that) {
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
		SiteMasterVo other = (SiteMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public List<EbMasterVo> getEbs() {
		return ebs;
	}

	public void setEbs(List<EbMasterVo> ebs) {
		this.ebs = ebs;
	}


	public void addEb(EbMasterVo eb){
		ebs.add(eb);
	}
	
	public List<WecMasterVo> getWecs(){
		List<WecMasterVo> wecs = new ArrayList<WecMasterVo>();
		for (EbMasterVo eb : getEbs()) {
			wecs.addAll(eb.getWecs());
		}
		return wecs;
	}
}
