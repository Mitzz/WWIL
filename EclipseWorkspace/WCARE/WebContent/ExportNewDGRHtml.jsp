<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "DGRExport.htm";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.enercon.admin.dao.*"%>
<%@ page import="java.math.BigDecimal"%>
<HTML>
<HEAD>
<style type="text/css">
<!--
.style1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	color: #008000;
	font-weight: bold;
	font-size: x-larger;
}
-->
</style>
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
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY >
<CENTER>
<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String type = request.getParameter("type");
	String atype = "DG";
	
%>



<%
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
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		Remarks = "";
		RemarksWEC = "";
		ebid = (String) v.get(5);
		cname = (String) v.get(0).toString().replaceAll("&", "and");
		state = (String) v.get(3) + "-" + (String) v.get(4);
%> <%
 		AdminDao adminDao = new AdminDao();
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = adminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");
 		java.util.Date ffd = format.parse(rdate);

 		String adate="01/04/3009";

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



<TABLE  width="100%" border="0">
	<TBODY>
	    <TR>
			<TD  colspan="9" align="center" ><p class="style1">ENERCON(India) Ltd.</p></TD>
		</TR>
		<TR>
			<TD  colspan="9" align="center" ><b> Daily Generation Report</b></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE width="90%" border="1">
	<TBODY>
		<tr>
			<td vAlign="middle"  align="center" width="100%" colspan="9"><b><%=v.get(0)%></b></td>
		</tr>
		<tr>
			<td vAlign="middle"  width="100%" align="center" colspan="9"></td>
		</tr>
		<tr >
			<td vAlign="middle"  align="center" width="100%" colspan="9"></td>
		</tr>

		<tr>
			<td vAlign="middle"  align="left" width="20%"><b>Location:</b></td>
			<td vAlign="middle"  align="left" width="38%" colspan="4"><b><%=v.get(3)%> - <%=v.get(4)%></b></td>

			<td vAlign="middle"  align="left" width="18%">Machines:</td>
			<td vAlign="middle"  align="left" width="24%" colspan="3"><b><%=v.get(8)%> X <%=v.get(6)%></b></td>
			</tr>
		<tr >
			<td vAlign="middle"  width="100%" align="left" colspan="9"></td>
		</tr>
		<tr>
			<td vAlign="middle"  align="left" width="20%"><b>Location Capacity:</b></td>
			<td vAlign="middle"  align="left" width="38%" colspan="4"><b><%=v.get(7)%> MW</b></td>
			
			<td vAlign="middle"  align="left" width="18%">Date:</td>
			<td vAlign="middle"  align="left" width="24%" colspan="3"><b><%=FromDatetxt%></b></td>
			
		</tr>

	</TBODY>
</TABLE>

<P></P>
<SPAN ><b>WEC Data </b></SPAN><BR>

