package com.enercon.spring.controllers.report;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ByDayController {

	@RequestMapping("/byDay")
	public ModelAndView first(){
		ModelAndView mv = new ModelAndView("report/DGRReportN");
		Map<String, Object> model = mv.getModel();
		model.put("Type", "D");
		return mv;
	}
}
