package com.enercon.admin.controller;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class AdminServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(AdminServlet.class);

    /**
     * 
     * @throws javax.servlet.ServletException
     * @param config
     */
    @Override
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("ENERCON_DEBUG: AdminServlet: doPost: Processing the request through service.");
        response.setContentType("text/html");
        logger.debug("ENERCON_DEBUG: AdminServlet: service: Entered the method.");
        try {
            String EmployeeID = "";
            //DynaBean dynaBean = GlobalUtils.getDynaBean(request);
            String inputType = request.getParameter("Admin_Input_Type").toString();
           // System.out.println("Admin Input is :" + inputType);
            logger.debug("ENERCON_DEBUG: AdminServlet: service: Input Type is" + inputType);
            HttpSession session = request.getSession(true);
            if (session == null) {

                request.setAttribute("ERROR_MSG", 
                                     "Your session has expired. Please Login Again.");
                //System.out.println("debugger@pallav : AdminServlet : Session loop : " + 
                                  // session);
                forward("/SessionExpired.jsp", request, response);
            } else {
                if (session.getAttribute("loginID") != null) {
                    EmployeeID = session.getAttribute("loginID").toString();
                }
            }
        } catch (Exception ex) {
        	logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
        }
    }

    /**
     * 
     * @throws java.lang.Exception
     * @param response
     * @param request
     * @param sPath
     */
    private void forward(String sPath, HttpServletRequest request, 
                         HttpServletResponse response) throws Exception {
        request.getRequestDispatcher(sPath).forward(request, response);

    }
}
