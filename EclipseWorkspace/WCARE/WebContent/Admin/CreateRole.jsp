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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
String rn = "";
String de = "";
String ri = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("RoleNametxt").toString();
	de = dynabean.getProperty("RoleDescriptiontxt").toString();
	ri = dynabean.getProperty("RoleIdtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
%>
<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getrolemaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("rolemaster")[0];
	var items = cart.getElementsByTagName("rolecode");	
		var divdetails = document.getElementById("roledetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Role</th><th class='detailsheading' width='230'>Description</th>"
		str +="<th class='detailsheading' width='40'>E</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("roleid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("rolename")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("desc")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("roleid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].RoleIdtxt.value = "";
	   	 	}   	 	
		}
		str += "</table>"
		divdetails.innerHTML = str;
}
function findDetails(roleid)
{
	 var req = newXMLHttpRequest();
     var ApplicationId = roleid;
	 req.onreadystatechange = getReadyStateHandler(req, showRoleMaster);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getrolemasterbyid&AppId="+ApplicationId);
}
function showRoleMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("rolemaster")[0];
	var items = cart.getElementsByTagName("rolecode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("name")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].RoleNametxt.value = item.getElementsByTagName("name")[0].firstChild.nodeValue;
   	 		document.forms[0].RoleDescriptiontxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue;    	 		
   	 		document.forms[0].RoleIdtxt.value = item.getElementsByTagName("roleid")[0].firstChild.nodeValue;
   	 	}
   	 	else{
   	 		document.forms[0].RoleNametxt.value = "";
   	 		document.forms[0].RoleDescriptiontxt.value = "";  
   	 		document.forms[0].RoleIdtxt.value = "";  	 		
   	 	} 			
 	}
}

</script>
</head>
<body onLoad="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/rolemaster.do" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Manage Role</th>
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
			<td id="t_street_address" width="180px">&nbsp;Role&nbsp;Name:</td>
			<td class="bgcolor" width="180px"><input type="text" id="RoleNametxt" name="RoleNametxt" size="25" value="<%=rn%>" class="ctrl" maxlength="30" /></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="RoleDescriptiontxt" name="RoleDescriptiontxt" class="ctrl" ><%=de%></textarea></td>
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
			<input type="hidden" id="RoleIdtxt" name="RoleIdtxt" value="<%=ri %>" />
			<input type="hidden" name="Admin_Input_Type" value="RoleMaster" />	
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