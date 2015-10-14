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
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
int status = 0;
String fdid = "";
String stid = "";
String fdshort="" ;
String fddesc="";
String fdsite = "";	
//session.removeAttribute("dynabean");
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){	
	stid = dynaBean.getProperty("Statetxt").toString();
	fdsite = dynaBean.getProperty("FDSitetxt").toString();	
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		fdshort = "";
		fddesc = "";
		fdid = "";
		status = 0;
	}else{
		fdshort = dynaBean.getProperty("FDShorttxt") == null ? "" : dynaBean.getProperty("FDShorttxt").toString() ;
		fddesc = dynaBean.getProperty("FDDesctxt") == null ? "" : dynaBean.getProperty("FDDesctxt").toString() ;
	  	if(dynaBean.getProperty("FDStatustxt") == null || dynaBean.getProperty("FDStatustxt").toString().equals("1")){
	  		status = 0;
		}
		else if(dynaBean.getProperty("FDStatustxt").toString().equals("2")){
			status = 1;
		}
  		fdid = dynaBean.getProperty("FDIdtxt") == null ? "" : dynaBean.getProperty("FDIdtxt").toString();
	}
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String sitename = "";
String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();

//if(roleid.equals("0000000001"))
	//sitename = AdminUtil.fillMaster("VIEW_SITE_MASTER","");
//else
	//sitename = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",userid);


String statename = "";//AdminUtil.fillMaster("TBL_STATE_MASTER","");

if(roleid.equals("0000000001"))
	statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
else
	statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",stid,session.getAttribute("loginID").toString());



if(!statename.equals("") && roleid.equals("0000000001"))
{ 
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS1",fdsite,stid+","+session.getAttribute("loginID").toString());
}
else
{
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER",fdsite,stid);
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS",fdsite,stid+","+session.getAttribute("loginID").toString());
} 


%>
<script type="text/javascript">

function createlist()
{   
   var selected = new Array();
   var mySelect = document.getElementById("SiteSelList");
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
   if(transactions.length == 10)
	{
		transactions = transactions + ",'.'";
	}	
   if (z == 1)
   {
   		document.getElementById("Transactions").value = transactions;
   		
   		findWec();
   		
   		
   }
   else
   {
   		alert("No Site selected");
	   	return false;
   }
}

function findSite() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     ApplicationId=ApplicationId+","+document.forms[0].RoleId.value;     
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSiteBYRIGHTS&AppId="+ApplicationId);
}

function showSite(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].FDSitetxt.options.length = 0;
	document.forms[0].FDSitetxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].FDSitetxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
 	    }
    }
}
function findFDMask() 
{ 
	 var req = newXMLHttpRequest();	 
	 
	 var list = document.forms[0].FDSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showFDMask);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getEBMask&AppId="+ApplicationId);
}

function showFDMask(dataXml)
{   
	var cart = dataXml.getElementsByTagName("maskhead")[0];
	var items = cart.getElementsByTagName("maskcode");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("stcode")[0].firstChild;
	     	if (nname != null)
	     	{
	     	 document.forms[0].FDDesctxt.value= "IEIL-"+item.getElementsByTagName("stcode")[0].firstChild.nodeValue+"-"+item.getElementsByTagName("sicode")[0].firstChild.nodeValue+"-";	          	 				
	     	 document.forms[0].FDShorttxt.value= "IEIL-"+item.getElementsByTagName("stcode")[0].firstChild.nodeValue+"-"+item.getElementsByTagName("sicode")[0].firstChild.nodeValue+"-";	          	 				
	        }		
        }
}

