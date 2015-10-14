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
<%@ page import="com.enercon.admin.util.AdminUtil"%>
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
String stid = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
String sitename = AdminUtil.fillMaster("VIEW_SITE_MASTER",stid);

%>
<script type="text/javascript">

function findAllRemarks()
{
	 var req = newXMLHttpRequest();	 	 
     var ApplicationId = "";
     
     req.onreadystatechange = getReadyStateHandler(req, displayRemarks);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getAllRemarks&AppId="+ApplicationId);
}
function displayRemarks(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	
	var divdetails = document.getElementById("remarksdetails");
	divdetails.innerHTML = "";
	
	var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='25'>S.N.</th><th class='detailsheading' width='115'>Site Name</th><th class='detailsheading' width='475'>Remarks</th></tr>";		
		
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("siteid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("sitename")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("siteremarks")[0].firstChild.nodeValue + "</td></tr>";	   	 		
	   	 	}   	 	
        }
        divdetails.innerHTML = str;
}

function findRemarks()
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].siteNameTxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
     req.onreadystatechange = getReadyStateHandler(req, showRemark);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getRemarksBySite&AppId="+ApplicationId);
}
function showRemark(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");	
	
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("siteRemarks")[0].firstChild;
	     	if (nname != null)
	     	{	     	
	     		document.forms[0].siteRemarksTxt.value = item.getElementsByTagName("siteRemarks")[0].firstChild.nodeValue;
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
<body onLoad="findAllRemarks()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/remarkssitemaster.do" method="post" onSubmit="return confirmation();">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Site Remarks Master</th>
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
			<td id="t_street_address_ln2" nowrap="nowrap">Site Name:</td>
			<td>
				<select name="siteNameTxt" id="siteNameTxt" class="ctrl" onChange="findRemarks()">
	              <option value="">--Make a Selection--</option>
				            <%=sitename%>
				</select>
			</td>			
		</tr>			
		<tr bgcolor="#ffffff">
			<td id="t_company">Description:</td>
			<td valign="top"><input name="siteRemarksTxt" id="siteRemarksTxt" value="" size="100" class="ctrl" type="text" maxlength="500" /></td>
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
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="RemarksSiteMaster" />
		<input type="hidden" name="RemarksIdtxt" id="RemarksIdtxt" value="<%=stid %>" />
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
		<table border="0" cellpadding="0" cellspacing="0" width="615"><tbody>
			<tr>
				<td>
					<div id="remarksdetails"></div>	
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