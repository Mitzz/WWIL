<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../resources/css/css.css" type="text/css">
<link rel="stylesheet" href="../resources/css/Enercon.css" type="text/css">
<script type="text/javascript" src="../resources/js/validateForm.js"></script>
<script type="text/javascript" src="../resources/js/leftmenu.js"></script>
<script type="text/javascript" src="../resources/js/scroll.js"></script>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
%>
<script type="text/javascript">
function gotoSave() 
{
     var blnSave=false;
     blnSave = validateForm('Login ID',document.forms[0].sLoginID.value,'M','',
                            'Password',document.forms[0].sPassword.value,'M',''                            
                            );
      if ( blnSave == true ) {
         return true;
      } else {
         return false;
      }
	}
</script>
</HEAD>

<body bgcolor="#64747B" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:document.forms[0].sLoginID.focus();">
<%
String msg="";
msg = (String)session.getAttribute("msg");
if(msg==null || msg=="")
{
%>
	 <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111"  width="100%" height="25">
    	<tr>
      	<td width="33%" align="center" height="5">
      	<STRONG><FONT color="Red" size="5"></FONT></STRONG>
      </tr>
   </table>
   <%
   }
   else
   {
   %>
   <table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111"  width="100%" height="25">
    	<tr>
      	<td width="33%" align="center" height="5">
	        <STRONG><FONT color="Red" size="2"><%= msg%></FONT></STRONG>
  	</tr>
   </table>
   <%
   }
   %>
<%//System.out.println(request.getContextPath()); %>
<map name="Map2"> 
    <area shape="rect" coords="7,10,301,99" href="#">
</map>
<table width="213" border="0" cellspacing="0" background="../resources/images/yourcredentials.gif" cellpadding="0" height="175">
	<tr rowspan="2"> 
		<td width="213" valign="top">			
        	<FORM name="frmHome" action="<%=request.getContextPath()%>/AdminServlet"  METHOD="POST" onSubmit="return gotoSave()" target="_parent" >
                 <table width="213" border="0" cellspacing="0"   background="../resources/images/yourcredentials1.jpg" cellpadding="0" height="558" >
                 	
                    <tr> 
                    	<td height="20" colspan=2 align=center class="bluehighlight1" >It's time to Login... <font size="3" class="text3 style2">&nbsp; </font> </td>
                    </tr>
                    <tr> 
                      	<td height="18" colspan="2" >&nbsp; </td>
                    </tr>
                    <tr> 
	                    <td width="108" height="22" align=right ><font size="2" class="blue_text">Login:</font></td>
	                    <td width="101" height="22">&nbsp;<input type=text name="sLoginID" size='15' class="TextBox"></td>
                    </tr>
                    <tr> 
                      <td width="108" height="22"align=right ><font size="2" class="blue_text">Password:</font> </td>
                      <td width="101" height="22">&nbsp;<input type=password name="sPassword" size='15' class="TextBox">
                      <input type="hidden" name="Admin_Input_Type" value="AdminLogin"/>
                    </td>                   
                    </tr>
                    <tr> 
                      <td width="108" height="22">&nbsp;</td>
                      <td width="101" height="22">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="Submit" type="submit" class="button1" value="Submit"/></td>
                    </tr>
                    <tr> 
                      <td colspan="2" align="center"><br />                    
                    </tr>
                    <tr> 
                      <td width="108" height="411">&nbsp;</td>
                      <td width="101" height="411">&nbsp;</td>
                    </tr>
                 </table>         
            </form>
        </td>
    </tr>
</table>
</body>
</html>
