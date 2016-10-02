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
        
        var form = null;
		var sites = ${sites};//List of Javascript Object
		var siteSelectionField = null;
		var wecSelectionField = null;
		var siteId = null;
		var siteVo = null;
		var wecs = null;
		
         $(document).ready(function(){
	
        	var id = null;
        	var form = $("form[name='WecReadingForm']");    
        	siteSelectionField = $('#siteSelection');
        	wecSelectionField = $('#wecSelection');
 
        	$.fn.initializeSelectionField(siteSelectionField, sites, "id", "name");
        	
			$('#siteSelection').change(function(){
    			  			
    			$.fn.emptySelection([ "#wecSelection"]);
    			siteId = $(this).find(":selected").val();
    			siteVo = $.fn.getVo(siteId, sites ,["id"]);
    			wecs = siteVo["wecs"];    			
    			$.fn.initializeSelectionField(wecSelectionField, wecs,"id", "name");    	
    				
    		});
	
    		$("#WecReadingForm").submit(function(){
    			form = $("#WecReadingForm");
    			 if(!validateForm()){
    				return false;
    			} 
    			if(!getConfirmation()){
    				return false;
    			}

    			function getConfirmation(){
    				var answer = confirm("Are you sure you want to Submit?")
    				if (answer)
    				{ return true; } else { return false; }	
    			}
    			
    			function validateForm(){
    				
    				if($('#siteSelection').val() == "ns"){
          				alert("Please select Site.");
          				return false;
          			}
    				if($('#wecSelection').val() == "ns"){
          				alert("Please select Wec.");
          				return false;
          			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#fromDate", "Please Select From date")){
    					return false;
    				}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#toDate", "Please Select To date")){
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
            <html:form action="deleteWecData" method="post" styleId="WecReadingForm">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="110">Delete WEC Reading</th>
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
                                                <table border="0" cellpadding="2" cellspacing="1" width="500">
                                                    <tbody>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" >Select Site:</td>
                                                            <td valign="top">
                                                                <select name="siteId" id="siteSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>                                                        
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" > Select WEC:</td>
                                                            <td valign="top">                                                                
                                                                <select name="wecId" id="wecSelection" class="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </select>
                                                            </td>
                                                        </tr>	
														<tr bgcolor="#ffffff"> 
																<td id="t_company">Measuring Point:</td>
																<td valign="top">
																    <select name="mpId" id="mpSelection" class="ctrl" >              
													                	<option value="All">-- ALL --</option>	
													                	<jsp:include
																				page="/jsppages/utility/SelectVoTemplateV2.jsp">
																				<jsp:param name="beanIdentifier" value="meaPoint" />
																				<jsp:param name="option" value="id" />
																				<jsp:param name="text" value="desc" />
																		</jsp:include>											            
																    </select>
																</td>																																					
														</tr>
														<tr bgcolor="#ffffff">
                                                            <td id="t_company" > From Date:</td>
                                                            <td class="bgcolor">
																<input type="text" name="fromDate" id="fromDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()"  />
																<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WecReadingForm.fromDate);return false;" >
																	<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
																</a>
															</td>			
                                                        </tr>	
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" > To Date:</td>
                                                            <td class="bgcolor">
																<input type="text" name="toDate" id="toDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()"  />
																<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WecReadingForm.toDate);return false;" >
																	<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
																</a>
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
            	
        </div>
    </body>
     <iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${ contextPath }/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>
</html>