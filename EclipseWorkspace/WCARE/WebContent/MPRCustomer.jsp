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
<%@ page import="com.enercon.security.utils.SecurityUtils;"%>

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
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/doubleList.js"></script>


<% 

response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String CustomerIdSelList = "";
String Sitename = "";
String stateid = "";
String custid ="";
StringBuffer buffer = new StringBuffer();
SecurityUtils secUtil = new SecurityUtils();
String got="0";
 	if(session.getAttribute("LoginType").equals("E"))
		 custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",CustomerIdSelList);
 	else
 	{ 
     	List tranList1 = new ArrayList(); 
     	tranList1 = (List)session.getAttribute("custtypee");
     	 if(tranList1.size()==0)
     	 {
     		 tranList1=secUtil.getcustomerdetails(userid);
     	 }
     	      	
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
	   	 
	   	  buffer.append("<OPTION VALUE='" + customerid + "' >" + customername + "</OPTION>");
	     }
	     custid=buffer.toString();	     
	   }

%>
<script type="text/javascript">
var selectedState="";
var selectedSite="";
var selectedCustomer="";

function createListCustomer(){  
   
   var selected = new Array();
   var mySelect = document.getElementById("CustomerIdSelList");
   
   var z = 0;
   var custtransactions = "";
   for(var i = 0; i < mySelect.length; i++)
   {
      if(custtransactions.length > 0)
	  {
	      custtransactions = custtransactions + ",'" + mySelect.options[i].value + "'";
	  }
	  else
	  {
	  	  custtransactions = "'" + mySelect.options[i].value + "'";	  	  
	  }	  
	  z = 1;
   }
   selectedCustomer=custtransactions;
   
   if(custtransactions.length == 10)
	{
		custtransactions = custtransactions + ",'.'";
	}	
   if (z == 1)
   {   
   		document.getElementById("CustTransactions").value = custtransactions;
   		// alert(selectedCustomer);
   		findState();
   		
   }
   else
   {
   		alert("No Customer selected");
	   	return false;
   }
}

function createlistSite()
{  
   
   var selected = new Array();
   var mySelect = document.getElementById("StateSelList");
   
   var z = 0;
   var transactions = "";
   for(var i = 0; i < mySelect.length; i++)
   {
      if(transactions.length > 0)
	  {
	      transactions = transactions + ",'" + mySelect.options[i].value + "'";
	  }
	  else
	  {
	  	  transactions = "'" + mySelect.options[i].value + "'";	  	  
	  }	  
	  z = 1;
   }
   selectedState=transactions;
   
   if(transactions.length == 10)
	{
		transactions = transactions + ",'.'";
	}	
   if (z == 1)
   {   
   		document.getElementById("Transactions").value = transactions;
   		
   		findSite();
   		
   }
   else
   {
   		alert("No State selected");
	   	return false;
   }
}

function createlist()
{   
   var selected = new Array();
   var mySelect = document.getElementById("SiteSelList");
   var z = 0;
   var transactions1 = "";
   
   for(var i = 0; i < mySelect.length; i++)
   {
      if(transactions1.length > 0)
	  {
	      transactions1 = transactions1 + ",'" + mySelect.options[i].value + "'";
	  }
	  else
	  {
	  	  transactions1 = "'" + mySelect.options[i].value + "'";	  	  
	  }	  
	  z = 1;
   }
   selectedSite=transactions1;
}

function loadExternal(url) {
  
    var list1=document.forms[0].SiteSelList;
    var list2=document.forms[0].StateSelList;
    var list3=document.forms[0].CustomerIdSelList;
    
    var site;
    var state;
    var customer;
    
    /// url="Billingdetail.jsp";
    /* url="ExportCustomerMPR.jsp"; */
    url="Change/ExportCustomerMPRChange.jsp";
    
    var fd=document.forms[0].FromDatetxt.value;	
    var td=document.forms[0].ToDatetxt.value;
          
     createlist();
          
     state=selectedState;
     site=selectedSite;
     customer=selectedCustomer;
     
     
     var w = document.forms[0].StateSelList.selectedIndex; 
     var sname = "";
     
     for(w=0;w<=document.forms[0].StateSelList.length-1;w++)
     {
     	sname = sname+","+document.forms[0].StateSelList.options[w].text;  
     	    	
     }     
     
     var list2=document.forms[0].CustIdtxt;
     
	 // url= url+"?id="+list5.options[list5.selectedIndex].value+"&fd="+fd+"&td="+td+"&site="+site+"&state="+state+"&sname="+sname;
	 url= url+"?id="+customer+"&fd="+fd+"&td="+td+"&site="+site+"&state="+state+"&sname="+sname;
	 
	 location.href=url;
   	
  }		
		
