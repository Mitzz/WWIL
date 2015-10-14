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


<STYLE type=text/css>BODY {
	PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 0.9em; PADDING-BOTTOM: 0px; MARGIN: 0px; COLOR: #000; PADDING-TOP: 0px; FONT-FAMILY: sans-serif,helvetica; BACKGROUND-COLOR: #fff
}
H1 {
	PADDING-RIGHT: 2em; PADDING-LEFT: 2em; FONT-WEIGHT: normal; FONT-SIZE: 1.75em; PADDING-BOTTOM: 0.4em; MARGIN: 0px; COLOR: #fff; PADDING-TOP: 0.6em; BORDER-BOTTOM: #000 2px solid; BACKGROUND-COLOR: #0a4224; TEXT-ALIGN: center
}
H2 {
	FONT-WEIGHT: normal; FONT-SIZE: 1.3em; MARGIN-LEFT: 10px; COLOR: #009
}
H3 {
	FONT-WEIGHT: bold; FONT-SIZE: 1.3em; MARGIN-LEFT: 20px; FONT-FAMILY: monospace
}
P {
	MARGIN: 10px
}
TABLE {
	MARGIN: 10px
}
TD {
	FONT-SIZE: 0.9em
}
.bold {
	FONT-WEIGHT: bold
}
</STYLE>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<SCRIPT language=JavaScript1.1>

function testPassword(passwd)
{

var description = new Array();
description[0] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=30 bgcolor=#ff0000></td><td height=15 width=120 bgcolor=#dddddd></td></tr></table></td><td class=bold>Weakest</td></tr></table>";
description[1] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=60 bgcolor=#bb0000></td><td height=15 width=90 bgcolor=#dddddd></td></tr></table></td><td class=bold>Weak</td></tr></table>";
description[2] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=90 bgcolor=#ff9900></td><td height=15 width=60 bgcolor=#dddddd></td></tr></table></td><td class=bold>Medium</td></tr></table>";
description[3] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=120 bgcolor=#00bb00></td><td height=15 width=30 bgcolor=#dddddd></td></tr></table></td><td class=bold>Strong</td></tr></table>";
description[4] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=150 bgcolor=#00ee00></td></tr></table></td><td class=bold>Strongest</td></tr></table>";
description[5] = "<table border=0 cellpadding=0 cellspacing=0><tr><td class=bold width=100>Strength:</td><td><table cellpadding=0 cellspacing=2><tr><td height=15 width=150 bgcolor=#dddddd></td></tr></table></td><td class=bold>Begin Typing</td></tr></table>";

		var intScore   = 0
		var strVerdict = 0
		
		// PASSWORD LENGTH
		if (passwd.length==0 || !passwd.length)                         // length 0
		{
			intScore = -1
		}
		else if (passwd.length>0 && passwd.length<5) // length between 1 and 4
		{
			intScore = (intScore+5)
		}
		else if (passwd.length>4 && passwd.length<8) // length between 5 and 8
		{
			intScore = (intScore+8)
		}
		else if (passwd.length>7 && passwd.length<10)// length between 7 and 10
		{
			intScore = (intScore+12)
		}
		else
		{
		intScore = (intScore+13)					// length 10
		}
		
		
		
		
		if (passwd.match(/[a-z]/))                              // [verified] at least one lower case letter
		{
			intScore = (intScore+1)
		}
		
		if (passwd.match(/[A-Z]/))                              // [verified] at least one upper case letter
		{
			intScore = (intScore+5)
		}
		
		// NUMBERS
		if (passwd.match(/\d+/))                                 // [verified] at least one number
		{
			intScore = (intScore+5)
		}
		
		if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/))             // [verified] at least three numbers
		{
			intScore = (intScore+5)
		}
		
		
		// SPECIAL CHAR
		if (passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/))            // [verified] at least one special character
		{
			intScore = (intScore+5)
		}
		
																 // [verified] at least two special characters
		if (passwd.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/))
		{
			intScore = (intScore+5)
		}
	
		
		// COMBOS
		if (passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))        // [verified] both upper and lower case
		{
			intScore = (intScore+2)
		}

		if (passwd.match(/(\d.*\D)|(\D.*\d)/))                    // [FAILED] both letters and numbers, almost works because an additional character is required
		{
			intScore = (intScore+2)
		}
 
																  // [verified] letters, numbers, and special characters
		if (passwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/))
		{
			intScore = (intScore+2)
		}
	
	
		if(intScore == -1)
		{
		   strVerdict = description[5];
		}
		else if(intScore > -1 && intScore < 12)
		{
		   strVerdict = description[0];
		}
		else if (intScore > 11&& intScore < 22)
		{
		   strVerdict = description[1];
		}
		else if (intScore > 21 && intScore < 32)
		{
		   strVerdict = description[2];
		}
		else if (intScore > 31 && intScore < 42)
		{
		   strVerdict = description[3];
		}
		else
		{
		   strVerdict = description[4];
		}
	
	document.getElementById("Words").innerHTML= (strVerdict);
	
}
// End-->



