<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
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
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="minutesFormatConverter" prefix="timeFormatter"%>
<%@ taglib uri="numberWithComma" prefix="numberFormatter"%>
<%@ taglib uri="wecTrial" prefix="trial"%>
<%@ taglib uri="isShow" prefix="show"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var = "class" scope="page" value=""/>
<c:set var = "counter" scope="page" value="-1"/>
<c:set var = "inTrial" scope="page" value=""/>
<c:set var = "show" scope="page" value=""/>
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
        	//May be not called 
            function myFunction(ur, wty, wna, eb, wid, rd, state, cname, type) {
                var url = ur + "?wectype=" + wty + "&wecname=" + wna + "&ebid=" + eb + "&wecid=" + wid + "&rd=" + rd + "&state=" + state + "&cname=" + cname + "&type=" + type;
                window.open(url, 'name', 'height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
            }
        </script>
    </HEAD>
    <BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0" marginwidth="0">
        <CENTER>
            <TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <tr>
                        <TD align="right" colspan="6">
                            <input type="button" value="Print" onClick="window.print()" class="printbutton">
                            <input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="<c:url value="/Excel/GenerationReportByDay.jsp"/>?stateid=<c:out value="${ stateId }" />&siteid=<c:out value="${ siteId }" />&id=<c:out value="${ customerId }" />&type=<%="D"%>&rd=<c:out value="${ reportDate }" />" />
							<input align="right" type="button" class="printbutton" value="PDF" onClick=location.href="<c:url value="/PDF/GenerationReportByDayPDF.jsp"/>?stateid=<c:out value="${ stateId }" />&siteid=<c:out value="${ siteId }" />&id=<c:out value="${ customerId }" />&type=<%="D"%>&rd=<c:out value="${ reportDate }" />" />
						</td>
                    </TR>
                    <TR>
                        <td colspan="6"></td>
                    </tr>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
                <tr>
                    <td colspan="6" height="5"></td>
                </tr>
                <tr>
                    <td colspan="2" align="left">
                    	<input type="button" class="printbutton"  style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="dgrWec.do?customerId=<c:out value="${ customerId }" />&date=<c:out value="${ previousDate }" />&siteId=<c:out value="${ siteId }" />&stateId=<c:out value="${ stateId }" />&type=<%="D"%>">
					</TD>
                    <td colspan="4" align="center"></td>
                    <td colspan="2"  align="right">
                    	<input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="dgrWec.do?customerId=<c:out value="${ customerId }" />&date=<c:out value="${ nextDate }" />&siteId=<c:out value="${ siteId }" />&stateId=<c:out value="${ stateId }" />&type=<%="D"%>">
					</TD>
                </TR>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>Power Generation Report</TD>
                    </TR>	
                </TBODY>
            </TABLE>
            <c:choose>
            <c:when test="${ dataPresent }">
            <c:forEach items="${ebDgrs}" var="ebDgr" varStatus="ebDgrCount">
            <table cellSpacing=0 cellPadding=0 width="90%" border=0>
                <tbody>
                    <tr class=TableTitle>
                        <td valign=middle class=TableCell align=center width=90% colspan="6">${ ebDgr.customer.name }</td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell width=90% align=center colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"></td>
                    </tr>

                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location:</td>
                        <td valign=middle align=left width=38% colspan=2>${ ebDgr.eb.site.name } - ${ ebDgr.eb.state.name }</td>

                        <td valign=middle class=TableCell1 align=left width=18%>Machines:</td>
                        <td valign=middle align=left width=24% colspan=2>${ ebDgr.typeCount } </td>
                        
                    <tr class=TableSummaryRow>
                        <td valign=middle width=90% align=left colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
                        <td valign=middle align=left width=38% colspan=2>${ ebDgr.capacity/1000 } MW</td>
                        <td valign=middle class=TableCell1 align=left width=18%>Date:</td>
                        <td valign=middle align=left width=24% colspan=2>${ date }</td>
                    </tr>
                </tbody>
                
            </table>
            <table cellSpacing=0 cellPadding=0 width="90%" border=0>
                <tbody>
                    <tr>
                        <td class=SectionTitle colspan="6" align="center" noWrap>WEC Generation</td>
                    </tr>
                </tbody>
            </table>
            <table cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <tbody>
                    <tr>
                        <td>
                            <table cellSpacing=1 cellPADding=2 width="100%" border=0>
                                <tbody>
                                	<tr class=TableTitleRow>
                                        <td class=TableCell1 width="14.28%">WEC Name</td>
                                        <td class=TableCell width="14.28%">Generation(KWH)</td>
                                        <td class=TableCell width="14.28%">Operating Hours</td>
                                        <td class=TableCell width="14.28%">Lull Hours</td>
                                        <td class=TableCell width="14.28%">Capacity Factor(%)</td>
                                        <td class=TableCell width="14.28%">Machine Availability(%)</td>
                                        <td class=TableCell width="14.28%">Grid Availability(%)</td>
                                    </tr>
                                    <c:set var = "counter" value="-1" />
                                    <c:forEach var="wecData" items="${ebDgr.wecsData}">
                                   	<c:set var = "counter" value="${counter + 1 }" />
                                    <tr class=${counter % 2 == 0 ? "TableRow2" : "TableRow1"}>
                                   	<show:isShow wecParameterVo="${wecData.value}" var="show" ></show:isShow>
                                   	<trial:isTrial wec="${wecData.key}" var="inTrial" ></trial:isTrial>
                                        <td class=TableCell1 align="left">${wecData.key.name} (${ wecData.key.foundationNo })</td>
                                   	<c:choose>
                                   	<c:when test="${show}">
                                    	<td class=TableCell><numberFormatter:convert number="${wecData.value.generation}"/></td>
                                        <td class=TableCell><timeFormatter:convert minutes="${ wecData.value.operatingHour}" /></td>
										<c:choose>
										<c:when test="${inTrial}">
												<td class=TableCell colspan="4">WEC Is In Stabilization Phase</td>
										</c:when>
										<c:otherwise>
												<td class=TableCell><timeFormatter:convert minutes="${wecData.value.lullHour}" /></td>
                                        		<td class=TableCell>${wecData.value.cf}</td>
                                        		<td class=TableCell>${wecData.value.ma}</td>
                                        		<td class=TableCell>${wecData.value.ga}</td>
										</c:otherwise>
										</c:choose>
									</c:when>
									<c:otherwise>
											<td class=TableCell>-</td>
											<td class=TableCell>-</td>
										<c:choose>
										<c:when test="${inTrial}">
											<td class=TableCell colspan="4">WEC Is In Stabilization Phase</td>
										</c:when>
										<c:otherwise>
											<td class=TableCell>-</td>
                                       		<td class=TableCell>-</td>
											<td class=TableCell>-</td>
                                       		<td class=TableCell>-</td>
										</c:otherwise>
										</c:choose>
									</c:otherwise>
									</c:choose>
                                    </tr>
                                    </c:forEach>
                                    <c:if test="${fn:length(ebDgr.wecsData) > 1 }">
                                    <TR class=TableSummaryRow>
                                        <td class=TableCell>Total:${ebDgr.total.size}</td>
                                        <td class=TableCell><numberFormatter:convert number="${ebDgr.total.generation}" /></td>
                                        <td class=TableCell><timeFormatter:convert minutes="${ebDgr.total.operatingHour}" /></td>
                                        <td class=TableCell><timeFormatter:convert minutes="${ebDgr.total.lullHour}" /></td>
                                        <td class=TableCell>${ebDgr.total.cf}</td>
                                        <td class=TableCell>${ebDgr.total.ma}</td>
                                        <td class=TableCell>${ebDgr.total.ga}</td>
                                    </TR>
                                    </c:if>
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table cellSpacing=0 cellPadding=0 width="90%" border=0>
                <tbody>
                    <tr>
                        <td class=SectionTitle colspan="6" align="center" noWrap>EB Generation</td>
                    </tr>
                </tbody>
            </table>
            <table cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <tbody>
                    <tr>
                        <td>
                            <table cellSpacing=1 cellPadding=2 width="100%" border=0>
                                <tbody>
                                    <tr class=TableTitleRow>
                                        <td class=TableCell width="16%">Description</TD>
                                        <td class=TableCell width="10%">KWH Export</TD>
                                        <td class=TableCell width="14%">KWH Import</TD>
                                        <td class=TableCell width="11%">Net KWH</TD>
                                    </tr>
                                    <tr class=<%="TableRow2"%>>
                                        <td class=TableCell>Daily</td>
                                    <c:choose>
                                    	<c:when test="${ empty ebDgr.ebData }">
                                    	<td class=TableCell>-</td>
                                        <td class=TableCell>-</td>
                                        <td class=TableCell>-</td>
                                    	</c:when>
                                    	<c:otherwise>
                                        <td class=TableCell>${ebDgr.ebData.kwhExport }</td>
                                        <td class=TableCell>${ebDgr.ebData.kwhImport }</td>
                                        <td class=TableCell>${ebDgr.ebData.exportImportDiff }</td>
                                        </c:otherwise>
                                    </c:choose>
                                    </tr>
                                </tbody>
                            </table>
						</td>
					</tr>
				</tbody>
			</table>
			<table cellSpacing=0 cellPadding=0 width="90%" border=0>
			    <tbody>
			        <tr>
			            <td class=SectionTitle colspan="6" align="center" noWrap>Remarks</td>
			        </tr>
			    </tbody>
			</table>
			<table cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
			    <TBODY>
			    	<%-- Checking for Eb Remark --%>
			    	<c:if test="${ not empty ebDgr.ebData.remark }">
			        <tr class=TableRow1>
			            <td valign=middle  align=center width=90% colspan="6"><font size="2"><b>${ebDgr.ebData.remark }</b></font></td>
			        </tr>
			        </c:if>
			        
			        <%-- Checking for All Wec Remarks --%>
			        <c:forEach var="wecData" items="${ebDgr.wecsData}">
			        <c:if test="${ not empty wecData.value.remark }">
			        <tr class="TableRow2">
			            <td valign=middle  align=center width=90% colspan="6">
			            	<font size="2"><b><u>${ wecData.key.name }</u></b> : ${ wecData.value.remark }</font>
			            </td>
			        </tr>
			        </c:if>
			        </c:forEach>
			        
			        <tr class="TableRow2">
			        	<td valign=middle  align=center width=90% colspan="6">
			        		<font size="2">
			        		<c:set var = "show" value="false"/>
			        		<%-- Checking for All Wec Remarks having connectivity issue --%>		
			        		<c:forEach var="noConnectivityWec" items="${ebDgr.noConnectivityWecs}">
			        		<c:set var = "show" value="true"/>
			        		<b>${ noConnectivityWec.name }:</b>
			        		</c:forEach>
			        			<c:if test="${ show }">
			        			<span style='color:red;'>Connectivity failure during data transfer </span>
			        			</c:if>
			        		</font>
			        	</td>
			        </tr>
			    </TBODY>
			</table>
            <p></p>   
            </c:forEach>
            </c:when>
            <c:otherwise>
            	<font size="2"><b>No Data Uploaded</b></font>
            </c:otherwise>
            </c:choose>
		</CENTER>		
	</BODY>
</HTML>
