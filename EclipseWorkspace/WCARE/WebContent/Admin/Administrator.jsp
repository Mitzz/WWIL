<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.*" %>

<%@ page import="com.enercon.security.utils.*" %>

<html>
<head>
<title>ECARE Portal@Wind World (SAMPARK)</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
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

<style type="text/css">
@media print {
body { display:none }
}
</style>
<style type="text/css">
	body{
		background-image:url('<%=request.getContextPath()%>/resources/images/heading3.gif');
		background-repeat:no-repeat;
		padding-top:85px;	

	}
	body,html{
		width:100%;
		height:100%;
		margin:0px;
		
		
	}

	a{
		color:blue;
	}
</style>
<script type="text/jscript">
  function disableContextMenu()
  {
   //document.frames["myframe"].document.oncontextmenu = function(){ return false;}; 
    //document.frames["myframe"].document.ondragstart=function(){ return false;};
     //document.frames["myframe"].document.onselectstart=function(){ return false;};
    document.getElementById("myframe").contentWindow.document.oncontextmenu = function();{ return false;};;
    document.getElementById("myframe").contentWindow.document.ondragstart=function();{ return false;};;
    document.getElementById("myframe").contentWindow.document.onselectstart=function(){ return false;};;

    //document.getElementById('mapFrame').contentWindow.event.offsetX; 
    // Or use this
    // document.getElementById("fraDisabled").contentWindow.document.oncontextmenu = function(){alert("No way!"); return false;};;    
  }  
// (C) 2003 CodeLifter.com
// Free for all users, but leave in this  header
// Set the message for the alert box
am = "This function is disabled!";

// do not edit below this line
// ===========================
bV  = parseInt(navigator.appVersion)
bNS = navigator.appName=="Netscape"
bIE = navigator.appName=="Microsoft Internet Explorer"

function nrc(e) {

   if (bNS && e.which > 1){
      //alert(am)
      return false
   } else if (bIE && (event.button >1)) {
    // alert(am);
     return false;
   } 
}
function nrc1(e) {

   if (bNS && e.which >43){
      alert(am)
      return false
   } else if (bIE && (event.button >43)) {
     alert(am);
     return false;
   } 
}
document.onmousedown=nrc;
document.onkeydown=nrc1;
if (document.layers) window.captureEvents(Event.MOUSEDOWN);
if(bNS && bV<5) window.onmousedown=nrc;


function disableselect(e)
{
    return false;
}

function reEnable()
{
    return true;
}

document.onselectstart=disableselect;




</SCRIPT>

<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
String LOGINID=(String)session.getAttribute("loginID").toString();
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
<script>
document.oncontextmenu=disableit
function disableit()
{
//alert("done")onload="showcoffAppApplications('')"
return false;
}


</script>
<SCRIPT language="JavaScript">

function popUp() {
day = new Date();
id = day.getTime();
eval("Dear" + id + " = window.open('PopMessage.jsp', '" + id + "', 'addressbar=0,titlebar=0,toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=1,width=920,height=630,left = 583,top = 284');");
}

</script>

</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  onload="showcoffAppApplications()" onselectstart="return false" unselectable="on" ondragstart='return false'>



<%    SecurityUtils  secutil= new SecurityUtils(); 
   //String eligible=adminutil.getEmplEligible(session.getAttribute("loginID").toString()); 
  // if(eligible.equals("0")){%>
 		<!--  	<script type="text/javascript" src="<%=request.getContextPath()%>/pop.files/dpopupwindow.js"></script> -->
	    <!--  	<script type="text/javascript" src="<%=request.getContextPath()%>/pop.files/pop_win.js"></script>-->
	<% //} %>
<table width="100%" border="1" cellspacing="0" cellpadding="0" align="center">

