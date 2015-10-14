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
href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
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
String type=request.getParameter("type");
//System.out.println(custid);

%>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 >
<TBODY>
 <tr bgcolor="#0a4224">
  <TD class=SectionTitle1 align="left">
 <input  type="button" value="Back" onClick=location.href='AdminView.jsp?id=<%=custid%>&rd=<%=rdate%>&type=<%=type%>'> </TD>
	 <TD class=SectionTitle1 colspan="2" align="center">
 
	
	<%		
			
			if (type.equals("Y")) {
			%>
			  <input type="button" value="View All Machine" onClick="myAllFunction('CustomerAllAdminReportY.jsp','<%=custid%>','<%=rdate%>','<%=siteid%>','<%=stateid%>')">
			 
			<%
			}%>
		</TD>

 <TD class=SectionTitle1  align="right">
 	<input  type="button" value="Excel" onClick=location.href='ExportAdminReport.jsp?id=<%=custid%>&rd=<%=rdate%>&type=<%=type%>&stateid=<%=stateid%>&siteid=<%=siteid%>'>
 	<input  type="button" value="Print" onClick="window.print()" class="printbutton">
 </TD>
 
 
 </tr>
 <TR >
  
    <TD class=SectionTitle1   align="center" colspan="20" noWrap>Analysis Report  </TD>





</TBODY></TABLE>
<%List tranList = new ArrayList();
List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 
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

<% 
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
//System.out.println("rdate1: " + rdate);
int month=ffd.getMonth()+1;
String day=rdate.substring(0,2);

String year=rdate.substring(6);

int cyear = ffd.getYear()-100;
int nyear =cyear;

 	 			     	    if(month>=4)
 	 			     	     	{
 	 			     			   nyear=cyear+1;
 	 			     		    }
 	 			     	    else
	 			    	        {   nyear=cyear;
	 			    	            cyear=cyear-1;
	 			    	        }
 	 			     		String pdate="APR-"+Integer.toString(cyear);
 	 			     		String ndate="MAR-"+Integer.toString(nyear);

 	 			     		String monthname="";
 	 			     		if(month==4)
 	 			     		{
 	 			     			   monthname="APRIL";
 	 			     			}
 	 			     			if(month==3)
 	 			     			{	
 	 			     			monthname="MARCH";
 	 			     			}
 	 			     			if(month==1)
 	 			     			{	
 	 			     			monthname="JANUARY";
 	 			     			}
 	 			     			if(month==2)
 	 			     			{
 	 			     			monthname="FEBRUARY";
 	 			     			}
 	 			     			if(month==5)
 	 			     			{
 	 			     				monthname="MAY";
 	 			     			}
 	 			     			if(month==6)
 	 			     			{
 	 			     			monthname="JUNE";
 	 			     			}
 	 			     			if(month==7)
 	 			     			{
 	 			     			monthname="JULY";
 	 			     			}
 	 			     			if(month==8)
 	 			     			{
 	 			     				monthname="AUGUST";
 	 			     			}
 	 			     			if(month==9)
 	 			     			{
 	 			     				monthname="SEPTEMBER";
 	 			     			}
 	 			     			if(month==10)
 	 			     			{
 	 			     				monthname="OCTOBER";
 	 			     			}
 	 			     			if(month==11)
 	 			     			{	
 	 			     				monthname="NOVEMBER";
 	 			     			}
 	 			     			if(month==12)
 	 			     			{	
 	 			     				monthname="DECEMBER";
 	 			     			}

%>

<P><BR>
 <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
  <TBODY>
  
<tr class=TableTitle>
<td vAlign = middle class=TableCell align = center width = "100%" colspan="6"><%=v.get(0)%></td>
		   
		            <tr class=TableSummaryRow>
		          <td vAlign = middle class=TableCell width = "90%" align = "center" colspan="6"></td>
		           </tr>
		           
		           
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = center width = 90% colspan="6"></td>
		           </tr>

		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 15%>Location:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2>Phase <%=v.get(9)%> - <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
		           <td vAlign = middle  class=TableCell align = left width = 15%>Machines: </td>
		           <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=v.get(8)%> </td>
		           
		            <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell width = 100% align = center colspan="6"></td>
		           </tr>
		           <tr class=TableSummaryRow>
		           <td vAlign = middle class=TableCell align = left width = 15%>Capacity:</td>
		           <td vAlign = middle class=TableCell align = left width = 38% colspan=2><%=v.get(7)%> MW</td>
		           
		          <%if(type.equals("D")){%>
		           	 <td vAlign = middle  class=TableCell align = left width = 15%>Date: </td>
		            <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=day%>-<%=monthname%>-<%=year%></td>
		             <%}else if(type.equals("M")||type.equals("DM")){%>
		             <td vAlign = middle  class=TableCell align = left width = 15%>Month: </td>
		         	  <td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=monthname%>-<%=year%></td>
		          <%}else if(type.equals("Y")){%>
		           	<td vAlign = middle  class=TableCell align = left width = 15%>Year: </td>
		           	<td vAlign = middle class=TableCell align = left width = 24% colspan=2><%=pdate%>-<%=ndate%></td>
		          
		          <%} %>
		           </tr>

