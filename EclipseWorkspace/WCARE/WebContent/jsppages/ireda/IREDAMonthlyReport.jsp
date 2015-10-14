<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@page import="com.enercon.security.utils.SecurityUtils" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@page import="com.enercon.admin.dao.WcareDao"%>
<html>
    <head>
        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        
        <%-- <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet> --%>
        <%-- <LINK href="<%=request.getContextPath()%>/resources/resources\deluxe-menu.files/style.css" type=text/css rel=stylesheet> --%>
        
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
        <script type="text/javascript"  src="<%=request.getContextPath()%>/resources/js/Grid/GridE.js"></script>

		<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/css/ScadaDataJumpReport.css"/>
 
         <script type="text/javascript" src="<%=request.getContextPath()%>/scripts/mscript/lib/jquery-1.10.1.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/mySelectFunction.js"></script>
        
        <%

        //String dd = "";
        //session.removeAttribute("dynabean");
        //DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
        //if(dynabean != null){		
        //	dd = dynabean.getProperty("Datetxt").toString();
        //	si = dynabean.getProperty("SiteIdtxt").toString();
        //	session.removeAttribute("dynabean");
        //}

        %>
        <%

            response.getOutputStream().flush();
            response.getOutputStream().close();
            String userid = session.getAttribute("loginID").toString();
            String Customeridtxt = "";
            List<Integer> fiscalYears=WcareDao.getFiscalYear();
            String custid = "";
            int k = 0;
            StringBuffer buffer = new StringBuffer();
            SecurityUtils secUtil = new SecurityUtils();
            String got = "0";         
         %>
         <style type="text/css">
         	/* .state{
         		border-color:black;
         		border-style: none; 
         		 
         	} */
         	
