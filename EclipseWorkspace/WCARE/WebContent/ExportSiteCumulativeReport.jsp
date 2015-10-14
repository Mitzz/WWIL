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
		file = "SiteGeneartion.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.enercon.admin.dao.*"%>
<%@ page import="java.math.BigDecimal"%> 
<HTML>
<HEAD>    
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%> 





<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>

</HEAD>
<BODY >

<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String Atype = request.getParameter("type");
	String type = request.getParameter("type");
	if(type.equals("YR"))
		type="Y";
%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(rdate);

String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String crdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

%>




<%
	List tranList = new ArrayList();
	List sitetranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	tranList = (List) secutils.getEBHeadingSite(custid, rdate, stateid,
			siteid);
	String cls = "TableRow1";
	String ebid = "";
	String cname = "";
	String state = "";
	String Remarks = "";
	String RemarksWEC = "";
	for (int i = 0; i < tranList.size(); i++) {
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		Remarks = "";
		RemarksWEC = "";
		//ebid = (String) v.get(5);
		cname = (String) v.get(0).toString().replaceAll("&", "and");
		//state = (String) v.get(3) + "-" + (String) v.get(4);
%> <%
 		AdminDao adminDao = new AdminDao();
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = adminDao.convertDateFormat(rdate,
 		"dd/MM/yyyy", "dd-MMM-yyyy");
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
 		////System.out.println("Month: " + month);
 		////System.out.println("Year: " + cyear);
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



<TABLE  width="100%" border="1">
	<TBODY>
		<TR>
			<TD  colspan="7" align="center" ><font size="3"><b>Generation Report</B></font>&nbsp;&nbsp;&nbsp;</TD>
		</TR>
	
		<tr >
			<td vAlign="middle"  align="center" width="100%" colspan="7"><b><%=v.get(0)%></b></td>
		</tr>
		<tr >
			<td vAlign="middle"  width="100%" align="center" colspan="7"></td>
		</tr>
		<tr >
			<td vAlign="middle"  align="center" width="100%" colspan="7"></td>
		</tr>

		<tr>
			<td vAlign="middle"  align="left" width="20%"><b>Location:</b></td>
			<td vAlign="middle" align="left" width="38%" colspan="2"><b><%=v.get(2)%> - <%=v.get(3)%></b></td>

			<td vAlign="middle"  align="left" ><b>Total WEC:</b></td>
			<td vAlign="middle" align="left"  colspan="3"><b><%=v.get(4)%></b> </td>
		</tr>
		<tr >
			<td vAlign="middle" width="100%" align="left" colspan="7"></td>
		</tr>
		<tr >
			<td vAlign="middle"  align="left" ><b>Location Capacity:</b></td>
			<td vAlign="middle" align="left"  colspan="2"><b><%=v.get(5)%> MW</b></td>
			<td vAlign="middle" ><b>Year:</b></td>
			<td vAlign="middle" colspan="3"><b><%=pdate%>-<%=ndate%></b></td>
		</tr>

<TR>
			<TD  colspan="7" align="center" ><font size="3"><b>WEC Data</b></font>&nbsp;&nbsp;&nbsp;</TD>
		</TR>
	


					<%
							List tranListData = new ArrayList();
							tranListData = (List) secutils.getWECSitewise(siteid,stateid,custid,rdate);

							cls = "TableRow1";
							
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>

											<TR>
						           <TD  width="14.28%">Month</TD>
						           
						          <TD  width="14.28%">Generation</TD> 
						          <TD  width="14.28%">Operating Hours </TD>
						          <TD  width="14.28%">Lull Hours </TD>
						          <TD  width="14.28%">Capacity Factor(%) </TD>
						           <TD  width="14.28%">Machine Availability(%)</TD>
						          <% if(diff<0){ %>
								 <TD  width="14.28%">Grid Availability(%)</TD>
								 <%} else{ %>
								 <TD  width="14.28%">Grid Internal Availability(%)</TD>
								 <TD  width="14.28%">Grid External Availability(%)</TD>
								 <%} %>
						         
						        </TR>  
						    	  
						   <%   for (int j=0; j <tranListData.size(); j++)
									{   
										Vector vdata = new Vector();
										vdata = (Vector)tranListData.get(j);
										String name = (String)vdata.get(0);
										//String gen =String.parse(vdata.get(1));
						              //String gen ="23.00";
										//String gen2 =gen.toString();
						
										//String ghrs = (String)vdata.get(2);
										//String lhrs = (String)vdata.get(3);
										//String mavdataail = (String)vdata.get(4);
										//String gavdataail = (String)vdata.get(5);
										//String cfactor = (String)vdata.get(6);
										//String stateid = (String)vdata.get(7);
										int rem=1;
										rem=j%2;
										
						            if(rem==0)
						            	cls="TableRow2";
						            else
						            	cls="TableRow1";
						        
						            		%>
						        
						        <TR >
						        
						          <TD ><%=vdata.get(0)%></TD>
						          <TD ><%=vdata.get(1)%></TD>
						          <TD ><%=vdata.get(2)%></TD>
						          <TD ><%=vdata.get(3)%></TD>
						          <TD ><%=vdata.get(6)%></TD>
						          <% if(diff<0){ %>
								  <TD ><%=vdata.get(4)%></TD>
												
									<%}else {%>
						            <TD ><%=vdata.get(7)%></TD>
							        <TD ><%=vdata.get(8)%></TD>
												
												<%}%>
						          <TD ><%=vdata.get(5)%></TD>
						          </TR>
						        
						        <% }%> 
						        
					<%
								tranListData.clear();
								if (wecsize > 1) {
							tranListData = (List) secutils.getSiteWiseTotal(siteid,custid,rdate, type);
							for (int j = 0; j < tranListData.size(); j++) {
								Vector vdata = new Vector();
								vdata = (Vector) tranListData.get(j);
								//String name = (String)vdata.get(0);
					%>
					<TR >
						<%
						if (type.equals("D")) {
						%>
						<TD >Total:<%=vdata.get(0)%></TD>

						<%
						} else {
						%>
						<TD >Total:</TD>

						<%
						}
						%>
						<TD ><%=vdata.get(1)%></TD>
						<TD ><%=vdata.get(2)%></TD>
						
						
						
						
						<%
						
						if(vdata.get(9).toString().equals("0")){ %>
							<% if(diff<0){ %>
							<TD ><%=vdata.get(3)%></TD>
							<TD ><%=vdata.get(6)%></TD>
							<TD ><%=vdata.get(4)%></TD>
							
							<%}else {%>
							<TD ><%=vdata.get(3)%></TD>
							<TD ><%=vdata.get(6)%></TD>
	                          <TD ><%=vdata.get(7)%></TD>
							  <TD ><%=vdata.get(8)%></TD>
							
							<%}%>
							<TD ><%=vdata.get(5)%></TD>
						
						<%} else{%>
						   <% if(diff<0){ %>
						   <TD  colspan="4"></TD>
						   <%}else {%>
						    <TD  colspan="5"></TD>
						<%}} %>
					</TR>

					<%
								}
								}}
					%>

				</TBODY>
			</TABLE>
			
		












<%
	}
	tranList.clear();
%>



</BODY>
</HTML>
