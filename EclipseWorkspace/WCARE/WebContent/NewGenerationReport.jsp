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
<%@ page import="com.enercon.security.utils.SecurityUtils"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

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
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();

Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

int k=0;
StringBuffer buffer = new StringBuffer();
SecurityUtils secUtil = new SecurityUtils();
String got="0";
 	if(session.getAttribute("LoginType").equals("E"))
		 custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
 	else
 	{ 
     	List tranList1 = new ArrayList(); 
     	tranList1 = (List)session.getAttribute("custtypee");
     	 if(tranList1.size()==0)
     	 {
     		 tranList1=secUtil.getcustomerdetails(userid);
     	 }
     	 
     	//System.out.println("custtypee"+session.getAttribute("custtypee")); 
	 	//System.out.println("tranList1.size():"+tranList1.size());
  		 if(tranList1.size()==1)
   			{
	  		 got="1";  
   			}
  
   	//System.out.println(tranList1.size());
	    for (int j=0; j <tranList1.size(); j++)
	     {
	    	Vector v11 = new Vector();
			 v11 = (Vector)tranList1.get(j);
	   	  String customerid=(String)v11.get(2);
	   	 String customername=(String)v11.get(3);
	   	 //System.out.println("customername"+customername);
	   	   buffer.append("<OPTION VALUE='" + customerid + "' >" + customername + "</OPTION>");
	     k++;
	     }
	     custid=buffer.toString();
	     //System.out.println("cc"+custid);
	   }



if(session.getAttribute("LoginType").equals("E"))
	custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);
else
{ 
    List tranList1 = new ArrayList(); 
	tranList1 = (List)session.getAttribute("custtype"); 
    for (int j=0; j <tranList1.size(); j++)
   	{
    	Vector v11 = new Vector();
	 	v11 = (Vector)tranList1.get(j);
   	  	String customerid=(String)v11.get(2);
   	 	String customername=(String)v11.get(3);
   	   	buffer.append("<OPTION VALUE='" + customerid + "'>" + customername + "</OPTION>");
    }
    custid=buffer.toString();
}
%>
<script type="text/javascript">

function gotoSave1()
{
	url="NewGenerationReport.jsp";
}

function  gotoSave()
{
	var blnSave=false;
	var blnSave1=false;
 	blnSave = validateForm('Customer Name ',document.getElementById("CustIdtxt").value,'M','',
				'From Date',document.getElementById("FromDatetxt").value,'M','',
				'To Date',document.getElementById("ToDatetxt").value,'M','');
	    
  	 url="NewMonthlyReport.jsp"; 	
  	 		
	if (blnSave == true )
	{
		var rd=document.forms[0].FromDatetxt.value;
    
     	var pdate=rd;
     	
     	var cdate = document.forms[0].ToDatetxt.value;;    
     	
	 	var todate=new Date(cdate.substr(6,4),cdate.substr(3,2) -1,cdate.substr(0,2)); //yyyy-mm-dd
   	 	var fromdate = new Date(pdate.substr(6,4),pdate.substr(3,2) -1,pdate.substr(0,2));
	 	var one_day=1000*60*60*24;	
    	var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day) );	
        var type=document.getElementById("PeriodTypetxt").value;
        var rtype=document.getElementById("MeterTypetxt").value;
     	if(days<0 && type=='M')
  	 		{
	  	 		alert("To Date is always greater than From Date");
	  	 		return false;	  	 		
  	 		}
  	 		else if(type=='M')
  	 		{
  	 		  if(days>3650)
  	 			{
	  	 			alert("The difference between From-To Data should not greater than 3650 days.");
	  	 			return false;
	  	 		
  	 			}
  	 		}
  	 		else if(type=='D')
  	 		{
  	 		  if(days>365)
  	 			{
	  	 			alert("The difference between From-To Data should not greater than 365 days.");
	  	 			return false;
	  	 		
  	 			}
  	 		}
  	 		if(type=='D')
  	 			window.open(url+"?id="+document.getElementById("CustIdtxt").value+"&fd="+rd+"&td="+rd+"&type="+type+"&rtype="+rtype);
  	 		else 
  	 			window.open(url+"?id="+document.getElementById("CustIdtxt").value+"&fd="+rd+"&td="+cdate+"&type="+type+"&rtype="+rtype);	 		
	}
	else
	{
		return false;
   }
   
   return false;
}

function disableToDate()
{	
	if(document.forms[0].PeriodTypetxt.value=='D')
		document.forms[0].ToDatetxt.disabled = true;
	else
		document.forms[0].ToDatetxt.disabled = false;
}


 


</script>

</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action=""   METHOD="POST" onSubmit="return gotoSave()" name="CoroporateReport" id="CoroporateReport">


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
				<%if(got.equals("0")){ %>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
				<select size="1" id="CustIdtxt" name="CustIdtxt" class="ctrl" >
		            <option value="">--Make a Selection--</option>
		            <%=custid %>
		        </select>
			</td>
		</tr>
		<%}else{ %>
			
		<tr class="bgcolor"> 
		
			<td id="t_street_address">Customer:</td>
			<td class="bgcolor">
			<select size="1" id="CustIdtxt" name="CustIdtxt"  class="ctrl" font:color="Black" disabled>
		           <option value="">--Make a Selection--</option>
		           <%=custid %>
		    </select>	
			</td>
			
		</tr>
		
		<script>
			document.forms[0].CustIdtxt.selectedIndex=1;
		</script>
				
		<%} %>			
			<tr class="bgcolor">
											<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
											<td class="bgcolor"><input type="text" name="FromDatetxt" id="FromDatetxt" value="<%=dateFormat.format(date.getTime())%>" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
											<a href="javascript:void(0)" id="dc"
			  													 onClick="if(self.gfPop)gfPop.fPopCalendar(document.CoroporateReport.FromDatetxt);return false;"><img
																	class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif"
														border="0" alt=""></a></td>
										</tr>
										<tr class="bgcolor">
												<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
												<td class="bgcolor"><input type="text" name="ToDatetxt" id="ToDatetxt" disabled value="<%=dateFormat.format(date.getTime())%>" size="20" class="ctrl" maxlength="10" onfocus="dc.focus()" /> 
		   										 <a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.CoroporateReport.ToDatetxt);return false;">
	        									  <img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></td>
		 								</tr>
		 								
		 								
		 								<tr class="bgcolor">
												<td id="t_street_address">&nbsp;Select&nbsp;Period:</td>
												<td class="bgcolor"><select size="1" id="PeriodTypetxt" name="PeriodTypetxt" class="ctrl" onChange="disableToDate()">
												<option value="D" selected>Daily</option>
												<option value="M" >Monthly</option>
												<option value="Y">Yearly</option>
											</select></td>
		 								</tr>
		 								
		 									<tr class="bgcolor">
												<td id="t_street_address">&nbsp;Select&nbsp;Meter Type:</td>
												<td class="bgcolor"><select size="1" id="MeterTypetxt" name="MeterTypetxt" class="ctrl">
												<option value="W">--WEC--</option>
												<option value="WE">--WEC/EB--</option>
												
				
											</select></td>
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
<DIV id=progressbar style="DISPLAY: none" align="center"><IMG style="VERTICAL-ALIGN: bottom" 
 src="<%=request.getContextPath()%>/resources/images/progressbar.gif"><Font size=4> Please wait...downloading data
</Font></DIV>	
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>