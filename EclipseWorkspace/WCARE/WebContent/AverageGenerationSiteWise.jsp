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

<%@ page import="java.util.*"%>
<%@ include file="FusionCharts.jsp"%>

<html>

    <head>
       <link href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
       <title>Site Wise - Average Generation</title>       
    </head>
   
    <body>
	    <form>	    
		   <%   
			   String id=request.getParameter("id");
			   String fdate=request.getParameter("fdate");
			   String tdate=request.getParameter("tdate");
			   String gen=request.getParameter("gen");
			 
			   List tranList = new ArrayList();
			   List tranListWECTypt = new ArrayList();
			   CustomerUtil secutils = new CustomerUtil();
			  
			   String strXML = "";
			   String strXML1 = "";
			  
			   if(gen.equals("0")){
			   		strXML +="<chart labelDisplay='Rotate' slantLabels='1' caption='Site Wise Generation' xAxisName='Site' yAxisName='Hours' showValues='1' decimals='2' formatNumber='0' formatNumberScale='0'  palette='1'>";
			   		strXML1 +="<chart labelDisplay='Rotate' slantLabels='1' caption='WEC Type Wise Generation' xAxisName='Site' yAxisName='Hours' showValues='1' decimals='2' formatNumber='0' formatNumberScale='0'  palette='1'>";
			   }
			   else {
				    strXML +="<chart labelDisplay='Rotate' slantLabels='1' caption='Site Wise Average Generation' xAxisName='Site' yAxisName='Hours' showValues='1' decimals='2' formatNumber='0' formatNumberScale='0'  palette='1'>";
				    strXML1 +="<chart labelDisplay='Rotate' slantLabels='1' caption='WEC Type Wise Average Generation' xAxisName='Site' yAxisName='Hours' showValues='1' decimals='2' formatNumber='0' formatNumberScale='0'  palette='1'>";
			   }
			   			
			
			   String state="";
			   if(gen.equals("0"))
			   {
			      tranList = (List)secutils.getSiteWiseAverageGeneration(id,fdate,tdate);
			      tranListWECTypt = (List)secutils.getWECTypeWiseAverageGeneration(id,fdate,tdate);
			   }
			   else if(gen.equals("1"))
			   {
				  tranList = (List)secutils.getSiteWiseAverageGenerationAvg(id,fdate,tdate);
				  tranListWECTypt = (List)secutils.getWECTypeWiseAverageGenerationAvg(id,fdate,tdate);
			   }
			      for (int i=0; i <tranList.size(); i++)
						{
							Vector v = new Vector();
							v = (Vector)tranList.get(i);
							String name = (String)v.get(0);
							
					        strXML +="<set label='"+name+"' value='"+v.get(1)+"' />"; 
					        state=v.get(2).toString();
					        
						}
			
			 int wdt=60*tranList.size();
			 if(wdt<500)
			 {
					wdt=500;	
			 }
			
			 strXML +="</chart>";
			
			 String chartCode="";
			 chartCode= createChartHTML("Column3D.swf","", strXML, "chart14",wdt, 350, false);  
			 
			 for (int i=0; i <tranListWECTypt.size(); i++)
				{
					Vector v = new Vector();
					v = (Vector)tranListWECTypt.get(i);
					String name = (String)v.get(0);
					
			        strXML1 +="<set label='"+name+"*"+v.get(3)+"' value='"+v.get(1)+"' />"; 
			        state=v.get(2).toString();
			        
				}
	
			 int wdt1=60*tranListWECTypt.size();
			 if(wdt1<500)
			 {
					wdt1=500;	
			 }
			
			 strXML1 +="</chart>";
			
			 String chartCode1="";
			 chartCode1= createChartHTML("Column3D.swf","", strXML1, "chart14",wdt1, 350, false);  
		 %> 
		    
		  <TABLE cellSpacing=1 cellPadding=2 width="100%" border=1>
				<TBODY>
					<TR>
						<TD COLSPAN="2" ALIGN="CENTER">  <b>
	  						<%=state%> - Selected Date: <%=fdate %> To  <%=tdate %></b> 
	  					</TD>
	  				</TR>	  				
	   				<TR>
							<TD COLSPAN="2" ALIGN="LEFT"> <%=chartCode%> </TD>
					</TR>					
					<TR>
							<TD COLSPAN="2" ALIGN="Left"> <%=chartCode1%> </TD>
					</TR>
					<TR><td class="TableRow1" colspan="2">Export To SiteWise:<a href=ExportAverageGenerationSite.jsp?id=<%=id %>&tdate=<%=tdate%>&fdate=<%=fdate%>&gen=<%=gen%>&state=<%=state%> Border="0"><img src="<%=request.getContextPath()%>/resources/images/excel.gif" border="0"></a></td></TR>											
					<TR class=TableTitleRow>
							<TD class=TableCell width="14.28%">Site Name</TD>	
							<% if(gen.equals("0")) {%>
							<TD class=TableCell width="14.28%">Generation</TD>
							<% }else { %>
							<TD class=TableCell width="14.28%">Average Generation</TD>
							<% } %>
					</TR>	
						 <%  String cls="";
						 for (int i=0; i <tranList.size(); i++)
							{
								Vector v = new Vector();
								v = (Vector)tranList.get(i);
							    int rem = 1;
								rem = i % 2;
	
								if (rem == 0)
									cls = "TableRow2";
								else
									cls = "TableRow1";
						%>
					<TR class=<%=cls%>>
						<TD class=TableCell><%=v.get(0)%></TD>
					    <TD class=TableCell><%=v.get(1)%></TD>
					</TR>
					    <%}						 
						 	tranList.clear();
						 %>							
				</TBODY>
			</TABLE>
	   </form>                 
    </body>
</html>
