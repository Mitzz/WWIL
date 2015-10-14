<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.customer.util.CustomerUtility"%>
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.admin.dao.AdminDao" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

        
<HTML><HEAD>
<% 
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
  
<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK 
href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>

<%

response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
<style type="text/css" media="print">
.printbutton {
  visibility: hidden;
  display: none;
}
</style>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
<%
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(rdate);


String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String crdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
%>
<TABLE cellSpacing=0 cellPadding=0 width="90%">
<TBODY>
<tr>
	<TD class=SectionTitle1 align="left"><input type="button"  value=" Back " onClick=location.href="CustomerSiteBoard.jsp?stateid=<%=stateid%>&id=<%=custid%>&rd=<%=rdate%>"> </TD>	
	<td class=SectionTitle1 align="right" colspan="2">
    Export To:<input type="button"  value="Excel" onClick=location.href="ExportCustomerReportBoard.jsp?stateid=<%=stateid%>&siteid=<%=siteid%>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>">
    <input type="button"  value="PDF" onClick=location.href="ExportCustomerReportBoardPDF.jsp?stateid=<%=stateid%>&siteid=<%=siteid%>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>">
    <input type="button" value="Print" onClick="window.print()" class="printbutton"></TD>
</TR>
<TR>
	<td class=SectionTitle1 colspan="1" align="left"><input type="button" style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="CustomerReportBoard.jsp?id=<%=custid%>&rd=<%=prevdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>&type=<%=type%>" class="printbutton"></TD>	
	<td class=SectionTitle1 colspan="2"  align="right"><input type="button" style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="CustomerReportBoard.jsp?id=<%=custid%>&rd=<%=nextDate%>&siteid=<%=siteid%>&stateid=<%=stateid%>&type=<%=type%>" class="printbutton"></TD>
</TR>
<TR>
<%if(type.equals("M")){ %>
 <td  class=SectionTitle1 colspan="3" >
				    	
 
 <a href="AllMachineReportM.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>">View All Machines</a> </TD>

<%}%>
<%if(type.equals("Y")){ %>
 <td  class=SectionTitle1  colspan="3"><a href="AllMachineReportY.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>">View All Machines</a> </TD>

<%}%>
</tr>


</TBODY></TABLE>
<%List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 
String cls="TableRow1";
String ebid="";
String cname="";
String state="";
String Remarks = "";
String RemarksWEC = "";
for (int i=0; i <tranList.size(); i++)
{
	Remarks = "";
	RemarksWEC = "";
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);
%>


<% 
AdminDao adminDao=new AdminDao();
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
String FromDatetxt= adminDao.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");				


java.util.Date ffd = format.parse(rdate);
int month=ffd.getMonth()+1;
int day=ffd.getDay();

String year=rdate.substring(6);
//String syear="";

int cyear = ffd.getYear()-100;
int nyear =cyear;
//System.out.println("Month: " + month);
//System.out.println("Year: " + cyear);
 	 			     	     if(month>=4)
 	 			     	     	{
 	 			     			   nyear=cyear+1;
 	 			     		    }
 	 			     	    else
	 			    	        {   nyear=cyear;
	 			    	            cyear=cyear-1;
	 			    	        }
 	 			     		String pdate="APR-"+Integer.toString(cyear);
 	 			     		String ndate="MAR-"+Integer.toString(nyear);

 	 			     		String monthname="";
 	 			     		if(month==4)
 	 			     		{
 	 			     			   monthname="APRIL";
 	 			     			}
 	 			     			if(month==3)
 	 			     			{	
 	 			     			monthname="MARCH";
 	 			     			}
 	 			     			if(month==1)
 	 			     			{	
 	 			     			monthname="JANUARY";
 	 			     			}
 	 			     			if(month==2)
 	 			     			{
 	 			     			monthname="FEBRUARY";
 	 			     			}

 	 			     			if(month==5)
 	 			     			{
 	 			     				monthname="MAY";
 	 			     			}

 	 			     			if(month==6)
 	 			     			{
 	 			     			monthname="JUNE";
 	 			     			}

 	 			     			if(month==7)
 	 			     			{
 	 			     			monthname="JULY";
 	 			     			}

 	 			     			if(month==8)
 	 			     			{
 	 			     				monthname="AUGUST";
 	 			     			}

 	 			     			if(month==9)
 	 			     			{
 	 			     				monthname="SEPTEMBER";
 	 			     			}

 	 			     			if(month==10)
 	 			     			{
 	 			     				monthname="OCTOBER";
 	 			     			}

 	 			     			if(month==11)
 	 			     			{	
 	 			     				monthname="NOVEMBER";
 	 			     			}

 	 			     			if(month==12)
 	 			     			{	
 	 			     				monthname="DECEMBER";
 	 			     			}

%>



<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0 bgcolor="#0a4224">
  <TBODY>
	  <TR>	  
	    	<TD class=SectionTitle colspan="6" noWrap>Generation Report  </TD>
	  </TR>
  </TBODY>
</TABLE>
  
