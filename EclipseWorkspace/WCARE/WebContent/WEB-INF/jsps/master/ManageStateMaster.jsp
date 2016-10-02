<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var = "srNo" scope="page" value="0"/>
<c:set var = "class" scope="page" value=""/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page isELIgnored="false" %>

<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="${ contextPath }/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="${ contextPath }/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="${ contextPath }/resources/js/ajax.js"></script>
<script type="text/javascript" src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/mySelectFunction.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/enums.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var actionPerform = new enums.Enum("create", "update", "delete");
	var actionStatus = actionPerform.create;
	
	var form = $("form[name='StateMasterForm']");
	//var standardMessageVos = ${standardMessageVosJson};//List of Javascript Object
	
	var id = null;
	
	//var selectionField = $('#msgheadtxt');
	/* for(var vo of standardMessageVos){
		selectionField.append(new Option(vo.messageHead, vo.id));
	} */
	
	function initialize(){
		actionStatus = actionPerform.create;
		id = null;
		resetForm();
		
		function resetForm(){
			form.find("[name='name']").val("");
			form.find("[name='sapCode']").val("");
			
		}
		changeValueOfSubmit("Submit");
	}
	
	$('.edit').click(function(){
		
		var row = $(this).parent().parent();
		id = row.attr('data-id');
		actionStatus = actionPerform.update;
		setFormValues(row);
		changeValueOfSubmit("Update");
	
		function setFormValues(row){
			var stateName = row.find(".name").text();
			var sapCode = row.find(".sapCode").text();
			
			var id = row.attr('data-id');
			form.find("[name='name']").val(stateName);
			form.find("[name='sapCode']").val(sapCode);
			
			//logger("Id: " + id + "\n Description: " + description + ",\n From Date: " + fromDate + ",\n To Date: " + toDate);
		}
		
	});
	
	function changeValueOfSubmit(value){
		var submit = document.getElementById("submit");
		submit.setAttribute("value", value);
	}
	
	$("#reset").click(function(){
		initialize();
	});
		
	$(form).submit(function(){
		if(!validateForm()){
			return false;
		}
		if(!getConfirmation()){
			return false;
		}
		updateFormAction();
		console.log("Action: " + $(form).attr("action"));
		
		function getConfirmation(){
			if(actionStatus == actionPerform.update){
				return confirm('Are you sure you want to update?'); 
			} 
			else if(actionStatus == actionPerform.create){
				return confirm('Are you sure you want to create?');
			}
		}
		function updateFormAction(){
			if(actionStatus == actionPerform.update){
    			var queryParameter = {"id":id};
			} else {
				var queryParameter = {};
			}
			
			var action = $(form).attr("action");
			
			var queryString = "?";
			for(var param in queryParameter){
				queryString += param + "=" + queryParameter[param] + "&";
			}
			if(actionStatus == actionPerform.update){
				$(form).attr("action", action + "/update" + queryString);
			} else {
				$(form).attr("action", action + "/create");
			}
			
		}
		
		function validateForm(){
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Name cannot be blank.")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#sapCode", "Please enter SAP code.")){
				return false;
			}
			
			return true;
		}
	});
});
</script>
</head>
<body>
<div align="center">
<%-- <form action="${ contextPath }/StateMaster.do" method="post" > --%>

