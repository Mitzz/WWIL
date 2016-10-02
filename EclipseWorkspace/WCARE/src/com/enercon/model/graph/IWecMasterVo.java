package com.enercon.model.graph;

public interface IWecMasterVo {

	IPlantMasterVo getPlant();
	IWecMasterVo setPlant(IPlantMasterVo plant);
	
	IEbMasterVo getEb();
	IWecMasterVo setEb(IEbMasterVo eb);
	
	ICustomerMasterVo getCustomer();
	IWecMasterVo setCustomer(ICustomerMasterVo customer);
	
	ISiteMasterVo getSite();
	IAreaMasterVo getArea();
	IStateMasterVo getState();
	
	String getId();
	String getName();
	String getScadaStatus();
	String getStatus();
	Double getCapacity();
	String getTechnicalNo();
	String getCommissionDate();
	String getType();
	String getFoundationNo();
	String getShow();
	String getRemark();
	String getTransferDate();
	String getTransferWecId();
	
	String getStartDate();
	String getEndDate();
	String getMultiFactor();
	String getGenComm();
	Double getCostPerUnit();
	String getMachineAvailability();
	String getExtGridAvailability();
	String getIntGridAvailability();
	String getFormula();
	String getCustomerId();
	String getEbId();
	String getFeederId();
	String getGuaranteeType();
	String getCustomerType();	
	
	String getCreatedBy();
	String getModifiedBy();
	String getCreatedAt();
	String getModifiedAt();
	
}
