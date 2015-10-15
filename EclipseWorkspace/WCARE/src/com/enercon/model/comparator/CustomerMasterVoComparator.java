package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.master.CustomerMasterVo;

public class CustomerMasterVoComparator {

	public final static Comparator<CustomerMasterVo> NAME = name();

	private static Comparator<CustomerMasterVo> name(){
		return new Comparator<CustomerMasterVo>(){
			public int compare(CustomerMasterVo o1, CustomerMasterVo o2) {
				return compareName(o1, o2);
			}
		};
	}

	public static int compareName(CustomerMasterVo o1, CustomerMasterVo o2) {
		int nameCompare = o1.getName().compareTo(o2.getName());
		if(nameCompare < 0) return -1;
		if(nameCompare > 0) return +1;
		
		return 0;
	}
}
