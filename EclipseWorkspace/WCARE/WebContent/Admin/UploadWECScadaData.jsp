<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<%
if (session.getAttribute("loginID") == null)
   {
       response.sendRedirect(request.getContextPath());
   }
String userId = session.getAttribute("loginID").toString();
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type="text/javascript">
function checkFlag()
{
	if(document.getElementById(checkid).checked==true)
	 {
	 	 list_of_value_of_i=list_of_value_of_i+i1;
	 	 //alert(list_of_value_of_i);
	 	 comfirmed_emp_list=comfirmed_emp_list+appid1;
	 }
}
</script>
<%
String si = "";
String dd = "";
String tp = "";
int pb = 1;

//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	dd = dynabean.getProperty("Datetxt").toString();
	si = dynabean.getProperty("SiteIdtxt").toString();
	tp = dynabean.getProperty("Typetxt").toString();	
	pb = Integer.parseInt(dynabean.getProperty("Optiontxt").toString());
	session.removeAttribute("dynabean");
}
%>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();
String sState="";

if(roleid.equals("0000000001"))
     sState = AdminUtil.fillMaster("TBL_STATE_MASTER",sState);
else
	sState = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS",si,userid);

%>
<script type="text/javascript">

var updateWEC = '';

function displayScadaWECDataToUpload()
{	
	 document.getElementById("progressbar").style.display ="";	
	 
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].SiteIdtxt;	 
     var ApplicationId = list.options[list.selectedIndex].value;  
     ApplicationId=ApplicationId+","+document.forms[0].Datetxt.value;   

	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getScadaWECDataToUpload&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("stateDataPublish")[0];
	var items = cart.getElementsByTagName("stateDataCode");
		var divdetails = document.getElementById("areadetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='75'>State Name</th><th class='detailsheading' width='75'>Site Name</th>"
		str +="<th class='detailsheading' width='120'>Total WEC To Publish</th><th class='detailsheading' width='120'>Reading Date</th><th class='detailsheading' width='20'>Scada Data</th><th class='detailsheading' width='20'>Scada Data Exist</th><th class='detailsheading' width='40'>Total WEC</th><th class='detailsheading' width='40'>Display Scada Data</th><th class='detailsheading' width='50'>Publish</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("stateid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	    
			      
			      var statedesc =   item.getElementsByTagName("statedesc")[0].firstChild.nodeValue;	
			      // var areadesc =   item.getElementsByTagName("areadesc")[0].firstChild.nodeValue;
			      var sitedesc =   item.getElementsByTagName("sitedesc")[0].firstChild.nodeValue;	
			      
			      var newstatedesc = statedesc.replace("~"," ");
			      // var newareadesc = areadesc.replace("~"," ");
			      var newsitedesc = sitedesc.replace(/~/g," ");
			      
	     		str+="<td align='left'>" +newstatedesc+ "</td>"
	     		str+="<td align='left'>" +newsitedesc+ "</td>"		
	     		if (item.getElementsByTagName("wecpublished")[0].firstChild.nodeValue == ".")
	     		{
	     			str+="<td align='left'></td>"
	     		}
	     		else
	     		{
	     			str+="<td align='center'>" + item.getElementsByTagName("wecpublished")[0].firstChild.nodeValue + "</td>"
	     		}	   
	     		str+="<td align='center'>" + item.getElementsByTagName("readingdate")[0].firstChild.nodeValue + "</td>"  
	     		if (item.getElementsByTagName("scadadata")[0].firstChild.nodeValue=="1")
	     		{
	     			str+="<td align='center'>AV</td>" 
	     			str+="<td align='center'>" + item.getElementsByTagName("scadacnt")[0].firstChild.nodeValue + "</td>"
	     			str+="<td align='center'>" + item.getElementsByTagName("wecCount")[0].firstChild.nodeValue + "</td>"
	     			str+="<td align='center'> <input type='checkbox' id='checkPublish"+I+"'/></td>"
	     			// str+="<td><input type='image' src='<%=request.getContextPath()%>/resources/images/submitbtn.gif' alt='Click to publish the data'"	 
	     		}
	     		else
	     		{
	     			str+="<td align='center'>NA</td>"
	     			str+="<td align='center'>" + item.getElementsByTagName("scadacnt")[0].firstChild.nodeValue + "</td>"
	     			str+="<td align='center'>" + item.getElementsByTagName("wecCount")[0].firstChild.nodeValue + "</td>"
	     			str+="<td align='center'> <input type='hidden' id='checkPublish"+I+"'/></td>"
	     			// str+="<td><input type='image' src='<%=request.getContextPath()%>/resources/images/submitbtn.gif' alt='data cannot be published' disabled='disabled'"	 
	     		}
	     		
	     		str+="<td><input type='image' src='<%=request.getContextPath()%>/resources/images/submitbtn.gif' alt='Click to publish the data'"	 
	     		
	     		// str+="<td align='center'>" + item.getElementsByTagName("scadadata")[0].firstChild.nodeValue + "</td>" 
	     		// str+="<td align='center'>" + item.getElementsByTagName("scadacnt")[0].firstChild.nodeValue + "</td>"
	     		// str+="<td align='center'> <input type='checkbox' id='checkPublish"+I+"'/></td>" 		
	     		
	     		//str+="<td><input type='image' src='<%=request.getContextPath()%>/resources/images/submitbtn.gif' alt='Click to publish the data'"	 
	     		str+="onClick=uploadScadaWECData('" + item.getElementsByTagName("stateid")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("statedesc")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("areaid")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("areadesc")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("siteid")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("sitedesc")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("readingdate")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("scadadata")[0].firstChild.nodeValue + "','"+I+"')></td></tr>"    		
	     		// str+="onClick=publishWECData('" + item.getElementsByTagName("siteid")[0].firstChild.nodeValue+ "')></td></tr>"    		
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str;
		
		document.getElementById("progressbar").innerHTML = "";
}

