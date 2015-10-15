package com.enercon.model.comparator;

import java.util.Comparator;
import java.util.List;

import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.StateMasterVo;

public class StateMasterVoComparator {

	public final static Comparator<StateMasterVo> NAME = name();
	public final static Comparator<StateMasterVo> CUSTOMER = customerName();

	private static Comparator<StateMasterVo> name(){
		return new Comparator<StateMasterVo>(){
			public int compare(StateMasterVo o1, StateMasterVo o2) {
				return compareName(o1, o2);
			}
		};
	}
	
	private static Comparator<StateMasterVo> customerName() {
		return new Comparator<StateMasterVo>(){
			public int compare(StateMasterVo o1, StateMasterVo o2) {
				return compareCustomer(o1, o2);
			}
		};
	}

	public static int compareCustomer(StateMasterVo o1, StateMasterVo o2) {
		int stateNameCompare = o1.getName().compareTo(o2.getName());
		if(stateNameCompare < 0) return -1;
		if(stateNameCompare > 0) return +1;
		List<CustomerMasterVo> customers1 = o1.getCustomers();
		List<CustomerMasterVo> customers2 = o2.getCustomers();
		if(customers1.size() != 1 || customers2.size() != 1) throw new IllegalArgumentException("State should not contain more than 1 customer");
		int customerNameCompare = customers1.get(0).getName().compareTo(customers2.get(0).getName());
		if(customerNameCompare < 0) return -1;
		if(customerNameCompare > 0) return +1;
		return 0;
	}

	public static int compareName(StateMasterVo o1, StateMasterVo o2) {
		int stateNameCompare = o1.getName().compareTo(o2.getName());
		if(stateNameCompare < 0) return -1;
		if(stateNameCompare > 0) return +1;
		
		return 0;
	}
}
