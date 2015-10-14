<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
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
<%! java.util.Date nextdate = null; %>
<HTML>
    <HEAD>    
    <%! 
    	public boolean showMasterData  = false;
    	boolean debug = false;
	%>
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
        <%
response.setHeader("Pragma", "no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
//	String userid = session.getAttribute("loginID").toString();
//	String Customeridtxt = "";
        %>
        <script type="text/javascript">
        function myFunction(ur, wty, wna, eb, wid, rd, state, cname, type){
            var url = ur + "?wectype=" + wty + "&wecname=" + wna + "&ebid=" + eb + "&wecid=" + wid + "&rd=" + rd + "&state=" + state + "&cname=" + cname + "&type=" + type;
            window.open(url, 'name', 'height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
        }
        function myFunction(ur, wty, wna, eb, wid, rd, state, cname, type, month, year){
            var url = ur + "?wectype=" + wty + "&wecname=" + wna + "&ebid=" + eb + "&wecid=" + wid + "&rd=" + rd + "&state=" + state + "&cname=" + cname + "&type=" + type + "&month=" + month + "&year=" + year;
            window.open(url, 'name', 'height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
        }
        </script>
    </HEAD>
    <BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0" marginwidth="0">
        <CENTER>
			<%

String custid  = request.getParameter("id");
String stateid = request.getParameter("stateid");
String siteid  = request.getParameter("siteid");
String rdate   = request.getParameter("rd");
String type    = request.getParameter("type");

int monthReceived = Integer.parseInt(request.getParameter("month"));
int yearReceived = Integer.parseInt(request.getParameter("year"));

//System.out.println("Month:" + monthReceived);
//System.out.println("Year:" + yearReceived);
if(debug){
	System.out.println("custid:" + custid);
	System.out.println("stateid:" + stateid);
	System.out.println("siteid:" + siteid);
	System.out.println("rdate:" + rdate);
	System.out.println("type:" + type);
}
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(rdate);
String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
nextdate= dateFormat.parse(nextDate);
            %>
            <TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <tr>
                        <TD align="right" colspan="6">
                            <%
if (type.equals("M")) {
                            %>
                            <input type="button" value="View All Machine" class="printbutton" onClick=location.href = "AllMachineReportM.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>">
                            <%
}
 
                            %>
                            <input type="button" value="Print" onClick="window.print()" class="printbutton">
                            <input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="../ExportCustomerReport.jsp?stateid=<%=stateid%>&siteid=<%=siteid %>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>&month=<%=monthReceived%>&year=<%=yearReceived%>">
							<input align="right" type="button" class="printbutton" value="PDF" onClick=location.href="../ExportCustomerReportToPDF.jsp?stateid=<%=stateid%>&siteid=<%=siteid %>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>&month=<%=monthReceived%>&year=<%=yearReceived%>"></td>
                    </TR>
                    <TR>
                        <td colspan="6"></td>
                    </tr>
				</TBODY>
            </TABLE>
            <%

			%>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>Power Generation Report</TD>
                    </TR>	
                </TBODY>
            </TABLE>
			<%
List getEBHeadingList_1 = new ArrayList();
//	List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();

getEBHeadingList_1 = (List) secutils.getEBHeading(custid, rdate, stateid, siteid);
if(debug){
	System.out.println("---------------------1---------------------");
	GlobalUtils.displayVectorMember(getEBHeadingList_1);
}
String cls = "TableRow1";
String ebid = "";
String cname = "";
String state = "";
String Remarks = "";
String RemarksWEC = "";
boolean ebScadaConnectivity = false;
for (int i = 0; i < getEBHeadingList_1.size(); i++) {
	Vector getEBHeadingListEach_2 = new Vector();
	getEBHeadingListEach_2 = (Vector) getEBHeadingList_1.get(i);
	if(debug){
		System.out.println("---------------------2---------------------");
		GlobalUtils.displayVectorMember(getEBHeadingListEach_2);
	}
	Remarks = "";
	RemarksWEC = "";
	ebid = (String) getEBHeadingListEach_2.get(5);
	cname = (String) getEBHeadingListEach_2.get(0).toString().replaceAll("&", "and");
    state = (String) getEBHeadingListEach_2.get(3) + "-" + (String) getEBHeadingListEach_2.get(4);
	AdminDao adminDao = new AdminDao();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    String FromDatetxt = adminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");
    String adate="01/04/3009";
    java.util.Date ffd = format.parse(rdate);
    java.util.Date afd = format.parse(adate);
    long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 	    
    int month = ffd.getMonth() + 1;
	// 		int day = ffd.getDay();
    String year = rdate.substring(6);
    //String syear="";
    int cyear = 2000 + ffd.getYear() - 100;
    int nyear = cyear;
    ////System.out.println("Month: " + month);
    ////System.out.println("Year: " + cyear);
    if (month >= 4) {
    	nyear = cyear + 1;
    }
    else {
	    nyear = cyear;
	    cyear = cyear - 1;
	}
    String pdate = Integer.toString(cyear);
    String ndate = Integer.toString(nyear);

	String monthname = "";
    if (month == 4) {
    	monthname = "APRIL";
	}
    if (month == 3) {
    	monthname = "MARCH";
	}
    if (month == 1) {
	        monthname = "JANUARY";
	}
	if (month == 2) {
	        monthname = "FEBRUARY";
	}
	
	if (month == 5) {
	        monthname = "MAY";
	}
	
	if (month == 6) {
	        monthname = "JUNE";
	}
	
	if (month == 7) {
	        monthname = "JULY";
	}
	
	if (month == 8) {
	        monthname = "AUGUST";
	}
	
	if (month == 9) {
	        monthname = "SEPTEMBER";
	}
	
	if (month == 10) {
	        monthname = "OCTOBER";
	}
	
	if (month == 11) {
	        monthname = "NOVEMBER";
	}
	
	if (month == 12) {
	        monthname = "DECEMBER";
	}
 	if(getEBHeadingListEach_2.get(8)!="0") { 
			%>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <tr class=TableTitle>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"><%=getEBHeadingListEach_2.get(0)%></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell width=90% align=center colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"></td>
                    </tr>

                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location:</td>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(3)%> - <%=getEBHeadingListEach_2.get(4)%></td>

                        <td valign=middle class=TableCell1 align=left width=18%>Machines:</td>
                        <td valign=middle align=left width=24% colspan=2><%=getEBHeadingListEach_2.get(8)%> </td>
                    <tr class=TableSummaryRow>
                        <td valign=middle width=90% align=left colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(7)%> MW</td>
                        <%
		if (type.equals("M") || type.equals("DM")) {
                        %>
                        <td valign=middle class=TableCell1 align=left width=18%>Month:</td>
                        <td valign=middle align=left width=24% colspan=2><%=monthname%>-<%=year%></td>
                        <%
		} 
                        %>
                    </tr>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>WEC Generation</TD>
                    </TR>
                </TBODY>
            </TABLE>
            <%
	} 
            %>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <TBODY>
                    <TR>
                        <TD>
                            <TABLE cellSpacing=1 cellPADding=2 width="100%" border=0>
                                <TBODY>
                                    <%
                                    	/*Lull Hour Individual*/
                                    	List getWECDataList_3 = new ArrayList();
                                    	
                                    	getWECDataList_3 = CustomerUtility.getOneEBWECWiseTotalForOneMonth(ebid, monthReceived, yearReceived);
                                    	if(debug){
                                    		System.out.println("---------------------3---------------------");
                                    		GlobalUtils.displayVectorMember(getWECDataList_3);
                                    	}
                                    	cls = "TableRow1";
                                    	int totalLullHour = 0;
                                    	int totalLullMinute = 0;
                                    	int wecsize = getWECDataList_3.size();
                                    	//System.out.println("getWECDataList_3.size()" + getWECDataList_3.size());
                                    	if (wecsize > 0) {
                                    %>
                                    <TR class=TableTitleRow>
                                        <TD class=TableCell1 width="14.28%">WEC Name</TD>
                                        <TD class=TableCell width="14.28%">Generation(KWH)</TD>
                                        <TD class=TableCell width="14.28%">Operating Hours</TD>
                                        <TD class=TableCell width="14.28%">Lull Hours</TD>
                                        <TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
                                        <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
                                        <% 
		if(diff<0){ 
										%>
                                        <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
                                        <%
		}
		else{
										%>
                                        <TD class=TableCell width="14.28%">Grid  Availability(%) Internal</TD>
                                        <TD class=TableCell width="14.28%">Grid  Availability(%) External</TD>
										<%
		} 
										%>
                                    </TR>
                                    <%
		double totalCapacityFactor = 0;	
		int noCapacityFactorCount = 0;
		for (int j = 0; j < getWECDataList_3.size(); j++) {
	        Vector getWECDataListEach_3_4 = new Vector();
	        getWECDataListEach_3_4 = (Vector) getWECDataList_3.get(j);
	        if(debug){
	    		System.out.println("---------------------4---------------------");
	    		GlobalUtils.displayVectorMember(getWECDataListEach_3_4);
	    	}
	        
	        
	        if(!getWECDataListEach_3_4.get(7).equals("-")){
	        	totalCapacityFactor += Double.parseDouble((String)getWECDataListEach_3_4.get(7));
	        	noCapacityFactorCount++;
	        }
	        String name = (String) getWECDataListEach_3_4.get(0);
			int namelen = name.length();
			//String gen =String.parse(getWECDataListEach_3_4.get(1));
			//String gen ="23.00";
			//String gen2 =gen.toString();

            //String ghrs = (String)getWECDataListEach_3_4.get(2);
            //String lhrs = (String)getWECDataListEach_3_4.get(3);
            //String mavdataail = (String)getWECDataListEach_3_4.get(4);
            //String gavdataail = (String)getWECDataListEach_3_4.get(5);
            //String cfactor = (String)getWECDataListEach_3_4.get(6);
            //if (!getWECDataListEach_3_4.get(9).toString().equals("NIL")) {
            //	RemarksWEC = RemarksWEC + "  ,  "
            //	+ (String) getWECDataListEach_3_4.get(9);
            //}

            int rem = 1;
            rem = j % 2;
														
			if ((!getWECDataListEach_3_4.get(9).toString().equals("NIL"))&&(getWECDataListEach_3_4.get(9).toString().length()>=namelen)) {
				if (!RemarksWEC.equals(".")) {									
                //if (rem == 0){										
                                                                            //RemarksWEC = RemarksWEC+"  <font color='GREEN'>"+getWECDataListEach_3_4.get(9).toString()+"</font>";	
                                                                    //}
                                                                    //else{
                	RemarksWEC = RemarksWEC+"<b>"+getWECDataListEach_3_4.get(9).toString().substring(0,namelen)+"</b>"+getWECDataListEach_3_4.get(9).toString().substring(namelen)+"<br>";
                                                                    	
                                                                    //}
				}
			}
			if (rem == 0)
            	cls = "TableRow2";
			else
            	cls = "TableRow1";
                                    %>
                                    <TR class=<%=cls%>>
                                        <%
			if (type.equals("D")) {
                                        %>
                                        <TD class=TableCell1 align="left"><%=getWECDataListEach_3_4.get(0)%></TD>
            							<%
            } 
			else if (type.equals("M")) {
                                        %>
                                        <TD class=TableCell1 align="left">
                                        	<a href="javascript:onClick=myFunction('CustomerReportMByMonth_MachineWise.jsp','<%=getWECDataListEach_3_4.get(1)%>','<%=name%>','<%=ebid%>','<%=getWECDataListEach_3_4.get(8)%>','<%=rdate%>','<%=state%>','<%=cname%>','M', '<%=monthReceived%>','<%=yearReceived%>')"><%=getWECDataListEach_3_4.get(0)%></a>
										</TD>
                                        <%
			}
			else if (type.equals("Y")) {
                                        %>
                                        <TD class=TableCell1 align="left">
                                        	<a href="javascript:onClick=myFunction('CustomerReportY.jsp','<%=getWECDataListEach_3_4.get(1)%>','<%=name%>','<%=ebid%>','<%=getWECDataListEach_3_4.get(8)%>','<%=rdate%>','<%=state%>','<%=cname%>','Y')"><%=getWECDataListEach_3_4.get(0)%></a>
										</TD>
                                        <%
			}
                                        %>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(2)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(3)%></TD>
                                        <%
			if (!getWECDataListEach_3_4.get(10).toString().equals("1")) {
				String lullHourInString = (String)getWECDataListEach_3_4.get(4);
                if(!lullHourInString.equals("0") && !lullHourInString.equals("-")){
                	
                	/*For each and every machine
                	Storing lull hour and minute individually.*/
                	String[] splitHourMinute = lullHourInString.split(":");
                    totalLullHour 	= totalLullHour + Integer.parseInt(splitHourMinute[0]);
                    totalLullMinute = totalLullMinute + Integer.parseInt(splitHourMinute[1]);
				}
                                        %>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(4)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(7)%></TD>
                                            <% 
				if(diff<0){
											%>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(5)%></TD>
                                        <%
				}
				else {
										%>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(11)%></TD>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(12)%></TD>
										<%
				}
										%>
                                        <TD class=TableCell><%=getWECDataListEach_3_4.get(6)%></TD>
                                        <%
			}
			else{
				if(diff<0){
										%>
                                        <TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
                                        <%
				}
				else{
										%>
                                        <TD class=TableCell colspan="5">WEC Is In Stabilization Phase</TD>
                                        <%
				}
			}
            							%>
                                    </TR>
	                                <%
	                                	}
	                                		double avgCapacityFactor = totalCapacityFactor/noCapacityFactorCount;
	                                		int[] hourMinute = GlobalUtils.minuteToHour(totalLullMinute);
	                                		totalLullMinute = hourMinute[1];
	                                		totalLullHour = totalLullHour + hourMinute[0];
	                                		int totalLullMinuteLength = Integer.toString(totalLullMinute).length();
	                                		int totalLullHourLength = Integer.toString(totalLullHour).length();
	                                		String totalLullHourMinute = "";
	                                		if(totalLullMinuteLength == 1){
	                                	totalLullHourMinute = totalLullHour + ":0" + totalLullMinute;
	                                		}
	                                		else{
	                                	totalLullHourMinute = totalLullHour + ":" + totalLullMinute;
	                                		}
	                                		if(totalLullHourLength == 1){
	                                	totalLullHourMinute = "0" + totalLullHourMinute;
	                                		}
	                                        getWECDataList_3.clear();
	                                        if (wecsize > 1) {
	                                	List getEBWiseTotalList_5 = CustomerUtility.getOneEBTotalForOneMonth(ebid, monthReceived, yearReceived);
	                                	if(debug){
	                                	    		System.out.println("---------------------5---------------------");
	                                	    		GlobalUtils.displayVectorMember(getEBWiseTotalList_5);
	                                	    	}
	                                            for (int j = 0; j < getEBWiseTotalList_5.size(); j++) {
	                                		Vector getEBWiseTotalListEach_5_6 = new Vector();
	                                		getEBWiseTotalListEach_5_6 = (Vector) getEBWiseTotalList_5.get(j);
	                                		if(debug){
	                                		    		System.out.println("---------------------6---------------------");
	                                		    		GlobalUtils.displayVectorMember(getEBWiseTotalListEach_5_6);
	                                		    	}
	                                		/* System.out.println("getEBWiseTotalListEach_5_6");
	                                		GlobalUtils.displayVectorMember(getEBWiseTotalListEach_5_6); */
	                                                //String name = (String)getEBWiseTotalListEach_5_6.get(0);
	                                %>
                                    <TR class=TableSummaryRow>
                                        <%
				if (type.equals("D")) {
                                        %>
                						<TD class=TableCell>Total:<%=getEBWiseTotalListEach_5_6.get(0)%></TD>
                                        <%
				} 
				else {
                                        %>
                                        <TD class=TableCell>Total:</TD>
                                        <%
				}
                                        %>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(1)%></TD>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(2)%></TD>
                                        <%
				if(getEBWiseTotalListEach_5_6.get(9).toString().equals("0")){ 
					//System.out.println("customer rep"+getEBWiseTotalListEach_5_6.get(9).toString());
					if(diff<0){
										%>
                                        
                                        <TD class=TableCell><%=totalLullHourMinute%></TD>
                                        <TD class=TableCell><%=new DecimalFormat("##.##").format(avgCapacityFactor)%></TD>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(4)%></TD>
                                        <%
					}
					else {
										%>
                                        <TD class=TableCell><%=totalLullHourMinute%></TD>
                                        <TD class=TableCell><%=new DecimalFormat("##.##").format(avgCapacityFactor)%></TD>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(7)%></TD>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(8)%></TD>
                                        <%
					}
										%>
                                        <TD class=TableCell><%=getEBWiseTotalListEach_5_6.get(5)%></TD>
                                        <%
				} 
				else{ 
					if(diff<0){ 
										%>
                                        <TD class=TableCell colspan=4>WEC Is In Stabilization Phase</TD>
                                        <%
					}
					else {
										%>
                                        <TD class=TableCell colspan=5>WEC Is In Stabilization Phase</TD>
                                        <%
					}
				} 
										%>
                                    </TR>
                                    <%
			}
			getEBWiseTotalList_5.clear();
		}
                                    %>
                                </TBODY>
                            </TABLE>
                        </TD>
                    </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>EB Generation</TD>
                    </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <TBODY>
                    <TR>
                        <TD>
                            <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
                                <TBODY>
                                    <TR class=TableTitleRow>
                                        <TD class=TableCell width="16%">Description</TD>
                                        <TD class=TableCell width="10%">KWH Export</TD>
                                        <TD class=TableCell width="14%">KWH Import</TD>
                                        <TD class=TableCell width="11%">Net KWH</TD>
                                    </TR>
                                    <%
		List getEBDataList_7 = (List) secutils.getEBData(ebid, rdate, type);
    	if(debug){
	  		System.out.println("---------------------7---------------------");
	  		GlobalUtils.displayVectorMember(getEBDataList_7);
  		}
        cls = "TableRow1";
        if (getEBDataList_7.size() > 0) {
        	for (int j = 0; j < getEBDataList_7.size(); j++) {
            	Vector getEBDataListEach_7_8 = new Vector();
				getEBDataListEach_7_8 = (Vector) getEBDataList_7.get(j);
				if(debug){
			  		System.out.println("---------------------8---------------------");
			  		GlobalUtils.displayVectorMember(getEBDataListEach_7_8);
		  		}
				
				/* System.out.println("getEBDataListEach_7_8");
				GlobalUtils.displayVectorMember(getEBDataListEach_7_8); */
				
				int rem = 1;
                rem = j % 2;
                if (!getEBDataListEach_7_8.get(4).toString().equals("NIL")){
                	if (!Remarks.equals("."))
                    	if(Remarks.length()>15)							
							Remarks = "<b>"+getEBDataListEach_7_8.get(4).toString().substring(0,15)+"</b>"+getEBDataListEach_7_8.get(4).toString().substring(15);
                        else
                        	Remarks = "<b>"+getEBDataListEach_7_8.get(4).toString();			
				}
                if (rem == 0)
                	cls = "TableRow2";
				else
                	cls = "TableRow1";
                                    %>
                                    <TR class=<%=cls%>>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(0)%></TD>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(1)%></TD>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(2)%></TD>
                                        <TD class=TableCell><%=getEBDataListEach_7_8.get(3)%></TD>
                                    </TR>
                                    <% 
			}
            getEBDataList_7.clear();
		}
        else{
                                    %>
                                    <TR class=<%=cls%>>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                        <TD class=TableCell>0</TD>
                                    </TR>
                                    <%
		}
	}
	else {
		if(getEBHeadingListEach_2.get(8)!="0") {
			//System.out.println("0000000000000000000000000000000ebid:" + ebid);
			ebScadaConnectivity = true;
			java.util.Date todaysDate = new Date();
			if(nextdate.after(todaysDate)){
			
									%>
                                    <tr>
                                    	<td>
                                            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                                                <TBODY>
                                                    <TR>
                                                        <TD class=SectionTitle colspan="6" align="center" noWrap>Data is not available.</TD>
                                                    </TR>
                                                </TBODY>
                                            </TABLE>
                                        </td>
                                    </tr>
                                    <%}else{ %>
                                    
                                    <!-- ------------------------------------------------------------------------------- -->
                                    <tr>
                                    	<td>
                                            <TABLE  cellSpacing=0 cellPadding=0 width="100%" border=1 bgColor=#555555>
                                                <TBODY>
													<TR class=TableTitleRow>
				                                        <TD class=TableCell1 width="14.28%">WEC Name</TD>
				                                        <TD class=TableCell width="14.28%">Generation(KWH)</TD>
				                                        <TD class=TableCell width="14.28%">Operating Hours</TD>
				                                        <TD class=TableCell width="14.28%">Lull Hours</TD>
				                                        <TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
				                                        <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
                                        				<% 
		if(diff<0){ 
														%>
                                        				<TD class=TableCell width="14.28%">Grid Availability(%)</TD>
                                        				<%
		}
		else{
														%>
                        				                <TD class=TableCell width="14.28%">Grid  Availability(%) Internal</TD>
                                        				<TD class=TableCell width="14.28%">Grid  Availability(%) External</TD>
														<%
		} 
														%>
													</TR>
                                                </TBODY>
                                                <%
		if(debug){
			System.out.println(secutils.getWECByEBId(ebid, rdate));
		} 
		  										%>
                                    <%= secutils.getWECByEBId(ebid, rdate)%>
                                    		</TABLE>
                                        </td>
                                    </tr>
                                    <!-- ------------------------------------------------------------------------------- -->
                                    <%}
		}
		else if(type.equals("D") && showMasterData){
									%>
									<br class=TableSummaryRow>
									            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                <TBODY>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"><%=getEBHeadingListEach_2.get(0)%></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell width=90% align=center colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell align=center width=90% colspan="6"></td>
                    </tr>

                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location:</td>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(3)%> - <%=getEBHeadingListEach_2.get(4)%></td>

                        <td valign=middle class=TableCell1 align=left width=18%>Machines:</td>
                        <td valign=middle align=left width=24% colspan=2><%=getEBHeadingListEach_2.get(8)%> </td>
                    <tr class=TableSummaryRow>
                        <td valign=middle width=90% align=left colspan="6"></td>
                    </tr>
                    <tr class=TableSummaryRow>
                        <td valign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
                        <td valign=middle align=left width=38% colspan=2><%=getEBHeadingListEach_2.get(7)%> MW</td>
                        <td valign=middle class=TableCell1 align=left width=18%>Date:</td>
                        <td valign=middle align=left width=24% colspan=2><%=FromDatetxt%></td>
                        </tr>
                        </TBODY>
                        </TABLE>
                        
						            <!-- <tr>
						            	<td>
						                    <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
						                        <TBODY>
						                            <TR>
						                                <TD class=SectionTitle colspan="6" align="center" noWrap>Data is not available.</TD>
						                            </TR>
						                        </TBODY>
						                    </TABLE>
						                </td>
						            </tr> -->
						            
						            
						            <!-- ------------------------------------------------------------------------------- -->
						            <tr>
						            	<td>
						                    <TABLE  cellSpacing=0 cellPadding=0 width="100%" border=1 bgColor=#555555>
						                        <TBODY>
													<TR class=TableTitleRow>
						                                <TD class=TableCell1 width="14.28%">WEC Name</TD>
						                                <TD class=TableCell width="14.28%">Generation(KWH)</TD>
						                                <TD class=TableCell width="14.28%">Operating Hours</TD>
						                                <TD class=TableCell width="14.28%">Lull Hours</TD>
						                                <TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
						                                <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
                				<% 
if(diff<0){ 
								%>
                				<TD class=TableCell width="14.28%">Grid Availability(%)</TD>
                				<%
}
else{
								%>
				                <TD class=TableCell width="14.28%">Grid  Availability(%) Internal</TD>
                				<TD class=TableCell width="14.28%">Grid  Availability(%) External</TD>
								<%
} 
								%>
							</TR>
                        </TBODY>
                        <%
if(debug){
System.out.println(secutils.getWECByEBId(ebid, rdate));
} 
							%>
            <%= secutils.getWECByEBId(ebid, rdate)%>
            		</TABLE>
                </td>
            </tr>
            <!-- ------------------------------------------------------------------------------- -->
            <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>Remarks</TD>
                    </TR>
                </TBODY>
            </TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#555555 border=0>
	            <TBODY>
	                <tr class=TableRow1>
	                    <td valign=middle  align=center width=90% colspan="6"><font style='color:red;' size="2">Connectivity failure during data transfer</font></td>
	                </tr>
	            </TBODY>
        	</TABLE>
            <%
			
		}
		
	}
                                    %> 
                                </TBODY>
                            </TABLE>
                        </TD>
                    </TR>
                </TBODY>
            </TABLE>
            <%
	if (type.equals("D")) {
		if(getEBHeadingListEach_2.get(8)!="0") {
			%>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
                <TBODY>
                    <TR>
                        <TD class=SectionTitle colspan="6" align="center" noWrap>Remarks</TD>
                    </TR>
                </TBODY>
            </TABLE>
            <%
		} 
        if (Remarks.equals("")) {
        	Remarks = "";
		}
		if (Remarks.equals(".")) {
			Remarks = "";
		}
        if (RemarksWEC.equals("")) {
        	RemarksWEC = "";
        }
            %>
            <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                <TBODY>
                    <tr class=TableRow1>
                        <td valign=middle  align=center width=90% colspan="6"><font size="2"><%=Remarks%></font></td>
                    </tr>
                    <tr class=TableRow2>
                        <td valign=middle  align=center width=90% colspan="6"><font size="2"><%=RemarksWEC%></font></td>
                    </tr>
                </TBODY>
            </TABLE>
            <%
        if(ebScadaConnectivity){
        	java.util.Date todaysDate = new Date();
        	if(nextdate.after(todaysDate)){
        	}
        	else{
			%>		
        	<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
	            <TBODY>
	                <tr class=TableRow1>
	                    <td valign=middle  align=center width=90% colspan="6"><font style='color:red;' size="2">Connectivity failure during data transfer</font></td>
	                </tr>
	            </TBODY>
        	</TABLE>
        	<%
        	}
        }
		ebScadaConnectivity = false;
	} 
			%>
            <P></P>
            <P></P>
            <%
}
getEBHeadingList_1.clear();
            %>
            <P></P>
        </CENTER>
    </BODY>
</HTML>
