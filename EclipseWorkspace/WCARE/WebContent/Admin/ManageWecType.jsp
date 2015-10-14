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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String tid = "";
String tname = "";
String tcap = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	tid = dynabean.getProperty("TypeIdtxt").toString();
	tname = dynabean.getProperty("TypeNametxt").toString();
	tcap = dynabean.getProperty("Capacitytxt").toString();
	session.removeAttribute("dynabean");
}
%>
<script type="text/javascript">
function gotoSave() 
{
    var blnSave=false;
    blnSave = validateForm('WEC Type',document.forms[0].TypeNametxt.value,'M','',
    					   'WEC Capacity',document.getElementById("Capacitytxt").value,'M','I'
    					   );
     if ( blnSave == true ) {
        return true;
     } else {
        return false;
     }
}
function cancel() 
{
	document.forms[0].TypeNametxt.value = "";
	document.forms[0].Capacitytxt.value = "";
    document.forms[0].TypeIdtxt.value = "";
}
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getwectype&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("typemaster")[0];
	var items = cart.getElementsByTagName("typecode");	
		var divdetails = document.getElementById("typedetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Type</th><th class='detailsheading' width='100'>Capacity</th>"
		str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("id")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("type")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("cap")[0].firstChild.nodeValue + "</td>"	     			  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("id")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	}   	 	
		}
		divdetails.innerHTML = str;
}
function findDetails(id)
{   
	 var req = newXMLHttpRequest();
     var ApplicationId = id;
	 req.onreadystatechange = getReadyStateHandler(req, showTypeMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getwectype&AppId="+ApplicationId);
}
function showTypeMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("typemaster")[0];
	var items = cart.getElementsByTagName("typecode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("id")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].TypeNametxt.value = item.getElementsByTagName("type")[0].firstChild.nodeValue;
   	 		document.forms[0].Capacitytxt.value = item.getElementsByTagName("cap")[0].firstChild.nodeValue;
   	 		document.forms[0].TypeIdtxt.value = item.getElementsByTagName("id")[0].firstChild.nodeValue;
   	 	}
   	 	else{
   	 		document.forms[0].TypeNametxt.value = "";
   	 		document.forms[0].Capacitytxt.value = "";
   	 		document.forms[0].TypeIdtxt.value = "";
   	 	} 			
 	}
}
function confirmation()
{   
	// if(document.forms[0].Submit.value=='Update')
	if(document.forms[0].TypeIdtxt.value!="")
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
<form action="<%=request.getContextPath()%>/typemaster.do" method="post" onSubmit="return gotoSave()" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="550">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="70">WEC Type Master</th>
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
			<td  width="219">Enter Wec Type:</td>
			<td colspan="3" valign="top"><input name="TypeNametxt"  id="TypeNametxt" value="<%=tname%>" size="20" class="ctrl" type="text" maxlength="10"></td>
			</tr>
		<tr class="bgcolor">
			<td id="t_company" width="219">Enter Capacity:</td>
			<td colspan="3" valign="top">
            <input name="Capacitytxt" id="Capacitytxt" size="10" value="<%=tcap%>" class="ctrl" type="text" maxlength="10"></td>
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
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="TypeMaster" />		
		<input type="hidden" name="TypeIdtxt" value="<%=tid %>" /><input name="Cancel" value="Cancel" class="btnform" type="button" onclick="cancel()">
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
					
					<div id="typedetails">	</div>	
				</td>
			</tr>
			</tbody>
		</table>	

</div>
</body>
</html>