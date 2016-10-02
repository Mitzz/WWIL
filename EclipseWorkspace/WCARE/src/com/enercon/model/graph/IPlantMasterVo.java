package com.enercon.model.graph;


public interface IPlantMasterVo {

	IWecMasterVo getWec();
	IPlantMasterVo setWec(IWecMasterVo wec);
	
	IEbMasterVo getEb();
	ISiteMasterVo getSite();
	IAreaMasterVo getArea();
	IStateMasterVo getState();
	ICustomerMasterVo getCustomer();
	
	String getLocationNo();
	String getPlantNo();
}
