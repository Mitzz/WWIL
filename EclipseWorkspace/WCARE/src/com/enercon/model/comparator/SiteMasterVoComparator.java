package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.SiteMasterVo;

public class SiteMasterVoComparator {

	public final static Comparator<SiteMasterVo> BY_NAME = new Comparator<SiteMasterVo>(){

			public int compare(SiteMasterVo o1, SiteMasterVo o2) {
				return sortByName(o1, o2);
			}
			
		};
		
	public final static Comparator<SiteMasterVo> BY_STATE_NAME = new Comparator<SiteMasterVo>(){

		public int compare(SiteMasterVo o1, SiteMasterVo o2) {
			return sortByStateSite(o1, o2);
		}
		
	};
	
	public static int sortByStateSite(SiteMasterVo v, SiteMasterVo w) {
		int compare = sortByState(v, w);
		if(compare != 0) return compare;
		
		return sortByName(v, w);
	}
	
	public static int sortByName(ISiteMasterVo v, ISiteMasterVo w){
		if(v.getName().compareTo(w.getName()) < 0) return -1;
		if(v.getName().compareTo(w.getName()) > 0) return +1;
		return 0;
	}
	
	public static int sortByState(ISiteMasterVo v, ISiteMasterVo w){
		return StateMasterVoComparator.sortByName(v.getState(), w.getState());
	}

	public static int sortByStateSite(ISiteMasterVo v, ISiteMasterVo w) {
		
		int compare = StateMasterVoComparator.sortByName(v.getState(), w.getState());
		if(compare != 0) return compare;
		
		return sortByName(v, w);
	}
}
