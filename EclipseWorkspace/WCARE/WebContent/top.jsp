<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">

</head>
<body>
<table border="0" width="970" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
	<tr>
		<td colspan="2" bgcolor="#0a4224" align="right" height="27" width="780" class="WhiteLink">
			&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/main.jsp" class="WhiteLink">Home</a>&nbsp;&nbsp;|
			&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/ChangePassword.jsp" class="WhiteLink" target="myframe">Change Password</a>&nbsp;&nbsp;|
			&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/LogOut.jsp" class="WhiteLink" >Log Out</a>&nbsp;&nbsp;
		</td>
	</tr>
	<tr>
		<td width="235" align="left"><a href="<%=request.getContextPath()%>/main.jsp"><img src="resources/images/Enercon_Logo.GIF" border="0" alt="Click to go on Home Page"></a></td>
		<td width="729" align="right"><a href="<%=request.getContextPath()%>/main.jsp"><img src="resources/images/EnergyForWorld.jpg" border="0" alt="Click to go on Home Page"></a></td>				
	<tr>
</table>
</body>
</html>