<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="com.enercon.projects.dao.*" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.text.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Project Master</title>
<meta http-equiv="Content-Language" content="en-us" /> 
<meta name="keywords" content="dhtml grid, AJAX grid, Data validation when input" >
<meta name="description" content="How to add Data validation to grid" >

<script src="<%=request.getContextPath()%>/DBGridRes/js/jssc3.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/DBGridRes/css/style.css" rel="stylesheet" type="text/css" />



<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/DBGridRes/calendar/calendar-blue.css"  />


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/DBGridRes/css/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/DBGridRes/skin/vista/skinstyle.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_msg_en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_const.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_grid_all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/fusioncharts/FusionCharts.js"></script>
    
<script type="text/javascript" >

var grid_demo_id = "myGrid1" ;

var dsOption= {

	fields :[
		{name : 'no'  },
		{name : 'country'  },
		{name : 'customer'  },
		{name : 'employee'  },
		{name : 'quality'  },
		{name : 'bill2005' }
		
	],

	recordType : 'array'
}



var colsOption = [
     {id: 'no' , header: "Activityid" , width :120, editor:{type:"text",validRule:['R','N']}},
     {id: 'employee' , header: "ProjectName" , width :140, editor:{type:"text",validRule:['R','ENC']}},
	   {id: 'country' , header: "NetworkID" , width :130, editor:{type:"text",validRule:['R']}},
	   {id: 'customer' , header: "ActivityNo" , width :140, editor:{type:"text",validRule:['R','E']}  },
	   {id: 'quality' , header: "ActivityDesc" , width :120, editor:{type:"text",validRule:['R','N']}},
        {id: 'bill2005' , header: "ActivityDisp" , width :60 , inChart :true, chartColor : 'eecc99'}
	  
       
];
 

var __TEST_DATA__=
		[	
<%
List tranList = new ArrayList();
ProjectDao secutils = new ProjectDao();
tranList = (List)secutils.getprojectsactivity();  
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
			
["<%=(String)v.get(0)%>","<%=(String)v.get(1)%>", "<%=(String)v.get(2)%>", "<%=(String)v.get(3)%>", "<%=(String)v.get(4)%>","<%=(String)v.get(5)%>"]				
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
	width: "600",  //"100%", // 700,
	height: "200",  //"100%", // 330,
	
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
	defaultRecord : ["","","","",0,0,0,0,"2008-01-01"],
	pageInfo : { pageSize:10 }
};




var mygrid=new Sigma.Grid( gridOption );
Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );


//////////////////////////////////////////////////////////






</script>
</head>
<body>
<div align="center">
<FORM  ENCTYPE="multipart/form-data" ACTION="upload_csv.jsp?UploadType=ActivityMaster" METHOD=POST>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="614">
<tbody><tr>
	<td class="newhead1" width="10"></td>
	<th class="headtext" width="70">Project Activity  Master</th>
	<td width="363"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3" width="8">&nbsp;</td>
	<td class="newhead4" width="11"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif" width="10">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	<td colspan="3" width="593">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="595">
		<tbody>
		
		<tr class="bgcolor">
			<td id="t_company" width="204">&nbsp;Choose the file To Upload:</td>
			<td width="378" colspan="3" valign="top">
            <INPUT NAME="file" TYPE="file" size="20"></td>
			</tr>
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif" width="11">
    <img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" width="1" height="1"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633" width="604">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		
		
		
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="200" colspan="2">
		<input type="hidden" name="Admin_Input_Type" value="ProjectMaster" />
			
		<input type="hidden" name="StateIdtxt"  />
		<input name="Submit" id="Submit" value="Send File" class="btnform" type="Submit"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>









  <div id="content">
    <div id="bigbox" style="margin:15px;display:!none;">
       <div id="gridbox" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;height:200px;width:700px;" ></div>
    </div>
  </div>
</body>
</html>