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
<script type="text/javascript">
var filter = function(fun, arr){
	var len = arr.length;
  
	if (typeof fun != "function") throw new TypeError();
  
	var res = new Array();
     
	for (var i = 0; i < len; i++) {
		var val = arr[i];
		//console.dir(val);
		if (fun(val, i))
			res.push(val);
	}
	
	return res;
}
</script>
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
		var actionStatus = actionPerform.update;
		
        function initializeSelectionField(selectionField, vos){
    		for(var vo of vos){
    			selectionField.append(new Option(vo.loginDescription + "-" + vo.userId, vo.id));
    		}
    	}
	
		var form = null;
		var loginId = null;
		var customerId = null;
		var logins = ${logins};//List of Javascript Object
		
		logins = $.grep(logins, 
						function(element, index) {
							return (element["loginType"] == "C");
						});
		
		logins.sort(function(v, w){
			return v["loginDescription"].toLowerCase().localeCompare(w["loginDescription"].toLowerCase());
		});
		/* loginDescription */
		
		var customers = ${customers}
		customers.sort(function(v, w){
			return v["name"].localeCompare(w["name"]);
		});
		var loginSelectionField = null;
		var customerSelectionField = null;
		var loginDetails = null;
		
        $(document).ready(function(){
        	
        	form = $("form[name='ManageCustomerLogin']");
        	
        	loginSelectionField = $('#loginSelection');
        	customerSelectionField = $('#customerSelection');
        	loginDetails = document.getElementById("loginDetails");
        	
        	initializeSelectionField(loginSelectionField, logins);
        	$.fn.initializeSelectionField(customerSelectionField, customers, "id", "name");
        	
        	function initialize(){
        		loginSelectionField.prop("disabled", false);
        		customerSelectionField.prop("disabled", false);
    			actionStatus = actionPerform.create;
    			loginId = null;
    			customerId = null;
    			loginDetails.innerHTML = "";
    			changeValueOfSubmit("Submit");
    		}
        	
        	$('#loginSelection').change(function(){
    			loginId = $(this).find(":selected").val();
    			populateCustomers(getCustomers(loginId, logins));
			    
			    function populateCustomers(logins){
			    	loginDetails.innerHTML = "";
		    		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>";
		    		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>Login Id</th><th class='detailsheading' width='100'>Customer Id</th>";
		    		str +="<th class='detailsheading' width='100'>Customer Name</th><th class='detailsheading' width='100'>Total WEC</th></tr>";
		    		var customers = logins["customers"];
		    		var classPicker = null;
		    		
		    		$.each(customers, function (i, vo) {
   			
            			if (i % 2 == 0) classPicker = "detailsbody";
            			else classPicker = "detailsbody1";
			      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + vo.id + "'><td ALIGN='center'>"+(i+1)+"</td>";
	     				str+="<td align='left'>" + logins.id + "</td>";
	     				str+="<td align='left'>" + vo.id + "</td>";			     			     				
	     				str+="<td align='left'>" + vo.name + "</td>";
	     				str+="<td align='center'>" + vo["wecs"].length + "</td></tr>";

            		});
		    		
		    		loginDetails.innerHTML = str;
			    }
			    
			    function getCustomers(id, vos){
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
        		customerId = $("#customerSelection").find(":selected").val();
    			 if(!validateForm()){
    				return false;
    			}
    			 if(!getConfirmation()){
    				return false;
    			} 
    			//updateFormAction();
    			//console.log("Action: " + $(form).attr("action"))

    			function getConfirmation(){
    				if(actionStatus == actionPerform.update){
    					return confirm('Are you sure you want to update?'); 
    				} 
    				/* else if(actionStatus == actionPerform.create){
    					return confirm('Are you sure you want to create?');
    				} */
    			}
    			
    			function updateFormAction(){
    				if(actionStatus == actionPerform.update ){
	        			var queryParameter = {"id":loginId, "customerId":customerId, "method": actionStatus.name};
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
    				
    				if($('#loginSelection').val() == "ns"){
         				alert("Please select Login.");
         				return false;
         			}
    				if($('#customerSelection').val() == "ns"){
          				alert("Please select Customer.");
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
            <html:form action="manageCustomerLogin" method="post" styleId="ManageCustomerLogin">
                <table align="center" border="0" cellpadding="0" cellspacing="0" width="599">
                    <tbody><tr>
                            <td class="newhead1" width="10"></td>
                            <th class="headtext" width="283">Customer Master</th>
                            <td width="301"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
                            <td class="newhead3" width="7">&nbsp;</td>
                            <td class="newhead4" width="10"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
                        </tr>
                        <tr>
                            <td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1">
                            </td>
                            <td colspan="3" width="597">
                                <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                    <tbody>
                                        <tr>
                                            <td bgcolor="#dbeaf5">
                                                <table border="0" cellpadding="2" cellspacing="1" width="597">
                                                    <tbody>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="156">Select Login:</td>
                                                            <td valign="top">
                                                                <html:select property="loginId" styleId="loginSelection" styleClass="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </html:select>
                                                            </td>
                                                        </tr>
                                                        <tr bgcolor="#ffffff">
                                                            <td id="t_company" width="156">Select Customer:</td>
                                                            <td valign="top">
                                                                 <html:select property="customerId" styleId="customerSelection" styleClass="ctrl">
                                                                    <option value="ns">--Make a Selection--</option>
                                                                </html:select>
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

                            <div id="loginDetails"></div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
        </div>
    </body>
</html>