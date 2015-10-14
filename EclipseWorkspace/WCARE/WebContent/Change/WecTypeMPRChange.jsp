<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.admin.metainfo.WECMetaInfo"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%@page import="com.enercon.admin.metainfo.CustomerMetaInfo"%>
<%@page import="com.enercon.global.utility.MethodClass"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%!    boolean debug = false;

%>
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
        <%
    if (session.getAttribute("loginID") == null) {
        response.sendRedirect(request.getContextPath());
    }
        %>

        <TITLE>DGR DashBoard Report</TITLE>

        <%
    response.setHeader("Pragma", "no-cache");
    response.getOutputStream().flush();
    response.getOutputStream().close();
    String userid = session.getAttribute("loginID").toString();
    String Customeridtxt = "";
        %>
    </HEAD>
    <BODY>
        <CENTER>
            <%
    String wectype = request.getParameter("wectype");
    String fdate = request.getParameter("fdate");
    String tdate = request.getParameter("tdate");
	String fromDate = DateUtility.convertDateFormats(fdate, "dd/MM/yyyy", "dd-MMM-yyy");
	String toDate = DateUtility.convertDateFormats(tdate, "dd/MM/yyyy", "dd-MMM-yyy");
    if (debug) {
        MethodClass.displayMethodClassName();
        System.out.println("wectype:" + wectype);
        System.out.println("fdate:" + fdate);
        System.out.println("tdate:" + tdate);
    }
    String[] wec = wectype.split("/");
    ArrayList<ArrayList<String>> customerMetaInfo = CustomerMetaInfo.getCustomerMetaInfoBasedOnWECTypeWithLoadCapacity(wec[0]);
    if(debug){
    	System.out.println("Customer Records: " + customerMetaInfo.size());
    }
    %>
    <TABLE cellSpacing="1" cellPadding="1" width="100%" border="1" >
                <TBODY>
                    <TR>
                        <td vAlign = "middle" align = "center" colspan="3"><b>&nbsp;</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b>Performance Report</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="3"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
                        <td vAlign = "middle" align = "center" colspan="3"><b><%=wec[0]%>,<%=WECMetaInfo.getWECCapacityFromType(wec[0])%> KWh</b></td>
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
    <%
    
    for(int i = 0 ; i < customerMetaInfo.size(); i++){
    	ArrayList<String> individualCustomerMetaInfo = customerMetaInfo.get(i);
    	String customerName = individualCustomerMetaInfo.get(0);
    	String customerId = individualCustomerMetaInfo.get(1);
    	String stateName = individualCustomerMetaInfo.get(2);
    	String stateId = individualCustomerMetaInfo.get(3);
    	String siteName = individualCustomerMetaInfo.get(4);
    	String siteId = individualCustomerMetaInfo.get(5);
    	String wecCapacity = individualCustomerMetaInfo.get(6);
    	String loadCapacity = individualCustomerMetaInfo.get(7);
    	String totalWEC = individualCustomerMetaInfo.get(8);
    	
    	ArrayList<ArrayList<Object>> wecWiseTotal = CustomerUtility.getOneSiteWECWiseTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMeta(siteId, customerId, fromDate, toDate, wec[0]);
    	if(debug){
	    	GlobalUtils.displayVectorMember(individualCustomerMetaInfo);
	    	//GlobalUtils.displayVectorMember(wecWiseTotal);
    	}
    	%>
    	<TR bgcolor="#CCCCCC">
        <td vAlign = "middle" align = "center" colspan="1"><b>Site: <%=siteName%></b></td>
        <td vAlign = "middle" align = "center" colspan="2"><b>&nbsp;</b></td>
        <td vAlign ="middle"  align = "center" colspan="7"><b><%=customerName%></b></td>
        <td vAlign ="middle"  align = "center" colspan="4"><b>Capacity:<%=loadCapacity%></b></td>
    </TR>
    <%
    	int wecCount = 0;
    	for(ArrayList<Object> oneWECTotal: wecWiseTotal){
    		if(oneWECTotal.size() == 2){
    			%>
                    <TR>		             
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(0)%></td>
                        <td vAlign = "middle" align = "center" colspan = 13><%=oneWECTotal.get(1)%></td>
                    </TR>
    			<%
    		}
    		else{
    			wecCount++;
    			%>
                    <TR>		             
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(1)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(6)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(8)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(12)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(19)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(20)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(27)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(28)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(21)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(26)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(29)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(13)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(15)%></td>
                        <td vAlign = "middle" align = "center" ><%=oneWECTotal.get(14)%></td>
                    </TR>
    			<%
    		}
    	}
    	if(wecCount > 1){
    		List<Object> siteTotal = CustomerUtility.getOneSiteTotalForBetweenDaysBasedOnWECTypeSiteIdCustomerIdMeta(siteId, customerId, fromDate, toDate, wec[0]);
    		%>
    		<tr>
    			<td vAlign = "middle" align = "center" ><b>Total<b></td>
    			<td vAlign = "middle" align = "center" ><b><%=siteTotal.get(6)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(8)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(12)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(19)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(20)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(27)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(28)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(21)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(26)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(29)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(13)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(15)%></b></td>
                <td vAlign = "middle" align = "center" ><b><%=siteTotal.get(14)%></b></td>
    		</tr>
    		<%
    		wecCount = 0;
    	}
    }
            %>
        </CENTER>
    </BODY>
</HTML>