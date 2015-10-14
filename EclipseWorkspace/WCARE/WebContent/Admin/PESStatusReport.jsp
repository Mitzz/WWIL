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
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.7.2.min.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
System.out.println("In PESStatusReport");
String locationNo = "";
String eid = "";
String wecid = "";
String facid = "";
String stid = "";
String ebtype="";
String ebstype="" ;	
String ebmfactor = "";	
String ebcapacity = "";
String ebsite = "";	
String fromdate="", todate = "";
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
//System.out.println("DynaBean:" + dynaBean);
if(dynaBean != null){
	locationNo = dynaBean.getProperty("locationNo").toString();
	stid = dynaBean.getProperty("stateId").toString();
	ebsite = dynaBean.getProperty("siteIdTxt").toString();	
	eid = dynaBean.getProperty("EBIdtxt").toString();
	wecid = dynaBean.getProperty("WECIdtxt").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		facid = "";
		ebtype= "";
		ebstype="" ;	
		ebmfactor = "";	
		ebcapacity = "";
		fromdate = "";
		todate = "";
	}else{
		locationNo = dynaBean.getProperty("locationNo").toString();
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
String wecname = "";
String statename = "";

String roleid=session.getAttribute("RoleID").toString();
System.out.println("RoleID:" + roleid);
//statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
locationNo = AdminUtil.fillLocationMaster("TBL_LOCATION_MASTER1","");
//System.out.println(AdminUtil.fillLocationMaster("TBL_LOCATION_MASTER",""));
%>
<script type="text/javascript">
function findPlantNoByLocationId(locationNoField){
	console.log("findPlantNoByLocationId Invoked");
	var locationId = locationNoField.options[locationNoField.selectedIndex].value;
	console.log("Location No Selected:" + locationId);
    var locationIdRoleId = locationId + "," + document.forms[0].RoleId.value;
    console.log("Role Id and Location Id:" + locationIdRoleId);
    /* var ApplicationId = locationIdRoleId; */
    var ApplicationId = locationId;
	var ajaxRequest = newXMLHttpRequest();
	ajaxRequest.onreadystatechange = getReadyStateHandler(ajaxRequest, displayPlantNoOnPage);	  
	ajaxRequest.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajaxRequest.send("Admin_Input_Type=getPlantNoBasedOnLocationNo&AppId="+ApplicationId);

	/*Ajax Callback Function handling the response*/
	function displayPlantNoOnPage(dataXml){
		console.log("displayPlantNoOnPage Invoked");
		console.log("Data XML:" + dataXml);
		var cart = dataXml.getElementsByTagName("plantmaster")[0];
		var items = cart.getElementsByTagName("plantcode");
		document.forms[0].plantNo.options.length = 0;
		document.forms[0].plantNo.options[0] = new Option("--All Plants--","All");
		for (var I = 0 ; I < items.length ; I++){   
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("plantno")[0].firstChild;
	     	if (nname != null){
	     		var id = item.getElementsByTagName("plantno")[0].firstChild.nodeValue;
	     		var content = item.getElementsByTagName("plantwec")[0].firstChild.nodeValue;
	     		document.forms[0].plantNo.options[I + 1] = new Option(content, id);
	     		//document.forms[0].nearWECIdtxt.options[I + 1] = new Option(content, id);
	     	}
	    }
	}
}

function getMessage(action){
	//alert($("div:contains('John')"));
	if(action.indexOf("/report.do") != -1){
		return "Want to Generate Report?";
	}
	if(action.indexOf("/activate.do") != -1){
		return "Want to Activate Machine?";
	}
	if(action.indexOf("/deactivate.do") != -1){
		return "Want to Deactivate Machine?";
	}
	
}

function submitDifferentAction(formReference,action){
	document.myform.action = action ;
	if(confirm(getMessage(action))){
		formReference.submit();
	}
}
function changeAdminType(t){
	//alert(t.value);
	var h = document.getElementById("adminType");
	h.value = t.value;
}
</script>
</head>
<body>

<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form name="myform" action="" method="post" onSubmit="return false;">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody>
<tr>
	<td class="newhead1"></td>
	<th class="headtext">WEC PES Status Report</th>
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
			<td id="t_company" width="219">&nbsp;Select Location No:</td>
			<td valign="top">
				<select name="locationNo" id="locationNo" class="ctrl" style='width: 150px' onChange="findPlantNoByLocationId(this);">
					<option value="All">--All Location--</option>
					<%=locationNo%>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Select Plant No:</td>
		  <td bgcolor="#ffffff">
		    <select name="plantNo" id="plantNo" class="ctrl" style='width: 150px'>
              <option value="">--Select Location--</option>
				   <%=sitename%>     
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
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
		<tbody>
		<tr>
			<td class="btn" width="100">
				<input name="Submit" value="Deactivate" class="btnform" type="submit" onclick="changeAdminType(this);submitDifferentAction(this.form,'<%=request.getContextPath()%>/deactivate.do')"/>
			</td>
			<td width="1">
				<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
			</td>
			<td class="btn" width="100">
				<input name="Submit" value="Activate" class="btnform" type="submit" onclick="changeAdminType(this);submitDifferentAction(this.form,'<%=request.getContextPath()%>/activate.do')" />
			</td>
			<td width="1">
				<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
			</td>
			<td width="1">
				<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
			</td>
			<td class="btn" width="100">
				<input id="adminType" type="hidden" name="Admin_Input_Type" value="WECPESAction" />		
				<input type="hidden" name="FacId" value="<%=facid %>" />
				<input type="hidden" name="RoleId" value="<%=roleid %>">
				<!-- <input name="Reset" value="Cancel" class="btnform" type="reset"> -->
				<input name="Submit" value="Report" class="btnform" type="submit" onclick="changeAdminType(this);submitDifferentAction(this.form,'<%=request.getContextPath()%>/report.do')" />
			</td>
			<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		</tr>
		</tbody>
	</table>
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
					<div id="curtailWECDisplay"></div>
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