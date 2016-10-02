<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
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
    			
    			console.log(row.attr('data-id'));
    			console.log(row.attr('area-id'));
    			
    			
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
        				if(actionStatus == actionPerform.update){
        	    			var queryParameter = {"id":substationId};
        				} else {
        					var queryParameter = {};
        				}
        				
        				var originalAction = $(form).attr("action");
        				var index = originalAction.indexOf("?");
        				var action = null;
        				if(index != -1){
        					action = originalAction.substring(0, index).replace("/create", "").replace("/update", "");
        				} else {
        					action = originalAction.replace("/create", "").replace("/update", "");
        				}
        				
        				var queryString = "?";
        				for(var param in queryParameter){
        					queryString += param + "=" + queryParameter[param] + "&";
        				}
        				index = $(form).attr("action").indexOf("?");
        				
        				if(actionStatus == actionPerform.update){
        					$(form).attr("action", action + "/update" + queryString);
        				} else {
        					$(form).attr("action", action + "/create");
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
                        <sf:form method="post" action="${pageContext.request.contextPath}/spring/substationMaster" modelAttribute="substation" id="SubstationMasterForm" name="SubstationMasterForm">
                        <%-- <html:form action="/manageSubstationMaster.do" method="post"> --%>
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
                                                                            <select name="areaId" id="areaSelection" class="ctrl">
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
                                                                        	 <sf:input cssClass="ctrl" path="name" size="30" id="name" type="text" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Capacity:</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="capacity" size="10" id="capacity" type="text" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation MVA:</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="mva" size="10" id="mva" type="text" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Voltage(H):</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="highVoltage" size="10" id="highVoltage" type="text" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Voltage(L):</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="lowVoltage" size="10" id="lowVoltage" type="text" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Substation Owner:</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="owner" size="30" id="owner" type="text" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Total Transformer:</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="transformerCount" size="30" id="transformerCount" type="text" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_city">Remarks:</td>
                                                                        <td>                                                                        	
                                                                        	<sf:input cssClass="ctrl" path="remark" size="30" id="remark" type="text" />
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
					                                    	<input value="Submit" id="submit" class="btnform" type="submit"/>
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
					                                    <td class="btn" width="100">					                                       
					                                        <input type="reset" value="Cancel" id="reset" class="btnform" />	
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
					                                </tr>
					                            </tbody>
											</table>
					                    </td>
                					</tr>
                				</tbody>
                			</table>
        				</sf:form>
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
			        <tr align="center" class="gradeA" data-id="${ substationVo.id }" area-id="${ substationVo.areaId }">	
			            <td>${srNo}</td>
			            <td align="left" class="name">${ substationVo.name }</td>
			            <td align="center" class="totalFeeder">${fn:length(substationVo.feeders)}</td>
			            <td align="left" class="transformerCount">${ substationVo.transformerCount }</td>
			            <td align="left" class="owner">${ substationVo.owner }</td>
			            <td align="center" class="capacity">${ substationVo.capacity }</td>
			            <td align="center" class="mva">${ substationVo.mva }</td>
			            <td align="center" class="highVoltage">${ substationVo.highVoltage }</td>
			            <td align="center" class="lowVoltage">${ substationVo.lowVoltage }</td>
			            <td align="center" class="remark" style='display:none'>${ substationVo.remark }</td>
			            <td align="center">
			            	<input class="edit" type='image' src="${ contextPath }/resources/images/edit.gif" onclick="editSubstation(this)">
			            </td>
			        </tr>
				</c:forEach>
    		</tbody>	
		</table>
	</body>
</html>