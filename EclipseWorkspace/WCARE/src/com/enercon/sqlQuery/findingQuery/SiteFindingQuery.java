package com.enercon.sqlQuery.findingQuery;

public interface SiteFindingQuery {
	static String getSiteIdSiteNameBasedOnStateIdCustomerIdQuery = 
			"Select S_Site_Name,S_Site_Id From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"and S_State_Id = ? " +
			"Group By S_Site_Name,S_Site_Id " +
			"order by S_Site_Name " ;
/*
Select S_Site_Name,S_Site_Id From Customer_Meta_Data
Where S_Customer_Id = ''
and S_State_Id = ''
Group By S_Site_Name,S_Site_Id
order by S_Site_Name  ;
 */
}
