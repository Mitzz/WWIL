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
<%@page import="com.enercon.global.utils.DynaBean"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/doubleList.js"></script>

<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String rn = "";
String mid = "";
String de = "";
String ri = "";
String stid = "";
String Customeridtxt="";
String sitename="";
String siteid="";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("msgheadtxt").toString();
	mid = dynabean.getProperty("MsgIdtxt").toString();
	de = dynabean.getProperty("MsgDescriptiontxt").toString();
	Customeridtxt=dynabean.getProperty("Customeridtxt").toString();
	stid = dynabean.getProperty("Statetxt").toString();
	siteid = dynabean.getProperty("Sitetxt").toString();
	ri = dynabean.getProperty("MsgIdtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
String stdmsg = AdminUtil.fillMaster("TBL_STANDARD_MESSAGE",rn);
String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
String statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
if(!stid.equals(""))
{ sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER",siteid,stid);
}
%>
<script type="text/javascript">

// sent message to customer
function SentMsg()
{   
	 var req = newXMLHttpRequest();
    
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, SentMsgDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_TEMP_TO_POST&AppId="+ApplicationId);
}
function SentMsgDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
     	if (nname != null)
     	{
   	 		alert("Message Sent Sucessfully");
   	 		
   	 	} 			
 	}
 findTempList();
}

// create a list of customer based on either Customer OR state&site
function AddTempMsg()
{  
    var msgid = document.forms[0].MsgIdtxt.value;
   
    
    var z=0;
    var selected = new Array();
    var custTransactions = "";
    var stateTransactions = "";
    var siteTransactions = "";
    var custlist = document.getElementById("Customeridtxt");
    var mySelect = document.getElementById("StateSelList");
    var sitelist = document.getElementById("SiteSelList");
    
    //create list of customer
    for(var i = 0; i < custlist.length; i++)
    {
       if(custTransactions.length > 0)
 	   {
 	      custTransactions = custTransactions +"," + custlist.options[i].value;
 	   }  else
 	   {
 	  	  custTransactions =  custlist.options[i].value ;
 	   }	   
    }
 
    //create list of state  
	for(var i = 0; i < mySelect.length; i++)
	{
	   if(stateTransactions.length > 0)
	   {
	      stateTransactions = stateTransactions +"," + mySelect.options[i].value ;
	   }  else
	   {
	 	  stateTransactions =  mySelect.options[i].value ;
       }	  
	 	  
     }
	   	   
     //create list of sites
	 for(var i = 0; i < sitelist.length; i++)
     {
	    if(siteTransactions.length > 0)
	    {
		   siteTransactions = siteTransactions +","+ sitelist.options[i].value ;
		}  else
	    {
	       siteTransactions =  sitelist.options[i].value ;
		}	   	  
	 }
		  
    //checking the criteria	   
    var cntttl=(custlist.length)+(mySelect.length);
   
    if(cntttl==0)
    {
    
    alert( "Select One Of The Selection Criteria " );
    }
    else
    {
     	if(custTransactions == 0)
     	 {	
    		  custTransactions = "NA";
    		  var req = newXMLHttpRequest();    
    	      var MsgId = msgid+":"+custTransactions+":"+stateTransactions+":"+siteTransactions; 
    	     
    		  req.onreadystatechange = getReadyStateHandler(req, InsertTempDetails);	  
    		  req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
    		  req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    		  req.send("Admin_Input_Type=CUST_ADD_TEMP_MESSAGE&AppId="+MsgId);
   		}
     	else
     	{  
     	      stateTransactions = "NA";
     	      siteTransactions = "NA";    
     	      var req = newXMLHttpRequest();    
              var MsgId = msgid+":"+custTransactions+":"+stateTransactions+":"+siteTransactions;    
            
     	      req.onreadystatechange = getReadyStateHandler(req, InsertTempDetails);	  
     	      req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
     	      req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
     	      req.send("Admin_Input_Type=CUST_ADD_TEMP_MESSAGE&AppId="+MsgId);  
     	}
   
    }// End of Criteria
     
 }// End Of AddTempMsg()
 
function InsertTempDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
     	if (nname != null)
     	{
   	 		alert("Customer Add Sucessfully"); 		
     	}	 			
 	}
 findTempList();
}
 
