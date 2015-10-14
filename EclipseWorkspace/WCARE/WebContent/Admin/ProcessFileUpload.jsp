<%@page import="com.enercon.global.utility.MethodClass"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="org.apache.commons.fileupload.DiskFileUpload"%>
<%@ page import="org.apache.commons.fileupload.FileItem"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>

<%@ page import="com.enercon.global.controller.InitServlet"%>
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
	//System.out.println("Content Type ="+request.getContentType());
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
		FileItem fi = (FileItem) itr.next();
		//Check if not form field so as to only handle the file inputs
		//else condition handles the submit button input
		filename = fi.getFieldName();
		//System.out.println("Filename:" + filename);
		if (filename.equals("filestype")) {
			extension = fi.getString();
			//System.out.println(extension);
		}
		if (!fi.isFormField()) {
			//System.out.println(//System.getProperty("user.dir"));
			//System.out.println("NAME: "+fi.getName());
			//System.out.println("SIZE: "+fi.getSize());
			//			//System.out.println("PATH: "+application.getRealPath("/"));
			//System.out.println("Upload file path : "+servlet.getDatabaseProperty("uploadfilepath"));
			//System.out.println("Linux Path : "+servlet.getDatabaseProperty("luploadfilepath"));
			//System.out.println(fi.getOutputStream().toString());
			File tempFileRef = new File(fi.getName());
			String name = tempFileRef.getName();
			String userSeparator = "\\";
			name = name.substring(name.lastIndexOf(userSeparator) + 1,
					name.length());
			//System.out.println("File name:"+ name);
			if (name.toLowerCase().endsWith(extension)) {
				request.setAttribute("filenm", name);
				request.setAttribute("Exceltxt",
						servlet.getDatabaseProperty("uploadfilepath")
								+ "/" + extension + "/" + name);
				//				File fNew = new File(application.getRealPath("/")+"Admin/" + extension + "/",tempFileRef.getName());
				File fNew = new File(
						servlet.getDatabaseProperty("uploadfilepath")
								+ "/" + extension + "/", name);
				//System.out.println(fNew.getAbsolutePath());	            
				fi.write(fNew);
				msg = "<font class='sucessmsgtext'>Upload Successful!</font>";
			} else {
				msg = "<font class='errormsgtext'>Please select "
						+ extension.toUpperCase() + " File</font>";
				request.setAttribute("FileError", "fileerror");
			}
		} else {
			//System.out.println("Field ="+fi.getFieldName());
			request.setAttribute(fi.getFieldName().toString(), fi
					.getString().toString());
		}
	}
	if (request.getAttribute("Exceltxt") == null) {
		request.setAttribute("Exceltxt", "");
	}
	ServletConfig sc = getServletConfig();
	ServletContext context = sc.getServletContext();
	String admin_input_type = (String) request
			.getAttribute("Admin_Input_Type");
	if (admin_input_type.equals("UploadFederData")) {
		context.getRequestDispatcher("/uploadfederdata.do").forward(
				request, response);
	} else if (admin_input_type.equals("UploadEBData")) {
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
