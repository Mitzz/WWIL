<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
 
<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="ExportData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

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

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();

%>
</HEAD>
<BODY>

  
  
<%
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");
%>

<%
List tranList = new ArrayList();
//List sitetranList = new ArrayList();
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




 <TABLE width="90%" border="1">
  <TBODY>
  
<tr BGCOLOR="#E0FFFF">
<td vAlign = "middle" align = "center" width = "100%" colspan="8"><%=v.get(0)%></td>
		            
		            </tr>
		            <tr >
		          <td vAlign = "middle" width = "100%" align = "center" colspan="8"></td>
		           </tr>
		           
		           
		           <tr>
		           <td vAlign = "middle" align = "center" width = "100%" colspan="8"></td>
		           </tr>

		           <tr>
		           <td vAlign = "middle" align = "left" width = "20%" colspan="2">Location:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="2"> <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = "middle"  align = "left" width = "18%" colspan="2">Machines: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=v.get(8)%></td>
		           </tr>
		            <tr >
		           <td vAlign = "middle" width = "100%" align = "center" colspan="8"></td>
		           </tr>
		           <tr >
		           <td vAlign = "middle" align = "left" width = "20%" colspan="2">Location Capacity:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="2"><%=v.get(7)%> MW</td>
		           <%if(type.equals("D")){%>
		           	 <td vAlign = "middle"  align = "left" width = "18%" colspan="2">Date: </td>
		            <td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=FromDatetxt%></td>
		             <%}else if(type.equals("M")||type.equals("DM")){%>
		             <td vAlign = "middle"  align = "left" width = "18%" colspan="2">Month: </td>
		         	  <td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=monthname%>-<%=year%></td>
		          <%}else if(type.equals("Y")){%>
		           	<td vAlign = "middle"  align = "left" width = "18%" colspan="2">Year: </td>
		           	<td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=pdate%>-<%=ndate%></td>
		          
		          <%} %>
		           </tr>

</TBODY></TABLE>

<TABLE  border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE  width="100%" border="1">
				<TBODY>
					<TR BGCOLOR="#FFF8C6"><TD align="center" colspan="8"><B>WEC DATA</B></TD></TR>

					<%
							List tranListData = new ArrayList();
							tranListData = (List) secutils.getWECData(ebid, rdate, type);

							cls = "TableRow1";
							//System.out.println("tranListData" + tranListData.size());
							int wecsize = tranListData.size();
							if (wecsize > 0) {
					%>

					<TR>
						<TD  width="14.28%">WEC Name</TD>
						<TD  width="14.28%">Generation</TD>
						<TD  width="14.28%">Operating Hours</TD>
						<TD  width="14.28%">Lull Hours</TD>
						<TD  width="10%">Capacity Factor(%)</TD>
						<TD  width="10%">Machine Availability(%)</TD>
						<TD  COLSPAN = "2" width="10%">Grid  Availability(%) </TD>	
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
					<TR>
						

						<TD ><%=vdata.get(0)%></TD>
						<TD ><%=vdata.get(2)%></TD>
						<TD ><%=vdata.get(3)%></TD>
						<TD ><%=vdata.get(4)%></TD>
						<%
						if (!vdata.get(10).toString().equals("1")) {
						%>





						<TD ><%=vdata.get(7)%></TD>
						<TD ><%=vdata.get(5)%></TD>
						<TD COLSPAN="2" ><%=vdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD  colspan="5">WEC Is In Stabilization Phase</TD>


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
						<TD ><%=vdata.get(4)%></TD>
						<TD ><%=vdata.get(5)%></TD>
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






<%
			tranListData.clear();
			tranListData = (List) secutils.getEBData(ebid, rdate, type);
			cls = "TableRow1";
			//System.out.println(tranListData.size());
			if (tranListData.size() > 0) {
%>

<P></P>
<SPAN >EB Data </SPAN><BR>

<TABLE  border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE width="100%" border="1">
				<TBODY>
					<TR>
						<TD  width="16%" colspan="2">Description</TD>
						<TD  width="10%" colspan="2">KWH Export</TD>
						<TD  width="14%" colspan="2">KWH Import</TD>
						<TD  width="11%" colspan="2">Net KWH</TD>

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

					<TR>
						<TD colspan="2"><%=vdata.get(0)%></TD>
						<TD colspan="2"><%=vdata.get(1)%></TD>
						<TD colspan="2"><%=vdata.get(2)%></TD>
						<TD colspan="2"><%=vdata.get(3)%></TD>
					</TR>

					<%
							}
							} else {
					%>
				
<TABLE  border="1">
	<TBODY>

		<tr >
			<td vAlign="middle"  align="center"width="100%" colspan="8">NO DATA TO DISPLAY</td>

		</tr>
	</TBODY>
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
<SPAN >Remarks </SPAN><BR>
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
<TABLE  border="1">
	<TBODY>

		<tr>
			<td vAlign="middle" align="center"width="100%" colspan="8"><%=Remarks%></td>

		</tr>
		<tr>

			<td vAlign="middle"  align="center"width="100%" colspan="8"><%=RemarksWEC%></td>
		</tr>
	</TBODY>
</TABLE>

<%
}
	}
	tranList.clear();
%>



</BODY>
</HTML>
