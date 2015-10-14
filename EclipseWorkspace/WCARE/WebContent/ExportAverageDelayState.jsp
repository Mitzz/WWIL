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
if(file==null || file.equals("")) file="ExportDataState.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>



<html>
  <body  >
    <form>
    
   <%   
 

   String fdate=request.getParameter("fdate");
   String tdate=request.getParameter("tdate");
   
   
  
   
   List tranList = new ArrayList();
  // String state="";
  CustomerUtil secutils = new CustomerUtil();
      tranList = (List)secutils.getStateWiseAverage(fdate,tdate);
 %> 
    
  
    
  <TABLE cellSpacing="1" cellPadding="2" width="50%" border="1">
				<TBODY>
					
					 <%  // String cls="";
					 for (int i=0; i <tranList.size(); i++)
						{
							Vector v = new Vector();
							v = (Vector)tranList.get(i);
							
					%>
					
					
					<%if(i==0) {%>
					<TR>
					<TD COLSPAN="2" ALIGN="CENTER">  
  					<b> Selected Date: <%=fdate %> To  <%=tdate %></b> </TD></TR>
  				     <TR>
						<TD  width="14.28%">State Name</TD>

						<TD  width="14.28%">Average Delay Time(In Hrs.)</TD>
					</TR>
  				   
  				   <%} %>
				
				
					<TR>
					<TD  ALIGN="LEFT"><%=v.get(1)%></TD>
				    <TD><%=v.get(2)%></TD>
				    
				    </TR>
				    <%}
					 
					 tranList.clear();
					 %>
					   
				    </TBODY>
				    </TABLE>
              
  
   </form>
     
                 
    </body>
</html>




