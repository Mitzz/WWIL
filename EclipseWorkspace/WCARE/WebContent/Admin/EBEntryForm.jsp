<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

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

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Enercon.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>

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
////System.out.println(strt);
////System.out.println(endd);


%>
<title>EB Reading</title>
<script type="text/javascript">
var totpara=new Array();
var mpunit=new Array();
var mpshow=new Array();
var mactype=new Array();
var mCum=new Array();
var ebs=new Array();
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
	str+="<th class='detailsheading' width='10%'>EB Meter</th><th class='detailsheading' width='5%'>Carry Forward</th>";
	totpara=new Array(items.length);
	var cnt = 0;
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("mpid")[0].firstChild;
     	if (nname != null)
     	{
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
	findEBData(str);
}
function findEBData(str) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showEBData,str,"");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findPagedEBMaster&AppId="+ApplicationId);
}
function showEBData(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	var divdetails = document.getElementById("mpdetails");
	divdetails.innerHTML = "";
	var str = a;
	var pdate = "";
	var numb = 1;
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	if (nname != null){
     		if (item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue == 1)
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
			    str+="<td align='left'>" + item.getElementsByTagName("ebshort")[0].firstChild.nodeValue + "<br>("+item.getElementsByTagName("ebname")[0].firstChild.nodeValue+")<br>Multi. Fact:"+item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue+"</td>"	    
	     		str+="<td align=center>"
	     		var ebidd = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
	     		ebs[I] = ebidd;
			    str+="<input type='hidden' name='"+ebidd+"' id='"+ebidd+"' "
				str+=" value='"+item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue+"' />"
				str+="<input type='checkbox' id='carryforward"+I+"' name='carryforward"+I+"' "
	     		str+="onclick=forwrd('" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"',"
	     		str+="'carryforward"+I+"',"+totpara.length+",'"+ebidd+"') style='text-align:right' class='ctrl' /></td>"		    
				for (var cnt = 0; cnt < totpara.length; cnt++ )
				{			
					var idname = totpara[cnt]+""+item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
					str+="<td align=right><input type='hidden' name='"+totpara[cnt]+"' id='"+totpara[cnt]+"' value='"+mpunit[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"T' id='"+totpara[cnt]+"T' value='"+mactype[cnt]+"' />"
					str+="<input type='hidden' name='"+totpara[cnt]+"C' id='"+totpara[cnt]+"C' value='"+mCum[cnt]+"' />"
					str+="<input type='text' name='"+idname+"P'"
					str+=" id='"+idname+"P' class='ebcreading' maxlength='25' size='15' style='text-align:right' disabled /><br>"
		     		str+="<input type='text' name='"+idname+"' id='"+idname+"' class='ebpreading' maxlength='25' size='15' "
		     		str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+ebidd+"') "
		     		str+=" style='text-align:right' /><br><input type='text' name='"+idname+"D'"
					str+=" id='"+idname+"D' class='ebcreading' maxlength='25' size='15' style='text-align:right'"
					if (document.getElementById("Typetxt").value == "I"||document.getElementById("Typetxt").value == "MI")
					{
						if (mCum[cnt] == "2")
						{
							str+=" readOnly='true' onBlur=gotoSave('"+idname+"') /></td>"
						}
						else
						{
							str+=" onBlur=validateNumber1('"+idname+"','"+totpara[cnt]+"','"+totpara[cnt]+"T','"+ebidd+"') /></td>"
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
			    var idrname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"R";
			    var idrrname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"RR"; //for remarks combo
			    var idlname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"LL";
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
	findEBReadings();
	if (pdate != "")
	{
		findEBPrevReadings(pdate);
	}
	findLL();
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
		for (var cnt1 = 0; cnt1 < ebs.length; cnt1++ )
		{
			var idname = totpara[cnt]+""+ebs[cnt1];
			if (document.getElementById("Typetxt").value != "D" && document.getElementById(idname + "P").value =="")
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
	 req.send("Admin_Input_Type=SU_findLL&AppId="+ApplicationId);
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
     		var txtname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue +"LL";
     		document.getElementById(txtname).value= item.getElementsByTagName("lvalue")[0].firstChild.nodeValue;
   	 	}   	 	
	}	
}
function findEBReadings() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById("SiteIdtxt").value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value+","+document.getElementById("Typetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showEBReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	//////// req.send("Admin_Input_Type=SU_findEBData&AppId="+ApplicationId);
	  if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	 {
	     req.send("Admin_Input_Type=SU_findEBData&AppId="+ApplicationId);
	 }
	 else  
	 {
		req.send("Admin_Input_Type=SU_findEBDataMax&AppId="+ApplicationId);
	 }
}
function showEBReadings(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	if ((nname != null) && (mpidnn != null)){
     		nname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;    
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{
	     		var txtname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
	     		document.getElementById(txtname).value= item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;
	     		document.getElementById(txtname+"D").value= item.getElementsByTagName("dvalue")[0].firstChild.nodeValue;
	     		var txtrname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"R";
	     		document.getElementById(txtrname).value= item.getElementsByTagName("remarks")[0].firstChild.nodeValue;
	     		//document.getElementById(txtrname+"R").value = document.getElementById(txtrname).value;
	     	}
   	 	}   	 	
	}	
	done = 1;	
}
function findEBPrevReadings(pdate) 
{
	 var req = newXMLHttpRequest();
	 var ApplicationId = "";
	 if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	 {
	     ApplicationId = document.getElementById("SiteIdtxt").value + "," + pdate + ",.," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value;
	 }
	 else
	 {
	 	 ApplicationId = document.getElementById("SiteIdtxt").value + "," + pdate + "," + document.getElementById("Typetxt").value + "," + document.getElementById("Starttxt").value + "," + document.getElementById("Endtxt").value + "," + document.getElementById("Datetxt").value+",P";
	 }
	 req.onreadystatechange = getReadyStateHandler(req, showEBPrevReadings,"","");	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 if (document.getElementById("Typetxt").value == "D" || document.getElementById("Typetxt").value == "I")
	 {
	     req.send("Admin_Input_Type=SU_findEBData&AppId="+ApplicationId);
	 }
	 else
	 {
		req.send("Admin_Input_Type=SU_findEBDataMax&AppId="+ApplicationId);
	 }
	 
}
function showEBPrevReadings(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
   		var txtpname = "";
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	var mpidnn = item.getElementsByTagName("mpid")[0].firstChild;
     	if ((nname != null) && (mpidnn != null)){
     		nname = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
     		mpidnn = item.getElementsByTagName("mpid")[0].firstChild.nodeValue;    
     		if (nname == "." || mpidnn == ".")
     		{
     		}
     		else
     		{
	     		txtpname = item.getElementsByTagName("mpid")[0].firstChild.nodeValue +"" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue + "P";
	     		document.getElementById(txtpname).value=item.getElementsByTagName("nvalue")[0].firstChild.nodeValue;  
	     	}   		
     		//calc(txtpname,item.getElementsByTagName("mpid")[0].firstChild.nodeValue);
   	 	}   	
	}
	enabledisable();
}

function calc(txtpname,cumulative,mFact)
{
	var txtname = txtpname.substr(0,20);     		
	var txtdname = txtpname.substr(0,20) + "D";
	//for (var i = 0; i <= 3; i++)
	//{
		if (document.getElementById(cumulative).value == 1)
		{
			if (eval(document.getElementById(txtpname).value) == 0 || document.getElementById(txtpname).value == "")
			{
				document.getElementById(txtdname).value=eval(document.getElementById(txtname).value);
			}
			else if(eval(document.getElementById(txtpname).value) > 0 && (document.getElementById(txtname).value != "Cannot be less" && document.getElementById(txtname).value != "Yesterdays Data Not Found" && document.getElementById(txtname).value != "More then tomorow"  && document.getElementById(txtname).value != "No EB Data" && document.getElementById(txtname).value !=  "No Feder Data" && document.getElementById(txtname).value != "more then 24"  && document.getElementById(txtname).value != "Should be More or equal"))
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
			//if (document.getElementById(numObj).value == "Cannot be less")
			//{
				/*var req = newXMLHttpRequest();	 
			    var ApplicationId =  numObj + "," + document.getElementById(numObj).value + "," + document.getElementById("Datetxt").value + "," + document.getElementById(numObj + "D").value + "," + document.getElementById("Typetxt").value;
				req.onreadystatechange = getReadyStateHandler(req, UpdateEb,numObj,"");
				req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
				req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				req.send("Admin_Input_Type=SU_UpdateEb&AppId="+ApplicationId);*/
			//}
		}
	}
}
function UpdateEb(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("ebentry")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	if (nname != null){
     		if(item.getElementsByTagName("ebid")[0].firstChild.nodeValue != "")
     		{
     			//alert(item.getElementsByTagName("ebid")[0].firstChild.nodeValue);
     			document.getElementById(a).value = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
	  			if (document.getElementById(a).value == "Cannot be less" && document.getElementById(a).value == "Yesterdays Data Not Found" && document.getElementById(a).value == "More then tomorow"  && document.getElementById(a).value == "No EB Data" && document.getElementById(a).value ==  "No Feder Data" && document.getElementById(a).value == "more then 24")	
	  			{
	  				document.getElementById(a).focus();
	   				document.getElementById(a).select();
	  				document.getElementById(a + "D").value = "0";
	  			}
     		}
   	 	}  
   	 	findLL(); 	 	
	}
}
function updateremarks(numObj)
{
	/*var req = newXMLHttpRequest();	 
   	var ApplicationId =  numObj + "," + document.getElementById(numObj).value + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value;
	req.onreadystatechange = getReadyStateHandler(req, UpdateEBRemarks,numObj,"");
	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=SU_UpdateEBemarks&AppId="+ApplicationId);*/
}
function UpdateEBRemarks(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("ebentry")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
     	if (nname != null){
	   		if(item.getElementsByTagName("ebid")[0].firstChild.nodeValue != "")
	   		{
     			document.getElementById(a).value = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;
     		}
   	 	}   	 	
	}
}
function forwrd(ebid,chkbox,cnt,mFact) 
{
	if (document.getElementById(chkbox).checked == true)
	{	
		var req = newXMLHttpRequest();	 
	    var ApplicationId =  ebid + "," + document.getElementById("Datetxt").value + "," + document.getElementById("Typetxt").value;
		req.onreadystatechange = getReadyStateHandler(req, EBCarryForward,cnt,ebid+""+document.getElementById(mFact).value);
		req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send("Admin_Input_Type=SU_EBCarryForward&AppId="+ApplicationId);
	}
}
function EBCarryForward(dataXml,a,b)
{
	findEBReadings();
//	for (var cnt = 0; cnt < a; cnt++)
//	{
//		var idname = totpara[cnt]+""+b+"P";
//		var mpname = totpara[cnt];
//		calc(idname,mpname);		
//	}
}
</script>
</head>
<body onload="findData()">
	
	<form action="<%=request.getContextPath()%>/EBReading.do" method="post" >
		<input type="hidden" name="SiteIdtxt" id="SiteIdtxt" value="<%=siteid %>" />
		<input type="hidden" name="Datetxt" id="Datetxt" value="<%=date %>" />
		<input type="hidden" name="Typetxt" id="Typetxt" value="<%=type %>" />
		<input type="hidden" name="Starttxt" id="Starttxt" value="<%=strt %>" />
		<input type="hidden" name="Endtxt" id="Endtxt" value="<%=endd %>" />
		
		<div id="mpdetails">
	
		</div>
		<div class="blue_text">Page No: <%=(Integer.parseInt(endd)/5)%>&nbsp;&nbsp;&nbsp;<input name="Submit" class="loginbox" value="Submit" type="submit" /></div>		
		<input type="hidden" name="Admin_Input_Type" value="EBEntry" />	
		<br>
		<%String str=(String)session.getAttribute("msg");%>
		<%if(str != null){%>
		<%=str%>
		<%}%>
		<%session.setAttribute("msg","");%>
	</form>
</body>
</html>