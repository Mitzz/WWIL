<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
int status = 0;
String eid = "";
String sname = "";
String stid = "";
String ebshort="" ;
String ebdesc="";
//String ebtype="";
//String ebstype="" ;	
//String ebmfactor = "";	
String ebstatus ="1";	
//String ebcapacity = "";
String ebsite = "";	
String fdid="";
String Customeridtxt = "";	
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	stid = dynaBean.getProperty("Statetxt").toString();
	ebsite = dynaBean.getProperty("EBSitetxt").toString();	
	// System.out.println("ebsite--"+ebsite);
	fdid = dynaBean.getProperty("FDDesc") == null ? "" : dynaBean.getProperty("FDDesc").toString();
	String str1=(String)session.getAttribute("SubmitMessage");
	if(str1 != null && str1.equals("Success")){
		eid = "";
		sname = "";
		ebshort="" ;
		ebdesc="";
		//ebtype="";
		//ebstype="" ;	
		//ebmfactor = "";	
		ebstatus ="";	
		//ebcapacity = "";
		//fdid="";
		Customeridtxt = "";	
	}else{
		ebshort = dynaBean.getProperty("EBShorttxt").toString();
		ebdesc = dynaBean.getProperty("EBDesctxt").toString();
		//ebtype = dynaBean.getProperty("EBTypetxt").toString();
		//ebstype = dynaBean.getProperty("EBSubTypetxt").toString();	
		//ebmfactor = dynaBean.getProperty("EBMFactortxt").toString();	
		Customeridtxt=dynaBean.getProperty("Customeridtxt").toString();
	  	//ebstatus = dynaBean.getProperty("EBStatustxt").toString();
	  	if(dynaBean.getProperty("EBStatustxt") == null || (dynaBean.getProperty("EBStatustxt")).toString().equals("1")){
	  		status = 1;
		}
		else{
			status = 2;
		}
		//ebcapacity = dynaBean.getProperty("EBCapacitytxt").toString();		
		eid = dynaBean.getProperty("EBId").toString();				
	}	
	session.setAttribute("SubmitMessage","");
	session.removeAttribute("dynabean");
}
String sitename = "";
String feder = "";
String statename = "";

String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();

if(roleid.equals("0000000001"))
	statename = AdminUtil.fillMaster("TBL_STATE_MASTER",stid);
else
	statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS",stid,session.getAttribute("loginID").toString());
//System.out.println(stid + " " + ebsite);
String custid = AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",Customeridtxt);

	if(!statename.equals("") && roleid.equals("0000000001"))
	{ 
		sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS1",ebsite,stid+","+session.getAttribute("loginID").toString());
	}
	else
	{
		sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER",ebsite,stid);
		sitename=AdminUtil.fillWhereMaster("TBL_SITE_MASTER_BY_RIGHTS",ebsite,stid+","+session.getAttribute("loginID").toString());
	} 

if(! sitename.equals(""))
{ 
	System.out.println("ebsite--"+ebsite);
	feder=AdminUtil.fillWhereMaster("TBL_FEDER_MASTER",fdid,ebsite);
}
%>
<script type="text/javascript">

function findSite() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     ApplicationId=ApplicationId+","+document.forms[0].RoleId.value;
     req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getSiteBYRIGHTS&AppId="+ApplicationId);
}
function showSite(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].EBSitetxt.options.length = 0;
	document.forms[0].EBSitetxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{	     	
	     		document.forms[0].EBSitetxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
   	 	    }
        }
}
function findFeder() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].EBSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showfeder);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getFeder&AppId="+ApplicationId);
}

