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
if(file==null || file.equals("")) file="ExportDeactiveHistory.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>
  
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %> 

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
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>

</HEAD>
<BODY>

  
  
<%
String custid=request.getParameter("cust");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");


%>

<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getLoginHistoryDetail(); 
String cls="TableRow1";
String ebid="";
String cname="";
String state="";
 %>
<TABLE BORDER="1">	<TBODY>
		<TR>
		   <TD width="50"><B>No.</B></TD>
          <TD width="200"><B>Customer Name</B></TD>
          <TD width="100"><B>Login ID</B></TD>
          <TD width="100"><B>Password</B></TD>
          <TD width="400"><B>Reasons</B></TD>
          <TD width="100"><B>Deactived By</B></TD>
          <TD width="100"><B>Deactived Date</B></TD>
           <TD width="100"><B>Actived By</B></TD>
          <TD width="100"><B>Actived Date</B></TD>
        </TR>  
 <%   	  
for (int i=0; i <tranList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);
%>


  
        <TR>
          <TD width="50"><%=i+1%></TD>
          <TD><%=v.get(1)%></TD>
          <TD><%=v.get(2)%></TD>
          <TD><%=v.get(3)%></TD>
          <TD><%=v.get(4)%></TD> 
          <TD><%=v.get(5)%></TD>
          <TD><%=v.get(6)%></TD>
          <TD><%=v.get(7)%></TD>
          <TD><%=v.get(8)%></TD>
          </TR>
        
        <% }tranList.clear();%> 
        
</TBODY></TABLE>	

</BODY></HTML>