</TBODY></TABLE>

<P></P>
<SPAN class=TableTitle>WEC Data </SPAN><BR>

<TABLE cellSpacing=0 cellPadding=0 width="100%"   border="1">
  <TBODY>
  <TR>
    <TD>
      <TABLE cellSpacing=1 cellPadding=1 width="100%" border="1">
        <TBODY>
        
        
       <%  
       
       
      List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDataAdmin(ebid,rdate,type); 
         
      cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      DecimalFormat dec = new DecimalFormat("###.##"); 
      double ttlWecGen = 0;
      int  wecsize=tranListData.size();
      if(wecsize>0){ 
      	if(type.equals("D")) {
      %>
    	  
    	<TR class=TableTitleRow>
          <TD class=TableCell width="80">WEC Name</TD>
          <TD class=TableCell width="50">Cum Generation</TD> 
          <TD class=TableCell width="50">Today's Generation</TD>
          <TD class=TableCell width="50">Cum Operating Hours </TD>
          <TD class=TableCell width="50">Today's Operating Hours </TD>
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
          <!-- <TD class=TableCell width="50">Grid Internal Availability(%) </TD> -->
          <TD class=TableCell width="50">External Grid Availability(%)</TD>
          <TD class=TableCell width="50">Resource Availability(%) </TD>
          <TD class=TableCell width="50">FM Hrs </TD>
          <TD class=TableCell width="50">Load Shedding Hrs </TD>          
        </TR>  
        <%}else{ %>
        <TR class=TableTitleRow>
          <TD class=TableCell width="80">WEC Name</TD>
           
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
          <!-- <TD class=TableCell width="50">Grid Internal Availability(%) </TD> -->
          <TD class=TableCell width="50">External Grid Availability(%)</TD>
          <TD class=TableCell width="50">Resource Availability(%)</TD>
          <TD class=TableCell width="50">FM Hrs</TD>
          <TD class=TableCell width="50">Load Shedding Hrs</TD>          
        </TR> 
        <%} %>
    	  
   		<%  for (int j=0; j <tranListData.size(); j++)
			{   
				Vector vdata = new Vector();
				vdata = (Vector)tranListData.get(j);
				String name = (String)vdata.get(0);
				ttlWecGen =  Double.parseDouble(vdata.get(2).toString());
			
				int rem=1;
				rem=j%2;
				
            if(rem==0)
            	cls="TableRow2";
            else
            	cls="TableRow1";
        
            		%>
        
        <TR class=<%=cls%>>
        <%if(type.equals("D")){%>
          <TD class=TableCell><%=vdata.get(0)%></TD>
          <TD class=TableCell><%=vdata.get(20)%></TD>
          
          <%}else if(type.equals("M")){%>
          <TD class=TableCell>
          
          <a
							href="javascript:onClick=myFunction('CustomerAdminReportM.jsp','<%=vdata.get(1)%>','<%=name%>','<%=ebid%>','<%=vdata.get(14)%>','<%=rdate%>','<%=state%>','<%=cname%>','M')"><%=vdata.get(0)%></a></TD>
				       
          
          
           <%}else if(type.equals("Y")){%>
           
           
          <TD class=TableCell>
          
            <a
							href="javascript:onClick=myFunction('CustomerAdminReportY.jsp','<%=vdata.get(1)%>','<%=name%>','<%=ebid%>','<%=vdata.get(14)%>','<%=rdate%>','<%=state%>','<%=cname%>','Y')"><%=vdata.get(0)%></a></TD>
		
          
           <%}%>
         
         
            <TD class=TableCell><%=vdata.get(2)%></TD>
            <%if(type.equals("D")){ %>
            <TD class=TableCell><%=vdata.get(21)%></TD>
            <%} %>
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
            <!-- <TD class=TableCell><%=vdata.get(16)%></TD> -->
            <TD class=TableCell><%=vdata.get(12)%></TD>
            <TD class=TableCell><%=vdata.get(17)%></TD>
            <TD class=TableCell><%=vdata.get(18)%></TD>
            <TD class=TableCell><%=vdata.get(19)%></TD>
          
          </TR>
        
        <% }%> 
        <%tranListData.clear();
        
        if(wecsize>1)
        {
          tranListData = (List)secutils.getEBWiseTotalAdmin(ebid,rdate,type);  
           for (int j=0; j <tranListData.size(); j++)
		   {
					Vector vdata = new Vector();
					vdata = (Vector)tranListData.get(j);
					ttlWecGen = Double.parseDouble(vdata.get(1).toString());
					//String name = (String)vdata.get(0);
					%> 
					<TR class=TableSummaryRow>
					<%if(type.equals("D")){%>
				         <TD class=TableCell>Total:<%=vdata.get(0)%></TD>
				         <TD class=TableCell><%=vdata.get(18)%></TD>
				       <%}else{%>
				       <TD class=TableCell>Total:</TD>
				         
				        <%}%> 
				          <TD class=TableCell><%=vdata.get(1)%></TD>
				          <%if(type.equals("D")){%>
				          <TD class=TableCell><%=vdata.get(19)%></TD>
				          <%}%>
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
				          <!-- <TD class=TableCell><%=vdata.get(14)%></TD> -->
				          <TD class=TableCell><%=vdata.get(11)%></TD>
				          <TD class=TableCell><%=vdata.get(15)%></TD>
				          <TD class=TableCell><%=vdata.get(16)%></TD>
				          <TD class=TableCell><%=vdata.get(17)%></TD>
				          <TR>
					        
		  <% }%> 
        

      <% }}%>
      
