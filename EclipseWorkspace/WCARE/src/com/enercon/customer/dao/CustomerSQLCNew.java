package com.enercon.customer.dao;

public class CustomerSQLCNew 
{

	public static final String GET_TOTAL_UNPUBLISHED_DATA = 
		"SELECT COUNT(1) AS CNT FROM TBL_WEC_READING WHERE N_PUBLISH = 0 "+
		"AND TO_DATE(D_READING_DATE) <= TO_DATE(?) ";

}
