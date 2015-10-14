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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<%
String site = "";
String fd = "";
String mp = "";
String fdate = "";
String td="";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	site = dynabean.getProperty("SiteIdtxt") == null ? "" : dynabean.getProperty("SiteIdtxt").toString();	
	fd = dynabean.getProperty("FederIdtxt") == null ? "" : dynabean.getProperty("FederIdtxt").toString();
	mp = dynabean.getProperty("MpIdtxt") == null ? "" : dynabean.getProperty("MpIdtxt").toString();
	fdate = dynabean.getProperty("FromDatetxt").toString();
	td = dynabean.getProperty("ToDatetxt").toString();
//	String str1=(String)session.getAttribute("SubmitMessage");
//	if(str1 != null && str1.equals("Success")){
//		si = "";
//		lid = "";
//	}else{
//		si = dynabean.getProperty("SiteIdtxt") == null ? "" : dynabean.getProperty("SiteIdtxt").toString();
//		lid = dynabean.getProperty("rightsid") == null ? "" : dynabean.getProperty("rightsid").toString();
//	}
//	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
%>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();
String sSite="";

if(roleid.equals("0000000001"))
     sSite = AdminUtil.fillMaster("VIEW_SITE_MASTER",sSite);
else
	sSite = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",userid);

String sFD = AdminUtil.fillWhereMaster("SELECT_FD_MASTER",fd,site);
String mps = AdminUtil.fillWhereMaster("TBL_MP_MASTER",mp,"EB");
%>
<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].SiteIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getFeder&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("federhead")[0];
	var items = cart.getElementsByTagName("federcode");	
	document.forms[0].FederIdtxt.options.length = 0;
	document.forms[0].FederIdtxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I]
     	var nname = item.getElementsByTagName("fid")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].FederIdtxt.options[I + 1] = new Option(item.getElementsByTagName("fdesc")[0].firstChild.nodeValue,item.getElementsByTagName("fid")[0].firstChild.nodeValue);
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
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/DeleteFederData.do" method="post" name="DeleteFederDatafrm">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Delete Feeder Reading</th>
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
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
				<select size="1" id="SiteIdtxt" name="SiteIdtxt" class="ctrl" onChange="findApplication()">
		            <option value="">--Make a Selection--</option>
		            <%=sSite %>
		        </select>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Feeder:</td>
			<td class="bgcolor">
				<select size="1" id="FederIdtxt" name="FederIdtxt" class="ctrl" >
		            <option value="">--Make a Selection--</option>
		            <%=sFD %>
		        </select>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Measuring &nbsp;Point:</td>
			<td class="bgcolor">
				<select size="1" id="MpIdtxt" name="MpIdtxt" class="ctrl" >
		             <option value="ALL">--ALL-</option>
		            <%=mps %>
		        </select>
			</td>
		</tr>			
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" maxlength="10" value="<%=fdate %>" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.DeleteFederDatafrm.FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" id="ToDatetxt" name="ToDatetxt" class="ctrl" value="<%=td %>" onfocus="dc1.focus()">
				<a href="javascript:void(0)" id="dc1" onClick="if(self.gfPop)gfPop.fPopCalendar(document.DeleteFederDatafrm.ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
			<input name="Submit" value="Submit" class="btnform" type="submit" onClick="return confirmation();">
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="DeleteFederData" />		
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
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
					<div id="ebdetails">
					
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
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>