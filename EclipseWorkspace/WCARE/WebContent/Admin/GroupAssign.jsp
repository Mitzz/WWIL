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
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>

<%
String rid = "";
String empcode = "";
String empname = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	rid = dynabean.getProperty("GroupNametxt").toString();
	empcode = dynabean.getProperty("EmpCodetxt").toString();
	empname = dynabean.getProperty("EmpNametxt").toString();	
	//String str1=(String)session.getAttribute("SubmitMessage");
	//if(str1 != null && str1.equals("Success")){
	//	newtrid = "";	
	//}else{
	//	newtrid = dynabean.getProperty("Transactiontxt").toString();
	//}
	//session.setAttribute("SubmitMessage","");	
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String rolename = AdminUtil.fillMaster("TBL_SHIFT_GROUP",rid);
%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].GroupNametxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getfullrolemaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("rolemaster")[0];
	var items = cart.getElementsByTagName("rolecode");	
		var divdetails = document.getElementById("userroledetails");
		divdetails.innerHTML = "";
		var str = "<hr><table width='500' cellspacing='0' cellpadding='0'>"
		str +="<tr class='resulttablehead' align='center' height='20' ><td width='20'></td><td width='150'>Transactions</td><td width='100'>Main Menu</td><td width='80'>Sub Menu</td><td width='80'>Last Menu</td><td width='30'>D</td></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("roletranid")[0].firstChild;
	     	if (nname != null){
	     		str+="<tr class='resulttablebody' align='left' height='20'><td></td><td>" + item.getElementsByTagName("tranname")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td>" + item.getElementsByTagName("under1")[0].firstChild.nodeValue + "</td>"   			     		
	     		str+="<td>" + item.getElementsByTagName("under2")[0].firstChild.nodeValue + "</td>"   			     		
	     		str+="<td>" + item.getElementsByTagName("under3")[0].firstChild.nodeValue + "</td>"   			     			     			     		
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to delete the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("roletranid")[0].firstChild.nodeValue + "')>"
	     		str+="</td></tr>"
	   	 		  	 		
	   	 	}   	 	
		}
		str += "</table>"
		divdetails.innerHTML = str;  
}
function fillTransaction() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].GroupNametxt;
     var LocId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showNonTransaction);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getnonrole&AppId="+LocId);
}
function showNonTransaction(dataXml)
{
	var cart = dataXml.getElementsByTagName("tranmaster")[0];
	var items = cart.getElementsByTagName("trancode");
	document.forms[0].Transactiontxt.options.length = 0;
	document.forms[0].Transactiontxt.options[0] = new Option("--Make a Selection--","");
	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("tranid")[0].firstChild;
     	if (nname != null)
     	{
   	 		document.forms[0].Transactiontxt.options[I + 1] = new Option(item.getElementsByTagName("tranname")[0].firstChild.nodeValue,item.getElementsByTagName("tranid")[0].firstChild.nodeValue);
   	 		
   	 	}
	}
}
function findEmployee() 
{
	 var req = newXMLHttpRequest();
	 var ApplicationId = "";
		 if (document.forms[0].EmpCodetxt.value != "") {
		 	 ApplicationId = document.forms[0].EmpCodetxt.value;
		 }
	 if (ApplicationId != ""){
		 req.onreadystatechange = getReadyStateHandler(req, showEmployee);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=findemployee&AppId="+ApplicationId);
	}
}
function showEmployee(dataXml)
{
	var cart = dataXml.getElementsByTagName("employeemaster")[0];
	var items = cart.getElementsByTagName("employeecode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("fname")[0].firstChild;
     	if (nname != null){
			document.forms[0].EmpNametxt.value = item.getElementsByTagName("fname")[0].firstChild.nodeValue + ' ' + item.getElementsByTagName("lname")[0].firstChild.nodeValue;
		document.forms[0].GroupNametxt.value =  item.getElementsByTagName("grid")[0].firstChild.nodeValue;
		findBalance();
		}
		else{
			document.forms[0].EmpNametxt.value = "Employee Not Found";
			document.forms[0].EmpCodetxt.value = "";
		}
	}
}
function findBalance() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].GroupNametxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getgroupdetailsbyid&AppId="+ApplicationId);
}

function showDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("shiftlink")[0];
	var items = cart.getElementsByTagName("shiftentry");
		var divdetails = document.getElementById("show");
		divdetails.innerHTML = "";
		var str = "<hr><table width='550' cellspacing='0' cellpadding='0'>"
		str +="<tr class='resulttablehead' align='center' height='20'><td width='30'></td><td width='80'>Shift Code</td><td width='120'>Shift Name</td><td width='80'>Shift Start</td><td width='80'>Shift End</td><td width='80'>Lunch Out</td><td width='80'>Lunch In</td><td width='80'>Grace In</td><td width='80'>Grace Out</td></tr>";
		//alert(items.length);
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("shiftid")[0].firstChild;
	     	if (nname != null){
	     			
	     			str+="<tr class='resulttablebody' align='center' height='20'><td><img src='<%=request.getContextPath()%>/resources/images/arrow.jpg'></td><td>" + item.getElementsByTagName("shiftcode")[0].firstChild.nodeValue + "</td>"	     		  			     		
		     		str+="<td>" + item.getElementsByTagName("shiftname")[0].firstChild.nodeValue + "</td>"   			     		
		     		str+="<td>" + item.getElementsByTagName("shiftstart")[0].firstChild.nodeValue + "</td>" 
		     		str+="<td>" + item.getElementsByTagName("shiftend")[0].firstChild.nodeValue + "</td>"
		     		str+="<td>" + item.getElementsByTagName("lunchout")[0].firstChild.nodeValue + "</td>"
		     		str+="<td>" + item.getElementsByTagName("lunchin")[0].firstChild.nodeValue + "</td>"
		     		str+="<td>" + item.getElementsByTagName("lateingrace")[0].firstChild.nodeValue + "</td>"
		     		str+="<td>" + item.getElementsByTagName("earlyoutgrace")[0].firstChild.nodeValue + "</td>"		     				     				     				     		
		     	    str+="</tr>"
		   	 		  	 		
		   	}   	 	
	    }
		str += "</table>"
		divdetails.innerHTML = str;  
	 	 		
 
}
</script>
</head>
<body>
<div align="center">
<table border="0" cellpadding="0" cellspacing="0" width="600">
  <tr>
   <td rowspan="3" class="r1_c1"></td>
   <td class="r1_c2"></td>
   <td rowspan="3" class="r1_c3"></td>
  </tr>
  <tr>
   <td class="r2_c2" align="center"><span class="tabletitle"> Assign Shift Group To Employee </span></td>
  </tr>
  <tr>
   <td class="r3_c2"></td>
  </tr>
  <tr>
   <td class="r4_c1"></td>
   <td class="r4_c2">
   		<table width="450" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<html:errors />
					<%String str=(String)session.getAttribute("msg");%>
					<%if(str != null){%>
					<%=str%>
					<%}%>
					<%session.setAttribute("msg","");%>
   				</td>
   			</tr>
   		</table>
   		<!-- <form action="ProcessFileUpload.jsp" method="post" enctype="multipart/form-data" > -->
		<form action="<%=request.getContextPath()%>/GroupAssign.do" method="post" >
			<table width="550" cellspacing="0" cellpadding="0">					
				<tr class="tabtextnormal" align="left">
					<td width="150" height="30" align="left">Employee</td>
					<td width="8">:</td>
					<td width="300">
				        <input type="text" id="Level1" value="<%=empcode %>" name="EmpCodetxt" size="10" class="tabtextnormal" maxlength="10" onblur="findEmployee()" />
				        <input type="text" id="Name1" value="<%=empname %>" name="EmpNametxt" size="30" class="tabtextnormal" maxlength="30" onfocus="Submitcmd.focus()" />
					</td>
				</tr>		
				<tr class="tabtextnormal" align="left">
					<td height="30" align="left">Select ShiftGroup</td>
					<td >:</td>
					<td >
						<select size="1" id="GroupNametxt" name="GroupNametxt" class="tabtextnormal" onChange="findBalance()">
				            <option value="">--Make a Selection--</option>
				            <%=rolename%>			            
				        </select>	
				       		        
					</td>
				</tr>															
				<tr class="tabtextnormal">
					<td height="20" align="center" colspan="3">
						<input type="hidden" name="Admin_Input_Type" value="GroupAssign" />
						<input type="Submit" name="Submitcmd" class="buttonstyle" value="Submit" />
						<input type="reset" name="Resetcmd" value="Cancel" class="buttonstyle" />
					</td>
				</tr>		
				<tr class="tabtextnormal" align="center">
					<td colspan="3" align="center">						
				       	<div id="show"></div> 			        
					</td>
				</tr>								
			</table>
		</form>		
		<div id="userroledetails"></div>		
   </td>
   <td class="r4_c3"></td>
  </tr>
  <tr>
   <td class="r5_c1"></td>
   <td class="r5_c2"></td>
   <td class="r5_c3"></td>
  </tr>
</table>
</div>
</body>
</html>