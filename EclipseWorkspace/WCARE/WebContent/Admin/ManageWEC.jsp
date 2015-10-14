<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
int status = 0;
int astatus = 0;
String ftype = "0";
String EBIdtxt = "";
String locationtxt = "";
String Customeridtxt = "";
String WECTypetxt = "";
String WECMFactortxt = "";
String Weccapacitytxt = "";
String WECShorttxt = "";
String wecidtxt="";
String Statetxt="";
String EBAreatxt="";
String EBSitetxt="";
String Datetxt="";
String StartDatetxt="";
String EndDatetxt="";
String GenComtxt = "";
String MacAvatxt = "";
String ExtAvatxt ="";
String IntAvatxt = "";
String CostPerUnit = "";
String WECSrNoTxt = "";
String EditTxt = "";
String Feedertxt = "";

DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	 Statetxt=dynabean.getProperty("Statetxt").toString();
	 EBAreatxt=dynabean.getProperty("EBAreatxt").toString();
	 EBSitetxt=dynabean.getProperty("EBSitetxt").toString();
	 EBIdtxt = dynabean.getProperty("EBIdtxt").toString();
	 Customeridtxt=dynabean.getProperty("Customeridtxt").toString();
	 String str1=(String)session.getAttribute("SubmitMessage");
	 WECTypetxt = dynabean.getProperty("WECTypetxt").toString();
	 Weccapacitytxt= dynabean.getProperty("Weccapacitytxt").toString();	
	 GenComtxt= dynabean.getProperty("GenComtxt").toString();	
	 MacAvatxt= dynabean.getProperty("MacAvatxt").toString();	
	 ExtAvatxt= dynabean.getProperty("ExtAvatxt").toString();	
	 IntAvatxt= dynabean.getProperty("IntAvatxt").toString();	
	 StartDatetxt=dynabean.getProperty("StartDatetxt").toString();	
	 EndDatetxt=dynabean.getProperty("EndDatetxt").toString();
	 CostPerUnit=dynabean.getProperty("CostPerUnit").toString();
	 WECSrNoTxt=dynabean.getProperty("WECSrNoTxt").toString();	
	 ftype=dynabean.getProperty("WECFormulatxt").toString();
	 Feedertxt=dynabean.getProperty("Feedertxt")==null?"":dynabean.getProperty("Feedertxt").toString();
	 
	if(str1 != null && str1.equals("Success")){
		wecidtxt = "";
		WECMFactortxt = "";
		WECShorttxt = "";
		locationtxt = "";
		Datetxt = "";
	}else{
		 wecidtxt=dynabean.getProperty("wecidtxt").toString();
		 WECMFactortxt = dynabean.getProperty("WECMFactortxt").toString();
		 WECShorttxt = dynabean.getProperty("WECShorttxt").toString();
		 locationtxt= dynabean.getProperty("locationtxt").toString();	
		 Datetxt= dynabean.getProperty("Datetxt").toString();	
		 if(dynabean.getProperty("WECStatustxt") == null || (dynabean.getProperty("WECStatustxt")).toString().equals("1")){
	  		status = 1;
		 }
		 else{
			status = 2;
		 }
		 
		 if(dynabean.getProperty("WECAStatustxt") == null || (dynabean.getProperty("WECAStatustxt")).toString().equals("0")){
		  		astatus = 0;
			 }
			 else{
				astatus = 1;
			 }
	}
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String ebid = "";
String sitename = "";
String custid = "";
String statename = "";
String areaname="";
String feedername="";

// Calendar now = Calendar.getInstance();
// String calfromdt = now.get(Calendar.DATE) + "/" + (now.get(Calendar.MONTH) + 2) + "/"+ now.get(Calendar.YEAR);
// String calendt = now.get(Calendar.DATE) + "/" + (now.get(Calendar.MONTH) + 1) + "/"+ (now.get(Calendar.YEAR)+1);

long MILLIS_IN_MONTH = 2628000000L;
long MILLIS_IN_YEAR = 34251600000L;

SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date date = new Date();

String commisionedDate = dateFormat.format(date.getTime());
String contractStDate = dateFormat.format(date.getTime()+ MILLIS_IN_MONTH);
String contractEnDate = dateFormat.format(date.getTime()+ MILLIS_IN_YEAR);
String roleid=session.getAttribute("RoleID").toString();

if(roleid.equals("0000000001"))
	statename = AdminUtil.fillMaster("TBL_STATE_MASTER",Statetxt);
