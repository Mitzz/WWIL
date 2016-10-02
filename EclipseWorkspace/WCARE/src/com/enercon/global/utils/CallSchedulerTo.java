package com.enercon.global.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;


public class CallSchedulerTo implements WcareConnector{
	private final static Logger logger = Logger.getLogger(CallSchedulerTo.class);
	
	
	public void CallTimer() throws  Exception  {
		
		
		CallSchedulerTo callScheduler = new CallSchedulerTo();
		callScheduler.callSchedule();
		
		callScheduler.CreateXML();
	
	}
	
	public void callSchedule() throws  Exception {
	
		
		//BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		//System.out.print("Enter number to add elements in your XML file: ");
		//String str = bf.readLine();
	
		//System.out.print("Enter root: ");
		
        DecimalFormat formatter = new DecimalFormat("#########0.00");
        //JDBCUtils conmanager = new JDBCUtils();
        
		
			//System.out.print("Enter the element: ");
			
			
			
			//String actualCurCum = "";
			//String actualTotCum = "";
			double totalCurCum=0;
			double totalCumAll=0;
			double totalYtdyAll=0;
			String sqlQuery1 = "";			
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			Connection conn = null;
			try {
				conn = wcareConnector.getConnectionFromPool();
				//Statement st = conn.createStatement();
				
				sqlQuery1 = "";			
				prepStmt = null;
				rs = null;
				
				/* sqlQuery1 = CallSchedulerXMLSQLC.CHECK_CUM_GEN_REGULAR;				
				 prepStmt = conn.prepareStatement(sqlQuery1);
				 rs = prepStmt.executeQuery();
				if(rs.next())
					totalCurCum = rs.getDouble("TOTALGEN");
				rs.close();
				prepStmt.close();
				
				if(totalCurCum==0)
				{*/
					sqlQuery1 = CallSchedulerXMLSQLC.CURR_CUM_GEN_ALL;
					prepStmt = conn.prepareStatement(sqlQuery1);
					rs = prepStmt.executeQuery();
					if(rs.next())
						totalCumAll = rs.getDouble("TOTALCURCUM");
					rs.close();
					prepStmt.close();
					
					sqlQuery1 = CallSchedulerXMLSQLC.TOTAL_YSTAYERD_GEN;
					prepStmt = conn.prepareStatement(sqlQuery1);
					rs = prepStmt.executeQuery();
					if(rs.next())
						totalYtdyAll = rs.getDouble("TOTALT_YTD_GEN");
					rs.close();
					prepStmt.close();
					
					java.util.Date ddate= new java.util.Date();
					int nday = ddate.getHours();
					
					if(nday==1)
					{
						totalYtdyAll=(totalYtdyAll/24)*1.3;
					}
					else if(nday==2)
					{
						totalYtdyAll=(totalYtdyAll/24)*2.6;
					}
					else if(nday==3)
					{
						totalYtdyAll=(totalYtdyAll/24)*3.9;
					}
					else if(nday==4)
					{
						totalYtdyAll=(totalYtdyAll/24)*5.2;
					}
					else if(nday==5)
					{
						totalYtdyAll=(totalYtdyAll/24)*6.5;
					}
					else if(nday==6)
					{
						totalYtdyAll=(totalYtdyAll/24)*7.8;
					}
					else if(nday==7)
					{
						totalYtdyAll=(totalYtdyAll/24)*9.1;
					}
					else if(nday==8)
					{
						totalYtdyAll=(totalYtdyAll/24)*10.4;
					}
					else if(nday==9)
					{
						totalYtdyAll=(totalYtdyAll/24)*11.7;
					}
					else if(nday==10)
					{
						totalYtdyAll=(totalYtdyAll/24)*13.0;
					}
					else if(nday==11)
					{
						totalYtdyAll=(totalYtdyAll/24)*13.5;
					}
				
					else if(nday==12)
					{
						totalYtdyAll=(totalYtdyAll/24)*14.0;
					}
					else if(nday==13)
					{
						totalYtdyAll=(totalYtdyAll/24)*14.5;
					}
					else if(nday==14)
					{
						totalYtdyAll=(totalYtdyAll/24)*15.0;
					}
					else if(nday==15)
					{
						totalYtdyAll=(totalYtdyAll/24)*15.5;
					}
					else if(nday==16)
					{
						totalYtdyAll=(totalYtdyAll/24)*16.0;
					}
					
					else if(nday==17)
					{
						totalYtdyAll=(totalYtdyAll/24)*17.5;
					}
					else if(nday==18)
					{
						totalYtdyAll=(totalYtdyAll/24)*18.0;
					}
					else if(nday==19)
					{
						totalYtdyAll=(totalYtdyAll/24)*19.0;
					}
					else if(nday==20)
					{
						totalYtdyAll=(totalYtdyAll/24)*20.0;
					}
					
					else if(nday==21)
					{
						totalYtdyAll=(totalYtdyAll/24)*21.0;
					}
					else if(nday==22)
					{
						totalYtdyAll=(totalYtdyAll/24)*22.0;
					}
					else if(nday==23)
					{
						totalYtdyAll=(totalYtdyAll/24)*23.0;
					}
					else
					{
						totalYtdyAll=(totalYtdyAll/24)*24.0;
					}
					
					
					
					 sqlQuery1 = CallSchedulerXMLSQLC.DELETE_GEN_REGULAR;
						prepStmt = conn.prepareStatement(sqlQuery1);
						
		     			 
						 prepStmt.executeUpdate();
						 prepStmt.close();
						 
					sqlQuery1 = CallSchedulerXMLSQLC.INSERT_CUM_GEN_REGULAR;
					prepStmt = conn.prepareStatement(sqlQuery1);
					 prepStmt.setObject(1,totalCumAll+totalYtdyAll);
	     			 
					 prepStmt.executeUpdate();
					 prepStmt.close();
					 
					   
						
					
					
				//}
					
					//st.close();
					conn.commit();
	        		//conn.close();
			} catch (Exception e) {
				logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
			} finally {
				DaoUtility.releaseResources(Arrays.asList(prepStmt), Arrays.asList(rs), conn);
			}
			

}	
	
public void CreateXML() throws  Exception  {	
	
		int no = 7;
		//System.out.print("Enter root: ");
		logger.warn("Running...");
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		String rdate = dateFormat.format(date.getTime()); 
		
		String root = "Generation";
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
		Element rootElement = document.createElement(root);
        document.appendChild(rootElement);
        DecimalFormat formatter = new DecimalFormat("#########0.00");
        String cox_data=".",coal_data=".",co2_data=".",fly_data=".",water_data=".";
        String tcox_data=".";
        double totalgen=0;
		double totalCurCum=0;
		double totalCumAll=0;
		double todayCumAll=0;
		
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			conn = wcareConnector.getConnectionFromPool();
			//Statement st = conn.createStatement();
			
			String sqlQuery1 = CallSchedulerXMLSQLC.CHECK_CUM_GEN_REGULAR;				
			prepStmt = conn.prepareStatement(sqlQuery1);
			rs = prepStmt.executeQuery();
			if(rs.next())
				totalgen = rs.getDouble("TOTALGEN");
			rs.close();
			prepStmt.close();
			
			if(totalgen==0)
			{
				
				CallSchedulerTo clto=new CallSchedulerTo();
				clto.callSchedule();
			}
			
			sqlQuery1 = CallSchedulerXMLSQLC.TOTAL_CUM_GEN;
			prepStmt = conn.prepareStatement(sqlQuery1);
			rs = prepStmt.executeQuery();
			while(rs.next())
				{
					totalCumAll = totalCumAll+rs.getDouble("N_GENERATION");
					if(rs.getString("S_DATA_TYPE").equals("REALTIME"))
						todayCumAll=rs.getDouble("N_GENERATION");
				}
			
			rs.close();
			prepStmt.close();
			//st.close();
			conn.commit();
        	//conn.close();
		} catch (Exception e) {
			
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
		
		for (int i = 1; i <= no; i++){
			//System.out.print("Enter the element: ");
			if(i ==1)
			{
			String element = "GWH";
						
			double totalGeneration = totalCumAll;
			totalGeneration=totalGeneration/1000;
			String data = formatter.format(totalGeneration/1000);
			
			double coxdata=(totalGeneration*0.922528)/1000;
			//double coxdata=(totalGeneration*2.52)/1000;
			 cox_data = formatter.format(coxdata);
			 
			 double coaldata=(totalGeneration*0.6)/1000;
			 coal_data = formatter.format(coaldata);
			 
			 double co2data=(coaldata*4.2);
			 co2_data = formatter.format(co2data);
			 
			 
			 double flydata=(coaldata*0.46);
			 fly_data = formatter.format(flydata);
			 
			 double waterdata=(totalGeneration*3.2)/1000;
			 water_data = formatter.format(waterdata);
			 
			Element em = document.createElement(element);
			em.appendChild(document.createTextNode(data+" Million Units"));
			
			rootElement.appendChild(em);
			}
			if(i ==2)
			{
			
				String element = "Carbon";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(cox_data+" Metric Tones"));
				rootElement.appendChild(em);
			}
			
			if(i ==3)
			{
			
				String element = "Coal";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(coal_data+" MMT"));
				rootElement.appendChild(em);
			}
			if(i ==4)
			{
			
				String element = "Co2";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(co2_data+" MMT"));
				rootElement.appendChild(em);
			}
			if(i ==5)
			{
			
				String element = "Flyash";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(fly_data+" MMT"));
				rootElement.appendChild(em);
			}
			if(i ==6)
			{
			
				String element = "Water";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(water_data+" MCu M"));
				rootElement.appendChild(em);
			}
			
			if(i ==7)
			{
			
				String element = "DateTime";
				
				//double coxdata=todayCumAll*0.92;
				// tcox_data = formatter.format(coxdata);
				Element em = document.createElement(element);
				//em.appendChild(document.createTextNode(formatter.format(todayCumAll)));
				em.appendChild(document.createTextNode(rdate));
				rootElement.appendChild(em);
			}
			/*if(i ==4)
			{
			
				String element = "Month";
				
				// data = "3335";
				Element em = document.createElement(element);
				em.appendChild(document.createTextNode(tcox_data+" T"));
				rootElement.appendChild(em);
			}*/
			
		}
		String fff="E:\\myfile.xml";
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result =  new StreamResult(fff);
      
        transformer.transform(source, result);
       
	}
	
	
}
	




