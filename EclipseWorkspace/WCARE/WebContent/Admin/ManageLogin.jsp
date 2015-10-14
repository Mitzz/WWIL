<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.js"></script>

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
System.out.println(session.getAttribute("loginID").toString());
String roletxt = "";
String logintypetxt = "";
String activetxt = "";
String Messagetxt = "";
String Passwordtxt = "";
String desctxt = "";
String loginidtxt = "";
String loginmasteridtxt = "";
String Customeridtxt1="";
String logindetail = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	loginmasteridtxt=dynabean.getProperty("loginmasteridtxt").toString();
	roletxt = dynabean.getProperty("roletxt").toString();
	logintypetxt = dynabean.getProperty("logintypetxt").toString();
	Passwordtxt = dynabean.getProperty("Passwordtxt").toString();
	desctxt = dynabean.getProperty("desctxt").toString();
	loginidtxt = dynabean.getProperty("loginidtxt").toString();
	activetxt= dynabean.getProperty("activetxt").toString();
	Messagetxt= dynabean.getProperty("Messagetxt").toString();
	Customeridtxt1=dynabean.getProperty("Customeridtxt1")==null?"":dynabean.getProperty("Customeridtxt1").toString();
	session.removeAttribute("dynabean");
	// System.out.println("roletxt===="+roletxt);
	
}
// String roleidtxt = session.getAttribute("");
String role = AdminUtil.fillMaster("TBL_ROLE_MASTER",roletxt);


//String abc = dynabean.getProperty("loginmasteridtxt").toString();
//AdminUtil autil = new AdminUtil();
//logindetail = autil.getLoginId(abc);
//System.out.println(logindetail);

%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getloginemaster&AppId="+ApplicationId);	 
}

function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("loginmaster")[0];	
	var items = cart.getElementsByTagName("logincode");	
	
		var divdetails = document.getElementById("logindetails");
		divdetails.innerHTML = "";
		// var str = "<table id='insured_list' class='tablesorter' border='0' cellpadding='2' cellspacing='1' width='100%' scroable>"
		var str = "<table id='example' class='display' border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<thead><tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>User</th><th class='detailsheading' width='100'>Role</th><th class='detailsheading' width='100'>LoginType</th><th class='detailsheading' width='100'>Description</th>"
		str +="<th class='detailsheading' width='40'>Edit</th></tr></thead><tbody>";
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
	     		str+="<td align='left'>" + item.getElementsByTagName("userid")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("rolename")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("logintype")[0].firstChild.nodeValue + "</td>"  
	     		str+="<td align='left'>" + item.getElementsByTagName("desc")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("loginid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].loginmasteridtxt.value = "";
	   	 	}   	 	
		}
		str += "</tbody></table>"
		divdetails.innerHTML = str;
		
		// calldocsortable();
}
/*
function calldocsortable()
{
 	$(document).ready(function()     
 	{         
 		$("#insured_list").tablesorter();     
 	} ); 	
}
*/
function findDetails(roleid)
{	 
	 // alert(roleid);
	 // alert(document.forms[0].roleidtst.value);
	 // alert(document.forms[0].loginmasteridtxt.value);
	 // var roleid = document.forms[0].roleidtst.value;
	 var req = newXMLHttpRequest();
     var ApplicationId = roleid;
	 req.onreadystatechange = getReadyStateHandler(req, showRoleMaster);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getloginmasterbyid&AppId="+ApplicationId);
}
function showRoleMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("loginmaster")[0];
	var items = cart.getElementsByTagName("logincode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("loginid")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].loginidtxt.value = item.getElementsByTagName("userid")[0].firstChild.nodeValue;
   	 		document.forms[0].Passwordtxt.value = item.getElementsByTagName("password")[0].firstChild.nodeValue;  
   	 		document.forms[0].roletxt.value = item.getElementsByTagName("rolename")[0].firstChild.nodeValue;
   	 		document.forms[0].logintypetxt.value = item.getElementsByTagName("logintype")[0].firstChild.nodeValue;
   	 		document.forms[0].desctxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue;
   	 		document.forms[0].loginmasteridtxt.value = item.getElementsByTagName("loginid")[0].firstChild.nodeValue;
   	 		document.forms[0].Messagetxt.value = item.getElementsByTagName("remarks")[0].firstChild.nodeValue;
   	 		document.forms[0].activetxt.value = item.getElementsByTagName("active")[0].firstChild.nodeValue;
   	 		//if(item.getElementsByTagName("logintype")[0].firstChild.nodeValue=='C')
   	 		///document.forms[0].Customeridtxt1.value = item.getElementsByTagName("custid")[0].firstChild.nodeValue;
   	 
   	 	}
   	 	else{
   	 		document.forms[0].loginidtxt.value = "";
   	 		document.forms[0].Passwordtxt.value = "";
   	 		document.forms[0].roletxt.value = "";
   	 		document.forms[0].logintypetxt.value = "";
   	 		document.forms[0].desctxt.value = "";  
   	 		document.forms[0].loginmasteridtxt.value = "";  
   	 		document.forms[0].Messagetxt.value = "";
   	 		document.forms[0].activetxt.value ="";
   	 				
   	 	} 			
 	}
 	
}

