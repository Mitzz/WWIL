package com.enercon.model.graph;

import java.util.List;

import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.graph.listener.PlantMasterListener;
import com.enercon.model.graph.listener.WecMasterListener;
import com.enercon.model.master.FederMasterVo;

public interface ISiteMasterVo extends WecMasterListener, CustomerMasterListener, PlantMasterListener {
	List<ICustomerMasterVo> getCustomers();
	List<IWecMasterVo> getWecs();
	List<IPlantMasterVo> getPlants();
	
	List<IEbMasterVo> getEbs();
	ISiteMasterVo addEb(IEbMasterVo eb);
	
	ISiteMasterVo setArea(IAreaMasterVo area);
	IAreaMasterVo getArea();
	
	IStateMasterVo getState();
	
	String getId();
	String getName();	
	String getCode();
	String getIncharge();
	String getAddress();
	String getStateId();
	String getAreaId();
	String getRemark();
	String getCreatedBy();
	String getModifiedBy();
	String getCreatedAt();
	String getModifiedAt();
	
	List<FederMasterVo> getFeders();
	void addFeder(FederMasterVo feder);
}
