<%@page import="java.util.List"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@page import="com.enercon.security.utils.SecurityUtils" %>
<%@ page language="java" contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/GridBifurcationReport.css"/>

<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.text.*" %>
<%@page import="com.enercon.admin.dao.WcareDao"%>
        <%
            response.setHeader("Pragma", "no-cache");
            response.getOutputStream().flush();
            response.getOutputStream().close();
        %>
<html>
    <head>
        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"></script>

<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/ScadaDataJumpReport.css"/>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
         <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>

        <%

            response.getOutputStream().flush();
            response.getOutputStream().close();
            String userid = session.getAttribute("loginID").toString();
            String Customeridtxt = "";
            List<Integer> financialYears=WcareDao.getFinancialYears();
            String custid = "";
            int k = 0;
            StringBuffer buffer = new StringBuffer();
           // SecurityUtils secUtil = new SecurityUtils();
            String got = "0";         
         %>
         <style type="text/css">
         	.TableTitleRow {
	FONT-WEIGHT: bold;  TEXT-ALIGN: center;COLOR: white
	;BORDER-RIGHT: #d4d4d4 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #d4d4d4 1px solid; PADDING-LEFT: 5px; BACKGROUND-IMAGE: url(images/bgfadegreen.gif); PADDING-BOTTOM: 0px; BORDER-LEFT: #d4d4d4 1px solid; COLOR: #000000; PADDING-TOP: 0px; BORDER-BOTTOM: #d4d4d4 1px solid; BACKGROUND-REPEAT: repeat-x; BACKGROUND-COLOR: #e5eecc
}

/* .btn
{border: 1px; padding:0px;  FONT-WEIGHT: bold; font-size: 14px; COLOR: #000000; background-color: #D2EBAB; width:40%; height:25px;  cursor: hand;} */
	
.TableTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 10pt; TEXT-ALIGN: center
}
.TableRow1 {
	BACKGROUND-COLOR: #F6F6EC
}
.TableRow2 {
	BACKGROUND-COLOR: #D2EBAB
}
         </style>
          <script type="text/javascript">
        	$(document).ready(function(){
        		
        		$("#iredaYearlyReport").submit(function(){
        			var projectSelected = $('#projectSelected').val();
        			 
        			if(projectSelected == "ns"){
        				alert("Please select Project.");
        				return false;
        			}
        			
        			var yearSelected = $('#FiscalYeartxt').val();
        			if(yearSelected == "ns"){
        				alert("Please select Year.");
        				return false;
        			}
        			
        			alert("Project Selected : " + projectSelected + ", Year Selected : " + yearSelected);
        			var multipleProjectNo = projectSelected;
        			
        			var param = {ajaxType: "getIREDAListVoBasedOnMultipleProjectNoAndFiscalYear", projectNos: multipleProjectNo, fiscalYear:yearSelected};
        			//console.log(param);
        			$(".details").empty();
        			$("#progressbar").css({"display":"block"})
        			$.ajax({
						url: 'AjaxFrontController.do',
					  	data: param,
					  	type: 'POST',
					  	dataType: 'json',
					  	success: function(data){
					  		
					  		
					  		$("#progressbar").css({"display":"none"})
					  		
					  		//console.log("Success");
					  		//console.log(data);
					  		
					  		$("<table/>", {id:"ttab",border:"1"}).appendTo(".details");
			                
					        
			                $("<thead/>", {id:"thea",class:"TableTitleRow"}).appendTo("#ttab").css({"border":"inherit"});
			                $("<tr/>", {id:"tr2"}).appendTo("#thea").css({"border":"inherit"});
			                $("<th/>", {text:"Statewise Power Generation for Fiscal Year " + yearSelected + "-" + (1 + parseInt(yearSelected)), colspan : "7"}).appendTo("#tr2").css({"font-size":"20px"});
			                
			                $("<tr/>", {id:"tr1",class:"TableRow1"}).appendTo("#thea").css({"font-size":"12px","border":"inherit"});
			                
			                
			                $("<th/>", {text:"State(No. Of WECs)",class:"TableRow1"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Generation(KWH)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Operating Hour (HH:MM)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Lull Hours (HH:MM)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Capacity Factor(%)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Machine Avail(%)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                $("<th/>", {text:"Grid Avail(%)"}).appendTo("#tr1").css({"font-size":"inherit"});
			                
			                $("<tbody/>", {id:"tbod",class:"TableTitleRow"}).appendTo("#ttab");
			                
			                
			                var projectNo = "";
			                var stateCount = 0;
			                var className = "";//projectNo + statecount
			                var iredaProject = data;
			                $.each(iredaProject, function(index, ireda) {
			                    
			                    //Accessing Project No
			                    projectNo = ireda.projectNo;
			                    $("<tr/>", {class:projectNo + " TableRow2"}).appendTo("#tbod");
			                    $("<td/>", {text:"Project No : " + projectNo, colspan:"7"}).appendTo("." + projectNo).css({"text-align" : "center", "font-size" : "15px"});
			                    
			                    stateCount = 0;
			                    
			                    $.each(ireda.stateWiseTotalForManyDays, function(index1, stateDetails){
			                    	if(stateDetails !== null){
				                        stateCount = stateCount + 1;
				                        className = projectNo + stateCount;
				                        $("<tr/>", {class:className}).appendTo("#tbod");
				                        
				                        $("<td/>", {class:"a" + className}).appendTo("." + className).css({"text-align" : "center"});
				                        
				                        $("<a/>", {text : ireda.stateIdNameMapping[index1] + "(" + stateDetails.wecIds.length + ")", href : "IredaFiscalYearWecWiseBasedOnStateReport.do?" + "stateId=" + index1 + "&iredaProjectNo=" + projectNo + "&fiscalYear=" + yearSelected, target:"_blank" }).appendTo(".a" + className).css({"text-align" : "center"});
				                        $("<td/>", {text:$.fn.formatNumber(stateDetails.generation)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:$.fn.formatTime(stateDetails.operatingHour)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:$.fn.formatTime(stateDetails.lullHours)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.capacityFactor}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.mavial}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.gavial}).appendTo("." + className).css({"text-align" : "center"});
			                    	}
			                    });
//			                    console.log(index + ":" + ireda.projectNo + "State Count : " + stateCount);
			                    
			                    if(stateCount > 1){
				                    $("<tr/>", {class:"total" + " TableRow2"}).appendTo("#tbod");
				                    $("<td/>", {text:"Total"}).appendTo(".total");
				                    $("<td/>", {text:$.fn.formatNumber(ireda.grandTotalForManyDays.generation)}).appendTo(".total");
				                    $("<td/>", {text:$.fn.formatTime(ireda.grandTotalForManyDays.operatingHour)}).appendTo(".total");
				                    $("<td/>", {text:$.fn.formatTime(ireda.grandTotalForManyDays.lullHours)}).appendTo(".total");
				                    $("<td/>", {text:ireda.grandTotalForManyDays.capacityFactor}).appendTo(".total");
				                    $("<td/>", {text:ireda.grandTotalForManyDays.mavial}).appendTo(".total");
				                    $("<td/>", {text:ireda.grandTotalForManyDays.gavial}).appendTo(".total");
				                    //console.log(ireda.grandTotalForManyDays.generation);
			                    }
			                    
			                    //Handling if no data is present
			                    if(stateCount == 0){
			                    	$("<tr/>", {class:projectNo + "noData"}).appendTo("#tbod");
			                    	$("<td/>", {text:"Data Not Available",colspan:"7"}).appendTo("." + projectNo + "noData");
			                    }
			                    $("<tr/>", {class:projectNo}).appendTo("#tbod").css({height:"15px"});
			                });
					  	},
						failure: function(data){
					  		
					  		
					  		$("#progressbar").css({"display":"none"})
					  		
					  		//console.log("Failure");
					  		//console.log(data);
					  		$(".details").innerHTML = "Data not available";
					  		
					  	}
					});
        			
        			return false;
        		});
        	});
        		</script>
 </head>
 
  <body>

         <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="80%">
                <tr width="100%">
                    <td width="100%" align="center">
                        <form action="<%=request.getContextPath()%>/YearlyReportForIREDARequestProcessor.do" method="post" enctype="multipart/form-data" name = "iredaYearlyReport" id = "iredaYearlyReport">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Fiscal-Year Generation Report</th>
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
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                            	<tbody>
                                            		<tr>
                                            			<td bgcolor="#dbeaf5">
                                                            <div class="divTable">
																<div class="divRow row12">
																	<div class="divCell cell1">
																		Select Project:
																	</div>
																	<div class="divCell cell3">
																	
																		<select size="1" name="projectSelected" id="projectSelected"  style="width: 100%">
																		 <option value="ns">--Make a Selection--</option>
																		    <option value="ALL">ALL</option>
																		<logic:iterate name="ProjectSelectVo" id="ProjectMasterData">
																			<option value = '<bean:write name="ProjectMasterData" property="optionValue"/>'><bean:write name="ProjectMasterData" property="textValue"/></option>
																		</logic:iterate>
																		</select>
																	
																	</div>
																</div>
																
																<div class="divRow row12">
																	<div class="divCell cell1">
																		Select Fiscal-Year:
																	</div>
																	<div class="divCell cell3">
																		
                                                                        <select size="1" id="FiscalYeartxt" name="FiscalYeartxt">
                                                                            <option value="ns">--Make a Selection--</option>
                                                    							<%for(int i=0;i<financialYears.size();i++){
                                                                            		int year=financialYears.get(i);%> 
                                                                             		<option value="<%=year %>"><%=year %>-<%=year+1 %></option>
                                                                             	<% }%>                                                                 
                                                                        </select>
																	</div>										
																</div>
																																
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
                                    
                                    <tr>
                                        <td colspan="5" height="50px">
                                        	<DIV id=progressbar style="DISPLAY: none" align="center">
                                        		<IMG style="VERTICAL-ALIGN: bottom" src="<%=request.getContextPath()%>/resources/images/progressbar.gif" />
                                                                      	<Font size=4> Please wait...downloading data </Font>
                                            </DIV>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="details" colspan="5">
                                        	
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
 </html>