function checkWholeForm()
 {
        var why = "";
        var a='0';
     	   if(document.forms[0].activetxt.value=='0')
     	     {
     	  	   if(document.forms[0].Messagetxt.value=='')
     	  	    {
     	  	      alert("Please Fill The Reasons To Deactive The Login.");
     	  	      return false;
     	         }
     	    else
     	      {
     	        a='1';
     	       }
     	    }
     	    var list5=document.forms[0].logintypetxt;
     	    var rd=list5.options[list5.selectedIndex].value;
            
              if(rd=="C")
              {
              
                 if(document.forms[0].Customeridtxt1.selectedIndex==0)
              {
                alert("Please Select One The Customer.");
     	  		return false; 
              }
              else
     	    	{
     	     	   a='1';
     	    	}
     	   		}
     	   		if(a=='1')
     	   		   return true;
     	
     	    }
     	    
     	  function  EnableCustomer()
     	  { 
     	      var list5=document.forms[0].logintypetxt;
              var rd=list5.options[list5.selectedIndex].value;
              
              if(rd=="E")
              {
     	     		document.forms[0].Customeridtxt1.disabled=true;
     	      }
     	      else
     	      {
     	        document.forms[0].Customeridtxt1.disabled=false;
     	      }
     	  }
</script>
</head>
<body> <!-- onLoad="findApplication()" -->
<form action="<%=request.getContextPath()%>/LoginMaster.do" name="frm" method="post" 	onSubmit="return checkWholeForm()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th width="245" class="headtext">Login Master</th>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td width="325" class="newhead3">&nbsp;</td>
	<td width="10" class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>
		<tr bgcolor="#ffffff">
			<td id="t_company">UserId:</td>
			<td valign="top"><input name="loginidtxt" size="35" class="ctrl" value="<%=loginidtxt%>" type="text" /></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address">Password:</td>
			<td bgcolor="#ffffff"><input name="Passwordtxt" size="35" class="ctrl" value="<%=Passwordtxt%>" type="text"></td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">LoginType:</td>
			<td><select name="logintypetxt" id="logintypetxt" class="ctrl" onChange="EnableCustomer()">
              <option value="" selected="selected" >-- select --</option>
			  
			  
			  <%if (logintypetxt.equals("C")){ %>
				  <OPTION value="C" selected>Customer</OPTION>			               
			       <%}else{%>
			       <option value="C">Customer</option>
			       <%} 
			        if(logintypetxt.equals("E")){ %>
			          	  <OPTION value="E">Employee</OPTION>
		       <%}else { %>	
		       <option value="E">Employee</option>
		       <%} %>		  
			  </select></td>
		</tr>
		
		<tr bgcolor="#ffffff"> 
			<td id="t_city">Role:</td>
			<td><select name="roletxt" id="roletxt" class="ctrl">
              <option value="" selected>-- select --</option>			 
			  <%=role%>				 
			  </select></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information">Description:</td>
			<td bgcolor="#ffffff"><input type="text" name="desctxt" id="desctxt" class="ctrl" value="<%=desctxt%>" size="50"></td>
		</tr>
		
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">Activate:</td>
			<td><select name="activetxt" id="activetxt" class="ctrl">
              <option value="" selected="selected">-- select --</option>
			 
			 
			  <%if (activetxt.equals("1")){ %>
				  <OPTION value="1" selected>Yes</OPTION>			               
			       <%}else{ %>
			        <option value="1">Yes</option>
			       <%} %>
			       <% if(activetxt.equals("0")){ %>
			          	  <OPTION value="0">No</OPTION>
		       <%}else{ %>	
		        <option value="0">No</option>
		       <%} %>		  
			  </select></td>
		</tr>
		
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information">Message:</td>
			<td bgcolor="#ffffff"><input type="text" name="Messagetxt" id="Messagetxt" value="<%=Messagetxt%>" class="ctrl" size="50" /></td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information" COLSPAN="2"><a href="ExportLoginDeactiveDetail.jsp"><u>Export Login Deactive History</u></a></td>
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
	<tbody>
	<tr>
		
		
		<td class="btn" width="100"><input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="LoginMaster" />		
		<input type="hidden" name="loginmasteridtxt" value="<%=loginmasteridtxt %>" />
		<input type="hidden" name="roleidtst" value="<%=logindetail%>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	
	</tbody></table>
	</td>
</tr>

</tbody></table></form>
<%--
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
	<tbody>		
			<tr>		
				<td align="center">
					<div id="logindetails">	</div>	
				</td>
			</tr>
	</tbody>
</table>
--%>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="90%">
	<thead>
		<tr align="center"> 
	 		<th width='30'>S.N.</th>
			<th align="left" width='100'>User</th>
			<th align="left" width='100'>Role</th>
			<th align="center" width='100'>LoginType</th>
			<th align="left" width='100'>Description</th>
			<th align="center" width='40'>Edit</th>
		</tr>
	</thead>
	<tbody>
		<% 
			com.enercon.admin.dao.AdminDao ad = new com.enercon.admin.dao.AdminDao();
			List displayLogin = new ArrayList();
			displayLogin = ad.getLoginDetails();
			
			int size = displayLogin.size();
			
			for (int i = 0; i < size; i++) {
				
				Vector v = new Vector();
				v = (Vector) displayLogin.get(i);
				String a = (String) v.get(0);
				String b = (String) v.get(1);
				String c = (String) v.get(2);
				String d = (String) v.get(3);
				String e = (String) v.get(4);		
			%>	
				<tr align="center" class="gradeA">	
					<td><%=i+1%></td>
					<td align="left"><%=b%></td>
					<td align="left"><%=c%></td>
					<td align="center"><%=d%></td>
					<td align="left"><%=e%></td>
					<td align="center"><input type='image' src="<%=request.getContextPath()%>/resources/images/edit.gif" onClick="findDetails('<%=a%>')"></td>
				</tr>
			<%
			}
		%>
		
	</tbody>	
</table>

	
</body>
</html>