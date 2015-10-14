<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="Export.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");
%>
        
<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

</HEAD>
<BODY text="#000000" bottomMargin="1"  leftMargin="1" topMargin="1" 
rightMargin="1" marginheight="1" marginwidth="1">

  
  
<%
String custid=request.getParameter("id");
String stateid=request.getParameter("stateid");
String siteid=request.getParameter("siteid");
String rdate=request.getParameter("rd");
String type=request.getParameter("type");%>

<%List tranList = new ArrayList();
// List sitetranList = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
tranList = (List)secutils.getEBHeading(custid,rdate,stateid,siteid); 
String cls="TableRow1";
String ebid="";
String cname="";
String state="";
// String Remarks="NA";
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
int month=ffd.getMonth()+1;
String day=rdate.substring(0,2);




String year=rdate.substring(6);
//String syear="";




int cyear = ffd.getYear()-100;
int nyear =cyear;
//System.out.println("Month: " + month);
//System.out.println("Year: " + cyear);
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



 <TABLE cellSpacing="1" cellPadding="1" width="90%" border="3"> 
 <tr><td>

  
<BR>
 <TABLE cellSpacing="1" cellPadding="1"width="100%" border="1">
  <TBODY>
<% if (i==0){%>  
<tr BGCOLOR="#E0FFFF">
<td vAlign = "middle"  align = "center" width = "100%" colspan="19" height="40"><font size="5"><b><%=v.get(0)%></b></font></td>
		            
		            </tr>
<%}%>   
		           
		           <tr>
			           <td vAlign = "middle"  align = "left" width = "20%">Location:</td>
			           <td vAlign = "middle"  align = "left" width = "38%" colspan="2"> <%=v.get(3)%> - <%=v.get(4)%> </td>
		           
			           <td vAlign = "middle"   align = "left" width = "18%">Machines: </td>
			           <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=v.get(8)%></td>
		           	   
		            
			           <td vAlign = "middle"  align = "left" width = "20%">Location Capacity:</td>
			           <td vAlign = "middle"  align = "left" width = "38%" colspan="2"><%=v.get(7)%>MW</td>
		           
		          <%if(type.equals("D")){%>
		           	   <td vAlign = "middle"   align = "left" width = "18%">Date: </td>
		               <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=day%>-<%=monthname%>-<%=year%></td>
		             <%}else if(type.equals("M")||type.equals("DM")){%>
		               <td vAlign = "middle"   align = "left" width = "18%">Month: </td>
		         	   <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=monthname%>-<%=year%></td>
		          <%}else if(type.equals("Y")){%>
		           	   <td vAlign = "middle"   align = "left" width = "18%">Year: </td>
		           	   <td vAlign = "middle"  align = "left" width = "24%" colspan="2"><%=pdate%>-<%=ndate%></td>		          
		          <%} %>
		          	   <td colspan="7"></td>
		           </tr>

