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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
int status = 0;
String cid = "";
String lid = "";

DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",cid);
String logid = AdminUtil.fillMaster("TBL_LOGIN_MASTER",lid);

%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].LoginIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getCustomerDetails&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("custhead")[0];
	var items = cart.getElementsByTagName("custcode");
	
		var divdetails = document.getElementById("custdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Login Id</th><th class='detailsheading' width='100'>Customer Id</th><th class='detailsheading' width='100'>Customer Name</th><th class='detailsheading' width='100'>Total WEC</th></tr>";
		// str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("loginid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			    str+="<td align='left'>" + item.getElementsByTagName("loginid")[0].firstChild.nodeValue + "</td>"	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("custid")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("custname")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='center'>" + item.getElementsByTagName("totalwec")[0].firstChild.nodeValue + "</td></tr>"
	     		// str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		// str+="onClick=findDetails('" + item.getElementsByTagName("loginid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str;
}
function findDetails(siteid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = siteid;
	 req.onreadystatechange = getReadyStateHandler(req, showSiteMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findCustDetailsById&AppId="+ApplicationId);
}
function showSiteMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("custmaster")[0];
	var items = cart.getElementsByTagName("custcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("sname")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].SiteNametxt.value = item.getElementsByTagName("sname")[0].firstChild.nodeValue;
   	 		document.forms[0].SiteCodetxt.value = item.getElementsByTagName("scode")[0].firstChild.nodeValue;
   	 		document.forms[0].Statetxt.value = item.getElementsByTagName("stateid")[0].firstChild.nodeValue;
   	 		document.forms[0].SiteIdtxt.value = item.getElementsByTagName("siteid")[0].firstChild.nodeValue;  
   	 		if (item.getElementsByTagName("sinc")[0].firstChild.nodeValue == ".")
     		{
     			document.forms[0].SiteInchargetxt.value = "";
     		}
     		else
     		{
     			document.forms[0].SiteInchargetxt.value = item.getElementsByTagName("sinc")[0].firstChild.nodeValue;
     		}
   	 		if (item.getElementsByTagName("sadd")[0].firstChild.nodeValue == ".")
     		{
     			document.forms[0].SiteAddresstxt.value = "";
     		}
     		else
     		{
     			document.forms[0].SiteAddresstxt.value = item.getElementsByTagName("sadd")[0].firstChild.nodeValue;  
     		}
   	 		document.forms[0].Submit.value="Update";	 		
   	 	}
   	 	else{
   	 		document.forms[0].SiteNametxt.value = "";
   	 		document.forms[0].SiteCodetxt.value = "";	
   	 		document.forms[0].Statetxt.value = "";	 		
   	 		document.forms[0].SiteIdtxt.value = "";   	 
   	 		document.forms[0].SiteInchargetxt.value = "";	 		
   	 		document.forms[0].SiteAddresstxt.value = "";   			
   	 	} 			
 	}
}

function gotoSave() 
{ 
	
    var blnSave=false;
    blnSave = validateForm(
    					    'Login Id',document.forms[0].LoginIdtxt.value,'M','',
    					    'Customer Id',document.forms[0].CustNametxt.value,'M',''
                          
                           );
     if ( blnSave == true ) {
        return true;
     } else {
        return false;
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
<body>
<form action="<%=request.getContextPath()%>/AssignLogin.do" method="post" onSubmit="return gotoSave()" >
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
			<td id="t_company" width="156">Select Login:</td>
			<td colspan="3" valign="top">
                <select size="1" id="LoginIdtxt" name="LoginIdtxt" class="ctrl" onchange="findApplication()">
		            <option value="">--Make a Selection--</option>
		            <%=logid %>
		        </select>
            
            </td>
		</tr>
		
		<tr bgcolor="#ffffff">
			<td id="t_company" width="156">Select Customer:</td>
				<td colspan="3" valign="top">
              	  <select size="1" id="CustNametxt" name="CustNametxt" class="ctrl">
		         	   <option value="">--Make a Selection--</option>
		            <%=custid %>
		       	 </select>
            
            	</td>
		</tr>
			
		<tr class="bgcolor"> 
			<td colspan="4"><font color="red">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</font></td>
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
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="AssignLogin" />		
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
<tbody><tr>		
				<td align="center">
					
					<div id="custdetails">	</div>	
				</td>
			</tr>
			</tbody>
</table>	

<!-- Form end -->


</body>
</html>