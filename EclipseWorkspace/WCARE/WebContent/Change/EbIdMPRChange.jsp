<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.global.utility.DatabaseUtility"%>
<%@page import="com.enercon.global.utility.DateUtility"%>
<%@page import="com.enercon.global.utility.MethodClass"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
    request.setCharacterEncoding("utf-8");
    String file = request.getParameter("File");
    if (file == null || file.equals("")) {
        file = "EbIdMPRChange.xls";
    }
    response.addHeader("Content-Disposition", "attachment; filename=\"" + file + "\"");%>

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
    //String userid = session.getAttribute("loginID").toString();
    //String Customeridtxt = "";
        %>
    </HEAD>
    <BODY>
        <CENTER>
            <%! boolean debug = false;%>
            <%
    String ebId = request.getParameter("ebid");
    String fdate = request.getParameter("fdate");
    String tdate = request.getParameter("tdate");
    
    if (debug) {
        MethodClass.displayMethodClassName();
        System.out.println("fdate:" + fdate);
        System.out.println("tdate:" + tdate);
        System.out.println("ebid:" + ebId);
    }
    ArrayList<ArrayList<Object>> ebWecWiseTotal = CustomerUtility.getOneWECTotalForBetweenDaysBasedOnEbId(ebId, DateUtility.convertDateFormats(fdate, "dd/MM/yyyy", "dd-MMM-yyy"), DateUtility.convertDateFormats(tdate, "dd/MM/yyyy", "dd-MMM-yyy"));
    List<String> customerMetaInfo = CustomerUtility.getCustomerMetaInfoBasedOnEbId(ebId);
            %>
            <TABLE cellSpacing="1" cellPadding="1" width="100%" border="1" >
                <TBODY>
                    <TR>
                        <td vAlign = "middle" align = "center" colspan="3"><b>State:<%=customerMetaInfo.get(2)%></b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="5"><b>Performance Report</b></td>
                        <td vAlign = "middle" align = "center" bgcolor="#CCCCCC" colspan="3"><b>Date:<%=fdate%>-<%=tdate%> </b></td>
                        <td vAlign = "middle" align = "center" colspan="3"><b><%=customerMetaInfo.get(6)%>,<%=customerMetaInfo.get(7)%> KWh</b></td>
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
                    <TR bgcolor="#CCCCCC">
                        <td vAlign = "middle" align = "center" colspan="3"><b>Site: <%=customerMetaInfo.get(4)%></b></td><!-- Site Name -->
                        <td vAlign ="middle"  align = "center" colspan="7"><b><%=customerMetaInfo.get(0)%></b></td><!-- Customer Name -->
                    </TR>
                    <%
    for (int i = 0; i < ebWecWiseTotal.size(); i++) {
        ArrayList<Object> wecTotal = ebWecWiseTotal.get(i);
        if (wecTotal.size() == 2) {
                    %>

                    <TR>		             
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(0)%></td>
                        <td vAlign = "middle" align = "center" colspan = 13><%=wecTotal.get(1)%></td>

                    </TR>
                    <%
        } else {
            double loadCapacity = ((Integer) wecTotal.get(4)) / 1000.0;
                    %>
                    <TR>		             
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(1)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(6)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(8)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(12)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(19)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(20)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(27)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(28)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(21)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(26)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(29)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(13)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(15)%></td>
                        <td vAlign = "middle" align = "center" ><%=wecTotal.get(14)%></td>
                    </TR>
                    <%
        }
    }
                    %>

                </TBODY>
            </TABLE>
        </CENTER>
    </BODY>
</HTML>
</HTML>