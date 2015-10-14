<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<html>

    
   <head>
   <link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/qna1.css" type="text/css">
   
   <style type="text/css">
<!--
.style6 {color: #800000; font-weight: bold;font-size: 14px }
.style7 {color: #800000; font-size: 14px}
.style10 {
	color: #FF0000;
	font-size: 18px
}
.style12 {color: #008000;font-size: 14px}
.style14 {color: #008000; font-weight: bold; font-size: 14px}
.style01 {color: #800000; font-size: 12px }
.style16 {color: #800000; }
-->
</style>
   </head> 
 <body>
<div align="center">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="800">
<tbody><tr>
	<td class="newhead1" width="13"></td>
	<th class="headtext" width="287">Thanks For Your Support</th>
	<td width="466"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="14">&nbsp;</td>
	<td class="newhead4" width="20"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td width="13" height="238" background="<%=request.getContextPath()%>/resources/images/line_l.gif">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td height="27" bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="800"  bgcolor=#CEE7B6>
		<tbody>
		<tr >
			<td colspan="4">						</td>
					</tr>
		<tr >
			<td colspan="4">						</td>	</tr>
		<tr >
		  <td height="9" colspan="4"></td>
		  </tr>
		<tr >
		  <td height="9" colspan="4" class="style6"> &nbsp;&nbsp;Dear Customers, </td>
		  </tr>
		<tr >
		  <td height="9" colspan="4"></td>
		  </tr>
		<tr>
		  <td height="9" colspan="4" class="style14">&nbsp;&nbsp;The information Enetered/Updated by You have been stored.Thanks for your Support. </td>
		  </tr>
		<tr >
		  <td height="7" colspan="4"></td>
		  </tr>
		 
		<tr >
		  <td height="31" colspan="4" class="style14">&nbsp;&nbsp;Your Login will be activated within one working days. </td>
		  </tr>
		<tr >
		  <td height="31" colspan="4" class="style14">&nbsp;</td>
		  </tr>
		<tr >
		  <td height="9" colspan="4">
		  
		   <p class="style6">&ldquo;ALWASYS AT YOUR  SERVICE&rdquo; </p>
                        <p class="style6">(TEAM - CUSTOMER SUPPORT)</p>		  </td>
		  </tr>
		<tr > 
			<td height="9" colspan="4"></td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="20">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	</tr>
<tr>
	<td width="13"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		
		
		<td width="100" colspn="4">&nbsp;</td>
		
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>

<table border="0" align="center" cellpadding="0"  cellspacing="0" width="400">
<tbody><tr>		
				<td align="center">
					
					<div id="statedetails">	</div>	
				</td>
			</tr>
	</tbody>
  </table>	

</div>
<%
    session.removeAttribute("loginID");		
    request.getSession(false).invalidate();
	//response.sendRedirect(request.getContextPath());
%>
   </body>
</html>