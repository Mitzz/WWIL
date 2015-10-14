<%@ page import="java.io.*,java.sql.*,com.enercon.global.utils.JDBCUtils,com.enercon.global.utils.CodeGenerate,java.sql.CallableStatement,java.sql.PreparedStatement,java.sql.ResultSet,java.sql.SQLException" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.io.File"%>
<%@ page import="javax.naming.Context"%>
<%@page import="com.enercon.security.utils.*"%>
<%@page import="com.enercon.global.utils.SendMail"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<title>Password Confirmation</title>

<STYLE type=text/css>
H2 {
	FONT-WEIGHT: normal; FONT-SIZE: 1.3em; MARGIN-LEFT: 10px; COLOR: #009
}
</STYLE>

</head>
<body>
<%
	//JDBCUtils conmanager = new JDBCUtils();
	//Connection conn = conmanager.getConnection();
	String loginid=request.getParameter("VUserId");
	String emailid=request.getParameter("VEmailId");
	SecurityUtils su=new SecurityUtils();
	String msg=su.getPwd(loginid,emailid);  
	boolean sentemail=false;
   if(msg.length()<25)
   {   String pwd=msg;
	   String sentmail=su.sentMsg(emailid,loginid,pwd); 
	   sentemail=true;

%>
	   
	    <span class="TableTitle">Your password has been sent to your email id-"<%=emailid%>", Please Check...</span>

<% 
   } else {
%>
	  
 <span class="TableTitle">User Id and Email Id does not match with our record, Please Contact to Enercon Customer Care Team.</span>

<% 
	}
%>

</body>
</html>