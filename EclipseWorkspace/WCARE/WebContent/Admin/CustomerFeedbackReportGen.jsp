<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
String custname = AdminUtil.fillWhereMaster("TBL_CUSTOMER_FEEDBACK","",session.getAttribute("loginID").toString());
//String sitename=AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",session.getAttribute("loginID").toString());
String roleid=session.getAttribute("RoleID").toString();
String sitename="";

if(roleid.equals("0000000001"))
	sitename = AdminUtil.fillMaster("VIEW_SITE_MASTER",sitename);
else
	sitename = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",sitename);
%>

<script type="text/javascript">
function findSite() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getsitebystate&AppId="+ApplicationId);
}

function loadExternal() 
{
    var state=".", comm=".";
    custName = document.getElementById("Custname").value;
    comm = document.getElementById("Datetxt").value; 
    ur="CustomerFeedBackReport.jsp";
 	var url=ur+"?custName="+custName+"&comm="+comm;
  	window.open(url,'name','height=700,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');  
}  
</script>
</head>
<body>
<form action="<%=request.getContextPath()%>/CustomerFeedbackReportGen.do" method="post"  target="_new" name="CustomerFeedbackReportGen" id="CustomerFeedbackReportGen" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Generate Customer Feedback Report</th>
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
				<td colspan="2" id="t_title">&nbsp;</td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select Customer:</td>
				<td valign="top">
					<select name="Custname" id="Custname" class="ctrl" onChange="findSite()">
						<option value=".">--Make a Selection--</option>
					    <%=custname%>
					</select>
				</td>
			
		  
			<tr class="bgcolor"> 
				<td id="t_street_address">Commision Year:</td>
				<td class="bgcolor">
					<select size="1" id="Datetxt" name="Datetxt" class="ctrl">
						<!-- <input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" />  -->
						<!-- <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.CustomerFeedbackReportGen.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>  -->
						<option value="0">--Make a Selection--</option>
		             	<option value="2011">2011</option>
		            </select>
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
	<tbody><tr>
		<td class="btn" width="100"><input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="loadExternal()" /></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="WecMaster" />		
			<input name="Reset" value="Cancel" class="btnform" type="reset">
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table></form>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>