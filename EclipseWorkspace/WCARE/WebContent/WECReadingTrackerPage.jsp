<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/jquery-1.7.2.min.js"></script>
<script type = "text/javascript">
function validateForm(){
	console.log("In Validate Form");
	var validatorFlag = true;
	var wecSelectionValue = $('#wecSelection :selected').val();
	var customerSelectionValue = $('#customerSelection :selected').val();
	var dateSelectionValue = $('#dateSelection').val();
	//alert("Customer Selected Value : " + customerSelectionValue + " ,WEC Selected Value : " + wecSelectionValue + " ,Date Selected : " + dateSelectionValue);
	if(dateSelectionValue == ""){
		alert("Please Select a Date");
		validatorFlag = false;
	}
	return validatorFlag;
}

$(document).ready(function() {
	function defaultWECSelection(){
		var wecSelection = $('#wecSelection');
		wecSelection.empty();
		//wecSelection.append(new Option("---Select WEC---", ""));
		wecSelection.append(new Option("ALL WECs", "All"));
	}
	defaultWECSelection();
	function defaultCustomerSelection(){
		var customerSelection = $('#customerSelection');
		customerSelection.empty();
		customerSelection.append(new Option("---Select Customer---", ""));
		customerSelection.append(new Option("ALL CUSTOMERS", "All"));
	}
	//defaultCustomerSelection();
	
	$('#customerSelection').change(function() {
		// assign the value to a variable, so you can test to see if it is working
		var selectVal = $('#customerSelection :selected').val();
		var wecSelection = $('#wecSelection');
		
		if(selectVal != "" && selectVal != "All"){
			defaultWECSelection();
			var param = {ajaxType: "wecIdWecDescriptionBasedOnCustomerId",cust_id: selectVal};
			$.ajax({
			  url: '/WCARE/AjaxController.do',
			  data: param,
			  type: 'POST',
			  dataType: 'xml',
			  success: function(result){
				  var wecs = $(result).find('wec');
				  var wecSelection = $('#wecSelection');
				  $.each(wecs,function(i, wecData){
					  wecSelection.append(new Option($(wecData).find('wecName').text(), $(wecData).find('wecId').text()));
				  });
			  }
			});
		}
		else{
			defaultWECSelection();
		}
	});
	$('#wecSelection').change(function() {
		// assign the value to a variable, so you can test to see if it is working
		var wecSelection = $('#wecSelection');
		var customerSelection = $('#customerSelection');
		var wecSelectionValue = $('#wecSelection :selected').val();
		if(wecSelectionValue != ""){
			//wecDown.append(new Option("ALL WECs", "All"));
		}
	});
});
</script>
<title></title>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form name="myform" action="/WCARE/WECReadingTrackerReport.do" method="post" onsubmit="return validateForm();">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody>
<tr>
	<td class="newhead1"></td>
	<th class="headtext">WEC Reading History</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>
		<tr bgcolor="#ffffff">
			<td id="t_company" width="219">&nbsp;Select Customer:</td>
			<td valign="top">
				<select name="customerSelection" id="customerSelection" class="ctrl" style='width: 250px'>
					<option value="All">ALL CUSTOMERS</option>
					<logic:iterate name="customerIdNameBean" id="customerIdName">
						<option value='<bean:write name="customerIdName" property="customerId"/>'><bean:write name="customerIdName" property="customerName"/></option>
					</logic:iterate>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">&nbsp;Select WEC:</td>
		  <td bgcolor="#ffffff">
		    <select name="wecSelection" id="wecSelection" class="ctrl" style='width: 150px'></select>
		  </td>
	  	</tr>

	  	<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select Date:</td>
			<td class="bgcolor">
				<input type="text" name="dateSelection" id="dateSelection" size="18" class="ctrl" maxlength="10" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.forms[0].dateSelection);return false;" >
					<img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt="">
				</a>
			</td>
		</tr>	 
		  <tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			
			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
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
			<td width="1">
				<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1">
			</td>
			<td class="btn" width="100">
				<input id="adminType" type="hidden" name="Admin_Input_Type" value="WECPESAction" />		
				<input name="Submit" value="Get Report" class="btnform" type="submit"  />
			</td>
			<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		</tr>
		</tbody>
	</table>
	</td>
</tr>
</tbody></table>
</form>
</td>		
</tr>
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody>
			<tr>
				<td >
					<div id="curtailWECDisplay"></div>
				</td>
			</tr>
			</tbody>
		</table>	
	</td>
</tr>
</table>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>