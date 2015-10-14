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
String ri = "";
String stid = "";
String Customeridtxt="";
String sitename="";
String siteid="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("msgheadtxt").toString();
	mid = dynabean.getProperty("MsgIdtxt").toString();
	de = dynabean.getProperty("MsgDescriptiontxt").toString();
	Customeridtxt=dynabean.getProperty("Customeridtxt").toString();
	stid = dynabean.getProperty("Statetxt").toString();
	siteid = dynabean.getProperty("Sitetxt").toString();
	ri = dynabean.getProperty("MsgIdtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
String stdmsg = AdminUtil.fillMaster("TBL_STANDARD_MESSAGE",rn);
String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
String statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
if(!stid.equals(""))
{ sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER",siteid,stid);
}
%>
<script type="text/javascript">
function ChangeState()
{

var list = document.forms[0].Customeridtxt;
var ApplicationId = list.options[list.selectedIndex].value;

  if(ApplicationId=="selectone")
   {
   
   document.forms[0].Statetxt.disabled=false;
   document.forms[0].Sitetxt.disabled=false;
   }
   else
   {
   document.forms[0].Statetxt.selectedIndex=0;
   document.forms[0].Sitetxt.selectedIndex=0;
   document.forms[0].Statetxt.disabled=true;
   document.forms[0].Sitetxt.disabled=true;
   }


}
function findSite() 
{ 

	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSite&AppId="+ApplicationId);
}

function showSite(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].Sitetxt.options.length = 0;
	document.forms[0].Sitetxt.options[0] = new Option("--ALL--","ALL");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     	document.forms[0].Sitetxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    }
	     	
    	 		
    	 				
        }
}
function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.forms[0].MsgIdtxt.value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_SENT_MESSAGE_DETAIL&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{  
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");	
		var divdetails = document.getElementById("msgdetails");
		divdetails.innerHTML = "";
		
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'><tr align='center' height='20'><th colspan=3 class='detailsheading' width='300'>Message Displayed To Customer</th></tr>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='230'>Customer</th>"
		str +="<th class='detailsheading' width='40'>D</th></tr>";
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
	     		str+="<td align='left'>" + item.getElementsByTagName("cname")[0].firstChild.nodeValue + "</td>"	  
	     		//str+="<td align='left'>" + item.getElementsByTagName("cemail")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to edit the record' "
	     		str+="onClick=DelMsgDetails('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	
	   	 		
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
{   alert("");
	 var req = newXMLHttpRequest();
    
     var MsgId = msgid;
	 req.onreadystatechange = getReadyStateHandler(req, showDelMsgDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_DELETE_MESSAGE_DETAIL&AppId="+MsgId);
}
function showDelMsgDetails(dataXml)
{alert("");
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
<body onLoad="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<%

String str=(String)session.getAttribute("msg");
//System.out.println("str"+str);
String msgdesc=(String)request.getParameter("msgdes");
String msgid=(String)request.getParameter("msgid");
//System.out.println("msgid"+msgid);
//System.out.println("msgdesc"+msgdesc);
%>
<form action="<%=request.getContextPath()%>/SendMessage.do" name="SendMessage" id="SendMessage" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Send Message</th>
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
			<td id="t_street_address">&nbsp;Message Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="MsgDescriptiontxt" name="MsgDescriptiontxt"  class="BoxBorder" ><%=msgdesc%></textarea></td>
		</tr>	
		
		
		
		<tr class="bgcolor"> 
			<td id="t_street_address" colspan=2 align="center">Selection Criteria:</td>
			
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
			   
			   <select name="Customeridtxt" id="Customeridtxt" class="ctrl"  class="tabtextnormal" onchange="ChangeState()">
			   
              <option value="selectone" selected="selected"  >--Select Customer--</option>
               <option value="ALL">--ALL--</option>
               <%=custid %>
            </select>
            </td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address" colspan=2 align="center">OR</td>
			
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
			<td class="bgcolor">
			<select size="1" name="Statetxt" id="Statetxt" class="tabtextnormal" onchange="findSite()">
		             <option value="selectone" selected="selected">-- Select State --</option>
		            
		             <%=statename%>
		         </select>
		         </td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
			<select size="1" name="Sitetxt" id="Sitetxt" class="tabtextnormal">
			<option value="selectone" selected="selected">-- Select Site --</option>
		            
		             <%=sitename%>
		         </select>
		         <input type="button" name="addlist" value="Add To List">
		         </td>
		</tr>				
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<% str=(String)session.getAttribute("msg");%>
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
			<input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=msgid%>" />	
			<input type="hidden" name="Admin_Input_Type" value="SendMessage" />	
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