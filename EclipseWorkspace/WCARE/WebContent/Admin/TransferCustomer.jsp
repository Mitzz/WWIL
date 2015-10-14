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
<%
String cf = "";
String ct = "";
String dt = "";
String rem = "";
String wecto = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	cf = dynabean.getProperty("CustomerFromtxt").toString();
	ct = dynabean.getProperty("CustomerTotxt").toString();
	dt = dynabean.getProperty("Datetxt").toString();
	wecto=dynabean.getProperty("WECTotxt").toString();
	rem=dynabean.getProperty("Remarkstxt").toString();
	session.removeAttribute("dynabean");
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String custfromid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",cf);
String custtoid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",ct);
%>
<script type="text/javascript">
function findvalidate() 
{
	var blnSave=false;
     blnSave = validateForm('Customer From',document.forms[0].CustomerFromtxt.value,'M','',
     						'WEC From',document.forms[0].WECtxt.value,'M','',
     						'EB From',document.forms[0].EBtxt.value,'M','',
                            'Customer To',document.forms[0].CustomerTotxt.value,'M','',
                            'WEC To',document.forms[0].WECTotxt.value,'M','',
                            'EB To',document.forms[0].EBTotxt.value,'M','',
                            'Remarks',document.forms[0].Remarkstxt.value,'M','',
                            'DATE',document.forms[0].Datetxt.value,'M','');
     if ( blnSave == true ) 
     {
     	return true;
		
		if(document.forms[0].WECtxt.value==document.forms[0].WECTotxt.value)
		{
		  alert("Not proceed because WEC From and WEC To are same.");
		  return false;
		}
		
     } 
     else 
     {
       return false;
     }
}
function findApplication(){
	 var req = newXMLHttpRequest();
	 var list =  document.forms[0].CustomerFromtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     if (ApplicationId != ""){
		 req.onreadystatechange = getReadyStateHandler(req, showAppDetails,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_findEBMasterByCust&AppId="+ApplicationId);
	 }
}
function showAppDetails(dataXml,a,b){
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	document.forms[0].EBtxt.options.length = 0;
	document.forms[0].EBtxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{  
		var item = items[I]
    	var nname = item.getElementsByTagName("wecid")[0].firstChild;
    	if (nname != null)
    	{
   			document.forms[0].EBtxt.options[I + 1] = new Option(item.getElementsByTagName("wecname")[0].firstChild.nodeValue,item.getElementsByTagName("wecid")[0].firstChild.nodeValue);
        }
 	}
}
function findApplicationWEC() 
{
	 var req = newXMLHttpRequest();
	 var list =  document.forms[0].EBtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     if (ApplicationId != "")
     {
		 req.onreadystatechange = getReadyStateHandler(req, showAppDetailsWEC,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_findWECMasterByEB&AppId="+ApplicationId);
	 }
}
function showAppDetailsWEC(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	document.forms[0].WECtxt.options.length = 0;
	document.forms[0].WECtxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{  
		var item = items[I]
    	var nname = item.getElementsByTagName("wecid")[0].firstChild;
    	if (nname != null)
    	{
   			document.forms[0].WECtxt.options[I + 1] = new Option(item.getElementsByTagName("wecname")[0].firstChild.nodeValue,item.getElementsByTagName("wecid")[0].firstChild.nodeValue);
        }
 	}
}

function findApplication_1() 
{
	 var req = newXMLHttpRequest();
	 var list =  document.forms[0].CustomerTotxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     if (ApplicationId != "")
     {
		 req.onreadystatechange = getReadyStateHandler(req, showAppDetails_1,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_findEBMasterByCust_1&AppId="+ApplicationId);
	 }
}
function showAppDetails_1(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	document.forms[0].EBTotxt.options.length = 0;
	document.forms[0].EBTotxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{  
		var item = items[I]
    	var nname = item.getElementsByTagName("wecid")[0].firstChild;
    	if (nname != null)
    	{
   			document.forms[0].EBTotxt.options[I + 1] = new Option(item.getElementsByTagName("wecname")[0].firstChild.nodeValue,item.getElementsByTagName("wecid")[0].firstChild.nodeValue);
        }
 	}
}
function findApplication_1WECTo() 
{
	 var req = newXMLHttpRequest();
	 var list =  document.forms[0].EBTotxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     if (ApplicationId != "")
     {
		 req.onreadystatechange = getReadyStateHandler(req, showAppDetails_1WEC,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_findWECMasterToByEB&AppId="+ApplicationId);
	 }
}
function findApplication_1WEC() 
{
	 var req = newXMLHttpRequest();
	 var list =  document.forms[0].EBTotxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     if (ApplicationId != "")
     {
		 req.onreadystatechange = getReadyStateHandler(req, showAppDetails_1WEC,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_findWECMasterByEB&AppId="+ApplicationId);
	 }
}
function showAppDetails_1WEC(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	document.forms[0].WECTotxt.options.length = 0;
	document.forms[0].WECTotxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{  
		var item = items[I]
    	var nname = item.getElementsByTagName("wecid")[0].firstChild;
    	if (nname != null)
    	{
   			document.forms[0].WECTotxt.options[I + 1] = new Option(item.getElementsByTagName("wecname")[0].firstChild.nodeValue,item.getElementsByTagName("wecid")[0].firstChild.nodeValue);
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
<body onload="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="800">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/TransferWEC.do" method="post" name="TransferWEC" onSubmit="return findvalidate()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Transfer WEC from Customer</th>
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
			 <td id="t_street_address">Customer From:</td>
			 <td class="bgcolor">
			    <select size="1" name="CustomerFromtxt" id="CustomerFromtxt" class="ctrl" onChange="findApplication()">
		            <option value="">--Make a Selection--</option>
		            <%=custfromid %>
		        </select>
			 </td>
	  	</tr>
	  	
	  	<tr class="bgcolor"> 
			<td id="t_street_address">Select EB From:</td>
			<td class="bgcolor">
				<select size="1" id="EBtxt" name="EBtxt" class="ctrl" onChange="findApplicationWEC()">
		            <option value="">--Make a Selection--</option>
		        </select>
			</td>
		</tr>
		
		<tr class="bgcolor"> 
			<td id="t_street_address">Select WEC From:</td>
			<td class="bgcolor">
				<select size="1" id="WECtxt" name="WECtxt" class="ctrl">
		            <option value="">--Make a Selection--</option>
		        </select>
			</td>
		</tr>	
		<tr class="bgcolor">
			 <td id="t_street_address">Customer To:</td>
			 <td class="bgcolor">
			    <select size="1" name="CustomerTotxt" id="CustomerTotxt" class="ctrl" onChange="findApplication_1()">
		            <option value="">--Make a Selection--</option>
		            <%=custtoid %>
		        </select>
			 </td>
	  	</tr>
	  	
	  	<tr class="bgcolor"> 
			<td id="t_street_address">Select EB To:</td>
			<td class="bgcolor">
				<select size="1" id="EBTotxt" name="EBTotxt" class="ctrl" onChange="findApplication_1WECTo()">
		            <option value="">--Make a Selection--</option>
		        </select>
			</td>
		</tr>
		
	  	<tr class="bgcolor"> 
			<td id="t_street_address">Select WEC To:</td>
			<td class="bgcolor">
				<select size="1" id="WECTotxt" name="WECTotxt" class="ctrl">
		            <option value="">--Make a Selection--</option>
		        </select>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">From Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value="<%=dt %>" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.TransferWEC.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">Remarks:</td>
			<td class="bgcolor">
				<input type="text" name="Remarkstxt" id="Remarkstxt" size="20" class="ctrl" maxlength="50" value="<%=rem %>" />
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
			<input type="submit" name="Submitcmd" class="btnform" value="Submit" onClick="return confirmation();"/>
			<input type="hidden" name="Admin_Input_Type" value="TransferWEC" />
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
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>