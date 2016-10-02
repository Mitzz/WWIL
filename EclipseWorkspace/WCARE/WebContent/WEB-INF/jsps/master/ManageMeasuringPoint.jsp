<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>

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

        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>
        <script type="text/javascript">
     	
         $(document).ready(function(){
        	
        	var actionPerform = new enums.Enum("create", "update", "delete");
        	var actionStatus = actionPerform.create;
        	var form = $("form[name='MeasuringPointMasterForm']");
        	
        	var id = null;
 
        	function initialize(){
        		actionStatus = actionPerform.create;
        		id = null;
        		resetForm();
        		
        		function resetForm(){
        			$("#desc").val("");
        			$("#type").val("");
        			$("#show").val("");
        			$("#unit").val("");
        			$("#seqNo").val("");
        			$("#readType").val("");        			
        			$("#status").prop('checked', false);        					
        			$("#cumulative").prop('checked', false);        					
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
        			//console.log(row.find(".readType").text())
        			var id = row.attr('data-id');
        			$("#desc").val(row.find(".desc").text());
        			$("#type").val(row.find(".type").text());
        			$("#show").val(row.find(".show").text());
        			$("#unit").val(row.find(".unit").text());
        			$("#seqNo").val(row.find(".seqNo").text());
        			
        			if(row.find(".readType").text()== 'ALL'){
        				$("#readType").val("A"); 
        			}else if(row.find(".readType").text()== 'Initial'){
        				$("#readType").val("I"); 
        			}else if(row.find(".readType").text()== 'Daily'){
        				$("#readType").val("D"); 
        			}else if(row.find(".readType").text()== 'Monthly'){
        				$("#readType").val("M"); 
        			}else if(row.find(".readType").text()== 'Yearly'){
        				$("#readType").val("Y"); 
        			}
        				       			
        			if(row.find(".status").text() == "Active"){        				
        				$("#status").prop('checked', false); 			
        			}
        			if(row.find(".status").text() == "De-Active"){
        				$("#status").prop('checked', true);        					
        			}
        			if(row.find(".cumulative").text() == "Yes"){        				
        				$("#cumulative").prop('checked', true); 			
        			}
        			if(row.find(".cumulative").text() == "No"){
        				$("#cumulative").prop('checked', false);        					
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
    			
    		$("#MeasuringPointMasterForm").submit(function(){
    			form = $("#MeasuringPointMasterForm");
    			if(!validateForm()){
    				return false;
    			}
    			if(!getConfirmation()){
    				return false;
    			}
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
    	    			var queryParameter = {"id":id};
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
    				
    				 if($.fn.isInputTextFieldEmptyWithErrorMessage("#desc", "Name cannot be blank.")){
    					return false;
    				}	
    				if($('#type').val() == "ns"){
          				alert("Please select Type.");
          				return false;
          			}
    				if($('#show').val() == "ns"){
          				alert("Please select Show In.");
          				return false;
          			}
    				if($('#unit').val() == "ns"){
          				alert("Please select Unit.");
          				return false;
          			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#seqNo", "Please enter Sequence Number.")){
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
          <%--   <html:form action="manageMeasuringPoint" method="post" styleId="MeasuringPointMasterForm"> --%>
          <sf:form method="post" action="${pageContext.request.contextPath}/spring/measuringPoint" modelAttribute="mp" id="MeasuringPointMasterForm" name="MeasuringPointMasterForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Measuring Master</th>
                            <td width="475"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                            <td class="newhead3" width="7">&nbsp;</td>
                            <td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                        </tr>
                        <tr>
                            <td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1">
                            </td>
                            <td colspan="3" width="592">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tbody>
                                        <tr>
                                            <td bgcolor="#dbeaf5">
                                                <table border="0" cellpadding="2" cellspacing="1" width="592">
                                                    <tbody>
                                                        <tr class="bgcolor">
															<td  id="t_company">Name:</td>
															<td colspan="3" valign="top">																									
																 <sf:input cssClass="ctrl" path="desc" size="20" id="desc" type="text" />
																<%-- <html:text property="desc" styleClass="ctrl" readonly="false" styleId="desc" size="20"></html:text> --%> 
															</td>
														</tr> 
														<tr class="bgcolor">
															<td id="t_company" >Type:</td>
															<td colspan="3" >									            	
												            	<select size="1" name="type" id="type" class="ctrl">                                                                   
                                                                    <option value="ns">-- select --</option>																																															 
																  	<option value="EB">EB</option>																 																  		 
																  	<option value="WEC">WEC</option>																 
                                                                </select>
												            </td>
														</tr>
														<tr class="bgcolor">
															<td id="t_company" >Show In:</td>
															<td colspan="3" >									            	
												            	<select size="1" name="show" id="show" class="ctrl">
                                                                    <option value="ns">-- select --</option>
                                                                    <option value="EB">EB</option>
                                                                    <option value="WEC">WEC</option>
                                                                    <option value="BOTH">BOTH</option>	
                                                                </select>
												            </td>
														</tr>
														<tr class="bgcolor">
															<td id="t_street_address" >Reading Type:</td>
															<td colspan="3" >									            	
												            	<select size="1" name="readType" id="readType" class="ctrl">
                                                                    <option value="A">ALL</option>
                                                                    <option value="I" >Initial</option>
																	<option value="D" >Daily</option>
																	<option value="M" >Monthly</option>
																	<option value="Y">Yearly</option>
                                                                </select>
												            </td>
														</tr>
														<tr class="bgcolor">
															<td id="t_street_address" >Unit:</td>
															<td colspan="3" valign="top">									            	
												            	<select size="1" name="unit" id="unit" class="ctrl">
                                                                    <option value="ns">-- select --</option>
                                                                    <option value="HOURS">HOURS</option>
                                                                    <option value="UNITS">UNITS</option>
                                                                    <option value="KW">KW</option>
                                                                </select>
												            </td>
														</tr>
														 <tr class="bgcolor">
															<td id="t_company" >Seq. No.:</td>
															<td colspan="3" valign="top">																												
																  <sf:input cssClass="ctrl" path="seqNo" size="20" id="seqNo" type="text" />
																<%-- <html:text property="seqNo" styleClass="ctrl" readonly="false" styleId="seqNo" size="20"></html:text> --%> 
															</td>
														</tr> 
														 <tr bgcolor="#ffffff">
                                                            <td id="t_general_information">Deactivate</td>
                                                            <td bgcolor="#ffffff">                                                                                                                                         
                                                                <%-- <html:checkbox property="status"  styleClass="ctrl"  styleId="status" value="2"/> --%> 
                                                                <input type="checkbox" value="2" class="ctrl" id="status" name="status"/>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_general_information">Cumulative</td>
                                                            <td bgcolor="#ffffff">                                                                                                                                      
                                                               <%--  <html:checkbox property="cumulative"  styleClass="ctrl"  styleId="cumulative" value="1"/> --%> 
                                                                <input type="checkbox" value="1" class="ctrl" id="cumulative" name="cumulative"/>
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
				                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
				                </td>
				                <td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="10">
				                    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1">
				                </td>
							</tr>
			                <tr>
			                    <td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
			                    <td colspan="4" align="right" bgcolor="#006633">
			                        <table border="0" cellpadding="0" cellspacing="0">
			                            <tbody>
			                                <tr>
			                                    <td class="btn" width="100">			                                    	
			                                    	<input value="Submit" id="submit" class="btnform" type="submit"/>
			                                    </td>
			                                    <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
			                                    <td class="btn" width="100">			                                       
			                                       <input type="reset" value="Cancel" id="reset" class="btnform" />	   
												</td>
			                                    <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
			                                </tr>
                            			</tbody>
                        			</table>
                    			</td>
                			</tr>
                		</tbody>
                	</table>
            	</sf:form>
            	<table border="0" align="center" cellpadding="0"  cellspacing="0" width="650">
					<tbody>
						<tr>		
							<td align="center">
								<div id="mpdetails">
									<table border='0' cellpadding='2' cellspacing='1' width='100%'>
										<tr align='center' height='20'>
											<th class='detailsheading' width='30'>S.N.</th>
											<th class='detailsheading' width='180'>Measuring Point</th>
											<th class='detailsheading' width='70'>Type</th>
											<th class='detailsheading' width='70'>Show In</th>
											<th class='detailsheading' width='70'>Read Type</th>
											<th class='detailsheading' width='70'>Unit</th>
											<th class='detailsheading' width='70'>SeqNo</th>
											<th class='detailsheading' width='70'>Status</th>
											<th class='detailsheading' width='70'>Cumulative</th>
											<th class='detailsheading' width='40'>Edit</th>
										</tr>
										<logic:iterate name="meaPoint" id="mpVo" >
											<c:set var = "srNo" value="${srNo + 1}"/>
				
										<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="mpVo" property="id"/>'>
											
											<td ALIGN='center'>${srNo}</td>
											<td align='left' class="desc"><bean:write name="mpVo" property="desc"/></td>
											<td align='left' class="type"><bean:write name="mpVo" property="type"/></td>
											<td align='left' class="show"><bean:write name="mpVo" property="show"/></td>
											<%-- <td align='left' class="readType"><bean:write name="mpVo" property="readType"/></td>  --%>			
											<c:if test = "${mpVo.readType=='A'}">
												  <td align='left' class="readType">ALL</td>
											</c:if>	
											<c:if test = "${mpVo.readType=='I'}">
												  <td align='left' class="readType">Initial</td>
											</c:if>	
											<c:if test = "${mpVo.readType=='D'}">
												  <td align='left' class="readType">Daily</td>
											</c:if>	
											<c:if test = "${mpVo.readType=='M'}">
												  <td align='left' class="readType">Monthly</td>
											</c:if>	
											<c:if test = "${mpVo.readType=='Y'}">
												  <td align='left' class="readType">Yearly</td>
											</c:if>								
											<td align='left' class="unit"><bean:write name="mpVo" property="unit"/></td>
											<td align='right' class="seqNo"><bean:write name="mpVo" property="seqNo"/></td>
											<c:choose>
												<c:when test = "${mpVo.status == 1}">
												     <td align='center' class="status">Active</td>
												</c:when>
												<c:otherwise>
													 <td align='center' class="status">De-Active</td>
												</c:otherwise>
											</c:choose>		
											<c:choose>
												<c:when test = "${mpVo.cumulative == 1}">
												     <td align='center' class="cumulative">Yes</td>
												</c:when>
												<c:otherwise>
													 <td align='center' class="cumulative">No</td>
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