<TABLE  width="90%" border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>


					<%
							List tranListData = new ArrayList();
							tranListData = (List) secutils.getWECDataCum(ebid, rdate);

							cls = "TableRow1";
							//System.out.println("tranListData" + tranListData.size());
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>


                       <TR>
						<TD width="14.28%"></TD>

						<TD width="28.56%" colspan="6"><b>Daily</b></TD>
						
						
						<TD width="28.56%" colspan="2"><b>Monthly</b></TD>

					</TR>
					<TR>
						<TD width="14.28%"><b>WEC Name</b></TD>

						<TD width="14.28%"><b>Generation</b></TD>
						<TD width="14.28%"><b>Operating Hours</b></TD>
						
						<TD width="14.28%"><b>Lull Hours</b></TD>
						<TD width="14.28%"><b>Capacity Factor(%)</b></TD>
						<TD width="14.28%"><b>Machine Availability(%)</b></TD>
						<% if(diff<0){ %>
						 <TD class=TableCell width="14.28%" colspan="2">Grid Availability(%)</TD>
						 <%} else{ %>
						 <TD class=TableCell width="14.28%">Grid Internal Availability(%)</TD>
						 <TD class=TableCell width="14.28%">Grid External Availability(%)</TD>
					 <%} %>
                        <TD width="14.28%"><b>Generation</b></TD>
						<TD width="14.28%"><b>Operating Hours</b></TD>
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
							//if (!vdata.get(9).toString().equals("NIL")) {
							//	RemarksWEC = RemarksWEC + "  ,  "
							//	+ (String) vdata.get(9);
							//}

							
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
					<TR >
						
						<TD ><%=vdata.get(0)%></TD>
						


						<TD ><%=vdata.get(2)%></TD>
						<TD ><%=vdata.get(3)%></TD>
						
						
						
						
						<%
						if (!vdata.get(10).toString().equals("1")) {
						%>




						<TD ><%=vdata.get(4)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<% if(diff<0){ %>
						<TD class=TableCell><%=vdata.get(5)%></TD>
						
						<%}else {%>
                          <TD class=TableCell><%=vdata.get(13)%></TD>
						  <TD class=TableCell><%=vdata.get(14)%></TD>
						
						<%}%>
						<TD class=TableCell><%=vdata.get(6)%></TD>

						<%
						} else {
						%>

							<% if(diff<0){ %>
							<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
							<%}else {%>
							<TD class=TableCell colspan="5">WEC Is In Stabilization Phase</TD>
							<%}%>
							<%
								}
							%>


						
                        <TD class=TableCell><%=vdata.get(11)%></TD>
						<TD class=TableCell><%=vdata.get(12)%></TD>
					</TR>

					<%
					}
					%>
					<%
								tranListData.clear();
								if (wecsize > 1) {
							tranListData = (List) secutils.getEBWiseTotalCum(ebid,
									rdate);
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
						
						<TD ><%=vdata.get(3)%></TD>
						<TD ><%=vdata.get(6)%></TD>
						<% if(diff<0){ %>
						<TD class=TableCell><%=vdata.get(4)%></TD>
						
						<%}else {%>
                         <TD class=TableCell><%=vdata.get(9)%></TD>
						 <TD class=TableCell><%=vdata.get(10)%></TD>
						
						<%}%>
						<TD ><%=vdata.get(5)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<TD ><%=vdata.get(8)%></TD>
					</TR>

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
<BR>






<%
			tranListData.clear();
			tranListData = (List) secutils.getEBDataCum(ebid, rdate);
			cls = "TableRow1";
			//System.out.println(tranListData.size());
			if (tranListData.size() > 0) {
%>

<P></P>
<SPAN ><b>EB Data </b></SPAN><BR>
<TABLE height="6" border="1">
	<TBODY>
		<TR>
			<TD></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE  width="90%" border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>
					<TR>
						<TD width="16%"><b>Description</b></TD>
						<TD width="10%" colspan="3"><b>KWH Export</b></TD>
						<TD width="14%" colspan="2"><b>KWH Import</b></TD>
						<TD width="11%"  colspan="3"><b>Net KWH</b></TD>

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

					<TR >
						<TD ><%=vdata.get(0)%></TD>
						<TD colspan="3"><%=vdata.get(1)%></TD>
						<TD colspan="2"><%=vdata.get(2)%></TD>
						<TD colspan="3"><%=vdata.get(3)%></TD>
					</TR>

					<%
							}%>
							</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
							<% } else {
					%>
				
<TABLE  width="90%" border="1">
	<TBODY>

		<tr >
			<td vAlign="middle" 1 align="center" width="100%" colspan="9">NO DATA TO DISPLAY</td>

		</tr>
	</TBODY>
</TABLE>

<%
		}
		tranListData.clear();
%> 




<p></p>
<SPAN ><b>Remarks </b></SPAN><BR>
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
<TABLE  width="90%" border="1">
	<TBODY>

		<tr >
			<td vAlign="middle" align="center" width="100%"  colspan="9"><%=Remarks%></td>

		</tr>
		<tr >

	 		<td vAlign="middle" align="center" width="100%" colspan="9"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>







      
<%
	}
	tranList.clear();
%>
<P></P>

</CENTER>
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
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "DGRExport.htm";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.enercon.admin.dao.*"%>
<%@ page import="java.math.BigDecimal"%>
<HTML>
<HEAD>
<style type="text/css">
<!--
.style1 {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	color: #008000;
	font-weight: bold;
	font-size: x-larger;
}
-->
</style>
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
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY >
<CENTER>
<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String type = request.getParameter("type");
	String atype = "DG";
	String change = request.getParameter("change");
	
