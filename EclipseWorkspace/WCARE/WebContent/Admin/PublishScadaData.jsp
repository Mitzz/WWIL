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
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<%
String si = "";
String dd = "";
int pb = 1;
String tp = "";
//session.removeAttribute("dynabean");
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){		
	dd = dynabean.getProperty("Datetxt").toString();
	si = dynabean.getProperty("SiteIdtxt").toString();
	tp = dynabean.getProperty("Typetxt").toString();	
	pb = Integer.parseInt(dynabean.getProperty("Optiontxt").toString());
	session.removeAttribute("dynabean");
}
%>
<% 
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String roleid=session.getAttribute("RoleID").toString();
String sSite="";

if(roleid.equals("0000000001"))
     sSite = AdminUtil.fillMaster("VIEW_SITE_MASTER",sSite);
else
	sSite = AdminUtil.fillWhereMaster("TBL_STATE_SITE_RIGHTS",si,userid);

%>
<script type="text/javascript">
function findvalidate() 
{
	var blnSave=false;
     blnSave = validateForm('Site',document.forms[0].SiteIdtxt.value,'M','',
                            'Date',document.forms[0].Datetxt.value,'M','');
     if ( blnSave == true ) {
     	blnSave = futuredate();
		if (blnSave == true)
		{
			return true;
		}else
			return false;
     } else {
       return false;
     }
}
function futuredate()
{
	 var todate=new Date(); //yyyy-mm-dd
   	 var fromdate = new Date(document.getElementById("Datetxt").value.substr(6,4),document.getElementById("Datetxt").value.substr(3,2) -1,document.getElementById("Datetxt").value.substr(0,2));
	 var one_day=1000*60*60*24;	
     var days = (Math.ceil((todate.getTime()-fromdate.getTime())/one_day));	
     if(days<=0)
  	 {
	  	 alert("Future date record are not allowed!");
	  	 return false;
  	 }else
  	 	return true;
}
</script>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tr width="100%">
<td width="100%" align="center">
<form action="<%=request.getContextPath()%>/PublishScadaData.do" method="post" name="PublishScadaData" onSubmit="return findvalidate()">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="500">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Publish Data</th>
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
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Site:</td>
			<td class="bgcolor">
				<select size="1" id="SiteIdtxt" name="SiteIdtxt" class="ctrl">
		            <option value="">--Make a Selection--</option>
		            <%=sSite %>
		        </select>
			</td>
		</tr>				
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Date:</td>
			<td class="bgcolor">
				<input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value="<%=dd %>" onfocus="dc.focus()" />
				<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.PublishScadaData.Datetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Criteria:</td>
			<td class="bgcolor">
				<select size="1" id="Typetxt" name="Typetxt" class="ctrl">
					<option value="BOTH">BOTH</option>
					<%
					if (tp == "EB")
					{ %>
		            	<option value="EB" selected>EB</option>
		            <%}else{ %>
		            	<option value="EB">EB</option>
		            <%}if (tp == "WEC")
					{ %>
		            	<option value="WEC" selected>WEC</option>
		            <%}else{ %>	
		            	<option value="WEC">WEC</option>
		            <%}%>		            
		        </select>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Option:</td>
			<td class="bgcolor">
				<select size="1" id="Optiontxt" name="Optiontxt" class="ctrl">
					<%
					if (pb == 1)
					{ %>
		            	<option value="1" selected>Publish</option>
		            <%}else{ %>
		            	<option value="1">Publish</option>
		            <%}if (pb == 0)
					{ %>
		            	<option value="0" selected>Un-Publish</option>
		            <%}else{ %>	
		            	<option value="0">Un-Publish</option>
		            <%}%>
		        </select>
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
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="submit" name="Submitcmd" class="btnform" value="Submit" />
			<input type="hidden" name="Admin_Input_Type" value="PublishScadaData" />
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
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