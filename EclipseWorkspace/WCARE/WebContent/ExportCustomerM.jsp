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
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.enercon.global.utils.Diff" %>

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
<BODY">

  
  
<%
String ebid=request.getParameter("ebid");
String wecid=request.getParameter("wecid");
String state=request.getParameter("state");
state=state.replace("/","-");
String cname=request.getParameter("cname");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");
String wectype=request.getParameter("wectype");
String wecname=request.getParameter("wecname");
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);

String adate="01/04/3009";
	
	java.util.Date afd = format.parse(adate);
	long diff1=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
	
	
int month=ffd.getMonth()+1;
Diff diff=new Diff();

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


  

 <TABLE width="100%" border="1">
  <TBODY>
  
<tr>
<td vAlign = "middle"  align = "center" width = "100%" colspan="7"><%=cname%></td>
		            
		            </tr>
		            <tr>
		          <td vAlign = "middle"  width = "100%" align = "center" colspan="7"></td>
		           </tr>
		           
		       
		             
		           <tr>
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="7"></td>
		           </tr>

		           <tr>
		           <td vAlign = "middle" 1 align = "left" width = "20%" colspan="2">Location:</td>
		           <td vAlign = "middle"  align = "left" width = "38%" colspan="2"><%=state%> </td>
		           
		            <td vAlign = "middle"  1 align = "left" width = "18%" >Month: </td>
		           <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=monthname%>-<%=year%></td>
		           </tr>
		             <tr>
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="7"></td>
		           </tr>
		           <tr>
		           <td vAlign = "middle" 1 align = "left" width = "20%" colspan="2">Capacity:</td>
		           <td vAlign = "middle"  align = "left" width = "38%" colspan="2"><%=wectype%> </td>
		           
		            <td vAlign = "middle"  1 align = "left" width = "18%" >WEC: </td>
		           <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=wecname%></td>
		           </tr>
		             <tr>
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="7"></td>
		           </tr>
		          
		          

</TBODY></TABLE>


<SPAN>WEC Data </SPAN><BR>

<TABLE border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE  width="100%" border="1">
        <TBODY>
        
          
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDetail(ebid,wecid,rdate,type);  
         
      cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){
    	 float cfactor=0,mavail=0,gavail=0;
    	 int cnt=0,trialexists=0;
    	 
    	  
    	  
    	  
    	  
    	  %>
    	  
    	<TR>
					<TD width="10.28%"><b>WEC Name</b></TD>

					<TD width="5.28%"><b>Generation</b></TD>
					<TD width="5.28%"><b>Operating Hours</b></TD>
					<TD width="5.28%"><b>Lull Hours</b></TD>
					<TD width="5.28%"><b>Capacity Factor(%) </b></TD>
					<TD width="7.28%"><b>Machine Availability(%)</b></TD>
					<% if(diff1<0){ %>
						<TD  width="14.28%" ><b>Grid Availability(%)</b></TD>
						<%} else{ %>
						<TD  width="7.14%"><b>Grid  Availability(%) Internal</b></TD>
						<TD  width="7.14%"><b>Grid  Availability(%) External</b></TD>
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
        
          
          <TD ><%=vdata.get(2)%></TD>
          <TD ><%=vdata.get(3)%></TD>
          <TD ><%=vdata.get(4)%></TD>
          
          
          
        <%if(!vdata.get(9).toString().equals("1"))
							{%>
							
							 
						
						
						<TD ><%=vdata.get(5)%></TD>
						<TD ><%=vdata.get(8)%></TD>
						<% if(diff1<0){ %>
						<TD ><%=vdata.get(6)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<%}else {%>
                          <TD ><%=vdata.get(10)%></TD>
						  <TD ><%=vdata.get(11)%></TD>
						<TD ><%=vdata.get(7)%></TD>
						<%}%>
						
						<% 
						
						
							}else{ %>
						
						<% if(diff1<0){ %>
						<TD  colspan="4">WEC Is In Stabilization Phase</TD>
						<%}else {%>
						<TD  colspan="5">WEC Is In Stabilization Phase</TD>
						<%}%>
						<%
						}
						%>
						
						
						<%	}%>
          
          </TR>
        
        <% }%> 
        
      




        <% 	
        tranListData.clear();
        tranListData = (List)secutils.getWECDetailTotal(ebid,wecid,rdate,"M");   
        for (int j=0; j <tranListData.size(); j++)
		{
			Vector vdata = new Vector();
			vdata = (Vector)tranListData.get(j);
			//String name = (String)vdata.get(0);
			%> 
			
			<TR>
			
	        <TD >Total:</TD>
	        
          <TD ><%=vdata.get(0)%></TD>
          <TD ><%=vdata.get(1)%></TD>
          <TD ><%=vdata.get(2)%></TD>
         
          <TD ><%=vdata.get(5)%></TD>
          <% if(diff1<0){ %>
			  		<TD ><%=vdata.get(3)%></TD>
			  		<TD > <%=vdata.get(4)%></TD>			
			<%}else {%>
        		 	<TD ><%=vdata.get(6)%></TD>
		 			<TD ><%=vdata.get(7)%></TD>
			 		<TD> <%=vdata.get(4)%></TD>			
			  <%}%>
        
          
          </TR>
	        
	        <% }%>
     
	        
	         
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>

      
      
</BODY></HTML>