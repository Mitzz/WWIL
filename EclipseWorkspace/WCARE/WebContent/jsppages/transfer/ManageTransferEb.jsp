<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
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

<c:set var = "srNo" scope="page" value="0"/>
<c:set var = "class" scope="page" value=""/>


<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
    <head>
        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="${ contextPath }/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="${ contextPath }/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="${ contextPath }/resources/js/ajaxnew.js"></script>
        <script type="text/javascript" src="${ contextPath }/resources/js/validateForm.js"></script>
        
        <script type="text/javascript">
        var actionPerform = new enums.Enum("create", "update", "delete");
		var actionStatus = actionPerform.create;
		
		function changeValueOfSubmit(value){
			var submit = document.getElementById("submit");
			submit.setAttribute("value", value);
		}
		
		//Form		
		var form = null;
		var sites = ${sites};//List of Javascript Object
		
		$(document).ready(function(){
        	form = $("form[name='ManageTransferEbForm']");
        	
        	$.fn.initializeSelectionField($('#fromSiteSelection'), sites, "id", "name");
        	$.fn.initializeSelectionField($('#toSiteSelection'), sites, "id", "name");
        	
        	$('#fromSiteSelection').change(function(){
        		$('#fromEbId').empty().append(new Option("--Make a Selection--", "ns"));
        		var siteVo = $.fn.getVo($(this).find(":selected").val(), sites, "id");
        		$.fn.initializeSelectionField($('#fromEbId'), siteVo["ebs"], "id", "name");
        	});
        	
        	$('#toSiteSelection').change(function(){
        		$('#toEbId').empty().append(new Option("--Make a Selection--", "ns"));
        		var siteVo = $.fn.getVo($(this).find(":selected").val(), sites);
        		$.fn.initializeSelectionField($('#toEbId'), siteVo["ebs"], "id", "name");
        	});
        	
    		/* function initialize(){
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
    		}); */
    	});
		</script>
    </head>
    <body>
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
                <tr width="100%">
                    <td width="100%" align="center">
                        <html:form action="manageTransferEb"  method="post">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
                                <tbody><tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Transfer WEC from EB</th>
                                        <td><img src="${ contextPath }/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                                        <td class="newhead3">&nbsp;</td>
                                        <td class="newhead4"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                                    </tr>
                                    <tr>
                                        <td class="newheadl"><img src="${ contextPath }/resources/images/pixel.gif" border="0"></td>
                                        <td colspan="3">
                                            <img src="${ contextPath }/${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                            	<tbody>
                                            		<tr>
                                            			<td bgcolor="#dbeaf5">
                                                            <table border="0" cellpadding="2" cellspacing="1" width="100%">
                                                                <tbody>		
                                                                    <tr class="bgcolor">
                                                                        <td id="t_street_address">Site From:</td>
                                                                        <td class="bgcolor">
                                                                            <select size="1" name="fromSiteId" id="fromSiteSelection" class="ctrl">
                                                                                <option value="ns">--Make a Selection--</option>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">EB From:</td>
                                                                        <td class="bgcolor">
                                                                        
                                                                            <!-- <select size="1" id="EBFromtxt" name="EBFromtxt" class="ctrl" onChange="findWEC()">
                                                                                <option value="">--Make a Selection--</option>
                                                                            </select> -->
                                                                            
                                                                            <html:select property="fromEbId" styleId="fromEbId" size="1" styleClass="ctrl">
                                                                            	<option value="ns">--Make a Selection--</option>
                                                                            </html:select>
                                                                            
                                                                        </td>
                                                                    </tr>	
                                                                    <tr class="bgcolor">
                                                                        <td id="t_street_address">Site To:</td>
                                                                        <td class="bgcolor">
                                                                            <select size="1" name="toSiteId" id="toSiteSelection" class="ctrl">
                                                                                <option value="ns">--Make a Selection--</option>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">EB To:</td>
                                                                        <td class="bgcolor">
                                                                            <html:select property="toEbId" size="1" styleClass="ctrl" styleId="toEbId">
                                                                            	<option value="ns">--Make a Selection--</option>
                                                                            </html:select>
                                                                            
                                                                        </td>
                                                                    </tr>		

                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">From Date:</td>
                                                                        <td class="bgcolor">
                                                                            <%-- <input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value="<%=dt%>" onfocus="dc.focus()" /> --%>
                                                                            <html:text property="date" size="20" styleClass="ctrl" maxlength="10" onfocus="dc.focus" />
                                                                            <a href="javascript:void(0)" id="dc" onClick="if (self.gfPop) gfPop.fPopCalendar(document.ManageTransferEbForm.date);
                                                                                    return false;" ><img class="PopcalTrigger" align="absmiddle" src="${ contextPath }/resources/images/calbtn.gif" border="0" alt=""></a>
                                                                        </td>
                                                                    </tr>
                                                                    <tr class="bgcolor"> 
                                                                        <td id="t_street_address">Remarks:</td>
                                                                        <td class="bgcolor">
                                                                            <%-- <input type="text" name="Remarkstxt" id="Remarkstxt" size="20" class="ctrl" maxlength="50" value="<%=rem%>" /> --%>
                                                                            <html:text property="remark" size="20" styleClass="20" maxlength="50" />
                                                                        </td>
                                                                    </tr>	
                                                                    <tr class="bgcolor"> 
                                                                        <td colspan="2">
                                                                		</td>
                                                    				</tr>	
                                                				</tbody>
                                                			</table>
                                                		</td>
                                                	</tr>
                                                </tbody>
											</table>
                            				<img src="${ contextPath }/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                            			</td>
                            			<td class="newheadr"><img src="${ contextPath }/resources/images/pixel.gif" border="0"></td>
                					</tr>
					                <tr>
					                    <td width="10"><img src="${ contextPath }/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
					                    <td colspan="4" align="right" bgcolor="#006633">
					                        <table border="0" cellpadding="0" cellspacing="0">
					                            <tbody>
					                            	<tr>
					                                    <td class="btn" width="100">
					                                        <!-- <input type="submit" name="Submitcmd" class="btnform" value="Submit" onClick="return confirmation();"/> -->
					                                        <html:submit value="Submit" styleClass="btnform" styleId="submit"/>
					                                        <%-- <input type="hidden" name="ebfromtxt" value="<%=ef%>" />
					                                        <input type="hidden" name="ebtotxt" value="<%=et%>" />
					                                        <input type="hidden" name="Admin_Input_Type" value="TransferEB" /> --%>
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
					                                    <td class="btn" width="100">
					                                    	<!-- <input name="Reset" value="Cancel" class="btnform" type="reset"> -->
					                                    	<html:reset value="Cancel" styleClass="btnform" styleId="reset"/>
					                                    </td>
					                                    <td width="1"><img src="${ contextPath }/resources/images/pixel.gif" border="0" height="18" width="1"></td>
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
			</table>
		</div>
	</body>
	<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${ contextPath }/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</html>