<%-- <html:form action="/manageStateMaster.do" method="post" styleId="StateMasterForm"> --%>
<sf:form method="post" action="${pageContext.request.contextPath}/spring/stateMaster" modelAttribute="state" id="StateMasterForm" name="StateMasterForm">
	<table align="center" border="0" cellpadding="0" cellspacing="0" width="550">
		<tbody>
			<tr>
				<td class="newhead1" width="10"></td>
				<th class="headtext" width="70">State Master</th>
				<td width="363"><img src="${ contextPath }/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
				<td class="newhead3" width="8">&nbsp;</td>
				<td class="newhead4" width="11">
					<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="1" width="10">
				</td>
			</tr>
			<tr>
				<td background="${ contextPath }/resources/images/line_l.gif" width="10">
    				<img src="${ contextPath }/resources/images/pixel.gif" border="0" width="1" height="1">
    			</td>
				<td colspan="3" width="550">
					<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td bgcolor="#dbeaf5">
									<table border="0" cellpadding="2" cellspacing="1" width="550">
										<tbody>
											<tr class="bgcolor">
												<td  width="219">State Name:</td>
												<td colspan="3" valign="top">
													<%-- <input name="StateNametxt"  id="StateNametxt" value="<%=sname%>" size="12" class="ctrl" type="text"> --%>
													<%-- <html:text property="name" styleClass="ctrl" readonly="false" styleId="name" size="12"></html:text> --%>
													<sf:input cssClass="ctrl" path="name" size="12" type="text" />
													<sf:errors path="name"></sf:errors>
												</td>
											</tr>
											<tr class="bgcolor">
												<td id="t_company" width="219">State SAP Code:</td>
												<td colspan="3" valign="top">
									            	<%-- <input name="StateCodetxt" id="StateCodetxt" size="12" value="<%=scode%>" class="ctrl" type="text"> --%>
									            	<%-- <html:text property="sapCode" styleClass="ctrl" readonly="false" styleId="sapCode" size="12"></html:text> --%>
									            	<sf:input cssClass="ctrl" path="sapCode" size="12" id="sapCode" type="text" />
									            </td>
											</tr>
											<c:if test="${not empty success }">
											<tr class="bgcolor"> 
												<td colspan="2" class = "sucessmsgtext">
												${ success }
												</td>
											</tr>
											</c:if>
											
											<c:if test="${not empty failure }">
											<tr class="bgcolor"> 
												<td colspan="2" class = "errormsgtext">
												${ failure }
												
												</td>
											</tr>
											</c:if>
											<%-- <c:set var="domainNameErrors"><form:errors path="name"/></c:set> --%>
											<%-- <c:if test="${not empty domainNameErrors}"> --%>
											<%-- <tr class="bgcolor"> 
												<td colspan="2" class = "errormsgtext">
													${ domainNameErrors }
												</td>
											</tr> --%>
											<%-- </c:if> --%>
											
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
					<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
				</td>
				<td background="${ contextPath }/resources/images/line_r.gif" width="11">
    				<img src="${ contextPath }/resources/images/pixel.gif" border="0" width="1" height="1">
    			</td>
			</tr>
			<tr>
				<td width="10"><img src="${ contextPath }/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
				<td colspan="4" align="right" bgcolor="#006633" width="604">
					<table border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
								<td class="btn" width="100">
									<!-- <input name="Submit" id="Submit" value="Submit" class="btnform" type="Submit" onClick="return confirmation();"></td> -->
									<%-- <html:submit value="Submit" styleId="submit" styleClass="btnform" /> --%>
									<input value="Submit" id="submit" class="btnform" type="submit"/>
								</td>
								<td width="1">
									<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1">
								</td>
								<td class="btn" width="100">
									<%-- <input type="hidden" name="Admin_Input_Type" value="StateMaster"  />		
									<input type="hidden" name="StateIdtxt" value="<%=sid %>" /> --%>
									<!-- <input name="Cancel" value="Cancel" class="btnform" type="reset"> -->
									<%-- <html:reset value="Cancel" styleId="reset" styleClass="btnform" /> --%>
									<input type="reset" value="Cancel" id="reset" class="btnform" />
								</td>
								<td width="1">
									<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1">
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</sf:form>
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="400">
	<tbody>
		<tr>		
			<td align="center">
				<div id="statedetails">
					<table border='0' cellpadding='2' cellspacing='1' width='100%'>
						<tr align='center' height='20'>
							<th class='detailsheading' width='30'>S.N.</th>
							<th class='detailsheading' width='100'>State</th>
							<th class='detailsheading' width='100'>State SAP Code</th>
							<th class='detailsheading' width='40'>Edit</th>
						</tr>
						<logic:iterate name="stateMasterVos" id="stateVo" >
							<c:set var = "srNo" value="${srNo + 1}"/>

						<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="stateVo" property="id"/>'>
							
							<td ALIGN='center'>${srNo}</td>
							<td align='left' class="name"><bean:write name="stateVo" property="name"/></td>
							<td align='left' class="sapCode"><bean:write name="stateVo" property="sapCode"/></td>
							
							<td align='center'>
								<input type='image' class = "edit" src='${ contextPath }/resources/images/edit.gif' alt='Click to edit the record'>
							</td>
						</tr>
						</logic:iterate>
					</table>
				</div>	
			</td>
		</tr>
	</tbody>
</table>	

</div>
</body>
</html>