</TBODY></TABLE>

      <TABLE cellSpacing="1" cellPadding="2" width="100%" border="1">
        <TBODY>
        <TR BGCOLOR="#FFF8C6"><TD align="center" colspan="19"><B>WEC DATA</B></TD></TR>
        
       <%  
       
       
       List tranListData = new ArrayList();
          tranListData = (List)secutils.getWECDataAdmin(ebid,rdate,type); 
          
          DecimalFormat dec = new DecimalFormat("###.##"); 
          double ttlWecGen = 0;
         
      cls="TableRow1";
      //System.out.println("tranListData"+tranListData.size());
      int  wecsize=tranListData.size();
      if(wecsize>0){ 
    	  if(type.equals("D")) {
      %>    	  
    	<TR>
          <TD  width="150" align="center"><B>WEC Name</B></TD>
          <TD  width="70" align="center"><B>Cum Generation</B></TD> 
          <TD  width="70" align="center"><B>Today's Generation</B></TD>
          <TD  width="50" align="center"><B>Cum Opr Hours </B></TD>
          <TD  width="50" align="center"><B>Today's Operating Hours </B></TD>
          
          <TD  width="50" align="center"><B>Lull Hours </B></TD>          
          <TD  width="50" align="center"><B>Machine Fault</B></TD>
          <TD  width="50" align="center"><B>Machine S/D</B></TD>
          <TD  width="50" align="center"><B>Internal Fault </B></TD>
          <TD  width="50" align="center"><B>Internal S/D</B></TD>
          <TD  width="50" align="center"><B>External Fault </B></TD>
          <TD  width="50" align="center"><B>External S/D</B></TD>
          <TD  width="50" align="center"><B>Total HRS.</B></TD>
          <TD  width="50" align="center"><B>Capacity Factor(%) </B></TD>
          <TD  width="50" align="center"><B>Machine Availability(%)</B></TD>
          <!--<TD  width="50" align="center"><B>Internal Grid Availability(%) </B></TD>-->
          <TD  width="50" align="center"><B>External Grid Availability(%) </B></TD>
          <TD  width="50" align="center"><B>Resource Availability(%)</B></TD>
          <TD  width="50" align="center"><B>FM Hrs</B></TD>
          <TD  width="50" align="center"><B>Load Shedding Hrs</B></TD>          
        </TR>  
      <%}else { %>
        <TR>
          <TD  width="150" align="center"><B>WEC Name</B></TD>           
          <TD  width="150" align="center"><B>Today's Generation</B></TD>
          <TD  width="50" align="center"><B>Today's Opr Hours </B></TD>
          
          <TD  width="50" align="center"><B>Lull Hours </B></TD>          
          <TD  width="50" align="center"><B>Machine Fault</B></TD>
          <TD  width="50" align="center"><B>Machine S/D</B></TD>
          <TD  width="50" align="center"><B>Internal Fault </B></TD>
          <TD  width="50" align="center"><B>Internal S/D</B></TD>
          <TD  width="50" align="center"><B>External Fault </B></TD>
          <TD  width="50" align="center"><B>External S/D</B></TD>
          <TD  width="50" align="center"><B>Total HRS.</B></TD>
          <TD  width="50" align="center"><B>Capacity Factor(%) </B></TD>
          <TD  width="50" align="center"><B>Machine Availability(%)</B></TD>
          <!--<TD  width="50" align="center"><B>Internal Grid Availability(%) </B></TD>-->
          <TD  width="50" align="center"><B>External Grid Availability(%) </B></TD>
          <TD  width="50" align="center"><B>Resource Availability(%)</B></TD>
          <TD  width="50" align="center"><B>FM Hrs</B></TD>
          <TD  width="50" align="center"><B>Load Shedding Hrs</B></TD>          
        </TR> 
        
    <%} %>	  
    <%   for (int j=0; j <tranListData.size(); j++)
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
        
        <TR >
        <%if(type.equals("D")){%>
          <TD align="center"><%=vdata.get(0)%></TD>
          <TD align="center"><%=vdata.get(20)%></TD>
          <%}else if(type.equals("M")){%>
          <TD ><a href="CustomerAdminReportM.jsp?wectype=<%=vdata.get(1)%>&wecname=<%=name%>&ebid=<%=ebid%>&wecid=<%=vdata.get(14)%>&rd=<%=rdate%>&state=<%=state%>&cname=<%=cname%>&type=M"><%=vdata.get(0)%></a></TD>
          <%}else if(type.equals("Y")){%>
          <TD ><a href="CustomerAdminReportY.jsp?wectype=<%=vdata.get(1)%>&wecname=<%=name%>&ebid=<%=ebid%>&wecid=<%=vdata.get(14)%>&rd=<%=rdate%>&state=<%=state%>&cname=<%=cname%>&type=Y"><%=vdata.get(0)%></a></TD>
          <%}%>
         
          <TD align="center"><%=vdata.get(2)%></TD>
          <%if(type.equals("D")){ %>
          <TD align="center"><%=vdata.get(21)%></TD>
          <% }%>
          <TD align="center"><%=vdata.get(3)%></TD>
          <TD align="center"><%=vdata.get(4)%></TD> 
          <TD align="center"><%=vdata.get(5)%></TD>
          <TD align="center"><%=vdata.get(6)%></TD>
          <TD align="center"><%=vdata.get(7)%></TD>
          <TD align="center"><%=vdata.get(8)%></TD>
          <TD align="center"><%=vdata.get(9)%></TD>
          <TD align="center"><%=vdata.get(10)%></TD>
          <TD align="center"><%=vdata.get(15)%></TD>
          <TD align="center"><%=vdata.get(13)%></TD>
          
          <TD align="center"><%=vdata.get(11)%></TD>
          <!--<TD align="center"><%=vdata.get(16)%></TD>-->
          <TD align="center"><%=vdata.get(12)%></TD>
          <TD align="center"><%=vdata.get(17)%></TD>
          <TD align="center"><%=vdata.get(18)%></TD>
          <TD align="center"><%=vdata.get(19)%></TD>          
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
					<TR >
					<%if(type.equals("D")){%>
				         <TD align="center"><B>Total:<%=vdata.get(0)%></B></TD>
				         <TD align="center"><B><%=vdata.get(18)%></B></TD>
				        
				       <%}else{%>
				       <TD align="center"><B>Total:</B></TD>
				         
				        <%}%> 
				          <TD align="center"><B><%=vdata.get(1)%></B></TD>
				          <%if(type.equals("D")){%>
				          <TD align="center"><B><%=vdata.get(19)%></B></TD>
				          <% }%>
				          <TD align="center"><B><%=vdata.get(2)%></B></TD>
				          <TD align="center"><B><%=vdata.get(3)%></B></TD>
				          <TD align="center"><B><%=vdata.get(4)%></B></TD>
				          <TD align="center"><B><%=vdata.get(5)%></B></TD>
				          <TD align="center"><B><%=vdata.get(6)%></B></TD>
				          <TD align="center"><B><%=vdata.get(7)%></B></TD>
				          <TD align="center"><B><%=vdata.get(8)%></B></TD>
				          <TD align="center"><B><%=vdata.get(9)%></B></TD>
				          <TD align="center"><B><%=vdata.get(13)%></B></TD>
				          <TD align="center"><B><%=vdata.get(12)%></B></TD>
				          <TD align="center"><B><%=vdata.get(10)%></B></TD>
				          
				          <!--<TD align="center"><B><%=vdata.get(14)%></B></TD>-->
				          <TD align="center"><B><%=vdata.get(11)%></B></TD>
				          <TD align="center"><B><%=vdata.get(15)%></B></TD>
				          <TD align="center"><B><%=vdata.get(16)%></B></TD>
				          <TD align="center"><B><%=vdata.get(17)%></B></TD>
				        </TR>
					        
		  <% }%> 
        
        

        <% }}%>
      
