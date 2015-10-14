package com.enercon.reports.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import oracle.jdbc.driver.OracleTypes;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.master.StateMasterDao;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utility.TimeUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.reports.pojo.AreaWiseMGSAvailabilityReportVo;
import com.enercon.reports.pojo.GridBifurcationReportVo;
import com.enercon.reports.pojo.OverAllMGSAvailibilityVo;
import com.enercon.reports.pojo.StateAreaWiseMGSAvailabilityVo;
import com.enercon.struts.pojo.ScadaDataJump;

public class ReportDao implements WcareConnector{
	
	public ReportDao(){}

	public String getRPTAjaxDetails(String item,String action,String UserId) throws Exception{
		StringBuffer xml = new StringBuffer();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		//  PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlQuery = "";
		String msg="";
		try{ 
			if(action.equals("CUST_DGRVIEW"))
			{
				sqlQuery = ReportSQLC.SELECT_MATERIAL_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item.toUpperCase() + "%");
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<mmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<mtcode>");
					xml.append("<mid>");
					xml.append(rs.getObject("S_MATERIAL_ID"));
					xml.append("</mid>\n");
					xml.append("<mname>");
					xml.append(rs.getObject("S_MATERIAL_DESC"));
					xml.append("</mname>\n");
					xml.append("<mcode>");
					xml.append(rs.getObject("S_MATERIAL_NO"));
					xml.append("</mcode>\n");
					xml.append("</mtcode>\n");
				}
				xml.append("</mmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("PRJ_findMaterialMasterByID"))
			{
				sqlQuery = ReportSQLC.SELECT_MATERIAL_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<mmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<mtcode>");
					xml.append("<mid>");
					xml.append(rs.getObject("S_MATERIAL_ID"));
					xml.append("</mid>\n");
					xml.append("<mname>");
					xml.append(rs.getObject("S_MATERIAL_DESC"));
					xml.append("</mname>\n");
					xml.append("<mcode>");
					xml.append(rs.getObject("S_MATERIAL_NO"));
					xml.append("</mcode>\n");
					xml.append("</mtcode>\n");
				}
				xml.append("</mmaster>\n");	
				rs.close();
				ps.close();
			} 
			else if (action.equals("PRJ_getProject"))
			{
				sqlQuery = ReportSQLC.GET_PROJECT_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<projhead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<projcode>\n");
					xml.append("<pid>");
					xml.append(rs.getObject("S_PROJECT_ID"));
					xml.append("</pid>\n");
					xml.append("<pdef>");
					xml.append(rs.getObject("S_PROJECT_DEFINITION"));
					xml.append("</pdef>\n");

					xml.append("</projcode>\n");
				}			    
				xml.append("</projhead>\n");
			}

			else if (action.equals("PRJ_findProjectDetails"))
			{   String pid="";
			String strData="";
			String appid=item;
			String app_para[]=appid.split(",");
			sqlQuery = ReportSQLC.GET_PROJECT_HEADING;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,app_para[0]);
			ps.setObject(2,app_para[1]);
			rs = ps.executeQuery();
			xml.append("<?xml version=\"1.0\"?>\n");
			xml.append("<projhead generated=\""+System.currentTimeMillis()+"\">\n");
			xml.append("<projcode>\n");
			strData = "<table id = projHead  cellSpacing=0 cellPadding=0  border=0 width = 100%>";
			while (rs.next())
			{	

				xml.append("<pid>");
				xml.append(rs.getObject("S_PROJECT_ID"));
				xml.append("</pid>\n");
				xml.append("<pdef>");
				xml.append(rs.getObject("S_PROJECT_DEFINITION"));
				xml.append("</pdef>\n");
				strData = strData + "<tr><td colspan=6 width = 100%><TABLE cellSpacing=0 cellPadding=0 width=100% border=0><TBODY><TR>";

				strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_left.gif width=10 border=0></TD>";
				strData = strData + " <TD class=SectionTitle noWrap>Project Status Report</TD>";
				strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_right.gif width=10 border=0></TD></TR></TBODY></TABLE></TD></TR>";




				pid=rs.getObject("S_PROJECT_ID").toString();
				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 20%> </td>";
				strData = strData + "<td vAlign = middle  class=TableCell align = left width = 60% colspan=4>" +rs.getObject("S_PROJECT_DESC").toString()+" </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 20%> </td>";
				strData = strData + "</tr>";

				strData = strData + "<tr class=TableSummaryRow>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>Project Definition:</td>";
				strData = strData + "<td  vAlign = middle class=TableCell align = left  width = 38% colspan=2>" +rs.getObject("S_PROJECT_DEFINITION").toString()+" </td>";

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>State/Site:</td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left  width = 38% colspan=2>"+rs.getObject("S_STATE_NAME").toString()+"/"+rs.getObject("S_SITE_NAME").toString()+" </td>";

				strData = strData + "</tr>";
				strData = strData + "<tr class=TableSummaryRow>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableSummaryRow>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 18%>No. Of Machines X Type:</td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 38% colspan=2>" +rs.getObject("N_WEC_QUANTITY").toString()+" X "+rs.getObject("S_WECTYPE").toString()+"</td>";

				strData = strData + "<td vAlign = middle  class=TableCell align = left width = 18%>WEC Capacity: </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 38% colspan=2>" +rs.getObject("S_WEC_CAPACITY").toString()+" </td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableSummaryRow height=2>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
				strData = strData + "</tr>";
			}			    


			ps.close();
			rs.close();


