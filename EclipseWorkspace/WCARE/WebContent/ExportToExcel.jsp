<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
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
<script type="text/javascript">
function myFunction(ur,wty,wna,eb,wid,rd,state,cname,type)
{

var url=ur+"?wectype="+wty+ "&wecname="+wna+ "&ebid="+eb+ "&wecid="+wid+ "&rd="+rd+ "&state="+state + "&cname="+cname +"&type="+type;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0"
	marginwidth="0">
<CENTER>
<%
	String custid = request.getParameter("id");
	
	String rdate = request.getParameter("rd");
	String type = request.getParameter("type");
	
%>


<TABLE cellSpacing=0 class=SectionTitle1 cellPadding=0 width="100%" border=0>
	<TBODY>
		<tr>
		

			<td align="center" colspan="2"> <input  align="right" type="button" value="Export To Excel" onClick=location.href="OverAllCustomerReport.jsp?id=<%=custid%>&type=<%=type%>&rd=<%=rdate%>"></td>
		</TR>
		<TR>
			<td colspan="6"></td>
		</tr>

	</TBODY>
</TABLE>

</body>
</html>