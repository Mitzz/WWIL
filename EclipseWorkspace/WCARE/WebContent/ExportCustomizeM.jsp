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
<CENTER>
  
  
<%
String ebid=request.getParameter("ebid");
String wecid=request.getParameter("wecid");
String state=request.getParameter("state");
state=state.replace("/","-");
String cname=request.getParameter("cname");
String rdate=request.getParameter("rd");

String comma[]=rdate.split(",");
String fdate=comma[0];
String tdate=comma[1],cls="";

String wectype=request.getParameter("wectype");
String wecname=request.getParameter("wecname");
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();



%>


 <TABLE width="100%" border="1">
  <TBODY>
  
<tr >
<td vAlign = "middle" align = "center" width = "100%" colspan="18"><%=cname%></td>
		            
		           </tr>
		            <tr >
		          <td vAlign = "middle" width = "100%" align = "center" colspan="18"></td>
		           </tr>
		              
		           <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="18"></td>
		           </tr>

		           <tr >
		           <td vAlign = "middle" align = "left" width = "20%" colspan="4">Location:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="5"><%=state%> </td>
		           
		            <td vAlign = "middle"  align = "left" width = "18%" colspan="4">Date: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="5"><%=fdate%>-<%=tdate%></td>
		           </tr>
		             <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="18"></td>
		           </tr>
		           <tr >
		           <td vAlign = "middle" align = "left" width = "20%" colspan="4">Capacity:</td>
		           <td vAlign = "middle" align = "left" width = "38%" colspan="5"><%=wectype%> </td>
		           
		            <td vAlign = "middle"  align = "left" width = "18%" colspan="4">WEC: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="5"><%=wecname%></td>
		           </tr>
		             <tr >
		           <td vAlign = "middle" align = "center" width = "100%" colspan="18"></td>
		     </tr>
		          
		          

</TBODY></TABLE>

<P></P>
<SPAN >WEC Data </SPAN><BR>

<TABLE border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE border="1">
        <TBODY>
        
          
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDetailAdmin(ebid,wecid,rdate,"YD");    
         
      cls="TableRow1";
      ////System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR>
          <TD width="100">WEC Name</TD>
           <TD width="100">Date</TD>
          <TD width="100">Generation</TD>
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
          <TD width="50">Grid Internal Availability(%) </TD>
          <TD width="50">Grid  External Availability(%) </TD>
          <TD width="50">Windfarm Availability(%) </TD>
           <TD width="50">WEC_SPSD </TD>
          <TD width="50">EB_SPSD  </TD>
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
        
        <TR>
        
          <TD><%=wecname%></TD>
           <TD><%=vdata.get(2)%></TD>
          <TD><%=vdata.get(3)%></TD>
          <TD><%=vdata.get(4)%></TD> 
          <TD><%=vdata.get(5)%></TD>
          <TD><%=vdata.get(6)%></TD>
          <TD><%=vdata.get(7)%></TD>
            <TD><%=vdata.get(8)%></TD>
          <TD><%=vdata.get(9)%></TD>
          <TD><%=vdata.get(10)%></TD>
          <TD><%=vdata.get(11)%></TD>
          <TD><%=vdata.get(15)%></TD>
          <TD><%=vdata.get(14)%></TD>
          <TD><%=vdata.get(12)%></TD>
          
          
          <TD><%=vdata.get(16)%></TD>
          <TD><%=vdata.get(13)%></TD>
          <TD><%=vdata.get(17)%></TD>
          <TD><%=vdata.get(18)%></TD>
          <TD><%=vdata.get(19)%></TD>
          </TR>
        
        <% }%> 
        
      




        <% 	
        tranListData.clear();
        tranListData = (List)secutils.getWECDetailTotalAdmin(ebid,wecid,rdate,"YD");   
        for (int j=0; j <tranListData.size(); j++)
		{
			Vector vdata = new Vector();
			vdata = (Vector)tranListData.get(j);
			//String name = (String)vdata.get(0);
			%> 
			
			<TR >
			
	        <TD colspan="2">Total:</TD>
	        
          <TD><%=vdata.get(0)%></TD>
            <TD><%=vdata.get(1)%></TD>
          <TD><%=vdata.get(2)%></TD>
          <TD><%=vdata.get(3)%></TD>
          <TD><%=vdata.get(4)%></TD>
          <TD><%=vdata.get(5)%></TD>
          <TD><%=vdata.get(6)%></TD>
          <TD><%=vdata.get(7)%></TD>
          <TD><%=vdata.get(8)%></TD>
          <TD><%=vdata.get(12)%></TD>
          <TD><%=vdata.get(11)%></TD>
          <TD><%=vdata.get(9)%></TD>
           
          
          <TD><%=vdata.get(13)%></TD>
          <TD><%=vdata.get(10)%></TD>
          <TD><%=vdata.get(14)%></TD>
          <TD><%=vdata.get(15)%></TD>
          <TD><%=vdata.get(16)%></TD>
          </TR>
	        <% }}%> 
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>

  
      
      
</CENTER></BODY></HTML>