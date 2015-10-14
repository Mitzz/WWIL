<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
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
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String locationName = "<option value='all'>-- ALL LOCATION --</option>" + AdminUtil.fillMaster("TBL_LOCATION_MASTER","");

%>
<script type="text/javascript">

function findPlant(){   	
	 var req = newXMLHttpRequest();
     var ApplicationId = document.forms[0].scadaLocationNo.value;
	 req.onreadystatechange = getReadyStateHandler(req, showPlant);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SCADA_plantByLocation&AppId="+ApplicationId);
}
function showPlant(dataXml)
{
	var cart = dataXml.getElementsByTagName("scadaLocationNo")[0];
	var items = cart.getElementsByTagName("scadaPlantNo");
	document.forms[0].scadaPlantNo.options.length = 0;
	document.forms[0].scadaPlantNo.options[0] = new Option(" -- ALL PLANT --","ALL");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("scadaPlantDESC")[0].firstChild;
	     	if (nname != null)
	     	{	     	
	     		document.forms[0].scadaPlantNo.options[I + 1] = new Option(item.getElementsByTagName("scadaPlantDESC")[0].firstChild.nodeValue,item.getElementsByTagName("scadaPlantID")[0].firstChild.nodeValue);
   	 	    }
        }
}
function confirmation()
{   
	
	if(document.forms[0].AreaIdtxt.value!="")
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
<body onLoad=findPlant()>
<div align="center">
<form action="<%=request.getContextPath()%>/uploadScadaData.do" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="110">Upload Location-Wise Scada Data</th>
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
				<td id="t_company" width="219">Select Location:</td>
				<td valign="top">
					<select name="scadaLocationNo" id="scadaLocationNo" class="ctrl" onchange="findPlant()">					
				            <%=locationName%></select>
				</td>
			</tr>
		    <tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select Plant:</td>
				<td valign="top">
					<select style="width:139px;" name="scadaPlantNo" id="scadaPlantNo" class="ctrl">					
				</td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_street_address">Upload Date:</td>
				<td bgcolor="#ffffff">
					<input type="text" name="uploadDate" id="uploadDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()" value="<%=dateFormat.format(new java.util.Date().getTime()) %>" />
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].uploadDate);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
			<td class="btn" width="100"><input name="Submit" value="Upload" class="btnform" type="submit"></td>
			<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
			<td class="btn" width="100">
			<input type="hidden" name="Admin_Input_Type" value="UploadScadaData" />				
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