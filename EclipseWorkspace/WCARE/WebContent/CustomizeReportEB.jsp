<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/doubleList.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateFormReports.js"></script>
<%
		if (session.getAttribute("loginID") == null) {
		response.sendRedirect(request.getContextPath());
	}
%>
<%

	String stid = "";
	String Customeridtxt = "";
	String sitename = "";
	String siteid = "";
	
%>
<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
	//String stdmsg = AdminUtil.fillMaster("TBL_STANDARD_MESSAGE", rn);
	String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",
			Customeridtxt);
	String statename = AdminUtil.fillMaster("TBL_STATE_MASTER", stid);
	if (!stid.equals("")) {
		sitename = AdminUtil.fillWhereMaster("TBL_SITE_MASTER", siteid,
		stid);
	}
%>
<script type="text/javascript">


function findSite() 
{ 
	 
	 var req = newXMLHttpRequest();	 
	 var ApplicationId = document.getElementById("Transactions").value
    
     
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findSiteByStateID&AppId="+ApplicationId);
}

function showSite(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");
	
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sname")[0].firstChild;
	     		
	     	if (nname != null)
	     	{
	     	 document.forms[0].SiteList.options[I] = new Option(item.getElementsByTagName("sname")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    
   	 	    }   	 	    
    	 				
        }
        
}

function createlistSite()
{   
   var selected = new Array();
   var mySelect = document.getElementById("StateSelList");
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
   		
   		findSite();
   }
   else
   {
   		alert("No State selected");
	   	return false;
   }
}


function createlist()
{   
   var selected = new Array();
   var mySelect = document.getElementById("SiteSelList");
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
   		alert("No Site selected");
	   	return false;
   }
}

