package com.enercon.spring.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enercon.global.utility.misc.MiscUtility;

@Controller
public class TestController {
	
	private static Logger logger = Logger.getLogger(TestController.class);

	@RequestMapping("/test")
	public String test(HttpServletRequest request){
		logger.debug(MiscUtility.getLoginId(request));
		System.out.println("Test");
		return "test";
	}
}
