<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.global.utility.TimeUtility"%>
<%@page import="com.enercon.global.utility.NumberUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.customer.dao.CustomerDao"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%! 
	private final static Logger logger = Logger.getLogger(JSPErrorLogger.class);
	private boolean debug = false;
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib uri="minutesFormatConverter" prefix="timeFormatter"%>
<%@ taglib uri="numberWithComma" prefix="numberFormatter"%>

<c:set var = "class" scope="page" value=""/>
<c:set var = "dataPresent" scope="page" value="${customerdgr.total}"/>
<c:set var = "siteDataPresent" scope="page" value=""/>
<c:set var = "stateDataPresent" scope="page" value=""/>

<html>
    <head>
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"></script>
        <script type="text/javascript">

            function popUp(custid, type, rdate) {
            	location.href = "<%=request.getContextPath()%>" + "/OverAllCustomerReport.jsp?id="+custid+"&type="+type+"&rd="+rdate;
            }

        </script>
        <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
        <style type="text/css">
            .missing{
                background-color: lime;
            }
        </style>
    </head>
    <body style="overflow: hidden">
		<form>
			<script type="text/javascript">
           		parent.window.document.getElementById("progressbar").style.display = "block";
           		parent.window.document.getElementById("progressbar").style.display = "none";
       		</script>
        	<c:choose><%-- Condition for Checking Data Present:Start --%>
       		<c:when test="${empty dataPresent}"><%-- Data Not Present --%>
       		<script type="text/javascript">
           		alert("Sorry! Data For Selected Date or Month Or Year Not Available.");
	        </script>
	        <jsp:include page="../Blank.jsp" />
       		</c:when><%-- Data Not Present --%>
       		<c:otherwise><%-- Data Present --%>
			<div style="width:100%;height:100%;"> 
	            <div id='menu' align='left'>
		            <table cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=0>
		                <tr class=TableTitleRow>
		                	<td class=TableCell11>State wise Power Generation Summary</td>
		                </tr>
	               	</table>
		            <Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1>
						<tr class=TableTitleRow>
							<td class=TableCell11>Location</td>
							<td class=TableCell11>Generation(KWH)</td>
							<td class=TableCell11>Operating Hours(hrs)</td>
							<td class=TableCell11>Lull HRS</td>
							<td class=TableCell11>Capacity Factor(%)</td>
							<td class=TableCell11>Machine Avail(%)</td>
						
							<c:choose>
							<c:when test="${diff < 0}">
		
							<td class=TableCell11>Grid Avail(%)</td>
		
							</c:when>    
							<c:otherwise>
		
							<td class=TableCell11>Grid Avail(%) Internal</td>
		      				<td class=TableCell11>Grid Avail(%) External</td>
		
							</c:otherwise>
							</c:choose>
						</tr>
						
						<c:forEach items="${customerdgr.statesTotal}" var="stateDetail" varStatus="stateCounter">
							<c:set var = "stateDataPresent" scope="page" value="${stateDetail.total}"/>
							<c:choose><%-- Condition Check for Data Present in State --%>
							<c:when test="${ empty stateDataPresent }"><%-- State Data Not Present --%>
						<tr class=TableSummaryRow>
							<td class=TableCell1>${ stateDetail.state.name } </td>
							<td style='text-align:center;' colspan = '6'>Site Data Not Available</td>
						</tr>
							</c:when><%-- State Data Not Present --%>
							<c:otherwise><%-- State Data Present --%>
									<c:forEach items="${stateDetail.sitesTotal}" var="siteDetail" varStatus="siteCounter">
										<c:set var = "siteDataPresent" scope="page" value="${siteDetail.total}"/>
										<c:choose><%-- Check for populating alternate class attribute --%>
	    									<c:when test="${siteCounter.index % 2 == 0}"><c:set var = "class" value="TableRow2"/></c:when>    
	    									<c:otherwise><c:set var = "class" value="TableRow1"/></c:otherwise>
										</c:choose><%-- Check for populating alternate class attribute --%>
						<tr class=${ class }>
										<c:choose><%-- Check for data present in site --%>
										<c:when test="${ empty siteDataPresent }"><%-- Site Data Not Present --%>
							<td class=TableCell1>${ siteDetail.site.name }</td>
							<td style='text-align:center;' colspan = '6'>Data Not Available</td>
										</c:when><%-- Site Data Not Present --%>
										<c:otherwise><%-- Site Data Present --%>
							<td class=TableCell1>
								<a href="<c:url value="/monthMachineWiseDetail.do"/>?stateId=<c:out value="${ siteDetail.state.id }" />&siteId=<c:out value="${ siteDetail.site.id }" />&customerId=<c:out value="${ siteDetail.customer.id }"/>&fromDate=<c:out value="${ fromDate }" />&toDate=<c:out value="${ toDate }" />" target="_blank"> ${ siteDetail.site.name }</a>
							</td>
	    					<td align="center"><numberFormatter:convert number="${ siteDetail.total.generation }" /></td>
	    					<td align="center"><timeFormatter:convert minutes="${ siteDetail.total.operatingHour }" /></td>
	    					
	    									<c:choose><%-- Check for site trial --%>
    										<c:when test="${ siteDetail.trial }"><%-- Site in trial --%>
    											<c:choose>
												<c:when test="${diff < 0}">
							<TD colspan='4' align=center>WEC Is In Stabilization Phase</td>
												</c:when>    
												<c:otherwise>
							<TD colspan='5' align=center>WEC Is In Stabilization Phase</td>
												</c:otherwise>
												</c:choose>
    										</c:when><%-- Site in trial --%>
    										<c:otherwise><%-- Site not in trial --%> 
	    					<td align="center"><timeFormatter:convert minutes="${ siteDetail.total.lullHour }" /></td>
	    					<td align="center">${ siteDetail.total.cf }</td>
		    									<c:choose>
													<c:when test="${diff < 0}">
							<td align="center">${ siteDetail.total.ma }</td>
													</c:when>    
													<c:otherwise>
							<td align="center">${ siteDetail.total.mia }</td>
		      				<td align="center">${ siteDetail.total.gia }</td>
													</c:otherwise>
												</c:choose>
							<td align="center">${ siteDetail.total.ga }</td>
											</c:otherwise><%-- Site not in trial --%>
											</c:choose><%-- Check for site trial --%>
	  									</c:otherwise><%-- Site Data Present --%>
	  									</c:choose><%-- Check for data present in site --%>
	  					</tr>
									</c:forEach> 
	  					<tr class=TableSummaryRow>
	  						<td class=TableCell1>${ stateDetail.state.name }</td>
	    					<td align="center"><numberFormatter:convert number="${ stateDetail.total.generation }" /></td>
	    					<td align="center"><timeFormatter:convert minutes="${ stateDetail.total.operatingHour }" /></td>
	    					
	    					<c:choose><%-- Check for State Trial --%>
	    					<c:when test="${ stateDetail.trial }"><%-- State in Trial --%>
	    						<c:choose>
								<c:when test="${diff < 0}">
							<TD colspan='4' align=center>WEC Is In Stabilization Phase</td>
								</c:when>    
								<c:otherwise>
							<TD colspan='5' align=center>WEC Is In Stabilization Phase</td>
								</c:otherwise>
								</c:choose>
	    					</c:when><%-- State in Trial --%>
	    					<c:otherwise><%-- State Not in Trial --%>
	    					<td align="center"><timeFormatter:convert minutes="${ stateDetail.total.lullHour }" /></td>
	    					<td align="center">${ stateDetail.total.cf }</td>
	    					
	    							<c:choose>
										<c:when test="${diff < 0}">
		
							<td align="center">${ stateDetail.total.ma }</td>
		
										</c:when>    
										<c:otherwise>
		
							<td align="center">${ stateDetail.total.mia }</td>
		      				<td align="center">${ stateDetail.total.gia }</td>
		
										</c:otherwise>
									</c:choose>
							
							<td align="center">${ stateDetail.total.ga }</td>
							</c:otherwise><%-- State Not in Trial --%>
	    					</c:choose><%-- Check for State Trial --%>
	  					</tr>
  							</c:otherwise><%-- State Data Present --%>
	  						</c:choose><%-- Condition Check for Data Present in State --%>
						</c:forEach>
						
						<tr class=TableSummaryRow align=center>
							<td class=TableCell11>Overall</td>
							<td class=TableCell111><numberFormatter:convert number="${ customerdgr.total.generation }" /></td>
							<td class=TableCell111><timeFormatter:convert minutes="${ customerdgr.total.operatingHour }" /></td>
							<td class=TableCell111><timeFormatter:convert minutes="${ customerdgr.total.lullHour }" /></td>
							<td class=TableCell111>${ customerdgr.total.cf }</td>
						
							<c:choose>
							<c:when test="${diff < 0}">
		
							<td class=TableCell111>${ customerdgr.total.ma }</td>
		
							</c:when>    
							<c:otherwise>
		
							<td class=TableCell111>${ customerdgr.total.mia }</td>
		      				<td class=TableCell111>${ customerdgr.total.gia }</td>
		
							</c:otherwise>
							</c:choose>
							
							<td class=TableCell111>${ customerdgr.total.ga }</td>
						</tr>
					</Table>
				</div>
			</div>
			</c:otherwise><%-- Data Present --%>
			</c:choose><%-- Condition for Checking Data Present:End --%>
		</form>
		
    </body>
</html>   