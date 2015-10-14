<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<html>
    <body>
    <%
    session.removeAttribute("loginID");		
    request.getSession(false).invalidate();
	response.sendRedirect(request.getContextPath());
%>
   </body>
</html>