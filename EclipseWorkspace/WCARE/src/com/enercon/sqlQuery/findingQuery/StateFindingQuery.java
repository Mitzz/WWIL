package com.enercon.sqlQuery.findingQuery;

public interface StateFindingQuery {
	public static final String getStateIdStateNameBasedOnCustomerIdQuery = 
			"Select S_State_Name,S_State_Id From Customer_Meta_Data " + 
			"where S_Customer_Id = ? " + 
			"Group By S_State_Name,S_State_Id " + 
			"order by S_State_Name ";
/*
Select S_State_Name,S_State_Id From Customer_Meta_Data
where S_Customer_Id = ''
Group By S_State_Name,S_State_Id
order by S_State_Name ;
 */
	public static final String getStateIdStateNameBasedOnWECIdQuery =
					"Select S_State_Name,S_State_Id From Customer_Meta_Data " + 
					"where S_WEC_Id = ? ";
}
