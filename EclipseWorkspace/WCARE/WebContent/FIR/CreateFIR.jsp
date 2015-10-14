<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/EILNew.css" type="text/css">
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/css/dhtmlx.css"></link>
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/css/dhtmlxgrid_pgn_bricks.css"></link>
<link rel="STYLESHEET" type="text/css" href="<%=request.getContextPath()%>/css/dhtmlxcalendar_dhx_skyblue.css"></link>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/dhtmlx.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/appCommon.js"></script>

<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String aid = "";
String aname = "";
String stid = "";
String acode="";
String ainc="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	stid = dynabean.getProperty("Statetxt").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		aname = "";
		aid = "";
		acode = "";
		ainc= "";		
	}else{
		aname = dynabean.getProperty("AreaNametxt").toString();
		aid = dynabean.getProperty("AreaIdtxt").toString();
		acode = dynabean.getProperty("AreaCodetxt").toString();
		ainc = dynabean.getProperty("AreaInchargetxt").toString();		
	}
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);

%>

	
<script type="text/javascript">
var documentGrid;
function doOnLoad(){
	populateDocDtlsGrid();
}
	function populateDocDtlsGrid(){
		var documentGridXML = "";
		documentGrid = new dhtmlXGridObject('docDtlsGridContainer');
			documentGrid.setImagePath("css/imgs/");
			documentGrid.setHeader(",Sr No.,Area,Document Required,Remarks");
			documentGrid.setInitWidthsP("0,8,25,25,40");
			documentGrid.setColAlign("left,center,left,left,left");
			documentGrid.setColTypes("ro,ro,coro,ed,ed");
			documentGrid.setColSorting("int,str,str,str,str");
			documentGrid.enableValidation("false,false,false,false,false");
		documentGrid.enableAutoHeight(true,"200");
		documentGrid.init();
		
		if(documentGridXML != ""){	 
			documentGrid.parse(documentGridXML);
		}
	}
