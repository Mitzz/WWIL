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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>\
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.7.2.min.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
System.out.println("In ManageWECCurtailment.jsp");
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
System.out.println("DynaBean:" + dynaBean);
if(dynaBean != null){
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
if(roleid.equals("0000000001"))
	statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
else
	 statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",stid,session.getAttribute("loginID").toString());
System.out.println("State:" + statename);
if(!statename.equals("")){ 
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS",ebsite,stid+","+session.getAttribute("loginID").toString());
}
if(!sitename.equals("")){ 
	ebname=AdminUtil.fillWhereMaster("SELECT_EB_MASTER",eid,ebsite);
}
if(!ebname.equals("")){ 
	wecname=AdminUtil.fillWhereMaster("SELECT_WEC_MASTER",wecid,eid);
}
System.out.println("SiteNAme:" + sitename);
System.out.println("EBName:" + ebname);
System.out.println("Wecname:" + wecname);
%>
<script type="text/javascript">
function validateForm(form) {
    var error = "";
    var LOWEST_HOUR_VALUE = 0;
    var HIGHEST_HOUR_VALUE = 23;
    var LOWEST_MINUTE_VALUE = 0;
    var HIGHEST_MINUTE_VALUE = 59;
    var CURTAILED_CAPACITY_LOWER_VALUE = 1;
    var CURTAILED_CAPACITY_HIGHER_VALUE = 1000;
    
    /*Utility Function*/
    function inRangeCheckingInclusive(value, lowerValue, higherValue) {
        if (value > higherValue) {
            return false;
        }
        else if (value < lowerValue) {
            return false;
        }
        else {
            return true;
        }
    }
    
    function checkingNumericValue(valueInString){
    	if(isNaN(parseInt(valueInString))){
    		return false;
    	}
    	return true;
    }
    
    function calculateTimeDifferenceBetweenHandoverandTakeover(handoverTime, takeoverDate,takeoverTime){
    	if(takeoverDate != '' && takeoverTime !=  ''){
    		//alert(handoverTime +  ':' + takeoverDate + ':' + takeoverTime);
    		var handoverDateTimeSplit = handoverTime.split(" ");
    		//alert(handoverDateTimeSplit[0] + ":" + handoverDateTimeSplit[1]);
    		var handoverDateSplit = handoverDateTimeSplit[0].split("/");
    		var handoverHourMinuteSplit = handoverDateTimeSplit[1].split(":");
    		
    		var handoverDay = handoverDateSplit[0];
    		var handoverMonth = handoverDateSplit[1];
    		var handoverYear = handoverDateSplit[2];
    		var handoverHour = handoverHourMinuteSplit[0];
    		var handoverMinute = handoverHourMinuteSplit[1];
    		
    		//alert(handoverDay + " " + handoverMonth + " " + handoverYear + " " + handoverHour + " " + handoverMinute);
    		
    		var takeoverDateSplit = takeoverDate.split("/");
    		var takeoverTimeSplit = takeoverTime.split(":");
    		
    		var takeoverDay = takeoverDateSplit[0];
    		var takeoverMonth = takeoverDateSplit[1];
    		var takeoverYear = takeoverDateSplit[2];
    		var takeoverHour = takeoverTimeSplit[0];
    		var takeoverMinute = takeoverTimeSplit[1];
    		
    		//alert(takeoverHour + " " + takeoverMinute + " " + takeoverDay + " " + takeoverMonth + " " + takeoverYear);
    		
    		var handoverDateObject = new Date(handoverYear, handoverMonth, handoverDay, handoverHour, handoverMinute, 0);
    		var takeoverDateObject = new Date(takeoverYear, takeoverMonth, takeoverDay, takeoverHour, takeoverMinute, 0);
    		
    		var differenceInMinutes = (takeoverDateObject.getTime() - handoverDateObject.getTime()) / (1000 * 60);
    		//var differenceInHours = differenceInMinutes / 60;
    		
    		//alert(differenceInMinutes);
    		//alert(differenceInHours);
    		if(differenceInMinutes < 0){
    			return -1;
    		}
    		
    		return differenceInMinutes;
 	
    	}
    	else{
    		return -1;
    	}
    }
    
    function minutesToHH_MMFormat(minutesValue){
    	var hh_mmFormat = 0;
    	var minutes = new String(parseInt(minutesValue % 60, 10));
    	var hour = new String(parseInt(minutesValue / 60, 10));
    	if(hour.length == 1){
    		hour = "0" + hour;
    	}
    	else if(hour == 0){
    		hour = "00";
    	}
    	
    	if(minutes.length == 1){
    		minutes = "0" + minutes;
    	}
    	else if(minutes == 0){
    		minutes = "00";
    	}
    	
    	hh_mmFormat = hour + ":" + minutes;
    	return hh_mmFormat;
    }
    
    /*Start Time Validation*/
    var startTimeFieldValue = form.startTime.value;
    var timeString = startTimeFieldValue.split(":");
    if (timeString.length != 2) {
        error = "Enter Start Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    else if(startTimeFieldValue.length != 5){
    	error = "Enter Start Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    
    if (!(checkingNumericValue(timeString[0]) && checkingNumericValue(timeString[1]))) {
        error = "Enter a Numeric value for Start Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    
    var hourInt = parseInt(timeString[0]);
    var minuteInt = parseInt(timeString[1]);
    
    var hourInRange = inRangeCheckingInclusive(hourInt, LOWEST_HOUR_VALUE, HIGHEST_HOUR_VALUE);
    if (!hourInRange) {
        alert("Enter Hour Value between " + LOWEST_HOUR_VALUE + " and " + HIGHEST_HOUR_VALUE + " in Start Time.");
        return false;
    }
    else {
    	console.log("Proper Hour Value in Start Time");
    }
    
    var minuteInRange = inRangeCheckingInclusive(minuteInt, LOWEST_MINUTE_VALUE, HIGHEST_MINUTE_VALUE);
    if (!minuteInRange) {
        alert("Enter Minute Value between " + LOWEST_MINUTE_VALUE + " and " + HIGHEST_MINUTE_VALUE + " in Start Time.");
        return false;
    }
    else {
        console.log("Proper Minute Value in Start Time");
    }
    
    /*End Time Validation*/
    var endTimeFieldValue = form.endTime.value;
    var timeString = endTimeFieldValue.split(":");
    if (timeString.length != 2) {
        error = "Enter End Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    else if(endTimeFieldValue.length != 5){
    	error = "Enter End Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    
    if (!(checkingNumericValue(timeString[0]) && checkingNumericValue(timeString[1]))) {
        error = "Enter a Numeric value for End Time in 'HH:MM' format";
        alert(error);
        return false;
    }
    
    var hourInt = parseInt(timeString[0]);
    var minuteInt = parseInt(timeString[1]);
    
    var hourInRange = inRangeCheckingInclusive(hourInt, LOWEST_HOUR_VALUE, HIGHEST_HOUR_VALUE);
    if (!hourInRange) {
        alert("Enter Hour Value between " + LOWEST_HOUR_VALUE + " and " + HIGHEST_HOUR_VALUE + " in End Time.");
        return false;
    }
    else {
        console.log("Proper Hour Value in End Time");
    }
    var minuteInRange = inRangeCheckingInclusive(minuteInt, LOWEST_MINUTE_VALUE, HIGHEST_MINUTE_VALUE);
    if (!minuteInRange) {
        alert("Enter Minute Value between " + LOWEST_MINUTE_VALUE + " and " + HIGHEST_MINUTE_VALUE + " in End Time.");
        return false;
    }
    else {
        console.log("Proper Minute Value in  End Time");
    }

    /*Start Date and End Date Validation*/
    var startDateTimeValue = form.startDatetxt.value + " " + form.startTime.value;
    var endDateValue = form.endDatetxt.value;
    var endTimeValue = form.endTime.value;
    
    //alert("startDateTimeValue:" + startDateTimeValue);
    //alert("endDateValue:" + endDateValue);
    //alert("endTimeValue:" + endTimeValue);
    if(calculateTimeDifferenceBetweenHandoverandTakeover(startDateTimeValue, endDateValue,endTimeValue) > 0){
    	console.log("Proper Start End Value");
    }
    else{
    	alert("Start Date and Time must come before End Date and Time");
    	return false;
    }
    
    /*Curtailed Capacity validation*/
    var curtailedCapacityFieldValue = form.curtailCapacity.value;
    var curtailedCapacityValue = parseInt(curtailedCapacityFieldValue);
    if (isNaN(curtailedCapacityValue)) {
        error = "Curtailed Capacity:Enter a numeric value between " + CURTAILED_CAPACITY_LOWER_VALUE + " to " + CURTAILED_CAPACITY_HIGHER_VALUE;
        alert(error);
        return false;
    }
    else if (curtailedCapacityValue > CURTAILED_CAPACITY_HIGHER_VALUE) {
        error = "Curtailed Capacity cannot be greater than " + CURTAILED_CAPACITY_HIGHER_VALUE;
        alert(error);
        return false;
    }
    else if (curtailedCapacityValue < CURTAILED_CAPACITY_LOWER_VALUE) {
        error = "Curtailed Capacity cannot be less than " + CURTAILED_CAPACITY_LOWER_VALUE;
        alert(error);
        return false;
    }
    else {
        console.log("Proper Curtailed Capacity");
    }
    
    /*Curtailment Remarks*/
    if(form.curtailRemark.value.length == 0){
    	error = "Curtailments Remarks Field cannot be empty";
    	alert(error);
    	return false;
    }
    return true;
}

/* - Invoke on Selecting State 
 * - To find Site for that particular State based on the State_ID 
 * - Using Ajax and 'displaySiteOnPage' function is register to handle the request 
 */
function findSiteByStateId(stateField){
	var stateId = stateField.options[stateField.selectedIndex].value;
	var stateIdRoleId = stateId + "," + document.forms[0].RoleId.value;
	var ApplicationId = stateIdRoleId; 
	var ajaxRequest = newXMLHttpRequest();
	ajaxRequest.onreadystatechange = getReadyStateHandler(ajaxRequest, displaySiteOnPage);	  
	ajaxRequest.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajaxRequest.send("Admin_Input_Type=getSiteBYRIGHTS&AppId="+ApplicationId);
	
	/*Ajax Callback Function*/
	function displaySiteOnPage(dataXml){
		var cart = dataXml.getElementsByTagName("sitehead")[0];
		var items = cart.getElementsByTagName("sitecode");
		document.forms[0].siteIdTxt.options.length = 0;
		document.forms[0].siteIdTxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++){   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null){
	     		document.forms[0].siteIdTxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
	 	    }
	    }
	}
}

/* - Invoke on Selecting Site
 * - To find WEC for that particular Site based on the Site_ID	
 * - Using Ajax and 'displayWECBySiteOnPage' function is register to handle the request 
 */
function findWECBySiteId(siteField){
	var siteId = siteField.options[siteField.selectedIndex].value;
    var siteIdRoleId = siteId + "," + document.forms[0].RoleId.value;
    /* alert("Site ID:" + siteId);
	alert("Site Id & RoleId:" + siteIdRoleId); */
	var ApplicationId = siteIdRoleId;
	var ajaxRequest = newXMLHttpRequest();
	ajaxRequest.onreadystatechange = getReadyStateHandler(ajaxRequest, displayWECBySiteOnPage);	  
	ajaxRequest.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajaxRequest.send("Admin_Input_Type=getWECBYSiteID&AppId="+ApplicationId);

	/*Ajax Callback Function handling the response*/
	function displayWECBySiteOnPage(dataXml){
		var cart = dataXml.getElementsByTagName("wecroot")[0];
		var items = cart.getElementsByTagName("weccode");
		document.forms[0].WECIdtxt.options.length = 0;
		document.forms[0].WECIdtxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++){   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("S_WECSHORT_DESCR")[0].firstChild;
	     	if (nname != null){
	     		var content = item.getElementsByTagName("S_WECSHORT_DESCR")[0].firstChild.nodeValue;
	     		var id = item.getElementsByTagName("S_WEC_ID")[0].firstChild.nodeValue;
	     		document.forms[0].WECIdtxt.options[I + 1] = new Option(content, id);
	     		document.forms[0].nearWECIdtxt.options[I + 1] = new Option(content, id);
	     	}
	    }
	}
}

/* - Invoke on Selecting Site
 * - To find WEC which are in curtailment at a particular SITE based on the Site_ID	
 * - Using Ajax and 'displayWECCurtailmentInSite' function is register to handle the request 
 */
function determineCurtailedWECBySiteId(siteField){
	var siteId = siteField.options[siteField.selectedIndex].value;
    var siteIdRoleId = siteId + "," + document.forms[0].RoleId.value;
    var ApplicationId = siteIdRoleId;
	var ajaxRequest = newXMLHttpRequest();
	ajaxRequest.onreadystatechange = getReadyStateHandler(ajaxRequest, displayCurtailedWECBySiteOnPage);	  
	ajaxRequest.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajaxRequest.send("Admin_Input_Type=getCurtailedWECBYSiteID&AppId="+ApplicationId);
	 
	function displayCurtailedWECBySiteOnPage(dataXml){
		var weccurtail = dataXml.getElementsByTagName("weccurtail")[0];
		var wecdetail = weccurtail.getElementsByTagName("wecdetail");
		
			var divdetails = document.getElementById("curtailWECDisplay");
			divdetails.innerHTML = "";
			var str = "<table border='0' cellpadding='2' cellspacing='1' width='94%' align='center'>\n";
				str+="\t<tr align='center' height='20'>\n\t\t<th class='detailsheading' width='30'>S.N.</th>\n";
				str+="\t\t<th class='detailsheading' width='90'>WEC Name</th>\n";
				str+="\t\t<th class='detailsheading' width='90'>Capacity</th>\n";
				str+="\t\t<th class='detailsheading' width='90'>From Date</th>\n";
				str+="\t\t<th class='detailsheading' width='90'>End Date</th>\n";
				str+="\t\t<th class='detailsheading' width='120'>Remarks</th>\n";
				str+="\t\t<th class='detailsheading' width='30'>Edit</th>\n\t</tr>\n";
			for (var I = 0 ; I < wecdetail.length ; I++){	  	
				var item = wecdetail[I];
		     	var nname = item.getElementsByTagName("S_WEC_ID")[0].firstChild;
		     	if (nname != null){
		     		if (I % 2 == 0){
				        str+="\t<tr style = 'text-align: center;' height='20' class='detailsbody edit'>\n\t\t<td ALIGN='center'>"+(I+1)+"</td>\n"
				    }
				    else{
						str+="\t<tr style = 'text-align: center;' height='20' class='detailsbody1 edit'>\n\t\t<td ALIGN='center'>"+(I+1)+"</td>\n"
					}	     		
		     		str+="\t\t<td style = 'text-align: center;' class='S_WECSHORT_DESCR'>" + item.getElementsByTagName("S_WECSHORT_DESCR")[0].firstChild.nodeValue + "</td>\n"
		     		str+="\t\t<td style = 'text-align: center;' class='N_CURTAILED_CAPACITY'>" + item.getElementsByTagName("N_CURTAILED_CAPACITY")[0].firstChild.nodeValue + "</td>\n"		
		     		str+="\t\t<td style = 'text-align: center;' class='D_START_DATE'>" + item.getElementsByTagName("D_START_DATE")[0].firstChild.nodeValue + "</td>\n"
		     		str+="\t\t<td style = 'text-align: center;' class='D_END_DATE'>" + item.getElementsByTagName("D_END_DATE")[0].firstChild.nodeValue + "</td>\n"
		     		str+="\t\t<td style = 'text-align: center;' class='S_CURTAILMENT_REMARKS'>" + item.getElementsByTagName("S_CURTAILMENT_REMARKS")[0].firstChild.nodeValue + "</td>\n"
		     		str+="\t\t<td style = 'text-align: center;' onclick = 'edit(this);'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' title='Click to Edit record' alt='Click to edit the record' /></td>\n\t</tr>\n"; 
		     		/* str+="onClick=findDetails('" + item.getElementsByTagName("sid")[0].firstChild.nodeValue + "')></td></tr>" */
		   	 	}   	 	
			}
			str += "</table>";
			console.log(str);
			divdetails.innerHTML = str;
			
	}
}

function edit(fwqw){
	console.log("Edit clicked");
	var tableRow = fwqw.parentNode;
	var WECName = tableRow.getElementsByClassName("S_WECSHORT_DESCR")[0].innerHTML;
	var startDate = tableRow.getElementsByClassName("D_START_DATE")[0].innerHTML;
	var curtailedCapacity = tableRow.getElementsByClassName("N_CURTAILED_CAPACITY")[0].innerHTML;
	var endDate = tableRow.getElementsByClassName("D_END_DATE")[0].innerHTML;
	var curtailmentRemark = tableRow.getElementsByClassName("S_CURTAILMENT_REMARKS")[0].innerHTML;
	
	alert(WECName);
	alert(startDate);
	alert(curtailedCapacity);
	alert(endDate);
	alert(curtailmentRemark);
}
</script>
</head>
<body>

<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/WECCurtailment.do" method="post" onSubmit="return validateForm(this);">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody>
<tr>
	<td class="newhead1"></td>
	<th class="headtext">Manage WEC Curtailment</th>
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
				<select name="stateId" id="stateId" class="ctrl" style='width: 150px' onChange="findSiteByStateId(this);">
					<option value="">--Make a Selection--</option>
					<%=statename%>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Select Site:</td>
		  <td bgcolor="#ffffff">
		    <select name="siteIdTxt" id="siteIdTxt" class="ctrl" style='width: 150px' onChange="findWECBySiteId(this);determineCurtailedWECBySiteId(this);">
              <option value="">--Make a Selection--</option>
				   <%=sitename%>     
            </select>
		  </td>
	  	</tr>
	  	<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Select WEC:</td>
		  <td bgcolor="#ffffff" id = "sdf">
		    <select name="WECIdtxt" id="WECIdtxt" class="ctrl" style='width: 150px' onchange="findNearByWEC()" >
              <option value="">--Make a Selection--</option>
				<%=wecname%>     
            </select>
		  </td>
	  	</tr>
	  	<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Near by WEC:</td>
		  <td bgcolor="#ffffff">
		    <select name="nearWECIdtxt" id="nearWECIdtxt" class="ctrl" style='width: 150px' onChange="findDetails()">
              <option value="">--Make a Selection--</option>
				   <%=wecname%>     
            </select>
		  </td>
	  	</tr>
	  	<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Start Date:</td>
			<td class="bgcolor">
				<input type="text" name="startDatetxt" id="startDatetxt" size="18" class="ctrl" value="<%=fromdate%>"  maxlength="10" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].startDatetxt);return false;" >
					<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
				</a>
			</td>
		</tr>	
		<tr class="bgcolor">
			<td id="t_street_address">&nbsp;Start Time:</td>
			<td class="bgcolor">
				<input name="startTime" id="startTime" value="<%=ebcapacity%>" size="15" class="ctrl" type="text" /> in (HH:MM)
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;End Date:</td>
			<td class="bgcolor">
				<input type="text" name="endDatetxt" id="endDatetxt" size="18" class="ctrl" maxlength="10" value="<%=todate%>"  onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].endDatetxt);return false;" >
					<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
				</a>
			</td>
		</tr>
		<tr class="bgcolor">
			<td id="t_street_address">&nbsp;End Time:</td>
			<td class="bgcolor"><input placeholder="24 hour format" name="endTime" id="endTime" value="<%=ebcapacity%>" size="15" class="ctrl" type="text" />in (HH:MM)</td>
		</tr>		
		<tr bgcolor="#ffffff">
			<td id="t_general_information">&nbsp;Curtailed Capacity:</td>
			<td bgcolor="#ffffff" ><input name="curtailCapacity" id="curtailCapacity" value="<%=ebcapacity%>" size="15" class="ctrl" type="text" /></td>
		</tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Curtailment Remarks:</td>
		  <td bgcolor="#ffffff" >
		  	<input name="curtailRemark" id="curtailRemark" value="<%=ebmfactor%>" size="25" class="ctrl" type="text" /></td>
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
				<input name="Submit" value="Submit" class="btnform" type="submit">
			</td>
			<td width="1">
				<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
			</td>
			<td class="btn" width="100">
				<input type="hidden" name="Admin_Input_Type" value="WECCurtailment" />		
				<input type="hidden" name="FacId" value="<%=facid %>" />
				<input type="hidden" name="RoleId" value="<%=roleid %>">
				<input name="Reset" value="Cancel" class="btnform" type="reset">
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