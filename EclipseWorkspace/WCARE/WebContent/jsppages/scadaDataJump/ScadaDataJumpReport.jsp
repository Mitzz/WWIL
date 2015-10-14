<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page language="java" contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="../../WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="../../WEB-INF/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/ScadaDataJumpReport.css"/>

<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.text.*" %>
        <%
            response.setHeader("Pragma", "no-cache");
            response.getOutputStream().flush();
            response.getOutputStream().close();
        %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
					var queryResolver=-1;
					$.fn.myFunction("customerSelection","customerSelected","customerLeftAll","customerRightAll","customerLeft","customerRight");
					$.fn.myFunction("stateSelection","stateSelected","stateLeftAll","stateRightAll","stateLeft","stateRight");
					$.fn.myFunction("locationSelection","locationSelected","locationLeftAll","locationRightAll","locationLeft","locationRight");
					$.fn.myFunction("areaSelection", "areaSelected", "areaLeftAll", "areaRightAll", "areaLeft", "areaRight");
	        		$.fn.myFunction("siteSelection", "siteSelected", "siteLeftAll", "siteRightAll", "siteLeft", "siteRight");
	        		$.fn.myFunction("wecSelection", "wecSelected", "wecLeftAll", "wecRightAll", "wecLeft", "wecRight");
	        		$("#scadaDataJumpReport").submit(function(){
	        			if($.fn.isSelectBoxEmpty("wecSelected")){
	        				alert("Please select wec.");
	        				return false;
	        			}
	        			if($.fn.isInputTextFieldEmpty("fromDate")){
	        				alert("Please select from date.");
	        				return false;
	        			}
	        			if($.fn.isInputTextFieldEmpty("toDate")){
	        				alert("Please select to date.");
	        				return false;
	        			}
// 	        			if($('#reportTypeSelection').val() == "ns"){
// 	        				alert("Please select Report Type.");
// 	        				return false;
// 	        			}
	        			
	        			if(!validateFromDateToDate($('#fromDate').val(), $('#toDate').val())){
	        				alert("Please select toDate after or equal to from Date");
	        				return false;
	        			}
	        			if($.fn.calculateTimeDifferenceBetweenTwoDateInMinutes($.fn.getDateObject($('#toDate').val()), new Date())  < 2160){//24 + 12 hours
	        				alert("Please select 'To Date' before today!!!");
	    					return false;
	    				}
	        			$.fn.selectAllFunction("wecSelected");
	        			
	        			function validateFromDateToDate(fromDate, toDate){
	        				if($.fn.calculateTimeDifferenceBetweenTwoDateInMinutes($.fn.getDateObject(fromDate),$.fn.getDateObject(toDate)) < 0){
	        					return false;
	        				}
	        				return true;
	        			}
	        			
	        		});