function validatePwd() {
var invalid = " "; // Invalid character is a space
var minLength = 6; // Minimum length
var pw1 = document.forms[0].NPasswordtxt.value;
var pw2 = document.forms[0].VPasswordtxt.value;
var pw3 = document.forms[0].OPasswordtxt.value;

// check for a value in both fields.
if (pw1 == '' || pw2 == '') {
alert('Please enter your password twice.');
return false;
}
 if (pw1 == pw3) {
alert ("You  enter the same new password as old one.");
return false;
}
// check for minimum length
if (document.forms[0].NPasswordtxt.value.length < minLength) {
alert('Your password must be at least ' + minLength + ' characters long. Try again.');
return false;
}
// check for spaces
if (document.forms[0].NPasswordtxt.value.indexOf(invalid) > -1) {
alert("Sorry, spaces are not allowed.");
return false;
}

if (pw1 != pw2) {
alert ("You did not enter the same new password twice. Please re-enter your password.");
return false;
}

else {

return true;
      }
   }
</SCRIPT>
<%
String pwd="";
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
if (session.getAttribute("pwd") == null)
{
	 response.sendRedirect(request.getContextPath());
}
else
{
pwd=session.getAttribute("pwd").toString();
}
%>










</head>
<body>
<BODY>
<H1>This Is Time To Change Your Password</H1>
<H2>Due To some security reasons,System has force you to change your password,</H2>
<P>After successfully changing password <BR>System
automatically proceed your login to home page. <BR>
This is suggestion to all users, <br>Do change your passwords on a regular basis (within 30 days period).
 </P>

<form action="<%=request.getContextPath()%>/forcechangepassword.do" method="post" name="forcechangepassword" onSubmit="return validatePwd()">

<TABLE cellSpacing=0 cellPadding=0 border="0">
  <TBODY>
  <TR>
    <TD width=100>&nbsp;</TD>
    <TD>
      <TABLE cellSpacing=0 cellPadding=0 border="1">
        <TBODY>
        <TR>
          <TD>
            <TABLE cellSpacing=0 cellPadding=0 border=0>
              <TBODY>
              <TR>
                <TD class=bold width=100></TD>
                <TD>
                  <TABLE>
                    <TBODY>
                    <TR>
                      <TD>
                      
                      
                      
				
		<tr class="bgcolor"> 
			<td id="t_street_address" width="350px"></td>
			<td class="bgcolor" width="400px" colspan="2"><input type=hidden name="OPasswordtxt" size='10' maxlength="15" class="ctrl" value="<%=pwd%>" /> </td>
		</tr>
		<tr class="bold"> 
			<td id="t_street_address">&nbsp;New&nbsp;Password:</td>
			<td class=bold  width="100px"><input type=password name="NPasswordtxt" size='15' maxlength="10" class="ctrl"  onkeyup="testPassword(this.value)" />  </td>
		     <td class="bgcolor" width="300px"><A id=Words>
            <TABLE cellSpacing=0 cellPadding=0 border=0>
              <TBODY>
              <TR>
                <TD class=bold width=100>Strength:</TD>
                <TD>
                  <TABLE cellSpacing=2 cellPadding=0>
                    <TBODY>
                    <TR>
                      <TD width=150 bgColor="#dddddd"
                  height=15></TD></TR></TBODY></TABLE></TD>
                <TD class=bold>Begin 
           Typing</TD></TR></TBODY></TABLE>
           </A>
           </TD>
        </tr>
		<tr class="bold"> 
			<td id="t_street_address">&nbsp;Verify&nbsp;Password:</td>
			<td class="bgcolor" colspan="2"><input type=password name="VPasswordtxt" size='15' maxlength="10" class="ctrl" /></td>
		</tr>	
		
		<tr class="bgcolor" > 
			<td id="t_street_address"  width="750px" align="center" colspan="3">
			<input type="hidden" name="Admin_Input_Type" value="ChangePassword" />
			<input name="Submit" type="submit" class="btnform" value="Proceed To Home Page"/></td>
		</tr>
		<tr> 
			<td colspan="3">
			<font color="red">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>
				</font>
			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
			</td>
	<td class="newheadr"></td>
	</tr>

</tbody></table>
</TD>
</TR>
</TBODY>
</TABLE>

</form>
<H1></H1>
</body>
</html>