<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.security.dao.SecurityDao"%>
<%@ page import="java.util.*"%>
<%@page import="com.enercon.siteuser.dao.SiteUserDao"%>
<%@page import="com.enercon.admin.dao.AdminDao"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

</head>
<body>

<%
String msg="";
   SecurityDao secutils = new SecurityDao();
   msg = secutils.MonthSalaryTransfer();
%>
<script type="text/javascript">
	alert("Done");
</script>
</body>
</html>
