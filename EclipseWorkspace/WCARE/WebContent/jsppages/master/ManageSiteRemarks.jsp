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
        	
    		$("#reset").click(function(){
    			alert();
    			$("#remark").val("");	  
    		});
    			
    		$("#ManageSiteRemarksForm").submit(function(){
    			form = $("#ManageSiteRemarksForm");   			
    			if(!validateForm()){
    				return false;
    			}
    			if(!getConfirmation()){
    				return false;
    			}
    			updateFormAction();
    			console.log("Action: " + $(form).attr("action"))
    			
    			function getConfirmation(){    
    				 if(actionStatus == actionPerform.create){
    					return confirm('Are you sure you want to submit?');
    				}
    			}
    			function updateFormAction(){
    				if(actionStatus == actionPerform.create){	        		
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
    				
    				if($('#siteSelection').val() == "ns"){
         				alert("Please select Site.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#remark", "Please enter Remark.")){
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
            <html:form action="manageSiteRemarks" method="post" styleId="ManageSiteRemarksForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Site Remarks Master</th>
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
															<td id="t_company" width="219">Select Site:</td>
                                                            <td valign="top">
                                                                <select name="id" id="siteSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                    <jsp:include page="/jsppages/utility/SelectVoTemplateV2.jsp">
																	<jsp:param name="beanIdentifier" value="sites" />
																	<jsp:param name="option" value="id" />
																	<jsp:param name="text" value="name" />
																</jsp:include>
                                                                </select>
                                                            </td>
														</tr>														 
														<tr class="bgcolor">
															<td id="t_company" width="219">Description:</td>
															<td colspan="3" valign="top">									            	
												            	<html:text property="remark" styleClass="ctrl" readonly="false" styleId="remark" size="100"></html:text>
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
														<logic:messagesPresent property="exists" message="true">
														<tr class="bgcolor"> 
															<td colspan="2" class = "errormsgtext">
															<html:messages id="msg" name="exists" message="true" bundle="stdmessage">
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
			                                    	<html:submit value="Submit" styleClass="btnform" styleId="submit"/>
			                                    </td>
			                                    <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
			                                    <td class="btn" width="100">			                                       
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
            	 <table border="0" align="center" cellpadding="0"  cellspacing="0" width="600">
				 <tbody>
					<tr>		
						<td align="center">
							<div id="sitedetails">
								<table border='0' cellpadding='2' cellspacing='1' width='100%'>
									<tr align='center' height='20'>
										<th class='detailsheading' width='25'>S.N.</th>
										<th class='detailsheading' width='115'>Site Name</th>
										<th class='detailsheading' width='600'>Remarks</th>
										
									</tr>
									<logic:iterate name="sitesRemarks" id="sitesVo" >
										<c:set var = "srNo" value="${srNo + 1}"/>
			
									<tr align='center' height='20' class='${srNo % 2 == 0 ? "detailsbody" : "detailsbody1"}' data-id = '<bean:write name="sitesVo" property="id"/>'>
										
										<td ALIGN='center'>${srNo}</td>
										<td align='left' class="name"><bean:write name="sitesVo" property="name"/></td>
										<td align='left' class="remark"><bean:write name="sitesVo" property="remark"/></td>
													
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