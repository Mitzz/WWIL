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
String activetxt = "";
String Customeridtxt1 = "";
String loginmasteridtxt = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	activetxt= dynabean.getProperty("activetxt").toString();
	Customeridtxt1=dynabean.getProperty("Customeridtxt1")==null?"":dynabean.getProperty("Customeridtxt1").toString();
	loginmasteridtxt=dynabean.getProperty("loginmasteridtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt1);
%>
<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getEmailActive&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("rolemaster")[0];
	var items = cart.getElementsByTagName("rolecode");	
		var divdetails = document.getElementById("roledetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='230'>Customer</th><th class='detailsheading' width='100'>Status</th>"
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
	   	 		document.forms[0].loginmasteridtxt.value = "";
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
	 req.send("Admin_Input_Type=getEmailActivatebyid&AppId="+ApplicationId);
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
   	 		document.forms[0].Customeridtxt1.value = item.getElementsByTagName("name")[0].firstChild.nodeValue;
   	 		document.forms[0].activetxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue;    	 		
   	 		document.forms[0].loginmasteridtxt.value = item.getElementsByTagName("roleid")[0].firstChild.nodeValue;
   	 	}
   	 	else{
   	 		document.forms[0].Customeridtxt1.selectedIndex = 0;
   	 		document.forms[0].activetxt.selectedIndex=0;
   	 		document.forms[0].loginmasteridtxt.value = "";  	 		
   	 	} 			
 	}
}
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
</head>
<body onLoad="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/emailactivate.do" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">DGR Via Email</th>
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
		<tr bgcolor="#ffffff">
			<td id="t_company">Customer:</td>
			<td valign="top"><select name="Customeridtxt1" id="Customeridtxt1" class="ctrl">
              <option value="" selected="selected">-- select --</option>
               <%=custid %>
            </select></td>
		</tr>
			<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">Activate:</td>
			<td><select name="activetxt" id="activetxt" class="ctrl">
              <option value="" selected="selected">-- select --</option>
			 
			 
			  <%if (activetxt.equals("1")){ %>
				  <OPTION value="1" selected>Yes</OPTION>			               
			       <%}else{ %>
			        <option value="1">Yes</option>
			       <%} %>
			       <% if(activetxt.equals("0")){ %>
			          	  <OPTION value="0">No</OPTION>
		       <%}else{ %>	
		        <option value="0">No</option>
		       <%} %>		  
			  </select></td>
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
			<input type="hidden" name="loginmasteridtxt" value="<%=loginmasteridtxt %>" />
			<input type="hidden" name="Admin_Input_Type" value="DGRViaEmail" />	
			<input name="Submit" type="submit" class="btnform" value="Submit" onClick="return confirmation();"/>
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