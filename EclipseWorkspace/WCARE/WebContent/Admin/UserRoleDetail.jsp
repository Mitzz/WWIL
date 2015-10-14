<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/qna1.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>

<%
String rid = "";

//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	rid = dynabean.getProperty("RoleNametxt").toString();
	//empcode = dynabean.getProperty("EmpCodetxt").toString();
	//empname = dynabean.getProperty("EmpNametxt").toString();	
	//String str1=(String)application.getAttribute("SubmitMessage");
	//if(str1 != null && str1.equals("Success")){
	//	newtrid = "";	
	//}else{
	//	newtrid = dynabean.getProperty("Transactiontxt").toString();
	//}
	//application.setAttribute("SubmitMessage","");	
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",rid);
%>


</head>
<body>
<div align="center">
 <table border="0" cellpadding="0" cellspacing="0" class="border2 fntAr f11 fntGray1"  background="<%=request.getContextPath()%>/resources/images/bg21.gif" >
  <tr>
   <td rowspan="3" bgcolor="#94A664"></td>
   <td bgcolor="#94A664"></td>
   <td rowspan="3" bgcolor="#94A664"></td>
  </tr>
  <tr>
   <td bgcolor="#94A664" align="center"><span class="textheadline2">Role Transaction Assign Master</span></td>
  </tr>
  <tr>
   <td bgcolor="#94A664"></td>
  </tr>
  <tr>
   <td ></td>
   <td >
   		<table width="450" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<html:errors />
					<%String str=(String)application.getAttribute("msg");%>
					<%if(str != null){%>
					<%=str%>
					<%}%>
					<%application.setAttribute("msg","");%>
   				</td>
   			</tr>
   		</table>
   		<!-- <form action="ProcessFileUpload.jsp" method="post" enctype="multipart/form-data" > -->
		<form action="<%=request.getContextPath()%>/Admin/ReportRoleDetail.jsp" method="post" >
			<table width="550" cellspacing="0" cellpadding="0">
				<tr class="tabtextnormal" align="left">
					<td width="200" height="30" align="left">Select Role</td>
					<td width="8">:</td>
					<td width="300">
						<select size="1" id="RoleNametxt" name="RoleNametxt" class="tabtextnormal">
				            <option value="ALL">--ALL--</option>
				            <%=rolename%>			            
				        </select>				        
					</td>
				</tr>		
																
				<tr class="tabtextnormal">
					<td height="20" align="center" colspan="3">
						<input type="hidden" name="Admin_Input_Type" value="UserRoleMaster" />
						<input type="Submit" name="Submitcmd" class="buttonstyle" value="View Detail" />
						
					</td>
				</tr>										
			</table>
		</form>		
		<div id="userroledetails"></div>		
   </td>
   <td bgcolor="#94A664"></td>
  </tr>
  <tr>
   <td bgcolor="#94A664"></td>
   <td bgcolor="#94A664"></td>
   <td bgcolor="#94A664"></td>
  </tr>
</table>
</div>
</body>
</html>