function showfeder(dataXml)
{
	var cart = dataXml.getElementsByTagName("federhead")[0];
	var items = cart.getElementsByTagName("federcode");
	document.forms[0].FDDesc.options.length = 0;
	document.forms[0].FDDesc.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
   	{   
    	var item = items[I]
    	var nname = item.getElementsByTagName("fdesc")[0].firstChild;
    	if (nname != null)
    	{
    		document.forms[0].FDDesc.options[I + 1] = new Option(item.getElementsByTagName("fdesc")[0].firstChild.nodeValue,item.getElementsByTagName("fid")[0].firstChild.nodeValue);
 	    }
     }
}
function MaskPlus()
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Customeridtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showcustMask);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findCustomerMasterByID&AppId="+ApplicationId);
	
}
function showcustMask(dataXml)
{
	var cart = dataXml.getElementsByTagName("custmaster")[0];
	var items = cart.getElementsByTagName("custcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("cname")[0].firstChild;
     	if (nname != null){
			var str = document.getElementById("EBDesctxt").value;
			if (str.length > 0)
			{
				document.forms[0].EBDesctxt.value = document.forms[0].EBDesctxt.value + item.getElementsByTagName("shortname")[0].firstChild.nodeValue + "-";
				document.forms[0].EBShorttxt.value = document.forms[0].EBDesctxt.value;
			}
		} 			
 	}
}
function findEBMask() 
{ 
	 var req = newXMLHttpRequest();	 
	 
	 var list = document.forms[0].EBSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showEBMask);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getEBMask&AppId="+ApplicationId);
}

function showEBMask(dataXml)
{   
	var cart = dataXml.getElementsByTagName("maskhead")[0];
	var items = cart.getElementsByTagName("maskcode");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("stcode")[0].firstChild;
     	if (nname != null)
     	{
     		if(document.forms[0].EBId.value.length <= 0)
     		{
		     	 document.forms[0].EBDesctxt.value= "IEIL-"+item.getElementsByTagName("stcode")[0].firstChild.nodeValue+"-"+item.getElementsByTagName("sicode")[0].firstChild.nodeValue+"-";	          	 				
		     	 document.forms[0].EBShorttxt.value= document.forms[0].EBDesctxt.value
		     	 MaskPlus();
		    }
        }		
    }        
}