function checkWholeForm()
{   var why = ""; 
   var selected = new Array();
   var mySelect = document.getElementById("SiteSelList");
   var mySelect1 = document.getElementById("Customeridtxt");
   var z = 0;
   var y = 0;
   var SiteTransactions = "";
   var custTransactions = "";
   
   var ss1=document.forms[0].FromDatetxt;
     	var ss2=document.forms[0].ToDatetxt;
     	why += checkUsername(ss1);
     	why += checkUsername(ss2);
     	
    if (why != "") {
       alert(why);
       return false;
    }
   
   for(var i = 0; i < mySelect1.length; i++)
   {
      if(custTransactions.length > 0)
	  {
	      custTransactions = custTransactions + ",'" + mySelect1.options[i].value + "'";
	  }
	  else
	  {
	  	  custTransactions = "'" + mySelect1.options[i].value + "'";
	  }	  
	  z = 1;
   }
   if(custTransactions.length == 10)
	{
		custTransactions = custTransactions + ",'.'";
	}	
   if (z == 1)
   {
   		document.getElementById("custTransactions").value = custTransactions;
   		return true;
   		//location.href="ExportCustomizeEB.jsp?state="NA"&cust="+custTransactions+"&site="NA"&fd="+ss1+"&td="+ss2;
   		//location.href="CorporateExcel.jsp?weclist="+SiteTransactions+"&fdate="+fdate+"&tdate="+tdate;
	
   }
   else
   {
   		     for(var j = 0; j < mySelect.length; j++)
   				{
     				if(SiteTransactions.length > 0)
	 				 {
	    				  SiteTransactions = SiteTransactions + ",'" + mySelect.options[j].value + "'";
	  				}
	  				else
	  				{
	  	 				 SiteTransactions = "'" + mySelect.options[j].value + "'";
	  				}	  
	 				 y = 1;
  				 }
   				if(SiteTransactions.length == 10)
					{
						SiteTransactions = SiteTransactions + ",'.'";
					}	
  			 if (y == 1)
  				 {
   					document.getElementById("SiteTransactions").value = SiteTransactions;
   		
   					return true;
   		//location.href="CorporateExcel.jsp?weclist="+SiteTransactions+"&fdate="+fdate+"&tdate="+tdate;
	
  				 }
   				else
   				{
   					alert("No Customer,Site selected");
	   				return false;
   				}
   	}
   
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

		<form name="CustomizeReportEB" id="CustomizeReportEB" action="<%=request.getContextPath()%>/ExportCustomizeEB.jsp"  method="post" onSubmit="return checkWholeForm()" >
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
			<tbody>
				<tr>
					<td class="newhead1"></td>
					<th class="headtext">Customer EB Data</th>
					<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
					<td class="newhead3">&nbsp;</td>
					<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1"
						width="10"></td>
				</tr>
				<tr>
					<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
					<td colspan="3"><img
						src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"
						height="10" width="1"><br>
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						<tbody>
							<tr>
								<td bgcolor="#dbeaf5">
								<table border="0" cellpadding="2" cellspacing="1" width="100%">
									<tbody>





										<tr class="bgcolor">
											<td id="t_street_address" colspan=2 align="center"><b>Selection Criteria</b></td>

										</tr>										
										
										
										
										<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Customer</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="customerList" id="customerList" class="ctrl" multiple="multiple" style="width: 250px" >
					            <%=custid%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.customerList, this.form.Customeridtxt)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="Customeridtxt" id="Customeridtxt" class="ctrl" multiple="multiple" style="width: 250px"  >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.customerList, this.form.Customeridtxt)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.Customeridtxt, this.form.customerList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.Customeridtxt, this.form.customerList)" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>				
						
										
										
										<tr class="bgcolor">
											<td id="t_street_address" colspan=2 align="center"><B>OR</B></td>

										</tr>
										
										
										
										<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select State</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="StateList" id="StateList" class="ctrl" multiple="multiple" style="width: 250px" >
					            <%=statename%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.StateList, this.form.StateSelList),getCustomers()"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="StateSelList" id="StateSelList" class="ctrl" multiple="multiple" style="width: 250px" >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.StateList, this.form.StateSelList),getCustomers()" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.StateSelList, this.form.StateList),getCustomers()" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.StateSelList, this.form.StateList),getCustomers()" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>
			
			<tr class="bgcolor"> 
			
			<td class="bgcolor" colspan="2" align="center">
				<input  type="button" onClick="createlistSite()" value="Generate Site">
				
			</td>
		</tr>
		
			<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Site</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="10" name="SiteList" id="SiteList" class="ctrl" multiple="multiple" style="width: 250px" >
					          
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.SiteList, this.form.SiteSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="10" name="SiteSelList" id="SiteSelList" class="ctrl" multiple="multiple" style="width: 250px" >
					        </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.SiteList, this.form.SiteSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.SiteSelList, this.form.SiteList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.SiteSelList, this.form.SiteList)" />
					    <td>
					</tr>
				</table>
			</td>
			</tr>
			
			
										<tr class="bgcolor">
														<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
														<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
														<a href="javascript:void(0)" id="dc"
			  													 onClick="if(self.gfPop)gfPop.fPopCalendar(document.CustomizeReportEB.FromDatetxt);return false;"><img
																	class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
														border="0" alt=""></a></td>
										</tr>
										<tr class="bgcolor">
												<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
												<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
		   										 <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.CustomizeReportEB.ToDatetxt);return false;">
	        									  <img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></td>
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
					<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20"
						width="10"></td>
					<td colspan="4" align="right" bgcolor="#006633">
					<table border="0" cellpadding="0" cellspacing="0">
						<tbody>
							<tr>
							
							<td>
									<input type="hidden" name="Transactions" id="Transactions" value="" />				
					
									<input type="hidden" name="SiteTransactions" id="SiteTransactions" value="" />	
									<input type="hidden" name="custTransactions" id="custTransactions" value="" />	
								</td>	
								<td class="btn" width="100"><input name="button"" type="submit" class="btnform" value="Show"
									/></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18"
									width="1"></td>
								<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
								<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18"
									width="1"></td>
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
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>
