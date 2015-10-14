<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utils.CallSchedulerForMissingScadaData"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility	
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<HTML>
<HEAD>    
<%
	if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%> 
<title>Missing Scada Data Report Details in ECARE</title>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<style type="text/css" media="print">
.printbutton {
  visibility: hidden;
  display: none;
}
</style>
<%! public boolean debug = false; %>
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
//	String userid = session.getAttribute("loginID").toString();
//	String Customeridtxt = "";
%>

</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<CENTER>
<%
	String stateName = request.getParameter("Statetxt");
	String siteName = request.getParameter("SiteNametxt");
	String areaName = request.getParameter("Areatxt");
	String reportDate = request.getParameter("ReportDatetxt");	
%>
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date date= dateFormat.parse(reportDate);

String prevDate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
//System.out.println(reportDate);
//System.out.println(prevDate);
%>

<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
	<TBODY>
		<tr>
			<TD align="right" colspan="6">		
				<input type="button" value="Print" onClick="window.print()" class="printbutton">
				<input align="right" type="button" class="printbutton" value="Excel" onClick=location.href="ExportMissingScadaDataReport.jsp?Statetxt=<%=stateName%>&Areatxt=<%=areaName%>&SiteNamexxt=<%=siteName%>&ReportDatetxt=<%=reportDate%>">
				<input align="right" type="button" class="printbutton" value="PDF" onClick=location.href="ExportMissingScadaDataReportToPDF.jsp?Statetxt=<%=stateName%>&Areatxt=<%=areaName%>&SiteNamexxt=<%=siteName%>&ReportDatetxt=<%=reportDate%>">
			</td>
		</TR>
		<TR>
			<td colspan="6"></td>
		</tr>
	</TBODY>
</TABLE>
<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="90%" border=0>
      <tr>
			<td colspan="6" height="5"></td>
	  </tr>
	<tr>
		<td colspan="2" align="left"><input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="MissingScadaDataDetails.jsp?Statetxt=<%=stateName%>&Areatxt=<%=areaName%>&SiteNametxt=<%=siteName%>&ReportDatetxt=<%=prevDate%>"></TD>
		<td colspan="4" align="center"></td>
		<td colspan="2"  align="right"><input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="MissingScadaDataDetails.jsp?Statetxt=<%=stateName%>&Areatxt=<%=areaName%>&SiteNametxt=<%=siteName%>&ReportDatetxt=<%=nextDate%>"></TD>
   </TR>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="90%" border=0>
	<TBODY>
		<TR>
			<TD class=SectionTitle colspan="6" align="center" noWrap>Missing Scada Data Report Details in ECARE (As on <%=reportDate %>)</TD>
		</TR>
	</TBODY>
</TABLE>
<%
	String wecName = "", plantNo = "", locationNo = "", technicalNo = "", cls="";	
	List missingScadaDataList_1 = new ArrayList();
	CustomerUtil custUtils = new CustomerUtil();
	missingScadaDataList_1 = (List) custUtils.getMissingScadaDataReport1(stateName, areaName, siteName, reportDate);
	if(debug){
		System.out.println("------------------1--------------------------");
		GlobalUtils.displayVectorMember(missingScadaDataList_1);
	}
	if(missingScadaDataList_1.size()>0){
	%>	
	<table cellSpacing=0 cellPadding=0 width="90%" border=1>
		<tr class=TableTitleRow>
			<td width="5%">Sr No.</td>
			<td width="15%">State Name</td>
			<td width="15%">Area Name</td>
			<td width="15%">Site Name</td>
			<td width="10%">Location No</td>
			<td width="10%">Plant No</td>
			<td width="15%">WEC Name</td>
			<td width="15%">Technical No</td>
		</tr>	
	<%
	for (int i = 0; i < missingScadaDataList_1.size(); i++) {
		Vector vector_2_1 = new Vector();
		vector_2_1 = (Vector)missingScadaDataList_1.get(i);
		if(debug){
			System.out.println("---------1---------2--------------------------");
			GlobalUtils.displayVectorMember(vector_2_1);
		}
		stateName = vector_2_1.get(0).toString();
		areaName = vector_2_1.get(1).toString();
		siteName = vector_2_1.get(2).toString();
		locationNo = vector_2_1.get(3).toString();
		plantNo = vector_2_1.get(4).toString();
		wecName = vector_2_1.get(5).toString();
		technicalNo = vector_2_1.get(6).toString();		
		
		int rem=1;		
		rem = i%2;
		
		if(rem==0)
        	cls="TableRow2";
        else
        	cls="TableRow1";
		%>		
			<tr class=<%=cls %>>
				<td><%=i+1 %></td>
				<td><%=stateName %></td>
				<td><%=areaName %></td>
				<td><%=siteName %></td>
				<td align="center"><%=locationNo %></td>
				<td align="center"><%=plantNo %></td>
				<td><%=wecName %></td>
				<td align="center"><%=technicalNo %></td>
			</tr>		
		<%		
		}
	%>
	</table>
	<%
	}
	missingScadaDataList_1.clear();
%>
<P></P>
</CENTER>
</BODY>
</HTML>
