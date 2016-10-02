<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<script type="text/javascript" src="${ contextPath }/resources/js/enums.js"></script>
<script type="text/javascript" src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript" src="${ contextPath }/resources/js/mySelectFunction.js"></script>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>

        <script type="text/javascript">
        	var textProperty = [];
	        textProperty.push({name:"name"}); 
	        textProperty.push({name:"description"});
			
	        var actionPerform = new enums.Enum("create", "update", "delete");
        	var actionStatus = actionPerform.create;
        	var form = null;
        	var federId = null;
        	
        	var states = ${states};
        	
        	var sites = null;
        	var state = null;
        	var site = null;
        	states.sort(function(v, w){
				return v["name"].toLowerCase().localeCompare(w["name"].toLowerCase());
			});
        	
        	function changeValueOfSubmit(value){
    			var submit = document.getElementById("submit");
    			submit.setAttribute("value", value);
    		}
        	
        	function createFeder(){
        		federId = null;
        		actionStatus = actionPerform.create;
        		changeValueOfSubmit("Submit");
        	}
        	
        	function copyFeder(th){
        		createFeder();
        		$.fn.enableSelection(["#stateSelection","#siteSelection"]);
        		setFormValues($(th).parent().parent());
        	}
        	
        	function setFormValues(row){
				$.each(textProperty, function (index, property) {
					var pr = property["name"];
					
    				form.find("[name='" + pr + "']").val(row.find("." + pr).text());
				});
				
    		}
        	
        	function editFeder(th){
        		var row = $(th).parent().parent();
        		disableSelectionField();
        		changeValueOfSubmit("Update");
    			federId = row.attr('data-id');
    			actionStatus = actionPerform.update;
    			setFormValues($(th).parent().parent());
    			console.log(row.find(".workingStatus").text())
    			if(row.find(".workingStatus").text() == "Active"){
    				//console.log("checked")
    				$("#workingStatus").prop('checked', false); 			
    			}
    			if(row.find(".workingStatus").text() == "De-active"){
    				$("#workingStatus").prop('checked', true);
    				//console.log("UNchecked")		
    			}
    		}
        	
        	function disableSelectionField(){
        		$.fn.disableSelection(["#stateSelection", "#siteSelection"]);
        		$.fn.disableSelection(["#stateSelection"]);
        	}
        	
			$(document).ready(function(){
				form = $("form[name='FederMasterForm']");
				
				$.fn.initializeSelectionField($("#stateSelection"), states, "id", "name");
				//console.dir(states)
				$("#stateSelection").change(function(){
					initialize();
					state = $.fn.getVo($(this).find(":selected").val(), states, "id");
					$.each(state["areas"], function(index, element){
						sites = sites.concat(element["sites"]);
					});
					sites.sort(function(v, w){
						return v["name"].toLowerCase().localeCompare(w["name"].toLowerCase());
					});
					$.fn.initializeSelectionField($("#siteSelection"), sites, "id", "name");

					//Form - Selection - Submit - Form Value Reset - Action - Others
					function initialize(){

						form.find("[name='name']").val("");
						form.find("[name='description']").val("");
						
						$.fn.emptySelection(["#siteSelection"]);
						createFeder();
						
						site = null;
						sites = [];
						state  = null;
						
						document.getElementById("fddetails").innerHTML = "";
					}
				});
				
				$("#siteSelection").change(function(){
					initialize();
					site = $.fn.getVo($(this).find(":selected").val(), sites, "id");
					populateName(site);
					populateDescription(site);
					displayFeders(site);
					
					function populateName(site){
						form.find("[name='name']").val("IEIL-"+state["sapCode"].trim() + "-" + site["code"].trim() + "-");
					}
					function populateDescription(site){
						form.find("[name='description']").val("IEIL-"+state["sapCode"].trim() + "-" + site["code"].trim() + "-");
					}
					function displayFeders(site){
						var feders = site["feders"];
						var divdetails = document.getElementById("fddetails");
						divdetails.innerHTML = "";
						if(feders.length == 0) return;
						var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>";
						str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>";
						str+="<th class='detailsheading' width='200'>Description</th><th class='detailsheading' width='200'>Short Description</th>";
						str+="<th class='detailsheading' width='60'>Status</th>";
						str+="<th class='detailsheading' width='30'>Edit</th><th class='detailsheading' width='40'>Copy</th></tr>";
						for (var I = 0 ; I < feders.length ; I++)
					   	{	  	
					     	var item = feders[I];
							if (I % 2 == 0) {
								str+="<tr data-id='" +item["id"] + "'align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>";
							}
							else {
								str+="<tr data-id='" +item["id"] + "' align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>";
							}
							str+="<td class='name' align='left'>" + item["description"] + "</td>";
				     		str+="<td class='description' align='left'>" + item["name"] + "</td>";				     						     	
				     		if (item["workingStatus"] == 1) {
				     			str+="<td class='workingStatus' align='left'>Active</td>";
				     		} else {
				     			str+="<td class='workingStatus' align='left'>De-active</td>";
				     		}
				     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' ";
				     		/* str+="onClick=findDetails('" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue + "','E')></td>"; */
				     		str+=" onClick=editFeder(this)></td>";
				     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' ";
				     		/* str+="onClick=findDetails('" + item.getElementsByTagName("fdid")[0].firstChild.nodeValue + "','C')></td></tr>" */
				     		str+=" onClick=copyFeder(this)></td></tr>";
					   	 	   	 	
						}
				    	divdetails.innerHTML = str;
						/* $.each(feders, function(index, element){
							
							console.log(element["name"]);
						}); */
					}
					
					//Form - Selection - Submit - Action - Global Variables
					function initialize(){
						form.find("[name='name']").val("");
						form.find("[name='description']").val("");
						
						site = null;
						
						document.getElementById("fddetails").innerHTML = "";
					}
				});
				
				function initialize(){
					resetForm();
        			resetGlobalVariables();
        			
        			function resetGlobalVariables(){
        				createFeder();
        				site = null;
    					sites = [];
    					state  = null;
        			}
        			
        			function resetForm(){
        				$.fn.enableSelection(["#stateSelection", "#siteSelection"]);
        				changeValueOfSubmit("Submit");
        				document.getElementById("fddetails").innerHTML = "";
        			}
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
        			$.fn.enableSelection(["#siteSelection"]);
        			updateFormAction();
        			console.log("Action: " + $(form).attr("action"))
        			return true;
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
    	        			var queryParameter = {"id":federId, "method": actionStatus.name};
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
        				
        				if($('#stateSelection').val() == "ns"){
             				alert("Please select State.");
             				return false;
             			}
        				if($('#siteSelection').val() == "ns"){
             				alert("Please select Site.");
             				return false;
             			}
         				/* if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Please enter Name.")){
             				return false;
             			}	
         				if($.fn.isInputTextFieldEmptyWithErrorMessage("#description", "Please enter Description.")){
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
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                <tr width="100%">
                    <td width="100%" align="center">
                        <html:form action="manageFederMaster" method="post">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Feder Master</th>
                                        <td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                                        <td class="newhead3">&nbsp;</td>
                                        <td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                                    </tr>
                                    <tr>
                                        <td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
                                        <td colspan="3">
                                            <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                            	<tbody>
                                            		<tr>
                                            			<td bgcolor="#dbeaf5">
                                                            <table border="0" cellpadding="2" cellspacing="1" width="100%">
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
                                                                        <td id="t_general_information">Site Name:</td>
                                                                        <td bgcolor="#ffffff" width="85%">
                                                                            <html:select property="siteId" styleId="siteSelection" styleClass="ctrl">
                                                                                <option value="ns">--Make a Selection--</option>
                                                                            </html:select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff">
                                                                        <td width="15%" id="t_company">Name</td>
                                                                        <td width="85%" valign="top">
                                                                        	<%-- <input name="FDDesctxt" id="FDDesctxt" value="<%=fddesc%>" size="25" class="ctrl" type="text" /> --%>
                                                                        	<html:text property="name" size="25" styleClass="ctrl" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff"> 
                                                                        <td id="t_street_address">Short Description</td>
                                                                        <td bgcolor="#ffffff">
                                                                        	<%-- <input name="FDShorttxt" id="FDShorttxt" value="<%=fdshort%>" size="50" maxlength="100" class="ctrl" type="text" /> --%>
                                                                        	<html:text property="description" size="50" maxlength="100" styleClass="strl" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr bgcolor="#ffffff">
                                                                        <td id="t_general_information">Deactivate</td>
                                                                        <td bgcolor="#ffffff">
                                                                            <%-- <%if (status == 0) { %>
                                                                            <input type="checkbox" name="FDStatustxt" id="FDStatustxt" value="1" />
                                                                            <%} else { %>
                                                                            <input type="checkbox" name="FDStatustxt" id="FDStatustxt" checked value="2" />
                                                                            <%}%>
                                                                             --%>
                                                                            <html:checkbox property="workingStatus"  styleClass="ctrl"  styleId="workingStatus" value="2"/>
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
                                            <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                        </td>
                                        <td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                    </tr>
                                    <tr>
                                        <td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
                                        <td colspan="4" align="right" bgcolor="#006633">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                                <tbody>
                                                	<tr>
                                                        <td class="btn" width="100">
                                                        	<!-- <input name="Submit" value="Submit" class="btnform" type="submit"> -->
                                                        	<html:submit value="Submit" styleClass="btnform" styleId="submit"/>
                                                        </td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                        <td class="btn" width="100">
                                                            <%-- <input type="hidden" name="Admin_Input_Type" value="FDMaster" />		
                                                            <input type="hidden" name="FDIdtxt" value="<%=fdid%>" />
                                                            <input type="hidden" name="RoleId" value="<%=roleid%>"/>
                                                            <input type="hidden" name="copytxt" id="copytxt" /> --%>
                                                            <!-- <input name="Reset" value="Cancel" class="btnform" type="reset"> -->
                                                            
	                                        				<html:reset value="Cancel" styleClass="btnform" styleId="reset"/>	
                                                        </td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
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
                        <table border="0" cellpadding="0" cellspacing="0" width="600"><tbody>
                                <tr>
                                    <td>
                                        <div id="fddetails"></div>
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