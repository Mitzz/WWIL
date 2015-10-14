<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ include file="FusionCharts.jsp"%>
<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<style type="text/css" media="print">
.printbutton {
  visibility: hidden;
  display: none;
}
</style>
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
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
<%

String custid=request.getParameter("custid");
String rdate=request.getParameter("rd");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");

String ebid="";
String wecid="";
String state="";

String cname="";
String type="M";
String wectype="";
String wecname="";


SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);

String adate="01/04/3009";
	
java.util.Date afd = format.parse(adate);
long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
int month=ffd.getMonth()+1;


String year=rdate.substring(6);
//String syear="";


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
String cls="TableRow1";

%>

<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0 class=SectionTitle1>
  <TBODY>
  <tr><td class=SectionTitle1 align="left" >
  
  <input  type="button" class="printbutton" value="Back" onClick=location.href="CustomerReport.jsp?stateid=<%= stateid%>&siteid=<%=siteid%>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>">
  </td>
 
  <td  class=SectionTitle1  colspan="2" align="right">
  
 <input type="button" value="Print" onClick="window.print()" class="printbutton"> <input  type="button"  value="Excel" class="printbutton" onClick=location.href="ExportAllMachineM.jsp?custid=<%=custid%>&rd=<%=rdate%>&siteid=<%=siteid%>&stateid=<%=stateid%>">
  </td>
  </tr>
  <TR>
    
    <TD class=SectionTitle colspan="3" noWrap>Monthly Generation Report  </TD>
    </TR></TBODY></TABLE>
  
<P><BR>
<%

List tranList = new ArrayList();

CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 

String Remarks="NA";
for (int i=0; i <tranList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);

  %>
  <%  
        
          List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECData(ebid,rdate,type); 
         
      cls="TableRow1";
      ////System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0)
      { 
    	    for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				wecname = (String)vdata.get(0);
				wecid=(String)vdata.get(8);
				wectype=(String)vdata.get(1);
		
      
      
      %>
 <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = 100% colspan="6"><%=cname%></td>
		            
		            </tr>
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           
		       
		             
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=state%> </td>
		           
		            <td vAlign = middle  class=TableCell align = left width = 18%>Month: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=monthname%>-<%=year%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=wectype%> </td>
		           
		            <td vAlign = middle  class=TableCell align = left width = 18%>WEC: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=wecname%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>
		          
		          

</TBODY></TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
  <TBODY>
  <TR>
    <TD></TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
        <TBODY>
        
          
       <%  
       
       
       List wecListData = new ArrayList();
        wecListData = (List)secutils.getWECDetail(ebid,wecid,rdate,type);  
         
      cls="TableRow1";
      ////System.out.println("tranListData"+wecListData.size());
      int  wec_size=wecListData.size();
      if(wec_size>0){ %>
    	  
    	<TR class=TableTitleRow>
           <TD class=TableCell width="14.28%">Date</TD>
           
          <TD class=TableCell width="14.28%">Generation</TD>
          <TD class=TableCell width="14.28%">Operating Hours </TD>
          <TD class=TableCell width="14.28%">Lull Hours </TD>
          <TD class=TableCell width="14.28%">Capacity Factor(%) </TD>
           <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
          <% if(diff<0){ %>
		 <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
		 <%} else{ %>
		 <TD class=TableCell width="14.28%">Grid Availability(%) Internal </TD>
		 <TD class=TableCell width="14.28%">Grid Availability(%) External </TD>
		 <%} %>
          
         
        </TR>  
    	  
   <%   for (int k=0; k <wecListData.size(); k++)
			{   
				Vector wecdata = new Vector();
				wecdata = (Vector)wecListData.get(k);
				String name = (String)wecdata.get(0);
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
				rem=k%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
        
          
          <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(3)%></TD>
          <TD class=TableCell><%=wecdata.get(4)%></TD>
          <%if(!wecdata.get(9).toString().equals("1"))
							{%>
							
							 
						
						
						<TD class=TableCell><%=wecdata.get(5)%></TD>
						<TD class=TableCell><%=wecdata.get(8)%></TD>
						<% if(diff<0){ %>
						<TD class=TableCell><%=wecdata.get(6)%></TD>
						
						<%}else {%>
                          <TD class=TableCell><%=wecdata.get(10)%></TD>
						  <TD class=TableCell><%=wecdata.get(11)%></TD>
						
						<%}%>
						<TD class=TableCell><%=wecdata.get(7)%></TD>
						<% 
						
						
							}else{ %>
						
						<% if(diff<0){ %>
						<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>
						<%}else {%>
						<TD class=TableCell colspan="5">WEC Is In Stabilization Phase</TD>
						<%}%>
						<%
						}
						%>
						
						
						
          
          </TR>
        
        <% }%> 
        
        
      




        <% 	
        wecListData.clear();
        wecListData = (List)secutils.getWECDetailTotal(ebid,wecid,rdate,"M");   
        for (int l=0; l <wecListData.size(); l++)
		{
			Vector wecdata = new Vector();
			wecdata = (Vector)wecListData.get(l);
			//String name = (String)vdata.get(0);
			%> 
			
			<TR class=TableSummaryRow>
			
	        <TD class=TableCell>Total:</TD>
	        
          <TD class=TableCell><%=wecdata.get(0)%></TD>
          <TD class=TableCell><%=wecdata.get(1)%></TD>
          <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(5)%></TD>
          <% if(diff<0){ %>
		 <TD class=TableCell ><%=wecdata.get(3)%></TD>
						
			<%}else {%>
         <TD class=TableCell><%=wecdata.get(6)%></TD>
		 <TD class=TableCell><%=wecdata.get(7)%></TD>
						
			  <%}%>
          <TD class=TableCell><%=wecdata.get(4)%></TD>
          
          </TR>
	        
	        
	        
	        <% }}
      wecListData.clear();
	        %> 
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR>
  
    	<% }} tranListData.clear();}%> 
      
</CENTER></BODY></HTML>