%>



<%
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
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		Remarks = "";
		RemarksWEC = "";
		ebid = (String) v.get(5);
		cname = (String) v.get(0).toString().replaceAll("&", "and");
		state = (String) v.get(3) + "-" + (String) v.get(4);
%> 
<%
 		AdminDao adminDao = new AdminDao();
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = adminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");
 		java.util.Date ffd = format.parse(rdate);

 		String adate="01/04/3009";

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

<TABLE  width="100%" border="0">
	<TBODY>
	    <TR>
			<TD  colspan="9" align="center" ><p class="style1">ENERCON(India) Ltd.</p></TD>
		</TR>
		<TR>
			<TD  colspan="9" align="center" ><b> Daily Generation Report</b></TD>
		</TR>
	</TBODY>
</TABLE>
<BR>
<TABLE width="90%" border="1">
	<TBODY>
		<tr>
			<td vAlign="middle"  align="center" width="100%" colspan="9"><b><%=v.get(0)%></b></td>
		</tr>
		<tr>
			<td vAlign="middle"  width="100%" align="center" colspan="9"></td>
		</tr>
		<tr >
			<td vAlign="middle"  align="center" width="100%" colspan="9"></td>
		</tr>

		<tr>
			<td vAlign="middle"  align="left" width="20%"><b>Location:</b></td>
			<td vAlign="middle"  align="left" width="38%" colspan="4"><b><%=v.get(3)%> - <%=v.get(4)%></b></td>

			<td vAlign="middle"  align="left" width="18%">Machines:</td>
			<td vAlign="middle"  align="left" width="24%" colspan="3"><b><%=v.get(8)%> X <%=v.get(6)%></b></td>
			</tr>
		<tr >
			<td vAlign="middle"  width="100%" align="left" colspan="9"></td>
		</tr>
		<tr>
			<td vAlign="middle"  align="left" width="20%"><b>Location Capacity:</b></td>
			<td vAlign="middle"  align="left" width="38%" colspan="4"><b><%=v.get(7)%> MW</b></td>
			
			<td vAlign="middle"  align="left" width="18%">Date:</td>
			<td vAlign="middle"  align="left" width="24%" colspan="3"><b><%=FromDatetxt%></b></td>
			
		</tr>

	</TBODY>
</TABLE>

<P></P>
<SPAN ><b>WEC Data </b></SPAN><BR>

