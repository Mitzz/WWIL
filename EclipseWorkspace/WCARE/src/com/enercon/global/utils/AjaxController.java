package com.enercon.global.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enercon.ajax.AjaxDao;

/**
 * Servlet implementation class AjaxController
 */
public class AjaxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ajaxType = request.getParameter("ajaxType");
		//System.out.println("Ajax Request Type:" + ajaxType);
		String dataInXMLFormat = "";
		if(ajaxType != null && !ajaxType.equals("")){
			if(ajaxType.equals("wecIdWecDescriptionBasedOnCustomerId")){
				String customerId = request.getParameter("cust_id");
				dataInXMLFormat = new AjaxDao().getWECIdNameBasedOnCustomerId(customerId);
			}
			if(ajaxType.equals("plantNoBasedOnLocationNo")){
				String locationNo = request.getParameter("locationNo");
				dataInXMLFormat = new AjaxDao().getPlantNoBasedOnLocationNo(locationNo);
			}
		}
	    response.setContentType("text/xml");
	    response.getWriter().write(dataInXMLFormat);
	}

}