/*          	BODY {
	FONT-SIZE: 8pt; FONT-FAMILY: Arial, Helvetica, sans-serif;
	color: #3E5C0E;
} */
TD {
	FONT-SIZE: 8pt; FONT-FAMILY: Arial, Helvetica, sans-serif
}
A:active {
	COLOR: #0000cc; TEXT-DECORATION: none
}
A:link {
	COLOR: #0000cc; TEXT-DECORATION: none
}
A:visited {
	COLOR: #0000ff; TEXT-DECORATION: none
}
A:hover {
	COLOR: #0000ff; TEXT-DECORATION: underline
}
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
.TableSolidRow {
	FONT-WEIGHT: bold; BACKGROUND-COLOR:#C0C0C0
}
.TableSummaryRow {
	
	FONT-WEIGHT: bold; 
	;BORDER-RIGHT: #d4d4d4 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #d4d4d4 1px solid; PADDING-LEFT: 5px; BACKGROUND-IMAGE: url(images/bgfadegreen.gif); PADDING-BOTTOM: 0px; BORDER-LEFT: #d4d4d4 1px solid; COLOR: #000000; PADDING-TOP: 0px; BORDER-BOTTOM: #d4d4d4 1px solid; BACKGROUND-REPEAT: repeat-x; BACKGROUND-COLOR: #e5eecc

}
.TableCell {
	PADDING-RIGHT: 4pt; PADDING-LEFT: 4pt BACKGROUND-COLOR: #C0C0C0 ; TEXT-ALIGN: center
}
.TableCell11 {
	PADDING-RIGHT: 4pt; FONT-SIZE: 10pt; PADDING-LEFT: 4pt BACKGROUND-COLOR: #C0C0C0 ;TEXT-ALIGN: center
}
.TableCell1 {
	PADDING-RIGHT: 4pt; PADDING-LEFT: 4pt BACKGROUND-COLOR: #C0C0C0 ;TEXT-ALIGN: left
}
.TableCell2 {
	PADDING-RIGHT: 4pt; PADDING-LEFT: 4pt BACKGROUND-COLOR: #C0C0C0 ;TEXT-ALIGN: right
}
.ReportTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 13pt
}
.CategoryTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 13pt
}
.SinglePageCategoryTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 13pt
}
.ChartTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 10pt; TEXT-ALIGN: center
}
.SectionTitle {
		FONT-WEIGHT: bold;  TEXT-ALIGN: center;COLOR: white
		;BORDER-RIGHT: #d4d4d4 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #d4d4d4 1px solid; PADDING-LEFT: 5px; BACKGROUND-IMAGE: url(images/bgfadegreen.gif); PADDING-BOTTOM: 0px; BORDER-LEFT: #d4d4d4 1px solid; COLOR: #000000; PADDING-TOP: 0px; BORDER-BOTTOM: #d4d4d4 1px solid; BACKGROUND-REPEAT: repeat-x; BACKGROUND-COLOR: #e5eecc

}
.SectionTitleLeft {
	FONT-WEIGHT: bold;  TEXT-ALIGN: left;COLOR: white
		;BORDER-RIGHT: #d4d4d4 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #d4d4d4 1px solid; PADDING-LEFT: 5px; BACKGROUND-IMAGE: url(images/bgfadegreen.gif); PADDING-BOTTOM: 0px; BORDER-LEFT: #d4d4d4 1px solid; COLOR: #000000; PADDING-TOP: 0px; BORDER-BOTTOM: #d4d4d4 1px solid; BACKGROUND-REPEAT: repeat-x; BACKGROUND-COLOR: #e5eecc

}
.SectionTitleRight {
	FONT-WEIGHT: bold;  TEXT-ALIGN: right;COLOR: white
		;BORDER-RIGHT: #d4d4d4 1px solid; PADDING-RIGHT: 10px; BORDER-TOP: #d4d4d4 1px solid; PADDING-LEFT: 5px; BACKGROUND-IMAGE: url(images/bgfadegreen.gif); PADDING-BOTTOM: 0px; BORDER-LEFT: #d4d4d4 1px solid; COLOR: #000000; PADDING-TOP: 0px; BORDER-BOTTOM: #d4d4d4 1px solid; BACKGROUND-REPEAT: repeat-x; BACKGROUND-COLOR: #e5eecc

}
.SectionTitle1 {
	FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: white; BACKGROUND-COLOR:#C0C0C0; 
}
.SectionTitle2 {
	FONT-WEIGHT: bold; FONT-SIZE: 10pt; COLOR: white; BACKGROUND-COLOR:#C0C0C0;
}
/* .TOC {
	BACKGROUND-IMAGE: url(contents_back.gif); BACKGROUND-REPEAT: repeat-x
}
.TranslatedContentsTitle {
	FONT-WEIGHT: bold; FONT-SIZE: 11pt; COLOR: #333333
}
 */

         	
         </style>
          <script type="text/javascript">
        	$(document).ready(function(){
        		
//         		$.fn.myFunction("projectSelection", "projectSelected", "projectLeftAll", "projectRightAll", "projectLeft", "projectRight");        	
//         		$.fn.myFunction("wecSelection", "wecSelected", "wecLeftAll", "wecRightAll", "wecLeft", "wecRight");
        		$("#iredaMonthlyReport").submit(function(){
        			var projectSelected = $('#projectSelected').val();
        			var monthSelected = $('#Monthtxt').val();
        			var yearSelected = $('#Yeartxt').val(); 
        			if(projectSelected == "ns"){
        				alert("Please select Project.");
        				return false;
        			}
        			
        			if(monthSelected == "ns"){
        				alert("Please select Month.");
        				return false;
        			}
        			
        			if(yearSelected == "ns"){
        				alert("Please select Year.");
        				return false;
        			}
        			
        			alert("Project Selected : " + projectSelected + ", Month Selected : " + monthSelected + ", Year Selected : " + yearSelected);
        			/* alert("Report Implementation Date 01.02.2014(As we have started entering data in this specific format from 01-Feb-2014 onward).Also two additional column ('Customer Scope Shutdown' and 'Grid Trip Count') have been added from 27-AUG-2014");
        			$.fn.selectAllFunction("projectSelected"); */
        			//var multipleProjectNo = $.fn.getSelectOptionValueForQuery("projectSelected");
        			var multipleProjectNo = projectSelected;
        			
        			var param = {ajaxType: "getIREDAListVoBasedOnMultipleProjectNoAndMonthYear", projectNos: multipleProjectNo, month : monthSelected, year:yearSelected};
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
			                $("<th/>", {text:"Statewise Power Generation for " + $.fn.getMonthName(parseInt(monthSelected,10)) + "," + yearSelected, colspan : "7"}).appendTo("#tr2").css({"font-size":"20px"});
			                
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
			                    
			                    //private Map<String, ManyWECsManyDatesTotal> stateWiseTotalForManyDays;
								//private ManyWECsManyDatesTotal grandTotalForManyDays;
			                    $.each(ireda.stateWiseTotalForManyDays, function(index1, stateDetails){
			                    	if(stateDetails !== null){
				                        stateCount = stateCount + 1;
				                        className = projectNo + stateCount;
				                        $("<tr/>", {class:className}).appendTo("#tbod");
				                        
				                        $("<td/>", {class:"a" + className}).appendTo("." + className).css({"text-align" : "center"});
				                        
				                        $("<a/>", {text : ireda.stateIdNameMapping[index1] + "(" + stateDetails.wecIds.length + ")", href : "IredaWecWiseStatewiseMonthlyReport.do?" + "stateId=" + index1 + "&iredaProjectNo=" + projectNo + "&month=" + monthSelected + "&year=" + yearSelected, target:"_blank" }).appendTo(".a" + className).css({"text-align" : "center"});
				                        <%-- $("<a href=\"" + <%=request.getContextPath()%> + "\IredaWecWiseStatewiseMonthlyReport.do\">" + ireda.stateIdNameMapping[index1] + "(" + stateDetails.wecIds.length + ")</a>").appendTo(".a" + className).css({"text-align" : "center"}); --%>
				                        //$("<a/>", {text: ireda.stateIdNameMapping[index1] + "(" + stateDetails.wecIds.length + ")"}).appendTo(".a" + className).css({"text-align" : "center"});)
				                        $("<td/>", {text:$.fn.formatNumber(stateDetails.generation)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:$.fn.formatTime(stateDetails.operatingHour)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:$.fn.formatTime(stateDetails.lullHours)}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.capacityFactor}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.mavial}).appendTo("." + className).css({"text-align" : "center"});
				                        $("<td/>", {text:stateDetails.gavial}).appendTo("." + className).css({"text-align" : "center"});
			                    	}
			                        
			                    });
			                    //console.log(index + ":" + ireda.projectNo + "State Count : " + stateCount);
			                    
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
			                    if(stateCount == 0){
			                    	$("<tr/>", {class:projectNo + "noData"}).appendTo("#tbod");
			                    	$("<td/>", {text:"Data Not Available",colspan:"7"}).appendTo("." + projectNo + "noData");
			                    }
			                    $("<tr/>", {class:projectNo}).appendTo("#tbod").css({height:"15px"});
			                });
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
                        <form action="<%=request.getContextPath()%>/IREDAMonthlyReportRequestProcessor.do" method="post" enctype="multipart/form-data" name = "iredaMonthlyReport" id = "iredaMonthlyReport">
                            <table align="center" border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tbody>
                                	<tr>
                                        <td class="newhead1"></td>
                                        <th class="headtext">Monthly Generation Report</th>
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
																	
																		<select size="1" name="projectSelected" id="projectSelected" style="width: 100%">
																		  <option value="ns">--Make a Selection--</option>
																		    <option value="ALL">ALL</option>
																		<logic:iterate name="ProjectSelectVo" id="ProjectMasterData">
																			<option value = '<bean:write name="ProjectMasterData" property="optionValue"/>'><bean:write name="ProjectMasterData" property="textValue"/></option>
																		</logic:iterate>
																		</select>

																</div>
