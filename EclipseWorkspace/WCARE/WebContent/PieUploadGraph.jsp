<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ include file="FusionCharts.jsp"%>

<script type="text/javascript">
function CloseWin()
{
self.close();
}
</script>
<title>Upload Graph</title>
<BODY onload=init()>
<DIV id=loading 
style="WIDTH: 100%; POSITION: absolute; TOP: 50px; TEXT-ALIGN: center"><IMG 
src="progress.gif" border=0></DIV>
<SCRIPT>
var ld=(document.all);

var ns4=document.layers;
var ns6=document.getElementById&&!document.all;
var ie4=document.all;

if (ns4)
	ld=document.loading;
else if (ns6)
	ld=document.getElementById("loading").style;
else if (ie4)
	ld=document.all.loading.style;

function init()
{
if(ns4){ld.visibility="hidden";}
else if (ns6||ie4) ld.display="none";
}
</SCRIPT>

<%  
String pdata=request.getParameter("pdata");
String updata=request.getParameter("updata");
String bdata=request.getParameter("bdata");
int tdata=Integer.parseInt(pdata)+Integer.parseInt(updata)+Integer.parseInt(bdata);
 String strXML1="<chart caption='Total WEC Upload Status'  subcaption='Total WEC="+tdata+"(100%25)' showValuesInToolTip ='1' showPercentInToolTip ='0' showLabels='1' decimals='0' formatNumberScale='0' bgColor='454646,DFDFDF' showPercentageValues='1' enableRotation='1' baseFontColor='135B5E'  pieBorderColor='FFFFFF'>";

	 strXML1+="<set label='Published' value='"+pdata+"' color='C7D285' />";
	 strXML1+="<set label='Unpublished' value='"+updata+"' color='AFD8F8' />";
	 strXML1+="<set label='Upload Balance' value='"+bdata+"' color='F6BD0F' />";
	 
 
 
 strXML1+="</chart>";
 String chartCode1= createChartHTML("Pie2D.swf","", strXML1, "chart14", 500, 350, false);
	

%>
<form>
<table border="1">
<tr><TD  align="CENTER" ><input type="button" value="Close This Window" onClick="CloseWin()"></TD></tr>

<tr>
<TD  align="CENTER"> <%=chartCode1%> </TD>
     
</tr>
<tr><TD  align="CENTER" ><input type="button" value="Close This Window" onClick="CloseWin()"></TD></tr>
</table>
</form>
</body>