function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var list = document.forms[0].EBSitetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
    
    
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);
	// alert("");
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBMaster&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{       
	     var cart = dataXml.getElementsByTagName("ebmaster")[0];	     
	     var items = cart.getElementsByTagName("ebcode");	
		var divdetails = document.getElementById("ebdetails");
		divdetails.innerHTML = "";
		var str = "<table border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str+="<tr align='center' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='250'>EB Description</th>"
		str+="<th class='detailsheading' width='150'>Short Description</th><th class='detailsheading' width='150'>Feder</th>"
		str+="<th class='detailsheading' width='150'>Customer</th><th class='detailsheading' width='40'>Staus</th>"
	
		str +="<th class='detailsheading' width='40'>Edit</th><th class='detailsheading' width='40'>Copy</th></tr>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  		   		
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("ebid")[0].firstChild;
	     	if (nname != null){
	     		
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     		
	     		str+="<td align='left'>" + item.getElementsByTagName("ebname")[0].firstChild.nodeValue + "</td>"	  
	     		str+="<td align='left'>" + item.getElementsByTagName("ebshort")[0].firstChild.nodeValue + "</td>"	
	     		if (item.getElementsByTagName("feder")[0].firstChild.nodeValue == ".")
	     		{
	     			str+="<td align='left'></td>"	
	     		}
	     		else
	     		{
	     			str+="<td align='left'>" + item.getElementsByTagName("feder")[0].firstChild.nodeValue + "</td>"	  
	     		}
	     		str+="<td align='left'>" + item.getElementsByTagName("custname")[0].firstChild.nodeValue + "</td>"  
	     		if(item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue == 1){
	     			str+="<td align='left'>Active</td>";
	     		}else{
	     			str+="<td align='left'>Deactive</td>";	     			  
	     		}
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to edit the record' "
	     		str+="onClick=findDetails('" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue + "','E')></td>"
	     		str+="<td align='center'><input type='image' src='<%=request.getContextPath()%>/resources/images/edit.gif' alt='Click to copy the record' "
		     	str+="onClick=findDetails('" + item.getElementsByTagName("ebid")[0].firstChild.nodeValue + "','C')></td></tr>"	     		
	   	 		
	   	 	}   	 	
		}
       	if(document.forms[0].EBId.value!="")
  	 		{}
 		else
 	 		document.forms[0].EBId.value = "";
		divdetails.innerHTML = str;
}
function findDetails(ebid,type)
{    
	 var req = newXMLHttpRequest();
	 document.forms[0].edittytxt.value =type;
	 alert(type);
     var ApplicationId = ebid;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBMasterByID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebname")[0].firstChild;
     	
     	if (nname != null){
     	
     	
   	 		document.forms[0].EBDesctxt.value = item.getElementsByTagName("ebname")[0].firstChild.nodeValue;
   	 		document.forms[0].EBShorttxt.value = item.getElementsByTagName("ebshort")[0].firstChild.nodeValue;
   	 		//document.forms[0].EBTypetxt.value = item.getElementsByTagName("ebtype")[0].firstChild.nodeValue;  
   	 		//document.forms[0].EBSubTypetxt.value = item.getElementsByTagName("ebstype")[0].firstChild.nodeValue;  
   	 		//document.forms[0].EBCapacitytxt.value = item.getElementsByTagName("ebcapacity")[0].firstChild.nodeValue;
   	 		//document.forms[0].EBMFactortxt.value = item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBId.value = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;  
   	 		 if(item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue=="1")
   	 		 {
  		       document.forms[0].EBStatustxt.checked=false;
	          }
	        else
	          document.forms[0].EBStatustxt.checked=true;
	          if (item.getElementsByTagName("fdid")[0].firstChild.nodeValue == ".")
	          {
		          	document.forms[0].FDDesc.value = "";
	          }
	          else
	          {
	          		document.forms[0].FDDesc.value = item.getElementsByTagName("fdid")[0].firstChild.nodeValue;
	          }
	          if (item.getElementsByTagName("custid")[0].firstChild.nodeValue == ".")
	          	document.forms[0].Customeridtxt.value = "";
	          else
	          	document.forms[0].Customeridtxt.value = item.getElementsByTagName("custid")[0].firstChild.nodeValue;
	          	
	          if(document.forms[0].edittytxt.value=="E")
		        {
   	 				document.forms[0].Submit.value="Update";	
   	 			}
   	 		  else
   	 		    {
   	 		    	document.forms[0].EBShorttxt.value = "";
   	 		    	document.forms[0].EBId.value = "";
   	 		    	document.forms[0].Submit.value="Submit";
   	 		    } 		
   	 	}
   	 	else{
   	 		document.forms[0].EBDesctxt.value = "";
   	 		document.forms[0].EBShorttxt.value = "";
   	 		//document.forms[0].EBTypetxt.value = "";  
   	 		//document.forms[0].EBSubTypetxt.value = "";  
   	 		//document.forms[0].EBCapacitytxt.value = "";
   	 		//document.forms[0].EBMFactortxt.value = "";
   	 		document.forms[0].EBId.value ="";
   	 		document.forms[0].FDDesc.value="";
   	 		document.forms[0].Customeridtxt.value="";
   	 	    document.forms[0].EBStatustxt.checked=false;
   	 	} 			
 	}
}
function chkFunction()
{
//        if(document.forms[0].EBStatustxt.checked=true)
//  	 		{document.forms[0].EBStatustxt.value=1; } 
//	    else{
//	        document.forms[0].EBStatustxt.value=0;
//	        }
	        
}
function validate(frm)
{	
	var ch;
	var flag=true;
	var error="";
	
		 if(isNaN(frm.EBMFactortxt.value)){
			flag=false;
			error=error+"Only numbers are allowed in Multifactor .\n";
			if(focus==false){
				frm.EBMFactortxt.focus();
				focus=true;
			  }
		 }
	
	if(flag==false)
	{
		alert(error);
		return false;
	}
	else 	
		return true;
}
function confirmation()
{   
	
	if(document.forms[0].EBId.value!="")
	{
		var answer = confirm("Are you sure you want to Update?")
		if (answer)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else
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
	}
}
</script>
</head>
<body onload="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/EBMaster.do" method="post" onSubmit="validate(this)">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">EB Master</th>
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
			<td id="t_company" width="219">Select State:</td>
			<td valign="top">
				<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findSite()">
					<option value="">--Make a Selection--</option>
					<%=statename%>
				</select>
			</td>
		</tr>					
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Site Name:</td>
		  <td bgcolor="#ffffff" width="85%">
		    <select name="EBSitetxt" id="EBSitetxt" class="ctrl"  onChange="findFeder();findEBMask();findApplication()">
              <option value="">--Make a Selection--</option>
				   <%=sitename%>   
            </select>
		  </td>
	  	</tr>
	  	<tr bgcolor="#ffffff">
		  <td id="t_general_information">Feder:</td>
		  <td bgcolor="#ffffff" width="85%">
		    <select name="FDDesc" id="FDDesc" class="ctrl" >
              <option value="">--Make a Selection--</option>
				   <%=feder%>     
            </select>
		  </td>
	  	</tr>
	  	<tr bgcolor="#ffffff">
			<td id="t_company">Customer:</td>
			<td valign="top"><select name="Customeridtxt" id="Customeridtxt" class="ctrl" onchange="findEBMask()">
              <option value="" selected="selected">-- select --</option>
               <%=custid %>
            </select></td>
		</tr> 
