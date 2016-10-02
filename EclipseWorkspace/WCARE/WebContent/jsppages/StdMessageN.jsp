<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/debug.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>

<script type="text/javascript">
		$(document).ready(function(){
			
			$.fn.charactersCheck();
		});
</script>
<%
/* if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
} */
%>
<%
/* String rn = "";
String de = "";
String ri = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("MsgHeadtxt").toString();
	de = dynabean.getProperty("MsgDescriptiontxt").toString();
	ri = dynabean.getProperty("MsgIdtxt").toString();
	session.removeAttribute("dynabean");
} */
%>
<%
/* response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close(); */
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
%>
<script type="text/javascript">
function findApplication() 
{
		enter( arguments.callee.name );
	 var req = newXMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_STD_MESSAGE&AppId="+ApplicationId);
	 left( arguments.callee.name );
}
function showAppDetails(dataXml)
{
	enter( arguments.callee.name );
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");	
		var divdetails = document.getElementById("msgdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Message Head</th><th class='detailsheading' width='230'>Description</th>"
		str +="<th class='detailsheading' width='40'>E</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("msghead")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("msgdesc")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "')></td></tr>"
	   	 		document.forms[0].MsgIdtxt.value = "";
	   	 	}   	 	
		}
		str += "</table>"
		divdetails.innerHTML = str;
		left( arguments.callee.name );
}
function findDetails(roleid)
{
	
	enter( arguments.callee.name );
	 var req = newXMLHttpRequest();
     var ApplicationId = roleid;
	 req.onreadystatechange = getReadyStateHandler(req, showRoleMaster);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_STD_MESSAGE_DETAIL&AppId="+ApplicationId);
	 left( arguments.callee.name );
}
function showRoleMaster(dataXml)
{
	enter( arguments.callee.name );
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgdesc")[0].firstChild;
     	if (nname != null){
   	 		document.forms[0].MsgHeadtxt.value = item.getElementsByTagName("msghead")[0].firstChild.nodeValue;
   	 		document.forms[0].MsgDescriptiontxt.value = item.getElementsByTagName("msgdesc")[0].firstChild.nodeValue;    	 		
   	 		document.forms[0].MsgIdtxt.value = item.getElementsByTagName("msgid")[0].firstChild.nodeValue;
   	 	}
   	 	else{
   	 		document.forms[0].MsgHeadtxt.value = "";
   	 		document.forms[0].MsgDescriptiontxt.value = "";  
   	 		document.forms[0].MsgIdtxt.value = "";  	 		
   	 	} 			
 	}
	left( arguments.callee.name );
}

</script>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
	<tr width="100%">
		<td width="100%" align="center">
		
		
		<html:form action="/StdMessageN.do?method=createStandardMessage">

			<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
				<tbody>
					<tr>
						<td class="newhead1"></td>
						<th class="headtext">Message Head</th>
						<td><html:img src="resources/images/formtab_r.gif" border="0" height="21" width="10" /></td>
						<td class="newhead3">&nbsp;</td>
						<!-- <td class="newhead4">&nbsp;</td> -->
						<td class="newhead4"><html:img src="resources/images/pixel.gif" border="0" height="1" width="10" /></td>
					</tr>
					<tr>
						<td class="newheadl"><html:img src="resources/images/pixel.gif" border="0" /></td>
						<td colspan="3">
							<html:img src="resources/images/pixel.gif" border="0" height="10" width="1" /><br>
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tbody>
									<tr>
										<td bgcolor="#dbeaf5">
											<table border="0" cellpadding="2" cellspacing="1" width="100%">
												<tbody>						
													<tr class="bgcolor"> 
														<td width="180px"><bean:message key="label.stdmessage.messageHead" bundle="stdmessage"/></td>
														<td class="bgcolor" width="180px">
															<!-- <input type="text" id="MsgHeadtxt" name="MsgHeadtxt" size="25" value="" class="BoxBorder" maxlength="30" /> -->
															<html:text property="messageHead" size="25" styleClass="BoxBorder check-character-length" maxlength="20" ></html:text>
														</td>
													</tr>
													<logic:messagesPresent message="false" property="messageHead" >
													<tr style="background-color: white">
														<td colspan="2" style="color: red;background-color: white;">
															<html:errors property="messageHead" />
														</td>
													</tr>
													</logic:messagesPresent>
													<tr class="bgcolor"> 
														<td><bean:message key="label.stdmessage.messageDescription" bundle="stdmessage" /></td>
														<td class="bgcolor">
															<html:textarea rows="3" cols="39" property="messageDescription" styleClass="BoxBorder check-character-length"></html:textarea>
														</td>
													</tr>
													<logic:messagesPresent message="false" property="messageDescription">
													<tr style="background-color: white">
														<td colspan="2" style="color: red;background-color: white;">
															<html:errors property="messageDescription"/>
														</td>
													</tr>
													</logic:messagesPresent>
													<logic:messagesPresent property="SuccessfullyCreated" message="true">
													<tr class="bgcolor">
														<td colspan="2" class="sucessmsgtext">
														<html:messages id="msg" name="SuccessfullyCreated" message="true" bundle="stdmessage">
															<bean:write name="msg" />
														</html:messages>
														</td>
													</tr>
													</logic:messagesPresent>
													<logic:messagesPresent property="NotCreated" message="true">
													<tr class="bgcolor">
														<td colspan="2" class="errormsgtext">
														<html:messages id="msg" name="NotCreated" message="true" bundle="stdmessage">
															<bean:write name="msg" />
														</html:messages>
														</td>
													</tr>
													</logic:messagesPresent>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
							<html:img src="resources/images/pixel.gif" border="0" height="10" width="1" /><br>
						</td>
						<td class="newheadr"><html:img src="resources/images/pixel.gif" border="0" /></td>
					</tr>
					<tr>
						<td width="10"><html:img src="resources/images/formtab_b.gif" border="0" height="20" width="10" /></td>
						<td colspan="4" align="right" bgcolor="#006633">
							<table border="0" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<td class="btn" width="100">
				<%-- <input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=ri %>" /> --%>	
											<!-- <input name="Submit" type="submit" class="btnform" value="Submit"/> -->
											<html:submit value="Submit" styleClass="btnform"></html:submit>
										</td>
										<td width="1"><html:img src="resources/images/pixel.gif" border="0" height="18" width="1" /></td>
										<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
										<td width="1"><html:img src="resources/images/pixel.gif" border="0" height="18" width="1" /></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			</html:form>	
		</td>		
	</tr>
	<tr>
		<td align="center">
			<table border="0" cellpadding="0" cellspacing="0" width="400">
				<tbody>
					<tr>
						<td>
							
							<div id="msgdetails">
					
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
</html>