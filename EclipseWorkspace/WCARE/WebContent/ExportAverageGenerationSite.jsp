<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>

<%@ page import="java.util.*"%>

<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="ExportDataSite.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<html>

    <head>       
       <title>Site Wise - Average Generation</title>       
    </head>
   
    <body>
	    <form>	    
		   <%   
			   String id=request.getParameter("id");
			   String fdate=request.getParameter("fdate");
			   String tdate=request.getParameter("tdate");
			   String gen=request.getParameter("gen");
			   String state=request.getParameter("state");
			 
			   List tranList = new ArrayList();
			   List tranListWECTypt = new ArrayList();
			   CustomerUtil secutils = new CustomerUtil();			
			   
			   if(gen.equals("0"))
			   {
			      tranList = (List)secutils.getSiteWiseAverageGeneration(id,fdate,tdate);
			      tranListWECTypt = (List)secutils.getWECTypeWiseAverageGeneration(id,fdate,tdate);
			   }
			   else if(gen.equals("1"))
			   {
				  tranList = (List)secutils.getSiteWiseAverageGenerationAvg(id,fdate,tdate);
				  tranListWECTypt = (List)secutils.getWECTypeWiseAverageGenerationAvg(id,fdate,tdate);
			   }		 
			 
		 %> 
		    
		  <TABLE cellSpacing=1 cellPadding=2 width="100%" border=1>
				<TBODY>
					<TR>
						<TD COLSPAN="2" ALIGN="CENTER"><b>
	  						<%=state%> - Selected Date: <%=fdate %> To  <%=tdate %></b> 
	  					</TD>
	  				</TR>	  				
	   				<tr><td colspan="2" align="center"><b>Site Wise Generation/Avg Generation</b></td></tr>
					<TR>
							<TD width="14.28%" align="center">Site Name</TD>	
							<% if(gen.equals("0")) {%>
							<TD width="14.28%" align="center">Generation</TD>
							<% }else { %>
							<TD width="14.28%" align="center">Average Generation</TD>
							<% } %>
					</TR>	
						 <%  
						 for (int i=0; i <tranList.size(); i++)
							{
								Vector v = new Vector();
								v = (Vector)tranList.get(i);							    
						%>
					<TR>
						<TD><%=v.get(0)%></TD>
					    <TD><%=v.get(1)%></TD>
					</TR>
					    <%}						 
						 	tranList.clear();
						 %>	
					<tr><td colspan="2"></td></tr>
					<tr><td colspan="2" align="center"><b>WEC Type Wise Generation/Avg Generation</b></td></tr>		
					<TR>
							<TD width="14.28%" align="center">Site Name</TD>	
							<% if(gen.equals("0")) {%>
							<TD width="14.28%" align="center">Generation</TD>
							<% }else { %>
							<TD width="14.28%" align="center">Average Generation</TD>
							<% } %>
					</TR>	
						 <%  
						 for (int i=0; i <tranListWECTypt.size(); i++)
							{
								Vector v = new Vector();
								v = (Vector)tranListWECTypt.get(i);							    
						%>
					<TR>
						<TD><%=v.get(0)%></TD>
					    <TD><%=v.get(1)%></TD>
					</TR>
					    <%}						 
							tranListWECTypt.clear();
						 %>											
				</TBODY>
			</TABLE>
	   </form>                 
    </body>
</html>
