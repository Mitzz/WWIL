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

<%@ page import="java.util.*" %>

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
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"> </script>

<%

//String dd = "";
//session.removeAttribute("dynabean");
//DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
//if(dynabean != null){		
//	dd = dynabean.getProperty("Datetxt").toString();
//	si = dynabean.getProperty("SiteIdtxt").toString();
//	session.removeAttribute("dynabean");
//}

%>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";
String custid ="";
StringBuffer buffer = new StringBuffer();
String got="0";
 if(session.getAttribute("LoginType").equals("E"))
	 custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
 else
 { 
     List tranList1 = new ArrayList(); 
	tranList1 = (List)session.getAttribute("custtype"); 
   if(tranList1.size()==1)
   {
	   got="1";  
   }
    for (int j=0; j <tranList1.size(); j++)
     {
    	Vector v11 = new Vector();
		 v11 = (Vector)tranList1.get(j);
   	  String customerid=(String)v11.get(2);
   	 String customername=(String)v11.get(3);
   	 //System.out.println("customername"+customername);
   	   buffer.append("<OPTION VALUE='" + customerid + "'>" + customername + "</OPTION>");
     }
    custid=buffer.toString();
    
 }
%>
<script type="text/javascript">

function findApplication() 
{



     
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].CustIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findDGRMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");	
		var divdetails = document.getElementById("DGRdetails");
		divdetails.innerHTML = "";
		var str = "<hr><table width='300' cellspacing='0' cellpadding='0'>"
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='80%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='150'>WEC Description</th><th class='detailsheading' width='80'>Location No.</th>"
		str +="<th class='detailsheading' width='30'>Staus</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	   
			    if (item.getElementsByTagName("desc")[0].firstChild.nodeValue == ".")
		     		str+="<td align='left'></td>"	  
		     	else
		     		str+="<td align='left'>" + item.getElementsByTagName("desc")[0].firstChild.nodeValue + "</td>"
	     		if (item.getElementsByTagName("locno")[0].firstChild.nodeValue == ".")
		     		str+="<td align='left'></td>"	  
		     	else
		     		str+="<td align='left'>" + item.getElementsByTagName("locno")[0].firstChild.nodeValue + "</td>"
	     			  
	     		if (item.getElementsByTagName("wecstatus")[0].firstChild.nodeValue == 1){
    	 			str+="<td align='left'><input type='checkbox' id='approved"+I+"' name='approved' "
		     		str+="onclick=updateWECStatus('" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"',"
		     		str+="approved"+I+") style='text-align:right' /></td></tr>"
    	 		}
    	 		else{
    	 			str+="<td align='left'><input type='checkbox' id='approved"+I+"' name='approved' "
		     		str+="onclick=updateWECStatus('" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"',"
		     		str+="approved"+I+") style='text-align:right' checked /></td></tr>"
    	 		}
	   	 	}   	 	
		}		
		divdetails.innerHTML = str;
}
function updateWECStatus(wecid,chkbox) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById(chkbox.id).checked + "," + wecid;
	 req.onreadystatechange = getReadyStateHandler(req, UpdateWECStat);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_UpdateWECStat&AppId="+ApplicationId);
}
function UpdateWECStat(dataXml)
{

}
function loadExternal(url) {
  
    var fd,td;	
    
    url="CustomizeView.jsp";
   
     fd=document.forms[0].FromDatetxt.value;	
     td=document.forms[0].ToDatetxt.value;
     
      var list2=document.forms[0].CustIdtxt;
				
	   url= url+"?id="+list2.options[list2.selectedIndex].value+"&fd="+fd+"&td="+td;
	   					 
	   window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');  
	   					
}
</script>
<script type="text/javascript">

/***********************************************
* IFrame SSI script II- � Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
* Visit DynamicDrive.com for hundreds of original DHTML scripts
* This notice must stay intact for legal use
***********************************************/

//Input the IDs of the IFRAMES you wish to dynamically resize to match its content height:
//Separate each ID with a comma. Examples: ["myframe1", "myframe2"] or ["myframe"] or [] for none:
var iframeids=["ifrm"]

//Should script hide iframe from browsers that don't support this script (non IE5+/NS6+ browsers. Recommended):
var iframehide="yes"

