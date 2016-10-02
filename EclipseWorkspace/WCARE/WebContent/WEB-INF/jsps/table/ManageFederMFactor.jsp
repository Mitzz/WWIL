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
		
		
		//Forma		
		var form = null;

		var states = ${states};//List of Javascript Object
		var stateSelectionField = null;
		var siteSelectionField = null;
		var federSelectionField = null;
		
		var MFid = null
		var ebSelectionField = null;
		
		var stateId = null;		
		var stateVo = null;
		var sites = null;
		
		var siteId = null;		
		var siteVo = null;
		var feders = null;
		
		var federId = null;		
		var federVo = null;
		var federMFs = null;
		
		
		function editFederMf(thisFeder){
			stateSelectionField.prop("disabled", true);
			siteSelectionField.prop("disabled", true);
			federSelectionField.prop("disabled", true);
			var row = $(thisFeder).parent().parent();
			MFid = row.attr('data-id');
			actionStatus = actionPerform.update;
			setFormValues(row);
			changeValueOfSubmit("Update");
		
			function setFormValues(row){
				$("#typeSelection").find("option:contains('"+row.find(".type").text()+"')").each(function(){
					 if( $(this).text() == row.find(".type").text()){  
						    $(this).attr("selected","selected"); 
					 }
				});
				$("#subTypeSelection").find("option:contains('"+row.find(".subType").text()+"')").each(function(){
					 if( $(this).text() == row.find(".subType").text()){  
						    $(this).attr("selected","selected"); 
					 }
				});
				form.find("[name='capacity']").val(row.find(".capacity").text());
    			form.find("[name='multiFactor']").val(row.find(".multiFactor").text());
    			form.find("[name='fromDate']").val(row.find(".fromDate").text());
    			form.find("[name='toDate']").val(row.find(".toDate").text());
    		}
		}
		
        $(document).ready(function(){
        	
        	form = $("form[name='FederMFactorForm']");
        	stateSelectionField = $('#stateSelection');
        	siteSelectionField = $('#siteSelection');
        	federSelectionField = $('#federSelection');
        	federMFactorDetails = document.getElementById("federMFactorDetails");
        	$.fn.initializeSelectionField(stateSelectionField, states, "id", "name");
        	
    		function initialize(){
    			stateSelectionField.prop("disabled", false);
    			siteSelectionField.prop("disabled", false);
    			federSelectionField.prop("disabled", false);
    			actionStatus = actionPerform.create;
    			MFid = null;
    			federMFactorDetails.innerHTML = "";
    			changeValueOfSubmit("Submit");
    			document.getElementById("typeSelection").selectedIndex=0;
	   			document.getElementById("subTypeSelection").selectedIndex=0;
    		}
    		
    		$('#stateSelection').change(function(){
    			
    			federMFactorDetails.innerHTML = "";    			
    			$.fn.emptySelection(["#siteSelection", "#federSelection"]);
    			stateId = $(this).find(":selected").val();
    			stateVo = $.fn.getVo(stateId, states,["id"]);
    			sites = stateVo["sites"];    			
    			$.fn.initializeSelectionField(siteSelectionField, sites,"id", "name"); 
    			
    		});
    		
    		$('#siteSelection').change(function(){
    			
    			federMFactorDetails.innerHTML = "";    			
    			$.fn.emptySelection([ "#ebSelection"]);
    			siteId = $(this).find(":selected").val();
    			siteVo = $.fn.getVo(siteId, sites ,["id"]);
    			feders = siteVo["feders"];    			
    			$.fn.initializeSelectionField(federSelectionField, feders,"id", "name");    	
    				
    		});
	
    		$('#federSelection').change(function(){
  
    			federId = $(this).find(":selected").val();
    			federVo = $.fn.getVo(federId, feders , ["id"]);
    			
    			federMFs = federVo["federMfs"];
    			console.dir(federMFs)
    		   // console.log(ebMFs.id);
	   			displayEbs(federMFs); 
                
			    function displayEbs(federMFs){
			    	
			    	federMFactorDetails.innerHTML = "";
			    	var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
			    		str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th>"
			    		str+="<th class='detailsheading' width='90'>FD Type</th><th class='detailsheading' width='90'>Sub Type</th>"
			    		str+="<th class='detailsheading' width='90'>Capacity</th><th class='detailsheading' width='90'>M Factor</th>"
			    		str+="<th class='detailsheading' width='90'>From Date</th><th class='detailsheading' width='90'>To Date</th>"
			    		str +="<th class='detailsheading' width='40'>Edit</th></tr>";		    		
	
		    		var classPicker = null;

			    	$.each(federMFs, function (i, vo) {
	           			
	           			if (i % 2 == 0) classPicker = "detailsbody";
	           			else classPicker = "detailsbody1";
			    			
				      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + vo.id + "'><td ALIGN='center'>"+(i+1)+"</td>";	     				
		     				str+="<td align='left' class='type'>" + vo.type + "</td>";	
		     				str+="<td align='left' class='subType'>" + vo.subType + "</td>";	
		     				str+="<td align='left' class='capacity'>" + vo.capacity + "</td>";	
		     				str+="<td align='left' class='multiFactor'>" + vo.multiFactor + "</td>";	
		     				str+="<td align='left' class='fromDate'>" + vo.fromDate + "</td>";	
		     				str+="<td align='left' class='toDate'>" + vo.toDate + "</td>";	
		     				str+="<td align='center'><input type='image' src='/WCARE/resources/images/edit.gif' alt='Click to edit the record' ";
		     				str+="onClick=editFederMf(this) /></td></tr>";
	           		}); 
			    	federMFactorDetails.innerHTML = str;
			     }			 			  
   			});
    		
    		$("#reset").click(function(){
    			initialize();
    		});
    			
    		$(form).submit(function(){
    			$.fn.enableSelection(["#federSelection"]);
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
    	    			var queryParameter = {"id":MFid};
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
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#fromDate", "Please Select From date ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#toDate", "Please Select To date ")){
        				return false;
        			}
    				if($('#type').val() == "ns"){
         				alert("Please select Type.");
         				return false;
         			}
    				if($('#subType').val() == "ns"){
         				alert("Please select SubType.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#capacity", "Please enter Capacity ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#multifactor", "Please enter Multifactor ")){
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
            <%-- <html:form action="manageFederMFactor" method="post" styleId="FederMFactorForm"> --%>
              <sf:form method="post" action="${pageContext.request.contextPath}/spring/federMFactor" modelAttribute="federMFactor" id="FederMFactorForm" name="FederMFactorForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="604">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110"> Feder Multiplication Factor Master</th>
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
                                                            <td id="t_company" width="219"> Site Name:</td>
                                                            <td valign="top">                                                                
                                                                <select name="siteId" id="siteSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select Feder:</td>
                                                            <td valign="top">                                                                
                                                               <select name="federId" id="federSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select From Date:</td>
                                                            <td class="bgcolor">
																<input type="text" name="fromDate" id="fromDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()"  />
																<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.FederMFactorForm.fromDate);return false;" >
																	<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
																</a>
															</td>			
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="219">Select To Date:</td>
                                                            <td class="bgcolor">
																<input type="text" name="toDate" id="toDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()"  />
																<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.FederMFactorForm.toDate);return false;" >
																	<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
																</a>
															</td>			
                                                        </tr>	
                                                        <tr bgcolor="#ffffff"> 
															<td id="t_company">Type:</td>
															<td valign="top">
															   <select name="type" id="typeSelection" class="ctrl">
												                    <option value="ns" >-- select --</option>												             
													          	    <option value="4Q">4Q</option>													         
												               		<option value="2Q">2Q</option>												               
												                </select>
												            </td>
														</tr>
														<tr bgcolor="#ffffff"> 
															<td id="t_company">SubType:</td>
															<td valign="top">
																<select name="subType" id="subTypeSelection" class="ctrl">
												           		    <option value="ns" >-- select --</option>												             
													          	    <option value="NTOD">NTOD</option>													         
												               		<option value="TOD">TOD</option>												             
												     			</select>
												     		</td>
														</tr>
														<tr bgcolor="#ffffff"> 
															<td id="t_company">Capacity:</td>
															<td valign="top">
																 <input name="capacity" id="capacity" size="35" class="ctrl"  type="text" />
														  	</td>	
													  	</tr>
													  	<tr bgcolor="#ffffff">
														  	<td id="t_company">Multifactor:</td>
															<td valign="top">
																 <input name="multiFactor" id="multifactor" size="35" class="ctrl"  type="text" />
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
            	<table border="0" align="center" cellpadding="0" cellspacing="0" width="600">
                <tbody>
                	<tr>		
                        <td align="center">

                            <div id="federMFactorDetails"></div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
        </div>
    </body>
    <iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${ contextPath }/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</html>