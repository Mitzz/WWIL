<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false" %>
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
		file = "Area Wise Weekly Availability.xls";
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
<META http-equiv="Content-Type"
	content="text/html; charset=windows-1252">

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	
%>
<%
	String fromDate=request.getAttribute("fromDate").toString();
	String toDate=request.getAttribute("toDate").toString();
%>
<style type="text/css">


.title {
	text-align: center;
	width: 100%;
	font-weight: bolder;
	background-color: lightgreen;
}
</style>
</HEAD>
<BODY>

	<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
		<TR>
			<td colspan="6" vAalign="middle" align="center"><b>Area Wise Weekly Availability from <%=fromDate%> to  <%=toDate%></b></td>
		</TR>
		<TR>
			<td vAlign="middle" align="center" width: 15%;><b>State</b></td>
			<td vAlign="middle" align="center" width: 15%;><b>Area</b></td>
			<td vAlign="middle" align="center" width: 10%;><b>Total WECS</b></td>
			<td vAlign="middle" align="center" width: 10%;><b>MA</b></td>
			<td vAlign="middle" align="center" width: 10%;><b>GA</b></td>
			<td vAlign="middle" align="center" width: 10%;><b>SSA</b></td>			
		</TR>
		<c:forEach items="${ReportData}" var="rd" varStatus="status" >
		   	<c:forEach items="${rd.oneStateInfo}" var="rd1" varStatus="status1" >
		   		<c:forEach items="${rd1.oneAreaInfo}" var="rd2" varStatus="status1" >
		  		 		<tr>
							<td vAlign="middle" align="center">${rd2.stateName}</td>
							<td  vAlign="middle" align="center">${rd2.areaName}</td>
							<td  vAlign="middle" align="center">${rd2.wecCount}</td>
							<td  vAlign="middle" align="center">${rd2.machineAvailability}</td>
							<td  vAlign="middle" align="center">${rd2.gridAvailability}</td>
							<td  vAlign="middle" align="center">${rd2.subStationAvailability}</td>				
				 		</tr>
			 	</c:forEach>
		  		<tr bgcolor="#EEEEDD">	
		  		<td></td>	  	
		   			<td  vAlign="middle" align="center"><B> Total</B></td>
					<td  vAlign="middle" align="center"><b>${rd1.totalWecCountForState}</b></td>
					<td  vAlign="middle" align="center"><b>${rd1.totalMAForState}</b></td>
					<td  vAlign="middle" align="center"><b>${rd1.totalGAForState}</b></td>
					<td  vAlign="middle" align="center"><b>${rd1.totalSACountForState}</b></td>				
		 		</tr>
   		 	</c:forEach>
   		 	<tr bgcolor="#EEEEDD">	
   		 	<td></td>	  	
		   	<td   vAlign="middle" align="center"><B>Grand Total</B></td>
			<td   vAlign="middle" align="center"><b>${rd.overAllWecCount}</b></td>
			<td   vAlign="middle" align="center"><b>${rd.overAllMA}</b></td>
			<td   vAlign="middle" align="center"><b>${rd.overAllGA}</b></td>
			<td   vAlign="middle" align="center"><b>${rd.overAllSA}</b></td>				
		 </tr>	
		  </c:forEach>	
		</TABLE>
</BODY>
</HTML>