			strData = strData + "<tr height=10>";
			strData = strData + "<td vAlign = middle align = left width = 40%> </td>";
			strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
			strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
			strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
			strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
			strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
			strData = strData + "</tr>";
			strData = strData + "</table>";
			if(pid.equals(""))
			{

			}
			else
			{


				///////////////////Part A Data To display ////////////////////

				strData = strData+"<table id = projPartA class = text width = 100%>";
				strData = strData + "<tr class=TableSolidRow>";
				strData = strData + "<td vAlign = middle class=TableCell colspan=6 align = left width = 90%>PART A Installation </td>";
				strData = strData + "</tr>";
				strData = strData + "<tr>";
				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%></td>";
				strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Planned</td>";

				strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Actual</td>";

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%></td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 40%>Description  </td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Start Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Finish Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Start Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Finish Date</td>";
				strData = strData + "<td vAlign = middle align = left  class=TableCell width = 12%>Status</td>";
				strData = strData + "</tr>";
				//		          prepare the CALL statement for P
				String procName = "PROC_MATERIAL_DISPLAY";
				String sql = "CALL " + procName + "(?,?,?,?,?,?,?,?)";
				CallableStatement callStmt = conn.prepareCall(sql);

				//	set input parameter to project value

				callStmt.registerOutParameter(1, OracleTypes.CURSOR);
				callStmt.registerOutParameter(2, OracleTypes.CURSOR);
				callStmt.registerOutParameter(3, OracleTypes.CURSOR);
				callStmt.registerOutParameter(4, OracleTypes.CURSOR);
				callStmt.registerOutParameter(5, OracleTypes.CURSOR);
				callStmt.registerOutParameter(6, OracleTypes.CURSOR);
				callStmt.registerOutParameter(7, OracleTypes.CURSOR);
				callStmt.setString(8,pid);

				//	 call the stored procedure
				//System.out.println();
				//System.out.println("Call stored procedure named " + procName);
				callStmt.execute();

				//System.out.println(procName + " completed successfully");

				//System.out.println("Result set 1: ");
				// get first result set
				// ResultSet rsproc = callStmt.getResultSet();
				ResultSet rsproc;
				//rsproc=callStmt.getResultSet();
				rsproc=(ResultSet)callStmt.getObject(1); 
				String weccnt="1";
				String wecpcnt="0";
				int rowcnt=0;
				String rowcss="TableRow";
				String partARem="";
				while (rsproc.next())
				{	weccnt=rsproc.getObject("N_ACTIVITY_WEC_LEVEL").toString();
				rowcnt=rowcnt+1;
				rowcss=rowcss+Integer.toString(rowcnt);
				if(!weccnt.equals(wecpcnt))
				{
					strData = strData + "<tr class=TableRow1>";
					strData = strData + "<td vAlign = middle align = left class=TableCell width = 40% colspan=5>WEC "+rsproc.getObject("N_ACTIVITY_WEC_LEVEL").toString()+" </td>";
					strData = strData + "<td vAlign = middle class=TableCell colspan=1 align = left ><a href='javascript:myFunction("+ pid +","+weccnt+")'>Graph</a></td>";

					strData = strData + "</tr>";
				}

				String pdate =convertDateFormat(rsproc.getObject("D_PLAN_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
				String pedate =convertDateFormat(rsproc.getObject("D_PLAN_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

				strData = strData + "<tr class=TableRow2>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%>"+rsproc.getObject("S_ACTIVITY_DESC").toString()+" </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+pdate+"  </td>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+pedate+"  </td>";
				if(rsproc.getObject("D_ACTUAL_STARTDATE") == null || rsproc.getObject("D_ACTUAL_STARTDATE").equals(""))	
				{
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> N.A. </td>";
				}
				else
				{   String padate =convertDateFormat(rsproc.getObject("D_ACTUAL_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+padate+"  </td>";
				}
				if(rsproc.getObject("D_ACTUAL_ENDDATE") == null || rsproc.getObject("D_ACTUAL_ENDDATE").equals(""))	
				{
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> N.A. </td>";
				}

				else
				{   String paedate =convertDateFormat(rsproc.getObject("D_ACTUAL_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> "+paedate+" </td>";
				}

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+rsproc.getObject("S_ACTIVITY_ACTUAL_STATUS").toString()+"  </td>";
				strData = strData + "</tr>";
				wecpcnt=weccnt;

				if(!rsproc.getObject("S_ACTIVITY_REMARKS").toString().equals("NIL")){
					partARem=partARem+rsproc.getObject("S_ACTIVITY_REMARKS").toString();}


				}

				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 20%>Remarks:</td>";
				strData = strData + "<td vAlign = middle class=TableCell align = center width = 80% colspan=5>"+partARem+"</td>";


				strData = strData + "</tr>";
				strData = strData + "</table>";

				///////////////////Part B Data To display ////////////////////
				strData = strData+"<table id = projPartB class = text width = 100%>";
				strData = strData + "<tr>";
				strData = strData + "<td vAlign = middle align = left width = 40%> </td>";
				strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
				strData = strData + "<td vAlign = middle align = left width = 12%> </td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableSolidRow>";
				strData = strData + "<td vAlign = middle class=TableCell colspan=6 align = left width = 90%>Part B : Approval And Common Infrastructure </td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%></td>";
				strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Planned</td>";

				strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Actual</td>";

				strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%></td>";
				strData = strData + "</tr>";
				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 40%>Description  </td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Start Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Finish Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Start Date</td>";
				strData = strData + "<td vAlign = middle align = left class=TableCell width = 12%>Finish Date</td>";
				strData = strData + "<td vAlign = middle align = left  class=TableCell width = 12%>Status</td>";
				strData = strData + "</tr>";
				//System.out.println();
				//System.out.println("Result set 3:");
				//  get third result set
				//'Fill Blade Status 
				rsproc=(ResultSet)callStmt.getObject(2); 
				String PartBRem="";
				while (rsproc.next())
				{				

					String pdate =convertDateFormat(rsproc.getObject("D_PLAN_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
					String pedate =convertDateFormat(rsproc.getObject("D_PLAN_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

					strData = strData + "<tr class=TableRow2>";
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 40%>"+rsproc.getObject("S_ACTIVITY_DESC").toString()+" </td>";
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+pdate+"  </td>";
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+pedate+"  </td>";
					if(rsproc.getObject("D_ACTUAL_STARTDATE") == null || rsproc.getObject("D_ACTUAL_STARTDATE").equals(""))	
					{
						strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> N.A. </td>";
					}
					else
					{   String padate =convertDateFormat(rsproc.getObject("D_ACTUAL_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+padate+"  </td>";
					}
					if(rsproc.getObject("D_ACTUAL_ENDDATE") == null || rsproc.getObject("D_ACTUAL_ENDDATE").equals(""))	
					{
						strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> N.A. </td>";
					}

					else
					{   String paedate =convertDateFormat(rsproc.getObject("D_ACTUAL_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	

					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> "+paedate+" </td>";
					}
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>"+rsproc.getObject("S_ACTIVITY_ACTUAL_STATUS").toString()+"  </td>";
					strData = strData + "</tr>";
					if(!rsproc.getObject("S_ACTIVITY_REMARKS").toString().equals("NIL")){
						PartBRem=PartBRem+rsproc.getObject("S_ACTIVITY_REMARKS").toString();}


				}

				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 20%>Remarks:</td>";
				strData = strData + "<td vAlign = middle class=TableCell align = center width = 80% colspan=5>"+PartBRem+"</td>";


				strData = strData + "</tr>";
				strData = strData + "</table>";

				///////////////////Part C Material Data To display ////////////////////  


				ArrayList<String> arrayWECFlag= new ArrayList<String>();
				ArrayList<String> arrayProjNetId= new ArrayList<String>();
				ArrayList<String> arrayTower= new ArrayList<String>();
				ArrayList<String> arrayMachine= new ArrayList<String>();
				ArrayList<String> arrayBlade = new ArrayList<String>();
				ArrayList<String> arrayTransformer = new ArrayList<String>();



				//System.out.println("Result set 3: ");

				rsproc=(ResultSet)callStmt.getObject(3); 
				int counter=0;
				//'Fill WEC Level Flag,ProjectNetId,Tower
				while (rsproc.next ()) 
				{
					arrayWECFlag.add("WEC-"+rsproc.getObject("N_ACTIVITY_WEC_LEVEL").toString());
					arrayProjNetId.add(rsproc.getObject("S_NETWORK_ID").toString());
					arrayTower.add(rsproc.getObject("S_MATERIAL_STATUS").toString());
					counter=counter+1;

				}

				//System.out.println();
				//System.out.println("Result set 4:");
				// get second result set
				rsproc=(ResultSet)callStmt.getObject(4); 
				//	'Fill Tower
				//'Fill Machine Status 
				while (rsproc.next ()) 
				{arrayMachine.add(rsproc.getObject("S_MATERIAL_STATUS").toString());}




				//System.out.println();
				//System.out.println("Result set 5:");
				//  get third result set
				//'Fill Blade Status 
				rsproc=(ResultSet)callStmt.getObject(5); 
				while (rsproc.next ()) 
				{
					arrayBlade.add(rsproc.getObject("S_MATERIAL_STATUS").toString());
				}



				//System.out.println();
				//System.out.println("Result set 6:");  
				//  get fourth result set
				//'Fill Transformer Status
				rsproc=(ResultSet)callStmt.getObject(6); 
				while (rsproc.next ()) 
				{
					arrayTransformer.add(rsproc.getObject("S_MATERIAL_STATUS").toString());
				}
				// close ResultSet and callStmt

				if(arrayWECFlag.isEmpty()==false)
				{

					strData = strData+ "<table id = tblPartC class = text width = 100%>";
					strData = strData + "<tr class=TableSolidRow>";
					strData = strData + "<td vAlign = middle class=TableCell colspan=6 align = left width = 90%>Part C : Material Status </td>";
					strData = strData + "</tr>";
					strData = strData + "<tr class=TableTitle>";
					strData = strData + "<td vAlign = middle class=TableCell  align = left width = 40%>Machines Number </td>";
					strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Tower</td>";
					strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Machine</td>";
					strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Blades</td>";
					strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Transformer</td>";
					strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%></td>";
					strData = strData + "</tr>";
					for (int iPartCCounter = 0; iPartCCounter < counter; iPartCCounter =iPartCCounter + 1)
					{
						strData= strData+"<tr class=TableRow2>";

						strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>" +arrayWECFlag.get(iPartCCounter) + "</td>";
						if(arrayTower.isEmpty()==false)
							strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayTower.get(iPartCCounter) + "</td>";
						else
							strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>  </td>";

						if(arrayMachine.isEmpty()==false)
							strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>" + arrayMachine.get(iPartCCounter) + "</td>";
						else
							strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>  </td>";


						if(arrayBlade.isEmpty()==false)
							strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayBlade.get(iPartCCounter) + "</td>";
						else
							strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>  </td>";

						if(arrayTransformer.isEmpty()==false)
							strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayTransformer.get(iPartCCounter) + "</td>";
						else
							strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>  </td>";



						strData= strData+ "</tr>";

					}

				}
				else
					strData=strData+"<tr><td colspan=5>No Or Insufficient Data</td></tr>";


				//System.out.println();
				//System.out.println("Result set 7:");  
				//  get fourth result set
				//'Fill Transformer Status
				rsproc=(ResultSet)callStmt.getObject(7); 
				String matRem="";
				while (rsproc.next ()) 
				{
					if(!rsproc.getObject("S_REMARKS").toString().equals("NIL")){
						matRem=matRem+rsproc.getObject("S_REMARKS").toString();}


				}

				strData = strData + "<tr class=TableTitle>";
				strData = strData + "<td vAlign = middle class=TableCell align = left width = 20%>Remarks:</td>";
				strData = strData + "<td vAlign = middle class=TableCell align = center width = 80% colspan=5>"+matRem+"</td>";


				strData = strData + "</tr>";
				strData= strData+ "</table>";
				rsproc.close();
				callStmt.close();
			}


			strData = strData + "<tr><td colspan=6 width = 100%><TABLE cellSpacing=0 cellPadding=0 width=100% border=0><TBODY><TR>";

			strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_left.gif width=10 border=0></TD>";
			strData = strData + " <TD class=SectionTitle noWrap>Project Status Report</TD>";
			strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_right.gif width=10 border=0></TD></TR></TBODY></TABLE></TD></TR>";
			strData=escape(strData);
			xml.append("<strData>");

			xml.append(strData);
			xml.append("</strData>\n");
			xml.append("</projcode>\n");
			xml.append("</projhead>\n");


























			}
		}catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (ps != null) ps.close();
				if (ps1 != null) ps1.close();
				if (ps2 != null) ps2.close();
				if (rs != null) rs.close();
				if (rs1 != null) rs1.close();
				if (rs2 != null) rs2.close();
				if (conn != null) 
				{
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				ps = null;
				ps1 = null;
				ps2 = null;
				rs = null;
				rs1 = null;
				rs2 = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return xml.toString();
	}



	public String escape(String s)
	{
		s = s.replace("&", " amp ");
		s = s.replace("'", " apos ");
		s = s.replace("<", " ltthan ");
		s = s.replace(">", " gtthan ");

		//s = s.replace('"', "quot");

		return s;	
	}

	public static void fetchAll(ResultSet rs)
	{
		try
		{
			//System.out.println("============================================================="); 

			// retrieve the  number, types and properties of the 
			// resultset's columns
			ResultSetMetaData stmtInfo = rs.getMetaData();

			int numOfColumns = stmtInfo.getColumnCount();
			int r = 0;

			while (rs.next())
			{
				r++;
				//System.out.print("Row: " + r + ": ");
				for (int i = 1; i <= numOfColumns; i++)
				{

					//System.out.print(rs.getString(i));

					if (i != numOfColumns)
					{
						//System.out.print(", ");
					}
				}
				//System.out.println();
			}
		}
		catch (Exception e)
		{
			System.out.println("Error: fetchALL: exception");
			System.out.println(e.getMessage());
		}
	}

	public List getWECMasterData(String state,String area,String substation,String feeder,String site,String cust,String eb,String wec,String wectype,String comm, String userid,String comm1, String wecStatus)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List tranList = new ArrayList();
		String sqlQuery="";
		if(wecStatus.equals("1"))
		{
			sqlQuery="SELECT * from VW_WEC_MASTER_DATA  WHERE S_STATUS IN(1) ";
		}
		else if(wecStatus.equals("2"))
		{
			sqlQuery="SELECT * from VW_WEC_MASTER_DATA  WHERE S_STATUS IN(0,2) ";
		}
		else if(wecStatus.equals("3"))
		{
			sqlQuery="SELECT * from VW_WEC_MASTER_DATA  WHERE S_STATUS IN(9) ";
		}
		else
			sqlQuery="SELECT * from VW_WEC_MASTER_DATA  WHERE S_STATUS IN(0,1,2,9) ";

		if (! comm.equals(""))
		{
			String date=convertDateFormat(comm, "dd/MM/yyyy", "dd-MMM-yyyy");
			sqlQuery +=" AND D_COMMISION_DATE >= '" + date +"'";
		} 
		if (! comm1.equals(""))
		{
			String date=convertDateFormat(comm1, "dd/MM/yyyy", "dd-MMM-yyyy");
			sqlQuery +=" AND D_COMMISION_DATE <= '" + date +"'";
		}

		if(!state.equals("."))
		{
			sqlQuery +=" AND S_STATE_ID in ('" + state.replaceAll(",", "','") +"')";
		}

		if(!area.equals("."))
		{
			sqlQuery +=" AND S_AREA_ID in ('" + area.replaceAll(",", "','") +"')";
		}

		if(!substation.equals("."))
		{
			sqlQuery +=" AND S_SUBSTATION_ID in ('" + substation.replaceAll(",", "','") +"')";
		}
		if(!feeder.equals("."))
		{
			sqlQuery +=" AND S_FEEDER_ID in ('" + feeder.replaceAll(",", "','") +"')";
		}

		if(!site.equals("."))
		{
			sqlQuery +=" AND S_SITE_ID in ('" + site.replaceAll(",", "','") +"')";
		}
		if(!cust.equals("."))
		{
			sqlQuery +=" AND S_CUSTOMER_ID in ('" + cust.replaceAll(",", "','") +"')";
		}
		if(!eb.equals("."))
		{
			sqlQuery +=" AND S_EB_ID in ('" + eb.replaceAll(",", "','") +"')";
		}
		if(!wec.equals("."))
		{
			sqlQuery +=" AND S_WEC_ID in ('" + wec.replaceAll(",", "','") +"')";
		}
		if(!wectype.equals("."))
		{
			sqlQuery +=" AND S_WEC_TYPE_ID = '" + wectype +"'";
		}
		prepStmt = conn.prepareStatement(sqlQuery);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			String status="";
			if((wecStatus=="3")||(wecStatus.equals("3")))
			{
				while (rs.next())
				{	
					Vector tranVector = new Vector();
					tranVector.add(rs.getString("S_STATE_NAME"));
					tranVector.add(rs.getString("S_SITE_NAME"));		          
					tranVector.add(rs.getString("S_WECSHORT_DESCR"));
					tranVector.add(rs.getString("S_EBSHORT_DESCR"));
					tranVector.add(rs.getString("S_CUSTOMER_NAME"));
					tranVector.add(rs.getObject("S_FOUND_LOC") == null ? "" : rs.getString("S_FOUND_LOC")); 
					tranVector.add(rs.getObject("D_COMMISION_DATE") == null ? "" : convertDateFormat(rs.getObject("D_COMMISION_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
					if(rs.getString("S_STATUS").equals("1"))
						status="Active";
					else if(rs.getString("S_STATUS").equals("2"))
						status="Deactive";
					else if(rs.getString("S_STATUS").equals("9"))
						status="Transfered";

					tranVector.add(status);
					tranVector.add(rs.getString("S_WEC_TYPE"));
					tranVector.add(rs.getString("N_WEC_CAPACITY"));
					tranVector.add(rs.getString("S_SAP_CUSTOMER_CODE"));
					tranVector.add(rs.getObject("D_TRANSFER_DATE")== null ? "" : convertDateFormat(rs.getObject("D_TRANSFER_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
					tranVector.add(rs.getString("WEC_NAME"));  
					tranVector.add(rs.getString("S_AREA_NAME"));
					tranVector.add(rs.getString("S_SUBSTATION_DESC") == null ? "" : rs.getString("S_SUBSTATION_DESC"));
					tranVector.add(rs.getString("S_FEEDER_DESC") == null ? "" : rs.getString("S_FEEDER_DESC"));

					tranList.add(i, tranVector);
					i++;        
				}
			}else
			{
				while (rs.next())
				{	
					Vector tranVector = new Vector();
					tranVector.add(rs.getString("S_STATE_NAME"));
					tranVector.add(rs.getString("S_SITE_NAME"));
					tranVector.add(rs.getString("S_WECSHORT_DESCR"));
					tranVector.add(rs.getString("S_EBSHORT_DESCR"));
					tranVector.add(rs.getString("S_CUSTOMER_NAME"));
					tranVector.add(rs.getObject("S_FOUND_LOC") == null ? "" : rs.getString("S_FOUND_LOC")); 
					tranVector.add(rs.getObject("D_COMMISION_DATE") == null ? "" : convertDateFormat(rs.getObject("D_COMMISION_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"));
					if(rs.getString("S_STATUS").equals("1"))
						status="Active";
					else if(rs.getString("S_STATUS").equals("2"))
						status="Deactive";
					else if(rs.getString("S_STATUS").equals("9"))
						status="Transfered";

					tranVector.add(status);
					tranVector.add(rs.getString("S_WEC_TYPE"));
					tranVector.add(rs.getString("N_WEC_CAPACITY"));
					tranVector.add(rs.getString("S_SAP_CUSTOMER_CODE"));
					tranVector.add(rs.getString("S_AREA_NAME"));
					tranVector.add(rs.getString("S_SUBSTATION_DESC") == null ? "" : rs.getString("S_SUBSTATION_DESC"));
					tranVector.add(rs.getString("S_FEEDER_DESC") == null ? "" : rs.getString("S_FEEDER_DESC"));

					tranList.add(i, tranVector);
					i++;        
				}
			}
			prepStmt.close();
			rs.close();
		}      
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null)
					rs.close();
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			}
		}
		return tranList;
	}

	public List getCustomerFeedbackData(String custname)
			throws Exception {
		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		// SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		// PreparedStatement ps = null;
		ResultSet rs = null;
		// ResultSet rs1 = null;
		List tranList = new ArrayList();

		String sqlQuery="SELECT * from TBL_CUSTOMER_FEEDBACK WHERE S_LOGIN_MASTER_ID IN " +
				"(SELECT S_USER_ID FROM TBL_LOGIN_MASTER WHERE S_LOGIN_DESCRIPTION = '"+custname+"')";

		prepStmt = conn.prepareStatement(sqlQuery);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			String status="";
			while (rs.next())
			{	
				Vector tranVector = new Vector();

				tranVector.add(rs.getString("S_PREVENT_RSHE"));
				tranVector.add(rs.getString("S_PREVENT_REXE"));
				tranVector.add(rs.getString("S_PREVENT_RQUA"));
				tranVector.add(rs.getString("S_PREVENT_DSHE"));
				tranVector.add(rs.getString("S_PREVENT_DEXE"));
				tranVector.add(rs.getString("S_PREVENT_DQUA"));
				tranVector.add(rs.getString("S_BREAKD_REXE"));
				tranVector.add(rs.getString("S_BREAKD_RSPE"));
				tranVector.add(rs.getString("S_BREAKD_REFF"));
				tranVector.add(rs.getString("S_BREAKD_DEXE"));
				tranVector.add(rs.getString("S_BREAKD_DSPE"));
				tranVector.add(rs.getString("S_BREAKD_DEFF"));
				tranVector.add(rs.getString("S_MANP_RCOMP"));
				tranVector.add(rs.getString("S_MANP_RSUFF"));
				tranVector.add(rs.getString("S_MANP_RREG"));
				tranVector.add(rs.getString("S_MANP_DCOMP"));
				tranVector.add(rs.getString("S_MANP_DSUFF"));
				tranVector.add(rs.getString("S_MANP_DREG"));
				tranVector.add(rs.getString("S_MA_RCON"));
				tranVector.add(rs.getString("S_MA_DCON"));
				tranVector.add(rs.getString("S_GEN_RCON"));
				tranVector.add(rs.getString("S_GEN_DCON"));
				tranVector.add(rs.getString("S_LIA_RTIME"));
				tranVector.add(rs.getString("S_LIA_RPERSONAL"));
				tranVector.add(rs.getString("S_LIA_DTIME"));
				tranVector.add(rs.getString("S_LIA_DPERSONAL"));
				tranVector.add(rs.getString("S_GA_RELECT"));
				tranVector.add(rs.getString("S_GA_DELECT"));
				tranVector.add(rs.getString("S_REPORT_RRLGR"));
				tranVector.add(rs.getString("S_REPORT_RCLR"));
				tranVector.add(rs.getString("S_REPORT_RADE"));
				tranVector.add(rs.getString("S_REPORT_DRLGR"));
				tranVector.add(rs.getString("S_REPORT_DCLR"));
				tranVector.add(rs.getString("S_REPORT_DADE"));
				tranVector.add(rs.getString("S_INTER_RCONTACT"));
				tranVector.add(rs.getString("S_INTER_RRESPON"));
				tranVector.add(rs.getString("S_INTER_RTECH"));
				tranVector.add(rs.getString("S_INTER_DCONTACT"));
				tranVector.add(rs.getString("S_INTER_DRESPON"));
				tranVector.add(rs.getString("S_INTER_DTECH"));
				tranVector.add(rs.getString("S_INTERCNT_RCONTACT"));
				tranVector.add(rs.getString("S_INTERCNT_RRESPON"));
				tranVector.add(rs.getString("S_INTERCNT_RTECH"));
				tranVector.add(rs.getString("S_INTERCNT_DCONTACT"));
				tranVector.add(rs.getString("S_INTERCNT_DRESPON"));
				tranVector.add(rs.getString("S_INTERCNT_DTECH"));
				tranVector.add(rs.getString("S_GENIMP_RMH"));
				tranVector.add(rs.getString("S_GENIMP_RWM"));
				tranVector.add(rs.getString("S_GENIMP_RQL"));
				tranVector.add(rs.getString("S_GENIMP_DMH"));
				tranVector.add(rs.getString("S_SUGGESTION"));
				tranList.add(i, tranVector);
				i++;        
			}
			prepStmt.close();
			rs.close();
		}      
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null)
					rs.close();
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			}
		}
		return tranList;
	}

	public List getWECShortFall(String states,String customers,String fiscalyear,int co)
			throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MMM-yyyy");
		DecimalFormat dformat = new DecimalFormat("#########0.00");
		DecimalFormat dformat1 = new DecimalFormat("#########0");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List tranList = new ArrayList();
		try {	
			String comm[] = fiscalyear.split("-");
			String syear = comm[0];
			String eyear = comm[1];
			String sqlQuery="SELECT A.S_WEC_ID,C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME, E.S_STATE_NAME, A.S_WECSHORT_DESCR,B.D_START_DATE, " +
					" B.D_END_DATE,B.N_GEN_COMM, B.N_EXT_AVA, b.N_MAC_AVA, SUM(GENERATION) AS TOTGEN, " +
					" to_char(AVG(A.GAvial),'FM999D99') AS GRIDAVA, to_char(AVG(A.MAvial),'FM999D99') AS MACAVA from VW_WEC_DATA A, " +
					" TBL_WEC_HISTORY B, TBL_CUSTOMER_MASTER C, TBL_WEC_MASTER D, TBL_STATE_MASTER E, TBL_SITE_MASTER F, TBL_EB_MASTER G " +
					" where A.S_WEC_ID = B.S_WEC_ID AND E.S_STATE_ID = F.S_STATE_ID AND F.S_SITE_ID = G.S_SITE_ID AND " +
					" G.S_EB_ID = D.S_EB_ID AND G.S_CUSTOMER_ID = C.S_CUSTOMER_ID AND A.S_WEC_ID = D.S_WEC_ID AND " +
					" C.S_CUSTOMER_ID = D.S_CUSTOMER_ID AND A.D_READING_DATE BETWEEN TO_CHAR(B.D_START_DATE,'DD-MONTH') || '-"+syear+"' " +
					" AND TO_CHAR(B.D_END_DATE,'DD-MONTH') || '-"+eyear+"' AND A.D_READING_DATE BETWEEN B.D_START_DATE AND B.D_END_DATE ";
			if (!customers.equals(""))
			{
				sqlQuery += " AND C.S_CUSTOMER_ID IN (" + customers + ")";
			}
			if (!states.equals("") && co == 0)
			{
				sqlQuery += " AND E.S_STATE_ID IN (" + states + ")";
			}
			sqlQuery += " GROUP BY B.D_START_DATE, B.D_END_DATE, B.N_GEN_COMM, A.S_WEC_ID,A.S_WECSHORT_DESCR,b.N_EXT_AVA,b.N_MAC_AVA," +
					" C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,E.S_STATE_NAME ORDER BY C.S_CUSTOMER_NAME, E.S_STATE_NAME,B.D_START_DATE,B.D_END_DATE";

			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			int i = 0, n = 0;
			String company = "",newcompany = "";
			String State = "", newstate = "";
			String startdate = "", newstartdate = "";
			String enddate = "", newenddate = "";
			String customerid = "", wecid="";
			double gengau = 0, gridgau = 0, macgua = 0, actgen = 0, actgrid = 0, actmac = 0;
			int wec = 0;
			while (rs.next())
			{	
				if (co == 0){
					company = rs.getString("S_CUSTOMER_NAME");
					State= rs.getString("S_STATE_NAME");
					startdate= format1.format(rs.getDate("D_START_DATE"));
					enddate= format1.format(rs.getDate("D_END_DATE"));
					if (n == 0)
					{
						newcompany = company;
						newstate = State;
						newstartdate = startdate;
						newenddate = enddate;
					}
					if (company.equals(newcompany) && State.equals(newstate) && startdate.equals(newstartdate) && enddate.equals(newenddate))
					{
						gengau += rs.getDouble("N_GEN_COMM");
						gridgau += rs.getDouble("N_EXT_AVA");
						macgua += rs.getDouble("N_MAC_AVA");
						actgen += rs.getDouble("TOTGEN");
						actgrid += rs.getDouble("GRIDAVA");
						actmac += rs.getDouble("MACAVA");
						wec += 1;
					}
					else
					{
						Vector tranVector = new Vector();
						double gridgauvalue = 0;
						try
						{
							gridgauvalue = Double.parseDouble(dformat.format(gridgau / wec));
						}
						catch (Exception exl)
						{
							gridgauvalue = 0;
						}
						double macguavalue = 0;
						try
						{
							macguavalue = Double.parseDouble(dformat.format(macgua / wec));
						}
						catch (Exception exl)
						{
							macguavalue = 0;
						}
						double actgridvalue = 0;
						try
						{
							actgridvalue = Double.parseDouble(dformat.format(actgrid / wec));
						}
						catch (Exception exl)
						{
							actgridvalue = 0;
						}
						double actmacvalue = 0;
						try
						{
							actmacvalue = Double.parseDouble(dformat.format(actmac / wec));
						}
						catch (Exception exl)
						{
							actmacvalue = 0;
						}
						double gencommited = 0;
						try
						{
							gencommited = Double.parseDouble(dformat.format((actgen / actgridvalue) * gridgauvalue));
						}
						catch (Exception exl)
						{
							gencommited = 0;
						}
						double shortfallgen = 0;
						try
						{
							shortfallgen = gengau > actgen ? Double.parseDouble(dformat.format((gengau - actgen) * (gridgauvalue/actgridvalue))) : 0; 
						}
						catch (Exception exl)
						{
							shortfallgen = 0;
						}
						double shortfallgrid = 0;
						try
						{	
							shortfallgrid = Double.parseDouble(dformat.format(gengau - gencommited));
						}
						catch (Exception exl)
						{
							shortfallgrid = 0;
						}
						double shortfallper = 0;
						try
						{
							shortfallper = Double.parseDouble(dformat.format((100 / gengau) * shortfallgen));
						}
						catch (Exception exc)
						{
							shortfallper = 0;
						}
						double avadiff = 0;
						try
						{
							avadiff = Double.parseDouble(dformat.format(gridgauvalue - actgridvalue));
						}
						catch (Exception exc)
						{
							avadiff = 0;
						}
						tranVector.add(newcompany);
						tranVector.add(newstate);
						tranVector.add(wec);
						tranVector.add(newstartdate);
						tranVector.add(newenddate);
						tranVector.add(dformat1.format(gengau));
						tranVector.add(dformat.format(gridgauvalue));
						tranVector.add(dformat.format(macguavalue));
						tranVector.add(dformat1.format(actgen));
						tranVector.add(dformat.format(actgridvalue));
						tranVector.add(dformat.format(actmacvalue));
						tranVector.add(dformat1.format(gencommited));
						tranVector.add(dformat1.format(shortfallgen));
						tranVector.add(dformat1.format(shortfallgrid));
						tranVector.add(dformat.format(shortfallper));
						tranVector.add(dformat.format(avadiff));
						tranVector.add(wecid);
						tranVector.add(customerid);
						tranList.add(i, tranVector);
						i++; 
						gengau = rs.getDouble("N_GEN_COMM");
						gridgau = rs.getDouble("N_EXT_AVA");
						macgua = rs.getDouble("N_MAC_AVA");
						actgen = rs.getDouble("TOTGEN");
						actgrid = rs.getDouble("GRIDAVA");
						actmac = rs.getDouble("MACAVA");
						wec = 1;
					}
					n = 1;
					newcompany = company;     
					newstate = State;
					newstartdate = startdate;
					newenddate = enddate;
					customerid = rs.getString("s_customer_id");
					wecid = rs.getString("s_wec_id");
				}
				else
				{
					Vector tranVector = new Vector();
					gengau = rs.getDouble("N_GEN_COMM");
					gridgau = rs.getDouble("N_EXT_AVA");
					macgua = rs.getDouble("N_MAC_AVA");
					actgen = rs.getDouble("TOTGEN");
					actgrid = rs.getDouble("GRIDAVA");
					actmac = rs.getDouble("MACAVA");
					wec = 1;
					double gridgauvalue = 0;
					try
					{
						gridgauvalue = Double.parseDouble(dformat.format(gridgau / wec));
					}
					catch (Exception exc)
					{
						gridgauvalue = 0;
					}
					double macguavalue = 0;
					try
					{
						macguavalue = Double.parseDouble(dformat.format(macgua / wec));
					}
					catch (Exception exc)
					{
						macguavalue = 0;
					}
					double actgridvalue = 0;
					try
					{
						actgridvalue = Double.parseDouble(dformat.format(actgrid / wec));
					}
					catch (Exception exc)
					{
						actgridvalue = 0;
					} 
					double actmacvalue = 0;
					try
					{
						actmacvalue = Double.parseDouble(dformat.format(actmac / wec));
					}
					catch (Exception exc)
					{
						actmacvalue = 0;
					} 
					double gencommited = 0;
					try
					{
						gencommited = Double.parseDouble(dformat.format((actgen / actgridvalue) * gridgauvalue));
					}
					catch (Exception exc)
					{
						gencommited = 0;
					} 
					double shortfallgen = 0;
					try
					{
						shortfallgen = gengau > actgen ? Double.parseDouble(dformat.format((gengau - actgen) * (gridgauvalue/actgridvalue))) : 0; 
					}
					catch (Exception exc)
					{
						shortfallgen = 0;
					} 
					double shortfallgrid = 0;
					try
					{
						shortfallgrid = Double.parseDouble(dformat.format(gengau - gencommited));
					}
					catch (Exception exc)
					{
						shortfallgrid = 0;
					}
					double shortfallper = 0;
					try
					{
						shortfallper = Double.parseDouble(dformat.format((100 / gengau) * shortfallgen));
					}
					catch (Exception exc)
					{
						shortfallper = 0;
					}
					double avadiff = 0;
					try
					{
						avadiff = Double.parseDouble(dformat.format(gridgauvalue - actgridvalue));
					}
					catch (Exception exc)
					{
						avadiff = 0;
					}
					tranVector.add(rs.getString("S_CUSTOMER_NAME"));
					tranVector.add(rs.getString("S_STATE_NAME"));
					tranVector.add(rs.getString("S_WECSHORT_DESCR"));
					tranVector.add(format1.format(rs.getDate("D_START_DATE")));
					tranVector.add(format1.format(rs.getDate("D_END_DATE")));
					tranVector.add(dformat1.format(gengau));
					tranVector.add(dformat.format(gridgauvalue));
					tranVector.add(dformat.format(macguavalue));
					tranVector.add(dformat1.format(actgen));
					tranVector.add(dformat.format(actgridvalue));
					tranVector.add(dformat.format(actmacvalue));
					tranVector.add(dformat1.format(gencommited));
					tranVector.add(dformat1.format(shortfallgen));
					tranVector.add(dformat1.format(shortfallgrid));
					tranVector.add(dformat.format(shortfallper));
					tranVector.add(dformat.format(avadiff));
					tranVector.add(rs.getString("s_wec_id"));
					tranVector.add(rs.getString("s_customer_id"));
					tranList.add(i, tranVector);
					i++;     
				}

			}
			if (! newcompany.equals(""))
			{
				Vector tranVector = new Vector();
				double gridgauvalue = 0;
				try
				{
					gridgauvalue = Double.parseDouble(dformat.format(gridgau / wec));
				}
				catch (Exception exc)
				{
					gridgauvalue = 0;
				}
				double macguavalue = 0;
				try
				{
					macguavalue = Double.parseDouble(dformat.format(macgua / wec));
				}	
				catch (Exception exc)
				{
					macguavalue = 0;
				}
				double actgridvalue = 0;
				try
				{
					actgridvalue = Double.parseDouble(dformat.format(actgrid / wec));
				}
				catch (Exception exc)
				{
					actgridvalue = 0;
				}
				double actmacvalue = 0;
				try
				{
					actmacvalue = Double.parseDouble(dformat.format(actmac / wec));
				}
				catch (Exception exc)
				{
					actmacvalue = 0;
				} 
				double gencommited = 0;
				try
				{
					gencommited = Double.parseDouble(dformat.format((actgen / actgridvalue) * gridgauvalue));
				}
				catch (Exception exc)
				{
					gencommited = 0;
				} 
				double shortfallgen = 0;
				try
				{
					shortfallgen = gengau > actgen ? Double.parseDouble(dformat.format((gengau - actgen) * (gridgauvalue/actgridvalue))) : 0; 
				}
				catch (Exception exc)
				{
					shortfallgen = 0;
				} 
				double shortfallgrid = 0;
				try
				{
					shortfallgrid = Double.parseDouble(dformat.format(gengau - gencommited));
				}
				catch (Exception exc)
				{
					shortfallgrid = 0;
				}
				double shortfallper = 0;
				try
				{
					shortfallper = Double.parseDouble(dformat.format((100 / gengau) * shortfallgen));
				}
				catch (Exception exc)
				{
					shortfallper = 0;
				}
				double avadiff = 0;
				try
				{
					avadiff = Double.parseDouble(dformat.format(gridgauvalue - actgridvalue));
				}
				catch (Exception exc)
				{
					avadiff = 0;
				}
				tranVector.add(newcompany);
				tranVector.add(newstate);
				tranVector.add(wec);
				tranVector.add(newstartdate);
				tranVector.add(newenddate);
				tranVector.add(dformat1.format(gengau));
				tranVector.add(dformat.format(gridgauvalue));
				tranVector.add(dformat.format(macguavalue));
				tranVector.add(dformat1.format(actgen));
				tranVector.add(dformat.format(actgridvalue));
				tranVector.add(dformat.format(actmacvalue));
				tranVector.add(dformat1.format(gencommited));
				tranVector.add(dformat1.format(shortfallgen));
				tranVector.add(dformat1.format(shortfallgrid));
				tranVector.add(dformat.format(shortfallper));
				tranVector.add(dformat.format(avadiff));
				tranVector.add(wecid);
				tranVector.add(customerid);
				tranList.add(i, tranVector);
				i++; 
				gengau = 0;
				gridgau = 0;
				macgua = 0;
				actgen = 0;
				actgrid = 0;
				actmac = 0;
				wec = 0;
			}
			prepStmt.close();
			rs.close();
		}      
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null)
					rs.close();
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conmanager.closeConnection();
					conn = null;
				}
			}
		}
		return tranList;
	}
	public static String convertDateFormat(String dateString, String oldFormat,String newFormat) {
		SimpleDateFormat old_formatter = new SimpleDateFormat(oldFormat);
		SimpleDateFormat new_formatter = new SimpleDateFormat(newFormat);
		ParsePosition pos = new ParsePosition(0);
		java.util.Date utilDate = old_formatter.parse(dateString, pos);
		if (utilDate == null) {

			return "";
		}
		String new_date_string = new_formatter.format(utilDate);
		return new_date_string;
	}


	public static List<GridBifurcationReportVo> getWecInfoWecWiseBetweenDays(List<String> wecIds, String fromDate, String toDate){

		List<GridBifurcationReportVo> gridBifurcationReportVos = new ArrayList<GridBifurcationReportVo>();
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)){
				sqlQuery = 
						"Select S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE,  to_char(D_READING_DATE,'dd-MON-yyyy') as D_READ_DATE, S_CUSTOMER_NAME,  " + 
						"S_WECSHORT_DESCR, TOTAL_GENERATION, GENERATION,  TOTAL_OPERATINGHRS, OPERATINGHRS, LULLHRS, " + 
						"MACHINEFAULT,  MACHINESD, INTERNALFAULT, INTERNALSD, E1FAULT,  " + 
						"E1SD, E2FAULT, E2SD, E3FAULT, E3SD,  " + 
						"CFACTOR, MAVIAL, GAVIAL,   RAV ,  GIAVIAL,  " + 
						"GEA , WECLOADRST, EBLOADRST, S_REMARKS, FAULT_HOUR, Customer_Scope, Grid_Trip_Count " + 
						"From VW_GRID_REPORT " + 
						"Where S_Wec_Id in " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) +  
						"and D_Reading_Date between ? and ? " + 
						"order by D_READING_DATE, S_WECSHORT_DESCR " ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(rs.getObject("D_READ_DATE").toString());
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(new BigDecimal(rs.getString("CFACTOR")).doubleValue());
					vo.setMachineAvailability(new BigDecimal(rs.getString("MAVIAL")).doubleValue());
					vo.setGridAvailability(new BigDecimal(rs.getString("GAVIAL")).doubleValue());
					vo.setResourceAvailability(new BigDecimal(rs.getString("RAV")).doubleValue());
					vo.setGridInternalAvailability(new BigDecimal(rs.getString("GIAVIAL")).doubleValue());
					vo.setGridExternalAvailability(new BigDecimal(rs.getString("GEA")).doubleValue());
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks(rs.getString("S_REMARKS"));
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("Customer_Scope")).longValue()/60, ":"));
					vo.setGridTripCount(rs.getString("Grid_Trip_Count"));
					
					//System.out.println(vo);
					gridBifurcationReportVos.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			return gridBifurcationReportVos;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return gridBifurcationReportVos;
	
	}
	
	public static List<GridBifurcationReportVo> getWecTotalWecWiseBetweenDays(List<String> wecIds, String fromDate, String toDate){

		int wecCount = -1;
		DecimalFormat df2 = new DecimalFormat("###.##");
		
		List<GridBifurcationReportVo> gridBifurcationReportVos = new ArrayList<GridBifurcationReportVo>();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)){
				sqlQuery = 
						"Select S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE, S_CUSTOMER_NAME, " + 
						"S_WECSHORT_DESCR, count(S_wec_id) as WEC_COUNT,MAX(TOTAL_GENERATION) AS TOTAL_GENERATION, SUM(GENERATION) AS GENERATION,   " + 
						"MAX(TOTAL_OPERATINGHRS) AS TOTAL_OPERATINGHRS, SUM(OPERATINGHRS) AS OPERATINGHRS, SUM(LULLHRS) AS LULLHRS, " + 
						"SUM(MACHINEFAULT) AS MACHINEFAULT,  SUM(MACHINESD) AS MACHINESD, SUM(INTERNALFAULT) AS INTERNALFAULT,  " + 
						"SUM(INTERNALSD) AS INTERNALSD, SUM(E1FAULT) AS E1FAULT, " + 
						"SUM(E1SD) AS E1SD, SUM(E2FAULT) AS E2FAULT, SUM(E2SD) AS E2SD, SUM(E3FAULT) AS E3FAULT, SUM(E3SD) AS E3SD, " + 
						"SUM(CFACTOR) AS CFACTOR, SUM(MAVIAL) AS MAVIAL, SUM(GAVIAL) AS GAVIAL,   SUM(RAV) AS RAV ,  SUM(GIAVIAL) AS GIAVIAL, " + 
						"SUM(GEA) AS GEA , SUM(WECLOADRST) AS WECLOADRST, SUM(EBLOADRST) AS EBLOADRST, SUM( FAULT_HOUR) AS FAULT_HOUR, SUM(CUSTOMER_SCOPE) as CUSTOMER_SCOPE,SUM(GRID_TRIP_COUNT) as GRID_TRIP_COUNT " + 
						"From VW_GRID_REPORT " + 
						"Where S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) + 
						"And D_Reading_Date between ? and ? " + 
						"group by S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE,  S_CUSTOMER_NAME,S_WECSHORT_DESCR " +
						"order by S_CUSTOMER_NAME, S_WECSHORT_DESCR" ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					wecCount = rs.getInt("WEC_COUNT");
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(fromDate + " to " + toDate);
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(Double.valueOf((df2.format(((new BigDecimal(rs.getString("CFACTOR")).doubleValue())/wecCount)))));
					vo.setMachineAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("MAVIAL")).doubleValue())/wecCount)))));
					vo.setGridAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GAVIAL")).doubleValue())/wecCount)))));
					vo.setResourceAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("RAV")).doubleValue())/wecCount)))));
					vo.setGridInternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GIAVIAL")).doubleValue())/wecCount)))));
					vo.setGridExternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GEA")).doubleValue())/wecCount)))));
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks("NA");
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("CUSTOMER_SCOPE")).longValue()/60, ":"));
					vo.setGridTripCount(rs.getString("GRID_TRIP_COUNT"));
					
					//System.out.println(vo);
					gridBifurcationReportVos.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			return gridBifurcationReportVos;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return gridBifurcationReportVos;
	
	}

	private static void displayResultSet(ResultSet rs) throws SQLException {

		String[] columnNames = GlobalUtils.getColumnNames(rs);
		for (String columnName : columnNames) {
			System.out.print(rs.getObject(columnName) +"\t");
		}
		System.out.println();
	}

	public static double getRevenueBasedOnWecIdsForOneDay(Collection<String> wecIds, String readingDate) {
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		double revenueTotal = 0.0;	
		Connection connection = null;	
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			sqlQuery = 
				"Select sum((N_gen - (N_gen * 3 /100)) * wecmaster.N_cost_per_unit) as revenue " + 
				"From tbl_reading_summary Metadata, Tbl_Wec_Master Wecmaster " + 
				"Where metadata.S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
				"And Metadata.S_Wec_Id = Wecmaster.S_Wec_Id " + 
				"And D_Reading_Date = ? " + 
				"order by revenue desc " ; 

			prepStmt = connection.prepareStatement(sqlQuery);
			prepStmt.setString(1, readingDate);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
					revenueTotal += new BigDecimal(rs.getString("revenue")).doubleValue();
			}
			if(prepStmt != null){
				prepStmt.close();
			}
			if(rs != null){
				rs.close();
			}
			
			return revenueTotal;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return revenueTotal;

	}
	
	public static double getFiscalGenerationBasedOnWECIdsForBetweenDays(Collection<String> wecIds, String fromReadingDate, String toReadingDate) throws SQLException {
		String wecIdsInString = GlobalUtils.getStringFromArrayForQuery(wecIds);
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		double revenueTotal = 0.0;	
		double generationTotal = 0.0;
		double fiscalGeneration = 0;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
				sqlQuery = "Select metadata.S_Wecshort_Descr,Sum(Generation) As Generation " + 
						"From Meta_Data Metadata, Tbl_Wec_Master Wecmaster " + 
						"Where metadata.S_Wec_Id In " + wecIdsInString + 
						"And Metadata.S_Wec_Id = Wecmaster.S_Wec_Id " + 
						"And D_Reading_Date Between ? And ? " + 
						"Group By Metadata.S_Wecshort_Descr " ; 

				prepStmt = connection.prepareStatement(sqlQuery);
				
				prepStmt.setString(1, fromReadingDate);
				prepStmt.setString(2, toReadingDate);
				rs = prepStmt.executeQuery();
				
				while (rs.next()) {
						generationTotal += new BigDecimal(rs.getString("generation")).doubleValue();
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			
			
			return generationTotal;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}

	public static double getFiscalRevenueBasedOnWECIdsForBetweenDays(
			Collection<String> wecIds,
			String fromReadingDate, String toReadingDate) throws SQLException {
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		double revenueTotal = 0.0;
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select sum((N_gen - (N_gen * 3 /100)) * wecmaster.N_cost_per_unit) as revenue " + 
					"From tbl_reading_summary Metadata, Tbl_Wec_Master Wecmaster " + 
					"Where metadata.S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(wecIds) + 
					"And Metadata.S_Wec_Id = Wecmaster.S_Wec_Id " + 
					"And D_Reading_Date between ? and ? " + 
					"order by revenue desc " ; 

			prepStmt = connection.prepareStatement(sqlQuery);

			prepStmt.setString(1, fromReadingDate);
			prepStmt.setString(2, toReadingDate);
			rs = prepStmt.executeQuery();

			while (rs.next()) {
				revenueTotal += new BigDecimal(rs.getString("revenue")).doubleValue();
			}
			if(prepStmt != null){
				prepStmt.close();
			}
			if(rs != null){
				rs.close();
			}
			return revenueTotal;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	
	public static List<ScadaDataJump> getScadaDataInfoWecWiseBetweenDays(List<String> wecIds, String fromDate, String toDate){
		List<ScadaDataJump> scadaDataJump = new ArrayList<ScadaDataJump>();
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)){
				sqlQuery = 
						"SELECT gen.D_DATE, plant.S_WEC_NAME, meta.S_CUSTOMER_NAME, meta.S_STATE_NAME, meta.N_WEC_CAPACITY, plant.S_LOCATION_NO, plant.S_PLANT_NO, " +
						"scadadw.GET_MAX_GENERATION(gen.D_DATE-1,plant.S_LOCATION_NO,plant.S_PLANT_NO) as PREVIOUSMAXGENERATION, " +
						"scadadw.get_max_operating_hr(gen.D_DATE-1,plant.S_LOCATION_NO,plant.S_PLANT_NO) as PREVIOUSMAXOPERATINGHRS, " +
						"scadadw.GET_MAX_GENERATION(gen.D_DATE,plant.S_LOCATION_NO,plant.S_PLANT_NO) as CURRENTMAXGENERATION, " +
						"scadadw.get_max_operating_hr(gen.D_DATE,plant.S_LOCATION_NO,plant.S_PLANT_NO) as CURRENTMAXOPERATINGHRS " +
						"From ecare.CUSTOMER_META_DATA meta ,scadadw.TBL_GENERATION_MIN_10 gen,scadadw.TBL_PLANT_MASTER plant " + 
						"Where meta.S_TECHNICAL_NO=plant.S_SERIAL_NO " +
						"and plant.S_LOCATION_NO=gen.S_LOCATION_NO "+
						"and plant.S_PLANT_NO=gen.S_PLANT_NO "+
			 			"and meta.S_WEC_ID in " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) +  
						"and gen.D_DATE BETWEEN ? and ? " +
						"GROUP By gen.D_DATE,plant.S_WEC_NAME,meta.S_CUSTOMER_NAME,meta.S_STATE_NAME,meta.N_WEC_CAPACITY,plant.S_LOCATION_NO,plant.S_PLANT_NO " +
						"ORDER By plant.S_WEC_NAME,gen.D_DATE " ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					ScadaDataJump vo = new ScadaDataJump();
					
					vo.setReadingDate(DateUtility.sqlDateToStringDate(rs.getDate("D_DATE"),"dd-MMM-yyyy"));
					vo.setWecName(rs.getString("S_WEC_NAME"));
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setMachineCapacity(rs.getInt("N_WEC_CAPACITY"));
					vo.setLocationNo(rs.getString("S_LOCATION_NO"));
					vo.setPlantNo(rs.getString("S_PLANT_NO"));
					vo.setPreviousMaxGeneration(rs.getLong("PREVIOUSMAXGENERATION"));
					vo.setPreviousMaxOperatingHour(rs.getLong("PREVIOUSMAXOPERATINGHRS"));
					vo.setCurrentMaxGeneration(rs.getLong("CURRENTMAXGENERATION"));
					vo.setCurrentMaxOperatingHour(rs.getLong("CURRENTMAXOPERATINGHRS"));
					vo.calculateDifferenceInGenOper();
					
					scadaDataJump.add(vo);
					/*vo.validateData();
					if(vo.isDataCorrupt()){
						System.out.println(vo);
						
					}*/
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			return scadaDataJump;
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return scadaDataJump;
	}
	
