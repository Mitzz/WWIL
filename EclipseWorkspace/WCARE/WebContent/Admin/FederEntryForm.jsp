<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

<%@page import="com.enercon.global.utils.DynaBean"%>
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
// String ardate = dateFormat.format(date.getTime());
// String nextdate = dateFormat.format(adate.getTime() + MILLIS_IN_DAY);
%>
<title>Feeder Reading</title>
<script type="text/javascript">
var totpara=new Array();
var mpunit=new Array();
var mpshow=new Array();
var mactype=new Array();
var mCum=new Array();
var fds=new Array();
var done = 0;
function findData() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId ="EB," + document.getElementById("Typetxt").value;
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
	var str = "<table border='0' cellpadding='1' cellspacing='1' width='100%'>"
	str +="<tr align='center' height='20'><th class='detailsheading' width='2%'>S.N.</th>"
	str+="<th class='detailsheading' width='10%'>Feeder Meter</th><th class='detailsheading' width='5%'>Carry Forward</th>";
	totpara=new Array(items.length);
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
	str+="</tr>";
	findFDData(str);
}
function findFDData(str) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showFDData,str,"");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findPagedFDMaster&AppId="+ApplicationId);
}
function showFDData(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("fdmaster")[0];
	var items = cart.getElementsByTagName("fdcode");	
	var divdetails = document.getElementById("mpdetails");
	divdetails.innerHTML = "";
	var str = a;
	var pdate = "";
	var numb = 1;
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
     	if (nname != null){
     		if (item.getElementsByTagName("fdstatus")[0].firstChild.nodeValue == 1)
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
			    str+="<td align='left'>" + item.getElementsByTagName("fdshort")[0].firstChild.nodeValue + "<br>("+item.getElementsByTagName("fdname")[0].firstChild.nodeValue+")<br>Multi. Fact:"+item.getElementsByTagName("fdmfact")[0].firstChild.nodeValue+"</td>"
	     		str+="<td align=center>"
	     		var fdidd = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
	     		fds[I] = fdidd;
			    str+="<input type='hidden' name='"+fdidd+"' id='"+fdidd+"' "
				str+=" value='"+item.getElementsByTagName("fdmfact")[0].firstChild.nodeValue+"' />"
				str+="<input type='checkbox' id='carryforward"+I+"' name='carryforward"+I+"' "
	     		str+="onclick=forwrd('" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue+"',"
	     		str+="'carryforward"+I+"',"+totpara.length+",'"+fdidd+"') style='text-align:right' class='ctrl' /></td>"		    
				for (var cnt = 0; cnt < totpara.length; cnt++ )
				{			
					var idname = totpara[cnt]+""+item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
					str+="<td align=right><input type='hidden' name='"+totpara[cnt]+"' id='"+totpara[cnt]+"' value='"+mpunit[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"T' id='"+totpara[cnt]+"T' value='"+mactype[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"C' id='"+totpara[cnt]+"C' value='"+mCum[cnt]+"' />"
					str+="<input type='text' name='"+idname+"P'"
					str+=" id='"+idname+"P' class='ebcreading' maxlength='25' size='15' style='text-align:right' disabled /><br>"
		     		str+="<input type='text' name='"+idname+"' id='"+idname+"' class='ebpreading' maxlength='25' size='15' "
		     		str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+fdidd+"') "
		     		str+=" style='text-align:right' /><br><input type='text' name='"+idname+"D'"
					str+=" id='"+idname+"D' class='ebcreading' maxlength='25' size='15' style='text-align:right' "
					if (document.getElementById("Typetxt").value == "I")
					{
						if (mCum[cnt] == "2")
						{
							str+=" readOnly='true' onBlur=gotoSave('"+idname+"') /></td>"
						}
						else
						{
							str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+fdidd+"') /></td>"
						}
					}
					else
					{
						str+=" readOnly='true' onBlur=gotoSave('"+idname+"') /></td>"
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
			    var idrname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue+"R";
			    var idlname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue+"LL";
				str+="<td></td><td>LineLoss:"
				str+="<input type='text' name='"+idlname+"' id='"+idlname+"' class='ebcreading' maxlength='10' size='8' "
	     		str+=" disabled "
	     		str+=" style='text-align:left' /></td><td>Remarks</td><td colspan='"+(totpara.length)+"' align='left'>"
				str+="<input type='text' name='"+idrname+"' id='"+idrname+"' class='ebcreading' maxlength='500' size='"+((totpara.length) * 15)+"' "
	     		str+=" onBlur=updateremarks('"+idrname+"') "
	     		str+=" style='text-align:left' /></td></tr>"
	     		str+="<tr height='4'><th class='detailsheading' colspan='"+(3+(totpara.length))+"'></th></tr>"
				numb++;
	     	}
	     	pdate= item.getElementsByTagName("pdate")[0].firstChild.nodeValue
   	 	}   	 	
	}
	divdetails.innerHTML = str;
	findFDReadings();
	if (pdate != "")
	{
		findFDPrevReadings(pdate);
	}
	findLL();
	//enabledisable();
}
function gotoSave(id) 
{
    var blnSave=false;
    blnSave = validateForm('Difference',document.getElementById(id + "D").value,'','M'
                           );
     if ( blnSave == false ) {
        document.getElementById(id + "D").value = "";
     }
}
function enabledisable()
{
	for (var cnt = 0; cnt < totpara.length; cnt++ )
	{			
		for (var cnt1 = 0; cnt1 < fds.length; cnt1++ )
		{
			var idname = totpara[cnt]+""+fds[cnt1];
			var pvalue = document.getElementById(idname + "P").value;
			if (document.getElementById("Typetxt").value != "D" && pvalue=="")
			{
				document.getElementById(idname + "D").readOnly = false;
			}
		}
	}
}
function findLL() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value+"," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showLL,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_findFDLL&AppId="+ApplicationId);
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
     		var txtname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue +"LL";
     		document.getElementById(txtname).value= item.getElementsByTagName("lvalue")[0].firstChild.nodeValue;
   	 	}   	 	
	}	
}
function findFDReadings() 
{
	 var req = newXMLHttpRequest();
	/// alert(document.getElementById("Typetxt").value);
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value+ ",D";
	 req.onreadystatechange = getReadyStateHandler(req, showFDReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	 {
	     req.send("Admin_Input_Type=SU_findFDData&AppId="+ApplicationId);
	 }
	 else
	 {
		req.send("Admin_Input_Type=SU_findFDDataMax&AppId="+ApplicationId);
	 }
}
function showFDReadings(dataXml,a,b)
{     
	var cart = dataXml.getElementsByTagName("fdmaster")[0];
	var items = cart.getElementsByTagName("fdcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	 	
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	
     	if ((nname != null) && (mpidnn != null)){
     	
     		nname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
     		  
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;
     		
     		  
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{   
	     		var txtname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
	     		document.getElementById(txtname).value= item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;
	     		document.getElementById(txtname+"D").value= item.getElementsByTagName("dvalue")[0].firstChild.nodeValue;
	     		var txtrname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue+"R";
	     		document.getElementById(txtrname).value= item.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	     	}
   	 	}   	 	
	}	
	done = 1;	
}
function findFDPrevReadings(pdate) 
{
	 var req = newXMLHttpRequest();
	 var ApplicationId = "";
	// if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	// {
	//     ApplicationId = document.getElementById("SiteIdtxt").value + "," + pdate + ",.," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value;
	// }
	// else
	// {
	 	 ApplicationId = document.getElementById("SiteIdtxt").value + "," + pdate + "," + document.getElementById("Typetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value+ ",P";
	// }
	 req.onreadystatechange = getReadyStateHandler(req, showFDPrevReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	 {
	     req.send("Admin_Input_Type=SU_findFDData&AppId="+ApplicationId);
	 }
	 else
	 {
		req.send("Admin_Input_Type=SU_findFDDataMax&AppId="+ApplicationId);
	 }
	 
}
function showFDPrevReadings(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("fdmaster")[0];
	var items = cart.getElementsByTagName("fdcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
   		var txtpname = "";
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	if ((nname != null) && (mpidnn != null)){
     		nname = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;    
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{ 
	     		txtpname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue + "P";
	     		document.getElementById(txtpname).value=item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;  
	     		calc(txtpname,item.getElementsByTagName("mpid")[0].firstChild.nodeValue);
	     	}
   	 	}   	
	}
	enabledisable();
}
function calc(txtpname,cumulative,mFact)
{
	var txtname = txtpname.substr(0,20);     		
	var txtdname = txtpname.substr(0,20) + "D";
	for (var i = 0; i <= 3; i++)
	{
		if (document.getElementById(cumulative).value == 1)
		{
			if (eval(document.getElementById(txtpname).value) == 0 || document.getElementById(txtpname).value == "")
			{
				document.getElementById(txtdname).value=eval(document.getElementById(txtname).value);
			}
			else if(eval(document.getElementById(txtpname).value) > 0 && (document.getElementById(txtname).value != "Cannot be less" && document.getElementById(txtname).value != "Yesterdays Data Not Found" && document.getElementById(txtname).value != "More then tomorow"  && document.getElementById(txtname).value != "No EB Data" && document.getElementById(txtname).value !=  "No Feeder Data" && document.getElementById(txtname).value != "more then 24" && document.getElementById(txtname).value != "Should be More or equal"))
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
		  	if(eval(document.getElementById(txtdname).value) < 0 && i == 2 && eval(document.getElementById(txtname).value) > 0)
		  	{
		  		alert("Cannot be less");
		  		document.getElementById(txtdname).value = "0";
		  		document.getElementById(txtname).focus();
		  		document.getElementById(txtname).select();
		  	}
		}
	}
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
		if (document.getElementById("Typetxt").value == "D")
		{
			calc(numObj + "P",mpObj+"C",mFact);
		}
		else if(document.getElementById(numObj + "P").value != "" && (document.getElementById("Typetxt").value == "M" || document.getElementById("Typetxt").value == "Y"))
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
				req.onreadystatechange = getReadyStateHandler(req, UpdateFD,numObj,"");
				req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				req.send("Admin_Input_Type=SU_UpdateFD&AppId="+ApplicationId);
				
				
				//ApplicationId =  document.getElementById("Datetxt").value +","+numObj.substr(11,20);
				//req.onreadystatechange = getReadyStateHandler(req, UpdateFD,numObj,"");
				//req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
				//req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				//req.send("Admin_Input_Type=SU_UpdateFD&AppId="+ApplicationId);
			}*/
		}
	}
}
function UpdateFD(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("fdentry")[0];
	var items = cart.getElementsByTagName("fdcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
     	if (nname != null){
     		if(item.getElementsByTagName("fdid")[0].firstChild.nodeValue != "")
     		{
     			//alert(item.getElementsByTagName("ebid")[0].firstChild.nodeValue);
     			document.getElementById(a).value = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
	  			if((document.getElementById(a).value == "Cannot be less" && document.getElementById(a).value == "Yesterdays Data Not Found" && document.getElementById(a).value == "More then tomorow"  && document.getElementById(a).value == "No EB Data" && document.getElementById(a).value ==  "No Feeder Data" && document.getElementById(a).value == "more then 24" && document.getElementById(a).value == "Should be More or equal"))
	  			{
	  				document.getElementById(a).focus();
	   				document.getElementById(a).select();
	  				document.getElementById(a + "D").value = "0";
	  			}
     		}
   	 	}  
   	 	findLL();
   	 	UpdateEBByfeder(a);
	}
}
function UpdateEBByfeder(a) 
{
	 var req = newXMLHttpRequest();
	 var dval = document.getElementById(a + "D").value;
	 if (dval == "")
	 {
	 	dval = "0";
	 }
	 if((document.getElementById(a).value != "Cannot be less" && document.getElementById(a).value != "Yesterdays Data Not Found" && document.getElementById(a).value != "More then tomorow"  && document.getElementById(a).value != "No EB Data" && document.getElementById(a).value !=  "No Feeder Data" && document.getElementById(a).value != "more then 24" && document.getElementById(a).value != "Should be More or equal"))
	 {
		 var ApplicationId =  a + "," + document.getElementById(a).value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value  + "," +dval;
		 req.onreadystatechange = getReadyStateHandler(req, showUpdateEBByfeder,"","");	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=SU_showUpdateEBByfeder&AppId="+ApplicationId);
	 }
}
function showUpdateEBByfeder(dataXml,a,b)
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
	req.onreadystatechange = getReadyStateHandler(req, UpdateEBRemarks,numObj,"");
	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=SU_UpdateFDemarks&AppId="+ApplicationId);*/
}
function UpdateEBRemarks(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("fdentry")[0];
	var items = cart.getElementsByTagName("fdcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("fdid")[0].firstChild;
     	if (nname != null){
	   		if(item.getElementsByTagName("fdid")[0].firstChild.nodeValue != "")
	   		{
     			document.getElementById(a).value = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
     		}
   	 	}   	 	
	}
}
function forwrd(fdid,chkbox,cnt,mFact) 
{
	if (document.getElementById(chkbox).checked == true)
	{	
		var req = newXMLHttpRequest();	 
	    var ApplicationId =  fdid + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value;
		req.onreadystatechange = getReadyStateHandler(req,FDCarryForward,cnt,fdid+""+document.getElementById(mFact).value);
		req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send("Admin_Input_Type=SU_FDCarryForward&AppId="+ApplicationId);
	}
}
function FDCarryForward(dataXml,a,b)
{
	findFDReadings();
	//for (var cnt = 0; cnt < a; cnt++)
	//{
	//	var idname = totpara[cnt]+""+b+"P";
	//	var mpname = totpara[cnt];
	//	calc(idname,mpname);		
	//}
}
</script>
</head>
<body onload="findData()" >
	
	<form action="<%=request.getContextPath()%>/FDReading.do" method="post" >
		<input type="hidden" name="SiteIdtxt" id="SiteIdtxt" value="<%=siteid %>" />
		<input type="hidden" name="Datetxt" id="Datetxt" value="<%=date %>" />
		<input type="hidden" name="Typetxt" id="Typetxt" value="<%=type %>" />
		<input type="hidden" name="Starttxt" id="Starttxt" value="<%=strt %>" />
		<input type="hidden" name="Endtxt" id="Endtxt" value="<%=endd %>" />
		
		<div id="mpdetails">
	
		</div>
		<div class="blue_text">Page No: <%=(Integer.parseInt(endd)/5)%>&nbsp;&nbsp;&nbsp;<input name="Submit" class="loginbox" value="Submit" type="submit" /></div>	
		<input type="hidden" name="Admin_Input_Type" value="FDEntry" />	
		<br>
		<%String str=(String)session.getAttribute("msg");%>
		<%if(str != null){%>
		<%=str%>
		<%}%>
		<%session.setAttribute("msg","");%>
	</form>
</body>
</html>