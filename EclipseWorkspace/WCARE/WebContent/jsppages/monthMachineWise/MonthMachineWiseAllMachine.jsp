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
        	<TABLE cellSpacing=0  cellPadding=0 width="90%" border=0>
			    <TBODY>
			  		<TR align="left">		 
				        <TD  align="left" >							
						   	<input type="button"  value="Back" onClick="location.href='<c:url value="monthMachineWiseDetail.do"/>?stateId=<c:out value="${ stateId }" />&siteId=<c:out value="${ siteId }" />&customerId=<c:out value="${ customerId }" />&fromDate=<c:out value="${ fromDate }" />&toDate=<c:out value="${ toDate }" />'" />							 
				  	    </td> 		 
				        <TD align="right">
							<input  type="button" value="Print" onClick="window.print()" class="printbutton">
							<%-- onClick=location.href="ExportAllMachineM.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%> --%>
							<input  type="button" value="Excel" onClick="location.href='<c:url value="ExportAllMachineM.jsp"/>?stateid=<c:out value="${ stateId }" />&siteid=<c:out value="${ siteId }" />&custid=<c:out value="${ customerId }" />&rd=<c:out value="${ reportDate }" />'" />  
				  	    </td>
			        </TR>
			    </TBODY>
			</TABLE>
           <c:forEach items="${ wecDateWises }" var="wecDateWise">
            <TABLE cellSpacing=0  cellPadding=0 width="90%" border=0>
			    <TBODY>
			  		<TR>  
			    		<TD class=SectionTitle colspan="3" noWrap>Monthly Generation Report</TD>	
			    	</TR>
			    </TBODY>
		    </TABLE>
           
           <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
			  <TBODY>
				<tr class=TableTitle>
					<td vAlign = middle class=TableCell align = center width = 100% colspan="6">${wecDateWise.wec.customer.name }</td>
			    </tr>
                <TR>  
			    	<TD class=SectionTitle colspan="6" noWrap>  </TD>
			    </TR>
			    <tr class=TableSummaryRow>
		            <td vAlign = middle class=TableCell1 align = left width = 20%>Location:</td>
		            <td vAlign = middle class=TableCell align = left width = 38% colspan=2> ${wecDateWise.wec.site.name } - ${wecDateWise.wec.state.name } </td>
		            <td vAlign = middle  class=TableCell1 align = left width = 18%>Month: </td>
		            <td vAlign = middle class=TableCell align = left width = 24% colspan=2>${ monthName } - ${ year }</td>
		        </tr>
		        <tr class=TableSummaryRow>
		            <td vAlign = middle class=TableCell1 align = left width = 20%>Capacity:</td>
		            <td vAlign = middle class=TableCell align = left width = 38% colspan=2>${wecDateWise.wec.type }/${wecDateWise.wec.capacity } </td>
		            <td vAlign = middle  class=TableCell1 align = left width = 18%>WEC: </td>
		            <td vAlign = middle class=TableCell align = left width = 24% colspan=2>${wecDateWise.wec.name }</td>
		        </tr>		      		         
		      </TBODY>
		   </TABLE>
		   
           
            <!-- <table cellSpacing=0 cellPadding=0 width="90%" border=0>
                <tbody>
                  <tr class=TableTitle>
					<td>WEC Data</td>
			      </tr>
                </tbody>
            </table> -->
            <SPAN class=TableTitle>WEC Data </SPAN><BR>
            <table cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <tbody>
                    <tr>
                        <td>
                            <table cellSpacing=1 cellPADding=2 width="100%" border=0>
                                <tbody>
                                	<tr class=TableTitleRow>
                                        <td class=TableCell width="14.28%">Date</td>
                                        <td class=TableCell width="14.28%">Generation</td>
                                        <td class=TableCell width="14.28%">Operating Hours</td>
                                        <td class=TableCell width="14.28%">Lull Hours</td>
                                        <td class=TableCell width="14.28%">Capacity Factor(%)</td>
                                        <td class=TableCell width="14.28%">Machine Availability(%)</td>
                                        <td class=TableCell width="14.28%">Grid Availability(%)</td>
                                    </tr>                                   
                                    <c:forEach var="parameter" items="${wecDateWise.datesData}" >
                                    <c:set var = "counter" value="${counter + 1 }" />
                                    <tr class=${counter % 2 == 0 ? "TableRow2" : "TableRow1"}>                               
	                                    <td class=TableCell>${parameter.key}</td>
	                                    <td class=TableCell><numberFormatter:convert number="${parameter.value.generation}"/></td>
	                                    <td class=TableCell><timeFormatter:convert minutes="${parameter.value.operatingHour}" /></td>
	                                    <td class=TableCell><timeFormatter:convert minutes="${parameter.value.lullHour}" /></td>
	                                    <td class=TableCell>${parameter.value.cf}</td>
	                                    <td class=TableCell>${parameter.value.ma}</td>
	                                    <td class=TableCell>${parameter.value.ga}</td>
                                    </tr>
                                    </c:forEach>  
                                     <c:if test="${fn:length(wecDateWise.datesData) > 1 }">
                                     <tr class=TableTitleRow>                               
	                                    <td class=TableCell>Total</td>
	                                    <td class=TableCell><numberFormatter:convert number="${wecDateWise.total.generation}"/></td>
	                                    <td class=TableCell><timeFormatter:convert minutes="${wecDateWise.total.operatingHour}" /></td>
	                                    <td class=TableCell><timeFormatter:convert minutes="${wecDateWise.total.lullHour}" /></td>
	                                    <td class=TableCell>${wecDateWise.total.cf}</td>
	                                    <td class=TableCell>${wecDateWise.total.ma}</td>
	                                    <td class=TableCell>${wecDateWise.total.ga}</td>
                                    </tr>    
                                    </c:if>                    
                                </tbody>
                            </table>
                        </td>
                    </tr>
                </tbody>
            </table>
           
          
           <br /><br />
		</c:forEach>           
		</CENTER>		
	</BODY>
</HTML>
