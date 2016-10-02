//Back Before Transaction Population Using TransactionMasterVo
package com.enercon.security.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.enercon.dao.master.LoginMasterDao;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.security.utils.SecurityUtils;
import com.enercon.service.TransactionMasterService;

public class SecurityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static Logger logger = Logger.getLogger(SecurityServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//System.out.println("");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType(CONTENT_TYPE);
		DynaBean dynaBean = GlobalUtils.getDynaBean(request);
		SecurityUtils secUtil = new SecurityUtils();
		
		String ipadd = "";
		String iphost = "";
		if (request.getRemoteAddr() != null)
			ipadd = request.getRemoteAddr();

		if (request.getRemoteHost() != null)
			iphost = request.getRemoteHost();
		
		logger.trace("DynaBean:" + dynaBean);
		logger.trace("Client IP Addr: " + request.getRemoteAddr() + ", Client IP Host: " + request.getRemoteHost());
		
		try {
			String sLoginID = dynaBean.getProperty("sLoginID").toString().toUpperCase(); 
			String sPassWord = dynaBean.getProperty("sPassword").toString();
			String sroleID = null;
			
			//LoginMasterVo has RoleMasterVo
			LoginMasterVo loginMasterVo = new LoginMasterDao().get(sLoginID, sPassWord);
			
			boolean isLoginValid = loginMasterVo != null;
			boolean isLoginActive = false;
			
			if(isLoginValid) isLoginActive = loginMasterVo.getActive().equals("1");
			
			if (isLoginValid && isLoginActive) {
				logger.trace("if block 1");
				sroleID = loginMasterVo.getRole().getId();
				String roleName = loginMasterVo.getRole().getName();
				
				HttpSession session = request.getSession(true);
				session.setAttribute("SESSION_EXISTS", "true");
				session.setAttribute("loginID", sLoginID);
				session.setAttribute("pwd", sPassWord);
				session.setAttribute("Name", loginMasterVo.getLoginDescription());
				session.setAttribute("RoleID", sroleID);
				session.setAttribute("LoginType", loginMasterVo.getLoginType());
					
				String loginRoleHistoryId = "";
				loginRoleHistoryId = secUtil.insertLoginHistory(sLoginID,sPassWord, sroleID, ipadd,iphost);
				logger.trace("loginRoleHistoryId->secUtil.insertLoginHistory(sLoginID, sPassWord, sroleID,ipadd,iphost):" + loginRoleHistoryId);
				
				session.setAttribute("Tracker", loginMasterVo.getPassword() + ":" + loginRoleHistoryId);
				if (loginMasterVo.getLoginType().equals("C") && sLoginID.equals("IREDA") && sPassWord.equals("iredapwd")) {
					logger.trace("if block 5");
					include("/ERDAmain.jsp", request, response);
				}
				else {
					logger.trace("else block 5");

					List roleTranList = secUtil.getAllTransactions(sroleID);
//					session.setAttribute("transactions", TransactionMasterService.getInstance().get(loginMasterVo.getRole()));
					
					session.setAttribute("RoleName", roleName);
					session.setAttribute("transactionList", roleTranList);
					
					GlobalUtils.displayVectorMember(roleTranList);
					GlobalUtils.displaySessionAttribute(session);
					
					request.setAttribute("LoggedIn", "TRUE");

					if (secUtil.isPasswordChange(sLoginID)) {
						logger.trace("if block 11");
						forceToChangePassword(request, response);
					} else {
						logger.trace("else block 11");
						if (loginMasterVo.getLoginType().equals("C")) {
							logger.trace("if block 12");
							String msgg = secUtil.getCustinformation(sLoginID);
							logger.trace("msgg: " + msgg);
							if (msgg.equals("Informationexist")) {
								logger.trace("if block 13");
								List custtype = secUtil.getcustomerdetails(sLoginID);
								logger.trace("custtype: " + custtype);
								session.setAttribute("custtypee", custtype);
								include(request, response);
							} else {
								logger.trace("else block 13");
								include("/ManageProfile.jsp", request, response);//Redirected to '/ThankOut.jsp'
							}
						} else {
							logger.trace("if block 12");
							include(request, response);
						}
					}
				}
			} else {
				logger.trace("else block 1");
				if(isLoginValid)
					if(isLoginActive) 	invalidDataHandler(request,response, "Role Not Defined");
					else 					invalidDataHandler(request,response, loginMasterVo.getRemarks());
				else 						invalidDataHandler(request,response, "Invalid User Id/Password. Try Again.");
			}
		} catch (Exception ex) {
			logger.warn(request.getSession() == null ? "Snull" : request.getSession().getAttribute("loginID"));
			logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
			try {
				include("/Error.jsp", request, response);
			} catch (Exception e) {
				logger.warn(request.getSession() == null ? "Snull" : request.getSession().getAttribute("loginID"));
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			}
		}
	}
	
	public void invalidDataHandler(HttpServletRequest request, HttpServletResponse response, String errorMessage) throws Exception{
		HttpSession session = request.getSession(false);
		logger.trace("Entering.....");
		session.setAttribute("valid", "false");
		session.setAttribute( "ERROR_MSG", "<font class='errormsgtext'>" + errorMessage + "</font>");
		include("/index.jsp", request, response);
		logger.trace("Leaving.....");
	}

	public void include(String sPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.trace("Entering.....");
		logger.trace("sPath: " + sPath);
		ServletConfig config = getServletConfig();
		ServletContext context = config.getServletContext();
		context.getRequestDispatcher(sPath).include(request, response);
		logger.trace("Leaving.....");
	}
	
	public void include(HttpServletRequest request, HttpServletResponse response) throws Exception {
		include("/main.jsp", request, response);
//		include("/mainN.jsp", request, response);
	}
	
	public void forceToChangePassword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		include("/ForceToChangePassword.jsp", request, response);
	}
}
