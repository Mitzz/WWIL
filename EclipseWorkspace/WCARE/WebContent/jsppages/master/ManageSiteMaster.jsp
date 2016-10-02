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

<!-- <script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/interface/SiteMasterResource.js"> </script> -->
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
		
		/* function initializeSelectionField(selectionField, vos){
    		for(var vo of vos){
    			selectionField.append(new Option(vo.name, vo.id));
    		}
    	} */
		
		//Forma		
		var form = null;
		//Site Master
		var siteId = null;

		var states = ${states};//List of Javascript Object
		var stateSelectionField = null;
		var areaSelectionField = null;
		var stateId = null;
		var areaId = null;
		var stateVo = null;
		var areaVo = null;
		var areas = null;
		var sites = null;
		var siteDetails = null;
		
		function editFeeder(thisFeeder){
			stateSelectionField.prop("disabled", true);
			areaSelectionField.prop("disabled", true);
			var row = $(thisFeeder).parent().parent();
			siteId = row.attr('data-id');
			actionStatus = actionPerform.update;
			setFormValues(row);
			changeValueOfSubmit("Update");
		
			function setFormValues(row){
				
				form.find("[name='name']").val(row.find(".siteName").text());
    			form.find("[name='code']").val(row.find(".siteCode").text());
    			form.find("[name='incharge']").val(row.find(".siteInCharge").text());
    			form.find("[name='address']").val(row.find(".siteAddress").text());
    		}
		}
		
        $(document).ready(function(){
        	form = $("form[name='SiteMasterForm']");
        	stateSelectionField = $('#stateSelection');
        	 areaSelectionField = $('#areaSelection');
        	siteDetails = document.getElementById("siteDetails");
        	$.fn.initializeSelectionField(stateSelectionField, states, "id", "name");
        	
    		function initialize(){
    			stateSelectionField.prop("disabled", false);
    			areaSelectionField.prop("disabled", false);
    			actionStatus = actionPerform.create;
    			siteId = null;
    			siteDetails.innerHTML = "";
    			changeValueOfSubmit("Submit");
    		}
    		
    		$('#stateSelection').change(function(){
  			
    			siteDetails.innerHTML = "";
    			
    			$('#areaSelection').empty().append(new Option("--Make a Selection--", "ns"));

    			 stateId = $(this).find(":selected").val();
    			 stateVo = getStateVo(stateId, states);
    			 areas = stateVo["areas"];
    			
    			 $.fn.initializeSelectionField(areaSelectionField, areas,"id", "name");
    			
    			function getStateVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }   			
    		});
    		
    		$('#areaSelection').change(function(){
    			if($('#areaSelection').val() == "ns"){
    				siteDetails.innerHTML = "";   				
     			}
	   			areaId = $(this).find(":selected").val();
	   			
	   			areaVo = getAreaVo(areaId, areas);
	   			function getAreaVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }
	   			sites = areaVo["sites"]
	   			displaySites(sites); 
                //console.dir(sites);
			    function displaySites(site){
			    	
			    	console.log("displaySites");
			    	siteDetails.innerHTML = "";
		    		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>";
		    		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Site Name</th><th class='detailsheading' width='100'>Site Code</th>";
		    		str +="<th class='detailsheading' width='100'>Incharge</th><th class='detailsheading' width='100'>Address</th>";
		    		str +="<th class='detailsheading' width='40'>Edit</th></tr>";		    		
	
		    		var classPicker = null;

			    	$.each(site, function (i, vo) {
	           			
	           			if (i % 2 == 0) classPicker = "detailsbody";
	           			else classPicker = "detailsbody1";
			    			
				      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + vo.id + "'><td ALIGN='center'>"+(i+1)+"</td>";	     				
		     				str+="<td align='left' class='siteName'>" + vo.name + "</td>";			     				
		     				
		     				if((vo.code)== null){
		     					str+="<td align='left' class='siteCode'>" + " " + "</td>";
		     				}else{
		     					str+="<td align='left' class='siteCode'>" + vo.code + "</td>";
		     				}
		     				if((vo.incharge)== null){
		     					str+="<td align='left' class='siteInCharge'>" + " " + "</td>";
		     				}else{
		     					str+="<td align='left' class='siteInCharge'>" + vo.incharge + "</td>";
		     				}
		     				if((vo.address)== null){
		     					str+="<td align='left' class='siteAddress'>" + " " + "</td>";
		     				}else{
		     					str+="<td align='left' class='siteAddress'>" + vo.address + "</td>";
		     				}		
		     				str+="<td align='center'><input type='image' src='/WCARE/resources/images/edit.gif' alt='Click to edit the record' ";
		     				str+="onClick=editFeeder(this) /></td></tr>";
	           		}); 
		    		siteDetails.innerHTML = str;
			     }			 			   
   			});
    		
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
	        			var queryParameter = {"id":siteId, "method": actionStatus.name};
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
    				if($('#areaSelection').val() == "ns"){
         				alert("Please select Area.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#siteName", "Please enter Site Name ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#siteCode", "Please enterSite Code ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#siteInCharge", "Please enter Site Incharge ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#siteAddress", "Please enter Site Address ")){
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
            <html:form action="manageSiteMaster" method="post" styleId="SiteMasterForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110"> Site Master</th>
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
                                                            <td id="t_company" width="219">Select State:</td>
                                                            <td valign="top">
                                                                <select name="stateId" id="stateSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select Area:</td>
                                                            <td valign="top">
                                                                <select name="areaId" id="areaSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219"> Site Name:</td>
                                                            <td valign="top">                                                                
                                                                <html:text property="name" styleId="siteName" size="15" styleClass="ctrl" />
                                                            </td>
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Site Short Code:</td>
                                                            <td valign="top">                                                             
                                                                <html:text property="code" styleId="siteCode" size="15" styleClass="ctrl" />
                                                            </td>
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219"> Site Incharge:</td>
                                                            <td valign="top">                                                                
                                                                <html:text property="incharge" styleId="siteInCharge" size="15" styleClass="ctrl" />
                                                            </td>
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219"> Site Address:</td>
                                                            <td valign="top">                                                               
                                                                <html:text property="address" styleId="siteAddress" size="15" styleClass="ctrl" />
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
            	<table border="0" align="center" cellpadding="0" cellspacing="0" width="600">
                <tbody>
                	<tr>		
                        <td align="center">

                            <div id="siteDetails"></div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
        </div>
    </body>
</html>