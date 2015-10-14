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
String rn = "";
String mid = "";
String de = "";


String fd="";
String td="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("msgheadtxt").toString();
	mid = dynabean.getProperty("MsgIdtxt").toString();
	de = dynabean.getProperty("MsgDescriptiontxt").toString();
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
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
String stdmsg = AdminUtil.fillMaster("TBL_STANDARD_MESSAGE",rn);

%>
<script type="text/javascript">

function DelTEMPMsgDetails()
{   
	 var req = newXMLHttpRequest();
    
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, DelTEMPDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_TEMP_DELETE&AppId="+ApplicationId);
}
function DelTEMPDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
     	if (nname != null)
     	{
   	 		
   	 		
   	 	}
   	 			
 	}
 
}
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_DETAIL_MESSAGE&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");	
		var divdetails = document.getElementById("msgdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='230'>Description</th><th class='detailsheading' width='100'>From Date</th><th class='detailsheading' width='100'>To Date</th>"
		str +="<th class='detailsheading' width='40'>E</th><th class='detailsheading' width='40'>D</th><th class='detailsheading' width='40'>Send</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("msgdesc")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("fdate")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("tdate")[0].firstChild.nodeValue + "</td>"	   
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findMsgDetails('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td>"
	   	 		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to edit the record' "
	     		str+="onClick=DelMsgDetails('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td>"
	   	 	    str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/menu/mifuncs.gif' alt='Click to send the message' "
	     		str+="onClick=SentMessage('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].MsgIdtxt.value = "";
	   	 	}   	 	
		}
		str += "</table>"
		divdetails.innerHTML = str;
}





function SentMessage(msgid)
{

location.href="<%=request.getContextPath()%>/Admin/SendMessage.jsp?msgid="+msgid;
//window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,status=no');

}
function findMsgDetails(msgid)
{
	 var req = newXMLHttpRequest();
    
     var ApplicationId = msgid;
	 req.onreadystatechange = getReadyStateHandler(req, showMsgDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_DETAIL_MESSAGE_BY_ID&AppId="+ApplicationId);
}
function showMsgDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgdesc")[0].firstChild;
     	if (nname != null){
   	 		
   	 		document.forms[0].MsgDescriptiontxt.value = item.getElementsByTagName("msgdesc")[0].firstChild.nodeValue;    	 		
   	 		document.forms[0].FromDatetxt.value = item.getElementsByTagName("fdate")[0].firstChild.nodeValue;  
   	 		document.forms[0].ToDatetxt.value = item.getElementsByTagName("tdate")[0].firstChild.nodeValue; 
   	 		document.forms[0].MsgIdtxt.value= item.getElementsByTagName("msgid")[0].firstChild.nodeValue; 
   	 	}
   	 	else{
   	 		
   	 		
   	 		document.forms[0].MsgDescriptiontxt.value = "";    	 		
   	 		document.forms[0].FromDatetxt.value = "";  
   	 		document.forms[0].ToDatetxt.value = ""; 
   	 		document.forms[0].MsgIdtxt.value="";
   	 		 	 		
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
	 req.send("Admin_Input_Type=CUST_DELETE_MESSAGE_DETAIL&AppId="+MsgId);
}
function showDelMsgDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
     	if (nname != null)
     	{
   	 		
   	 		alert("Mesaage Deleted Sucessfuly");
   	 	}
   	 			
 	}
 	findApplication();
}
function findDetails()
{
	 var req = newXMLHttpRequest();
     var list = document.forms[0].msgheadtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showRoleMaster);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_STD_MESSAGE_DETAIL&AppId="+ApplicationId);
}
function showRoleMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgdesc")[0].firstChild;
     	if (nname != null){
   	 		
   	 		document.forms[0].MsgDescriptiontxt.value = item.getElementsByTagName("msgdesc")[0].firstChild.nodeValue;    	 		
   	 		
   	 	}
   	 	else{
   	 		
   	 		document.forms[0].MsgDescriptiontxt.value = "";  
   	 		 	 		
   	 	} 			
 	}
}



</script>
</head>
<body onLoad="findApplication(),DelTEMPMsgDetails();">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/PostMessage.do" name="PostMessage" id="PostMessage" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Message Head</th>
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
			<td id="t_street_address" width="180px">&nbsp;Select&nbsp;Message:</td>
			<td class="bgcolor" width="180px">
                <select size="1" name="msgheadtxt" id="msgheadtxt" class="tabtextnormal" onchange="findDetails()">
		             <option value="">--Make a Selection--</option>
		             <%=stdmsg%>
		         </select>

</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Message Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="MsgDescriptiontxt" name="MsgDescriptiontxt" class="BoxBorder" ></textarea></td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="FromDatetxt" name="FromDatetxt" class="tabtextnormal">
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.PostMessage.FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
		</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="ToDatetxt" name="ToDatetxt" class="tabtextnormal">
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.PostMessage.ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
			<input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=mid%>" />	
			<input type="hidden" name="Admin_Input_Type" value="PostMessage" />	
			<input name="Submit" type="submit" class="btnform" value="Submit"/>
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
		<table border="0" cellpadding="0" cellspacing="0" width="400"><tbody>
			<tr>
				<td >
					<div id="msgdetails">
					
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