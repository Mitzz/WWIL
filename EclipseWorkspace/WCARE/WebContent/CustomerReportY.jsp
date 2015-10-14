<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utility.MethodClass"%>
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ include file="FusionCharts.jsp"%>
<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null){
	response.sendRedirect(request.getContextPath());
}
/* MethodClass.displayMethodClassName(); */
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
  
  
<%
String ebid=request.getParameter("ebid");
String wecid=request.getParameter("wecid");
String state=request.getParameter("state");
state=state.replace("/","-");
String cname=request.getParameter("cname");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");
String wectype=request.getParameter("wectype");
String wecname=request.getParameter("wecname");
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
String adate="01/04/3009";

java.util.Date afd = format.parse(adate);
long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);

int month=ffd.getMonth()+1;


String year=rdate.substring(6);
//String syear="";




int cyear = 2000+ffd.getYear()-100;
int nyear =cyear;
//System.out.println("Month: " + month);
//System.out.println("Year: " + cyear);
if(month>=4){
	nyear=cyear+1;
}
else{
	nyear=cyear;
	cyear=cyear-1;
}
String pdate=Integer.toString(cyear);
String ndate=Integer.toString(nyear);

String cls="TableRow1";

%>

<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
   <TR align="left">
   
     <TD align="left">
       <input type="button" value="Print" onClick="window.print()" class="printbutton">
     <input  align="right" type="button" value="Excel" class="printbutton" onClick="location.href='ExportCustomerY.jsp?wectype=<%=wectype%>&wecname=<%=wecname%>&ebid=<%=ebid%>&wecid=<%=wecid%>&rd=<%=rdate%>&state=<%=state%>&cname=<%=cname%>&type=Y'"; />
     </TD>
       </TR> 
  <TR>
    
    <TD class=SectionTitle colspan="3" noWrap>Yearly Generation Report  </TD>
    </TR></TBODY></TABLE>
  
<P><BR>
 <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = 100% colspan="6"><%=cname%></td>
		            
		            </tr>
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           
		       
		             
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=state%> </td>
		           
		            <td vAlign = middle  class=TableCell align = left width = 18%>Year: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=pdate%>-<%=ndate%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=wectype%> </td>
		           
		            <td vAlign = middle  class=TableCell align = left width = 18%>WEC: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=wecname%></td>
		           
		             <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>
		          
		          

</TBODY></TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>
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
        
          
       <%  
List tranListData = new ArrayList();
tranListData = (List)secutils.getWECMonthwise(ebid,wecid,rdate,type);  
         
cls="TableRow1";
//System.out.println("tranListData"+tranListData.size());
int  wecsize=tranListData.size();
if(wecsize>0){ 
	%>
    	  
    	<TR class=TableTitleRow>
           <TD class=TableCell width="14.28%">Month</TD>
           
          <TD class=TableCell width="14.28%">Generation</TD> 
          <TD class=TableCell width="14.28%">Operating Hours </TD>
          <TD class=TableCell width="14.28%">Lull Hours </TD>
          <TD class=TableCell width="14.28%">Capacity Factor(%) </TD>
           <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
          <% 
	if(diff<0){ 
          %>
		 <TD class=TableCell width="14.28%">Grid Availability(%)</TD>
		 <%
	} 
	else{ %>
		 <TD class=TableCell width="14.28%">Grid Internal Availability(%)</TD>
		 <TD class=TableCell width="14.28%">Grid External Availability(%)</TD>
		 <%
	}
           %>
        </TR>  
   <%
	for (int j=0; j <tranListData.size(); j++){   
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
        
        <TR class=<%=cls%>>
        
          <TD class=TableCell><%=vdata.get(0)%></TD>
          <TD class=TableCell><%=vdata.get(1)%></TD>
          <TD class=TableCell><%=vdata.get(2)%></TD>
          <TD class=TableCell><%=vdata.get(3)%></TD>
          <TD class=TableCell><%=vdata.get(6)%></TD>
          <% 
		if(diff<0){ 
          %>
		  <TD class=TableCell><%=vdata.get(4)%></TD>
						
			<%
		}
        else {
          %>
            <TD class=TableCell><%=vdata.get(7)%></TD>
	        <TD class=TableCell><%=vdata.get(8)%></TD>
						
						<%
		}
						%>
          <TD class=TableCell><%=vdata.get(5)%></TD>
          </TR>
        
        <% 
	}
        %> 
        <% 	
	tranListData.clear();
    tranListData = (List)secutils.getWECDetailTotal(ebid,wecid,rdate,"Y");   
    for (int j=0; j <tranListData.size(); j++){
		Vector vdata = new Vector();
		vdata = (Vector)tranListData.get(j);
			//String name = (String)vdata.get(0);
			%> 
			
			<TR class=TableSummaryRow>
			
	        <TD class=TableCell>Total:</TD>
	        
          <TD class=TableCell><%=vdata.get(0)%></TD>
          <TD class=TableCell><%=vdata.get(1)%></TD>
          <TD class=TableCell><%=vdata.get(2)%></TD>
          <TD class=TableCell><%=vdata.get(5)%></TD>
           <% 
		if(diff<0){ 
           %>
		 <TD class=TableCell><%=vdata.get(3)%></TD>
						
			<%
		}else {
			%>
         <TD class=TableCell><%=vdata.get(6)%></TD>
		 <TD class=TableCell><%=vdata.get(7)%></TD>
						
			  <%
		}
			  %>
          <TD class=TableCell><%=vdata.get(4)%></TD>
          </TR>
	        
	        <%
	}
}
        %> 
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>
<P><BR>
  
      
      
</CENTER></BODY></HTML>