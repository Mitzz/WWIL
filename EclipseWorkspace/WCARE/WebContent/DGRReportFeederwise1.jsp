<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.enercon.security.utils.SecurityUtils;"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"> </script>

<% 

response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

String custid ="";
int k=0;
StringBuffer buffer = new StringBuffer();
SecurityUtils secUtil = new SecurityUtils();
String got="0";
 	if(session.getAttribute("LoginType").equals("E"))
		 custid= AdminUtil.fillMaster("VIEW_SUBSTATION_MASTER",Customeridtxt);
 	else
 	{
 		
 	}
%>
<script type="text/javascript">

function loadExternal(url) {
	
	document.getElementById("progressbar").style.display ="";

   var right_now=new Date();
   var the_year=right_now.getYear();
   var the_month=right_now.getMonth()+1;

   
  
    var type=document.forms[0].Type.value;
    var rd;	
    
    url="DGRSubstationView.jsp";
    if(type=="M" || type=="DM" )
     {    
     var list3=document.forms[0].Monthtxt;
     var list4=document.forms[0].Yeartxt;
     
     if (list4.options[list4.selectedIndex].value == the_year && list3.options[list3.selectedIndex].value > the_month)
		{
			alert("Please check the month of your request.");
			return (false);
		}     
     	rd="01/"+list3.options[list3.selectedIndex].value+"/"+list4.options[list4.selectedIndex].value;
     }
   	 if(type=="Y" || type=="YR")
     {
       var list5=document.forms[0].FiscalYeartxt;
       rd=list5.options[list5.selectedIndex].value;
      
     }
     if(type=="D" || type=="DG")
     {
        rd=document.forms[0].Datetxt.value;
        
     	var pdate=rd;     	
     	var cdate = document.forms[0].todaydate.value;;    
     	
	 	var todate=new Date(cdate.substr(6,4),cdate.substr(3,2) -1,cdate.substr(0,2)); //yyyy-mm-dd
   	 	var fromdate = new Date(pdate.substr(6,4),pdate.substr(3,2) -1,pdate.substr(0,2));
	 	var one_day=1000*60*60*24;	
    	var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day) );	
      
     	if(days<0)
  	 		{
	  	 		alert("Please check the date of your request.");
	  	 		return false;
  	 		}
  	 		
     }
 
   	
   	if(url !=""){   	
	
	 	if (window.frames['ifrm'] ) {
	 	
				var list2=document.forms[0].CustIdtxt;
				
	   					  window.frames['ifrm'].location = url+"?id="+list2.options[list2.selectedIndex].value+"&rd="+rd+"&type="+type;
	   					  // window.open(url+"?id="+list2.options[list2.selectedIndex].value+"&rd="+rd+"&type="+type);
	   					   
	   					  return false;
	 			 } else if ( document.layers ) {
	  				  document.layers['outer'].document.layers['inner'].src = url;
	  				   
	    			return false;
	  			} else return true;
			 }			 	
		}
</script>

<script type="text/javascript">

