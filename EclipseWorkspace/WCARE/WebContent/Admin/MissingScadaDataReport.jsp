<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@page import="com.enercon.global.utils.CallSchedulerForMissingScadaData"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>

<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

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
	}
	else{
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

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date date = new Date();
%>
<script type="text/javascript">
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
function findSite()
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Areatxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
     req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSiteByArea&AppId="+ApplicationId);
}
function showSite(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].SiteNametxt.options.length = 0;
	document.forms[0].SiteNametxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{	     	
	     		document.forms[0].SiteNametxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    }
        }
}

</script>
</head>
<body>
<div align="center">
<form action="<%=request.getContextPath()%>/Admin/MissingScadaDataDetails.jsp" method="post" name="missingScadaData">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="110">Missing Scada Data in ECARE</th>
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
				<select name="Areatxt" id="Areatxt" class="ctrl" style="width:136px;" onchange="findSite()">
				<option value="">--Make a Selection--</option>
				            <%=aname%></select></td>
			</tr>
		    <tr bgcolor="#ffffff">
			<td id="t_company" width="219">Site Name :</td>
			<td valign="top">            
            	<select name="SiteNametxt" id="SiteNametxt" class="ctrl" style="width:136px;">
				<option value="">--Make a Selection--</option>
				            <%=sname%></select></td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_street_address">Report Date:</td>
				<td class="bgcolor">
					<input type="text" name="ReportDatetxt" id="ReportDatetxt" value="<%=dateFormat.format(date.getTime())%>" size="14" class="ctrl" maxlength="10"/>
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.missingScadaData.ReportDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>	
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
			<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
			<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		</tr>
		</tbody></table>
		</td>
	</tr>
</tbody></table>
</form>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>