public static String getSqlQueryForHistoricalDataView1() {
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		String queryForHistoricalDataVw1="";
		try{
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"select s_text from tbl_historical_data_vw where s_vw_name='VW_HISTORICAL_DATA_1' " ; 
			prepStmt = connection.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
			if(rs.next()){
				queryForHistoricalDataVw1 = rs.getString(1);
			}
			return queryForHistoricalDataVw1;

		}
		catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return queryForHistoricalDataVw1;
	}

public static void createViewForHistoricalData(String sqlQuery) {
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Connection connection = null;
		String queryForHistoricalDataVw1=null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			//sqlQuery = 	"select s_text from tbl_historical_data_vw where s_vw_name='VW_HISTORICAL_DATA_1' " ; 
			prepStmt = connection.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
		}
		catch(Exception e){
			MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
			}
			catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		
	}

	public static List<GridBifurcationReportVo> getWecInfoWecWiseBetweenDaysforHistoricalData() {
		

		List<GridBifurcationReportVo> historicalDataReportVo = new ArrayList<GridBifurcationReportVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			
			
				sqlQuery = 
						"Select S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE,  to_char(D_READING_DATE,'dd-MON-yyyy') as D_READ_DATE, S_CUSTOMER_NAME,"+   
						"S_WECSHORT_DESCR, TOTAL_GENERATION, GENERATION,  TOTAL_OPERATINGHRS, OPERATINGHRS, LULLHRS,"+  
						"MACHINEFAULT,  MACHINESD, INTERNALFAULT, INTERNALSD, E1FAULT,"+   
						"E1SD, E2FAULT, E2SD, E3FAULT, E3SD,"+   
						"CFACTOR, MAVIAL, GAVIAL,   RAV ,  GIAVIAL,"+  
						"GEA , WECLOADRST, EBLOADRST, S_REMARKS,FAULT_HOUR,CUSTOMER_SCOPE,GRID_TRIP_COUNT"+
						" From VW_HISTORICAL_DATA_3 "+
//						--"Where S_Wec_Id in " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) +  
//					--	"and D_Reading_Date between ? and ? " + 
						" order by D_READING_DATE, S_WECSHORT_DESCR"; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
//				prepStmt.setObject(1, fromDate);
//				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(rs.getObject("D_READ_DATE").toString());
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(new BigDecimal(rs.getString("CFACTOR")).doubleValue());
					vo.setMachineAvailability(new BigDecimal(rs.getString("MAVIAL")).doubleValue());
					vo.setGridAvailability(new BigDecimal(rs.getString("GAVIAL")).doubleValue());
					vo.setResourceAvailability(new BigDecimal(rs.getString("RAV")).doubleValue());
					vo.setGridInternalAvailability(new BigDecimal(rs.getString("GIAVIAL")).doubleValue());
					vo.setGridExternalAvailability(new BigDecimal(rs.getString("GEA")).doubleValue());
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks(rs.getString("S_REMARKS"));
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("CUSTOMER_SCOPE")).longValue()/60, ":"));
					vo.setGridTripCount(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("GRID_TRIP_COUNT")).longValue()/60, ":"));
					
					
					//System.out.println(vo);
					historicalDataReportVo.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			
			return historicalDataReportVo;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return historicalDataReportVo;
	}

	public static List<GridBifurcationReportVo> getWecTotalWecWiseBetweenDaysforHistoricalData(String fromDate, String toDate) {
		

		int wecCount = -1;
		DecimalFormat df2 = new DecimalFormat("###.##");
		
		List<GridBifurcationReportVo> historicalDataReporttVos = new ArrayList<GridBifurcationReportVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = conmanager.getConnection();
			
				sqlQuery = 
						"Select S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE, S_CUSTOMER_NAME, " + 
						"S_WECSHORT_DESCR, count(S_wec_id) as WEC_COUNT,MAX(TOTAL_GENERATION) AS TOTAL_GENERATION, SUM(GENERATION) AS GENERATION,   " + 
						"MAX(TOTAL_OPERATINGHRS) AS TOTAL_OPERATINGHRS, SUM(OPERATINGHRS) AS OPERATINGHRS, SUM(LULLHRS) AS LULLHRS, " + 
						"SUM(MACHINEFAULT) AS MACHINEFAULT,  SUM(MACHINESD) AS MACHINESD, SUM(INTERNALFAULT) AS INTERNALFAULT,  " + 
						"SUM(INTERNALSD) AS INTERNALSD, SUM(E1FAULT) AS E1FAULT, " + 
						"SUM(E1SD) AS E1SD, SUM(E2FAULT) AS E2FAULT, SUM(E2SD) AS E2SD, SUM(E3FAULT) AS E3FAULT, SUM(E3SD) AS E3SD, " + 
						"SUM(CFACTOR) AS CFACTOR, SUM(MAVIAL) AS MAVIAL, SUM(GAVIAL) AS GAVIAL,   SUM(RAV) AS RAV ,  SUM(GIAVIAL) AS GIAVIAL, " + 
						"SUM(GEA) AS GEA , SUM(WECLOADRST) AS WECLOADRST, SUM(EBLOADRST) AS EBLOADRST, SUM( FAULT_HOUR) AS FAULT_HOUR,SUM(CUSTOMER_SCOPE) as CUSTOMER_SCOPE,SUM(GRID_TRIP_COUNT)as GRID_TRIP_COUNT  " + 
						"From VW_HISTORICAL_DATA_3 " + 
						/*"Where S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) +*/ 
						"WHERE D_Reading_Date between ? and ? " + 
						"group by S_STATE_NAME, S_SITE_NAME, S_WEC_TYPE,  S_CUSTOMER_NAME,S_WECSHORT_DESCR " +
						"order by S_CUSTOMER_NAME, S_WECSHORT_DESCR" ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					wecCount = rs.getInt("WEC_COUNT");
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(fromDate + " to " + toDate);
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(Double.valueOf((df2.format(((new BigDecimal(rs.getString("CFACTOR")).doubleValue())/wecCount)))));
					vo.setMachineAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("MAVIAL")).doubleValue())/wecCount)))));
					vo.setGridAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GAVIAL")).doubleValue())/wecCount)))));
					vo.setResourceAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("RAV")).doubleValue())/wecCount)))));
					vo.setGridInternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GIAVIAL")).doubleValue())/wecCount)))));
					vo.setGridExternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GEA")).doubleValue())/wecCount)))));
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks("NA");
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("CUSTOMER_SCOPE")).longValue()/60, ":"));
					vo.setGridTripCount(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("GRID_TRIP_COUNT")).longValue()/60, ":"));
					//System.out.println(vo);
					historicalDataReporttVos.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			
			return historicalDataReporttVos;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					conn.close();
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return historicalDataReporttVos;
	
	}

	public static List<OverAllMGSAvailibilityVo> getAreaWiseMGSAvailabilityBetweenDays(
			 String[] areaIds, String fromDate, String toDate) {
		
		DecimalFormat df2 = new DecimalFormat("###.##");
		double overAllWecCount=0;
		double overAllMA=0;
		double overAllGA=0;
		double overAllSA=0;
		List<OverAllMGSAvailibilityVo> overAllVo=new ArrayList<OverAllMGSAvailibilityVo>();
		List<StateAreaWiseMGSAvailabilityVo> stateAreaMGSReportVos = new ArrayList<StateAreaWiseMGSAvailabilityVo>();
		List<String> areaStateVos=new ArrayList<String>();	
		
		areaStateVos=StateMasterDao.getStateIdsBasedOnAreaIDs(areaIds);
		int overAllStateCount=areaStateVos.size();
		
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;		
		try{
				for(String stateId:areaStateVos){
					
					double totalWecCountForState=0;
					double totalMAForState=0;
					double totalGAForState=0;
					double totalSAForState=0;
					int areaCount = 0;
					List<AreaWiseMGSAvailabilityReportVo> oneAreaVo=new ArrayList<AreaWiseMGSAvailabilityReportVo>();
				
					conn = wcareConnector.getConnectionFromPool();		
					sqlQuery ="select s_state_name,s_area_name,count(distinct s_wec_id) as total_wecs,Round(avg(Mavial),2)as MA,Round(avg(gavial),2)as GA,Round(avg(SAVIAL),2) as SA  " +
							  "from vw_grid_report "+
							  "where s_area_id in " + GlobalUtils.getStringFromArrayForQuery( Arrays.asList(areaIds)) +
							  " and s_state_id="+stateId+
							  " and d_reading_date between ? and ? "+
							  " group by s_state_name,s_area_name"+
							  " order by s_state_name,s_area_name" ; 
	
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1, fromDate);
					prepStmt.setObject(2, toDate);
					rs = prepStmt.executeQuery();
					while (rs.next()) {
					
						String stateName=rs.getString("S_STATE_NAME");
						String areaName=rs.getString("S_AREA_NAME");
						double wecCountForArea=rs.getDouble("TOTAL_WECS");
						double mAForArea=rs.getDouble("MA");
						double gAForArea=rs.getDouble("GA");
						double sAForArea=rs.getDouble("SA");
					
						oneAreaVo.add(new AreaWiseMGSAvailabilityReportVo(stateName,areaName,wecCountForArea,mAForArea,gAForArea,sAForArea));
//						System.out.println(oneAreaVo);
						totalWecCountForState+=wecCountForArea;
						totalMAForState+=mAForArea*wecCountForArea;
						totalGAForState+=gAForArea*wecCountForArea;
						totalSAForState+=sAForArea*wecCountForArea;					 
						areaCount++;					
					}//end of while loop
				
					if(prepStmt != null){
						prepStmt.close();
					}
					if(rs != null){
						rs.close();
					}
				
				  totalMAForState=Double.valueOf(df2.format(totalMAForState/totalWecCountForState));
				  totalGAForState=Double.valueOf(df2.format(totalGAForState/totalWecCountForState));
				  totalSAForState=Double.valueOf(df2.format(totalSAForState/totalWecCountForState));				 
				  stateAreaMGSReportVos.add(new StateAreaWiseMGSAvailabilityVo(oneAreaVo,totalWecCountForState,totalMAForState,totalGAForState,totalSAForState)); 
//				  System.out.println(stateAreaMGSReportVos);
				  overAllWecCount+=totalWecCountForState;
				  overAllMA+=totalMAForState*totalWecCountForState;
				  overAllGA+=totalGAForState*totalWecCountForState;
				  overAllSA+=totalSAForState*totalWecCountForState;				 
				}	//end of for loop		 
				overAllMA=Double.valueOf(df2.format(overAllMA/overAllWecCount));
				overAllGA=Double.valueOf(df2.format(overAllGA/overAllWecCount));
				overAllSA=Double.valueOf(df2.format(overAllSA/overAllWecCount));
				overAllVo.add(new OverAllMGSAvailibilityVo(stateAreaMGSReportVos,overAllWecCount,overAllMA,overAllGA,overAllSA));
//				System.out.println(overAllVo);
				return overAllVo;
		}//end of try block
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return overAllVo;
 }
	public static List<GridBifurcationReportVo> getWecTotalWecWiseWithWindspeedBetweenDays(List<String> wecIds, String fromDate, String toDate) {
		int wecCount = -1;
		DecimalFormat df2 = new DecimalFormat("###.##");
		
		List<GridBifurcationReportVo> gridBifurcationReportVos = new ArrayList<GridBifurcationReportVo>();
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			for(List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)){
				sqlQuery = 
						"Select GR.S_STATE_NAME, GR.S_SITE_NAME, GR.S_WEC_TYPE, GR.S_CUSTOMER_NAME, " + 
						"GR.S_WECSHORT_DESCR, count(GR.S_wec_id) as WEC_COUNT,MAX(TOTAL_GENERATION) AS TOTAL_GENERATION, SUM(GENERATION) AS GENERATION,   " + 
						"MAX(TOTAL_OPERATINGHRS) AS TOTAL_OPERATINGHRS, SUM(OPERATINGHRS) AS OPERATINGHRS, SUM(LULLHRS) AS LULLHRS, " + 
						"SUM(MACHINEFAULT) AS MACHINEFAULT,  SUM(MACHINESD) AS MACHINESD, SUM(INTERNALFAULT) AS INTERNALFAULT,  " + 
						"SUM(INTERNALSD) AS INTERNALSD, SUM(E1FAULT) AS E1FAULT, " + 
						"SUM(E1SD) AS E1SD, SUM(E2FAULT) AS E2FAULT, SUM(E2SD) AS E2SD, SUM(E3FAULT) AS E3FAULT, SUM(E3SD) AS E3SD, " + 
						"SUM(CFACTOR) AS CFACTOR, SUM(MAVIAL) AS MAVIAL, SUM(GAVIAL) AS GAVIAL,   SUM(RAV) AS RAV ,  SUM(GIAVIAL) AS GIAVIAL, " + 
						"SUM(GEA) AS GEA , SUM(WECLOADRST) AS WECLOADRST, SUM(EBLOADRST) AS EBLOADRST, SUM( FAULT_HOUR) AS FAULT_HOUR, SUM(CUSTOMER_SCOPE) as CUSTOMER_SCOPE,SUM(GRID_TRIP_COUNT) as GRID_TRIP_COUNT, " +
						"ROUND(avg(WC.n_avg_windspeed),2) as AVG_WINDSPEED,"+
			            "ROUND(avg(WC.n_max_windspeed),2) as MAX_WINDSPEED,round(avg(WC.n_min_windspeed),2) as MIN_WINDSPEED"+
						" From VW_GRID_REPORT GR ,SCADADW.TBL_WEC_SCADA_READING WC" + 
						" Where GR.S_Wec_Id In " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) + 
						" And GR.D_Reading_Date between ? and ? " + 
						" and GR.d_reading_date=WC.d_date"+
						" and GR.S_Wec_Id=WC.S_Wec_Id"+		
						" group by GR.S_STATE_NAME, GR.S_SITE_NAME, GR.S_WEC_TYPE,  GR.S_CUSTOMER_NAME,GR.S_WECSHORT_DESCR " +
						" order by GR.S_CUSTOMER_NAME, GR.S_WECSHORT_DESCR" ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					wecCount = rs.getInt("WEC_COUNT");
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(fromDate + " to " + toDate);
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(Double.valueOf((df2.format(((new BigDecimal(rs.getString("CFACTOR")).doubleValue())/wecCount)))));
					vo.setMachineAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("MAVIAL")).doubleValue())/wecCount)))));
					vo.setGridAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GAVIAL")).doubleValue())/wecCount)))));
					vo.setResourceAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("RAV")).doubleValue())/wecCount)))));
					vo.setGridInternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GIAVIAL")).doubleValue())/wecCount)))));
					vo.setGridExternalAvailability(Double.valueOf((df2.format(((new BigDecimal(rs.getString("GEA")).doubleValue())/wecCount)))));
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks("NA");
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("CUSTOMER_SCOPE")).longValue()/60, ":"));
					vo.setGridTripCount(rs.getString("GRID_TRIP_COUNT"));
					vo.setAvgWindspeed(new BigDecimal(rs.getString("AVG_WINDSPEED")).doubleValue());
					vo.setMaxWindspeed(new BigDecimal(rs.getString("MAX_WINDSPEED")).doubleValue());
					vo.setMinWindspeed(new BigDecimal(rs.getString("MIN_WINDSPEED")).doubleValue());
					
					//System.out.println(vo);
					gridBifurcationReportVos.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			return gridBifurcationReportVos;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return gridBifurcationReportVos;
		}

	public static List<GridBifurcationReportVo> getWecInfoWecWiseWithWindspeedBetweenDays(List<String> wecIds, String fromDate, String toDate) {
		List<GridBifurcationReportVo> gridBifurcationReportVos = new ArrayList<GridBifurcationReportVo>();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> splitWecIds : GlobalUtils.splitArrayList(wecIds, 900)){
				sqlQuery = 
						"Select GR.S_STATE_NAME, GR.S_SITE_NAME, GR.S_WEC_TYPE,  to_char(D_READING_DATE,'dd-MON-yyyy') as D_READ_DATE, GR.S_CUSTOMER_NAME,  " + 
						"GR.S_WECSHORT_DESCR, TOTAL_GENERATION, GENERATION,  TOTAL_OPERATINGHRS, OPERATINGHRS, LULLHRS, " + 
						"MACHINEFAULT,  MACHINESD, INTERNALFAULT, INTERNALSD, E1FAULT,  " + 
						"E1SD, E2FAULT, E2SD, E3FAULT, E3SD,  " + 
						"CFACTOR, MAVIAL, GAVIAL,   RAV ,  GIAVIAL,  " + 
						"GEA , WECLOADRST, EBLOADRST, S_REMARKS, FAULT_HOUR, Customer_Scope, Grid_Trip_Count,WC.n_avg_windspeed as AVG_WINDSPEED,"+
						"WC.n_max_windspeed as MAX_WINDSPEED,WC.n_min_windspeed as MIN_WINDSPEED " + 
						"From VW_GRID_REPORT GR,SCADADW.TBL_WEC_SCADA_READING WC" + 
						" Where GR.S_Wec_Id in " + GlobalUtils.getStringFromArrayForQuery(splitWecIds) +  
						" and D_Reading_Date between ? and ? " +
						" and GR.d_reading_date=WC.d_date"+
						" and GR.s_wec_id=WC.s_wec_id"+	
						" order by D_READING_DATE, GR.S_WECSHORT_DESCR " ; 
	
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, fromDate);
				prepStmt.setObject(2, toDate);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					//displayResultSet(rs);
					GridBifurcationReportVo vo = new GridBifurcationReportVo();
					
					vo.setStateName(rs.getString("S_STATE_NAME"));
					vo.setSiteName(rs.getString("S_SITE_NAME"));
					vo.setWecType(rs.getString("S_WEC_TYPE"));
					vo.setDate(rs.getObject("D_READ_DATE").toString());
					vo.setCustomerName(rs.getString("S_CUSTOMER_NAME"));
					vo.setWecDescription(rs.getString("S_WECSHORT_DESCR"));
					vo.setCumulativeGeneration(new BigDecimal(rs.getString("TOTAL_GENERATION")).doubleValue());
					vo.setGeneration(new BigDecimal(rs.getString("GENERATION")).doubleValue());
					vo.setCumulativeOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("TOTAL_OPERATINGHRS")).longValue()/60, ":"));
					vo.setOperatingHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("OPERATINGHRS")).longValue()/60, ":"));
					vo.setLullHour(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("LULLHRS")).longValue()/60, ":"));
					vo.setMachineFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINEFAULT")).longValue()/60, ":"));
					vo.setMachineShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("MACHINESD")).longValue()/60, ":"));
					vo.setInternalFault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALFAULT")).longValue()/60, ":"));
					vo.setInternalShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("INTERNALSD")).longValue()/60, ":"));
	
					vo.setE1Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1FAULT")).longValue()/60, ":"));
					vo.setE1Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E1SD")).longValue()/60, ":"));
					vo.setE2Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2FAULT")).longValue()/60, ":"));
					vo.setE2Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E2SD")).longValue()/60, ":"));
					vo.setE3Fault(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3FAULT")).longValue()/60, ":"));
					vo.setE3Shutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("E3SD")).longValue()/60, ":"));
	
					vo.setCapacityFactor(new BigDecimal(rs.getString("CFACTOR")).doubleValue());
					vo.setMachineAvailability(new BigDecimal(rs.getString("MAVIAL")).doubleValue());
					vo.setGridAvailability(new BigDecimal(rs.getString("GAVIAL")).doubleValue());
					vo.setResourceAvailability(new BigDecimal(rs.getString("RAV")).doubleValue());
					vo.setGridInternalAvailability(new BigDecimal(rs.getString("GIAVIAL")).doubleValue());
					vo.setGridExternalAvailability(new BigDecimal(rs.getString("GEA")).doubleValue());					
					vo.setWecSpecialShutdown(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("WECLOADRST")).longValue()/60, ":"));
					vo.setLoadShedding(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("EBLOADRST")).longValue()/60, ":"));
					vo.setRemarks(rs.getString("S_REMARKS"));
					vo.setFaultHours(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("FAULT_HOUR")).longValue()/60, ":"));
					vo.setCustomerScope(TimeUtility.convertMinutesToTimeStringFormat(new BigDecimal(rs.getString("Customer_Scope")).longValue()/60, ":"));
					vo.setGridTripCount(rs.getString("Grid_Trip_Count"));
					vo.setAvgWindspeed(new BigDecimal(rs.getString("AVG_WINDSPEED")).doubleValue());
					vo.setMaxWindspeed(new BigDecimal(rs.getString("MAX_WINDSPEED")).doubleValue());
					vo.setMinWindspeed(new BigDecimal(rs.getString("MIN_WINDSPEED")).doubleValue());
					
					//System.out.println(vo);
					gridBifurcationReportVos.add(vo);
					
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			return gridBifurcationReportVos;
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		return gridBifurcationReportVos;
	
	}

}
