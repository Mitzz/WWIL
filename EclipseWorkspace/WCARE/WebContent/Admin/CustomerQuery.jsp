<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.util.*" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.text.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Query</title>
<meta http-equiv="Content-Language" content="en-us" /> 
<meta name="keywords" content="dhtml grid, AJAX grid, Data validation when input" >
<meta name="description" content="How to add Data validation to grid" >
<link href="<%=request.getContextPath()%>/DBGridRes/css/style.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/DBGridRes/js/jssc3.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/DBGridRes/css/style.css" rel="stylesheet" type="text/css" />
<style type="text/css" media="all">@import "<%=request.getContextPath()%>/DBGridRes/css/doc_no_left.css";</style>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/DBGridRes/calendar/calendar-blue.css"  />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/DBGridRes/css/gt_grid.css" />
<link rel="stylesheet" type="text/css" href="DBGridRes/skin/vista/skinstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_msg_en.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_const.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/js/gt_grid_all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/DBGridRes/fusioncharts/FusionCharts.js"></script>

   
<script type="text/javascript" >
function myFunction1(aid)
{
//alert(aid);
var url="ReplyQuery.jsp?aid="+aid;
//location.href=url;
window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes,status=no');

}
function my_renderer(value ,record,columnObj,grid,colNo,rowNo){

		var no= grid.dataset.getRecordValue(record,"printdetail");
	
		//var url="printcert.jsp?wecid="+no;
		
		return "<a  href=\"javascript:onClick=myFunction1('"+no+"')\"><img border=\"0\" src=\"<%=request.getContextPath()%>/resources/images/edit.gif\" ></a>";
}
var grid_demo_id = "myGrid1" ;

var dsOption= {

	fields :[
		{name : 'printdetail' },
		{name : 'customer'  },
		{name : 'msghead'  },
		{name : 'msgdesc'  },
		{name : 'msgsign'  },
		{name : 'sentdate' },
		{name : 'sentemailid' },
		{name : 'status' },
		{name : 'replydesc' },
		{name : 'replyated' }
	],

	recordType : 'array'
}



var colsOption = [
     {id: 'printdetail' , header: "Edit" ,width :40,renderer:my_renderer},
     {id: 'customer' , header: "Customer" , width :140, editor:{type:"text",validRule:['R','ENC']}},
	   {id: 'msghead' , header: "Msg Head" , width :130, editor:{type:"text",validRule:['R']}},
	   {id: 'msgdesc' , header: "Msg Desc" , width :140, editor:{type:"text",validRule:['R','E']}  },
	   {id: 'msgsign' , header: "Msg Sign" , width :120, editor:{type:"text",validRule:['R','N']}},
        {id: 'sentdate' , header: "Sent Date" , width :60 , inChart :true, chartColor : 'eecc99'},
        {id: 'sentemailid' , header: "Sent Email ID" , width :120, editor:{type:"text",validRule:['R','N']}},
         {id: 'status' , header: "Status" , width :120, editor:{type:"text",validRule:['R','N']}},
        {id: 'replydesc' , header: "Reply Desc" , width :60 , inChart :true, chartColor : 'eecc99'},
	    {id: 'replydate' , header: "Reply Date" , width :60 , inChart :true, chartColor : 'eecc99'}
	  
       
];
 

var __TEST_DATA__=
		[	
<%
List tranList = new ArrayList();
CustomerUtil custutils = new CustomerUtil();
tranList = (List)custutils.getQueryDetail(session.getAttribute("loginID").toString(),"ALL");  
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
			
["<%=(String)v.get(0)%>","<%=(String)v.get(7)%>","<%=(String)v.get(1)%>", "<%=(String)v.get(2)%>", "<%=(String)v.get(3)%>", "<%=(String)v.get(9)%>","<%=(String)v.get(6)%>","<%=(String)v.get(5)%>","<%=(String)v.get(4)%>","<%=(String)v.get(10)%>"]				
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
	width: "900",  //"100%", // 700,
	height: "500",  //"100%", // 330,
	
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
	pageInfo : { pageSize:1000 }
};




var mygrid=new Sigma.Grid( gridOption );
Sigma.Util.onLoad( Sigma.Grid.render(mygrid) );

//////////////////////////////////////////////////////////

</script>
</head>
<body>
	<div align="center">
		 <div id="content">
			    <div id="bigbox" style="margin:15px;display:!none;">
			       <div id="gridbox" style="border:0px solid #cccccc;background-color:#f3f3f3;padding:5px;height:500px;width:1000px;" ></div>
			    </div>
		  </div>
	</div>
</body>
</html>