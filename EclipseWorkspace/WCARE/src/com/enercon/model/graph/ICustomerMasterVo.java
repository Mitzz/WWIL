package com.enercon.model.graph;

import java.util.List;

import com.enercon.model.graph.listener.AreaMasterListener;
import com.enercon.model.graph.listener.EbMasterListener;
import com.enercon.model.graph.listener.PlantMasterListener;
import com.enercon.model.graph.listener.SiteMasterListener;
import com.enercon.model.graph.listener.StateMasterListener;

public interface ICustomerMasterVo extends EbMasterListener, SiteMasterListener, AreaMasterListener, StateMasterListener, PlantMasterListener{

	List<IWecMasterVo> getWecs();
	ICustomerMasterVo addWec(IWecMasterVo wec);
	
	List<IPlantMasterVo> getPlants();
	List<IEbMasterVo> getEbs();
	List<ISiteMasterVo> getSites();
	List<IAreaMasterVo> getAreas();
	List<IStateMasterVo> getStates();
	
	String getName();
	String getId();
	String getSapCode();
	String getTelephoneNo();
	String getCellNo();
	String getFaxNo();
	String getContactPerson();
	String getMarketingPerson();
	String getEmail();
	String getActive();
	String getLoginId();
	
	String getCreatedBy();
	String getModifiedBy();
	String getCreatedAt();
	String getModifiedAt();
}
