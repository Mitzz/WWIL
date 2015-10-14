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
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
int status = 0;
String eid = "";
String sname = "";
String stid = "";
String ebshort="" ;
String ebdesc="";
String ebtype="";
String ebstype="" ;	
String ebmfactor = "";	
String ebstatus ="";	
String ebcapacity = "";
String ebsite = "";	
String fdid="";
String Customeridtxt = "";	
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	stid = dynaBean.getProperty("Statetxt").toString();
	ebsite = dynaBean.getProperty("EBSitetxt").toString();	
	fdid = dynaBean.getProperty("FDDesc") == null ? "" : dynaBean.getProperty("FDDesc").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		eid = "";
		sname = "";
		ebshort="" ;
		ebdesc="";
		ebtype="";
		ebstype="" ;	
		ebmfactor = "";	
		ebstatus ="";	
		ebcapacity = "";
		//fdid="";
		Customeridtxt = "";	
	}else{
		ebshort = dynaBean.getProperty("EBShorttxt").toString();
		ebdesc = dynaBean.getProperty("EBDesctxt").toString();
		ebtype = dynaBean.getProperty("EBTypetxt").toString();
		ebstype = dynaBean.getProperty("EBSubTypetxt").toString();	
		ebmfactor = dynaBean.getProperty("EBMFactortxt").toString();	
		Customeridtxt=dynaBean.getProperty("Customeridtxt").toString();
	  	//ebstatus = dynaBean.getProperty("EBStatustxt").toString();
	  	if(dynaBean.getProperty("EBStatustxt") == null || (dynaBean.getProperty("EBStatustxt")).toString().equals("1")){
	  		status = 1;
		}
		else{
			status = 2;
		}
		ebcapacity = dynaBean.getProperty("EBCapacitytxt").toString();		
		eid = dynaBean.getProperty("EBId").toString();				
	}	
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String sitename = "";
String feder = "";
String statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
//String statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",stid,session.getAttribute("loginID").toString());
//////System.out.println(stid + " " + ebsite);
//String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
if(!statename.equals(""))
{ 
	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER",ebsite,stid);
//	sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS",ebsite,stid+","+session.getAttribute("loginID").toString());
}

%>
<script type="text/javascript">
function findSite() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSite&AppId="+ApplicationId);
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




function popUp(URL) 
{
var day = new Date();
var id = day.getTime();
var rd=document.forms[0].Datetxt.value;
var list2=document.forms[0].Statetxt;
var list3=document.forms[0].EBSitetxt; 
var st=list2.options[list2.selectedIndex].value; 
var site=list3.options[list3.selectedIndex].value; 
var url="SiteWiseUpload.jsp";

//var urldesc=url+"?stateid="+list2.options[list2.selectedIndex].value+"&rd="+rd+"&siteid="+list3.options[list3.selectedIndex].value;

//eval("page" + id + " = window.open(urldesc, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=400,height=10,left = 262,top = 234');");

url=url+"?stateid="+st+"&rd="+rd+"&siteid="+site;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');


}



function checkWholeForm() 
{
    var why = "";
     	why += checkDropdown(document.forms[0].Statetxt.selectedIndex);
     	why += checkDropdown(document.forms[0].EBSitetxt.selectedIndex);
     	
     	var ss=document.UploadedWEC.Datetxt;
     	
     	why += checkUsername(ss);
     	
     	
     	
    if (why != "") {
       alert(why);
       return false;
    }
    else
    {
    popUp('url');
    return true;
    }
}
function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkUsername (strng) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "You didn't enter a Date.\n";
 		}
 		return error;
 }      	



</script>
</head>
<body onload="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/EBMaster.do" method="post" name="UploadedWEC" onSubmit="chkFunction(),validate(this)">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Upload Detail</th>
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
			<td id="t_company" width="219">Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findSite()">
					<option value="">--Make a Selection--</option>
					<%=statename%>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Site Name:</td>
		  <td bgcolor="#ffffff" width="85%">
		    <select name="EBSitetxt" id="EBSitetxt" class="ctrl">
              <option value="">--Make a Selection--</option>
				   <%=sitename%>     
            </select>
		  </td>
	  	</tr>
	  	<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10"  onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.UploadedWEC.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
			</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
		<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="checkWholeForm()" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
</table>
	

</body>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

</html>