<!-- 																	<div class="divCell cell3"> -->
<!-- 																		<input type="button" id="projectRight"  value="->" alt="Move selected"  style="width:50%" /><br /> -->
<!--                                                         				<input type="button" id="projectRightAll"  value="->>"  style="width:50%" /><br /> -->
<%--                                                         				<input type="button" id="projectLeftAll"  value="<<-"  style="width:50%" /><br /> --%>
<%--                                                         				<input type="button" id="projectLeft"  value="<-"  style="width:50%" /> --%>
<!-- 																	</div> -->
<!-- 																	<div class="divCell cell4"> -->
<!-- 																		<select size="5" name="projectSelected" id="projectSelected"   multiple="multiple" style="width: 100%"> -->
<!-- 																		</select> -->
<!-- 																	</div> -->
																</div>
<!-- 																<div class="divRow row10"> -->
<!-- 																	<div class="divCell cell1"> -->
<!-- 																		<input type="button" id="generateWec" value="Generate WEC"/> -->
<!-- 																	</div> -->
<!-- 																</div>															 -->
<!-- 																<div class="divRow row11"> -->
<!-- 																	<div class="divCell cell1"> -->
<!-- 																		Select WEC: -->
<!-- 																	</div> -->
<!-- 																	<div class="divCell cell2"> -->
<!-- 																		<select size="5" name="wecSelection" id="wecSelection" multiple="multiple" style="width: 100%"> -->
																			
