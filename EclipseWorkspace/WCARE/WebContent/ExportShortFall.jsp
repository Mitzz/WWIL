<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "ShortFall.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>

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
<TITLE>Short Fall Report</TITLE>

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
%>


</HEAD>
<BODY bottomMargin="0" leftMargin="0" topMargin="0" 
rightMargin="0" marginheight="0" marginwidth="0">
<CENTER>
  
  
<%

String Transactions=request.getParameter("Transactions");
String SiteTransactions=request.getParameter("SiteTransactions");
String FiscalYeartxt=request.getParameter("FiscalYeartxt");
int typetxt=Integer.parseInt(request.getParameter("typetxt"));
AdminUtil adminutil = new AdminUtil();
String comma[] = FiscalYeartxt.split("-");
String eYear = comma[1];
String enddate = "";
java.util.Date dd = new java.util.Date();
java.util.Date cdd = new java.util.Date();
SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
%> 

<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
  <TBODY>
  <TR> 
        <%if (typetxt == 0){ %>
       		<TD colspan="11" >Customer Wise Short Fall Report</TD>
       	<%}else{ %>
       		<TD colspan="11" >WEC Wise Short Fall Report</TD>
       	<%} %>
    
    </TR></TBODY></TABLE>
  
<BR>

<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
  <TBODY>
  <TR>
    <TD>
      <TABLE  cellSpacing="1" cellPadding="2" width="100%" border="1">
        <TBODY>
       <%  
       List tranListData = new ArrayList();
       tranListData = (List)adminutil.getWECShortFall(SiteTransactions,Transactions,FiscalYeartxt,typetxt);   
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR >
    	  <TD  width="4%">Sr. No</TD>
    	  	<%if (typetxt == 0){ %>
          		<TD  width="15%">Customer</TD>
    	  		<TD  width="7%">State</TD>
          		<TD  width="4%">Wec</TD>          
          	<%}else{ %>
          		<TD  width="26%">Wec Name</TD>
          	<%} %>
    	  <TD  width="7%">Start Date</TD>
          <TD  width="7%">End Date</TD>
          <TD  width="8%">Generation Gaurantee</TD>
          <TD  width="5%">Grid</TD>
          <TD  width="5%">MA</TD>
          <TD  width="8%">Actual Generation</TD>          
          <TD  width="5%">Actual Grid</TD>
          <TD  width="5%">Actual M</TD>
          <TD  width="7%">Gen. at Comm. Grid</TD>
          <TD  width="7%">Short Fall</TD>
          <TD  width="7%">Short Fall @ Comm. Grid</TD>
          <TD  width="6%">Short Fall %</TD>          
          <TD  width="6%">Ava Diff (Comm % - Act %)</TD>
          <TD  width="10%">Remarks</TD>
        </TR>   
   <%  
	   for (int j=0; j <tranListData.size(); j++)
		{   
			Vector vdata = new Vector();
			vdata = (Vector)tranListData.get(j);
      		%>
	        <TR >
	           	<TD ><%=j+1%></TD>
	           	<%if (typetxt == 0){ %>
	          		<TD ><%=vdata.get(0)%></TD>
	          		<TD ><%=vdata.get(1)%></TD>
	          		<TD ><%=vdata.get(2)%></TD>
	          	<%}else{ %>
	          		<TD  ><%=vdata.get(2)%></TD>
	          	<%}
	           	enddate = vdata.get(4).toString().substring(0,vdata.get(4).toString().length() - 5) + "-" + eYear;
	           	cdd =format.parse(enddate);
	           	String remarks = "";
	           	if (cdd.after(dd))
	           	{
	           		remarks = "Period is not yet completed <br> for the selected fiscal year";
	           	}
	           	%>
	          	<TD ><%=vdata.get(3)%></TD>
	     	  	<TD ><%=vdata.get(4)%></TD>
	     	  	<TD ><%=vdata.get(5)%></TD> 
	     	  	<TD ><%=vdata.get(6)%></TD>
	     	  	<TD ><%=vdata.get(7)%></TD>
	     	  	<TD ><%=vdata.get(8)%></TD>
	     	  	<TD ><%=vdata.get(9)%></TD>
	     	  	<TD ><%=vdata.get(10)%></TD> 
	     	  	<TD ><%=vdata.get(11)%></TD>
	     	  	<TD ><%=vdata.get(12)%></TD>
	     	  	<TD ><%=vdata.get(13)%></TD>
	     	  	<TD ><%=vdata.get(14)%></TD>
	     	  	<TD ><%=vdata.get(15)%></TD>
	     	  	<TD ><%=remarks%></TD>
          	</TR>
        
        <% }}
      else
      {%>
    	  
      
     <% }tranListData.clear();%> 
        
      




        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE><BR>
  
     
      
</CENTER></BODY></HTML>