<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var = "srNo" scope="page" value="0"/>
<c:set var = "class" scope="page" value=""/>

<%@ page isELIgnored="false" %>
<script type="text/javascript" src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/enums.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/mySelectFunction.js"></script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="${ contextPath }/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="${ contextPath }/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="${ contextPath }/resources/js/ajax.js"></script>

        <script type="text/javascript" src="${ contextPath }/resources/tablesorter/js/jquery.tablesorter.js"></script>
        <script type="text/javascript" src="${ contextPath }/resources/tablesorter/js/jquery.scrollabletable.js"></script>
        <script type="text/javascript" src="${ contextPath }/resources/tablesorter/js/jquery.tablesorter.pager.js"></script>
        <script type="text/javascript" src="${ contextPath }/resources/tablesorter/js/picnet.table.filter.min.js"></script>

        <style type="text/css" title="currentStyle">
            @import "${ contextPath }/resources/media/css/demo_page.css";
            @import "${ contextPath }/resources/media/css/demo_table.css";
        </style>
        <%-- <script type="text/javascript" language="javascript" src="${ contextPath }/resources/media/js/jquery.js"></script> --%>
        <script type="text/javascript" language="javascript" src="${ contextPath }/resources/media/js/jquery.dataTables.js"></script>
        <script type="text/javascript" charset="utf-8">
	        var textProperty = [];
	        textProperty.push({name:"name"}); 
	        textProperty.push({name:"owner"});
			textProperty.push({name:"capacity"});
			textProperty.push({name:"mva"});
			textProperty.push({name:"highVoltage"});
			textProperty.push({name:"lowVoltage"});
			textProperty.push({name:"transformerCount"});
			textProperty.push({name:"remark"});
	        
            var actionPerform = null;
            var actionStatus = null;
            var substationId = null;
    		var form = null;
    		
    		function changeValueOfSubmit(value){
    			var submit = document.getElementById("submit");
    			submit.setAttribute("value", value);
    		}
    		
    		function editSubstation(th){
    			
    			var row = $(th).parent().parent();
    			//Form Values
    			setFormValues(row);
    			
    			//Selection Field
				$("#areaSelection").val($("#areaSelection option:first").val());
    			$.fn.disableSelection(["#areaSelection"]);
    			
    			//Submit
    			changeValueOfSubmit("Update");
    			
    			//Action Status
    			actionStatus = actionPerform.update;
    			
    			//Global variables 
    			substationId = row.attr('data-id');
    		
    			function setFormValues(row){
    				
    				$.each(textProperty, function (index, property) {
    					var pr = property["name"];
        				form.find("[name='" + pr + "']").val(row.find("." + pr).text());
    				});
        			
    				//logger("Id: " + id + "\n Description: " + description + ",\n From Date: " + fromDate + ",\n To Date: " + toDate);
        		}
    			
    		}
            $(document).ready(function(){
            	$('#example').dataTable({
                    "sScrollY": 200,
                    "sScrollX": "100%",
                    "sScrollXInner": "100%"
                });
            	actionPerform = new enums.Enum("create", "update", "delete");
        		actionStatus = actionPerform.create;
        		
        		form = $("form[name='SubstationMasterForm']");
        		var areaSelection = $('#areaSelection');
        		
        		function initialize(){
        			//Submit
        			changeValueOfSubmit("Submit");
        			
        			//Action Status
        			actionStatus = actionPerform.create;
        			
        			//Global Variables
        			substationId = null;
        			
        			function resetForm(){
        				form.find("[name='name']").val("");
            			form.find("[name='code']").val("");
            			form.find("[name='inCharge']").val("");
        			}
        			
        		}
        		
        		$("#reset").click(function(){
        			$("#areaSelection").prop("disabled", false);
        			initialize();
        			
        		});
        			
        		$(form).submit(function(){
        			if(!validateForm()){
        				return false;
        			}
        			if(!getConfirmation()){
        				return false;
        			}
        			$.fn.enableSelection(['#areaSelection']);
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
    	        			var queryParameter = {"id":substationId, "method": actionStatus.name};
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
        				
        				if($('#areaSelection').val() == "ns" && actionStatus == actionPerform.create){
             				alert("Please select Area.");
             				return false;
             			}
         				
         				if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Name cannot be blank.")){
             				return false;
             			}
         				
         				if($.fn.isInputTextFieldEmptyWithErrorMessage("#capacity", "Please enter Capacity")){
             				return false;
             			}
         				
         				if($.fn.isInputTextFieldEmptyWithErrorMessage("#mva", "Please enter MVA")){
             				return false;
             			}        				
 						if(isNaN($("#mva").val())){
 							alert("Please Enter MVA as Number");
 							return false;
 						}
         				
 						if($.fn.isInputTextFieldEmptyWithErrorMessage("#highVoltage", "Please enter High Voltage")){
             				return false;
             			}
 						
 						if(isNaN($("#highVoltage").val())){
 							alert("Please Enter High Voltage as Number");
 							return false;
 						}
 						
 						if($.fn.isInputTextFieldEmptyWithErrorMessage("#lowVoltage", "Please enter Low Voltage")){
             				return false;
             			}					
 						if(isNaN($("#lowVoltage").val())){
 							alert("Please Enter Low Voltage as Number");
 							return false;
 						}
 						
 						if($.fn.isInputTextFieldEmptyWithErrorMessage("#owner", "Please enter Owner Name")){
             				return false;
             			}
 						
 						if($.fn.isInputTextFieldEmptyWithErrorMessage("#transformerCount", "Please enter Transformer")){
             				return false;
             			}						
 						if(isNaN($("#transformerCount").val())){
 							alert("Please Enter Transformer as Number");
 							return false;
 						}
 						
 						if($.fn.isInputTextFieldEmptyWithErrorMessage("#remark", "Please enter Remarks")){
             				return false;
             			}
        				
        				return true;
        			}
        		});
            
        	});
        </script>

        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>
    </head>
    <body><!-- onload="findApplication()" -->
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
                <tr width="100%">
                    <td width="100%" align="center">
                        <html:form action="/manageSubstationMaster.do" method="post">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Sub Station Master</th>
                                        <td><img src="${ contextPath }/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                                        <td class="newhead3">&nbsp;</td>
                                        <td class="newhead4"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                                    </tr>
                                    <tr>
                                        <td background="${ contextPath }/resources/images/line_l.gif">&nbsp;</td>
                                        <td colspan="3">
                                            <img src="${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                            	<tbody>
                                            		<tr>
                                            			<td bgcolor="#dbeaf5">
                                                            <table border="0" cellpadding="2" cellspacing="1" width="180%">
                                                                <tbody>
                                                                    <tr bgcolor="#ffffff">
                                                                        <td id="t_company" width="219">Select Area:</td>
                                                                        <td valign="top">
                                                                            <select name="areaId" id="areaSelection" class="ctrl"><!--  onChange="findApplication()" -->
                                                                                <option value="ns">--Make Selection--</option>
																				<jsp:include page="/jsppages/utility/SelectVoTemplateV2.jsp">
																					<jsp:param name="beanIdentifier" value="areas" />
																					<jsp:param name="option" value="id" />
																					<jsp:param name="text" value="name" />
																				</jsp:include>
                                                                            </select>
                                                                        </td>
                                                                    </tr>			
                                                                    <tr bgcolor="#ffffff">
                                                                        <td id="t_general_information">Substation Name:</td>
                                                                        <td>
                                                                        	<html:text property="name" styleId="name" styleClass="ctrl"  size="30" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Capacity:</td>
                                                                        <td>
                                                                        	<html:text property="capacity" size="10" styleClass="ctrl" styleId="capacity" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation MVA:</td>
                                                                        <td>
                                                                        	<html:text property="mva" size="10" styleClass="ctrl" styleId="mva"/>
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Voltage(H):</td>
                                                                        <td>
                                                                        	<html:text property="highVoltage" size="10" styleClass="ctrl" styleId="highVoltage"/>
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Voltage(L):</td>
                                                                        <td>
                                                                        	<html:text property="lowVoltage" size="10" styleClass="ctrl" styleId="lowVoltage" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Owner:</td>
                                                                        <td>
                                                                        	<html:text property="owner" size="30" styleClass="ctrl" styleId="owner"/>
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Total Transformer:</td>
                                                                        <td>
                                                                        	<html:text property="transformerCount" size="30" styleClass="ctrl" styleId="transformerCount"/>
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Remarks:</td>
                                                                        <td>
                                                                        	<html:text property="remark" size="30" styleClass="ctrl" styleId="remark"/>
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
                            			<td background="${ contextPath }/resources/images/line_r.gif"><img src="${ contextPath }/resources/images/pixel.gif" border="0"></td>
                					</tr>
                					<tr>
                    					<td width="10">
                    						<img src="${ contextPath }/resources/images/formtab_b.gif" border="0" height="20" width="10">
                    					</td>
					                    <td colspan="4" align="right" bgcolor="#006633">
					                        <table border="0" cellpadding="0" cellspacing="0">
					                            <tbody>
					                            	<tr>
					                                    <td class="btn" width="100">
					                                    	<html:submit value="Submit" styleClass="btnform" styleId="submit"/>
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
					                                    <td class="btn" width="100">
					                                        <!-- <input name="Reset" value="Cancel" class="btnform" type="reset"> -->
					                                        <html:reset value="Cancel" styleClass="btnform" styleId="reset"/>
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
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
			</table>
		</div>
		<table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="90%">
		    <thead>
		        <tr align="center"> 
		            <th width='15'>S.N.</th>
		            <th align="left" width='115'>Substation Name</th>
		            <th align="center" width='70'>Total Feeder</th>
		            <th align="center" width='70'>Total Transformer</th>
		            <th align="left" width='115'>Substation belongs To</th>
		            <th align="center" width='50'>Capacity</th>
		            <th align="center" width='50'>MVA</th>
		            <th align="center" width='50'>High Vol</th>
		            <th align="center" width='50'>Low Vol</th>
		            <th align="right" width='12'>Edit</th>
		        </tr>
		    </thead>
		    
		    
    		<tbody>
    			<c:forEach var="substationVo" items="${ substations }">
					<c:set var = "srNo" value="${srNo + 1}"/>

				<%-- <tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="stateVo" property="id"/>'>
					
					<td ALIGN='center'>${srNo}</td>
					<td align='left' class="name"><bean:write name="stateVo" property="name"/></td>
					<td align='left' class="sapCode"><bean:write name="stateVo" property="sapCode"/></td>
					
					<td align='center'>
						<input type='image' class = "edit" src='${ contextPath }/resources/images/edit.gif' alt='Click to edit the record'>
					</td>
					<!-- <td align='center'>
						<input type='image' class = "delete" src='/WCARE/resources/images/delet.gif' alt='Click to edit the record' />
					</td>
					<td align='center'>
						<input type='image' class="sent-message" src='/WCARE/resources/menu/mifuncs.gif' alt='Click to send the message' />
					</td> -->
				</tr> --%>
				
		        <tr align="center" class="gradeA" data-id="${ substationVo.id }">	
		            <td>${srNo}</td>
		            <td align="left" class="name">${ substationVo.name }</td>
		            <td align="center" class="totalFeeder">${fn:length(substationVo.feeders)}</td>
		            <td align="left" class="transformerCount">${ substationVo.transformerCount }</td>
		            <td align="left" class="owner">${ substationVo.owner }</td>
		            <td align="center" class="capacity">${ substationVo.capacity }</td>
		            <td align="center" class="mva">${ substationVo.mva }</td>
		            <td align="center" class="highVoltage">${ substationVo.highVoltage }</td>
		            <td align="center" class="lowVoltage">${ substationVo.lowVoltage }</td>
		            <td align="center">
		            	<input class="edit" type='image' src="${ contextPath }/resources/images/edit.gif" onclick="editSubstation(this)">
		            </td>
		        </tr>
				</c:forEach>
    		</tbody>	
		</table>
	</body>
</html>