<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@page import="com.enercon.security.utils.SecurityUtils;"%>
<html>
<head>
<%
	if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"> </script>

<%
	//String dd = "";
	//session.removeAttribute("dynabean");
	//DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
	//if(dynabean != null){		
	//	dd = dynabean.getProperty("Datetxt").toString();
	//	si = dynabean.getProperty("SiteIdtxt").toString();
	//	session.removeAttribute("dynabean");
	//}
%>
<%
response.getOutputStream().flush();
response.getOutputStream().close();
String userid = session.getAttribute("loginID").toString();
String Customeridtxt = "";

String custid = "";
int k = 0;
StringBuffer buffer = new StringBuffer();
SecurityUtils secUtil = new SecurityUtils();
String noOfCustomers = "0";
if (session.getAttribute("LoginType").equals("E")) {
	custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER", Customeridtxt);
} 
else {
	List tranList1 = new ArrayList();
	tranList1 = (List) session.getAttribute("custtypee");
	if (tranList1.size() == 0) {
		tranList1 = secUtil.getcustomerdetails(userid);
	}

	//System.out.println("custtypee"+session.getAttribute("custtypee")); 
	//System.out.println("tranList1.size():"+tranList1.size());
	if (tranList1.size() == 1) {
		noOfCustomers = "1";
	}

	//System.out.println(tranList1.size());
	for (int j = 0; j < tranList1.size(); j++) {
		Vector v11 = new Vector();
		v11 = (Vector) tranList1.get(j);
		String customerid = (String) v11.get(2);
		String customername = (String) v11.get(3);
		//System.out.println("customername"+customername);
		buffer.append("<OPTION VALUE='" + customerid + "' >" + customername + "</OPTION>");
		k++;
	}
	custid = buffer.toString();
	//System.out.println("cc"+custid);
}
%>
<script type="text/javascript">
function loadExternal(url) {
	document.getElementById("progressbar").style.display = "";

   	var currentDate = new Date();
   	var currentYear = currentDate.getYear();
   	var currentMonth = currentDate.getMonth() + 1;

   	var type=document.forms[0].Type.value;
    var requestDate;	
    
    url="DGRView.jsp";
    
    if(type=="D" || type=="DG"){
		requestDate = document.forms[0].Datetxt.value;
     	var pdate = requestDate;
     	var cdate = document.forms[0].todaydate.value;;    
     	var todate = new Date(cdate.substr(6,4),cdate.substr(3,2) -1,cdate.substr(0,2)); //yyyy-mm-dd
   	 	var fromdate = new Date(pdate.substr(6,4),pdate.substr(3,2) -1,pdate.substr(0,2));
	 	var one_day=1000*60*60*24;	
    	var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day) );	
     	if(days < 0){
			alert("Please check the date of your request.");
	  	 	return false;
		}
	}
   	if(url !=""){
	 	if (window.frames['ifrm'] ) {
			var list2 = document.forms[0].CustIdtxt;
			window.frames['ifrm'].location = url+"?id="+list2.options[list2.selectedIndex].value+"&rd="+requestDate + "&type="+type;
	   					  // window.open(url+"?id="+list2.options[list2.selectedIndex].value+"&rd="+rd+"&type="+type);
			return false;
		} 
	 	else if ( document.layers ) {
			document.layers['outer'].document.layers['inner'].src = url;
			return false;
		} 
	 	else return true;
	}			 	
}
</script>
<script type="text/javascript">
function checkWholeForm() {
	var why = "";
    why += checkIfCustomerIsSelected(document.forms[0].CustIdtxt.selectedIndex);
	if(document.forms[0].Type.value == "D" || document.forms[0].Type.value == "DG"){
    	var dateTxt=document.DGRREPORT.Datetxt;
     	why += checkIfDateIsSelected(dateTxt);
	}
    if (why != "") {
       alert(why);
       return false;
    }
    else{
    	loadExternal('url');
	    return true;
    }
    
    function checkIfCustomerIsSelected(choice){
    	var error = "";
        if (choice == 0) {
           error = "You didn't choose an option from the drop-down list.\n";
        }    
    	return error;
    }
    
    function checkIfDateIsSelected(date){
    	var dateValue = date.value; 
    	var error = "";
     	if (dateValue == '') {
    		error = "You didn't enter a Date.\n";
    	}
     	return error;
    }
    
}
</script>
<script type="text/javascript">

/***********************************************
* IFrame SSI script II- © Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
* Visit DynamicDrive.com for hundreds of original DHTML scripts
* This notice must stay intact for legal use
***********************************************/

//Input the IDs of the IFRAMES you wish to dynamically resize to match its content height:
//Separate each ID with a comma. Examples: ["myframe1", "myframe2"] or ["myframe"] or [] for none:
var iframeids=["ifrm"]

//Should script hide iframe from browsers that don't support this script (non IE5+/NS6+ browsers. Recommended):
var iframehide="yes"

var getFFVersion=navigator.userAgent.substring(navigator.userAgent.indexOf("Firefox")).split("/")[1]
var FFextraHeight=parseFloat(getFFVersion)>=0.1? 16 : 0 //extra height in px to add to iframe in FireFox 1.0+ browsers

function resizeCaller() {
var dyniframe=new Array()
for (i=0; i<iframeids.length; i++){
if (document.getElementById)
resizeIframe(iframeids[i])
//reveal iframe for lower end browsers? (see var above):
if ((document.all || document.getElementById) && iframehide=="no"){
var tempobj=document.all? document.all[iframeids[i]] : document.getElementById(iframeids[i])
tempobj.style.display="block"
}
}
}

