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
<%@ page import="java.text.*" %>
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
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>


</HEAD>
<BODY>

  
  
<%

String weclist=request.getParameter("SiteTransactions");


String fdate=request.getParameter("FromDatetxt");
String tdate=request.getParameter("ToDatetxt");

List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil custutils = new CustomerUtil();

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");



%> 


 

<TABLE width="90%"  border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE   width="100%" border="1">
        <TBODY>
        
          
       <%  
       
       
       		List tranListData = new ArrayList();
            tranListData = (List)custutils.getWecSelectedListDetail(weclist,fdate,tdate,"D");   
         
      
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	  <TR>
    	  <TD  width="5.28%" COLSPAN="17" align="center"><font size="5"><b>WEC Wise Report</b></font></TD>
    	  
           
        </TR>  
    	  
    	  
    	 
    	<TR>
    	  <TD  width="5.28%"><b>Sr. No</b></TD>
    	  <TD  width="14.28%"><b>DATE</b></TD>
           <TD  width="14.28%"><b>WEC</b></TD>
           <TD  width="14.28%"><b>WEC Type</b></TD>
        
          
        
          <TD  width="14.28%"><b>GENERATION</b></TD>
           <TD  width="14.28%"><b>O.Hrs</b></TD>
            <TD  width="14.28%"><b>L.Hrs</b></TD>
          <TD  width="14.28%"><b>MA </b></TD>
          <TD  width="14.28%"><b>CF</b></TD>
          <TD  width="14.28%"><b>GIA</b></TD>
           <TD  width="14.28%"><b>GA</b></TD>
            <TD  width="28.56%" colspan="2"><b>REMARKS</b></TD>
            
            <TD  width="14.28%"><b>Customer</b></TD>
         	<TD  width="14.28%"><b>SITE</b></TD>
          	<TD  width="14.28%"><b>STATE </b></TD>
        </TR>  
    	  
   <%  
   String ebid="NA",ebRemarks="";
       AdminDao adminDao = new AdminDao();
   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				//String name = (String)vdata.get(0);
				String readdate =adminDao.convertDateFormat(vdata.get(5).toString(),"dd-MMM-yyyy","dd/MM/yyyy");
				String aebid=(String)vdata.get(11);
				ebRemarks="";
				if(!ebid.equals(aebid))
				{
					ebRemarks=custutils.getEBRemarks(aebid,readdate); 
					if(ebRemarks.equals("NA"))
						ebRemarks="";
				}
				
				%>
        
        <TR >
        
           <TD><%=j+1%></TD>
           <TD><%=vdata.get(5)%></TD>
          <TD><%=vdata.get(0)%></TD>
          <TD><%=vdata.get(1)%></TD>
         
         
     	  <TD><%=vdata.get(6)%></TD>
     	   <TD><%=vdata.get(12)%></TD>
     	   <TD><%=vdata.get(14)%></TD>
     	   <TD><%=vdata.get(7)%></TD>
		   <TD><%=vdata.get(8)%></TD> 
		  <TD><%=vdata.get(13)%></TD> 
		   <TD><%=vdata.get(9)%></TD> 
		   <TD colspan="2"><%=vdata.get(10).toString().replace("NA","")+" "+ebRemarks%></TD> 
           <TD><%=vdata.get(2)%></TD>
          <TD><%=vdata.get(3)%></TD>
          <TD><%=vdata.get(4)%></TD>
          </TR>
        
        <%  ebid=aebid;}}
     	   tranListData.clear();%> 
        
      




        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE>

  
     
      
</BODY></HTML>