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
	String Sitetxt = "";
	String stateid = "";
	String WECTypetxt = "";
	stateid = AdminUtil.fillMaster("TBL_STATE_MASTER", Stateidtxt);
	String sitename = AdminUtil.fillWhereMaster("TBL_SITE_MASTER",
			Sitetxt, stateid);
	String wectype = AdminUtil.fillMaster("TBL_WEC_TYPE_CAPACITY",
			WECTypetxt);
%>

<script type="text/javascript">
function checkWholeForm() {
	//console.log("StateGenerationReport.jsp");
	var why = "";
	var sel = "";
	var asel = "";
	var act = "";
	//console.log(document.forms[0].StateIdtxt.selectedIndex);
	sel += checkDropdown(document.forms[0].StateIdtxt.selectedIndex);
	
	if (sel != "") {
		act = "Select state or WEC Type"
	}
	if (act != "") {
		asel += checkDropdown(document.forms[0].WECTypetxt.selectedIndex);
		if (asel != "") {
			act = "Select state or WEC Type"
		} else {
			act = "";
		}
	}
	why += checkUsername(document.forms[0].FromDatetxt);

	why += checkUsername(document.forms[0].ToDatetxt);

	if (why != "") {
		alert(why);
		return false;
	}

	if (act != "") {
		alert(act);
		return false;
	} else {

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
function checkUsername(strng) {
	var SDate = strng.value;

	var error = "";
	if (SDate == '') {
		error = "You didn't enter a Date.\n";
	}
	return error;
}

function popUp() {
	var list4 = document.forms[0].StateIdtxt;

	var state;

	if (list4 == 0) {
		state = "ALL";
	} else {
		state = list4.options[list4.selectedIndex].value;
	}

	var list3 = document.forms[0].WECTypetxt;

	var wec;

	if (list3 == 0) {
		wec = "ALL";
	} else {
		wec = list3.options[list3.selectedIndex].value;
	}

	var fdate = document.forms[0].FromDatetxt.value;
	var tdate = document.forms[0].ToDatetxt.value;

	var w = document.forms[0].StateIdtxt.selectedIndex;
	var sname = document.forms[0].StateIdtxt.options[w].text;

	var w1 = document.forms[0].Sitetxt.selectedIndex;
	var siteid;

	if (w1 == 0) {
		siteid = "ALL";
	} else {
		siteid = document.forms[0].Sitetxt.options[w1].value;
	}

	var eb = document.forms[0].EBIdtxt.selectedIndex;
	var ebid;

	if (eb == 0) {
		ebid = "ALL";
	} else {
		ebid = document.forms[0].EBIdtxt.options[eb].value;
	}

	var swes = document.forms[0].WECIdtxt.selectedIndex;
	var wecid;

	if (swes == 0) {
		wecid = "ALL";
	} else {
		wecid = document.forms[0].WECIdtxt.options[swes].value;
	}

	var url = "MPRExcel.jsp?stateid=" + state + "&wectype=" + wec + "&fdate="
			+ fdate + "&tdate=" + tdate + "&sname=" + sname + "&siteid="
			+ siteid + "&ebid=" + ebid + "&wecid=" + wecid;
	
	//console.log(url);
	day = new Date();
	id = day.getTime();
	eval("page"
			+ id
			+ " = window.open(url, '"
			+ id
			+ "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=400,height=10,left = 262,top = 234');");
}
function findSite() {

	var req = newXMLHttpRequest();
	var list = document.forms[0].StateIdtxt;
	var ApplicationId = list.options[list.selectedIndex].value;

	req.onreadystatechange = getReadyStateHandler(req, showSite);
	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=getSite&AppId=" + ApplicationId);
}

function showSite(dataXml) {
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].Sitetxt.options.length = 0;
	document.forms[0].Sitetxt.options[0] = new Option("--ALL--", "ALL");
	for ( var I = 0; I < items.length; I++) {
		var item = items[I]
		var nname = item.getElementsByTagName("sdesc")[0].firstChild;
		if (nname != null) {
			document.forms[0].Sitetxt.options[I + 1] = new Option(item
					.getElementsByTagName("sdesc")[0].firstChild.nodeValue,
					item.getElementsByTagName("sid")[0].firstChild.nodeValue);
		}
	}

}

function findApplication() {
	var req = newXMLHttpRequest();
	var list = document.forms[0].Sitetxt;
	var ApplicationId = list.options[list.selectedIndex].value;

	req.onreadystatechange = getReadyStateHandler(req, showAppDetails);

	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=findEBMaster&AppId=" + ApplicationId);
}
function showAppDetails(dataXml) {
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");
	document.forms[0].EBIdtxt.options.length = 0;
	document.forms[0].EBIdtxt.options[0] = new Option("--ALL-", "ALL");
	;
	for ( var I = 0; I < items.length; I++) {
		var item = items[I]
		var nname = item.getElementsByTagName("ebname")[0].firstChild;
		if (nname != null) {
			document.forms[0].EBIdtxt.options[I + 1] = new Option(item
					.getElementsByTagName("ebname")[0].firstChild.nodeValue,
					item.getElementsByTagName("ebid")[0].firstChild.nodeValue);
		}
	}
}

function findWec() {
	var req = newXMLHttpRequest();
	var list = document.forms[0].Sitetxt;
	var ApplicationId = list.options[list.selectedIndex].value;

	req.onreadystatechange = getReadyStateHandler(req, showWecDetails);

	req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	req.send("Admin_Input_Type=findWECMaster&AppId=" + ApplicationId);
}
function showWecDetails(dataXml) {
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	document.forms[0].WECIdtxt.options.length = 0;
	document.forms[0].WECIdtxt.options[0] = new Option("--ALL-", "ALL");
	for ( var I = 0; I < items.length; I++) {
		var item = items[I]
		var nname = item.getElementsByTagName("desc")[0].firstChild;
		if (nname != null) {
			document.forms[0].WECIdtxt.options[I + 1] = new Option(item
					.getElementsByTagName("desc")[0].firstChild.nodeValue, item
					.getElementsByTagName("wecid")[0].firstChild.nodeValue);
		}
	}
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
					<th class="headtext">Performance Report</th>
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
											<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
											<td class="bgcolor"><select size="1" id="StateIdtxt" name="StateIdtxt" class="ctrl"
												onchange="findSite()">
												<option value="ALL">--ALL--</option>
												<%=stateid%>
											</select></td>
										</tr>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
											<td class="bgcolor"><select size="1" name="Sitetxt" id="Sitetxt" class="ctrl"
												onChange="findApplication(),findWec()">
												<option value="selectone" selected="selected">-- Select Site --</option>

												<%=sitename%>
											</select></td>
										</tr>


								<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;WEC Type:</td>
											<td class="bgcolor"><select size="1" id="WECTypetxt" name="WECTypetxt" class="ctrl">
												<option value="ALL">--ALL--</option>
												<%=wectype%>
											</select></td>
										</tr>
									
										<tr bgcolor="#ffffff">
											<td id="t_general_information">&nbsp;Select EB:</td>
											<td bgcolor="#ffffff"><select name="EBIdtxt" id="EBIdtxt" class="ctrl">
												<option value="">--Make a Selection--</option>

											</select></td>
										</tr>
										<tr bgcolor="#ffffff">
											<td id="t_general_information">&nbsp;Select WEC:</td>
											<td bgcolor="#ffffff"><select name="WECIdtxt" id="WECIdtxt" class="ctrl">
												<option value="">--Make a Selection--</option>

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
