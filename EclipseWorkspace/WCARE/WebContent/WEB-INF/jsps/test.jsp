<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<sql:query var="rs" dataSource="ecareDS">
		select S_WEC_ID, S_EB_ID from tbl_wec_master
	</sql:query>
	
	<c:forEach var="row" items="${rs.rows}">
    	Id: 	${row.S_WEC_ID}	<br/>
    	Name: 	${row.S_EB_ID}	<br/>
	</c:forEach>
Test Successful
</body>
</html>