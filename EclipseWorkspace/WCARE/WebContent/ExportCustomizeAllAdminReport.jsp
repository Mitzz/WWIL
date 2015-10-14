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
if(file==null || file.equals("")) file="Export.xls";
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
String custid=request.getParameter("custid");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String comma[]=rdate.split(",");
String fdate=comma[0];
String tdate=comma[1];

List tranList = new ArrayList();
//List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();



%>



<%




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






  
<%  
        
          List tranListData = new ArrayList();
          tranListData = (List)secutils.getFindWECByEB(ebid); 
         
      cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0)
      { 
    	    for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String	wecname = (String)vdata.get(0);
				String	wecid=(String)vdata.get(2);
				String	wectype=(String)vdata.get(3);
		
      
      
      %>
 <TABLE cellSpacing="0" cellPadding="0" width="90%" border="1">
  <TBODY>
  
<tr >
<td vAlign = "middle"  align = "center" width = "100%" colspan="16"><%=cname%></td>
		            
		            </tr>
		            <tr >
		          <td vAlign = "middle"  width = "100%" align = "center" colspan="6"></td>
		           </tr>
		           
		       
		             
		           <tr >
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="16"></td>
		           </tr>

		           <tr >
		           <td vAlign = "middle"  align = "left" width = "20%">Location:</td>
		           <td vAlign = "middle"  align = "left" width = "38%" colspan="8"><%=state%> </td>
		           
		            <td vAlign = "middle"   align = "left" width = "18%">Date: </td>
		           <td vAlign = "middle" align = "left" width = "24%" colspan="8"><%=fdate%>-<%=tdate%></td>
		                </tr >
		             <tr >
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="16"></td>
		           </tr>
		           <tr >
		           <td vAlign = "middle"  align = "left" width = "20%">Capacity:</td>
		           <td vAlign = "middle"  align = "left" width = "38%" colspan="8"><%=wectype%> </td>
		           
		            <td vAlign = "middle"   align = "left" width = "18%">WEC: </td>
		           <td vAlign = "middle"  align = "left" width = "24%" colspan="8"><%=wecname%></td>
		            </tr >
		             <tr >
		           <td vAlign = "middle"  align = "center" width = "100%" colspan="16"></td>
		           </tr>
		          
		          

</TBODY></TABLE>
<SPAN >WEC Data </SPAN><BR>

<TABLE  border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE  width="100%" border="1">
        <TBODY>
        
          
       <%  
       
       
       List tranListData1 = new ArrayList();
          tranListData1 = (List)secutils.getWECDetailAdmin(ebid,wecid,rdate,"YD");    
         
      cls="TableRow1";
      ////System.out.println("tranListData"+tranListData.size());
      int  wecsize1=tranListData1.size();
      if(wecsize1>0){ %>
    	  
    	<TR>
          <TD  width="100">WEC Name</TD>
           <TD  width="100">Date</TD>
          <TD  width="100">Generation</TD>
          <TD  width="50">Operating Hours </TD>
          <TD  width="50">Lull Hours </TD>
          
          <TD  width="50">Machine Fault</TD>
          <TD  width="50">Machine S/D</TD>
          <TD  width="50">Internal Fault </TD>
          <TD  width="50">Internal S/D</TD>
         <TD  width="50">External Fault </TD>
          <TD  width="50">External S/D</TD>
           <TD  width="50">Total HRS.</TD>
          <TD  width="50">Capacity Factor(%) </TD>
          <TD  width="50">Machine Availability(%)</TD>
          <TD  width="50">Grid Internal Availability(%) </TD>
          <TD  width="50">Grid  External Availability(%) </TD>
          <TD  width="50">Windfarm Availability(%) </TD>
           <TD  width="50">WEC_SPSD </TD>
          <TD  width="50">EB_SPSD  </TD>
        </TR>  
   <%   for (int l=0; l <tranListData1.size(); l++)
			{   
				Vector wecdata = new Vector();
				wecdata = (Vector)tranListData1.get(l);
				String name = (String)wecdata.get(0);
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
				rem=l%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR>
        
          <TD ><%=wecname%></TD>
           <TD ><%=wecdata.get(2)%></TD>
          <TD ><%=wecdata.get(3)%></TD>
          <TD ><%=wecdata.get(4)%></TD> 
          <TD ><%=wecdata.get(5)%></TD>
          <TD ><%=wecdata.get(6)%></TD>
          <TD ><%=wecdata.get(7)%></TD>
            <TD ><%=wecdata.get(8)%></TD>
          <TD ><%=wecdata.get(9)%></TD>
          <TD ><%=wecdata.get(10)%></TD>
          <TD ><%=wecdata.get(11)%></TD>
          <TD ><%=wecdata.get(15)%></TD>
          <TD ><%=wecdata.get(14)%></TD>
          <TD ><%=wecdata.get(12)%></TD>
          
          
          <TD ><%=wecdata.get(16)%></TD>
          <TD ><%=wecdata.get(13)%></TD>
          <TD ><%=wecdata.get(17)%></TD>
          <TD ><%=wecdata.get(18)%></TD>
          <TD ><%=wecdata.get(19)%></TD>
          </TR>
        
        <% }%> 
        
      




        <% 	
        tranListData1.clear();
        tranListData1 = (List)secutils.getWECDetailTotalAdmin(ebid,wecid,rdate,"YD");   
        for (int k=0; k <tranListData1.size(); k++)
		{
			Vector wecdata = new Vector();
			wecdata = (Vector)tranListData1.get(k);
			//String name = (String)wecdata.get(0);
			%> 
			
			<TR >
			
	        <TD  colspan="2">Total:</TD>
	        
          <TD ><%=wecdata.get(0)%></TD>
            <TD ><%=wecdata.get(1)%></TD>
          <TD ><%=wecdata.get(2)%></TD>
          <TD ><%=wecdata.get(3)%></TD>
          <TD ><%=wecdata.get(4)%></TD>
          <TD ><%=wecdata.get(5)%></TD>
          <TD ><%=wecdata.get(6)%></TD>
          <TD ><%=wecdata.get(7)%></TD>
          <TD ><%=wecdata.get(8)%></TD>
          <TD ><%=wecdata.get(12)%></TD>
          <TD ><%=wecdata.get(11)%></TD>
          <TD ><%=wecdata.get(9)%></TD>
           
          
          <TD ><%=wecdata.get(13)%></TD>
          <TD ><%=wecdata.get(10)%></TD>
          <TD ><%=wecdata.get(14)%></TD>
          <TD ><%=wecdata.get(15)%></TD>
          <TD ><%=wecdata.get(16)%></TD>
          </TR>
	        <% }}tranListData1.clear();%> 
        
     </TBODY></TABLE></TD></TR></TBODY></TABLE>	   

      <% }tranListData.clear();%>
      

 <% }}tranList.clear();%> 

  
      
      
</CENTER></BODY></HTML>