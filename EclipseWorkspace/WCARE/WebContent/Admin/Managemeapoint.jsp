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
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String mid = "";
String mname = "";
String mtype = "";
String mshow = "";
String munit = "";
String seqno = "";
String type = "";
int status = 1;
int cum = 1;
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	mid = dynabean.getProperty("MPIdtxt").toString();
	mname = dynabean.getProperty("MPDesctxt").toString();
	mtype = dynabean.getProperty("MPTypetxt").toString();
	mshow= dynabean.getProperty("MPShowtxt").toString();
	munit = dynabean.getProperty("MPUnittxt").toString();
	seqno = dynabean.getProperty("Seqnotxt").toString();
	if(dynabean.getProperty("Statustxt") == null){
		status = 1;
	}
	else if((dynabean.getProperty("Statustxt")).toString().equals("on")){
		status = 2;
	}
	if(dynabean.getProperty("Cumtxt") == null){
		cum = 1;
	}
	else if((dynabean.getProperty("Cumtxt")).toString().equals("on")){
		cum = 2;
	}
	type = dynabean.getProperty("ReadTypetxt").toString();
	session.removeAttribute("dynabean");
}

%>
<script type="text/javascript">

function findApplication() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findMPMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("mpmaster")[0];
	var items = cart.getElementsByTagName("mpcode");	
		var divdetails = document.getElementById("mpdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>"
		str+="<th class='detailsheading' width='180'>Measuring Point</th><th class='detailsheading' width='70'>Type</th>"
		str+="<th class='detailsheading' width='70'>Show In</th><th class='detailsheading' width='70'>Read Type</th><th class='detailsheading' width='70'>Unit</th>"
		str+="<th class='detailsheading' width='70'>SeqNo</th><th class='detailsheading' width='70'>Status</th>"		
		str+="<th class='detailsheading' width='70'>Cumulative</th>"
		str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("mpid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
     			str+="<td align='left'>" + item.getElementsByTagName("mpdesc")[0].firstChild.nodeValue + "</td>"
	     		str+="<td align='left'>" + item.getElementsByTagName("mptype")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("mpshow")[0].firstChild.nodeValue + "</td>"	  
	     		var readtype = "ALL";
	     		if (item.getElementsByTagName("readtype")[0].firstChild.nodeValue == "A")
	     			readtype = "ALL";
	     		else if (item.getElementsByTagName("readtype")[0].firstChild.nodeValue == "I")
	     			readtype = "Initial";
     			else if (item.getElementsByTagName("readtype")[0].firstChild.nodeValue == "D")
	     			readtype = "Daily";
	     		else if (item.getElementsByTagName("readtype")[0].firstChild.nodeValue == "M")
	     			readtype = "Monthly";
	     		else if (item.getElementsByTagName("readtype")[0].firstChild.nodeValue == "Y")
	     			readtype = "Yearly";
	     		str+="<td align='left'>" + readtype + "</td>"	  	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("mpunit")[0].firstChild.nodeValue + "</td>"	     			  
	     		str+="<td align='right'>" + item.getElementsByTagName("seqno")[0].firstChild.nodeValue + "</td>"
	     		if (item.getElementsByTagName("status")[0].firstChild.nodeValue == 1)
	     		{
	     			str+="<td align='center'>Active</td>"
	     		}else
	     		{
	     			str+="<td align='center'>De-Active</td>"
	     		}
	     		if (item.getElementsByTagName("cum")[0].firstChild.nodeValue == 1)
	     		{
	     			str+="<td align='center'>Yes</td>"	     			     			     		
	     		}else
	     		{
	     			str+="<td align='center'>No</td>"
	     		}
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("mpid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].MPIdtxt.value = "";
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str;
}
function findDetails(mpid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = mpid;
	 req.onreadystatechange = getReadyStateHandler(req, showMPMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findMPByID&AppId="+ApplicationId);
}
function showMPMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("mpmaster")[0];
	var items = cart.getElementsByTagName("mpcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("mpdesc")[0].firstChild;
     	
     	if (nname != null){
     	
     	
   	 		document.forms[0].MPDesctxt.value = item.getElementsByTagName("mpdesc")[0].firstChild.nodeValue;
   	 		document.forms[0].MPTypetxt.value = item.getElementsByTagName("mptype")[0].firstChild.nodeValue;
   	 		document.forms[0].MPShowtxt.value = item.getElementsByTagName("mpshow")[0].firstChild.nodeValue;  
   	 		document.forms[0].MPUnittxt.value = item.getElementsByTagName("mpunit")[0].firstChild.nodeValue;  
   	 		document.forms[0].ReadTypetxt.value = item.getElementsByTagName("readtype")[0].firstChild.nodeValue;  
   	 		document.forms[0].MPIdtxt.value = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;  
   	 		document.forms[0].Seqnotxt.value = item.getElementsByTagName("seqno")[0].firstChild.nodeValue;  
			if (item.getElementsByTagName("status")[0].firstChild.nodeValue == 1)
			{
   	 			document.forms[0].Statustxt.checked = 0;  
   	 		}
   	 		else
   	 		{	
   	 			document.forms[0].Statustxt.checked = 1;  
   	 		}
   	 		if (item.getElementsByTagName("cum")[0].firstChild.nodeValue == 1)
			{
   	 			document.forms[0].Cumtxt.checked = 1;  
   	 		}
   	 		else
   	 		{	
   	 			document.forms[0].Cumtxt.checked = 0;  
   	 		}
   	 		document.forms[0].Submit.value="Update";	 		
   	 	}
   	 	else{
   	 		document.forms[0].MPDesctxt.value = "";	
   	 		document.forms[0].MPTypetxt.value = "";
   	 		document.forms[0].MPShowtxt.value = "";	
   	 		document.forms[0].MPUnittxt.value = "";	 		
   	 		document.forms[0].MPIdtxt.value = "";  
   	 		document.forms[0].Seqnotxt.value = "";
   	 		document.forms[0].ReadTypetxt.value = "A";
  	 		document.forms[0].Statustxt.checked = false;
  	 		document.forms[0].Cumtxt.checked = false; 		
   	 	} 			
 	}
}
function confirmation()
{   
	// if(document.forms[0].Submit.value=='Update')
	if(document.forms[0].MPIdtxt.value!="")
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
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/MPMaster.do" method="post" onSubmit="return confirmation();">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Measuring Master</th>
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
			<td id="t_company">Name:</td>
			<td valign="top"><input name="MPDesctxt" id="MPDesctxt" value="<%=mname%>" size="35" class="ctrl" type="text" /></td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address_ln2" nowrap="nowrap">Type:</td>
			<td><select name="MPTypetxt" id="MPTypetxt"  class="ctrl">
              <option value="">-- select --</option>
			  <%if (mtype.equals("EB")){ %>
				<option value="EB" selected>EB</option>
			  <%}else{ %>
			  	<option value="EB">EB</option>
			  <%} if (mtype.equals("WEC")){%>
			  	<option value="WEC" selected>WEC</option>
			  <%}else{ %>
			  	<option value="WEC">WEC</option>
			  <%} %>  
			  </select></td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_city">Show In:</td>
			<td>
				<select name="MPShowtxt" id="MPShowtxt" class="ctrl">
              		<option value="" selected="selected">-- select --</option>
              		<%if (mshow.equals("EB")){%>
					  	<option value="EB" selected>EB</option>
				  	<%}else{ %>		
					  	<option value="EB">EB</option>
				  	<%}%>
				  	<%if (mshow.equals("WEC")){%>
					  	<option value="WEC" selected>WEC</option>	
				  	<%}else{ %>		
					  	<option value="WEC">WEC</option>	
				  	<%}%>					
					<%if (mshow.equals("BOTH")){%>
					  	<option value="BOTH" selected>BOTH</option>	
				  	<%}else{ %>		
					  	<option value="BOTH">BOTH</option>	
				  	<%}%>
			  	</select>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">Reading&nbsp;Type:</td>
			<td class="bgcolor">
				<select size="1" id="ReadTypetxt" name="ReadTypetxt" class="ctrl" >
					<% if (type.equals("A")){ %>
		            	<option value="A" selected>ALL</option>		            
		            <%}else{ %>
		            	<option value="A" >ALL</option>
		            <%}if (type.equals("I")){ %>
		            	<option value="I" selected>Initial</option>		            
		            <%}else{ %>
		            	<option value="I" >Initial</option>
		            <%} if (type.equals("D")){%>
		            	<option value="D" selected>Daily</option>
		            <%}else{ %>
		            	<option value="D" >Daily</option>
		            <%} if (type.equals("M")){%>
			            <option value="M" selected>Monthly</option>
		           	<%}else{ %>
		            	<option value="M" >Monthly</option>
		            <%} if (type.equals("Y")){%>
			            <option value="Y" selected>Yearly</option>
			        <%}else{ %>
			        	<option value="Y">Yearly</option>
			        <%} %>
		        </select>
			</td>
		</tr>	
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information">Unit</td>
			<td bgcolor="#ffffff">
				<select name="MPUnittxt" id="MPUnittxt" class="ctrl">
              		<option value="" selected="selected">-- select --</option>
              		<%if(munit.equals("HOURS")){ %>
						<option value="HOURS" selected>HOURS</option>
					<%} else { %>		
						<option value="HOURS">HOURS</option>
					<%}if(munit.equals("UNITS")){ %>			
						<option value="UNITS" selected>UNITS</option>
					<%} else { %>	
						<option value="UNITS">UNITS</option>
					<%}if(munit.equals("KW")) {%>		
						<option value="KW" selected>KW</option>					
					<%} else { %>	
						<option value="KW">KW</option>
					<%} %>
			  	</select>
			
			</td>
		</tr>
		<tr bgcolor="#ffffff">
			<td id="t_company">Seq. No.:</td>
			<td valign="top"><input name="Seqnotxt" id="Seqnotxt" value="<%=seqno%>" size="10" class="ctrl" type="text" onblur="validateNumber(Seqnotxt)" /></td>
		</tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Deactivate</td>
		  <td bgcolor="#ffffff">
		  <%if (status == 0 || status == 1){ %>
				<input type="checkbox" name="Statustxt" id="Statustxt" value="1" />
			<%}else{ %>
				<input type="checkbox" name="Statustxt" id="Statustxt" checked value="2" />
			<%} %>
			  </td>
	  	</tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Cumulative</td>
		  <td bgcolor="#ffffff">
		  	<%if (cum == 0 || cum == 1){ %>
				<input type="checkbox" name="Cumtxt" id="Cumtxt" value="1" />
			<%}else{ %>
				<input type="checkbox" name="Cumtxt" id="Cumtxt" checked value="2" />
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
	<tbody>
	<tr>
		
		
		<td class="btn" width="100"><input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="MPMaster" />		
		<input type="hidden" name="MPIdtxt" value="<%=mid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody>
</table>
</form>
</td>		
</tr>
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="650"><tbody>
			<tr>
				<td >
					<div id="mpdetails">
					
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