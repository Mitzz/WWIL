<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
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
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<CENTER>
<%
	String custid = request.getParameter("id");
	String stateid = request.getParameter("stateid");
	
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

// String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
// String crdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
// String ardate = dateFormat.format(date.getTime());
// String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

%>


<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>

		<tr>
			<TD align="left" colspan="2"><input type="button" class="printbutton" value="Back" onClick=location.href="DGRView.jsp?id=<%=custid%>&rd=<%=rdate%>&type=<%=Atype%>"></TD>

			
			
			<%
			if (type.equals("Y")) {
			%>
			<td colspan="2" align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</TD>

			<%
			} else {
			%>
			<td colspan="2" align="center"></td>

			<%
			}
			%>

			<td align="right" colspan="2"><input type="button" value="Print" onClick="window.print()" class="printbutton"><input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="ExportStateCumulativeReport.jsp?stateid=<%=stateid%>&id=<%=custid%>&type=<%=Atype%>&rd=<%=rdate%>"></td>
		</TR>
		<TR>
			<td colspan="6"></td>
		</tr>

	</TBODY>
</TABLE>

<%
	List tranList = new ArrayList();
	List sitetranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	tranList = (List) secutils.getEBHeadingState(custid, rdate, stateid);
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



<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD class=SectionTitle colspan="6" align="center" noWrap>Generation Report&nbsp;&nbsp;&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>
<P><BR>
<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
	<TBODY>
		<tr class=TableTitle>
			<td vAlign=middle class=TableCell align=center width=100% colspan="6"><%=v.get(0)%></td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell width=100% align=center colspan="6"></td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell align=center width=100% colspan="6"></td>
		</tr>

		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell1 align=left width=20%>State:</td>
			<td vAlign=middle align=left width=38% colspan=2><%=v.get(3)%></td>

			<td vAlign=middle class=TableCell1 align=left width=18%>Total WEC:</td>
			<td vAlign=middle align=left width=24% colspan=2><%=v.get(4)%> </td>
		<tr class=TableSummaryRow>
			<td vAlign=middle width=100% align=left colspan="6"></td>
		</tr>
		<tr class=TableSummaryRow>
			<td vAlign=middle class=TableCell1 align=left width=20%>Location Capacity:</td>
			<td vAlign=middle align=left width=38% colspan=2><%=v.get(5)%> MW</td>
			<%
			if (type.equals("D")) {
			%>
			<td vAlign=middle class=TableCell1 align=left width=18%>Date:</td>
			<td vAlign=middle align=left width=24% colspan=2><%=FromDatetxt%></td>
			<%
			} else if (type.equals("M") || type.equals("DM")) {
			%>
			<td vAlign=middle class=TableCell1 align=left width=18%>Month:</td>
			<td vAlign=middle align=left width=24% colspan=2><%=monthname%>-<%=year%></td>
			<%
			} else if (type.equals("Y")) {
			%>
			<td vAlign=middle class=TableCell1 align=left width=18%>Year:</td>
			<td vAlign=middle align=left width=24% colspan=2><%=pdate%>-<%=ndate%></td>

			<%
			}
			%>
		</tr>

	</TBODY>
</TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
	<TBODY>
		<TR>
			<TD></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
	<TBODY>
		<TR>
			<TD>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<TBODY>


					<%
							List tranListData = new ArrayList();
							tranListData = (List) secutils.getWECStatewise(stateid,custid,rdate);

							cls = "TableRow1";
							
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>

											<TR class=TableTitleRow>
						           <TD class=TableCell width="14.28%">Month</TD>
						           
						          <TD class=TableCell width="14.28%">Generation</TD> 
						          <TD class=TableCell width="14.28%">Operating Hours </TD>
						          <TD class=TableCell width="14.28%">Lull Hours </TD>
						          <TD class=TableCell width="14.28%">Capacity Factor(%) </TD>
						           <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
						          <% if(diff<0){ %>
								 <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
								 <%} else{ %>
								 <TD class=TableCell width="14.28%">Grid Internal Availability(%)</TD>
								 <TD class=TableCell width="14.28%">Grid External Availability(%)</TD>
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
						        
						        <TR class=<%=cls%>>
						        
						          <TD class=TableCell><%=vdata.get(0)%></TD>
						          <TD class=TableCell><%=vdata.get(1)%></TD>
						          <TD class=TableCell><%=vdata.get(2)%></TD>
						          <TD class=TableCell><%=vdata.get(3)%></TD>
						          <TD class=TableCell><%=vdata.get(6)%></TD>
						          <% if(diff<0){ %>
								  <TD class=TableCell><%=vdata.get(4)%></TD>
												
									<%}else {%>
						            <TD class=TableCell><%=vdata.get(7)%></TD>
							        <TD class=TableCell><%=vdata.get(8)%></TD>
												
												<%}%>
						          <TD class=TableCell><%=vdata.get(5)%></TD>
						          </TR>
						        
						        <% }%> 
						        
					<%
								tranListData.clear();
								if (wecsize > 1) {
							tranListData = (List) secutils.getStateWiseTotal(stateid,custid,rdate, type);
							for (int j = 0; j < tranListData.size(); j++) {
								Vector vdata = new Vector();
								vdata = (Vector) tranListData.get(j);
								//String name = (String)vdata.get(0);
					%>
					<TR class=TableSummaryRow>
						
						<TD class=TableCell>Total:</TD>
						<TD class=TableCell><%=vdata.get(1)%></TD>
						<TD class=TableCell><%=vdata.get(2)%></TD>
						
						
						
						
						<%
						
						if(vdata.get(9).toString().equals("0")){ %>
							<% if(diff<0){ %>
							<TD class=TableCell><%=vdata.get(3)%></TD>
							<TD class=TableCell><%=vdata.get(6)%></TD>
							<TD class=TableCell><%=vdata.get(4)%></TD>
							
							<%}else {%>
							<TD class=TableCell><%=vdata.get(3)%></TD>
							<TD class=TableCell><%=vdata.get(6)%></TD>
	                          <TD class=TableCell><%=vdata.get(7)%></TD>
							  <TD class=TableCell><%=vdata.get(8)%></TD>
							
							<%}%>
							<TD class=TableCell><%=vdata.get(5)%></TD>
						
						<%} else{%>
						   <% if(diff<0){ %>
						   <TD class=TableCell colspan=4>WEC Is In Stabilization Phase</TD>
						   <%}else {%>
						    <TD class=TableCell colspan=5>WEC Is In Stabilization Phase</TD>
						<%}} %>
					</TR>

					<%
								}
								}}
					%>

				</TBODY>
			</TABLE>
			</TD>
		</TR>
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