else
	statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",Statetxt,session.getAttribute("loginID").toString());

if(!statename.equals(""))
{ 
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS1",EBSitetxt,Statetxt+","+session.getAttribute("loginID").toString());
}
if(!statename.equals(""))
{ 
	areaname=AdminUtil.fillWhereMaster("TBL_STATE_AREA_RIGHTS_1",EBAreatxt,Statetxt);
}
if(!sitename.equals(""))
{ 
	ebid=AdminUtil.fillWhereMaster("VIEW_EB_MASTER",EBIdtxt,EBSitetxt);
}
if(!sitename.equals(""))
{ 
	custid = AdminUtil.fillWhereMaster("SELECT_CUSTOMER_MASTER_BY_EB",Customeridtxt,EBSitetxt);
}
if(!areaname.equals(""))
{ 
	feedername = AdminUtil.fillWhereMaster("SELECT_FEDEER_MASTER_BY_AREA",Feedertxt,EBAreatxt);
}
String wectype = AdminUtil.fillMaster("TBL_WEC_TYPE",WECTypetxt);
String guaranteetype = "";
%>

<script type="text/javascript">

  function ViewFormulaDesc()
  { 	var str="<table width=710 border=1>"
  				str+="<tr bgcolor=#ffffff><td colspan='2' width=600 align=center class='detailsheading'>Formula For</td><td width=100 class='detailsheading'>Applicable State</td></tr>"
  				str+="<tr><td colspan='3' width=600 align=center class='detailsheading'>Machine Availability</td></tr>"
  				str+="<tr><td>0</td><td>((Total hours - (machine fault+machine shut down+grid internal fault+grid internal shut down))/Total hours) *100</td><td>AP,GJ,TN,RJ,MH</td></tr><tr><td>1</td><td>(Total hours - (machine fault+machine shut down))/Total hours *100</td><td>KA,MP</td></tr>"
  				str+="<tr ><td class='detailsheading' colspan='3'  width=600 align=center>Grid Availability</td></tr>"
  				str+="<tr><td>0</td><td>(Total hours - (grid internal fault+grid internal shut down))/Total hours *100</td><td>AP,GJ,TN,RJ,MH</td></tr><tr><td>1</td><td>((Total hours - (grid external fault+grid external shut down+grid internal fault+grid internal shut down)/Total hours)*100</td><td>KA,MP</td></tr>";
  		document.getElementById("formuladesc").innerHTML = str;
  }
  
  function findArea() 
	{
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].Statetxt;
	     var ApplicationId = list.options[list.selectedIndex].value;
	     // ApplicationId=ApplicationId+","+document.forms[0].RoleId.value;	    
		 req.onreadystatechange = getReadyStateHandler(req, showArea);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getAreaBYRIGHTS&AppId="+ApplicationId);
	}

	function showArea(dataXml)
	{
		var cart = dataXml.getElementsByTagName("areahead")[0];
		var items = cart.getElementsByTagName("areacode");
		document.forms[0].EBAreatxt.options.length = 0;
		document.forms[0].EBAreatxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("adesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     		document.forms[0].EBAreatxt.options[I + 1] = new Option(item.getElementsByTagName("adesc")[0].firstChild.nodeValue,item.getElementsByTagName("aid")[0].firstChild.nodeValue);
	        }
        }
	}
	
	function findFeeder() 
	{
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].EBAreatxt;
	     var ApplicationId = list.options[list.selectedIndex].value;
	     // ApplicationId=ApplicationId+","+document.forms[0].RoleId.value;	    
		 req.onreadystatechange = getReadyStateHandler(req, showFeeder);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getFeederByArea&AppId="+ApplicationId);
	}

	function showFeeder(dataXml)
	{
		var cart = dataXml.getElementsByTagName("feederhead")[0];
		var items = cart.getElementsByTagName("feedercode");
		document.forms[0].Feedertxt.options.length = 0;
		document.forms[0].Feedertxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("fdesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     		document.forms[0].Feedertxt.options[I + 1] = new Option(item.getElementsByTagName("fdesc")[0].firstChild.nodeValue,item.getElementsByTagName("fid")[0].firstChild.nodeValue);
	        }
        }
	}
	
	function findSite() 
	{
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].EBAreatxt;
	     var ApplicationId = list.options[list.selectedIndex].value;
	     // ApplicationId=ApplicationId+","+document.forms[0].RoleId.value;	 THIS IS REQUIRED WHEN AITE BY AREA IMPLEMENTED PLZ   
		 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getSiteByArea&AppId="+ApplicationId);
		 // req.send("Admin_Input_Type=getSiteBYRIGHTS&AppId="+ApplicationId);  sight by rights need to be changed to site by area
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
	function findeb() 
	{		
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].Customeridtxt;	
		 var list1 = document.forms[0].EBSitetxt;	
	     var ApplicationId = list.options[list.selectedIndex].value+","+list1.options[list1.selectedIndex].value;  	     	     
		 req.onreadystatechange = getReadyStateHandler(req, showeb);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getebbycust&AppId="+ApplicationId);
	}
	function showeb(dataXml)
	{
		var cart = dataXml.getElementsByTagName("sitehead")[0];
		var items = cart.getElementsByTagName("sitecode");
		document.forms[0].EBIdtxt.options.length = 0;
		document.forms[0].EBIdtxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++)
		   	{   
		     	var item = items[I]
		     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
		     	if (nname != null)
		     	{
		     	document.forms[0].EBIdtxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
		     	//document.forms[0].Customeridtxt.options[I + 1] = new Option(item.getElementsByTagName("weccusid")[0].firstChild.nodeValue,item.getElementsByTagName("custid")[0].firstChild.nodeValue);
	
		        }		
	        }
	        // findcust();
	}
	function findcust() 
	{ 	
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].EBSitetxt;		 	
	     var ApplicationId = list.options[list.selectedIndex].value;	       
		 req.onreadystatechange = getReadyStateHandler(req, showebcust);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getcust_id&AppId="+ApplicationId);
	}
	function showebcust(dataXml)
	{
		var cart = dataXml.getElementsByTagName("sitehead")[0];
		var items = cart.getElementsByTagName("sitecode");
		document.forms[0].Customeridtxt.options.length = 0;
		document.forms[0].Customeridtxt.options[0] = new Option("--Make a Selection--","");
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("weccusid")[0].firstChild;
	     	if (nname != null)
	     	{
	     		document.forms[0].Customeridtxt.options[I + 1] = new Option(item.getElementsByTagName("weccusid")[0].firstChild.nodeValue,item.getElementsByTagName("custid")[0].firstChild.nodeValue);

	        }		
        }
        // findeb();
	}
	function findCost()
	{		
		var req = newXMLHttpRequest();	 
		var list = document.forms[0].EBSitetxt;	
	    var ApplicationId = list.options[list.selectedIndex].value;     
		req.onreadystatechange = getReadyStateHandler(req, showcostbysite);	  
		req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		req.send("Admin_Input_Type=getcostbysite&AppId="+ApplicationId);
	}
	function showcostbysite(dataXml)
	{     
		var cart = dataXml.getElementsByTagName("costhead")[0];
		var items = cart.getElementsByTagName("costcode");
	     	
	    for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("costid")[0].firstChild;
	     	if (nname != null)
	     	{
	     		document.forms[0].CostPerUnit.value = item.getElementsByTagName("costunit")[0].firstChild.nodeValue;	     		
	        }		
        }     
	}	
	
	function findWECType()
	{
		 var req = newXMLHttpRequest();	 
		 var list = document.forms[0].WECTypetxt;	
	     var ApplicationId = list.options[list.selectedIndex].value;     
		 req.onreadystatechange = getReadyStateHandler(req, showebtype);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getwectype&AppId="+ApplicationId);
	}
	function showebtype(dataXml)
	{
		var cart = dataXml.getElementsByTagName("typemaster")[0];
		var items = cart.getElementsByTagName("typecode");
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("id")[0].firstChild;
	     	if (nname != null)
	     	{
	     		document.forms[0].Weccapacitytxt.value = item.getElementsByTagName("cap")[0].firstChild.nodeValue;

	        }		
        }
	        
	}
	function findApplication() 
	{
	    var req = newXMLHttpRequest();
	    // alert(req);
	    var list = document.forms[0].EBIdtxt;	    
      	var ApplicationId = list.options[list.selectedIndex].value;
      	if (ApplicationId != "")
      	{
			 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
			 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
			 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			 req.send("Admin_Input_Type=getwecmaster_new&AppId="+ApplicationId);
		}
	}
	function showAppDetails(dataXml)
	{
		var cart = dataXml.getElementsByTagName("wecmaster")[0];
		
		var items = cart.getElementsByTagName("weccode");	
			var divdetails = document.getElementById("WECdetails");
			divdetails.innerHTML = "";
			var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
			str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='90'>NAME</th><th class='detailsheading' width='180'>Customer</th><th class='detailsheading' width='120'>EB</th>"
			str +="<th class='detailsheading' width='70'>WECTYPE</th><th class='detailsheading' width='70'>CAPACITY</th><th class='detailsheading' width='70'>FACTOR</th><th class='detailsheading' width='70'>GEN.COM.</th>"
			str +="<th class='detailsheading' width='40'>STATUS</th><th class='detailsheading' width='40'>EDIT</th><th class='detailsheading' width='40'>Copy</th></tr>";
			for (var I = 0 ; I < items.length ; I++)
		   	{	   		
		     	var item = items[I];
		     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
		     	if (nname != null){
		     	
		     		
		     		if (I % 2 == 0)
				      {
				        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
				      }
				      else
				      {
				        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
				      }	     		
		     		str+="<td align='left'>" + item.getElementsByTagName("desc")[0].firstChild.nodeValue + "</td>"	
		     		str+="<td align='left'>" + item.getElementsByTagName("weccusid")[0].firstChild.nodeValue + "</td>" 
		     		str+="<td align='left'>" + item.getElementsByTagName("wecebname")[0].firstChild.nodeValue + "</td>"
		     		str+="<td align='left'>" + item.getElementsByTagName("wectype")[0].firstChild.nodeValue + "</td>"
		     		str+="<td align='left'>" + item.getElementsByTagName("weccaptype")[0].firstChild.nodeValue + "</td>"	     		
		     		str+="<td align='left'>" + item.getElementsByTagName("wecfactor")[0].firstChild.nodeValue + "</td>"
		     		str+="<td align='left'>" + item.getElementsByTagName("gencom")[0].firstChild.nodeValue + "</td>"
		     		if(item.getElementsByTagName("wecstatus")[0].firstChild.nodeValue == "1"){
		     			str+="<td align='left'>Active</td>";
		     		}else if(item.getElementsByTagName("wecstatus")[0].firstChild.nodeValue == "2"){
		     			str+="<td align='left'>Deactive</td>";	     			  
		     		}else {
		     			str+="<td align='left'>Transferred</td>";	     			  
		     		}		     		  
		     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
		     		str+="onClick=findDetails('" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue + "','E')></td>"
		     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' "
		     		str+="onClick=findDetails('" + item.getElementsByTagName("wecid")[0].firstChild.nodeValue + "','C')></td></tr>"
		   	 		
		   	 	}   	 	
			}
			str += "</table>"
	        if(document.forms[0].wecidtxt.value!="")
   	 		{}
   	 		else
   	 			document.forms[0].wecidtxt.value= "";		
			divdetails.innerHTML = str;
			
			
	}
	function findDetails(roleid,type)
	{
		 var req = newXMLHttpRequest();
		 document.forms[0].edittytxt.value =type;		 
	     var ApplicationId = roleid+"|";
		 req.onreadystatechange = getReadyStateHandler(req, showRoleMaster);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getwecmaster_new&AppId="+ApplicationId);
	}
	function showRoleMaster(dataXml)
	{
		var cart = dataXml.getElementsByTagName("wecmaster")[0];	
		var items = cart.getElementsByTagName("weccode");
		
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
	     	if (nname != null){
	     	  // alert(item.getElementsByTagName("custid")[0].firstChild.nodeValue);
	     	  // alert(item.getElementsByTagName("weccusid")[0].firstChild.nodeValue);
	     	  	document.forms[0].EBAreatxt.value = item.getElementsByTagName("areaid")[0].firstChild.nodeValue;
	   	 		document.forms[0].EBSitetxt.value = item.getElementsByTagName("siteid")[0].firstChild.nodeValue;
	     	  
	   	 		document.forms[0].Customeridtxt.value = item.getElementsByTagName("custid")[0].firstChild.nodeValue;
	   	 		document.forms[0].EBIdtxt.value = item.getElementsByTagName("wecebid")[0].firstChild.nodeValue;
	   	 		
	   	 		document.forms[0].WECTypetxt.value = item.getElementsByTagName("wectype")[0].firstChild.nodeValue;
	   	 		document.forms[0].Weccapacitytxt.value = item.getElementsByTagName("weccaptype")[0].firstChild.nodeValue;   	 		
	   	 		document.forms[0].WECMFactortxt.value = item.getElementsByTagName("wecfactor")[0].firstChild.nodeValue;
	   	 		document.forms[0].WECShorttxt.value = item.getElementsByTagName("desc")[0].firstChild.nodeValue; 
	   	 		document.forms[0].locationtxt.value = item.getElementsByTagName("locno")[0].firstChild.nodeValue;    	 		
	   	 		document.forms[0].wecidtxt.value = item.getElementsByTagName("wecid")[0].firstChild.nodeValue;
	   	 		document.forms[0].GenComtxt.value = item.getElementsByTagName("gencom")[0].firstChild.nodeValue;
	   	 		document.forms[0].MacAvatxt.value = item.getElementsByTagName("macava")[0].firstChild.nodeValue;	 
	   	 		document.forms[0].ExtAvatxt.value = item.getElementsByTagName("extava")[0].firstChild.nodeValue;
	   	 		document.forms[0].IntAvatxt.value = item.getElementsByTagName("intava")[0].firstChild.nodeValue;  
	   	 		document.forms[0].CostPerUnit.value = item.getElementsByTagName("costperunit")[0].firstChild.nodeValue;	 
	   	 		document.forms[0].WECSrNoTxt.value = item.getElementsByTagName("wecserialno")[0].firstChild.nodeValue;	
	   	 		document.forms[0].WECGuaranteeTypeTxt.value = item.getElementsByTagName("guaranteetype")[0].firstChild.nodeValue;	
	   	 		document.forms[0].CustomerTypeTxt.value = item.getElementsByTagName("customertype")[0].firstChild.nodeValue;
	   	 		document.forms[0].DataScadatxt.value = item.getElementsByTagName("scadadata")[0].firstChild.nodeValue;	   	 		
	   	 		if(item.getElementsByTagName("feederid")[0].firstChild.nodeValue!=".")	   	 		
	   	 			document.forms[0].Feedertxt.value = item.getElementsByTagName("feederid")[0].firstChild.nodeValue;	 
	   	 			  	 		
	   	 		
	   	 		if(document.forms[0].edittytxt.value=="E")
	   	 		{	   	 		 		
	   	 				document.forms[0].EBIdtxt.disabled = true;	   	 			
	   	 		}
	   	 		if (item.getElementsByTagName("wecserialno")[0].firstChild.nodeValue != ".")
	   	 		{
	   	 			document.forms[0].WECSrNoTxt.value = item.getElementsByTagName("wecserialno")[0].firstChild.nodeValue;
	   	 		}
	   	 		else
	   	 		{
	   	 			document.forms[0].WECSrNoTxt.value = "";
	   	 		}
	   	 		if (item.getElementsByTagName("cdate")[0].firstChild.nodeValue != ".")
	   	 		{
	   	 			document.forms[0].Datetxt.value = item.getElementsByTagName("cdate")[0].firstChild.nodeValue;
	   	 		}
	   	 		else
	   	 		{
	   	 			document.forms[0].Datetxt.value = "";
	   	 		}
	   	 		if (item.getElementsByTagName("fromdate")[0].firstChild.nodeValue != ".")
	   	 		{
	   	 			document.forms[0].StartDatetxt.value = item.getElementsByTagName("fromdate")[0].firstChild.nodeValue;
	   	 		}
	   	 		else
	   	 		{
	   	 			document.forms[0].StartDatetxt.value = "";
	   	 		}
	   	 		if (item.getElementsByTagName("todate")[0].firstChild.nodeValue != ".")
	   	 		{
	   	 			document.forms[0].EndDatetxt.value = item.getElementsByTagName("todate")[0].firstChild.nodeValue;
	   	 		}
	   	 		else
	   	 		{
	   	 			document.forms[0].EndDatetxt.value = "";
	   	 		}
	   	 		if(item.getElementsByTagName("wecstatus")[0].firstChild.nodeValue=="2")
	   	 		{	   	 			
	  		       	document.forms[0].WECStatustxt.checked=true;
	        	}
		        else
		        {		        	
		        	document.forms[0].WECStatustxt.checked=false;
		        }
		        
		        if(item.getElementsByTagName("wecastatus")[0].firstChild.nodeValue=="1")
	   	 		{	   	 			
	  		       	document.forms[0].WECAStatustxt.checked=false;
	        	}
		        else
		        {		        	
		        	document.forms[0].WECAStatustxt.checked=true;
		        }
		        document.forms[0].WECFormulatxt.value = item.getElementsByTagName("wecformula")[0].firstChild.nodeValue;
		        if(document.forms[0].edittytxt.value=="E")
		        {
		        EditTxt = document.forms[0].edittytxt.value;		        
		       	 	
		            document.getElementById("idwecname").innerHTML.value = "WEC Name";
		            
	   	 			document.forms[0].Submit.value="Update";
	   	 		}
	   	 		else
	   	 		{
	   	 		  
		        	document.forms[0].WECShorttxt.value = ""; 
		        	document.getElementById("idwecname").innerHTML.value = "<B>New WEC Name :</b>";
		        	document.forms[0].wecidtxt.value = "";  
	   	 			document.forms[0].Submit.value="Submit";
	   	 		}
	   	 	}
	   	 	else{
	   	 		document.forms[0].Customeridtxt.value = "";
	   	 		document.forms[0].EBIdtxt.value = "";  
	   	 		document.forms[0].WECTypetxt.value = "";
	   	 		document.forms[0].Weccapacitytxt.value = "";
	   	 		document.forms[0].WECMFactortxt.value = "";  
	   	 		document.forms[0].WECShorttxt.value = "";  	
	   	 		document.forms[0].wecidtxt.value = "";  
	   	 		document.forms[0].Datetxt.value = "";  	 
	   	 		document.forms[0].StartDatetxt.value = "";  	 
	   	 		document.forms[0].EndDatetxt.value = "";  	 
	   	 		document.forms[0].GenComtxt.value = "";  	 
	   	 		document.forms[0].MacAvatxt.value = "";	 
	   	 		document.forms[0].ExtAvatxt.value = "";
	   	 		document.forms[0].IntAvatxt.value = "";
	   	 		document.forms[0].CostPerUnit.value = "";  
	   	 		document.forms[0].WECSrNoTxt.value = "";  	 		
	   	 		document.forms[0].WECStatustxt.checked=false;  
	   	 		document.forms[0].WECAStatustxt.checked=false;  	 				
	   	 	} 			
	 	}
	}
	
