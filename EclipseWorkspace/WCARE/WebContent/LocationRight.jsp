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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<%
String empcode = "";
String empname = "";
String lid = "";
String st = "";
String si = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	empcode = dynabean.getProperty("UserId") == null ? "" : dynabean.getProperty("UserId").toString();	
	empname = dynabean.getProperty("UserNametxt") == null ? "" : dynabean.getProperty("UserNametxt").toString();
	st = dynabean.getProperty("StateIdtxt") == null ? "" : dynabean.getProperty("StateIdtxt").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		si = "";
		lid = "";
	}else{
		si = dynabean.getProperty("SiteIdtxt") == null ? "" : dynabean.getProperty("SiteIdtxt").toString();
		lid = dynabean.getProperty("rightsid") == null ? "" : dynabean.getProperty("rightsid").toString();
	}
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String sState = AdminUtil.fillMaster("TBL_STATE_MASTER",st);
//String sSite = AdminUtil.fillWhereMaster("TBL_STATE_MASTER",si,st);
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
		 req.onreadystatechange = getReadyStateHandler(req, showEmployee, "", "");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=findemployee&AppId="+ApplicationId);
	}
}
function showEmployee(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("customermaster")[0];
	var items = cart.getElementsByTagName("customercode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("customerid")[0].firstChild;
     	if (nname != null){
			document.forms[0].UserNametxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue;
	   	 	findApplication();
		}
		else{
			document.forms[0].UserNametxt.value = "Employee Not Found";
			document.forms[0].UserId.value = "";
		}
	}
}
function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].UserId.value;
     var ApplicationId = list;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getLocationRight&AppId="+ApplicationId);
}
function showAppDetails(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("lright")[0];
	var items = cart.getElementsByTagName("loc");	
		var divdetails = document.getElementById("rightsdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='200'>State</th>"
		str +="<th class='detailsheading' width='200'>Site</th>"
		str +="<th class='detailsheading' width='50'>E | D</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("rcode")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
	     		str+="<td align=center>" + item.getElementsByTagName("state")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align=center>" + item.getElementsByTagName("site")[0].firstChild.nodeValue + "</td>"
	     		str+="<td><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("rcode")[0].firstChild.nodeValue + "')>"
	     		str+="&nbsp;&nbsp;&nbsp;<img src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to delete the record'"
	     		str+="onClick=confirmation('" + item.getElementsByTagName("rcode")[0].firstChild.nodeValue + "') ></td></tr>"
	   	 		document.forms[0].rightsid.value = "";   	 		
	   	 	}   	 	
		}
		str += "</tbody></table>"
		divdetails.innerHTML = str;  
}

function confirmation(list) {
	var answer = confirm("Are you sure you want to Delete?")
	if (answer){
		var req = newXMLHttpRequest();
	 	req.onreadystatechange = getReadyStateHandler(req, delreturn);
	 	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 	req.send("Admin_Input_Type=delocationrights&AppId="+list);
	}	
}
function delreturn(dataXml){
	getSite();
	findApplication();
}

function findDetails(list) 
{
     var req = newXMLHttpRequest();
	 req.onreadystatechange = getReadyStateHandler(req, showDetails,"","");
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getrightdetails&AppId="+list);
}
function showDetails(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("lright")[0];
	var items = cart.getElementsByTagName("loc");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("rcode")[0].firstChild;     	
     	if (nname != null){
   	 		document.forms[0].StateIdtxt.value = item.getElementsByTagName("statecode")[0].firstChild.nodeValue;
			getSite();
   	 		alert("Data Retreived!");
	 		document.forms[0].SiteIdtxt.value = item.getElementsByTagName("sitecode")[0].firstChild.nodeValue;
   	 		document.forms[0].rightsid.value = item.getElementsByTagName("rcode")[0].firstChild.nodeValue;  	 
   	 	}
	}
}
function getSite()
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].StateIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value+","+document.getElementById("UserId").value;
	 req.onreadystatechange = getReadyStateHandler(req, showSiteDetails,"","");
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getsitebystateexclude&AppId="+ApplicationId);	 	 
}
function showSiteDetails(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("lright")[0];
	var items = cart.getElementsByTagName("loc");
	document.forms[0].SiteIdtxt.options.length = 0;
	document.forms[0].SiteIdtxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("scode")[0].firstChild;
     	if (nname != null)
     	{
   	 		document.forms[0].SiteIdtxt.options[I + 1] = new Option(item.getElementsByTagName("site")[0].firstChild.nodeValue,item.getElementsByTagName("scode")[0].firstChild.nodeValue);   	 		
   	 	}
	}
}
</script>
</head>
<body onload="getSite(),findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/LocationRight.do" method="post" name="frmLocationRight">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">State & Site Rights</th>
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
			<td id="t_street_address">&nbsp;Customer/User:</td>
			<td class="bgcolor">
				<input type="text" id="UserId" value="<%=empcode %>" name="UserId" size="10" class="ctrl" maxlength="10" onblur="findEmployee()" />
		        <input type="text" id="UserNametxt" value="<%=empname %>" name="UserNametxt" size="30" class="ctrl" maxlength="30" onfocus="StateIdtxt.focus()" />
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
			<td class="bgcolor">
				<select size="1" name="StateIdtxt" class="ctrl" onchange="getSite()">
		            <option value="">--Make a Selection--</option>
		            <%=sState%>
		        </select>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
				<select size="1" name="SiteIdtxt" class="ctrl" >
		            <option value="">--Make a Selection--</option>
		        </select>
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
			<input type="hidden" name="Admin_Input_Type" value="LocationRight" />
			<input type="hidden" name="rightsid" value="<%=lid%> " />
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
		<table border="0" cellpadding="0" cellspacing="0" width="500"><tbody>
			<tr>
				<td >
					<div id="rightsdetails">
					
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