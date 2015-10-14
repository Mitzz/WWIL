<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page language="java" contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="../../WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="../../WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="minutesFormatConverter" prefix="formatTime"%>
<%@ taglib uri="numberWithComma" prefix="formatNumber"%>
<%@ page isELIgnored="false" %>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/GridBifurcationReport.css"/>

<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.text.*" %>
        <%
            response.setHeader("Pragma", "no-cache");
            response.getOutputStream().flush();
            response.getOutputStream().close();
        %>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
        <LINK href="<%=request.getContextPath()%>/resources/resources\deluxe-menu.files/style.css" type=text/css rel=stylesheet>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>
        
        <style type="text/css">
        	.generation, .operatingHour, .lullHour, .capacityFactor, .mavail, .gavail, .wecName{
        		text-align: center;
        	}
        	.capacityFactor,.mavail, .gavail,.wecName{
        		width:10%;
        	}
        	
        	.operatingHour, .generation, .lullHour{
        		width: 15%;
        	}
        	
        </style>
        <script type="text/javascript">
        	$(document).ready(function(){
        		
        		
        	});
        </script>

<title>IREDA WEC-wise Report for a day</title>
</head>
<body>
	<div align="center">
	<table border="1" cellpadding="0" cellspacing="0" width="80%" >
		<thead>
        	<tr class=TableTitleRow>
				<th colspan="7">Project No : ${ireda.projectNo} of State - ${state}</td>
			</tr>
			<tr class=TableTitleRow>
				<th colspan="7">WEC-wise Power Generation for ${date} </td>
			</tr>
        	<tr class=TableTitleRow>
				<th>Wec Name</th>
				<th>Generation <br>(KWH)</th>
				<th>Operating Hour <br>(HH:MM)</th>
				<th>Lull Hours <br>(HH:MM)</th>
				<th>Capacity Factor(%)</th>
				<th>Machine Avail(%)</th>
				<th>Grid Avail(%)</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="stateDetails" items="${ ireda.stateWiseWECWiseDetailsForManyDays }" varStatus="stateCounter">
			<tr>
			<c:forEach var="wecDetails" items="${ stateDetails.value.oneWECManyDatesWECWiseTotalList }" varStatus="wecCounter">
			<tr class="TableRow1">
				<td class="wecName">${ireda.wecIdNameMapping[wecDetails.wecId] }</td>	
				<td class="generation"><formatNumber:convert number="${ wecDetails.generation }" /></td>
				<td class="operatingHour"><formatTime:convert minutes="${wecDetails.operatingHour }" /></td>	
				<td class="lullHour"><formatTime:convert minutes="${wecDetails.lullHours }" /></td>
				<td class="capacityFactor">${wecDetails.capacityFactor}</td>	
				<td class="mavail">${wecDetails.mavial }</td>
				<td class="gavail">${wecDetails.gavial }</td>
			</tr>
			</c:forEach>
			<tr class=TableTitleRow>
				<td>Total</td>
				<td class="generation"><formatNumber:convert number="${stateDetails.value.generation}" /></td>
				<td class="operatingHour"><formatTime:convert minutes="${stateDetails.value.operatingHour}" /></td>
				<td class="lullHour"><formatTime:convert minutes="${stateDetails.value.lullHours}" /></td>
				<td class="capacityFactor">${stateDetails.value.capacityFactor}</td>
				<td class="mavail">${stateDetails.value.mavial}</td>
				<td class="gavail">${stateDetails.value.gavial}</td>
			</tr>
			</c:forEach>
		</tbody>	
	</table>
	</div>
</body>
</html>