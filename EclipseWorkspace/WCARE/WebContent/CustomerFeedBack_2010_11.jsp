<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer FeedBack Form</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 

<% 
response.setHeader("Pragma","no-cache");
//response.getOutputStream().flush();
//response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();


%>
 
 <style type="text/css" media="screen">
		body		
		{
			margin: 0;
			padding: 0;
			text-align: center;
		}
		
		div#container		
		{
			margin: 1em auto;
			width: 80%;
			border: 1px solid #000;
			padding: 1em;
			background-color: #ddd;
			text-align: left;
		}
</style> 
<style type="text/css">
<!--
.style2 {font-family: Arial, Helvetica, sans-serif; font-size: 12px; }
.style3 {font-family: Arial, Helvetica, sans-serif}
.style4 {font-size: 18px}
.style5 {font-size: 18px}
.style8 {font-size: 18px;font-color: red}
.style6 {font-size: 12px}
.style7 {
	font-size: 24px;
	font-weight: bold;
}
.style8 {
	font-size: 20px;
	font-weight: bold;
}
.style10 {
    align: center;
	font-size: 12px;
	font-weight: bold;
	color: #FF0000;
}
.style11 {
	font-size: 12px;
	font-weight: bold;
	color:#336600;
}
.style12 {
	font-size: 12px;
	font-weight: bold;
	color:#666633;
}
.style13 {
	align: center;
	font-size: 12px;
	font-weight: bold;
	color:GREEN;
}
-->
</style>
<script type="text/javascript">
function checkWholeForm() {

    var why = "";
     	
     	why += checkRadio(document.forms[0].r1,'Select Priority of Concern in Asset Management');
     	why += checkRadio(document.forms[0].r2,'Select Priority of Concern in Asset Management');
     	why += checkRadio(document.forms[0].r3,'Select Priority of Concern in Asset Management');
     	why += checkRadio(document.forms[0].d1,'Rate for performance in Asset Management');
     	why += checkRadio(document.forms[0].d2,'Rate for performance in Asset Management');
     	why += checkRadio(document.forms[0].d3,'Rate for performance in Asset Management');
     	
     	
     	why += checkRadio(document.forms[0].r4,'Select Priority of Concern in Resource');
     	why += checkRadio(document.forms[0].r5,'Select Priority of Concern in Resource');
     	why += checkRadio(document.forms[0].d4,'Rate for performance in Resources');
     	why += checkRadio(document.forms[0].d5,'Rate for performance in Resources');
     	
     	why += checkRadio(document.forms[0].r6,'Select Priority of Concern in Preventive Maintenance');
     	why += checkRadio(document.forms[0].r7,'Select Priority of Concern in Preventive Maintenance');
     	why += checkRadio(document.forms[0].d6,'Rate for performance in Preventive Maintenance');
     	why += checkRadio(document.forms[0].d7,'Rate for performance in Preventive Maintenance');
     	
     	
     	why += checkRadio(document.forms[0].r8,'Select Priority of Concern in Breakdown Maintenance');
     	why += checkRadio(document.forms[0].r9,'Select Priority of Concern in Breakdown Maintenance');
     	why += checkRadio(document.forms[0].r10,'Select Priority of Concern in Breakdown Maintenance');
     	why += checkRadio(document.forms[0].d8,'Rate for performance in Breakdown Maintenance');
     	why += checkRadio(document.forms[0].d9,'Rate for performance in Breakdown Maintenance');
     	why += checkRadio(document.forms[0].d10,'Rate for performance in Breakdown Maintenance');
     	
     	why += checkRadio(document.forms[0].r11,'Select Priority of Concern in Safety Adherence');
     	why += checkRadio(document.forms[0].r12,'Select Priority of Concern in Safety Adherence');
     	why += checkRadio(document.forms[0].d11,'Rate for performance in Safety Adherence');
     	why += checkRadio(document.forms[0].d12,'Rate for performance in Safety Adherence');
     	
     	why += checkRadio(document.forms[0].r14,'Select Priority of Concern in Machine Availability');
     	why += checkRadio(document.forms[0].d14,'Rate for performance in Machine Availability');
     	
     	why += checkRadio(document.forms[0].r15,'Select Priority of Concern in Generation / Yield');
     	why += checkRadio(document.forms[0].d15,'Rate for performance in  Generation / Yield');
     	
     	
     	why += checkRadio(document.forms[0].r16,'Select Priority of Concern in Liaisoning');
     	why += checkRadio(document.forms[0].r17,'Select Priority of Concern in Liaisoning');
     	why += checkRadio(document.forms[0].d16,'Rate for performance in Liaisoning');
     	why += checkRadio(document.forms[0].d17,'Rate for performance in Liaisoning');
     	
     	why += checkRadio(document.forms[0].r18,'Select Priority of Concern in Grid Network');
     	why += checkRadio(document.forms[0].d18,'Rate for performance in Grid Network');
     	
     	why += checkRadio(document.forms[0].r19,'Select Priority of Concern in WPP generation');
     	why += checkRadio(document.forms[0].r20,'Select Priority of Concern in WPP generation');
     	why += checkRadio(document.forms[0].d19,'Rate for performance in WPP generation');
     	why += checkRadio(document.forms[0].d20,'Rate for performance in WPP generation');
     	
     	why += checkRadio(document.forms[0].r22,'Select Priority of Concern in Grievance Management');
     	why += checkRadio(document.forms[0].r23,'Select Priority of Concern in Grievance Management');
     	why += checkRadio(document.forms[0].d22,'Rate for performance in Grievance Management');
     	why += checkRadio(document.forms[0].d23,'Rate for performance in Grievance Management');
     	
     	why += checkRadio(document.forms[0].r25,'Select Priority of Concern in Interaction with Servicemen');
     	why += checkRadio(document.forms[0].r26,'Select Priority of Concern in Interaction with Servicemen');
     	why += checkRadio(document.forms[0].d25,'Rate for performance in Interaction with Servicemen');
     	why += checkRadio(document.forms[0].d26,'Rate for performance in Interaction with Servicemen');
     	
     	why += checkRadio(document.forms[0].r28,'Select Priority of Concern in Overall Impression');
     	why += checkRadio(document.forms[0].r29,'Select Priority of Concern in Overall Impression');
     	why += checkRadio(document.forms[0].d28,'Rate for performance in Overall Impression');
     	why += checkRadio(document.forms[0].d29,'Rate for performance in Overall Impression');
     	
     	
        why += checkUsername(document.forms[0].txtsug.value,'Suggestion');
     
     	
    if (why != "") {
       alert(why);
       return false;
    }
    
   
}
function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkRadio (strng,str) {
	//var SDate = strng.value; 
	      var btn=strng;
	     
	      var cnt = -1;
		for (var i=btn.length-1; i > -1; i--) {
  		 if (btn[i].checked) {cnt = i; i = -1;}
   			}
	if (cnt > -1) 
	{
	 strng= btn[cnt].value;
	}
	else
	{
	 strng= "";
	}
	
 	var error = "";
 	if (strng=='') {
        	 error = str+".\n";
 		}
 	
 		return error;
 }   
