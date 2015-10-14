package com.enercon.sqlQuery.infoQuery;


public interface WecInfoQuery {

	public final static String getWecInfoBasedOnOneWecIdQuery = 
			"Select wecmas.* " + 
			"from tbl_wec_master wecmas " + 
			"where wecmas.S_wec_id = ? " ;
}
