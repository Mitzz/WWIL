package com.enercon.sqlQuery.findingQuery;

public interface MiscFindingQuery {

	public static String getCustomerInfoBasedonSiteIdWECTypeQuery = 
			"SELECT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_STATE_NAME,S_SITE_ID , NVL(N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt  " + 
			"FROM customer_meta_data " + 
			"WHERE S_SITE_ID = ?  " + 
			"and S_wec_type = ? " + 
			"group by S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_STATE_NAME,S_SITE_ID, n_wec_capacity " + 
			"ORDER BY S_CUSTOMER_NAME " ; 

}