</script>
</head>
<body onLoad = "doOnLoad();" bottommargin="15" topmargin="15" leftmargin="15" rightmargin="15" marginheight="15" marginwidth="15" bgcolor="white" >
	<div align="left" style="width: 850px">   
		<table>
			<tr>
				<td width="825" >
					<form method="post" action="saveAnnualPlan.do" name="annualPlanForm">						
						<table cellpadding="0" cellspacing="0" border="0" width="100%"  >
							<tr>
								<td bgcolor="#4682B4" width="10"><img src="<%=request.getContextPath()%>/img/pixel.gif" width="10" height="1" border="0"></td>
								<td class="header1" nowrap>Create FIR<img src="<%=request.getContextPath()%>/img/pixel.gif" width="10" height="1" border="0"></td>
								<td><img src="<%=request.getContextPath()%>/img/formtab_r.gif" width="10" height="21" border="0"></td>
								<td background="<%=request.getContextPath()%>/img/line_t.gif" width="100%">&nbsp;</td>
								<td background="<%=request.getContextPath()%>/img/line_t.gif"><img src="<%=request.getContextPath()%>/img/pixel.gif" width="10" height="1" border="0"></td>
							</tr>
							<tr>
								<td background="<%=request.getContextPath()%>/img/line_l.gif"><img src="<%=request.getContextPath()%>/img/pixel.gif" border="0"></td>
								<td colspan="3">
									<img src="<%=request.getContextPath()%>/img/pixel.gif" width="1" height="10" border="0"><br>
									<div align="center" id="error_registration" class="greenHead" style="display: block;">
									  
									</div>
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<tr>	
											<td bgcolor="#DBEAF5">
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr><td colspan="6"><b>(A) Technical Specification</b></td></tr>
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_stateName">&nbsp;State Name:</td>
														<td width="20%">
															<select name="stateName" id="stateName" style="width:107px;">
																<option value="">--Make a Selection--</option>
															</select>
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Site Name:</td>
														<td width="20%">
															<select name="siteName" id="siteName" style="width:107px;">
																<option value="">--Make a Selection--</option>
															</select>
														</td>
														<td width="15%" id="t_siteName">&nbsp;WEC Name:</td>
														<td width="15%">
															<select name="wecName" id="wecName" style="width:107px;">
																<option value="">--Make a Selection--</option>
															</select>
														</td>
													</tr>													
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr bgcolor="#ffffff"> 																																									 
														<td width="15%" colspan="1" id="t_compFailed">&nbsp;Component Failed:</td>
														<td width="20%" colspan="1">
															<select name="Statetxt" id="Statetxt" style="width:107px;">
																<option value="">--Make a Selection--</option>
															</select>
														</td>
														<td width="15%" colspan="1" id="t_firDate">&nbsp;FIR Date:</td>
														<td width="20%" colspan="1">
															<input type="text" id="firDate" />
														</td>	
														<td colspan="2"></td>													
													</tr>													
												</table>												
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr><td colspan="6"><b>(B1) Fault Event Details</b></td></tr>
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_stateName">&nbsp;Status:</td>
														<td width="20%">
															<input type="text" id="stateName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Sub-status:</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td colspan="2"></td>
													</tr>
																									
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">													
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_stateName">&nbsp;Fault Start Date:</td>
														<td width="20%">
															<input type="text" id="stateName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Restoration Date:</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td width="15%" id="t_siteName">&nbsp;Last Fault Date:</td>
														<td width="15%">
															<input type="text" id="siteName" />
														</td>
													</tr>														
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">													
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_siteName">&nbsp;Time of Fault:</td>
														<td width="20">
															<input type="text" id="siteName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Total Breakdown Dur:</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td width="15%" id="t_siteName">&nbsp;Mean Time of Failure:</td>
														<td width="15%">
															<input type="text" id="siteName" />
														</td>
													</tr>														
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">													
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_siteName">&nbsp;PM Lead Name:</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;PM Details:</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td width="15%" id="t_siteName">&nbsp;PM Mob No:</td>
														<td width="15%">
															<input type="text" id="siteName" />
														</td>
													</tr>														
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr><td colspan="6"><b>(B2) Ambient Condition During Fault</b></td></tr>
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_stateName">&nbsp;Temperature(deg.C):</td>
														<td width="20%">
															<input type="text" id="stateName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Wind Speed(m/s):</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td colspan="2"></td>
													</tr>																									
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">													
													<tr bgcolor="#ffffff"> 
														<td width="15%" id="t_stateName">&nbsp;Weather During Fault:</td>
														<td width="20%">
															<input type="text" id="stateName" />
														</td>													 
														<td width="15%" id="t_siteName">&nbsp;Humidity(%):</td>
														<td width="20%">
															<input type="text" id="siteName" />
														</td>
														<td colspan="2"></td>
													</tr>																									
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr><td><b>(C) List of Event before the occurance of Fault</b></td></tr>
													<tr bgcolor="#ffffff"> 
														<td width="100%">
															&nbsp;
														</td>
													</tr>																									
												</table>
												<table cellspacing="1" cellpadding="2" border="0" width="100%">
													<tr><td colspan="7"><b>(D) Past History</b></td></tr>
													<tr><td>Sr No</td><td>Date</td><td>Time</td><td>BD Dur</td><td>RCA Done</td><td>Root Cause</td><td>Recommendations</td></tr>
													<tr bgcolor="#ffffff"> 
														<td width="100%" colspan="7">
															&nbsp;
														</td>
													</tr>																									
												</table>												
											</td>
										</tr>
									</table>
									<img src="<%=request.getContextPath()%>/img/pixel.gif" width="1" height="10" border="0"><br>
								</td>
								<td background="<%=request.getContextPath()%>/img/line_r.gif"><img src="<%=request.getContextPath()%>/img/pixel.gif" border="0"></td>
							</tr>								
							<tr>
								<td width="10"><img src="<%=request.getContextPath()%>/img/formtab_b.gif" width="10" height="20" border="0"></td>
								<td bgcolor="#4682B4" colspan="4" align="right">
									<table cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td class="btn" width="100"><input type="reset" name="Reset" value="Reset" class="btnform"></td>
											<td width="1"><img src="<%=request.getContextPath()%>/img/pixel.gif" width="1" height="18" border="0"></td>
											<td class="btn" width="100"><input type="submit" name="Submit" value="Submit" class="btnform" onclick="createAnnualPlan()"></td>
											<td width="1"><img src="<%=request.getContextPath()%>/img/pixel.gif" width="1" height="18" border="0"></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</form>
				</td>	
			</tr>
		</table>						
	</div>
</body>
</html>