function checkUsername (strng,str) {
	//var SDate = strng.value; 
	       
 	var error = "";
 	if (strng=='') {
   	 error = "You didn't enter "+str+" .\n";
 		}
 	else if (strng.length<20)
 	{
 		error = "Please enter suggestion with minimum of 20 characters.\n";
 	}
 		return error;
 }   
 


function popuponclick()
{
window.open("custMessage.html",
  "mywindow","status=1,width=500,height=350");

}
</script>

</head>

<body ALIGN="CENTER" onload="popuponclick()">
<form align="center" name="CustomerFeedBack" id="CustomerFeedBack" action="<%=request.getContextPath()%>/CustomerFeedBack.do" method="post" onSubmit="return checkWholeForm()">
<div >

<table border="1" cellspacing="0" cellpadding="0">
  <tr>
    <td width="252"><p><img src="resources/images/Enercon_Logo.GIF" width="252" height="60" > </p></td>
    <td width="358"><br><br/><p align="center"><span class="style8">CUSTOMER&nbsp;&nbsp;FEEDBACK - FORM</span></p><br/>
    <p align="center">Assuring you the best health of your WPP at all times.</p>   
    </td>
    <td width="282"><p align="center" class="style6">Plot No 33, Daman      Patalia Road,<br/>
      Bhimpore, DAMAN-396 210<br/>
      Tel: 0260&ndash;222 0628,<br/>
      Fax: 0260&ndash;2221218 <br/>
      <em>(SER-F-03)&nbsp;&nbsp;&nbsp; Revision    no.&nbsp; : 02 /&nbsp; 01.05.2005</em></p></td>
  </tr>
</table>


