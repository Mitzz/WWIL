package com.enercon.sqlQuery.findingQuery;

public interface EBFindingQuery {
	static final String getEbIdsBasedOnCustomerIdQuery = 
			"Select distinct S_EB_ID From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"order by S_EB_ID ";
	/*
Select distinct S_EB_ID From Customer_Meta_Data
Where S_Customer_Id = ''
order by S_EB_ID ;
*/
	
	static final String getEBIdsBasedOnStateIdCustomerIdQuery = 
			"Select S_Eb_Id From Customer_Meta_Data " + 
			"Where S_Customer_Id = ?  " + 
			"And S_State_Id = ? " +
			"Group By S_EB_ID " +
			"order by S_EB_ID " ;
	
	/*
Select S_Eb_Id From Customer_Meta_Data
Where S_Customer_Id = ''
And S_State_Id = ''
Group By S_EB_ID
order by S_EB_ID  ;
	 
	 */
	
	static final String getEBIdsWithoutTransferredBasedOnStateIdCustomerIdQuery = 
			"Select S_Eb_Id From Customer_Meta_Data " + 
			"Where S_Customer_Id = ?  " + 
			"And S_State_Id = ? " +
			"And S_Status = '1' " +
			"Group By S_EB_ID " +
			"order by S_EB_ID " ;
/*
Select S_Eb_Id From Customer_Meta_Data
Where S_Customer_Id = ''
And S_State_Id = ''
And S_Status = '1'
Group By S_EB_ID
order by S_EB_ID  ;
 
*/
	
	static String getEBIdsBasedOnSiteIdStateIdCustomerIdQuery = 
			"Select S_EB_ID From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"And S_State_Id = ? " +
			"And S_Site_Id = ? " +
			"Group By S_Eb_Id " +
			"order by S_EB_ID " ;

/*
Select S_EB_ID From Customer_Meta_Data
Where S_Customer_Id = ''
And S_State_Id = ''
And S_Site_Id = ''
Group By S_Eb_Id
order by S_EB_ID  ;

 * */
	
	static String getEBIdsWithoutTransferredBasedOnSiteIdStateIdCustomerIdQuery = 
			"Select S_EB_ID From Customer_Meta_Data " +
			"Where S_Site_Id = ? " +
			"And S_State_Id = ? " +
			"And S_Customer_Id = ? " +
			"And S_Status = '1' " +
			"Group By S_Eb_Id " +
			"order by S_EB_ID " ;
/*
Select S_EB_ID From Customer_Meta_Data
Where S_Customer_Id = ''
And S_State_Id = ''
And S_Site_Id = ''
And S_Status = '1'
Group By S_Eb_Id
order by S_EB_ID  ;

 */
	static String getEBIdsBasedOnSiteIdCustomerIdQuery = 
			"Select S_EB_ID From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"And S_Site_Id = ? " +
			"Group By S_Eb_Id " +
			"order by S_EB_ID ";
/*
Select S_EB_ID From Customer_Meta_Data
Where S_Customer_Id = ''
And S_Site_Id = ''
Group By S_Eb_Id
order by S_EB_ID ;

 * */
}