</script>

<script type="text/javascript">
function checkWholeForm() {
    var why = "";
     	why += checkDropdown(document.forms[0].CustomerIdSelList.selectedIndex,"Select Customer");
     	
     	if(document.forms[0].CustomerIdSelList.length==0) 
     	{
     		alert("Please select Customer..");    	
     		return false;
     	}
     	else if(document.forms[0].StateSelList.length==0) 
     	{
     		alert("Please select State..");    	
     		return false;
     	}
     	else if(document.forms[0].SiteSelList.length==0)
     	{
     		alert("Please select Site..");
     		return false;
     	}
     	
		var ss1=document.MPRCustomer.FromDatetxt;     	
     	var ss2=document.MPRCustomer.ToDatetxt;
     	
     	why += checkUsername(ss1,"From Date");
     	why += checkUsername(ss2,"To Date");
     	
     	why += checkDropdown(document.forms[0].StateSelList.selectedIndex,"Select State");     
     	why += checkDropdown(document.forms[0].SiteSelList.selectedIndex,"Select State");       	
     	
    if (why != "") {
       	// alert(document.forms[0].StateSelList.selectedIndex);       
       	alert("Blank field cannot be processed, Please select all field...");	
       return false;
    }

    else
    {    
    	loadExternal('url');
    	return true;
    }
}
function checkDropdown(choice,msg) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list "+msg+".\n";
    }    
return error;
}  
function checkUsername (strng,msg) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "You didn't enter a " +msg +".\n";
 		}
 		return error;
 }      	
</script>
<script type="text/javascript">