<!-- 																		</select> -->
<!-- 																	</div> -->
<!-- 																	<div class="divCell cell3"> -->
<!-- 																		<input type="button" id="wecRight"  value="->" alt="Move selected"  style="width:50%" /><br /> -->
<!--                                                         				<input type="button" id="wecRightAll"  value="->>"  style="width:50%" /><br /> -->
<%--                                                         				<input type="button" id="wecLeftAll"  value="<<-"  style="width:50%" /><br /> --%>
<%--                                                         				<input type="button" id="wecLeft"  value="<-"  style="width:50%" /> --%>
<!-- 																	</div> -->
<!-- 																	<div class="divCell cell4"> -->
<!-- 																		<select size="5" name="wecSelected" id="wecSelected"   multiple="multiple" style="width: 100%"> -->
<!-- 																		</select> -->
<!-- 																	</div> -->
<!-- 																</div> -->
																<div class="divRow row12">
																	<div class="divCell cell1">
																		Select Month:
																	</div>
																	<div class="divCell cell3">
																		<select size="1" id="Monthtxt" name="Monthtxt">
                                                                            <option value="ns">--Make a Selection--</option>
                                                                            <option value="01">JANUARY</option>
                                                                            <option value="02">FEBRUARY</option>
                                                                            <option value="03">MARCH</option>
                                                                            <option value="04">APRIL</option>
                                                                            <option value="05">MAY</option>
                                                                            <option value="06">JUNE</option>
                                                                            <option value="07">JULY</option>
                                                                            <option value="08">AUGUST</option>
                                                                            <option value="09">SEPTEMBER</option>
                                                                            <option value="10">OCTOBER</option>
                                                                            <option value="11">NOVEMBER</option>
                                                                            <option value="12">DECEMBER</option>
                                                                            </select>
																		
																	</div>
																	<div class="divCell cell4">
																		Year:
																	</div>
																	<div class="divCell cell5">
																		<select size="1" id="Yeartxt" name="Yeartxt" >
                                                                            <option value="ns">--Make a Selection--</option>
                           													 <%for(int i=0;i<fiscalYears.size();i++){ 
	                                                                             	int year=fiscalYears.get(i);%> 
	                                                                             	<option value="<%=year %>"><%=year %></option>
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