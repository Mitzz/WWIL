<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.enercon.reports.pojo.PESDataStorage"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%> 
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "WEC PES File Status.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
%>  
       

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.*"%>
 
<HTML> 
<HEAD>  
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<TITLE>WEC PES File Status</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=windows-1252">

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	
%>
<style type="text/css">
	.data{
		text-align: center;	
		width:  100%;
		
	}
	.title{
		text-align: center;	
		width:  100%;
		font-weight: bolder;
		background-color: lightgreen;
	}
</style>
</HEAD>
<BODY>

<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
<tr>
		<td class='title'>WEC Name</td>
		<td class='title'>Location No</td>
		<td class='title'>Plant No</td>
		<td class='title'>PES Status</td>
</tr>
<logic:iterate name="PESDataStorage" id="PESDataId">
<tr>
	<td class="data"><bean:write name="PESDataId" property="wecName"/></td>		
	<td class="data"><bean:write name="PESDataId" property="locationNo"/></td> 
	<td class="data"><bean:write name="PESDataId" property="plantNo"/></td>
	<td class="data"><bean:write name="PESDataId" property="pesStatus"/></td>
</tr>
</logic:iterate>
</BODY>
</HTML>
