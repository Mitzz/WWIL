package com.enercon.model.comparator;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

import com.enercon.model.master.WecMasterVo;

public class WecMasterVoComparator {

	public final static Comparator<WecMasterVo> NAME = sortName();
	//SALPW -> State Area Location Plant Wec
	//SASEW -> State Area Site Eb Wec
	public final static Comparator<WecMasterVo> SALPW = sortSALPW();
	public final static Comparator<WecMasterVo> SASEW = sortSASEW();
	public final static Comparator<Map.Entry<String, WecMasterVo>> SASEW_M = sortSASEW_M();
	
	private static Comparator<WecMasterVo> sortName() {
		return new Comparator<WecMasterVo>(){
			public int compare(WecMasterVo o1, WecMasterVo o2) {
				return sortName(o1, o2);
			}
		};
	}

	public static int sortName(WecMasterVo o1, WecMasterVo o2) {
		int wecCompare = o1.getName().compareTo(o2.getName());
		if(wecCompare < 0) return -1;
		if(wecCompare > 0) return +1;
		
		return 0;
	}

	private static Comparator<WecMasterVo> sortSALPW(){
		return new Comparator<WecMasterVo>(){
			public int compare(WecMasterVo o1, WecMasterVo o2) {
				return sortSALPW(o1, o2);
			}
		};
	}
	
	private static Comparator<WecMasterVo> sortSASEW() {
		return new Comparator<WecMasterVo>(){
			public int compare(WecMasterVo o1, WecMasterVo o2) {
				return sortSASEW(o1, o2);
			}
		};
	}

	public static int sortSASEW(WecMasterVo o1, WecMasterVo o2) {
		int stateCompare = o1.getEb().getSite().getArea().getState().getName().compareTo(o2.getEb().getSite().getArea().getState().getName());
		if(stateCompare < 0) return -1;
		if(stateCompare > 0) return +1;
		
		int areaCompare = o1.getEb().getSite().getArea().getName().compareTo(o2.getEb().getSite().getArea().getName());
		if(areaCompare < 0) return -1;
		if(areaCompare > 0) return +1;
		
		int siteCompare = o1.getEb().getSite().getName().compareTo(o2.getEb().getSite().getName());
		if(siteCompare < 0) return -1;
		if(siteCompare > 0) return +1;
		
		int ebCompare = o1.getEb().getName().compareTo(o2.getEb().getName());
		if(ebCompare < 0) return -1;
		if(ebCompare > 0) return +1;
		
		int wecCompare = o1.getName().compareTo(o2.getName());
		if(wecCompare < 0) return -1;
		if(wecCompare > 0) return +1;
		
		return 0;
	}
	
	private static Comparator<Map.Entry<String, WecMasterVo>> sortSASEW_M(){
		return new Comparator<Map.Entry<String, WecMasterVo>>(){
			public int compare(Entry<String, WecMasterVo> o1, Entry<String, WecMasterVo> o2) {
				return sortSASEW(o1, o2);
			}
		};
	}
	
	public static int sortSASEW(Entry<String, WecMasterVo> o1, Entry<String, WecMasterVo> o2){
		int stateCompare = o1.getValue().getEb().getSite().getArea().getState().getName().compareTo(o2.getValue().getEb().getSite().getArea().getState().getName());
		if(stateCompare < 0) return -1;
		if(stateCompare > 0) return +1;
		
		int areaCompare = o1.getValue().getEb().getSite().getArea().getName().compareTo(o2.getValue().getEb().getSite().getArea().getName());
		if(areaCompare < 0) return -1;
		if(areaCompare > 0) return +1;
		
		int siteCompare = o1.getValue().getEb().getSite().getName().compareTo(o2.getValue().getEb().getSite().getName());
		if(siteCompare < 0) return -1;
		if(siteCompare > 0) return +1;
		
		int ebCompare = o1.getValue().getEb().getName().compareTo(o2.getValue().getEb().getName());
		if(ebCompare < 0) return -1;
		if(ebCompare > 0) return +1;
		
		int wecCompare = o1.getValue().getName().compareTo(o2.getValue().getName());
		if(wecCompare < 0) return -1;
		if(wecCompare > 0) return +1;
		
		return 0;
	}
	
	public static int sortSALPW(WecMasterVo o1, WecMasterVo o2) {
		int stateCompare = o1.getEb().getSite().getArea().getState().getName().compareTo(o2.getEb().getSite().getArea().getState().getName());
		if(stateCompare < 0) return -1;
		if(stateCompare > 0) return +1;
		
		int areaCompare = o1.getEb().getSite().getArea().getName().compareTo(o2.getEb().getSite().getArea().getName());
		if(areaCompare < 0) return -1;
		if(areaCompare > 0) return +1;
		
		int locationNo = o1.getPlant().getLocationNo().compareTo(o2.getPlant().getLocationNo());
		if(locationNo < 0) return -1;
		if(locationNo > 0) return +1;
		
		int plantNo = Integer.valueOf(o1.getPlant().getPlantNo()).compareTo(Integer.valueOf(o2.getPlant().getPlantNo()));
		if(plantNo < 0) return -1;
		if(plantNo > 0) return +1;
		
		int wecCompare = o1.getName().compareTo(o2.getName());
		if(wecCompare < 0) return -1;
		if(wecCompare > 0) return +1;
		
		return 0;
	}
}
