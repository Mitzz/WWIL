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
<%@page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.enercon.admin.dao.*" %>
<%@ page import="java.math.BigDecimal" %>
<html>
    <head>
      <TITLE>Customer Report</TITLE>
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"> </script>
        <LINK href="<%=request.getContextPath()%>/resources/css/GridReport.css" type=text/css rel=stylesheet>
 <script>  

function myFunction(stateid,siteid,custid,type,rdate)
{

var url="CustomerAdminReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate;
window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');

}
</script>
    </head>
   
    <body  >
    <form>
    <input type="hidden" name="custid" value="<%=request.getParameter("id") %>" />
    <input type="hidden" name="rdate" value="<%=request.getParameter("rd") %>" />
   <% String strXML = ""; 
   
   //  strXML +="<Grid><Cfg id='DGRView' MainCol='Location'/><Cols><C Name='Location'  Width='150' Type='Html' CanEdit='0'/><C Name='Generation'/><C Name='HRS' Width='150'/><C Name='LullHrs'/><C Name='MFault'/><C Name='MSD'/><C Name='GIFault'/><C Name='GISD'/><C Name='GEFault'/><C Name='GESD'/><C Name='CFactor'/><C Name='MAvail'/><C Name='GIAvail'/><C Name='GAvail'/><C Name='WAvail'/></Cols><Body><B>";
     
     strXML +="<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1><tr class=TableTitleRow><td class=TableCell11>Location</td><td class=TableCell11>Generation</td><td class=TableCell11>HRS</td><td class=TableCell11>LullHRS</td><td class=TableCell11>MFault</td><td class=TableCell11>MSD</td><td class=TableCell11>GIFault</td>   <td class=TableCell11>GISD</td><td class=TableCell11>GEFault</td><td class=TableCell11>GESD</td><td class=TableCell11>Capacity Factor</td>  <td class=TableCell11>MAvail</td><td class=TableCell11>GIAvail</td><td class=TableCell11>GAvail</td><td class=TableCell11>WAvail</td> <td class=TableCell11>WEC_SPSD</td><td class=TableCell11>EB_SPSD</td> </tr>";
      
     String custid=request.getParameter("id");
     String fd=request.getParameter("fd");
     String td=request.getParameter("td");
    
    
     //System.out.println(rdate);
     //System.out.println(type);
     List tranList = new ArrayList();
     List sitetranList = new ArrayList();
     CustomerUtil secutils = new CustomerUtil();
    
       tranList.clear();
        tranList = (List)secutils.getStateTotalAdmin(custid,fd+","+td,"YD");
        int cnt=0;
        for (int i=0; i <tranList.size(); i++)
			{
 				Vector v = new Vector();
 				v = (Vector)tranList.get(i);
 				String name = (String)v.get(0);
 				
				String stateid = (String)v.get(13);
				//strXML +="<I Location='"+name+"' Generation='"+v.get(1)+"' HRS='"+v.get(2)+"' LullHrs='"+v.get(3)+"' MFault='"+v.get(4)+"'  MSD='"+v.get(5)+"'  GIFault='"+v.get(6)+"' GISD='"+v.get(7)+"' GEFault='"+v.get(8)+"' GESD='"+v.get(9)+"' CFactor='"+v.get(12)+"' MAvail='"+v.get(10)+"'  GIAvail='"+v.get(14)+"' GAvail='"+v.get(11)+"'   WAvail='"+v.get(15)+"' >";
					   
				sitetranList = (List)secutils.getSiteTotalAdmin(custid,fd+","+td,stateid,"YD");
		        for (int j=0; j <sitetranList.size(); j++)
					{
		        	    Vector v1 = new Vector();
	 					v1 = (Vector)sitetranList.get(j);
	 				   String sname = (String)v1.get(0);
	 				   String siteid = (String)v1.get(13);
						strXML +="<tr class=TableRow2><td class=TableCell><a href=CustomizeAdminReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type=YD&rd="+fd+","+td+">"+sname+"</td><td class=TableCell>"+v1.get(1)+"</td><td class=TableCell>"+v1.get(2)+"</td><td class=TableCell>"+v1.get(3)+"</td><td class=TableCell>"+v1.get(4)+"</td><td class=TableCell>"+v1.get(5)+"</td><td class=TableCell>"+v1.get(6)+"</td>   <td class=TableCell>"+v1.get(7)+"</td><td class=TableCell>"+v1.get(8)+"</td><td class=TableCell>"+v1.get(9)+"</td><td class=TableCell>"+v1.get(12)+"</td>  <td class=TableCell>"+v1.get(10)+"</td><td class=TableCell>"+v1.get(14)+"</td><td class=TableCell>"+v1.get(11)+"</td> <td class=TableCell>"+v1.get(15)+"</td><td class=TableCell>"+v1.get(16)+"</td><td class=TableCell>"+v1.get(17)+"</td></tr>";	
				  }
		        strXML +="<tr class=TableRow1><td class=TableCell><b>"+name+"</b></td><td class=TableCell><b>"+v.get(1)+"</b></td><td class=TableCell><b>"+v.get(2)+"</td><td class=TableCell><b>"+v.get(3)+"</td><td class=TableCell>"+v.get(4)+"</td><td class=TableCell><b>"+v.get(5)+"</b></td><td class=TableCell><b>"+v.get(6)+"</b></td>   <td class=TableCell><b>"+v.get(7)+"</b></td><td class=TableCell><b>"+v.get(8)+"</b></td><td class=TableCell><b>"+v.get(9)+"</b></td><td class=TableCell><b>"+v.get(12)+"</b></td>  <td class=TableCell><b>"+v.get(10)+"</b></td><td class=TableCell><b>"+v.get(14)+"</b></td><td class=TableCell><b>"+v.get(11)+"</b></td> <td class=TableCell><b>"+v.get(15)+"</b></td><td class=TableCell><b>"+v.get(16)+"</b></td><td class=TableCell><b>"+v.get(17)+"</b></td> </tr>";
				  
		        sitetranList.clear();
		        cnt=cnt+1;
		       
			}
          tranList.clear();
         
          if(cnt>1)
          {
          tranList = (List)secutils.getOverallTotalAdmin(custid,fd+","+td,"YD");
          
          for (int i=0; i <tranList.size(); i++)
     		{
       		Vector v = new Vector();
       		v = (Vector)tranList.get(i);
       		String name = (String)v.get(0);
       		
       	//	showgrid=1;
       		strXML +="<tr class=TableSummaryRow><td class=TableCell>"+name+"</td><td class=TableCell>"+v.get(1)+"</td><td class=TableCell>"+v.get(2)+"</td><td class=TableCell>"+v.get(3)+"</td><td class=TableCell>"+v.get(4)+"</td><td class=TableCell>"+v.get(5)+"</td><td class=TableCell>"+v.get(6)+"</td>   <td class=TableCell>"+v.get(7)+"</td><td class=TableCell>"+v.get(8)+"</td><td class=TableCell>"+v.get(9)+"</td><td class=TableCell>"+v.get(12)+"</td>  <td class=TableCell>"+v.get(10)+"</td><td class=TableCell>"+v.get(13)+"</td><td class=TableCell>"+v.get(11)+"</td> <td class=TableCell>"+v.get(14)+"</td> <td class=TableCell>"+v.get(15)+"</td> <td class=TableCell>"+v.get(16)+"</td> </tr>";
       	     
            // strXML +="<I Location='"+name+"' Generation='"+v.get(1)+"' HRS='"+v.get(2)+"' LullHrs='"+v.get(3)+"' MFault='"+v.get(4)+"'  MSD='"+v.get(5)+"'  GIFault='"+v.get(6)+"' GISD='"+v.get(7)+"' GEFault='"+v.get(8)+"' GESD='"+v.get(9)+"'  CFactor='"+v.get(12)+"' MAvail='"+v.get(10)+"'  GIAvail='"+v.get(13)+"' GAvail='"+v.get(11)+"'   WAvail='"+v.get(14)+"'>";
     		}
          
         
          tranList.clear();
          }
          strXML +="</Table>";
          //strXML +="</I>";
	     //strXML +="</B></Body>";
	    // strXML +="<Toolbar Styles='1'/><MenuCfg ShowDeleted='0' AutoSort='0' AutoUpdate='0' Separator1='0' MouseHover='1' ShowDrag='1' ShowPanel='1' ShowIcons='1'/><Panel Copy='0' Move='0' Select='0' Delete='0'/></Grid>";
	     //strXML= strXML.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
   //System.out.println(strXML);
   
   %> 
   
   
    </form>
   <% if(cnt>0){ %> 
    
        <div style="width:100%;height:100%;"> 
 		       <%=strXML%>
 		    
 		      
 		       
 		     
        </div>
     <% } else{%> 
          <script type="text/javascript">
          alert("Sorry!Data For Selected Date or Month Or Year Not Available.");
         
          </script>
    
 
     <% }%>
     
                 
    </body>
</html>
