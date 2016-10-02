<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utility.MethodClass"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<HTML>
<HEAD>
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>

<TITLE>DGR DashBoard Report</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">

<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	// String userid = session.getAttribute("loginID").toString();
	// String Customeridtxt = "";
%>
<%!
	boolean debug = false;
%>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0" marginwidth="0">
<CENTER>
<%
	
	String state=request.getParameter("stateid");
    //System.out.println("state"+state);
	
	String wec=request.getParameter("wectype");
	String fdate=request.getParameter("fdate");
	String tdate=request.getParameter("tdate");
	
	String sname = request.getParameter("sname");
	sname=sname.replace(" ",",");
	//System.out.println("sname"+sname);
	String siteid = request.getParameter("siteid");
	String ebid = request.getParameter("ebid");
	String wecid = request.getParameter("wecid");
	if(debug){
		System.out.println("State Id:" + state);
		System.out.println("WECType:" + wec);
		System.out.println("From Date:" + fdate);
		System.out.println("To Date:" + tdate);
		System.out.println("Sname:" + sname);
		System.out.println("Site Id:" + siteid); 
		System.out.println("Eb Id:" + ebid);
		System.out.println("WEC Id:" + wecid);
	}
	//System.out.println("siteid"+siteid);
	//System.out.println("wec"+wec);
%>


<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<%
			if(!wecid.equals("ALL")){%>
				<%-- One Particular Wec is selected --%>
				<td align="center" colspan="2"> 
					<!-- System.out.println("Particular WEC"); -->
					<input  align="right" type="button" value="Export To Excel" onClick=location.href="Change/WecIDMPRChange.jsp?wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>&ebid=<%=ebid%>&wecid=<%=wecid%>">
					
				</td>
			
			<% }else if(wecid.equals("ALL") && !ebid.equals("ALL")){%>
			<%-- One Particular Eb is selected with all Wec is selected --%>
				<!-- System.out.println("Particular EB"); -->
				<td align="center" colspan="2"> <input  align="right" type="button" value="Export To Excel" onClick=location.href="Change/EbIdMPRChange.jsp?wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>&ebid=<%=ebid%>&wecid=<%=wecid%>"></td>
					
			<% }else if(state.equals("ALL")){%>
				<%-- Only Wec Type Select --%>
				<!-- System.out.println("All State"); -->
				<td align="center" colspan="2"> <input  align="right" type="button" value="Export To Excel" onClick=location.href="Change/WecTypeMPRChange.jsp?wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>"></td>
					
			<% }else if(siteid.equals("ALL")){%>
				<%-- Only State Select --%>
				<%-- State and WecType --%>
				<td align="center" colspan="2">
					 <!-- System.out.println("All Site"); -->
					 <input  align="right" type="button" value="Export To Excel" onClick=location.href="Change/StateMPRChange.jsp?stateid=<%=state%>&wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>&sname=<%=sname%>&ebid=<%=ebid%>&wecid=<%=wecid%>">
					 
				</td>
			<%} else{%>
				<!-- System.out.println(); -->
				<%-- Site and State Select --%>
				<%-- Site ,State and Wec Type Select --%>
				<td align="center" colspan="2"> 
					<input  align="right" type="button" value="Export To Excel" onClick=location.href="SiteMPR.jsp?stateid=<%=state%>&wectype=<%=wec%>&fdate=<%=fdate%>&tdate=<%=tdate%>&sname=<%=sname%>&siteid=<%=siteid%>&ebid=<%=ebid%>&wecid=<%=wecid%>">
					
				</td>
			<%}%>
		</TR>
		<TR>
			<td colspan="6"></td>
		</tr>

	</TBODY>
</TABLE>

</body>
</html>