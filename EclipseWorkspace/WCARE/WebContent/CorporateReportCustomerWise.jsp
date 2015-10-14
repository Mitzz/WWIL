<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.admin.util.AdminUtil" %> 
<%@ page import="java.util.*" %>
<html>
<head>
<%
if (session.getAttribute("loginID") == null)
{
	response.sendRedirect(request.getContextPath());
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/doubleList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
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
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String Customeridtxt = "";
String custid ="";

StringBuffer buffer = new StringBuffer();
if(session.getAttribute("LoginType").equals("E"))
	custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
else
{ 
    List tranList1 = new ArrayList(); 
	tranList1 = (List)session.getAttribute("custtypee");
	//System.out.println("jshdfkjshf : " + tranList1);
    for (int j=0; j <tranList1.size(); j++)
   	{
    	Vector v11 = new Vector();
	 	v11 = (Vector)tranList1.get(j);
   	  	String customerid=(String)v11.get(2);
   	 	String customername=(String)v11.get(3);
   	   	buffer.append("<OPTION VALUE='" + customerid + "'>" + customername + "</OPTION>");
    }
    custid=buffer.toString();
    //System.out.println("custius : " + custid);
}
%>
<script type="text/javascript">
function createlist()
{   
   var selected = new Array();
   var mySelect = document.getElementById("TransactionSeltxt");
   var z = 0;
   var transactions = "";
   for(var i = 0; i < mySelect.length; i++)
   {
      if(transactions.length > 0)
	  {
	      transactions = transactions + ",'" + mySelect.options[i].value + "'";
	  }
	  else
	  {
	  	  transactions = "'" + mySelect.options[i].value + "'";
	  }	  
	  z = 1;
   }
   if(transactions.length == 10)
	{
		transactions = transactions + ",'.'";
	}	
   if (z == 1)
   {
   		document.getElementById("Transactions").value = transactions;
   		
   		findWec();
   		
   		
   }
   else
   {
   		alert("No Customer selected");
	   	return false;
   }
}
function getCustomers()
{
	//createStatelist();
	//alert(document.getElementById("SiteTransactions").value);
}
function createCustomerlist()
{   var why = ""; 

   var selected = new Array();
   var mySelect = document.getElementById("WECSelList");
   var z = 0;
   var SiteTransactions = "";
   
   
   var ss1=document.forms[0].FromDatetxt;
     	var ss2=document.forms[0].ToDatetxt;
     	why += checkUsername(ss1);
     	why += checkUsername(ss2);
     	
    if (why != "") {
       alert(why);
       return false;
    }
    
    
   for(var i = 0; i < mySelect.length; i++)
   {
      if(SiteTransactions.length > 0)
	  {
	      SiteTransactions = SiteTransactions + ",'" + mySelect.options[i].value + "'";
	  }
	  else
	  {
	  	  SiteTransactions = "'" + mySelect.options[i].value + "'";
	  }	  
	  z = 1;
   }
   if(SiteTransactions.length == 10)
	{
		SiteTransactions = SiteTransactions + ",'.'";
	}	
   if (z == 1)
   {
   		document.getElementById("SiteTransactions").value = SiteTransactions;
   		
   		return true;
   		//location.href="CorporateExcel.jsp?weclist="+SiteTransactions+"&fdate="+fdate+"&tdate="+tdate;
	
   }
   else
   {
   		alert("No State selected");
	   	return false;
   }
}
function getReport()
{
	var blnSave=false;
	var blnSave1=false;
	blnsave1 = createStatelist();
	blnsave = createlist();
	if (blnsave == true || blnsave1 == true)
	{
		blnsave = false;
	    blnSave = validateForm('Fiscal Year ',document.getElementById("FiscalYeartxt").value,'M','');
	    //					   'Customer ',document.getElementById("Transactions").value,'M','');
	     if ( blnSave == true ) {	        
			return true;
	     } else {
	        return false;
	     }
	}
	else
		return false;
}


 function findWec() 
{ 

	 var req = newXMLHttpRequest();	 
	 var ApplicationId = document.getElementById("Transactions").value
    
     
	 req.onreadystatechange = getReadyStateHandler(req, showWec);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findWECByCustomerID&AppId="+ApplicationId);
}

function showWec(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sname")[0].firstChild;
	     		
	     	if (nname != null)
	     	{
	     	document.forms[0].WECList.options[I] = new Option(item.getElementsByTagName("sname")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    
   	 	    }
	     	
    	 		
    	 				
        }
}


function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkUsername (strng) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "You didn't enter a Date.\n";
 		}
 		return error;
 } 
</script>

</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/CorporateExcel.jsp" onSubmit="return  createCustomerlist()" name="CoroporateReport" id="CoroporateReport" method="post" >


<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Customize Report</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>	
		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Customer</td>
			<td width="450">
				<select size="10" name="Transactiontxt" id="Transactiontxt" class="ctrl" multiple="multiple" style="width: 500px" >
		             <%=custid %>
		        </select>
			</td>
		</tr>	
		<tr align="center" class="bgcolor">
			<td></td>
			<td>
				<input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.Transactiontxt, this.form.TransactionSeltxt)"  alt="Move selected" />&nbsp;
				<input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.Transactiontxt, this.form.TransactionSeltxt)" />&nbsp;
				<input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.TransactionSeltxt, this.form.Transactiontxt)" />		&nbsp;				
				<input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.TransactionSeltxt, this.form.Transactiontxt)" />
			</td>
		</tr>		
		<tr class="bgcolor">
			<td></td>
			<td >
		        <select size="10" name="TransactionSeltxt" id="TransactionSeltxt" class="ctrl" multiple="multiple" style="width: 500px" >
		        </select>
		    </td>
		</tr>
		<tr class="bgcolor"> 
			
			<td class="bgcolor" colspan="2" align="center">
				<input  type="button" onClick="createlist()" value="Generate WEC">
				
			</td>
		</tr>
			<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select WEC</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="WECList" id="WECList" class="ctrl" multiple="multiple" style="width: 250px" >
					           
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.WECList, this.form.WECSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="WECSelList" id="WECSelList" class="ctrl" multiple="multiple" style="width: 250px" >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.WECList, this.form.WECSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.WECSelList, this.form.WECList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.WECSelList, this.form.WECList)" />
					    <td>
					</tr>
				</table>
			</td>
		</tr>
			<tr class="bgcolor">
														<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
														<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
														<a href="javascript:void(0)" id="dc"
			  													 onClick="if(self.gfPop)gfPop.fPopCalendar(document.CoroporateReport.FromDatetxt);return false;"><img
																	class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
														border="0" alt=""></a></td>
										</tr>
										<tr class="bgcolor">
												<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
												<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
		   										 <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.CoroporateReport.ToDatetxt);return false;">
	        									  <img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></td>
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
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="hidden" name="Transactions" id="Transactions" value="" />				
			<input type="hidden" name="typetxt" id="typetxt" value="0" />					
			<input type="hidden" name="SiteTransactions" id="SiteTransactions" value="" />	
			<input name="Submitcmd" type="Submit" class="btnform" value="Submit" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Resetcmd" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>	
</td>		
</tr>
</table>
</div>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>