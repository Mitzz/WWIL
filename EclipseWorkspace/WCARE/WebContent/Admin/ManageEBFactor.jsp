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
String eid = "";
String facid = "";
String stid = "";
String ebtype="";
String ebstype="" ;	
String ebmfactor = "";	
String ebcapacity = "";
String ebsite = "";	
String fromdate="", todate = "";
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	stid = dynaBean.getProperty("Statetxt").toString();
	ebsite = dynaBean.getProperty("EBSitetxt").toString();	
	eid = dynaBean.getProperty("EBIdtxt").toString();	
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		facid = "";
		ebtype="";
		ebstype="" ;	
		ebmfactor = "";	
		ebcapacity = "";
		fromdate = "";
		todate = "";
	}else{
		ebtype = dynaBean.getProperty("EBTypetxt").toString();
		ebstype = dynaBean.getProperty("EBSubTypetxt").toString();	
		ebmfactor = dynaBean.getProperty("EBMFactortxt").toString();	
		ebcapacity = dynaBean.getProperty("EBCapacitytxt").toString();		
		facid = dynaBean.getProperty("FacId").toString();		
		fromdate = dynaBean.getProperty("FromDatetxt").toString();		
		todate = dynaBean.getProperty("ToDatetxt").toString();				
	}	
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String sitename = "";
String ebname = "";
String statename = "";

String roleid=session.getAttribute("RoleID").toString();

if(roleid.equals("0000000001"))
	statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
else
	 statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",stid,session.getAttribute("loginID").toString());

if(!statename.equals(""))
{ 
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS",ebsite,stid+","+session.getAttribute("loginID").toString());
}
if(!sitename.equals(""))
{ 
	ebname=AdminUtil.fillWhereMaster("SELECT_EB_MASTER",eid,ebsite);
}

%>
<script type="text/javascript">

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
	document.forms[0].EBSitetxt.options.length = 0;
	document.forms[0].EBSitetxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].EBSitetxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
 	    }
    }
}

function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].EBSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
    
    
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);
	// alert("");
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{       
     var cart = dataXml.getElementsByTagName("ebmaster")[0];	     
     var items = cart.getElementsByTagName("ebcode");	
		document.forms[0].EBIdtxt.options.length = 0;
		document.forms[0].EBIdtxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	    	var item = items[I]
	    	var nname = item.getElementsByTagName("ebshort")[0].firstChild;
	    	if (nname != null)
	    	{
	    		document.forms[0].EBIdtxt.options[I + 1] = new Option(item.getElementsByTagName("ebshort")[0].firstChild.nodeValue,item.getElementsByTagName("ebid")[0].firstChild.nodeValue);
	 	    }
	     }
}
function findDetails()
{    
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].EBIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBFactorByEBID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];	     
	     var items = cart.getElementsByTagName("ebcode");	
		var divdetails = document.getElementById("ebdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>"
		str+="<th class='detailsheading' width='90'>EB Type</th><th class='detailsheading' width='90'>Sub Type</th>"
		str+="<th class='detailsheading' width='90'>Capacity</th><th class='detailsheading' width='90'>M Factor</th>"
		str+="<th class='detailsheading' width='90'>From Date</th><th class='detailsheading' width='90'>To Date</th>"
		str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  		   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
	     	if (nname != null){
	     		
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("ebtype")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("ebstype")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='left'>" + item.getElementsByTagName("ebcapacity")[0].firstChild.nodeValue + "</td>"  
	     		str+="<td align='left'>" + item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("fromdate")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("todate")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=Details('" + item.getElementsByTagName("facid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 	}   	 	
		}
		divdetails.innerHTML = str;
}

function Details(ebid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = ebid;
	 req.onreadystatechange = getReadyStateHandler(req, showdetails);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBFactorByID&AppId="+ApplicationId);
}
function showdetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	if (nname != null){
     		document.forms[0].FromDatetxt.value = item.getElementsByTagName("fromdate")[0].firstChild.nodeValue;
   	 		document.forms[0].ToDatetxt.value = item.getElementsByTagName("todate")[0].firstChild.nodeValue;
   	 		document.forms[0].EBTypetxt.value = item.getElementsByTagName("ebtype")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBSubTypetxt.value = item.getElementsByTagName("ebstype")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBCapacitytxt.value = item.getElementsByTagName("ebcapacity")[0].firstChild.nodeValue;
   	 		document.forms[0].EBMFactortxt.value = item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue;  
   	 		document.forms[0].FacId.value = item.getElementsByTagName("facid")[0].firstChild.nodeValue;  
   	 	}
   	 	else{
   	 		document.forms[0].FromDatetxt.value = "";
   	 		document.forms[0].ToDatetxt.value = "";
   	 		document.forms[0].EBTypetxt.value = "";  
   	 		document.forms[0].EBSubTypetxt.value = "";  
   	 		document.forms[0].EBCapacitytxt.value = "";
   	 		document.forms[0].EBMFactortxt.value = "";
   	 		document.forms[0].FacId.value ="";
   	 	} 			
 	}
}