<%--
		<tr bgcolor="#ffffff"> 
				<td id="t_street_address">Commision Date:</td>
				<td bgcolor="#ffffff">
					<input type="text" name="Datetxt" id="Datetxt" size="15" class="ctrl" maxlength="10" onfocus="dc.focus()" value="<%=new java.util.Date() %>" />
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECMaster.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
				</td>
		</tr> 	
--%>			
		<tr bgcolor="#ffffff">
			<td width="15%" id="t_company">Name</td>
			<td width="85%" valign="top"><input name="EBDesctxt" id="EBDesctxt" value="<%=ebdesc%>" size="25" class="ctrl" type="text" /></td>
			</tr>
		<tr bgcolor="#ffffff"> 
			<td id="t_street_address">Short Description</td>
			<td bgcolor="#ffffff"><input name="EBShorttxt" id="EBShorttxt" value="<%=ebshort%>" size="55" maxlength="100" class="ctrl" type="text" /></td>
		</tr>
		<tr bgcolor="#ffffff">
		  <td id="t_general_information">Deactivate</td>
		  <td bgcolor="#ffffff">
		  <%if (status == 0){ %>
				<input type="checkbox" name="EBStatustxt" id="EBStatustxt" value="1" />
			<%}else{ %>
				<input type="checkbox" name="EBStatustxt" id="EBStatustxt" value="2" />
			<%} %>
			  </td>
		  </tr>
		  <tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
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
	<tbody><tr>
		<td class="btn" width="100"><input name="Submit" value="Submit" class="btnform" type="submit" onClick="return confirmation()"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100">
		<input type="hidden" name="Admin_Input_Type" value="EBMaster" />		
		<input type="hidden" name="EBId" value="<%=eid %>" />
		<input type="hidden" name="RoleId" value="<%=roleid %>" />
		<input name="Reset" value="Cancel" class="btnform" type="reset">
		<input type="hidden" name="edittytxt" id="edittytxt" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
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
					<div id="ebdetails"></div>
				</td>
			</tr>
			</tbody>
		</table>	
	</td>
</tr>
</table>
</div>
</body>
</html>