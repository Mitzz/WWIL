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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String rid = "";
String remarks = "";
String mtype = "";
String mwectype = "";
String error = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rid = dynabean.getProperty("RemarksIdtxt").toString();
	remarks = dynabean.getProperty("Remarkstxt").toString();
	mtype = dynabean.getProperty("RemarksTypetxt").toString();
	mwectype = dynabean.getProperty("WECTypetxt").toString();	
	error = dynabean.getProperty("ErrorNotxt").toString();		
	session.removeAttribute("dynabean");
}
%>
<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("RemarksTypetxt").value;
     if (ApplicationId == "EB")
     {
     	document.forms[0].WECTypetxt.disabled = true;
     }
     else if (ApplicationId == "WEC")
     {
     	document.forms[0].WECTypetxt.disabled = false;
     }
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findRemarksMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("remarksmaster")[0];
	var items = cart.getElementsByTagName("remarkscode");	
	var divdetails = document.getElementById("remarksdetails");
	divdetails.innerHTML = "";
	var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
	str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>"
	str+="<th class='detailsheading' width='200'>Remarks</th><th class='detailsheading' width='50'>Error No</th>"
	str+="<th class='detailsheading' width='50'>Type</th>"
	str+="<th class='detailsheading' width='50'>Wec Type</th><th class='detailsheading' width='40'>Edit</th></tr>";
	for(var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("remarksid")[0].firstChild;
     	if (nname != null){
     		if (I % 2 == 0)
		    {
		        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
		    }
		    else
		    {
		        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
		    }	     		
    		str+="<td align='left'>" + item.getElementsByTagName("remarks")[0].firstChild.nodeValue + "</td>"
    		str+="<td align='left'>" + item.getElementsByTagName("error")[0].firstChild.nodeValue + "</td>"    		
     		str+="<td align='left'>" + item.getElementsByTagName("rtype")[0].firstChild.nodeValue + "</td>"	  
     		if (item.getElementsByTagName("wectype")[0].firstChild.nodeValue == ".")
     		{
	     		str+="<td align='left'></td>"	  
	     	}
	     	else
	     	{
	     		str+="<td align='left'>" + item.getElementsByTagName("wectype")[0].firstChild.nodeValue + "</td>"	  
	     	}
     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
     		str+="onClick=findDetails('" + item.getElementsByTagName("remarksid")[0].firstChild.nodeValue + "')></td></tr>"
   	 		document.forms[0].RemarksIdtxt.value = "";
   	 	}   	 	
	}
	divdetails.innerHTML = str;
}
function findDetails(rid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = rid;
	 req.onreadystatechange = getReadyStateHandler(req, showRemarksMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findRemarksMaster&AppId="+ApplicationId);
}
function showRemarksMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("remarksmaster")[0];
	var items = cart.getElementsByTagName("remarkscode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("remarksid")[0].firstChild;
     	if (nname != null){
     		document.forms[0].Remarkstxt.value = item.getElementsByTagName("remarks")[0].firstChild.nodeValue;
   	 		document.forms[0].RemarksTypetxt.value = item.getElementsByTagName("rtype")[0].firstChild.nodeValue;
   	 		document.forms[0].RemarksIdtxt.value = item.getElementsByTagName("remarksid")[0].firstChild.nodeValue;  
   	 		document.forms[0].WECTypetxt.value = item.getElementsByTagName("wectype")[0].firstChild.nodeValue;     	 		
   	 		document.forms[0].ErrorNotxt.value = item.getElementsByTagName("error")[0].firstChild.nodeValue;      	 		
   	 		document.forms[0].Submit.value="Update";
   	 	}
   	 	else{
   	 		document.forms[0].Remarkstxt.value = "";
   	 		document.forms[0].RemarksTypetxt.value = "";
   	 		document.forms[0].RemarksIdtxt.value = "";
   	 		document.forms[0].WECTypetxt.value = "";
   	 		document.forms[0].ErrorNotxt.value = "";
   	 	} 			
 	}
}
function confirmation()
{   
	// if(document.forms[0].Submit.value=='Update')
	if(document.forms[0].RemarksIdtxt.value!="")
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
<body >
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/remarksmaster.do" method="post" onSubmit="return confirmation();">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Remarks Master</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">Type:</td>
			<td>
				<select name="RemarksTypetxt" id="RemarksTypetxt" class="ctrl" onchange="findApplication()" >
	              <option value="">-- select --</option>
	              <%if(mtype.equals("EB")){ %>
					  <option value="EB" selected>EB</option>
				  <%}else{ %>
				  	  <option value="EB">EB</option>
				  <%} if(mtype.equals("WEC")){ %>
				  		<option value="WEC" selected>WEC</option>
				  <%}else{ %>
				  		<option value="WEC">WEC</option>
				  <%} %>
			  	</select>
			</td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">Wec Type:</td>
			<td><select name="WECTypetxt" id="WECTypetxt" class="ctrl" >              
              <option value="">-- select --</option>				  
			  <%if (mwectype.equals("E-30")){ %>
			  		<option value="E-30" selected>E-30</option>
				<%}else{ %>  			               
					<OPTION value="E-30">E-30</OPTION>
		       <%} if (mwectype.equals("E-33")){ %>
			  		<option value="E-33" selected>E-33</option>
				<%}else{ %>  			               
					<OPTION value="E-33">E-33</OPTION>
		       <%} if (mwectype.equals("E-40")){ %>
			  		<option value="E-40" selected>E-40</option>
				<%}else{ %>  			               
					<OPTION value="E-40" >E-40</OPTION>
		       <%} if(mwectype.equals("E-48")){ %>
			       	<OPTION value="E-48" selected>E-48</OPTION>
		       <%}else{ %>
	          	  	<option value="E-48">E-48</option>
		       <%} if(mwectype.equals("E-53")){ %>
			       	<OPTION value="E-53" selected>E-53</OPTION>
		       <%}else{ %>
	          	  	<option value="E-53">E-53</option>
		       <%} %>
			  </select>
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td id="t_company">Error No:</td>
			<td valign="top"><input name="ErrorNotxt" id="ErrorNotxt" value="<%=error%>" size="20" class="ctrl" type="text" maxlength="10" /></td>
		</tr>
		<tr bgcolor="#ffffff">
			<td id="t_company">Description:</td>
			<td valign="top"><input name="Remarkstxt" id="Remarkstxt" value="<%=remarks%>" size="100" class="ctrl" type="text" maxlength="500" /></td>
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
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody>
	<tr>
		<td class="btn" width="100"><input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="RemarksMaster" />
		<input type="hidden" name="RemarksIdtxt" id="RemarksIdtxt" value="<%=rid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody>
</table>
</form>
</td>		
</tr>
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="500"><tbody>
			<tr>
				<td >
					<div id="remarksdetails">
					
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