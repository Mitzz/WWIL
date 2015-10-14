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
		file = "WEC Reading Tracker Report.xls";
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
		<td class='title'>Reading Date</td>
		<td class='title'>WEC Name</td>
		<td class='title'>MP Type</td>
		<td class='title'>Activity Type</td>
		<td class='title'>Activity Time</td>
		<td class='title'>Old Value</td>
		<td class='title'>New Value</td>
		<td class='title'>Modified By</td>
</tr>
<logic:iterate name="WECReadingTrackerData" id="DataId">
<tr>
	<td class="data"><bean:write name="DataId" property="readingDate"/></td>		
	<td class="data"><bean:write name="DataId" property="wecName"/></td> 
	<td class="data"><bean:write name="DataId" property="mpDescription"/></td>
	<td class="data"><bean:write name="DataId" property="activityType"/></td>
	<td class="data"><bean:write name="DataId" property="activityTime"/></td>
	<td class="data"><bean:write name="DataId" property="oldValue"/></td>
	<td class="data"><bean:write name="DataId" property="newValue"/></td>
	<td class="data"><bean:write name="DataId" property="modifiedBy"/></td>
	
</tr>
</logic:iterate>
</BODY>
</HTML>
