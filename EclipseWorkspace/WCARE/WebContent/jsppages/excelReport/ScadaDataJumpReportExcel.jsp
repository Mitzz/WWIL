<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.enercon.struts.pojo.ScadaDataJump"%>
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
		file = "Scada Data Report.xls";
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
<style type="text/css">
.data {
	text-align: center;
	width: 100%;
}

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
		<tr>
			<td vAlign="middle" align="center" rowspan="1"><b>Date</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>WEC Name</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Customer Name</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>State</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Machine Capacity</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>SCADA Wind Farm Number</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Plant Number </b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Previous Max Cummulative Generation</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Previous Max Cummulative OperatingHrs</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Current Max Cummulative Generation<br></b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Current Max Cummulative OperatingHrs</b><br></td>
			<td vAlign="middle" align="center" rowspan="1"><b>Generation Difference (Current-Previous)</b></td>
			<td vAlign="middle" align="center" rowspan="1"><b>OperatingHrs Difference(Current-Previous)</b></td>
		</tr>
		
		<logic:iterate name="ReportData" id="DataId">
		<tr>
			<td class="data"><bean:write name="DataId" property="readingDate"/></td>
			<td class="data"><bean:write name="DataId" property="wecName"/></td>
			<td class="data"><bean:write name="DataId" property="customerName"/></td>
			<td class="data"><bean:write name="DataId" property="stateName"/></td>
			<td class="data"><bean:write name="DataId" property="machineCapacity"/></td>
			<td class="data"><bean:write name="DataId" property="locationNo"/></td>
			<td class="data"><bean:write name="DataId" property="plantNo"/></td>
			<td class="data"><bean:write name="DataId" property="previousMaxGeneration"/></td>
			<td class="data"><bean:write name="DataId" property="previousMaxOperatingHour"/></td>
			<td class="data"><bean:write name="DataId" property="currentMaxGeneration"/></td>
			<td class="data"><bean:write name="DataId" property="currentMaxOperatingHour"/></td>
			<td class="data"><bean:write name="DataId" property="generationDifference"/></td>
			<td class="data"><bean:write name="DataId" property="operatingHrDifference"/></td>
		</tr>
		</logic:iterate>
		
		</TABLE>
</BODY>
</HTML>
