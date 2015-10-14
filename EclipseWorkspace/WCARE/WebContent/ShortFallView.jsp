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
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
%>


</HEAD>
<BODY text=#000000 bottomMargin=0 leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
<%

String Transactions=request.getParameter("Transactions");
String SiteTransactions=request.getParameter("SiteTransactions");
String FiscalYeartxt=request.getParameter("FiscalYeartxt");
int typetxt=Integer.parseInt(request.getParameter("typetxt"));
AdminUtil adminutil = new AdminUtil();
String cls="TableRow1";
String comma[] = FiscalYeartxt.split("-");
String eYear = comma[1];
String enddate = "";
java.util.Date dd = new java.util.Date();
java.util.Date cdd = new java.util.Date();
SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
%> 

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  <TR>
  <tr>
     <TD class=SectionTitle >
     <%if (typetxt == 0){ %>
      	<input  type="button" value="Excel" onClick=location.href="ExportShortFall.jsp?Transactions=<%=Transactions%>&SiteTransactions=<%=SiteTransactions%>&FiscalYeartxt=<%=FiscalYeartxt%>&typetxt=0">
      <%}else{ %>
       		<input  type="button" value="Excel" onClick=location.href="ExportShortFall.jsp?Transactions=<%=Transactions%>&SiteTransactions=<%=SiteTransactions%>&FiscalYeartxt=<%=FiscalYeartxt%>&typetxt=1">
      	<%} %>
      	</TD>
      </TR>  
        <TR>  
        <%if (typetxt == 0){ %>
       		<TD class=SectionTitle colspan="11" noWrap>Customer Wise Short Fall Report</TD>
       	<%}else{ %>
       		<TD class=SectionTitle colspan="11" noWrap>WEC Wise Short Fall Report</TD>
       	<%} %>
    
    </TR></TBODY></TABLE>
  
<P><BR>
 
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
  <TBODY>
  <TR>
    <TD></TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#555555 border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE  cellSpacing=1 cellPadding=2 width="100%" border=0>
        <TBODY>
       <%  
       List tranListData = new ArrayList();
       tranListData = (List)adminutil.getWECShortFall(SiteTransactions,Transactions,FiscalYeartxt,typetxt);   
	   cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR class=TableTitleRow>
    	  <TD class=TableCell width="4%">Sr. No</TD>
    	  	<%if (typetxt == 0){ %>
          		<TD class=TableCell width="15%">Customer</TD>
    	  		<TD class=TableCell width="7%">State</TD>
          		<TD class=TableCell width="4%">Wec</TD>          
          	<%}else{ %>
          		<TD class=TableCell width="26%">Wec</TD>
          	<%} %>
    	  <TD class=TableCell width="7%">Start Date</TD>
          <TD class=TableCell width="7%">End Date</TD>
          <TD class=TableCell width="8%">Generation Gaurantee</TD>
          <TD class=TableCell width="5%">Grid</TD>
          <TD class=TableCell width="5%">MA</TD>
          <TD class=TableCell width="8%">Actual Generation</TD>          
          <TD class=TableCell width="5%">Actual Grid</TD>
          <TD class=TableCell width="5%">Actual M</TD>
          <TD class=TableCell width="7%">Gen. at Comm. Grid</TD>
          <TD class=TableCell width="7%">Short Fall</TD>
          <TD class=TableCell width="7%">Short Fall @ Comm. Grid</TD>
          <TD class=TableCell width="6%">Short Fall %</TD>          
          <TD class=TableCell width="6%">Ava Diff (Comm % - Act %)</TD>
          <TD class=TableCell width="10%">Remarks</TD>
        </TR>   
   <%  
	   for (int j=0; j <tranListData.size(); j++)
		{   
			Vector vdata = new Vector();
			vdata = (Vector)tranListData.get(j);
			int rem=1;
			rem=j%2;
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
            String customerid = "";
            String wecid = "";
      		%>
	        <TR class=<%=cls%>>
	           	<TD class=TableCell><%=j+1%></TD>
	           	<% 
	           	//wecid = "'" + vdata.get(16) + "'";
	           	customerid = "'" + vdata.get(17) + "'";
	           	%>
	           	<%if (typetxt == 0){ %>
	          		<TD class=TableCell><a href="<%=request.getContextPath()%>/ShortFallView.jsp?Transactions=<%=customerid%>&SiteTransactions=&FiscalYeartxt=<%=FiscalYeartxt%>&typetxt=1" target="_blank"><%=vdata.get(0)%></a></TD>
	          		<TD class=TableCell><%=vdata.get(1)%></TD>
	          		<TD class=TableCell><%=vdata.get(2)%></TD>
	          	<%}else{ %>
	          		<TD class=TableCell ><%=vdata.get(2)%></TD>
	          	<%}
	           	enddate = vdata.get(4).toString().substring(0,vdata.get(4).toString().length() - 5) + "-" + eYear;
	           	cdd =format.parse(enddate);
	           	String remarks = "";
	           	if (cdd.after(dd))
	           	{
	           		remarks = "Period is not yet completed <br> for the selected fiscal year";
	           	}
	           	%>
	          	<TD class=TableCell><%=vdata.get(3)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(4)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(5)%></TD> 
	     	  	<TD class=TableCell><%=vdata.get(6)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(7)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(8)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(9)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(10)%></TD> 
	     	  	<TD class=TableCell><%=vdata.get(11)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(12)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(13)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(14)%></TD>
	     	  	<TD class=TableCell><%=vdata.get(15)%></TD>
	     	  	<TD class=TableCell><%=remarks%></TD>
          	</TR>
        
        <% }}
      else
      {%>
    	  <script type="text/javascript">
    	  alert("Such Type Of Record Not Found Found In Database.");
    	  self.close(); 
    	  </script>
      
     <% }tranListData.clear();%> 
        
      




        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR>
  
     
      
</CENTER></BODY></HTML>