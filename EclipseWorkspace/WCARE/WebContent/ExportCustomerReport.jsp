<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%> 
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "ExportData.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>  
       

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.*"%>
 
<HTML> 
<HEAD>  
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=windows-1252">

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>
</HEAD>
<BODY text="#000000" bottomMargin="1" leftMargin="1" topMargin="1" rightMargin="1" marginheight="1" marginwidth="1">

<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String type = request.getParameter("type");
	
%> <%
 	List tranList = new ArrayList();
 	List sitetranList = new ArrayList();
 	CustomerUtil secutils = new CustomerUtil();
 	tranList = (List) secutils.getEBHeading(custid, rdate, stateid,
 			siteid);
 	String cls = "TableRow1";
 	String ebid = "";
 	String cname = "";
 	String state = "";
 	String Remarks = "";
	String RemarksWEC = "";
 	for (int i = 0; i < tranList.size(); i++) {
 		Remarks = "";
 		RemarksWEC="";
 		Vector v = new Vector();
 		v = (Vector) tranList.get(i);
 		ebid = (String) v.get(5);

 		cname = (String) v.get(0).toString().replaceAll("&", "and");
 		state = (String) v.get(3) + "-" + (String) v.get(4);
 %> 
 <%
 		AdminDao adminDao = new AdminDao();
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = adminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");

 		String adate="01/04/3009";
 		java.util.Date ffd = format.parse(rdate);
 		java.util.Date afd = format.parse(adate);
 		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 		int month = ffd.getMonth() + 1;
 		int day = ffd.getDay();

 		String year = rdate.substring(6);
 		//String syear="";

 		int cyear = 2000 + ffd.getYear() - 100;
 		int nyear = cyear;
 		//System.out.println("Month: " + month);
 		//System.out.println("Year: " + cyear);
 		if (month >= 4) {
 			nyear = cyear + 1;
 		} else {
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
 %>
 
<TABLE cellSpacing="1" cellPadding="1" width="100%" border="3">
<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
		<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
			<TBODY>

				<tr>
					<td vAlign="middle" align="center" width="" 100%"" colspan="7"><%=v.get(0)%></td>

				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>


				<tr>
					<td vAlign="middle" align="center" width="100%" colspan="7"></td>
				</tr>

				<tr>
					<td vAlign="middle" align="left" width="20%"><b>Location:</b></td>
					<td vAlign="middle" align="left" width="38%" colspan="2"><%=v.get(3)%> - <%=v.get(4)%></td>

					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Machines:</b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=v.get(8)%></td>

				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>
				<tr>
					<td vAlign="middle" align="left" width="20%" ><b>Location Capacity:</b></td>
					<td vAlign="middle" align="left" width="38%" colspan="2"><%=v.get(7)%> MW</td>
					<%
					if (type.equals("D")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Date: </b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=FromDatetxt%></td>
					<%
					} else if (type.equals("M") || type.equals("DM")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Month: </b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=monthname%>-<%=year%></td>
					<%
					} else if (type.equals("Y")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Year:</b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=pdate%>-<%=ndate%></td>

					<%
					}
					%>
				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>
                <tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"><B>WEC DATA</B></td>
				</tr>
			</TBODY>
		</TABLE>
        </td></tr>
		<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
	
 
		<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
			<TBODY>
                


				<%
						List tranListData = new ArrayList();
						tranListData = (List) secutils.getWECData(ebid, rdate, type);

						cls = "TableRow1";
						//System.out.println("tranListData" + tranListData.size());
						int wecsize = tranListData.size();
						if (wecsize > 0) {
				%>

				<TR>
					<TD width="10.28%"><b>WEC Name</b></TD>

					<TD width="5.28%"><b>Generation</b></TD>
					<TD width="5.28%"><b>Operating Hours</b></TD>
					<TD width="5.28%"><b>Lull Hours</b></TD>
					<TD width="5.28%"><b>Capacity Factor(%) </b></TD>
					<TD width="7.28%"><b>Machine Availability(%)</b></TD>
					<% if(diff<0){ %>
						<TD  width="14.28%" ><b>Grid Availability(%)</b></TD>
						<%} else{ %>
						<TD  width="7.14%"><b>Grid  Availability(%) Internal</b></TD>
						<TD  width="7.14%"><b>Grid  Availability(%) External</b></TD>
					<%} %>

				</TR>

				<%
						for (int j = 0; j < tranListData.size(); j++) {
						Vector vdata = new Vector();
						vdata = (Vector) tranListData.get(j);
						String name = (String) vdata.get(0);
						//String gen =String.parse(vdata.get(1));
						//String gen ="23.00";
						//String gen2 =gen.toString();

						//String ghrs = (String)vdata.get(2);
						//String lhrs = (String)vdata.get(3);
						//String mavdataail = (String)vdata.get(4);
						//String gavdataail = (String)vdata.get(5);
						//String cfactor = (String)vdata.get(6);
						//String stateid = (String)vdata.get(7);
						if (!vdata.get(9).toString().equals("NIL")) {
								if(!RemarksWEC.equals(".")){
								RemarksWEC = RemarksWEC +(String) vdata.get(9);
								
								
								}
							}
						int rem = 1;
						rem = j % 2;

						if (rem == 0)
							cls = "TableRow2";
						else
							cls = "TableRow1";
				%>

				<TR>
					
                    <TD><%=vdata.get(0)%></TD>
					<TD><%=vdata.get(2)%></TD>
					<TD><%=vdata.get(3)%></TD>
					
					<%if(!vdata.get(10).toString().equals("1"))
							{%>
							
							 
						
						
						<TD><%=vdata.get(4)%></TD>
						<TD><%=vdata.get(7)%></TD>
						<% if(diff<0){ %>
						<TD ><%=vdata.get(5)%></TD>
						<TD ><%=vdata.get(6)%></TD>
						<%}else {%>
                          <TD ><%=vdata.get(11)%></TD>
						  <TD ><%=vdata.get(12)%></TD>
						 <TD ><%=vdata.get(6)%></TD>
						<%}%>
						
						
						<%	}else{ %>
						
						<% if(diff<0){ %>
						<TD colspan="4">WEC Is In Stabilization Phase</TD>
						<%}else {%>
						<TD colspan="5">WEC Is In Stabilization Phase</TD>
						<%}%>
						<%
						}
						%>
				</TR>

				<%
				}
				%>
				<%
							tranListData.clear();
							if (wecsize > 1) {
						tranListData = (List) secutils.getEBWiseTotal(ebid,
								rdate, type);
						for (int j = 0; j < tranListData.size(); j++) {
							Vector vdata = new Vector();
							vdata = (Vector) tranListData.get(j);
							//String name = (String)vdata.get(0);
				%>
				<TR>
					<%
					if (type.equals("D")) {
					%>
					<TD>Total:<%=vdata.get(0)%></TD>

					<%
					} else {
					%>
					<TD>Total:</TD>

					<%
					}
					%>
					<TD><%=vdata.get(1)%></TD>
					<TD><%=vdata.get(2)%></TD>
				<%
						
						if(vdata.get(9).toString().equals("0")){ %>
							<% if(diff<0){ %>
							<TD><%=vdata.get(3)%></TD>
							<TD><%=vdata.get(6)%></TD>
							<TD><%=vdata.get(4)%></TD>
							
							<%}else {%>
							<TD><%=vdata.get(3)%></TD>
							<TD><%=vdata.get(6)%></TD>
	                          <TD><%=vdata.get(7)%></TD>
							  <TD><%=vdata.get(8)%></TD>
							
							<%}%>
							<TD><%=vdata.get(5)%></TD>
						
						<%} else{%>
						   <% if(diff<0){ %>
						   <TD  colspan="4">WEC Is In Stabilization Phase</TD>
						   <%}else {%>
						    <TD  colspan="5">WEC Is In Stabilization Phase</TD>
						<%}} %>
					</TR>

					<%
								}
								}
					%>

			</TBODY>
		</TABLE>
		<BR>






		<%
					tranListData.clear();
					tranListData = (List) secutils.getEBData(ebid, rdate, type);
					cls = "TableRow1";
					//System.out.println(tranListData.size());
					if (tranListData.size() > 0) {
		%>
		<P></P>
		<SPAN><b>EB Data</b> </SPAN><BR>

		<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
			<TBODY>
				<TR>
					<TD width="32.5%" colspan="4"><b>Description</b></TD>
					<TD width="22.5%"><b>KWH Export</b></TD>
					<TD width="22.5%"><b>KWH Import</b></TD>
					<TD width="22.5%"><b>Net KWH</b></TD>

				</TR>
				<%
				}
				%>
				<%
						for (int j = 0; j < tranListData.size(); j++) {
						Vector vdata = new Vector();
						vdata = (Vector) tranListData.get(j);
						String name = (String) vdata.get(0);
						//String gen =String.parse(vdata.get(1));
						//String gen ="23.00";
						//String gen2 =gen.toString();

						//String ghrs = (String)vdata.get(2);
						//String lhrs = (String)vdata.get(3);
						//String mavdataail = (String)vdata.get(4);
						//String gavdataail = (String)vdata.get(5);
						//String cfactor = (String)vdata.get(6);
						//String stateid = (String)vdata.get(7);
						int rem = 1;
						rem = j % 2;
						if (!vdata.get(4).toString().equals("NIL")) 
							if(!Remarks.equals(".")){
							{
								Remarks = (String) vdata.get(4);
							
							}
							}
						if (rem == 0)
							cls = "TableRow2";
						else
							cls = "TableRow1";
				%>

				<TR>
					<TD width="32.5%" colspan="4"><%=vdata.get(0)%></TD>
					<TD width="22.5%"><%=vdata.get(1)%></TD>
					<TD width="22.5%"><%=vdata.get(2)%></TD>
					<TD width="22.5%"><%=vdata.get(3)%></TD>
				</TR>

				<%
						}
						} else {
				%>
			
		<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
			<TBODY>

				<tr>
					<td vAlign="middle" align="center" width="100%" colspan="7">NO DATA TO DISPLAY</td>

				</tr>
			</TBODY>
		</TABLE>

		<%
				}
				tranListData.clear();
		%>
		</TBODY>
</TABLE>

		
		 <%
 if (type.equals("D")) {
 %>
		
	
<p></p>
<SPAN><b>Remarks </b></SPAN><BR>
<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
	<TBODY>
	<% if(Remarks.equals("")){ 
		         Remarks="";
		}
if(Remarks.equals(".")){ 
    Remarks="";
}
%>
		<% if(RemarksWEC.equals("")){ 
			RemarksWEC="";
		} %>
		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="7"><%=Remarks%></td>

		</tr>
		<tr class="TableRow1">
			
            <td vAlign="middle" align="center" width="100%" colspan="7"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>

<%
}
%>


</td></tr>
<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
	</td></tr>
</TABLE>



<%
	}
	tranList.clear();
%>
</BODY>
</HTML>


<%-- <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%> 
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "ExportData.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>  
       

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.*"%>
 
<HTML> 
<HEAD>  
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=windows-1252">

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>
</HEAD>
<BODY text="#000000" bottomMargin="1" leftMargin="1" topMargin="1" rightMargin="1" marginheight="1" marginwidth="1">

<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String type = request.getParameter("type");
	int monthReceived = 0;
	int yearReceived = 0;
	int fiscalYear = 0;
	if(type.equalsIgnoreCase("M")){
		monthReceived = Integer.parseInt(request.getParameter("month"));
		yearReceived = Integer.parseInt(request.getParameter("year"));
	}
	if(type.equalsIgnoreCase("Y")){
		 fiscalYear = Integer.parseInt(request.getParameter("year"));
	}
%> <%
 	List tranList = new ArrayList();
 	List sitetranList = new ArrayList();
 	CustomerUtil secutils = new CustomerUtil();
 	tranList = (List) secutils.getEBHeading(custid, rdate, stateid,siteid);
 	String cls = "TableRow1";
 	String ebid = "";
 	String cname = "";
 	String state = "";
 	String Remarks = "";
	String RemarksWEC = "";
 	for (int i = 0; i < tranList.size(); i++) {
 		Remarks = "";
 		RemarksWEC="";
 		Vector v = new Vector();
 		v = (Vector) tranList.get(i);
 		ebid = (String) v.get(5);

 		cname = (String) v.get(0).toString().replaceAll("&", "and");
 		state = (String) v.get(3) + "-" + (String) v.get(4);
 %> 
 <%
 		AdminDao adminDao = new AdminDao();
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = adminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");

 		String adate="01/04/3009";
 		java.util.Date ffd = format.parse(rdate);
 		java.util.Date afd = format.parse(adate);
 		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 		int month = ffd.getMonth() + 1;
 		int day = ffd.getDay();

 		String year = rdate.substring(6);
 		//String syear="";

 		int cyear = 2000 + ffd.getYear() - 100;
 		int nyear = cyear;
 		//System.out.println("Month: " + month);
 		//System.out.println("Year: " + cyear);
 		if (month >= 4) {
 			nyear = cyear + 1;
 		} else {
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
 %>
 
<TABLE cellSpacing="1" cellPadding="1" width="100%" border="3">
<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
		<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
			<TBODY>

				<tr>
					<td vAlign="middle" align="center" width="" 100%"" colspan="7"><%=v.get(0)%></td>

				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>


				<tr>
					<td vAlign="middle" align="center" width="100%" colspan="7"></td>
				</tr>

				<tr>
					<td vAlign="middle" align="left" width="20%"><b>Location:</b></td>
					<td vAlign="middle" align="left" width="38%" colspan="2"><%=v.get(3)%> - <%=v.get(4)%></td>

					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Machines:</b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=v.get(8)%></td>

				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>
				<tr>
					<td vAlign="middle" align="left" width="20%" ><b>Location Capacity:</b></td>
					<td vAlign="middle" align="left" width="38%" colspan="2"><%=v.get(7)%> MW</td>
					<%
					if (type.equals("D")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Date: </b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=FromDatetxt%></td>
					<%
					} else if (type.equals("M") || type.equals("DM")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Month: </b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=monthname%>-<%=year%></td>
					<%
					} else if (type.equals("Y")) {
					%>
					<td vAlign="middle" align="left" width="18%" colspan="2"><b>Year:</b></td>
					<td vAlign="middle" align="left" width="24%" colspan="2"><%=pdate%>-<%=ndate%></td>

					<%
					}
					%>
				</tr>
				<tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"></td>
				</tr>
                <tr>
					<td vAlign="middle" width="100%" align="center" colspan="7"><B>WEC DATA</B></td>
				</tr>
			</TBODY>
		</TABLE>
        </td></tr>
		<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
	
 
		<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
			<TBODY>
                


				<%
						List tranListData = new ArrayList();
						if(type.equalsIgnoreCase("D")){
							tranListData = (List)CustomerUtility.getOneEBWECWiseInfoForOneDay(ebid, rdate);
						}
						else if(type.equalsIgnoreCase("M")){
							tranListData = (List)CustomerUtility.getOneEBWECWiseTotalForOneMonth(ebid, monthReceived, yearReceived);
						}
						else if(type.equalsIgnoreCase("Y")){
							tranListData = (List) CustomerUtility.getOneEBWECWiseTotalForOneFiscalYear(ebid, fiscalYear);
						}
						else {
							tranListData = (List) secutils.getWECData(ebid, rdate, type);
						}
						

						cls = "TableRow1";
						//System.out.println("tranListData" + tranListData.size());
						int wecsize = tranListData.size();
						if (wecsize > 0) {
				%>

				<TR>
					<TD width="10.28%"><b>WEC Name</b></TD>

					<TD width="5.28%"><b>Generation</b></TD>
					<TD width="5.28%"><b>Operating Hours</b></TD>
					<TD width="5.28%"><b>Lull Hours</b></TD>
					<TD width="5.28%"><b>Capacity Factor(%) </b></TD>
					<TD width="7.28%"><b>Machine Availability(%)</b></TD>
					<% if(diff<0){ %>
						<TD  width="14.28%" ><b>Grid Availability(%)</b></TD>
						<%} else{ %>
						<TD  width="7.14%"><b>Grid  Availability(%) Internal</b></TD>
						<TD  width="7.14%"><b>Grid  Availability(%) External</b></TD>
					<%} %>

				</TR>

				<%
						for (int j = 0; j < tranListData.size(); j++) {
						Vector vdata = new Vector();
						vdata = (Vector) tranListData.get(j);
						String name = (String) vdata.get(0);
						//String gen =String.parse(vdata.get(1));
						//String gen ="23.00";
						//String gen2 =gen.toString();

						//String ghrs = (String)vdata.get(2);
						//String lhrs = (String)vdata.get(3);
						//String mavdataail = (String)vdata.get(4);
						//String gavdataail = (String)vdata.get(5);
						//String cfactor = (String)vdata.get(6);
						//String stateid = (String)vdata.get(7);
						if (!vdata.get(9).toString().equals("NIL")) {
								if(!RemarksWEC.equals(".")){
								RemarksWEC = RemarksWEC +(String) vdata.get(9);
								
								
								}
							}
						int rem = 1;
						rem = j % 2;

						if (rem == 0)
							cls = "TableRow2";
						else
							cls = "TableRow1";
				%>

				<TR>
					
                    <TD><%=vdata.get(0)%></TD>
					<TD><%=vdata.get(2)%></TD>
					<TD><%=vdata.get(3)%></TD>
					
					<%if(!vdata.get(10).toString().equals("1"))
							{%>
							
							 
						
						
						<TD><%=vdata.get(4)%></TD>
						<TD><%=vdata.get(7)%></TD>
						<% if(diff<0){ %>
						<TD ><%=vdata.get(5)%></TD>
						<TD ><%=vdata.get(6)%></TD>
						<%}else {%>
                          <TD ><%=vdata.get(11)%></TD>
						  <TD ><%=vdata.get(12)%></TD>
						 <TD ><%=vdata.get(6)%></TD>
						<%}%>
						
						
						<%	}else{ %>
						
						<% if(diff<0){ %>
						<TD colspan="4">WEC Is In Stabilization Phase</TD>
						<%}else {%>
						<TD colspan="5">WEC Is In Stabilization Phase</TD>
						<%}%>
						<%
						}
						%>
				</TR>

				<%
				}
				%>
				<%
							tranListData.clear();
							if (wecsize > 1) {
								if(type.equalsIgnoreCase("D")){
									tranListData = (List) CustomerUtility.getOneEBTotalForOneday(ebid, rdate);
								}
								else if(type.equalsIgnoreCase("M")){
									tranListData = (List) CustomerUtility.getOneEBTotalForOneMonth(ebid, monthReceived, yearReceived);
								}
								else if(type.equalsIgnoreCase("Y")){
									tranListData = (List) CustomerUtility.getOneEBTotalForOneFiscalYear(ebid, fiscalYear);
								}
								else{
									tranListData = (List) secutils.getEBWiseTotal(ebid,rdate, type);
								}
						for (int j = 0; j < tranListData.size(); j++) {
							Vector vdata = new Vector();
							vdata = (Vector) tranListData.get(j);
							//String name = (String)vdata.get(0);
				%>
				<TR>
					<%
					if (type.equals("D")) {
					%>
					<TD>Total:<%=vdata.get(0)%></TD>

					<%
					} else {
					%>
					<TD>Total:</TD>

					<%
					}
					%>
					<TD><%=vdata.get(1)%></TD>
					<TD><%=vdata.get(2)%></TD>
				<%
						
						if(vdata.get(9).toString().equals("0")){ %>
							<% if(diff<0){ %>
							<TD><%=vdata.get(3)%></TD>
							<TD><%=vdata.get(6)%></TD>
							<TD><%=vdata.get(4)%></TD>
							
							<%}else {%>
							<TD><%=vdata.get(3)%></TD>
							<TD><%=vdata.get(6)%></TD>
	                          <TD><%=vdata.get(7)%></TD>
							  <TD><%=vdata.get(8)%></TD>
							
							<%}%>
							<TD><%=vdata.get(5)%></TD>
						
						<%} else{%>
						   <% if(diff<0){ %>
						   <TD  colspan="4">WEC Is In Stabilization Phase</TD>
						   <%}else {%>
						    <TD  colspan="5">WEC Is In Stabilization Phase</TD>
						<%}} %>
					</TR>

					<%
								}
								}
					%>

			</TBODY>
		</TABLE>
		<BR>






		<%
					tranListData.clear();
					tranListData = (List) secutils.getEBData(ebid, rdate, type);
					cls = "TableRow1";
					//System.out.println(tranListData.size());
					if (tranListData.size() > 0) {
		%>
		<P></P>
		<SPAN><b>EB Data</b> </SPAN><BR>

		<TABLE cellSpacing="1" cellPadding="1" width="100%" border="1">
			<TBODY>
				<TR>
					<TD width="32.5%" colspan="4"><b>Description</b></TD>
					<TD width="22.5%"><b>KWH Export</b></TD>
					<TD width="22.5%"><b>KWH Import</b></TD>
					<TD width="22.5%"><b>Net KWH</b></TD>

				</TR>
				<%
				}
				%>
				<%
						for (int j = 0; j < tranListData.size(); j++) {
						Vector vdata = new Vector();
						vdata = (Vector) tranListData.get(j);
						String name = (String) vdata.get(0);
						//String gen =String.parse(vdata.get(1));
						//String gen ="23.00";
						//String gen2 =gen.toString();

						//String ghrs = (String)vdata.get(2);
						//String lhrs = (String)vdata.get(3);
						//String mavdataail = (String)vdata.get(4);
						//String gavdataail = (String)vdata.get(5);
						//String cfactor = (String)vdata.get(6);
						//String stateid = (String)vdata.get(7);
						int rem = 1;
						rem = j % 2;
						if (!vdata.get(4).toString().equals("NIL")) 
							if(!Remarks.equals(".")){
							{
								Remarks = (String) vdata.get(4);
							
							}
							}
						if (rem == 0)
							cls = "TableRow2";
						else
							cls = "TableRow1";
				%>

				<TR>
					<TD width="32.5%" colspan="4"><%=vdata.get(0)%></TD>
					<TD width="22.5%"><%=vdata.get(1)%></TD>
					<TD width="22.5%"><%=vdata.get(2)%></TD>
					<TD width="22.5%"><%=vdata.get(3)%></TD>
				</TR>

				<%
						}
						} else {
				%>
			
		<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
			<TBODY>

				<tr>
					<td vAlign="middle" align="center" width="100%" colspan="7">NO DATA TO DISPLAY</td>

				</tr>
			</TBODY>
		</TABLE>

		<%
				}
				tranListData.clear();
		%>
		</TBODY>
</TABLE>

		
		 <%
 if (type.equals("D")) {
 %>
		
	
<p></p>
<SPAN><b>Remarks </b></SPAN><BR>
<TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
	<TBODY>
	<% if(Remarks.equals("")){ 
		         Remarks="";
		}
if(Remarks.equals(".")){ 
    Remarks="";
}
%>
		<% if(RemarksWEC.equals("")){ 
			RemarksWEC="";
		} %>
		<tr>
			<td vAlign="middle" align="center" width="100%" colspan="7"><%=Remarks%></td>

		</tr>
		<tr class="TableRow1">
			
            <td vAlign="middle" align="center" width="100%" colspan="7"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>

<%
}
%>


</td></tr>
<tr>
		<td vAlign="middle" align="center" width="" 100%"" colspan="7">
	</td></tr>
</TABLE>



<%
	}
	tranList.clear();
%>
</BODY>
</HTML>
 --%>