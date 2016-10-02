<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var = "srNo" scope="page" value="0"/>
<c:set var = "class" scope="page" value=""/>

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
	
	var form = $("form[name='CustomerMasterForm']");
	var custId = null;
		 
	function initialize(){
		actionStatus = actionPerform.create;
		custId = null;	
		changeValueOfSubmit("Submit");
	}
	
	$('.edit').click(function(){
		
		var row = $(this).parent().parent();
		custId = row.attr('data-id');
		console.log(custId);
		actionStatus = actionPerform.update;
		setFormValues(row);
		changeValueOfSubmit("Update");
	     
		function setFormValues(row){
			var custId = row.attr('data-id');	
			form.find("[name='name']").val(row.find(".name").text());
			form.find("[name='sapCode']").val(row.find(".sapCode").text());
			form.find("[name='telephoneNo']").val(row.find(".telephoneNo").text());
			form.find("[name='cellNo']").val(row.find(".cellNo").text());
			form.find("[name='faxNo']").val(row.find(".faxNo").text());
			form.find("[name='contactPerson']").val(row.find(".contactPerson").text());
			form.find("[name='marketingPerson']").val(row.find(".marketingPerson").text());
			form.find("[name='email']").val(row.find(".email").text());
			
			//console.log(row.find(".act").text());
			if(row.find(".act").text() == "Active"){
				//console.log("checked")
				$("#act").prop('checked', true); 			
			}
			if(row.find(".act").text() == "DeActive"){
				$("#act").prop('checked', false);
				//console.log("UNchecked")		
			}
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
				console.log("custId :: "+custId + " :: method : "+ actionStatus.name)
    			var queryParameter = {"id":custId, "method": actionStatus.name};	
			} else {
				var queryParameter = {"method": actionStatus.name};
			}
			var queryString = "?";
			var action = $(form).attr("action");
			for(var param in queryParameter){
				queryString += param + "=" + queryParameter[param] + "&";
			}
			var index = $(form).attr("action").indexOf("?");
			if(index != -1){
				$(form).attr("action", action.substring(0, index) + queryString);
			} else {
				$(form).attr("action", action + queryString);
			}
		}
		
		function validateForm(){
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Name cannot be blank.")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#sapCode", "Please enter SAP code.")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#telephoneNo", "Please enter Telephone Number.")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#cellNo", "Please enter Cell Number")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#faxNo", "Please enter Fax Number")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#contactPerson", "Please enter Contact Person")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#marketingPerson", "Please enter Marketing Person")){
				return false;
			}
			
			if($.fn.isInputTextFieldEmptyWithErrorMessage("#email", "Please enter Email")){
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
<html:form action="/manageCustomerMaster.do" method="post" styleId="CustomerMasterForm">
	<table align="center" border="0" cellpadding="0" cellspacing="0" width="550">
		<tbody>
			<tr>
				<td class="newhead1" width="10"></td>
				<th class="headtext" width="70">Customer Master</th>
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
												<td  width="219">Customer:</td>
												<td colspan="3" valign="top">													
													<html:text property="name" styleClass="ctrl" styleId="name" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td id="t_company" width="219">Customer SAP Code:</td>
												<td colspan="3" valign="top">									            	
									            	<html:text property="sapCode" styleClass="ctrl"  styleId="sapCode" size="32"></html:text>
									            </td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Telephone Number:</td>
												<td colspan="3" valign="top">													
													<html:text property="telephoneNo" styleClass="ctrl"  styleId="telephoneNo" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Cell No:</td>
												<td colspan="3" valign="top">													
													<html:text property="cellNo" styleClass="ctrl"  styleId="cellNo" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Fax Number:</td>
												<td colspan="3" valign="top">													
													<html:text property="faxNo" styleClass="ctrl"  styleId="faxNo" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Contact Person:</td>
												<td colspan="3" valign="top">													
													<html:text property="contactPerson" styleClass="ctrl"  styleId="contactPerson" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Marketing Person:</td>
												<td colspan="3" valign="top">													
													<html:text property="marketingPerson" styleClass="ctrl"  styleId="marketingPerson" size="50"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Email:</td>
												<td colspan="3" valign="top">													
													<html:text property="email" styleClass="ctrl"  styleId="email" size="32"></html:text>
												</td>
											</tr>
											<tr class="bgcolor">
												<td  width="219">Active:</td>
												<td colspan="3" valign="top">												 												
													<html:checkbox property="active" styleClass="ctrl"  styleId="act" value="1" ></html:checkbox>
													<!-- <input type="checkbox" class="ctrl"  id="act" name=act/> -->
												</td>
											</tr>
											<logic:messagesPresent property="success" message="true">
											<tr class="bgcolor"> 
												<td colspan="2" class = "sucessmsgtext">
												<html:messages id="msg" name="success" message="true" bundle="stdmessage">
													<bean:write name="msg" />
												</html:messages>
												</td>
											</tr>
											</logic:messagesPresent>
											<logic:messagesPresent property="error" message="true">
											<tr class="bgcolor"> 
												<td colspan="2" class = "errormsgtext">
												<html:messages id="msg" name="error" message="true" bundle="stdmessage">
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
									<html:submit value="Submit" styleId="submit" styleClass="btnform" />
								</td>
								<td width="1">
									<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1">
								</td>
								<td class="btn" width="100">									
									<html:reset value="Cancel" styleId="reset" styleClass="btnform" />
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
</html:form>
<table border="0" align="center" cellpadding="0"  cellspacing="0" width="400">
	<tbody>
		<tr>		
			<td align="center">
				<div id="statedetails">
					<table border='0' cellpadding='2' cellspacing='1' width='100%'>
						<tr align='center' height='20'>
							<th class='detailsheading' width='30'>S.N.</th>
							<th class='detailsheading' width='100'>Customer</th>
							<th class='detailsheading' width='100'>Marketing Person</th>
							<th class='detailsheading' width='100'>Code</th>
							<th class='detailsheading' width='100'>Contact</th>
							<th class='detailsheading' width='100'>Telephone No</th>
							<th class='detailsheading' width='100'>Cell No</th>
							<th class='detailsheading' width='100'>FAX</th>
							<th class='detailsheading' width='100'>EMail</th>
							<th class='detailsheading' width='100'>Active</th>
							<th class='detailsheading' width='40'>Edit</th>
						</tr>
						<logic:iterate name="customerMasterVos" id="customerVo" >
							<c:set var = "srNo" value="${srNo + 1}"/>

						<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="customerVo" property="id"/>'>
							
							<td ALIGN='center'>${srNo}</td>
							<td align='left' class="name"><bean:write name="customerVo" property="name"/></td>
							<td align='left' class="marketingPerson"><bean:write name="customerVo" property="marketingPerson"/></td>
							<td align='left' class="sapCode"><bean:write name="customerVo" property="sapCode"/></td>
							<td align='left' class="contactPerson"><bean:write name="customerVo" property="contactPerson"/></td>
							<td align='left' class="telephoneNo"><bean:write name="customerVo" property="telephoneNo"/></td>
							<td align='left' class="cellNo"><bean:write name="customerVo" property="cellNo"/></td>
							<td align='left' class="faxNo"><bean:write name="customerVo" property="faxNo"/></td>
							<td align='left' class="email"><bean:write name="customerVo" property="email"/></td>
						   <%--  <td align='left' class="act"><bean:write name="customerVo" property="active" /></td> --%> 
							 <c:choose>
								<c:when test = "${customerVo.active == 1}">
								     <td align='left' class="act">Active</td>
								</c:when>
								<c:otherwise>
									 <td align='left' class="act">DeActive</td>
								</c:otherwise>
							</c:choose>						
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