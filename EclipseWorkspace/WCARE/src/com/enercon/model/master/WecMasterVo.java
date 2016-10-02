package com.enercon.model.master;


public class WecMasterVo implements Comparable<WecMasterVo> {

	private String name;
	private String id;
	private String technicalNo;
	
	private EbMasterVo eb;
	private PlantMasterVo plant;
	
	private CustomerMasterVo customer;
	
	public WecMasterVo(String id) {
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

	public EbMasterVo getEb() {
		return eb;
	}

	public void setEb(EbMasterVo eb) {
		eb.addWec(this);
		this.eb = eb;
	}
	
	public String getEbName(){
		String name = eb.getName();
		return name;
	}

	public String getSiteName(){
		String name = eb.getSite().getName();
		return name;
	}
	
	public String getAreaName(){
		String name = eb.getSite().getArea().getName();
		return name;
	}

	public String getStateName(){
		String name = eb.getSite().getArea().getState().getName();
		return name;
	}
	
	@Override
	public String toString() {
		return eb.getSite().getArea().getState().getName() + ":" + eb.getSite().getArea().getName() + ":" + eb.getSite().getName() + ":" + eb.getName() + ":" + getName(); 
	}

	public int compareTo(WecMasterVo that) {
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
		WecMasterVo other = (WecMasterVo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getPlantNo() {
		return plant.getPlantNo();
	}
	
	public String getLocationNo() {
		return plant.getLocationNo();
	}
	
	public PlantMasterVo getPlant() {
		return plant;
	}

	public void setPlant(PlantMasterVo plant) {
		this.plant = plant;
	}

	public String getTechnicalNo() {
		return technicalNo;
	}

	public void setTechnicalNo(String technicalNo) {
		this.technicalNo = technicalNo;
	}

	public CustomerMasterVo getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerMasterVo customer) {
		customer.addWec(this);
		this.customer = customer;
	}
	
}