<P><BR>
 <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = 100% colspan="6"><%=v.get(0)%></td>
		            
		            </tr>
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           
		           
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2> <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = middle  class=TableCell align = left width = 18%>Machines: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=v.get(8)%> </td>
		           
		            <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=v.get(7)%> MW</td>
		           <%if(type.equals("D")){%>
		           	 <td vAlign = middle  class=TableCell align = left width = 18%>Date: </td>
		            <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=FromDatetxt%></td>
		             <%}else if(type.equals("M")||type.equals("DM")){%>
		             <td vAlign = middle  class=TableCell align = left width = 18%>Month: </td>
		         	  <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=monthname%>-<%=year%></td>
		          <%}else if(type.equals("Y")){%>
		           	<td vAlign = middle  class=TableCell align = left width = 18%>Year: </td>
		           	<td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=pdate%>-<%=ndate%></td>
		          
		          <%} %>
		           </tr>

</TBODY></TABLE>

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
							//tranListData = (List) secutils.getWECDataNew(ebid, rdate, type);
							tranListData = (List) CustomerUtility.getOneEBWECWiseInfoForOneDay(ebid, rdate);

							cls = "TableRow1";
							//System.out.println("tranListData" + tranListData.size());
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>

			<TR class=TableTitleRow>
				<TD class=TableCell1 width="14.28%">WEC Name</TD>
				<TD class=TableCell width="8%">Generation(KWH)</TD>
	           	<TD class=TableCell width="8%">Gen. Hours (Hrs.)</TD>
	            <TD class=TableCell width="8%">Lull Hours (Hrs.)</TD>
	            <TD class=TableCell width="8%">Capaciity Factor(%) </TD>
	            <TD class=TableCell width="8%">MachineAvail (%)</TD>           
	            <TD class=TableCell width="8%">Grid Avail (%) </TD>
						

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
								if (!RemarksWEC.equals(".")) {
									RemarksWEC = RemarksWEC + (String) vdata.get(9);

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
						

						<TD class=TableCell1><%=vdata.get(0)%></TD>
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
					<TR class=TableSummaryRow>
						<%
						if (type.equals("D")) {
						%>
						<TD class=TableCell>Total:<%=vdata.get(0)%></TD>

						<%
						} else {
						%>
						<TD class=TableCell>Total:</TD>

						<%
						}
						%>
						<TD class=TableCell><%=vdata.get(1)%></TD>
						<TD class=TableCell><%=vdata.get(2)%></TD>
						<%
						if (vdata.get(9).toString().equals("0")) {
						%>
						<TD class=TableCell><%=vdata.get(3)%></TD>
						<TD class=TableCell><%=vdata.get(6)%></TD>
						<TD class=TableCell><%=vdata.get(4)%></TD>
						<TD class=TableCell><%=vdata.get(5)%></TD>
						<%}else{ %>
						<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
						<%} %>
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
<P><BR>


<%
			tranListData.clear();
			tranListData = (List) secutils.getEBData(ebid, rdate, type);
			cls = "TableRow1";
			//System.out.println(tranListData.size());
			if (tranListData.size() > 0) {
%>

<P></P>
<SPAN class=TableTitle>EB Data </SPAN><BR>
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
					<TR class=TableTitleRow>
						<TD class=TableCell width="16%">Description</TD>
						<TD class=TableCell width="10%">KWH Export</TD>
						<TD class=TableCell width="14%">KWH Import</TD>
						<TD class=TableCell width="11%">Net KWH</TD>

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
								if (!Remarks.equals(".")) {
									{
								Remarks = (String) vdata.get(4);

									}
								}

							if (rem == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>

					<TR class=<%=cls%>>
						<TD class=TableCell><%=vdata.get(0)%></TD>
						<TD class=TableCell><%=vdata.get(1)%></TD>
						<TD class=TableCell><%=vdata.get(2)%></TD>
						<TD class=TableCell><%=vdata.get(3)%></TD>
					</TR>

					<%
							}
							} else {
					%>
				
<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
	<TBODY>

		<tr class=TableTitle>
			<td vAlign=middle class=TableCell1 align=center width=100% colspan="6">NO DATA TO DISPLAY</td>

		</tr>
	<TBODY>
</TABLE>

<%
		}
		tranListData.clear();
%> 


</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>




<%
 if (type.equals("D")) {
 %>



<p></p>
<SPAN class=TableTitle>Remarks </SPAN><BR>
<%
		if (Remarks.equals("")) {
		Remarks = "";
			}
			if (Remarks.equals(".")) {
		Remarks = "";
			}
%> <%
 		if (RemarksWEC.equals("")) {
 		RemarksWEC = "";
 			}
 %>
<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
	<TBODY>

		<tr class=TableRow1>
			<td vAlign=middle class=TableCell11 align=center width=100% colspan="6"><%=Remarks%></td>

		</tr>
		<tr class=TableRow1>

			<td vAlign=middle class=TableCell11 align=center width=100% colspan="6"><%=RemarksWEC%></td>
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
