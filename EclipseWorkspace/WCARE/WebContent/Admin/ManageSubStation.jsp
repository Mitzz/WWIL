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

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>

<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.scrollabletable.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/picnet.table.filter.min.js"></script>

<style type="text/css" title="currentStyle">
			@import "<%=request.getContextPath()%>/resources/media/css/demo_page.css";
			@import "<%=request.getContextPath()%>/resources/media/css/demo_table.css";
</style>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/media/js/jquery.js"></script>
<script type="text/javascript" language="javascript" src="<%=request.getContextPath()%>/resources/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" charset="utf-8">
	$(document).ready(function() {
		$('#example').dataTable( {
			"sScrollY": 200,
			"sScrollX": "100%",
			"sScrollXInner": "100%"
		} );
	} );
</script>

<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
int status = 0;
String substationid = "";
String areaid = "";
String substationname="" ;
String substationowner="";
String substationcap ="";	
String substationmva ="";	
String substationhv ="";	
String substationlv ="";
String fdid="";
String subid="";
String areaname="";

DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	areaid = dynaBean.getProperty("Statetxt").toString();
	substationid = dynaBean.getProperty("EBSitetxt").toString();	

	String str1=(String)session.getAttribute("SubmitMessage");
	
	if(str1 != null && str1.equals("Success")){
		subid = "";
	}else{	
		subid = dynaBean.getProperty("EBId").toString();				
	}	
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}

