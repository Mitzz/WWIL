<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*"%>

<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type="text/css" rel="stylesheet">
<% 


response.getOutputStream().flush();
response.getOutputStream().close();
// String userid=session.getAttribute("loginID").toString();
// String Customeridtxt = "";

%>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
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

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(rdate);

String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);

//String crdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

%>
<TABLE cellSpacing="0" cellPadding="0" width="90%" border="0" bgcolor="#94A664">
<TBODY>
<TR>
	<td class="" colspan="2" align="left" >
		<input  type="button" value="Back" onClick=location.href='CompareView.jsp?id=<%=custid%>&rd=<%=rdate%>&type=<%=type%>&Compare=<%=compare%>'>
 	</td>
 	<td align="right" colspan="2" >
	 	<input type="button" value="Print" onClick="window.print()" class="printbutton">
	 	<input  align="right" type="button" value="Excel" onClick=location.href="ExportWECComparision.jsp?stateid=<%=stateid%>&siteid=<%=siteid%>&custid=<%=custid%>&type=<%=type%>&rd=<%=rdate%>&wecid=<%=wecid%>&compare=<%=compare%>&compare=<%=compare%>&name=<%=name%>" class="printbutton">
 	</td>
</TR>
<TR>
	<td colspan="1" align="left"><input type="button" style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="WECComparsion.jsp?compare=<%=compare%>&stateid=<%=stateid%>&siteid=<%=siteid%>&wecid=<%=wecid%>&id=<%=custid%>&type=<%=type%>&rd=<%=prevdate%>&name=<%=name%>" class="printbutton"></TD>
	<td colspan="1" align="center"></td>
	<td colspan="1"  align="right"><input type="button" style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="WECComparsion.jsp?compare=<%=compare%>&stateid=<%=stateid%>&siteid=<%=siteid%>&wecid=<%=wecid%>&id=<%=custid%>&type=<%=type%>&rd=<%=nextDate%>&name=<%=name%>" class="printbutton"></TD>
</TR>

</TBODY></TABLE>
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

<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0 bgcolor="green">
  <TBODY>
  <TR>
  
    <TD class=SectionTitle colspan="3" noWrap>Comparison Report  </TD>
    </TR></TBODY></TABLE>
  
<P><BR>

       <SPAN class=TableTitle>Machine:<%=name %></SPAN><BR>
     <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
				  <TBODY>
				  <TR>
				    <TD>
				      <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				        <TBODY>
				        <TR class=TableTitleRow>
          <TD class=TableCell width="14.28%">Date</TD>
           
          <TD class=TableCell width="14.28%">Generation</TD>
          <TD class=TableCell width="14.28%">Operating Hours </TD>
          <TD class=TableCell width="14.28%">Lull Hours </TD>
          <TD class=TableCell width="14.28%">Capacity Factor(%) </TD>
          <TD class=TableCell width="14.28%">Machine Availability(%)</TD>
          <TD class=TableCell width="14.28%">Grid Availability(%) </TD>
          
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
        
        <TR class=<%=cls%>>
        <% if(type.equals("M")){%>
          <TD class=TableCell><%=wecdata.get(9)%>-<%=cyear%></TD>
       <%}else{%>
           <TD class=TableCell><%=wecdata.get(9)%></TD>
           <%}%>
           <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(3)%></TD>
           
          
          <%
						if (!wecdata.get(10).toString().equals("1")) {
						%>




						<TD class=TableCell><%=wecdata.get(4)%></TD>
						<TD class=TableCell><%=wecdata.get(7)%></TD>
						<TD class=TableCell><%=wecdata.get(5)%></TD>
						<TD class=TableCell><%=wecdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD class=TableCell colspan="4">WEC Is In Stabilization Phase</TD>


						<%
						}
						%>
          
          
          </TR>
          <% cyear=cyear-1;} compareListData.clear();}%>
        
       

</TBODY></TABLE></TD></TR></TBODY></TABLE> 
        
       
   
  <P></P>
  
</CENTER></BODY></HTML>