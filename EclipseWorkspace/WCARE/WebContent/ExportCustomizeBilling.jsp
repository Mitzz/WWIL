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
if(file==null || file.equals("")) file="ExpBillingData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

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

String site=request.getParameter("SiteTransactions");
String state="NA";
String cls = "TableRow1";
String ebid = "";

String loc = "";
String Remarks = "";
String RemarksWEC = "";
String cname = "";

if(site.equals(""))
{
	site="NA";
}
//System.out.print("site"+site);
String cust=request.getParameter("custTransactions");
//System.out.print("cust"+cust);
String fd=request.getParameter("Monthtxt");
String td=request.getParameter("Yeartxt");
DecimalFormat df = new DecimalFormat("0.##");
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();




%> 
          
       <%  
       
       
       //List tranList = new ArrayList();
       tranList = (List)secutils.getBillDataDetail(fd,td,site,state,cust);   
         
   
      //System.out.println("tranListData"+tranListData.size());
 int j=0;
	
	
	
	double t1=0,t2=0,t3=0,t4=0,t5=0,t6=0,t7=0,t8=0,t9=0;
	int twec=0,k=0;
	for (int i = 0; i < tranList.size(); i++) 
	{   j=1;
	    k=k+1;
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		String sloc=v.get(0).toString();
		
 
 



	     
	    %>
		
<%if(i==0){%>		
<TABLE  width="100%" border="1">
	<TBODY>
		<%if(i==0){ %>
		
		<tr>
			<td   class="TableCell"  colspan="16" align="center"><b>BILLING REPORT FOR THE MONTH :<%=v.get(2)%></b></td>
		</tr>
		<%} %>
	
		

	
<% } %>

				<%if(i==0){ %>
                	<TR>
					    <TD  width="2.28%"><b>Sr no.</b></TD>
						<TD  width="9.00%"><b>Meter Point</b></TD>
						
						<TD  width="11.00%"><b>Billing Period</b></TD>
						<TD  width="5.28%"><b>WEC Connected</b></TD>
						<TD  width="7.28%"><b>WEC Generation</b></TD>
						
						<TD  width="7.28%"><b>KWH Export</b></TD>
						<TD  width="7.28%"><b>KWH Import</b> </TD>
						<TD  width="5.28%"><b>Rkvah Export Lag</b> </TD>
					    <TD  width="5.28%"><b>Rkvah Export Lead</b></TD>
						<TD  width="5.28%"><b>Rkvah Import Lag</b></TD>
						<TD  width="5.28%"><b>Rkvah Import Lead</b></TD>
						<TD  width="5.28%"><b>KVAH Export</b></TD>
						<TD  width="5.28%"><b>KVAH Import</b></TD>
						<TD  width="5.28%"><b>Customer</b></TD>
						<TD  width="5.28%"><b>Site</b></TD>
						<TD  width="5.28%"><b>State</b></TD>
					</TR>
<%  }
 %>



					<%
						
					 		
							int rem = 1;
							rem = i % 2;

							if (rem == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>
					<TR >
						<TD ><%=i+1%></TD>
						<TD ><%=v.get(1)%></TD>
						
						<% if(!v.get(4).toString().equals("")){%>
						<TD ><%=v.get(4)%> To <%=v.get(3)%></TD>
						<%}else{ %>
						<TD >First Reading:<%=v.get(3)%></TD>
						
						<%}%>
						<TD ><%=v.get(14)%></TD>
						<TD ><%=v.get(13)%></TD>
						<TD ><%=v.get(5)%></TD>
						<TD ><%=v.get(6)%></TD>
						<TD ><%=v.get(7)%></TD>
						<TD ><%=v.get(8)%></TD>
						<TD ><%=v.get(9)%></TD>
						<TD ><%=v.get(10)%></TD>
						<TD ><%=v.get(11)%></TD>
						<TD ><%=v.get(12)%></TD>
						<TD ><%=v.get(15)%></TD>
						<TD ><%=v.get(0)%></TD>
						<TD ><%=v.get(16)%></TD>
					</TR>
				






<%
					//	twec=twec+Integer.parseInt(v.get(14).toString());
					//	if(!v.get(13).equals("-"))
					//	  t1=t1+Double.parseDouble(v.get(13).toString());
					//	t2=t2+Double.parseDouble(v.get(5).toString());
					//	t3=t3+Double.parseDouble(v.get(6).toString());
					//	t4=t4+Double.parseDouble(v.get(7).toString());
					//	t5=t5+Double.parseDouble(v.get(8).toString());
					//	t6=t6+Double.parseDouble(v.get(9).toString());
					//	t7=t7+Double.parseDouble(v.get(10).toString());
					//	t8=t8+Double.parseDouble(v.get(11).toString());
					//	t9=t9+Double.parseDouble(v.get(12).toString());
						
	}	tranList.clear();
	
	
%>

  
	</TBODY>
</TABLE>
      
      
</BODY>
</html>