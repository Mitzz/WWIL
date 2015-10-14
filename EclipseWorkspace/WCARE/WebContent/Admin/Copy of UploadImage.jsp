<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validate.js"></script>
</head>
<body onLoad="findApplication()">
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="400">
	<tbody><tr>
	<td class="newhead1"></td>
	<th class="headtext">Image Upload</th>
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
			<td id="t_street_address" width="180px">&nbsp;Uload Image:</td>
			<td class="bgcolor" width="180px">
			<form id="form1" enctype="multipart/form-data" action="<%=request.getContextPath()%>/UploadImage" name="UploadImage" id="UploadImage" method="post">
			
                <input type="file" id="NewsTitletxt" name="NewsTitletxt" class="ctrl" >
			    <textarea rows="3" cols="39" id="NewsDescriptiontxt" name="NewsDescriptiontxt" class="ctrl" ></textarea>
			    <input name="Submit" type="submit" class="btnform" value="Submit"/>
			    </form>
			</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Image Description:</td>
			<td class="bgcolor"><textarea rows="3" cols="39" id="NewsDescriptiontxt" name="NewsDescriptiontxt" class="ctrl" ></textarea></td>
		</tr>	
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;From&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="FromDatetxt" name="FromDatetxt" class="ctrl" >
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.UploadImage.FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
		</td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;To&nbsp;Date:</td>
			<td class="bgcolor"><input type="text" id="ToDatetxt" name="ToDatetxt" class="ctrl" >
		<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.UploadImage.ToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
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
		</tbody></table>
		
		</td></tr></tbody></table>
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
		
			
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>

</td>		
</tr>
<tr>
	<td align="center">
		<table border="0" cellpadding="0" cellspacing="0" width="650"><tbody>
			<tr>
				<td >
					<div id="imagedetails">
					
					</div>	
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