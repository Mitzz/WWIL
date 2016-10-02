package com.enercon.global.utility.misc;

import javax.servlet.http.HttpServletRequest;

public class MiscUtility {

	public static String getLoginId(HttpServletRequest request){
		return request.getSession().getAttribute("loginID").toString();
	}
}
