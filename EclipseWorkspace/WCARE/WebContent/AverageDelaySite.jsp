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

<%@ page import="java.util.*"%>
<%@ include file="FusionCharts.jsp"%>

<html>

    <head>
      <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
     <title>Site Wise - Average Delay</title>
       <script language="JavaScript" href="FusionCharts.js"></script>
    </head>
   
    <body>
    <form>
    
   <%   
   String id=request.getParameter("id");
   String fdate=request.getParameter("fdate");
   String tdate=request.getParameter("tdate"); 
   List tranList = new ArrayList();

   CustomerUtil secutils = new CustomerUtil();
  
   String strXML = "";
  
	//strXML +="<chart caption='Daily Visits' subcaption='(from 8/6/2006 to 8/12/2006)' lineThickness='1' showValues='0' formatNumberScale='0' anchorRadius='2'   divLineAlpha='20' divLineColor='CC3300' divLineIsDashed='1' showAlternateHGridColor='1' alternateHGridAlpha='5' alternateHGridColor='CC3300' shadowAlpha='40' labelStep='2' numvdivlines='5' chartRightMargin='35' bgColor='FFFFFF,CC3300' bgAngle='270' bgAlpha='10,10'>";

	//strXML +="<chart palette='2' caption='"+s+"' yaxisname='Revenue (Millions)' hovercapbg='FFFFFF' toolTipBorder='889E6D' divLineColor='999999' divLineAlpha='80' showShadow='0' canvasBgColor='FEFEFE' canvasBaseColor='FEFEFE' canvasBaseAlpha='50' divLineIsDashed='1' divLineDashLen=1' divLineDashGap='2' numberPrefix='$' numberSuffix='M' chartRightMargin='30' useRoundEdges='1' legendBorderAlpha='0'>";
	strXML +="<chart labelDisplay='Rotate' slantLabels='1' caption='Scheduled Upload Time - 10.30 AM DAILY' subCaption='Site Wise Average Delay Time' xAxisName='Site' yAxisName='Hours' showValues='1' decimals='2' formatNumber='0' formatNumberScale='0'  palette='1'>";

	
	//strXML +="<chart palette='1' caption='Average Delay-Site Wise' shownames='1' showvalues='0' decimals='0' numberPrefix='' useRoundEdges='1' legendBorderAlpha='0' formatNumberScale='0'>";
	//strXML1 +="<chart palette='1' caption='M.A.,G.A. and Capacity Factor' shownames='1' showvalues='0'  sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='(in Percentage)' SYAxisName='(in Percentage)'  numDivLines='4' formatNumberScale='0'>";
	//strXML2 +="<chart palette='2' formatNumberScale='0' caption='Generation and Lull Hrs' shownames='1' showvalues='0'   showSum='1' decimals='0' useRoundEdges='1'>";
	
	
	//strXML +="<categories>";
	//strXML1 +="<categories>";
	//strXML2 +="<categories>";
	String state="";
      tranList = (List)secutils.getSiteWiseAverage(id,fdate,tdate);
      for (int i=0; i <tranList.size(); i++)
			{
				Vector v = new Vector();
				v = (Vector)tranList.get(i);
				String name = (String)v.get(0);
				
				//String stateid = (String)v.get(13);
				//strXML +="<I Location='"+name+"' Generation='"+v.get(1)+"' HRS='"+v.get(2)+"' LullHrs='"+v.get(3)+"' MFault='"+v.get(4)+"'  MSD='"+v.get(5)+"'  GIFault='"+v.get(6)+"' GISD='"+v.get(7)+"' GEFault='"+v.get(8)+"' GESD='"+v.get(9)+"' CFactor='"+v.get(12)+"' MAvail='"+v.get(10)+"'  GIAvail='"+v.get(14)+"' GAvail='"+v.get(11)+"'   WAvail='"+v.get(15)+"' >";
					   
				
		      //  strXML +="<tr class=TableRow1><td class=TableCell><b>"+name+"</b></td><td class=TableCell><b>"+v.get(1)+"</b></td><td class=TableCell><b>"+v.get(2)+"</td><td class=TableCell><b>"+v.get(3)+"</td><td class=TableCell>"+v.get(4)+"</td><td class=TableCell><b>"+v.get(5)+"</b></td><td class=TableCell><b>"+v.get(6)+"</b></td>   <td class=TableCell><b>"+v.get(7)+"</b></td><td class=TableCell><b>"+v.get(8)+"</b></td><td class=TableCell><b>"+v.get(9)+"</b></td><td class=TableCell><b>"+v.get(12)+"</b></td>  <td class=TableCell><b>"+v.get(10)+"</b></td><td class=TableCell><b>"+v.get(14)+"</b></td><td class=TableCell><b>"+v.get(11)+"</b></td> <td class=TableCell><b>"+v.get(15)+"</b></td> </tr>";
		        strXML +="<set label='"+name+"' value='"+v.get(1)+"' />"; 
		        state=v.get(2).toString();
		        //strXML1 +="<category label='"+name+"' />"; 
		        //strXML2 +="<category label='"+name+"' />"; 
			}
      //strXML +="</categories>";
     // strXML1 +="</categories>";
     // strXML2 +="</categories>";
    //  strXML +="<dataset seriesName='Delay time' color='8EAC41' showValues='0'>";
     // for (int i=0; i <tranList.size(); i++)
	//	{
	//			Vector v = new Vector();
	//			v = (Vector)tranList.get(i);
				//String id = (String)v.get(0);
				
			//String stateid = (String)v.get(13);
	//	    strXML +="<set value='"+v.get(1)+"'/>";
	        
	        //strXML +="</I>";
	//	}
  //strXML +="</dataset>";
  
 
//strXML +="<styles><definition><style name='CaptionFont' type='font' size='12'/></definition><application><apply toObject='CAPTION' styles='CaptionFont' /><apply toObject='SUBCAPTION' styles='CaptionFont' /></application></styles>";


int wdt=60*tranList.size();
if(wdt<500)
{
	wdt=500;	
}

strXML +="</chart>";


String chartCode="";
chartCode= createChartHTML("Column3D.swf","", strXML, "chart14",wdt, 350, false);  
   
   %> 
    
  
    
  <TABLE cellSpacing=1 cellPadding=2 width="100%" border=1>
				<TBODY>
				<TR>
				<TD COLSPAN="2" ALIGN="CENTER">  <b>
  <%=state%> - Selected Date: <%=fdate %> To  <%=tdate %></b> </TD></TR>
   <TR>
				<TD COLSPAN="2" ALIGN="CENTER"> <%=chartCode%> </TD>
				</TR>
				<TR class=TableTitleRow>
						<TD class=TableCell width="14.28%">Site Name</TD>

						<TD class=TableCell width="14.28%">Average Delay Time(In Hrs.)</TD>
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
					<TD class=TableCell ALIGN="LEFT"><%=v.get(0)%></TD>
				    <TD class=TableCell><%=v.get(1)%></TD>
				    </TR>
				    <%}
					 
					 tranList.clear();
					 %>
					   <TR class=<%=cls%>>  <td colspan="2">Export To SiteWise:<a href=ExportAverageDelaySite.jsp?id=<%=id %>&tdate=<%=tdate%>&fdate=<%=fdate%> Border="0"><img src="<%=request.getContextPath()%>/resources/images/excel.gif" border="0"></a>
						</td>   </TR>
						
				    </TBODY>
				    </TABLE>
              
  
   </form>
     
                 
    </body>
</html>