areaname = AdminUtil.fillMaster("VIEW_AREA_MASTER",areaid);
%>
<script type="text/javascript">
function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;    
    
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findSubstationMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{       
	    var cart = dataXml.getElementsByTagName("substation")[0];	     
	    var items = cart.getElementsByTagName("substationcode");	
		var divdetails = document.getElementById("substationdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th align='left' class='detailsheading' width='180'>Substation Description</th>"
		str+="<th class='detailsheading' width='90'>Total Feeder</th><th class='detailsheading' width='90'>Total Transformer</th><th align='left' class='detailsheading' width='170'>Substation belongs To</th><th class='detailsheading' width='70'>Capacity</th>"
		str+="<th class='detailsheading' width='40'>MVA</th><th class='detailsheading' width='60'>High Vol</th><th class='detailsheading' width='60'>Low Vol</th>"
		str+="<th class='detailsheading' width='40'>Edit</th><th class='detailsheading' width='40'>Copy</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  		   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("substationid")[0].firstChild;
	     	if (nname != null){
	     		
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("substationname")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='center'>" + item.getElementsByTagName("totalfeeder")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("subbelongsto")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("subbelongsto")[0].firstChild.nodeValue + "</td>"		     		
	     		str+="<td align='center'>" + item.getElementsByTagName("substationcap")[0].firstChild.nodeValue + "</td>"  
	     		str+="<td align='center'>" + item.getElementsByTagName("substationmva")[0].firstChild.nodeValue + "</td>" 
	     		str+="<td align='center'>" + item.getElementsByTagName("substationhv")[0].firstChild.nodeValue + "</td>" 
	     		str+="<td align='center'>" + item.getElementsByTagName("substationlv")[0].firstChild.nodeValue + "</td>" 
	     		
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("substationid")[0].firstChild.nodeValue + "','E')></td>"
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' "
		     	str+="onClick=findDetails('" + item.getElementsByTagName("substationid")[0].firstChild.nodeValue + "','C')></td></tr>"	     		
	   	 		
	   	 	}   	 	
		}
       	if(document.forms[0].EBId.value!="")
  	 		{}
 		else
 	 		document.forms[0].EBId.value = "";
		divdetails.innerHTML = str;
}
function findDetails(substationid,type)
{    
	 var req = newXMLHttpRequest();
	 document.forms[0].edittytxt.value =type;	 
     var ApplicationId = substationid;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findSubstationMasterByID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("substationmaster")[0];
	var items = cart.getElementsByTagName("substationcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("substationname")[0].firstChild;
     	
     	if (nname != null){        	
     		document.forms[0].Statetxt.value = item.getElementsByTagName("areaid")[0].firstChild.nodeValue;     		
   	 		document.forms[0].EBSitetxt.value = item.getElementsByTagName("substationname")[0].firstChild.nodeValue;
   	 		document.forms[0].Substationownertxt.value = item.getElementsByTagName("substationowner")[0].firstChild.nodeValue;   	 		
   	 		document.forms[0].Substationcaptxt.value = item.getElementsByTagName("substationcap")[0].firstChild.nodeValue;  
   	 		document.forms[0].Substationmvatxt.value = item.getElementsByTagName("substationmva")[0].firstChild.nodeValue;  
   	 		document.forms[0].Substationvolhtxt.value = item.getElementsByTagName("substationhv")[0].firstChild.nodeValue;  
   	 		document.forms[0].Substationvolltxt.value = item.getElementsByTagName("substationlv")[0].firstChild.nodeValue;  
   	 		document.forms[0].Transformertxt.value = item.getElementsByTagName("totalTransformer")[0].firstChild.nodeValue;  
   	 		document.forms[0].Remarkstxt.value = item.getElementsByTagName("substationRemarks")[0].firstChild.nodeValue; 
   	 		// document.forms[0].EBId.value = item.getElementsByTagName("areaid")[0].firstChild.nodeValue;  
   	 		
   	 		document.forms[0].EBId.value = item.getElementsByTagName("substationid")[0].firstChild.nodeValue; 	          
	          	
          if(document.forms[0].edittytxt.value=="E")
	        {
  	 			document.forms[0].Submit.value="Update";	  	 			
  	 		}
  	 		else
  	 		{
  	 		    document.forms[0].EBSitetxt.value ="";
  	 		    document.forms[0].EBId.value ="";
  	 		    document.forms[0].Submit.value="Submit";
  	 		} 		
   	 	}
   	 	else{
   	 		document.forms[0].Statetxt.value ="";
   	 		document.forms[0].EBSitetxt.value ="";
   	 		document.forms[0].Substationownertxt.value ="";
   	 		document.forms[0].Substationcaptxt.value ="";
   	 		document.forms[0].Substationmvatxt.value="";
   	 		document.forms[0].Substationvolhtxt.value="";
   	 	    document.forms[0].Substationvolltxt.value="";
   	 	    document.forms[0].Transformertxt.value="";  
   	 		document.forms[0].Remarkstxt.value="";
   	 	} 			
 	}
}

