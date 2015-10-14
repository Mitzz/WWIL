<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>

<%@ page import="java.util.*" %>
<%@ page import="java.text.DecimalFormat" %>

<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK 
href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();

%>

</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
<%
String custid=request.getParameter("id");
String fromDdate=request.getParameter("rd");
String toDate=request.getParameter("rd1");

%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
<TBODY>
 <TR bgcolor="#0a4224">
	 <TD class=SectionTitle1  align="right"> 	
	 	<input type="button" value="Print" onClick="window.print()" class="printbutton">
	 </TD>
 </TR>
 <TR>  
    <TD class=SectionTitle1 align="center" colspan="20" noWrap>WEC Curtailment Report</TD>

</TBODY></TABLE>
<%
List wecList = new ArrayList();
List nearByWECList = new ArrayList();

DecimalFormat df = new DecimalFormat("#.##");

CustomerUtil secutils = new CustomerUtil();
wecList = (List)secutils.getWECCurtailmentList(custid,fromDdate,toDate); 
String wecid = "", nearbywec="";
%>
<TABLE cellSpacing=0 cellPadding=0 width="950" border="1">
  <TBODY>
  
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" border="1">
        <TBODY>
         <TR class=TableTitleRow>
          <TD class=TableCell width="150">WEC Name</TD>  
          <TD class=TableCell width="100">Start Date</TD>
          <TD class=TableCell width="100">End Date</TD> 
          <TD class=TableCell width="50">Duration</TD>         
          <TD class=TableCell width="50">P-Max Setting</TD>         
          <TD class=TableCell width="50">Generation</TD>
          
          <TD class=TableCell width="150">Near By WEC</TD>            
          <TD class=TableCell width="50">Machine Availability(%)</TD>         
          <TD class=TableCell width="50">Generation</TD> 
                           
          <TD class=TableCell width="50">Generation at 100%</TD>
          <TD class=TableCell width="50">Actual MA(%) of Curtailed WEC</TD>
          <TD class=TableCell width="110">Calculated MA(%) of Curtailed WEC</TD>                    
        </TR> 
<%
for (int i=0; i <wecList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)wecList.get(i);
	
	wecid=v.get(5).toString();
	nearbywec=v.get(6).toString();	
	

		
  		nearByWECList = (List)secutils.getNearByWECCurtailmentList(nearbywec,v.get(7).toString(),v.get(8).toString()); 
  		Vector v1 = new Vector();
	  	v1 = (Vector)nearByWECList.get(0);
	  	
	  	double genat100 = Double.parseDouble(v1.get(1).toString())*100/Double.parseDouble(v1.get(3).toString());//Math.round(Double.parseDouble(v1.get(1).toString())*100/Double.parseDouble(v1.get(3).toString()));
  		genat100 = Math.round(Double.parseDouble(v1.get(1).toString()))*100/Math.round(Double.parseDouble(v1.get(3).toString()));
  		
	  	//long d = Math.round((Double.parseDouble(v.get(1).toString())/Math.round(Double.parseDouble(v1.get(1).toString()))*100/Math.round(Double.parseDouble(v1.get(3).toString())))*100);
	  	double d = (Double.parseDouble(v.get(1).toString())/Double.parseDouble(v1.get(1).toString())*100/Double.parseDouble(v1.get(3).toString()))*100;
		//double d = (Double.parseDouble(v1.get(1).toString())/(Double.parseDouble(v1.get(1).toString())*100)/Double.parseDouble(v1.get(3).toString()))*100;
		d = (Double.parseDouble(v.get(1).toString())/genat100)*100;
  		if(d > Math.round(Double.parseDouble(v.get(3).toString())))
  			d = Math.round(Double.parseDouble(v.get(3).toString()));
  		String str = df.format(d);
  		int strLen = str.length();
  		if(strLen == 2)
  			str = Double.toString(d);
  		
%>
		<TR class=TableSummaryRow>			
       	  <TD class=TableCell width="150" bgcolor="pink"><%=v.get(0)%></TD>
       	  <TD class=TableCell width="100" bgcolor="pink"><%=v.get(7)%></TD>
          <TD class=TableCell width="100" bgcolor="pink"><%=v.get(8)%></TD>		          
          <TD class=TableCell width="50" bgcolor="pink"><%=Math.round(Integer.parseInt(v.get(10).toString()))+1%> Days</TD> 
          <TD class=TableCell width="50" bgcolor="pink"><%=v.get(9)%></TD>		          
          <TD class=TableCell width="50" bgcolor="pink"><%=v.get(1)%></TD>
               
          <%--<TD class=TableCell width="50"><%=Math.round((Double.parseDouble(v.get(1).toString())/Double.parseDouble(v1.get(1).toString()))*100) %></TD>  --%>
          
          <TD class=TableCell width="150" bgcolor="yellow"><%=v1.get(0)%></TD>          		          
          <TD class=TableCell width="50" bgcolor="yellow"><%=v1.get(3)%></TD>         
          <TD class=TableCell width="50" bgcolor="yellow"><%=v1.get(1)%></TD>                  	          
          <TD class=TableCell width="50" bgcolor="yellow"><%=Math.round(Double.parseDouble(v1.get(1).toString()))*100/Math.round(Double.parseDouble(v1.get(3).toString()))%></TD>
          <TD class=TableCell width="50" bgcolor="pink"><%=v.get(3)%></TD>
          
          <TD class=TableCell width="110" bgcolor="white"><font color="red"><%=str%></font></TD>           
        </TR>
<%
}
wecList.clear();
%>
		</TBODY>
	  </TABLE>
</TD></TR></TBODY></TABLE>	
 
</CENTER></BODY></HTML>


