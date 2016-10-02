<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>

 <%-- <%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %> --%>
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
       
		var form = null;
		var states = ${states}; //List of Javascript Object
		var stateSelectionField = null;
		var areaSelectionField = null;
		var areaId = null;
					
		var stateId = null;
		var stateVo = null;
		var areas = null;
		
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
        	
        	//console.dir(states);
        	actionPerform = new enums.Enum("create", "update", "delete");
    		actionStatus = actionPerform.create;
    		
    		form = $("form[name='AreaMasterForm']");
    		stateSelectionField = $('#stateSelection');
    		states.sort(function(v, w){
				return v["name"].toLowerCase().localeCompare(w["name"].toLowerCase());
			});
    		$.fn.initializeSelectionField(stateSelectionField, states, "id", "name");
 
    		function initialize(){
    			actionStatus = actionPerform.create;
    			areaId = null;
    			resetForm();
    			
    			function resetForm(){
    				form.find("[name='name']").val("");
        			form.find("[name='code']").val("");
        			form.find("[name='inCharge']").val("");
        			
        			//stateSelection.val($("#stateSelection option:first").val());      			
    			}
    			changeValueOfSubmit("Submit");
    		}
    		
    		$('#stateSelection').change(function(){
    			
    			stateId = $(this).find(":selected").val();
    			//console.log(stateId);
    			if(stateId != "ns") {
    				stateVo = $.fn.getVo(stateId, states,"id");
    				areas = stateVo["areas"];  
    			} else {
    				areas = [];
    			}  
    			displayAreas(areas)

    			function displayAreas(areas){
    				var divdetails = document.getElementById("areadetails");
					divdetails.innerHTML = "";
					if(areas.length == 0) return;
					areas.sort(function(v, w){
						return v["name"].toLowerCase().localeCompare(w["name"].toLowerCase());
					});
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
    				/* if(actionStatus == actionPerform.update){
    	    			var queryParameter = {"id":areaId};
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
    				
    				if(actionStatus == actionPerform.update){
    					$(form).attr("action", action + "/update" + queryString);
    				} else {
    					$(form).attr("action", action + "/create");
    				} */
    				
    				if(actionStatus == actionPerform.update){
    	    			var queryParameter = {"id":areaId};
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
    				return true;
    			}
    		});
    	});
        
        </script>
    </head>
    <body>
        <div align="center">            
            <sf:form method="post" action="${pageContext.request.contextPath}/spring/areaMaster" modelAttribute="area" id="AreaMasterForm" name="AreaMasterForm">
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
                                                               <select name="stateId" id="stateSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Name :</td>
                                                            <td valign="top">                                                                
                                                                <sf:input cssClass="ctrl" path="name" size="15" id="areaName" type="text" />
                                                             </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Short Code :</td>
                                                            <td valign="top">                                                               
                                                                <sf:input cssClass="ctrl" path="code" size="15" id="areaCode" type="text" />
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Area Incharge :</td>
                                                            <td valign="top">                                                              
                                                               <sf:input cssClass="ctrl" path="inCharge" size="25" id="areaInCharge" type="text" maxlength="25"/>
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
	                                    		<input value="Submit" id="submit" class="btnform" type="submit"/>
	                                    	</td>
	                                    	<td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	                                    	<td class="btn" width="100">	                                        	
	                                        	<input type="reset" value="Cancel" id="reset" class="btnform" />	                                        	
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