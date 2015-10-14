<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@page contentType="application/vnd.ms-excel"%>
<%@page pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	String file = request.getParameter("File"); 
	if(file==null || file.equals("")) file="ExportData.xls";
	response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");
%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>


<%@page import="java.text.DecimalFormat"%>
<HTML>
<HEAD>    
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%> 

<TITLE>DGR Monthly Report</TITLE>

<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
//	String userid = session.getAttribute("loginID").toString();
//	String Customeridtxt = "";
%>

</HEAD>
<BODY bottomMargin="0" leftMargin="0" topMargin="0" rightMargin="0" marginheight="0" marginwidth="0">
<CENTER>
<%
	String custid = request.getParameter("id");	
	String fdate = request.getParameter("fd");
	String tdate = request.getParameter("td");
	String type = request.getParameter("type");
	String rtype = request.getParameter("rtype");
%>
<%
	if((type=="M")||type.equals("M"))
{
%>
<TABLE cellSpacing="0" cellPadding="0" width="90%" border="0">
	<TBODY>
		<TR BGCOLOR="#808000">
			<TD colspan="7" align="center">Monthly (Machine wise) Generation Report</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE cellSpacing="0" cellPadding="0" width="90%" border="1">
	<TBODY>
<%
	List tranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	
	tranList = (List) secutils.getInitialYearGen(custid, fdate, tdate, type, rtype);
	DecimalFormat dformat=new DecimalFormat("########.00");
	DecimalFormat dformat1=new DecimalFormat("########");
	
	String customerName = "";
	String wecID = "";
	String wecName = "";
	String wecType = "";
	String wecCapacity = "";
	String siteName = "";
	// String areaName = "";
	String stateName = "";
	String generation = ""; 
	String operatingHrs = "";
	String lullHrs = "";
	String machineAvailability = "";
	String capacityFactor = "";
	String gridAvailability = "";
	
	double tgeneration = 0; 
	double toperatingHrs = 0;
	double tlullHrs = 0;
	double tmachineAvailability = 0;
	double tcapacityFactor = 0;
	double tgridAvailability = 0;
	
	int tcount = 0;
	
	String monthWise = "";
	String yearWise = "";
	String oldwecid="";
	int diff=-1;
	int i = 0;
	for (i = 0; i < tranList.size(); i++) {
		ArrayList v = new ArrayList();
		v = (ArrayList) tranList.get(i);
				
		 customerName = v.get(0).toString();
		 wecID = v.get(1).toString(); 
		 wecName = v.get(2).toString(); 
		 wecType = v.get(3).toString(); 
		 wecCapacity = v.get(4).toString();
		 siteName = v.get(5).toString();
		 // areaName = v.get(6).toString(); 
		 stateName = v.get(7).toString(); 
		 generation = v.get(8).toString(); 
		 operatingHrs = v.get(9).toString();
		 lullHrs = v.get(10).toString();
		 machineAvailability = v.get(11).toString(); 
		 capacityFactor = v.get(12).toString(); 
		 gridAvailability = v.get(13).toString(); 
		 monthWise = v.get(14).toString();
		 yearWise = v.get(15).toString();
 		
 %>	
	
	<%if(i == 0){ %>
		<tr class="TableTitle">
			<td vAlign="middle" align="center" width="90%" colspan="7"><%=customerName%></td>
		</tr>
		
		<tr >
			<td vAlign="middle" width="90%" align="center" colspan="7"></td>
		</tr>		

		<tr >
			<td vAlign="middle" class="TableCell1" align="left" width="20%">Location:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=siteName%> - <%=stateName%></td>

			<td vAlign="middle" class="TableCell1" align="left" width="18%">Period:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=fdate%>-<%=tdate%></td>
		</tr>
		<%} %>		
		
		<%if(!wecID.equals(oldwecid)){ %>		 
		   
		<% if(i>1 && tcount>1){
			  tmachineAvailability=tmachineAvailability/tcount;
			  tcapacityFactor=tcapacityFactor/tcount;
			  tgridAvailability=tgridAvailability/tcount;			
			
			%>
		  	<TR class="TableSummaryRow">
		  		 <TD class="TableCell1" align="left"><B>Total</B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(tgeneration)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(toperatingHrs)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(tlullHrs)%></B></TD>
		   		 <TD class="TableCell"><B><%=dformat.format(tcapacityFactor)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat.format(tmachineAvailability)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat.format(tgridAvailability)%></B></TD>		  	
		  	</TR>
		  	<TR><TD COLSPAN="7">&nbsp;</TD></TR>
		  	<%		  		  
		  		} 
		  	%>		
		
		<tr class="TableSummaryRow">
			<td vAlign="middle" width="90%" align="left" colspan="7"></td>
		</tr>
		<tr class="TableSummaryRow">
			<td vAlign="middle" class="TableCell1" align="left" width="18%">Machines:</td>
			<td vAlign="middle" align="left" width="24%" colspan="2"><%=wecName%> </td>
			<td vAlign="middle" class="TableCell1" align="left" width="20%">Machine Capacity:</td>
			<td vAlign="middle" align="left width="38%" colspan="3"><%=wecType%> X <%=wecCapacity%> KW</td>
		</tr>

		<TR BGCOLOR="#808000">
			<TD colspan="7" align="center">Month WEC Wise</TD>
		</TR>

				<TR class="TableTitleRow">
						<TD width="14.28%">Month</TD>
						<TD width="14.28%">Generation(KWH)</TD>
						<TD width="14.28%">Operating Hours</TD>
						<TD width="14.28%">Lull Hours</TD>
						<TD width="14.28%">Capacity Factor(%)</TD>
						<TD width="14.28%">Machine Availability(%)</TD>
						<% if(diff<0){ %>
						<TD width="14.28%">Grid Availability(%)</TD>
						<%} else{ %>
						<TD width="14.28%">Grid  Availability(%) Internal</TD>
						<TD width="14.28%">Grid  Availability(%) External</TD>
						<%} %>

					</TR>
		<%			 tcount=0; 
					 tgeneration=0;
					 toperatingHrs=0;
					 tlullHrs=0;
					 tmachineAvailability=0;
					 tcapacityFactor=0;
					 tgridAvailability=0;			
		
		} %>
		
	
		
		<TR>
		   <TD class="TableCell1" align="left"><%=monthWise%>-<%=yearWise%></TD>
		   <TD class="TableCell"><%=generation%></TD>
		   <TD class="TableCell"><%=operatingHrs%></TD>
		   <TD class="TableCell"><%=lullHrs%></TD>
		   <TD class="TableCell"><%=capacityFactor%></TD>
		   <TD class="TableCell"><%=machineAvailability%></TD>
		   <TD class="TableCell"><%=gridAvailability%></TD>
		</TR>
      
        <%oldwecid=wecID;
		     tgeneration+=Double.parseDouble(generation);
			 toperatingHrs+=Double.parseDouble(operatingHrs);
			 tlullHrs+=Double.parseDouble(lullHrs);
			 tmachineAvailability+=Double.parseDouble(machineAvailability);
			 tcapacityFactor+=Double.parseDouble(capacityFactor);
			 tgridAvailability+=Double.parseDouble(gridAvailability);	
		   tcount++;	 
		  } %>
		  <% if(i>1){ %>
		  <%
		  
		  
		  tmachineAvailability=tmachineAvailability/tcount;
		  tcapacityFactor=tcapacityFactor/tcount;
		  tgridAvailability=tgridAvailability/tcount;
		  
		  %>
		  	<TR class="TableSummaryRow">
		  		 <TD align="left">Total</TD>
		  		 <TD ><B><%=dformat1.format(tgeneration)%></B></TD>
		  		 <TD ><B><%=dformat1.format(toperatingHrs)%></B></TD>
		  		 <TD ><B><%=dformat1.format(tlullHrs)%></B></TD>
		   		 <TD ><B><%=dformat.format(tcapacityFactor)%></B></TD>
		  		 <TD ><B><%=dformat.format(tmachineAvailability)%></B></TD>
		  		 <TD ><B><%=dformat.format(tgridAvailability)%></B></TD>		  	
		  	</TR>		  	
		  	<%} %>
	</TBODY>
</TABLE>

<% } else {%>

<TABLE cellSpacing="0" cellPadding="0" width="90%" border="0">
	<TBODY>
		<TR BGCOLOR="#808000">
			<TD colspan="9" align="center">Daily (Machine wise) Generation Report</TD>
		</TR>
	</TBODY>
</TABLE>
<TABLE cellSpacing="0" cellPadding="0" width="90%" border="1">
	<TBODY>
<%
	List tranList = new ArrayList();
	CustomerUtil secutils = new CustomerUtil();
	
	tranList = (List) secutils.getInitialYearDailyGen(custid, fdate, tdate, type, rtype);
	DecimalFormat dformat=new DecimalFormat("########.00");
	DecimalFormat dformat1=new DecimalFormat("########");
	
	String customerName = "";
	String wecID = "";
	String wecName = "";
	String wecType = "";
	String wecCapacity = "";
	String siteName = "";
	String remarks = "";
	// String areaName = "";
	String stateName = "";
	String generation = ""; 
	String egeneration = ""; 
	String eoperatingHrs = "";
	String operatingHrs = "";
	String lullHrs = "";
	String machineAvailability = "";
	String capacityFactor = "";
	String gridAvailability = "";
	String ebid = "";
	String Remarks="";
	
	double tgeneration = 0; 
	double toperatingHrs = 0;
	double tegeneration = 0; 
	double teoperatingHrs = 0;
	double tlullHrs = 0;
	double tmachineAvailability = 0;
	double tcapacityFactor = 0;
	double tgridAvailability = 0;
	
	int tcount = 0;
	
	String monthWise = "";
	String yearWise = "";
	String oldwecid="";
	String oldebid="";
	int diff=-1;
	int i = 0;
	for (i = 0; i < tranList.size(); i++) {
		ArrayList v = new ArrayList();
		v = (ArrayList) tranList.get(i);
				
		customerName = v.get(0).toString();
		 wecID = v.get(1).toString(); 
		 wecName = v.get(2).toString(); 
		 wecType = v.get(3).toString(); 
		 wecCapacity = v.get(4).toString();
		 siteName = v.get(5).toString();		 
		 stateName = v.get(7).toString(); 
		 generation = v.get(8).toString(); 
		 operatingHrs = v.get(9).toString();
		 egeneration = v.get(10).toString(); 
		 eoperatingHrs = v.get(11).toString();
		 lullHrs = v.get(12).toString();		 
		 machineAvailability = v.get(13).toString(); 
		 capacityFactor = v.get(14).toString(); 
		 gridAvailability = v.get(15).toString(); 
		 monthWise = v.get(16).toString();	
		 remarks = v.get(17).toString();
		 ebid = v.get(18).toString();	
 		
 %>	
	
	<%if(i == 0){ %>
		<tr class="TableTitle">
			<td vAlign="middle" align="center" width="90%" colspan="9"><%=customerName%></td>
		</tr>
		
		<tr >
			<td vAlign="middle" width="90%" align="center" colspan="9"></td>
		</tr>		

		<tr >
			<td vAlign="middle" class="TableCell1" align="left" width="20%">Location:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=siteName%> - <%=stateName%></td>

			<td vAlign="middle" class="TableCell1" align="left" colspan="2" width="18%">Date:</td>
			<td vAlign="middle" align="left" width="24%" colspan="3"><%=fdate%></td>
		</tr>
		<%} %>		
		
		<%if(!ebid.equals(oldebid)){ %>		 
		   
		<% if(i>1 && tcount>1){
			  tmachineAvailability=tmachineAvailability/tcount;
			  tcapacityFactor=tcapacityFactor/tcount;
			  tgridAvailability=tgridAvailability/tcount;			
			
			%>
		  	<TR class="TableSummaryRow">
		  		 <TD class="TableCell1" align="left"><B>Total</B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(tgeneration)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(toperatingHrs)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(tegeneration)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(teoperatingHrs)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat1.format(tlullHrs)%></B></TD>
		   		 <TD class="TableCell"><B><%=dformat.format(tcapacityFactor)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat.format(tmachineAvailability)%></B></TD>
		  		 <TD class="TableCell"><B><%=dformat.format(tgridAvailability)%></B></TD>		  	
		  	</TR>
		  	<TR><TD COLSPAN="9">&nbsp;</TD></TR>
		  	  	<TR ><TD colspan="9">
		<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
			<TBODY>
				<TR BGCOLOR="#808000">
					<TD colspan="9" align="center"><B>EB Generation</B></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing="0" cellPadding="0" width="100%" border="1">
			<TBODY>
				<TR>
					<TD colspan="9">
					<TABLE cellSpacing="1" cellPadding="2" width="100%" border="1">
						<TBODY>
							<TR>
								<TD colspan="3" align="center" width="16%">Description</TD>
								<TD colspan="2" align="center" width="10%">KWH Export</TD>
								<TD colspan="2" align="center" width="14%">KWH Import</TD>
								<TD colspan="2" align="center" width="11%">Net KWH</TD>
							</TR>
							<%							
							List tranListData = (List) secutils.getEBData(oldebid, tdate, type);
							
							//System.out.println(tranListData.size());
							if (tranListData.size() > 0) {
								for (int j = 0; j < tranListData.size(); j++) {
									Vector vdata = new Vector();
									vdata = (Vector) tranListData.get(j);//									
									int rem1 = 1;
									// rem = j % 2;
									 if (!vdata.get(4).toString().equals("NIL"))
										if (!Remarks.equals(".")) {
											{
										Remarks = (String) vdata.get(4);
					
											}
										}									
										
										%>
					
										<TR>
											<TD colspan="3" align="center"><%=vdata.get(0)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(1)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(2)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(3)%></TD>
										</TR>
					
										<%
								}
							}else
							{
								%>
									<TR>
										<TD colspan="3" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
									</TR>
								<%
							}
							%>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</TD></TR>
		<TR>
			<TD colspan="9" align="center" >Remarks</TD>
		</TR>
		<TR">
			<TD vAlign="middle" align="center" width="90%" colspan="9"><%=remarks%></TD>
		</TR>
		<TR>
			<TD vAlign="middle" align="center" width="90%" colspan="9"><%=Remarks%></TD>
		</TR>
		  	
		<tr>
			<td vAlign="middle"  align="center" width="90%" colspan="9"><%=customerName%></td>
		</tr>
		
		<tr>
			<td vAlign="middle" width="90%" align="center" colspan="9"></td>
		</tr>
		

		<tr>
			<td vAlign="middle" align="left" width="20%">Location:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=siteName%> - <%=stateName%></td>

			<td vAlign="middle" align="left" colspan="2" width="18%">Date:</td>
			<td vAlign="middle" align="left" width="24%" colspan="3"><%=fdate%></td>
		</tr>	
		
		  	<%		  		  
		  		} 
		  	%>		
		
		<tr class="TableSummaryRow">
			<td vAlign="middle" width="90%" align="left" colspan="9"></td>
		</tr>
		<tr class="TableSummaryRow">
			<td vAlign="middle" class="TableCell1" align="left" width="18%">Machines:</td>
			<td vAlign="middle" align="left" width="24%" colspan="3"><%=wecName%> </td>
			<td vAlign="middle" class="TableCell1" align="left" colspan="2" width="20%">Machine Capacity:</td>
			<td vAlign="middle" align="left" width="38%" colspan="3"><%=wecType%> X <%=wecCapacity%> KW</td>
		</tr>

		<TR BGCOLOR="#808000">
			<TD colspan="9" align="center"><B>Day Wise Generation</B></TD>
		</TR>

				<TR class="TableTitleRow">
						<TD width="14.28%">WEC Name</TD>
						<TD width="14.28%">Scada Generation(KWH)</TD>
						<TD width="14.28%">Scada Operating Hours</TD>
						<TD width="14.28%">Ecare Generation(KWH)</TD>
						<TD width="14.28%">Ecare Operating Hours</TD>
						<TD width="14.28%">Lull Hours</TD>
						<TD width="14.28%">Capacity Factor(%)</TD>
						<TD width="14.28%">Machine Availability(%)</TD>
						<% if(diff<0){ %>
						<TD width="14.28%">Grid Availability(%)</TD>
						<%} else{ %>
						<TD width="14.28%">Grid  Availability(%) Internal</TD>
						<TD width="14.28%">Grid  Availability(%) External</TD>
						<%} %>

					</TR>
		<%			 tcount=0; 
					 tgeneration=0;
					 toperatingHrs=0;
					 tlullHrs=0;
					 tmachineAvailability=0;
					 tcapacityFactor=0;
					 tgridAvailability=0;			
		
		} %>
		
	
		
		<TR>
		   <TD class="TableCell1" align="left"><%=wecName%></TD>
		   <TD class="TableCell"><%=generation%></TD>
		   <TD class="TableCell"><%=operatingHrs%></TD>
		   <TD class="TableCell"><%=egeneration%></TD>
		   <TD class="TableCell"><%=eoperatingHrs%></TD>
		   <TD class="TableCell"><%=lullHrs%></TD>
		   <TD class="TableCell"><%=capacityFactor%></TD>
		   <TD class="TableCell"><%=machineAvailability%></TD>
		   <TD class="TableCell"><%=gridAvailability%></TD>
		</TR>
      
        <%oldebid=ebid;
		     tgeneration+=Double.parseDouble(generation);
			 toperatingHrs+=Double.parseDouble(operatingHrs);
			 tegeneration+=Double.parseDouble(egeneration);
			 teoperatingHrs+=Double.parseDouble(eoperatingHrs);
			 tlullHrs+=Double.parseDouble(lullHrs);
			 tmachineAvailability+=Double.parseDouble(machineAvailability);
			 tcapacityFactor+=Double.parseDouble(capacityFactor);
			 tgridAvailability+=Double.parseDouble(gridAvailability);	
		   tcount++;	 
		  } %>
		  <% if(i>1){ %>
		  <%		  
		  
		  tmachineAvailability=tmachineAvailability/tcount;
		  tcapacityFactor=tcapacityFactor/tcount;
		  tgridAvailability=tgridAvailability/tcount;
		  
		  %>
		  	<TR class="TableSummaryRow">
		  		 <TD align="left">Total</TD>
		  		 <TD ><B><%=dformat1.format(tgeneration)%></B></TD>
		  		 <TD ><B><%=dformat1.format(toperatingHrs)%></B></TD>
		  		 <TD ><B><%=dformat1.format(tegeneration)%></B></TD>
		  		 <TD ><B><%=dformat1.format(teoperatingHrs)%></B></TD>
		  		 <TD ><B><%=dformat1.format(tlullHrs)%></B></TD>
		   		 <TD ><B><%=dformat.format(tcapacityFactor)%></B></TD>
		  		 <TD ><B><%=dformat.format(tmachineAvailability)%></B></TD>
		  		 <TD ><B><%=dformat.format(tgridAvailability)%></B></TD>		  	
		  	</TR>	
		  	<TR><TD COLSPAN="9">&nbsp;</TD></TR>
		  		  	<TR ><TD colspan="9">
		<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0">
			<TBODY>
				<TR BGCOLOR="#808000">
					<TD colspan="9" align="center"><B>EB Generation</B></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing="0" cellPadding="0" width="100%" border="1">
			<TBODY>
				<TR>
					<TD colspan="9">
					<TABLE cellSpacing="1" cellPadding="2" width="100%" border="1">
						<TBODY>
							<TR>
								<TD  colspan="3" align="center" width="16%">Description</TD>
								<TD  colspan="2" align="center" width="10%">KWH Export</TD>
								<TD  colspan="2" align="center" width="14%">KWH Import</TD>
								<TD  colspan="2" align="center" width="11%">Net KWH</TD>
							</TR>
							<%							
							List tranListData = (List) secutils.getEBData(oldebid, tdate, type);
							
							//System.out.println(tranListData.size());
							if (tranListData.size() > 0) {
								for (int j = 0; j < tranListData.size(); j++) {
									Vector vdata = new Vector();
									vdata = (Vector) tranListData.get(j);//									
									int rem1 = 1;
									// rem = j % 2;
									 if (!vdata.get(4).toString().equals("NIL"))
										if (!Remarks.equals(".")) {
											{
										Remarks = (String) vdata.get(4);
					
											}
										}									
										
										%>
					
										<TR>
											<TD colspan="3" align="center"><%=vdata.get(0)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(1)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(2)%></TD>
											<TD colspan="2" align="center"><%=vdata.get(3)%></TD>
										</TR>
					
										<%
								}
							}else
							{
								%>
									<TR>
										<TD colspan="3" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
										<TD colspan="2" align="center">0</TD>
									</TR>
								<%
							}
							%>
						</TBODY>
					</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		</TD></TR>
		<TR>
			<TD colspan="9" align="center" >Remarks</TD>
		</TR>
		<TR>
			<TD vAlign="middle" align="center" width="90%" colspan="9"><%=remarks%></TD>
		</TR>
		<TR>
			<TD vAlign="middle" align="center" width="90%" colspan="9"><%=Remarks%></TD>
		</TR>	  	
		  	<%} %>
	</TBODY>
</TABLE>

<% } %>


</CENTER>
</BODY>
</HTML>
