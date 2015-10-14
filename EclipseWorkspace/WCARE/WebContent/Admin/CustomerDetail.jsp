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
<%@page import="com.enercon.global.utils.DynaBean"%>
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
	String rn = "";
	String mid = "";
	String de = "";
	String ri = "";
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

function ChangeState()
{

var list = document.forms[0].Customeridtxt;
var ApplicationId = list.options[list.selectedIndex].value;

  if(ApplicationId=="NA")
   {
   
   document.forms[0].Statetxt.disabled=false;
   document.forms[0].Sitetxt.disabled=false;
   }
   else
   {
   document.forms[0].Statetxt.selectedIndex=0;
   document.forms[0].Sitetxt.selectedIndex=0;
   document.forms[0].Statetxt.disabled=true;
   document.forms[0].Sitetxt.disabled=true;
   }


}
function findSite() 
{ 

	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSite&AppId="+ApplicationId);
}

function showSite(dataXml)
{ 
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].Sitetxt.options.length = 0;
	document.forms[0].Sitetxt.options[0] = new Option("--ALL--","NA");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     	document.forms[0].Sitetxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    }
	     	
    	 		
    	 				
        }
}

function popUp() 
{
	var list4=document.forms[0].Statetxt;
	var list3=document.forms[0].Customeridtxt;   
	var cust;
	var state;
	if(list4==0)
    {
		state ="NA";
 	}
	else
	{
    	state = list4.options[list4.selectedIndex].value;
    }
	if(list3==0)
	{
		cust ="NA";
	}
	else
	{
     	cust = list3.options[list3.selectedIndex].value;
    }
	var w1 = document.forms[0].Sitetxt.selectedIndex;
	var siteid;
	 
	 if(w1==0)
	 {
	  siteid ="NA";
	 }
	 else
	 {
      siteid = document.forms[0].Sitetxt.options[w1].value;
     }
   location.href="ExportCustomerDetail.jsp?stateid="+state+"&cust="+cust+"&siteid="+siteid;
	
}
</script>


</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
	<tr width="100%">
		<td width="100%" align="center">

		<form name="customerDetail" id="customerDetail" method="post">
		<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
			<tbody>
				<tr>
					<td class="newhead1"></td>
					<th class="headtext">Customer Address Detail</th>
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
											<td id="t_street_address" colspan=2 align="center">Selection Criteria:</td>

										</tr>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
											<td class="bgcolor"><select name="Customeridtxt" id="Customeridtxt" class="ctrl" class="tabtextnormal"
												onchange="ChangeState()">

												<option value="NA" selected="selected">--Select Customer--</option>
												
												<%=custid%>
											</select></td>
										</tr>
										<tr class="bgcolor">
											<td id="t_street_address" colspan=2 align="center">OR</td>

										</tr>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;State:</td>
											<td class="bgcolor"><select size="1" name="Statetxt" id="Statetxt" class="tabtextnormal"
												onchange="findSite()">
												<option value="NA" selected="selected">-- Select State --</option>

												<%=statename%>
											</select></td>
										</tr>
										<tr class="bgcolor">
											<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
											<td class="bgcolor"><select size="1" name="Sitetxt" id="Sitetxt" class="tabtextnormal">
												<option value="NA" selected="selected">-- Select Site --</option>

												<%=sitename%>
											</select></td>
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
								<td class="btn" width="100"><input name="" button"" type="button" class="btnform" value="Show"
									onClick="popUp()" /></td>
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

</html>