function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].FDSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findFDMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{       
	     var cart = dataXml.getElementsByTagName("fdmaster")[0];
	     var items = cart.getElementsByTagName("fdcode");	
		var divdetails = document.getElementById("fddetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>"
		str+="<th class='detailsheading' width='200'>Description</th><th class='detailsheading' width='200'>Short Description</th>"
		str+="<th class='detailsheading' width='60'>Status</th>"
		str+="<th class='detailsheading' width='30'>Edit</th><th class='detailsheading' width='40'>Copy</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("fdname")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("fdshort")[0].firstChild.nodeValue + "</td>"	  
	     		if (item.getElementsByTagName("fdstatus")[0].firstChild.nodeValue == 1)
	     		{
	     			str+="<td align='left'>Active</td>"
	     		}else
	     		{
	     			str+="<td align='left'>De-active</td>"
	     		}
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue + "','E')></td>"
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue + "','C')></td></tr>"
	   	 	}   	 	
		}
    	if(document.forms[0].FDIdtxt.value!="")
	 		{}
 		else
 	 		document.forms[0].FDIdtxt.value = "";
		divdetails.innerHTML = str;
}
function findDetails(fdid,type)
{    
	 var req = newXMLHttpRequest();
	 document.forms[0].copytxt.value =type;
	 alert(type);
     var ApplicationId = fdid;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findFDMasterByID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("fdmaster")[0];
	var items = cart.getElementsByTagName("fdcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdname")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].FDDesctxt.value = item.getElementsByTagName("fdname")[0].firstChild.nodeValue;
   	 		document.forms[0].FDShorttxt.value = item.getElementsByTagName("fdshort")[0].firstChild.nodeValue;
   	 		document.forms[0].FDIdtxt.value = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;  
   	 		 if(item.getElementsByTagName("fdstatus")[0].firstChild.nodeValue=="1")
   	 		 {
  		       document.forms[0].FDStatustxt.checked=false;
	          }
	        else
	          document.forms[0].FDStatustxt.checked=true;
	          
	        if(document.forms[0].copytxt.value=="E")
	        {
 	 			document.forms[0].Submit.value="Update";	
	 	 	}
   	 		else
 	 		{
 	 		    document.forms[0].FDShorttxt.value = "";
 	 		    document.forms[0].Submit.value="Submit";
 	 		} 	
   	 	}
   	 	else{
   	 		document.forms[0].FDDesctxt.value = "";
   	 		document.forms[0].FDShorttxt.value = "";
   	 		document.forms[0].FDIdtxt.value ="";
   	 	    document.forms[0].FDStatustxt.checked=false;
	        	 		
   	 	} 			
 	}
}
function chkFunction()
{ 
	        
}

function confirmation()
{   
	// if(document.forms[0].Submit.value=='Update')
	if(document.forms[0].FDIdtxt.value!="")
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
<body onload="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/FederMaster.do" method="post" onSubmit="return chkFunction(),confirmation()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Feder Master</th>
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
			<td id="t_company" width="219">Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findSite()">
					<option value="">--Make a Selection--</option>
				            <%=statename%>
				</select>
			</td>
		</tr>
					
  	
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Site Name:</td>
		  <td bgcolor="#ffffff" width="85%">
		    <select name="FDSitetxt" id="FDSitetxt" class="ctrl" onChange="findFDMask();findApplication()">
              <option value="">--Make a Selection--</option>
				   <%=sitename%>     
            </select>
		  </td>
		</tr>

<!--
		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Site</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="FDSitetxt" id="FDSitetxt" class="ctrl" multiple="multiple" style="width: 250px" >
					          
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.FDSitetxt, this.form.SiteSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="SiteSelList" id="SiteSelList" class="ctrl" multiple="multiple" style="width: 250px" >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.FDSitetxt, this.form.SiteSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.SiteSelList, this.form.FDSitetxt)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.SiteSelList, this.form.FDSitetxt)" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>
-->

		<tr bgcolor="#ffffff">
			<td width="15%" id="t_company">Name</td>
			<td width="85%" valign="top"><input name="FDDesctxt" id="FDDesctxt" value="<%=fddesc%>" size="25" class="ctrl" type="text" /></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address">Short Description</td>
			<td bgcolor="#ffffff"><input name="FDShorttxt" id="FDShorttxt" value="<%=fdshort%>" size="50" maxlength="100" class="ctrl" type="text" />
		</tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Deactivate</td>
		  <td bgcolor="#ffffff">
		  <%if (status == 0){ %>
				<input type="checkbox" name="FDStatustxt" id="FDStatustxt" value="1" />
			<%}else{ %>
				<input type="checkbox" name="FDStatustxt" id="FDStatustxt" checked value="2" />
			<%} %>
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
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="FDMaster" />		
		<input type="hidden" name="FDIdtxt" value="<%=fdid %>" />
		<input type="hidden" name="RoleId" value="<%=roleid %>"/>
		<input name="Reset" value="Cancel" class="btnform" type="reset">
		<input type="hidden" name="copytxt" id="copytxt" />
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
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="600"><tbody>
			<tr>
				<td >
					<div id="fddetails"></div>
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