<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var="srNo" scope="page" value="0" />
<c:set var="class" scope="page" value="" />

<%@ page isELIgnored="false"%>

<!-- <script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/interface/SiteMasterResource.js"> </script> -->
<script type="text/javascript"
	src="${ contextPath }/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
<script type="text/javascript"
	src="${ contextPath }/resources/js/enums.js"></script>
<script type="text/javascript"
	src="${ contextPath }/resources/js/mySelectFunction.js"></script>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/screen.css"
	type="text/css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/MYCSS.css"
	type="text/css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
	if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
<script type="text/javascript">
        var actionPerform = new enums.Enum("create", "update", "delete");
		var actionStatus = actionPerform.create;
			
		function changeValueOfSubmit(value){
			var submit = document.getElementById("submit");
			submit.setAttribute("value", value);
		}
	
		var form = null;
		var states = ${states};//List of Javascript Object
		var stateSelectionField = null;
		var siteSelectionField = null;
		var federSelectionField = null;
		var customerSelectionField = null;
		var stateId = null;
		var siteId = null;
		var stateVo = null;
		var siteVo = null;
		var sites = null;
		var feders = null;
		var ebId = null;
		
		function editEb(thisEb){
			
			stateSelectionField.prop("disabled", true);
			siteSelectionField.prop("disabled", true);			
			
			var row = $(thisEb).parent().parent();
			ebId = row.attr('data-id');
			actionStatus = actionPerform.update;
			setFormValues(row);
			changeValueOfSubmit("Update");
		
			function setFormValues(row){
				
				$("#name").val(row.find(".name").text());
				$("#description").val(row.find(".description").text());		
				$("#federSelection").find("option:contains('"+row.find(".feder").text()+"')").each(function(){
					 if( $(this).text() == row.find(".feder").text()){
					 		 $(this).attr("selected","selected");
					  }
				});
				$("#customerSelection").find("option:contains('"+row.find(".customer").text()+"')").each(function(){
					 if( $(this).text() == row.find(".customer").text()){
					 		 $(this).attr("selected","selected");
					  }
				});				
    		}
		}
		
		function createEb(){
    		ebId = null;
    		actionStatus = actionPerform.create;
    		changeValueOfSubmit("Submit");
    	}
    	
    	function copyEb(thisEb){
    		
    		createEb();
    		$.fn.enableSelection(["#stateSelection","#siteSelection"]);   		    		
    		var row = $(thisEb).parent().parent();
    		$("#name").val(row.find(".name").text());
			$("#description").val("");		
			
			$("#federSelection").find("option:contains('"+row.find(".feder").text()+"')").each(function(){
				 if( $(this).text() == row.find(".feder").text()){  
					    $(this).attr("selected","selected"); 
				 }
			});
			$("#customerSelection").find("option:contains('"+row.find(".customer").text()+"')").each(function(){
				 if( $(this).text() == row.find(".customer").text()){
				  		$(this).attr("selected","selected");
				  		 
				  }
			});		
    	}
    	
        $(document).ready(function(){
        	//console.dir(states)
        	form = $("form[name='EbMasterForm']");
        	stateSelectionField = $('#stateSelection');
        	siteSelectionField = $('#siteSelection');
        	federSelectionField = $('#federSelection');
        	ebDetails = document.getElementById("ebDetails");
        	$.fn.initializeSelectionField(stateSelectionField, states, "id", "name");
        	
        	 
        	
    		function initialize(){
    			stateSelectionField.prop("disabled", false);
    			siteSelectionField.prop("disabled", false);
    			actionStatus = actionPerform.create;
    			ebId = null;
    			ebDetails.innerHTML = "";
    			changeValueOfSubmit("Submit");
    			
    			$("#name").val(" ");
	   			$("#description").val(" ");
	   			document.getElementById("stateSelection").selectedIndex=0;
	   			document.getElementById("siteSelection").selectedIndex=0;
	   			document.getElementById("federSelection").selectedIndex=0;
	   			document.getElementById("customerSelection").selectedIndex=0;
    		}
    		
    		$('#stateSelection').change(function(){
  			
    			$('#siteSelection').empty().append(new Option("--Make a Selection--", "ns"));

    			stateId = $(this).find(":selected").val();
    			stateVo = getStateVo(stateId, states);
    			sites =stateVo["sites"]
    			$.fn.initializeSelectionField(siteSelectionField, sites,"id", "name");
    			
    			function getStateVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }   			
    		});
    		
    		$('#siteSelection').change(function(){
				
    			$('#federSelection').empty().append(new Option("--Make a Selection--", "ns"));
                    
	   			siteId = $(this).find(":selected").val();
	   			siteVo = getSiteVo(siteId, stateVo["sites"]);
	   			feders = siteVo["feders"];

	   			$.fn.initializeSelectionField(federSelectionField, feders,"id", "name");
	   			$("#name").val("IEIL-"+stateVo["sapCode"]+"-"+siteVo["code"]+"-");
	   			$("#description").val("IEIL-"+stateVo["sapCode"]+"-"+siteVo["code"]+"-");
	   			
	   			displayEbs(sites);
	   			
	   			function displayEbs(sites){
	   									
					var ebDetails = document.getElementById("ebDetails");
					ebDetails.innerHTML = "";									
					var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
					str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='250'>EB Description</th>"
					str+="<th class='detailsheading' width='150'>Short Description</th><th class='detailsheading' width='150'>Feder</th>"
					str+="<th class='detailsheading' width='150'>Customer</th><th class='detailsheading' width='40'>Staus</th>"				
					str +="<th class='detailsheading' width='40'>Edit</th><th class='detailsheading' width='40'>Copy</th></tr>";
					
					var classPicker = null;
		    						
					$.each(sites, function(index, site){
						if(site["id"] == siteId){						
							$.each(site["ebs"], function(index, eb){								
								var customers = [];
								$.each(eb["wecs"], function(index, wec){
									var customer = wec["customer"];
									if(wec["status"] != 2){
										var present = false;
										$.each(customers, function(index, cust){
											if(customer["id"] === cust["id"]){
												present = true;
											}
										});
										if(!present)customers.push(customer);
										//console.log("Customer " + customer["name"] + "(" + customer["id"] + ")");
									}
								});
								//console.log(customers.length);
								if(customers[0] != undefined && site["feders"][0] != undefined)
								 {
									if (index % 2 == 0) classPicker = "detailsbody";
			            			else classPicker = "detailsbody1";
						      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + eb["id"] + "'><td ALIGN='center'>"+(index+1)+"</td>";
				     				str+="<td class='name' align='left'>" + eb["description"] + "</td>";
				     				str+="<td class='description' align='left'>" + eb["name"] + "</td>";
				     				str+="<td class='feder' align='left'>" + site["feders"][0]["name"] + "</td>";
				     				str+="<td class='customer' align='left'>" + customers[0]["name"] + "</td>";
				     				if (eb["workingStatus"] == 1) {
						     			str+="<td class='workingStatus' align='left'>Active</td>";
						     		} else {
						     			str+="<td class='workingStatus' align='left'>De-active</td>";
						     		}
						     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' ";						     		
						     		str+=" onClick=editEb(this)></td>";
						     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' ";						     		
						     		str+=" onClick=copyEb(this)></td></tr>";				     				
								} 
									//console.log(eb["description"] + ", " + eb["name"] + ", " + site["feders"][0]["name"] + ", " + customers[0]["name"] + ", " + eb["workingStatus"]);
							});
						}
					});

					ebDetails.innerHTML = str;	
				}

	   			function getSiteVo(id, vos){
			    	for(var vo of vos){
           			if(vo.id == id)
           				return vo;
           		    }
				}   
	   			
    		});
    		
    		$("#reset").click(function(){
    			initialize();
    		});
    			
    		$(form).submit(function(){
    			
    			siteId = $("#siteSelection").find(":selected").val();
    			if(!validateForm()){
    				return false;
    			}
    			if(!getConfirmation()){
    				return false;
    			}
    			$.fn.enableSelection(["#siteSelection"]);
    			updateFormAction();
    			//console.log("Action: " + $(form).attr("action"))
    			
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
	        			var queryParameter = {"id":ebId, "method": actionStatus.name};
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
    				if($('#federSelection').val() == "ns"){
         				alert("Please select Feder.");
         				return false;
         			}
    				if($('#customerSelection').val() == "ns"){
         				alert("Please select Custromer.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Please enter  Name ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#description", "Please enter Description ")){
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
		<html:form action="manageEbMaster" method="post"
			styleId="EbMasterForm">
			<table align="center" border="0" cellpadding="0" cellspacing="0"
				width="604">
				<tbody>
					<tr>
						<td class="newhead1" width="10"></td>
						<th class="headtext" width="110">EB Master</th>
						<td width="475"><img
							src="<%=request.getContextPath()%>/resources/images/formtab_r.gif"
							border="0" height="21" width="10"></td>
						<td class="newhead3" width="7">&nbsp;</td>
						<td class="newhead4" width="10"><img
							src="<%=request.getContextPath()%>/resources/images/pixel.gif"
							border="0" height="1" width="10"></td>
					</tr>
					<tr>
						<td
							background="<%=request.getContextPath()%>/resources/images/line_l.gif"
							width="10"><img
							src="<%=request.getContextPath()%>/resources/images/pixel.gif"
							border="0" width="1" height="1"></td>
						<td colspan="3" width="592"><img
							src="<%=request.getContextPath()%>/resources/images/pixel.gif"
							border="0" height="10" width="1"><br>
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tbody>
									<tr>
										<td bgcolor="#dbeaf5">
											<table border="0" cellpadding="2" cellspacing="1" width="592">
												<tbody>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Select State:</td>
														<td valign="top"><select name="stateId"
															id="stateSelection" class="ctrl">
																<option value="ns">--Make a Selection--</option>
														</select></td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Select Site:</td>
														<td valign="top"><select name="siteId"
															id="siteSelection" class="ctrl">
																<option value="ns">--Make a Selection--</option>
														</select></td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Feder:</td>
														<td valign="top"><select name="federId"
															id="federSelection" class="ctrl">
																<option value="ns">--Make a Selection--</option>
														</select></td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Customer:</td>
														<td valign="top"><select name="customerId"
															id="customerSelection" class="ctrl">
																<option value="ns">--Make a Selection--</option>
																<jsp:include
																	page="/jsppages/utility/SelectVoTemplateV2.jsp">
																	<jsp:param name="beanIdentifier" value="customer" />
																	<jsp:param name="option" value="id" />
																	<jsp:param name="text" value="name" />
																</jsp:include>
														</select></td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Name:</td>
														<td valign="top"><html:text property="name"
																styleId="name" size="25" styleClass="ctrl" /></td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company" width="219">Short Description:</td>
														<td valign="top"><input type="text"
															name="description" id="description" class="ctrl"
															size="55" /> <%-- <html:text property="description" styleId="description" size="55" styleClass="ctrl" />  --%>
														</td>
													</tr>
													<tr bgcolor="#ffffff">
														<td id="t_company">Deactivate</td>
														<td bgcolor="#ffffff"><html:checkbox
																property="workingStatus" styleClass="ctrl"
																styleId="workingStatus" value="2" /></td>
													</tr>
													<logic:messagesPresent property="success" message="true">
														<tr class="bgcolor">
															<td colspan="2" class="sucessmsgtext"><html:messages
																	id="msg" name="success" message="true"
																	bundle="stdmessage">
																	<bean:write name="msg" />
																</html:messages></td>
														</tr>
													</logic:messagesPresent>
													<logic:messagesPresent property="error" message="true">
														<tr class="bgcolor">
															<td colspan="2" class="errormsgtext"><html:messages
																	id="msg" name="error" message="true"
																	bundle="stdmessage">
																	<bean:write name="msg" />
																</html:messages></td>
														</tr>
													</logic:messagesPresent>
													<logic:messagesPresent property="exists" message="true">
														<tr class="bgcolor">
															<td colspan="2" class="errormsgtext"><html:messages
																	id="msg" name="exists" message="true"
																	bundle="stdmessage">
																	<bean:write name="msg" />
																</html:messages></td>
														</tr>
													</logic:messagesPresent>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table> <img
							src="<%=request.getContextPath()%>/resources/images/pixel.gif"
							border="0" height="10" width="1"><br></td>
						<td
							background="<%=request.getContextPath()%>/resources/images/line_r.gif"
							width="10"><img
							src="<%=request.getContextPath()%>/resources/images/pixel.gif"
							border="0" width="1" height="1"></td>
					</tr>
					<tr>
						<td width="10"><img
							src="<%=request.getContextPath()%>/resources/images/formtab_b.gif"
							border="0" height="20" width="10"></td>
						<td colspan="4" align="right" bgcolor="#006633">
							<table border="0" cellpadding="0" cellspacing="0">
								<tbody>
									<tr>
										<td class="btn" width="100"><html:submit value="Submit"
												styleClass="btnform" styleId="submit" /></td>
										<td width="1"><img
											src="<%=request.getContextPath()%>/resources/images/pixel.gif"
											border="0" height="18" width="1"></td>
										<td class="btn" width="100">
										
										<%--  <html:reset value="Cancel"
												styleClass="btnform" styleId="reset" />  --%>
												<input type="button" value="Cancel" id="reset" class="btnform">
												
												</td>
										<td width="1"><img
											src="<%=request.getContextPath()%>/resources/images/pixel.gif"
											border="0" height="18" width="1"></td>
									</tr>
								</tbody>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
		</html:form>
		<table border="0" align="center" cellpadding="0" cellspacing="0"
			width="600">
			<tbody>
				<tr>
					<td align="center">

						<div id="ebDetails"></div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>