//displaying customer list to sent message
function findTempList() 
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = document.forms[0].MsgIdtxt.value;
	 req.onreadystatechange = getReadyStateHandler(req, findTempListDetail);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_SENTTEMPMESSAGE_DETAIL&AppId="+ApplicationId);
}
function findTempListDetail(dataXml)
{  
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");	
	var divdetails = document.getElementById("tempmsgdetails");
	divdetails.innerHTML = "";
	var cust = "";
	var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'><tr align='center' height='20'><th colspan=3 class='detailsheading' width='300'>Message Displayed To Customer</th></tr>"
		str +="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='230'>Customer</th>"
		str +="<th class='detailsheading' width='40'>D</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	   	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("cname")[0].firstChild.nodeValue + "</td>"	  
	     		//str+="<td align='left'>" + item.getElementsByTagName("cemail")[0].firstChild.nodeValue + "</td>"	
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/delet.gif' alt='Click to edit the record' "
	     		str+="onClick=DelMsgTemp('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "','" + item.getElementsByTagName("cid")[0].firstChild.nodeValue + "')></td></tr>"
	       	 	//to add customer for Clear button 
	     		cust = cust+","+item.getElementsByTagName("cid")[0].firstChild.nodeValue;	
	     	}   
		}  
		//alert("'" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "','" + cust + "'");
		str += "<tr align='center' height='20'><td colspan=3 class='detailsheading' width='300'><input type='button' value='Send Message' onClick='SentMsg()'</td></tr>"
		str += "<tr align='center' height='20'><td colspan=3 class='detailsheading' width='300'><input type='button' value='Clear' onClick=DelCustTemp('" + item.getElementsByTagName("msgid")[0].firstChild.nodeValue + "','" + cust + "')></td></tr>"		
		str += "</table>"
		divdetails.innerHTML = str;
}

//delete whole list of customer
function DelCustTemp(msgid,cust)
{
	 var req = newXMLHttpRequest();
     var MsgId = msgid+":"+cust;
     //alert(MsgId);
     
	 req.onreadystatechange = getReadyStateHandler(req, showDelMsgTemp);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_LIST_DELETE_TEMPMSG&AppId="+MsgId);
	 
}

//delete selected customer from list
function DelMsgTemp(msgid,cid)
{   
	 var req = newXMLHttpRequest();
    
     var MsgId = msgid+","+cid;
	 req.onreadystatechange = getReadyStateHandler(req, showDelMsgTemp);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_DELETE_TEMPMSG&AppId="+MsgId);
}