</TBODY></TABLE>
</TD></TR><TR><TD>
<TABLE width="100%" border="1">
	<TBODY>		
		<TR BGCOLOR="#FFF8C6">
			<TD colspan="19" align="center" height="25"><B>EB DATA</B></TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE  width="100%" border="1">
	<TBODY>						
					<TR>
						<TD   colspan="2" align="center"><B>Description</B></TD>
						<TD   colspan="2" align="center"><B>KWH Export</B></TD>
						<TD   colspan="2" align="center"><B>KWH Import</B></TD>						
						<TD   colspan="2" align="center"><B>Net KWH</B></TD>
						<TD   colspan="2" align="center"><B>RKVAH Import Lag</B></TD>
						<TD   colspan="2" align="center"><B>RKVAH Export Lag</B></TD>
						<TD   colspan="2" align="center"><B>RKVAH Import Lead</B></TD>
						<TD   colspan="2" align="center"><B>RKVAH Export Lead</B></TD>
						<TD   colspan="3" align="center"><B>Line Loss</B></TD>
					</TR>

<%
			tranListData.clear();
			tranListData = (List) secutils.getEBData(ebid, rdate, type);
			
			if (tranListData.size() > 0) {
				for (int j = 0; j < tranListData.size(); j++) {
					Vector vdata = new Vector();
					vdata = (Vector) tranListData.get(j);					
														
						%>
	
						<TR>
							<TD colspan="2" align="center"><%=vdata.get(0)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(1)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(2)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(3)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(5)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(6)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(7)%></TD>
							<TD colspan="2" align="center"><%=vdata.get(8)%></TD>
							<TD colspan="3" align="center"><%=dec.format(((ttlWecGen-Double.parseDouble(vdata.get(1).toString()))/ttlWecGen)*100)%></TD>
						</TR>
	
						<%ttlWecGen = 0;
				}
			}else
			{
				%>
					<TR>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="2">0</TD>
						<TD colspan="3">0</TD>
					</TR>
				<%
			}
							
					%>
				

<%
		
		tranListData.clear();
%> 



	</TBODY>
</TABLE>

</TD></TR> </TABLE>	
 <P></P>
  <P></P>  
 <% }tranList.clear();%>         
   
  
</BODY></HTML>