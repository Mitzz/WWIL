package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.graph.IEbMasterVo;

public class EbMasterVoComparator {

	public final static Comparator<IEbMasterVo> BY_NAME_DESC = new Comparator<IEbMasterVo>(){

		public int compare(IEbMasterVo o1, IEbMasterVo o2) {
			return sortByEbDesc(o1, o2);
		}
		
	};
	
	public static int sortByEbDesc(IEbMasterVo v, IEbMasterVo w){
		if(v.getName().compareTo(w.getName()) < 0) return +1;
		if(v.getName().compareTo(w.getName()) > 0) return -1;
		return 0;
	}
	
	public static int sortByEb(IEbMasterVo v, IEbMasterVo w){
		if(v.getName().compareTo(w.getName()) < 0) return -1;
		if(v.getName().compareTo(w.getName()) > 0) return +1;
		return 0;
	}
}
