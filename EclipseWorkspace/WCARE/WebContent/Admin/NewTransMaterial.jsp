<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<html>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="com.enercon.security.controller.SecurityServlet" %>
<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.security.utils.SecurityUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="com.enercon.admin.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.projects.dao.*" %>

<head>
<style>
@media print {
    BODY { font-size: 10pt }
	h2 {page-break-before: always;} 
	
  }

#div1{background-color:#CCCC00;
	width:400px;
	height:300px;
	border:solid black thin;
}
#div2{
	background-color:#3399FF;
	width:400px;
	height:300px;
	border:solid black thin;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/qna1.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/grid/calendar/calendar-blue.css"  />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/grid/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/grid/skin/vista/skinstyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/grid/gt_msg_en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/grid/gt_const.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/grid/gt_grid_all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/grid/fusioncharts/FusionCharts.js"></script>
    
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



<script type="text/javascript" >


var grid_demo_id = "myGrid1" ;

var dsOption= {

	fields :[
		{name : 'no'  },
		{name : 'country'  },
		{name : 'customer'  },
		{name : 'employee'  },
		{name : 'quality'  },
		{name : 'bill2005'  },
		{name : 'bill2006'  },
		{name : 'bill2007'  }
	],

	recordType : 'array'
}



var colsOption = [
     {id: 'no' , header: "MATERIALTRANSID" , width :120, editor:{type:"text",validRule:['R','N']}},
     {id: 'employee' , header: "PROJECT_DEFINITION" , width :140, editor:{type:"text",validRule:['R','ENC']}},
	   {id: 'country' , header: "NETWORK_ID" , width :130, editor:{type:"text",validRule:['R']}},
	   {id: 'customer' , header: "ACTIVITY_NO" , width :140, editor:{type:"text",validRule:['R','E']}},
	   {id: 'quality' , header: "MATERIAL_DESC" , width :120, editor:{type:"text",validRule:['R','N']}},
        {id: 'bill2005' , header: "WEC_QUANTITY" , width :120, editor:{type:"text",validRule:['R','N']}},
	   {id: 'bill2006' , header: "WECTYPE" , width :120, editor:{type:"text",validRule:['R','N']}},
	   {id: 'bill2007' , header: "WEC_CAPACITY" , wwidth :120, editor:{type:"text",validRule:['R','N']}}
       
];
 






var __TEST_DATA__=
[

<%
List tranList = new ArrayList();
ProjectDao secutils = new ProjectDao();
tranList = (List)secutils.getprojectsmaterialtransaction();  
%>
 
<%
for (int i=0; i <tranList.size(); i++)
		{
	if(i == 0){
		%><%
	}else{
		%>,<%
	}
	 Vector v = new Vector();
 	 v = (Vector)tranList.get(i);
 %>		
			
["<%=(String)v.get(0)%>","<%=(String)v.get(1)%>", "<%=(String)v.get(2)%>", "<%=(String)v.get(3)%>", "<%=(String)v.get(4)%>","<%=(String)v.get(5)%>","<%=(String)v.get(6)%>","<%=(String)v.get(7)%>"]				
<%

 }%>      

];





function getTestData(grid) {
	var responseT={};
	var pageInfo= grid.getPageInfo();
	responseT[grid.CONST.data]=__TEST_DATA__.slice( pageInfo.startRowNum-1,pageInfo.endRowNum);
	responseT[grid.CONST.pageInfo]={totalRowNum :__TEST_DATA__.length } ;
	return responseT;
}

var gridOption={
	
	debug : false,

	id : grid_demo_id,
	loadUrl : getTestData,
	saveUrl : './data/masterData.js',
	width: "700",  //"100%", // 700,
	height: "400",  //"100%", // 330,
	
	container : 'gridbox', 
	replaceContainer : true, 
	listenResize : false,
	//showIndexColumn : true,

	resizable : false,
	editable : false,
	remoteSort : false,

	allow_gmenu : false,
	allow_freeze : false,
    groupable : false,

	toolbarPosition : 'bottom',
	encoding : 'UTF-8', // Sigma.$encoding(), 
	pageStateBar : null , //'outStateBar',
	dataset : dsOption ,
	columns : colsOption ,
	clickStartEdit : false ,
	defaultRecord : ["","","","",""],
	pageInfo : { pageSize:5000 }
};




var mygrid=new Sigma.Grid( gridOption );
Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );


//////////////////////////////////////////////////////////






</script>

</head>
<body>

	<table border="0" cellpadding="0" cellspacing="0" width="700" class="border2 fntAr f11 fntGray1" background="<%=request.getContextPath()%>/resources/images/bg21.gif">
	<tr height=5 bgcolor="#94A664">
		<td rowspan="3" height=5></td>
		<td height=5></td>
		<td rowspan="3" height=5></td>
	</tr>
	<tr bgcolor="#94A664" height=5 class="textheadline2">
		<td align="center">Material Transaction</td>
	</tr>
	<tr bgcolor="#94A664" height=1>
		<td></td>
	</tr>
	</table>
  
   	
   		
    
    <div id="bigbox" style="margin:15px;display:!none;">
       <div id="gridbox" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;height:200px;width:100%;" ></div>
    </div>	
		
		<table>
	 <tr>
		<td bgcolor="#94A664"></td>
		<td bgcolor="#94A664"></td>
		<td bgcolor="#94A664"></td>
	</tr>
</table> 
  

</body>
</html>