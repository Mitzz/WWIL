<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.global.utility.MethodClass"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<%@ page import="com.enercon.global.controller.InitServlet"%>

<%! private final static Logger logger = Logger.getLogger(JSPErrorLogger.class); %>
<html>
<head>
<%
	if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Process File Upload</title>
</head>
<%
	//logger.debug("Content Type ="+request.getContentType());
	String msg = "";
	String extension = "";
	String filename = "";
	DiskFileUpload fileUpload = new DiskFileUpload();
	// If file size exceeds, a FileUploadException will be thrown
	fileUpload.setSizeMax(100000000);
	InitServlet servlet = new InitServlet();
	
	List fileItems = fileUpload.parseRequest(request);
	Iterator itr = fileItems.iterator();

	while (itr.hasNext()) {
		logger.debug("while loop");
		FileItem fi = (FileItem) itr.next();
		//Check if not form field so as to only handle the file inputs
		//else condition handles the submit button input
		filename = fi.getFieldName();
		logger.debug("Filename:" + filename);
		if (filename.equals("filestype")) {
			logger.debug("if 1");
			extension = fi.getString();
			logger.debug("Extension: " + extension);
		}
		if (!fi.isFormField()) {
			logger.debug("if 2");
			logger.debug("user.dir: " + System.getProperty("user.dir"));
			logger.debug("NAME: "+fi.getName());
			logger.debug("SIZE: "+fi.getSize());
			logger.debug("PATH: "+application.getRealPath("/"));
			logger.debug("Upload file path : "+servlet.getDatabaseProperty("uploadfilepath"));
			//logger.debug("Linux Path : "+servlet.getDatabaseProperty("luploadfilepath"));
			logger.debug("Output: " + fi.getOutputStream().toString());
			File tempFileRef = new File(fi.getName());
			String name = tempFileRef.getName();
			String userSeparator = "\\";
			name = name.substring(name.lastIndexOf(userSeparator) + 1,
					name.length());
			logger.debug("File name:"+ name);
			if (name.toLowerCase().endsWith(extension)) {
				logger.debug("if 3");
				request.setAttribute("filenm", name);
				request.setAttribute("Exceltxt",
						servlet.getDatabaseProperty("uploadfilepath")
								+ "/" + extension + "/" + name);
				//				File fNew = new File(application.getRealPath("/")+"Admin/" + extension + "/",tempFileRef.getName());
				File fNew = new File(servlet.getDatabaseProperty("uploadfilepath") + "/" + extension + "/", name);
				logger.debug(fNew.getAbsolutePath());	            
				fi.write(fNew);
				msg = "<font class='sucessmsgtext'>Upload Successful!</font>";
			} else {
				logger.debug("else 3");
				msg = "<font class='errormsgtext'>Please select "
						+ extension.toUpperCase() + " File</font>";
				request.setAttribute("FileError", "fileerror");
			}
		} else {
			logger.debug("else 2");
			//logger.debug("Field ="+fi.getFieldName());
			request.setAttribute(fi.getFieldName().toString(), fi
					.getString().toString());
		}
	}
	if (request.getAttribute("Exceltxt") == null) {
		request.setAttribute("Exceltxt", "");
	}
	ServletConfig sc = getServletConfig();
	ServletContext context = sc.getServletContext();
	String admin_input_type = (String) request.getAttribute("Admin_Input_Type");
	if (admin_input_type.equals("UploadFederData")) {
		context.getRequestDispatcher("/uploadfederdata.do").forward(
				request, response);
	} else if (admin_input_type.equals("UploadEBData")) {
		logger.debug("s1");
		context.getRequestDispatcher("/uploadebdata.do").forward(
				request, response);
	} else if (admin_input_type.equals("UploadWECData")) {
		context.getRequestDispatcher("/uploadwecdata.do").forward(
				request, response);
	} else if (admin_input_type.equals("UploadWECRemarks")) {
		context.getRequestDispatcher("/uploadwecremarks.do").forward(
				request, response);
	}
%>
<body>
</body>
</html>
