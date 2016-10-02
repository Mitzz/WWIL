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
        	var form = $("form[name='RemarkMasterForm']");
        	
        	var id = null;
        	
        	
        	function initialize(){
        		actionStatus = actionPerform.create;
        		id = null;
        		resetForm();
        		$('#typeSelection').prop("disabled", false);
        		$('#wecTypeSelection').prop("disabled", false);    
        		function resetForm(){
        			$("#description").val("");
        			$("#errorNo").val("");
        			
        		}
        		changeValueOfSubmit("Submit");
        	}
        	
        	$('#typeSelection').click(function(){
        		
        		if($('#typeSelection').val()=='EB'){
        			$('#wecTypeSelection').prop("disabled", true);       			
        		}
        	});	 
        	$('.edit').click(function(){
        		
        		$('#typeSelection').prop("disabled", true);
        		$('#wecTypeSelection').prop("disabled", true);        		
        		var row = $(this).parent().parent();
        		id = row.attr('data-id');
        		actionStatus = actionPerform.update;
        		setFormValues(row);
        		changeValueOfSubmit("Update");
        	
        		function setFormValues(row){
        			
        			var id = row.attr('data-id');
        			$("#description").val(row.find(".description").text());
        			$("#errorNo").val(row.find(".errorNo").text());
        			$("#typeSelection").find("option:contains('"+row.find(".type").text()+"')").each(function(){
   					 if( $(this).text() == row.find(".type").text()){
   					 		 $(this).attr("selected","selected");
   					  }
   					});
        			$("#wecTypeSelection").find("option:contains('"+row.find(".wecType").text()+"')").each(function(){
   					 if( $(this).text() == row.find(".wecType").text()){
   					 		 $(this).attr("selected","selected");
   					  }
   					});			
        		}       		
        	});
        	
        	function changeValueOfSubmit(value){
        		var submit = document.getElementById("submit");
        		submit.setAttribute("value", value);
        	}
    		
    		
    		
    		$("#reset").click(function(){
    			initialize();
    		});
    			
    		$("#RemarkMasterForm").submit(function(){
    			
    			form = $("#RemarkMasterForm");
    			$('#typeSelection').prop("disabled", false);
        		$('#wecTypeSelection').prop("disabled", false); 	
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
    				
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#errorNo", "Error No cannot be blank.")){
    					return false;
    				}	
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#description", "Description cannot be blank.")){
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
            <%-- <html:form action="manageRemarks" method="post" styleId="RemarkMasterForm"> --%>
            <sf:form method="post" action="${pageContext.request.contextPath}/spring/remarkMaster" modelAttribute="remark" id="RemarkMasterForm" name="RemarkMasterForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Remarks Master</th>
                            <td width="475"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                            <td class="newhead3" width="7">&nbsp;</td>
                            <td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                        </tr>
                        <tr>
                            <td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1">
                            </td>
                            <td colspan="3" width="600">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tbody>
                                        <tr>
                                            <td bgcolor="#dbeaf5">
                                                <table border="0" cellpadding="2" cellspacing="1" width="592">
                                                    <tbody>
                                                        <tr class="bgcolor">
															<td id="t_street_address_ln2" nowrap="nowrap">Type:</td>
															<td>
																<select name="type" id="typeSelection" class="ctrl" >
													              <option value="">-- select --</option>
													         	  <option value="EB">EB</option>
																  <option value="WEC">WEC</option>
																</select>
															</td>
														</tr>
														<tr class="bgcolor">
															<td id="t_company">WEC Type:</td>
															<td valign="top">
															    <select name="wecType" id="wecTypeSelection" class="ctrl" >              
												                	<option value="ns">-- select --</option>	
												                	<jsp:include
																			page="/jsppages/utility/SelectVoTemplateV2.jsp">
																			<jsp:param name="beanIdentifier" value="wecType" />
																			<jsp:param name="option" value="id" />
																			<jsp:param name="text" value="description" />
																	</jsp:include>											            
															    </select>
															</td>	
														</tr>
                                                        <tr class="bgcolor">
															<td  width="219">Error No:</td>
															<td colspan="3" valign="top">													
																<sf:input cssClass="ctrl" path="errorNo" size="20" id="errorNo" type="text" />
																<%-- <html:text property="errorNo" styleClass="ctrl" readonly="false" styleId="errorNo" size="20"></html:text> --%>
															</td>
														</tr>
														<tr class="bgcolor">
															<td id="t_company" width="219">Description:</td>
															<td colspan="3" valign="top">									            	
												            	<sf:input cssClass="ctrl" path="description" size="100" id="description" type="text" />
												            	<%-- <html:text property="description" styleClass="ctrl" readonly="false" styleId="description" size="100"></html:text> --%>
												            </td>
														</tr>
                                                       <c:if test="${not empty success }">
														<tr class="bgcolor"> 
															<td colspan="4" class = "sucessmsgtext">
															${ success }
															</td>
														</tr>
														</c:if>
														
														<c:if test="${not empty failure }">
														<tr class="bgcolor"> 
															<td colspan="4" class = "errormsgtext">
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
            	<table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
				<tbody>
					<tr>		
						<td align="center">
							<div id="remarkdetails">
								<table border='0' cellpadding='2' cellspacing='1' width='100%'>
									<tr align='center' height='20'>
										<th class='detailsheading' width='30'>S.N.</th>
										<th class='detailsheading' width='200'>Remarks</th>
										<th class='detailsheading' width='50'>Error No</th>
										<th class='detailsheading' width='50'>Type</th>
										<th class='detailsheading' width='50'>Wec Type</th>
										<th class='detailsheading' width='40'>Edit</th>
									</tr>
									<logic:iterate name="remarks" id="remarkVo" >
										<c:set var = "srNo" value="${srNo + 1}"/>
			
									<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="remarkVo" property="id"/>'>
										
										<td ALIGN='center'>${srNo}</td>
										<td align='left' class="description"><bean:write name="remarkVo" property="description"/></td>
										<td align='left' class="errorNo"><bean:write name="remarkVo" property="errorNo"/></td>
										<td align='left' class="type"><bean:write name="remarkVo" property="type"/></td>
										<td align='left' class="wecType"><bean:write name="remarkVo" property="wecType"/></td>										
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