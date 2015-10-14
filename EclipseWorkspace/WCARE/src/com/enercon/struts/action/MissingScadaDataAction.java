package com.enercon.struts.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.enercon.model.missingScadaData.MissingScadaDataReportVo;

/**
 * Servlet implementation class MissingScadaDataAction
 */
public class MissingScadaDataAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MissingScadaDataAction() {
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
		String date = request.getParameter("date");
		MissingScadaDataReportVo dataReport = new MissingScadaDataReportVo(date);
		
		try {
			dataReport.populateData();
			request.setAttribute("date", date);
			request.setAttribute("missingScadaReport", dataReport);
			RequestDispatcher rd = request.getRequestDispatcher("/MissingScadaData.jsp");
			rd.include(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
