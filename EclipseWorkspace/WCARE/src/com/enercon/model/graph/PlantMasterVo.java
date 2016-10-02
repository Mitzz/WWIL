package com.enercon.model.graph;

import java.io.Serializable;

public class PlantMasterVo implements IPlantMasterVo, Serializable{
	
	private static final long serialVersionUID = 4829178833635391844L;
	private String plantNo;
	private String locationNo;
	
	private IWecMasterVo wec;
	
	private ICustomerMasterVo customer;
	private IEbMasterVo eb;
	private ISiteMasterVo site;
	private IAreaMasterVo area;
	private IStateMasterVo state;
	
	public PlantMasterVo() {
	}
	
	
	public IWecMasterVo getWec() {
		return wec;
	}

	
	public IPlantMasterVo setWec(IWecMasterVo wec) {
		this.wec = wec;
		return this;
	}

	
	public IEbMasterVo getEb() {
		if(eb == null)
			eb = MasterUtility.getEbByWec(getWec());
		return eb;
	}
	
	
	public ISiteMasterVo getSite() {
		if(site == null)
			site = MasterUtility.getSiteByEb(getEb());
		return site;
	}

	
	public IStateMasterVo getState() {
		if(state == null)
			state = MasterUtility.getStateByArea(getArea());
		return state;
	}

	
	public ICustomerMasterVo getCustomer() {
		if(customer == null)
			customer = MasterUtility.getCustomerByWec(getWec());
		
		return customer;
	}

	public String getPlantNo() {
		return plantNo;
	}

	public void setPlantNo(String plantNo) {
		this.plantNo = plantNo;
	}

	public String getLocationNo() {
		return locationNo;
	}

	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}

	
	public IAreaMasterVo getArea() {
		if(area == null)
			area = MasterUtility.getAreaBySite(getSite());
		return area;
	}

	
}
