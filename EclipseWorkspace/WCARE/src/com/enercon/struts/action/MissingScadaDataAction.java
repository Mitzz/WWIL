package com.enercon.struts.action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.enercon.model.missingScadaData.MissingScadaDataReportVo;

public class MissingScadaDataAction extends HttpServlet {
	private static final Logger logger = Logger.getLogger(MissingScadaDataAction.class);
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
		logger.debug("kjdfs");
		String date = request.getParameter("date");
		logger.debug("kjdfs");
		MissingScadaDataReportVo dataReport = new MissingScadaDataReportVo(date);
		logger.debug("kjdfs");
		try {
			logger.debug("kjdfs");
			dataReport.populateData();
			logger.debug("kjdfs");
			request.setAttribute("date", date);
			request.setAttribute("missingScadaReport", dataReport);
			RequestDispatcher rd = request.getRequestDispatcher("/MissingScadaData.jsp");
			rd.include(request, response);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n");
			
		}
		
	}

}
