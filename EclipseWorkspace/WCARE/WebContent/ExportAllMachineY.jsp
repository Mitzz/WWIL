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
if(file==null || file.equals("")) file="Export.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%@ include file="FusionCharts.jsp"%>
<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=windows-1252">

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
</HEAD>
<BODY>
<center>
  
  
<%

String custid=request.getParameter("custid");
String rdate=request.getParameter("rd");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");

String ebid="";
String wecid="";
String state="";

String cname="";
String type="Y";
String wectype="";
String wecname="";


SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
String adate="01/04/3009";

java.util.Date afd = format.parse(adate);
long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
int month=ffd.getMonth()+1;


//int month=ffd.getMonth()+1;


String year=rdate.substring(6);
//String syear="";




int cyear = 2000+ffd.getYear()-100;
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
 	 			     		String pdate=Integer.toString(cyear);
 	 			     		String ndate=Integer.toString(nyear);;
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

  
<BR>
<%

List tranList = new ArrayList();

CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 

String Remarks="NIL";
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
      //System.out.println("tranListData"+tranListData.size());
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
 <TABLE cellSpacing="1" cellPadding="1" width="90%" border="3">
 <tr><td>
   <TABLE cellSpacing="1" cellPadding="1" width="90%" border="1">
    <TBODY>
  
   <tr >
       <td vAlign = "middle" align = "center" width = "100%" colspan="7"><%=cname%></td>
		            
		            </tr>
		            <tr >
		          <td vAlign = "middle" width = "100%" align = "center" colspan="7"></td>
		           </tr>
		           
		       
		             
		           <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="7"></td>
		           </tr>

		           <tr >
		           <td vAlign = "middle" align = "left" width = "20%" colspan="2">Location:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="2"><%=state%> </td>
		           
		            <td vAlign = "middle"  align = "left" width = "18%" >Year: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=pdate%>-<%=ndate%></td>
		           
		             </tr >
		              <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="7"></td>
		           </tr>
		           <tr >
		           <td vAlign = "middle" align = "left" width = "20%" colspan="2">Capacity:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="2"><%=wectype%> </td>
		           
		            <td vAlign = "middle"  align = "left" width = "18%" >WEC: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="2"><%=wecname%></td>
		           
		             </tr >
		              <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="7"></td>
		           </tr>
		          
		          

</TBODY></TABLE>

<P></P>
<SPAN >WEC Data </SPAN><BR>


      <TABLE cellSpacing="1" cellPadding="2" width="100%" border="1">
        <TBODY>
        
          
       <%  
       
       
       List wecListData = new ArrayList();
        wecListData = (List)secutils.getWECMonthwise(ebid,wecid,rdate,type);  
         
      cls="TableRow1";
      //System.out.println("tranListData"+wecListData.size());
      int  wec_size=wecListData.size();
      if(wec_size>0){ %>
    	  
    	<TR>
           <TD width="14.28%">Month</TD>
           
          <TD width="14.28%">Generation</TD>
          <TD width="14.28%">Operating Hours </TD>
          <TD width="14.28%">Lull Hours </TD>
          <TD width="14.28%">Capacity Factor(%) </TD>
          <TD width="14.28%">Machine Availability(%)</TD>
          <% if(diff<0){ %>
		 <TD width="14.28%"  >Grid Availability(%)</TD>
		 <%} else{ %>
		 <TD width="14.28%">Grid Availability(%) Internal </TD>
		 <TD width="14.28%">Grid Availability(%) External </TD>
		 <%} %>
          
         
          
        </TR>  
    	  
    	  
   <%   for (int k=0; k <wecListData.size(); k++)
			{   
				Vector wecdata = new Vector();
				wecdata = (Vector)wecListData.get(k);
				String name = (String)wecdata.get(0);
			
				int rem=1;
				rem=j%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR>
        
          
          
						<TD ><%=wecdata.get(0)%></TD>
						<TD ><%=wecdata.get(1)%></TD>
						<TD><%=wecdata.get(2)%></TD>
						<TD ><%=wecdata.get(3)%></TD>
						<TD><%=wecdata.get(6)%></TD>
						<%
						if (diff < 0) {
						%>
						<TD  ><%=wecdata.get(4)%></TD>

						<%
						} else {
						%>
						<TD><%=wecdata.get(7)%></TD>
						<TD><%=wecdata.get(8)%></TD>

						<%
						}
						%>

						<TD><%=wecdata.get(5)%></TD>
					</TR>

               
        
        
        
        <% }%> 
        
      




        <% 	
        wecListData.clear();
        wecListData = (List)secutils.getWECDetailTotal(ebid,wecid,rdate,"Y");   
        for (int l=0; l <wecListData.size(); l++)
		{
			Vector wecdata = new Vector();
			wecdata = (Vector)wecListData.get(l);
			//String name = (String)vdata.get(0);
			%> 
			
			<TR >
			
	                <TD>Total:</TD>

						<TD ><%=wecdata.get(0)%></TD>
						<TD ><%=wecdata.get(1)%></TD>
						<TD ><%=wecdata.get(2)%></TD>
						<TD ><%=wecdata.get(5)%></TD>
						<%
						if (diff < 0) {
						%>
						<TD  ><%=wecdata.get(3)%></TD>

						<%
						} else {
						%>
						<TD ><%=wecdata.get(6)%></TD>
						<TD ><%=wecdata.get(7)%></TD>

						<%
						}
						%>


						<TD><%=wecdata.get(4)%></TD>
					
             
          </TR>
	        
	        <% }}
      wecListData.clear();
      
	        %> 
        
        </TBODY></TABLE> </TD></TR>
<P></p><BR>
  
    	<% }}%>
      <P></p></TABLE><P></p><P></p>
        <% tranListData.clear();}%> 
</center></BODY></HTML>