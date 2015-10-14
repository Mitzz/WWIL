<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="com.enercon.projects.dao.*" %>
<%@ page import="com.enercon.admin.dao.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project Master</title>
<meta http-equiv="Content-Language" content="en-us" /> 

   <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
     

</head>
<body>
<div align="center">
<FORM  ENCTYPE="multipart/form-data" ACTION="upload_csv.jsp?UploadType=MaterialTrans" METHOD=POST>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="614">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="70">Project Material Transaction</th>
	<td width="363"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="8">&nbsp;</td>
	<td class="newhead4" width="11"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3" width="593">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="595">
		<tbody>
		
		<tr class="bgcolor">
			<td id="t_company" width="204">&nbsp;Choose the file To Upload:</td>
			<td width="378" colspan="3" valign="top">
            <INPUT NAME="file" TYPE="file" size="20"></td>
			</tr>
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="11">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633" width="604">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		
		
		
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="200" colspan="2">
		<input type="hidden" name="Admin_Input_Type" value="ProjectMaster" />
			
		<input type="hidden" name="StateIdtxt"  />
		<input name="Submit" id="Submit" value="Send File" class="btnform" type="Submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>









  
</body>
</html>