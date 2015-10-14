<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<HTML><HEAD>
<%
if (session.getAttribute("loginID") == null)
      {
            response.sendRedirect(request.getContextPath());
      }
%>

<TITLE>Activate Deactivate Status</TITLE>
<META http-equiv=Content-Type content="text/html; charset=windows-1252">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/tablesorter/js/ajax.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>

<script type="text/javascript" src="<%= request.getContextPath()%>/resources/js/jquery.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.scrollabletable.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/jquery.tablesorter.pager.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/resources/tablesorter/js/picnet.table.filter.min.js"></script>

<SCRIPT type=text/javascript> 
           
                lqm_channel = 1;
                lqm_publisher = 302;
                lqm_zone = 1;
                lqm_format = 2;
</SCRIPT>

<SCRIPT src="Table Sorting, Paging and Filtering with jQuery Demo_files/s.js" 
type=text/javascript></SCRIPT>
<LINK 
href="Table Sorting, Paging and Filtering with jQuery Demo_files/style.css" 
type=text/css rel=stylesheet>
<SCRIPT 
src="Table Sorting, Paging and Filtering with jQuery Demo_files/jquery-1.2.6.min.js" 
type=text/javascript></SCRIPT>

<SCRIPT 
src="Table Sorting, Paging and Filtering with jQuery Demo_files/jquery.tablesorter-2.0.3.js" 
type=text/javascript></SCRIPT>

<SCRIPT 
src="Table Sorting, Paging and Filtering with jQuery Demo_files/jquery.tablesorter.filer.js" 
type=text/javascript></SCRIPT>

<SCRIPT 
src="Table Sorting, Paging and Filtering with jQuery Demo_files/jquery.tablesorter.pager.js" 
type=text/javascript></SCRIPT>

<SCRIPT type=text/javascript>
/*       $(document).ready(function() {
            $("#tableOne").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                        .tablesorterPager({ container: $("#pagerOne"), positionFixed: false })
                        .tablesorterFilter({ filterContainer: $("#filterBoxOne"),
                            filterClearContainer: $("#filterClearOne"),
                            filterColumns: [0, 1, 2, 3, 4, 5, 6],
                            filterCaseSensitive: false
                        });

            $("#tableTwo").tablesorter({ debug: false, sortList: [[0, 0]], widgets: ['zebra'] })
                .tablesorterPager({ container: $("#pagerTwo"), positionFixed: false })
                .tablesorterFilter({ filterContainer: $("#filterBoxTwo"),
                    filterClearContainer: $("#filterClearTwo"),
                    filterColumns: [0, 1, 2, 3, 4, 5, 6],
                    filterCaseSensitive: false
                });

            $("#tableTwo .header").click(function() {
                $("#tableTwo tfoot .first").click();
            });
        });  
*/
</SCRIPT>
<% 

	//response.getOutputStream().flush();
	//response.getOutputStream().close();
	//String userid=session.getAttribute("loginID").toString();
	//String Customeridtxt = "";

%>

<script type="text/javascript">

function findApplication() 
{  
	 var req = new XMLHttpRequest();
     var ApplicationId = "";
	 req.onreadystatechange = getReadyStateHandler(req, showAppDetails);	
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");	 
	 req.send("Admin_Input_Type=CUST_GETALLCUSTSTATUS&AppId="+ApplicationId);
}
function showAppDetails(dataXml)
{    
	var cart = dataXml.getElementsByTagName("msgmaster")[0];
	var items = cart.getElementsByTagName("mcode");
	
		var divdetails = document.getElementById("sitedetails");
		divdetails.innerHTML = "";
		// var str = "<table border='1' class=yui id=tableTwo cellpadding='2' cellspacing='1' width='100%'>"
		var str = "<table id='insured_list' class='tablesorter' border='0' cellpadding='2' cellspacing='1' width='100%'>"
		str +="<thead><tr align='center' class='TableTitleRow' height='20'><th class='detailsheading' width='30'>S.N.</th><th class='detailsheading' width='100'>LoginId</th><th class='detailsheading' width='100'>Customer Name</th><th class='detailsheading' width='60'>Email</th><th class='detailsheading' width='100'>Action</th><th class='detailsheading' width='50'>Views</th>"
		str +="</tr></thead><tbody>";
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("lid")[0].firstChild;
	     	var nname1 = item.getElementsByTagName("lid")[0].firstChild.nodeValue;
	     	if (nname != null){
	     		if (I % 2 == 0)
			      {
			        str+="<tr align='center' height='20' class='detailsbody'><td ALIGN='center'>"+(I+1)+"</td>"
			      }
			      else
			      {
			        str+="<tr align='center' height='20' class='detailsbody1'><td ALIGN='center'>"+(I+1)+"</td>"
			      }	     
			    str+="<td class='TableCell1' align='left'>" + item.getElementsByTagName("uid")[0].firstChild.nodeValue + "</td>"		
	     		str+="<td class='TableCell1' align='left'>" + item.getElementsByTagName("name")[0].firstChild.nodeValue + "</td>"
	     		str+="<td class='TableCell1' align='left'>" + item.getElementsByTagName("email")[0].firstChild.nodeValue + "</td>"
	     		var muid=item.getElementsByTagName("uid")[0].firstChild.nodeValue;
	     		
	     	
	     		var sta=item.getElementsByTagName("activated")[0].firstChild.nodeValue;
	     		if(sta==0){
	     		str+="<td class='TableCell' align='left'><input type='button' value='Activate' onClick=acdeactivate('"+nname1+"','"+sta+"')></td>"
	     		}else if(sta==1){
	     		str+="<td class='TableCell' align='left'><input type='button' value='Deactivate' onClick=acdeactivate('"+nname1+"','"+sta+"')></td>"
	     		}
	     		str+="<td class='TableCell' align='left'><input type='button' value='View' onClick=viewDetail('"+muid+"')></td></tr>"
	   	 	}   	 	
		}
		
		divdetails.innerHTML = str+"</tbody></table>";
		
		calldocsortable();
}

function calldocsortable()
{
	$(document).ready(function()     
	{         
		$("#insured_list").tablesorter();     
	} ); 
	
}

function acdeactivate(a,b) 
{  
	 var req = newXMLHttpRequest();
     var nStatus =b+","+a;
     req.onreadystatechange = getReadyStateHandler(req, showAppDetails1);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=CUST_UPDATEACTIVATEDSTATUS&AppId="+nStatus);
}
function showAppDetails1(dataXml)
{    
	var cart = dataXml.getElementsByTagName("msgmasterr")[0];
	var items = cart.getElementsByTagName("mcodee");
		
		for (var I = 0 ; I < items.length ; I++)
	   	{	  	
	     	var item = items[I];
	     	var nname = item.getElementsByTagName("lid")[0].firstChild;
	     	if (nname != null){
	     	alert("Status Updated Successfully");
	     	findApplication(); 
	   	 	}   	 	
		}
		
		
}

function viewDetail(ur,wna)
{

var url="ViewProfile.jsp"+"?uid="+ur+"&name="+wna;

window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
</HEAD>
<BODY onLoad="findApplication()" text=#000000 bottomMargin=0 bgColor=#ffffff leftMargin=0 topMargin=0 rightMargin=0 marginheight="0" marginwidth="0">
 <center>
 <SPAN class=TableTitle><b>Customer Active/Deactive Status</b></SPAN><BR>

	       
						<div id="sitedetails"> </div>
			
</CENTER>
</BODY>
</HTML>

