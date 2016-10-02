package com.enercon.model.dgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weblogic.utils.collections.TreeMap;

import com.enercon.global.utility.WecParameterUtility;
import com.enercon.model.comparator.WecMasterVoComparator;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.parameter.wec.IWecParameterVo;
import com.enercon.service.WecMasterService;

public class CustomerSiteDGR {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((site == null) ? 0 : site.hashCode());
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
		CustomerSiteDGR other = (CustomerSiteDGR) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (site == null) {
			if (other.site != null)
				return false;
		} else if (!site.equals(other.site))
			return false;
		return true;
	}

	//Input
	private ICustomerMasterVo customer;
	private ISiteMasterVo site;
	private List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
	Map<IWecMasterVo, IWecParameterVo> wecData = new TreeMap<IWecMasterVo, IWecParameterVo>(WecMasterVoComparator.BY_NAME);
	
	//Output
	private IWecParameterVo total;
	private double capacity;
	
	public CustomerSiteDGR(ICustomerMasterVo customer, ISiteMasterVo site) {
		this.customer = customer;
		this.site = site;
	}
	
	public CustomerSiteDGR(ICustomerMasterVo customer, ISiteMasterVo site, Map<IWecMasterVo, IWecParameterVo> wecData) {
		this(customer, site);
		this.wecData.putAll(wecData);
		
	}
	
	public CustomerSiteDGR capacity(){
		capacity = WecMasterService.getInstance().getCapacity(wecs);
		return this;
	}
	
	public CustomerSiteDGR addWec(IWecMasterVo wec){
		wecs.add(wec);
		return this;
	}

	public double getCapacity(){
		return capacity;
	}

	public ICustomerMasterVo getCustomer() {
		return customer;
	}

	public ISiteMasterVo getSite() {
		return site;
	}

	public Map<IWecMasterVo, IWecParameterVo> getWecsData() {
		return wecData;
	}

	public List<IWecMasterVo> getWecs() {
		return wecs;
	}

	public CustomerSiteDGR setWecs(List<IWecMasterVo> wecs) {
		this.wecs = wecs;
		return this;
	}

	public CustomerSiteDGR setWecData(Map<IWecMasterVo, IWecParameterVo> wecData) {
		this.wecData.putAll(wecData);
		return this;
	}

	public CustomerSiteDGR doTotal(){
		if(wecData.size() == 1) return this;
		total = WecParameterUtility.total(wecData.values());
		return this;
	}
	
	public IWecParameterVo getTotal(){
		return total;
	}
}
