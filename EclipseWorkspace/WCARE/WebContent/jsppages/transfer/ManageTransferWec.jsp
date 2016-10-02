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
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
        <script type="text/javascript">
        
        	var customers = null;
        	var toEbs = null;
        	var fromEbs = null;
        	
        	$(document).ready(function(){
        		customers = ${customers};
        		
        		$.fn.initializeSelectionField($('#fromCustomerIdSelection'), customers, "id", "name");
        		$.fn.initializeSelectionField($('#toCustomerIdSelection'), customers, "id", "name");
        		
        		$('#fromCustomerIdSelection').change(function(){
            		$('#fromEbIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		$('#fromWecIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		var customerVo = $.fn.getVo($(this).find(":selected").val(), customers, "id");
            		fromEbs = customerVo["ebs"];
            		$.fn.initializeSelectionField($('#fromEbIdSelection'), fromEbs, "id", "name");
            	});
        		
        		$('#toCustomerIdSelection').change(function(){
            		$('#toEbIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		$('#toWecIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		var customerVo = $.fn.getVo($(this).find(":selected").val(), customers, "id");
            		toEbs = customerVo["ebs"];
            		$.fn.initializeSelectionField($('#toEbIdSelection'), toEbs, "id", "name");
            	});
        		
        		$('#fromEbIdSelection').change(function(){
            		$('#fromWecIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		var ebVo = $.fn.getVo($(this).find(":selected").val(), fromEbs, "id");
            		$.fn.initializeSelectionField($('#fromWecIdSelection'), ebVo["wecs"], "id", "name");
            	});
        		
        		$('#toEbIdSelection').change(function(){
        			$('#toWecIdSelection').empty().append(new Option("--Make a Selection--", "ns"));
            		var ebVo = $.fn.getVo($(this).find(":selected").val(), toEbs, "id");
            		$.fn.initializeSelectionField($('#toWecIdSelection'), ebVo["wecs"], "id", "name");
            	});
        		
        		$('#reset').click(function(){
        			toEbs = null;
                	fromEbs = null;
        		});
        		
        	});
            /* function findvalidate()
            {
                var blnSave = false;
                blnSave = validateForm('Customer From', document.forms[0].CustomerFromtxt.value, 'M', '',
                        'WEC From', document.forms[0].WECtxt.value, 'M', '',
                        'EB From', document.forms[0].EBtxt.value, 'M', '',
                        'Customer To', document.forms[0].CustomerTotxt.value, 'M', '',
                        'WEC To', document.forms[0].WECTotxt.value, 'M', '',
                        'EB To', document.forms[0].EBTotxt.value, 'M', '',
                        'Remarks', document.forms[0].Remarkstxt.value, 'M', '',
                        'DATE', document.forms[0].Datetxt.value, 'M', '');
                if (blnSave == true)
                {
                    return true;

                    if (document.forms[0].WECtxt.value == document.forms[0].WECTotxt.value)
                    {
                        alert("Not proceed because WEC From and WEC To are same.");
                        return false;
                    }

                }
                else
                {
                    return false;
                }
            }
            function confirmation()
            {
                var answer = confirm("Are you sure you want to Submit?")
                if (answer)
                {
                    return true;
                }
                else
                {
                    return false;
                }

            } */
        </script>
    </head>
    
    <body>
        <div align="center">
            <table align="center" border="0" cellpadding="0" cellspacing="0" width="800">
                <tr width="100%">
                    <td width="100%" align="center">
		                <html:form action="manageTransferWec" method="post">
		                    <table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
		                        <tbody>
		                            <tr>
		                                <td class="newhead1"></td>
		                                <th class="headtext">Transfer WEC from Customer</th>
		                                <td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
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
		                                                    <table border="0" cellpadding="2" cellspacing="1" width="100%">
		                                                        <tbody>		
		                                                            <tr class="bgcolor">
		                                                                <td id="t_street_address">Customer From:</td>
		                                                                <td class="bgcolor">
		                                                                    <%-- <select size="1" name="CustomerFromtxt" id="CustomerFromtxt" class="ctrl" onChange="findApplication()">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                        <%=custfromid%>
		                                                                    </select> --%>
		                                                                    <html:select size="1" property="fromCustomerId" styleId="fromCustomerIdSelection" styleClass="ctrl">
		                                                                        <option value="ns">--Make a Selection--</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>
		
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">Select EB From:</td>
		                                                                <td class="bgcolor">
		                                                                    <!-- <select size="1" id="EBtxt" name="EBtxt" class="ctrl" onChange="findApplicationWEC()">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                    </select> -->
		                                                                    <html:select size="1" styleId="fromEbIdSelection" property="fromEbId" styleClass="ctrl">
		                                                                        <option value="ns">--Make a Selection--</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>
		
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">Select WEC From:</td>
		                                                                <td class="bgcolor">
		                                                                    <!-- <select size="1" id="WECtxt" name="WECtxt" class="ctrl">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                    </select> -->
		                                                                    <html:select size="1" styleId="fromWecIdSelection" property="fromWecId" styleClass="ctrl">
		                                                                        <option value="ns">--Make a Selection--</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>	
		                                                            <tr class="bgcolor">
		                                                                <td id="t_street_address">Customer To:</td>
		                                                                <td class="bgcolor">
		                                                                    <%-- <select size="1" name="CustomerTotxt" id="CustomerTotxt" class="ctrl" onChange="findApplication_1()">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                        <%=custtoid%>
		                                                                    </select> --%>
		                                                                    <html:select size="1" property="toCustomerId" styleId="toCustomerIdSelection" styleClass="ctrl">
		                                                                        <option value="ns">--Make a Selection--</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>
		
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">Select EB To:</td>
		                                                                <td class="bgcolor">
		                                                                    <!-- <select size="1" id="EBTotxt" name="EBTotxt" class="ctrl" onChange="findApplication_1WECTo()">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                    </select> -->
		                                                                    <html:select size="1" styleId="toEbIdSelection" property="toEbId" styleClass="ctrl">
		                                                                        <option value="ns">--Make a Selection--</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>
		
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">Select WEC To:</td>
		                                                                <td class="bgcolor">
		                                                                    <!-- <select size="1" id="WECTotxt" name="WECTotxt" class="ctrl">
		                                                                        <option value="">--Make a Selection--</option>
		                                                                    </select> -->
		                                                                    <html:select size="1" styleId="toWecIdSelection" property="toWecId" styleClass="ctrl">
		                                                                        <option value="ns">-- Make a Selection --</option>
		                                                                    </html:select>
		                                                                </td>
		                                                            </tr>
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">From Date:</td>
		                                                                <td class="bgcolor">
		                                                                    <%-- <input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value="<%=dt%>" onfocus="dc.focus()" /> --%>
		                                                                    <html:text property="transferDate" styleId="transferDate" size="20" styleClass="ctrl" maxlength="10" onfocus="dc.focus()" />
		                                                                    <a href="javascript:void(0)" id="dc" onClick="if (self.gfPop)
		                                                                                        gfPop.fPopCalendar(document.ManageTransferWecForm.transferDate);
		                                                                                    return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
		                                                                </td>
		                                                            </tr>	
		                                                            <tr class="bgcolor"> 
		                                                                <td id="t_street_address">Remarks:</td>
		                                                                <td class="bgcolor">
		                                                                    <%-- <input type="text" name="Remarkstxt" id="Remarkstxt" size="20" class="ctrl" maxlength="50" value="<%=rem%>" /> --%>
		                                                                    <html:text property="transferRemark" styleId="transferRemark" size="20" styleClass="ctrl" maxlength="50" />
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
		                                                    <!-- <input type="submit" name="Submitcmd" class="btnform" value="Submit" onClick="return confirmation();"/> -->
		                                                    <html:submit value="Submit" styleClass="btnform" styleId="submit"/>
					                                    	
		                                                    <!-- <input type="hidden" name="Admin_Input_Type" value="TransferWEC" /> -->
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
					</td>		
				</tr>
			</table>
		</div>
	</body>
    <iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
    </iframe>
</html>