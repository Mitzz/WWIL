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

<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.js"></script>

<!-- <link rel="stylesheet" href="<%= request.getContextPath()%>/resources/tablesorter/css/style.css" type="text/css" /> -->
	
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.scrollabletable.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/picnet.table.filter.min.js"></script>

<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
int status = 0;
String cid = "";
String cname = "";
String ccode = "";
String ccellno="" ;
String cfax="";
String ccontact="";
String cemail="" ;	
String cphone = "";	
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	cname = dynaBean.getProperty("CustNametxt").toString();
	ccode = dynaBean.getProperty("CustCodetxt").toString();
	ccontact = dynaBean.getProperty("CustContacttxt").toString();
	cphone = dynaBean.getProperty("CustPhonetxt").toString();	
	ccellno = dynaBean.getProperty("CustCelltxt").toString();	
  	cfax = dynaBean.getProperty("CustFaxtxt").toString();	
  	cemail = dynaBean.getProperty("CustEmailtxt").toString();	
		
  	//ebstatus = dynaBean.getProperty("EBStatustxt").toString();
  	if(dynaBean.getProperty("CustActivetxt") == null){
  		status = 0;
	}
	else if((dynaBean.getProperty("CustActivetxt")).toString().equals("1")){
		status = 1;
	}	
	cid = dynaBean.getProperty("CustIdtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<script type="text/javascript">
function gotoSave() 
{
	if(document.forms[0].CustActivetxt.checked=true)
	{
		document.forms[0].CustActivetxt.value=1; 
	} 
    else
    {
   	    document.forms[0].CustActivetxt.value=0;
    }
    var blnSave=false;
    blnSave = validateForm('Customer Name',document.forms[0].CustNametxt.value,'M','',
    					   'SAP Code',document.getElementById("CustCodetxt").value,'M','',
                           'Phone',document.forms[0].CustPhonetxt.value,'M','',
                           'Cell',document.forms[0].CustCelltxt.value,'M','',
                           'Fax',document.forms[0].CustFaxtxt.value,'M','',
                           'Contact Person',document.forms[0].CustContacttxt.value,'M','',
                           'Email',document.forms[0].CustEmailtxt.value,'M','E'
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
	 req.send("Admin_Input_Type=findCustomerMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("custmaster")[0];
	var items = cart.getElementsByTagName("custcode");	
		var divdetails = document.getElementById("custdetails");
		divdetails.innerHTML = "";
		var str = "<table id='insured_list' class='tablesorter' border='0' cellpadding='2' cellspacing='1' width='600'>"
		str +="<thead><tr align='center' height='20'><th class='detailsheading' width='5'>S.N.</th><th class='detailsheading' width='80'>Customer</th><th class='detailsheading' width='80'>Marketing Person</th><th class='detailsheading' width='35'>Code</th><th class='detailsheading' width='50'>Contact</th><th class='detailsheading' width='50'>Telephone No</th><th class='detailsheading' width='30'>Cell No</th><th class='detailsheading' width='80'>FAX</th><th class='detailsheading' width='80'>EMail </th><th class='detailsheading' width='5'>Active</th>"
		str +="<th class='detailsheading' width='5'>Edit</th></tr></thead><tbody>";
		
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("cid")[0].firstChild;
	     	if (nname != null){
	     		  if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	
			           		
			      if (item.getElementsByTagName("cname")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
				      str+="<td align='left'>" + item.getElementsByTagName("cname")[0].firstChild.nodeValue + "</td>"
				      
				      
				  if (item.getElementsByTagName("mktperson")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
				      str+="<td align='left'>" + item.getElementsByTagName("mktperson")[0].firstChild.nodeValue + "</td>"
				      
				      
				  if (item.getElementsByTagName("ccode")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else	
		     		str+="<td align='left'>" + item.getElementsByTagName("ccode")[0].firstChild.nodeValue + "</td>"	  
		     		
		     	  if (item.getElementsByTagName("ccontact")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
	     				str+="<td align='left'>" + item.getElementsByTagName("ccontact")[0].firstChild.nodeValue + "</td>"	  
	     				
	     		  if (item.getElementsByTagName("cphone")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
	     			  str+="<td align='left'>" + item.getElementsByTagName("cphone")[0].firstChild.nodeValue + "</td>"	 
	     			  
	     		  if (item.getElementsByTagName("ccell")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else 
	     			  str+="<td align='left'>" + item.getElementsByTagName("ccell")[0].firstChild.nodeValue + "</td>"
	     			  	  
	     		  if (item.getElementsByTagName("cfax")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
	     			  str+="<td align='left'>" + item.getElementsByTagName("cfax")[0].firstChild.nodeValue + "</td>"
	     			  	  
	     		  if (item.getElementsByTagName("cemail")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else
	     			  str+="<td align='left'>" + item.getElementsByTagName("cemail")[0].firstChild.nodeValue + "</td>"	  
	     			  
	     		  if (item.getElementsByTagName("cactive")[0].firstChild.nodeValue == ".")
				      str+="<td align='left'></td>"				      	
			      else{
			      	if (item.getElementsByTagName("cactive")[0].firstChild.nodeValue == "1")
	     				str+="<td align='left'>Active</td>"     			  
	     			else
	     				str+="<td align='left'>DeActive</td>"     			  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("cid")[0].firstChild.nodeValue + "')></td></tr>"}
	   	 		document.forms[0].CustIdtxt.value = "";
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str+"</tbody></table>";
		
		calldocsortable();
		
}

function calldocsortable()
{
	$(document).ready(function()     
	{         
		$("#insured_list").tablesorter();     
	} ); 	
}

// $(document).ready(function()     {         $("#myTable").tablesorter( {sortList: [[0,0], [1,0]]} );     } ); 

function findDetails(cid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = cid;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findCustomerMasterByID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("custmaster")[0];
	var items = cart.getElementsByTagName("custcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("cname")[0].firstChild;
     	
     	if (nname != null){
     	
     	
   	 		document.forms[0].CustNametxt.value = item.getElementsByTagName("cname")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("cname")[0].firstChild.nodeValue;
   	 		document.forms[0].CustCodetxt.value = item.getElementsByTagName("ccode")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("ccode")[0].firstChild.nodeValue;
   	 		document.forms[0].CustContacttxt.value = item.getElementsByTagName("ccontact")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("ccontact")[0].firstChild.nodeValue;  
   	 		document.forms[0].CustPhonetxt.value = item.getElementsByTagName("cphone")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("cphone")[0].firstChild.nodeValue;  
   	 		document.forms[0].Marktpertxt.value = item.getElementsByTagName("mktperson")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("mktperson")[0].firstChild.nodeValue;  
   	 		document.forms[0].CustCelltxt.value = item.getElementsByTagName("ccell")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("ccell")[0].firstChild.nodeValue;
   	 		document.forms[0].CustFaxtxt.value = item.getElementsByTagName("cfax")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("cfax")[0].firstChild.nodeValue;  
   	 		document.forms[0].CustEmailtxt.value = item.getElementsByTagName("cemail")[0].firstChild.nodeValue == "." ? "" : item.getElementsByTagName("cemail")[0].firstChild.nodeValue; 
   	 		document.forms[0].CustIdtxt.value = item.getElementsByTagName("cid")[0].firstChild.nodeValue;  
   	 		 if(item.getElementsByTagName("cactive")[0].firstChild.nodeValue=="0")
   	 		 {
  		       document.forms[0].CustActivetxt.checked=false;
	          }
	        else
	          document.forms[0].CustActivetxt.checked=true;
   	 		
   	 		document.forms[0].Submit.value="Update";	 		
   	 	}
   	 	else{
   	 		document.forms[0].CustNametxt.value = "";
   	 		document.forms[0].CustCodetxt.value =  "";
   	 		document.forms[0].CustContacttxt.value =  ""; 
   	 		document.forms[0].CustPhonetxt.value =  "";  
   	 		document.forms[0].CustCelltxt.value = "";
   	 		document.forms[0].CustFaxtxt.value =  ""; 
   	 		document.forms[0].CustEmailtxt.value = "";
   	 		document.forms[0].CustIdtxt.value =  ""; 
   	 		document.forms[0].Marktpertxt.value =  ""; 
   	 	    document.forms[0].CustActivetxt.checked=false;
	        	 		
   	 	} 			
 	}
}
function confirmation()
{   
	
	if(document.forms[0].CustIdtxt.value!="")
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
<body onLoad="findApplication()">
<form action="<%=request.getContextPath()%>/CustomerMaster.do" method="post" onSubmit="return gotoSave()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="599">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="283">Customer Master</th>
	<td width="301"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="13">&nbsp;</td>
	<td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3" width="597">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="597">
		<tbody>
		<tr bgcolor="#ffffff">
			<td id="t_company" width="156">Customer:</td>
			<td colspan="3" valign="top">
            <input name="CustNametxt" id="CustNametxt" VALUE="<%=cname%>" size="32" class="ctrl" type="text"></td>
			</tr>
		<tr bgcolor="#ffffff">
			<td id="t_country" >Customer SAP&nbsp;Code:</td>
			<td width="192">
				<input name="CustCodetxt"  id="CustCodetxt" VALUE="<%=ccode%>" class="ctrl" size="32" type="text"></td>			
		</tr>
		<tr bgcolor="#ffffff">
			<td id="t_telephone_number">Telephone&nbsp;Number:</td>
			<td width="104">
            <input name="CustPhonetxt" id="CustPhonetxt" VALUE="<%=cphone%>" size="32" class="ctrl" type="text"></td>
        </tr>
		<tr bgcolor="#ffffff">
			<td id="t_country" >Cell No:</td>
			<td width="192">
				<input name="CustCelltxt" id="CustCelltxt" VALUE="<%=ccellno%>" size="32" class="ctrl" type="text"></td>
			
		</tr>
		<tr bgcolor="#ffffff">
			<td id="t_country" width="156">Fax Number:</td>
			<td width="104">
            <input name="CustFaxtxt" id="CustFaxtxt" VALUE="<%=cfax%>" size="32" class="ctrl" type="text"></td>
        </tr>
		<tr bgcolor="#ffffff">
			<td id="t_country" width="156">Contact Person:</td>
			<td colspan="3">
				<input name="CustContacttxt" id="CustContacttxt" VALUE="<%=ccontact%>" size="32" class="ctrl" type="text"></td>
			</tr>
			
		<tr bgcolor="#ffffff">
			<td id="t_country" width="156">Marketing Person:</td>
			<td colspan="3">
				<input name="Marktpertxt" id="Marktpertxt" VALUE="<%=ccontact%>" size="50" class="ctrl" type="text"></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_email" width="156">Email:</td>
			<td colspan="3"><input name="CustEmailtxt"  id="CustEmailtxt" VALUE="<%=cemail%>" size="32" class="ctrl" type="text"></td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information" width="156">Active:</td>
			<td colspan="3" bgcolor="#ffffff">
           
            <%if (status == 0){ %>
				<input type="checkbox" name="CustActivetxt" id="CustActivetxt" value="0" />
			<%}else{ %>
				<input type="checkbox" name="CustActivetxt" id="CustActivetxt" checked value="1" />
			<%} %></td>
		</tr>
		<tr class="bgcolor"> 
			<td colspan="4">
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
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody>
	<tr>
		<td class="btn" width="100"><input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit" onClick="return confirmation();"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="CustomerMaster" />		
		<input type="hidden" name="CustIdtxt" value="<%=cid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
	<tbody>
		<tr>		
				<td align="center">
					
					<div id="custdetails"></div>	
				</td>
		</tr>
	</tbody>
</table>	
	
	</body>
</html>