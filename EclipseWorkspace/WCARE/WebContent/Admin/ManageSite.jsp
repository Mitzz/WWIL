<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
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
String stid = "";
String atid = "";
String scode="";
String sinc="",sadd="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	stid = dynabean.getProperty("Statetxt").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		sname = "";
		sid = "";
		scode = "";
		sinc= "";
		sadd="";
	}else{
		sname = dynabean.getProperty("SiteNametxt").toString();
		sid = dynabean.getProperty("SiteIdtxt").toString();
		scode = dynabean.getProperty("SiteCodetxt").toString();
		sinc = dynabean.getProperty("SiteInchargetxt").toString();
		sadd = dynabean.getProperty("SiteAddresstxt").toString();
	}
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
String aname = AdminUtil.fillMaster("VIEW_AREA_MASTER",atid);

%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].Areatxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getAreaByID&AppId="+ApplicationId);  // Admin_Input_Type=getSite
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	
		var divdetails = document.getElementById("sitedetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Site Name</th><th class='detailsheading' width='100'>Site Code</th>"
		str +="<th class='detailsheading' width='100'>Incharge</th><th class='detailsheading' width='100'>Address</th><th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("sid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("sdesc")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("scode")[0].firstChild.nodeValue + "</td>"		
	     		if (item.getElementsByTagName("sinc")[0].firstChild.nodeValue == ".")
	     		{
	     			str+="<td align='left'></td>"
	     		}
	     		else
	     		{
	     			str+="<td align='left'>" + item.getElementsByTagName("sinc")[0].firstChild.nodeValue + "</td>"
	     		}
	     		if (item.getElementsByTagName("sadd")[0].firstChild.nodeValue == ".")
	     		{
	     			str+="<td align='left'></td>"
	     		}
	     		else
	     		{
	     			str+="<td align='left'>" + item.getElementsByTagName("sadd")[0].firstChild.nodeValue + "</td>"
	     		}
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("sid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str;
}
function findDetails(siteid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = siteid;
	 req.onreadystatechange = getReadyStateHandler(req, showSiteMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findSiteByID&AppId="+ApplicationId);
}
function showSiteMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitemaster")[0];
	var items = cart.getElementsByTagName("sitecode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("sname")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].SiteNametxt.value = item.getElementsByTagName("sname")[0].firstChild.nodeValue;
   	 		document.forms[0].SiteCodetxt.value = item.getElementsByTagName("scode")[0].firstChild.nodeValue;
   	 		document.forms[0].Statetxt.value = item.getElementsByTagName("stateid")[0].firstChild.nodeValue;
   	 		document.forms[0].SiteIdtxt.value = item.getElementsByTagName("siteid")[0].firstChild.nodeValue;  
   	 		if (item.getElementsByTagName("sinc")[0].firstChild.nodeValue == ".")
     		{
     			document.forms[0].SiteInchargetxt.value = "";
     		}
     		else
     		{
     			document.forms[0].SiteInchargetxt.value = item.getElementsByTagName("sinc")[0].firstChild.nodeValue;
     		}
   	 		if (item.getElementsByTagName("sadd")[0].firstChild.nodeValue == ".")
     		{
     			document.forms[0].SiteAddresstxt.value = "";
     		}
     		else
     		{
     			document.forms[0].SiteAddresstxt.value = item.getElementsByTagName("sadd")[0].firstChild.nodeValue;  
     		}
   	 		document.forms[0].Submit.value="Update";	 		
   	 	}
   	 	else{
   	 		document.forms[0].SiteNametxt.value = "";
   	 		document.forms[0].SiteCodetxt.value = "";	
   	 		document.forms[0].Statetxt.value = "";	 		
   	 		document.forms[0].SiteIdtxt.value = "";   	 
   	 		document.forms[0].SiteInchargetxt.value = "";	 		
   	 		document.forms[0].SiteAddresstxt.value = "";   			
   	 	} 			
 	}
}
function findArea()
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
     req.onreadystatechange = getReadyStateHandler(req, showArea);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getAreaBYRIGHTS&AppId="+ApplicationId);
}
function showArea(dataXml)
{
	var cart = dataXml.getElementsByTagName("areahead")[0];
	var items = cart.getElementsByTagName("areacode");
	document.forms[0].Areatxt.options.length = 0;
	document.forms[0].Areatxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("adesc")[0].firstChild;
	     	if (nname != null)
	     	{	     	
	     		document.forms[0].Areatxt.options[I + 1] = new Option(item.getElementsByTagName("adesc")[0].firstChild.nodeValue,item.getElementsByTagName("aid")[0].firstChild.nodeValue);
   	 	    }
        }
}
function confirmation()
{   
	
	if(document.forms[0].SiteIdtxt.value!="")
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
<form action="<%=request.getContextPath()%>/SiteMaster.do" method="post" onSubmit="return confirmation()" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="110">Site Master</th>
	<td width="475"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="7">&nbsp;</td>
	<td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3" width="592">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="592">
		<tbody>
		<tr bgcolor="#ffffff">
			<td id="t_company" width="219">Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onchange="findArea()">
				<option value="">--Make a Selection--</option>
				            <%=statename%></select></td>
			</tr>
			<tr bgcolor="#ffffff">
			<td id="t_company" width="219">Select Area:</td>
			<td valign="top">
				<select name="Areatxt" id="Areatxt" class="ctrl" onchange="findApplication()">
				<option value="">--Make a Selection--</option>
				            <%=aname%></select></td>
			</tr>
		    <tr bgcolor="#ffffff">
			<td id="t_company" width="219">Site Name :</td>
			<td valign="top">
            <input name="SiteNametxt" id="SiteNametxt" size="15" class="ctrl" value="<%=sname%>" type="text"></td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Site Short Code :</td>
				<td valign="top">
	            <input name="SiteCodetxt" id="SiteCodetxt" size="15" class="ctrl" value="<%=scode%>" type="text"></td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Site Incharge :</td>
				<td valign="top">
	            <input name="SiteInchargetxt" id="SiteInchargetxt" size="25" class="ctrl" value="<%=sinc%>" type="text" maxlength="25"></td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Site Address :</td>
				<td valign="top">
	            <input name="SiteAddresstxt" id="SiteAddresstxt" size="50" class="ctrl" value="<%=sadd%>" type="text" maxlength="150"></td>
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
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	</tr>

<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="SiteMaster" />		
		<input type="hidden" name="SiteIdtxt" value="<%=sid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
<tbody><tr>		
				<td align="center">
					
					<div id="sitedetails">	</div>	
				</td>
			</tr>
			</tbody>
		</table>	

</div>
</body>
</html>