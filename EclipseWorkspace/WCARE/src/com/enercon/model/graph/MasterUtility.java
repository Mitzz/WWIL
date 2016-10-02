package com.enercon.model.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class MasterUtility {
	
	private static Logger logger = Logger.getLogger(MasterUtility.class);

	public static List<ICustomerMasterVo> getCustomersByWecs(List<IWecMasterVo> wecs){
		Set<ICustomerMasterVo> customersS = new HashSet<ICustomerMasterVo>();
		for(IWecMasterVo wec: wecs)
			customersS.add(wec.getCustomer());
		return new ArrayList<ICustomerMasterVo>(customersS);
	}
	
	public static ICustomerMasterVo getCustomerByWec(IWecMasterVo wec) {
		return wec.getCustomer();
	}

	public static List<IPlantMasterVo> getPlantsByWecs(List<IWecMasterVo> wecs) {
		List<IPlantMasterVo> plants = new ArrayList<IPlantMasterVo>();
		for(IWecMasterVo wec: wecs)
			plants.add(wec.getPlant());
		return plants;
	}

	public static List<IWecMasterVo> getWecsByEbs(List<IEbMasterVo> ebs) {
		List<IWecMasterVo> wecs = new ArrayList<IWecMasterVo>();
		for(IEbMasterVo eb: ebs)
			wecs.addAll(eb.getWecs());
		return wecs;
	}
	
	public static List<IEbMasterVo> getEbsByWecs(List<IWecMasterVo> wecs){
		Set<IEbMasterVo> ebsS = new HashSet<IEbMasterVo>();
		for(IWecMasterVo wec: wecs)
			ebsS.add(wec.getEb());
		return new ArrayList<IEbMasterVo>(ebsS);
	}
	
	public static List<IEbMasterVo> getEbsBySites(List<ISiteMasterVo> sites) {
		List<IEbMasterVo> ebs = new ArrayList<IEbMasterVo>();
		for(ISiteMasterVo site: sites)
			ebs.addAll(site.getEbs());
		return ebs;
	}
	
	public static IEbMasterVo getEbByWec(IWecMasterVo wec){
		return wec.getEb();
	}
	
	public static List<ISiteMasterVo> getSitesByEbs(List<IEbMasterVo> ebs){
		Set<ISiteMasterVo> sitesS = new HashSet<ISiteMasterVo>();
		for(IEbMasterVo eb: ebs)
			sitesS.add(eb.getSite());
		return new ArrayList<ISiteMasterVo>(sitesS);
	}
	
	public static ISiteMasterVo getSiteByEb(IEbMasterVo eb){
		return eb.getSite();
	}
	
	public static List<ISiteMasterVo> getSitesByArea(IAreaMasterVo area){
		List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
		sites.addAll(area.getSites());
		return sites;
	}
	
	public static List<ISiteMasterVo> getSitesByAreas(List<IAreaMasterVo> areas){
		List<ISiteMasterVo> sites = new ArrayList<ISiteMasterVo>();
		for(IAreaMasterVo area: areas)
			sites.addAll(area.getSites());
		return sites;
	}
	
	public static IStateMasterVo getStateByArea(IAreaMasterVo area){
		//logger.debug(area);
		return area.getState();
	}
	
	public static List<IStateMasterVo> getStatesByAreas(List<IAreaMasterVo> areas) {
		Set<IStateMasterVo> statesS = new HashSet<IStateMasterVo>();
		
		for(IAreaMasterVo area: areas)
			statesS.add(area.getState());
		
		return new ArrayList<IStateMasterVo>(statesS);
	}

	public static IAreaMasterVo getAreaBySite(ISiteMasterVo site){
		return site.getArea();
	}
	
	public static List<IAreaMasterVo> getAreasBySites(List<ISiteMasterVo> sites){
		Set<IAreaMasterVo> areasS = new HashSet<IAreaMasterVo>();
		for(ISiteMasterVo site: sites)
			areasS.add(site.getArea());
		return new ArrayList<IAreaMasterVo>(areasS);
	}
	
}