function validate(frm)
{	
	var ch;
	var flag=true;
	var error="";
	
		 if(isNaN(frm.Substationvolhtxt.value)){
			flag=false;
			error=error+"Only numbers are allowed in Multifactor .\n";
			if(focus==false){
				frm.Substationvolhtxt.focus();
				focus=true;
			  }
		 }
		 else if(isNaN(frm.Substationvolltxt.value)){
			flag=false;
			error=error+"Only numbers are allowed in Multifactor .\n";
			if(focus==false){
				frm.Substationvolltxt.focus();
				focus=true;
			  }
		 }
	
	if(flag==false)
	{
		alert(error);
		return false;
	}
	else 	
		return true;
}
function confirmation()
{   
	
	if(document.forms[0].EBId.value!="")
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
<body><!-- onload="findApplication()" -->
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/SubStationMaster.do" method="post" onSubmit="validate(this)">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Sub Station Master</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="180%">
		<tbody>
		<tr bgcolor="#ffffff">
			<td id="t_company" width="219">Select Area:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="displayFeeder()"><!--  onChange="findApplication()" -->
					<option value="">--Make a Selection--</option>
					<%=areaname%>
				</select>
			</td>
		</tr>			
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Substation Name:</td>
		  <td><input name="EBSitetxt" id="EBSitetxt" class="ctrl"  size="30" type="text" value="<%=substationname%>"/></td>
	  	</tr>	
	  	<tr bgcolor="#ffffff"> 
				<td id="t_city">Substation Capacity:</td>
				<td><input name="Substationcaptxt" size="10" class="ctrl" value="<%=substationcap%>" type="text" /></td>
		</tr>	
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Substation MVA:</td>
				<td><input name="Substationmvatxt" size="10" class="ctrl" value="<%=substationmva%>" type="text" /></td>
		</tr>	
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Substation Voltage(H):</td>
				<td><input name="Substationvolhtxt" size="10" class="ctrl" value="<%=substationhv%>" type="text" /></td>
		</tr>	
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Substation Voltage(L):</td>
				<td><input name="Substationvolltxt" size="10" class="ctrl" value="<%=substationlv%>" type="text" /></td>
		</tr>	
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Substation Owner:</td>
				<td><input name="Substationownertxt" size="30" class="ctrl" value="<%=substationowner%>" type="text" /></td>
		</tr>
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Total Transformer:</td>
				<td><input name="Transformertxt" size="30" class="ctrl" value="<%=""%>" type="text" /></td>
		</tr>
		<tr bgcolor="#ffffff"> 
				<td id="t_city">Remarks:</td>
				<td><input name="Remarkstxt" size="30" class="ctrl" value="<%=""%>" type="text" /></td>
		</tr>
		
		  <tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
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
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit" onClick="return confirmation()"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="SubStationMaster" />		
		<input type="hidden" name="EBId" value="<%=subid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset">
		<input type="hidden" name="edittytxt" id="edittytxt" />
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
<!-- 
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody>
			<tr>
				<td >
					<div id="substationdetails"></div>
				</td>
			</tr>
			</tbody>
		</table>	
	</td>
</tr>
</table>
</div>
 -->
</table></div>
<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="90%">
	<thead>
		<tr align="center"> 
	 		<th width='15'>S.N.</th>
			<th align="left" width='115'>Substation Name</th>
			<th align="center" width='70'>Total Feeder</th>
			<th align="center" width='70'>Total Transformer</th>
			<th align="left" width='115'>Substation belongs To</th>
			<th align="center" width='50'>Capacity</th>
			<th align="center" width='50'>MVA</th>
			<th align="center" width='50'>High Vol</th>
			<th align="center" width='50'>Low Vol</th>
			<th align="right" width='12'>Edit</th>
		</tr>
	</thead>
	<tbody>
		<% 
			com.enercon.admin.dao.AdminDao ad = new com.enercon.admin.dao.AdminDao();
			java.util.List displayLogin = new java.util.ArrayList();
			displayLogin = ad.findSubstationMaster(areaid);
			
			int size = displayLogin.size();
			
			for (int i = 0; i < size; i++) {
				
				java.util.Vector v = new java.util.Vector();
				v = (java.util.Vector) displayLogin.get(i);
				String a = (String) v.get(0);
				String b = (String) v.get(1);
				String c = (String) v.get(2);
				String d = (String) v.get(3);
				String e = (String) v.get(4);		
				String f = (String) v.get(5);
				String g = (String) v.get(6);
				String h = (String) v.get(7);
				String j = (String) v.get(8);
			%>	
				<tr align="center" class="gradeA">	
					<td><%=i+1%></td>
					<td align="left"><%=b%></td>
					<td align="center"><%=c%></td>
					<td align="center"><%=j%></td>
					<td align="left"><%=d%></td>
					<td align="center"><%=e%></td>
					<td align="center"><%=f%></td>
					<td align="center"><%=g%></td>
					<td align="center"><%=h%></td>
					<td align="center"><input type='image' src="<%=request.getContextPath()%>/resources/images/edit.gif" onClick="findDetails('<%=a%>','E')"></td>
				</tr>
			<%
			}			
		%>
		
	</tbody>	
</table>
</body>
</html>