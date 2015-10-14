<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>
<%@ include file="FusionCharts.jsp"%>

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
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
  <p><A name=By_Day></A>
    </p>
  <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
  <TR>
    
    <TD class=SectionTitle noWrap colspan="3">Site Wise Power Generation for&nbsp;<%=request.getParameter("rd")%>  </TD>
    </TR></TBODY></TABLE>
  

<%
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String rdate=request.getParameter("rd");

List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();

    %>
<SPAN class=TableTitle> </SPAN><BR>
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
        <TR class=TableTitleRow>
          
       		<TD class=TableCell1 width="10%">Site</TD>
           
        
            <TD class=TableCell width="8%">Generation(KWH)</TD>
            <TD class=TableCell width="8%">Gen. Hours (Hrs.)</TD>
            <TD class=TableCell width="8%">Lull Hours (Hrs.)</TD>
            <TD class=TableCell width="8%">Capaciity Factor(%) </TD>
            <TD class=TableCell width="8%">MachineAvail (%)</TD>
           
            <TD class=TableCell width="8%">Grid Avail (%) </TD>
          
        </TR>
        
       <%  
       
       
    // tranList.clear(); 
     tranList = (List)secutils.getSiteTotal(custid,rdate,stateid,"D"); 
     String cls="TableRow1";
     for (int i=0; i <tranList.size(); i++)
			{
				Vector v = new Vector();
				v = (Vector)tranList.get(i);
				String name = (String)v.get(0);
				//String gen =String.parse(v.get(1));
              //String gen ="23.00";
				//String gen2 =gen.toString();

				//String ghrs = (String)v.get(2);
				//String lhrs = (String)v.get(3);
				//String mavail = (String)v.get(4);
				//String gavail = (String)v.get(5);
				//String cfactor = (String)v.get(6);
				//String stateid = (String)v.get(7);
				int rem=1;
				rem=i%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
          <TD class=TableCell1><a href=CustomerReportBoard.jsp?stateid=<%=stateid%>&siteid=<%=v.get(7)%>&id=<%=custid%>&type=D&rd=<%=rdate%>><%=v.get(0)%></a></TD>
          <TD class=TableCell><%=v.get(1)%></TD>
          <TD class=TableCell><%=v.get(2)%></TD>
          				 <%
						if (v.get(10).toString().equals("0")) {
						%>




										
						          <TD class=TableCell><%=v.get(3)%></TD>
						          <TD class=TableCell><%=v.get(6)%></TD>
						         <TD class=TableCell><%=v.get(4)%></TD>
						          <TD class=TableCell><%=v.get(5)%></TD>

						<%
						} else {
						%>

						<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>


						<%
						}
						%>
         </TR>
        
        <% }%> 
        <%tranList.clear();
        tranList = (List)secutils.getStateTotalByID(custid,rdate,stateid,"D"); 
        for (int i=0; i <0; i++)
		{
			Vector v = new Vector();
			v = (Vector)tranList.get(i);
			String name = (String)v.get(0);
			%> 
			<TR class=TableSummaryRow>
	          <TD class=TableCell>Total</TD>
	          <TD class=TableCell><%=v.get(1)%></TD>
	          <TD class=TableCell><%=v.get(2)%></TD>
	          <TD class=TableCell><%=v.get(3)%></TD>
	          <TD class=TableCell><%=v.get(6)%></TD>
	          <TD class=TableCell><%=v.get(4)%></TD>
	          <TD class=TableCell><%=v.get(5)%></TD></TR>
	        
	        <% }%> 
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR>
</CENTER></BODY></HTML>

