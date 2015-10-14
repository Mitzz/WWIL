<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.Date"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
		<title>Insert title here</title>
	</head>
	<body>
		<form name="blockSceduler" action="<%=request.getContextPath()%>/UpdateWECData" name="UpdateWECData" id="UpdateWECData" method="post">
		<table cellpadding="0" cellspacing="0" width="400" align="center" class=SectionTitle1>
			<tr bgcolor="#006633"><td colspan="2" align="center"><B>Update Wec Data</B></td></tr>
			<tr bgcolor="#006633"><td colspan="2" >&nbsp;</td></tr>
			<% 	
				com.enercon.global.utils.JDBCUtils conmanager = new com.enercon.global.utils.JDBCUtils();
				try {
					java.sql.Connection conn = conmanager.getConnection();
					
					String sqlQuery1 = com.enercon.admin.dao.AdminSQLC.CHK_UPDATE_WEC_DATA;
					java.sql.PreparedStatement prepStmt = conn.prepareStatement(sqlQuery1);
					java.sql.ResultSet rs = prepStmt.executeQuery();
				int i=0;
				String cls = "TableRow1";				
				java.text.DateFormat dateFormatter  = new java.text.SimpleDateFormat("dd/MM/yyyy");
				while(rs.next())
				{		
					i++;
					if(i%2==0)
						cls = "TableRow2";
				
			%>
			<tr class="<%=cls %>"><td width="80%"><font color="black"><%=i %>. Click here to update the feeder data for <%=dateFormatter.format(new Date())%></font></td><td align="center"><input type="Submit" name="Submit" value="Update"/></td></tr>
			<%} }catch(Exception e){}%>
			<tr bgcolor="#006633"><td colspan="2" >&nbsp;</td></tr>
		</table>
	</body>
</html>