<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="java.text.*" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type="text/javascript">
function confirmation()
{   
	var answer = confirm("Are you sure you want to Submit?")
	if (answer)
	{
		return true;
	}
	else
	{
		return false;
	}	
}
</script>
<%
String et = "";
String st = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	et = dynabean.getProperty("Exceltxt").toString();
	st = dynabean.getProperty("Sheettxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date cdate = new java.util.Date();
java.util.Date ldate = format.parse("30/06/2009");
%>
</head>
<body>
<%if (ldate.after(cdate)){ %>
<script type="text/javascript">
	alert("Upload Excel format has been changed w.e.f. 08-JUNE-2009! \n Kindly check Excel file sample and Change excel file according");
</script>
<%} %>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="ProcessFileUpload.jsp" method="post" enctype="multipart/form-data">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Upload EB Data</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;File:</td>
			<td class="bgcolor">
				<input type="hidden" name="filestype" value="xls" />
				<input type="file" name="Exceltxt" value="<%=et %>" size="40" class="ctrl" />
			</td>
		</tr>				
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Sheet&nbsp;Name(Exact):</td>
			<td class="bgcolor">
				<input type="text" name="Sheettxt" size="20" value="<%=st %>" class="ctrl" maxlength="20" />
			</td>
		</tr>		
		<tr class="bgcolor"> 
			<td class="bgcolor" colspan="2" align="center">
				<a href="<%=request.getContextPath()%>/Admin/ExcelFiles/EBData.htm" target="_blank"><span class="linkbluebold">Excel File Sample</span></a>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>
			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="hidden" name="Admin_Input_Type" value="UploadEBData" />
			<input type="Submit" name="UploadCmd" class="btnform" value="Submit" onClick="return confirmation();"/>
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>	
</td>		
</tr>
</table>
</div>
</body>
</html>