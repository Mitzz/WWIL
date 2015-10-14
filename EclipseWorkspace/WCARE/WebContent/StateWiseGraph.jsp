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
<%@ page import="java.text.*" %>

<%@ include file="FusionCharts.jsp"%>
<% 
int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

String fdate=request.getParameter("fdate")==null?dateFormat.format(date.getTime() - MILLIS_IN_DAY):request.getParameter("fdate");  
String tdate = request.getParameter("tdate")==null?dateFormat.format(date.getTime() - MILLIS_IN_DAY):request.getParameter("tdate");
%>
<html>
    <head>
       <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
       <LINK href="<%=request.getContextPath()%>/resources/resources\deluxe-menu.files/style.css" type=text/css rel=stylesheet>
       
       <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 
	   <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtree.js"></script>
	   <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtabs.js"></script>
       
       <script language="JavaScript" href="<%=request.getContextPath()%>/resources/js/FusionCharts.js"></script>
       
 	   <script>  
			function myFunction(stateid,siteid,custid,type,rdate)
			{
			
			var url="CustomerAdminReport.jsp?stateid="+stateid+"&siteid="+siteid+"&id="+custid+"&type="+type+"&rd="+rdate;
			window.open(url,'name','height=600,width=800, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
			
			}
	  </script>
	  <script language="JavaScript">
                
                /** 
                 * updateChart method is invoked when the user clicks on a pie slice.
                 * In this method, we get the index of the factory after which we 
                 * request for XML data
                 * for that that factory from FactoryData.asp, and finally
                 * update the Column Chart.
                 *    @param    factoryIndex    Sequential Index of the factory.
                */        
                function siteWiseGenChart(stateid,gen){         
                    //DataURL for the chart                    
                    var fdate=document.forms[0].FromDatetxt.value;
					var tdate=document.forms[0].ToDatetxt.value;
					var url="AverageGenerationSiteWise.jsp?id="+stateid+"&fdate="+fdate+"&tdate="+tdate+"&gen="+gen;
					//var myChart = new FusionCharts("RealTimeLine.swf", "ChId1", "500", "350", "0", "1");
                    window.open(url,'name','height=500,width=1000, top=100,resizable=yes,scrollbars=yes,menubar=no,toolbar=no,status=no');
                    
                }
	  </script>
    </head>
   
    <body bgcolor="white">
	    <form  name="stateWiseGenGraph" post="StateWiseGraph.jsp">    	   
		     <%  
		     
		     String stateid=request.getParameter("stateid")==null?"'1000000066','1000000070','1000000036','1000000038','1000000067','1000000080','1000000079'":request.getParameter("stateid").toString();
		     //System.out.println("stateid"+stateid);
		     List tranList = new ArrayList();
		     List avgTranList = new ArrayList();
		     CustomerUtil secutils = new CustomerUtil();		     
		    
		     String strXML = "";
		     String strXML1 = "";
		     String strXML2 = "";
		     String strXML3 = "";
		     String cum = "0";
		     String avg = "1";
		 	 //strXML +="<chart caption='Daily Visits' subcaption='(from 8/6/2006 to 8/12/2006)' lineThickness='1' showValues='0' formatNumberScale='0' anchorRadius='2'   divLineAlpha='20' divLineColor='CC3300' divLineIsDashed='1' showAlternateHGridColor='1' alternateHGridAlpha='5' alternateHGridColor='CC3300' shadowAlpha='40' labelStep='2' numvdivlines='5' chartRightMargin='35' bgColor='FFFFFF,CC3300' bgAngle='270' bgAlpha='10,10'>";
		
		 	 //strXML +="<chart palette='2' caption='"+s+"' yaxisname='Revenue (Millions)' hovercapbg='FFFFFF' toolTipBorder='889E6D' divLineColor='999999' divLineAlpha='80' showShadow='0' canvasBgColor='FEFEFE' canvasBaseColor='FEFEFE' canvasBaseAlpha='50' divLineIsDashed='1' divLineDashLen=1' divLineDashGap='2' numberPrefix='$' numberSuffix='M' chartRightMargin='30' useRoundEdges='1' legendBorderAlpha='0'>";
		 	 strXML +="<chart palette='1' caption='State Wise Generation' shownames='1' showvalues='0' decimals='0' numberPrefix='' useRoundEdges='1' legendBorderAlpha='0' formatNumberScale='0'>";
		 	 strXML1 +="<chart palette='1' caption='M.A.,G.A. and Capacity Factor' shownames='1' showvalues='0'  sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='(in Percentage)' SYAxisName='(in Percentage)'  numDivLines='4' formatNumberScale='0'>";
		 	 strXML2 +="<chart palette='2' formatNumberScale='0' caption='State Wise Average Operating and Lull Hrs' shownames='1' showvalues='0'   showSum='1' decimals='0' useRoundEdges='1'>";
		 	 strXML3 +="<chart palette='1' caption='State Wise Average Generation' shownames='1' showvalues='0' decimals='0' numberPrefix='' useRoundEdges='1' legendBorderAlpha='0' formatNumberScale='0'>";
		 	
		 	 strXML +="<categories>";
		 	 strXML1 +="<categories>";
		 	 strXML2 +="<categories>";
		 	 strXML3 +="<categories>";
		        tranList = (List)secutils.getStateWiseDash(stateid,fdate,tdate);
		        avgTranList = (List)secutils.getStateWiseDashAvg(stateid,fdate,tdate);
		        for (int i=0; i <tranList.size(); i++)
					{
		 				Vector v = new Vector();
		 				v = (Vector)tranList.get(i);
		 				String name = (String)v.get(0);
		 				if(name.equalsIgnoreCase("KARNATAKA"))
		 					name="KAR";
		 				else if(name.equalsIgnoreCase("MADHYA PRADESH"))
		 					name="MP";
		 				else if(name.equalsIgnoreCase("GUJARAT"))
		 					name="GUJ";
		 				else if(name.equalsIgnoreCase("MAHARASHTRA"))
		 					name="MAH";
		 				else if(name.equalsIgnoreCase("RAJASTHAN"))
		 					name="RAJ";
		 				else if(name.equalsIgnoreCase("ANDHRA PRADESH"))
		 					name="AP";
		 				else if(name.equalsIgnoreCase("TAMILNADU"))
		 					name="TAM";
		 				
						// String stateid = (String)v.get(13);
						// strXML +="<I Location='"+name+"' Generation='"+v.get(1)+"' HRS='"+v.get(2)+"' LullHrs='"+v.get(3)+"' MFault='"+v.get(4)+"'  MSD='"+v.get(5)+"'  GIFault='"+v.get(6)+"' GISD='"+v.get(7)+"' GEFault='"+v.get(8)+"' GESD='"+v.get(9)+"' CFactor='"+v.get(12)+"' MAvail='"+v.get(10)+"'  GIAvail='"+v.get(14)+"' GAvail='"+v.get(11)+"'   WAvail='"+v.get(15)+"' >";
							   
						
				        // strXML +="<tr class=TableRow1><td class=TableCell><b>"+name+"</b></td><td class=TableCell><b>"+v.get(1)+"</b></td><td class=TableCell><b>"+v.get(2)+"</td><td class=TableCell><b>"+v.get(3)+"</td><td class=TableCell>"+v.get(4)+"</td><td class=TableCell><b>"+v.get(5)+"</b></td><td class=TableCell><b>"+v.get(6)+"</b></td>   <td class=TableCell><b>"+v.get(7)+"</b></td><td class=TableCell><b>"+v.get(8)+"</b></td><td class=TableCell><b>"+v.get(9)+"</b></td><td class=TableCell><b>"+v.get(12)+"</b></td>  <td class=TableCell><b>"+v.get(10)+"</b></td><td class=TableCell><b>"+v.get(14)+"</b></td><td class=TableCell><b>"+v.get(11)+"</b></td> <td class=TableCell><b>"+v.get(15)+"</b></td> </tr>";
				        strXML +="<category label='"+name+"' />"; 				        
				        strXML1 +="<category label='"+name+"' />"; 
				        strXML2 +="<category label='"+name+"' />"; 
				        strXML3 +="<category label='"+name+"' />"; 
					}
		     strXML +="</categories>";
		     strXML1 +="</categories>";
		     strXML2 +="</categories>";
		     strXML3 +="</categories>";
		     
//		     strXML +="<dataset seriesName='TargetData' color='8EAC41' showValues='0'>";
//		        for (int i=0; i <tranList.size(); i++)
//				{
//						Vector v = new Vector();
//						v = (Vector)tranList.get(i);
//					
//				    	strXML +="<set value='"+v.get(1)+"'/>";
//			       
//				}
//		    strXML +="</dataset>";
		    
		    strXML +="<dataset seriesName='Generated Data' color='607142'  showValues='0'>";
		    for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);			
					
				    // String stateid = (String)v.get(13);
				    strXML +="<set value='"+v.get(1)+"' link='javascript:siteWiseGenChart("+v.get(13).toString()+","+cum+")'/>";
		        	// strXML +="<set value='"+v.get(1)+"'/>";
		        //strXML +="</I>";
			}
			strXML +="</dataset>";
			strXML +="<styles><definition><style name='CaptionFont' type='font' size='12'/></definition><application><apply toObject='CAPTION' styles='CaptionFont'/><apply toObject='SUBCAPTION' styles='CaptionFont' /></application></styles>";
		
			strXML +="</chart>";
			String chartCode="";
			chartCode= createChartHTML("MSColumn2D.swf","", strXML, "chart13", 461, 300, false);  
			
			strXML3 +="<dataset seriesName='Generated Data' color='F6BD0F'  showValues='0'>";
		    for (int i=0; i <avgTranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)avgTranList.get(i);			
					
				    // String stateid = (String)v.get(13);
				    strXML3 +="<set value='"+v.get(1)+"' link='javascript:siteWiseGenChart("+v.get(13).toString()+","+avg+")'/>";
		        	// strXML +="<set value='"+v.get(1)+"'/>";
		        //strXML +="</I>";
			}
			strXML3 +="</dataset>";
			strXML3 +="<styles><definition><style name='CaptionFont' type='font' size='12'/></definition><application><apply toObject='CAPTION' styles='CaptionFont'/><apply toObject='SUBCAPTION' styles='CaptionFont' /></application></styles>";
		
			strXML3 +="</chart>";
			String chartCode3="";
			chartCode3= createChartHTML("MSColumn2D.swf","", strXML3, "chart13", 450, 300, false);  
		
			strXML1 +="<dataset seriesName='Machine Avail.' color='AFD8F8' showValues='0'>";
			for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);		
					
				    //String stateid = (String)v.get(13);
				    strXML1 +="<set value='"+v.get(10)+"' />";
			    
			    //strXML +="</I>";
			}
			strXML1 +="</dataset>";
		
			strXML1 +="<dataset seriesName='Grid Avail.' color='F6BD0F' showValues='0'>";
			for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);		
					
				    //String stateid = (String)v.get(13);
				    strXML1 +="<set value='"+v.get(11)+"' />";
			    
			    //strXML +="</I>";
			}
			strXML1 +="</dataset>";
			strXML1 +="<dataset seriesName='Capacity Factor'  color='607142' showValues='0' parentYAxis='S'>";
			for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);		
					
				    //String stateid = (String)v.get(13);
				    strXML1 +="<set value='"+v.get(12)+"' />";
			    
			    //strXML +="</I>";
			}
			strXML1 +="</dataset>";
		
			//strXML1 +="<styles><definition><style name='CaptionFont' type='font' size='12'/></definition><application><apply toObject='CAPTION' styles='CaptionFont' /><apply toObject='SUBCAPTION' styles='CaptionFont' /></application></styles>";
			
			strXML1 +="</chart>";
			String chartCode1="";
			//System.out.println(strXML1);
			chartCode1= createChartHTML("MSColumn3DLineDY.swf","", strXML1, "chart14", 465, 320, false);  
			
			strXML2 +="<dataset seriesName='LULL HRS' color='F6BD0F' showValues='0'>";
			for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);
					//String name = (String)v.get(0);
					
				    //String stateid = (String)v.get(3);
				    strXML2 +="<set value='"+v.get(3)+"' />";
			    
			    //strXML +="</I>";
			}
			strXML2 +="</dataset>";
			strXML2 +="<dataset seriesName='OPERATING HRS' color='607142' showValues='0'>";
			for (int i=0; i <tranList.size(); i++)
			{
					Vector v = new Vector();
					v = (Vector)tranList.get(i);		
					
				    //String stateid = (String)v.get(13);
				    strXML2 +="<set value='"+v.get(2)+"' />";
			    
			    //strXML +="</I>";
			}
			strXML2 +="</dataset>";
		
			//strXML1 +="<styles><definition><style name='CaptionFont' type='font' size='12'/></definition><application><apply toObject='CAPTION' styles='CaptionFont' /><apply toObject='SUBCAPTION' styles='CaptionFont' /></application></styles>";
			
			strXML2 +="</chart>";
			String chartCode2="";
			//System.out.println(strXML2);
			chartCode2= createChartHTML("MSLine.swf","", strXML2, "chart15", 450, 300, false);  
		
		          tranList.clear(); 
		          avgTranList.clear(); 
		   
		   %>  
		   <input type="hidden" name="FromDatetxt" id="FromDatetxt" value="<%=fdate%>">
		   <input type="hidden" name="ToDatetxt" id="ToDatetxt" value="<%=tdate%>">  
		   
		   	<table>	
			   	<tr class="TableRow2">			   	
					<td class="Tablecell2">From Date: 
						<input type="text" name="fdate" id="fdate" size="20" class="ctrl" maxlength="10" value=<%=fdate%>  onfocus="dc.focus()" />
						<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.stateWiseGenGraph.fdate);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
						To Date: <input type="text" name="tdate" id="tdate" size="20" class="ctrl" maxlength="10" value=<%=tdate%>  onfocus="dc.focus()" />
						<a href="javascript:void(0)" id="dc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.stateWiseGenGraph.tdate);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
					</td><td><input type="submit" value="Generate"></td>
				</tr>
			</table>
		  
		   <br>
		   <%=chartCode%> 
		   
		   <%=chartCode3%> 
		   <br>
		   <%=chartCode1%> 
		    
		   <%=chartCode2%> 
	    </form>                 
    </body>
    <iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</html>
