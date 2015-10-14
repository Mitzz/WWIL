package com.enercon.global.utils;

import com.enercon.global.controller.InitServlet;
import com.enercon.global.utils.SendMail;
import com.enercon.customer.util.CustomerUtil;
import com.enercon.admin.dao.*;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;

public class SendMailAtEOD extends TimerTask {
	public final Connection conn;

	public SendMailAtEOD(Connection con) {
		conn = con;
	}

	public void run() {
		// //System..out.println("************************************************");

		String sqlQuery = null;
		PreparedStatement ps0 = null, ps1 = null;
		ResultSet rs0 = null, rs1 = null;
		SendMail sm = new SendMail();
		AdminDao ad = new AdminDao();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		// ResultSet rs1 = null;
		try {
			int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String rdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
			String ardate = dateFormat.format(date.getTime());
			// String ardate = dateFormat.format(date.getTime());
			String nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);
			// //System..out.println("Previous date: " + rdate);
			// //System..out.println("Currnent date: " + ardate);

			Calendar rightNow = Calendar.getInstance();
			String fd = "", td = "";
			java.util.Date ddate = new java.util.Date();
			int nday = ddate.getHours();
			System.out.println(nday);
			java.util.Date fromdate = new java.util.Date();
			java.util.Date todate = new java.util.Date();

			String custid = "", custname = "", custemail = "";
			nday = 1;
			/* /............Admin Email Facility.............../ */
			if (nday == 11) {

				sqlQuery = AdminSQLC.CHECK_SEND_MAIL;
				prepStmt = conn.prepareStatement(sqlQuery);
				int rcnt = 0;
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					rcnt = rs.getInt("cnt");
				}
				if (rcnt > 0) {

					String msg = "";
					/* /............Vish Wind Email Facility.............../ */

					/*
					 * for(int k=0;k<2;k++) { String cuid=""; String cname="";
					 * if(k==0) { cuid="1009000010";
					 * cname="VISH WIND INFRASTRUCTURE LLP"; } else {
					 * cuid="1009000004";
					 * cname="ILFS ENERGY DEVELOPMENT COMPANY LTD"; }
					 * 
					 * 
					 * 
					 * 
					 * 
					 * 
					 * msg =
					 * "<table width='630'    style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;>"
					 * ; msg +=
					 * "<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:20px;color:#000000';font-weight: bold;'><td width='630'align='center' colspan='9'><font class='sucessmsgtext'><b>Generation Report</b></font></td></tr>"
					 * ; msg +=
					 * "<tr  bgcolor='#ADD2D3' style='font-family:arial;font-size:19px;color:#000000';font-weight: bold;'><td width='630'align='center' colspan='9'><font class='sucessmsgtext'><b>"
					 * +cname+"</b></font></td></tr>";
					 * 
					 * String capacity=CustomerDao.getCustCapacity(cuid);
					 * 
					 * msg +=
					 * "<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td width='40'>&nbsp;</td><td colspan=2 align=center><b>Total Capacity</b></td><td><b>"
					 * +capacity+
					 * " MW</b></td><td colspan='2'>&nbsp;</td><td colspan='2' align=center><b>Report Date</b></td><td><b>"
					 * +rdate+"</b></td></tr>";
					 * 
					 * msg +=
					 * "<tr  style='font-family:arial;font-size:14px;color:#000000';font-weight: bold;'><td width='630'align='center' colspan='9'><font class='sucessmsgtext'><b>Site Wise Daily Generation Summary</b></font></td></tr>"
					 * ;
					 * 
					 * msg +=
					 * "<tr bgcolor='#BBB87E' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td  align='center' colspan='2'><b>SN</b></td><td colspan='3'><b>Site Name</b></td><td align='center'><b>Total WEC Commisioned</b></td><td align='center'><b>Data Uploaded</b></td><td align='center'><b>Balance M/C</b></td><td  align='center'><b>Daily Generation</b></td></tr>"
					 * ;
					 * 
					 * 
					 * List tranListData1 = (List)
					 * CustomerDao.getWECSiteWise(ardate,cuid); int sn=1; int
					 * tcnt=0,tgen=0,tcnt1=0,tcnt2=0,tgen1=0; for(int
					 * j=0;j<tranListData1.size();j++) { Vector v1 = new
					 * Vector(); v1 = (Vector) tranListData1.get(j);
					 * 
					 * msg +="<tr><td align='center' colspan='2'>"+sn+
					 * "</td><td align='left' colspan='3' >"
					 * +v1.get(0)+"</td><td align='center' >"
					 * +v1.get(1)+"</td><td align='center' >"
					 * +v1.get(2)+"</td><td align='center' >"
					 * +v1.get(4)+"</td><td align='center' >"
					 * +v1.get(3)+"</td></tr>";
					 * tcnt+=Integer.parseInt(v1.get(1)==
					 * null?"0":v1.get(1).toString());
					 * tcnt1+=Integer.parseInt(v1
					 * .get(2)==null?"0":v1.get(2).toString());
					 * tcnt2+=Integer.parseInt
					 * (v1.get(4)==null?"0":v1.get(4).toString());
					 * tgen+=Integer.
					 * parseInt(v1.get(3)==null?"0":v1.get(3).toString());
					 * 
					 * sn++; } tranListData1.clear();
					 * 
					 * msg
					 * +="<tr><td align='center' colspan='5'><b>Total</b></td>"
					 * + "<td align='center' ><b>"+tcnt+"</b></td>" +
					 * "<td align='center'><b>"+tcnt1+"</b></td>" +
					 * "<td align='center'><b>"+tcnt2+"</b></td>" +
					 * "<td align='center' ><b>"+tgen+"</b></td></tr>" ;
					 * 
					 * 
					 * 
					 * 
					 * msg
					 * +="<tr><td align='center' colspan='9'>&nbsp;</td></tr>";
					 * msg +=
					 * "<tr  style='font-family:arial;font-size:14px;color:#000000';font-weight: bold;'><td width='630'align='center' colspan='8'><font class='sucessmsgtext'><b>Site Wise Detail </b></font></td></tr>"
					 * ;
					 * 
					 * msg +=
					 * "<tr bgcolor='#BBB87E' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'><td><b>SN</b></td><td><b>WEC Name</b></td><td><b>Daily Generation</b></td><td ><b>Operating Hours</b></td><td ><b>Lull Hours</b></td><td ><b>Capacity Factor(%)</b></td><td><b>Machine Availability (%)</td><td><b>Grid Availability (%)</b></td><td><b>Cumm. Generation</b></td></tr>"
					 * ;
					 * 
					 * 
					 * List tranListData = new ArrayList();
					 * 
					 * tranListData = (List)
					 * CustomerDao.getStateWise(cuid,ardate); String
					 * RemarksWEC=""; sn=1; for(int
					 * i=0;i<tranListData.size();i++) { RemarksWEC=""; Vector
					 * vdata = new Vector(); vdata = (Vector)
					 * tranListData.get(i); String id = (String) vdata.get(0);
					 * msg +=
					 * "<tr bgcolor='#F0F7A0' style='font-family:arial;font-size:12px;color:#000000';font-weight: bold;'><td colspan='9' align='center'><b>"
					 * +
					 * vdata.get(1)+"-"+vdata.get(2)+"("+vdata.get(3)+" M/C-"+vdata
					 * .get(4)+"MW)</b></td></tr>";
					 * 
					 * 
					 * List tranListData2 = (List)
					 * CustomerDao.getWECWise(id,ardate,cuid); for(int
					 * j=0;j<tranListData2.size();j++) { Vector v1 = new
					 * Vector(); v1 = (Vector) tranListData2.get(j);
					 * 
					 * msg +=
					 * "<tr style='font-family:arial;font-size:12px;color:#000000'><td align='center'>"
					 * +sn+"</td><td align='left'>"+v1.get(0)+
					 * "</td><td align='center'>"
					 * +v1.get(2)+"</td><td align='center' >"
					 * +v1.get(3)+"</td><td align='center' >"
					 * +v1.get(4)+"</td><td align='center'>"
					 * +v1.get(7)+"</td><td align='center'>"
					 * +v1.get(5)+"</td><td align='center'>"
					 * +v1.get(6)+"</td><td align='center'>"
					 * +v1.get(11)+"</td></tr>";
					 * 
					 * if (!v1.get(9).toString().equals("NIL")) {
					 * if(!RemarksWEC.equals(".")) { RemarksWEC = RemarksWEC
					 * +(String) v1.get(9)+"."; } } sn++; } msg +=
					 * "<tr><td width='600' align='left' colspan='9' style='font-family:arial;font-size:13px;color:#000000';><b>Remarks:-</b>"
					 * +RemarksWEC+" </td></tr>";
					 * 
					 * tranListData2.clear();
					 * 
					 * } tranListData.clear(); msg +="</table>";
					 */

					// String emailstr1 =
					// "Bikram.Baruah@windworldindia.com,samir.singh@windworldindia.com,Narendra.Somoshi@windworldindia.com,abhishek.thakur@windworldindia.com,Shailendra.Tiwari@windworldindia.com,Shaheen.Momin@windworldindia.com,Manoj.Tiwari@windworldindia.com,Hardik.Dixit@windworldindia.com";
					/*
					 * String emailstr1 = "abhishek.thakur@windworldindia.com";
					 * 
					 * String comma1[] = emailstr1.split(","); for(int i = 0;i <
					 * Array.getLength(comma1);i++) { String
					 * email=(String)Array.get(comma1, i);
					 * sm.sendMail(email,"WindWorldCare@windworldindia.com"
					 * ,"Generation Report Of "
					 * +cname+" For The Date - "+rdate,msg); }
					 * 
					 * msg=""; }
					 */
					msg = "";
					CustomerUtil secUtil = new CustomerUtil();
					List uploadList = new ArrayList();
					uploadList = (List) secUtil.getStateSiteUploadStatus("",ardate);
					msg = "<table width='630' border='0'  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>"
							+ "<tr  style='font-family:arial;font-size:19px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Sir,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>"
							+

							"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'></font></td></tr>"
							+

							"<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>Please find below the status showing state wise daily generation data </i></b></font></td></tr>"
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i> uploading Completed/Balanced/Published.</i></b></font></td></tr>"
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>"
							+ "<tr style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td td width='600'align='center' colspan='2'><table  border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;' width='630'>"
							+ "<tr><td colspan='5' align='center'><b>Data Uploaded</b></td></tr><tr bgcolor='#FFFFCC' align='center'><td><b>Site</b></td>"
							+ "<td><b>WEC</b></td><td><b>Completed</b></td><td><b>Balance</b></td><td><b>Published</b></td></tr>";

					int totalwec = 0, tlupload = 0, tlbal = 0, tlpublish = 0;
					String statename = "", newstatename = "";
					for (int i = 0; i < uploadList.size() - 1; i++) {
						Vector v = new Vector();
						v = (Vector) uploadList.get(i);
						statename = v.get(0).toString();
						if (!statename.equals(newstatename)) {
							msg += "<tr bgcolor='#FFFFCC' align='center'><td colspan='5'>"
									+ statename + "</td></tr>";
						}
						msg = msg
								+ "<tr style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td><b>"
								+ v.get(1) + "</b></td><td>" + v.get(2)
								+ "</td><td>" + v.get(3) + "</td><td>"
								+ v.get(4) + "</td><td>" + v.get(6)
								+ "</td></tr>";
						totalwec = totalwec
								+ Integer.parseInt(v.get(2).toString());
						tlupload = tlupload
								+ Integer.parseInt(v.get(3).toString());
						tlbal = tlbal + Integer.parseInt(v.get(4).toString());
						tlpublish = tlpublish
								+ Integer.parseInt(v.get(6).toString());
						newstatename = v.get(0).toString();
					}
					uploadList.clear();
					msg = msg
							+ "<tr bgcolor='#FFFFCC'><td><b>Total</b></td><td><b>"
							+ totalwec + "</b></td><td><b>" + tlupload
							+ "</b></td><td><b>" + tlbal + "</b></td><td><b>"
							+ tlpublish + "</b></td></tr>";
					InitServlet servlet = new InitServlet();
					String cupath = servlet.getDatabaseProperty("appserver");
					// CreateChart cr=new CreateChart();
					// String chartname=ardate.replace("/", "");
					// cr.CreateChart(totalwec, tlupload, tlbal,
					// tlpublish,chartname);

					// msg=msg+"<tr bgcolor='#FFFFCC'><td colspan='5'><img src='http://172.18.16.27:7001/ECARE/"+chartname+".jpg'></td></tr>";
					msg = msg + "</table></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b></b></font></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>This report is automatically generated by ECare Application.</i></b></font></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>You will be getting this mail everyday at 11:00 AM.</i></b></font></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>&nbsp;</b></font></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>Thanks for viewing</i></b></font></td></tr>";
					msg = msg
							+ "<tr  style='font-family:arial;font-size:17px;color:#000000';font-weight: bold;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b><i>WCARE-Admin</b></font></td></tr></table> ";
					int updata = tlupload - tlpublish;
					String wmsg = msg + "<script>";
					// msg+="location.href='http://172.18.17.155:7001/ECARE/UploadGraph.jsp'";
					wmsg += "window.open('http://"
							+ cupath
							+ ":7001/ECARE/PieUploadGraph.jsp?pdata="
							+ tlpublish
							+ "&updata="
							+ updata
							+ "&bdata="
							+ tlbal
							+ "',null,'left=400,top=200,height=500,width=600,status=0,toolbar=0,menubar=0,resizable=0,titlebar=0,scrollbars=0')";
					// msg+="window.open('http://172.18.17.155:7001/ECARE/PieUploadGraph.jsp?pdata="+2000+"&updata="+800+"&bdata="+200+"',null,'left=400,top=200,height=500,width=600,status=0,toolbar=0,menubar=0,resizable=0,titlebar=0,scrollbars=0')";
					wmsg += "</script>";

					List emailList = new ArrayList();
					emailList = (List) secUtil.getEmployeeEmailList();
					for (int i = 0; i < emailList.size(); i++) {
						Vector v = new Vector();
						v = (Vector) emailList.get(i);
						String email = v.get(0) == null ? "" : v.get(0)
								.toString();
						// sm.sendMail(email,"WindWorldCare@windworldindia.com","Generation Data Upload Status For The Date - "+rdate,wmsg);
					}
					emailList.clear();
					String emailstr = "amit.kotekar@windworldindia.com, c.kurubalan@windworldindia.com, dharmendra.sutar@windworldindia.com, "
							+ "ganpati.v@windworldindia.com, shrinivas.bijapur@windworldindia.com, t.subramanian@windworldindia.com, "
							+ "ulkesh.sonawane@windworldindia.com, virendra.rathore@windworldindia.com, s.jayaprakash@windworldindia.com, "
							+ "Muppandal.Service@windworldindia.com, Pcb.Chitradurga@windworldindia.com , "
							+ "Poolavadi.Service@windworldindia.com, Porbandar.Service@windworldindia.com, "
							+ "S.Mustak@windworldindia.com, Satara.Service@windworldindia.com, "
							+ "Vinod.Pawar@windworldindia.com, a.karthik@windworldindia.com, lotus.serviceareainchargegroup@windworldindia.com";
					String comma[] = emailstr.split(",");
					for (int i = 0; i < Array.getLength(comma); i++) {
						String email = (String) Array.get(comma, i);
						// sm.sendMail(email,"WindWorldCare@windworldindia.com","Generation Data Upload Status For The Date - "+rdate,msg);
					}

					msg = "";

					/* /............Customer Email Facility.............../ */

					String type = "D";
					List tranList = new ArrayList();
					List sitetranList = new ArrayList();
					CustomerUtil secutils = new CustomerUtil();
					List custtranList = new ArrayList();
					custtranList = (List) secutils.getCustList();
					StringBuffer output = new StringBuffer();
					for (int b = 0; b < custtranList.size(); b++) {
						Vector vcust = new Vector();
						vcust = (Vector) custtranList.get(b);
						custid = (String) vcust.get(0);
						custname = (String) vcust.get(1);
						custemail = (String) vcust.get(2);

						String filename = "c:\\csv\\" + custid + ".xls";

						File file = new File(filename);
						boolean exists = file.exists();
						if (exists) {
							file.delete();
						}

						FileWriter fw = new FileWriter(filename);
						HSSFWorkbook wb = new HSSFWorkbook();
						HSSFSheet sheet = wb.createSheet("DGR");

						sheet.setColumnWidth((short) 0, (short) (200 * 25));

						sheet.setColumnWidth((short) 1, (short) (256 * 25));

						sheet.setColumnWidth((short) 2, (short) (256 * 25));

						sheet.setColumnWidth((short) 3, (short) (200 * 25));

						sheet.setColumnWidth((short) 4, (short) (256 * 25));

						sheet.setColumnWidth((short) 5, (short) (256 * 25));

						sheet.setColumnWidth((short) 6, (short) (256 * 25));

						sheet.setColumnWidth((short) 7, (short) (256 * 25));
						sheet.setColumnWidth((short) 8, (short) (256 * 25));

						HSSFRow row = sheet.createRow((short) 0);
						HSSFCellStyle cs1 = wb.createCellStyle();
						HSSFFont f1 = wb.createFont();

						HSSFCellStyle cs2 = wb.createCellStyle();
						HSSFFont f2 = wb.createFont();

						f1.setFontHeight((short) 200);
						f1.setColor((short) HSSFFont.COLOR_NORMAL);
						f1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

						cs1.setFont(f1);
						cs1.setBorderBottom((short) HSSFCellStyle.BORDER_THICK);
						cs1.setBorderTop((short) HSSFCellStyle.BORDER_THICK);
						cs1.setBorderLeft((short) HSSFCellStyle.BORDER_THICK);
						cs1.setBorderRight((short) HSSFCellStyle.BORDER_THICK);
						// cs1.setVerticalAlignment((short)HSSFCellStyle.VERTICAL_JUSTIFY);
						boolean t = true;
						cs1.setWrapText(t);

						f2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

						cs2.setFont(f2);
						// cs2.setBorderBottom((short)HSSFCellStyle.BORDER_MEDIUM);
						// cs2.setBorderTop((short)HSSFCellStyle.BORDER_MEDIUM);
						// cs2.setBorderLeft((short)HSSFCellStyle.BORDER_MEDIUM);
						// cs2.setBorderRight((short)HSSFCellStyle.BORDER_MEDIUM);
						cs2.setVerticalAlignment((short) HSSFCellStyle.VERTICAL_CENTER);

						if (custid.equals("1000000064")) {

							// cs1.setLocked(t);

							row.createCell((short) 0).setCellValue("Date");
							row.getCell((short) 0).setCellStyle(cs1);

							row.createCell((short) 1).setCellValue("Location");
							row.getCell((short) 1).setCellStyle(cs1);

							row.createCell((short) 2).setCellValue("Tower ID");
							row.getCell((short) 2).setCellStyle(cs1);

							row.createCell((short) 3).setCellValue(
									"Daily Generation");
							row.getCell((short) 3).setCellStyle(cs1);

							row.createCell((short) 4).setCellValue(
									"Operating Hours");
							row.getCell((short) 4).setCellStyle(cs1);

							row.createCell((short) 5).setCellValue(
									"Down Time Electrical(Grid Down)");
							row.getCell((short) 5).setCellStyle(cs1);

							row.createCell((short) 6).setCellValue(
									"Down Time Mechanical(Machine Down)");
							row.getCell((short) 6).setCellStyle(cs1);

							tranList = (List) secutils.getMailData(custid,rdate, type);
							for (int i = 0; i < tranList.size(); i++) {
								Vector v = new Vector();
								v = (Vector) tranList.get(i);

								String fdate = ad
										.convertDateFormat(v.get(0).toString(),
												"yyyy-MM-dd", "dd.MM.yyyy");

								row = sheet.createRow((short) i + 1);
								row.createCell((short) 0).setCellValue(fdate);
								row.getCell((short) 0).setCellStyle(cs2);

								row.createCell((short) 1).setCellValue(
										v.get(1).toString());
								row.getCell((short) 1).setCellStyle(cs2);

								row.createCell((short) 2).setCellValue(
										v.get(2).toString());
								row.getCell((short) 2).setCellStyle(cs2);

								row.createCell((short) 3).setCellValue(
										v.get(3).toString());
								row.getCell((short) 3).setCellStyle(cs2);

								row.createCell((short) 4).setCellValue(
										v.get(4).toString());
								row.getCell((short) 4).setCellStyle(cs2);

								row.createCell((short) 5).setCellValue(
										v.get(5).toString());
								row.getCell((short) 5).setCellStyle(cs2);

								row.createCell((short) 6).setCellValue(
										v.get(6).toString());
								row.getCell((short) 6).setCellStyle(cs2);

							}

						} else {
							row.createCell((short) 0).setCellValue("Date");
							row.getCell((short) 0).setCellStyle(cs1);

							row.createCell((short) 1).setCellValue("Location");
							row.getCell((short) 1).setCellStyle(cs1);

							row.createCell((short) 2).setCellValue("Tower ID");
							row.getCell((short) 2).setCellStyle(cs1);

							row.createCell((short) 3).setCellValue("Daily Generation");
							row.getCell((short) 3).setCellStyle(cs1);

							row.createCell((short) 4).setCellValue("Operating Hours");
							row.getCell((short) 4).setCellStyle(cs1);

							row.createCell((short) 5).setCellValue("Lull Hours");
							row.getCell((short) 5).setCellStyle(cs1);

							row.createCell((short) 6).setCellValue("Capacity Factor(%)");
							row.getCell((short) 6).setCellStyle(cs1);

							row.createCell((short) 7).setCellValue("Machine Availability(%)");
							row.getCell((short) 7).setCellStyle(cs1);

							row.createCell((short) 8).setCellValue("Grid Availability(%) Internal");
							row.getCell((short) 8).setCellStyle(cs1);

							row.createCell((short) 9).setCellValue(
									"Grid Availability(%) External");
							row.getCell((short) 9).setCellStyle(cs1);

							tranList = (List) secutils.getMailDataCustomer(custid, rdate, type);
							for (int i = 0; i < tranList.size(); i++) {
								Vector v = new Vector();
								v = (Vector) tranList.get(i);

								String fdate = ad
										.convertDateFormat(v.get(0).toString(),
												"yyyy-MM-dd", "dd.MM.yyyy");

								row = sheet.createRow((short) i + 1);
								row.createCell((short) 0).setCellValue(fdate);
								row.getCell((short) 0).setCellStyle(cs2);

								row.createCell((short) 1).setCellValue(
										v.get(1).toString());
								row.getCell((short) 1).setCellStyle(cs2);

								row.createCell((short) 2).setCellValue(v.get(2).toString());
								row.getCell((short) 2).setCellStyle(cs2);

								row.createCell((short) 3).setCellValue(v.get(3).toString());
								row.getCell((short) 3).setCellStyle(cs2);

								row.createCell((short) 4).setCellValue(
										v.get(4).toString());
								row.getCell((short) 4).setCellStyle(cs2);

								row.createCell((short) 5).setCellValue(
										v.get(5).toString());
								row.getCell((short) 5).setCellStyle(cs2);

								row.createCell((short) 6).setCellValue(
										v.get(8).toString());
								row.getCell((short) 6).setCellStyle(cs2);

								row.createCell((short) 7).setCellValue(
										v.get(9).toString());
								row.getCell((short) 7).setCellStyle(cs2);

								row.createCell((short) 8).setCellValue(
										v.get(10).toString());
								row.getCell((short) 8).setCellStyle(cs2);

								row.createCell((short) 9).setCellValue(
										v.get(7).toString());
								row.getCell((short) 9).setCellStyle(cs2);

							}

						}

						tranList.clear();
						FileOutputStream fileOut = new FileOutputStream(
								"c:\\csv\\" + custid + ".xls");
						wb.write(fileOut);
						fileOut.close();

						file = new File(filename);
						exists = file.exists();
						if (exists) {

							msg = "<table width='630' border='0' style='border:1px solid #D1DBDD;' style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;'>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='left' colspan='2'><font class='sucessmsgtext'><b>Dear Sir/Madam,</b></font>&nbsp;&nbsp;<font class='errormsgtext'> </font></td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>"
									+

									"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Attached Please Find Generation Detail Of Your Wind Farm,</font></td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>For the Date "
									+ rdate
									+ ".</font></td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'>&nbsp;</td></tr>"
									+

									"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left' ><font class='sucessmsgtext'><b>Regards,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>Wind World India(India) Ltd.,</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"
									+

									"<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='200' align='left'><font class='sucessmsgtext'><b>Customer Support Team.</b></font></td><td width='430'><font style='font-family:arial;font-size:14px;color:green';font-weight: bold;'></font></td></tr>"
									+ "<tr  style='font-family:arial;font-size:13px;color:#000000';font-weight: bold;border:1px solid #D1DBDD;'><td width='600'align='center' colspan='2'><font class='sucessmsgtext'>Attached Please Find Generation Detail Of Your Wind Farm,</font></td></tr>"
									+ "</table>";

							// sm.sendMailWithAttachement("Hardikndixit@yahoo.com","abhishek.thakur@windworldindia.com","DGR For The Date - "+rdate,filename,msg);

							// sm.sendMailWithAttachement(custemail,"Hardik.Dixit@windworldindia.com","DGR For The Date - "+rdate,filename,msg);

						}

					}

				}
				prepStmt.close();
				rs.close();
			}

		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		} finally {
			try {
				if (ps0 != null)
					ps0.close();
				if (ps1 != null)
					ps1.close();
				if (rs0 != null)
					rs0.close();
				if (rs1 != null)
					rs1.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				ps0 = null;
				ps1 = null;
				rs0 = null;
				ps1 = null;
			}
		}
	}
}