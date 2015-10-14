<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
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
String statename = AdminUtil.fillWhereMaster("TBL_STATE_MASTER_BY_RIGHTS","",session.getAttribute("loginID").toString());
//String sitename=AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",session.getAttribute("loginID").toString());
String roleid=session.getAttribute("RoleID").toString();
String sitename="";
String areaname="";

if(roleid.equals("0000000001"))	
	sitename = AdminUtil.fillMaster("VIEW_SITE_MASTER",sitename);
else
	sitename = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS","",sitename);
if(roleid.equals("0000000001"))	
	
	areaname = AdminUtil.fillMaster("VIEW_AREA_MASTER",areaname);
else
	areaname = AdminUtil.fillWhereMaster("TBL_STATE_AREA_RIGHTS","",areaname);

String substationname=AdminUtil.fillMaster("VIEW_SUBSTATION_MASTER","");
String feedername=AdminUtil.fillMaster("VIEW_FEEDER_MASTER","");
String ebid =AdminUtil.fillWhereMaster("SELECT_EB_MASTER_BY_RIGHTS","",session.getAttribute("loginID").toString());
String wecname = AdminUtil.fillWhereMaster("SELECT_WEC_MASTER_BY_RIGHTS","",session.getAttribute("loginID").toString());
String custid  = AdminUtil.fillWhereMaster("SELECT_CUSTOMER_MASTER_BY_RIGHTS","",session.getAttribute("loginID").toString());
String wectype = AdminUtil.fillMaster("TBL_WEC_TYPE","");
%>

<script type="text/javascript">
function findArea() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Statetxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showArea);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getareabystate&AppId="+ApplicationId);
}
function showArea(dataXml)
{

	var cart = dataXml.getElementsByTagName("areahead")[0];
	var items = cart.getElementsByTagName("areacode");	
	
	document.forms[0].Areatxt.options.length = 0;

	document.forms[0].Areatxt.options[0] = new Option("--Make a Selection--",".");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("acode")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].Areatxt.options[I + 1] = new Option(item.getElementsByTagName("area")[0].firstChild.nodeValue,item.getElementsByTagName("acode")[0].firstChild.nodeValue);
        }		
    }
}
function findSubstation() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Areatxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showSubstation);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getsubstationbyarea&AppId="+ApplicationId);
}
function showSubstation(dataXml)
{

	var cart = dataXml.getElementsByTagName("substationhead")[0];
	var items = cart.getElementsByTagName("substationcode");	
	
	document.forms[0].Subtxt.options.length = 0;

	document.forms[0].Subtxt.options[0] = new Option("--Make a Selection--",".");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("scode")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].Subtxt.options[I + 1] = new Option(item.getElementsByTagName("substation")[0].firstChild.nodeValue,item.getElementsByTagName("scode")[0].firstChild.nodeValue);
        }		
    }
}

function findFeeder() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Subtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showSubstation);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getfeederbysub&AppId="+ApplicationId);
}
function showSubstation(dataXml)
{

	var cart = dataXml.getElementsByTagName("feederhead")[0];
	var items = cart.getElementsByTagName("feedercode");	
	
	document.forms[0].Feedertxt.options.length = 0;

	document.forms[0].Feedertxt.options[0] = new Option("--Make a Selection--",".");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("fcode")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].Feedertxt.options[I + 1] = new Option(item.getElementsByTagName("feeder")[0].firstChild.nodeValue,item.getElementsByTagName("fcode")[0].firstChild.nodeValue);
        }		
    }
}

