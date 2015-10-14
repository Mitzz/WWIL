<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

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
<META http-equiv=Content-Type content="text/html; charset=windows-1252">

<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>

</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>
		<tr>
			<td align="right" colspan="2"><input type="button" value="Print" onClick="window.print()" class="printbutton"></td>
		</tr>
	</TBODY>
</TABLE>
<CENTER>
<%
	
	String stateid = request.getParameter("stateid");
	String siteid = request.getParameter("siteid");
	String rdate = request.getParameter("rd");
	String type = "D";
	
%> <%
	List tranList = new ArrayList();
	// List sitetranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	tranList = (List) secutils.getEBHeadingSiteWise(stateid,siteid); 
	String cls = "TableRow1";
	String ebid = "";
	String cname = "";
	String state = "";
	
	String RemarksWEC = "";
	for (int i = 0; i < tranList.size(); i++) {
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		RemarksWEC = "";
		ebid = (String) v.get(1);
		cname = (String) v.get(0).toString().replaceAll("&", "and");
		//state = (String) v.get(3) + "-" + (String) v.get(4);
%>


<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD class=SectionTitle colspan="6" align="center" noWrap>Generation Report:&nbsp;&nbsp;&nbsp;</TD>
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
							tranListData = (List) secutils.getWECDataUpload(ebid, rdate, type);

							cls = "TableRow1";
							//System.out.println("tranListData" + tranListData.size());
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>

					<TR class=TableTitleRow>
						<TD class=TableCell width="14.28%">WEC Name</TD>

						<TD class=TableCell width="14.28%">Generation</TD>
						<TD class=TableCell width="14.28%">Operating Hours</TD>
						<TD class=TableCell width="14.28%">Lull Hours</TD>
						<TD class=TableCell width="14.28%">Capacity Factor(%)</TD>
						<TD class=TableCell width="14.28%">Machine Availability(%)</TD>
						<TD class=TableCell width="14.28%">Grid Availability(%)</TD>

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
					<TR class=<%=cls%>>

						<TD class=TableCell><%=vdata.get(0)%></TD>



						<TD class=TableCell><%=vdata.get(2)%></TD>
						<TD class=TableCell><%=vdata.get(3)%></TD>
						
						<%
						if (!vdata.get(10).toString().equals("1")) {
						%>




						<TD class=TableCell><%=vdata.get(4)%></TD>
						<TD class=TableCell><%=vdata.get(7)%></TD>
						<TD class=TableCell><%=vdata.get(5)%></TD>
						<TD class=TableCell><%=vdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>


						<%
						}
						%>

					</TR>

					<%
					}}
					%>
					<%
								tranListData.clear();
								%>

				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
<P><BR>

<%
 if (type.equals("D")) {
 %>

<p></p>
<SPAN class=TableTitle>Remarks </SPAN><BR>

<% if(RemarksWEC.equals("")){ 
			RemarksWEC="";
		} %>
<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
	<TBODY>


		<tr class=TableRow1>

			<td vAlign=middle class="TableCell11" align=center width=100% colspan="6"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>

<%
}
%>




<P></P>
<P></P>

<%
	}
	tranList.clear();
%>
<P></P>

</CENTER>
</BODY>
</HTML>
