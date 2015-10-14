<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>


<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>Customize Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

%>
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}

function myAllFunction(ur,custid,rdate,siteid,stateid)
{

var url=ur+"?custid="+custid+ "&rd="+rdate+ "&stateid="+stateid+ "&siteid="+siteid;

window.open(url,'name','height=600,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}

</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 
rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
  
  
<%
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String comma[]=rdate.split(",");
String fdate=comma[0];
String tdate=comma[1];




%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
<TBODY>
 <tr bgcolor="#0a4224">
 <TD class=SectionTitle1 align="center" colspan="5">
		 &nbsp;
 		 
		
		</TD>
  <TD class=SectionTitle1 align="left" colspan="5">
 <input  type="button" value="Back" onClick=location.href='CustomizeView.jsp?id=<%=custid%>&fd=<%=fdate%>&td=<%=tdate%>'> </TD>
	
		<TD class=SectionTitle1 align="center" colspan="5">
		
 		 <input  type="button" value="View All Machine" onClick=location.href='CustomizeAllAdminReportM.jsp?custid=<%=custid%>&rd=<%=rdate%>&stateid=<%=stateid%>&siteid=<%=siteid%>'>
 
		
		</TD>
 		<TD class=SectionTitle1  align="right" colspan="5">
           <input  type="button" value="Excel" onClick=location.href='ExportCustomizeReport.jsp?id=<%=custid%>&rd=<%=rdate%>&type=YD&stateid=<%=stateid%>&siteid=<%=siteid%>'>
           <input  type="button" value="Print" onClick="window.print()" class="printbutton">
        </TD>
	</tr>
  
   <tr bgcolor="#0a4224">
    <TD class=SectionTitle1   align="center" colspan="10" noWrap>&nbsp; </TD>
    <TD class=SectionTitle1   align="center" colspan="10" noWrap>&nbsp; Analysis Report  </TD>

</TR>


</TBODY></TABLE>
<%
List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
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






  

 <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = "100%" colspan="8"><%=v.get(0)%></td>
		   
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = "90%" align = "center" colspan="8"></td>
		           </tr>
		           
		           
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 90% colspan="8"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 15% colspan=2>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2>Phase <%=v.get(9)%> - <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = middle  class=TableCell align = left width = 15% colspan=2>Machines: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=v.get(8)%> X <%=v.get(6)%></td>
		           </tr>
		            <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell width = 100% align = center colspan="8"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 15% colspan=2>Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=v.get(7)%> MW</td>
		           
		         
		           	 <td vAlign = middle  class=TableCell align = left width = 15% colspan=2>Date: </td>
		            <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=fdate%>-<%=tdate%></td>
		             
		        
		           </tr>

</TBODY></TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>
<TABLE height=6 cellSpacing=0 cellPadding=0 border=0>
  <TBODY>
  <TR>
    <TD></TD></TR></TBODY></TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%"   border=0>
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" border=0>
        <TBODY>
        
        
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDataAdmin(ebid,rdate,"YD"); 
         
      cls="TableRow1";
      //////System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ %>
    	  
    	<TR class=TableTitleRow>
          <TD class=TableCell width="100">WEC Name</TD>
           
          <TD class=TableCell width="50">Generation</TD>
          <TD class=TableCell width="50">Operating Hours </TD>
          <TD class=TableCell width="50">Lull Hours </TD>
          
          <TD class=TableCell width="50">Machine Fault</TD>
          <TD class=TableCell width="50">Machine S/D</TD>
          <TD class=TableCell width="50">Internal Fault </TD>
          <TD class=TableCell width="50">Internal S/D</TD>
         <TD class=TableCell width="50">External Fault </TD>
          <TD class=TableCell width="50">External S/D</TD>
          <TD class=TableCell width="50">Total HRS.</TD>
          <TD class=TableCell width="50">Capacity Factor(%) </TD>
          <TD class=TableCell width="50">Machine Availability(%)</TD>
          <TD class=TableCell width="50">Grid Internal Availability(%) </TD>
          <TD class=TableCell width="50">Grid External Availability(%) </TD>
          <TD class=TableCell width="50">WindFarm Availability(%) </TD>
           <TD class=TableCell width="50">WEC_SPSD </TD>
          <TD class=TableCell width="50">EB_SPSD  </TD>
          
        </TR>  
    	  
   <%   for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String name = (String)vdata.get(0);
			
				int rem=1;
				rem=j%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
       
          
         
          <TD class=TableCell><a href="javascript:onClick=myFunction('CustomizeAdminReportM.jsp','<%=vdata.get(1)%>','<%=name%>','<%=ebid%>','<%=vdata.get(14)%>','<%=rdate%>','<%=state%>','<%=cname%>','M')"><%=vdata.get(0)%></a></TD>
		 <TD class=TableCell><%=vdata.get(2)%></TD>
          <TD class=TableCell><%=vdata.get(3)%></TD>
          <TD class=TableCell><%=vdata.get(4)%></TD> 
          <TD class=TableCell><%=vdata.get(5)%></TD>
          <TD class=TableCell><%=vdata.get(6)%></TD>
          <TD class=TableCell><%=vdata.get(7)%></TD>
            <TD class=TableCell><%=vdata.get(8)%></TD>
          <TD class=TableCell><%=vdata.get(9)%></TD>
          <TD class=TableCell><%=vdata.get(10)%></TD>
           <TD class=TableCell><%=vdata.get(15)%></TD>
          <TD class=TableCell><%=vdata.get(13)%></TD>
          
          <TD class=TableCell><%=vdata.get(11)%></TD>
           <TD class=TableCell><%=vdata.get(16)%></TD>
          <TD class=TableCell><%=vdata.get(12)%></TD>
           <TD class=TableCell><%=vdata.get(17)%></TD>
           <TD class=TableCell><%=vdata.get(18)%></TD>
           <TD class=TableCell><%=vdata.get(19)%></TD>
          
          </TR>
        
        <% }%> 
        <%tranListData.clear();
        if(wecsize>1)
        {
          tranListData = (List)secutils.getEBWiseTotalAdmin(ebid,rdate,"YD");  
           for (int j=0; j <tranListData.size(); j++)
		   {
					Vector vdata = new Vector();
					vdata = (Vector)tranListData.get(j);
					//String name = (String)vdata.get(0);
					%> 
					<TR class=TableSummaryRow>
					
				         <TD class=TableCell>Total:<%=vdata.get(0)%></TD>
				        
				       
				          <TD class=TableCell><%=vdata.get(1)%></TD>
				          <TD class=TableCell><%=vdata.get(2)%></TD>
				          <TD class=TableCell><%=vdata.get(3)%></TD>
				          <TD class=TableCell><%=vdata.get(4)%></TD>
				          <TD class=TableCell><%=vdata.get(5)%></TD>
				          <TD class=TableCell><%=vdata.get(6)%></TD>
				          <TD class=TableCell><%=vdata.get(7)%></TD>
				          <TD class=TableCell><%=vdata.get(8)%></TD>
				          <TD class=TableCell><%=vdata.get(9)%></TD>
				          <TD class=TableCell><%=vdata.get(13)%></TD>
				          <TD class=TableCell><%=vdata.get(12)%></TD>
				          <TD class=TableCell><%=vdata.get(10)%></TD>
				          
				          <TD class=TableCell><%=vdata.get(14)%></TD>
				          <TD class=TableCell><%=vdata.get(11)%></TD>
				          <TD class=TableCell><%=vdata.get(15)%></TD>
				          <TD class=TableCell><%=vdata.get(16)%></TD>
				          <TD class=TableCell><%=vdata.get(17)%></TD>
				          <TR>
					        
		  <% }%> 
        
        

      <% }}%>
      
</TBODY></TABLE></TD></TR></TBODY></TABLE>	
 <% }tranList.clear();%> 
</CENTER></BODY></HTML>