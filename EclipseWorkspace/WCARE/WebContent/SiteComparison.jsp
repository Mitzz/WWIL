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
<%@ page import="com.enercon.customer.dao.CustomerDao"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>    


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
href="<%=request.getContextPath()%>/resources/css/Report.css" type="text/css" rel="stylesheet">
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
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");
String compare=request.getParameter("compare");
int cmpeval=Integer.parseInt(compare);
//System.out.println("cmpeval"+cmpeval);

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(rdate);
String prevdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

%>
<TABLE cellSpacing="0" cellPadding="0" width="90%" border="0" bgcolor="brown">
	<TBODY>
		<TR>
			<TD class=SectionTitle2 colspan="2" align="left">
				<input type="button" value="Back" onClick=location.href='CompareView.jsp?id=<%=custid%>&rd=<%=rdate%>&type=<%=type%>&Compare=<%=compare%>'>	
		 	</TD>
		 	<TD class=SectionTitle2 colspan="1" align="right">
			 	<input type="button" value="Print" onClick="window.print()" class="printbutton">
			 	<input align="right" type="button" value="Excel" onClick=location.href="ExportSiteComparision.jsp?stateid=<%=stateid%>&siteid=<%=siteid%>&id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>&compare=<%=compare%>" class="printbutton">
 			</TD>
		</TR>
		<TR>
			<TD class=SectionTitle2 colspan="2" align="left">
				<input type="button" style="color:white;font=bold;background-color:green" value="Previous" onClick=location.href='SiteComparison.jsp?id=<%=custid%>&rd=<%=prevdate%>&type=<%=type%>&compare=<%=compare%>&stateid=<%=stateid%>&siteid=<%=siteid%>'>	
		 	</TD>
		 	<TD class=SectionTitle2 colspan="1" align="right">
				<input type="button" style="color:white;font=bold;background-color:green" value="Next" onClick=location.href='SiteComparison.jsp?id=<%=custid%>&rd=<%=nextDate%>&type=<%=type%>&compare=<%=compare%>&stateid=<%=stateid%>&siteid=<%=siteid%>'>	
		 	</TD>
		</TR>
	</TBODY>
