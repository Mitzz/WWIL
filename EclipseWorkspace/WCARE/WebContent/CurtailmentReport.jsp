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
	// String userid = session.getAttribute("loginID").toString();
	String Stateidtxt = "";	
	String stateid = "";	
	stateid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER", Stateidtxt);	
%>
<script type="text/javascript">
function loadExternal(url) {
  	// document.getElementById("progressbar").style.display ="";
    
    var rd;	
    var rd1;	
    
    url="CurtailmentAdminReport.jsp";    
   	 
     rd=document.forms[0].FromDatetxt.value;
     rd1=document.forms[0].ToDatetxt.value;
     var type='D';
     
   	 var list2=document.forms[0].CustIdtxt;
				
	  url = url+"?id="+list2.options[list2.selectedIndex].value+"&rd="+rd+"&rd1="+rd1;
	  window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');  
}     
</script>
</head>
<body valign="top" align="center">

<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
	<tr width="100%">
		<td width="100%" align="center">
		<form action="<%=request.getContextPath()%>/CURTAILMENTREPORT.do" method="post" name="MPRREPORT" body leftmargin="0"
			topmargin="0">
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
			<tbody>
				<tr>
					<td class="newhead1"></td>
					<th class="headtext">Curtailment Report</th>
					<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"
						alt=""></td>
					<td class="newhead3">&nbsp;</td>
					<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1"
						width="10" alt=""></td>
				</tr>
				<tr>
					<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" alt=""></td>
					<td colspan="3"><img
						src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"
						height="10" width="1" alt=""><br>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td bgcolor="#dbeaf5">
								<table border="0" cellpadding="2" cellspacing="1" width="100%">
									<tbody>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
											<td class="bgcolor"><select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl">												
												<%=stateid%>
											</select></td>
										</tr>
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
					<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10" alt=""></td>
					<td colspan="4" align="right" bgcolor="#006633">
					<table border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td class="btn" width="100"><input type="button" name="Searchcmd" class="btnform" value="Generate"  onclick="loadExternal('ur')"/></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1" alt=""></td>
								<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1" alt=""></td>
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
