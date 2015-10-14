package com.enercon.projects.dao;
import com.enercon.projects.dao.ProjectDao;
import com.enercon.projects.dao.ProjectSQLC;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.global.utils.CodeGenerate;
import java.sql.*;
import java.text.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.lang.Integer;
import org.apache.log4j.Logger;
import oracle.jdbc.driver.*;

public class ProjectDao 
{
	
	private static Logger logger = Logger.getLogger(ProjectDao.class);

    public ProjectDao(){}
    
    public List getprojects() throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
          Connection con = conmanager.getConnection();
          PreparedStatement prepStmt = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          ResultSet rs1 = null;
          List tranList = new ArrayList();         
          String sqlQuery = ProjectSQLC.GET_PROJECT_GRID;
          prepStmt = con.prepareStatement(sqlQuery);          
          try {
             
              rs = prepStmt.executeQuery();
              int i = 0;
              while (rs.next()) {
                  Vector tranVector = new Vector();                
                  tranVector.add(rs.getString("S_PROJECT_ID"));
                  tranVector.add(rs.getString("S_PROJECT_DEFINITION"));
                  tranVector.add(rs.getString("S_CUSTOMER_ID"));
                  tranVector.add(rs.getString("S_CUSTOMER_NAME"));
                  tranVector.add(rs.getString("S_SITE_NAME"));                 
                  tranVector.add(rs.getString("N_WEC_QUANTITY"));
                  tranVector.add(rs.getString("S_WECTYPE"));
                  tranVector.add(rs.getString("S_WEC_CAPACITY"));
                  tranList.add(i, tranVector);
                  i++;
              } 
          } catch (SQLException sqlExp) {
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
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              } catch (Exception e) {
                  prepStmt = null;
                  rs = null;
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              }
          }
          return tranList;
      }
    //samir
    public List getprojectsactivity() throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
          Connection con = conmanager.getConnection();
          PreparedStatement prepStmt = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          ResultSet rs1 = null;
          List tranList = new ArrayList();         
          String sqlQuery = ProjectSQLC.GET_PROJECT_ACTIVITY;
          prepStmt = con.prepareStatement(sqlQuery);          
          try {
             
              rs = prepStmt.executeQuery();
              int i = 0;
              while (rs.next()) {
                  Vector tranVector = new Vector();                
                  tranVector.add(rs.getString("S_ACTIVITY_ID"));
                  tranVector.add(rs.getString("S_PROJECT_DEFINITION"));
                  tranVector.add(rs.getString("S_NETWORK_ID"));
                  tranVector.add(rs.getString("S_ACTIVITY_NO"));
                  tranVector.add(rs.getString("S_ACTIVITY_DESC"));                 
                  tranVector.add(rs.getString("S_ACTIVITY_PART_DISP"));                 
                  tranList.add(i, tranVector);
                  i++;
              } 
          } catch (SQLException sqlExp) {
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
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              } catch (Exception e) {
                  prepStmt = null;
                  rs = null;
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              }
          }
          
          return tranList;
      }
    public List getprojectsmaterialtransaction() throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
          Connection con = conmanager.getConnection();
          PreparedStatement prepStmt = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          ResultSet rs1 = null;
          List tranList = new ArrayList();         
          String sqlQuery = ProjectSQLC.GET_MATERIAL_TRANSACTION;
          prepStmt = con.prepareStatement(sqlQuery);          
          try {
             
              rs = prepStmt.executeQuery();
              int i = 0;
              while (rs.next()) {
                  Vector tranVector = new Vector();                
                  tranVector.add(rs.getString("S_MATERIAL_TRANS_ID"));
                  tranVector.add(rs.getString("S_PROJECT_DEFINITION"));
                  tranVector.add(rs.getString("S_NETWORK_ID"));
                  tranVector.add(rs.getString("S_ACTIVITY_NO"));
                  tranVector.add(rs.getString("S_MATERIAL_DESC"));                 
                  tranVector.add(rs.getString("N_WEC_QUANTITY"));
                  tranVector.add(rs.getString("S_WECTYPE"));
                  tranVector.add(rs.getString("S_WEC_CAPACITY"));
                  tranList.add(i, tranVector);
                  i++;
              } 
          } catch (SQLException sqlExp) {
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
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              } catch (Exception e) {
                  prepStmt = null;
                  rs = null;
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              }
          }
          return tranList;
      }
    public List getprojectsactivitytransaction() throws Exception {
        JDBCUtils conmanager = new JDBCUtils();
          Connection con = conmanager.getConnection();
          PreparedStatement prepStmt = null;
          PreparedStatement ps = null;
          ResultSet rs = null;
          ResultSet rs1 = null;
          List tranList = new ArrayList();         
          String sqlQuery = ProjectSQLC.GET_ACTIVITY_TRANSACTION;
          prepStmt = con.prepareStatement(sqlQuery);          
          try {
             
              rs = prepStmt.executeQuery();
              int i = 0;
              while (rs.next()) {
                  Vector tranVector = new Vector();                
                  tranVector.add(rs.getString("S_ACTIVITY_TRAN_ID"));
                  tranVector.add(rs.getString("S_ACTIVITY_NO"));
                  tranVector.add(rs.getString("S_ACTIVITY_DESC"));
                  
                  String pdate =convertDateFormat(rs.getObject("D_PLAN_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
                  String pedate =convertDateFormat(rs.getObject("D_PLAN_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");

                  
                  tranVector.add(pdate);
                  tranVector.add(pedate);   
                  String padate="NA",paedate="NA";
                  
                  if(rs.getObject("D_ACTUAL_STARTDATE") == null || rs.getObject("D_ACTUAL_STARTDATE").equals(""))	
		    		{
		            }
		            else
		            {    padate =convertDateFormat(rs.getObject("D_ACTUAL_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
			    	    			    	
		                 }
		            if(rs.getObject("D_ACTUAL_ENDDATE") == null || rs.getObject("D_ACTUAL_ENDDATE").equals(""))	
		    		{
		            }
		            	
		            else
		            {    
		            	paedate =convertDateFormat(rs.getObject("D_ACTUAL_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
		        	
		            }	
			    	
		            
		            
                  tranVector.add(padate);
                  tranVector.add(paedate);
                  
                  tranList.add(i, tranVector);
                  i++;
              } 
          } catch (SQLException sqlExp) {
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
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              } catch (Exception e) {
                  prepStmt = null;
                  rs = null;
                  if (con != null) {
                      conmanager.closeConnection();
                      con = null;
                  }
              }
          }
          return tranList;
      }
    /////////////////////samir
    
    public String addNewMaterialIn(String UserId,DynaBean dynaBean) throws Exception{
       	String msg="";
       	JDBCUtils conmanager = new JDBCUtils();
           Connection conn = conmanager.getConnection();
           PreparedStatement prepStmt = null;
           PreparedStatement ps = null;
           ResultSet rs = null;
           String sqlQuery = "";
           try{
       		String mname = dynaBean.getProperty("MNametxt").toString();
       		String mcode = dynaBean.getProperty("MCodetxt").toString();
       		mname=mname.toUpperCase();
       		String mid = "";
       		if (dynaBean.getProperty("MIdtxt") == null || dynaBean.getProperty("MIdtxt").equals("")){
       			mid  = CodeGenerate.NewCodeGenerate("TBL_MATERIAL_MASTER");
       			sqlQuery = ProjectSQLC.CHECK_MATERIAL_MASTER;
       			prepStmt = conn.prepareStatement(sqlQuery);
       			prepStmt.setObject(1,mname);
       			prepStmt.setObject(2,mcode);
       			prepStmt.setObject(3,mid);
       			rs = prepStmt.executeQuery();
       			if (rs.next())
       			{
       				msg = "<font class='errormsgtext'>Material already exists!</font>";
       				rs.close();
       				prepStmt.close();    				
       			}
       			else
       			{
       				sqlQuery = ProjectSQLC.INSERT_MATERIAL;
       				ps = conn.prepareStatement(sqlQuery);
       				ps.setObject(1, mid);
       				ps.setObject(2, mname);
       				ps.setObject(3, mcode);
       				ps.setObject(4, UserId);
       				ps.setObject(5, UserId);
       	            int iInserteddRows = ps.executeUpdate();
                       //conn.commit();
                       if (iInserteddRows != 1)
                           throw new Exception("DB_UPDATE_ERROR", null);
                       ps.close();
       	            msg = "<font class='sucessmsgtext'>Material Added Successfull!</font>";
       			}
       			prepStmt.close();
       			rs.close();
       		}else{
       			mid = dynaBean.getProperty("MIdtxt").toString();
       			sqlQuery = ProjectSQLC.CHECK_MATERIAL_MASTER;
       			prepStmt = conn.prepareStatement(sqlQuery);
       			prepStmt.setObject(1,mname);
       			prepStmt.setObject(2,mcode);
       			prepStmt.setObject(3,mid);
       			rs = prepStmt.executeQuery();
       			if (rs.next())
       			{
       				msg = "<font class='errormsgtext'>Material already exists!</font>";
       				rs.close();
       				prepStmt.close();    				
       			}
       			else
       			{		    			
    	    			sqlQuery = ProjectSQLC.UPDATE_MATERIAL;
    	    			ps = conn.prepareStatement(sqlQuery);
    	    			ps.setObject(1, mname);
    	    			ps.setObject(2, mcode);					
    	    			ps.setObject(3, UserId);
    	    			ps.setObject(4, mid);				
    	    			ps.executeUpdate();
    	                //conn.commit();
    	                ps.close();
    					msg = "<font class='sucessmsgtext'>Material Updated Successfully!</font>";
       			}
       			prepStmt.close();
       			rs.close();
       		}				
           }catch (SQLException sqlExp) {
               sqlExp.printStackTrace();
               Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
               throw exp;
           } finally {
               try {
                   if (prepStmt != null)
                       prepStmt.close();
                   if (ps != null)
                       ps.close();
                   if (rs != null)
                       rs.close();
                   if (conn != null) {
                   	conn.close();
                   	conn = null;
                   	
                       conmanager.closeConnection();conmanager = null;
                   }
               } catch (Exception e) {
                   prepStmt = null;
                   ps = null;
                   rs = null;
                   if (conn != null) {
                   	conn.close();
                   	conn = null;
                   	
                       conmanager.closeConnection();conmanager = null;
                   }
               }
           }
       	return msg;
    } 
    
	
    public String getPRJAjaxDetails(String item,String action,String UserId) throws Exception{
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
        try{        	 if(action.equals("PRJ_findMaterialMaster"))
        	{
        		sqlQuery = ProjectSQLC.SELECT_MATERIAL_MASTER;
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
        		sqlQuery = ProjectSQLC.SELECT_MATERIAL_MASTER_BY_ID;
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
        		sqlQuery = ProjectSQLC.GET_PROJECT_ID;
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
        		sqlQuery = ProjectSQLC.GET_PROJECT_HEADING;
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,app_para[0]);
			    ps.setObject(2,app_para[1]);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
			    xml.append("<projhead generated=\""+System.currentTimeMillis()+"\">\n");
			    xml.append("<projcode>\n");
			    strData = "<table id = projHead  cellSpacing=0 cellPadding=0  border=0 width = 80%>";
			    while (rs.next())
			    {	
			    	
			    	xml.append("<pid>");
				    xml.append(rs.getObject("S_PROJECT_ID"));
				    xml.append("</pid>\n");
				    xml.append("<pdef>");
				    xml.append(rs.getObject("S_PROJECT_DEFINITION"));
				    xml.append("</pdef>\n");
				    strData = strData + "<tr><td colspan=6 width = 80%><TABLE cellSpacing=0 cellPadding=0 width=90% border=0><TBODY><TR >";
				    
				  //  strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_left.gif width=10 border=0></TD>";
				    strData = strData + " <TD class=SectionTitle noWrap colspan=6>Project Status Report</TD>";
				   // strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_right.gif width=10 border=0></TD></TR></TBODY></TABLE></TD></TR>";
				    
				   
				   
				    
				    pid=rs.getObject("S_PROJECT_ID").toString();
				    strData = strData + "<tr class=TableTitle>";
		           // strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
		            strData = strData + "<td vAlign = middle  class=TableCell align = left width = 100% colspan=6>" +rs.getObject("S_PROJECT_DESC").toString()+" </td>";
		           // strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%> </td>";
		            strData = strData + "</tr>";
		            
		            strData = strData + "<tr class=TableSummaryRow>";
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>Project Definition:</td>";
		            strData = strData + "<td  vAlign = middle class=TableCell align = left  width = 38% colspan=2>" +rs.getObject("S_PROJECT_DEFINITION").toString()+" </td>";
		           
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>State/Site:</td>";
		            strData = strData + "<td vAlign = middle class=TableCell align = left  width = 38% colspan=2>"+rs.getObject("S_STATE_NAME").toString()+"/"+rs.getObject("S_SITE_NAME").toString()+" </td>";
		           
		            strData = strData + "</tr>";
		            strData = strData + "<tr class=TableSummaryRow>";
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 25%> </td>";
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
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 25%> </td>";
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
	            strData = strData + "<td vAlign = middle align = left width = 25%> </td>";
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
			    	
			    	strData = strData+"<table id = projPartA class = text width = 90%>";
			    	strData = strData + "<tr class=TableSolidRow>";
		            strData = strData + "<td vAlign = middle class=TableCell colspan=6 align = left width = 90%>PART A Installation </td>";
		              strData = strData + "</tr>";
		            strData = strData + "<tr>";
		            strData = strData + "<tr class=TableTitle>";
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 26%></td>";
		            strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Planned</td>";
		           
		            strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Actual</td>";
		            
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%></td>";
		            strData = strData + "</tr>";
		            strData = strData + "<tr class=TableTitle>";
		            strData = strData + "<td vAlign = middle align = left class=TableCell width = 26%>Description  </td>";
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
			            strData = strData + "<td vAlign=middle align=left  width=30% colspan=6><b>WEC "+rsproc.getObject("N_ACTIVITY_WEC_LEVEL").toString()+" </b></td>";
			           // strData = strData + "<td vAlign = middle class=TableCell colspan=1 align = left ><a href='javascript:myFunction("+ pid +","+weccnt+")'>Graph</a></td>";
				          
			            strData = strData + "</tr>";
				    	}
				    	
				    	String pdate =convertDateFormat(rsproc.getObject("D_PLAN_STARTDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
				    	String pedate =convertDateFormat(rsproc.getObject("D_PLAN_ENDDATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");	
				    	
				    	strData = strData + "<tr class=TableRow2>";
			            strData = strData + "<td vAlign = middle class=TableCell align = left width = 25%>"+rsproc.getObject("S_ACTIVITY_DESC").toString()+" </td>";
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
			           
			            strData = strData + "<td vAlign = middle class=TableCell align = Remarks: width = 90% colspan=6><u>Remarks:</u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+partARem+"</td>";
			           
			            
			            strData = strData + "</tr>";
				    strData = strData + "</table>";
				    
				    ///////////////////Part B Data To display ////////////////////
				    strData = strData+"<table id = projPartB class = text width = 90%>";
				    strData = strData + "<tr>";
		            strData = strData + "<td vAlign = middle align = left width = 25%> </td>";
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
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 25%></td>";
		            strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Planned</td>";
		           
		            strData = strData + "<td vAlign = middle class=TableCell align = center width = 24% colspan=2>Actual</td>";
		            
		            strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%></td>";
		            strData = strData + "</tr>";
		            strData = strData + "<tr class=TableTitle>";
		            strData = strData + "<td vAlign = middle align = left class=TableCell width = 25%>Description  </td>";
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
			            strData = strData + "<td vAlign = middle class=TableCell align = left width = 25%>"+rsproc.getObject("S_ACTIVITY_DESC").toString()+" </td>";
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
		           
		            strData = strData + "<td vAlign = middle class=TableCell align = left width =90% colspan=6><u>Remarks:</u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+PartBRem+"</td>";
		           
		            
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
				      String cls="TableRow1";
                     if(arrayWECFlag.isEmpty()==false)
                     {
                        
                    		
							
			            strData = strData+ "<table id = tblPartC class = text width = 90%>";
			            strData = strData + "<tr class=TableSolidRow>";
			            strData = strData + "<td vAlign = middle class=TableCell colspan=6 align = left width = 90%>Part C : Material Status </td>";
			            strData = strData + "</tr>";
			            strData = strData + "<tr class=TableTitle>";
			            strData = strData + "<td vAlign = middle class=TableCell  align = left width = 25%>Machines Number </td>";
			            strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Tower</td>";
			            strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Machine</td>";
			            strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Blades</td>";
			            strData = strData + "<td vAlign = middle class=TableCell  align = left width = 12%>Transformer</td>";
			            strData = strData + "<td vAlign = middle class=TableCell align = left width = 12%>&nbsp;</td>";
			            strData = strData + "</tr>";
			           for (int iPartCCounter = 0; iPartCCounter < counter; iPartCCounter =iPartCCounter + 1)
			            {
			        	   int rem1 = 1;
							rem1 = iPartCCounter % 2;
							
							if (rem1 == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
			            strData= strData+"<tr class=" + cls +">";
			            
			            strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>" +arrayWECFlag.get(iPartCCounter) + "</td>";
			                if(arrayTower.isEmpty()==false && arrayTower.size()> iPartCCounter)
			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayTower.get(iPartCCounter) + "</td>";
			                else
			                strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>  </td>";

			                if(arrayMachine.isEmpty()==false && arrayMachine.size()> iPartCCounter)
			                strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>" + arrayMachine.get(iPartCCounter) + "</td>";
			                else
			                strData= strData+"<td vAlign = middle class=TableCell align = left width = 12%>  </td>";

			              
			                if(arrayBlade.isEmpty()==false && arrayBlade.size()> iPartCCounter)
			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayBlade.get(iPartCCounter) + "</td>";
			                else
			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>  </td>";
			               
			                if(arrayTransformer.isEmpty()==false && arrayTransformer.size()> iPartCCounter)
			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>" + arrayTransformer.get(iPartCCounter) + "</td>";
			                else
			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>  </td>";

			                strData= strData+ "<td vAlign = middle class=TableCell align = left width = 12%>&nbsp;</td>";

			                strData= strData+ "</tr>";
			           
			            }
			            
                     }
			            else
			            strData=strData+"<tr><td colspan=6 align=center width=90%><b>No Or Insufficient Data</b></td></tr>";
                     
                     
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
						   
				            strData = strData + "<td vAlign=middle class=TableCell align=left width=90% colspan=6><u>Remarks:</u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+matRem+"</td>";
				           
				            
				            strData = strData + "</tr>";
                        strData= strData+ "</table>";
                      rsproc.close();
      			      callStmt.close();
                     }
			  
			    
			    strData = strData + "<tr><td colspan=6 width = 90%><TABLE cellSpacing=0 cellPadding=0 width=90% border=0><TBODY><TR>";
			    
			   // strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_left.gif width=10 border=0></TD>";
			    strData = strData + " <TD class=SectionTitle noWrap colspan=6>Project Status Report</TD>";
			  //  strData = strData + "<TD width=10><IMG height=20 src=resources/images/section_right.gif width=10 border=0></TD></TR></TBODY></TABLE></TD></TR>";
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
        //System.out.println("Error: fetchALL: exception");
        //System.out.println(e.getMessage());
      }
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
    



}
