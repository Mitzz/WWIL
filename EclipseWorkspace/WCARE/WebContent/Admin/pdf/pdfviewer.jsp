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

<%@ page contentType="text/html;charset=windows-1252"%>

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
<link href="mytable.css" rel=stylesheet>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 
<script type="text/javascript" src="table.js"></script> 
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


<script type="text/javascript">


function myFunction()
{
var url=document.forms[0].pdfpath.value;
window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}








</script>


</head>
<body onLoad="myFunction()">





<div align="center">

 
  
   		<table width="550" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
					<html:errors />
					<%String id=(String)request.getParameter("id");%>	
							
   				</td>
   			</tr>
   			
   		</table>
   		
   	


   		  
		<form  method="post" name="frmPdfViewer">
			<table width="550" cellspacing="0" cellpadding="0">				
				
				<tr class="tabtextnormal">
					<td height="20" align="center" colspan="3">
							
						<input type="hidden" name="tbcontent"  value="" />	
						<input type="hidden" name="pdfpath" value="<%=id%>" />		
						
						
					</td>
				</tr>										
			</table>
		</form>		
		
   
</div>



</body>
</html>