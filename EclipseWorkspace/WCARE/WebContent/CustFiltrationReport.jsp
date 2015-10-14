<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="com.enercon.admin.dao.*"%>
<%@ page import="java.math.BigDecimal"%>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"> </script>

<%
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Stateidtxt = "";
	String Sitetxt = "";
	String stateid = "";
	
%>

<script type="text/javascript">
function checkWholeForm(){

    var why = "";
    var sel = "";
    var asel = "";
    var act = "";
           why += checkUsername(document.forms[0].FromDatetxt,"From Date");
     	
     	   why += checkUsername(document.forms[0].ToDatetxt,"To Date");
     	   if(document.forms[0].elements["FilerObject"].disabled==false)
     	   {
     	   why += checkUsername(document.forms[0].txtFirstParam,"Fill Up text box");
     	   }
     	   if(document.forms[0].FilterTypetxt.selectedIndex==3)
     	     why += checkUsername(document.forms[0].txtSecondParam);
        if (why != "") {
                     alert(why);
                       return false;
                   }
                   
                    if (act != "") {
                     alert(act);
                       return false;
                   }
    else
    {
    
    popUp();
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
function checkUsername(strng,s) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "You didn't enter  "+s+".\n";
 		}
 		return error;
 }    
 
 
       
  

<!-- Begin
function popUp() {



	
	var fdate=document.forms[0].FromDatetxt.value;
	var tdate=document.forms[0].ToDatetxt.value;
	
	
	
     var fobject = document.forms[0].FilerObject.selectedIndex;
     var fobjectdesc;
     
     
      if(document.forms[0].elements["FilerObject"].disabled==true)
     	   {
     			fobjectdesc="NA";
     		}
        else
     		{
     		
     		    fobjectdesc = document.forms[0].FilerObject.options[fobject].value;
     		}
     		
      var ftype = document.forms[0].FilterTypetxt.selectedIndex;
      var ftypedesc = document.forms[0].FilterTypetxt.options[ftype].value;
     var firstparam= document.forms[0].txtFirstParam.value;
     	  
     var secondparam= document.forms[0].txtSecondParam.value;
       var ebtype = document.forms[0].EBObject.selectedIndex;
        var ebobject;
       if(document.forms[0].elements["EBObject"].disabled==true)
     	   {
     	     ebobject ="NA";
     	   }
     	   else
     	   {
             ebobject = document.forms[0].EBObject.options[ebtype].value;
           }
    
	location.href="CustFilterReportExcel.jsp?fdate="+fdate+"&tdate="+tdate+"&fobjectdesc="+fobjectdesc+"&ftypedesc="+ftypedesc+"&firstparam="+firstparam+"&secondparam="+secondparam+"&ebobject="+ebobject;
	
//day = new Date();
//id = day.getTime();
//eval("page" + id + " = window.open(url, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=200,height=5,left = 262,top = 234');");
}

  
function toggle() {       

            
              document.forms[0].elements["FilerObject"].disabled=true;
               document.forms[0].elements["EBObject"].disabled=false;
 } 
 
 function toggle1() { 
     
 document.forms[0].elements["FilerObject"].disabled=false;
               document.forms[0].elements["EBObject"].disabled=true;
				
   
 } 
 function toggle2() {       

              document.forms[0].elements["FilerObject"].disabled=false;
               document.forms[0].elements["EBObject"].disabled=false;
 } 
</script> 

</head>
<body valign="top" align="center">

<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
	<tr width="100%">
		<td width="100%" align="center">
		<form action="<%=request.getContextPath()%>/DGRREPORT.do" method="post" name="MPRREPORT" body leftmargin="0"
			topmargin="0">
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
			<tbody>
				<tr>
					<td class="newhead1"></td>
					<th class="headtext">Filteration Report</th>
					<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"
						alt=""></td>
					<td class="newhead3">&nbsp;</td>
					<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1"
						width="10" alt=""></td>
				</tr>

				<tr>
					<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" alt=""></td>
					<td colspan="3"><img
						src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"
						height="10" width="1" alt=""><br>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td bgcolor="#dbeaf5">
								<table border="0" cellpadding="2" cellspacing="1" width="100%">
									<tbody>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
											<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl"
												maxlength="10" onfocus="dc.focus()" /> <a href="javascript:void(0)" id="dc"
												onClick="if(self.gfPop)gfPop.fPopCalendar(document.MPRREPORT.FromDatetxt);return false;"><img
												class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
												border="0" alt=""></a></td>
										</tr>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
											<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl"
												maxlength="10" onfocus="dc.focus()" /> <a href="javascript:void(0)" id="dc"
												onClick="if(self.gfPop)gfPop.fPopCalendar(document.MPRREPORT.ToDatetxt);return false;"><img
												class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
												border="0" alt=""></a></td>
										
										<tr class="bgcolor">
											<td id="t_street_address">Evaluation base</td>
											<td class="bgcolor">
											<input type="radio" name="id1" value="Dual" checked onClick="toggle2()"> Both<input type="radio" name="id1" value="Avail" onClick="toggle1()">Availability<input name ="id1" type="radio" value="Pre" onClick="toggle()">Maintenance Activity
											</td>
										</tr>
										
										
										<tr class="bgcolor">
											<td id="t_street_address"><select size="1" id="FilerObject" name="FilerObject" class="tabtextnormal">
												<option value="MA">MACHINE AVAILABILITY</option>
												<option value="GA">GRID AVAILABILITY</option>
												<option value="CF">CAPACITY FACTOR</option>
										</select></td>
											<td class="bgcolor"><select size="1" id="FilterTypetxt" name="FilterTypetxt" class="tabtextnormal">
												<option value="EQ">=</option>
												<option value="GT">></option>
												<option value="LT"><</option>
												<option value="BT"><></option>
												
											</select>&nbsp;&nbsp;<input type="text" name="txtFirstParam" id="txtFirstParam"  size="5" maxlength="3">&nbsp;&nbsp;<input type="text" name="txtSecondParam" id="txtSecondParam" size="5" maxlength="3">
											
											
											
											</td>
										</tr>
										
										
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;Maintenance Activity:</td>
											<td class="bgcolor">
											<select size="1" id="EBObject" name="EBObject" class="tabtextnormal">
												<option value="PM">PM/CM</option>
												<option value="BD">BD</option>
											</select></td>
										</tr>
										

									</tbody>
								</table>
								</td>
							</tr>
						</tbody>
					</table>
					<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1" alt=""><br>
					</td>
					<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" alt=""></td>
				</tr>
				<tr>
					<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20"
						width="10" alt=""></td>
					<td colspan="4" align="right" bgcolor="#006633">
					<table border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td class="btn" width="100"><input type="button" name="Searchcmd" class="btnform" value="Generate"
									onclick="checkWholeForm()" /></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18"
									width="1" alt=""></td>
								<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18"
									width="1" alt=""></td>
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

</body>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
	src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0"
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> </iframe>
</html>
