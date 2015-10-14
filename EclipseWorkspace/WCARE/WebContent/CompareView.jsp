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
     String atype=request.getParameter("type");
     String compare=request.getParameter("Compare");
     
     //System.out.println(rdate);
     //System.out.println(type);
     List tranList = new ArrayList();
     List sitetranList = new ArrayList();
     List wectranList = new ArrayList();
     CustomerUtil secutils = new CustomerUtil();
     int showgrid=0;
        tranList = (List)secutils.getState(custid,rdate,type);
        for (int i=0; i <tranList.size(); i++)
			{
 				Vector v = new Vector();
 				v = (Vector)tranList.get(i);
 				String name = (String)v.get(0);
 				showgrid=1;
				String stateid = (String)v.get(1);
				//strXML +="<I Location='"+name+"'>";
				sitetranList = (List)secutils.getSite(custid,rdate,stateid,type);
		        for (int j=0; j <sitetranList.size(); j++)
					{
		        	Vector v1 = new Vector();
	 				v1 = (Vector)sitetranList.get(j);
	 				String sname = (String)v1.get(0);
	 				
					String siteid = (String)v1.get(1);
					
					strXML +="<tr class=TableRow1 align=left><td><a href=SiteComparison.jsp?compare="+compare+"&stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate+">"+sname+"</td></tr>";
					        	
					wectranList = (List)secutils.getWEC(custid,rdate,siteid,type);
			       		 for (int k=0; k <wectranList.size(); k++)
								{
			        				Vector v2 = new Vector();
		 							v2 = (Vector)wectranList.get(k);
		 							String wecname = (String)v2.get(0);
		 							
									String wecid = (String)v2.get(1);
									strXML +="<tr align=left bgcolor=#D2EBAB><td><a href=WECComparsion.jsp?compare="+compare+"&stateid="+stateid+"&siteid="+siteid+"&wecid="+wecid+"&id="+custid+"&type="+type+"&rd="+rdate+"&name="+wecname.replace(" ","")+">"+wecname+"</td></tr>";
									//System.out.println(wecname);
						
								}
			       		wectranList.clear();
			       	 //  strXML +="</I>";
					
					
					
				}
		        sitetranList.clear();
		         // strXML +="</I>";
			}
          tranList.clear();
          strXML +="</Table>";
	    // strXML +="</B></Body>";
	    // strXML +="<Toolbar Styles='1'/><MenuCfg ShowDeleted='0' AutoSort='0' AutoUpdate='0' Separator1='0' MouseHover='0' ShowDrag='0' ShowPanel='0' ShowIcons='0'/><Panel Copy='0' Move='0' Select='0' Delete='0'/></Grid>";
	    // strXML= strXML.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
   //System.out.println(strXML);
   
   %> 
   
   
    </form>
  
    
       
     <% if(showgrid>0){ %> 
   			 <Table  cellSpacing=0 cellPadding=0 align=center width=40%  border=1>
 		  	<tr><td>    <%=strXML%></td></tr>
       		</Table>
     <% } else{%> 
     <script type="text/javascript">
          alert("Sorry!Data For Selected Date or Month Or Year Not Available.");
         
      </script>
      <jsp:include page="Blank.jsp"/>
 
     <% }%>
     
                 
    </body>
</html>


