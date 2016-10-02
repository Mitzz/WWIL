package com.enercon.global.utils;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.enercon.admin.dao.AdminDao;
import com.enercon.admin.dao.AdminSQLC;
import com.enercon.customer.dao.CustomerDao;
import com.enercon.customer.util.CustomerUtil;
import com.enercon.dao.DaoUtility;
import com.enercon.global.controller.InitServlet;
import com.enercon.global.utility.DateUtility;

public class CallScheduler {
	
	private final static Logger logger = Logger.getLogger(CallScheduler.class);
	
	public void CallTimer() throws  Exception  {
		
		String todaysDate = DateUtility.getTodaysDateInGivenFormat("dd/MM/yyyy");
		String autoSchedulerDate = todaysDate;		
		String scheduleRemark = "scheduledMail";
		
		callSchedule(scheduleRemark, autoSchedulerDate);
		
	}
	
	public void pushScadaDataToECARE() throws Exception{
		
		Connection connection = wcareConnector.getConnectionFromPool();
		
		String sqlQuery = null;
		
		CallableStatement callStatement = null;
		
		try{
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			
			Date date = new Date();
			int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
			
			String readingDate = dateFormat.format(date.getTime() -  MILLIS_IN_DAY);
			logger.warn("Start DATE:: " + readingDate);
			sqlQuery = AdminSQLC.PUSH_SCADA_DATA_TO_ECARE;
			callStatement = connection.prepareCall(sqlQuery);
			callStatement.setObject(1, readingDate);
        		        	
			callStatement.executeUpdate();
			
			callStatement.close();
			logger.warn("End DATE:: " + readingDate);
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally {
			DaoUtility.releaseResources(callStatement, connection);
	    }
	}
	
	/**
	 * 
	 * @param scheduleRemark
	 * @param schedulerDate:Date in 'dd/MM/yyyy' format
	 * @throws Exception
	 */
	public void callSchedule(String scheduleRemark, String schedulerDate) throws  Exception {
	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = wcareConnector.getConnectionFromPool();
	String sqlQuery=null;
	PreparedStatement ps0=null,ps1=null;
	ResultSet rs0=null,rs1=null;
	SendMail sm = new SendMail();
	// AdminDao ad = new AdminDao();
	
	PreparedStatement prepStmt=null;
	ResultSet rs = null;
	PreparedStatement ps=null;

	try{		
		Date currentOrSchedulerDate ;
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		if(schedulerDate.equals("")){
			currentOrSchedulerDate = new Date();
		}
		else{
			DateFormat dateFormatter  = new SimpleDateFormat("dd/MM/yyyy");
			currentOrSchedulerDate = dateFormatter.parse(schedulerDate);
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// AdminDao adminDAo = new AdminDao();
		// String  rdate = adminDAo.convertDateFormat(date.toString(), "dd/MM/yyyy","dd-MMM-yyyy");
		java.sql.Date currentOrSchedulerSqlDate = new java.sql.Date(currentOrSchedulerDate.getTime());
		// java.sql.Date arddt = new java.sql.Date(date.getTime() -  MILLIS_IN_DAY);
		String requestDate = dateFormat.format(currentOrSchedulerDate.getTime());
		String previuosDate = dateFormat.format(currentOrSchedulerDate.getTime() -  MILLIS_IN_DAY);
		
		// int acntt=0;
		// Calendar rightNow = Calendar.getInstance();			
		
		//java.util.Date presentDate= new java.util.Date();
		//int nday = presentDate.getHours();
		int nday = 0;
		
		DecimalFormat decimalFormatWithTwoPlaces= new DecimalFormat("#0.00");
		DecimalFormat decimalFormatWithOnePlace = new DecimalFormat("#0.0");
		String custid="",custname="",custemail="",secondEmailId="";
		nday=11;
		   /*.............Admin Email Facility................*/
		   if(nday==11) 
		   {      
			   
			 sqlQuery = AdminSQLC.CHECK_SEND_MAIL;
/*
SELECT count(*) cnt, S_SEND_TYPE AS MAIL_TYPE
FROM TBL_SEND_MAIL WHERE TO_DATE(D_SEND_DATE)=TO_DATE('')
GROUP BY S_SEND_TYPE;
*/
      	     prepStmt = conn.prepareStatement(sqlQuery);
      	     prepStmt.setDate(1, currentOrSchedulerSqlDate);
      	     
      		 int sendMailCount=0;
      	     rs = prepStmt.executeQuery();
      		 if (rs.next()){
      		  	sendMailCount=rs.getInt("cnt");	 				
      		  }
      		 if(sendMailCount == 0){      			
      			 sqlQuery = AdminSQLC.INSERT_SEND_MAIL;
      			 ps = conn.prepareStatement(sqlQuery);
     			 ps.setDate(1,currentOrSchedulerSqlDate);
     			 ps.setString(2,scheduleRemark);
     			 ps.executeUpdate();
     			 ps.close();
      					  	     
		  		String msg="";
		  		boolean d = true;
		  		CallSchedulerForSendingMail c = new CallSchedulerForSendingMail();
				c.sendMail(DateUtility.getPreviousDateFromGivenDateInString(DateUtility.convertDateFormats(schedulerDate, "dd/MM/yyyy", "dd-MMM-yyyy"), "dd-MMM-yyyy"));
		  		for(int k=1; k<=0; k++)
				{
		  			
		  			if(d){
		  				break;
		  			}
		  			
					String customerId="";
					String customerName="";
					String customerNameIdentity="";
					if(k==5)
					{
						   customerId="1009000010";
						   customerName="VISH WIND INFRASTRUCTURE LLP";
						   customerNameIdentity="Vish Wind LLP";
					}
					else if(k==2){
						  customerId="1009000004','1107000004','1107000002','1107000003','1205000001','1204000008','1207000002";
						  customerName="IL & FS ENERGY DEVELOPMENT COMPANY LTD";
						  customerNameIdentity="ILFS Ltd";
					}
					else if(k==3)
					{
						customerId="1000000172";
						customerName="J N INVESTMENTS";
						customerNameIdentity="JNI Ltd";
					}
					else if(k==1)
					{
						customerId="1204000010";
						customerName="VAAYU INFRASTRUCTURE LLP";
						customerNameIdentity="VAAYU INFRASTRUCTURE LLP";
					}
					else if(k==6)
					{
						customerId="1306000002";
						customerName="VAAYU RENEWABLE ENERGY (TAPTI) PVT. LTD.";
						customerNameIdentity="VAAYU RENEWABLE ENERGY (TAPTI) PVT. LTD.";
					}
					
					else{
						customerId="1004000026";
						customerName="VAAYU (INDIA) POWER CORPORATION PVT. LTD.";
						customerNameIdentity="VAAYU Power Corporation Ltd";
					}					  
					 
				msg +="<table width='830' border=1 cellspacing=0 cellpadding=0 style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;>";
				msg +=	"<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:18px;color:#000000';font-weight: bold;'>" +
							"<td width='830'align='center' colspan='16'>" +
								"<font class='sucessmsgtext'>" +
									"<b>Generation Report</b>" +
								"</font>" +
							"</td>" +
						"</tr>";
				msg +=	"<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'>" +
							"<td width='830'align='center' colspan='16'>" +
								"<font class='sucessmsgtext'>" +
									"<b>"+customerName+"</b>" +
								"</font>" +
							"</td>" +
						"</tr>"; 
						
			  	String capacity=CustomerDao.getCustCapacity(customerId);
			  	if(customerId.equals("1000000172")){
			  		capacity="4.6";
			  	}
			  	
			  	msg +=	"<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
			  				"<td colspan=8 align=center>" +
			  					"<b>Total Capacity:</b>&nbsp;<b>"+capacity+" MW</b>" +
			  				"</td>" +
			  				"<td colspan='8' align=center>" +
			  					"<b>Report Date:</b>&nbsp;<b>"+previuosDate+"</b>" +
			  				"</td>" +
			  			"</tr>";
			  	
			    //  msg +="<tr  style='font-family:arial;font-size:14px;color:#000000';font-weight: bold;'><td width='830'align='center' colspan='16'><font class='sucessmsgtext'><b>Site Wise Daily Generation Summary</b></font></td></tr>";
			 	msg +=	"<tr  style='font-family:arial;font-size:12px;color:#000000';'>" +
			 				"<td align='center' colspan='6'>&nbsp;</td>" +
			 				"<td colspan=6 align='center'>" +
			 					"<b>Daily</b>" +
			 				"</td>" +
			 				"<td colspan=4 align='left'  style='font-family:arial;font-size:11px;color:#000000';'>" +
			 					"<b>Cummulative FY:2013-14</b>" +
			 				"</td>" +
			 			"</tr>";
				
			 	//if(!cuid.equals("1009000010"))
			 	//{
			 	msg +=	"<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'>" +
			 				"<td  align='center' ><b>SN</b></td>" +
			 				"<td colspan='2'><b>State Name</b></td>" +
			 				"<td colspan='2'>" +
			 					"<b>Site Name</b>" +
			 				"</td>" +
			 				"<td align='center'>" +
			 					"<b>Total WEC Commisioned</b>" +
			 				"</td>" +
			 				"<td align='center'>" +
			 					"<b>Avg KWH</b>" +
			 				"</td>" +
			 				"<td align='center'>" +
			 					"<b>Total KWH</b>" +
			 				"</td>" +
			 				"<td align='center'><b> Revenue(Rs) with LL @ 3%</b></td>" +
			 				"<td align='center'><b>CF(%)</b></td><td  align='center'><b>MA(%)</b></td>" +
			 				"<td  align='center'><b>GA(%)</b></td><td  align='center'><b>MWH</b></td>" +
			 				"<td  colspan='3' align='center' ><b> Revenue(Rs In Lacs)</b></td>" +
			 			"</tr>";
			 	//}
			 	
				//	msg +="<tr bgcolor='#BBB87E' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td  align='center' colspan='2'><b>SN</b></td><td colspan='3'><b>Site Name</b></td><td align='center'><b>Total WEC Commisioned</b></td><td align='center'><b>Data Uploaded</b></td><td align='center'><b>Balance M/C</b></td><td  align='center'><b>Daily Generation</b></td></tr>";
				//	ardate="06/02/2011";
				//  List tranListData1 = (List) CustomerDao.getWECSiteWise(ardate,cuid);
				
				int sn=1;
				int jj=1;
				int acntt=0;
				int tcnt=0,tcnt1=0,tgen=0,tgen1=0,cumgen=0,tavgt=0,site_cnt=0,site_tgen=0,site_revenue=0,site_tavgt=0,tcntp2=0,cumgenp2=0;				
				double tmavail=0,tcfac=0,tgavail=0,tcnt2=0,tgavail7=0,cumgenall=0;
				double site_cg=0,site_cg2=0,site_tmavail=0,site_tcfac=0,site_tgavail=0,site_tcnt2=0,site_tgavail7=0,site_cumgenall=0,cumgenallp2=0;
				//double vish_cg=0,vish_cg2=0,vish_tmavail=0,vish_tcfac=0,vish_tgavail=0,vish_tcnt2=0,vish_tgavail7=0,vish_cumgenall=0;
				//int vish_tavgt = 0, vish_cnt=0,vish_tgen=0,vish_revenue=0;
		  		int gotoeneter=0;
		  		
		  		if(customerId.equals("10090000101"))
		  		{		  				  			
		  		msg+=	"<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'>" +
		  					"<td  colspan='17' align='center' ><b>PHASE-1</b></td>" +
		  				"</tr>";
		  		msg+=	"<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'>" +
		  					"<td  align='center' ><b>SN</b></td>" +
		  					"<td colspan='4'><b>Site Name</b></td>" +
		  					"<td align='center'><b>Total WEC Commisioned</b></td>" +
		  					"<td align='center'><b>Avg KWH</b></td>" +
		  					"<td align='center'><b>Total KWH</b></td>" +
		  					"<td align='center'><b> Revenue(Rs) with LL @ 3%</b></td>" +
		  					"<td align='center'><b>CF(%)</b></td><td  align='center'><b>MA(%)</b></td>" +
		  					"<td  align='center'><b>GA(%)</b></td><td  align='center'><b>MWH</b></td>" +
		  					"<td  colspan='3' align='center' ><b> Revenue(Rs In Lacs)</b></td>" +
		  				"</tr>";
		  			
		  			//String stateId = "";
					//int checkState = 0;
					//int checkFlag = 1;
					//int a = 1;
					//int b = 1;
					/*if(1==0){
		  			List tranListDataPhase1 = (List) CustomerDao.getWECSiteWisePhase1(previuosDate,customerId);
		  			//acntt = 0;
			  		for(int j=0;j<tranListDataPhase1.size();j++)
				  	{  
			  		  
				  		Vector v1 = new Vector();
						v1 = (Vector) tranListDataPhase1.get(j);
						int avg=0; int avg1=0; int tavg=0;
						avg=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
						avg1=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
						// String sname=v1.get(0).toString();
						// System.out.println(v1.get(11).toString());						
						
						//	if(!v1.get(11).toString().contains("1000000164,1103000001,1000000184,1000000167,1003000006,1000000181,1102000002,1000000172,1103000002"))
						
						if(!stateId.equals(v1.get(12).toString())){
							stateId = v1.get(12).toString();
							a = CustomerDao.getStateCount(ardate, cuid, v1.get(12).toString());
							b=a;
						}
						
						gotoeneter=0;
						
						if(v1.get(11).toString().equals("1010000003"))
							gotoeneter=1;
						else if(v1.get(11).toString().equals("1010000004"))
							gotoeneter=1;
						else if(v1.get(11).toString().equals("1010000005"))
							gotoeneter=1;							
						
						if(gotoeneter!=1)
						{
									if(avg!=0) //if((avg!=0|| avg1 !=0) && avg!=0 )
									{  										
									
									 tavg= Math.round(avg1/avg);
									 double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
									 // double cgg=Math.round(cg/100000);
										
										    
									 double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
									 	// String cg21=Dformat.format(cg2);
									 msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(cg)+"</td></tr>";
									    // msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center'>"+sn+"</td><td align='left' colspan='2'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
									 	//if(a==b)
								 		//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
								 		//else
								 		//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' >"+v1.get(0)+"</td><td align='center'>"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
								 		//a--;
									 	
									    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
										tavgt+=tavg;
										tcnt1+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
										tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
										cumgen+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
										tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
										tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
										tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
										tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
										tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
									
										cumgenall+=cg;
									}
									else
									{
										msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//if(a==b)
										//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//else
										//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2' >"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//a--;									
									 
									 String remString = CustomerDao.getSiteRemarks(v1.get(0).toString());
									 
									 msg += remString+"<td></tr>";
										
									}
									sn++;	
						}else{
								
					 		if(avg == 0)
					 			avg = 1;
					 		tavg= Math.round(avg1/avg);
					 		site_cnt+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
						 
						    site_tavgt+=tavg;
						    site_tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
						    site_revenue+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
						    site_tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
						    site_tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
						    site_tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
						    // site_tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
						    site_cg+=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());   
						    site_cg2+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
				 
					        acntt=acntt+1;
					         
					        // tavg= Math.round(avg1/avg);
							double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
								//double cgg=Math.round(cg/100000);
								    
							double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
							// String cg21=Dformat.format(cg2);
							
							// msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='4' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
							 
						    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
							tavgt+=tavg;
							tcnt1+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
							tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
							cumgen+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
							tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
							tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
							tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
							tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
							tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
						
							cumgenall+=cg;								
						 
						}
						 		
					}
			  		tranListDataPhase1.clear();	
			  		
			  		if(customerId.equals("10090000101"))
					{
			  			site_tavgt= site_tavgt/acntt;
			  			site_tmavail= site_tmavail/acntt;
			  			site_tgavail= site_tgavail/acntt;
			  			site_tcnt2= site_tcnt2/acntt;
			  		    msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>GUJRAT</td><td align='left' colspan='2'>SAMANA</td><td align='center' >"+site_cnt+"</td> <td align='center' >"+site_tavgt+"</td><td align='center' >"+site_tgen+"</td><td align='center' > "+site_revenue+" </td><td align='center' >"+decimalFormatWithOnePlace.format(site_tcnt2)+"</td><td align='center' >"+decimalFormatWithOnePlace.format(site_tmavail)+"</td><td align='center' >"+decimalFormatWithOnePlace.format(site_tgavail)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(site_cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(site_cg)+"</td></tr>";
					}
			  		
			  		msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center' colspan='5'><b>Total</b></td>" +
	  		        
		  		        "<td align='center' ><b>"+ tcnt1+"</b></td>" +	  
		  		        //+ Math.round(tavgt/tcnt1) +
		  		        "<td align='center' >&nbsp;</td>"+
		  		        "<td align='center' >&nbsp;</td>"+
	  		     
				  		"<td align='center'><b>"+cumgen+"</b></td>" +	
				  		"<td align='center' >&nbsp;</td>"+
				  		"<td align='center' >&nbsp;</td>"+
				  		"<td align='center' >&nbsp;</td>"+
				  		"<td align='center' >&nbsp;</td>"+
			  		    // "<td align='center' >&nbsp;</td>"+			  		 
		  				// "<td align='center' ><b>"+tgen+"</b></td>" +
		  				// "<td align='center' ><b>"+cumgen+"</b></td>" +	
		  				// "<td align='center'><b>"+ Math.round(tcfac/tcnt1)+"</b></td>" +
			  		  	// "<td align='center'><b>"+ Math.round(tmavail/tcnt1)+"</b></td>" +
		  				// "<td align='center'><b>"+ Math.round(tgavail/tcnt1)+"</b></td>" +
		  				// "<td align='center'><b>"+Math.round(tgavail7)+"</b></td>" +			  						
				  		"<td align='center' colspan='3'><b>"+decimalFormatWithTwoPlaces.format(cumgenall)+"</b></td></tr>" ;				  		
				  		msg +="<tr><td align='center' colspan='16'>&nbsp;</td></tr>";			  		
			  		
			  		sn=1;
					}*/	  			
		  		msg+=	"<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'>" +
		  					"<td  colspan='17' align='center' ><b>PHASE-2</b></td>" +
		  				"</tr>";
		  		msg+=	"<tr bgcolor='#BBB87E'   style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'>" +
		  					"<td  align='center' ><b>SN</b></td>" +
		  					"<td colspan='4'><b>Site Name</b></td>" +
		  					"<td align='center'><b>Total WEC Commisioned</b></td>" +
		  					"<td align='center'><b>Avg KWH</b></td>" +
		  					"<td align='center'><b>Total KWH</b></td>" +
		  					"<td align='center'><b> Revenue(Rs) with LL @ 3%</b></td>" +
		  					"<td align='center'><b>CF(%)</b></td><td  align='center'><b>MA(%)</b></td>" +
		  					"<td  align='center'><b>GA(%)</b></td><td  align='center'><b>MWH</b></td>" +
		  					"<td  colspan='3' align='center' ><b> Revenue(Rs In Lacs)</b></td>" +
		  				"</tr>";
			  		
			  		List tranListDataPhase2 = (List) CustomerDao.getWECSiteWisePhase2(previuosDate,customerId);/*403*/	
			  		//stateId = "";
			  		//a = 1;
			  		//b = 1;
			  		tcnt=0;tgen=0;tgen1=0;tavgt=0;site_cnt=0;site_tgen=0;site_revenue=0;site_tavgt=0;tcntp2=0;cumgenp2=0;acntt=0;				
					tmavail=0;tcfac=0;tgavail=0;tcnt2=0;tgavail7=0;
					site_cg=0;site_cg2=0;site_tmavail=0;site_tcfac=0;site_tgavail=0;site_tcnt2=0;site_tgavail7=0;site_cumgenall=0;cumgenallp2=0;
			  		for(int j=0;j<tranListDataPhase2.size();j++)
				  	{  
			  		  
				  		Vector v1 = new Vector();
						v1 = (Vector) tranListDataPhase2.get(j);
						int avg=0; int avg1=0; int tavg=0;
						avg=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
						avg1=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
						// String sname=v1.get(0).toString();
						// System.out.println(v1.get(11).toString());	
						
						//if(avg==0)
						//	avg = 1;
						
						// if(!v1.get(11).toString().contains("1000000164,1103000001,1000000184,1000000167,1003000006,1000000181,1102000002,1000000172,1103000002"))
						
						/*if(!stateId.equals(v1.get(12).toString())){
							stateId = v1.get(12).toString();
							a = CustomerDao.getStateCount(ardate, cuid, v1.get(12).toString());
							b=a;
						}*/
						
						gotoeneter=0;
						
						/*if(v1.get(11).toString().equals("1110000004"))
							gotoeneter=1;
						else if(v1.get(11).toString().equals("1110000007"))
							gotoeneter=1;						
						else if(v1.get(11).toString().equals("1110000002"))
							gotoeneter=1;
						else if(v1.get(11).toString().equals("1110000003"))
							gotoeneter=1;						
						else if(v1.get(11).toString().equals("1110000005"))
							gotoeneter=1;
						else if(v1.get(11).toString().equals("1110000006"))
							gotoeneter=1;	
						else if(v1.get(11).toString().equals("1010000003"))
							gotoeneter=2;
						else if(v1.get(11).toString().equals("1010000004"))
							gotoeneter=2;
						else if(v1.get(11).toString().equals("1010000005"))
							gotoeneter=2;*/
						
						if(gotoeneter!=1)
						{
									if(avg!=0) // if((avg!=0|| avg1 !=0) && avg!=0)
									{  									 
									
									 tavg= Math.round(avg1/avg);
									 double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
									 // double cgg=Math.round(cg/100000);
										    
									 double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
									 	// String cg21=Dformat.format(cg2);
									 msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(cg)+"</td></tr>";
									    // msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='4' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
									 	//if(a==b)
								 		//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
								 		//else
								 		//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
								 		//a--;
									 
									    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
										tavgt+=tavg;
										tcntp2+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());										
										tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
										cumgenp2+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
										tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
										tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
										tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
										tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
										tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());										
										
										cumgenall+=cg;									
										cumgenallp2+=cg;
									}
									else
									{
										msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//if(a==b)
										//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//else
										//	msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
										//a--;
										
										String remString = CustomerDao.getSiteRemarks(v1.get(0).toString());									 
										msg += remString+"<td></tr>";
										
									}
									sn++;	
						}else{
							if(avg == 0)
					 			avg = 1;
					 		tavg= Math.round(avg1/avg);
					 		site_cnt+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
						 
						    site_tavgt+=tavg;
						    site_tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
						    site_revenue+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
						    site_tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
						    site_tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
						    site_tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
						    // site_tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
						    site_cg+=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());   
						    site_cg2+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
				 
					        acntt=acntt+1;
					         
					        // tavg= Math.round(avg1/avg);
							double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
								//double cgg=Math.round(cg/100000);
								    
							double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
							// String cg21=Dformat.format(cg2);
							
							// msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='4' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
							 
						    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
							tavgt+=tavg;
							tcntp2+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
							tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
							cumgenp2+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
							tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
							tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
							tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
							tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
							tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
						
							cumgenall+=cg;
							cumgenallp2+=cg;
						}
						 		
					}
			  		
			  		tranListDataPhase2.clear();
			  		/*if(cuid.equals("1009000010"))
					{
			  		if(acntt==0)
			  			acntt=1;
				  	  site_tavgt= site_tavgt/acntt;
			  		  site_tmavail= site_tmavail/acntt;
			  		  site_tgavail= site_tgavail/acntt;
			  		  site_tcnt2= site_tcnt2/acntt;
			  		  msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>GUJRAT</td><td align='left' colspan='2'>"+site_name+"</td><td align='center' >"+site_cnt+"</td> <td align='center' >"+site_tavgt+"</td><td align='center' >"+site_tgen+"</td><td align='center' > "+site_revenue+" </td><td align='center' >"+Dformat1.format(site_tcnt2)+"</td><td align='center' >"+Dformat1.format(site_tmavail)+"</td><td align='center' >"+Dformat1.format(site_tgavail)+"</td><td align='center' >"+Dformat.format(site_cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(site_cg)+"</td></tr>";
					}*/
			  		if(sn>1){
			  		  msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center' colspan='5'><b>Total</b></td>" +
	  		        
	  		          "<td align='center' ><b>"+ tcntp2+"</b></td>" +	  
	  		          //+ Math.round(tavgt/tcnt1) +
	  		          "<td align='center' >&nbsp;</td>"+
	  		          "<td align='center' >&nbsp;</td>"+
	  		     
	  		     
			  		  "<td align='center'><b>"+cumgenp2+"</b></td>" +	
			  		  "<td align='center' >&nbsp;</td>"+
			  		  "<td align='center' >&nbsp;</td>"+
			  		  "<td align='center' >&nbsp;</td>"+
			  		  "<td align='center' >&nbsp;</td>"+
			  		  // "<td align='center' >&nbsp;</td>"+
			  		 
		  			  // "<td align='center' ><b>"+tgen+"</b></td>" +
		  			  // "<td align='center' ><b>"+cumgen+"</b></td>" +	
		  			  // "<td align='center'><b>"+ Math.round(tcfac/tcnt1)+"</b></td>" +
		  			  // "<td align='center'><b>"+ Math.round(tmavail/tcnt1)+"</b></td>" +
		  			  // "<td align='center'><b>"+ Math.round(tgavail/tcnt1)+"</b></td>" +
		  			  // "<td align='center'><b>"+Math.round(tgavail7)+"</b></td>" +
			  						
			  		"<td align='center' colspan='3'><b>"+decimalFormatWithTwoPlaces.format(cumgenallp2)+"</b></td></tr>" ;		
			  		}
			  		tcnt1+=tcntp2;
			  		cumgen+=cumgenp2;
			  		
			  		msg +="<tr><td align='center' colspan='16'>&nbsp;</td></tr>"; 	
			  		
		  		}
		  		else
		  		{
		  			String stateId = "";
					//int checkState = 0;
					//int checkFlag = 1;
					int a = 1;
					int b = 1;
					
		  			List tranListData1 = (List) CustomerDao.getWECSiteWise(previuosDate,customerId);
		  			for(int j=0;j<tranListData1.size();j++)
				  	{  
				  		Vector v1 = new Vector();
						v1 = (Vector) tranListData1.get(j);
						int avg=0; int avg1=0; int tavg=0;
						avg=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
						avg1=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
						if(!stateId.equals(v1.get(12).toString())){
							stateId = v1.get(12).toString();
							a = CustomerDao.getStateCount(previuosDate, customerId, v1.get(12).toString());
							b=a;
						}
						//if(avg==0)
						//	avg = 1;
						// String sname=v1.get(0).toString();
						// System.out.println(v1.get(11).toString());
						gotoeneter=0;
						if(customerId.equals("1004000026"))
						{
							if(v1.get(11).toString().equals("1000000164"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1103000001"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000184"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000167"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1003000006"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000181"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1102000002"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000172"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1103000002"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000180"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000171"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1000000183"))
								gotoeneter=1;
							
						}/*else if(cuid.equals("1009000010")) //check this to club the sites if possible
						{
							if(v1.get(11).toString().equals("1000000141"))
								gotoeneter=3;
							else if(v1.get(11).toString().equals("1001000002"))
								gotoeneter=3;
							else if(v1.get(11).toString().equals("1010000001"))
								gotoeneter=3;
							else if(v1.get(11).toString().equals("1105000001"))
								gotoeneter=3;
							else if(v1.get(11).toString().equals("1110000004"))
								gotoeneter=2;
							else if(v1.get(11).toString().equals("1110000007"))
								gotoeneter=2;						
							else if(v1.get(11).toString().equals("1110000002"))
								gotoeneter=2;
							else if(v1.get(11).toString().equals("1110000003"))
								gotoeneter=2;						
							else if(v1.get(11).toString().equals("1110000005"))
								gotoeneter=2;
							else if(v1.get(11).toString().equals("1110000006"))
								gotoeneter=2;
							else if(v1.get(11).toString().equals("1010000003"))
								gotoeneter=1;	
							else if(v1.get(11).toString().equals("1010000004"))
								gotoeneter=1;
							else if(v1.get(11).toString().equals("1010000005"))
								gotoeneter=1;	
						}*/
						
						// if(!v1.get(11).toString().contains("1000000164,1103000001,1000000184,1000000167,1003000006,1000000181,1102000002,1000000172,1103000002"))
						if(gotoeneter!=1)
						{
								/*if(j==0){
							 		checkState = 1;
							 		stateId = v1.get(11).toString();
							 	}
							 	else {
							 		if(stateId.equals(v1.get(11).toString()))
							 			checkState++ ;
							 		else
							 			checkState = 1;
							 		stateId = v1.get(11).toString();
							 	}*/
								if(avg!=0) // if((avg!=0|| avg1 !=0) && avg!=0)
								{  										
								
									tavg= Math.round(avg1/avg);
									double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
									// double cgg=Math.round(cg/100000);										
									    
									double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
									// String cg21=Dformat.format(cg2);									 
								 	
							 		if(a==b)
							 			msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(cg)+"</td></tr>";
							 		else
							 			msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(cg)+"</td></tr>";
							 		a--;
								 	
								 	
								    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
									tavgt+=tavg;
									tcnt1+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
									tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
									cumgen+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
									tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
									tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
									tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
									tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
									tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
								
									cumgenall+=cg;
								}
								else
								{
									if(a==b)
										msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2' rowspan='"+a+"'>"+v1.get(12)+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
									else
										msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan='2'>"+v1.get(0)+"</td><td align='center' colspan='9'>";
									a--;
									String remString = CustomerDao.getSiteRemarks(v1.get(0).toString());
									msg += remString+"<td></tr>";										
									
								}
								sn++;
						 	}
						 	else
						 	{  									
							 		if(avg == 0)
							 			avg = 1;
							 		tavg= Math.round(avg1/avg);
							 		site_cnt+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
								 
								    site_tavgt+=tavg;
								    site_tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
								    site_revenue+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
								    site_tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
								    site_tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
								    site_tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
								    // site_tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
								    site_cg+=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());   
								    site_cg2+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
						 
							        acntt=acntt+1;
							         
							        // tavg= Math.round(avg1/avg);
									double cg=Double.parseDouble(v1.get(9)==null?"1":v1.get(9).toString());
									// double cgg=Math.round(cg/100000);
										    
									double cg2=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
									// String cg21=Dformat.format(cg2);
									
									// msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='4' >"+v1.get(0)+"</td><td align='center' >"+v1.get(2)+"</td> <td align='center' >"+tavg+"</td><td align='center' >"+v1.get(3)+"</td><td align='center' > "+v1.get(7)+" </td><td align='center' >"+v1.get(4)+"</td><td align='center' >"+v1.get(5)+"</td><td align='center' >"+v1.get(6)+"</td><td align='center' >"+Dformat.format(cg2)+"</td><td align='center' colspan='3' >"+Dformat.format(cg)+"</td></tr>";
									 
								    tcnt+=Integer.parseInt(v1.get(1)==null?"0":v1.get(1).toString());
									tavgt+=tavg;
									tcnt1+=Integer.parseInt(v1.get(2)==null?"0":v1.get(2).toString());
									tcnt2+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
									cumgen+=Integer.parseInt(v1.get(7)==null?"0":v1.get(7).toString());
									tgen+=Integer.parseInt(v1.get(3)==null?"0":v1.get(3).toString());
									tcfac+=Double.parseDouble(v1.get(4)==null?"0":v1.get(4).toString());
									tmavail+=Double.parseDouble(v1.get(5)==null?"0":v1.get(5).toString());
									tgavail+=Double.parseDouble(v1.get(6)==null?"0":v1.get(6).toString());
									tgavail7+=Double.parseDouble(v1.get(8)==null?"0":v1.get(8).toString());
								
									cumgenall+=cg;
										
								 }
						 		
						  	}
		  		tranListData1.clear();
		  		}		  				  		 
		  		
		  		// "<td align='center'><b>"+tcnt1+"</b></td>" +
	  			// "<td align='center'><b>"+tcnt2+"</b></td>" +"+tcnt+"
		  		// msg+="<tr><td align='center' colspan='16'>&nbsp;</td></tr>";
		  		if(customerId.equals("1004000026"))		  		
				{
		  			site_tavgt=site_tavgt/acntt;
		  			site_tmavail= site_tmavail/acntt;
		  			site_tgavail= site_tgavail/acntt;
		  			site_tcnt2=site_tcnt2/acntt;
		  		     msg +="<tr style='font-family:arial;font-size:12px;color:#000000' ><td align='center' >"+sn+"</td><td align='left' colspan='2'>GUJRAT</td><td align='left' colspan='2'>SAMANA</td><td align='center'>"+site_cnt+"</td> <td align='center' >"+site_tavgt+"</td><td align='center' >"+site_tgen+"</td><td align='center' > "+site_revenue+" </td><td align='center' >"+decimalFormatWithOnePlace.format(site_tcnt2)+"</td><td align='center' >"+decimalFormatWithOnePlace.format(site_tmavail)+"</td><td align='center' >"+decimalFormatWithOnePlace.format(site_tgavail)+"</td><td align='center' >"+decimalFormatWithTwoPlaces.format(site_cg2)+"</td><td align='center' colspan='3' >"+decimalFormatWithTwoPlaces.format(site_cg)+"</td></tr>";
				}
		  		// site_tavgt = 0; site_tgavail = 0; site_tmavail= 0;
		  		msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center' colspan='5'><b>Total</b></td>" +
		  		        
		  		        "<td align='center' ><b>"+ tcnt1+"</b></td>" +	  
		  		        //+ Math.round(tavgt/tcnt1) +
		  		        "<td align='center' >&nbsp;</td>"+
		  		        "<td align='center' >&nbsp;</td>"+
		  		     
		  		     
		  		  "<td align='center'><b>"+cumgen+"</b></td>" +	
		  		  "<td align='center' >&nbsp;</td>"+
		  		  "<td align='center' >&nbsp;</td>"+
		  		  "<td align='center' >&nbsp;</td>"+
		  		  "<td align='center' >&nbsp;</td>"+
		  		  // "<td align='center' >&nbsp;</td>"+
		  		 
		  		  // "<td align='center' ><b>"+tgen+"</b></td>" +
		  		  // "<td align='center' ><b>"+cumgen+"</b></td>" +	
		  		  // "<td align='center'><b>"+ Math.round(tcfac/tcnt1)+"</b></td>" +
		  		  // "<td align='center'><b>"+ Math.round(tmavail/tcnt1)+"</b></td>" +
		  		  // "<td align='center'><b>"+ Math.round(tgavail/tcnt1)+"</b></td>" +
		  		  // "<td align='center'><b>"+Math.round(tgavail7)+"</b></td>" +
		  						
		  		"<td align='center' colspan='3'><b>"+decimalFormatWithTwoPlaces.format(cumgenall)+"</b></td></tr>" ;				
		  		
		  		msg +="<tr><td align='center' colspan='16'>&nbsp;</td></tr>";
		  		System.out.println("*******OVERALL***********");
			  	List tranListData = new ArrayList();
			  	
				tranListData = (List) CustomerDao.getStateWise3(customerId,previuosDate); 
			  	String RemarksWEC="";
			  	sn=1;
			  	// boolean status=false;
			  	for(int i=0;i<tranListData.size();i++)
			  	{  
			  		RemarksWEC="";
			  		
			  		Vector vdata = new Vector();
					vdata = (Vector) tranListData.get(i);
					String id = (String) vdata.get(0);
					String site_name="";
					gotoeneter=0;
					if(customerId.equals("1004000026"))
					{
						if(id.equals("1000000164"))
							gotoeneter=1;
						else if(id.equals("1103000001"))
							gotoeneter=1;
						else if(id.equals("1000000184"))
							gotoeneter=1;
						else if(id.equals("1000000167"))
							gotoeneter=1;
						else if(id.equals("1003000006"))
							gotoeneter=1;
						else if(id.equals("1000000181"))
							gotoeneter=1;
						else if(id.equals("1102000002"))
							gotoeneter=1;
						else if(id.equals("1000000172"))
							gotoeneter=1;
						else if(id.equals("1103000002"))
							gotoeneter=1;
						else if(id.equals("1000000180"))
							gotoeneter=1;
						else if(id.equals("1000000171"))
							gotoeneter=1;
						else if(id.equals("1000000183"))
							gotoeneter=1;							
					}
					else if(customerId.equals("1009000010"))
					{
						if(id.equals("1000000141"))
							gotoeneter=3;
						else if(id.equals("1001000002"))
							gotoeneter=3;
						else if(id.equals("1010000001"))
							gotoeneter=3;
						else if(id.equals("1105000001"))
							gotoeneter=3;
						else if(id.equals("1110000004"))
							gotoeneter=2;
						else if(id.equals("1110000007"))
							gotoeneter=2;						
						else if(id.equals("1110000002"))
							gotoeneter=2;
						else if(id.equals("1110000003"))
							gotoeneter=2;						
						else if(id.equals("1110000005"))
							gotoeneter=2;
						else if(id.equals("1110000006"))
							gotoeneter=2;
						else if(id.equals("1010000003"))
							gotoeneter=1;	
						else if(id.equals("1010000004"))
							gotoeneter=1;
						else if(id.equals("1010000005"))
							gotoeneter=1;	
					}
					 if(gotoeneter==1) {
						 
						 site_name="SAMANA";
					 }
					 else if(gotoeneter==2)	 {
						 
						 site_name="LALPUR";
					 }
					 else {
						 site_name=(String) vdata.get(1);; 
					 }
					double per_unit=Double.parseDouble(vdata.get(5).toString());
					int mavial=Integer.parseInt(vdata.get(6).toString());
			  		// System.out.print(mavial);
			  		int gen=0,cgen=0;
			  		double rgen=0,rcgen=0;
			  		if(i==0){
			  		msg +="<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='830'align='center' colspan='16'><font class='sucessmsgtext'><b>Daily Details of WECs for which MA is less than 95% </b></font></td></tr>";
					
				  	msg +="<tr bgcolor='#BBB87E' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'><td><b>SN</b></td><td colspan=4 width=200><b>Site Name</b></td><td><b>WEC Name</b></td><td align='center'><b> Generation</b></td><td align='center' ><b>Operating Hours</b></td><td align='center'><b>MA(%)</td><td colspan=7><b>Remarks</b></td></tr>";
			  		}
			  		if(mavial<95){
			  		
					  		// msg +="<tr bgcolor='#F0F7A0' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'><td colspan='15' align='center'><b>"+vdata.get(1)+"-"+vdata.get(2)+"("+vdata.get(3)+" M/C-"+vdata.get(4)+"MW)-Unit Cost -RS. "+per_unit+"</b></td></tr>";
			  		}
			  		
			  		List tranListData2 = (List) CustomerDao.getWECWise(id,previuosDate,customerId);
			  		for(int j=0;j<tranListData2.size();j++)
				  	{
				  		Vector v1 = new Vector();
						v1 = (Vector) tranListData2.get(j);
						
						msg +="<tr   style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+sn+"</td><td align='left' colspan=4 width=200>"+site_name+"</td><td align='left'>"+v1.get(0)+"</td><td align='center'>"+v1.get(2)+"</td><td align='center'>"+v1.get(3)+"</td><td align='center'>"+v1.get(5)+"</td>";
						
						gen=gen+Integer.parseInt(v1.get(2).toString());
						cgen=cgen+Integer.parseInt(v1.get(11).toString());
						
						if (!v1.get(9).toString().equals("NIL")) 
						{
							if(!RemarksWEC.equals("."))
							{
							  RemarksWEC = (String) v1.get(9)+".";
							  msg +="<td  align='left' colspan='7' style='font-family:arial;font-size:12px;color:#000000';>"+RemarksWEC+" </td></tr>";
							  
							}
						 }
						sn++;
				  	}
			  		rgen=per_unit*gen;
			  		rcgen=per_unit*cgen;
			  		// String rgen1=formatter.format(rgen);
			  		// String rcgen1=formatter.format(rcgen);
		  		    // msg +="<td width='600' align='left' colspan='9' style='font-family:arial;font-size:13px;color:#000000';><b>Remarks:-</b>"+RemarksWEC+" </td></tr>";
		  		    // msg +="<tr bgcolor='blue' style='font-family:arial;font-size:13px;color:white';><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:white';><b>Total Units</b></td><td style='font-family:arial;font-size:13px;color:white';>"+gen+"</td><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:#white';><b>Total Revenue</b></td><td style='font-family:arial;font-size:13px;color:white';>"+rgen1+"</td><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:#white';><b>Comm. Revenue</b></td><td style='font-family:arial;font-size:13px;color:white';>"+rcgen1+"</td></tr>";
						
			  		  tranListData2.clear();
			  		
			  	}
			  	tranListData.clear();
			  	System.out.println("*******95%***********");
			  	// less then98
			  	//*********************************************************
			  	List tranListData4 = new ArrayList();
			  	
			  	 
				tranListData4 = (List) CustomerDao.getStateWise2(customerId,previuosDate); 
			  	// String RemarksWEC4="";
				
			  	sn=1;
			  	// boolean status4=false;
			  	for(int l=0;l<tranListData4.size();l++)
			  	{  
			  		RemarksWEC="";
			  		
			  		Vector vdata = new Vector();
					vdata = (Vector) tranListData4.get(l);
					String id = (String) vdata.get(0);
					
					String site_name="";
					String state_name=(String) vdata.get(2);
					gotoeneter=0;
					
					if(customerId.equals("1004000026"))
					{
						if(id.equals("1000000164"))
							gotoeneter=1;
						else if(id.equals("1103000001"))
							gotoeneter=1;
						else if(id.equals("1000000184"))
							gotoeneter=1;
						else if(id.equals("1000000167"))
							gotoeneter=1;
						else if(id.equals("1003000006"))
							gotoeneter=1;
						else if(id.equals("1000000181"))
							gotoeneter=1;
						else if(id.equals("1102000002"))
							gotoeneter=1;
						else if(id.equals("1000000172"))
							gotoeneter=1;
						else if(id.equals("1103000002"))
							gotoeneter=1;
						else if(id.equals("1000000180"))
							gotoeneter=1;
						else if(id.equals("1000000171"))
							gotoeneter=1;
						
					}
					else if(customerId.equals("1009000010"))
					{
						if(id.equals("1000000141"))
							gotoeneter=3;
						else if(id.equals("1001000002"))
							gotoeneter=3;
						else if(id.equals("1010000001"))
							gotoeneter=3;
						else if(id.equals("1105000001"))
							gotoeneter=3;
						else if(id.equals("1110000004"))
							gotoeneter=2;
						else if(id.equals("1110000007"))
							gotoeneter=2;						
						else if(id.equals("1110000002"))
							gotoeneter=2;
						else if(id.equals("1110000003"))
							gotoeneter=2;						
						else if(id.equals("1110000005"))
							gotoeneter=2;
						else if(id.equals("1110000006"))
							gotoeneter=2;
						else if(id.equals("1010000003"))
							gotoeneter=1;	
						else if(id.equals("1010000004"))
							gotoeneter=1;
						else if(id.equals("1010000005"))
							gotoeneter=1;	
					}
					if(gotoeneter==1) {
						 
						 site_name="SAMANA";
					} else if(gotoeneter==2)	 {
						 
						 site_name="LALPUR";
					} else {
						 site_name=(String) vdata.get(1);
					}
					 
					double per_unit=Double.parseDouble(vdata.get(6).toString());
					
					double mavial=Double.parseDouble(vdata.get(5).toString());
					String rd = (String) vdata.get(7);
			  		// System.out.print(mavial);
			  		// int gen=0,cgen=0;
			  		// double rgen=0,rcgen=0;
			  	if(l==0){
			  		msg +="<tr  style='font-family:arial;font-size:14px;color:#000000';font-weight: bold;'><td width='830'align='center' colspan='16'><font class='sucessmsgtext'><b>Monthly Details of WECs for which MA is less than 98% </b></font></td></tr>";
			  		msg +="<tr bgcolor='#BBB87E' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'><td><b>SN</b></td><td colspan='2' align='left' width=200><b>State Name</b></td><td colspan='2' align='left' width=200><b>Site Name</b></td><td colspan=3 align='left'><b>WEC Name</b></td><td colspan=3 align='center'><b> Generation</b></td><td colspan=3 align='center'><b>Operating Hours</b></td><td colspan=3 align='center'><b>MA (%)</td></tr>";
					
			  	}
			  	if(mavial<98){
			    }
			  		// if(mavial<98){
			  		
					  		// msg +="<tr bgcolor='#F0F7A0' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'><td colspan='15' align='center'><b>"+vdata.get(1)+"-"+vdata.get(2)+"("+vdata.get(3)+" M/C-"+vdata.get(4)+"MW)-Unit Cost -RS. "+per_unit+"</b></td></tr>";
			  		// }
			  		List tranListData61 = new ArrayList();
					  	
			  		tranListData61 = (List) CustomerDao.getWECWise1(id,rd,previuosDate,customerId);
			  		for(int j=0;j<tranListData61.size();j++)
				  	{
				  		Vector v1 = new Vector();
						v1 = (Vector) tranListData61.get(j);
						String ss=v1.get(4).toString();
						//if(j==0){
						//}
						double sss=Double.parseDouble(ss);
						
						if(sss<98){
						msg +="<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"+jj+"</td><td align='left' colspan='2' width=200>"+state_name+"</td><td align='left' colspan='2' width=200>"+site_name+"</td><td colspan=3 align='left'>"+v1.get(0)+"</td><td align='center' colspan=3>"+v1.get(2)+"</td><td align='center' colspan=3>"+v1.get(3)+"</td><td align='center' colspan=3>"+v1.get(4)+"</td></tr>";
						jj++;
						}
						// gen=gen+Integer.parseInt(v1.get(2).toString());
						// cgen=cgen+Integer.parseInt(v1.get(11).toString());
						
						// if (!v1.get(9).toString().equals("NIL")) 
						// {
							// if(!RemarksWEC4.equals("."))
							// {
							// 		RemarksWEC4 = RemarksWEC4 +(String) v1.get(9)+".";
							//   	msg +="<td  align='left' colspan='7' style='font-family:arial;font-size:13px;color:#000000';> </td></tr>";
							  
							// }
						// }
						sn++;
				  	}
			  		// rgen=per_unit*gen;
			  		// rcgen=per_unit*cgen;
			  		// String rgen1=formatter.format(rgen);
			  		// String rcgen1=formatter.format(rcgen);
			  		// msg +="<td width='600' align='left' colspan='9' style='font-family:arial;font-size:13px;color:#000000';><b>Remarks:-</b>"+RemarksWEC+" </td></tr>";
			  		// msg +="<tr bgcolor='blue' style='font-family:arial;font-size:13px;color:white';><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:white';><b>Total Units</b></td><td style='font-family:arial;font-size:13px;color:white';>"+gen+"</td><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:#white';><b>Total Revenue</b></td><td style='font-family:arial;font-size:13px;color:white';>"+rgen1+"</td><td align='left' colspan='2' style='font-family:arial;font-size:13px;color:#white';><b>Comm. Revenue</b></td><td style='font-family:arial;font-size:13px;color:white';>"+rcgen1+"</td></tr>";
						
			  		tranListData61.clear();
			  		
			  	}
			  	tranListData4.clear();
			  	System.out.println("*******98%***********");
			  	 
			  	msg +="</table>";
			  				  		
			  	String emailstr1 = "mithul.bhansali@windworldindia.com,vivek.chaudhary@windworldindia.com,Shazia.Kamal@windworldindia.com," +
					 "Manjit.Bhagria@windworldindia.com,Dinesh.Majithia@windworldindia.com," +
					 "Siddharth.Mehra@windworldindia.com,Narendra.Somoshi@windworldindia.com," +
					 "Shaheen.Momin@windworldindia.com,Manoj.Tiwari@windworldindia.com," +					 
					 "Ashutosh.Rahane@windworldindia.com,yogeshmehra5599@yahoo.co.in," +
					 "yogesh.mehra@windworldindia.com,Rajnish.Khanna@windworldindia.com," +
					 "Manvir.Singh@windworldindia.com,Harsh.Thaker@windworldindia.com," +
					 "Bikram.Baruah@windworldindia.com," +					 
					 "ketan.kadam@windworldindia.com," +
					 "prashant.yevale@windworldindia.com,sumit.prasad@windworldindia.com," +
					 "ashish.srivastava@windworldindia.com,rohan.gupta@windworldindia.com,Generation.Assurance@windworldindia.com,mithul.bhansali80@gmail.com";
			  	
			  	
			  	/*String emailstr1 = "vivek.chaudhary@windworldindia.com,mithul.bhansali@windworldindia.com";*/
			  	/*String emailstr1 = "mithul.bhansali@windworldindia.com,Manoj.Tiwari@windworldindia.com";*/
			  	
			  	String comma1[] = emailstr1.split(",");
			  	for(int i = 0;i < Array.getLength(comma1);i++){
			  		String email=(String)Array.get(comma1, i);
			  		//sm.sendMail(email,"WindWorldCare@windworldindia.com","Generation Report Of "+customerNameIdentity+" For The Date - "+previuosDate,msg);
			  	}
			  	
			  	msg="";
      		}
		    System.out.println("Mailed Sent");
		    msg="";
		
		    CustomerUtil secUtil = new CustomerUtil();
	  	    List uploadList = new ArrayList();
	  	    uploadList = (List)secUtil.getStateSiteUploadStatus("",previuosDate);
		 
		  	msg ="<table width='630' border='0'  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
        	"<tr  style='font-family:arial;font-size:19px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Sir,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>" +        
        	"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'></font></td></tr>" +        	
        	"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>Please find below the status showing state wise daily generation data </i></b></font></td></tr>" +
        	"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i> uploading Completed/Balanced/Published.</i></b></font></td></tr>" +
        	"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
        	"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td td width='600'align='center' colspan='2'><table  border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;' width='630'>" +
        	"<tr><td colspan='5' align='center'><b>Data Uploaded</b></td></tr><tr bgcolor='#FFFFCC' align='center'><td><b>Site</b></td>" +
        	"<td><b>WEC</b></td><td><b>Completed</b></td><td><b>Balance</b></td><td><b>Published</b></td></tr>";
        	
		  	int totalwec=0,tlupload=0,tlbal=0,tlpublish=0;
		  	String statename = "", newstatename = "";
		  	 for (int i=0; i <uploadList.size()-1; i++)
	          {
		  		Vector v = new Vector();
		  		v = (Vector)uploadList.get(i);
		  		statename = v.get(0).toString();
		  		if (! statename.equals(newstatename)){
		  			msg+="<tr bgcolor='#FFFFCC' align='center'><td colspan='5'>" + statename + "</td></tr>";
		  		}
		  		 msg=msg+"<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td><b>"+v.get(1)+"</b></td><td>"+v.get(2)+"</td><td>"+v.get(3)+"</td><td>"+v.get(4)+"</td><td>"+v.get(6)+"</td></tr>";
		  		 totalwec=totalwec+Integer.parseInt(v.get(2).toString());
		  		 tlupload=tlupload+Integer.parseInt(v.get(3).toString());
		  		 tlbal=tlbal+Integer.parseInt(v.get(4).toString());
		  		 tlpublish=tlpublish+Integer.parseInt(v.get(6).toString());
		  		 newstatename = v.get(0).toString();
	          }
		  	uploadList.clear();
		  	msg=msg+"<tr bgcolor='#FFFFCC'><td><b>Total</b></td><td><b>"+ totalwec +"</b></td><td><b>"+ tlupload +"</b></td><td><b>"+ tlbal +"</b></td><td><b>"+ tlpublish +"</b></td></tr>";
		  	InitServlet servlet = new InitServlet();
		  	String cupath = servlet.getDatabaseProperty("appserver");
		  	// CreateChart cr=new CreateChart();
        	// String chartname=ardate.replace("/", "");
        	// cr.CreateChart(totalwec, tlupload, tlbal, tlpublish,chartname);
		  
        	// msg=msg+"<tr bgcolor='#FFFFCC'><td colspan='5'><img src='http://172.18.16.27:7001/ECARE/"+chartname+".jpg'></td></tr>";
        	msg=msg+"</table></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b></b></font></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>This report is automatically generated by ECare Application.</i></b></font></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>You will be getting this mail everyday at 11:00 AM.</i></b></font></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>&nbsp;</b></font></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>Thanks for viewing</i></b></font></td></tr>";
		  	msg=msg+"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>ECare-Admin</b></font></td></tr></table> ";
		  	int updata=tlupload-tlpublish;
		  	String wmsg=msg+"<script>";
  			// msg+="location.href='http://172.18.17.155:7001/ECARE/UploadGraph.jsp'";
		  	wmsg+="window.open('http://"+ cupath +":7001/ECARE/PieUploadGraph.jsp?pdata="+tlpublish+"&updata="+updata+"&bdata="+tlbal+"',null,'left=400,top=200,height=500,width=600,status=0,toolbar=0,menubar=0,resizable=0,titlebar=0,scrollbars=0')";
  			// msg+="window.open('http://172.18.17.155:7001/ECARE/PieUploadGraph.jsp?pdata="+2000+"&updata="+800+"&bdata="+200+"',null,'left=400,top=200,height=500,width=600,status=0,toolbar=0,menubar=0,resizable=0,titlebar=0,scrollbars=0')";
		  	wmsg+="</script>";
  	
		  	List emailList = new ArrayList();
		  	emailList = (List)secUtil.getEmployeeEmailList(); 
		  	for (int i=0; i <emailList.size(); i++)
	        {  
		  		 Vector v = new Vector();
		  		 v = (Vector)emailList.get(i);
		  		 String email=v.get(0)==null?"":v.get(0).toString();
		  		 
		  		 // String email="vivek.chaudhary@windworldindia.com";
		  		 //sm.sendMail(email,"WindWorldCare@windworldindia.com","Generation Data Upload Status For The Date - "+requestDate,wmsg);   
	        } 
		  	emailList.clear();	
		  	
		 	String emailstr = "Shazia.Kamal@windworldindia.com, c.kurubalan@windworldindia.com, " +
		  					  "shrinivas.bijapur@windworldindia.com, t.subramanian@windworldindia.com, " +
		  					  "ulkesh.sonawane@windworldindia.com, s.jayaprakash@windworldindia.com, " +
		  					  "Muppandal.Service@windworldindia.com, " +
		  					  "Poolavadi.Service@windworldindia.com, " +
		  					  "Satara.Service@windworldindia.com, " +
		  					  "Vinod.Pawar@windworldindia.com, a.karthik@windworldindia.com,Dinesh.Majithia@windworldindia.com, mithul.bhansali@windworldindia.com";
		  	
		  	// String emailstr = "vivek.chaudhary@windworldindia.com"; 
		  	String comma[] = emailstr.split(",");
		  	for(int i = 0;i < Array.getLength(comma);i++)
			 {
				 String email=(String)Array.get(comma, i);
			     //sm.sendMail(email,"WindWorldCare@windworldindia.com","Generation Data Upload Status For The Date - "+requestDate,msg);
			 }
		  	
			msg="";
			
			 //............Customer Email Facility...............//
			
			 String type="D";
		     List tranList = new ArrayList();
		     // List sitetranList = new ArrayList();
		     CustomerUtil secutils = new CustomerUtil();
		     List custtranList = new ArrayList();
		     custtranList = (List)secutils.getCustList(); 
		     // StringBuffer output = new StringBuffer();
		     for (int b=0; b <custtranList.size(); b++)
		     {   
		    	 Vector vcust = new Vector();
		    	 vcust = (Vector)custtranList.get(b);
		    	 custid=(String)vcust.get(0);
		    	 custname=(String)vcust.get(1);
		    	 custemail=(String)vcust.get(2);
		    	 secondEmailId=(String)vcust.get(3);
		    	 
		    	 String filename = "c:\\csv\\"+custid+".xls";
		    			    	  
		    	 File file=new File(filename);
		    	 boolean exists = file.exists();
		    	    if(exists)
		    	    {
		    	    	file.delete(); 
		    	    }
		    	    
		    	    FileWriter fw = new FileWriter(filename);
		    	    HSSFWorkbook wb = new HSSFWorkbook();
		    	    HSSFSheet sheet = wb.createSheet("DGR");
		    	   
		    	    sheet.setColumnWidth((
		    	            short)0,  (short)(200*25));

		    	    sheet.setColumnWidth((
		    	            short)1,  (short)(256*25));
		    	    
		    	    sheet.setColumnWidth((
		    	            short)2,  (short)(256*25));
		    	    
		    	    sheet.setColumnWidth((
		    	            short)3,  (short)(200*25));

		    	    sheet.setColumnWidth((
		    	            short)4,  (short)(256*25));
		    	    
		    	    sheet.setColumnWidth((
		    	            short)5,  (short)(256*25));

		    	    sheet.setColumnWidth((
		    	            short)6,  (short)(256*25));
		    	   
		    	    sheet.setColumnWidth((
		    	            short)7,  (short)(256*25));
		    	    sheet.setColumnWidth((
		    	            short)8,  (short)(256*25));
		    	    
		    	    HSSFRow row = sheet.createRow((short)0);
		    	    HSSFCellStyle cs1 = wb.createCellStyle();
		    	    HSSFFont f1 = wb.createFont();
		    	   
		    	    HSSFCellStyle cs2 = wb.createCellStyle();
		    	    HSSFFont f2 = wb.createFont();
		    	    
		    	    f1.setFontHeight((short)200);
	    	 		f1.setColor((short)HSSFFont.COLOR_NORMAL);
	    	 		f1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    	 		
	    	 		cs1.setFont(f1);
	    	 		cs1.setBorderBottom((short)HSSFCellStyle.BORDER_THICK);
	    	 		cs1.setBorderTop((short)HSSFCellStyle.BORDER_THICK);
	    	 		cs1.setBorderLeft((short)HSSFCellStyle.BORDER_THICK);
	    	 		cs1.setBorderRight((short)HSSFCellStyle.BORDER_THICK);
	    	 		// cs1.setVerticalAlignment((short)HSSFCellStyle.VERTICAL_JUSTIFY);
	    	 		boolean t =true;
	    	 		cs1.setWrapText(t);
	    	 		
	    	 		
	    	 		f2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    	 		
	    	 		cs2.setFont(f2);
	    	 		// cs2.setBorderBottom((short)HSSFCellStyle.BORDER_MEDIUM);
	    	 		// cs2.setBorderTop((short)HSSFCellStyle.BORDER_MEDIUM);
	    	 		// cs2.setBorderLeft((short)HSSFCellStyle.BORDER_MEDIUM);
	    	 		// cs2.setBorderRight((short)HSSFCellStyle.BORDER_MEDIUM);
	    	 		cs2.setVerticalAlignment((short)HSSFCellStyle.VERTICAL_CENTER);
		    	    
		    	 if(custid.equals("1000000064"))
		    	 {    	
		    		 	
		    	 		// cs1.setLocked(t);
		    	 		
		    	 		row.createCell((short)0).setCellValue("Date");
		    		    row.getCell((short)0).setCellStyle(cs1);
		    		 
			    		row.createCell((short)1).setCellValue("Location");
			    		row.getCell((short)1).setCellStyle(cs1);
				    		   
			    		row.createCell((short)2).setCellValue("Tower ID");
			    		row.getCell((short)2).setCellStyle(cs1);
				    		
			    		row.createCell((short)3).setCellValue("Daily Generation");
			    		row.getCell((short)3).setCellStyle(cs1);
				    		
		    			row.createCell((short)4).setCellValue("Operating Hours");
		    			row.getCell((short)4).setCellStyle(cs1);
		    			
				        row.createCell((short)5).setCellValue("Down Time Electrical(Grid Down)");
				        row.getCell((short)5).setCellStyle(cs1);
		    			
				    	row.createCell((short)6).setCellValue("Down Time Mechanical(Machine Down)");
				    	row.getCell((short)6).setCellStyle(cs1);
		    			
				    	tranList = (List)secutils.getMailData(custid,previuosDate,type);
				        for (int i=0; i <tranList.size(); i++)
							{
				 				Vector v = new Vector();
				 				v = (Vector)tranList.get(i);
				 				
				 				String fdate= AdminDao.convertDateFormat(v.get(0).toString(),"yyyy-MM-dd","dd.MM.yyyy");				
				 				
						    	row=sheet.createRow((short)i+1);
						    	row.createCell((short)0).setCellValue(fdate);
						    	row.getCell((short)0).setCellStyle(cs2);
				    			
				    			row.createCell((short)1).setCellValue(v.get(1).toString());
				    			row.getCell((short)1).setCellStyle(cs2);
				    			
				    			row.createCell((short)2).setCellValue(v.get(2).toString());
				    			row.getCell((short)2).setCellStyle(cs2);
				    			
				    			row.createCell((short)3).setCellValue(v.get(3).toString());
				    			row.getCell((short)3).setCellStyle(cs2);
				    			
				    			row.createCell((short)4).setCellValue(v.get(4).toString());
				    			row.getCell((short)4).setCellStyle(cs2);
				    			
				    			row.createCell((short)5).setCellValue(v.get(5).toString());
				    			row.getCell((short)5).setCellStyle(cs2);
				    			
				    			row.createCell((short)6).setCellValue(v.get(6).toString());
				    			row.getCell((short)6).setCellStyle(cs2);
									
							}
		    		 
		    	 }
		    	 /*else if(custid.equals("1000000064")){//0905000002

		    		 	row.createCell((short)0).setCellValue("Date");
		    		 	row.getCell((short)0).setCellStyle(cs1);
		    		 
			    		row.createCell((short)1).setCellValue("Location");
			    		row.getCell((short)1).setCellStyle(cs1);
				    		   
			    		row.createCell((short)2).setCellValue("Tower ID");
			    		row.getCell((short)2).setCellStyle(cs1);
			    		
			    		row.createCell((short)3).setCellValue("Total Generation");
			    		row.getCell((short)3).setCellStyle(cs1);
			    		row.createCell((short)4).setCellValue("Total Generation Yesterday");
			    		row.getCell((short)4).setCellStyle(cs1);
				    		
			    		row.createCell((short)5).setCellValue("Daily Generation");
			    		row.getCell((short)5).setCellStyle(cs1);
			    		
			    		row.createCell((short)6).setCellValue("Total Operating Hrs");
			    		row.getCell((short)6).setCellStyle(cs1);
			    		row.createCell((short)7).setCellValue("Total Operating Hrs Yesterday");
			    		row.getCell((short)7).setCellStyle(cs1);
			    		 
			    		row.createCell((short)8).setCellValue("Operating Hours");
			    		row.getCell((short)8).setCellStyle(cs1);
				    	
			    		row.createCell((short)9).setCellValue("Lull Hours");
			    		row.getCell((short)9).setCellStyle(cs1);
			    		 
			    		row.createCell((short)10).setCellValue("Capacity Factor(%)");
					    row.getCell((short)10).setCellStyle(cs1);
					    	
		    			row.createCell((short)11).setCellValue("Machine Availability(%)");
		    			row.getCell((short)11).setCellStyle(cs1);
		    			
		    			row.createCell((short)12).setCellValue("Grid Availability(%) Internal");
				        row.getCell((short)12).setCellStyle(cs1);
				        
				        row.createCell((short)13).setCellValue("Grid Availability(%) External");
				        row.getCell((short)13).setCellStyle(cs1);
				    	
				    	tranList = (List)secutils.getMailDataCLP(custid,ardate,type);
				        for (int i=0; i <tranList.size(); i++)
							{
				 				Vector v = new Vector();
				 				v = (Vector)tranList.get(i);				 				
				 				
				 				String fdate= ad.convertDateFormat(v.get(0).toString(),"yyyy-MM-dd","dd.MM.yyyy");				
				 				
						    	row=sheet.createRow((short)i+1);
						    	row.createCell((short)0).setCellValue(fdate);
						    	row.getCell((short)0).setCellStyle(cs2);
				    			
				    			row.createCell((short)1).setCellValue(v.get(1).toString());
				    			row.getCell((short)1).setCellStyle(cs2);
				    			
				    			row.createCell((short)2).setCellValue(v.get(2).toString());
				    			row.getCell((short)2).setCellStyle(cs2);
				    			
				    			row.createCell((short)3).setCellValue(v.get(11).toString());
				    			row.getCell((short)3).setCellStyle(cs2);
				    			row.createCell((short)4).setCellValue(v.get(13).toString());
				    			row.getCell((short)4).setCellStyle(cs2);
				    			
				    			row.createCell((short)5).setCellValue(v.get(3).toString());
				    			row.getCell((short)5).setCellStyle(cs2);
				    			
				    			row.createCell((short)6).setCellValue(v.get(12).toString());
				    			row.getCell((short)6).setCellStyle(cs2);
				    			row.createCell((short)7).setCellValue(v.get(14).toString());
				    			row.getCell((short)7).setCellStyle(cs2);
				    			
				    			row.createCell((short)8).setCellValue(v.get(4).toString());
				    			row.getCell((short)8).setCellStyle(cs2);
				    			
				    			row.createCell((short)9).setCellValue(v.get(5).toString());
				    			row.getCell((short)9).setCellStyle(cs2);

				    			row.createCell((short)10).setCellValue(v.get(8).toString());
				    			row.getCell((short)10).setCellStyle(cs2); 
				    			
				    			row.createCell((short)11).setCellValue(v.get(9).toString());
				    			row.getCell((short)11).setCellStyle(cs2);
				    			
				    			row.createCell((short)12).setCellValue(v.get(10).toString());
				    			row.getCell((short)12).setCellStyle(cs2);
				    			
				    			row.createCell((short)13).setCellValue(v.get(7).toString());
				    			row.getCell((short)13).setCellStyle(cs2);				    			
									
							}	
		    	  
		    	 }*/
		    	 else
		    	 {
		    		 	row.createCell((short)0).setCellValue("Date");
		    		 	row.getCell((short)0).setCellStyle(cs1);
		    		 
			    		row.createCell((short)1).setCellValue("Location");
			    		row.getCell((short)1).setCellStyle(cs1);
				    		   
			    		row.createCell((short)2).setCellValue("Tower ID");
			    		row.getCell((short)2).setCellStyle(cs1);
				    		
			    		row.createCell((short)3).setCellValue("Daily Generation");
			    		row.getCell((short)3).setCellStyle(cs1);
			    		 
			    		row.createCell((short)4).setCellValue("Operating Hours");
			    		row.getCell((short)4).setCellStyle(cs1);
				    	
			    		row.createCell((short)5).setCellValue("Lull Hours");
			    		row.getCell((short)5).setCellStyle(cs1);
			    		 
			    		row.createCell((short)6).setCellValue("Capacity Factor(%)");
					    row.getCell((short)6).setCellStyle(cs1);
					    	
		    			row.createCell((short)7).setCellValue("Machine Availability(%)");
		    			row.getCell((short)7).setCellStyle(cs1);
		    			
		    			row.createCell((short)8).setCellValue("Grid Availability(%) Internal");
				        row.getCell((short)8).setCellStyle(cs1);
				        
				        row.createCell((short)9).setCellValue("Grid Availability(%) External");
				        row.getCell((short)9).setCellStyle(cs1);
				    	
				    	tranList = (List)secutils.getMailDataCustomer(custid,previuosDate,type);
				        for (int i=0; i <tranList.size(); i++)
							{
				 				Vector v = new Vector();
				 				v = (Vector)tranList.get(i);				 				
				 				
				 				String fdate= AdminDao.convertDateFormat(v.get(0).toString(),"yyyy-MM-dd","dd.MM.yyyy");				
				 				
						    	row=sheet.createRow((short)i+1);
						    	row.createCell((short)0).setCellValue(fdate);
						    	row.getCell((short)0).setCellStyle(cs2);
				    			
				    			row.createCell((short)1).setCellValue(v.get(1).toString());
				    			row.getCell((short)1).setCellStyle(cs2);
				    			
				    			row.createCell((short)2).setCellValue(v.get(2).toString());
				    			row.getCell((short)2).setCellStyle(cs2);
				    			
				    			row.createCell((short)3).setCellValue(v.get(3).toString());
				    			row.getCell((short)3).setCellStyle(cs2);
				    			
				    			row.createCell((short)4).setCellValue(v.get(4).toString());
				    			row.getCell((short)4).setCellStyle(cs2);
				    			
				    			row.createCell((short)5).setCellValue(v.get(5).toString());
				    			row.getCell((short)5).setCellStyle(cs2);

				    			row.createCell((short)6).setCellValue(v.get(8).toString());
				    			row.getCell((short)6).setCellStyle(cs2); 
				    			
				    			row.createCell((short)7).setCellValue(v.get(9).toString());
				    			row.getCell((short)7).setCellStyle(cs2);
				    			
				    			row.createCell((short)8).setCellValue(v.get(10).toString());
				    			row.getCell((short)8).setCellStyle(cs2);
				    			
				    			row.createCell((short)9).setCellValue(v.get(7).toString());
				    			row.getCell((short)9).setCellStyle(cs2);				    			
									
							}		    			
		    		 
		    	  }		    	
		    	 
		          tranList.clear();
		          FileOutputStream fileOut = new FileOutputStream("c:\\csv\\"+custid+".xls");
		          wb.write(fileOut);
		          fileOut.close(); 

		          file=new File(filename);
			      exists = file.exists();
			      if(exists)
			      { 			    	  
			    	  
			    	 msg ="<table width='630' border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Dear Sir/Madam,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
			        	
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Attached Please Find Generation Detail Of Your Wind Farm,</font></td></tr>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>For the Date "+ previuosDate +".</font></td></tr>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>" +
			        	
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left' ><font class='sucessmsgtext'><b>Regards,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>" +
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>WWIL(India) Ltd.,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+
			        	
			        	"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>Customer Support Team.</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"+
			        	// "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Attached Please Find Generation Detail Of Your Wind Farm,</font></td></tr>" +
			        	"</table>";			    	  
			      							
			    	 	sm.sendMailWithAttachement(custemail,"WindWorldCare@windworldindia.com","DGR For The Date - "+previuosDate,filename,msg);
			    	 	sm.sendMailWithAttachement(secondEmailId,"WindWorldCare@windworldindia.com","DGR For The Date - "+previuosDate,filename,msg);
			    	   	  
			      }
				
		     }
		
		   }
		   prepStmt.close();
		   rs.close();
		   }
		
		
	}catch(Exception e)
	{
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
	}
	finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps0,ps1) , Arrays.asList(rs,rs0,rs1) , conn);
    }
}
}
	




