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
if(file==null || file.equals("")) file="WECMasterData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.admin.util.AdminUtil" %>

<%@ page import="java.util.*" %>

<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<META http-equiv="Content-Type" content="text/html; charset=windows-1252">

<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
%>

<BODY >
<CENTER>
  
<%

String state=request.getParameter("state");
String site=request.getParameter("site");
String substation=request.getParameter("substation");
String feeder=request.getParameter("feeder");
String cust=request.getParameter("cust");
String eb=request.getParameter("eb");
String wec=request.getParameter("wec");
String wectype=request.getParameter("wectype");
String comm=request.getParameter("comm");
String comm1=request.getParameter("comm1");
String wecStatus=request.getParameter("wecStatus");
String area=request.getParameter("area");
AdminUtil adminutil = new AdminUtil();
String cls="TableRow1";

%> 

<TABLE >
  <TBODY>
  <TR>
    <TD>
      <TABLE   border="1">
        <TBODY>
       <%  
       List tranListData = new ArrayList(); 
       tranListData = (List)adminutil.getWECMasterData(state,area,substation,feeder,site,cust,eb,wec,wectype,comm,userid,comm1,wecStatus);   
	   cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
	      <%if((wecStatus=="3")||(wecStatus.equals("3"))) {%>    	  
		    	<TR>
		    	  <TD width="5.28%"><b>Sr. No</b></TD>
		    	  <TD width="10%"><b>STATE</b></TD>
		    	  <TD width="10%"><b>AREA</b></TD>
		          <TD width="10%"><b>SUBSTATION</b></TD>
		          <TD width="10%"><b>FEEDER</b></TD>
		    	  <TD width="10%"><b>SITE</b></TD>
		          <TD width="15%"><b>WEC</b></TD>
		          <TD width="15%"><b>EB</b></TD>
		          <TD width="20%"><b>CUSTOMER</b></TD>
		          <TD  width="20%">CUSTOMER CODE</TD>
		          <TD width="10%"><b>FOUND. LOC.</b></TD>
		          <TD width="10%"><b>COMM. DATE</b></TD>
		          <TD width="10%"><b>STATUS</b></TD>
		          <TD width="10%"><b>WEC TYPE</b></TD>          
		          <TD width="10%"><b>CAPACITY</b></TD>
		          <TD width="10%"><b>TRANSFER DATE</b></TD>          
		          <TD width="10%"><b>WEC NAME</b></TD>
		        </TR> 
	        <% } else { %> 
		        <TR>
		    	  <TD width="5.28%"><b>Sr. No</b></TD>
		    	  <TD width="10%"><b>STATE</b></TD>
		    	  <TD width="10%"><b>AREA</b></TD>
		          <TD width="10%"><b>SUBSTATION</b></TD>
		          <TD width="10%"><b>FEEDER</b></TD>
		    	  <TD width="10%"><b>SITE</b></TD>
		          <TD width="15%"><b>WEC</b></TD>
		          <TD width="15%"><b>EB</b></TD>
		          <TD width="20%"><b>CUSTOMER</b></TD>
		          <TD  width="20%">CUSTOMER CODE</TD>
		          <TD width="10%"><b>FOUND. LOC.</b></TD>
		          <TD width="10%"><b>COMM. DATE</b></TD>
		          <TD width="10%"><b>STATUS</b></TD>
		          <TD width="10%"><b>WEC TYPE</b></TD>          
		          <TD width="10%"><b>CAPACITY</b></TD>
		        </TR> 
	        <%} %> 
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
      		%>
      		<%if((wecStatus=="3")||(wecStatus.equals("3"))) {%>   
		        <TR >
		           	<TD><%=j+1%></TD>
		          	<TD><%=vdata.get(0)%></TD>
		          	<TD><%=vdata.get(13)%></TD>
		          	<TD><%=vdata.get(14)%></TD>
		          	<TD><%=vdata.get(15)%></TD>
		          	<TD><%=vdata.get(1)%></TD>
		          	<TD><%=vdata.get(2)%></TD>
		          	<TD><%=vdata.get(3)%></TD>
		     	  	<TD><%=vdata.get(4)%></TD>
		     	  	<TD><%=vdata.get(10)%></TD>
		     	  	<TD><%=vdata.get(5)%></TD> 
		     	  	<TD><%=vdata.get(6)%></TD>
		     	  	<TD><%=vdata.get(7)%></TD>
		     	  	<TD><%=vdata.get(8)%></TD>
		     	  	<TD><%=vdata.get(9)%></TD>
		     	  	<TD><%=vdata.get(11)%></TD>
		     	  	<TD><%=vdata.get(12)%></TD>
	          	</TR>
          	<% } else { %>
	          	<TR >
		           	<TD><%=j+1%></TD>
		          	<TD><%=vdata.get(0)%></TD>
		          	<TD><%=vdata.get(11)%></TD>
		          	<TD><%=vdata.get(12)%></TD>
		          	<TD><%=vdata.get(13)%></TD>
		          	<TD><%=vdata.get(1)%></TD>
		          	<TD><%=vdata.get(2)%></TD>
		          	<TD><%=vdata.get(3)%></TD>
		     	  	<TD><%=vdata.get(4)%></TD>
		     	  	<TD><%=vdata.get(10)%></TD>
		     	  	<TD><%=vdata.get(5)%></TD> 
		     	  	<TD><%=vdata.get(6)%></TD>
		     	  	<TD><%=vdata.get(7)%></TD>
		     	  	<TD><%=vdata.get(8)%></TD>
		     	  	<TD><%=vdata.get(9)%></TD>
	          	</TR>
          	<%} %>
        
        <% }}
      
      %>
    	  
      
     <% tranListData.clear();%> 
        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>

  </center>
  </body>
     
      