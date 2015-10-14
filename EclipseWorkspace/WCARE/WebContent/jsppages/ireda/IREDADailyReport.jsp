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
<%@taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib uri="minutesFormatConverter" prefix="customTag"%>
<%@ taglib uri="numberWithComma" prefix="customTag2"%>
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
        	.generation, .operatingHour, .lullHour, .capacityFactor, .mavail, .gavail{
        		text-align: center;
        	}
        	.capacityFactor,.mavail, .gavail{
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

    </head>
    <body>
    
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="80%">
                <tr width="100%">
                    <td width="100%" align="center">
                        <form action="<%=request.getContextPath()%>/gridBifurcationReport.do" method="post" enctype="multipart/form-data" name = "gridBifurcationReport" id = "gridBifurcationReport">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Power Generation On <c:out value="${ date }"></c:out></th>
                                        <td>
                                        	<img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10">
                                        </td>
                                        <td class="newhead3">&nbsp;</td>
                                        <td class="newhead4">
                                        	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10">
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                        <td colspan="3">
                                            <img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="1" cellpadding="0" cellspacing="0" width="100%" >
                                            	<thead>
	                                            	<tr class=TableTitleRow>
														<th>State(No. Of WECs)</td>
														<th>Generation <br>(KWH)</td>
														<th>Operating Hour <br>(HH:MM)</td>
														<th>Lull Hours <br>(HH:MM)</td>
														<th>Capacity Factor(%)</td>
														<th>Machine Avail(%)</td>
														<th>Grid Avail(%)</td>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${ iredaDetails }" var="ireda" varStatus="projectCounter">
													<tr class="TableTitleRow">
														<td class="TableCel1" colspan="7" style="text-align:center;">
															Project No : <c:out value="${ ireda.projectNo }" default="N/A"/>
														</td>
													</tr>
													<c:forEach var="stateDetails" items="${ ireda.stateWiseTotalForOneDay }" varStatus="stateCounter">
													<tr class="TableRow1">
														<td style="text-align: left;">
																<c:out value="${ ireda.stateIdNameMapping[stateDetails.key] }" default="N/A"/>
																(<c:out value="${fn:length(ireda.stateWiseWecIdMapping[stateDetails.key])}" />)
														</td>
														<td class="generation">
														<%-- <c:out value="${ stateDetails.value.generation }" default="N/A"/> --%>
															<customTag2:convert number="${ stateDetails.value.generation }" />
														</td>
														<td class="operatingHour">
															<customTag:convert minutes="${stateDetails.value.operatingHour}" />
														</td>
														<td class="lullHour">
															<customTag:convert minutes="${stateDetails.value.lullHours}" />
														</td>
														<td class="capacityFactor">
															<c:out value="${ stateDetails.value.capacityFactor }" default="N/A"/>
														</td>
														<td class="mavail">
															<c:out value="${ stateDetails.value.mavial }" default="N/A"/>
														</td>
														<td class="gavail">
															<c:out value="${ stateDetails.value.gavial }" default="N/A"/>
														</td>
													</tr>
													<c:set var="stateCount" value="${stateCounter.count }" scope="page"/>
													</c:forEach>
													
													<tr class="TableRow2">
													<c:if test="${stateCount > 1}">
   														<td>Total</td>
														<td class="generation">
															<customTag2:convert number="${ ireda.grandTotalForOneDay.generation }" />
														</td>
														<td class="operatingHour">
															<customTag:convert minutes="${ireda.grandTotalForOneDay.operatingHour}" />
														</td>
														<td class="lullHour">
															<customTag:convert minutes="${ireda.grandTotalForOneDay.lullHours}" />
														</td>
														<td class="capacityFactor">
															<c:out value="${ ireda.grandTotalForOneDay.capacityFactor }" default="N/A"/>
														</td>
														<td class="mavail">
															<c:out value="${ ireda.grandTotalForOneDay.mavial }" default="N/A"/>
														</td>
														<td class="gavail">
															<c:out value="${ ireda.grandTotalForOneDay.gavial }" default="N/A"/>
														</td>
													</c:if>
														
													</tr>
												 	<tr style="height:15px">
												 		<%-- <td>${fn:length(ireda.stateWiseWecIdMapping[stateDetails.key])}</td> --%>
												 	</tr>
													</c:forEach>
												</tbody>	
												
											</table>
                                            <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                        </td>
                                        <td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                    </tr>
                                    
                                    
                                </tbody>
                          	</table>
                        </form>	
                    </td>		
                </tr>
            </table>
        </div>
    </body>
    <%-- <iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
            src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0"
            style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> </iframe> --%>
</html>