<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.enercon.global.utils.DynaBean"%>

<%@ page import="java.text.*"%>
<html>
<head>
<%
if (session.getAttribute("loginID") == null)
{
     response.sendRedirect(request.getContextPath());
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Enercon.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>



<script type="text/javascript">
<!--
/* http://www.alistapart.com/articles/zebratables/ */
function removeClassName (elem, className) {
	elem.className = elem.className.replace(className, "").trim();
}

function addCSSClass (elem, className) {
	removeClassName (elem, className);
	elem.className = (elem.className + " " + className).trim();
}

String.prototype.trim = function() {
	return this.replace( /^\s+|\s+$/, "" );
}

function stripedTable() {
	if (document.getElementById && document.getElementsByTagName) {  
		var allTables = document.getElementsByTagName('table');
		if (!allTables) { return; }

		for (var i = 0; i < allTables.length; i++) {
			if (allTables[i].className.match(/[\w\s ]*scrollTable[\w\s ]*/)) {
				var trs = allTables[i].getElementsByTagName("tr");
				for (var j = 0; j < trs.length; j++) {
					removeClassName(trs[j], 'alternateRow');
					addCSSClass(trs[j], 'normalRow');
				}
				for (var k = 0; k < trs.length; k += 2) {
					removeClassName(trs[k], 'normalRow');
					addCSSClass(trs[k], 'alternateRow');
				}
			}
		}
	}
}

window.onload = function() { stripedTable(); }
-->
</script>
<style type="text/css">
<!--
/* Terence Ordona, portal[AT]imaputz[DOT]com         */
/* http://creativecommons.org/licenses/by-sa/2.0/    */

/* begin some basic styling here                     */
body {
	background: #FFF;
	color: #000;
	font: normal normal 12px Verdana, Geneva, Arial, Helvetica, sans-serif;
	margin: 10px;
	padding: 0
}

table, td, a {
	color: #000;
	font: normal normal 12px Verdana, Geneva, Arial, Helvetica, sans-serif
}

h1 {
	font: normal normal 18px Verdana, Geneva, Arial, Helvetica, sans-serif;
	margin: 0 0 5px 0
}

h2 {
	font: normal normal 16px Verdana, Geneva, Arial, Helvetica, sans-serif;
	margin: 0 0 5px 0
}

h3 {
	font: normal normal 13px Verdana, Geneva, Arial, Helvetica, sans-serif;
	color: #008000;
	margin: 0 0 15px 0
}
/* end basic styling                                 */

/* define height and width of scrollable area. Add 16px to width for scrollbar          */
div.tableContainer {
	clear: both;
	border: 1px solid #963;
	height: 795px;
	overflow: auto;
	width: 1330px
}

/* Reset overflow value to hidden for all non-IE browsers. */
html>body div.tableContainer {
	overflow: hidden;
	width: 1216px
}

/* define width of table. IE browsers only                 */
div.tableContainer table {
	float: left;
	width: 1216px
}

/* define width of table. Add 16px to width for scrollbar.           */
/* All other non-IE browsers.                                        */
html>body div.tableContainer table {
	width: 756px
}

/* set table header to a fixed position. WinIE 6.x only                                       */
/* In WinIE 6.x, any element with a position property set to relative and is a child of       */
/* an element that has an overflow property set, the relative value translates into fixed.    */
/* Ex: parent element DIV with a class of tableContainer has an overflow property set to auto */
thead.fixedHeader tr {
	position: relative
}

/* set THEAD element to have block level attributes. All other non-IE browsers            */
/* this enables overflow to work on TBODY element. All other non-IE, non-Mozilla browsers */
html>body thead.fixedHeader tr {
	display: block
}

/* make the TH elements pretty */
thead.fixedHeader th {
	background: #006633;
	border-left: 1px solid #EB8;
	border-right: 1px solid #B74;
	border-top: 1px solid #EB8;
	font-weight: normal;
	padding: 4px 3px;
	text-align: left
}

/* make the A elements pretty. makes for nice clickable headers                */
thead.fixedHeader a, thead.fixedHeader a:link, thead.fixedHeader a:visited {
	color: #FFF;
	display: block;
	text-decoration: none;
	width: 100%
}

/* make the A elements pretty. makes for nice clickable headers                */
/* WARNING: swapping the background on hover may cause problems in WinIE 6.x   */
thead.fixedHeader a:hover {
	color: #FFF;
	display: block;
	text-decoration: underline;
	width: 100%
}

/* define the table content to be scrollable                                              */
/* set TBODY element to have block level attributes. All other non-IE browsers            */
/* this enables overflow to work on TBODY element. All other non-IE, non-Mozilla browsers */
/* induced side effect is that child TDs no longer accept width: auto                     */
html>body tbody.scrollContent {
	display: block;
	height: 1262px;
	overflow: auto;
	width: 150%
}

/* make TD elements pretty. Provide alternating classes for striping the table */
/* http://www.alistapart.com/articles/zebratables/                             */
tbody.scrollContent td, tbody.scrollContent tr.normalRow td {
	background: #FFF;
	border-bottom: none;
	border-left: none;
	border-right: 1px solid #CCC;
	border-top: 1px solid #DDD;
	padding: 2px 3px 3px 4px
}

tbody.scrollContent tr.alternateRow td {
	background: #EEE;
	border-bottom: none;
	border-left: none;
	border-right: 1px solid #CCC;
	border-top: 1px solid #DDD;
	padding: 2px 3px 3px 4px
}

/* define width of TH elements: 1st, 2nd, and 3rd respectively.          */
/* Add 16px to last TH for scrollbar padding. All other non-IE browsers. */
/* http://www.w3.org/TR/REC-CSS2/selector.html#adjacent-selectors        */
html>body thead.fixedHeader th {
	width: 600px
}

html>body thead.fixedHeader th + th {
	width: 720px
}

html>body thead.fixedHeader th + th + th {
	width: 916px
}

/* define width of TD elements: 1st, 2nd, and 3rd respectively.          */
/* All other non-IE browsers.                                            */
/* http://www.w3.org/TR/REC-CSS2/selector.html#adjacent-selectors        */
html>body tbody.scrollContent td {
	width: 600px
}

html>body tbody.scrollContent td + td {
	width: 720px
}

html>body tbody.scrollContent td + td + td {
	width: 900px
}
-->
</style>

<%
String siteid = ""; 
String date = "";
String type = "";
String strt = "";
String endd = "";
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	siteid = dynaBean.getProperty("SiteIdtxt").toString();
	date = dynaBean.getProperty("Datetxt").toString();	
	type = dynaBean.getProperty("Typetxt").toString();
	strt = dynaBean.getProperty("Starttxt").toString();
	endd = dynaBean.getProperty("Endtxt").toString();		
	session.removeAttribute("dynabean");
}else
{
	siteid = request.getParameter("siteid").toString(); 
	date = request.getParameter("date").toString();
	type = request.getParameter("type").toString();
	strt = request.getParameter("strt").toString();
	endd = request.getParameter("endd").toString();
}
// SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
// int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
// java.util.Date adate= dateFormat.parse(date);

