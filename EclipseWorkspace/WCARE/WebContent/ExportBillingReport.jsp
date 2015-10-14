<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "BillingData.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""
			+ file + "\"");
%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<HTML>
<HEAD>
<%  
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>

</HEAD>
<BODY>
<CENTER>
<%
	String custid = request.getParameter("id");
	String month = request.getParameter("month");
	String year = request.getParameter("year");
	String site = request.getParameter("site");
	String state = request.getParameter("state");
	String cname = request.getParameter("cname").replace("SPACE"," ");	
	
%>

<%
	List tranList = new ArrayList();
	DecimalFormat df = new DecimalFormat("0.##");
	CustomerUtil custutils = new CustomerUtil();
	tranList = (List) custutils.getBillingDetail(custid, month, year,site,state);
	String cls = "TableRow1";
	String ebid = "";
	
	String loc = "";
	String Remarks = "";
	String RemarksWEC = "";
	int j=0;
	int g=1;
	if(g==0)
	{%>
	
	                 
		<TABLE> 
		<TBODY>	
		<TR>
		<TD>
		<TABLE>
		<TBODY>
	<% }
	
	double t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0;
	int twec=0,k=0;
	for (int i = 0; i < tranList.size(); i++) 
	{   j=1;
	    k=k+1;
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		String sloc=v.get(0).toString();
		
 
 


if(!loc.equals(sloc))
		{
	     
	     if(i !=0){ 
	    	 if(k>1) {
	    %>
			
		
		   <TR>
						<TD colspan="3">Total</TD>
						
						<TD ><%=twec%></TD>
						
						<TD><%=df.format(t1)%></TD>
						<TD><%=df.format(t2)%></TD>
						<TD><%=df.format(t3)%></TD>
						<TD><%=df.format(t4)%></TD>
						<TD><%=df.format(t5)%></TD>
						<TD><%=df.format(t6)%></TD>
						<TD><%=df.format(t7)%></TD>
						<TD><%=df.format(t8)%></TD>
						<TD><%=df.format(t9)%></TD>
					
					</TR>
					<%}} %>
		  </TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
		
<TABLE cellSpacing=0 cellPadding=0 width="100%" border="1">
	<TBODY>
		<%if(i==0){ %>
		<tr BGCOLOR="#808000">
			<td vAlign=middle width=100%  align=center colspan="13"><%=cname%> : SITE - <%=v.get(0)%></td>
		</tr>
		<tr >
			<td vAlign=middle width=100%  align=center colspan="13">BILLING REPORT FOR THE MONTH :<%=v.get(2)%></td>
		</tr>
		<%}else{ %>
		<tr >
			<td vAlign=middle  align=center  width=100% colspan="13">&nbsp;</td>
		</tr>
		<tr >
			<td vAlign=middle width=100%  align=center colspan="13"><u>SITE- <%=v.get(0)%></u></td>
		</tr>
		<%} %>
		<tr >
			<td vAlign=middle  align=center  width=100% colspan="13">&nbsp;</td>
		</tr>

		

	</TBODY>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%"  border="1">
	<TBODY>
		<TR>
			<TD>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border="1">
				<TBODY>
                	<TR >
					    <TD width="2.28%">Sr no.</TD>
						<TD width="12.28%">Meter Point</TD>
						
						<TD width="16.28%">Billing Period</TD>
						<TD width="5.28%">WEC Connected</TD>
						<TD width="8.28%">WEC Generation</TD>
						
						<TD width="7.28%">KWH Export</TD>
						<TD width="7.28%">KWH Import </TD>
						<TD width="4.28%">Rkvah Export Lag </TD>
					    <TD width="4.28%">Rkvah Export Lead</TD>
						<TD width="4.28%">Rkvah Import Lag</TD>
						<TD width="4.28%">Rkvah Import Lead</TD>
						<TD width="4.28%">KVAH Export</TD>
						<TD width="4.28%">KVAH Import</TD>
					</TR>
<%  
loc=sloc;
t1=0;t2=0;t3=0;t4=0;t6=0;t7=0;t8=0;t9=0;
twec=0;k=0;
} %>



				
					<TR>
						<TD><%=i+1%></TD>
						<TD><%=v.get(1)%></TD>
						
						<% if(!v.get(4).toString().equals("")){%>
						<TD><%=v.get(4)%> To <%=v.get(3)%></TD>
						<%}else{ %>
						<TD>First Reading:<%=v.get(3)%></TD>
						
						<%}%>
						<TD><%=v.get(14)%></TD>
						<TD><%=v.get(13)%></TD>
						<TD><%=v.get(5)%></TD>
						<TD><%=v.get(6)%></TD>
						<TD><%=v.get(7)%></TD>
						<TD><%=v.get(8)%></TD>
						<TD><%=v.get(9)%></TD>
						<TD><%=v.get(10)%></TD>
						<TD><%=v.get(11)%></TD>
						<TD><%=v.get(12)%></TD>
					
					</TR>

<%
						twec=twec+Integer.parseInt(v.get(14).toString());
						if(!v.get(13).equals("-"))
						  	t1=t1+Double.parseDouble(v.get(13).toString());
							t2=t2+Double.parseDouble(v.get(5).toString());
							t3=t3+Double.parseDouble(v.get(6).toString());
							t4=t4+Double.parseDouble(v.get(7).toString());
							t5=t5+Double.parseDouble(v.get(8).toString());
							t6=t6+Double.parseDouble(v.get(9).toString());
							t7=t7+Double.parseDouble(v.get(10).toString());
							t8=t8+Double.parseDouble(v.get(11).toString());
							t9=t9+Double.parseDouble(v.get(12).toString());
						
	}	tranList.clear();
	
	
   if(k>1) {%>

   <TR>
	<TD colspan="3">Total</TD>
	
	<TD ><%=twec%></TD>
	
	<TD><%=df.format(t1)%></TD>
	<TD><%=df.format(t2)%></TD>
	<TD><%=df.format(t3)%></TD>
	<TD><%=df.format(t4)%></TD>
	<TD><%=df.format(t5)%></TD>
	<TD><%=df.format(t6)%></TD>
	<TD><%=df.format(t7)%></TD>
	<TD><%=df.format(t8)%></TD>
	<TD><%=df.format(t9)%></TD>

</TR>
<%} %>

  <TR>
	<TD colspan="13">&nbsp;</TD>
   </TR>
<TR>
	<TD colspan="13">&nbsp;</TD>
    </TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>


</CENTER>
</BODY>
</HTML>