<TABLE  width="90%" border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>


					<%
							List tranListData = new ArrayList();
				if(change != null){
					if(change.equalsIgnoreCase("DC")){
						tranListData = (List<Object>)CustomerUtility.getOneWECInfoForOneDayCumOneWECTotalForOneMonthButLessThanCurrentReadingDate(ebid, rdate);
					}
					else{
						tranListData = (List) secutils.getWECDataCum(ebid, rdate);
					}
				}
				else{
					tranListData = (List) secutils.getWECDataCum(ebid, rdate);
				}
							

							cls = "TableRow1";
							//System.out.println("tranListData" + tranListData.size());
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>


                       <TR>
						<TD width="14.28%"></TD>

						<TD width="28.56%" colspan="6"><b>Daily</b></TD>
						
						
						<TD width="28.56%" colspan="2"><b>Monthly</b></TD>

					</TR>
					<TR>
						<TD width="14.28%"><b>WEC Name</b></TD>

						<TD width="14.28%"><b>Generation</b></TD>
						<TD width="14.28%"><b>Operating Hours</b></TD>
						
						<TD width="14.28%"><b>Lull Hours</b></TD>
						<TD width="14.28%"><b>Capacity Factor(%)</b></TD>
						<TD width="14.28%"><b>Machine Availability(%)</b></TD>
						<% if(diff<0){ %>
						 <TD class=TableCell width="14.28%" colspan="2">Grid Availability(%)</TD>
						 <%} else{ %>
						 <TD class=TableCell width="14.28%">Grid Internal Availability(%)</TD>
						 <TD class=TableCell width="14.28%">Grid External Availability(%)</TD>
					 <%} %>
                        <TD width="14.28%"><b>Generation</b></TD>
						<TD width="14.28%"><b>Operating Hours</b></TD>
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
							//if (!vdata.get(9).toString().equals("NIL")) {
							//	RemarksWEC = RemarksWEC + "  ,  "
							//	+ (String) vdata.get(9);
							//}

							
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
					<TR >
						
						<TD ><%=vdata.get(0)%></TD>
						


						<TD ><%=vdata.get(2)%></TD>
						<TD ><%=vdata.get(3)%></TD>
						
						
						
						
						<%
						if (!vdata.get(10).toString().equals("1")) {
						%>




						<TD ><%=vdata.get(4)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<% if(diff<0){ %>
						<TD class=TableCell><%=vdata.get(5)%></TD>
						
						<%}else {%>
                          <TD class=TableCell><%=vdata.get(13)%></TD>
						  <TD class=TableCell><%=vdata.get(14)%></TD>
						
						<%}%>
						<TD class=TableCell><%=vdata.get(6)%></TD>

						<%
						} else {
						%>

							<% if(diff<0){ %>
							<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
							<%}else {%>
							<TD class=TableCell colspan="5">WEC Is In Stabilization Phase</TD>
							<%}%>
							<%
								}
							%>


						
                        <TD class=TableCell><%=vdata.get(11)%></TD>
						<TD class=TableCell><%=vdata.get(12)%></TD>
					</TR>

					<%
					}
					%>
					<%
								tranListData.clear();
								if (wecsize > 1) {
									if(change != null){
										if(change.equalsIgnoreCase("DC")){
											tranListData = (List<Object>) CustomerUtility.getOneEBTotalForOneDayCumOneEBTotalForOneMonthButLessThanCurrentReadingDate(ebid, rdate);
										}
										else{
											tranListData = (List) secutils.getEBWiseTotalCum(ebid,
													rdate);
										}
									}
									else{
										tranListData = (List) secutils.getEBWiseTotalCum(ebid,
												rdate);										
									}
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
						
						<TD ><%=vdata.get(3)%></TD>
						<TD ><%=vdata.get(6)%></TD>
						<% if(diff<0){ %>
						<TD class=TableCell><%=vdata.get(4)%></TD>
						
						<%}else {%>
                         <TD class=TableCell><%=vdata.get(9)%></TD>
						 <TD class=TableCell><%=vdata.get(10)%></TD>
						
						<%}%>
						<TD ><%=vdata.get(5)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<TD ><%=vdata.get(8)%></TD>
					</TR>

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
<BR>






<%
			tranListData.clear();
			tranListData = (List) secutils.getEBDataCum(ebid, rdate);
			cls = "TableRow1";
			//System.out.println(tranListData.size());
			if (tranListData.size() > 0) {
%>

<P></P>
<SPAN ><b>EB Data </b></SPAN><BR>
<TABLE height="6" border="1">
	<TBODY>
		<TR>
			<TD></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE  width="90%" border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>
					<TR>
						<TD width="16%"><b>Description</b></TD>
						<TD width="10%" colspan="3"><b>KWH Export</b></TD>
						<TD width="14%" colspan="2"><b>KWH Import</b></TD>
						<TD width="11%"  colspan="3"><b>Net KWH</b></TD>

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

					<TR >
						<TD ><%=vdata.get(0)%></TD>
						<TD colspan="3"><%=vdata.get(1)%></TD>
						<TD colspan="2"><%=vdata.get(2)%></TD>
						<TD colspan="3"><%=vdata.get(3)%></TD>
					</TR>

					<%
							}%>
							</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
							<% } else {
					%>
				
<TABLE  width="90%" border="1">
	<TBODY>

		<tr >
			<td vAlign="middle" 1 align="center" width="100%" colspan="9">NO DATA TO DISPLAY</td>

		</tr>
	</TBODY>
</TABLE>

<%
		}
		tranListData.clear();
%> 




<p></p>
<SPAN ><b>Remarks </b></SPAN><BR>
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
<TABLE  width="90%" border="1">
	<TBODY>

		<tr >
			<td vAlign="middle" align="center" width="100%"  colspan="9"><%=Remarks%></td>

		</tr>
		<tr >

	 		<td vAlign="middle" align="center" width="100%" colspan="9"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>







      
<%
	}
	tranList.clear();
%>
<P></P>

</CENTER>
</BODY>
</HTML>
 --%>