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
        var actionPerform = new enums.Enum("create", "update", "delete");
		var actionStatus = actionPerform.create;
		
		
		function changeValueOfSubmit(value){
			var submit = document.getElementById("submit");
			submit.setAttribute("value", value);
		}
		
		function initializeSelectionField(selectionField, vos){
    		for(var vo of vos){
    			selectionField.append(new Option(vo.name, vo.id));
    		}
    	}
		
		//Forma		
		var form = null;
		//Feeder Master
		var feederId = null;

		var substations = ${substations};//List of Javascript Object
		var substationSelectionField = null;
		var substationId = null;
		var feederDetails = null;
		
		function editFeeder(thisFeeder){
			substationSelectionField.prop("disabled", true);
			var row = $(thisFeeder).parent().parent();
			feederId = row.attr('data-id');
			actionStatus = actionPerform.update;
			setFormValues(row);
			changeValueOfSubmit("Update");
		
			function setFormValues(row){
				form.find("[name='name']").val(row.find(".name").text());
    		}
		}
		
        $(document).ready(function(){
        	form = $("form[name='FeederMasterForm']");
        	substationSelectionField = $('#substationSelection');
        	feederDetails = document.getElementById("feederDetails");
        	initializeSelectionField(substationSelectionField, substations);
        	
    		function initialize(){
    			substationSelectionField.prop("disabled", false);
    			actionStatus = actionPerform.create;
    			feederId = null;
    			feederDetails.innerHTML = "";
    			changeValueOfSubmit("Submit");
    		}
    		
    		$('#substationSelection').change(function(){
    			substationId = $(this).find(":selected").val();
    			populateFeeders(getSubstation(substationId, substations));
			    
			    function populateFeeders(substation){
			    	feederDetails.innerHTML = "";
		    		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>";
		    		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Substation Name</th><th class='detailsheading' width='100'>Feeder Name</th>";
		    		str +="<th class='detailsheading' width='40'>Edit</th></tr>";
		    		var feeders = substation["feeders"];
		    		var classPicker = null;
		    		
		    		$.each(feeders, function (i, vo) {
            			
		    			//console.log(substation.name + ":" + vo.name)
            			if (i % 2 == 0) classPicker = "detailsbody";
            			else classPicker = "detailsbody1";
			      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + vo.id + "'><td ALIGN='center'>"+(i+1)+"</td>";
	     				str+="<td align='left'>" + substation.name + "</td>";
	     				str+="<td align='left' class='name'>" + vo.name + "</td>";			     		
	     				str+="<td align='center'><input type='image' src='/WCARE/resources/images/edit.gif' alt='Click to edit the record' ";
	     				str+="onClick=editFeeder(this) /></td></tr>";
            		});
		    		
		    		feederDetails.innerHTML = str;
			    }
			    
			    function getSubstation(id, vos){
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
    			if(!validateForm()){
    				return false;
    			}
    			updateFormAction();
    			console.log("Action: " + $(form).attr("action"))
    			return true;
    			function updateFormAction(){
    				if(actionStatus == actionPerform.update || actionStatus == actionPerform.delete){
	        			var queryParameter = {"id":feederId, "method": actionStatus.name};
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
    				
    				if($('#substationSelection').val() == "ns"){
         				alert("Please select Substation.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#feederName", "Feeder Name cannot be blank.")){
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
            <html:form action="manageFeederMaster" method="post" styleId="FeederMasterForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Feeder Site Master</th>
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
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select Substation:</td>
                                                            <td valign="top">
                                                                <select name="substationId" id="substationSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Feeder Site Name:</td>
                                                            <td valign="top">
                                                                <%-- <input name="SiteNametxt" id="SiteNametxt" size="15" class="ctrl" value="<%=sname%>" type="text"> --%>
                                                                <html:text property="name" styleId="feederName" size="15" styleClass="ctrl" />
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
                                                        <!-- <tr class="bgcolor"> 
                                                            <td colspan="2">
                                            				</td>
                                        				</tr>	 -->
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
			                                    	<!-- <input name="Submit" value="Submit" class="btnform" type="submit"> -->
			                                    	<html:submit value="Submit" styleClass="btnform" styleId="submit"/>
			                                    </td>
			                                    <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
			                                    <td class="btn" width="100">
			                                        <%-- <input type="hidden" name="Admin_Input_Type" value="FeederSiteMaster" />		
			                                        <input type="hidden" name="SiteIdtxt" value="<%=feederid%>" />
			                                        <input name="Reset" value="Cancel" class="btnform" type="reset"> --%>
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
            	<table border="0" align="center" cellpadding="0" cellspacing="0" width="600">
                <tbody>
                	<tr>		
                        <td align="center">

                            <div id="feederDetails"></div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
        </div>
    </body>
</html>