function validate(frm)
{	
	var ch;
	var flag=true;
	var error="";
	
		 if(isNaN(frm.WECMFactortxt.value)){
			flag=false;
			error=error+"Only numbers are allowed in multifactor .\n";
			if(focus==false){
				frm.WECMFactortxt.focus();
				focus=true;
			  }
		 }
		if(isNaN(frm.GenComtxt.value)){
			flag=false;
			error=error+"Only numbers are allowed in Generation Committed .\n";
			if(focus==false){
				frm.GenComtxt.focus();
				focus=true;
			  }
		 }
	
	if(flag==false)
	{
		alert(error);
		return false;
	}
	else {
		// return true;
		if(confirmation())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
}
function confirmation()
{   	
	if(document.forms[0].wecidtxt.value!="")
	{
		var answer = confirm("Are you sure you want to Update?")
		if (answer)
		{   
			document.forms[0].EBIdtxt.disabled = false;			
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
			if(valDate())
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
			else 
			{				
				return false;
			}		
	}
}
function valDate()
{	
	var todate=new Date(); // yyyy-mm-dd
	var fromdate = new Date(document.getElementById("Datetxt").value.substr(6,4),document.getElementById("Datetxt").value.substr(3,2) -1,document.getElementById("Datetxt").value.substr(0,2));
	var one_day=1000*60*60*24;	
    var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day));
    if(days<=0)
  	{
	  	 alert("Future commision date not allowed!");
	  	 return false;
  	}else
  	 	 return true;
}
</script>
</head>
<body onload="findApplication()">
<form action="<%=request.getContextPath()%>/WECMaster.do" method="post" name="WECMaster" id="WECMaster"  onSubmit="return validate(this)">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">WEC Master</th>
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
		<tbody><tr bgcolor="#ffffff">
			<td colspan="4" id="t_title">&nbsp;</td>
			</tr>
			
			
			<tr bgcolor="#ffffff">
			<td id="t_company" >Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findArea()">
				<option value="">--Make a Selection--</option>
				            <%=statename%></select></td>
				            
				            
		    <td id="t_general_information">Select Area:</td>
			  <td bgcolor="#ffffff" ><label>
			    <select name="EBAreatxt" id="EBAreatxt" class="ctrl" onChange="findFeeder(), findSite()">
	              <option value="">--Make a Selection--</option>
	              <%=areaname%> 
	            </select>
			  </td>
			</tr>
			
			<tr bgcolor="#ffffff">
			<td id="t_company" >Select Feeder:</td>
			<td valign="top">
				<select name="Feedertxt" id="Feedertxt" class="ctrl">
				<option value="">--Make a Selection--</option>
				            <%=feedername%></select></td>
				            
				            
		    <td id="t_general_information">Select Site:</td>
			  <td bgcolor="#ffffff" ><label>
			    <select name="EBSitetxt" id="EBSitetxt" class="ctrl" onChange="findcust(), findCost()">
	              <option value="">--Make a Selection--</option>
	              <%=sitename%> 
	            </select>
			  </td>
			</tr>
			
		  	<tr bgcolor="#ffffff"> 
	            
	            <td id="t_company">Customer:</td>
				<td valign="top">
					<select name="Customeridtxt" id="Customeridtxt" class="ctrl" onChange="findeb()">
		              	<option value="" selected="selected">-- select --</option>
		             	<%=custid%>
		            </select>
		        </td>
	            
	            <td id="t_street_address">EB:</td>
				<td bgcolor="#ffffff">
					<select name="EBIdtxt" class="ctrl" onChange="findApplication()">
	              		<option value="" selected="selected">-- select --</option>  
               			<%=ebid%>
	            	</select>
	            </td>	           
			</tr>
			
			<tr bgcolor="#ffffff"> 
				<td id="t_street_address_ln2" nowrap="nowrap">WEC Type:</td>
				<td><select name="WECTypetxt" class="ctrl" onChange="findWECType()">              
	              <option value="">-- select --</option>			
	              <%=wectype %>	  
				  </select>
				</td>
			
				<td id="n_cost_per_unit" nowrap="nowrap">Cost Per Unit:</td>
				<td bgcolor="#ffffff">
					<input name="CostPerUnit" id="CostPerUnit" size="20" class="ctrl" value="<%=CostPerUnit%>" type="text" />
			  	</td>
			
			</tr>
			<tr bgcolor="#ffffff">
			  <td id="t_general_information"><span="idwecname" id="idwecname">Name:</td>
			  <td bgcolor="#ffffff">
			  <input name="WECShorttxt" size="35" class="ctrl" value="<%=WECShorttxt %>" type="text" />
			  </td>
			 
			  <td id="t_general_information">Foundation Location :</td>
			  <td bgcolor="#ffffff">
			  <input name="locationtxt" size="20" class="ctrl" value="<%=locationtxt %>" type="text" />
			  </td>
		  	</tr>
		  	
			<tr bgcolor="#ffffff"> 
				<td id="t_city">WEC Capacity:</td>
				<td><input name="Weccapacitytxt" size="10" class="ctrl" value="<%=Weccapacitytxt %>" type="text" /></td>
			
				<td id="t_general_information">Multifactor:</td>
				<td bgcolor="#ffffff"><input name="WECMFactortxt" size="10" class="ctrl"  value="1" type="text" value="<%=WECMFactortxt %>" /></td>
			</tr>	
			
		    <tr bgcolor="#ffffff"> 
				<td id="t_city">WEC Techincal No:</td>
				<td><input name="WECSrNoTxt" size="10" class="ctrl" value="<%=WECSrNoTxt%>" type="text" /></td>
			
				<td id="t_general_information" nowrap="nowrap">Guarantee Type:</td>
				<td bgcolor="#ffffff"><select name="WECGuaranteeTypeTxt" id="WECGuaranteeTypeTxt" class="ctrl"> 
					  <option value="0">--Make a Selection--</option>             
		              <option value="1">Machine Guarantee</option>
		              <option value="2" selected>Power Curve Guarantee</option>
		              <option value="3">Absolute Guarantee</option>	
		              <option value="4">Generation Guarantee at EB</option>
		              <option value="5">Others</option>   
				  </select>
				</td>
			</tr>	
			
			<tr class="bgcolor"> 
				<td id="t_street_address">Commision Date:</td>
				<td class="bgcolor">
					<input type="text" name="Datetxt" id="Datetxt" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()" value="<%=commisionedDate%>" />
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECMaster.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
				</td>
			 
				<td id="t_street_address">Cont. Start Date:</td>
				<td class="bgcolor">
					<input type="text" name="StartDatetxt" id="StartDatetxt" size="15" class="ctrl" maxlength="10" onfocus="dc1.focus()" value="<%=contractStDate%>" />
					<a href="javascript:void(0)" id="dc1" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECMaster.StartDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
				</td>
			</tr>
			<tr class="bgcolor"> 
				<td id="t_street_address">Cont. End Date:</td>
				<td class="bgcolor">
					<input type="text" name="EndDatetxt" id="EndDatetxt" size="15" class="ctrl" maxlength="10" onfocus="dc2.focus()" value="<%=contractEnDate%>" />
					<a href="javascript:void(0)" id="dc2" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECMaster.EndDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
				</td>
			
				<td id="t_general_information">Generation Committed:</td>
				<td bgcolor="#ffffff"><input name="GenComtxt" size="10" class="ctrl"  type="text" value="<%=GenComtxt %>" /></td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_general_information">Machine Availability:</td>
				<td bgcolor="#ffffff"><input name="MacAvatxt" id="MacAvatxt" size="10" class="ctrl"  type="text" value="<%=MacAvatxt %>" /></td>
			 
				<td id="t_general_information">External Grid Availability:</td>
				<td bgcolor="#ffffff"><input name="ExtAvatxt" id="ExtAvatxt" size="10" class="ctrl"  type="text" value="<%=ExtAvatxt %>" /></td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_general_information">Internal Grid Availability:</td>
				<td bgcolor="#ffffff"><input name="IntAvatxt" id="IntAvatxt" size="10" class="ctrl"  type="text" value="<%=IntAvatxt %>" /></td>
			
			  <td id="t_general_information">Deactivate</td>
			  <td bgcolor="#ffffff">
			  <%if (status == 1){ %>
					<input type="checkbox" name="WECStatustxt" id="WECStatustxt" value="1" />
				<%}else{ %>
					<input type="checkbox" name="WECStatustxt" id="WECStatustxt" value="2" />
				<%} %>
			  </td>
		  	</tr>
		  	
		  	
		  	<tr bgcolor="#ffffff">
			  <td id="t_general_information">Display Active</td>
			  <td bgcolor="#ffffff">
			  <%if (astatus == 0){ %>
					<input type="checkbox" name="WECAStatustxt" id="WECAStatustxt" value="0"  checked/>
				<%}else{ %>
					<input type="checkbox" name="WECAStatustxt" id="WECAStatustxt" value="1" />
				<%} %>
			  </td>
			  <td id="t_general_information" nowrap="nowrap">Customer Type:</td>
				<td bgcolor="#ffffff"><select name="CustomerTypeTxt" id="CustomerTypeTxt" class="ctrl"> 
					  <option value="0">--Make a Selection--</option>             
		              <option value="1" selected>Premier Customer</option>
		              <option value="2">Classic Customer</option>
		              <option value="3">General Customer</option>	
		              <option value="4">Others</option>   
				  </select>
				</td>
		  	</tr>
		  	
		 	<tr bgcolor="#ffffff">		 	
			 <td id="t_street_address_ln2" nowrap="nowrap">WEC Formula:</td>
				<td><select name="WECFormulatxt" class="ctrl" >    
				<% if(ftype.equals("0")) { %>          
	              <option value="0" selected>0</option>	
	              <option value="1">1</option>	
	              <%}else{%>
	               <option value="0" >0</option>	
	              <option value="1" selected>1</option>			
	                <%}%>
				  </select>
				   &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Show Description" onClick="ViewFormulaDesc()">
				</td>
				<td id="t_street_address_ln2" nowrap="nowrap">Data From Scada:</td>
				<td><select name="DataScadatxt" class="ctrl" >    
				<% if(ftype.equals("0")) { %>          
	              <option value="0" selected>0</option>	
	              <option value="1">1</option>	
	              <%}else{%>
	               <option value="0" >0</option>	
	              <option value="1" selected>1</option>			
	                <%}%>
				  </select>
				  
				</td>								
			</tr>
		  		
		  	<tr bgcolor="#ffffff"> 
				<td id="t_general_information" colspan="4"><span id="formuladesc" id="formuladesc"></td>				
		  	</tr>
		  	
			<tr class="bgcolor"> 
				<td colspan="4">
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
	<tbody><tr>
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit" FDIdtxt></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="WecMaster" />		
		<input type="hidden" name="wecidtxt" value="<%=wecidtxt %>" />		
		<input type="hidden" name="RoleId" value="<%=roleid %>" />		
		<input name="Reset" value="Cancel" class="btnform" type="reset">
		<input type="hidden" name="edittytxt" id="edittytxt" />	
		
		
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table></form>
<table border="0" cellpadding="0" align="center" cellspacing="0" width="700">
	<tbody>
		<tr>		
			<td align="center" >			
				<div id="WECdetails">	</div>	
			</td>
		</tr>
	</tbody>
</table>	
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>