package com.enercon.model.comparator;

import java.util.Comparator;

import com.enercon.model.dgr.SiteStateCustomerDGR;

public class SiteStateCustomerDGRComparator {

	public final static Comparator<SiteStateCustomerDGR> BY_SITE_NAME = bySiteName();

	private static Comparator<SiteStateCustomerDGR> bySiteName() {
		return new Comparator<SiteStateCustomerDGR>(){
			public int compare(SiteStateCustomerDGR o1, SiteStateCustomerDGR o2) {
				return sortBySiteName(o1, o2);
			}
		};
	}
	
	public static int sortBySiteName(SiteStateCustomerDGR v, SiteStateCustomerDGR w){
		return SiteMasterVoComparator.sortByName(v.getSite(), w.getSite());
	}
}
