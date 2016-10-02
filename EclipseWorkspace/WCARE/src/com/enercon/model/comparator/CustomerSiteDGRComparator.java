package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.dgr.CustomerSiteDGR;

public class CustomerSiteDGRComparator {

	public final static Comparator<CustomerSiteDGR> BY_STATE_SITE_CUSTOMER = new Comparator<CustomerSiteDGR>(){

		public int compare(CustomerSiteDGR v, CustomerSiteDGR w) {
			return sortByStateSiteCustomer(v, w);
		}
		
	};

	public static int sortByStateSiteCustomer(CustomerSiteDGR v, CustomerSiteDGR w) {
		
		
		int compare = SiteMasterVoComparator.sortByStateSite(v.getSite(), w.getSite());
		if(compare != 0) return compare;
		
		return CustomerMasterVoComparator.sortByName(v.getCustomer(), w.getCustomer());
	}
}