// 	        		queryResolver=getFunctionResolver();
// 	        		function getFunctionResolver()
// 					{
// // 					if($('#locationSelected').has('option').length>0)
// // 						return 0;
// 					else if($('#customerSelected').has('option').length>0)
// 							return 1;
// 						else ($('#stateSelected').has('option').length>0)
// 							return 2;
// 					}
					$("#generateArea").click(function(){
						queryResolver=getFunctionResolver();
						switch(queryResolver){
						case 1:populateAreaSelectVoBasedOnCustomerIds();break;
						case 2:populateAreaSelectVoBasedOnStateIds();break;
						}
					
					function getFunctionResolver()
					{
// 					if($('#locationSelected').has('option').length>0)
// 						return 0;
					 if($('#customerSelected').has('option').length>0)
							return 1;
						else ($('#stateSelected').has('option').length>0)
							return 2;
					}
					function populateAreaSelectVoBasedOnCustomerIds()
					{
						var multipleCustomerIds=$.fn.getSelectOptionValueForQuery("customerSelected");
						var param={ajaxType:"getAreaSelectVoBasedOnMultipleCustomerIds", customerIds:multipleCustomerIds};
						$.ajax({
							url:'AjaxFrontController.do',
							data:param,
							type:'POST',
							dataType:'json',
							success:function(data){
								populateAreaSelectionField(data);
							}							
						});
					}
					function populateAreaSelectVoBasedOnStateIds()
					{
						var multipleStateIds=$.fn.getSelectOptionValueForQuery("stateSelected");
						var param={ajaxType:"getAreaSelectVoBasedOnMultipleStateIds", stateIds:multipleStateIds};
						$.ajax({
							url:'AjaxFrontController.do',
							data:param,
							type:'POST',
							dataType:'json',
							success:function(data){
								populateAreaSelectionField(data);
							}
							
						});
					}
				
					function populateAreaSelectionField(data)
					{
						$("#areaSelection").empty();
						$("#areaSelected").empty();
						$("#siteSelection").empty();
						$("#siteSelected").empty();
						$("#wecSelection").empty();
						$("#wecSelected").empty();
						if($.isEmptyObject(data)){
							alert("Area Not Found for Specified Criteria.Please Select Proper Criteria");
					//	queryResolver = -1;
							return false;
							}
						var areaSelectionField=$("#areaSelection");
						$.each(data,function(index,selectVo)
								{
									areaSelectionField.append(new Option(selectVo.textValue,selectVo.optionValue));
								});
						}
					});
					

					$("#generateSite").click(function(){
						if($.fn.isSelectBoxEmpty("areaSelected")){
							alert("Please select area.");
							return false;
						}
						var multipleAreaIds=$.fn.getSelectOptionValueForQuery("areaSelected");
						switch(queryResolver){
						case 1:populateSiteSelectVoBasedOnCustomerIdsAreaIds();break;
						case 2:populateSiteSelectVoBasedOnStateIdsAreaIds();break;
						}
						
						function populateSiteSelectVoBasedOnCustomerIdsAreaIds(){
							var multipleCustomerIds=$.fn.getSelectOptionValueForQuery("customerSelected");
							var param= {ajaxType: "getSiteSelectVoBasedOnCustomerIdsAreaIds", customerIds:multipleCustomerIds, areaIds:multipleAreaIds};
							$.ajax({
								url:'AjaxFrontController.do',
								data:param,
								type:'POST',
								dataType:'json',
								success:function(data){
									populateSiteSelectionField(data);
								}
							});
						}
						
						function populateSiteSelectVoBasedOnStateIdsAreaIds(){
							var multipleStateIds=$.fn.getSelectOptionValueForQuery("stateSelected");
							var param= {ajaxType: "getSiteSelectVoBasedOnStateIdsAreaIds", stateIds:multipleStateIds, areaIds:multipleAreaIds};
							$.ajax({
								url:'AjaxFrontController.do',
								data:param,
								type:'POST',
								dataType:'json',
								success: function(data){
									populateSiteSelectionField(data);
								}
							});
						}
						
						function populateSiteSelectionField(data){
							$("#siteSelection").empty();
							$("#siteSelected").empty();
							$("#wecSelection").empty();
							$("#wecSelected").empty();
							if($.isEmptyObject(data)){
								alert("Site Not Found For Specified Criteria.Please Select Proper Criteria.");
								return ;
							}				
							var siteSelectionField=$("#siteSelection");
							$.each(data,function(index,selectVo){
								siteSelectionField.append(new Option(selectVo.textValue,selectVo.optionValue));
							});
						}
					});
					
					$("#generateWec").click(function(){
						if($.fn.isSelectBoxEmpty("siteSelected") && $.fn.isSelectBoxEmpty("locationSelected")){
							alert("Please Select Site or Select Location.");
							return ;
						}
						
						var multipleAreaIds=$.fn.getSelectOptionValueForQuery("areaSelected");
						var multipleSiteIds=$.fn.getSelectOptionValueForQuery("siteSelected");
						
						switch(queryResolver){
						case -1:populateWecSelectVoBasedOnLocationIds();break;
						case 1:populateWecSelectVoBasedOnCustomerIdsAreaIdsSiteIds();break;
						case 2:populateWecSelectVoBasedOnStateIdsAreaIdsSiteIds();break;
						}
						function populateWecSelectVoBasedOnCustomerIdsAreaIdsSiteIds(){
						
							var multipleCustomerIds=$.fn.getSelectOptionValueForQuery("customerSelected");
							
							var param={ajaxType: "getWecSelectVoBasedOnCustomerIdsAreaIdsSiteIds", customerIds: multipleCustomerIds, areaIds: multipleAreaIds, siteIds: multipleSiteIds};
							$.ajax({
								url:'AjaxFrontController.do',
								data:param,
								type:'POST',
								dataType:'json',
								success: function(data){
									populateWecSelectionField(data);
								}
							});
						}

						
						function populateWecSelectVoBasedOnStateIdsAreaIdsSiteIds(){
	
							var multipleStateIds=$.fn.getSelectOptionValueForQuery("stateSelected");
							var param={ajaxType: "getWecSelectVoBasedOnStateIdsAreaIdsSiteIds", stateIds: multipleStateIds, areaIds: multipleAreaIds, siteIds: multipleSiteIds};
							$.ajax({
								url:'AjaxFrontController.do',
								data:param,
								type:'POST',
								dataType:'json',
								success: function(data){
									populateWecSelectionField(data);
								}
							});
 						}
						
						function populateWecSelectVoBasedOnLocationIds(){
							var multipleLocationIds=$.fn.getSelectOptionValueForQuery("locationSelected");	
							var param={ajaxType:"getWecSelectVoBasedOnLocationIds", locationIds:multipleLocationIds};
							$.ajax({
								url:'AjaxFrontController.do',
								data:param,
								type:'POST',
								dataType:'json',
								success:function(data){
									populateWecSelectionField(data);
								}
							});
						}
						
						function populateWecSelectionField(data){
							$("#wecSelection").empty();
							$("#wecSelected").empty();
							if($.isEmptyObject(data)){
								alert("WEC Not Found For Specified Criteria.Please Select Proper Criteria.");
								return ;
							}				
							var wecSelectionField=$("#wecSelection");
							$.each(data,function(index,selectVo){
								wecSelectionField.append(new Option(selectVo.textValue,selectVo.optionValue));
							});
						}
				});
			});
		</script>

    </head>
    <body>
    
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="80%">
                <tr width="100%">
               		 <td align="center">
                		<form action="<%=request.getContextPath()%>/scadaDataJumpReport.do" method="post" enctype="multipart/form-data" name="scadaDataJumpReport" id="scadaDataJumpReport">
                			<table align="center" width="100%" border="0" cellpadding="0" cellspacing="0">
                				<tbody>
                					<tr>
                						<td class="newhead1"></td>
                						<th class="headtext">Scada Data Jump Report</th>
                						<td>
                                        	<img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10">
                 						</td>
                 						<td class="newhead3">&nbsp;</td>
                                        <td class="newhead4">
                                        	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10">
                              			</td>
                					</tr>
                					<tr>
                                        <td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                        <td colspan="3">
                                            <img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                            	<table align="center" border="0" width="100%" cellspacing="0" cellpadding="0">
                                            		<tbody>
                                            			<tr>
                                            				<td bgcolor="#dbeaf5">
                                            					<div class="divTable">
                                            						<div class="divRow row1" style="border: 5px none #FFFFFF;">
                                            							<div class="divCell cell1">
                                            								Select Location:
                                            							</div>
                                            							<div class="divCell cell2">
                                            								<select size="5" name="locationSelection" id="locationSelection" multiple="multiple" style="width:100%">
                                            								<logic:iterate name="LocationMasterSelectVo" id="LocationMasterData">
																			<option value = '<bean:write name="LocationMasterData" property="optionValue"/>'><bean:write name="LocationMasterData" property="optionValue"/> [<bean:write name="LocationMasterData" property="textValue"/>]</option>
																				</logic:iterate>
                                            								</select>
                                                        				</div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="locationRight" id="locationRight" value="->" alt="Move selected" style="width:50%"/><br />
                                           									<input type="button" name="locationRightAll" id="locationRightAll" value="->>" style="width:50%" /><br />
                                            								<input type="button" name="locationLeft" id="locationLeft" value="<-" style="width:50%" /><br />
                                            								<input type="button" name="locationLeftAll" id="locationLeftAll" value="<<-"  style="width:50%"/><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="locationSelected" id="locationSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                            						<div class="divRow row2">
                                            							<div class="divCell cell1">
                                            								<input type="button" id="" value="OR" /> 
                                            							</div>
                                            						</div>
                                            						<div class="divRow row3">
                                           		 						<div class="divCell cell1">
                                            								Select Customer:
                                            							</div>
                                            							<div class="divCell cell2">
                                           		 							<select size="5" name="customerSelection" id="customerSelection" multiple="multiple" style="width:100%">
                                           		 							<logic:iterate name="CustomerMasterSelectVo" id="CustomerMasterData">
																			<option value = '<bean:write name="CustomerMasterData" property="optionValue"/>'><bean:write name="CustomerMasterData" property="textValue"/></option>
																				</logic:iterate>
                                           		 							</select>
                                                                        </div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="customerRight" id="customerRight" value="->" alt="Move selected" style="width:50%"/><br />
                                            								<input type="button" name="customerRightAll" id="customerRightAll" value="->>" style="width:50%" /><br />
                                            								<input type="button" name="customerLeft" id="customerLeft" value="<-"  style="width:50%"/><br />
                                            								<input type="button" name="customerLeftAll" id="customerLeftAll" value="<<-" style="width:50%"/><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="customerSelected" id="customerSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                           	 						<div class="divRow row4">
                                            							<div class="divCell cell1">
                                            								<input type="button" id="" value="OR" /> 
                                            							</div>
                                            						</div>
                                            						<div class="divRow row5">
                                           		 						<div class="divCell cell1">
                                            								Select State:
                                            							</div>
                                            							<div class="divCell cell2">
                                           		 							<select size="5" name="stateSelection" id="stateSelection" multiple="multiple" style="width:100%">
                                           		 							<logic:iterate name="StateMasterSelectVo" id="StateMasterData">
																				<option value = '<bean:write name="StateMasterData" property="optionValue"/>'><bean:write name="StateMasterData" property="textValue"/></option>
																			</logic:iterate>
                                           		 							</select>
                                                                        </div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="stateRight" id="stateRight" value="->" alt="Move selected" style="width:45s%"/><br />
                                            								<input type="button" name="stateRightAll" id="stateRightAll" value="->>"  style="width:45%"/><br />
                                            								<input type="button" name="stateLeft" id="stateLeft" value="<-"  style="width:45%"/><br />
                                            								<input type="button" name="stateLeftAll" id="stateLeftAll" value="<<-" style="width:45%" /><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="stateSelected" id="stateSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                            						<div class="divRow row6">
                                            							<div class="divCell cell1">
                                            								<input type="button" id="generateArea" value="Generate Area" /> 
                                            							</div>
                                            						</div>
                                            						<div class="divRow row7">
                                           		 						<div class="divCell cell1">
                                            								Select Area:
                                            							</div>
                                            							<div class="divCell cell2">
                                           		 							<select size="5" name="areaSelection" id="areaSelection" multiple="multiple" style="width:100%"></select>
                                                                        </div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="areaRight" id="areaRight" value="->" alt="Move selected" style="width:45%"/><br />
                                            								<input type="button" name="areaRightAll" id="areaRightAll" value="->>"  style="width:45%"/><br />
                                            								<input type="button" name="areaLeft" id="areaLeft" value="<-"  style="width:45%"/><br />
                                            								<input type="button" name="areaLeftAll" id="areaLeftAll" value="<<-" style="width:45%" /><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="areaSelected" id="areaSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                            						<div class="divRow row8">
                                            							<div class="divCell cell1">
                                            								<input type="button" id="generateSite" value="Generate Site" /> 
                                            							</div>
                                            						</div>
                                            						<div class="divRow row9">
                                           		 						<div class="divCell cell1">
                                            								Select Site:
                                            							</div>
                                            							<div class="divCell cell2">
                                           		 							<select size="5" name="siteSelection" id="siteSelection" multiple="multiple" style="width:100%"></select>
                                                                        </div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="siteRight" id="siteRight" value="->" alt="Move selected" style="width:45%"/><br />
                                            								<input type="button" name="siteRightAll" id="siteRightAll" value="->>"  style="width:45%"/><br />
                                            								<input type="button" name="siteLeft" id="siteLeft" value="<-"  style="width:45%"/><br />
                                            								<input type="button" name="siteLeftAll" id="siteLeftAll" value="<<-" style="width:45%" /><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="siteSelected" id="siteSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                            						<div class="divRow row10">
                                            							<div class="divCell cell1">
                                            								<input type="button" id="generateWec" value="Generate WEC" /> 
                                            							</div>
                                            						</div>
                                            						<div class="divRow row11">
                                           		 						<div class="divCell cell1">
                                            								Select WEC:
                                            							</div>
                                            							<div class="divCell cell2">
                                           		 							<select size="5" class="wecSelection" id="wecSelection" multiple="multiple" style="width:100%"></select>
                                                                        </div>
                                            							<div class="divCell cell3">
                                            								<input type="button" name="wecRight" id="wecRight" value="->" alt="Move selected" style="width:45%"/><br />
                                            								<input type="button" name="wecRightAll" id="wecRightAll" value="->>"  style="width:45%"/><br />
                                            								<input type="button" name="wecLeft" id="wecLeft" value="<-"  style="width:45%"/><br />
                                            								<input type="button" name="wecLeftAll" id="wecLeftAll" value="<<-"  style="width:45%"/><br />
                                            							</div>
                                            							<div class="divCell cell4">
                                             								<select size="5" name="wecSelected" id="wecSelected" multiple="multiple" style="width:100%"></select>
                                            							</div>
                                            						</div>
                                            						<div class="divRow row12">
                                            							<div class="divCell cell1">
                                            								Select Period:
                                            							</div>
                                            							<div class="divCell cell2">
                                            								From:
                                            							</div>
                                            							<div class="divCell cell3">
                                            								<input type="text" name="fromDate" id="fromDate" size="15" maxlength="10" readonly="readonly">
                                            								<a href="javascript:void(0)" id="dc" onClick="if (self.gfPop) gfPop.fPopCalendar(fromDate); return false;" >
																			<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" title="Click to select from calendar">
																			</a>
                                            							</div>
                                            							<div class="divCell cell4">
                                            								To:
                                            							</div>
                                            							<div class="divCell cell5">
                                            								<input type="text" name="toDate" id="toDate" size="15" maxlength="10" readonly="readonly">
                                            								<a href="javascript:void(0)" id="dc" onClick="if (self.gfPop) gfPop.fPopCalendar(toDate); return false;" >
																			<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" title="Click to select from calendar">
																			</a>
                                            							</div>
                                            						</div>
                                            						<!-- <div class="divRow row13">
                                            							<div class="divCell cell1">
                                            								Select Report Type:
                                            							</div>
                                            							<div class="divCell cell2">
                                            								<select name="reportTypeSelection" id="reportTypeSelection" style="width:100%">
                                            								<option value="ns" >--Select Report--</option>
                                            								<option value="D" >--Daily--</option>
                                            								<option value="M" >--Monthly--</option>
                                            								<option value="Y" >--Yearly--</option>
                                            								</select>
                                            							</div>
                                            						</div> -->
                                            					</div>
                                            				</td>
                                            			</tr>
                                            	</tbody>
                                          </table>
                                           	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                        </td>
                                        <td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
                                    </tr>
                                    <tr>
                                        <td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
                                        <td colspan="4" align="right" bgcolor="#006633">
                                            <table border="0" cellpadding="0" cellspacing="0">
                                                <tbody>
                                                	<tr>
                                                        <td class="btn" width="100">
                                                            <input type="Submit" name="UploadCmd" class="btnform" value="Submit"/>
                                                        </td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                        <td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
                                                        <td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
                                                    </tr>
                                                </tbody>
                                         	</table>
                                        </td>
                                    </tr>                                               			
                			</tbody>
                		</table>
         			</form>
   				</td>
			</tr>
		</table>
	</div>
</body>
<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js"
            src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0"
            style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"> </iframe>
</html>