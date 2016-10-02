package com.enercon.model.graph;

import java.util.List;

import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.model.graph.listener.PlantMasterListener;
import com.enercon.model.graph.listener.SiteMasterListener;
import com.enercon.model.graph.listener.WecMasterListener;

public interface IStateMasterVo extends Cloneable, SiteMasterListener, EbMasterListener, WecMasterListener, CustomerMasterListener, PlantMasterListener {
	
	List<IWecMasterVo> getWecs();
	List<IPlantMasterVo> getPlants();
	List<IEbMasterVo> getEbs();
	List<ICustomerMasterVo> getCustomers();
	
	List<ISiteMasterVo> getSites();
	
	List<IAreaMasterVo> getAreas();
	IStateMasterVo addArea(IAreaMasterVo area);
	
	String getId();
	String getName();
	void setName(String name);
	
	String getSapCode();
	String getCreatedBy();
	String getModifiedBy();
	String getCreatedAt();
	String getModifiedAt();
	Object clone() throws CloneNotSupportedException;
	
}
