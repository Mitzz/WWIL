<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>


<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File");
	if (file == null || file.equals(""))
		file = "DGRExport.xls";
	response.addHeader("Content-Disposition", "attachment; filename=\""	+ file + "\"");
%>


<%@ page import="com.enercon.customer.util.CustomerUtil" %>

<%@ page import="java.util.*" %>


<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR DashBoard Report</TITLE>

<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type="text/css" rel="stylesheet">
<% 


response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
</HEAD>
<BODY>

  
  
<%
String custid=request.getParameter("id");
String wecid=request.getParameter("wecid");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");
String compare=request.getParameter("compare");
String name=request.getParameter("name");
int cmpeval=Integer.parseInt(compare);

%>

<%List tranList = new ArrayList();
List sitetranList = new ArrayList();
List compareListData = new ArrayList();
CustomerUtil secutils = new CustomerUtil();

String cls="TableRow1";
String ebid="";
String cname="";
String state="";
String Remarks="NA";
String year=rdate.substring(6);
String dmonth=rdate.substring(0,6);
String monthno=rdate.substring(3,5);
int ayear=Integer.parseInt(year);

%>

<TABLE border="2">
  <TBODY>
  <TR>
    <TD  colspan="7" align="center">Comparison Report  </TD>
  </TR></TBODY></TABLE>
  
<TABLE border="2">
  <TBODY>
     <TR><TD colspan="7" align="center">Machine:<%=name %></TD></TR>
</TBODY></TABLE>
     <TR><TD colspan="7"></TD></TR>
     <TABLE  width="90%" border="1">
				  <TBODY>
				  <TR>
				    <TD>
				      <TABLE  width="100%" border="1">
				        <TBODY>
				        <TR>
          <TD  width="14.28%">Date</TD>
           
          <TD  width="14.28%">Generation</TD>
          <TD  width="14.28%">Operating Hours </TD>
          <TD  width="14.28%">Lull Hours </TD>
          <TD  width="14.28%">Capacity Factor(%) </TD>
          <TD  width="14.28%">Machine Availability(%)</TD>
          <TD  width="14.28%">Grid Availability(%) </TD>
          
        </TR>  
    	  
   <%          
           String cdate="";
          int cyear=ayear;
      for(int g=0;g<=cmpeval;g++)
           {  
    	    
    	     cdate=dmonth+cyear;
    	     //System.out.println("cdate"+cdate);
    	     compareListData.clear();
             compareListData = (List)secutils.getWECDataCompare(wecid,cdate,type);  
               for (int k=0; k <compareListData.size(); k++)
			    {   
				   Vector wecdata = new Vector();
				   wecdata = (Vector)compareListData.get(k);
				
				
				   int  rem=1;
				   rem=k%2;
				
                 if(rem==0)
            	    cls="TableRow2";
                 else
            	   cls="TableRow1";
        
           %>
        
        <TR >
        <% if(type.equals("M")){%>
          <TD ><%=wecdata.get(9)%>-<%=cyear%></TD>
       <%}else{%>
           <TD ><%=wecdata.get(9)%></TD>
           <%}%>
           <TD ><%=wecdata.get(2)%></TD>
          <TD ><%=wecdata.get(3)%></TD>
           
          
          <%
						if (!wecdata.get(10).toString().equals("1")) {
						%>
						<TD ><%=wecdata.get(4)%></TD>
						<TD ><%=wecdata.get(7)%></TD>
						<TD ><%=wecdata.get(5)%></TD>
						<TD ><%=wecdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD  colspan="4">WEC Is In Stabilization Phase</TD>


						<%
						}
						%>
          
          </TR>
          <% cyear=cyear-1;} compareListData.clear();}%>
        
        
</TBODY></TABLE></TD></TR></TBODY></TABLE> 
     
<P></P>
  

</BODY>
</HTML>