function resizeIframe(frameid){
var currentfr=document.getElementById(frameid)
if (currentfr && !window.opera){
currentfr.style.display="block"
if (currentfr.contentDocument && currentfr.contentDocument.body.offsetHeight) //ns6 syntax
currentfr.height = currentfr.contentDocument.body.offsetHeight+FFextraHeight; 
else if (currentfr.Document && currentfr.Document.body.scrollHeight) //ie5+ syntax
currentfr.height = currentfr.Document.body.scrollHeight;
if (currentfr.addEventListener)
currentfr.addEventListener("load", readjustIframe, false)
else if (currentfr.attachEvent){
currentfr.detachEvent("onload", readjustIframe) // Bug fix line
currentfr.attachEvent("onload", readjustIframe)
}
}
}

function readjustIframe(loadevt) {
var crossevt=(window.event)? event : loadevt
var iframeroot=(crossevt.currentTarget)? crossevt.currentTarget : crossevt.srcElement
if (iframeroot)
resizeIframe(iframeroot.id);
}

function loadintoIframe(iframeid, url){
if (document.getElementById)
document.getElementById(iframeid).src=url
}

if (window.addEventListener)
window.addEventListener("load", resizeCaller, false)
else if (window.attachEvent)
window.attachEvent("onload", resizeCaller)
else
window.onload=resizeCaller




</script>


</head>
<body  valign="top"  align="center"  onLoad="loadintoIframe('ifrm','Blank.jsp')" style="overflow: hidden">
	<div align="center">
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
			<tr width="100%">
				<td width="100%" align="center">
					<form action="<%=request.getContextPath()%>/DGRREPORT.do" method="post" name="DGRREPORT" body leftmargin="0" topmargin="0" >
						<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
							<tbody>
								<tr>
									<td class="newhead1"></td>
									<th class="headtext">Daily Generation Report</th>
									<td>
										<img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10">
									</td>
									<td class="newhead3">&nbsp;</td>
									<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
								</tr>
<%
//System.out.println("Type"+request.getParameter("Type"));
String Type = request.getParameter("Type");

int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String rdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
%>
								<tr>
									<td class="newheadl">
										<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" />
									</td>
									<td colspan="3">
										<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1" />
										<br>
										<table border="0" cellpadding="0" cellspacing="0" width="100%">
											<tbody>
												<tr>
													<td bgcolor="#dbeaf5">
														<table border="0" cellpadding="2" cellspacing="1" width="100%">
															<tbody>	
			<%
if (noOfCustomers.equals("0")) {
			%>
																<tr class="bgcolor"> 
																	<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
																	<td class="bgcolor">
																		<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl" >
																            <option value="">--Make a Selection--</option>
																            <%=custid%>
																        </select>
																	</td>
																</tr>
			<%
} 
else {
			%>
			
																<tr class="bgcolor"> 
																	<td id="t_street_address">Customer:</td>
																	<td class="bgcolor">
																	<select size="1" id="CustIdtxt" name="CustIdtxt"  class="ctrl" font:color="Black" disabled>
																           <option value="">--Make a Selection--</option>
																           <%=custid%>
																    </select>	
																	</td>
																</tr>
		
																<script>
																	document.forms[0].CustIdtxt.selectedIndex=1;
																</script>
	<%
	System.out.println(k);
	if (k < 2) {
	%>
																<script>
																 	document.getElementById("spancust").style.visibility=hidden;
																 	document.getElementById("spancust").style.display=none;
																</script>
	<%
	}
}
	%>	
	<%
if (Type.equals("D") || Type.equals("DG")) {
	%>			
																<tr class="bgcolor"> 
																	<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
																	<td class="bgcolor">
																		<input type="text" name="Datetxt" id="Datetxt" value="<%=rdate%>" size="20" class="ctrl" maxlength="10"  onfocus="dc.focus()" />
																		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.DGRREPORT.Datetxt);return false;" >
																			<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="" />
																		</a>
																	</td>
																</tr>
	<%
} 
	%>
																<tr class="bgcolor"> 
																	<td colspan="2">
																		<html:errors />
				<%
String str = (String) session.getAttribute("msg");
				%>
				<%
if (str != null) {
				%>
				<%=str%>
				<%
}
				%>
				<%
session.setAttribute("msg", "");
				%>
																		<input type="hidden" name="Type" id="Type" value=<%=Type%>>
																	 	<input type="hidden" name="todaydate" id="todaydate" value=<%=rdate%>>
																	</td>			 
																</tr>	
															</tbody>
														</table>
													</td>
												</tr>
											</tbody>
										</table>
										<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1" />
										<br>
									</td>
									<td class="newheadr">
										<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0">
									</td>
								</tr>
								<tr>
									<td width="10">
										<img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10" />
									</td>
									<td colspan="4" align="right" bgcolor="#006633">
										<table border="0" cellpadding="0" cellspacing="0">
										<tbody>
											<tr>
												<td class="btn" width="100">
													<input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="checkWholeForm();" />
												</td>
												<td width="1">
													<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
												</td>
												<td class="btn" width="100">
													<input name="Reset" value="Cancel" class="btnform" type="reset">
												</td>
												<td width="1">
													<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</form>	
			</td>		
		</tr>
	</table>
</div>
<div id=progressbar style="DISPLAY: none" align="center">
	<IMG style="VERTICAL-ALIGN: bottom" src="<%=request.getContextPath()%>/resources/images/progressbar.gif">
		<Font size=4> Please wait...downloading data</Font>
</div>	
<table  name="tblifrm"  id=="tblifrm" width="700"  valign="top" align="center" >
	<tr >
		<td>
			<iframe id="ifrm" name="ifrm" src="" width="100%"  valign="top" align="center" scrolling="no" frameborder="0"></iframe> 
		</td>
	</tr>
</table>	  
</body>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>