package com.enercon.global.utils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.enercon.model.graph.Graph;

public class InvokingServlet extends HttpServlet {
	private final static Logger logger = Logger.getLogger(InvokingServlet.class);
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		config.getServletContext().setAttribute("contextPath", "/WCARE");
		try {
			System.out.println("WCARE Invoking Servlet Started");
//			productionCode();
//			 test();
			System.out.println("WCARE Invoking Servlet Ended");
		} catch (Exception e) {
			System.out.println("Exception in init Method of InvokingServlet");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}

	private void productionCode() throws Exception {
		/*new Thread(new Runnable() {

			public void run() {
				Graph.getInstance();
			}
			
		}).start();*/
		
		new CallSchedule();
	}

	private void test() throws Exception {
//		new CallSchedulerForMissingScadaData().callTimer();
//		new CallSchedulerForSendingMail().sendMail("09/09/2014");// todays date;

		logger.debug("Invoking Servlet with Test Code");
		new Thread(new Runnable() {

			public void run() {
				try {
					Graph G = Graph.getInstance();
					G.log();
					/*G.flush();
					G.initialize();*/
//					 new CallSchedulerForSendingMail().sendIPPGroupMail("02-OCT-2015");
					 new CallSchedulerForSendingMail().sendMail("06-MAR-2016");
//					new CallJobToPushScadaData().execute(null);
					// new CallJobToSentMissingScadaData().execute(null);
				} catch (Exception e) {
					logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
				}
			}
		}).start();
		logger.debug("Invoking Servlet with Test Code");
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType(CONTENT_TYPE);
		// PrintWriter out = response.getWriter();
		// out.println("<html>");
		// out.println("<head><title>InvokingServlet</title></head>");
		// out.println("<body>");
		// out.println("<p>The servlet has received a GET. This is the reply.</p>");
		// out.println("</body></html>");
		// out.close();
	}
}