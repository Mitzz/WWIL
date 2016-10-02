package com.enercon.global.utils;
import java.io.File;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;

import com.enercon.global.controller.InitServlet;
public class CreateChart {
	 private final static Logger logger = Logger.getLogger(CreateChart.class);
	
	public void CreateChart(double a,double b,double c,double d,String chartname)
	{    InitServlet servlet = new InitServlet();
		 final DefaultPieDataset data = new DefaultPieDataset();
        // data.setValue("Toal WEC", new Double(a));
        // data.setValue("Uploaded WEC", new Double(b));
         //data.setValue("Balance WEC", new Double(c));
         
         double f= b-d;
         
		 //DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// data.setValue("Total WEC",new Double(a));
		// data.setValue("Uploaded WEC",new Double(b));
		 
		 data.setValue("Published WEC", new Double(d));
		  data.setValue("Not Published WEC", new Double(f));
		  data.setValue("Balance WEC", new Double(c));
		 
		// DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		// dataset.setValue(a, "Total WEC", "Total WEC");
		// dataset.setValue(b, "Uploaded WEC", "Uploaded WEC");
		// dataset.setValue(c, "Balance WEC", "Balance WEC");
		// dataset.setValue(d, "Published WEC", "Published WEC");
		// dataset.setValue(f, "Not Published WEC", "Not Published WEC");
		// JFreeChart chart = ChartFactory.createBarChart("Uploaded Status","WEC", "Number", dataset, PlotOrientation.HORIZONTAL, true,true, false);
		 //chart.setBackgroundPaint(Color.magenta);
		// chart.getTitle().setPaint(Color.blue);
		  
		String title="Total WEC Upload Status(Total WEC-"+a+")";
		  JFreeChart chart = ChartFactory.createPieChart(title, data, true, true, false);
	        
        //JFreeChart chart = ChartFactory.createBarhart("Total WEC Upload Status ", data, true, true, false);
         //JFreeChart chart = ChartFactory.createBarChart("Total WEC Upload Status ","","", dataset, PlotOrientation.HORIZONTAL, true, true, true);
         try {
             final ChartRenderingInfo info = new 
            ChartRenderingInfo(new StandardEntityCollection());
             String cupath = servlet.getDatabaseProperty("chartuploadfilepath");
             final File file1 = new File(cupath+chartname+".jpg");
           //  System.out.println(cupath+"mychart.jpg");
             ChartUtilities.saveChartAsJPEG(file1, chart, 600, 400, info);
         } catch (Exception e) {
           //  System.out.println(e);
        	 logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
         }
       
	}
	
	

}