function findSite() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Areatxt;
     var ApplicationId = list.options[list.selectedIndex].value;
	 req.onreadystatechange = getReadyStateHandler(req, showSite);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getsitebystate&AppId="+ApplicationId);
}
function showSite(dataXml)
{

	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");	
	
	document.forms[0].EBSitetxt.options.length = 0;

	document.forms[0].EBSitetxt.options[0] = new Option("--Make a Selection--",".");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("scode")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].EBSitetxt.options[I + 1] = new Option(item.getElementsByTagName("site")[0].firstChild.nodeValue,item.getElementsByTagName("scode")[0].firstChild.nodeValue);
        }		
    }
}
function findeb() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].EBSitetxt;	
     var ApplicationId = list.options[list.selectedIndex].value;     
	 req.onreadystatechange = getReadyStateHandler(req, showeb);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=geteb&AppId="+ApplicationId);
}
function showeb(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].EBIdtxt.options.length = 0;
	document.forms[0].EBIdtxt.options[0] = new Option("--Make a Selection--",".");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("sdesc")[0].firstChild;
	     	if (nname != null)
	     	{
	     	document.forms[0].EBIdtxt.options[I + 1] = new Option(item.getElementsByTagName("sdesc")[0].firstChild.nodeValue,item.getElementsByTagName("sid")[0].firstChild.nodeValue);
	     	//document.forms[0].Customeridtxt.options[I + 1] = new Option(item.getElementsByTagName("weccusid")[0].firstChild.nodeValue,item.getElementsByTagName("custid")[0].firstChild.nodeValue);

	        }		
        }
        //findcust();
}

function findcust() 
{ 

	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].EBSitetxt;	
     var ApplicationId = list.options[list.selectedIndex].value;     
	 req.onreadystatechange = getReadyStateHandler(req, showebcust);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=getcust_id&AppId="+ApplicationId);
}
function showebcust(dataXml)
{
	var cart = dataXml.getElementsByTagName("sitehead")[0];
	var items = cart.getElementsByTagName("sitecode");
	document.forms[0].Customeridtxt.options.length = 0;
	document.forms[0].Customeridtxt.options[0] = new Option("--Make a Selection--",".");
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I]
     	var nname = item.getElementsByTagName("weccusid")[0].firstChild;
     	if (nname != null)
     	{
     		document.forms[0].Customeridtxt.options[I + 1] = new Option(item.getElementsByTagName("weccusid")[0].firstChild.nodeValue,item.getElementsByTagName("custid")[0].firstChild.nodeValue);

        }		
    }
}
	
function findWEC() 
{
    var req = newXMLHttpRequest();
    var list = document.forms[0].EBIdtxt;	    
   	var ApplicationId = list.options[list.selectedIndex].value;
   	if (ApplicationId != "")
   	{
		 req.onreadystatechange = getReadyStateHandler(req, showWECDetails);	  
		 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
		 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		 req.send("Admin_Input_Type=getwecmaster_new&AppId="+ApplicationId);
	}
}
	 
