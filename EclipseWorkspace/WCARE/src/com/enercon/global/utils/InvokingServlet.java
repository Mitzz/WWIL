package com.enercon.global.utils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InvokingServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			System.out.println("WCARE Invoking Servlet Started");
//			productionCode();
//			 test();
			System.out.println("WCARE Invoking Servlet Ended");
		} catch (Exception e) {
			System.out.println("Exception in init Method of InvokingServlet");
			e.printStackTrace();
		}
	}

	private void productionCode() throws Exception {
		new CallSchedule();
	}

	private void test() throws Exception {
//		new CallSchedulerForMissingScadaData().callTimer();
//		new CallSchedulerForSendingMail().sendMail("09/09/2014");// todays date;

		System.out.println("Invoking Servlet with Test Code");
		new Thread(new Runnable() {

			public void run() {
				try {
//					 new CallSchedulerForSendingMail().sendIPPGroupMail("02-OCT-2015");
					 new CallSchedulerForSendingMail().sendMail("04-OCT-2015");
//					new CallJobToPushScadaData().execute(null);
					// new CallJobToSentMissingScadaData().execute(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
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