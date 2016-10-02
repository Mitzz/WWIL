package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.master.CustomerMasterVo;

public class CustomerMasterVoComparator {

	public final static Comparator<CustomerMasterVo> NAME = name();
	public final static Comparator<ICustomerMasterVo> BY_NAME = new Comparator<ICustomerMasterVo>() {

		public int compare(ICustomerMasterVo v, ICustomerMasterVo w) {
			return sortByName(v, w);
		}
		
	};

	private static Comparator<CustomerMasterVo> name(){
		return new Comparator<CustomerMasterVo>(){
			public int compare(CustomerMasterVo v, CustomerMasterVo w) {
				return compareName(v, w);
			}
		};
	}

	public static int sortByName(ICustomerMasterVo v, ICustomerMasterVo w) {
		int nameCompare = v.getName().compareTo(w.getName());
		if(nameCompare < 0) return -1;
		if(nameCompare > 0) return +1;
		
		return 0;
	}

	public static int compareName(CustomerMasterVo o1, CustomerMasterVo o2) {
		int nameCompare = o1.getName().compareTo(o2.getName());
		if(nameCompare < 0) return -1;
		if(nameCompare > 0) return +1;
		
		return 0;
	}
}
