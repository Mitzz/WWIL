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
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/debug.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/enums.js"></script>
<script type="text/javascript">
        	$(document).ready(function(){
        		var actionPerform = new enums.Enum("create", "update", "delete");
        		var actionStatus = actionPerform.create;
        		
        		var form = $("form[name='MessageDetailForm']");
        		var standardMessageVos = ${standardMessageVosJson};//List of Javascript Object
        		
        		var id = null;
        		
        		var selectionField = $('#msgheadtxt');
        		for(var vo of standardMessageVos){
        			selectionField.append(new Option(vo.messageHead, vo.id));
        		}
        		
        		function initialize(){
        			actionStatus = actionPerform.create;
        			id = null;
        			resetForm();
        			
        			function resetForm(){
        				form.find("[name='description']").val("");
            			form.find("[name='fromDate']").val("");
            			form.find("[name='toDate']").val("");
            			$("#msgheadtxt").val($("#msgheadtxt option:first").val());
        			}
        			changeValueOfSubmit("Submit");
        		}
        		
        		$('#msgheadtxt').change(function(){
        			var id = $(this).find(":selected").val();
        			actionStatus = actionPerform.create;
        			populateForm(id); 
        			changeValueOfSubmit("Submit");
        			function populateForm(id){
        				var vo = getMessage(id);
        				form.find("[name='description']").val(vo.messageDescription);
        				form.find("[name='fromDate']").val("");
            			form.find("[name='toDate']").val("");
        			}
        			
        			function getMessage(id){
        				for(var standardMessage of standardMessageVos){
                			if(standardMessage.id == id)
                				return standardMessage;
                		}
        			}
        			
        		});
        		
        		$('.edit').click(function(){
        			
        			var row = $(this).parent().parent();
        			id = row.attr('data-id');
        			actionStatus = actionPerform.update;
        			setFormValues(row);
        			changeValueOfSubmit("Update");
        		
        			function setFormValues(row){
            			var description = row.find(".description").text();
            			var fromDate = row.find(".fromDate").text();
            			var toDate = row.find(".toDate").text();
            			
            			var id = row.attr('data-id');
            			form.find("[name='description']").val(description);
            			form.find("[name='fromDate']").val($.fn.getCalendarFormat(fromDate));
            			form.find("[name='toDate']").val($.fn.getCalendarFormat(toDate));
        				//logger("Id: " + id + "\n Description: " + description + ",\n From Date: " + fromDate + ",\n To Date: " + toDate);
            		}
        			
        		});
        		
        		$('.delete').click(function(){
        			if (confirm('Are you sure you want to delete?')) {
        			    id = $(this).parent().parent().attr('data-id');
            			actionStatus = actionPerform.delete;
            			$('#submit').trigger("click");
        			}
        		});
        		
        		$('.sent-message').click(function(){
        			var id = $(this).parent().parent().attr('data-id');
        			location.href="/WCARE/jsppages/SendMessageN.jsp?msgid="+id;
        			<%-- location.href="<%=request.getContextPath()%>/Admin/SendMessage.jsp?msgid="+msgid; --%>
        			console.log("Id: " + id);
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
        			updateFormAction();
        			logger("Action: " + $(form).attr("action"))
        			
        			function updateFormAction(){
        				if(actionStatus == actionPerform.update || actionStatus == actionPerform.delete){
    	        			var queryParameter = {"id":id, "method": actionStatus.name};
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
        				if(actionStatus == actionPerform.delete){
        					return true;
        				}
        				if($.fn.isInputTextFieldEmptyWithErrorMessage("#description", "Description cannot be blank.")){
            				return false;
            			}
        				
        				if($.fn.isInputTextFieldEmptyWithErrorMessage("#fromDate", "Please select 'from' date.")){
            				return false;
            			}
        				
        				if($.fn.isInputTextFieldEmptyWithErrorMessage("#toDate", "Please select 'to' date.")){
            				return false;
            			}
        				
        				if(!$.fn.validateFromDateToDate($('#fromDate').val(), $('#toDate').val())){
            				alert("Please select toDate after or equal to from Date");
            				return false;
            			}
        				
        				return true;
        			}
        		});
        	});
</script>
</head>
<body><!-- onLoad="findApplication(),DelTEMPMsgDetails();" -->
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">

<html:form action="/PostMessageN.do" styleId="MessageDetailForm">

<%-- <html:hidden property="id" value="1000"/> --%>
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Message Head</th>
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
			<td id="t_street_address" width="180px">&nbsp;Select&nbsp;Message:</td>
			<td class="bgcolor" width="180px">
                <!-- <select size="1" name="msgheadtxt" id="msgheadtxt" class="tabtextnormal" onchange="findDetails()"> -->
                <select size="1" name="msgheadtxt" id="msgheadtxt" class="tabtextnormal" onchange="">
		             <option value="">--Make a Selection--</option>
		             <%-- <%=stdmsg%> --%>
		         </select>

</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Message Description:</td>
			<td class="bgcolor">
				<!-- <textarea rows="3" cols="39" id="MsgDescriptiontxt" name="MsgDescriptiontxt" class="BoxBorder" >
				</textarea> -->
				<html:textarea rows="3" cols="39" property="description" styleClass="BoxBorder" styleId="description"></html:textarea>
			</td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor">
				<!-- <input type="text" id="FromDatetxt" name="FromDatetxt" class="tabtextnormal"> -->
				<html:text property="fromDate" styleClass="tabtextnormal" readonly="true" styleId="fromDate"></html:text>
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.MessageDetailForm.fromDate);return false;" >
					<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
				</a>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
			<td class="bgcolor">
			<!-- <input type="text" id="ToDatetxt" name="ToDatetxt" class="tabtextnormal"> -->
			<html:text property="toDate" styleClass="tabtextnormal" readonly="true" styleId="toDate"></html:text>
		<%-- <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.MessageDetailForm.toDate);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a> --%>
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.MessageDetailForm.toDate, [[2010,1,1],[2020,12,25],[2003,9]]);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
			<html:submit value="Submit" styleId="submit" styleClass="btnform" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
			<html:reset value="Reset" styleId="reset" styleClass="btnform" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</html:form>	
