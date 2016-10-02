package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.graph.IAreaMasterVo;

public class AreaMasterVoComparator {

	public final static Comparator<IAreaMasterVo> BY_NAME = new Comparator<IAreaMasterVo>(){

		public int compare(IAreaMasterVo o1, IAreaMasterVo o2) {
			return sortByName(o1, o2);
		}
		
	};

	public static int sortByName(IAreaMasterVo v, IAreaMasterVo w) {
		return v.getName().compareTo(w.getName());
	}
			
}
