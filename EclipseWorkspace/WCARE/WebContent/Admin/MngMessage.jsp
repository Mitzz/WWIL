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

<script type="text/javascript">

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
{   alert("");
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_DETAIL_MESSAGE_BY_DATE&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{   
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");	
		var divdetails = document.getElementById("msgdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='230'>Description</th><th class='detailsheading' width='100'>From Date</th><th class='detailsheading' width='100'>To Date</th>"
		str +="<th class='detailsheading' width='40'>E</th><th class='detailsheading' width='40'>D</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   alert("");		
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
	     		str+="onClick=DelMsgDetails('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	
	   	 		document.forms[0].MsgIdtxt.value = "";
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

</html>