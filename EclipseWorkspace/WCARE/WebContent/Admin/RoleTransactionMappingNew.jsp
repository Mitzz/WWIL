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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/doubleList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>

<%
String rid = "";
String existstrid = "";
String newtrid = "";
String Transactions = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	rid = dynabean.getProperty("RoleNametxt").toString();
	//existstrid = dynabean.getProperty("tranId").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		newtrid = "";	
	}else{
		//newtrid = dynabean.getProperty("Transactiontxt").toString();
	}
	Transactions = dynabean.getProperty("Transactions").toString();
	session.setAttribute("SubmitMessage","");	
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",rid);
//String tranname = AdminUtil.fillWhereMaster("GET_TRANSACTION_DESCRIPTION_NON_ASSIGN_ROLE",newtrid,rid);
%>
<script type="text/javascript">
function createlist()
{   
   var selected = new Array();
   var mySelect = document.getElementById("TransactionSeltxt");
   var z = 0;
   var transactions = "";
   for(var i = 0; i < mySelect.length; i++)
   {
      if(transactions.length > 0)
	  {
	      transactions = transactions + "," + mySelect.options[i].value;
	  }
	  else
	  {
	  	  transactions = mySelect.options[i].value;
	  }	  
	  z = 1;
   }
   if(transactions.length == 10)
	{
		transactions = transactions + ",.";
	}	
   if (z == 1)
   {
   		document.getElementById("Transactions").value = transactions;
   		return true;
   }
   else
   {
   		alert("No transactions selected");
	   	return false;
   }
}

function getReport()
{
	var blnSave=false;
	blnsave = createlist();
	if (blnsave == true)
	{
		blnsave = false;
	    blnSave = validateForm('Role ',document.getElementById("RoleNametxt").value,'M','');
	     if ( blnSave == true ) {	        
			return true;
	     } else {
	        return false;
	     }
	}
	else
		return false;
}
function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].RoleNametxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getfullrolemaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("rolemaster")[0];
	var items = cart.getElementsByTagName("rolecode");	
		var divdetails = document.getElementById("roletrandetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Transactions</th>"
		str +="<th class='detailsheading' width='170'>Main Menu</th>"
		str +="<th class='detailsheading' width='170'>Sub Menu</th>"
		str +="<th class='detailsheading' width='170'>Last Menu</th>"
		str +="<th class='detailsheading' width='40'>D</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("roletranid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	 
	     		var u2 = item.getElementsByTagName("under2")[0].firstChild;
		   		var u3 = item.getElementsByTagName("under3")[0].firstChild;	     		
	     		str+="<td>" + item.getElementsByTagName("tranname")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td>" + item.getElementsByTagName("under1")[0].firstChild.nodeValue + "</td>"
	     		if (u2 != null){   			     		
		     		str+="<td>" + item.getElementsByTagName("under2")[0].firstChild.nodeValue + "</td>"   			     		
		     	}else{
		     		str+="<td></td>"   			     		
		     	}
	     		if (u3 != null){   			     		
		     		str+="<td>" + item.getElementsByTagName("under3")[0].firstChild.nodeValue + "</td>"   			     		
		     	}else{
		     		str+="<td></td>"   			     		
		     	}		     			     			     		
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to delete the record' "
				str+="onClick=confirmation('" + item.getElementsByTagName("roletranid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].tranId.value = ""; 
	   	 	}   	 	
		}
		str += "</tbody></table>"
		divdetails.innerHTML = str;  
}
function confirmation(list) {
	var answer = confirm("Are you sure you want to Delete?")
	if (answer){
		var req = newXMLHttpRequest();
	 	req.onreadystatechange = getReadyStateHandler(req, delreturn);
	 	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 	req.send("Admin_Input_Type=delroletranmap&AppId="+list);
	}	
}
function delreturn(dataXml){
	findApplication();
	fillTransaction() ;
}

function fillTransaction() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].RoleNametxt;
     var LocId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showNonTransaction,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getnonrole&AppId="+LocId);
}
function showNonTransaction(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("tranmaster")[0];
	var items = cart.getElementsByTagName("trancode");
	document.forms[0].Transactiontxt.options.length = 0;
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("tranid")[0].firstChild;
     	if (nname != null)
     	{
   	 		document.forms[0].Transactiontxt.options[I + 1] = new Option(item.getElementsByTagName("tranname")[0].firstChild.nodeValue + " (" + item.getElementsByTagName("trandesc")[0].firstChild.nodeValue + ")",item.getElementsByTagName("tranid")[0].firstChild.nodeValue);   	 		
   	 	}
	}
}

</script>
</head>
<body onload="findApplication(),fillTransaction()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/roletranmasternew.do" onSubmit="return getReport()" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Role Transaction Assign Master</th>
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
		<tr class="bgcolor" align="left">
			<td id="t_street_address" height="30" align="left">Select Role</td>
			<td >
				<select size="1" id="RoleNametxt" name="RoleNametxt" class="ctrl" onChange="findApplication(),fillTransaction()">
		            <option value="">--Make a Selection--</option>
		            <%=rolename%>			            
		        </select>				        
			</td>
		</tr>				
		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Transaction</td>
			<td width="450">
				<select size="20" name="Transactiontxt" id="Transactiontxt" class="ctrl" multiple="multiple" style="width: 500px" >
		             
		        </select>
			</td>
		</tr>	
		<tr align="center" class="bgcolor">
			<td></td>
			<td >
				<input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.Transactiontxt, this.form.TransactionSeltxt)"  alt="Move selected" />&nbsp;
				<input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.Transactiontxt, this.form.TransactionSeltxt)" />&nbsp;
				<input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.TransactionSeltxt, this.form.Transactiontxt)" />		&nbsp;				
				<input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.TransactionSeltxt, this.form.Transactiontxt)" />
			</td>
		</tr>		
		<tr class="bgcolor">
			<td></td>
			<td >
		        <select size="20" name="TransactionSeltxt" id="TransactionSeltxt" class="ctrl" multiple="multiple" style="width: 500px" >
		        </select>
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
			<input type="hidden" name="Admin_Input_Type" value="RoleTranMasterNew" />
			<input type="hidden" name="Transactions" id="Transactions" value="<%=Transactions %>" />						
			<input type="hidden" name="tranId" value="<%=existstrid %>" />	
			<input name="Submitcmd" type="submit" class="btnform" value="Submit"/>
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Resetcmd" value="Cancel" class="btnform" type="reset"></td>
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
		<table border="0" cellpadding="0" cellspacing="0" width="700"><tbody>
			<tr>
				<td >
					<div id="roletrandetails">
					
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