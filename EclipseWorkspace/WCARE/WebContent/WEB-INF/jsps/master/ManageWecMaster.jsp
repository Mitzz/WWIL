<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<c:set var="srNo" scope="page" value="0" />
<c:set var="class" scope="page" value="" />

<%@ page isELIgnored="false"%>

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
	<%
		String Datetxt="";
		String StartDatetxt="";
		String EndDatetxt="";
		
		long MILLIS_IN_MONTH = 2628000000L;
		long MILLIS_IN_YEAR = 34251600000L;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		
		String commisionedDate = dateFormat.format(date.getTime());
		String contractStDate = dateFormat.format(date.getTime()+ MILLIS_IN_MONTH);
		String contractEnDate = dateFormat.format(date.getTime()+ MILLIS_IN_YEAR);
	%>
	
<script type="text/javascript">

		var actionPerform = new enums.Enum("create", "update", "delete");
		var actionStatus = actionPerform.create;
	
		function changeValueOfSubmit(value){
			var submit = document.getElementById("submit");
			submit.setAttribute("value", value);
		}   
            
		function ViewFormulaDesc()
		{ 	var str="<table width=710 border=1>"
						str+="<tr bgcolor=#ffffff><td colspan='2' width=600 align=center class='detailsheading'>Formula For</td><td width=100 class='detailsheading'>Applicable State</td></tr>"
						str+="<tr><td colspan='3' width=600 align=center class='detailsheading'>Machine Availability</td></tr>"
						str+="<tr><td>0</td><td>((Total hours - (machine fault+machine shut down+grid internal fault+grid internal shut down))/Total hours) *100</td><td>AP,GJ,TN,RJ,MH</td></tr><tr><td>1</td><td>(Total hours - (machine fault+machine shut down))/Total hours *100</td><td>KA,MP</td></tr>"
						str+="<tr ><td class='detailsheading' colspan='3'  width=600 align=center>Grid Availability</td></tr>"
						str+="<tr><td>0</td><td>(Total hours - (grid internal fault+grid internal shut down))/Total hours *100</td><td>AP,GJ,TN,RJ,MH</td></tr><tr><td>1</td><td>((Total hours - (grid external fault+grid external shut down+grid internal fault+grid internal shut down)/Total hours)*100</td><td>KA,MP</td></tr>";
				document.getElementById("formuladesc").innerHTML = str;
		}
		
		var form = null;
		var states = ${states}; //List of Javascript Object
		var wecDetails = null; // table
		
		var stateSelectionField = null;
		var areaSelectionField = null;
		var feederSelectionField = null;
		var siteSelectionField = null;
		var customerSelectionField = null;
		var ebSelectionField = null;
		
		var stateId = null;
		var stateVo = null;
		var areas = null;
		
		var areaId = null;
		var areaVo = null;
		var sites = null;
		
		var siteId = null;
		var siteVo = null;
		var customers = null;
		
		var customerId = null;
		var customerVo = null;
		var ebs = null;
		
		var ebId = null;
		var ebVo = null;
		var wecs = null;
		
		var wecId = null;		
		
		function editWec(thisWec){	
			
			actionStatus = actionPerform.update;
			changeValueOfSubmit("Update");
			
		    stateSelectionField.prop("disabled", true);
			areaSelectionField.prop("disabled", true);
			feederSelectionField.prop("disabled", true);
			siteSelectionField.prop("disabled", true);
			customerSelectionField.prop("disabled", true);
			ebSelectionField.prop("disabled", true);
			
			var row = $(thisWec).parent().parent();
			wecId = row.attr('data-id');
			console.log(wecId)
			
			setFormValues(row);
			
		
			function setFormValues(row){
					
					$("#wecName").val(row.find(".wecName").text());
					$("#capacity").val(row.find(".capacity").text());
					$("#genComm").val(row.find(".genComm").text());
					$("#multiFactor").val(row.find(".factor").text());
					$("#wecTypeSelection").find("option:contains('"+row.find(".wecType").text()+"')").each(function(){
						 if( $(this).text() == row.find(".wecType").text()){  
							    $(this).attr("selected","selected"); 
						 }
					});
					$("#technicalNo").val(row.find(".technicalNo").text());
					$("#foundationNo").val(row.find(".foundationNo").text()); 
					$("#costPerUnit").val(row.find(".costPerUnit").text()); 
					$("#machineAvailability").val(row.find(".machineAvailability").text()); 
					$("#extGridAvailability").val(row.find(".extGridAvailability").text()); 
					$("#intGridAvailability").val(row.find(".intGridAvailability").text()); 
					
					if(row.find(".status").text() == "Active"){	    				
	    				$("#status").prop('checked', false); 			
	    			}
	    			if(row.find(".status").text() == "De-active"){
	    				$("#status").prop('checked', true);	    					
	    			}
    		}
		}
		
		function copyWec(thisWec){	
			
			wecId = null;
    		actionStatus = actionPerform.create;
    		changeValueOfSubmit("Submit");
			
    		$.fn.enableSelection(["#stateSelection","#areaSelection","#feederSelection","#siteSelection","#customerSelection","#ebSelection"]);   				   
			
			var row = $(thisWec).parent().parent();	
			setFormValues(row);
		
			function setFormValues(row){
					
					$("#wecName").val(row.find(".wecName").text());
					$("#capacity").val(row.find(".capacity").text());
					$("#genComm").val(row.find(".genComm").text());
					$("#multiFactor").val(row.find(".factor").text());
					$("#wecTypeSelection").find("option:contains('"+row.find(".wecType").text()+"')").each(function(){
						 if( $(this).text() == row.find(".wecType").text()){  
							    $(this).attr("selected","selected"); 
						 }
					});
					$("#technicalNo").val(row.find(".technicalNo").text());
					$("#foundationNo").val(row.find(".foundationNo").text()); 
					$("#costPerUnit").val(row.find(".costPerUnit").text()); 
					$("#machineAvailability").val(row.find(".machineAvailability").text()); 
					$("#extGridAvailability").val(row.find(".extGridAvailability").text()); 
					$("#intGridAvailability").val(row.find(".intGridAvailability").text()); 
					
					if(row.find(".status").text() == "Active"){	    				
	    				$("#status").prop('checked', true); 			
	    			}
	    			if(row.find(".status").text() == "De-active"){
	    				$("#status").prop('checked', false);	    					
	    			}
    		}
		}
		
		function initialize()
		{
			stateSelectionField.prop("disabled", false);
			areaSelectionField.prop("disabled", false);
			feederSelectionField.prop("disabled", false);
			siteSelectionField.prop("disabled", false);
			customerSelectionField.prop("disabled", false);
			ebSelectionField.prop("disabled", false);
				
			actionStatus = actionPerform.create;
			wecId = null;
			wecDetails.innerHTML = "";
			changeValueOfSubmit("Submit");
			
			document.getElementById("wecTypeSelection").selectedIndex=0;
			$("#wecTypeSelection").val("");
			/* $("#name").val(" ");
			$("#name").val(" ");
			$("#name").val(" ");
			$("#name").val(" "); */

		}
		
		
		
		$(document).ready(function(){
			 
			form = $("form[name='WecMasterForm']");
			wecDetails = document.getElementById("wecDetails");
        	stateSelectionField = $('#stateSelection');
        	areaSelectionField = $('#areaSelection');
        	feederSelectionField = $('#feederSelection');
        	siteSelectionField = $('#siteSelection');
        	customerSelectionField = $('#customerSelection');
        	ebSelectionField = $('#ebSelection');
        	
        	$.fn.initializeSelectionField(stateSelectionField, states, "id", "name");
        	
        	$('#stateSelection').change(function(){      			    			
        		$.fn.emptySelection(["#areaSelection", "#feederSelection", "#siteSelection"]);
    			
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
    			
				$.fn.emptySelection(["#feederSelection", "#siteSelection"]);
				
    			areaId = $(this).find(":selected").val();
    			areaVo = getAreaVo(areaId, areas);
    			sites = areaVo["sites"];
    			$.fn.initializeSelectionField(siteSelectionField, sites,"id", "name");
    			var feeders = [];
    			var substations = areaVo["substations"];
    			$.each(substations, function(index, substation){
					feeders = feeders.concat(substation["feeders"]);
				});
    			$.fn.initializeSelectionField($("#feederSelection"), feeders,"id", "name");
    			    			
    			function getAreaVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }   			
    		});
			
			$('#siteSelection').change(function(){      			    			
    			
    			$('#customerSelection').empty().append(new Option("--Make a Selection--", "ns"));
    			siteId = $(this).find(":selected").val();
    			siteVo = getSiteVo(siteId, sites);
    			customers = siteVo["customers"];    			
    			$.fn.initializeSelectionField(customerSelectionField, customers,"id", "name");    			
    			function getSiteVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }   			
    		});
			
			$('#customerSelection').change(function(){      			    			
    			
    			$('#ebSelection').empty().append(new Option("--Make a Selection--", "ns"));
    			customerId = $(this).find(":selected").val();
    			customerVo = getCustomerVo(customerId, customers);
    			ebs = customerVo["ebs"];      			
    			$.fn.initializeSelectionField(ebSelectionField, ebs,"id", "name");    			
    			function getCustomerVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }   			
    		});
			
			$('#ebSelection').change(function(){      			    			
    			
				ebId = $(this).find(":selected").val();	   			
	   			ebVo = getEbVo(ebId, ebs);
	   			function getEbVo(id, vos){
			    	for(var vo of vos){
            			if(vo.id == id)
            				return vo;
            		}
			    }
	   			wecs = ebVo["wecs"]
	   			displayWecs(wecs); 
               
			    function displayWecs(wecs){
			    			    	
			    	wecDetails.innerHTML = "";
		    		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
						str +="<tr align='center' height='20'>"
						str +="<th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='90'>NAME</th>";
						str +="<th class='detailsheading' width='180'>Customer</th><th class='detailsheading' width='120'>EB</th>";
						str +="<th class='detailsheading' width='70'>WECTYPE</th><th class='detailsheading' width='70'>CAPACITY</th>";						
						str +="<th class='detailsheading' width='70'>FACTOR</th><th class='detailsheading' width='70'>GEN.COM.</th>"												
						str +="<th class='detailsheading' width='40'>STATUS</th><th class='detailsheading' width='40'>EDIT</th>";
						str +="<th class='detailsheading' width='40'>Copy</th></tr>";		    		
	
		    		var classPicker = null;

			    	$.each(wecs, function (i, vo) {
	           			
	           			if (i % 2 == 0) classPicker = "detailsbody";
	           			else classPicker = "detailsbody1";			    			
				      	  	str+="<tr align='center' height='20' class='" + classPicker + "' data-id='" + vo.id + "'><td ALIGN='center'>"+(i+1)+"</td>";	     				
		     				str+="<td align='left' class='wecName'>" + vo.name + "</td>";
		     				str+="<td align='left' class='customerName'>" + customerVo["name"] + "</td>";
		     				str+="<td align='left' class='ebName'>" + ebVo["name"] + "</td>";
		     				str+="<td align='left' class='wecType'>" + vo.type + "</td>";
		     				str+="<td align='left' class='capacity'>" + vo.capacity + "</td>";
		     				str+="<td align='left' class='factor'>" + vo.multiFactor + "</td>";
		     				str+="<td align='left' class='genComm'>" + vo.genComm + "</td>";
		     				/* FOR EDIT HIDDEN */
		     				str+="<td align='left' class='technicalNo' style='display:none'>" + vo.technicalNo + "</td>";
		     				str+="<td align='left' class='foundationNo' style='display:none'>" + vo.foundationNo + "</td>";
		     				str+="<td align='left' class='costPerUnit' style='display:none'>" + vo.costPerUnit + "</td>";
		     				str+="<td align='left' class='machineAvailability' style='display:none'>" + vo.machineAvailability + "</td>";
		     				str+="<td align='left' class='extGridAvailability' style='display:none'>" + vo.extGridAvailability + "</td>";
		     				str+="<td align='left' class='intGridAvailability' style='display:none'>" + vo.intGridAvailability + "</td>";
		     				
		     				if (vo.status == 1) {
				     			str+="<td class='status' align='left'>Active</td>";
				     		} else {
				     			str+="<td class='status' align='left'>De-active</td>";
				     		}
		     				str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' ";						     		
				     		str+=" onClick=editWec(this)></td>";
				     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' ";						     		
				     		str+=" onClick=copyWec(this)></td></tr>";
				     		
				     		//console.log("techNO : "+vo.technicalNo+" : founDationNO :: "+vo.foundationNo+" : CostPerUnit :: "+vo.costPerUnit);
				     		     
				     	
	           		}); 
			    	wecDetails.innerHTML = str;
			     }			 		
    		});
			
			$("#reset").click(function(){
				initialize(); 
			});
			
			
			$(form).submit(function(){
    			    			
    			/* if(!validateForm()){
    				return false;
    			} */
    			if(!getConfirmation()){
    				return false;
    			}
    			if(!valDate()){
    				return false;
    			}
    			
    			$.fn.enableSelection(["#customerSelection","#ebSelection","#feederSelection"]);
    			console.log(actionStatus +" :: "+actionStatus.name)
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
    	    			var queryParameter = {"id":wecId};
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
    			
    			function valDate(){
    				
    				var todate=new Date(); // yyyy-mm-dd
    				var fromdate = new Date(document.getElementById("commissionDate").value.substr(6,4),document.getElementById("commissionDate").value.substr(3,2) -1,document.getElementById("commissionDate").value.substr(0,2));
    				var one_day=1000*60*60*24;	
    			    var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day));
    			    if(days<=0)
    			  	{
    				  	 alert("Future commision date not allowed!");
    				  	 return false;
    			  	}
    			  	return true;
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
    				if($('#feederSelection').val() == "ns"){
         				alert("Please select Feeder.");
         				return false;
         			}
    				if($('#siteSelection').val() == "ns"){
         				alert("Please select Site.");
         				return false;
         			}
    				if($('#customerSelection').val() == "ns"){
         				alert("Please select Customer.");
         				return false;
         			}
    				if($('#ebSelection').val() == "ns"){
         				alert("Please select EB.");
         				return false;
         			}
    				if($('#wecTypeSelection').val() == "ns"){
         				alert("Please select Wec Type.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#costPerUnit", "Please enter Cost Per Unit ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Please enter Name ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#foundationNo", "Please enter FoundationNo ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#capacity", "Please enter Capacity ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#multiFactor", "Please enter Multifactor ")){
        				return false;
        			}
    				if(isNaN($("#multiFactor").val())){
						alert("Only numbers are allowed in Multifactor");
						return false;
					}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#technicalNo", "Please enter TechnicalNo ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Please enter Name ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#name", "Please enter Name ")){
        				return false;
        			}
    				if($('#guaranteeType').val() == "0"){
         				alert("Please select Guarantee Type.");
         				return false;
         			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#genComm", "Please enter Generation Committed ")){
        				return false;
        			}
    				if(isNaN($("#genComm").val())){
						alert("Only numbers are allowed in Generation Committed");
						return false;
					}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#machineAvailability", "Please enter Machine Availability ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#extGridAvailability", "Please enter External Grid Availability ")){
        				return false;
        			}
    				if($.fn.isInputTextFieldEmptyWithErrorMessage("#intGridAvailability", "Please enter Internal Grid Availability ")){
        				return false;
        			}
    				if($('#customerType').val() == "0"){
         				alert("Please select Customer Type.");
         				return false;
         			}
    				
 
    				return true;
    			}
    		});

		});// document ready
		
		
		
