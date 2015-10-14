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

<%
String rid = "";
String empcode = "";
String empname = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	rid = dynabean.getProperty("RoleNametxt").toString();
	empcode = dynabean.getProperty("UserId").toString();
	empname = dynabean.getProperty("UserNametxt").toString();	
	//String str1=(String)session.getAttribute("SubmitMessage");
	//if(str1 != null && str1.equals("Success")){
	//	newtrid = "";	
	//}else{
	//	newtrid = dynabean.getProperty("Transactiontxt").toString();
	//}
	//session.setAttribute("SubmitMessage","");	
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",rid);
%>
<script type="text/javascript">

function findEmployee() 
{
	 var req = newXMLHttpRequest();
	 var ApplicationId = "";
		 if (document.forms[0].UserId.value != "") {
		 	 ApplicationId = document.forms[0].UserId.value;
		 }
	 if (ApplicationId != ""){
		 req.onreadystatechange = getReadyStateHandler(req, showEmployee);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=findemployee&AppId="+ApplicationId);
	}
}
function showEmployee(dataXml)
{
	var cart = dataXml.getElementsByTagName("customermaster")[0];
	var items = cart.getElementsByTagName("customercode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("customerid")[0].firstChild;
     	if (nname != null){
			document.forms[0].UserNametxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue;
		}
		else{
			document.forms[0].UserNametxt.value = "Employee Not Found";
			document.forms[0].UserId.value = "";
		}
	}
}
</script>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/userrolemaster.do" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Role Transaction Assign Master</th>
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
			<td id="t_street_address" width="120px">&nbsp;Role&nbsp;Name:</td>
			<td class="bgcolor" width="240px">
				<select size="1" id="RoleNametxt" name="RoleNametxt" class="ctrl">
		            <option value="">--Make a Selection--</option>
		            <%=rolename%>			            
		        </select>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Customer/User:</td>
			<td class="bgcolor">
				<input type="text" id="UserId" value="<%=empcode %>" name="UserId" size="10" class="ctrl" maxlength="10" onblur="findEmployee()" />
		        <input type="text" id="UserNametxt" value="<%=empname %>" name="UserNametxt" size="30" class="ctrl" maxlength="30" onfocus="Submitcmd.focus()" />
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
			<input type="hidden" name="Admin_Input_Type" value="UserRoleMaster" />	
			<input name="Submit" type="submit" class="btnform" value="Submit"/>
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
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="400"><tbody>
			<tr>
				<td >
					<div id="roledetails">
					
					</div>	
				</td>
			</tr>
			</tbody>
		</table>	
	</td>
</tr>
</table>
</div>
</body>
</html>