</TABLE>
<%List tranList = new ArrayList();
// List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 
String cls="TableRow1";
String ebid="";
String cname="";
String state="";
String Remarks="NA";
String year=rdate.substring(6);
String dmonth=rdate.substring(0,6);
String monthno=rdate.substring(3,5);
int ayear=Integer.parseInt(year);
for (int i=0; i <tranList.size(); i++)
{
	Vector v = new Vector();
	v = (Vector)tranList.get(i);
    ebid=(String)v.get(5);
    
    cname=(String)v.get(0).toString().replaceAll("&","and");
    state=(String)v.get(3)+"-"+(String)v.get(4);
%>



<p></p><p></p>
<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0 bgcolor="green">
  <TBODY>
  <TR>
  
    <TD class=SectionTitle colspan="3" noWrap>Comparison Report  </TD>
    </TR></TBODY></TABLE>
  
<P><BR>
 <TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = 100% colspan="6"><%=v.get(0)%></td>
		            
		            </tr>
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           
		           
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 100% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2> <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = middle  class=TableCell align = left width = 18%>Machines: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=v.get(8)%> </td>
		           
		            <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 20%>Location Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=v.get(7)%> MW</td>
		           
		           <td vAlign = middle  class=TableCell align = left width = 18%></td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2></td>
		           </tr>

</TBODY></TABLE>

<P></P>

        
        
       <%  
       
       CustomerDao cd=new CustomerDao(); 
       String monthname="";
       List tranListData = new ArrayList();
        List compareListData = new ArrayList();
          tranListData = (List)secutils.getCompareData(ebid,rdate,type); 
         
      
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      String wecid="";
      String pwecid="";
      if(wecsize>0){ %>
    	 
    	
    	  
   <%   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String name = (String)vdata.get(0);
				wecid=(String)vdata.get(8);
				
				if(wecid.equals(pwecid))
						{
					
						}else {  if(j!=0){%>
						 
						 
					  	    
				             
				              
						
						                <%}%>
				<SPAN class=TableTitle>Machine:<%=name %></SPAN><BR>
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
				        <TR class=TableTitleRow>
          <TD class=TableCell width="20%">Date</TD>
           
          <TD class=TableCell width="14.28%">Generation</TD>
          <TD class=TableCell width="10%">Operating Hours </TD>
          <TD class=TableCell width="10%">Lull Hours </TD>
          <TD class=TableCell width="10%">Capacity Factor(%) </TD>
           <TD class=TableCell width="10%">Machine Availability(%)</TD>
          <TD class=TableCell width="10%">Grid Availability(%) </TD>
         
        </TR>  <%} %>
				        <%
				 cls="TableRow1";
				int rem=1;
				rem=j%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
       <% if(type.equals("D")){%> 
          <TD class=TableCell><%=vdata.get(9)%></TD>
         <%}else if(type.equals("M")){
         
        
        	 monthname= cd.GetMonthName(monthno);
        	 //System.out.println("monthno"+monthno);
        	 monthname=monthname+"-"+year;
        	 
         %>
         
         
          <TD class=TableCell><%=monthname%></TD>
         <%}  else if(type.equals("Y")){%>
          <TD class=TableCell><%=vdata.get(9)%></TD>
         <%}%>
           <TD class=TableCell><%=vdata.get(2)%></TD>
          <TD class=TableCell><%=vdata.get(3)%></TD>
          <%
						if (!vdata.get(10).toString().equals("1")) {
						%>




						<TD class=TableCell><%=vdata.get(4)%></TD> 
						<TD class=TableCell><%=vdata.get(7)%></TD>
						<TD class=TableCell><%=vdata.get(5)%></TD>
						<TD class=TableCell><%=vdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD class=TableCell colspan="4">Machine Under Trial Run</TD>


						<%
						}
						%>
          
          
         
          
          </TR>
        
      <% String cdate="";
          int cyear=ayear;
      for(int g=0;g<cmpeval;g++)
           {  
    	    cyear=cyear-1;
    	     cdate=dmonth+cyear;
    	     //System.out.println("cdate"+cdate);
    	     compareListData.clear();
             compareListData = (List)secutils.getCompareWECData(ebid,wecid,cdate,type);  
               for (int k=0; k <compareListData.size(); k++)
			    {   
				   Vector wecdata = new Vector();
				   wecdata = (Vector)compareListData.get(k);
				
				
				    rem=1;
				   rem=k%2;
				
                 if(rem==0)
            	    cls="TableRow2";
                 else
            	   cls="TableRow1";
        
           %>
        
        <TR class=<%=cls%>>
        <% if(type.equals("M")){%>
          <TD class=TableCell><%=wecdata.get(9)%>-<%=cyear%></TD>
       <%} else {%>
           <TD class=TableCell><%=wecdata.get(9)%></TD>
           <%} %>
           <TD class=TableCell><%=wecdata.get(2)%></TD>
          <TD class=TableCell><%=wecdata.get(3)%></TD>
          <TD class=TableCell><%=wecdata.get(4)%></TD> 
          
          
           <%
						if (!wecdata.get(10).toString().equals("1")) {
						%>





						<TD class=TableCell><%=wecdata.get(7)%></TD>
						<TD class=TableCell><%=wecdata.get(5)%></TD>
						<TD class=TableCell><%=wecdata.get(6)%></TD>

						<%
						} else {
						%>

						<TD class=TableCell colspan="3">Machine Under Trial Run</TD>


						<%
						}
						%>
          
         
          
          </TR>
          <%} compareListData.clear();}%>
        
        
        
        
        </TBODY></TABLE></TD></TR></TBODY></TABLE> 
        
        
        
        
        
        <% }%> 
        <%tranListData.clear();}}tranList.clear();%>
        
        
   

     

 
        
       
        
        
  

   
  <P></P>
  
</CENTER></BODY></HTML>