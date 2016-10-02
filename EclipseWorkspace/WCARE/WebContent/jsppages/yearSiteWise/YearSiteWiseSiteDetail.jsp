<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="minutesFormatConverter" prefix="timeFormatter"%>
<%@ taglib uri="numberWithComma" prefix="numberFormatter"%>
<c:set var = "class" scope="page" value=""/>

<jsp:useBean id="monthNames" class="java.text.DateFormatSymbols" />
<c:set value="${monthNames.shortMonths}" var="months" />
<c:set value="" var="month" />
<HTML>
<HEAD>    
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%> 

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<style type="text/css" media="print">
.printbutton {
  visibility: hidden;
  display: none;
}
</style>


<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>

<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<CENTER>
<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>

		<tr>
		<%-- <a href="<c:url value="/yearSiteWiseSiteDetail.do"/>?stateId=<c:out value="${ siteDetail.state.id }" />&siteId=<c:out value="${ siteDetail.site.id }" />&customerId=<c:out value="${ siteDetail.customer.id }"/>&fiscalYear=<c:out value="${ fiscalYear }" />" target="_blank"> ${ siteDetail.site.name }</a> --%>
		
			<TD align="left" colspan="2">
				<input type="button" class="printbutton" value="Back" onClick=location.href="<c:url value="/yearSiteWiseCumulative.do"/>?customerId=<c:out value="${ customerId }"/>&fiscalYear=<c:out value="${ fiscalYear }" />" />
			</TD>

			<td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>
			<td colspan="2" align="center"></td>
				
			<td align="right" colspan="2">
				<input type="button" value="Print" onClick="window.print()" class="printbutton" />
			<c:choose>
				<c:when test="${ not empty siteId }">	
				<input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="ExportSiteCumulativeReport.jsp?stateid=${ stateId }&siteid=${ siteId }&id=${ customerId }&type=${ type }&rd=${ rd }" />
				</c:when>
				<c:otherwise>
				<input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="ExportStateCumulativeReport.jsp?stateid=${ stateId }&id=${ customerId }&type=${ type }&rd=${ rd }" />
				</c:otherwise>
			</c:choose>
			</td>
		</TR>
		<TR>
			<td colspan="6"></td>
		</tr>

	</TBODY>
</TABLE>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD class=SectionTitle colspan="6" align="center" noWrap>Generation Report&nbsp;&nbsp;&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<P><BR>
<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
	<TBODY>
		<tr class=TableTitle>
			<td vAlign=middle class=TableCell align=center width=100% colspan="6">${wecsMonthWise.customer.name}</td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell width=100% align=center colspan="6"></td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell align=center width=100% colspan="6"></td>
		</tr>

		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell1 align=left width=20%>Location:</td>
			<c:choose>
				<c:when test="${ not empty siteId }">
			<td vAlign=middle align=left width=38% colspan=2>${wecsMonthWise.site.name} - ${wecsMonthWise.site.state.name}</td>
				</c:when>
				<c:otherwise>
			<td vAlign=middle align=left width=38% colspan=2>${wecsMonthWise.state.name}</td>
				</c:otherwise>
			</c:choose>
			<td vAlign=middle class=TableCell1 align=left width=18%>Total WEC:</td>
			<td vAlign=middle align=left width=24% colspan=2>${fn:length(wecsMonthWise.wecs)} </td>
		<tr class=TableSummaryRow>
			<td vAlign=middle width=100% align=left colspan="6"></td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
			<%-- <td vAlign=middle align=left width=38% colspan=2><fmt:formatNumber value="${wecsMonthWise.capacity/1000}" maxFractionDigits="0" /> MW</td> --%>
			<td vAlign=middle align=left width=38% colspan=2>${wecsMonthWise.capacity/1000} MW</td>
			
			<td vAlign=middle class=TableCell1 align=left width=18%>Fiscal Year:</td>
			<td vAlign=middle align=left width=24% colspan=2>${ fiscalYear } - ${ fiscalYear + 1 }</td>
		</tr>

	</TBODY>
</TABLE>

<P></P>
<SPAN class=TableTitle>Month Wise Data </SPAN><BR>
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
	<TBODY>
		<TR>
			<TD></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE width="90%" bgColor=#555555 cellSpacing=1 cellPadding=2 width="100%" border=0>
	<TBODY>
		<TR class=TableTitleRow>
			<TD class=TableCell width="14.28%">Month</TD>
			<TD class=TableCell width="14.28%">Generation</TD> 
			<TD class=TableCell width="14.28%">Operating Hours </TD>
			<TD class=TableCell width="14.28%">Lull Hours </TD>
			<TD class=TableCell width="14.28%">Capacity Factor(%) </TD>
			<TD class=TableCell width="14.28%">Machine Availability(%)</TD>
			<TD class=TableCell width="14.28%">Grid Availability(%)</TD>
		</TR>
		<c:forEach var="parameter" items="${wecsMonthWise.monthData}" >  
		<c:set var = "counter" value="${counter + 1 }" />
		<tr class=${counter % 2 == 0 ? "TableRow2" : "TableRow1"}>
			<c:set value="${ parameter.key.monthOfYear }" var="month" />
			<TD class=TableCell>${ months[month - 1] } - ${ parameter.key.year }</TD>
			
			<td class=TableCell><numberFormatter:convert number="${ parameter.value.generation }"/></td>
			<td class=TableCell><timeFormatter:convert minutes="${ parameter.value.operatingHour }" /></td>
			<td class=TableCell><timeFormatter:convert minutes="${parameter.value.lullHour}" /></td>
			<td class=TableCell>${parameter.value.cf}</td>
			<td class=TableCell>${parameter.value.ma}</td>
			<td class=TableCell>${parameter.value.ga}</td>
		</tr>	           
		</c:forEach>	        
		
		<tr class=TableTitleRow>                               
			<td class=TableCell>Total</td>
			<td class=TableCell><numberFormatter:convert number="${wecsMonthWise.total.generation}"/></td>
			<td class=TableCell><timeFormatter:convert minutes="${wecsMonthWise.total.operatingHour}" /></td>
			<td class=TableCell><timeFormatter:convert minutes="${wecsMonthWise.total.lullHour}" /></td>
			<td class=TableCell>${wecsMonthWise.total.cf}</td>
			<td class=TableCell>${wecsMonthWise.total.ma}</td>
			<td class=TableCell>${wecsMonthWise.total.ga}</td>
		</tr>    
        
	</TBODY>
</TABLE>
<P></P>
</CENTER>
</BODY>
</HTML>