function checkWholeForm() {
    var why = "";
     	why += checkDropdown(document.forms[0].CustIdtxt.selectedIndex);
     	if(document.forms[0].Type.value =="D" || document.forms[0].Type.value =="DG"){
     	var ss=document.DGRREPORT.Datetxt;
     	
     	why += checkUsername(ss);
     	}else if(document.forms[0].Type.value =="M" || document.forms[0].Type.value =="DM"){
     	why += checkDropdown(document.forms[0].Monthtxt.selectedIndex);
     	 why += checkDropdown(document.forms[0].Yeartxt.selectedIndex);
     	}else{
     	 why += checkDropdown(document.forms[0].FiscalYeartxt.selectedIndex);
     	
     	}
    if (why != "") {
       alert(why);
       return false;
    }
    else
    {
    loadExternal('url');
    
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
<script type="text/javascript">

/***********************************************
* IFrame SSI script II- � Dynamic Drive DHTML code library (http://www.dynamicdrive.com)
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
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Daily Generation Report</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<%

//System.out.println("Type"+request.getParameter("Type"));
String Type=request.getParameter("Type");


int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String rdate = dateFormat.format(date.getTime()); 

%>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>	
		<%if(got.equals("0")){ %>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
				<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl" >
		            <option value="">--Make a Selection--</option>
		            <%=custid %>
		        </select>
			</td>
		</tr>
		<%}else{ %>
			
		<tr class="bgcolor"> 
		
			<td id="t_street_address">Customer:</td>
			<td class="bgcolor">
			<select size="1" id="CustIdtxt" name="CustIdtxt"  class="ctrl" font:color="Black" disabled>
		           <option value="">--Make a Selection--</option>
		           <%=custid %>
		    </select>	
			</td>
			
		</tr>
		
		<script>
			document.forms[0].CustIdtxt.selectedIndex=1;
		</script>
	 
	 <% 
	 System.out.println(k);
	 if(k<2){ %>
	 	<script>
	 	
	 	document.getElementById("spancust").style.visibility=hidden;	 	
	 	document.getElementById("spancust").style.display=none;
		
		</script>
	  <%}} %>	
	<% if(Type.equals("D") || Type.equals("DG")){	%>			
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" value="<%=dateFormat.format(date.getTime())%>" size="20" class="ctrl" maxlength="10"  onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.DGRREPORT.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>
	<% }else if(Type.equals("M") || Type.equals("DM")){	%>		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Month:</td>
			<td class="bgcolor">
				<select size="1" id="Monthtxt" name="Monthtxt" class="ctrl">
		             <option value="0">--Make a Selection--</option>
		             <option value="01">JANUARY</option>
		             <option value="02">FEBRUARY</option>
		             <option value="03">MARCH</option>
		             <option value="04">APRIL</option>
		             <option value="05">MAY</option>
		             <option value="06">JUNE</option>
		             <option value="07">JULY</option>
		             <option value="08">AUGUST</option>
		             <option value="09">SEPTEMBER</option>
		             <option value="10">OCTOBER</option>
		             <option value="11">NOVEMBER</option>
		             <option value="12">DECEMBER</option>
		            
		        </select>
		       Select Year:
		        	<select size="1" id="Yeartxt" name="Yeartxt" class="ctrl">
		            <option value="0">--Make a Selection--</option>
		             <option value="2013">2013</option>
		             <option value="2012">2012</option>
		             <option value="2011">2011</option>
		             <option value="2010">2010</option>
		             <option value="2009">2009</option>
		             <option value="2008">2008</option>
		             <option value="2007">2007</option>
		             <option value="2006">2006</option>
		             <option value="2005">2005</option>
		           
		        </select>
				
			</td>
		</tr> 
		<% }else if(Type.equals("Y")|| Type.equals("YR")){	%>	
		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Fiscal-Year:</td>
			<td class="bgcolor">
				
		        	<select size="1" id="FiscalYeartxt" name="FiscalYeartxt" class="ctrl">
		            <option value="0">--Make a Selection--</option>
		             <option value="01/05/2012">2012-2013</option>
		             <option value="01/05/2011">2011-2012</option>
		             <option value="01/05/2010">2010-2011</option>
		             <option value="01/05/2009">2009-2010</option>
		             <option value="01/05/2008">2008-2009</option>
		             <option value="01/05/2007">2007-2008</option>
		             <option value="01/05/2006">2006-2007</option>
		             <option value="01/05/2005">2005-2006</option>
		             <option value="01/05/2004">2004-2005</option>
		           
		        </select>
				
			</td>
		</tr>
		<% }%>
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>
			</td>
			 <input type="hidden" name="Type" id="Type" value=<%=Type%>>
			 <input type="hidden" name="todaydate" id="todaydate" value=<%=rdate%>>
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
			<input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="checkWholeForm();closePBar()" />
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
</div>
<DIV id=progressbar style="DISPLAY: none" align="center"><IMG style="VERTICAL-ALIGN: bottom" 
 src="<%=request.getContextPath()%>/resources/images/progressbar.gif"><Font size=4> Please wait...downloading data
</Font></DIV>	
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