</td>		
</tr>
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="90%"><tbody>
			<tr>
				<td >
					<div id="msgdetails">
						<table border='0' cellpadding='2' cellspacing='1' width='100%'>
							<tr align='center' height='20'>
								<th class='detailsheading' width='5%'>S.N.</th>
								<th class='detailsheading' width='60%'>Description</th>
								<th class='detailsheading' width='10%'>From Date</th>
								<th class='detailsheading' width='10%'>To Date</th>
								<th class='detailsheading' width='5%'>E</th>
								<th class='detailsheading' width='5%'>D</th>
								<th class='detailsheading' width='5%'>Send</th>
							</tr>
							<logic:iterate name="messageVos" id="messageVo" >
								<c:set var = "srNo" value="${srNo + 1}"/>

							<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="messageVo" property="id"/>'>
								
								<td ALIGN='center'>${srNo}</td>
								<td align='left' class="description"><bean:write name="messageVo" property="description"/></td>
								<td align='left' class="fromDate"><bean:write name="messageVo" property="fromDate"/></td>
								<td align='left' class="toDate"><bean:write name="messageVo" property="toDate"/></td>
								<td align='center'>
									<input type='image' class = "edit" src='/WCARE/resources/images/edit.gif' alt='Click to edit the record'>
								</td>
								<td align='center'>
									<input type='image' class = "delete" src='/WCARE/resources/images/delet.gif' alt='Click to edit the record' />
								</td>
								<td align='center'>
									<input type='image' class="sent-message" src='/WCARE/resources/menu/mifuncs.gif' alt='Click to send the message' />
								</td>
							</tr>
							</logic:iterate>
						</table>

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