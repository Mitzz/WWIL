<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.global.utility.TimeUtility"%>
<%@page import="com.enercon.global.utility.NumberUtility"%>
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%@page import="com.enercon.customer.dao.CustomerDao"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>

<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%! private final static Logger logger = Logger.getLogger(JSPErrorLogger.class); %>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<%!
    private boolean debug = false;
%>
<html>
    <head>
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"></script>
        <script type="text/javascript">

            function popUp(custid, type, rdate) {
            	location.href = "<%=request.getContextPath()%>" + "/OverAllCustomerReport.jsp?id="+custid+"&type="+type+"&rd="+rdate;
            	//location.href = "/WCARE/OverAllCustomerReport.jsp?id="+custid+"&type="+type+"&rd="+rdate;
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
            <input type="hidden" name="custid" value="<%=request.getParameter("id")%>" />
            <input type="hidden" name="custid" value="<%=request.getParameter("rd")%>" />
            <%
            
				logger.info("DGRViewChangeN.jsp");
            	logger.info(request.getContextPath());
                String custid = request.getParameter("id");
                String rdate = request.getParameter("rd");
                String type = "D";
            //String atype = request.getParameter("type");
                String adate = "01/04/3009";
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            // System.out.println(adate);
                java.util.Date ffd = format.parse(rdate);
                java.util.Date afd = format.parse("01/04/3009");
                
                //Difference between 'Request Date - Hard Coded Date' in terms of Days
                //It will be '-1' till 3008
                long diff = (ffd.getTime() - afd.getTime()) / (24 * 60 * 60 * 1000);
                
                if (debug) {
                    System.out.println("custid:" + custid);
                    System.out.println("rdate:" + rdate);
                    System.out.println("adate:" + adate);
                    System.out.println("diff:" + diff);
                }
            // System.out.println(rdate);
            // System.out.println(type);
                String strXML = "";
                strXML = "<div id='menu' align='left'>";
                strXML += "<table cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=0>";
                strXML += "<tr class=TableTitleRow>";
                strXML += "<td class=TableCell11>State wise Power Generation Summary</td></tr></table></div>";
                strXML += "<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1>";
                strXML += "<tr class=TableTitleRow>";
                strXML += "<td class=TableCell1>Location</td>";
                strXML += "<td class=TableCell11>Generation(KWH)</td>";
                strXML += "<td class=TableCell11>Operating Hours(hrs)</td>";
                strXML += "<td class=TableCell11>Lull HRS</td>";
            	strXML += "<td class=TableCell11>Capacity Factor(%)</td>";
                strXML += "<td class=TableCell11>Machine Avail(%)</td>";
                if (diff < 0) {
                    strXML += "<td class=TableCell11>Grid Avail(%)</td>";
                } else {
                    strXML += "<td class=TableCell11>Grid Avail(%) Internal</td>";
                    strXML += "<td class=TableCell11>Grid Avail(%) External</td>";
                }
                strXML += "</tr>";

                List<Object> tranList_1 = new ArrayList();
                List sitetranList_3 = new ArrayList();
                CustomerUtil secutils = new CustomerUtil();
            // String st="_blank";
                String cls = "TableRow1";
                int list_1_counter = 0;
            //tranList_1 = (List<Object>) CustomerUtility.getStateWiseTotalForOneDayBasedOnCustomerId(custid, rdate);
                tranList_1 = (List<Object>) CustomerUtility.getStateWiseTotalForOneDayBasedOnCustomerIdMeta(custid, rdate);

                for (int i = 0; i < tranList_1.size(); i++) {
                    ArrayList<String> siteIDsBasedOnStateId = new ArrayList<String>();
                    Vector vector_12 = new Vector();
                    vector_12 = (Vector) tranList_1.get(i);

                    /*Data Not Available*/
                    if (vector_12.size() == 4) {
                        strXML += "<tr class=TableSummaryRow><td class=TableCell1>" + vector_12.get(2) + "</td>";
                        strXML += "<td style='text-align:center;' colspan = '6'>Site Data Not Available</td></tr>";
                        continue;
                    }

                    /*Machine Tranferred*/
                    if (vector_12.size() == 5) {
                        //strXML += "<tr class=TableSummaryRow><td class=TableCell1>" + vector_12.get(0) + "</td><td style='text-align:center;' colspan = '6'>Site Data Not Available</td></tr>";
//                        System.out.println("----------------Transfer");
                        continue;
                    }
                    list_1_counter++;
                    
                    //
                    String name = (String) vector_12.get(30);
                    String stateid = (String) vector_12.get(31);
                    
                    sitetranList_3.clear();
                    sitetranList_3 = (List) CustomerUtility.getSiteWiseTotalForOneDayBasedOnStateIdCustomerIdMeta(custid, stateid, rdate);
                    for (int j = 0; j < sitetranList_3.size(); j++) {
                        Vector vector_34 = new Vector();
                        vector_34 = (Vector) sitetranList_3.get(j);
                        int rem = 1;
                        rem = j % 2;
                        if (rem == 0) {
                            cls = "TableRow2";
                        } else {
                            cls = "TableRow1";
                        }
                        if (vector_34.size() == 5) {
                            strXML += "<tr class=TableRow1><td class=" + cls + ">" + vector_34.get(3) + "</td><td style='text-align:center;' colspan = '6'>Data Not Available</td></tr>";
                            continue;
                        }
                        if (vector_34.size() == 6) {
                            //strXML += "<tr class=TableRow1><td class="+cls+">" + vector_34.get(0) + "</td><td style='text-align:center;' colspan = '6'>Data Not Available</td></tr>";
                            continue;
                        }
                        String sname = (String) vector_34.get(31);

                        String siteid = (String) vector_34.get(32);
                        
                        if (diff < 0) {
                            
                            strXML += "<tr class=" + cls + " align=left>"
                                    + "<td class=TableCell1>"
                                    /* + "<a href=CustomerReportChangeByDay.jsp?stateid=" + stateid + "&siteid=" + siteid + "&id=" + custid + "&type=" + type + "&rd=" + rdate + "  target='_blank'>" + sname + "</a>" */
                                    + "<a href=CustomerReportChangeByDay.jsp?stateid=" + stateid + "&siteid=" + siteid + "&id=" + custid + "&rd=" + rdate + "  target='_blank'>" + sname + "</a>"
                                    + "</td>"
                                    + "<td align=center>" + NumberUtility.formatNumber((Long) vector_34.get(6)) + "</td>"
                                    + "<td align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_34.get(8)), ":") + "</td>";
                            
                            if (vector_34.get(18).toString().equals("0")) {
                                strXML += "<td align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_34.get(12)), ":") + "</td>"
                                        + "<td align=center>" + vector_34.get(15) + "</td>"
                                        + "<td align=center>" + vector_34.get(13) + "</td>"
                                        + "<td align=center>" + vector_34.get(14) + "</td>"
                                        + "</tr>";
                            } else {
                                strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
                            }
                        } else {
                            
                            strXML += "<tr class="
                                    + cls
                                    + " align=left><td class=TableCell1><a   href=CustomerReportChangeByDay.jsp?stateid="
                                    + stateid + "&siteid=" + siteid + "&id="
                                    /* + custid + "&type=" + type + "&rd=" + rdate */
                                    + custid + "&rd=" + rdate
                                    + " target='_blank'>" + sname
                                    + "</td><td align=center>" + NumberUtility.formatNumber((Long) vector_34.get(6))
                                    + "</td><td align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_34.get(8)), ":")
                                    + "</td>";
                            if (vector_34.get(18).toString().equals("0")) {
                                strXML += "<TD align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_34.get(12)), ":")
                                        + "</td><td align=center>" + vector_34.get(15)
                                        + "</td><td align=center>" + vector_34.get(17)
                                        + "</td><td align=center>" + vector_34.get(16)
                                        + "</td><td align=center>" + vector_34.get(14)
                                        + "</td></tr>";
                            } else {
                                strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";
                            }
                        }
                        
                    }

                    if (diff < 0) {
                        strXML += "<tr class=TableSummaryRow>";
                        
                        strXML += "<td class=TableCell1><b>" + name + "</b></td>";
                        
                        strXML += "<td align=center><b>" + NumberUtility.formatNumber((Long) vector_12.get(6))
                                + "</b></td><td align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_12.get(8)), ":")
                                + "</b></td>";
                        if (vector_12.get(18).toString().equals("0")) {
                            strXML += "<td align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_12.get(12)), ":")
                                    + "</b></td><td align=center><b>" + vector_12.get(15)
                                    + "</b></td><td align=center><b>" + vector_12.get(13)
                                    + "</b></td><td align=center><b>" + vector_12.get(14)
                                    + "</b></td></tr>";
                        } else {
                            strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";

                        }
                    } else {
                        strXML += "<tr class=TableSummaryRow>";
                        
                        strXML += "<td class=TableCell1><b>" + name + "</b></td>";
                        

                        strXML += "<td align=center><b>" + NumberUtility.formatNumber((Long) vector_12.get(6))
                                + "</b></td><td  align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_12.get(8)), ":")
                                + "</b></td>";

                        if (vector_12.get(18).toString().equals("0")) {
                            strXML += "<td  align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_12.get(12)), ":")
                                    + "</b></td><td  align=center><b>" + vector_12.get(15)
                                    + "</b></td><td align=center><b>" + vector_12.get(17)
                                    + "</b></td><td align=center><b>" + vector_12.get(16)
                                    + "</b></td><td align=center><b>" + vector_12.get(14)
                                    + "</b></td></tr>";
                        } else {
                            strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";

                        }
                    }
                    sitetranList_3.clear();
                }
                tranList_1.clear();
                List tranList_5 = new ArrayList();
            // System.out.println(list_1_counter);
                if (list_1_counter > 1) {
                    //tranList_5 = (List) CustomerUtility.getOverallGenerationTotalForOneDayBasedOnCustomerId(custid, rdate);
                    tranList_5 = (List) CustomerUtility.getOverallTotalForOneDayBasedOnCustomerIdMeta(custid, rdate);
                    if (debug) {
                        System.out.println("-----------------5------------------");
                        GlobalUtils.displayVectorMember(tranList_5);
                    }
                    for (int i = 0; i < tranList_5.size(); i++) {
                        Vector vector_56 = new Vector();
                        vector_56 = (Vector) tranList_5.get(i);
                        if (debug) {
                            System.out.println("-----------------6------------------");
                            GlobalUtils.displayVectorMember(vector_56);
                        }
                        //String name = (String) vector_56.get(0);
                        String name = "Overall";
                        list_1_counter = 1;
                        
                        if (custid.equals("1000000762")) {
                        	strXML += 
                            		"<tr class=TableSummaryRow align=center>" +
                            		"<td class=TableCell111 align=center><a href=javascript:onClick=popUp('"+ custid + "','" + "D" + "','" + rdate + "')>" + name + "</a></td>" +
                                    "<td class=TableCell111 align=center>" + NumberUtility.formatNumber((Long) vector_56.get(6)) + "</td>" +
                                    "<td class=TableCell111 align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_56.get(8)), ":") + "</td>" +
                                    "<td class=TableCell111 align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_56.get(12)), ":") + "</td>" +
                                    "<td class=TableCell111 align=center>" + vector_56.get(15) + "</td>" ;
                           if (diff < 0) {
                               strXML += "<td class=TableCell111 align=center>" + vector_56.get(13) + "</td>"; 
                           } else {
                               strXML += "<td class=TableCell111>" + vector_56.get(17) + "</td>" + 
                               		  "<td class=TableCell111>" + vector_56.get(16) + "</td>" ;
                           }
                            strXML += 
                                    "<td class=TableCell111 align=center>" + vector_56.get(14) + "</td>" +
                                    "</tr>";

                        } else {
                        	strXML += 
                            		"<tr class=TableSummaryRow align=center>" + 
                        			"<td class=TableCell11>" + name + "</td>" + 
                            		"<td class=TableCell111 align=center>" + NumberUtility.formatNumber((Long) vector_56.get(6)) + "</td>" + 
                        			"<td class=TableCell111  align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_56.get(8)), ":") + "</td>" +
                        			"<td class=TableCell111  align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long) vector_56.get(12)), ":") + "</td>" + 
                        			"<td class=TableCell111 align=center>" + vector_56.get(15) + "</td>";
                            if (diff < 0) {
                                strXML += "<td class=TableCell111 align=center>" + vector_56.get(13) + "</td>"; 
                            } else {
                                strXML += "<td class=TableCell111>" + vector_56.get(17) + "</td>" + 
                                		  "<td class=TableCell111>" + vector_56.get(16) + "</td>" ;
                            }
                           	strXML += 
                       			"<td class=TableCell111 align=center>" + vector_56.get(14) + "</td>" + 
                       			"</tr>";
                        }
                        
                    }
                }
                /* if(list_1_counter == 1 && type.equals("D") ){
                 //Display the overall Total
                 } */
                tranList_5.clear();
                strXML += "</table>";
            %>    

        </form>
        <script type="text/javascript">

            parent.window.document.getElementById("progressbar").style.display = "block";
            parent.window.document.getElementById("progressbar").style.display = "none";

        </script>
        <%
            if (list_1_counter > 0) {
        %> 

        <div style="width:100%;height:100%;"> 
            <%=strXML%>
        </div>
        <%
        } else {
        %> 
        <script type="text/javascript">
            alert("Sorry! Data For Selected Date or Month Or Year Not Available.");

        </script>
        <jsp:include page="Blank.jsp"/>
        <%
            }
        %>
    </body>
</html>