function showWECDetails(dataXml)
{
	var cart = dataXml.getElementsByTagName("wecmaster")[0];
	var items = cart.getElementsByTagName("weccode");	
	document.forms[0].Wecidtxt.options.length = 0;
	document.forms[0].Wecidtxt.options[0] = new Option("--Make a Selection--",".");
	for (var I = 0 ; I < items.length ; I++)
   	{	   		
     	var item = items[I];
     	var nname = item.getElementsByTagName("wecid")[0].firstChild;
     	if (nname != null){
	     	document.forms[0].Wecidtxt.options[I + 1] = new Option(item.getElementsByTagName("desc")[0].firstChild.nodeValue,item.getElementsByTagName("wecid")[0].firstChild.nodeValue);   		
		}
	}
}
function loadExternal() 
{
    var state=".",site=".",substation=".",feeder=".",area=".",cust=".",eb=".", wec=".", wectype=".", comm=".", comm1=".";
    state = document.getElementById("Statetxt").value;
    area = document.getElementById("Areatxt").value;
    substation = document.getElementById("Subtxt").value;
    feeder = document.getElementById("Feedertxt").value;
    site = document.getElementById("EBSitetxt").value;
    cust = document.getElementById("Customeridtxt").value;
    eb = document.getElementById("EBIdtxt").value;
    wec = document.getElementById("Wecidtxt").value;
    wectype = document.getElementById("WECTypetxt").value;
    comm = document.getElementById("Datetxt").value; 
    comm1 = document.getElementById("ToDatetxt").value;   
    WECStatustxt = document.getElementById("WECStatustxt").value;                        
    ur="WECReportView.jsp";
 	var url=ur+"?state="+state+"&substation="+substation+"&feeder="+feeder+"&area="+area+"&site="+site+"&cust="+cust+"&eb="+eb+"&wec="+wec+"&wectype="+wectype+"&comm="+comm+"&comm1="+comm1+"&WECStatustxt="+WECStatustxt;
  	window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');  
}  
</script>
</head>
<body>
<form action="<%=request.getContextPath()%>/WECReport.do" method="post" name="WECReport" id="WECReport" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Wec Master Report</th>
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
				<td colspan="2" id="t_title">&nbsp;</td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select State:</td>
				<td valign="top">
					<select name="Statetxt" id="Statetxt" class="ctrl" onChange="findArea()">
						<option value=".">--Make a Selection--</option>
					    <%=statename%>
					</select>
				</td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select Area:</td>
				<td valign="top">
					<select name="Areatxt" id="Areatxt" class="ctrl" onChange="findSite(),findSubstation()">
						<option value=".">--Make a Selection--</option>
					    <%=areaname%>
					</select>
				</td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select Substation:</td>
				<td valign="top">
					<select name="Subtxt" id="Subtxt" class="ctrl" onChange="findFeeder()">
						<option value=".">--Make a Selection--</option>
					    <%=substationname%>
					</select>
				</td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company" width="219">Select Feeder:</td>
				<td valign="top">
					<select name="Feedertxt" id="Feedertxt" class="ctrl">
						<option value=".">--Make a Selection--</option>
					    <%=feedername%>
					</select>
				</td>
			</tr>
			<tr bgcolor="#ffffff">
			  <td id="t_general_information">Select Site:</td>
			  <td bgcolor="#ffffff" width="85%"><label>
			    <select name="EBSitetxt" id="EBSitetxt" class="ctrl" onChange="findeb(),findcust() ">
	              <option value=".">--Make a Selection--</option>
	              <%=sitename%> 
	            </select>
			  </td>
		  	</tr>
		  	<tr bgcolor="#ffffff">
				<td id="t_company">Select Customer:</td>
				<td valign="top">
				<select name="Customeridtxt" id="Customeridtxt" class="ctrl">
	              	<option value="." selected="selected">--Make a Selection--</option>
	             	<%=custid%>
	            </select></td>
			</tr>
		  	<tr bgcolor="#ffffff"> 
				<td id="t_street_address">Select EB:</td>
				<td bgcolor="#ffffff">
					<select name="EBIdtxt" id="EBIdtxt" class="ctrl" onChange="findWEC()">
	              		<option value="." selected="selected">--Make a Selection--</option>  
               			<%=ebid%>
	            	</select>
	            </td>
			</tr>
			<tr bgcolor="#ffffff">
				<td id="t_company">Select WEC:</td>
				<td valign="top">
				<select name="Wecidtxt" id="Wecidtxt" class="ctrl">
	              	<option value="." selected="selected">--Make a Selection--</option>
	             	<%=wecname%>
	            </select></td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_street_address_ln2" nowrap="nowrap">Select Wec Type:</td>
				<td><select name="WECTypetxt" id="WECTypetxt" class="ctrl">              
		              <option value=".">--Make a Selection--</option>			
		              <%=wectype %>	  
				  </select>
				</td>
			</tr>
			<tr bgcolor="#ffffff"> 
				<td id="t_street_address_ln2" nowrap="nowrap">Select Wec Status:</td>
				<td><select name="WECStatustxt" id="WECStatustxt" class="ctrl"> 
					  <option value="0">--Select--</option>             
		              <option value="1">Activated</option>
		              <option value="2">Deactivated</option>
		              <option value="3">Transfer</option>	  
				  </select>
				</td>
			</tr>
			<tr class="bgcolor"> 
				<td id="t_street_address">Commision Date:</td>
				<td class="bgcolor">
					<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" />
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECReport.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
				
					<input type="text" name="ToDatetxt" id="ToDatetxt" size="20" class="ctrl" maxlength="10"  />
					<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.WECReport.ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
	<tbody><tr>
		<td class="btn" width="100"><input type="button" name="Searchcmd" class="btnform" value="Generate" onclick="loadExternal()" /></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input type="hidden" name="Admin_Input_Type" value="WecMaster" />		
			<input name="Reset" value="Cancel" class="btnform" type="reset">
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table></form>
</body>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>