function uploadScadaWECData(stateid,statedesc,areaid,areadesc,siteid,sitedesc,readingdate,scadadata,i)
{ 	
	 var flag=0;	 
	 
	 if(document.getElementById('checkPublish'+i).checked==true)
	 	flag=1;	 
	 var userId = <%=userId%>;
	 var sid = stateid+","+statedesc+","+areaid+","+areadesc+","+siteid+","+sitedesc+","+readingdate+","+scadadata+","+flag+","+userId;
	 
	 var req = newXMLHttpRequest();
     var ApplicationId = sid;
     updateWEC =  sid;
     // alert(ApplicationId);   
     
	 req.onreadystatechange = getReadyStateHandler(req, showPublishedData);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=uploadScadaWECData&AppId="+ApplicationId);
}
function showPublishedData(dataXml)
{
	var cart = dataXml.getElementsByTagName("publishedData")[0];
	var items = cart.getElementsByTagName("msg")[0].firstChild.nodeValue;	
	var updateflag = cart.getElementsByTagName("updateflag")[0].firstChild.nodeValue;	
	displayScadaWECDataToUpload();
	if(updateflag=="0")
	{
		alert(items);	
	}
	else
	{
		if(confirm(items))
		{
			var req = newXMLHttpRequest();
			var ApplicationId = updateWEC;
			req.onreadystatechange = getReadyStateHandler(req, showUpdatedData);
			req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
			req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			req.send("Admin_Input_Type=updateScadaWECData&AppId="+ApplicationId);				
		}			
	}	
}
function showUpdatedData(dataXml)
{
	var cart = dataXml.getElementsByTagName("updatedData")[0];
	var items = cart.getElementsByTagName("msg")[0].firstChild.nodeValue;
	displayScadaWECDataToUpload();
	alert(items);
}
/* function checkRecord(siteid,readingdate)
{	
    -----checkRecord('" + item.getElementsByTagName("siteid")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("readingdate")[0].firstChild.nodeValue + "'),-----
	var req = newXMLHttpRequest();
	var ApplicationId = siteid+","+readingdate;
	alert(ApplicationId);
	req.onreadystatechange = getReadyStateHandler(req, showCheckRecord);
	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=checkScadaWECRecord&AppId="+ApplicationId);
}
function showCheckRecord(dataXml)
{
	var cart = dataXml.getElementsByTagName("checkscadarecord")[0];
	var items = cart.getElementsByTagName("msg")[0].firstChild.nodeValue;	
	displayScadaWECDataToUpload();	
	alert(items);
}
*/

function findvalidate() 
{
	var blnSave=false;
     blnSave = validateForm('Site',document.forms[0].SiteIdtxt.value,'M','',
                            'Date',document.forms[0].Datetxt.value,'M','');
     if ( blnSave == true ) {
     	blnSave = futuredate();
		if (blnSave == true)
		{
			return true;
		}else
			return false;
     } else {
       return false;
     }
}
function futuredate()
{
	 var todate=new Date(); //yyyy-mm-dd
   	 var fromdate = new Date(document.getElementById("Datetxt").value.substr(6,4),document.getElementById("Datetxt").value.substr(3,2) -1,document.getElementById("Datetxt").value.substr(0,2));
	 var one_day=1000*60*60*24;	
     var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day));	
     if(days<=0)
  	 {
	  	 alert("Future date record are not allowed!");
	  	 return false;
  	 }else
  	 	return true;
}
</script>
</head>
<body>

<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/UploadWECScadaData.do" method="post" name="UploadWECScadaData" onSubmit="return findvalidate()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Upload WEC Data</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
			<td class="bgcolor">
				<select size="1" id="SiteIdtxt" name="SiteIdtxt" class="ctrl">
		            <option value="">-----Make a Selection------</option>
		            <%=sState %>
		        </select>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.UploadWECScadaData.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="button" name="Submitcmd" class="btnform" value="Generate" onClick="displayScadaWECDataToUpload()"/>
			<input type="hidden" name="Admin_Input_Type" value="UploadWECScadaData" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>	
</td>		
</tr>
</table>
<DIV id=progressbar style="DISPLAY: none" align="center"><IMG style="VERTICAL-ALIGN: bottom" 
 		src="<%=request.getContextPath()%>/resources/images/progressbar.gif"><Font size=4> Please wait...downloading data</Font>
</DIV>	
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
<tbody><tr>		
				<td align="center">
					
					<div id="areadetails">	</div>	
				</td>
			</tr>
			</tbody>
		</table>	

</div>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>