<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
 
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>

<%@ page import="java.util.*" %>

<%@ include file="FusionCharts.jsp"%>

<script type="text/javascript">
function CloseWin()
{
self.close();
}
</script>
<title>Upload Graph</title>
<body>
<%  
 	 String ardate=request.getParameter("rdate");
	 ardate="02/07/2009";
	 CustomerUtil secUtil = new CustomerUtil();
	 List uploadList = new ArrayList();
	 uploadList = (List)secUtil.getUploadStatus("",ardate);
	
	 
	String strXML = "";
	strXML +="<chart palette='1' caption='WEC Uploaded Status' shownames='1' showvalues='0' numberPrefix='' sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='Upload Data' SYAxisName='Publish Data'  numDivLines='4' formatNumberScale='1'>";
	
	int totalwec=0,tlupload=0,tlbal=0,tlpublish=0;
  	
  	strXML +="<categories>";
  	
  	
  	for (int i=0; i <uploadList.size()-1; i++)
      {
  		 Vector v = new Vector();
  		 v = (Vector)uploadList.get(i);
  		strXML +="<category label='"+v.get(0)+"' />";
      }
  	strXML +="</categories>";
  
	strXML +="<dataset seriesName='Uploaded Data' color='8EAC41' showValues='0'>";
  	for (int i=0; i <uploadList.size()-1; i++)
    {
		 Vector v = new Vector();
		 v = (Vector)uploadList.get(i);
		strXML +="<set value='"+v.get(2)+"' />";
		
		
		
    }
  	strXML +="</dataset>";
	
	
  	
  	strXML +="<dataset seriesName='Balanced Data' color='F6BD0F' showValues='0'>";
  	for (int i=0; i <uploadList.size()-1; i++)
    {
		 Vector v = new Vector();
		 v = (Vector)uploadList.get(i);
		strXML +="<set value='"+v.get(3)+"' />";
		
		
	    }
  	strXML +="</dataset>";
	
	strXML +="<dataset seriesName='Published Data' color='607142' showValues='0' parentYAxis='S' >";
	for (int i=0; i <uploadList.size()-1; i++)
    {
		 Vector v = new Vector();
		 v = (Vector)uploadList.get(i);
		strXML +="<set value='"+v.get(5)+"' />";
		
		
	    }
  	strXML +="</dataset></chart>";
	String chartCode="";
	chartCode= createChartHTML("myChart.swf","", strXML, "chart13", 700, 400, false);
	

 String strXML1="<chart caption='Total WEC Upload Status' xAxisName='Status' yAxisName='WEC No' showValues='0' decimals='0' formatNumberScale='0'>";
 for (int i=uploadList.size()-1; i <uploadList.size(); i++)
 {    Vector v = new Vector();
 	 v = (Vector)uploadList.get(i);
	 strXML1+="<set label='Total WEC' value='"+v.get(1)+"' />";
	 strXML1+="<set label='Uploaded WEC' value='"+v.get(2)+"' />";
	 strXML1+="<set label='Balanced WEC' value='"+v.get(3)+"' />";
	 strXML1+="<set label='Published WEC' value='"+v.get(5)+"' />";
 }
 
 strXML1+="</chart>";
 String chartCode1= createChartHTML("Column3D.swf","", strXML1, "chart14", 300, 400, false);
	
 System.out.println(strXML1); 
 
 System.out.println(chartCode);
%>
<form>
<table border="1">
<tr><TD colspan="3" align="CENTER" style='font-family:arial;font-size:18px;color:#000000';font-weight: bold;'>Summary WEC Data Uploaded Detail</TD></tr>
<tr><TD colspan="3" align="CENTER" ><input type="button" value="Close This Window" onClick="CloseWin()"></TD></tr>

<tr>
<TD  align="CENTER"> <%=chartCode1%> </TD><TD COLSPAN="2" align="CENTER"> <%=chartCode%> </TD>
     <%
   
     
     %>
</tr>
</table>
</form>
</body>