</script>

</head>
<body>
	<div align="center">
		<%-- <html:form action="manageWecMaster" method="post"styleId="WecMasterForm"> --%>
		 <sf:form method="post" action="${pageContext.request.contextPath}/spring/wecMaster" modelAttribute="wec" id="WecMasterForm" name="WecMasterForm">
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
			<tbody>
			   <tr>
					<td class="newhead1"></td>
					<th class="headtext">WEC Master</th>
					<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
					<td class="newhead3">&nbsp;</td>
					<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
				</tr>
				<tr>
						<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
						<td colspan="3">
						<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
						<table border="0" cellpadding="0" cellspacing="0" width="100%">
							<tbody>
								<tr>
									<td bgcolor="#dbeaf5">
									<table border="0" cellpadding="2" cellspacing="1" width="100%">
										<tbody>
											<tr bgcolor="#ffffff">
												<td colspan="4" id="t_title">&nbsp;</td>
											</tr>
						
											<tr bgcolor="#ffffff">
													<td id="t_company" >Select State:</td>
													<td valign="top">
														 <select name="stateId" id="stateSelection" class="ctrl">
															<option value="ns">--Make a Selection--</option>
														</select>
													</td>	            
												    <td id="t_company">Select Area:</td>
													<td valign="top" >
														 <select name="areaId" id="areaSelection" class="ctrl">
												              <option value="ns">--Make a Selection--</option>
												         </select>
													</td>
											</tr>
											
											<tr bgcolor="#ffffff">
													<td id="t_company" >Select Feeder:</td>
													<td valign="top">
														<select name="feederId" id="feederSelection" class="ctrl">
															<option value="ns">--Make a Selection--</option>
														</select>
													</td>	            
												    <td id="t_company">Select Site:</td>
													<td valign="top">
													     <select name="siteId" id="siteSelection" class="ctrl">
											              <option value="ns">--Make a Selection--</option>											             
											             </select>
													</td>
											</tr>
											
										  	<tr bgcolor="#ffffff"> 									            
										            <td id="t_company">Customer:</td>
													<td valign="top">
														<select name="customerId" id="customerSelection" class="ctrl">
											              	<option value="ns" selected="selected">-- select --</option>											             	
											            </select>
											        </td>									            
										            <td id="t_company">EB:</td>
													<td bgcolor="#ffffff">
														<select name="ebId" id="ebSelection" class="ctrl">
										              		<option value="ns" selected="selected">-- select --</option>  								               		
										            	</select>
										            </td>	           
											</tr>
											
											<tr bgcolor="#ffffff"> 
													<td id="t_company">WEC Type:</td>
													<td valign="top">
													    <select name="type" id="wecTypeSelection" class="ctrl" >              
										                	<option value="ns">-- select --</option>	
										                	<jsp:include
																	page="/jsppages/utility/SelectVoTemplateV2.jsp">
																	<jsp:param name="beanIdentifier" value="wecType" />
																	<jsp:param name="option" value="id" />
																	<jsp:param name="text" value="description" />
															</jsp:include>											            
													    </select>
													</td>											
													<td id="t_company">Cost Per Unit:</td>
													<td valign="top">
														 <input name="costPerUnit" id="costPerUnit" size="20" class="ctrl"  type="text" />
												  	</td>											
											</tr>
											
											<tr bgcolor="#ffffff">
													<td id="t_company">Name:</td>
													<td bgcolor="#ffffff"><input name="name" size="35" id="wecName" class="ctrl"  type="text" /></td>													 
											        <td id="t_company">Foundation Location :</td>
													<td bgcolor="#ffffff"><input name="foundationNo" id="foundationNo" size="20" class="ctrl"  type="text" /></td>
										  	</tr>
										  	
											<tr bgcolor="#ffffff"> 
													<td id="t_company">WEC Capacity:</td>
													<td><input name="capacity" id="capacity" size="10" class="ctrl"  type="text" /></td>											
													<td id="t_company">Multifactor:</td>
													<td bgcolor="#ffffff"><input name="multiFactor" id="multiFactor" size="10" class="ctrl"  value="1" type="text"  /></td>
											</tr>	
											
										    <tr bgcolor="#ffffff"> 
													<td id="t_city">WEC Techincal No:</td>
													<td><input name="technicalNo" id="technicalNo" size="10" class="ctrl"  type="text" /></td>											
													<td id="t_company" nowrap="nowrap">Guarantee Type:</td>
													<td bgcolor="#ffffff">
													    <select name="guaranteeType" id="guaranteeType" class="ctrl"> 
															  <option value="0">--Make a Selection--</option>             
												              <option value="1">Machine Guarantee</option>
												              <option value="2" selected>Power Curve Guarantee</option>
												              <option value="3">Absolute Guarantee</option>	
												              <option value="4">Generation Guarantee at EB</option>
												              <option value="5">Others</option>   
													    </select>
													</td>
											</tr>	
											
											<tr class="bgcolor"> 
													<td id="t_company">Commision Date:</td>
													<td class="bgcolor">
															<input type="text" name="commissionDate" id="commissionDate" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()" value="<%=commisionedDate%>" />
															<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WecMasterForm.commissionDate);return false;" >
																<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
															</a>
													</td>											
													<td id="t_company">Cont. Start Date:</td>
													<td class="bgcolor">
															<input type="text" name="startDate" id="startDate" size="15" class="ctrl" maxlength="10" onfocus="dc1.focus()" value="<%=contractStDate%>" />
															<a href="javascript:void(0)" id="dc1" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WecMasterForm.startDate);return false;">
																<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
															</a>
													</td>
											</tr>
											
											<tr class="bgcolor"> 
													<td id="t_company">Cont. End Date:</td>
													<td class="bgcolor">
															<input type="text" name="endDate" id="endDate" size="15" class="ctrl" maxlength="10" onfocus="dc2.focus()" value="<%=contractEnDate%>" />
															<a href="javascript:void(0)" id="dc2" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WecMasterForm.endDate);return false;" >
																<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
															</a>
													</td>											
													<td id="t_company">Generation Committed:</td>
													<td bgcolor="#ffffff"><input name="genComm" id="genComm" size="10" class="ctrl"  type="text" /></td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
													<td id="t_company">Machine Availability:</td>
													<td bgcolor="#ffffff"><input name="machineAvailability" id="machineAvailability" size="10" class="ctrl"  type="text"/></td>											 
													<td id="t_company">External Grid Availability:</td>
													<td bgcolor="#ffffff"><input name="extGridAvailability" id="extGridAvailability" size="10" class="ctrl"  type="text" /></td>
											</tr>
											
											<tr bgcolor="#ffffff"> 
													<td id="t_company">Internal Grid Availability:</td>
													<td bgcolor="#ffffff"><input name="intGridAvailability" id="intGridAvailability" size="10" class="ctrl"  type="text" /></td>											
												    <td id="t_company">Deactivate</td>
												    <td bgcolor="#ffffff"><input type="checkbox" name="status" id="status" value="1" /></td>
										  	</tr>
										  						  	
										  	<tr bgcolor="#ffffff">
												 	<td id="t_company">Display Active</td>
												    <td bgcolor="#ffffff"><input type="checkbox" name="show" id="show" value="0" checked="checked"/></td>
												    <td id="t_company" nowrap="nowrap">Customer Type:</td>
												    <td bgcolor="#ffffff">
													      <select name="customerType" id="customerType" class="ctrl"> 
															  <option value="0">--Make a Selection--</option>             
												              <option value="1" selected>Premier Customer</option>
												              <option value="2">Classic Customer</option>
												              <option value="3">General Customer</option>	
												              <option value="4">Others</option>   
														  </select>
												    </td>
										  	</tr>
					  	
										 	<tr bgcolor="#ffffff">		 	
													<td id="t_street_address_ln2" nowrap="nowrap">WEC Formula:</td>
													<td>
														<select name="formula" class="ctrl" >    												       
										             		 <option value="0" >0</option>	
										             		 <option value="1">1</option>										            									             
													  	</select>
													    &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="Show Description" onClick="ViewFormulaDesc()">
													</td>
													<td id="t_street_address_ln2" nowrap="nowrap">Data From Scada:</td>
													<td>
													 	<select name="scadaStatus" class="ctrl" >    												        
										             		 <option value="0" >0</option>	
										             		 <option value="1">1</option>										          									               
													    </select>												  
													</td>								
											</tr>
										  		
										  	<tr bgcolor="#ffffff"> 
													<td id="t_general_information" colspan="4"><span id="formuladesc" id="formuladesc"></td>				
										  	</tr>	
										  		
										  	<c:if test="${not empty success }">
											<tr class="bgcolor"> 
												<td colspan="4" class = "sucessmsgtext">
												${ success }
												</td>
											</tr>
											</c:if>
											
											<c:if test="${not empty failure }">
											<tr class="bgcolor"> 
												<td colspan="4" class = "errormsgtext">
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
						<td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
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
										<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="WecMaster" />				
											<input type="reset" value="Cancel" id="reset" class="btnform" />	   
											<input type="hidden" name="edittytxt" id="edittytxt" />	
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
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="700">
                <tbody>
                	<tr>		
                        <td align="center">

                            <div id="wecDetails"></div>	
                        </td>
                    </tr>
                </tbody>
            </table>	
	</div>

</body>

<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="${ contextPath }/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
	</iframe>

</html>