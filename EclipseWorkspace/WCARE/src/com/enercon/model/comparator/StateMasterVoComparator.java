package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.master.StateMasterVo;

public class StateMasterVoComparator {

	public final static Comparator<StateMasterVo> NAME = name(); 

	private static Comparator<StateMasterVo> name(){
		return new Comparator<StateMasterVo>(){
			public int compare(StateMasterVo o1, StateMasterVo o2) {
				return compareName(o1, o2);
			}
		};
	}
	
	public static int compareName(StateMasterVo o1, StateMasterVo o2) {
		int stateNameCompare = o1.getName().compareTo(o2.getName());
		if(stateNameCompare < 0) return -1;
		if(stateNameCompare > 0) return +1;
		
		return 0;
	}
}
