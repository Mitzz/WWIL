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
if(file==null || file.equals("")) file="ExportReport.xls";
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
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String comma[]=rdate.split(",");
String fdate=comma[0];
String tdate=comma[1];




%>

<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,fdate,stateid,siteid); 
String cls="TableRow1";
String ebid="";
String cname="";
String state="";
String Remarks="NA";
for (int i=0; i <tranList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);
%>
 <TABLE  width="90%" border="1">
  <TBODY>
  
<tr BGCOLOR="#E0FFFF">
<td vAlign = "middle" align = "center" width = "100%" colspan="18"><%=v.get(0)%></td>
		</tr>   
		            <tr>
		          <td vAlign = "middle" width = "90%" align = "center" colspan="18"></td>
		           </tr>
		           
		           
		           <tr>
		           <td vAlign = "middle" align = "center" width = "90%" colspan="18"></td>
		           </tr>

		           <tr>
		           <td vAlign = "middle" align = "left"width = "15%" colspan="4">Location:</td>
		           <td vAlign = "middle" align = "left"width = "38%" colspan="5">Phase <%=v.get(9)%> - <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = "middle" align = "left"width = "15%" colspan="4">Machines: </td>
		           <td vAlign = "middle" align = "left"width = "24%" colspan="5"><%=v.get(8)%> X <%=v.get(6)%></td>
		           </tr>
		            <tr>
		           <td vAlign = "middle" width = "100%" align = "center" colspan="18"></td>
		           </tr>
		           <tr>
		           <td vAlign = "middle" align = "left" width = "15%" colspan="4">Capacity:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="5"><%=v.get(7)%> MW</td>
		           
		         
		           	 <td vAlign = "middle" align = "left" width = "15%" colspan="4">Date: </td>
		            <td vAlign = "middle" align = "left" width = "24%" colspan="5"><%=fdate%>-<%=tdate%></td>
		             
		        
		           </tr>

</TBODY></TABLE>


<SPAN>WEC Data </SPAN><BR>

<TABLE   border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE border="1">
        <TBODY>
        
        
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDataAdmin(ebid,rdate,"YD"); 
         
      cls="TableRow1";
      //////System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR>
          <TD width="100">WEC Name</TD>
           
          <TD width="50">Generation</TD>
          <TD width="50">Operating Hours </TD>
          <TD width="50">Lull Hours </TD>
          <TD width="50">Machine Fault</TD>
          <TD width="50">Machine S/D</TD>
          <TD width="50">Internal Fault </TD>
          <TD width="50">Internal S/D</TD>
          <TD width="50">External Fault </TD>
          <TD width="50">External S/D</TD>
          <TD width="50">Total HRS.</TD>
          <TD width="50">Capacity Factor(%) </TD>
          <TD width="50">Machine Availability(%)</TD>
          <TD width="50">Grid Internal Availability(%)</TD>
          <TD width="50">Grid External Availability(%)</TD>
          <TD width="50">WindFarm Availability(%)</TD>
          <TD width="50">WEC_SPSD</TD>
          <TD width="50">EB_SPSD</TD>
          
        </TR>  
    	  
   <%   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String name = (String)vdata.get(0);
			
				int rem=1;
				rem=j%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR>
       
          
         
          <TD><%=vdata.get(0)%></TD>
		  <TD><%=vdata.get(2)%></TD>
          <TD><%=vdata.get(3)%></TD>
          <TD><%=vdata.get(4)%></TD> 
          <TD><%=vdata.get(5)%></TD>
          <TD><%=vdata.get(6)%></TD>
          <TD><%=vdata.get(7)%></TD>
          <TD><%=vdata.get(8)%></TD>
          <TD><%=vdata.get(9)%></TD>
          <TD><%=vdata.get(10)%></TD>
          <TD><%=vdata.get(15)%></TD>
          <TD><%=vdata.get(13)%></TD>
          <TD><%=vdata.get(11)%></TD>
          <TD><%=vdata.get(16)%></TD>
          <TD><%=vdata.get(12)%></TD>
          <TD><%=vdata.get(17)%></TD>
          <TD><%=vdata.get(18)%></TD>
          <TD><%=vdata.get(19)%></TD>
          </TR>
        
        <% }%> 
        <%tranListData.clear();
        if(wecsize>1)
        {
          tranListData = (List)secutils.getEBWiseTotalAdmin(ebid,rdate,"YD");  
           for (int j=0; j <tranListData.size(); j++)
		   {
					Vector vdata = new Vector();
					vdata = (Vector)tranListData.get(j);
					//String name = (String)vdata.get(0);
					%> 
					<TR>
					
				          <TD>Total:<%=vdata.get(0)%></TD>
				          <TD><%=vdata.get(1)%></TD>
				          <TD><%=vdata.get(2)%></TD>
				          <TD><%=vdata.get(3)%></TD>
				          <TD><%=vdata.get(4)%></TD>
				          <TD><%=vdata.get(5)%></TD>
				          <TD><%=vdata.get(6)%></TD>
				          <TD><%=vdata.get(7)%></TD>
				          <TD><%=vdata.get(8)%></TD>
				          <TD><%=vdata.get(9)%></TD>
				          <TD><%=vdata.get(13)%></TD>
				          <TD><%=vdata.get(12)%></TD>
				          <TD><%=vdata.get(10)%></TD>
				          <TD><%=vdata.get(14)%></TD>
				          <TD><%=vdata.get(11)%></TD>
				          <TD><%=vdata.get(15)%></TD>
				          <TD><%=vdata.get(16)%></TD>
				          <TD><%=vdata.get(17)%></TD>
				          </TR>
					        
		  <% }%> 
        
        

      <% }}%>
      
</TBODY></TABLE></TD></TR></TBODY></TABLE>	
 <% }tranList.clear();%> 
</BODY></HTML>