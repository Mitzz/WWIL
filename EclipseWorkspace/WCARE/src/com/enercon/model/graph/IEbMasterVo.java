package com.enercon.model.graph;

import java.util.List;

import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.graph.listener.PlantMasterListener;
import com.enercon.model.table.EbMFactorVo;

public interface IEbMasterVo extends CustomerMasterListener, PlantMasterListener {
	
	List<ICustomerMasterVo> getCustomers();
	
	List<IWecMasterVo> getWecs();
	IEbMasterVo addWec(IWecMasterVo wec);
	
	List<EbMFactorVo> getEbMfs();
	IEbMasterVo addEbMf(EbMFactorVo ebmf);
	
	List<IPlantMasterVo> getPlants();
	
	ISiteMasterVo getSite();
	IEbMasterVo setSite(ISiteMasterVo site);
	
	IAreaMasterVo getArea();
	IStateMasterVo getState();
	
	String getId();
	String getName();
	String getWorkingStatus();
	String getDescription();
	String getType();
	String getSubType();
	int getFactor();
	String getCapacity();
	String getTransferDate();
	String getTransferToEbId();
	String getRemark();
	String getDisplay();

	String getSiteId();
	String getCreatedBy();
	String getModifiedBy();
	String getFederId();
	String getCustomerId();

	void setId(String id);
}
