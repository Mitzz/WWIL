<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.security.utils.SecurityUtils" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
        <%
			String newsList = "";
		    /*  Getting the Transaction of the User from the Session. */
		    SecurityUtils secutils = new SecurityUtils();
		    newsList = secutils.getNewsList();
			////
	     %>
	     <tr>
	     		<td class="high">
					<%=newsList%>
				</td>
		</tr>
		<%
		//}
     	%>
</body>
</html>