<table border="1" cellspacing="0" cellpadding="0">
  <tr class=TableSummaryRow>
    <td width="252" align="center" class="style12">Customer Name</td>
    <td width="358"  align="center" class="style12">Windfarm Site 
    </td>
    <td width="282"  align="center" class="style12">WEC Type
     </td>
  </tr>
  <tr>
    <td width="252" class="style12"  align="center">
  <% 
  CustomerUtil secutils = new CustomerUtil();
	List tranList = (List) secutils.getCustList(userid);
	
	
	String cname = "";
	
	for (int i = 0; i < tranList.size(); i++) {
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		
		
		cname = (String) v.get(0);%>
	<%=cname %>	<br>
  <% }%>
  </td>
  <td width="358" class="style12"  align="center">
  <% 
     tranList.clear();
	 tranList = (List) secutils.getSiteList(userid);
	
	
	cname = "";
	
	for (int i = 0; i < tranList.size(); i++) {
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		
		
		cname = (String) v.get(0);%>
	<%=cname %>	<br>
  <% }%>
  </td>
    <td width="282" class="style12"  align="center">
  <% 
     tranList.clear();
	 tranList = (List) secutils.getWecTypeList(userid);
	
	
	cname = "";
	
	for (int i = 0; i < tranList.size(); i++) {
		Vector v = new Vector();
		v = (Vector) tranList.get(i);
		
		
		
		cname = (String) v.get(0);%>
	<%=cname %>	<br>
  <% }%>
  </td>
</table>

<table width="766" height="139" border="1">
 <tr class=TableSummaryRow>
    <td width="30" rowspan="2" align="center"><b>Sl.  No</b></td>
    <td width="207" rowspan="2" align="left"><b>Parameters</b></td>
    <td width="151" rowspan="2" align="center"><b>Traits</b></td>
    <td width="306" colspan="2" align="center"> <b>Rating </b></td>
  </tr>
 <tr class=TableSummaryRow>
    <td width="130"><h1 align="center" class="style2">Priority of Concern (Your Choice) {Rate as<br>
      <strong>L</strong>(Low),<strong>H</strong>(High)}</td>
    <td width="180"><h1 align="left" class="style2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Performance Rating<br>
    	<span class="style10">&nbsp;&nbsp;
       	<img src="<%=request.getContextPath()%>/resources/images/smileyupdate.gif" width="180">
    	</span>     
    </td>
  </tr>
  <tr>
    <td rowspan="3"  align="center"> 1. </td>
    <td rowspan="3" class="style12">Asset Management.</td>
    <td><em>Innovative Approach</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r1" value="1">
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r1" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d1" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d1" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d1" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d1" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d1" value="5"  >
      		&nbsp;&nbsp;&nbsp;
    </td>
  </tr>
  <tr>
    <td><em>Communication Network</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r2" value="1" >
L&nbsp;&nbsp;
</span>
<span class="style13">
<input type="radio" name="r2" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d2" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d2" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d2" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d2" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d2" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Committed Servicmen</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r3" value="1">
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r3" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d3" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d3" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d3" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d3" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d3" value="5"  >
    </td>
  </tr>
  <tr>
    <td rowspan="2" align="center"> 2. </td>
    <td rowspan="2" class="style12">Resources.</td>
    <td><em>Manpower Competency</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r4" value="1" >
      L&nbsp;&nbsp;</span><span class="style13">
   
      
      <input type="radio" name="r4" value="2"  >
    H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d4" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d4" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d4" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d4" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d4" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Availability</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r5" value="1" >
      L&nbsp;&nbsp;</span><span class="style13">
     
      
      <input type="radio" name="r5" value="2"  >
    H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d5" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d5" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d5" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d5" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d5" value="5"  >
    </td>
  </tr>
  <tr>
    <td rowspan="2" align="center"> 3. </td>
    <td rowspan="2" class="style12">Preventive Maintenance of the Wind Power Plant(WPP).</td>
    <td><em>Timelyness</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r6" value="1" >
      L&nbsp;&nbsp;</span><span class="style13">
   
      
      <input type="radio" name="r6" value="2"  >
    H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d6" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d6" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d6" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d6" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d6" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Quality</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r7" value="1" >
      L&nbsp;&nbsp;</span><span class="style13">
     
      
      <input type="radio" name="r7" value="2"  >
    H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d7" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d7" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d7" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d7" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d7" value="5"  >
    </td>
  </tr>  
   <tr>
    <td rowspan="3"  align="center"> 4. </td>
    <td rowspan="3" class="style12">Breakdown Maintenance of the Wind Power Plant.</td>
    <td><em>Execution</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r8" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r8" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d8" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d8" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d8" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d8" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d8" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Speed</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r9" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r9" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d9" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d9" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d9" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d9" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d9" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Effectiveness  of repairs</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r10" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r10" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d10" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d10" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d10" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d10" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d10" value="5"  >
    </td>
  </tr>
  
  <tr>
    <td rowspan="2"  align="center"> 5. </td>
    <td rowspan="2" class="style12">Safety Adherence</td>
    <td><em>Wind Power Plant</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r11" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r11" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d11" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d11" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d11" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d11" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d11" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Employees</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r12" value="1" >
L&nbsp;&nbsp;</span><span class="style13">

