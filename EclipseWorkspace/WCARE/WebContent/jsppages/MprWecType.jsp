<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.admin.metainfo.WECMetaInfo"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%@page import="com.enercon.admin.metainfo.CustomerMetaInfo"%>
<%@page import="com.enercon.global.utility.MethodClass"%>
<%@ page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="minutesFormatConverter" prefix="timeFormatter"%>
<%@ taglib uri="numberWithComma" prefix="numberFormatter"%>
<%@ taglib uri="wecTrial" prefix="trial"%>
<%@ taglib uri="isShow" prefix="show"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("utf-8");
    String file = request.getParameter("File");
    if (file == null || file.equals("")) {
        file = "WecTypeExcel.xls";
    }
    response.addHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");
%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*"%>


<HTML>
    <HEAD>
        <TITLE>DGR DashBoard Report</TITLE>
    </HEAD>
    <BODY>
        <CENTER>

			<c:forEach var="mpr" items="${mprs}">
    		<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1" >
                <TBODY>
                    <TR>
                        <td vAlign = "middle" align = "center" colspan="3"><b>&nbsp;</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b>Performance Report</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="3"><b>Date:${ fromDate } - ${ toDate } </b></td>
                        <c:choose>
                        	<c:when test="${ empty mpr.wecType }">
                        		<td vAlign = "middle" align = "center" colspan="3"></td>
                        	</c:when>
                        	<c:otherwise>
                        		<td vAlign = "middle" align = "center" colspan="3"><b>${ mpr.wecType.description },${ mpr.wecType.capacity } KWh</b></td>
                        	</c:otherwise>
                        </c:choose>
                        
                        
                    </tr>
                    <TR >
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Wec No.</b></td>
                        <td vAlign = "middle" align = "center" colspan="2"><b>Generation</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Lack Of Wind</b></td>
                        <td vAlign = "middle" align = "center" colspan="7"><b>Down Time</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Machine Availabilty(%)</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Capacity Factor(%)</b></td>		              
                        <td vAlign = "middle" align = "center" ROWSPAN="3"><b>Grid Availabilty(%)</b></td>
                    </tr>
                    <tr>
                        <td vAlign = "middle" align = "center" ROWSPAN="2"><b>KWh</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="2"><b>Hrs</b></td>
                        <td vAlign = "middle" align = "center" colspan="2"> <b>Machine</b></td>
                        <td vAlign = "middle" align = "center" colspan="2"><b> Grid</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="2"><b>Load Rest. Hours/EB</b></td>
                        <td vAlign = "middle" align = "center" ROWSPAN="2"><b>Special Shutdown/WEC</b></td>
                        <td vAlign = "middle" align = "center" rowspan="2"><b> Total</b></td>
                    </tr>
                    <tr>
                        <td vAlign = "middle" align = "center"><b>Fault</b></td>
                        <td vAlign = "middle" align = "center"><b>Shutdown</b></td>
                        <td vAlign = "middle" align = "center"><b>Fault</b></td>
                        <td vAlign = "middle" align = "center"><b>Shutdown</b></td>
                    </tr>
    <c:forEach var="dgr" items="${ mpr.dgrs }">
    				<TR bgcolor="#CCCCCC">
	    				<td vAlign = "middle" align = "center" colspan="1"><b>Site: ${ dgr.site.name }</b></td>
        				<td vAlign = "middle" align = "center" colspan="2"><b>&nbsp;</b></td>
        				<td vAlign ="middle"  align = "center" colspan="7"><b>${ dgr.customer.name }</b></td>
        				<td vAlign ="middle"  align = "center" colspan="4"><b>Capacity: ${ dgr.capacity/1000 } MW</b></td>
    				</TR>
    				<c:forEach var="wecData" items="${dgr.wecsData}">
    				
    				<c:choose>
    					<c:when test="${ empty wecData.value }">
    						<TR>		             
                        		<td vAlign = "middle" align = "center" >${ wecData.key.name }</td>
                        		<td vAlign = "middle" align = "center" colspan = 13>
                        			<trial:remark wec="${ wecData.key }"/>
                        		</td>
                    		</TR>
    					</c:when>
    					<c:otherwise>
    						<TR>		             
                        		<td vAlign = "middle" align = "center" >${ wecData.key.name } (${ wecData.key.foundationNo })</td>
                        		<td vAlign = "middle" align = "center" ><numberFormatter:convert number="${wecData.value.generation}"/></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${ wecData.value.operatingHour}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.lullHour}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.mf}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.ms}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.gif + wecData.value.gef}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.gis + wecData.value.ges}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.ebLoad}" /></td>
                        		<td vAlign = "middle" align = "center" ><timeFormatter:convert minutes="${wecData.value.fm}" /></td>
                        		<td vAlign = "middle" align = "center" >
                        			<timeFormatter:convert minutes="${wecData.value.lullHour + wecData.value.mf + wecData.value.ms + wecData.value.gif + wecData.value.gis + wecData.value.gef + wecData.value.ges + wecData.value.ebLoad + wecData.value.fm}" />
                        		</td>
                        		<td vAlign = "middle" align = "center" >${ wecData.value.ma }</td>
                        		<td vAlign = "middle" align = "center" >${ wecData.value.cf }</td>
                        		<td vAlign = "middle" align = "center" >${ wecData.value.ga }</td>
                    		</TR>
    					</c:otherwise>
    				</c:choose>
    				</c:forEach>
    				<c:if test="${not empty dgr.total }">
                    <TR>
                        <td vAlign = "middle" align = "center" ><b>Total</b></td>
                   		<td vAlign = "middle" align = "center" ><b><numberFormatter:convert number="${dgr.total.generation}"/></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.operatingHour}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.lullHour}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.mf}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.ms}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.gif + dgr.total.gef}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.gis + dgr.total.ges}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.ebLoad}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b><timeFormatter:convert minutes="${dgr.total.fm}" /></b></td>
                   		<td vAlign = "middle" align = "center" ><b>
                   			<timeFormatter:convert minutes="${dgr.total.lullHour + dgr.total.mf + dgr.total.ms + dgr.total.gif + dgr.total.gis + dgr.total.gef + dgr.total.ges + dgr.total.ebLoad + dgr.total.fm}" />
                   		</b></td>
                   		<td vAlign = "middle" align = "center" ><b>${ dgr.total.ma }</b></td>
                   		<td vAlign = "middle" align = "center" ><b>${ dgr.total.cf }</b></td>
                   		<td vAlign = "middle" align = "center" ><b>${ dgr.total.ga }</b></td>
                    </TR>
                    </c:if>
    	</c:forEach>
    	</c:forEach>

           </TBODY>
           </TABLE>
        </CENTER>
    </BODY>
</HTML>