</TBODY></TABLE>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>		
		<TR>
			<TD class=SectionTitle colspan="20" align="center" height="30" noWrap>EB Generation</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" bgColor=#555555 border=0>
	<TBODY>
		<TR>
			<TD>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<TBODY>
				<%if(type.equals("D")) {%>
					<TR class=TableTitleRow>
						<TD class=TableCell width="16%" colspan="2">Description</TD>
						<TD class=TableCell width="10%" colspan="2">KWH Export</TD>
						<TD class=TableCell width="14%" colspan="2">KWH Import</TD>						
						<TD class=TableCell width="11%" colspan="2">Net KWH</TD>
						<TD class=TableCell width="10%" colspan="2">RKVAH IMP LAG</TD>
						<TD class=TableCell width="14%" colspan="2">RKVAH EXP LAG</TD>
						<TD class=TableCell width="10%" colspan="2">RKVAH IMP LEAD</TD>
						<TD class=TableCell width="14%" colspan="2">RKVAH EXP LEAD</TD>
						<TD class=TableCell width="14%" colspan="2">Line Loss</TD>
					</TR>
				<%}else{ %>
					<TR class=TableTitleRow>
						<TD class=TableCell width="16%" colspan="6">Description</TD>
						<TD class=TableCell width="10%" colspan="4">KWH Export</TD>
						<TD class=TableCell width="14%" colspan="4">KWH Import</TD>						
						<TD class=TableCell width="11%" colspan="4">Net KWH</TD>
					</TR>
				<%} %>
<%
			tranListData.clear();
			tranListData = (List) secutils.getEBData(ebid, rdate, type);
			
			cls = "TableRow1";
			
			if (tranListData.size() > 0) {
				for (int j = 0; j < tranListData.size(); j++) {
					Vector vdata = new Vector();
					vdata = (Vector) tranListData.get(j);
					
					int rem = 1;
					rem = j % 2;
					if (!vdata.get(4).toString().equals("NIL")){
						if (!Remarks.equals("."))
							if(Remarks.length()>15)							
								Remarks = "<b>"+vdata.get(4).toString().substring(0,15)+"</b>"+vdata.get(4).toString().substring(15);
							else
								Remarks = "<b>"+vdata.get(4).toString();			
						
					}
					if (rem == 0)
						cls = "TableRow2";
					else
						cls = "TableRow1";					
						%>
					<%if(type.equals("D")) {%>
						<TR class=<%=cls%>>
							<TD class=TableCell colspan="2"><%=vdata.get(0)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(1)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(2)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(3)%></TD>							
							<TD class=TableCell colspan="2"><%=vdata.get(5)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(6)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(7)%></TD>
							<TD class=TableCell colspan="2"><%=vdata.get(8)%></TD>
							<TD class=TableCell colspan="2"><%=dec.format(((ttlWecGen-Double.parseDouble(vdata.get(1).toString()))/ttlWecGen)*100)%></TD>							
						</TR>
					<%}else{ %>
						<TR class=<%=cls%>>
							<TD class=TableCell colspan="6"><%=vdata.get(0)%></TD>
							<TD class=TableCell colspan="4"><%=vdata.get(1)%></TD>
							<TD class=TableCell colspan="4"><%=vdata.get(2)%></TD>
							<TD class=TableCell colspan="4"><%=vdata.get(3)%></TD>
						</TR>
					<%} %>
						<%ttlWecGen = 0;
				}
			}else
			{
				%>
					<TR class=<%=cls%>>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
						<TD class=TableCell colspan="2">0</TD>
					</TR>
				<%
			}
							
					%>
				

<%
		
		tranListData.clear();
%> 


</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
 <% }tranList.clear();%> 
</TD></TR></TBODY></TABLE>	


 
</CENTER></BODY></HTML>