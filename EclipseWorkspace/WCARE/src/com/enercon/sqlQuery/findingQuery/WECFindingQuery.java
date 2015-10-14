package com.enercon.sqlQuery.findingQuery;

public interface WECFindingQuery {
	static String getWECIdsBasedOnCustomerIdQuery = 
			"Select S_Wec_Id, S_Wecshort_Descr From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"order by S_Wecshort_Descr " ;
	
	static String getWECIdsBasedOnSiteIdCustomerIdQuery = 
			"Select S_Wec_Id, S_Wecshort_Descr From Customer_Meta_Data " +
			"Where S_Site_Id = ? " +
			"And S_Customer_Id = ? " +
			"order by S_Wecshort_Descr " ;
	
	static String getWECIdsBasedOnSiteIdStateIdCustomerIdQuery = 
			"Select S_Wec_Id, S_Wecshort_Descr From Customer_Meta_Data " +
			"Where S_Site_Id = ? " +
			"And S_State_Id = ? " +
			"And S_Customer_Id = ? " +
			"order by S_Wecshort_Descr " ;
/*
Select S_Wec_Id, S_Wecshort_Descr From Customer_Meta_Data
Where S_Site_Id = ''
And S_Customer_Id = ''
order by S_Wecshort_Descr  ;
*/
	
	static String getWECCapacityDependingOnWECTypeQuery = 
			"Select N_Wec_Capacity From Tbl_Wec_Type " +
			"where S_wec_type = ? "; 
	
	static String getWECIdsBasedOnWECTypeSiteIdCustomerIdQuery = 
			"Select S_Wec_Id, S_Wecshort_Descr From Customer_Meta_Data " +
			"Where S_WEC_TYPE = ? " +
			"and S_Site_Id = ? " +
			"And S_Customer_Id = ? " +
			"order by S_Wecshort_Descr " ;
	
	static String getWECIdsBasedOnEbIdQuery = 
			"Select S_Wec_Id ,S_Wecshort_Descr " +
			"From Customer_Meta_Data " +
			"Where S_Eb_Id = ? " +
			"Group By S_Wec_Id,S_Wecshort_Descr " +
			"order by S_Wecshort_Descr " ;
	
	static String getActiveWECIdsBasedOnEbIdQuery = 
			"Select wecmas.S_wec_id  " + 
			"from tbl_wec_master wecmas, tbl_eb_master ebmas " + 
			"where wecmas.S_eb_id = ebmas.S_eb_id " + 
			"and wecmas.s_eb_id = ? " + 
			"and wecmas.S_status in ('1') " + 
			"order by wecmas.s_wecshort_descr ";
	
	static String getWECIdsBasedOnEBIdsQuery = "";
/*
Select S_Wec_Id ,S_Wecshort_Descr
From Customer_Meta_Data
Where S_Eb_Id = ''
Group By S_Wec_Id,S_Wecshort_Descr
order by S_Wecshort_Descr  ;
 
 * */	
	static String getWECTypeCapacityBasedOnSiteIdQuery = 
					"select S_WEC_TYPE||'/'||N_WEC_CAPACITY AS WECCAPACITY " + 
					"From Customer_Meta_Data " + 
					"Where S_Site_Id = ? " + 
					"group by S_WEC_TYPE||'/'||N_WEC_CAPACITY " ; 

	static String getWECTranferStatus =
			"Select S_STATUS From Tbl_Wec_master " + 
			"where S_wec_id = ? ";
	
	static String getWECIdsBasedOnStateIdCustomerIdQuery = 
			"Select S_WEC_ID " +
			"From Customer_Meta_Data " +
			"Where S_State_Id = ? " +
			"And S_Customer_Id = ? ";
	
	static String getOneWECDataFromWECMasterBasedOnWECIdQuery =
			"Select * From Tbl_Wec_master " + 
			"where S_wec_id = ? ";
}
