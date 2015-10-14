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
String rn = "";
String nid = "";
String de = "";
String fd="";
String td="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("NewsTitletxt").toString();
	nid = dynabean.getProperty("NewsIdtxt").toString();
	de = dynabean.getProperty("NewsDescriptiontxt").toString();
	fd = dynabean.getProperty("FromDatetxt").toString();
	td = dynabean.getProperty("ToDatetxt").toString();
	session.removeAttribute("dynabean");
	//System.out.println("mid"+mid);
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
%>
<script type="text/javascript">
function gotoSave() 
{
    var blnSave=false;
    blnSave = validateForm('News Title',document.forms[0].NewsTitletxt.value,'M','',
    					   'News Description',document.getElementById("NewsDescriptiontxt").value,'M','',
                           'From Date',document.forms[0].FromDatetxt.value,'M','D',
                           'To Date',document.forms[0].ToDatetxt.value,'M','D'
                           );
     if ( blnSave == true ) {
        return true;
     } else {
        return false;
     }
}

function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=newsdetails&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("newsmaster")[0];
	var items = cart.getElementsByTagName("ncode");	
		var divdetails = document.getElementById("newsdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Title</th>"
		str +="<th class='detailsheading' width='250'>Description</th><th class='detailsheading' width='90'>From Date</th><th class='detailsheading' width='90'>To Date</th>"
		str +="<th class='detailsheading' width='40'>E</th><th class='detailsheading' width='40'>D</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("newsid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("title")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("descr")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("fdate")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("tdate")[0].firstChild.nodeValue + "</td>"	   
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findMsgDetails('" + item.getElementsByTagName("newsid")[0].firstChild.nodeValue + "')></td>"
	   	 		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to edit the record' "
	     		str+="onClick=DelMsgDetails('" + item.getElementsByTagName("newsid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	}   	 	
		}
		str += "</table>"
		divdetails.innerHTML = str;
}

function findMsgDetails(msgid)
{
	 var req = newXMLHttpRequest();
     var ApplicationId = msgid;
	 req.onreadystatechange = getReadyStateHandler(req, showMsgDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=newsdetails&AppId="+ApplicationId);
}
function showMsgDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("newsmaster")[0];
	var items = cart.getElementsByTagName("ncode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("newsid")[0].firstChild;
     	if (nname != null)
     	{
   	 		document.forms[0].NewsTitletxt.value = item.getElementsByTagName("title")[0].firstChild.nodeValue;    	 		
   	 		document.forms[0].NewsDescriptiontxt.value = item.getElementsByTagName("descr")[0].firstChild.nodeValue;     	 		
   	 		document.forms[0].FromDatetxt.value = item.getElementsByTagName("fdate")[0].firstChild.nodeValue;  
   	 		document.forms[0].ToDatetxt.value = item.getElementsByTagName("tdate")[0].firstChild.nodeValue; 
   	 		document.forms[0].NewsIdtxt.value= item.getElementsByTagName("newsid")[0].firstChild.nodeValue; 
   	 	}
   	 	else{
   	 		document.forms[0].NewsTitletxt.value = "";   
   	 		document.forms[0].NewsDescriptiontxt.value = "";    	 		
   	 		document.forms[0].FromDatetxt.value = "";  
   	 		document.forms[0].ToDatetxt.value = ""; 
   	 		document.forms[0].NewsIdtxt.value="";
   	 	} 			
 	}
}

function DelMsgDetails(msgid)
{   
	 var req = newXMLHttpRequest();
     var MsgId = msgid;
	 req.onreadystatechange = getReadyStateHandler(req, showDelMsgDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=newsdelete&AppId="+MsgId);
}
function showDelMsgDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("newsmaster")[0];
	var items = cart.getElementsByTagName("ncode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("newsid")[0].firstChild;
     	if (nname != null)
     	{
   	 		alert("News Deleted Sucessfuly");
   	 	}
   	 			
 	}
 	findApplication();
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
<body onLoad="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/ManageNews.do" name="ManageNews" onSubmit="return gotoSave()" id="ManageNews" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Manage News</th>
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
			<td id="t_street_address" width="180px">&nbsp;News Title:</td>
			<td class="bgcolor" width="180px">
                <input type="text" id="NewsTitletxt" name="NewsTitletxt" class="ctrl" value="<%=rn %>">
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;News Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="NewsDescriptiontxt" name="NewsDescriptiontxt" class="ctrl" ><%=de %></textarea></td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="FromDatetxt" name="FromDatetxt" class="ctrl" value="<%=fd %>">
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageNews.FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
		</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="ToDatetxt" name="ToDatetxt" class="ctrl" value="<%=td %>">
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ManageNews.ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
			<input type="hidden" id="NewsIdtxt" name="NewsIdtxt" value="<%=nid%>" />	
			<input type="hidden" name="Admin_Input_Type" value="ManageNews" />	
			<input name="Submit" type="submit" class="btnform" value="Submit" onClick="return confirmation();"/>
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
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="650"><tbody>
			<tr>
				<td >
					<div id="newsdetails">
					
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