/***********************************************
* IFrame SSI script II- © Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
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


function findState() 
{	 
	 var req = newXMLHttpRequest();	 
	 // var list = document.forms[0].CustIdtxt;
     // var ApplicationId = list.options[list.selectedIndex].value;
     // var list = document.forms[0].CustomerIdSelList;
     
     var ApplicationId = document.getElementById("CustTransactions").value
     
	 req.onreadystatechange = getReadyStateHandler(req, showState);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getStateByCustomerID&AppId="+ApplicationId);
}

function showState(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].StateIdtxt.options.length = 0;
	document.forms[0].StateIdtxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     	document.forms[0].StateIdtxt.options[I] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    }    	 		
    	 				
        }
        
}	


function findSite() 
{ 
	 
	 var req = newXMLHttpRequest();	 
	 
	 // var ApplicationId = document.getElementById("Transactions").value	 
     // var list = document.forms[0].CustIdtxt;  
     // var ApplicationId = list.options[list.selectedIndex].value+";;"+document.getElementById("Transactions").value;
     
     var ApplicationId = document.getElementById("CustTransactions").value+";;"+document.getElementById("Transactions").value;
     // alert(ApplicationId);
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findSiteByState_custID&AppId="+ApplicationId);
}

function showSite(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sname")[0].firstChild;
	     		
	     	if (nname != null)
	     	{
	     	 document.forms[0].Sitetxt.options[I] = new Option(item.getElementsByTagName("sname")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    
   	 	    }
    	 				
        }
        
}

	
 function findSite1() 
{ 

	 var req = newXMLHttpRequest();	 
	 // var list = document.forms[0].StateIdtxt;
   	 // var ApplicationId = list.options[list.selectedIndex].value;
     // var list1 = document.forms[0].CustIdtxt;
     // var ApplicationId = list.options[list.selectedIndex].value+","+list1.options[list1.selectedIndex].value; 
     var ApplicationId = document.getElementById("Transactions").value;
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSiteByCustID&AppId="+ApplicationId);
}

function showSite1(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].Sitetxt.options.length = 0;
	document.forms[0].Sitetxt.options[0] = new Option("--ALL Site--","ALL");
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

</script>


</head>
 <body  valign="top"  align="center"  onLoad="loadintoIframe('ifrm','Blank.jsp')">
        
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/MPRCustomerxyz.do" method="post" name="MPRCustomer" body leftmargin="0" topmargin="0" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">MPR Report</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<%

//System.out.println("Type"+request.getParameter("Type"));
String Type=request.getParameter("Type");

  

%>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>	
		
		<%if(got.equals("0")){ %>
<!-- 	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
				<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl" onchange="findState()">
		            <option value="">--Make a Selection--</option>
		            <%=custid %>
		        </select>
			</td>
		</tr>
 -->
 		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Customer</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="CustIdtxt" id="CustIdtxt" class="ctrl" multiple="multiple" style="width: 250px" >
					            <%=custid%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.CustIdtxt, this.form.CustomerIdSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="CustomerIdSelList" id="CustomerIdSelList" class="ctrl" multiple="multiple" style="width: 250px"  >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.CustIdtxt, this.form.CustomerIdSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.CustomerIdSelList, this.form.CustIdtxt)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.CustomerIdSelList, this.form.CustIdtxt)" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>
		<%}else{ %>	
<!-- 	-->
		<tr class="bgcolor"> 
			<td id="t_street_address">Customer:</td>
			<td class="bgcolor">
			<select size="1" id="CustomerIdSelList" name="CustomerIdSelList"  class="ctrl" font:color="BLack" disabled>
		           <option value="">--Make a Selection--</option>
		           <%=custid %>
		        </select>				
			</td>
		</tr>
<!--  -->
		<script>
			document.forms[0].CustomerIdSelList.selectedIndex=1;
			findState();
		</script>
	  <%} %>	
	  
	  	<tr class="bgcolor"> 			
				<td class="bgcolor" colspan="2" align="center">
					<input  type="button" onClick="createListCustomer();" value="Generate State">
					
				</td>
		</tr>
	  		
<!-- 	
		<tr class="bgcolor">
			<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
			<td class="bgcolor"><select size="1" id="StateIdtxt" name="StateIdtxt" class="ctrl" onchange="findSite()">
				<option value="">--Make a Selection--</option>
				<%=stateid%>
			</select></td>
		</tr>
-->

		<tr class="bgcolor" align="left">
			<td width="100" height="25" align="left" id="t_street_address">Select State</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="StateIdtxt" id="StateIdtxt" class="ctrl" multiple="multiple" style="width: 250px"  >
					            <%=stateid%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.StateIdtxt, this.form.StateSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="StateSelList" id="StateSelList" class="ctrl" multiple="multiple" style="width: 250px" >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.StateIdtxt, this.form.StateSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.StateSelList, this.form.StateIdtxt)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.StateSelList, this.form.StateIdtxt)" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>
			
		<tr class="bgcolor"> 			
				<td class="bgcolor" colspan="2" align="center">
					<input  type="button" onClick="createlistSite()" value="Generate Site">
					
				</td>
		</tr>
		
		<tr class="bgcolor" align="left">
			<td width="100" height="25" align="left" id="t_street_address">Select Site</td>
				<td width="450">
					<table border="0" width="100%">
						<tr>
							<td rowspan="4">
								<select size="10" name="Sitetxt" id="Sitetxt" class="ctrl" multiple="multiple" style="width: 250px" >
						          	<%=Sitename%>
						        </select>
						    </td>
						    <td>
						        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.Sitetxt, this.form.SiteSelList)"  alt="Move selected" />
						    <td>
						   	<td rowspan="4">
						        <select size="10" name="SiteSelList" id="SiteSelList" class="ctrl" multiple="multiple" style="width: 250px" >
						        </select>
						    </td>
						</tr>
						<tr>
							<td>
						        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.Sitetxt, this.form.SiteSelList)" />
						    <td>
						</tr>
						<tr>
							<td>
						        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.SiteSelList, this.form.Sitetxt)" />
						    <td>
						</tr>
						<tr>
							<td>
						        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.SiteSelList, this.form.Sitetxt)" />
						    <td>
						</tr>
					</table>
				</td>
			</tr>
		
<!--
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
			<select size="1" name="Sitetxt" id="Sitetxt" class="ctrl">
			<option value="selectone" selected="selected">-- Select Site --</option>
		            
		             <%=Sitename%>
		         </select>
		         
		         </td>
		</tr>	
-->

			<tr class="bgcolor">
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
			<a href="javascript:void(0)" id="dc"
			   onClick="if(self.gfPop)gfPop.fPopCalendar(document.MPRCustomer.FromDatetxt);return false;"><img
				class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
				border="0" alt=""></a></td>
										</tr>
		<tr class="bgcolor">
		<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
		<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
		     <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.MPRCustomer.ToDatetxt);return false;">
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
			 <input type="hidden" name="Type" id="Type" value=<%=Type%>>
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
			<input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="checkWholeForm();" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset">
									<input type="hidden" name="Transactions" id="Transactions" value="">
									<input type="hidden" name="Transactions1" id="Transactions1" value="">
									<input type="hidden" name="CustTransactions" id="CustTransactions" value="">
		</td>
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
<table  name="tblifrm"  id=="tblifrm" width="700"  valign="top" align="center" >
				<tr >
						<td>
							<iframe id="ifrm" name="ifrm" src="" width="100%"  valign="top" align="center" scrolling="no" frameborder="0"></iframe> 
			          
						</td>
			</tr>
</table>	  
</body>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>