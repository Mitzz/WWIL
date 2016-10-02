package com.enercon.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enercon.ajax.factory.AjaxFactory;
import com.enercon.ajax.interfaces.AjaxDataRetriver;
import com.enercon.ajax.interfaces.AjaxVo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxFrontController extends HttpServlet {
       
	private static final long serialVersionUID = 1L;

	public AjaxFrontController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ajaxType = request.getParameter("ajaxType");
		ObjectMapper mapper = new ObjectMapper();
		
		AjaxDataRetriver ajaxDataRetriver = null;
		List<AjaxVo> ajaxVos = null;
		List<String> ajaxRequestParameterNames = null;
		List<String> ajaxRequestParameterValues = new ArrayList<String>();
		
		if(ajaxType != null && !ajaxType.equals("")){
			ajaxDataRetriver = AjaxFactory.getAjaxDataRetriver(ajaxType);
			ajaxRequestParameterNames = ajaxDataRetriver.getAjaxRequestParameterNames();
			for (String ajaxRequestParameterName : ajaxRequestParameterNames) {
				ajaxRequestParameterValues.add(request.getParameter(ajaxRequestParameterName));
			}
			ajaxVos = ajaxDataRetriver.getAjaxVo(ajaxRequestParameterValues);
		}
		
		response.setContentType("application/json");
		mapper.writeValue(response.getOutputStream(), ajaxVos);
		
	}
}