var getFFVersion=navigator.userAgent.substring(navigator.userAgent.indexOf("Firefox")).split("/")[1]
var FFextraHeight=parseFloat(getFFVersion)>=0.1? 16 : 0 //extra height in px to add to iframe in FireFox 1.0+ browsers

function resizeCaller() {
var dyniframe=new Array()
for (i=0; i<iframeids.length; i++){
if (document.getElementById)
resizeIframe(iframeids[i])
//reveal iframe for lower end browsers? (see var above):
if ((document.all || document.getElementById) && iframehide=="no"){
var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i])
tempobj.style.display="block"
}
}
}

function resizeIframe(frameid){
var currentfr=document.getElementById(frameid)
if (currentfr && !window.opera){
currentfr.style.display="block"
if (currentfr.contentDocument && currentfr.contentDocument.body.offsetHeight) //ns6 syntax
currentfr.height = currentfr.contentDocument.body.offsetHeight+FFextraHeight; 
else if (currentfr.Document && currentfr.Document.body.scrollHeight) //ie5+ syntax
currentfr.height = currentfr.Document.body.scrollHeight;
if (currentfr.addEventListener)
currentfr.addEventListener("load", readjustIframe, false)
else if (currentfr.attachEvent){
currentfr.detachEvent("onload", readjustIframe) // Bug fix line
currentfr.attachEvent("onload", readjustIframe)
}
}
}

function readjustIframe(loadevt) {
var crossevt=(window.event)? event : loadevt
var iframeroot=(crossevt.currentTarget)? crossevt.currentTarget : crossevt.srcElement
if (iframeroot)
resizeIframe(iframeroot.id);
}

function loadintoIframe(iframeid, url){
if (document.getElementById)
document.getElementById(iframeid).src=url
}

if (window.addEventListener)
window.addEventListener("load", resizeCaller, false)
else if (window.attachEvent)
window.attachEvent("onload", resizeCaller)
else
window.onload=resizeCaller




</script>
<script type="text/javascript">
function checkWholeForm() {
    var why = "";
     	why += checkDropdown(document.forms[0].CustIdtxt.selectedIndex);
     	
     	var ss1=document.CustomizeReport.FromDatetxt;
     	var ss2=document.CustomizeReport.ToDatetxt;
     	why += checkUsername(ss1);
     	why += checkUsername(ss2);
     	
    if (why != "") {
       alert(why);
       return false;
    }
    else
    {
    loadExternal('url');
    return true;
    }
}
function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkUsername (strng) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "You didn't enter a Date.\n";
 		}
 		return error;
 }      	
</script>

</head>
 <body valign="top" align="center" onLoad="loadintoIframe('ifrm','Blank.jsp')">
        
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/CustomizeReport.do" method="post" name="CustomizeReport" body leftmargin="0" topmargin="0" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Customize Report</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<%

//System.out.println("Type"+request.getParameter("Type"));
//String Type=request.getParameter("Type");

  

%>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>	
		<%if(got.equals("0")){ %>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
				<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl" >
		            <option value="">--Make a Selection--</option>
		            <%=custid %>
		        </select>
			</td>
		</tr>
		<%}else{ %>	
		<tr class="bgcolor"> 
			<td id="t_street_address">Customer:</td>
			<td class="bgcolor">
			<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl">
		            <%=custid %>
		        </select>
				
			</td>
		</tr>	
	  <%} %>	
		<tr class="bgcolor">
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
			<a href="javascript:void(0)" id="dc"
			   onClick="if(self.gfPop)gfPop.fPopCalendar(document.CustomizeReport.FromDatetxt);return false;"><img
				class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
				border="0" alt=""></a></td>
										</tr>
		<tr class="bgcolor">
		<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
		<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
		     <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.CustomizeReport.ToDatetxt);return false;">
	          <img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></td>
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
		  <input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="checkWholeForm()" />
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
<table  name="tblifrm"  id=="tblifrm" width="1200"  valign="top" align="center" >
				<tr>
						<td>
							<iframe id="ifrm" name="ifrm" src="" width="100%" valign="top" align="center" scrolling="no" frameborder="0"></iframe> 
			          
						</td>
			</tr>
</table>  
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>