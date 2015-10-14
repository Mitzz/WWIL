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
if(file==null || file.equals("")) file="ExportData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>


<%@ page import="java.util.*" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="com.enercon.customer.util.*" %>


<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>Report</TITLE>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
%>


</HEAD>
<BODY>
  
<%

String weclist=request.getParameter("SiteTransactions");

String fdate=request.getParameter("FromDatetxt");
String tdate=request.getParameter("ToDatetxt");

String ptype=request.getParameter("PeriodTypetxt");
String mtype=request.getParameter("MeterTypetxt");

CustomerUtil custutils = new CustomerUtil();

%> 

<TABLE width="90%"  border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE   width="100%" border="1">
        <TBODY>
          
       <%  
       
       		List tranListData = new ArrayList();
            tranListData = (List)custutils.getWecSelectedListDetail(weclist,fdate,tdate,ptype);             
      
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR BGCOLOR="#FFF8C6">
    	  	<TD  width="5.28%" COLSPAN="17" align="center"><font size="5"><b>WEC Wise Report</b></font></TD>    	             
        </TR>  
    	 
    	<TR>
    	    <TD ALIGN="CENTER" width="5.28%"><b>Sr. No</b></TD>
    	    <TD ALIGN="CENTER" width="14.28%"><b>DATE</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>WEC</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>WEC Type</b></TD>
            <%if(ptype.equals("D")){%>
            <TD ALIGN="CENTER" width="14.28%"><b>Cum GENERATION</b></TD>
            <%}%>  
            <TD ALIGN="CENTER" width="14.28%"><b>GENERATION</b></TD>   
            <%if(ptype.equals("D")){%>          
            <TD ALIGN="CENTER" width="14.28%"><b>Cum Opr .Hrs</b></TD>  
            <%}%>
            <TD ALIGN="CENTER" width="14.28%"><b>Opr Hrs</b></TD>              
            <TD ALIGN="CENTER" width="14.28%"><b>L.Hrs</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>MA</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>CF</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>GIA</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>GA</b></TD>
            <TD ALIGN="CENTER" width="14.28%"><b>Customer</b></TD>
         	<TD ALIGN="CENTER" width="14.28%"><b>SITE</b></TD>
          	<TD ALIGN="CENTER" width="14.28%"><b>STATE</b></TD>
          	<TD ALIGN="CENTER" width="28.56%"><b>REMARKS</b></TD>
        </TR>  
    	  
   <%  
   String ebid="NA",ebRemarks="";
   // AdminDao adminDao = new AdminDao();
   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				//String name = (String)vdata.get(0);
				
				String aebid=(String)vdata.get(11);
				ebRemarks="";
				
				%>
        
          <TR>        
	          <TD ALIGN="CENTER"><%=j+1%></TD>
	          <TD><%=vdata.get(5)%></TD>
	          <TD><%=vdata.get(0)%></TD>
	          <TD><%=vdata.get(1)%></TD>
	          <%if(ptype.equals("D")){%>
	          	<TD><%=vdata.get(15)%></TD>
	          <%}%> 
	     	  <TD><%=vdata.get(6)%></TD>
	     	  <%if(ptype.equals("D")){%>
	     	  	<TD><%=vdata.get(16)%></TD>
	          <%}%>
	     	  <TD><%=vdata.get(12)%></TD>
	     	  <TD><%=vdata.get(14)%></TD>
	     	  <TD><%=vdata.get(7)%></TD>
			  <TD><%=vdata.get(8)%></TD> 
			  <TD><%=vdata.get(13)%></TD> 
			  <TD><%=vdata.get(9)%></TD> 
			  
	          <TD><%=vdata.get(2)%></TD>
	          <TD><%=vdata.get(3)%></TD>
	          <TD><%=vdata.get(4)%></TD>
	          <TD><%=vdata.get(10)%></TD>
          </TR>
        
        <%  ebid=aebid;}}
     	   tranListData.clear();%> 

 <%  
       
 			tranListData.clear();
 
            if(mtype.equals("WE"))
            {
           	 tranListData = (List)custutils.getEBSelectDetail(weclist,fdate,tdate,ptype);    
         
      
      				//System.out.println("tranListData"+tranListData.size());
     			wecsize=tranListData.size();
     			 if(wecsize>0){ %>
    	  
   			  	<TR BGCOLOR="#FFF8C6">
   			  		<TD  width="5.28%" COLSPAN="17" align="center"><font size="5"><b>EB DATA</b></font></TD>
     			</TR>
    	 
    			<TR>
    			 <TD ALIGN="CENTER" width="5.28%"><b>Sr. No</b></TD>
    	 		 <TD ALIGN="CENTER" width="14.28%"><b>DATE</b></TD>
          		 <TD ALIGN="CENTER" width="14.28%"><b>EB</b></TD>
        
         		 <TD ALIGN="CENTER" width="14.28%"><b>KWH IMPORT</b></TD>
          		 <TD ALIGN="CENTER" width="14.28%"><b>KWH EXPORT</b></TD>
           		 <TD ALIGN="CENTER" width="14.28%" COLSPAN="12"><b>REMARKS</b></TD>
       		 	</TR>  
    	  
   <%  
   // String ebid="NA";
   // String ebRemarks="";
       // AdminDao adminDao = new AdminDao();
   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				//String name = (String)vdata.get(0);				
				
				// ebRemarks="";
				
				%>
        
        <TR>        
          <TD ALIGN="CENTER"><%=j+1%></TD>
          <TD><%=vdata.get(2)%></TD>
          <TD><%=vdata.get(0)%></TD>
          <TD><%=vdata.get(4)%></TD>         
     	  <TD><%=vdata.get(3)%></TD>
     	  <TD COLSPAN="12"><%=vdata.get(5)%></TD>     	  
        </TR>
        
        <%  }}
     	   tranListData.clear();}%> 



        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>

  
     
      
</BODY></HTML>