package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.dgr.EbDgr;

public class EbDgrComparator {

	public final static Comparator<EbDgr> BY_EB_NAME_DESC = new Comparator<EbDgr>(){

		public int compare(EbDgr o1, EbDgr o2) {
			return sortByEbNameDesc(o1, o2);
		}
		
	};
	
	public final static Comparator<EbDgr> BY_EB_NAME = new Comparator<EbDgr>(){

		public int compare(EbDgr o1, EbDgr o2) {
			return sortByEbName(o1, o2);
		}
		
	};
	
	public static int sortByEbNameDesc(EbDgr o1, EbDgr o2){
		return EbMasterVoComparator.sortByEbDesc(o1.getEb(), o2.getEb());
	}
	
	public static int sortByEbName(EbDgr o1, EbDgr o2){
		return EbMasterVoComparator.sortByEb(o1.getEb(), o2.getEb());
	}
}
