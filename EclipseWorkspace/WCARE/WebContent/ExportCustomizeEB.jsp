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
if(file==null || file.equals("")) file="ExportEBData.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>

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
// String userid=session.getAttribute("loginID").toString();
// String Customeridtxt = "";

%>
</HEAD>
<BODY>
<CENTER>
  
  
<%

String site=request.getParameter("SiteTransactions");
String state="NA";
if(site.equals(""))
{
	site="NA";
}
//System.out.print("site"+site);
String cust=request.getParameter("custTransactions");
//System.out.print("cust"+cust);
String fd=request.getParameter("FromDatetxt");
String td=request.getParameter("ToDatetxt");

// List tranList = new ArrayList();
// List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();




%> 

<TABLE width="100%" border="0">
  <TBODY>
  
        <TR>  
    <TD  colspan="17"  align="center"><b>EB Data</b></TD>
    </TR></TBODY></TABLE>
<TABLE width="100%"  border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing="1" cellPadding="2" width="100%" border="1">
        <TBODY>
        
          
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getEBDataDetail(fd,td,site,state,cust);   
         
   
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){
    	 // float cfactor=0,mavail=0,gavail=0;
    	 // int cnt=0,trialexists=0;
    	  
    	  %>
    	  
    	<TR>
           <TD  width="14.28%"><b>EB Desc</b></TD>
           <TD  width="14.28%"><b>Date</b></TD>
           <TD  width="14.28%"><b>KWH IMP</b></TD>
           <TD  width="14.28%"><b>KWH EXP </b></TD>
           <TD  width="14.28%"><b>KVAH IMP</b></TD>
           <TD  width="14.28%"><b>KVAH EXP</b></TD>
           <TD  width="14.28%"><b>RKVAH IMP</b></TD>
           <TD  width="14.28%"><b>RKVAH EXP</b></TD>
		   <TD  width="25"><b>RKVAH IMP LAG </b></TD>
		   <TD  width="25%"><b>RKVAH IMP LEAD</b></TD>
		   <TD  width="25%"><b>RKVAH EXP LAG </b></TD>
		   <TD  width="25%"><b>RKVAH EXP LEAD</b></TD>
		   <TD  width="25%"><b>JR KWH IMP</b></TD>
           <TD  width="25%"><b>JR KWH EXP</b></TD>
           <TD  width="25%"><b>Site</b></TD>
           <TD  width="25%"><b>State</b></TD>
           <TD  width="25%"><b>Customer</b></TD>
        </TR>  
    	  
   		<%   
   			for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				// String name = (String)vdata.get(0);
				//String gen =String.parse(vdata.get(1));
              	//String gen ="23.00";
				//String gen2 =gen.toString();

				//String ghrs = (String)vdata.get(2);
				//String lhrs = (String)vdata.get(3);
				//String mavdataail = (String)vdata.get(4);
				//String gavdataail = (String)vdata.get(5);
				//String cfactor = (String)vdata.get(6);
				//String stateid = (String)vdata.get(7);
        
        %>
        
        <TR>          
          <TD ><%=vdata.get(0)%></TD>
          <TD ><%=vdata.get(1)%></TD>
          <TD ><%=vdata.get(2)%></TD>
          <TD ><%=vdata.get(3)%></TD>
          <TD ><%=vdata.get(4)%></TD>
          <TD ><%=vdata.get(5)%></TD>
          <TD ><%=vdata.get(6)%></TD>
          <TD ><%=vdata.get(7)%></TD>
          <TD ><%=vdata.get(8)%></TD>
          <TD ><%=vdata.get(9)%></TD>
          <TD ><%=vdata.get(10)%></TD>
          <TD ><%=vdata.get(11)%></TD>
          <TD ><%=vdata.get(12)%></TD>
          <TD ><%=vdata.get(13)%></TD>
          <TD ><%=vdata.get(14)%></TD>
      	  <TD ><%=vdata.get(15)%></TD>
          <TD ><%=vdata.get(16)%></TD>
          </TR>
        
        <% }
        }
        tranListData.clear();
       %>
       </TBODY></TABLE>
       </TD></TR>
       </TBODY></TABLE>
      
      
</CENTER></BODY>
</html>