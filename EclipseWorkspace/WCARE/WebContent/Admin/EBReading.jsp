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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<%
//String si = "";
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
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();
String sSite="";
System.out.println(roleid);
if(roleid.equals("0000000001"))
 sSite = AdminUtil.fillMaster("VIEW_SITE_MASTER",sSite);
else
	sSite = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",userid);
%>
<script type="text/javascript">
function findSearch() 
{
	var blnSave=false;
     blnSave = validateForm('Site',document.forms[0].SiteIdtxt.value,'M','',
                            'Date',document.forms[0].Datetxt.value,'M','');
     if ( blnSave == true ) {
     	blnSave = futuredate();
		if (blnSave == true)
		{
    	//window.open("EBEntryForm.jsp?siteid="+document.getElementById("SiteIdtxt").value+"&date="+document.getElementById("Datetxt").value+"&type="+document.getElementById("Typetxt").value,'EBEntry','height=725,width=1250,top=0,resizable=no,scrollbars=yes,menubar=no,toolbar=no,status=no');
    		window.open("EBDataEntryDisplay.jsp?siteid="+document.getElementById("SiteIdtxt").value+"&date="+document.getElementById("Datetxt").value+"&type="+document.getElementById("Typetxt").value,'EBEntry','height=725,width=1250,top=0,resizable=no,scrollbars=yes,menubar=no,toolbar=no,status=no');
    	}
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
function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].SiteIdtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
		var divdetails = document.getElementById("ebdetails");
		divdetails.innerHTML = "";
		var str = "<hr><table width='300' cellspacing='0' cellpadding='0'>"
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='200'>EB Description</th><th class='detailsheading' width='100'>Short Description</th>"
		str +="<th class='detailsheading' width='30'>Staus</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("ebname")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("ebshort")[0].firstChild.nodeValue + "</td>"	  
	     		//if (item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue == 1){
    	 			//str+="<td align='left'><input type='checkbox' id='approved"+I+"' name='approved' "
		     		//str+="onclick=updateEBStatus('" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"',"
		     		//str+="approved"+I+") style='text-align:right' /></td></tr>"
		     		if(item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue == 1)
		     		{
		     			str+="<td align='left'>Active</td></tr>"
		     		}else
		     		{
		     			str+="<td align='left'>DeActive</td></tr>"
		     		}
    	 		//}
    	 		//else{
    	 		//	str+="<td align='left'><input type='checkbox' id='approved"+I+"' name='approved' "
		     	//	str+="onclick=updateEBStatus('" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue+"',"
		     	//	str+="approved"+I+") style='text-align:right' checked /></td></tr>"
    	 		//}
	   	 	}   	 	
		}		
		divdetails.innerHTML = str;
}
function updateEBStatus(ebid,chkbox) 
{
	 var req = newXMLHttpRequest();
     var ApplicationId = document.getElementById(chkbox.id).checked + "," + ebid;
	 req.onreadystatechange = getReadyStateHandler(req, UpdateEBStat);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=SU_UpdateEBStat&AppId="+ApplicationId);
}
function UpdateEBStat(dataXml)
{

}


</script>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/EBParameter.do" method="post" name="EBParameter">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">EB Reading</th>
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
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
				<select size="1" id="SiteIdtxt" name="SiteIdtxt" class="ctrl" onChange="findApplication()">
		            <option value="">--Make a Selection--</option>
		            <%=sSite %>
		        </select>
			</td>
		</tr>				
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.EBParameter.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>			
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Reading&nbsp;Type:</td>
			<td class="bgcolor">
				<select size="1" id="Typetxt" name="Typetxt" class="ctrl" >
		            <option value="I">Initial</option>		            
		            <option value="D" selected="selected">Daily</option>
		             <option value="MI">Monthly Initial</option>
		            <option value="M">Monthly</option>
		            <option value="Y">Yearly</option>
		        </select>
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
			<input type="button" name="Searchcmd" class="btnform" value="Submit" onclick="findSearch()" />
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
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="500"><tbody>
			<tr>
				<td >
					<div id="ebdetails">
					
					</div>	
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