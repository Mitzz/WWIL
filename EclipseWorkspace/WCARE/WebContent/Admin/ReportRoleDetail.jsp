<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
	response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>
<%@page contentType="application/vnd.ms-excel"%><%@page pageEncoding="UTF-8"%><%
request.setCharacterEncoding("utf-8");
String file = request.getParameter("File"); 
if(file==null || file.equals("")) file="AuthDetail.xls";
response.addHeader("Content-Disposition","attachment; filename=\""+file+"\"");%>
<%@ page import="com.enercon.admin.util.AdminUtil"%>
<%@ page import="com.enercon.admin.dao.AdminDao"%>

<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Calendar"%>


<HTML>
<HEAD>



<%
	response.setHeader("Pragma", "no-cache");
	response.getOutputStream().flush();
	response.getOutputStream().close();
	String userid = session.getAttribute("loginID").toString();
	String Customeridtxt = "";
%>

</HEAD>
<BODY >

<%
	String roleid = request.getParameter("RoleNametxt");
	
	
	
	
	
%>







<%
	List tranList = new ArrayList();
	DecimalFormat df = new DecimalFormat("0.##");
	AdminUtil adminutils = new AdminUtil();
	tranList = (List) adminutils.getAuthDetail(roleid); 
	String cls = "TableRow1";
	
	
	
	int j=0;
	//int i=0;
	%>


			<TABLE width="100%" border="1">
				<TBODY>
				<TR >
					    <TD  colspan="6" align="center"> <b>Authorization Detail</b></TD>
						
						
						
					</TR>
                	<TR >
					    <TD align="center" width="2.28%"><b>Sr no.</b></TD>
						<TD align="center" width="5.00%"><b>User ID</b></TD>
						<TD align="center" width="5.00%"><b>Password</b></TD>
						<TD  align="center" width="2.00%"><b>Login Type</b></TD>
						<TD  align="center" width="11.00%"><b>Login Description</b></TD>
						<TD  align="center" width="5.28%"><b>Role</b></TD>
						
						
					</TR>




					<%
					 for (int i=0; i <tranList.size(); i++)
					  {	
						   Vector v = new Vector();
						   v = (Vector)tranList.get(i);
							
					%>
					<TR>
						<TD align="center"><%=i+1%></TD>
						<TD align="center"><%=v.get(0)%></TD>
						
						<TD align="center"><%=v.get(1)%></TD>
						<TD align="center"><%=v.get(2)%></TD>
						<TD align="center"><%=v.get(3)%></TD>
						<TD align="center"><%=v.get(4)%></TD>
					
					</TR>
				






<%
			j++;
						
	}	tranList.clear();
	
	
   %>

  
				</TBODY>
			</TABLE>
	



</BODY>
</HTML>