<input type="radio" name="r12" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d12" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d12" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d12" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d12" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d12" value="5"  >
    </td>
  </tr>
  
  <tr>
    <td  align="center">6.</td>
    <td class="style12">Machine Availability</td>
    <td><em>Contractual</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r14" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r14" value="2"  >
H&nbsp;&nbsp;</span></td>
   <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d14" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d14" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d14" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d14" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d14" value="5"  >
    </td>
  </tr>
    <tr>
    <td  align="center">7.</td>
    <td class="style12">Generation / Yield</td>
    <td><em>Generation / Yield</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r15" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r15" value="2"  >
H&nbsp;&nbsp;</span></td>
   <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d15" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d15" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d15" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d15" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d15" value="5"  >
    </td>
  </tr>

<tr>
    <td rowspan="2"> <div align="center">8. </div></td>
    <td rowspan="2" class="style12">Liaisoning -  EB and nodal agencies related interface.</td>
    <td><em>Timely</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r16" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r16" value="2"  >
H&nbsp;&nbsp;</span></td>
   <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d16" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d16" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d16" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d16" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d16" value="5"  >
    </td>
  </tr>
  <tr>
    <td><em>Effectiveness</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r17" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r17" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d17" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d17" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d17" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d17" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d17" value="5"  >
    </td>
  </tr>
 
 <tr>
    <td rowspan="1"> <div align="center">9. </div></td>
    <td rowspan="1" class="style12">Grid Network</td>
    <td><em>Grid Network</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r18" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r18" value="2"  >
H&nbsp;&nbsp;</span></td>
   <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d18" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d18" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d18" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d18" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d18" value="5"  >
    </td>
  </tr>

 <tr>
    <td rowspan="2"> <div align="center">10. </div></td>
    <td rowspan="2" class="style12">Reporting on the WPP generation & performance.</td>
    <td><em>Regularity / Timely</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r19" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r19" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d19" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d19" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d19" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d19" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d19" value="5"  >
    </td>
  </tr>
<tr>
    <td><em>Accuracy</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r20" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r20" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d20" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d20" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d20" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d20" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d20" value="5"  >
    </td>
  </tr>
  
  

<tr>
    <td rowspan="2"> <div align="center">11. </div></td>
    <td rowspan="2" class="style12">Grievance Management.</td>
    <td><em>Timely</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r22" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r22" value="2"  >
H&nbsp;&nbsp;</span></td>
  <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d22" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d22" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d22" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d22" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d22" value="5"  >
    </td>
  </tr>
<tr>
    <td><em>Appropriate</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r23" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r23" value="2"  >
H&nbsp;&nbsp;</span></td>
   <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d23" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d23" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d23" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d23" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d23" value="5"  >
    </td>
  </tr>
  
  
<tr>
    <td rowspan="2"> <div align="center">12. </div></td>
    <td rowspan="2" class="style12">Your Interaction with Servicemen about your WPP.</td>
    <td><em>Site Team</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r25" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r25" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d25" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d25" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d25" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d25" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d25" value="5"  >
    </td>
  </tr>
<tr>
    <td><em>Central Team</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r26" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r26" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d26" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d26" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d26" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d26" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d26" value="5">
    </td>
  </tr>
  
 

<tr>
    <td rowspan="2"> <div align="center">13. </div></td>
    <td rowspan="2" class="style12">Your Overall Impression about Enercon (India) Service Division</td>
    <td><em>Machine health</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r28" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r28" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
      		&nbsp;&nbsp;<input type="radio" name="d28" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d28" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d28" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d28" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d28" value="5"  >
    </td>
  </tr>

  
  <tr>
    <td><em>Quality consciousness</em></td>
    <td><span class="style10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input type="radio" name="r29" value="1" >
L&nbsp;&nbsp;</span><span class="style13">


<input type="radio" name="r29" value="2"  >
H&nbsp;&nbsp;</span></td>
    <td>
    	<span class="style10">
       		&nbsp;&nbsp;<input type="radio" name="d29" value="1">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d29" value="2">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d29" value="3">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d29" value="4">
      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="d29" value="5"  >
    </td>
  </tr>
  
  <tr>
    <td class="style12">Suggestion</td>
    <td colspan="4">
      <div align="center">
        <input type="text" name="txtsug" id="txtsug"  style="font-family: Verdana; font-weight: bold; font-size: 14px; background-color: #FFFF66;"  maxlength="1000" size="100" >
    </div>   
    </td>
   </tr>  
     
    <tr>
    
   
       <td class="btn" colspan="5" width="100%" colspan="5" align="center">
			<input type="hidden" name="logIdtxt" value="" />	
			<input type="hidden" name="Admin_Input_Type" value="CustomerFeedBack" />	
			<input name="Submit" type="submit" class="btnform" value="Submit Your Feedback"/>
		</td> </tr>
</table>
</div>
</form>
</body>
</html>
