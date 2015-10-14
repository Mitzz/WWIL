<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@page import="java.sql.*" %>
<%@page import="com.enercon.global.utils.JDBCUtils"%>
<%@page import="com.enercon.admin.dao.AdminSQLC"%>
<%@page import="java.text.*"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
	<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
	
	<title>Block/Unblock Todays Scheduled E-Mail</title>
	<script type="text/javascript">
		function schedulerDateFinder(jj){
			var tableCell = jj.parentNode;
			var tableRow = tableCell.parentNode;
			var tableRowIDAttributeValue = tableRow.getAttribute("id");
			var ss = document.getElementById("totalblockmail");
			ss.setAttribute("value", tableRowIDAttributeValue);
		}
	</script>
	
	</head>
	<body>
		<form name="blockSceduler" action="<%=request.getContextPath()%>/BlockScheduler" name="BlockScheduler" id="BlockScheduler" method="post">
			<table border="1" cellpadding="0" cellspacing="0" width="400" align="center" class=SectionTitle1 >
				<tr bgcolor="#006633" >
					<td height="20" colspan="3">
				    	<div align="center"><font color="#ffffff"><B>Block/Unblock Todays's Scheduler</B></font></div>
					</td>
				</tr>
				<tr class=TableTitleRow>
				    <td colspan="3" height="15"></td>
				</tr>
				<tr class=TableSummaryRow>
				    <td bgcolor="#D0E7CB" width="282" colspan="2">Please click to block the scheduler</td>
				    <td bgcolor="#D0E7CB" width="77">
				    	<div align="center">
				    		<strong>
				      			<input type="submit" name="blockSchedule"  value="&nbsp;&nbsp;Block&nbsp;"/>
				    		</strong>
						</div>
					</td>				    
				</tr>
				<tr class=TableTitleRow>
				    <td colspan="3" height="15"></td>
				</tr>
				<tr> 
					<td colspan="3">
						<table>
							<%
JDBCUtils conmanager = new JDBCUtils();
try {
	Connection conn = conmanager.getConnection();
	// Statement st = conn.createStatement();
	Format dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	// List tranList = new ArrayList();
								
	String sqlQuery1 = AdminSQLC.CHECK_BLOCKED_MAIL;
	/* "SELECT D_SEND_DATE  " +
	"FROM TBL_SEND_MAIL WHERE S_SEND_TYPE='blockedScheduledMail'"; */
	PreparedStatement prepStmt = conn.prepareStatement(sqlQuery1);
	ResultSet rs = prepStmt.executeQuery();
	int i=0;
	String cls = "TableRow1";
	while (rs.next()) {
		cls = "TableRow1";
		//Vector tranVector = new Vector();	
		//tranVector.add(rs.getString("D_SEND_DATE"));
		i=i+1;
		if(i%2==0)
			cls = "TableRow2";
							%>
							<tr class="<%=cls %>" id="<%=i%>">
								<td width="26"><%=i%></td>
								<td width="230">Mail has been blocked on <%=dateFormat.format(rs.getDate("D_SEND_DATE"))%></td>
								<td width="70" align="right"> 
							<%
		String schedulerDate = dateFormat.format(rs.getDate("D_SEND_DATE"));
							%>
									<input type="submit" name="unblockSchedule"  value="Resend" onclick="schedulerDateFinder(this);"/>
									<input type="hidden" name="resendDate<%=i%>" value="<%=schedulerDate%>">
									<input type="hidden" id="totalblockmail" name="totalblockmail" value="<%=i%>">
								</td>
								<td width="52"></td>
							</tr>
							<%
									
		//	tranList.add(i, tranVector);
		//	i++;
		//out.println("Mail has been blocked for the date: "+tranList.toString());
	}
}
catch(Exception e){
	e.printStackTrace();
}
							%>
						</table>
						<%
String str=(String)session.getAttribute("msgBlockScheduler");
						%>
						<%
if(str != null){
						%>
						<%=str%>
						<%
}
						%>
						<%
session.removeAttribute("msgBlockScheduler");
						%>
					</td>
				</tr>
			  	
			</table>
		</form>
	</body>
</html>