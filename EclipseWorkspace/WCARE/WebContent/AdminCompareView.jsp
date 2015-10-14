<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*" %>

<html>
    <head>     
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"> </script>
 		<LINK href="<%=request.getContextPath()%>/resources/css/GridReport.css" type=text/css rel=stylesheet> 
    </head>   
    <body>
    <form>
    <input type="hidden" name="custid" value="<%=request.getParameter("id") %>" />
    <input type="hidden" name="custid" value="<%=request.getParameter("rd") %>" />
   <% String strXML = ""; 
     strXML +="<Table  cellSpacing=1 cellPadding=1 align=center width=100% bgColor=#555555 border=1><tr class=TableTitleRow align=left><td class=TableCell align=left Width='700'>Location</td></tr>";
   
     //strXML +="<Grid><Cfg id='DGRView' MainCol='Location'/><Cols><C Name='Location'  Width='700' Type='Html' CanEdit='0'/></Cols><Body><B>";
     String custid=request.getParameter("id");
     String rdate=request.getParameter("rd");
     String type=request.getParameter("type");
     // String atype=request.getParameter("type");
     String compare=request.getParameter("Compare");
     String state=request.getParameter("state");
     String wec=request.getParameter("wec");
     //System.out.println(rdate);
     //System.out.println(type);
     //List tranList = new ArrayList();
     //List sitetranList = new ArrayList();
     //List wectranList = new ArrayList();
     //CustomerUtil secutils = new CustomerUtil();
     //int showgrid=0;
     //System.out.println("custid=="+custid);
     if(custid.equals("0"))
     {
    		strXML +="<tr class=TableRow1 align=left><td><a href=CustomerComparation.jsp?compare="+compare+"&wec="+wec+"&id="+custid+"&type="+type+"&rd="+rdate+">Export Data</td></tr>";
    		//strXML +="<tr class=TableRow1 align=left><td><a href=StateComparation.jsp?compare="+compare+"&wec="+wec+"&id="+state+"&type="+type+"&rd="+rdate+">Export Data</td></tr>";
			
     }
     else
     {    
    	   strXML +="<tr class=TableRow1 align=left><td><a href=StateComparation.jsp?compare="+compare+"&wec="+wec+"&id="+state+"&type="+type+"&rd="+rdate+">Export Data</td></tr>";
		   //strXML +="<tr class=TableRow1 align=left><td><a href=CustomerComparation.jsp?compare="+compare+"&wec="+wec+"&id="+custid+"&type="+type+"&rd="+rdate+">Export Data</td></tr>";
    	  // strXML +="<tr class=TableRow1 align=left><td><a href=StateComparison.jsp?compare="+compare+"&stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+">"+sname+"</td></tr>";
    		
     }
       
          strXML +="</Table>";
	    // strXML +="</B></Body>";
	    // strXML +="<Toolbar Styles='1'/><MenuCfg ShowDeleted='0' AutoSort='0' AutoUpdate='0' Separator1='0' MouseHover='0' ShowDrag='0' ShowPanel='0' ShowIcons='0'/><Panel Copy='0' Move='0' Select='0' Delete='0'/></Grid>";
	    // strXML= strXML.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
   //System.out.println(strXML);
   
   %> 
    <% System.out.println(strXML); %>
   <%=strXML %>
     
    </form>
     
                 
    </body>
</html>


