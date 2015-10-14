package com.enercon.sqlQuery.findingQuery;

public interface CustomerFindingMetaQuery {
	public static String getCustomerMetaInfoBasedOnWECId = 
			"Select * " + 
			"From Customer_Meta_Data " +
			"Where S_Wec_Id = ? " ;
	
	public static String getCustomerMetaInfoBasedOnEbId =
					"Select * " + 
					"From Customer_Meta_Data " +
					"Where S_EB_Id = ? " ;
	
	public static String getCustomerMetaInfoBasedOnWECTypeWithLoadCapacityQuery = 
					"Select S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity, NVL(N_Wec_Capacity/1000 * count(*), 0),count(*) as Total_WEC " +
					"From Customer_Meta_Data " +
					"Where S_Wec_Type = ? " +
					"Group By S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity " +
					"Order By S_state_name,S_Site_Name,S_customer_name " ;
	
	public static String getCustomerMetaInfoBasedOnWECTypeStateIdWithLoadCapacityQuery = 
			"Select S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity, NVL(N_Wec_Capacity/1000 * count(*), 0),count(*) as Total_WEC " +
			"From Customer_Meta_Data " +
			"Where S_Wec_Type = ? " +
			"and S_State_id = ? " +
			"Group By S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity " +
			"Order By S_state_name,S_Site_Name,S_customer_name " ;
	
	public static String getCustomerMetaInfoBasedOnStateIdWithLoadCapacityQuery = 
			"Select S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity, NVL(N_Wec_Capacity/1000 * count(*), 0),count(*) as Total_WEC " +
			"From Customer_Meta_Data " +
			"Where S_State_id = ? " +
			"Group By S_Customer_Name, S_Customer_Id, S_State_Name, S_State_Id, S_Site_Name, S_Site_Id, N_Wec_Capacity " +
			"Order By S_state_name,S_Site_Name,S_customer_name " ;
}