// String prevdate = dateFormat.format(adate.getTime() - MILLIS_IN_DAY);
// String crdate = dateFormat.format(adate.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
// String nextdate = dateFormat.format(adate.getTime() + MILLIS_IN_DAY);
%>
<title>WEC Reading </title>
<script type="text/javascript">
var totpara=new Array();
var mpunit=new Array();
var mpshow=new Array();
var mactype=new Array();
var mCum=new Array();
var done = 0;
function findData() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = "WEC," + document.getElementById("Typetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showData,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findMPMaster&AppId="+ApplicationId);
}
function showData(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("mpmaster")[0];
	var items = cart.getElementsByTagName("mpcode");	
	var divdetails = document.getElementById("mpdetails");
	divdetails.innerHTML = "";
	var str = "<div id='tableContainer' class='tableContainer'>"
	str +="<table border='0'  cellpadding='1' cellspacing='1' width='100%'>"
	str +="<thead class='fixedHeader'>"
	str +="<tr class='detailsheading' align='center' height='20'><th  width='2%'>S.N.</th>"
	str+="<th  class='detailsheading' width='10%'>WEC Meter</th><th class='detailsheading' width='5%'>Carry Forward</th>";
	totpara=new Array(items.length);
	mpunit=new Array(items.length);
	var cnt = 0;
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("mpid")[0].firstChild;
     	if (nname != null){	     			
     		if (item.getElementsByTagName("status")[0].firstChild.nodeValue == 1)
     		{     		
	   			str+="<th class='detailsheading' width='6%'>" + item.getElementsByTagName("mpdesc")[0].firstChild.nodeValue + "</th>";	 
	   			totpara[cnt] = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;
	   			mpunit[cnt] = item.getElementsByTagName("mpunit")[0].firstChild.nodeValue;	   			
	   			mpshow[cnt] = item.getElementsByTagName("mpshow")[0].firstChild.nodeValue;
	   			mactype[cnt] = item.getElementsByTagName("mptype")[0].firstChild.nodeValue;
	   			mCum[cnt] = item.getElementsByTagName("cum")[0].firstChild.nodeValue;
	   			cnt++;
	   		}
   	 	}
	}		
	totpara.length = cnt;
	mpunit.length = cnt;
	str+="</tr>";
	str+="</thead>";
	
	findWECData(str);
}
function findWECData(str) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showWECData,str,"");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findPagedWECMaster&AppId="+ApplicationId);
}
function showWECData(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");	
	var divdetails = document.getElementById("mpdetails");
	divdetails.innerHTML = "";
	var str = a;
	var pdate = "";
	var numb = 1;
	str+="<tbody class='scrollContent'>"
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	
     	if (nname != null){
     		if (item.getElementsByTagName("wecstatus")[0].firstChild.nodeValue == 1)
     		{
	     		if (numb % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'>"
			      }	 
			    str+="<td ALIGN='center'>"+item.getElementsByTagName("rowno")[0].firstChild.nodeValue+"</td>"
			    str+="<td align='left'>" + item.getElementsByTagName("desc")[0].firstChild.nodeValue + "(" + item.getElementsByTagName("locno")[0].firstChild.nodeValue + ")"
			    str+="/" + item.getElementsByTagName("wecebid")[0].firstChild.nodeValue +"/" + item.getElementsByTagName("feeder")[0].firstChild.nodeValue +"</td>"
			    str+="<td align=center>"
			    var wecidd = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
			    str+="<input type='hidden' name='"+wecidd+"' id='"+wecidd+"' "
				str+=" value='"+item.getElementsByTagName("mfact")[0].firstChild.nodeValue+"' />"
				str+="<input type='checkbox' id='carryforward"+I+"' name='carryforward"+I+"' "
	     		str+="onclick=forwrd('" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"',"
	     		str+="'carryforward"+I+"',"+totpara.length+",'"+wecidd+"') style='text-align:right' class='ctrl' /></td>"		    
				
				for (var cnt = 0; cnt < totpara.length; cnt++ )
				{			
					var idname = totpara[cnt]+""+item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
					str+="<td align=right><input type='hidden' name='"+totpara[cnt]+"' id='"+totpara[cnt]+"' value='"+mpunit[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"T' id='"+totpara[cnt]+"T' value='"+mactype[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"C' id='"+totpara[cnt]+"C' value='"+mCum[cnt]+"' />"																
					str+="<input type='text' name='"+idname+"P'"
					str+=" id='"+idname+"P' class='ebcreading' maxlength='25' size='15' style='text-align:right' disabled /><br>"
//					alert(idname.length);
		     		str+="<input type='text' name='"+idname+"' id='"+idname+"' class='ebpreading' maxlength='25' size='15' "
		     		str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+wecidd+"') "
		     		str+=" style='text-align:right' /><br><input type='text' name='"+idname+"D'"
					str+=" id='"+idname+"D' class='ebcreading' maxlength='25' size='15' style='text-align:right' "
					if (document.getElementById("Typetxt").value == "I")
					{
						if (mCum[cnt] == "2")
						{
							str+="readonly='readonly' /></td>"
						}
						else
						{
							str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+wecidd+"') /></td>"
						}
					}
					else
					{
						str+="readonly='readonly' /></td>"
					}
				}
				str+="</tr>"
				if (numb % 2 == 0)
		      	{
		        	str+="<tr align='center' height='20' class='detailsbody'>"
		      	}
		      	else
		      	{
		        	str+="<tr align='center' height='20' class='detailsbody1'>"
		      	}	
			    var idrname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"R";
			    var idrrname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"RR"; //for remarks combo			    
			    var idlname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"LL";
				str+="<td></td><td>LineLoss:"
				str+="<input type='text' name='"+idlname+"' id='"+idlname+"' class='ebcreading' maxlength='10' size='8' "
	     		str+=" disabled "
	     		str+=" style='text-align:left' /></td><td>Remarks</td><td colspan='"+(totpara.length)+"' align='left'>"
	     		str+="<select name='"+idrrname+"' id='"+idrrname+"' class='ebcreading' onChange=Remarks('"+idrrname+"','"+idrname+"') >"
           		//str+="	<option value=''>-- select --</option>"
	            str+="	<option value='OTHERS'>OTHERS</option>"
	            str+="</select><br>"
				str+="<input type='text' name='"+idrname+"' id='"+idrname+"' class='ebcreading' maxlength='500' size='"+((totpara.length) * 17)+"' "
	     		str+=" onBlur=updateremarks('"+idrname+"')  " //disabled
	     		str+=" style='text-align:left' /></td></tr>"
	     		str+="<tr height='4'><th class='detailsheading' colspan='"+(3+(totpara.length))+"'></th></tr>"
				numb++;
				//findRemarks(idrrname,item.getElementsByTagName("wectype")[0].firstChild.nodeValue);
	     	}
	     	pdate= item.getElementsByTagName("pdate")[0].firstChild.nodeValue;
   	 	}   	 	
	}
	str+="</tbody></table></div>"
	divdetails.innerHTML = str;
	findWECReadings();
	if (pdate != "")
	{
		findWECPrevReadings(pdate);
	}
	findLL();
}
function findRemarks(rname,wectype)
{
	 var req = newXMLHttpRequest();
     var ApplicationId = wectype;     
	 req.onreadystatechange = getReadyStateHandler(req, showRemarks,rname,"");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findRemarksMaster&AppId="+ApplicationId);
}
function showRemarks(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("remarksmaster")[0];
	var items = cart.getElementsByTagName("remarkscode");
	document.getElementById(a).options.length = 0;
	document.getElementById(a).options[0] = new Option("--Make a Selection--","");
	var I = 0 ;
	for (I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("remarksid")[0].firstChild;
     	if (nname != null)
     	{
   	 		document.getElementById(a).options[I + 1] = new Option(item.getElementsByTagName("error")[0].firstChild.nodeValue,item.getElementsByTagName("remarks")[0].firstChild.nodeValue);   	 		
   	 	}
	}
	document.getElementById(a).options[I + 1] = new Option("--OTHERS--","OTHERS");
}
function Remarks(remObj,remDesc) 
{
	 var ApplicationId = document.getElementById(remObj).value;
	 if (ApplicationId == "OTHERS")
	 {
	 	document.getElementById(remDesc).disabled = false;
	 	document.getElementById(remDesc).focus();
	 }
	 else if (ApplicationId == "")
	 {
	 	document.getElementById(remDesc).disabled = true;
	 	document.getElementById(remDesc).value = "";
	 }
	 else
	 {
	 	if (document.getElementById(remDesc).value.length > 0)
	 	{
	 		document.getElementById(remDesc).value = document.getElementById(remDesc).value + " ; " + document.getElementById(remObj).value;
	 	}
	 	else
	 	{
	 		document.getElementById(remDesc).value = document.getElementById(remObj).value;
	 	}
	 	document.getElementById(remDesc).disabled = false;
	 	updateremarks(remDesc);
	 }
}
function findLL() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showLL,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_findWECLL&AppId="+ApplicationId);
}
function showLL(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("lineloss")[0];
	var items = cart.getElementsByTagName("ll");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("lvalue")[0].firstChild;
     	if (nname != null){
     		var txtname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue +"LL";
     		document.getElementById(txtname).value= item.getElementsByTagName("lvalue")[0].firstChild.nodeValue;
   	 	}   	 	
	}	
}
function findWECReadings() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showWECReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_findWECData&AppId="+ApplicationId);
}
function showWECReadings(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	if ((nname != null) && (mpidnn != null)){
     		nname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;    
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{
   	     		var txtname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
	     		document.getElementById(txtname).value= item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;
	     		document.getElementById(txtname+"D").value= item.getElementsByTagName("dvalue")[0].firstChild.nodeValue;
	     		var txtrname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue+"R";
	     		if (item.getElementsByTagName("remarks")[0].firstChild.nodeValue != ".")
	     		{
	     			document.getElementById(txtrname).value= item.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	     		}
	     		else
	     		{
	     			document.getElementById(txtrname).value = "";
	     		}
	     	}
   	 	}   	 	
	}	
	done = 1;	
}
function findWECPrevReadings(pdate) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + pdate + ",.," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showWECPrevReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_findWECData&AppId="+ApplicationId);
}
function showWECPrevReadings(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
   		var txtpname = "";
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	if ((nname != null) && (mpidnn != null)){
     		nname = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;    
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{
	     		txtpname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue + "P";
    	 		document.getElementById(txtpname).value=item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;     		
    	 	}
     		//calc(txtpname,item.getElementsByTagName("mpid")[0].firstChild.nodeValue);
   	 	}   	
	}	
}
function calc(txtpname,cumulative,mFact)
{
	var txtname = txtpname.substr(0,20);     		
	var txtdname = txtpname.substr(0,20) + "D";
	//for (var i = 0; i <= 3; i++)
	//{
		if (document.getElementById(cumulative).value == 1)
		{
//			alert(document.getElementById(cumulative).value);
			if (eval(document.getElementById(txtpname).value) == 0 || document.getElementById(txtpname).value == "")
			{
				document.getElementById(txtdname).value=eval(document.getElementById(txtname).value);
			}
			else if(eval(document.getElementById(txtpname).value) > 0 && (document.getElementById(txtname).value != "Cannot be less" && document.getElementById(txtname).value != "Yesterdays Data Not Found" && document.getElementById(txtname).value != "More then tomorow"  && document.getElementById(txtname).value != "No EB Data" && document.getElementById(txtname).value !=  "No Feder Data" && document.getElementById(txtname).value != "more then 24" && document.getElementById(txtname).value != "Should be More or equal"))
			{
				document.getElementById(txtdname).value=((eval(document.getElementById(txtname).value) - eval(document.getElementById(txtpname).value)) * document.getElementById(mFact).value).toFixed(2);
			}
			else
			{	
				document.getElementById(txtdname).value = "0";
			}
			if (document.getElementById(txtdname).value == "NaN" || document.getElementById(txtdname).value == "undefined")
		 	{
		  		document.getElementById(txtdname).value = "0";
		  	}
		  	if(eval(document.getElementById(txtdname).value) < 0 && eval(document.getElementById(txtname).value) > 0)
		  	{
		  		alert("Cannot be less");
		  		document.getElementById(txtdname).value = "0";
		  		document.getElementById(txtname).focus();
		  		document.getElementById(txtname).select();
		  	}
		}
	//}
}
function validateNumber1(numObj,mpObj,typeObj,mFact) {
	if (!isDecimal(document.getElementById(numObj).value)) {
		alert("Numbers Only");
//	   alert ('\n\nIt should be a proper number field.\n\n');
	   document.getElementById(numObj).focus();
	   document.getElementById(numObj).select();
//		setTimeout(function(){if(document.getElementById(numObj.name))document.getElementById(numObj.name).focus();document.getElementById(numObj.name).select();},100);
	}
	else
	{
		if (document.getElementById("Typetxt").value != "I")
		{
			calc(numObj + "P",mpObj+"C",mFact);
		}		
		if (!isDecimal(document.getElementById(numObj + "D").value)) {
			alert("Numbers Only");
		   	document.getElementById(numObj + "D").focus();
		   	document.getElementById(numObj + "D").select();
		}
		else
		{
			//if (eval(document.getElementById(numObj + "D").value) > eval(document.getElementById(numObj).value))
			//{
			//	document.getElementById(numObj + "D").value = "More then Cumulative";
			//}
			//else 
			/*if (document.getElementById(numObj).value != "Cannot be less")
			{
		   		var req = newXMLHttpRequest();	 
			    var ApplicationId =  numObj + "," + document.getElementById(numObj).value + "," + document.getElementById("Datetxt").value + "," + document.getElementById(numObj + "D").value + "," + document.getElementById("Typetxt").value;
				req.onreadystatechange = getReadyStateHandler(req, UpdateWEC,numObj,"");
				req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				req.send("Admin_Input_Type=SU_UpdateWEC&AppId="+ApplicationId);
			}*/
		}
	}
}
function UpdateWEC(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecentry")[0];
	var items = cart.getElementsByTagName("weccode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	if (nname != null){
     		if(item.getElementsByTagName("wecid")[0].firstChild.nodeValue != "")
     		{
     			//alert(item.getElementsByTagName("ebid")[0].firstChild.nodeValue);
     			document.getElementById(a).value = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
	  			if((document.getElementById(a).value == "Cannot be less" && document.getElementById(a).value == "Yesterdays Data Not Found" && document.getElementById(a).value == "More then tomorow"  && document.getElementById(a).value == "No EB Data" && document.getElementById(a).value ==  "No Feder Data" && document.getElementById(a).value == "more then 24"))
	  			{
	  				document.getElementById(a).focus();
	   				document.getElementById(a).select();
	  				document.getElementById(a + "D").value = "0";
	  			}
     		}
   	 	}   	 	
   	 	findLL();
   	 	UpdateEBByWec(a);
	}
}
function UpdateEBByWec(a) 
{
	 var req = newXMLHttpRequest();
	 var ApplicationId =  a + "," + document.getElementById("Datetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showUpdateEBByWec,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_showUpdateEBByWec&AppId="+ApplicationId);
}
function showUpdateEBByWec(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("updateeb")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("msg")[0].firstChild;
     	if (nname != null){
     		var msg = item.getElementsByTagName("msg")[0].firstChild.nodeValue;
     		if(msg.length > 0 && msg != ".")
     		{
     			alert(msg);
     		}
   	 	}   	 	
	}	
}
function updateremarks(numObj)
{
	/*var req = newXMLHttpRequest();	 
   	var ApplicationId =  numObj + "," + document.getElementById(numObj).value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value;
	req.onreadystatechange = getReadyStateHandler(req, UpdateWECRemarks,numObj,"");
	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=SU_UpdateWECRemarks&AppId="+ApplicationId);*/
}
function UpdateWECRemarks(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecentry")[0];
	var items = cart.getElementsByTagName("weccode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	if (nname != null){
	   		if(item.getElementsByTagName("wecid")[0].firstChild.nodeValue != "")
	   		{
     			document.getElementById(a).value = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
     		}
   	 	}   	 	
	}
}
function forwrd(wecid,chkbox,cnt,mFact) 
{
//	alert(document.getElementById(mFact).value);
	if (document.getElementById(chkbox).checked == true)
	{	
		var req = newXMLHttpRequest();	 
	    var ApplicationId =  wecid + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value;
		req.onreadystatechange = getReadyStateHandler(req, WECCarryForward,cnt,wecid+""+document.getElementById(mFact).value);
		req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send("Admin_Input_Type=SU_WECCarryForward&AppId="+ApplicationId);
	}
}
function WECCarryForward(dataXml,a,b)
{
	findWECReadings();
	//var wecid = b.substr(0,10);    
	//var mfact = b.substr(10,11);   
	//for (var cnt = 0; cnt < a; cnt++)
	//{
	//	var idname = totpara[cnt]+""+wecid+"P";
	//	var mpcum = totpara[cnt]+"C";
	//	calc(idname,mpcum,mfact);
	//}
}
</script>

</head>
<body onload="findData()" >
	<form action="<%=request.getContextPath()%>/WECReading.do" method="post" >
	
		<input type="hidden" name="SiteIdtxt" id="SiteIdtxt" value="<%=siteid %>" />
		<input type="hidden" name="Datetxt" id="Datetxt" value="<%=date %>" />
		<input type="hidden" name="Typetxt" id="Typetxt" value="<%=type %>" />	
		<input type="hidden" name="Starttxt" id="Starttxt" value="<%=strt %>" />
		<input type="hidden" name="Endtxt" id="Endtxt" value="<%=endd %>" />
		
		<div id="mpdetails">
	
		</div>
		<div class="blue_text">Page No: <%=(Integer.parseInt(endd)/5)%>&nbsp;&nbsp;&nbsp;<input name="Submit" class="loginbox" value="Submit" type="submit" /></div>
		<input type="hidden" name="Admin_Input_Type" value="WECEntry" />	
		<br>
		<%String str=(String)session.getAttribute("msg");%>
		<%if(str != null){%>
		<%=str%>
		<%}%>
		<%session.setAttribute("msg","");%>
		
		
	</form>
	
	
	
</body>
</html>