<tr>
	<td width="140" align="left" valign="top">
			
		<%  String tabid=request.getParameter("TabId");
			List tranList = new ArrayList();
		    /*  Getting the Transaction of the User from the Session. */
		    tranList = (List)secutil.getAllTransactions(session.getAttribute("RoleID").toString());    
		%>
			
						<%	
		 		
		//String count = od.getAllloginhistory(); 
		//String id=session.getAttribute("loginID").toString();
		//String lastlogin = od.getLastloginhistory(id);
		%>
		
		<br>
		<script type="text/javascript" >
				/*
		<script type="text/javascript" >
			/*
			   Deluxe Menu Data File
			   Created by Deluxe Tuner v3.2
			   http://deluxe-menu.com
			*/
			
			
			// -- Deluxe Tuner Style Names
			/*
   Deluxe Menu Data File
   Created by Deluxe Tuner v4.1
   http://deluxe-menu.com
*/

	
	// -- Deluxe Tuner Style Names
	var tstylesNames=["Individual Style","Individual Style","Individual Style","Individual Style",];
	var tXPStylesNames=["Individual Style",];
	// -- End of Deluxe Tuner Style Names
	
	//--- Common
	var tlevelDX=20;
	var texpanded=0;
	var texpandItemClick=0;
	var tcloseExpanded=1;
	var tcloseExpandedXP=0;
	var ttoggleMode=1;
	
	//--- Dimensions
	var tmenuWidth="230px";
	var tmenuHeight="";
	
	//--- Positioning
	var tabsolute=0;
	var tleft="";
	var ttop="";
	
	//--- Menu Appearance
	var tmenuBackColor="";
	var tmenuBackImage="";
	var tmenuBorderColor="#FFFFFF";
	var tmenuBorderWidth=0;
	var tmenuBorderStyle="solid";
	
	//--- Item Appearance & Font
	var tfontStyle="normal 8pt Tahoma";
	var tfontColor=["#3F3D3D","#7E7C7C"];
	var tfontDecoration=["none","underline"];
	var tfontColorDisabled="#ACACAC";
	var tpressedFontColor="#AA0000";
	var titemAlign="left";
	var titemHeight=22;
	var titemBackColor=["#F0F1F5","#F0F1F5"];
	var titemBackImage=["",""];
	var tnoWrap=1;
	
	//--- Icons & Buttons
	var ticonWidth=21;
	var ticonHeight=15;
	var ticonAlign="left";
	var texpandBtn=["deluxe-tree1html.files/expandbtn2.gif","deluxe-tree1html.files/expandbtn2.gif","deluxe-tree1html.files/collapsebtn2.gif"];
	var texpandBtnW=9;
	var texpandBtnH=9;
	var texpandBtnAlign="left";
	
	//--- Lines
	var tpoints=0;
	var tpointsImage="";
	var tpointsVImage="";
	var tpointsCImage="";
	var tpointsBImage="";
	
	//--- Floatable Menu
	var tfloatable=0;
	var tfloatIterations=10;
	var tfloatableX=1;
	var tfloatableY=1;
	
	//--- Movable Menu
	var tmoveable=0;
	var tmoveHeight=12;
	var tmoveColor="transparent";
	var tmoveImage="deluxe-tree1html.files/movepic.gif";
	
	//--- XP-Style
	var tXPStyle=1;
	var tXPIterations=10;
	var tXPBorderWidth=1;
	var tXPBorderColor="#FFFFFF";
	var tXPAlign="left";
	var tXPTitleBackColor="#AFB1C3";
	var tXPTitleBackImg="deluxe-tree1html.files/xptitle_s.gif";
	var tXPTitleLeft="deluxe-tree1html.files/xptitleleft_s.gif";
	var tXPTitleLeftWidth=4;
	var tXPIconWidth=31;
	var tXPIconHeight=32;
	var tXPMenuSpace=10;
	var tXPExpandBtn=["deluxe-tree1html.files/xpexpand1_s.gif","deluxe-tree1html.files/xpexpand1_s.gif","deluxe-tree1html.files/xpcollapse1_s.gif","deluxe-tree1html.files/xpcollapse1_s.gif"];
	var tXPBtnWidth=25;
	var tXPBtnHeight=23;
	var tXPFilter=1;
	
	//--- Advanced
	var tdynamic=0;
	var tajax=0;
	var titemCursor="default";
	var statusString="";
	var tblankImage="deluxe-tree1html.files/blank.gif";
	var tpathPrefix_img="";
	var tpathPrefix_link="";
	var titemTarget="";
	
	//--- State Saving
	var tsaveState=0;
	var tsavePrefix="menu1";
	
	var tstyles = [
	    ["tfontStyle=bold 8pt Tahoma","tfontColor=#FFFFFF,#E6E6E6","tfontDecoration=none,none"],
	    ["tfontStyle=bold 8pt Tahoma","tfontColor=#3F3D3D,#7E7C7C","tfontDecoration=none,none"],
	    ["tfontDecoration=none,none"],
	    ["tfontStyle=bold 8pt Tahoma","tfontColor=#444444,#5555FF"],
	];
	var tXPStyles = [
	    ["tXPTitleBackColor=#D9DAE2","tXPTitleBackImg=deluxe-tree1html.files/xptitle2_s.gif","tXPExpandBtn=deluxe-tree1html.files/xpexpand2_s.gif,deluxe-tree1html.files/xpexpand3_s.gif,deluxe-tree1html.files/xpcollapse2_s.gif,deluxe-tree1html.files/xpcollapse3_s.gif"],
	];
			
			var tmenuItems = [

   
        ["|Home","testlink.htm", "deluxe-tree1html.files/icon1_s.gif", "deluxe-tree1html.files/icon1_so.gif", "", "Home Page Tip", "", "", "", "", ],
        ["|Product Info","", "deluxe-tree1html.files/icon2_s.gif", "deluxe-tree1html.files/icon2_so.gif", "", "Product Info Tip", "", "", "", "", ],
            ["||What's New","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Features","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Installation","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Functions","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Supported Browsers","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
        ["|Samples","", "deluxe-tree1html.files/icon3_s.gif", "deluxe-tree1html.files/icon3_so.gif", "", "Samples Tip", "", "", "", "", ],
            ["||Sample 1","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Sample 2","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Sample 3","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Sample 4","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Sample 5","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||Sample 6","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||More Samples","", "deluxe-tree1html.files/icon3_s.gif", "deluxe-tree1html.files/icon3_so.gif", "", "", "", "", "", "", ],
                ["|||New Sample 1","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
                ["|||New Sample 2","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
                ["|||New Sample 3","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
                ["|||New Sample 4","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
                ["|||New Sample 5","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
        ["|Purchase","testlink.htm", "deluxe-tree1html.files/icon4_s.gif", "deluxe-tree1html.files/icon4_so.gif", "", "Purchase Tip", "", "", "", "", ],
        ["|Support","", "deluxe-tree1html.files/icon5_s.gif", "deluxe-tree1html.files/icon5_so.gif", "", "Support Tip", "", "", "", "", ],
            ["||Write Us","mailto:dhtml@dhtml-menu.com", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
   
        ["|+Samples Block 1","", "deluxe-tree1html.files/icon3_s.gif", "deluxe-tree1html.files/icon3_so.gif", "", "", "", "", "", "", ],
            ["||New Sample 1","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 2","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 3","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 4","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 5","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
        ["|Samples Block 2","", "deluxe-tree1html.files/icon3_s.gif", "deluxe-tree1html.files/icon3_so.gif", "", "", "", "", "", "", ],
            ["||New Sample 1","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 2","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 3","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 4","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 5","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
        ["|Samples Block 3","", "deluxe-tree1html.files/icon3_s.gif", "deluxe-tree1html.files/icon3_so.gif", "", "", "", "", "", "", ],
            ["||New Sample 1","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 2","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 3","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 4","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
            ["||New Sample 5","testlink.htm", "deluxe-tree1html.files/iconarrs.gif", "", "", "", "", "", "", "", ],
];

dtree_init();		
		</script>		
		
		
		<div id="masterdiv">
		
		<input type="hidden" name="Menu_Input_Type"/>
		<input type="hidden" name="Admin_Input_Type"/>				
	</td>
   	<td valign="top" width="730">
		<table width="100%" cellpadding="0" cellspacing="0" class="TableBorder">
			
			
			<tr>
				<td>						
					<IFRAME name="myframe1" id="myframe1" frameborder="0" TITLE="Wind World (India) Ltd" width="730" height="700"  src="AmzFact.jsp" >
						<!-- Alternate content for non-supporting browsers -->
					</IFRAME>
				</td>
			</tr>
			
		</table>		
	</td>
 	</tr>
</table>										
</body>
</html>




