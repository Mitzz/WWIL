<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/menu/dtabs.js"></script>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
%>

</head>
<body>
<div align="center">	
	<script type="text/javascript" >						
		var bstylesNames=["Individual Style",];
		// -- End of Deluxe Tuner Style Names
		
		//--- Common
		var bblankImage="resources/menu/blank.gif";
		var bitemCursor="pointer";
		var bselectedItem=0;
		
		//--- Dimensions
		var bmenuWidth="100%";
		var bmenuHeight="33px";
		
		//--- Positioning
		var babsolute=0;
		var bleft="120px";
		var btop="120px";
		
		//--- Font
		var bfontStyle=["normal 11px Tahoma, Arial","","",""];
		var bfontColor=["#000000","#000000","#000000","#000000"];
		var bfontDecoration=["none","none","none","none"];
		
		//--- Tab-mode
		var tabMode=1;
		var bselectedSmItem=-1;
		var bsmHeight=33;
		var bsmBackColor="#FFFFFF";
		var bsmBorderColor="#91A7B4";
		var bsmBorderWidth=0;
		var bsmBorderStyle="solid";
		var bsmBorderBottomDraw=2;
		var bitemTarget="frame3";
		var bsmItemAlign="center";
		var bsmItemSpacing=1;
		var bsmItemPadding="2px";
		
		//--- Appearance
		var bmenuBackColor="";
		var bmenuBackImage="";
		var bmenuBorderColor="#FFFFFF";
		var bmenuBorderWidth=0;
		var bmenuBorderStyle="ridge";
		
		//--- Tabs Appearance
		var bbeforeItemSpace=0;
		var bafterItemSpace=0;
		var bitemBackColor=["#ffffff","#ffffff","#ffffff"];
		var bitemBorderColor=["#ffffff","#ffffff","#ffffff"];
		var bitemBorderWidth=0;
		var bitemBorderStyle=["solid","solid","solid"];
		var bitemAlign="center";
		var bitemSpacing=0;
		var bitemPadding="0px";
		var browSpace=0;
		
		//--- Tabs Images
		var bitemBackImage=["resources/menu/style04_n_back.gif","resources/menu/style04_n_back.gif","resources/menu/style04_s_back.gif"];
		var bbeforeItemImage=["resources/menu/style04_n_left.gif","resources/menu/style04_n_left.gif","resources/menu/style04_s_left.gif"];
		var bafterItemImage=["resources/menu/style04_n_right.gif","resources/menu/style04_n_right.gif","resources/menu/style04_s_right.gif"];
		var bbeforeItemImageW=7;
		var bbeforeItemImageH=33;
		var bafterItemImageW=7;
		var bafterItemImageH=33;
		
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
		var bfloatable=0;
		var bfloatIterations=6;
		
		var bstyles = [
		    ["bitemBackColor=#ffffff,#bbccee,#bbccee","bitemBorderColor=#ffffff,#316AC5,#316AC5","bitemBorderWidth=1","bitemBorderStyle=solid,solid,solid"],
		];
		
		var bmenuItems = [		
			["&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload Status&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;","DisplayCustomerBoard.jsp", "", "", "", "", "", "", "", ],
			["&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Generation Details&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;","DisplayDashBoard.jsp", "", "", "", "", "", "", "", ],
			["&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Average Delay&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;","DisplayAverageDelayBoard.jsp", "", "", "", "", "", "", "", ],
						];
		
		dtabs_init();
	</script>
      <IFRAME name="frame3" frameborder="0" width="100%" height="100%" src="DisplayCustomerBoard.jsp" >
	  </IFRAME>
</div>

</body>
</html>
