package com.enercon.model;

import java.sql.SQLException;
import java.util.Map;

import com.enercon.dao.WECParameterVoDao;

public class WECData {

	/*key : wecId 
	  value : corresponds to various attributes of WEC like statename, areaname, etc 
	 		  as defined by Enum 'WECDataEnum'
	 */
	private static Map<String, Map<WecLocationData, String>> wecData;
	
	static {
		WECParameterVoDao wecDao = new WECParameterVoDao();
		try {
			long start = System.currentTimeMillis();
			
			setWecData(wecDao.getActiveWecLocationData());
			
			long end = System.currentTimeMillis();
//			System.out.println("------------------Time Taken : " + ((end-start)/1000));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Map<WecLocationData, String>> getWecData() {
		return wecData;
	}


	public static void setWecData(Map<String, Map<WecLocationData, String>> wecData) {
		WECData.wecData = wecData;
	}
}
