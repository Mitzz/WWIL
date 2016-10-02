package com.enercon.model.graph;

import java.util.List;

import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.model.graph.listener.PlantMasterListener;
import com.enercon.model.graph.listener.WecMasterListener;
import com.enercon.model.master.SubstationMasterVo;

public interface IAreaMasterVo extends EbMasterListener, WecMasterListener, CustomerMasterListener, PlantMasterListener, Cloneable{

	List<IPlantMasterVo> getPlants();
	List<ICustomerMasterVo> getCustomers();
	List<IWecMasterVo> getWecs();
	List<IEbMasterVo> getEbs();
	
	List<ISiteMasterVo> getSites();
	IAreaMasterVo addSite(ISiteMasterVo site);
	
	List<SubstationMasterVo> getSubstations();
	IAreaMasterVo addSubstation(SubstationMasterVo substation);
	
	IStateMasterVo getState();
	IAreaMasterVo setState(IStateMasterVo state);
	
	String getId();
	String getName();
	String getCode();
	String getInCharge();
	String getCreatedBy();
	String getModifiedBy();
	String getCreatedAt();
	String getModifiedAt();
	String getStateId();
	
}
