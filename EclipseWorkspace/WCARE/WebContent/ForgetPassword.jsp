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


function echeck(str) {

		var at="@";
		var dot=".";
		var lat=str.indexOf(at);
		var lstr=str.length;
		var ldot=str.indexOf(dot);
		if (str.indexOf(at)==-1){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(at)==-1 || str.indexOf(at)==0 || str.indexOf(at)==lstr){
		   alert("Invalid E-mail ID");
		   return false;
		}

		if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 || str.indexOf(dot)==lstr){
		    alert("Invalid E-mail ID");
		    return false;
		}

		 if (str.indexOf(at,(lat+1))!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.substring(lat-1,lat)==dot || str.substring(lat+1,lat+2)==dot){
		    alert("Invalid E-mail ID");
		    return false;
		 }

		 if (str.indexOf(dot,(lat+2))==-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }
		
		 if (str.indexOf(" ")!=-1){
		    alert("Invalid E-mail ID");
		    return false;
		 }

 		 return true;					
	}



function checkWholeForm() {
    	var why = "";
    	
    	var emailID=document.forms[0].VEmailId;
        why +=checkUsername(document.forms[0].VUserId,"Login id");
     
     	
    if (why != "") 
    {
       alert(why);
       return false;
    }
    else
    {
    
    if (echeck(emailID.value)==false)
    {
		emailID.value="";
		emailID.focus();
		return false;
	}
    }
}

function checkUsername (strng,msg) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = msg+" is Required,You didn't Enter in text field\n";
 		}
 		return error;
 }
 </SCRIPT>

</head>
<body>
<BODY>
<H1>Forgot Password</H1>
<H2>Submit the following mandatory details and very soon you will get a mail that containing your credential details…</H2>

<form action="ForgetPasswordRetrieved.jsp" method="post">	   

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
                      
 		<tr class="bold"> 
			<td id="t_street_address" width="200px">&nbsp;Your Ecare User Id:</td>
			<td class=bold  width="100px"><input type=text name="VUserId" size='25' maxlength="10" class="ctrl" />  </td>
		    <td class="bgcolor" width="300px"><A id=Words>
            </A>
           </TD>
        </tr>
		<tr class="bold"> 
			<td id="t_street_address" width="200px">Registered Email Id:</td>
			<td class="bgcolor" colspan="2"><input type=text name="VEmailId" size='25' class="ctrl" /></td>
		</tr>	
		
		<tr class="bgcolor" > 
			<td id="t_street_address"  width="570px" align="center" colspan="3">
			<input type="hidden" name="Admin_Input_Type" value="ChangePassword" />
			<input name="Submit" type="submit" class="btnform" value="Submit"  onClick="return checkWholeForm()" /></td>
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