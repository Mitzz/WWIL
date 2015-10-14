<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.*" %>

<html>
<head>
<title>ECARE</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Enercon.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/leftmenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/scroll.js"></script>
<script type="text/javascript">var tWorkPath="<%=request.getContextPath()%>/resources/menu/";</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/menu/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/menu/dtabs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/floating-window.css" media="screen" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxfloat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/floating-window.js">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tsbs.js"></script>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>
<script type="text/javascript">

    
  function xmlhttpPost(strURL) {
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
  
  
  
    self.xmlHttpReq.open('POST', strURL, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
        if (self.xmlHttpReq.readyState == 4) {
            updatepage(self.xmlHttpReq.responsetext);
        }
    }
    self.xmlHttpReq.send(null);
}

function getquerystring() {
    var form = document.forms['f1'];
    var word = form.word.value;
    qstr = 'w=' + escape(word);  // NOTE: no '?' before querystring
    return qstr;
}

function updatepage(str){
    document.getElementById("rig").innerHTML = str;
    document.write(str)
}
  
  function goToSecServlet(menuValue){
          document.forms[0].Menu_Input_Type.value = menuValue;
          document.forms[0].action = "SecurityServlet";
          document.forms[0].submit();
        }
        
        function goToAdminServlet(menuValue){
          document.forms[0].Admin_Input_Type.value = menuValue;
          document.forms[0].action = "RegisterReq";
          document.forms[0].submit();
        }
        
         //  This Javascript Code ensures that right-click is disabled on the page.
      var message="";
    
      function clickIE() {
          if (document.all) {
            (message);
            return false;
          }
      }
      
      function clickNS(e) {
        if  (document.layers||(document.getElementById&&!document.all)) {
          if (e.which==2||e.which==3) {
            (message);
            return false;
          }
        }
      }
      
      if (document.layers) {
        document.captureEvents(Event.MOUSEDOWN);
        document.onmousedown=clickNS;
      } else { 
        document.onmouseup=clickNS;
        document.oncontextmenu=clickIE;
      }      
      document.oncontextmenu=new Function("return false")  
      
  function callHelloWorld(bb) {     
    	 var cc=bb;    
    	
    		 if(cc==3){
   				 	document.getElementById(3).style["display"] = "block";
				    document.getElementById(3).style["visibility"] = "visible";
				    document.getElementById(2).style["display"] = "none";
				    document.getElementById(2).style["visibility"] = 'hidden';
				    document.getElementById(1).style["display"] = "none";
				    document.getElementById(1).style["visibility"] = 'hidden';
   
    		}if (cc==2){
				     document.getElementById(2).style["display"] = "block";
				     document.getElementById(2).style["visibility"] = 'visible';
				     document.getElementById(1).style["display"] = "none";
				     document.getElementById(1).style["visibility"] = 'hidden';
				     document.getElementById(3).style["display"] = "none";
				     document.getElementById(3).style["visibility"] = "hidden";
		    } if(cc==1){
			    	document.getElementById(1).style["display"] = "block";
			      	document.getElementById(1).style["visibility"] = 'visible';
			       	document.getElementById(2).style["visibility"] = 'hidden';
			      	document.getElementById(2).style["display"] = "none";
			        document.getElementById(3).style["display"] = "none";
			   		document.getElementById(3).style["visibility"] = "hidden";
   			 }if(cc==4){
				     document.getElementById(4).style["visibility"] = 'hidden';
				     document.getElementById(4).style["display"] = "none";
				     window.callHelloWorld('1');
			}
	}
	
  </script>
  <script type="text/javascript">
// Setting initial size of windows
// These values could be overridden by cookies.
windowSizeArray[1] = [300,200];	// Size of first window
windowPositionArray[1] = [500,200]; // X and Y position of first window
windowSizeArray[2] = [150,250];	// Size of second window
windowPositionArray[2] = [500,300]; // X and Y position of first window
windowSizeArray[3] = [140,100];	// Size of third window
windowPositionArray[3] = [50,500]; // X and Y position of first window

</script>

</head>
 <center>
<body   valign="top"  align="center"  bgcolor="#FFFFFF" >

<table width="1000" border="1" cellspacing="0" cellpadding="0" valign="top" align="center">
<tr>
	<td colspan="2">
		<table border="0" width="100%" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
			<tr>
				<td  bgcolor="#0a4224" align="left" height="27" width="30%" class="WhiteLink"> 
						<%
						GregorianCalendar calendar = new GregorianCalendar();
						
						
					    String sDayTime="";
					    String sAmPm;
					    int hour = 0;
					    hour = calendar.get(Calendar.HOUR_OF_DAY);
					    if(calendar.get(Calendar.AM_PM)==0)
					    	sAmPm = "AM";
					    else
					    	sAmPm = "PM";					    	
					    if((hour<12 )&& sAmPm =="AM")
					      	sDayTime = "Good Morning!  ";
					    else if((hour >=12 && hour <=15) && sAmPm == "PM")
					    	sDayTime = "Good Afternoon!  ";
					    else if((hour >15 && hour <19) && sAmPm == "PM")
					    	sDayTime = "Good Evening!  ";
					    else if(hour >=19  && sAmPm =="PM")
					    	sDayTime = "Good Night!  ";
					    
					  
				 %>
						<%=sDayTime%> <%=session.getAttribute("Name").toString()%>
					
			  </td>
				<td  bgcolor="#0a4224" align="right" height="27" width="50%" >
				<input type="button" value="LogOut Your Session" onClick=location.href="<%=request.getContextPath()%>/LogOut.jsp" />
       	</td>
			</tr>
			<tr>
				<td width="50%" align="left"><a href="<%=request.getContextPath()%>/ERDAmain.jsp"><img src="resources/images/Enercon_Logo.GIF" border="0" alt="Click to go on Home Page"></a></td>
				<td width="50%" align="right"><a href="<%=request.getContextPath()%>/main.jsp"><img src="resources/images/EnergyForWorld.jpg" border="0" alt="Click to go on Home Page"></a></td>				
			<tr>
		</table>
  	</td>
</tr>
<tr>
	
   	<td valign="top" width="100%" colspan="2">
   	<DIV id=progressbar align="center"><IMG style="VERTICAL-ALIGN: bottom" 
 src="<%=request.getContextPath()%>/resources/images/progressbar.gif"><Font size=4> Please wait...downloading data
</Font></DIV>
		<table width="1000" cellpadding="0" cellspacing="0" class="TableBorder"  bgcolor="#FFFFFF">
			
			<tr>
			  <td>	
			  <iframe align="middle" id="myframe" name="myframe" width="100%" height="1000" style="BORDER-RIGHT:0px; BORDER-TOP:0px; BORDER-LEFT:0px; BORDER-BOTTOM:0px"
										frameborder="0" src="ERDACustomerBoard.jsp"></iframe>					

			  </td>
			</tr>
			
		</table>		
	</td>
  </tr>
</table>

</body>
</center>	
</html>