function showDelMsgTemp(dataXml)
{
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("msgid")[0].firstChild;
     	if (nname != null)
     	{	
   	 		alert("Mesaage Deleted Sucessfuly");
   	 	}			
 	}
 	findTempList();
}

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
  // alert(mySelect.length);
   var z = 0;
   var transactions = "";
   for(var i = 0; i < mySelect.length; i++)
   {
      if(transactions.length > 0)
	  {
	      transactions = transactions + ",'" + mySelect.options[i].value + "'";
	  } else
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


</script>
</head>
<body >
 
<!-- <body onLoad="findApplication(),findMsgDetails();">   -->
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<%

String str=(String)session.getAttribute("msg");
//System.out.println("str"+str);


String msgid=(String)request.getParameter("msgid");
//System.out.println("msgid"+msgid);
AdminUtil autil=new AdminUtil();
String msgdesc=autil.getMsgDescription(msgid);

%>
<form action="<%=request.getContextPath()%>/SendMessage.do" name="SendMessage" id="SendMessage" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody>
	  <tr>
	     <td class="newhead1"></td>
	     <th class="headtext">Send Message</th>
	     <td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	     <td class="newhead3">&nbsp;</td>
	     <td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
      </tr>
      
      <tr>
	     <td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	     <td colspan="3"><img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	     <table border="0" cellpadding="0" cellspacing="0" width="100%">
	     <tbody>
	     <tr>
	     <td bgcolor="#dbeaf5">
		 <table border="0" cellpadding="2" cellspacing="1" width="100%">
		 <tbody>						
		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Message Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="MsgDescriptiontxt" name="MsgDescriptiontxt"  class="BoxBorder" ><%=msgdesc%></textarea></td>
		</tr>	
		
		<tr class="bgcolor"> 
			<td id="t_street_address" colspan=4 align="center">Selection Criteria:</td>
		</tr>
	
<!-- New Customer -->
		<tr class="bgcolor"  align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Customer</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4" background-color="#DBEAF5">
							<select size="5" name="customerList" id="customerList" class="ctrl"  multiple="multiple" style="width: 250px" >
					            <%=custid%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px"  onClick="moveOption(this.form.customerList, this.form.Customeridtxt)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="5" name="Customeridtxt" id="Customeridtxt" class="ctrl" multiple="multiple" style="width: 250px" ></select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" onClick="moveALLOption(this.form.customerList, this.form.Customeridtxt)"  alt="Move selected" />
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
		
			<!-- Customer strt-->
		<!-- 	
			
		<tr class="bgcolor" > 
			<td id="t_street_address" >&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor" >
			     <select size="1" name="Customeridtxt" id="Customeridtxt"   class="tabtextnormal" onchange="ChangeState()">
			      
			   
                   <option value="selectone" selected="selected"  >--Select Customer--</option>
                   <option value="ALL">--ALL--</option>
                   <%=custid %>
                </select>
            </td>
            <td>
                  <input type="button" id="customerRight"  value="->" alt="Move selected" /><br />
                  <input type="button" id="customerRightAll"  value="->>" /><br />
                  <input type="button" id="customerLeftAll"  value="<<-" /><br />
                  <input type="button" id="customerLeft"  value="<-" />
           </td>
           <td>
                 <select size="5" name="customerSelected" id="customerSelected"   multiple="multiple" style="width: 50%">
           </td>
           </tr> 
             -->
            <!-- Customer end-->

		
		<tr class="bgcolor"> 
			<td id="t_street_address" colspan=2 align="center"><b>OR</b></td>
		</tr>
			
<!-- New State -->		
		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select State</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="5" name="StateList" id="StateList" class="ctrl" multiple="multiple" style="width: 250px" >
					            <%=statename%>
					        </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.StateList, this.form.StateSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					         <select size="5" name="StateSelList" id="StateSelList" class="ctrl" multiple="multiple" style="width: 250px" > </select>
					    </td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="RightAllcmd" class="singlebtn" value="->>" width="100px" onClick="moveALLOption(this.form.StateList, this.form.StateSelList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="LeftAllcmd" class="singlebtn" value="<<-" width="100px" onClick="moveALLOption(this.form.StateSelList, this.form.StateList)" />
					    <td>
					</tr>
					<tr>
						<td>
					        <input type="button" name="Leftcmd" class="singlebtn" value="<-" width="100px" onClick="moveOption(this.form.StateSelList, this.form.StateList)" />
					    <td>
					</tr>
				</table>
			</td>
		</tr>
			
<!-- Generate Site -->		
		<tr class="bgcolor"> 
			 <td class="bgcolor" colspan="2" align="center">
			 	<input  type="button" onClick="createlistSite()" value="Generate Site"> 		
			 </td>
		</tr>
		
<!-- New Site -->		
		<tr class="bgcolor" align="left">
			<td width="100" height="30" align="left" id="t_street_address">Select Site</td>
			<td width="450">
				<table border="0" width="100%">
					<tr>
						<td rowspan="4">
							<select size="5" name="SiteList" id="SiteList" class="ctrl" multiple="multiple" style="width: 250px" > </select>
					    </td>
					    <td>
					        <input type="button" name="Rightcmd" class="singlebtn" value="->" width="100px" onClick="moveOption(this.form.SiteList, this.form.SiteSelList)"  alt="Move selected" />
					    <td>
					   	<td rowspan="4">
					        <select size="5" name="SiteSelList" id="SiteSelList" class="ctrl" multiple="multiple" style="width: 250px" ></select>
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
		   <td colspan=2 align="center">
                <input type="button" name="addlist" value="Add To List" onClick="AddTempMsg()"  ;>
            </td> 		
		</tr>
			
		<!-- state site strt-->	
		<!-- 
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
			<td class="bgcolor">
			<select size="1" name="Statetxt" id="Statetxt" class="tabtextnormal" onchange="findSite()">
		             <option value="selectone" selected="selected">-- Select State --</option>
		             <option value="ALL">--ALL--</option>
		             <%=statename%>
		         </select>
		         </td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
			<select size="1" name="Sitetxt" id="Sitetxt" class="tabtextnormal">
			<option value="selectone" selected="selected">-- Select Site --</option>
		            
		             <%=sitename%>
		         </select>
		         <input type="button" name="addlist" value="Add To List" onClick="AddTempMsg()";>
		         </td>
		</tr>	
		-->
		<!-- state site end -->		
		
		
			
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<% str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>
			</td>
		</tr>
			
   </tbody>
   </table>
   </td>
   </tr>
   </tbody>
   </table>
   <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br></td>
   <td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
</tr>
	
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	 <td colspan="4" align="right" bgcolor="#006633">
	  <table border="0" cellpadding="0" cellspacing="0">
	    <tbody>
	      <tr>
		     <td  bgcolor="#063" width="100">
		         <input type="hidden" name="Transactions" id="Transactions" value="" />				
	    	     <input type="hidden" name="SiteTransactions" id="SiteTransactions" value="" />	
			     <input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=msgid%>" />	
			     <input type="hidden" name="Admin_Input_Type" value="SendMessage" />	
		     </td> 
	         <td width="0" ></td>                                                                        
             <td class="btn" width="100"><input name="Reset" value="Reset" class="btnform" type="reset"    onClick="moveALLOption(this.form.Customeridtxt, this.form.customerList) ; moveALLOption(this.form.StateSelList, this.form.StateList) ; moveALLOption(this.form.SiteSelList, this.form.SiteList)" ></td>
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

  <tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="400">
		    <tbody>		
			  <tr>
				<td >
					<div id="tempmsgdetails"></div>	
				</td>
			  </tr>
			</tbody>
		</table>	
	</td>
  </tr>
  
  <tr>
     <td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="400">
		  <tbody>		
			<tr>
				<td>
					<div id="msgdetails"></div>	
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