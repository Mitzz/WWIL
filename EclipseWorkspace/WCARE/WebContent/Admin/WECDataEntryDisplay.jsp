<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<head>
<%
if (session.getAttribute("loginID") == null)
{
     response.sendRedirect(request.getContextPath());
}
%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajaxnew.js"></script>
<title>WEC Reading</title>
<%
String siteid = request.getParameter("siteid").toString(); 
String date = request.getParameter("date").toString();
String type = request.getParameter("type").toString();
%>
<script type="text/javascript">
function get_page() 
{
	 var req = newXMLHttpRequest();
     var ApplicationId =document.getElementById("SiteIdtxt").value+","+document.getElementById("Datetxt").value;
	 req.onreadystatechange = getReadyStateHandler(req, showData,"","");
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findWECPaged&AppId="+ApplicationId);
}
function showData(dataXml,a,b)
{
	var cart = dataXml.getElementsByTagName("wecpagemaster")[0];
	var items = cart.getElementsByTagName("pages");	
	var divdetails = document.getElementById("paging");
	divdetails.innerHTML = "";
	var str = "";
	for (var I = 0 ; I < items.length ; I++)
   	{	  	
     	var item = items[I];
     	var nname = item.getElementsByTagName("pagestart")[0].firstChild;
     	if (nname != null)
     	{
     		if (item.getElementsByTagName("pagestart")[0].firstChild.nodeValue != "." || item.getElementsByTagName("pageend")[0].firstChild.nodeValue != ".")
     		{     		
	   			str+="&nbsp;<a href='WECEntryForm.jsp?siteid=<%=siteid %>&date=<%=date %>&type=<%=type %>&strt="+item.getElementsByTagName("pagestart")[0].firstChild.nodeValue+"&endd="+item.getElementsByTagName("pageend")[0].firstChild.nodeValue+"' target='weciframe'>" + (I + 1) + "</a>&nbsp;";
	   		}
   	 	}
	}		
	divdetails.innerHTML = str;
}
</script>
</head>
<body onload="get_page()">
<%
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
java.util.Date adate= dateFormat.parse(date);

String prevdate = dateFormat.format(adate.getTime() - MILLIS_IN_DAY);
String crdate = dateFormat.format(adate.getTime() - MILLIS_IN_DAY);
//String ardate = dateFormat.format(date.getTime());
String nextdate = dateFormat.format(adate.getTime() + MILLIS_IN_DAY);
%>
<table>
		<tr>
			<td colspan="2" align="left"><input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="WECDataEntryDisplay.jsp?siteid=<%=siteid %>&date=<%=prevdate %>&type=<%=type %>&strt=1&endd=5"></TD>
			<td colspan="4" align="center"><b><font size="4">Reading Date:<%=date %></font></b></td>
			<td colspan="2"  align="right"><input type="button"  class="printbutton"  style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="WECDataEntryDisplay.jsp?siteid=<%=siteid %>&date=<%=nextdate %>&type=<%=type %>&strt=1&endd=5"></TD>
   		</TR>
   	</table>
<iframe align="middle" id="weciframe" name="weciframe" width="100%" height="650" 
style="BORDER-RIGHT:0px; BORDER-TOP:0px; BORDER-LEFT:0px; BORDER-BOTTOM:0px"
frameborder="0" src="WECEntryForm.jsp?siteid=<%=siteid %>&date=<%=date %>&type=<%=type %>&strt=1&endd=5"></iframe>
<input type="hidden" name="SiteIdtxt" id="SiteIdtxt" value="<%=siteid %>" />
<input type="hidden" name="Datetxt" id="Datetxt" value="<%=date %>" />
<input type="hidden" name="Typetxt" id="Typetxt" value="<%=type %>" />
<div id="paging" align="center">
	
</div>	
</body>
</html>