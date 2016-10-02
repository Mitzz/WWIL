<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var = "srNo" scope="page" value="0"/>
<c:set var = "class" scope="page" value=""/>

<%@ page isELIgnored="false" %>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/interface/AreaMasterResource.js"> </script>
<script type="text/javascript" src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/enums.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/mySelectFunction.js"></script>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="${ contextPath }/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="${ contextPath }/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="${ contextPath }/resources/js/ajax.js"></script>
        
        <script type="text/javascript">
        var actionPerform = null;
        var actionStatus = null;
        var areaId = null;
		var areas = null;
		var form = null;
		
		function changeValueOfSubmit(value){
			var submit = document.getElementById("submit");
			submit.setAttribute("value", value);
		}
		
		function editArea(th){
			$("#stateSelection").prop("disabled", true);
			var row = $(th).parent().parent();
			areaId = row.attr('data-id');
			actionStatus = actionPerform.update;
			setFormValues(row);
			changeValueOfSubmit("Update");
		
			function setFormValues(row){
				
    			form.find("[name='name']").val(row.find(".areaName").text());
    			form.find("[name='code']").val(row.find(".areaCode").text());
    			form.find("[name='inCharge']").val(row.find(".areaInCharge").text());
				//logger("Id: " + id + "\n Description: " + description + ",\n From Date: " + fromDate + ",\n To Date: " + toDate);
    		}
			
		}
        $(document).ready(function(){
        	actionPerform = new enums.Enum("create", "update", "delete");
    		actionStatus = actionPerform.create;
    		
    		form = $("form[name='AreaMasterForm']");
    		//var standardMessageVos = ${standardMessageVosJson};//List of Javascript Object
    		
    		var stateSelection = $('#stateSelection');
    		//alert("Ready");
    		
    		function initialize(){
    			actionStatus = actionPerform.create;
    			areaId = null;
    			resetForm();
    			
    			function resetForm(){
    				form.find("[name='name']").val("");
        			form.find("[name='code']").val("");
        			form.find("[name='inCharge']").val("");
        			//stateSelection.removeAttr("disabled");
        			
        			//document.getElementById("stateSelection").disabled=false;
        			//console.log("dfgdfg");
        			stateSelection.val($("#stateSelection option:first").val());
        			
    			}
    			changeValueOfSubmit("Submit");
    		}
    		
    		$('#stateSelection').change(function(){
    			var stateId = $(this).find(":selected").val();
    			//alert(stateId);
    			actionStatus = actionPerform.create;
    			populateArea(stateId); 
    			changeValueOfSubmit("Submit");
    			function populateArea(stateId){
    				if(areas == null){
    					AreaMasterResource.getAll(
    						function(data){
    							areas = data;
    							var areasByState = getAreasByState(stateId);
    							displayAreas(areasByState);
    						});
    				}
    				else{
    					var areasByState = getAreasByState(stateId);
						displayAreas(areasByState);
    				}
    			}
    			
    			function getAreasByState(stateId){
    				var areasByState = [];
    				$.each(areas, function (index, areaVo) {
    					if(areaVo.state.id == stateId){
    						areasByState.push(areaVo);
    					}
    				});
    				return areasByState;
    			}
    			
    			function displayAreas(areas){
    				var divdetails = document.getElementById("areadetails");
					divdetails.innerHTML = "";
    				var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>";
					str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Area Name</th><th class='detailsheading' width='100'>Area Code</th>";
					str +="<th class='detailsheading' width='100'>Incharge</th><th class='detailsheading' width='40'>Edit</th></tr>";
					var classPicker = null;
    				$.each(areas, function (index, areaVo) {
    					//console.log(areaVo.name);
    					
    					if (index % 2 == 0) classPicker = "detailsbody";
						else  classPicker = "detailsbody1";
						
    					str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + areaVo.id + "'><td ALIGN='center'>"+(index+1)+"</td>"
    		     		str+="<td align='left' class='areaName'>" + areaVo.name + "</td>"
    		     		str+="<td align='left' class='areaCode'>" + areaVo.code + "</td>"		
						str+="<td align='left' class='areaInCharge'>" + areaVo.inCharge + "</td>"
    		     			     		
    		     		str+="<td align='center'>";
    		     		str+="<input class='edit' type='image' src='/WCARE/resources/images/edit.gif' alt='Click to edit the record'  onclick = editArea(this)";
    		     		str+=" </td></tr>";
    		     		
    				});
    				divdetails.innerHTML = str;
    			}
    			
    			
    		});
    		
    		<%-- $('.delete').click(function(){
    			if (confirm('Are you sure you want to delete?')) {
    			    id = $(this).parent().parent().attr('data-id');
        			actionStatus = actionPerform.delete;
        			$('#submit').trigger("click");
    			}
    		});
    		
    		$('.sent-message').click(function(){
    			var id = $(this).parent().parent().attr('data-id');
    			location.href="/WCARE/jsppages/SendMessageN.jsp?msgid="+id;
    			location.href="<%=request.getContextPath()%>/Admin/SendMessage.jsp?msgid="+msgid;
    			console.log("Id: " + id);
    		}); --%>
    		
    		$("#reset").click(function(){
    			
    			$("#stateSelection").prop("disabled", false); 
    			var divdetails = document.getElementById("areadetails");
				divdetails.innerHTML = "";
    			initialize();
    		});
    			
    		$(form).submit(function(){
    			if(!validateForm()){
    				return false;
    			}
    			if(!getConfirmation()){
    				return false;
    			}
    			$("#stateSelection").prop("disabled", false);
    			updateFormAction();
    			console.log("Action: " + $(form).attr("action"))
    			
    			function getConfirmation(){
    				if(actionStatus == actionPerform.update){
    					return confirm('Are you sure you want to update?'); 
    				} 
    				else if(actionStatus == actionPerform.create){
    					return confirm('Are you sure you want to create?');
    				}
    			}
    			
    			function updateFormAction(){
    				if(actionStatus == actionPerform.update || actionStatus == actionPerform.delete){
	        			var queryParameter = {"id":areaId, "method": actionStatus.name};
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
    				/* if(actionStatus == actionPerform.delete){
    					return true;
    				} */
    				if($('#stateSelection').val() == "ns"){
        				alert("Please select State.");
        				return false;
        			}
    				
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#areaName", "Name cannot be blank.")){
        				return false;
        			}
    				
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#areaCode", "Please enter Area Code")){
        				return false;
        			}
    				
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#areaInCharge", "Please enter Area Incharge")){
        				return false;
        			}
    				
    				/* if(!$.fn.validateFromDateToDate($('#fromDate').val(), $('#toDate').val())){
        				alert("Please select toDate after or equal to from Date");
        				return false;
        			} */
    				
    				return true;
    			}
    		});
    	});
            


        </script>
    </head>
    <body>
        <div align="center">
            <%-- <form action="${ contextPath }/areaMaster.do" method="post" onSubmit="return confirmation()" > --%>
            <html:form action="/manageAreaMaster.do" method="post" styleId="AreaMasterForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
                    <tbody>
                    	<tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Area Master</th>
                            <td width="475"><img src="${ contextPath }/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                            <td class="newhead3" width="7">&nbsp;</td>
                            <td class="newhead4" width="10"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                        </tr>
                        <tr>
                            <td background="${ contextPath }/resources/images/line_l.gif" width="10">
                                <img src="${ contextPath }/resources/images/pixel.gif" border="0" width="1" height="1">
                            </td>
                            <td colspan="3" width="592">
                                <img src="${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                	<tbody>
                                		<tr>
                                			<td bgcolor="#dbeaf5">
                                                <table border="0" cellpadding="2" cellspacing="1" width="592">
                                                    <tbody>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select State:</td>
                                                            <td valign="top">
                                                                <select name="stateId" id="stateSelection" class="ctrl" >
                                                                <option value="ns">--Make Selection--</option>
																<jsp:include page="/jsppages/utility/SelectVoTemplateV2.jsp">
																	<jsp:param name="beanIdentifier" value="stateMasterVos" />
																	<jsp:param name="option" value="id" />
																	<jsp:param name="text" value="name" />
																</jsp:include>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Name :</td>
                                                            <td valign="top">
                                                                <%-- <input name="AreaNametxt" id="AreaNametxt" size="15" class="ctrl" value="<%=aname%>" type="text"></td> --%>
                                                                <html:text styleId="areaName" size="15" styleClass="ctrl" property="name"  />
                                                             </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Short Code :</td>
                                                            <td valign="top">
                                                                <%-- <input name="AreaCodetxt" id="AreaCodetxt" size="15" class="ctrl" value="<%=acode%>" type="text"> --%>
                                                                <html:text styleId="areaCode" size="15" styleClass="ctrl" property="code" />
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Incharge :</td>
                                                            <td valign="top">
                                                                <%-- <input name="AreaInchargetxt" id="AreaInchargetxt" size="25" class="ctrl" value="<%=ainc%>" type="text" maxlength="25"> --%>
                                                                <html:text styleId="areaInCharge" size="25" styleClass="ctrl" property="inCharge" maxlength="25" />
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
                			<td background="${ contextPath }/resources/images/line_r.gif" width="10">
                    			<img src="${ contextPath }/resources/images/pixel.gif" border="0" width="1" height="1">
                    		</td>
                		</tr>
                		<tr>
                    		<td width="10"><img src="${ contextPath }/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
                    		<td colspan="4" align="right" bgcolor="#006633">
	                        	<table border="0" cellpadding="0" cellspacing="0">
	                            	<tbody>
	                            		<tr>
	                                    	<td class="btn" width="100">
	                                    		<!-- <input name="Submit" value="Submit" class="btnform" type="submit"> -->
	                                    		<html:submit value="Submit" styleClass="btnform" styleId="submit"/>
	                                    	</td>
	                                    	<td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	                                    	<td class="btn" width="100">
	                                        	<%-- <input type="hidden" name="Admin_Input_Type" value="AreaMaster" />		
	                                        	<input type="hidden" name="AreaIdtxt" value="<%=aid%>" /> --%>
	                                        	<!-- <input name="Reset" value="Cancel" class="btnform" type="reset"></td> -->
	                                        	
	                                        	<html:reset value="Cancel" styleClass="btnform" styleId="reset"/>
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
            <table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
                <tbody>
                	<tr>		
                        <td align="center">

                            <div id="areadetails">	</div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
        </div>
    </body>
</html>