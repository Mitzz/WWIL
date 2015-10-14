<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Missing Scada Data</title>
<meta http-equiv=Content-Type content=text/html; charset=UTF-8>
<style type=text/css>
.center-align{
	text-align: center;
}
.light-gray{
	background-color: #d3d3d3;
}
.right{
	text-align: right;
	padding-right: 3px;
}

.left{
	text-align: left;
	padding-left: 3px;
}
.table-spacing-border{
	border-spacing: 0;
	border-width: 2px;
	border-style: solid;
}
.header{
	background-color: #a8a8a8;
	text-align: center;
	font-weight: bolder;
}
.data{
	border-width: 1px;
	border-style: solid;
}

.half-width{
	width: 50%;
}
</style>
</head>
<body>
	<c:set var = "totalWecs" scope="page" value="${0}"/>
	<c:set var = "srNo" scope="page" value="${0}"/>
	<c:set var = "class" scope="page" value=""/>
	
	<table class="table-spacing-border" width=50% align="center">
		<tr class="header">
			<td colspan='2' class="data">
				Missing SCADA Data for  ${ date }: Total WECs Statewise
			</td>
		</tr>
		<tr class="header">
			<td class="data">State Name</td>
			<td class="data">Total WECs</td>
		</tr>
		<c:forEach items="${missingScadaReport.stateWise}" var="element">
		<tr class="center-align">
			<td class="data">${ element.key.name }</td>
			<td class="data">${ element.value }</td>
			<c:set var="totalWecs" scope="page" value="${totalWecs + element.value}"/>
		</tr>
		</c:forEach>
		<tr class="header">
			<td class="data"> Grand Total</td>
			<td class="data">${totalWecs}</td>
		</tr>
	</table>
	<br>	
	<table class="table-spacing-border" width=100%>
		<tr>
			<td colspan='9' class="data header">
				Missing SCADA Data for ${ date }: All WECs Details Statewise
			</td>
		</tr>
		<tr class="header">
			<td class = "data" width=5%>Sr No.</td>
			<td class = "data" width=12%>State Name</td>
			<td class = "data" width=15%>Area Name</td>
			<td class = "data" width=15%>Site Name</td>
			<td class = "data" width=10%>Location No</td>
			<td class = "data" width=10%>Plant No</td>
			<td class = "data" width=15%>WEC Name</td>
			<td class = "data" width=10%>Missing Days</td>
			<td class = "data" width=18%>Technical No</td>
		</tr>
		<c:forEach items="${missingScadaReport.missingScadaDataVos}" var="element">
		<c:set var = "srNo" value="${srNo + 1}"/>
		<c:choose>
    		<c:when test="${srNo % 2 == 0}">
    			<c:set var = "class" value="light-gray"/>
        	</c:when>    
    		<c:otherwise>
        		<c:set var = "class" value=""/>
    		</c:otherwise>
		</c:choose>
		<tr class="${class}">
			<td class="data right">${srNo}</td>
			<td class="data left">${element.masterVo.stateName}</td>
			<td class="data left">${element.masterVo.areaName}</td>
			<td class="data left">${element.masterVo.siteName}</td>
			<td class="data right">${element.masterVo.locationNo}</td>
			<td class="data right">${element.masterVo.plantNo}</td>
			<td class="data left">${element.masterVo.name}</td>
			<td class="data right">${element.noOfDaysMissing}</td>
			<td class="data right">${element.masterVo.technicalNo}</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>