function gotoSave() 
{
    var blnSave=false;
    blnSave = validateForm('Multiplication Factor',document.forms[0].EBMFactortxt.value,'M','M'
                           );
     if ( blnSave == true ) {
        return true;
     } else {
        return false;
     }
}
function confirmation()
{   
	// if(document.forms[0].Submit.value=='Update')
	if(document.forms[0].FacId.value!="")
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
<body onload="findDetails()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/EBMFactor.do" method="post" onSubmit="return gotoSave()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">EB Multiplication Factor Master</th>
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
			<td id="t_company" width="219">&nbsp;Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findSite()">
					<option value="">--Make a Selection--</option>
					<%=statename%>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Site Name:</td>
		  <td bgcolor="#ffffff">
		    <select name="EBSitetxt" id="EBSitetxt" class="ctrl" onChange="findApplication()">
              <option value="">--Make a Selection--</option>
				   <%=sitename%>     
            </select>
		  </td>
	  	</tr>
	  	<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Select EB:</td>
		  <td bgcolor="#ffffff" >
		    <select name="EBIdtxt" id="EBIdtxt" class="ctrl" onChange="findDetails()">
              <option value="">--Make a Selection--</option>
				   <%=ebname%>     
            </select>
		  </td>
	  	</tr>
	  	<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select From Date:</td>
			<td class="bgcolor">
				<input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" value="<%=fromdate%>"  maxlength="10" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select To Date:</td>
			<td class="bgcolor">
				<input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10" value="<%=todate%>"  onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_city">&nbsp;Type:</td>
			<td ><select name="EBTypetxt" id="EBTypetxt" class="ctrl">
              <option value="" selected="selected">-- select --</option>
              <%if (ebtype.equals("4Q")){ %>
	              <option value="4Q" selected>4Q</option>
	          <%}else{ %>
	          	  <option value="4Q">4Q</option>
	          <%} if (ebtype.equals("2Q")){%>
              		<option value="2Q" selected>2Q</option>
               <%}else{ %>
               		<option value="2Q">2Q</option>
               <%}%>
            </select></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_general_information">&nbsp;SubType:</td>
			<td bgcolor="#ffffff" >
			<select name="EBSubTypetxt" id="EBSubTypetxt" class="ctrl">
              <option value="" selected="selected">-- select --</option>
               <%if (ebstype.equals("NTOD")){ %>
	              <option value="NTOD" selected>NTOD</option>
	          <%}else{ %>
	          	  <option value="NTOD">NTOD</option>
	          <%} if (ebstype.equals("TOD")){%>
              		<option value="TOD" selected>TOD</option>
               <%}else{ %>
               		<option value="TOD">TOD</option>
               <%}%>
     		</select>
     		</td>
		</tr>
		
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Capacity</td>
		  <td bgcolor="#ffffff" ><input name="EBCapacitytxt" id="EBCapacitytxt" value="<%=ebcapacity%>" size="35" class="ctrl" type="text" /></td>
		  </tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Multifactor</td>
		  <td bgcolor="#ffffff" ><input name="EBMFactortxt" id="EBMFactortxt" value="<%=ebmfactor%>" size="35" class="ctrl" type="text" onblur="validateDecimal(EBMFactortxt)" /></td>
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
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit" onClick="return confirmation();"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="EBMFactor" />		
		<input type="hidden" name="FacId" value="<%=facid %>" />
		<input type="hidden" name="RoleId" value="<%=roleid %>">
		<input name="Reset" value="Cancel" class="btnform" type="reset">
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
		<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody>
			<tr>
				<td >
					<div id="ebdetails"></div>
				</td>
			</tr>
			</tbody>
		</table>	
	</td>
</tr>
</table>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>