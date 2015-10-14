<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
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
String sid = "";
String sname = "";
String scode = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	sid = dynabean.getProperty("StateIdtxt").toString();
	sname = dynabean.getProperty("StateNametxt").toString();
	scode = dynabean.getProperty("StateCodetxt").toString();
	session.removeAttribute("dynabean");
}
%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findStateMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("statemaster")[0];
	var items = cart.getElementsByTagName("statecode");	
		var divdetails = document.getElementById("statedetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>State</th><th class='detailsheading' width='100'>State SAP Code</th>"
		str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("stateid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("sname")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("scode")[0].firstChild.nodeValue + "</td>"	     			  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("stateid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].StateIdtxt.value = "";
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str;
}
function findDetails(stateid)
{   
	 var req = newXMLHttpRequest();
     var ApplicationId = stateid;
	 req.onreadystatechange = getReadyStateHandler(req, showStateMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findStateByID&AppId="+ApplicationId);
}
function showStateMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("statemaster")[0];
	var items = cart.getElementsByTagName("statecode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("sname")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].StateNametxt.value = item.getElementsByTagName("sname")[0].firstChild.nodeValue;
   	 		document.forms[0].StateCodetxt.value = item.getElementsByTagName("scode")[0].firstChild.nodeValue;
   	 		document.forms[0].StateIdtxt.value = item.getElementsByTagName("stateid")[0].firstChild.nodeValue;  
   	 		document.forms[0].Submit.value="Update";
   	 			
   	 	}
   	 	else{
   	 		document.forms[0].StateNametxt.value = "";	
   	 		document.forms[0].StateCodetxt.value = "";	 		
   	 		document.forms[0].StateIdtxt.value = "";  
   	 	} 			
 	}
}

function confirmation()
{   
	
	if(document.forms[0].StateIdtxt.value!="")
	{
		var answer = confirm("Are you sure you want to Update?")
		if (answer)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else
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
}

</script>
</head>
<body onLoad="findApplication()">
<div align="center">
<form action="<%=request.getContextPath()%>/StateMaster.do" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="550">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="70">State Master</th>
	<td width="363"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="8">&nbsp;</td>
	<td class="newhead4" width="11"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3" width="550">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="550">
		<tbody>
		<tr class="bgcolor">
			<td  width="219">State Name:</td>
			<td colspan="3" valign="top"><input name="StateNametxt"  id="StateNametxt" value="<%=sname%>" size="12" class="ctrl" type="text"></td>
			</tr>
		<tr class="bgcolor">
			<td id="t_company" width="219">State SAP Code:</td>
			<td colspan="3" valign="top">
            <input name="StateCodetxt" id="StateCodetxt" size="12" value="<%=scode%>" class="ctrl" type="text"></td>
			</tr>
		<tr class="bgcolor"> 
			<td colspan="4">
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
		
		
		<td class="btn" width="100"><input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit" onClick="return confirmation();"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="StateMaster"  />		
		<input type="hidden" name="StateIdtxt" value="<%=sid %>" /><input name="Cancel" value="Cancel" class="btnform" type="reset">
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="400">
<tbody><tr>		
				<td align="center">
					
					<div id="statedetails">	</div>	
				</td>
			</tr>
			</tbody>
		</table>	

</div>
</body>
</html>