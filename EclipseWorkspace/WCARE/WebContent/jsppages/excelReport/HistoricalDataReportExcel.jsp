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
		file = "WEC Data Report.xls";
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
		<TR>
			<td vAlign="middle" align="center" rowspan="2"><b>State</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Site</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>WEC Model</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Date</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Customer Name</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>WEC Name</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Cumulative Generation</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Generation(Period)</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Cumulative Operating Hours</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Operating Hours(Period)</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Lull Hours</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Machine Fault</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Machine Shutdown</b></td>
			<td vAlign="middle" align="center" colspan="2"><b>Internal (WEC to VCB)</b></td>
			<td vAlign="middle" align="center" colspan="2"><b>External (VCB to WWIL_SS)_E1</b></td>
			<td vAlign="middle" align="center" colspan="2"><b>WWIL SS_E2</b></td>
			<td vAlign="middle" align="center" colspan="2"><b>WWIL S/S onwards (EHV LINE)_E3</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Customer Scope</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Grid Trip Count</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Total Hour</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Capacity Factor</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Machine Availability</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Internal Grid Availability</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>External Grid Availability</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Grid Availability</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Resource Availability</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>FM</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Load Shedding hours</b></td>
			<td vAlign="middle" align="center" rowspan="2"><b>Remarks</b></td>
		</tr>
		<tr>
			<td vAlign="middle" align="center"><b>Fault</b></td>
			<td vAlign="middle" align="center"><b>Shutdown</b></td>
			<td vAlign="middle" align="center"><b>Fault</b></td>
			<td vAlign="middle" align="center"><b>Shutdown</b></td>
			<td vAlign="middle" align="center"><b>Fault</b></td>
			<td vAlign="middle" align="center"><b>Shutdown</b></td>
			<td vAlign="middle" align="center"><b>Fault</b></td>
			<td vAlign="middle" align="center"><b>Shutdown</b></td>
		</tr>
		<logic:iterate name="ReportData" id="DataId">
		<tr>
			<td class="data"><bean:write name="DataId" property="stateName"/></td>
			<td class="data"><bean:write name="DataId" property="siteName"/></td>
			<td class="data"><bean:write name="DataId" property="wecType"/></td>
			<td class="data"><bean:write name="DataId" property="date"/></td>
			<td class="data"><bean:write name="DataId" property="customerName"/></td>
			<td class="data"><bean:write name="DataId" property="wecDescription"/></td>
			<td class="data"><bean:write name="DataId" property="cumulativeGeneration"/></td>
			<td class="data"><bean:write name="DataId" property="generation"/></td>
			<td class="data"><bean:write name="DataId" property="cumulativeOperatingHour"/></td>
			<td class="data"><bean:write name="DataId" property="operatingHour"/></td>
			<td class="data"><bean:write name="DataId" property="lullHour"/></td>
			<td class="data"><bean:write name="DataId" property="machineFault"/></td>
			<td class="data"><bean:write name="DataId" property="machineShutdown"/></td>
			<td class="data"><bean:write name="DataId" property="internalFault"/></td>
			<td class="data"><bean:write name="DataId" property="internalShutdown"/></td>
			<td class="data"><bean:write name="DataId" property="e1Fault"/></td>
			<td class="data"><bean:write name="DataId" property="e1Shutdown"/></td>
			<td class="data"><bean:write name="DataId" property="e2Fault"/></td>
			<td class="data"><bean:write name="DataId" property="e2Shutdown"/></td>
			<td class="data"><bean:write name="DataId" property="e3Fault"/></td>
			<td class="data"><bean:write name="DataId" property="e3Shutdown"/></td>
			<td class="data"><bean:write name="DataId" property="customerScope"/></td>
			<td class="data"><bean:write name="DataId" property="gridTripCount"/></td>
			<td class="data"><bean:write name="DataId" property="faultHours"/></td>
			<td class="data"><bean:write name="DataId" property="capacityFactor"/></td>
			<td class="data"><bean:write name="DataId" property="machineAvailability"/></td>
			<td class="data"><bean:write name="DataId" property="gridInternalAvailability"/></td>
			<td class="data"><bean:write name="DataId" property="gridExternalAvailability"/></td>
			<td class="data"><bean:write name="DataId" property="gridAvailability"/></td>
			<td class="data"><bean:write name="DataId" property="resourceAvailability"/></td>
			<td class="data"><bean:write name="DataId" property="wecSpecialShutdown"/></td>
			<td class="data"><bean:write name="DataId" property="loadShedding"/></td>
			<td class="data"><bean:write name="DataId" property="remarks"/></td>
			
		</tr>
		</logic:iterate>
		</TABLE>
</BODY>
</HTML>
