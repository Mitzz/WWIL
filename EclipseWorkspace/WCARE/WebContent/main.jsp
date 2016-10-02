<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.model.master.TransactionMasterVo"%>
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>

<%@ page import="java.util.*" %>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%! private final static Logger logger = Logger.getLogger(JSPErrorLogger.class); %>

<html>
<head>
<title>WCARE</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/Enercon.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/validateForm.js"></script>
<script type='text/javascript' src="<%=request.getContextPath()%>/resources/js/leftmenu.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/scroll.js"></script>
<script type="text/javascript">var tWorkPath="<%=request.getContextPath()%>/resources/ess.files/";</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtabs.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/floating-window.css" media="screen" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxfloat.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/floating-window.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/tsbs.js"></script>
<style type="text/css">
<!--
.style6 {color: #800000; font-weight: bold;font-size: 12px}
.style7 {color: #800000; font-size: 12px}
.style01 {color: #800000; font-size: 12px}
.style10 {color: #FF0000; font-size: 18px}
.style12 {color: #008000;font-size: 12px}
.style14 {color: #008000; font-weight: bold; font-size: 12px}
.style16 {color: #800000;}
.style17 {color: #008000}
.style18 {font-size: 18px}
.style19 {color: #800000; font-weight: bold;}
.style160 {color: #FFFFFF;font-weight: bold;}
.style180 {font-size: 14px}
-->
</style>
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
<body   valign="top" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="1200" border="1" cellspacing="0" cellpadding="0" valign="top" align="center">
	<tr>
		<td colspan="2">
			<table border="0" width="1200" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
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
					<%=sDayTime%>
					<%=session.getAttribute("Name").toString()%>
					</td>
					<td bgcolor="#0a4224" align="right" height="27" width="100%">
						&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/main.jsp" class="headwhitetext">Home</a>
						&nbsp;&nbsp;<font class="headwhitetext">|</font>
						&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/ChangePassword.jsp" class=headwhitetext target="myframe">Change Password</a>
						&nbsp;&nbsp;
						<font class="headwhitetext">|</font>
						&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/LogOut.jsp" class="headwhitetext">Log Out</a>
						&nbsp;&nbsp;				
					</td>
				</tr>
				<tr>
					<td width="50%" align="left">
						<a href="<%=request.getContextPath()%>/main.jsp">
							<img src="resources/images/Enercon_Logo1.GIF" border="0" alt="Click to go on Home Page" />
						</a>
					</td>
					<td width="50%" align="right">
						<a href="<%=request.getContextPath()%>/main.jsp">
							<img src="resources/images/EnergyForWorld.jpg" border="0" alt="Click to go on Home Page">
						</a>
					</td>				
				<tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="20%" height="704" align="left" valign="top">
		<%	
		AdminDao od = new AdminDao(); 		
		String count = od.getAllloginhistory(); 
		//String id=session.getAttribute("loginID").toString();
		//String lastlogin = od.getLastloginhistory(id);
		%>
		<table width="100%"  bgcolor="#0a4224" cellspacing="0" cellpadding="0" align="center" border="0" height="20"  >
			<tr align="left" valign="center" width="100%" height="20">
				<td valign="center">
					<font style="font-size:8pt; weight:bold; color:white;">&nbsp;
						<strong>You are visitor number <%=count%></strong>
					</font>
				</td>
			</tr>
		</table>
		<br>
		<script type="text/javascript" >
			/*
			   Deluxe Menu Data File
			   Created by Deluxe Tuner v3.2
			   http://deluxe-menu.com
			*/
			
			
			// -- Deluxe Tuner Style Names
			var tstylesNames=["Top Item","Top Item","Top Item","Top Item",];
			var tXPStylesNames=[];
			// -- End of Deluxe Tuner Style Names
			
			//--- Common
			var tlevelDX=20;
			var texpanded=0;
			var texpandItemClick=1;
			var tcloseExpanded=1;
			var tcloseExpandedXP=1;
			var ttoggleMode=1;
			var tnoWrap=1;
			var titemTarget="_self";
			var titemCursor="pointer";
			var statusString="link";
			var tblankImage="resources/ess.files/blank.gif";
			var tpathPrefix_img="";
			var tpathPrefix_link="";
			
			//--- Dimensions
			var tmenuWidth="230px";
			var tmenuHeight="auto";
			
			//--- Positioning
			var tabsolute=1;
			var tleft="20px";
			var ttop="80px";
			
			//--- Font
			
			var tfontStyle="normal 8pt Tahoma";
            var tfontColor=["#3F3D3D","#7E7C7C"];
            var tfontDecoration=["none","underline"];
            var tfontColorDisabled="#ACACAC";
            var tpressedFontColor="#AA0000";
            
			//--- Appearance
			var tmenuBackColor="";
			var tmenuBackImage="resources/ess.files/blank.gif";
			var tmenuBorderColor="#FFFFFF";
			var tmenuBorderWidth=0;
			var tmenuBorderStyle="solid";
			
			//--- Item Appearance
			var titemAlign="left";
			var titemHeight=22;
			var titemBackColor=["#F6F6EC","#F6F6EC"];
			
			var titemBackImage=["resources/ess.files/blank.gif","resources/ess.files/blank.gif"];
			
			//--- Icons & Buttons
			var ticonWidth=21;
			var ticonHeight=15;
			var ticonAlign="left";
			var texpandBtn=["resources/ess.files/expandbtn2.gif","resources/ess.files/expandbtn2.gif","resources/ess.files/collapsebtn2.gif"];
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
			var tmoveColor="#AA0000";
			var tmoveImage="resources/ess.files/movepic.gif";
			
			//--- XP-Style
			var tXPStyle=1;
			var tXPIterations=10;
			
			var tXPBorderWidth=1;
			var tXPBorderColor="#FFFFFF";
			var tXPTitleBackColor="#94A664";
			var tXPTitleBackImg="resources/ess.files/xptitle1.gif";
			var tXPTitleLeft="resources/ess.files/xptitleleft_o.gif";
			var tXPTitleLeftWidth=4;
			var tXPIconWidth=31;
			var tXPIconHeight=32;
			var tXPMenuSpace=10;
			var tXPExpandBtn=["resources/ess.files/xpexpand11.gif","resources/ess.files/xpexpand111.gif","resources/ess.files/xpcollapse11.gif","resources/ess.files/xpcollapse111.gif"];
			var tXPBtnWidth=25;
			var tXPBtnHeight=25;
			var tXPFilter=1;
			
			//--- Advanced
			var tdynamic=1;
			var tajax=1;
			
			//--- State Saving
			var tsaveState=0;
			var tsavePrefix="menu1";
			
			var tstyles = [
    ["tfontStyle=bold 8pt Tahoma","tfontColor=#FFFFFF,#E0E7B8","tfontDecoration=none,none"],
    ["tfontStyle=bold 8pt Tahoma","tfontColor=#56662D,#72921D","tfontDecoration=none,none"],
    ["tfontDecoration=none,none"],
    ["tfontStyle=bold 8pt Tahoma","tfontColor=#444444,#5555FF"],
];

			var tXPStyles = [
			];
			
			var tmenuItems = [
			<%
		String sU1 = "";
		List<TransactionMasterVo> transactions = (List<TransactionMasterVo>) session.getAttribute("transactions");
		TransactionMasterVo vU1 = null;
		TransactionMasterVo vU2 = null;
		TransactionMasterVo vU3 = null;
		TransactionMasterVo vU4 = null;
		logger.debug(transactions);
		for (int u1=0; u1 <transactions.size(); u1++){
			//Vector vU1 = new Vector();
		    vU1 = transactions.get(u1);
		    String sNext1 = vU1.get(3);
		    if (sU1.equals(sNext1)){
			}
		    else{
				sU1 = vU1.get(3);						
			%>
				["<%=sU1%>","", "resources/ess.files/<%=vU1.get(8)%>", "resources/ess.files/<%=vU1.get(9)%>", "resources/ess.files/<%=vU1.get(10)%>", "Click to expand/collapse Administrators Options", "", "0", "0", "", ],				
						
			<%
				String sU2 = "";
				int i2 = 0;
				for (int u2=0;u2<transactions.size();u2++){
					String sURL = "";
					String sName = "";
					String sAlt = "";	  
					String sFrame = "";
					vU2 = transactions.get(u2);
					if (sU1.equals(vU2.get(3))){		        				
						String sNext2 = vU2.get(4);
			    		String Head1 = vU2.get(3) == null ? "" : vU2.get(3);
			    		if (sNext2 == null  && Head1.equals(sU1)){
							sURL = vU2.get(2);
			    			sName = vU2.get(1);	        		
			    			sAlt = vU2.get(6);
			    			sFrame = vU2.get(7);
			%>  
			    					["|<%=sName%>","<%=sURL%>", "resources/ess.files/<%=vU2.get(17)%>", "resources/ess.files/<%=vU2.get(18)%>", "resources/ess.files/<%=vU2.get(19)%>", "<%=sAlt%>", "<%=sFrame %>", "", "", "", ],
			<%
						}
						else if (sNext2 != null){
							if(sU2.equals(sNext2)){								
							}
							else if(Head1.equals(sU1)){
								sU2 = vU2.get(4);
			%>
										["|<%=sU2%>","", "resources/ess.files/<%=vU2.get(11)%>", "resources/ess.files/<%=vU2.get(12)%>", "resources/ess.files/<%=vU2.get(13)%>", "", "", "", "", "", ],
         	<%
								String sU3 = "";
								int i3 = 0;
								for (int u3=0;u3<transactions.size();u3++){
									String sURL3 = "";
									String sName3 = "";
									String sAlt3 = "";
									String sFrame3 = "";
									vU3 = transactions.get(u3);
									if (sU2.equals(vU3.get(4))){		        				
										String sNext3 = vU3.get(5);
										Head1 = vU3.get(3) == null ? "" : vU3.get(3);								    				
										String Head2 = vU3.get(4) == null ? "" : vU3.get(4);
										if (sNext3 == null && Head1.equals(sU1) && Head2.equals(sU2)){
											sURL3 = vU3.get(2);
											sName3 = vU3.get(1);
											sAlt3 = vU3.get(6);
											sFrame3 = vU3.get(7);
			%> 
								    					["||<%=sName3%>","<%=sURL3%>", "resources/ess.files/<%=vU3.get(17)%>", "resources/ess.files/<%=vU3.get(18)%>", "resources/ess.files/<%=vU3.get(19)%>", "<%=sAlt3%>", "<%=sFrame3 %>", "", "", "", ],								    					 					     						
			<%
										}
										else if (sNext3 != null){
											if(sU3.equals(sNext3)){													
											}
											else if (Head1.equals(sU1) && Head2.equals(sU2)){
												sU3 = vU3.get(5);	
			%>
															["||<%=sU3%>","", "resources/ess.files/<%=vU3.get(14)%>", "resources/ess.files/<%=vU3.get(15)%>", "resources/ess.files/<%=vU3.get(16)%>", "", "", "", "", "", ],
			<%
												//String sU4 = "";
												for (int u4=0;u4<transactions.size();u4++){
													String sURL4 = "";
													String sName4 = "";
													String sAlt4 = "";
													String sFrame4 = "";													   				
													vU4 = transactions.get(u4);
													Head1 = vU4.get(3) == null ? "" : vU4.get(3);								    				
													Head2 = vU4.get(4) == null ? "" : vU4.get(4);
													if (sU3.equals(vU4.get(5)) && Head1.equals(sU1) && Head2.equals(sU2)){		        				
														//String sNext4 = (String)vU3.get(5);											    				
														sURL4 = vU4.get(2);
														sName4 = vU4.get(1);	        		
														sAlt4 = vU4.get(6);
														sFrame4 = vU4.get(7);
			%>  
												    					["|||<%=sName4%>","<%=sURL4%>", "resources/ess.files/<%=vU4.get(17)%>", "resources/ess.files/<%=vU4.get(18)%>","resources/ess.files/<%=vU4.get(19)%>", "<%=sAlt4%>", "<%=sFrame4 %>", "", "", "", ],
			<%																
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}						
			}
		}    		
			%>			    			        
			];
			//console.dir(tmenuItems);
			/* for(var i = 0; i < tmenuItems.length; i++){
				
				var menuItems = tmenuItems[i];
				console.dir(menuItems);
				for(var j = 0; j < menuItems.length; j++){
					console.log(menuItems[j]);
				}
			} */
			dtree_init();			
		</script>
		<div id="masterdiv">
			<input type="hidden" name="Menu_Input_Type"/>
			<input type="hidden" name="Admin_Input_Type"/>				
		</td>
		<td valign="top" width="80%">
		<table width="100%" cellpadding="0" cellspacing="0" class="TableBorder">		
			<tr>
				<td valign="top" width="1000">
					<table width="100%" cellpadding="0" cellspacing="0" class="TableBorder">
						<tr>
						<%
						
						
						
						
						
						if(session.getAttribute("LoginType").equals("E") 
							&& (session.getAttribute("RoleID").equals("0000000001")
								||session.getAttribute("RoleID").equals("0811000001")
								||session.getAttribute("RoleID").equals("0808000002")
								||session.getAttribute("RoleID").equals("1400000001"))){
						%>	
							<td valign="top">
								<script type="text/javascript" >
						
						// -- Deluxe Tuner Style Names
						var bstylesNames=[];
						// -- End of Deluxe Tuner Style Names
						
						//--- Common
						var bblankImage="resources/ess.files/blank.gif";
						var bitemCursor="pointer";
						var bselectedItem=1;
						
						//--- Dimensions
						var bmenuWidth="100%";
						var bmenuHeight="auto";
						
						//--- Positioning
						var babsolute=0;
						var bleft="120px";
						var btop="120px";
						
						//--- Font
						var bfontStyle=["bold 12px Arial","",""];
						var bfontColor=["#000000","#000000","#000000"];
						var bfontDecoration=["none","none","none"];
						
						//--- Tab-mode
						var tabMode=1;
						var bselectedSmItem=-1;
						var bsmHeight=10;
						var bsmBackColor="#FFFFFF";
						var bsmBorderColor="#91A7B4";
						var bsmBorderWidth=0;
						var bsmBorderStyle="solid";
						var bsmBorderBottomDraw=1;
						var bitemTarget="myframe";
						var bsmItemAlign="center";
						var bsmItemSpacing=1;
						var bsmItemPadding="0px";
						
						//--- Appearance
						var bmenuBackColor="#D4D0C8";
						var bmenuBackImage="";
						var bmenuBorderColor="#FFFFFF";
						var bmenuBorderWidth=0;
						var bmenuBorderStyle="dotted";
						
						//--- Tabs Appearance
						var bbeforeItemSpace=0;
						var bafterItemSpace=0;
						var bitemBackColor=["#E2E2E2","#E2E2E2","#95D1FF"];
						var bitemBorderColor=["#ffffff","#ffffff","#ffffff"];
						var bitemBorderWidth=0;
						var bitemBorderStyle=["solid","solid","solid"];
						var bitemAlign="center";
						var bitemSpacing=0;
						var bitemPadding="0px";
						var browSpace=0;
						
						//--- Tabs Images
						var bitemBackImage=["resources/ess.files/style04_n_back.gif","resources/ess.files/style04_n_back.gif","resources/ess.files/style04_s_back.gif"];
						var bbeforeItemImage=["resources/ess.files/style04_n_left.gif","resources/ess.files/style04_n_left.gif","resources/ess.files/style04_s_left.gif"];
						var bafterItemImage=["resources/ess.files/style04_n_right.gif","resources/ess.files/style04_n_right.gif","resources/ess.files/style04_s_right.gif"];
						var bbeforeItemImageW=7;
						var bbeforeItemImageH=31;
						var bafterItemImageW=7;
						var bafterItemImageH=31;
						
						//--- Icons
						var biconWidth=16;
						var biconHeight=16;
						var biconAlign="left";
						
						//--- Separators
						var bseparatorWidth="1px";
						
						//--- Transitional Effects
						var btransition=24;
						var btransOptions="";
						var btransDuration=300;
						
						//--- Floatable Menu
						var bfloatable=1;
						var bfloatIterations=6;
						
						var bstyles = [
						];
						
						var bmenuItems = [						
						    ["State Wise Generation Status","StateWiseGraph.jsp", "", "", "", "", "", "", "", ],
						    ["State Wise Upload Status","DisplayCustomerBoard.jsp", "", "", "", "", "", "", "", ],
						    ["State Wise Upload Delay Status","AverageDelayGraph.jsp", "", "", "", "", "", "", "", ],						    
						];
						
						dtabs_init();
					</script>
				</td><%
				} %>
			</tr>
 <!--  --><%if(session.getAttribute("LoginType").equals("G")){%>
	 		<tr>
				<td>						
					<IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="iredaHome.do">
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src=""> -->
						Alternate content for non-supporting browsers
					</IFRAME>
				</td>
			</tr>
 <%}else{%>
			<tr>
				<td>						
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="DisplayCustomerBoard.jsp"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="jsppages/SendMessageN.jsp?msgid=1504000015"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="jsppages/DGRReportN.jsp?Type=D"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="DGRReport.jsp?Type=D"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src=""> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="dgrWec.do?date=24/02/2016&customerId=1000000064&siteId=1000000142&stateId=1000000079"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="dgrWec.do?date=19/02/2016&customerId=0905000002&siteId=1204000001&stateId=1000000067"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="Change/CustomerReportChangeByDayN.jsp?stateid=1000000079&siteid=1000000142&id=1000000064&rd=09/02/2016"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="mprWecType.do?wecType=E-53&fromDate=01/01/2016&toDate=31/01/2016"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="StateGenerationReportN.jsp"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="monthDateWise.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="monthMachineWise.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="yearWecWise.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="yearSiteWise.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="areaMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="siteMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="customerMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="transferEb.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="cMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="customerLogin.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="federMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="wecMaster.do"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="transferWec.do"> -->
					
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/test"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/stateMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/areaMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/customerMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/ebMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/ebMFactor"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/federMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/federMFactor"> -->
					<IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/feederMaster">
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/measuringPoint"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/remarkMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/siteMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/siteRemark"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/substationMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/wecMaster"> -->
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/wecTypeMaster"> -->
					
					<!-- <IFRAME name="myframe" id="myframe" frameborder="0" TITLE="Wind World (India) Ltd" width="100%" height="1000"  src="spring/byDay"> -->
						Alternate content for non-supporting browsers
					</IFRAME>
				</td>
			</tr>
			<%} %>
		<!-- </table>		
		</td>
			</tr> -->
			
					<tr>
						<td bgcolor="#0a4224" align="right" class="whiteNormal2" ><%@include file="commBottomPanel.jsp"%></td>
					</tr>
					<tr>
						<td height=2></td>
					</tr>
					<tr>
						<td align=center bgcolor="#0a4224" ><SPAN class="style160" >
						Site best viewed in Mozilla Firefox Verion 30 and above. &copy;2013 Wind World (India) Ltd.</span></td>
					</tr>
			</table>
		</td>
  	</tr>
</table>	

</body>
</html>
