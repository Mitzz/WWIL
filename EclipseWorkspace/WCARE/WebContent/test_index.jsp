<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.security.utils.SecurityUtils"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.ObjectOutputStream"%>
<%@ page import = "java.io.*" %>

<html>
<head>
<title>ECARE (Customer Portal)@Enercon</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/qna1.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/resources/js/leftmenu.js'></script>
<script type="text/javascript" src='<%=request.getContextPath()%>/resources/js/scroll.js'></script>
<script type="text/javascript">  
  

<!-- Begin
function popUp(URL) {
day = new Date();
id = day.getTime();
eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=500,height=300,left = 262,top = 234');");
}
function popUp1(URL) {
day = new Date();
id = day.getTime();
eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=1,scrollbars=1,location=0,statusbar=0,menbar=1,resizable=1,width=800,height=500,left = 262,top = 234');");
}
// End -->

 
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

<%
	String ERROR_MSG = "";
	if (session != null) {
		if (session.getAttribute("valid") == null) {
		} else {
			if (session.getAttribute("valid").equals("false")) {
		if (session.getAttribute("ERROR_MSG") == null) {
			ERROR_MSG = "Invalid Username/Password. Try Again.";
		} else {
			ERROR_MSG = session.getAttribute("ERROR_MSG")
			.toString();
		}
		session.setAttribute("valid", "true");
			}
		}
	} else
		ERROR_MSG = "Session has been TIMED OUT";
	/* This code is put for test purpose, do not remove */
	//System.out.println("Client IP Addr: " + request.getRemoteAddr());
	////System.out.println("Client IP Host: " + request.getRemoteHost());
	/*  End of Test Code  */
%>

