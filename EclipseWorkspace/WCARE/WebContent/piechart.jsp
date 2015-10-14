<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page  import="java.awt.*" %>
<%@ page  import="java.io.*" %>
<%@ page  import="org.jfree.chart.*" %>
<%@ page  import="org.jfree.chart.axis.*" %>
<%@ page  import="org.jfree.chart.entity.*" %>
<%@ page  import="org.jfree.chart.labels.*" %>
<%@ page  import="org.jfree.chart.plot.*" %>
<%@ page  import="org.jfree.chart.renderer.category.*" %>
<%@ page  import="org.jfree.chart.urls.*" %>
<%@ page  import="org.jfree.data.category.*" %>
<%@ page  import="org.jfree.data.general.*" %>
<%@ page  import="com.enercon.global.utils.SendMail"%>

<%
            final double[][] data = new double[][]{
				{3049},
                {2358},
                {691},
                {2355}
            };

            final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.setValue(3049, "Total WEC", "");
			dataset.setValue(2358, "Uploaded WEC", "");
			dataset.setValue(691, "Balance WEC","");
			dataset.setValue(2355, "Published WEC","");
			JFreeChart chart = null;
            BarRenderer renderer = null;
            CategoryPlot plot = null;


            final CategoryAxis categoryAxis = new CategoryAxis("");
            final ValueAxis valueAxis = new NumberAxis("WEC");
            renderer = new BarRenderer();

            plot = new CategoryPlot(dataset, categoryAxis, valueAxis, 
            renderer); 
            plot.setOrientation(PlotOrientation.VERTICAL);
            chart = new JFreeChart("Total WEC Uploaded Status", JFreeChart.DEFAULT_TITLE_FONT, 
            plot, true);
            chart = new JFreeChart("Total WEC Uploaded Status", JFreeChart.DEFAULT_TITLE_FONT, 
                    plot, true);
            chart.setBackgroundPaint(new Color(249, 231, 236));

            Paint p1 = new GradientPaint(
                    0.0f, 0.0f, new Color(16, 89, 172), 0.0f, 0.0f, new Color
                   (201, 201, 244)); 

            renderer.setSeriesPaint(1, Color.getColor(""));

            Paint p2 = new GradientPaint(
                    0.0f, 0.0f, new Color(255, 35, 35), 0.0f, 0.0f, new Color
                    (255, 180, 180)); 

            renderer.setSeriesPaint(2, Color.blue);
            
            Paint p3 = new GradientPaint(
                    0.0f, 0.0f, new Color(255, 235, 135), 0.0f, 0.0f, new Color
                    (255, 80, 170)); 

            renderer.setSeriesPaint(3, Color.green);
            
            Paint p4 = new GradientPaint(
                    0.0f, 0.0f, new Color(255, 235, 135), 0.0f, 0.0f, new Color
                    (255, 80, 170)); 

            renderer.setSeriesPaint(4, Color.green);

            plot.setRenderer(renderer);

            try {
                final ChartRenderingInfo info = new ChartRenderingInfo
                (new StandardEntityCollection());
                final File file1 = new File("D:/mychart.jpg");
                ChartUtilities.saveChartAsJPEG(file1, chart, 400, 300, info);
            } catch (Exception e) {
                out.println(e);
            }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" >
        <meta http-equiv="refresh" content="1" >
        <title>JSP Page</title>
    </head>
    
    <body>
        <IMG SRC="http://172.18.16.27/GQMDocument/mychart.jpg" WIDTH="400" HEIGHT="300" BORDER="0" USEMAP="#chart">
        <%  
       // String msg="<IMG SRC='http://172.18.16.27:7001/ECARE/mychart.jpg' WIDTH='400' HEIGHT='300' BORDER='0' USEMAP='#chart'>";
      //  String msg="<iframe align='middle' id='myframe' name='myframe' width='100%' height='1000' style='BORDER-RIGHT:0px; BORDER-TOP:0px; BORDER-LEFT:0px; BORDER-BOTTOM:0px' frameborder='0' src='http://172.18.17.155:7001/ECARE/UploadGraph.jsp'></iframe>";
		String msg="<script>";
		//msg+="location.href='http://172.18.17.155:7001/ECARE/UploadGraph.jsp'";
		msg+="window.open('http://172.18.17.155:7001/ECARE/PieUploadGraph.jsp?pdata="+2000+"&updata="+800+"&bdata="+200+"',null,'left=400,top=200,height=500,width=600,status=0,toolbar=0,menubar=0,resizable=0,titlebar=0,scrollbars=0')";
			 
		msg+="</script>";
		
		
		//Stringmsg=<iframe name='calendar' id='calendar' src='http://172.18.17.155:7001/ECARE/PieUploadGraph.jsp?pdata="+2000+"&updata="+800+"&bdata="+200+"' height='300' width='350'></iframe>"; 
        SendMail sm = new SendMail();
        sm.sendMail("vivek.chaudhary@windworldindia.com","WindWorldCare@windworldindia.com","Generation Data Upload Status For The Date - ",msg);   
		 
       %>
    </body>
</html> 