<link href="resources/css/form.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style6 {color: #800000; font-weight: bold;font-size: 12px }
.style61 {color: #000066; font-weight: bold;font-size: 12px }
.style7 {color: #800000; font-size: 12px}
.style10 {
	color: #FF0000;
	font-size: 18px
}
.style12 {color: #008000;font-size: 12px}
.style14 {color: #008000; font-weight: bold; font-size: 12px}
.style01 {color: #800000; font-size: 12px }
.style16 {color: #800000; }
.style160 {color: #FFFFFF;font-weight: bold; }
.style17 {color: #008000}
.style18 {font-size: 18px}
.style180 {font-size: 14px}
.style19 {color: #800000; font-weight: bold; }
-->
</style>
</HEAD>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"
	onLoad="javascript:document.forms[0].sLoginID.focus();">

<table width="884" border="1" cellspacing="0" cellpadding="0" align="center">
	<tr>
		<td>
		<table border="0" width="881" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
			<tr>
				<td colspan="2" bgcolor="#0a4224" align="right" height="27" class="WhiteLink"></td>
			</tr>
			<tr>
				<td width="246" align="left"><img src="resources/images/Enercon_Logo.GIF"></td>
				<td width="627" align="right"><img src="resources/images/EnergyForWorld.jpg"></td>
			<tr>
		</table>
		</td>
	</tr>

	<tr>
		<td width="974" height="436">
		<TABLE cellSpacing=0 cellPadding=0 width=882 border=0 class="border1 fntAr f11">
			<TBODY>
				<TR>
					<TD vAlign=top width=400 height="428"><img height=150 alt="" src="resources/images/homepage_image.jpg"
						width=400 border=0 name=homeImg>
					<FORM name="frmHome" action="SecurityServlet" METHOD="POST" onSubmit="return gotoSave()" target="_parent">
					<p></p>
					<p></p>
					<table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">
						<tbody>
							<tr>
								<td class="newhead1"></td>
								<th class="headtext">Its Time to Login.....</th>
								<td><img src="resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
								<td class="newhead3">&nbsp;</td>
								<td class="newhead4"><img src="resources/images/pixel.gif" border="0" height="1" width="10"></td>
							</tr>
							<tr>
								<td class="newheadl"><img src="resources/images/pixel.gif" border="0"></td>
								<td colspan="3"><img src="resources/images/pixel.gif" border="0" height="10" width="1"><br>
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tbody>
										<tr>
											<td bgcolor="#dbeaf5">
											<table border="0" cellpadding="2" cellspacing="1" class="border1 fntAr f11" width="100%">
												<tbody>
													<tr class="bgcolor">
														<td id="t_street_address" width="50%">&nbsp;User&nbsp;Id:</td>
														<td class="bgcolor" width="50%"><input type=text name="sLoginID" size='19' class="ctrl"
															maxlength="10"></td>
													</tr>
													<tr class="bgcolor">
														<td id="t_street_address">&nbsp;Password:</td>
														<td class="bgcolor"><input type=password name="sPassword" size='19' class="ctrl" maxlength="10">
														</td>
													</tr>
													<tr class="bgcolor">
														<td colspan="2">
														<%
														if (session.getAttribute("ERROR_MSG") != null) {
														%> <%=session.getAttribute("ERROR_MSG")
														
														
														
														%> <%
 	session.setAttribute("ERROR_MSG", "");
 	}
 %>
														</td>
													</tr>
												</tbody>
											</table>
											</td>
										</tr>
									</tbody>
								</table>
								<img src="resources/images/pixel.gif" border="0" height="10" width="1"><br>
								</td>
								<td class="newheadr"><img src="resources/images/pixel.gif" border="0"></td>
							</tr>
							<tr>
								<td width="10"><img src="resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
								<td colspan="4" align="right" bgcolor="#006633">
								<table border="0" cellpadding="0" cellspacing="0">
									<tbody>
										<tr>
											<td width="100"><input type="submit" name="Searchcmd" class="btnform" value="Login" /></td>
											<td width="1"><img src="resources/images/pixel.gif" border="0" height="18" width="1"></td>
											<td width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
											<td width="1"><img src="resources/images/pixel.gif" border="0" height="18" width="1"></td>
										</tr>
									</tbody>
								</table>
								</td>
							</tr>
						</tbody>
					</table>
					<center><a href="ForgetPassword.jsp"><u><font size="2"><B>Click here</B> </font></u></a> <font size="2"> <B>if you forgot your password.</B></font></center>
					
					</FORM>
					<table>
					<tr>
										<td class="style6">&nbsp;&nbsp;Click And View The Details: </td>
									</tr>

									<tr>
										<td class="style180">
										<ol>
										
										<li  class="style180"><A HREF="javascript:onClick=popUp1('WindAward2008.pdf')"  class="style180">WIND AWARD 2008</a></li>
											<li  class="style180"><A HREF="javascript:onClick=popUp1('UserManual.pdf')"  class="style180">User Manual</a></li>
										
											<li  class="style180"><A HREF="javascript:onClick=popUp('Navigational.jsp')"  class="style180">Portal Navigation Steps</a> 
											</li>
											<li  class="style180"><A HREF="javascript:onClick=popUp('Portal.jsp')"  class="style180">Portal Features</a></li>
											<LI><A HREF="javascript:onClick=popUp1('InitialLetter.pdf')"  class="style180">Letter For You</a></li>
											



										</ol>
										</td>
									</tr></table>
					</TD>
					<TD vAlign=top width=574 height="428">
					<TABLE cellSpacing=0 cellPadding=0 width=482 border=0>
						<TBODY>
							<TR>
								<TD width=1></TD>
								<TD width=532>
								<p class="style10"><span class="style17">&nbsp; </span></p>
								<p class="style10"><span class="style17">Dear Customers,</span></p>
								</TD>
								<TD width=1></TD>
							</TR>
							<TR rowspan=4>
								<TD width=1></TD>
								<TD class="f12 fntAr" colspan="4">
								<table width="481" border="0">
									<tr>
										<td width="567">
										<div align="justify">
										<p><span class="style6"><span class="style12">In our continuous endeavor to improve the
										quality of the service to our esteemed customers, Enercon has come up with &ldquo;<span class="style12">E-CARE</span>&rdquo;<span
											class="style14"></span>, a new Internet CRM portal for the Customer. </span></span><br><BR>
										<span class="style14">Different requirements collected through our Customer Feedback Surveys have been
										put together in this new customer portal. It is user friendly &amp; easy to navigate with host of new options
										for you.</span></p>
										</div>
										</td>
									</tr>
									<tr>
										<td class="style6">&nbsp;&nbsp; </td>
									</tr>
									<% 
									   //ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
								       //ObjectOutputStream oos = new ObjectOutputStream(bos); 
								       
									   AdminDao ado = new AdminDao(); 
									   //List tranList = new ArrayList();	
									   
									   String imageDesc = "";
									   imageDesc = ado.getImageDesc();
									   
									   ///byte [] image = null;
									  // image = ado.imageDetailsDisplay(); 
									  // out.clear();
								      // OutputStream os = response.getOutputStream();
								       //os.write(image);
								       //out.flush(); 
									   		//if(	tranList.size()>0)
									   		//{									          
								 		      //image = tranList.get(0);//.toByteArray();
								 		      //oos.writeObject(image);
								 		      //imgData=bos.toByteArray();								 		      
								 		      //System.out.println("VVVVVVVVVVVVVVVVVVV"+image);
								 		   // }
									   
									   
								 	 %>	
								

									<tr>
										<td><p class="style10"><span class="style17"><%=imageDesc%></span></p></td>
									</tr>
									<tr>
										<td class="style180" align="center">
										<img src="<%=request.getContextPath()%>/image.jsp" align="center" WIDTH="350" border="0">
										<!-- <img src="00.jpg" align="center" WIDTH="350" border="0">  -->
										</td>
									</tr>
								
									<tr>
										<td class="style19" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"ALWAYS AT YOUR SERVICE"</td>
									</tr>
									<tr>
										<td class="style19">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ENERCON (INDIA) LTD</td>
									</tr>
									<tr>
										<td class="style19">&nbsp;</td>
									</tr>
									<tr>
									<tr>
										<td class="style6" colspan="4">
										</td>
									</tr>
									<tr>
										<td class="style19"></td>
									</tr>
									
									<tr>
										<td class="style17" colspan="4">
									</td>
									</tr>
									
									<tr>
						 			<td align="right">
						 			</tr>
								</table>
								</TD>
							</TR>
							

						</TBODY>
					</TABLE>
					</TD>
				
                 
			</TBODY>
			
							
			<TABLE cellSpacing=0 cellPadding=0 width=882 border=0>
				<TBODY>
					
					<TR>
						<TD height=2></TD>
					</TR>
					<tr>
						<td bgcolor="#0a4224" align="right" class="whiteNormal2" ><%@include file="commBottomPanel.jsp"%></td>
					</tr>
					<TR>
						<TD height=2></TD>
					</TR>
					<TR>
						<TD align=center bgcolor="#0a4224" ><SPAN class="style160" >
						</span></TD>
					</TR>
				</TBODY>
			</TABLE>
		
</table>
</body>

</html>
