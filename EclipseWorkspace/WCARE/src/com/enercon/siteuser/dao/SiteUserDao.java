package com.enercon.siteuser.dao;

import com.enercon.global.utils.Diff;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.CodeGenerate;
import com.enercon.admin.dao.*;
import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.text.*;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import oracle.jdbc.driver.*;

public class SiteUserDao implements WcareConnector {
    private static Logger logger = Logger.getLogger(SiteUserDao.class);

    public String UploadFederData(Iterator rows,String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        PreparedStatement ps10 = null;
        ResultSet rs = null;
        ResultSet rs10 = null;
        String sqlQuery = "";
        String sqlQuery10 = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		AdminDao ad = new AdminDao();
        try{
        	if(rows.hasNext())
			{
        		int rw = 2;
        		HSSFRow row = (HSSFRow)rows.next();
        		String mp[] = new String[256];
        		String mpcode[] = new String[256];
        		java.util.Calendar now = java.util.Calendar.getInstance();
        		int cellno = 3;
        		int totrecords = 0;
        		
        		for (int i = 0;i <= 256;i++)
				{
        			cellno = cellno + 1;
        			mp[i] = row.getCell((short)cellno) == null ? "" : row.getCell((short)cellno).toString();
        			if(mp[i].trim().equals(""))
        			{
        				break;
        			}
        			else
        			{
	        			totrecords = i;
					}        			
    			}        		
        		/*
	        		mp[0] = row.getCell((short)3) == null ? "" : row.getCell((short)3).toString(); //KWH IMP
	        		mp[1] = row.getCell((short)4) == null ? "" : row.getCell((short)4).toString(); //RKVAH IMP LAG
	        		mp[2] = row.getCell((short)5) == null ? "" : row.getCell((short)5).toString(); //RKVAH IMP LEAD
	        		mp[3] = row.getCell((short)6) == null ? "" : row.getCell((short)6).toString(); //KVAH IMP
	        		mp[4] = row.getCell((short)7) == null ? "" : row.getCell((short)7).toString(); //KWH EXP
	        		mp[5] = row.getCell((short)8) == null ? "" : row.getCell((short)8).toString(); //RKVAH EXP LAG
	        		mp[6] = row.getCell((short)9) == null ? "" : row.getCell((short)9).toString(); //RKVAH EXP LEAD
	        		mp[7] = row.getCell((short)10) == null ? "" : row.getCell((short)10).toString(); //KVAH EXP
        		*/        		
        		for (int i = 0;i <= totrecords;i++)
				{
        			sqlQuery = SiteUserSQLC.SELECT_MP_MASTER_BY_DESC;
        			ps = conn.prepareStatement(sqlQuery);
        			ps.setObject(1, mp[i]);
        			rs = ps.executeQuery();
        			if (rs.next())
        			{
        				mpcode[i] = rs.getString("s_mp_id");
        			}
        			else
        			{
        				mpcode[i] = "";
        			}
        			rs.close();
        			ps.close();
				}				
				String ebid = "";
				String eid = "";
				double mpf = 0;
				String dt = "";								
				String readtype="";
				String ms= "";
	        	while(rows.hasNext()) 
				{
					row = (HSSFRow)rows.next();
					dt = row.getCell((short)0).toString();
					dt = dt.replace(".", "/");
					java.util.Date ddt = new java.util.Date();
	        		ddt = format.parse(dt);
	        		java.util.Date nw = new java.util.Date();
	        		if (nw.before(ddt))
	        		{
	        			msg = msg + "<BR> Row No: " + rw + " - Future date not acceptable";
	        		}
	        		else
	        		{
		        		java.sql.Date date = new java.sql.Date(ddt.getTime());
		        		now.setTime(date);	
						now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
						String pdate= DateFormat.getDateInstance().format(now.getTime());
						String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
						java.util.Date dt1 = new java.util.Date();
			            dt1 = format.parse(fdate);
			            java.sql.Date prevdate = new java.sql.Date(dt1.getTime());
			            double vl[] = new double[256];
			            
			            //***********************************************************//
			            double nvalue=0;
			            double nvaluewithll=0;
			            String a= row.getCell((short)1).toString();
			            
			            sqlQuery10 = SiteUserSQLC.SELECT_WEC_READING_BY_FEDERDESC;
						ps10 = conn.prepareStatement(sqlQuery10);
						ps10.setObject(1,a);
						ps10.setObject(2,date);
						
			            rs10=ps10.executeQuery();
			            if (rs10.next())
			            {
			            	nvalue=rs10.getDouble("n_value");
			            	
			            }
			            if(nvalue!=0)
			            	nvaluewithll=nvalue-(nvalue*3/100);
			            
			            rs10.close();
			            ps10.close();
			            			           
			            //***********************************************************//
			            //if(row.getCell((short)8).getNumericCellValue()>=nvaluewithll)
			            //{
			            //	msg = msg + "<BR> Row No: " + rw + " " + ebid + " : Line loss more than 3%";
			            //}
						if(ebid != row.getCell((short)1).toString())
						{
							ebid = row.getCell((short)1).toString();
							sqlQuery = SiteUserSQLC.SELECT_FEDER_MASTER_BY_DESC_NEW;
							ps = conn.prepareStatement(sqlQuery);
							ps.setObject(1,ebid);
							ps.setObject(2,date);
							rs = ps.executeQuery();
							if (rs.next())
							{
								//cid = rs.getString("S_CUSTOMER_ID");
								eid = rs.getString("S_FEDER_ID");
								mpf = rs.getDouble("N_MULTI_FACTOR");
								//eid = rs.getString("S_EB_ID");
							}
							
							rs.close();
							ps.close();
						}
						readtype= row.getCell((short)2) != null ?  row.getCell((short)2).toString() : "" ;
						if (readtype.equals("I")){
							msg = msg + "<BR> Cannot upload Initial Type of row: " + rw;
						}
						else						
			            {
							String remarks = row.getCell((short)3) != null ?  row.getCell((short)3).toString() : "" ;
							remarks= remarks.replace(",", " ");
							double value[] = new double[256];
							cellno = 3;
							for (int i = 0;i <= totrecords;i++)
							{
								cellno = cellno + 1;
								value[i] = row.getCell((short)cellno) != null ?  row.getCell((short)cellno).getNumericCellValue() : 0 ; 
							}
							//double gen = row.getCell((short)3) != null ?  row.getCell((short)3).getNumericCellValue() : 0 ;
							//double hrs = row.getCell((short)4) != null ?  row.getCell((short)4).getNumericCellValue() : 0 ;
							/*value[0] = row.getCell((short)3) != null ?  row.getCell((short)3).getNumericCellValue() : 0 ; 
							value[1] = row.getCell((short)4) != null ?  row.getCell((short)4).getNumericCellValue() : 0 ; 
							value[2] = row.getCell((short)5) != null ?  row.getCell((short)5).getNumericCellValue() : 0 ; 
							value[3] = row.getCell((short)6) != null ?  row.getCell((short)6).getNumericCellValue() : 0 ; 
							value[4] = row.getCell((short)7) != null ?  row.getCell((short)7).getNumericCellValue() : 0 ; 
							value[5] = row.getCell((short)8) != null ?  row.getCell((short)8).getNumericCellValue() : 0 ; 
							value[6] = row.getCell((short)9) != null ?  row.getCell((short)9).getNumericCellValue() : 0 ; 
							value[7] = row.getCell((short)10) != null ?  row.getCell((short)10).getNumericCellValue() : 0 ; */
							
							for (int i = 0;i <= totrecords;i++)
							{
								System.out.println("mpcode[]=="+mpcode[i].toString());
								if (! mpcode[i].equals("") && ! eid.equals(""))
								{
									if(readtype.equals("D"))
						            {
										sqlQuery = SiteUserSQLC.CHECK_FEDER_READING_YESTERDAY_BY_MP;
						        		ps = conn.prepareStatement(sqlQuery);
						        		ps.setDate(1,prevdate);
						        		ps.setObject(2,mpcode[i]);
						        		ps.setObject(3,eid);
						        		rs = ps.executeQuery();
						        		if(rs.next()){
						        			if (value[i] <= 0)
							    			{
						        				vl[i] = rs.getDouble("N_VALUE");
							    			}
						        			else
						        			{
						        				vl[i] = rs.getDouble("N_VALUE") + (value[i] / mpf);
							    			}
						        		}
						        		else{
						        			vl[i] = 0;
						        		}
						        		rs.close();
						        		ps.close();
						        		sqlQuery = mpcode[i]+eid+","+vl[i]+","+dt+","+value[i]+","+readtype;
						            }
									else if(readtype.equals("M") || readtype.equals("Y"))
									{
										sqlQuery = SiteUserSQLC.CHECK_FEDER_READING_YESTERDAY_BY_MP_MAX;
										ps = conn.prepareStatement(sqlQuery);
						        		ps.setObject(1,mpcode[i]);
						        		ps.setObject(2,eid);
						        		ps.setObject(3,readtype);
						        		ps.setObject(4,readtype);
						        		ps.setObject(5,prevdate);
						        		ps.setObject(6,mpcode[i]);
						        		ps.setObject(7,eid);
						        		rs = ps.executeQuery();
						        		if(rs.next()){
						        			if (value[i] <= 0)
							    			{
						        				vl[i] = 0; //rs.getDouble("N_VALUE");
							    			}
						        			else
						        			{
						        				vl[i] = (value[i] - rs.getDouble("N_VALUE")) * mpf;
							    			}
						        		}
						        		else{
						        			vl[i] = 0;
						        		}
						        		rs.close();
						        		ps.close();
						        		sqlQuery = mpcode[i]+eid+","+value[i]+","+dt+","+vl[i]+","+readtype;						        		
									}
									ms = UpdateFDData(UserId, sqlQuery,1);
									if (! ms.equals(""))
									{
										msg = msg + "<BR> Row No: " + rw + " - " + mp[i] + " : " + ms;
									}
								}	
								else
								{
									break;
								}
								String ApplicationId = mpcode[i]+eid+","+vl[i]+","+dt+","+readtype+","+value[i];
								msg=msg+showUpdateEBByfeder(UserId, ApplicationId);
								
							}
							if ( remarks.equals("") && ! eid.equals(""))
							{
								remarks = "ee";
								sqlQuery = eid + "," + remarks + "," + dt + "," + readtype;
								ms = UpdateFDemarks(UserId, sqlQuery);
								if (! ms.equals(""))
								{
									msg = msg + "<BR> Row No: " + rw + " - Remarks : " + ms;
								}
							}
							if (eid.equals(""))
							{
								msg = msg + "<BR> Row No: " + rw + " " + ebid + " : EB not found";
							}
							/*if(nvaluewithll!=0)
							{
								if(row.getCell((short)8).getNumericCellValue()< nvaluewithll)
						        {
						        	msg = msg + "<BR><font class='errormsgtext'>Row No: " + rw + " " + ebid + ": Line loss more than 3%</font>";
						        }
							}
							else
								msg = msg + "<BR><font class='errormsgtext'>Row No: " + rw + " " + ebid + ": Line loss not calculated due to non availabilty of WEC Data</font>";*/
						}
	        		//}
		            //else
		        	//{
		        	//	msg = msg + "<BR> Row No: " + rw + " " + ebid + " : Line loss more than 3%";
		        	//}
	        	}	        	
	        	rw = rw + 1;
				}
	        	if (msg.equals(""))
	        	{
	        		msg = "<font class='sucessmsgtext'>Upload Successful!</font>";
	        	}
			}
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps10) , Arrays.asList(rs,rs10) , conn);
        }
    	return msg;
    }
    
    public String UploadEBData(Iterator rows,String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        PreparedStatement ps10 = null;
        ResultSet rs = null;
        ResultSet rs10 = null;
        String sqlQuery = "";
        String sqlQuery10 = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		AdminDao ad = new AdminDao();
        try{
        	if(rows.hasNext())
			{
        		int rw = 2;
        		HSSFRow row = (HSSFRow)rows.next();
        		String mp[] = new String[256];
        		String mpcode[] = new String[256];
        		java.util.Calendar now = java.util.Calendar.getInstance();
        		int cellno = 3;
        		int totrecords = 0;
        		
        		for (int i = 0;i <= 256;i++)
				{
        			cellno = cellno + 1;
        			mp[i] = row.getCell((short)cellno) == null ? "" : row.getCell((short)cellno).toString();
        			if(mp[i].trim().equals(""))
        			{
        				break;
        			}
        			else
        			{
	        			totrecords = i;
					}        			
    			}
        		// System.out.println("totrecords.."+totrecords);
        		/*mp[0] = row.getCell((short)3) == null ? "" : row.getCell((short)3).toString(); //KWH IMP
        		mp[1] = row.getCell((short)4) == null ? "" : row.getCell((short)4).toString(); //RKVAH IMP LAG
        		mp[2] = row.getCell((short)5) == null ? "" : row.getCell((short)5).toString(); //RKVAH IMP LEAD
        		mp[3] = row.getCell((short)6) == null ? "" : row.getCell((short)6).toString(); //KVAH IMP
        		mp[4] = row.getCell((short)7) == null ? "" : row.getCell((short)7).toString(); //KWH EXP
        		mp[5] = row.getCell((short)8) == null ? "" : row.getCell((short)8).toString(); //RKVAH EXP LAG
        		mp[6] = row.getCell((short)9) == null ? "" : row.getCell((short)9).toString(); //RKVAH EXP LEAD
        		mp[7] = row.getCell((short)10) == null ? "" : row.getCell((short)10).toString(); //KVAH EXP*/
        		for (int i = 0;i <= totrecords;i++)
				{
        			sqlQuery = SiteUserSQLC.SELECT_MP_MASTER_BY_DESC;
        			ps = conn.prepareStatement(sqlQuery);
        			ps.setObject(1, mp[i]);
        			rs = ps.executeQuery();
        			if (rs.next())
        			{
        				mpcode[i] = rs.getString("s_mp_id");
        			}
        			else
        			{
        				mpcode[i] = "";
        			}
        			rs.close();
        			ps.close();
				}				
				String ebid = "";
				String eid = "";
				double mpf = 0;
				String dt = "";								
				String readtype="";
				String ms= "";
	        	while(rows.hasNext()) 
				{
					row = (HSSFRow)rows.next();
					dt = row.getCell((short)0).toString();
					dt = dt.replace(".", "/");
					java.util.Date ddt = new java.util.Date();
	        		ddt = format.parse(dt);
	        		java.util.Date nw = new java.util.Date();
	        		if (nw.before(ddt))
	        		{
	        			msg = msg + "<BR> Row No: " + rw + " - Future date not acceptable";
	        		}
	        		else
	        		{
		        		java.sql.Date date = new java.sql.Date(ddt.getTime());
		        		now.setTime(date);	
						now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
						String pdate= DateFormat.getDateInstance().format(now.getTime());
						String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
						java.util.Date dt1 = new java.util.Date();
			            dt1 = format.parse(fdate);
			            java.sql.Date prevdate = new java.sql.Date(dt1.getTime());
			            double vl[] = new double[256];
			            
			            /***********************************************************/
			            double nvalue=0;
			            double nvaluewithll=0;
			            String a= row.getCell((short)1).toString();
			            
			            sqlQuery10 = SiteUserSQLC.SELECT_WEC_READING_BY_EBDESC;
						ps10 = conn.prepareStatement(sqlQuery10);
						ps10.setObject(1,a);
						ps10.setObject(2,date);
						
			            rs10=ps10.executeQuery();
			            if (rs10.next())
			            {
			            	nvalue=rs10.getDouble("n_value");
			            	//System.out.println("nvalue=="+nvalue);
			            }
			            if(nvalue!=0)
			            	nvaluewithll=nvalue-(nvalue*3/100);
			            
			            rs10.close();
			            ps10.close();
			            			           
			            /***********************************************************/
			            //if(row.getCell((short)8).getNumericCellValue()>=nvaluewithll)
			            //{
			            //	msg = msg + "<BR> Row No: " + rw + " " + ebid + " : Line loss more than 3%";
			            //}
						if(ebid != row.getCell((short)1).toString())
						{
							ebid = row.getCell((short)1).toString();
							sqlQuery = SiteUserSQLC.SELECT_EB_MASTER_BY_DESC_NEW;
							ps = conn.prepareStatement(sqlQuery);
							ps.setObject(1,ebid);
							ps.setObject(2,date);
							rs = ps.executeQuery();
							if (rs.next())
							{
								//cid = rs.getString("S_CUSTOMER_ID");
								eid = rs.getString("S_EB_ID");
								mpf = rs.getDouble("N_MULTI_FACTOR");
								//eid = rs.getString("S_EB_ID");
							}
							
							rs.close();
							ps.close();
						}
						readtype= row.getCell((short)2) != null ?  row.getCell((short)2).toString() : "" ;
						if (readtype.equals("I")){
							msg = msg + "<BR> Cannot upload Initial Type of row: " + rw;
						}
						else						
			            {
							String remarks = row.getCell((short)3) != null ?  row.getCell((short)3).toString() : "" ;
							remarks= remarks.replace(",", " ");
							double value[] = new double[256];
							cellno = 3;
							for (int i = 0;i <= totrecords;i++)
							{
								cellno = cellno + 1;
								value[i] = row.getCell((short)cellno) != null ?  row.getCell((short)cellno).getNumericCellValue() : 0 ; 
							}
							//double gen = row.getCell((short)3) != null ?  row.getCell((short)3).getNumericCellValue() : 0 ;
							//double hrs = row.getCell((short)4) != null ?  row.getCell((short)4).getNumericCellValue() : 0 ;
							/*value[0] = row.getCell((short)3) != null ?  row.getCell((short)3).getNumericCellValue() : 0 ; 
							value[1] = row.getCell((short)4) != null ?  row.getCell((short)4).getNumericCellValue() : 0 ; 
							value[2] = row.getCell((short)5) != null ?  row.getCell((short)5).getNumericCellValue() : 0 ; 
							value[3] = row.getCell((short)6) != null ?  row.getCell((short)6).getNumericCellValue() : 0 ; 
							value[4] = row.getCell((short)7) != null ?  row.getCell((short)7).getNumericCellValue() : 0 ; 
							value[5] = row.getCell((short)8) != null ?  row.getCell((short)8).getNumericCellValue() : 0 ; 
							value[6] = row.getCell((short)9) != null ?  row.getCell((short)9).getNumericCellValue() : 0 ; 
							value[7] = row.getCell((short)10) != null ?  row.getCell((short)10).getNumericCellValue() : 0 ; */
							
							for (int i = 0;i <= totrecords;i++)
							{
								if (! mpcode[i].equals("") && ! eid.equals(""))
								{
									if(readtype.equals("D"))
						            {
										sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
						        		ps = conn.prepareStatement(sqlQuery);
						        		ps.setDate(1,prevdate);
						        		ps.setObject(2,mpcode[i]);
						        		ps.setObject(3,eid);
						        		rs = ps.executeQuery();
						        		if(rs.next()){
						        			if (value[i] <= 0)
							    			{
						        				vl[i] = rs.getDouble("N_VALUE");
							    			}
						        			else
						        			{
						        				vl[i] = rs.getDouble("N_VALUE") + (value[i] / mpf);
							    			}
						        		}
						        		else{
						        			vl[i] = 0;
						        		}
						        		rs.close();
						        		ps.close();
						        		sqlQuery = mpcode[i]+eid+","+vl[i]+","+dt+","+value[i]+","+readtype;
						            }
									else if(readtype.equals("M") || readtype.equals("Y"))
									{
										sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP_MAX;
										ps = conn.prepareStatement(sqlQuery);
						        		ps.setObject(1,mpcode[i]);
						        		ps.setObject(2,eid);
						        		ps.setObject(3,readtype);
						        		ps.setObject(4,readtype);
						        		ps.setObject(5,prevdate);
						        		ps.setObject(6,mpcode[i]);
						        		ps.setObject(7,eid);
						        		rs = ps.executeQuery();
						        		if(rs.next()){
						        			if (value[i] <= 0)
							    			{
						        				vl[i] = 0; //rs.getDouble("N_VALUE");
							    			}
						        			else
						        			{
						        				vl[i] = (value[i] - rs.getDouble("N_VALUE")) * mpf;
							    			}
						        		}
						        		else{
						        			vl[i] = 0;
						        		}
						        		rs.close();
						        		ps.close();
						        		sqlQuery = mpcode[i]+eid+","+value[i]+","+dt+","+vl[i]+","+readtype;
									}
									ms = UpdateEBData(UserId, sqlQuery,1,1);
									if (! ms.equals(""))
									{
										msg = msg + "<BR> Row No: " + rw + " - " + mp[i] + " : " + ms;
									}
								}	
								else
								{
									break;
								}
							}
							if (! remarks.equals("") && ! eid.equals(""))
							{
								sqlQuery = eid + "," + remarks + "," + dt + "," + readtype;
								ms = UpdateEBemarks(UserId, sqlQuery);
								if (! ms.equals(""))
								{
									msg = msg + "<BR> Row No: " + rw + " - Remarks : " + ms;
								}
							}
							if (eid.equals(""))
							{
								msg = msg + "<BR> Row No: " + rw + " " + ebid + " : EB not found";
							}
							if(nvaluewithll!=0)
							{
								if(row.getCell((short)8).getNumericCellValue()< nvaluewithll)
						        {
						        	msg = msg + "<BR><font class='errormsgtext'>Row No: " + rw + " " + ebid + ": Line loss more than 3%</font>";
						        }
							}
							else
								msg = msg + "<BR><font class='errormsgtext'>Row No: " + rw + " " + ebid + ": Line loss not calculated due to non availabilty of WEC Data</font>";
						}
	        		//}
		            //else
		        	//{
		        	//	msg = msg + "<BR> Row No: " + rw + " " + ebid + " : Line loss more than 3%";
		        	//}
	        	}	        	
	        	rw = rw + 1;
				}
	        	if (msg.equals(""))
	        	{
	        		msg = "<font class='sucessmsgtext'>Upload Successful!</font>";
	        	}
			}
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps10) , Arrays.asList(rs,rs10) , conn);
        }
    	return msg;
    }
    
    public String UploadWECData(Iterator rows,String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//SimpleDateFormat hm = new SimpleDateFormat("HH:mm");
		AdminDao ad = new AdminDao();
        try{
        	if(rows.hasNext())
			{
        		int rw = 2;
        		HSSFRow row = (HSSFRow)rows.next();
        		if ((row.getCell((short)0) != null && row.getCell((short)0).toString().equals("Date"))
        				&& (row.getCell((short)1) != null && row.getCell((short)1).toString().equals("Location"))
        				&& (row.getCell((short)2) != null && row.getCell((short)2).toString().equals("ReadType"))
        				&& (row.getCell((short)3) != null && row.getCell((short)3).toString().equals("Remarks"))
        				&& (row.getCell((short)4) != null && row.getCell((short)4).toString().equals("Generation"))
        				&& (row.getCell((short)5) != null && row.getCell((short)5).toString().equals("Hours"))
    				)
        		{        			
        			String mp[] = new String[256];
	        		String mpcode[] = new String[256];
	        		java.util.Calendar now = java.util.Calendar.getInstance();
	        		int cellno = 5;
	        		int totrecords = 0;
	        		for (int i = 0;i <= 256;i++)
					{
	        			cellno = cellno + 1;
	        			mp[i] = row.getCell((short)cellno) == null ? "" : row.getCell((short)cellno).toString();
	        			if(mp[i].trim().equals(""))
	        			{
	        				break;
	        			}
	        			else
	        			{
		        			totrecords = i;
						}
	    			}
	        		for (int i = 0;i <= totrecords;i++)
					{
	        			sqlQuery = SiteUserSQLC.SELECT_MP_MASTER_BY_DESC;
	        			ps = conn.prepareStatement(sqlQuery);
	        			ps.setObject(1, mp[i]);
	        			rs = ps.executeQuery();
	        			if (rs.next())
	        			{
	        				mpcode[i] = rs.getString("s_mp_id");
	        			}
	        			else
	        			{
	        				mpcode[i] = "";
	        			}
	        			rs.close();
	        			ps.close();
					}
					//String cid="";
					String wecid = "";
					String wid = "";
					//String eid = "";
					String dt = "";								
					// String readid="";	
					String readtype="";
					String ms= "";
		        	while(rows.hasNext()) 
					{
						row = (HSSFRow)rows.next();
						dt = row.getCell((short)0).toString();
						dt = dt.replace(".", "/");
						java.util.Date ddt = new java.util.Date();
		        		ddt = format.parse(dt);
		        		java.util.Date nw = new java.util.Date();
		        		if (nw.before(ddt))
		        		{
		        			msg = msg + "<BR> Row No: " + rw + " - Future date not acceptable";
		        		}
		        		else
		        		{
			        		java.sql.Date date = new java.sql.Date(ddt.getTime());
			        		now.setTime(date);		
							now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
							String pdate= DateFormat.getDateInstance().format(now.getTime());
							String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
							java.util.Date dt1 = new java.util.Date();
				            dt1 = format.parse(fdate);
				            java.sql.Date prevdate = new java.sql.Date(dt1.getTime());
							if(wecid != row.getCell((short)1).toString())
							{
								wecid = row.getCell((short)1).toString();
								sqlQuery = SiteUserSQLC.S_WECSHORT_DESCR;
								ps = conn.prepareStatement(sqlQuery);
								ps.setObject(1,wecid.toUpperCase());
								rs = ps.executeQuery();
								if (rs.next())
								{
									//cid = rs.getString("S_CUSTOMER_ID");
									wid = rs.getString("S_WEC_ID");
									//eid = rs.getString("S_EB_ID");
								}
								rs.close();
								ps.close();
							}
							readtype= row.getCell((short)2) != null ?  row.getCell((short)2).toString() : "" ;
							String remarks = row.getCell((short)3) != null ?  row.getCell((short)3).toString() : "" ;
							remarks= remarks.replace(",", " ");
							
							double value[] = new double[256];
							cellno = 5;
							for (int i = 0;i <= totrecords;i++)
							{
								cellno = cellno + 1;
								value[i] = row.getCell((short)cellno) != null ?  row.getCell((short)cellno).getNumericCellValue() : 0 ; 
							}
							
							double gen = row.getCell((short)4) != null ?  row.getCell((short)4).getNumericCellValue() : 0 ;
							double hrs = row.getCell((short)5) != null ?  row.getCell((short)5).getNumericCellValue() : 0 ;
							
							double hrsCum = row.getCell((short)7) != null ?  row.getCell((short)7).getNumericCellValue() : 0 ;
							double hrsMf = row.getCell((short)8) != null ?  row.getCell((short)8).getNumericCellValue() : 0 ;
							double hrsMs = row.getCell((short)9) != null ?  row.getCell((short)9).getNumericCellValue() : 0 ;
							double hrsGfi = row.getCell((short)10) != null ?  row.getCell((short)10).getNumericCellValue() : 0 ;
							double hrsGsi = row.getCell((short)11) != null ?  row.getCell((short)11).getNumericCellValue() : 0 ;
							double hrsGfe = row.getCell((short)12) != null ?  row.getCell((short)12).getNumericCellValue() : 0 ;
							double hrsGse = row.getCell((short)13) != null ?  row.getCell((short)13).getNumericCellValue() : 0 ;
							double hrsLRSet = row.getCell((short)14) != null ?  row.getCell((short)14).getNumericCellValue() : 0 ;
							double hrsSsd = row.getCell((short)15) != null ?  row.getCell((short)15).getNumericCellValue() : 0 ;
							double totalCumHrs=hrsCum+hrsMf+hrsMs+hrsGfi+hrsGsi+hrsGfe+hrsGse+hrsSsd+hrsLRSet; // added hrsLRSet on 30-08-2012
							double totalCumHrsInRset=hrsCum+hrsMf+hrsMs+hrsGfi+hrsGsi+hrsGfe+hrsGse+hrsSsd+hrsLRSet;
							//double totalCumHrs=hrsCum+hrsMf+hrsMs;
							//double diff = gen - (row.getCell((short)6) != null ?  row.getCell((short)5).getNumericCellValue() : 0) ;
							
							if(totalCumHrsInRset!=0){
							
								if(gen != 0 || totalCumHrs != 0){	//if(1==1){//					
								
									if((totalCumHrs<=24)||((readtype=="I")||(readtype.equals("I")))){
									
									/*
									value[0] = row.getCell((short)5) != null ?  row.getCell((short)5).getNumericCellValue() : 0 ; 
									value[1] = row.getCell((short)6) != null ?  row.getCell((short)6).getNumericCellValue() : 0 ; 
									value[2] = row.getCell((short)7) != null ?  row.getCell((short)7).getNumericCellValue() : 0 ; 
									value[3] = row.getCell((short)8) != null ?  row.getCell((short)8).getNumericCellValue() : 0 ; 
									value[4] = row.getCell((short)9) != null ?  row.getCell((short)9).getNumericCellValue() : 0 ; 
									value[5] = row.getCell((short)10) != null ?  row.getCell((short)10).getNumericCellValue() : 0 ; 
									value[6] = row.getCell((short)11) != null ?  row.getCell((short)11).getNumericCellValue() : 0 ; 
									value[7] = row.getCell((short)12) != null ?  row.getCell((short)12).getNumericCellValue() : 0 ; 
									value[8] = row.getCell((short)13) != null ?  row.getCell((short)13).getNumericCellValue() : 0 ; 
									value[9] = row.getCell((short)14) != null ?  row.getCell((short)14).getNumericCellValue() : 0 ; 
									*/
										
									for (int i = 0;i <= totrecords;i++)
									{
										if (! mpcode[i].equals("") && ! wid.equals(""))
										{
											if (i == 0)
											{
												if(readtype.equals("D"))
									            {
									    			if (gen <= 0)
									    			{
														sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
										        		ps = conn.prepareStatement(sqlQuery);
										        		ps.setDate(1,prevdate);
										        		ps.setObject(2,mpcode[i].trim());
										        		ps.setObject(3,wid.trim());
										        		rs = ps.executeQuery();
										        		if(rs.next()){
										        			gen = rs.getDouble("N_VALUE") + value[i];
										        		}
										        		rs.close();
										        		ps.close();
									    			}
									            }
												sqlQuery = mpcode[i]+wid+","+gen+","+dt+","+value[i]+","+readtype;
												
											}
											else if( i == 1)
											{
												if(readtype.equals("D"))
									            {
									    			if (hrs <= 0)
									    			{
														sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
										        		ps = conn.prepareStatement(sqlQuery);
										        		ps.setDate(1,prevdate);
										        		ps.setObject(2,mpcode[i].trim());
										        		ps.setObject(3,wid.trim());
										        		rs = ps.executeQuery();
										        		if(rs.next()){
										        			hrs = rs.getDouble("N_VALUE") + value[i];
										        		}
										        		rs.close();
										        		ps.close();
									    			}
									            }
												sqlQuery = mpcode[i]+wid+","+hrs+","+dt+","+value[i]+","+readtype;
												
											}
											else
											{
												sqlQuery = mpcode[i]+wid+","+value[i]+","+dt+","+value[i]+","+readtype;
												
											}
							        		
											ms = UpdateWECData(UserId, sqlQuery,1,1);
											if (! ms.equals(""))
											{
												msg = msg + "<BR> Row No: " + rw + " - " + mp[i] + " : " + ms;
											}
										}												
									}
									if (! remarks.equals("") && ! wid.equals(""))
									{
										sqlQuery = wid + "," + remarks + "," + dt + "," + readtype;
										ms = UpdateWECRemarks(UserId, sqlQuery);
										if (! ms.equals(""))
										{
											msg = msg + "<BR> Row No: " + rw + " - Remarks : " + ms;
										}
									}
									/*}
									else
									{
					        			msg = "<font class='errormsgtext'>Total cummulative hour exceeds 24 hours..Please check</font>";
					        		}*/
									if (wid.equals(""))									
										msg = msg + "<BR> Row No: " + rw + " " + wecid + " : WEC not found";
																			
				        		}else
				        			msg = msg + "<BR><font class='errormsgtext'>Row No: " + rw + " - " + "Total Cummulative hour exceeds 24 hours..Please check and upload manually..</font>";								
							}else
								msg = msg + "<BR> Row No: " + rw + " - " + "Total Cummulative hour and Total Generation cannot be 0..Please check";
						}else
							msg = msg + "<BR> Row No: " + rw + " - " + "Total Cummulative hrs including Load Restriction EB and Spcl Shutdown WEC cannot be 0..Please check";
							
						}
						rw = rw + 1;
					}
		        	if (msg.equals(""))
		        	{
		        		msg = "<font class='sucessmsgtext'>Upload Successful!</font>";
		        	}
        		}
        		else
        		{
        			msg = "<font class='errormsgtext'>Format Improper! First 6 Heads are mandatory.</font>";
        		}
			}
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
    		
        }
    	return msg;
    }
    
    public String UploadWECRemarks(Iterator rows,String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    //	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0");
		
        try{
        	if(rows.hasNext())
			{
        		int rw = 2;
        		HSSFRow row = (HSSFRow)rows.next();
        		if ((row.getCell((short)0) != null && row.getCell((short)0).toString().equals("Date"))
        				&& (row.getCell((short)1) != null && row.getCell((short)1).toString().equals("Location"))        				
        				&& (row.getCell((short)2) != null && row.getCell((short)2).toString().equals("Remarks Code"))        				
    				)
        		{
        			java.util.Calendar now = java.util.Calendar.getInstance();
	        		int cellno = 5;
	        		int totrecords = 0;
	        		
	        		String wecid = "";
					String wid = "";	
					String rid = "";	
					String dt = "";		
					String ms= "";
					String remarkid = "";
					
		        	while(rows.hasNext()) 
					{
						row = (HSSFRow)rows.next();
						dt = row.getCell((short)0).toString();
						dt = dt.replace(".", "/");
						java.util.Date ddt = new java.util.Date();
		        		ddt = format.parse(dt);
		        		java.util.Date nw = new java.util.Date();
		        		if (nw.before(ddt))
		        		{
		        			msg = msg + "<BR> Row No: " + rw + " - Future date not acceptable";
		        		}
		        		else
		        		{
		        			AdminDao ad = new AdminDao();
			        		java.sql.Date date = new java.sql.Date(ddt.getTime());
			        		
							if(wecid != row.getCell((short)1).toString())
							{
								wecid = row.getCell((short)1).toString();
								sqlQuery = SiteUserSQLC.S_WECSHORT_DESCR;
								ps = conn.prepareStatement(sqlQuery);
								ps.setObject(1,wecid.toUpperCase());
								rs = ps.executeQuery();
								if (rs.next())
								{									
									wid = rs.getString("S_WEC_ID");									
								}
								else
									wid="";
								rs.close();
								ps.close();
							}						
							//  remarkid = formatter.format(row.getCell((short)2) != null ?  row.getCell((short)2).toString() : "0") ;	
							
							if(remarkid != row.getCell((short)2).toString())
							{
								remarkid = row.getCell((short)2).toString();
								//String ss[] = remarkid.split(".");								
								//remarkid = remarkid.substring(0, remarkid.length()-2);
								sqlQuery = SiteUserSQLC.S_UPLOAD_REMARKS;
								ps = conn.prepareStatement(sqlQuery);
								ps.setObject(1,remarkid);
								rs = ps.executeQuery();
								if (rs.next())
								{									
									rid = rs.getString("S_REMARKS_ID");									
								}else
								{
									rid="";
									msg = msg + "<font class='errormsgtext'><BR>Row No: " + rw + " - Remarks ID "+remarkid+" does not exist, Please check..</font>";
								}
								rs.close();
								ps.close();
							}		
							
							if (! rid.equals("") && ! wid.equals(""))
							{
								sqlQuery = SiteUserSQLC.CHECK_UPLOAD_REMARKS;								
								PreparedStatement ps0 = conn.prepareStatement(sqlQuery);
								ps0.setObject(1, rid);
								ResultSet rs0 = ps0.executeQuery();
								if(rs0.next())
								{
								sqlQuery = SiteUserSQLC.SELECT_UPLOAD_REMARKS;
								PreparedStatement ps01 = conn.prepareStatement(sqlQuery);
								ps01.setObject(1, date);
								ps01.setObject(2, rid);
								ps01.setObject(3, wid);
								
								ResultSet rs01 = ps01.executeQuery();
								if(rs01.next())
								{
									msg = msg + "<BR>Row No: " + rw + " - Remarks ID "+rid+" already entered for the Location: "+wecid.toUpperCase();
								}
								else{
									sqlQuery = SiteUserSQLC.INSERT_UPLOAD_REMARKS;
									PreparedStatement ps1 = conn.prepareStatement(sqlQuery);
									ps1.setObject(1, date);
									ps1.setObject(2, rid);
									ps1.setObject(3, wid);
									ps1.executeUpdate();									
								}
								}
								else{
									msg = msg + "<font class='errormsgtext'><BR>Remarks ID does not exist for ID: "+rid+" </font>";
								}
								if (! ms.equals(""))
								{
									msg = msg + "<BR> Row No: " + rw + " - Remarks : " + ms;
								}							
							}
		        		}
		        		rw = rw + 1;
					}
		        	if(ms.equals(""))
					{
						msg = msg + "<font class='sucessmsgtext'><BR>Upload Successful!</font>";
					}
		        	
        		}        		
        		else
        		{
        			msg = "<font class='errormsgtext'>Format Improper! All Heads are mandatory.</font>";
        		}
        		
			}
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String PublishData(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString();
    		String Optiontxt = dynaBean.getProperty("Optiontxt").toString();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString();	
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		int op = Integer.parseInt(Optiontxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		/*sqlQuery = SiteUserSQLC.GENERATE_EB_DATA;
    		cs = conn.prepareCall(sqlQuery);    			
			cs.setObject(1, SiteIdtxt);
			cs.setObject(2, date);
			cs.setObject(3, UserId);
			cs.setObject(4, op);
			cs.registerOutParameter(5, OracleTypes.CHAR);    			
			cs.executeUpdate();
			msg = cs.getObject(5).toString();
			cs.close();
			if (msg.trim().length() <= 0){*/
	   		
    		if (Typetxt.equals("EB") || Typetxt.equals("BOTH")){
				sqlQuery = SiteUserSQLC.UPDATE_FD_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();

	            sqlQuery = SiteUserSQLC.UPDATE_EB_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();	            
    		}    
    		if (Typetxt.equals("WEC") || Typetxt.equals("BOTH")){
	            sqlQuery = SiteUserSQLC.UPDATE_WEC_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();
    		}
            if (op == 1)
            {
            	msg = "<font class='sucessmsgtext'>Data Publish Successfully!</font>";
            }else if(op == 0)
            {
            	msg = "<font class='sucessmsgtext'>Data Un-Publish Successfully!</font>";
            }
    			
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    public String PublishScadaData(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString();
    		String Optiontxt = dynaBean.getProperty("Optiontxt").toString();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString();	
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		int op = Integer.parseInt(Optiontxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    			   		
    		/*
    		if (Typetxt.equals("EB") || Typetxt.equals("BOTH")){
				sqlQuery = SiteUserSQLC.UPDATE_FD_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();

	            sqlQuery = SiteUserSQLC.UPDATE_EB_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();	            
    		}      
    		 */
    		// if (Typetxt.equals("WEC") || Typetxt.equals("BOTH")){
	            sqlQuery = SiteUserSQLC.UPDATE_SCADA_WEC_PUBLISH;
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, op);
				ps.setObject(2, SiteIdtxt);
				ps.setDate(3, date);
				ps.executeUpdate();
	            ps.close();
    		// }
            if (op == 1)
            {
            	msg = "<font class='sucessmsgtext'>Scada Data Publish Successfully!</font>";
            }else if(op == 0)
            {
            	msg = "<font class='sucessmsgtext'>Scada Data Un-Publish Successfully!</font>";
            }
    			
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String DeleteEBData(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	////JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String EBIdtxt = dynaBean.getProperty("EBIdtxt").toString();
    		String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();
    		String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();
    		String MpIdtxt = dynaBean.getProperty("MpIdtxt").toString();
    		java.util.Date fdt = format.parse(FromDatetxt);
    		java.util.Date tdt = format.parse(ToDatetxt);
    		java.sql.Date fromdate = new java.sql.Date(fdt.getTime());
    		java.sql.Date todate = new java.sql.Date(tdt.getTime());
    		
    		if(MpIdtxt.equals("ALL"))
    		{
				sqlQuery = SiteUserSQLC.DELETE_EB_DATA_1;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, EBIdtxt);
				ps.setDate(2, fromdate);
				ps.setDate(3, todate);
    		}
    		else
    		{
    			sqlQuery = SiteUserSQLC.DELETE_EB_DATA;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, MpIdtxt);
				ps.setObject(2, EBIdtxt);
				ps.setDate(3, fromdate);
				ps.setDate(4, todate);
    		}
			ps.executeUpdate();
            ps.close();

        	msg = "<font class='sucessmsgtext'>EB Data Deleted Successfully!</font>";
        	
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String DeleteFederData(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String FederIdtxt = dynaBean.getProperty("FederIdtxt").toString();
    		String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();
    		String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();
    		String MpIdtxt = dynaBean.getProperty("MpIdtxt").toString();
    		java.util.Date fdt = format.parse(FromDatetxt);
    		java.util.Date tdt = format.parse(ToDatetxt);
    		java.sql.Date fromdate = new java.sql.Date(fdt.getTime());
    		java.sql.Date todate = new java.sql.Date(tdt.getTime());
    		
    		
    		if(MpIdtxt.equals("ALL"))
    		{
    			sqlQuery = SiteUserSQLC.DELETE_FEDER_DATA_1;
    			ps = conn.prepareStatement(sqlQuery);
    			
    			ps.setObject(1, FederIdtxt);
    			ps.setDate(2, fromdate);
    			ps.setDate(3, todate);
    			ps.executeUpdate();
                ps.close();

                sqlQuery = SiteUserSQLC.DELETE_EB_DATA_BY_FEDER_1;
    			ps = conn.prepareStatement(sqlQuery);
    			
    			ps.setDate(1, fromdate);
    			ps.setDate(2, todate);
    			ps.setObject(3, FederIdtxt);
    			ps.executeUpdate();
                ps.close();
    		}
    		else
    		{
			sqlQuery = SiteUserSQLC.DELETE_FEDER_DATA;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, MpIdtxt);
			ps.setObject(2, FederIdtxt);
			ps.setDate(3, fromdate);
			ps.setDate(4, todate);
			ps.executeUpdate();
            ps.close();

            sqlQuery = SiteUserSQLC.DELETE_EB_DATA_BY_FEDER;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, MpIdtxt);
			ps.setDate(2, fromdate);
			ps.setDate(3, todate);
			ps.setObject(4, FederIdtxt);
			ps.executeUpdate();
            ps.close();
    		}
        	msg = "<font class='sucessmsgtext'>Feeder Data Deleted Successfully!</font>";
        	
        }catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String DeleteWECData(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String WECIdtxt = dynaBean.getProperty("WECIdtxt").toString();
    		String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();
    		String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();
    		String MpIdtxt = dynaBean.getProperty("MpIdtxt").toString();
    		java.util.Date fdt = format.parse(FromDatetxt);
    		java.util.Date tdt = format.parse(ToDatetxt);
    		java.sql.Date fromdate = new java.sql.Date(fdt.getTime());
    		java.sql.Date todate = new java.sql.Date(tdt.getTime());
    		
    		if(MpIdtxt.equals("ALL"))
    		{
				sqlQuery = SiteUserSQLC.DELETE_WEC_DATA_1;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, WECIdtxt);
				ps.setDate(2, fromdate);
				ps.setDate(3, todate);
    		}
    		else
    		{
    			sqlQuery = SiteUserSQLC.DELETE_WEC_DATA;
    			ps = conn.prepareStatement(sqlQuery);
    			ps.setObject(1, MpIdtxt);
    			ps.setObject(2, WECIdtxt);
    			ps.setDate(3, fromdate);
    			ps.setDate(4, todate);
    		}
			ps.executeUpdate();
            ps.close();

        	msg = "<font class='sucessmsgtext'>WEC Data Deleted Successfully!</font>";
        	
        }catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String TransferEB(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        String custid="";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String EBFromtxt = dynaBean.getProperty("EBFromtxt").toString();
    		String EBTotxt = dynaBean.getProperty("EBTotxt").toString();
    		//String WECtxt = dynaBean.getProperty("WECtxt").toString();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString();
    		String rem = dynaBean.getProperty("Remarkstxt").toString();
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		
    		sqlQuery = SiteUserSQLC.SELECT_CUSTOMER_DETAIL;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, EBTotxt);
			rs=ps.executeQuery();
			if(rs.next())
			{
				custid=rs.getString("S_CUSTOMER_ID");
			}
			rs.close();
            ps.close();
            
            sqlQuery = SiteUserSQLC.UPDATE_EB_TRANSFER;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, EBTotxt);
			ps.setObject(2, custid);
			ps.setObject(3, EBFromtxt);
			ps.setDate(4, date);
			ps.executeUpdate();
            ps.close();
            
            
            sqlQuery = SiteUserSQLC.UPDATE_EB_MASTER_1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, date);
			ps.setObject(2, EBTotxt);
			ps.setObject(3, rem);
			ps.setObject(4, EBFromtxt);
			ps.executeUpdate();
            ps.close();
            
            
        	msg = "<font class='sucessmsgtext'>WEC Transfered Successfully!</font>";
	        	
        }catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String TransferWEC(String UserId,DynaBean dynaBean) throws Exception{
    	String msg="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sqlQuery = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try{
    		String CustomerFromtxt = dynaBean.getProperty("CustomerFromtxt").toString();
    		String WECtxt = dynaBean.getProperty("WECtxt").toString();
    		String EBtxt = dynaBean.getProperty("EBtxt").toString();
    		String CustomerTotxt = dynaBean.getProperty("CustomerTotxt").toString();
    		String WECTotxt = dynaBean.getProperty("WECTotxt").toString();
    		String EBTotxt = dynaBean.getProperty("EBTotxt").toString();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString();
    		String Remarks = dynaBean.getProperty("Remarkstxt").toString();
    		String ebid="";
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		
    		sqlQuery = SiteUserSQLC.SELECT_EB_DETAIL;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, WECTotxt);
			rs=ps.executeQuery();
			if(rs.next())
			{
				ebid=rs.getString("S_EB_ID");
			}
			rs.close();
            ps.close();
            
            
            
			sqlQuery = SiteUserSQLC.UPDATE_WEC_TRANSFER;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, CustomerTotxt);
			ps.setObject(2, WECTotxt);
			ps.setObject(3, ebid);
			ps.setObject(4, UserId);
			//ps.setObject(4, CustomerFromtxt);
			ps.setObject(5, WECtxt);
			ps.setDate(6, date);
			ps.executeUpdate();
            ps.close();
             
            sqlQuery = SiteUserSQLC.UPDATE_WEC_MASTER_1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, date);
			ps.setObject(2, WECTotxt);
			ps.setObject(3, Remarks);
			ps.setObject(4, WECtxt);
			
			ps.executeUpdate();
            ps.close();
            
        	msg = "<font class='sucessmsgtext'>WEC Transfered Successfully!</font>";
	        	
        }catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String UpdateEBData(String UserId, String Item, int dd, int pub) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String mpid = txtbox.substring(0, 10);
    		String ebid = txtbox.substring(10, 20);
    		String txtvalue=comma[1];
    		if (txtvalue.trim().length() == 0)
    		{
    			txtvalue = "0";
    		}
    		double value = Double.parseDouble(txtvalue);
    		String Datetxt=comma[2];
    		String Typetxt=comma[4];
    		String txtdvalue=comma[3];
    		if (txtdvalue.trim().length() == 0)
    		{
    			txtdvalue = "0";
    		}
    		double dvalue = Double.parseDouble(txtdvalue);
    		//String MpUnittxt=comma[5];
    		//String MacType=comma[6];
    		String MpUnittxt="";
    		String MacType = "";
    		String cumulative = "1";
    		sqlQuery = AdminSQLC.SELECT_MP_MASTER_BY_ID;
    		ps = conn.prepareStatement(sqlQuery);
    		ps.setObject(1,mpid);
    		rs = ps.executeQuery();
    		if(rs.next()){
    			MpUnittxt = rs.getObject("s_mp_unit") == null ? "" : rs.getString("s_mp_unit");	   
    			MacType = rs.getObject("s_mp_type") == null ? "" : rs.getString("s_mp_type");	
    			cumulative = rs.getObject("S_CUMULATIVE") == null ? "1" : rs.getString("S_CUMULATIVE");
    		}
    		rs.close();
    		ps.close();
    		if (MacType.equals("WEC"))
    		{
    			sqlQuery = AdminSQLC.GET_WEC;
    			ps = conn.prepareStatement(sqlQuery);
    			ps.setObject(1, ebid);
    			rs = ps.executeQuery();
    			while (rs.next())
    			{
    				msg = "";
    				sqlQuery = mpid + rs.getString("S_WEC_ID")+","+txtvalue+","+Datetxt+","+txtdvalue+","+Typetxt;
    				
    				msg = UpdateWECData(UserId, sqlQuery,1,1); //earlier it was zero for publish
    				if (msg.trim().length() > 0)
    					break;
    				else
    					continue;
    			}				
    			rs.close();
    			ps.close();
    		}
    		//else if (MacType.equals("EB"))
    		//{
    			if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
	    		{
	    			if ((value * 60) > 1440)
	    			{
	    				msg="more then 24";
	        			exists = false;
	    			}
	    		}
    			if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
        		{
    	    		if ((dvalue * 60) > 1440)
    				{
    					msg="more then 24";
    	    			exists = false;
    				}
        		}
	    		java.util.Date dt = new java.util.Date();
	    		dt = format.parse(Datetxt);
	    		String readid = "";    		
	    		java.sql.Date date = new java.sql.Date(dt.getTime());    		
	    		java.util.Calendar now = java.util.Calendar.getInstance();
				now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
				String pdate= DateFormat.getDateInstance().format(now.getTime());
				String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(fdate);
	            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
	    	    
	            now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
				String ndate= DateFormat.getDateInstance().format(now.getTime());
				String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(tdate);
	            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
	            
	            if (cumulative.equals("1") && dd == 1)
				{
	            	sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
	        		ps = conn.prepareStatement(sqlQuery);
	        		ps.setDate(1,nextdate);
	        		ps.setObject(2,mpid);
	        		ps.setObject(3,ebid);
	        		rs = ps.executeQuery();
	        		if(rs.next()){
	        			if (rs.getDouble("N_VALUE") < value)
	        			{
		        			msg="More then tomorow (" + String.valueOf(rs.getDouble("N_VALUE")) + ")";
		        			exists = false;
	        			}
	        		}
	        		rs.close();
	        		ps.close();
				}
	            
	            if (exists == true && Typetxt.equals("D"))
	            {
	            	if (cumulative.equals("1") && dd == 1)
					{
		            	sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
		        		ps = conn.prepareStatement(sqlQuery);
		        		ps.setDate(1,prevdate);
		        		ps.setObject(2,mpid);
		        		ps.setObject(3,ebid);
		        		rs = ps.executeQuery();
		        		if(! rs.next()){
		        			msg="Yesterdays Data Not Found";
		        			exists = false;
		        		}else if(rs.getDouble("N_VALUE") > value)
		        		{
		        			msg="Should be More or equal";
		        			exists = false;
		        		}
		        		rs.close();
		        		ps.close();
		            }
				}
	            String custid = "";
	            sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,ebid);
				rs = ps.executeQuery();
	    		if(rs.next()){
	    			custid = rs.getObject("S_CUSTOMER_ID") == null ? "" : rs.getObject("S_CUSTOMER_ID").toString();
	    		}
	    		rs.close();
	    		ps.close();
	    		if (exists == true && ! custid.equals(""))
	    		{
	        		sqlQuery = SiteUserSQLC.CHECK_EB_READING;	        		
	        		if (Typetxt.equals("I") || Typetxt.equals("D"))
	        		{
	        			sqlQuery = sqlQuery + " AND S_READING_TYPE IN ('D','I') ";
	        			ps = conn.prepareStatement(sqlQuery);
	        			ps.setDate(1,date);
	            		ps.setObject(2,mpid);
	            		ps.setObject(3,ebid);
	        		}else
	        		{
	        			sqlQuery = sqlQuery + " AND S_READING_TYPE = ? ";
	        			ps = conn.prepareStatement(sqlQuery);
	        			ps.setDate(1,date);
	            		ps.setObject(2,mpid);
	            		ps.setObject(3,ebid);
	            		ps.setObject(4,Typetxt);
	        		}	        		
	        		rs = ps.executeQuery();
	        		if(rs.next()){
	        			readid = rs.getObject("S_READING_ID").toString();
	        			/*if (Typetxt.equals("I") && dvalue <= 0)
	        			{
		        			sqlQuery = SiteUserSQLC.UPDATE_EB_READING_INITIAL;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setDouble(1,value);
		        			ps1.setObject(2,UserId);
		        			ps1.setObject(3,readid);
	        			}
	        			else
	        			{*/
	        			//if (value != 0 || dvalue != 0)
	        			//{
	        				sqlQuery = SiteUserSQLC.UPDATE_EB_READING;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setDouble(1,value);
		        			ps1.setDouble(2,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
		        			ps1.setObject(3,UserId);
		        			ps1.setObject(4,readid);
		        			ps1.executeUpdate();	            		
		            		ps1.close();
	        			//}	            		
	            		//conn.commit();
	        		}else
	        		{
	        			if (value > 0 || dvalue > 0)
	        			{
	        			readid = CodeGenerate.NewCodeGenerate("TBL_EB_READING");
	        			/*if (Typetxt.equals("I") && dvalue <= 0)
	        			{
		        			sqlQuery = SiteUserSQLC.INSERT_EB_READING_INITIAL;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,readid);
		        			ps1.setDate(2,date);
		        			ps1.setObject(3,mpid);
		        			ps1.setObject(4,ebid);
		        			ps1.setObject(5,Typetxt);
		        			ps1.setDouble(6,value);
		        			ps1.setObject(7,UserId);
		        			ps1.setObject(8,UserId);
		        			ps1.setObject(9,""); //remarks
		        			ps1.setObject(10, custid);
	        			}
	        			else
	        			{*/
	        				sqlQuery = SiteUserSQLC.INSERT_EB_READING;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,readid);
		        			ps1.setDate(2,date);
		        			ps1.setObject(3,mpid);
		        			ps1.setObject(4,ebid);
		        			ps1.setObject(5,Typetxt);
		        			ps1.setDouble(6,value);
		        			ps1.setObject(7,UserId);
		        			ps1.setObject(8,UserId);
		        			ps1.setDouble(9,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
		        			ps1.setObject(10,""); //remarks
		        			ps1.setObject(11, custid);
		        			ps1.setObject(12, pub);
	        			//}
	        			ps1.executeUpdate();
	            		ps1.close();
	            		//conn.commit();
	        			}
	        		}
	        		rs.close();
	        		ps.close();
	    		}        		
    		//}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
       
    public String UpdateFDData(String UserId, String Item, int dd) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	PreparedStatement ps2=null;
    	ResultSet rs=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String mpid = txtbox.substring(0, 10);
    		String fdid = txtbox.substring(10, 20);
    		String txtvalue=comma[1];
    		if (txtvalue.trim().length() == 0)
    		{
    			txtvalue = "0";
    		}
    		double value = Double.parseDouble(txtvalue);
    		String Datetxt=comma[2];
    		String Typetxt=comma[4];
    		String txtdvalue=comma[3];
    		if (txtdvalue.trim().length() == 0)
    		{
    			txtdvalue = "0";
    		}
    		double dvalue = Double.parseDouble(txtdvalue);
    		//String MpUnittxt=comma[5];
    		//String MacType=comma[6];
    		String MpUnittxt="";
    		String MacType="";
    		String cumulative = "1";
    		
    		sqlQuery = AdminSQLC.SELECT_MP_MASTER_BY_ID;
    		ps = conn.prepareStatement(sqlQuery);
    		ps.setObject(1,mpid);
    		rs = ps.executeQuery();
    		if(rs.next()){
    			MpUnittxt = rs.getObject("s_mp_unit") == null ? "" : rs.getString("s_mp_unit");	   
    			MacType = rs.getObject("s_mp_type") == null ? "" : rs.getString("s_mp_type");	
    			cumulative = rs.getObject("S_CUMULATIVE") == null ? "1" : rs.getString("S_CUMULATIVE");
    		}
    		rs.close();
    		ps.close();
    		
    		if (MacType.equals("WEC"))
    		{
    			sqlQuery = AdminSQLC.GET_WEC_BY_FEDER;
    			ps = conn.prepareStatement(sqlQuery);
    			ps.setObject(1, fdid);
    			rs = ps.executeQuery();
    			while (rs.next())
    			{
    				msg = "";
    				sqlQuery = mpid + rs.getString("S_WEC_ID")+","+txtvalue+","+Datetxt+","+txtdvalue+","+Typetxt;
    				
    				msg = UpdateWECData(UserId, sqlQuery,1,1); //earlier it was zero for publish
    				if (msg.trim().length() > 0)
    					break;
    				else
    					continue;
    			}				
    			rs.close();
    			ps.close();
    		}
    		//else if (MacType.equals("EB"))
    		//{
    			if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
	    		{
	    			if ((value * 60) > 1440)
	    			{
	    				msg="more then 24";
	        			exists = false;
	    			}
	    		}
    			if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
        		{
    	    		if ((dvalue * 60) > 1440)
    				{
    					msg="more then 24";
    	    			exists = false;
    				}
        		}
	    		java.util.Date dt = new java.util.Date();
	    		dt = format.parse(Datetxt);
	    		String readid = "";
	    		java.sql.Date date = new java.sql.Date(dt.getTime());
	    		
	    		java.util.Calendar now = java.util.Calendar.getInstance();
				now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
				String pdate= DateFormat.getDateInstance().format(now.getTime());
				String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(fdate);
	            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
	    	    
	            now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
				String ndate= DateFormat.getDateInstance().format(now.getTime());
				String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(tdate);
	            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
	            
	            if (cumulative.equals("1") && dd == 1)
				{
		        	sqlQuery = SiteUserSQLC.CHECK_FD_READING_YESTERDAY_BY_MP;
		    		ps = conn.prepareStatement(sqlQuery);
		    		ps.setDate(1,nextdate);
		    		ps.setObject(2,mpid);
		    		ps.setObject(3,fdid);
		    		rs = ps.executeQuery();
		    		if(rs.next()){
		    			if (rs.getDouble("N_VALUE") < value)
		    			{
		        			msg="More then tomorow (" + String.valueOf(rs.getDouble("N_VALUE")) + ")";
		        			exists = false;
		    			}
		    		}
		    		rs.close();
		    		ps.close();
				}
	        	
	            if(exists == true && Typetxt.equals("D"))
	            {
	            	if (cumulative.equals("1") && dd == 1)
	    			{
		        		sqlQuery = SiteUserSQLC.CHECK_FD_READING_YESTERDAY_BY_MP;
		        		ps = conn.prepareStatement(sqlQuery);
		        		ps.setDate(1,prevdate);
		        		ps.setObject(2,mpid);
		        		ps.setObject(3,fdid);
		        		rs = ps.executeQuery();
		        		if(! rs.next()){
		        			msg="Yesterdays Data Not Found";
		        			exists = false;
		        		}else if(rs.getDouble("N_VALUE") > value)
		        		{
		        			msg="Should be More or equal";
		        			exists = false;
		        		}
		        		rs.close();
		        		ps.close();
		            }
				}
	            
	            /*FEDER DISTRIBUTION TO LOGICAL EB WITH WEC DATA
	            if (MpUnittxt.equals("HOURS"))
	            {
	            	sqlQuery = SiteUserSQLC.GET_EB_MASTER_BY_FEDER_ID;
	            	ps = conn.prepareStatement(sqlQuery);
		    		ps.setObject(1,fdid);
		    		rs = ps.executeQuery();
		    		while (rs.next()){
		            	sqlQuery =  mpid+rs.getString("s_eb_id")+","+value+","+Datetxt+","+Typetxt+","+dvalue+","+MpUnittxt+","+MacType;
		            	msg = UpdateEBData(UserId, sqlQuery);
		    		}
		    		rs.close();
		    		ps.close();
	            }
	            else
	            {
	            	double totgen = 0;
	            	double percent = 0;
		            sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER;
		            ps = conn.prepareStatement(sqlQuery);
	        		ps.setDate(1,date);
	        		ps.setObject(2,fdid);
	        		rs = ps.executeQuery();
	        		if(rs.next()){
	        			totgen = rs.getObject("TOTGEN") == null ? 0 : rs.getDouble("TOTGEN");
	        			if (totgen > 0) percent = dvalue / totgen;
	        		}
	        		rs.close();
	        		ps.close();		         
	        		if (percent > 0 && percent < 1 && totgen > 0)
	        		{
		        		sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER_GROUP_EB;
	        			ps = conn.prepareStatement(sqlQuery);
		        		ps.setDate(1,date);
		        		ps.setObject(2,fdid);
		        		rs = ps.executeQuery();
		        		while(rs.next()){
		        			double cumgen = rs.getObject("CUMGEN") == null ? 0 : rs.getDouble("CUMGEN");
		        			double daygen = rs.getObject("TOTGEN") == null ? 0 : rs.getDouble("TOTGEN");
		        			cumgen = percent * cumgen;
		        			daygen = percent * daygen;
		        			sqlQuery =  mpid+rs.getString("s_eb_id")+","+formatter.format(cumgen)+","+Datetxt+","+Typetxt+","+formatter.format(daygen)+","+MpUnittxt+","+MacType;
			            	msg = UpdateEBData(UserId, sqlQuery);
		        		}
		        		rs.close();
		        		ps.close();
	        		}
	            }
	            END FEDER DISTRIBUTION TO EB*/
	            
	    		if (exists == true)
	    		{
	        		sqlQuery = SiteUserSQLC.CHECK_FD_READING;	        		
	        		if (Typetxt.equals("I") || Typetxt.equals("D"))
	        		{
	        			sqlQuery = sqlQuery + " AND S_READING_TYPE NOT IN ('M','Y') ";
	        			ps = conn.prepareStatement(sqlQuery);
	        			ps.setDate(1,date);
	            		ps.setObject(2,mpid);
	            		ps.setObject(3,fdid);
	        		}else
	        		{
	        			sqlQuery = sqlQuery + " AND S_READING_TYPE = ? ";
	        			ps = conn.prepareStatement(sqlQuery);
	        			ps.setDate(1,date);
	            		ps.setObject(2,mpid);
	            		ps.setObject(3,fdid);
	            		ps.setObject(4,Typetxt);
	        		}
	        		rs = ps.executeQuery();
	        		if(rs.next()){
	        			readid = rs.getObject("S_READING_ID").toString();
	        			/*if (Typetxt.equals("I") && dvalue <= 0)
	        			{
	        				sqlQuery = SiteUserSQLC.UPDATE_FD_READING_INITIAL;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setDouble(1,value);
		        			ps1.setObject(2,UserId);
		        			ps1.setObject(3,readid);
	        			}
	        			else
	        			{*/
		        			sqlQuery = SiteUserSQLC.UPDATE_FD_READING;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setDouble(1,value);
		        			ps1.setDouble(2,cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
		        			ps1.setObject(3,UserId);
		        			ps1.setObject(4,readid);
		        			ps1.executeUpdate();
		            		ps1.close();
		        			
	        			//}
		        			
		        			
	        			//ps1.executeUpdate();
	            		//ps1.close();
	        		}
	        		else
	        		{
	        			if (value > 0 || dvalue > 0)
	        			{
	        			readid = CodeGenerate.NewCodeGenerate("TBL_FEDER_READING");
	        			/*if (Typetxt.equals("I") && dvalue <= 0)
	        			{
		        			sqlQuery = SiteUserSQLC.INSERT_FEDER_READING_INITIAL;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,readid);
		        			ps1.setDate(2,date);
		        			ps1.setObject(3,mpid);
		        			ps1.setObject(4,fdid);
		        			ps1.setObject(5,Typetxt);
		        			ps1.setDouble(6,value);
		        			ps1.setObject(7,UserId);
		        			ps1.setObject(8,UserId);
		        			ps1.setObject(9,""); //remarks
		        			
	        			}
	        			else
	        			{*/
		        			sqlQuery = SiteUserSQLC.INSERT_FEDER_READING;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,readid);
		        			ps1.setDate(2,date);
		        			ps1.setObject(3,mpid);
		        			ps1.setObject(4,fdid);
		        			ps1.setObject(5,Typetxt);
		        			ps1.setDouble(6,value);
		        			ps1.setObject(7,UserId);
		        			ps1.setObject(8,UserId);
		        			ps1.setDouble(9,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
		        			ps1.setObject(10,""); //remarks		        			
	        			//}	  
	        			ps1.executeUpdate();
	            		ps1.close();
	        			}
	        		}
	        		rs.close();
	        		ps.close();
	    		}    		
    		//}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps,ps1,ps2) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    
    public String UpdateWECData(String UserId, String Item, int dd,int pub) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
        CallableStatement cs = null;
    	try{
    		/*boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String mpid = txtbox.substring(0, 10).trim();
    		String wecid = txtbox.substring(10, 20).trim();
    		String ebid = "";
    		String custid = "";
    		String txtvalue=comma[1].trim();
    		if (txtvalue.trim().length() == 0)
    		{
    			txtvalue = "0";
    		}
    		double value = Double.parseDouble(txtvalue);
    		String Datetxt=comma[2].trim();
    		String txtdvalue=comma[3].trim();
    		String Typetxt=comma[4].trim();
    		if (txtdvalue.trim().length() == 0)
    		{
    			txtdvalue = "0";
    		}*/
    		
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String mpid = txtbox.substring(0, 10).trim();
    		String wecid = txtbox.substring(10, 20).trim();
    		String txtvalue=comma[1].trim();
    		String Datetxt=comma[2].trim();
    		String txtdvalue=comma[3].trim();
    		String Typetxt=comma[4].trim();
    		
    		sqlQuery = SiteUserSQLC.INSERT_WEC_DATA;
    		
    		cs = conn.prepareCall(sqlQuery);    
    		cs.setObject(1, UserId);
			cs.setObject(2, mpid.trim());
			cs.setObject(3, wecid.trim());
			cs.setObject(4, txtvalue.trim());
			cs.setObject(5, Datetxt.trim());
			cs.setObject(6, txtdvalue.trim());
			cs.setObject(7, Typetxt.trim());
			cs.setObject(8, dd);
			cs.setObject(9, pub);
			cs.registerOutParameter(10, OracleTypes.CHAR);    			
			cs.executeUpdate();
			msg = cs.getObject(10) == null ? "" : cs.getObject(10).toString();
			cs.close();
			
    		/*double dvalue = Double.parseDouble(txtdvalue);
    		String cumulative = "1";
    		String MpUnittxt = "";
    		String MacType = "";
    		
    		sqlQuery = AdminSQLC.SELECT_MP_MASTER_BY_ID;
    		ps = conn.prepareStatement(sqlQuery);
    		ps.setObject(1,mpid);
    		rs = ps.executeQuery();
    		if(rs.next()){
    			MpUnittxt = rs.getObject("s_mp_unit") == null ? "" : rs.getString("s_mp_unit").trim();	   
    			MacType = rs.getObject("s_mp_type") == null ? "" : rs.getString("s_mp_type").trim();	
    			cumulative = rs.getObject("S_CUMULATIVE") == null ? "1" : rs.getString("S_CUMULATIVE").trim();
    		}
    		rs.close();
    		ps.close();
    		
    		if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
    		{
    			if ((value * 60) > 1440)
    			{
    				msg="more then 24";
        			exists = false;
    			}
    		}
    		if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
    		{
	    		if ((dvalue * 60) > 1440)
				{
					msg="more then 24";
	    			exists = false;
				}
    		}
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		String readid = "";        		
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		java.util.Calendar now = java.util.Calendar.getInstance();
			now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
			String pdate= DateFormat.getDateInstance().format(now.getTime());
			String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(fdate);
            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
    	    
            now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
			String ndate= DateFormat.getDateInstance().format(now.getTime());
			String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(tdate);
            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
            
            sqlQuery = AdminSQLC.SEARCH_WEC_ID_DETAILS;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,wecid);
			rs = ps.executeQuery();
    		if(rs.next()){
    			ebid = rs.getObject("s_eb_id").toString();
    			custid = rs.getObject("S_CUSTOMER_ID").toString();
    		}
    		rs.close();
    		ps.close();
    		
            if(exists == true)
            {
    			if (cumulative.equals("1") && dd == 1)
    			{
	        		sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
	        		ps = conn.prepareStatement(sqlQuery);
	        		ps.setDate(1,nextdate);
	        		ps.setObject(2,mpid.trim());
	        		ps.setObject(3,wecid.trim());
	        		rs = ps.executeQuery();
	        		if(rs.next()){
	        			if (rs.getDouble("N_VALUE") < value)
	        			{
		        			msg="More then tomorow (" + String.valueOf(rs.getDouble("N_VALUE")) + ")";
		        			exists = false;
	        			}
	        		}
	        		rs.close();
	        		ps.close();
    			}
            }	
            if(exists == true && Typetxt.equals("D"))
            {
    			if (cumulative.equals("1") && dd == 1)
    			{
	        		sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
	        		ps = conn.prepareStatement(sqlQuery);
	        		ps.setDate(1,prevdate);
	        		ps.setObject(2,mpid.trim());
	        		ps.setObject(3,wecid.trim());
	        		rs = ps.executeQuery();
	        		if(! rs.next()){
	        			msg="Yesterdays Data Not Found";
	        			exists = false;
	        		}else if(rs.getDouble("N_VALUE") > value)
	        		{
	        			msg="Should be More or equal";
	        			exists = false;
	        		}
	        		rs.close();
	        		ps.close();
    			}
            }
            
    		if (exists == true)
    		{
        		sqlQuery = SiteUserSQLC.CHECK_WEC_READING;        		
        		if (Typetxt.equals("I") || Typetxt.equals("D"))
        		{
        			sqlQuery = sqlQuery + " AND S_READING_TYPE NOT IN ('M','Y') ";
        			ps = conn.prepareStatement(sqlQuery);
        			ps.setDate(1,date);
            		ps.setObject(2,mpid);
            		ps.setObject(3,wecid);
        		}else
        		{
        			sqlQuery = sqlQuery + " AND S_READING_TYPE = ? ";
        			ps = conn.prepareStatement(sqlQuery);
        			ps.setDate(1,date);
            		ps.setObject(2,mpid);
            		ps.setObject(3,wecid);
            		ps.setObject(4,Typetxt);
        		}	        		         		
        		rs = ps.executeQuery();
        		if(rs.next()){
        			readid = rs.getObject("S_READING_ID").toString();
    				sqlQuery = SiteUserSQLC.UPDATE_WEC_READING;
        			ps1 = conn.prepareStatement(sqlQuery);
        			ps1.setDouble(1,value);
        			ps1.setDouble(2,cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
        			ps1.setObject(3,UserId);
        			ps1.setObject(4,readid);
        			ps1.executeUpdate();
            		ps1.close();
     			
        		}else
        		{
        			if (value != 0 || dvalue != 0)
        			{
        				if (! ebid.equals("") && ! custid.equals(""))
	        			{
	        				readid = CodeGenerate.NewCodeGenerate("TBL_WEC_READING");
	        				sqlQuery = SiteUserSQLC.INSERT_WEC_READING;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,readid);
		        			ps1.setDate(2,date);
		        			ps1.setObject(3,mpid);
		        			ps1.setObject(4,wecid);
		        			ps1.setObject(5,ebid);
		        			ps1.setObject(6,custid);
		        			ps1.setDouble(7,value);
		        			ps1.setObject(8,UserId);
		        			ps1.setObject(9,UserId);
		        			ps1.setDouble(10,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
		        			ps1.setObject(11,Typetxt);	
		        			ps1.setObject(12,pub);
	        			}	  
	        			ps1.executeUpdate();
	            		ps1.close();
        			}
        		}        				
        		rs.close();
        		ps.close();
    		}   */ 		
    		
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs,rs1) , conn);
        }
    	return msg;
    }
    
    public String showUpdateEBByWec(String UserId, String Item) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        DecimalFormat twodecimal = new DecimalFormat("#########0.00");
        String msg="";
    	try{
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0].trim();
    		String mpid = txtbox.substring(0, 10).trim();
    		String wecid = txtbox.substring(10, 20).trim();
    		String Datetxt=comma[1].trim();
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
            if (mpid.equals("0808000022"))
            {
	            String fdid = "";
	            sqlQuery = SiteUserSQLC.GET_FEDER_BY_WEC;
	            ps = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            ps.setObject(1,wecid);
	            ps.setDate(2,date);
	    		rs = ps.executeQuery();
	    		if(rs.next())
	    		{
	    			fdid = rs.getObject("s_feder_id") == null ? "" : rs.getString("s_feder_id").trim();
	    			double totgen = 0;
	            	double percent = 0;
	            	sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER;
		            ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
	        		ps1.setObject(2,fdid);
	        		rs1 = ps1.executeQuery();
	        		if(rs1.next()){
	        			totgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");	        			
	        		}
	        		rs1.close();
	        		ps1.close();
	    			if (! fdid.equals(""))
	    			{
		        		sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER_GROUP_EB;
	        			ps1 = conn.prepareStatement(sqlQuery);
		        		ps1.setDate(1,date);
		        		ps1.setObject(2,fdid);
		        		rs1 = ps1.executeQuery();
		        		while(rs1.next()){
		        			double cumgen = rs1.getObject("CUMGEN") == null ? 0 : rs1.getDouble("CUMGEN");
		        			double daygen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");
		        			rs.beforeFirst();
		    				while (rs.next()){		    			
		    					if (totgen > 0) percent = rs.getDouble("n_actual_value") / totgen;
				        		if (percent > 0 && percent < 1 && totgen > 0)
				        		{
				        			double cumgen1 = percent * cumgen;
				        			double daygen1 = percent * daygen;
				        			sqlQuery =  rs.getString("s_mp_id").trim()+rs1.getString("s_eb_id").trim()+","+twodecimal.format(cumgen1)+","+Datetxt.trim()+","+twodecimal.format(daygen1)+","+rs.getString("S_READING_TYPE").trim();
					            	msg = UpdateEBData(UserId, sqlQuery,2,1); //earlier it was zero for publish
					            	
				        		}				        		
			        		}
		    			}
		        		rs1.close();
		        		ps1.close();
		    		}
	    		}
	    		rs.close();
	    		ps.close();
            }  		
    		
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs,rs1) , conn);
        }
    	return msg;
    }
    
    public String showUpdateEBByfeder(String UserId, String Item) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        DecimalFormat twodecimal = new DecimalFormat("#########0.00");
        String msg="";
    	try{
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String mpid = txtbox.substring(0, 10).trim();
    		String fdid = txtbox.substring(10, 20).trim();
    		String txtvalue=comma[1].trim();
    		if (txtvalue.trim().length() == 0)
    		{
    			txtvalue = "0";
    		}
    		double value = Double.parseDouble(txtvalue);
    		String Datetxt=comma[2].trim();
    		String Typetxt=comma[3].trim();
    		String txtdvalue=comma[4].trim();
    		if (txtdvalue.trim().length() == 0)
    		{
    			txtdvalue = "0";
    		}
    		double dvalue = Double.parseDouble(txtdvalue);
    		String MpUnittxt="";
    		String MacType="";
    		String cumulative = "1";
    		
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		java.sql.Date prevdate = null;
            
            sqlQuery = AdminSQLC.SELECT_MP_MASTER_BY_ID;
    		ps1 = conn.prepareStatement(sqlQuery);
    		ps1.setObject(1,mpid);
    		rs1 = ps1.executeQuery();
    		if(rs1.next()){
    			MpUnittxt = rs1.getObject("s_mp_unit") == null ? "" : rs1.getString("s_mp_unit").trim();	   
    			MacType = rs1.getObject("s_mp_type") == null ? "" : rs1.getString("s_mp_type").trim();
    			cumulative = rs1.getObject("S_CUMULATIVE") == null ? "1" : rs1.getString("S_CUMULATIVE").trim();
    		}
    		rs1.close();
    		ps1.close();
    		
    		if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
            {
            	sqlQuery = SiteUserSQLC.GET_EB_MASTER_BY_FEDER_ID;
            	ps = conn.prepareStatement(sqlQuery);
	    		ps.setObject(1,fdid);
	    		rs = ps.executeQuery();
	    		while (rs.next()){
	            	sqlQuery =  mpid+rs.getString("s_eb_id")+","+value+","+Datetxt+","+dvalue+","+Typetxt;
	            	msg = UpdateEBData(UserId, sqlQuery,2,1); //earlier it was zero for publish
	    		}
	    		rs.close();
	    		ps.close();
            }
            else
            {	
            	if (! fdid.equals(""))
            	{
	            	double totgen = 0;
	            	double percent = 0;
	            	if (Typetxt.equals("M"))
	            	{
		            	sqlQuery = SiteUserSQLC.GET_FD_MASTER_FOR_READING_MAX_BY_ID;
						ps1 = conn.prepareStatement(sqlQuery);
						ps1.setObject(1,Typetxt);
						ps1.setObject(2,Typetxt);
						ps1.setObject(3,date);
						ps1.setObject(4,Typetxt);
						ps1.setObject(5,fdid);
						rs1 = ps1.executeQuery();
		        		if(rs1.next()){
		        			//dt = format.parse(Datetxt);
		        			prevdate = rs1.getDate("D_READING_DATE");
		        			java.util.Calendar now = java.util.Calendar.getInstance(); 
		        			now.setTime(prevdate);	
		        			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
		        			String pdate= format.format(now.getTime());
		        			java.util.Date dt1 = new java.util.Date();
		        			dt1 = new java.util.Date();
		                    dt1 = format.parse(pdate);
		                    prevdate = new java.sql.Date(dt1.getTime());
		        		}
		        		rs1.close();
		        		ps1.close();
		        		sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER_BY_MONTH;
			            ps1 = conn.prepareStatement(sqlQuery);
			            ps1.setDate(1,prevdate);
		        		ps1.setDate(2,date);
		        		ps1.setObject(3,fdid);
		        		rs1 = ps1.executeQuery();
		        		if(rs1.next()){
		        			totgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");	        			
		        		}
		        		rs1.close();
		        		ps1.close();
		        		sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER_GROUP_EB_MONTH;
		        		ps1 = conn.prepareStatement(sqlQuery);
		        		ps1.setDate(1,prevdate);
		        		ps1.setDate(2,date);
		        		ps1.setObject(3,fdid);
	            	}
	            	else
	            	{
		            	sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER;
			            ps1 = conn.prepareStatement(sqlQuery);
		        		ps1.setDate(1,date);
		        		ps1.setObject(2,fdid);
		        		rs1 = ps1.executeQuery();
		        		if(rs1.next()){
		        			totgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");	        			
		        		}
		        		rs1.close();
		        		ps1.close();
		        		sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER_GROUP_EB;
		        		ps1 = conn.prepareStatement(sqlQuery);
		        		ps1.setDate(1,date);
		        		ps1.setObject(2,fdid);
	            	}	
	        		rs1 = ps1.executeQuery();
	        		while(rs1.next()){
	        			double cumgen = rs1.getObject("CUMGEN") == null ? 0 : rs1.getDouble("CUMGEN");
	        			double daygen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");
    					if (totgen > 0) percent = dvalue / totgen;
		        		//if (percent > 0 && totgen > 0)
		        		//{
		        			double cumgen1 = percent * cumgen;
		        			double daygen1 = percent * daygen;
		        			sqlQuery =  mpid+rs1.getString("s_eb_id")+","+twodecimal.format(cumgen1)+","+Datetxt+","+twodecimal.format(daygen1)+","+Typetxt;
			            	msg = UpdateEBData(UserId, sqlQuery,2,1); //earlier it was zero for publish
		        		//}				        		
	    			}
	        		rs1.close();
	        		ps1.close();
	    		}
            }
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs,rs1) , conn);
        }
    	return msg;
    }
    
    
    public String NewEBDataEntry(String UserId,DynaBean dynaBean) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	PreparedStatement ps2=null;
    	PreparedStatement ps3=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	ResultSet rs2=null;
    	ResultSet rs3=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString().trim();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString().trim();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString().trim();
    		if(Typetxt.equals("MI"))
    		{
    			Typetxt="M";
    		}
    		String Starttxt = dynaBean.getProperty("Starttxt").toString().trim();	
    		String Endtxt = dynaBean.getProperty("Endtxt").toString().trim();
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
   		
    		java.sql.Date date = new java.sql.Date(dt.getTime());    		
    		java.util.Calendar now = java.util.Calendar.getInstance();
			now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
			String pdate= DateFormat.getDateInstance().format(now.getTime());
			String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(fdate);
            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
    	    
            now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
			String ndate= DateFormat.getDateInstance().format(now.getTime());
			String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(tdate);
            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
            
    		sqlQuery = AdminSQLC.SELECT_MP_BY_READ_TYPE_FOR_WEC;
    		ps1 = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ps1.setObject(1,"EB");
    		ps1.setObject(2,Typetxt);
    		rs1 = ps1.executeQuery();
    		if(rs1.next())
    		{
	    		sqlQuery = AdminSQLC.SELECT_PAGED_EB_MASTER;
	    		ps = conn.prepareStatement(sqlQuery);
	    		ps.setObject(1,SiteIdtxt);
	    		ps.setObject(2,date);
	    		ps.setObject(3,Endtxt);
	    		ps.setObject(4,Starttxt);
				rs = ps.executeQuery();
				while(rs.next())
				{
					String ebid = rs.getString("s_eb_id").trim();
					String custid = "";
					sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_ID;
					ps2 = conn.prepareStatement(sqlQuery);
					ps2.setObject(1,ebid);
					rs2 = ps2.executeQuery();
		    		if(rs2.next()){
		    			custid = rs2.getObject("S_CUSTOMER_ID") == null ? "" : rs2.getObject("S_CUSTOMER_ID").toString();
		    		}
		    		rs2.close();
		    		ps2.close();
		    		
					rs1.beforeFirst();
					while(rs1.next())
					{
						if (rs1.getObject("s_status").equals("1"))
						{
							boolean exists = true;
							String MpUnittxt = rs1.getObject("s_mp_unit") == null ? "" : rs1.getString("s_mp_unit").trim();	   
							String MacType = rs1.getObject("s_mp_type") == null ? "" : rs1.getString("s_mp_type").trim();	
							String cumulative = rs1.getObject("S_CUMULATIVE") == null ? "1" : rs1.getString("S_CUMULATIVE").trim();
							String MpDesc = rs1.getObject("S_MP_DESCR") == null ? "" : rs1.getString("S_MP_DESCR");
			    			
			    			String mpid = rs1.getObject("s_mp_id").toString().trim();
			    			
			    			String txtvalue = dynaBean.getProperty(mpid + "" + ebid) == null ? "0" : dynaBean.getProperty(mpid + "" + ebid).toString();
			    			String txtdvalue = dynaBean.getProperty(mpid + "" + ebid + "D") == null ? "0" : dynaBean.getProperty(mpid + "" + ebid + "D").toString();
			    			double value = txtvalue.trim().length() > 0 ? Double.parseDouble(txtvalue) : 0;
			    			double dvalue = txtdvalue.trim().length() > 0 ? Double.parseDouble(txtdvalue) : 0;
			    			
			    			if (MacType.equals("WEC"))
			        		{
			        			sqlQuery = AdminSQLC.GET_WEC;
			        			ps2 = conn.prepareStatement(sqlQuery);
			        			ps2.setObject(1, ebid);
			        			rs2 = ps2.executeQuery();
			        			while (rs2.next())
			        			{
			        				String msg1 = "";
			        				sqlQuery = mpid.trim() + rs2.getString("S_WEC_ID").trim()+","+txtvalue.trim()+","+Datetxt.trim()+","+txtdvalue.trim()+","+Typetxt.trim();
			        				
			        				msg1 = UpdateWECData(UserId, sqlQuery,1,1); //earlier it was zero for publish
			        				if (msg1.trim().length() > 0)
			        					break;
			        				else
			        					continue;
			        			}				
			        			rs2.close();
			        			ps2.close();
			        		}
			    			if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
				    		{
				    			if ((value * 60) > 1440)
				    			{
				    				msg=msg+" " + MpDesc + " Cumulative for " + rs.getString("S_EB_DESCRIPTION") + " is more then 24 Hours<br>";
				        			exists = false;
				    			}
				    		}
			    			if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
			        		{
			    	    		if ((dvalue * 60) > 1440)
			    				{
			    	    			msg=msg+" " + MpDesc + " daily for " + rs.getString("S_EB_DESCRIPTION") + " is more then 24 Hours<br>";
			    	    			exists = false;
			    				}
			        		}
			    			if (cumulative.equals("1") && !Typetxt.equals("M") && !Typetxt.equals("Y"))
							{
				            	sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
				        		ps2 = conn.prepareStatement(sqlQuery);
				        		ps2.setDate(1,nextdate);
				        		ps2.setObject(2,mpid);
				        		ps2.setObject(3,ebid);
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			if (rs2.getDouble("N_VALUE") < value)
				        			{
				        				msg=msg+" " + MpDesc + " for " + rs.getString("S_EB_DESCRIPTION") + " is more then tomorrow i.e. " + String.valueOf(rs2.getDouble("N_VALUE")) + "<br>";
					        			exists = false;
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
							}
			    			else if (Typetxt.equals("M") || Typetxt.equals("Y"))
			    			{
			    				
			    			}
			    			if (Typetxt.equals("D")) // || Typetxt.equals("I")
				            {
				            	if (cumulative.equals("1"))
								{
					            	sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
					        		ps2 = conn.prepareStatement(sqlQuery);
					        		ps2.setDate(1,prevdate);
					        		ps2.setObject(2,mpid);
					        		ps2.setObject(3,ebid);
					        		rs2 = ps2.executeQuery();
					        		if(! rs2.next()){
					        			msg=msg+" Yesterdays data of " + MpDesc + " for " + rs.getString("S_EB_DESCRIPTION") + " not found<br>";
					        			exists = false;
					        		}else if(rs2.getDouble("N_VALUE") > value)
					        		{
					        			msg=msg+" " + MpDesc + " for " + rs.getString("S_EB_DESCRIPTION") + " should be More or equal<br>";
					        			exists = false;
					        		}
					        		rs2.close();
					        		ps2.close();
					            }
							}
			    			
			    			///
			    			if (exists == true && ! custid.equals(""))
				    		{
			    	    		String readid = "";
				        		sqlQuery = SiteUserSQLC.CHECK_EB_READING;				        		
				        		if (Typetxt.equals("I") || Typetxt.equals("D"))
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE IN ('D','I') ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,ebid);
				        		}else
				        		{
				        			sqlQuery = SiteUserSQLC.CHECK_EB_READING_OF_MONTH;	
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				        			ps2.setDate(2,date);
				            		ps2.setObject(3,mpid);
				            		ps2.setObject(4,ebid);
				            		
				        		}	        		
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			//if (value != 0 || dvalue != 0)
				        			//{
					        			readid = rs2.getObject("S_READING_ID").toString();
				        				sqlQuery = SiteUserSQLC.UPDATE_EB_READING;
					        			ps3 = conn.prepareStatement(sqlQuery);
					        			ps3.setDouble(1,value);
					        			ps3.setDouble(2,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
					        			ps3.setObject(3,UserId);
					        			ps3.setObject(4,readid);
					            		ps3.executeUpdate();	            		
					            		ps3.close();
				        			//}
				        		}else
				        		{
				        			if (value > 0 || dvalue > 0)
				        			{
					        			readid = CodeGenerate.NewCodeGenerate("TBL_EB_READING");
				        				sqlQuery = SiteUserSQLC.INSERT_EB_READING;
				        				ps3 = conn.prepareStatement(sqlQuery);
				        				ps3.setObject(1,readid);
				        				ps3.setDate(2,date);
				        				ps3.setObject(3,mpid);
				        				ps3.setObject(4,ebid);
				        				ps3.setObject(5,Typetxt);
				        				ps3.setDouble(6,value);
				        				ps3.setObject(7,UserId);
				        				ps3.setObject(8,UserId);
				        				ps3.setDouble(9,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
				        				ps3.setObject(10,""); //remarks
				        				ps3.setObject(11, custid);
				        				ps3.setObject(12, 0);
				        				ps3.executeUpdate();
				        				ps3.close();
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
				    		}
						}
					}
					String remarks = dynaBean.getProperty(ebid + "R") == null ? "" : dynaBean.getProperty(ebid + "R").toString();					
					msg = msg + UpdateEBemarks(UserId, ebid+","+remarks+","+Datetxt.trim()+","+Typetxt.trim());
				}
				rs.close();
				ps.close();
    		}
    		rs1.close();
    		ps1.close();
    		if (msg.equals(""))
    		{
    			msg = "<font class='sucessmsgtext'>Records Update Successfully</font>";
    		}
    		else
    		{
    			msg = "<font class='errormsgtext'>" + msg + "</font>";
    		}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps,ps2,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
        }
    	return msg;
    }
    
    public String NewFDDataEntry(String UserId,DynaBean dynaBean) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	PreparedStatement ps2=null;
    	PreparedStatement ps3=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	ResultSet rs2=null;
    	ResultSet rs3=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString().trim();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString().trim();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString().trim();
    		if(Typetxt.equals("MI"))
    		{
    			Typetxt="M";
    		}
    		String Starttxt = dynaBean.getProperty("Starttxt").toString().trim();	
    		String Endtxt = dynaBean.getProperty("Endtxt").toString().trim();
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
   		
    		java.sql.Date date = new java.sql.Date(dt.getTime());   
    		java.sql.Date date1 = new java.sql.Date(dt.getTime()+1);
    		java.util.Calendar now = java.util.Calendar.getInstance();
			now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
			String pdate= DateFormat.getDateInstance().format(now.getTime());
			String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(fdate);
            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
    	    
            now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH+1));
			String ndate= DateFormat.getDateInstance().format(now.getTime());
			String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(tdate);
            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
            
            sqlQuery = AdminSQLC.SELECT_MP_BY_READ_TYPE_FOR_WEC;
    		ps1 = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ps1.setObject(1,"EB");
    		ps1.setObject(2,Typetxt);
    		rs1 = ps1.executeQuery();
    		if(rs1.next())
    		{
    			sqlQuery = AdminSQLC.SELECT_PAGED_FD_MASTER;
	    		ps = conn.prepareStatement(sqlQuery);
	    		ps.setObject(1,SiteIdtxt);
	    		ps.setObject(2,date);
	    		ps.setObject(3,Endtxt);
	    		ps.setObject(4,Starttxt);
				rs = ps.executeQuery();
				while(rs.next())
				{
					String fdid = rs.getString("S_FEDER_ID").trim();
					rs1.beforeFirst();
					while(rs1.next())
					{
						if (rs1.getObject("s_status").equals("1"))
						{
							boolean exists = true;
							String MpUnittxt = rs1.getObject("s_mp_unit") == null ? "" : rs1.getString("s_mp_unit").trim();	   
							String MacType = rs1.getObject("s_mp_type") == null ? "" : rs1.getString("s_mp_type").trim();	
							String cumulative = rs1.getObject("S_CUMULATIVE") == null ? "1" : rs1.getString("S_CUMULATIVE").trim();
							String MpDesc = rs1.getObject("S_MP_DESCR") == null ? "" : rs1.getString("S_MP_DESCR");
			    			
			    			String mpid = rs1.getObject("s_mp_id").toString().trim();
			    			
			    			String txtvalue = dynaBean.getProperty(mpid + "" + fdid) == null ? "0" : dynaBean.getProperty(mpid + "" + fdid).toString();
			    			String txtdvalue = dynaBean.getProperty(mpid + "" + fdid + "D") == null ? "0" : dynaBean.getProperty(mpid + "" + fdid + "D").toString();
			    			double value = txtvalue.trim().length() > 0 ? Double.parseDouble(txtvalue) : 0;
			    			double dvalue = txtdvalue.trim().length() > 0 ? Double.parseDouble(txtdvalue) : 0;
			    			
			    			if (MacType.equals("WEC"))
			        		{
			        			sqlQuery = AdminSQLC.GET_WEC_BY_FEDER;
			        			ps2 = conn.prepareStatement(sqlQuery);
			        			ps2.setObject(1, fdid);
			        			rs2 = ps2.executeQuery();
			        			while (rs2.next())
			        			{
			        				String msg1 = "";
			        				sqlQuery = mpid + rs2.getString("S_WEC_ID").trim()+","+txtvalue+","+Datetxt.trim()+","+txtdvalue+","+Typetxt.trim();
			        				
			        				msg1 = UpdateWECData(UserId, sqlQuery,1,1); //earlier it was zero for publish
			        				if (msg1.trim().length() > 0)
			        					break;
			        				else
			        					continue;
			        			}				
			        			rs2.close();
			        			ps2.close();
			        		}
			    			if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
				    		{
				    			if ((value * 60) > 1440)
				    			{
				        			msg=msg+" " + MpDesc + " Cumulative for " + rs.getString("S_FEDER_DESCRIPTION") + " is more than 24 Hours<br>";
				        			exists = false;
				    			}
				    		}
			    			if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
			        		{
			    	    		if ((dvalue * 60) > 1440)
			    				{
			    	    			msg=msg+" " + MpDesc + " daily for " + rs.getString("S_FEDER_DESCRIPTION") + " is more than 24 Hours<br>";
			    	    			exists = false;
			    				}
			        		}
			    			if (cumulative.equals("1") && !Typetxt.equals("M") && !Typetxt.equals("Y"))
							{
					        	sqlQuery = SiteUserSQLC.CHECK_FD_READING_YESTERDAY_BY_MP;
					    		ps2 = conn.prepareStatement(sqlQuery);
					    		ps2.setDate(1,nextdate);
					    		ps2.setObject(2,mpid);
					    		ps2.setObject(3,fdid);
					    		rs2 = ps2.executeQuery();
					    		if(rs2.next()){
					    			if (rs2.getDouble("N_VALUE") < value)
					    			{
					    				msg=msg+" " + MpDesc + " for " + rs.getString("S_FEDER_DESCRIPTION") + " is more then tomorrow i.e. " + String.valueOf(rs2.getDouble("N_VALUE")) + "<br>";
					        			exists = false;
					    			}
					    		}
					    		rs2.close();
					    		ps2.close();
							}
			    			if(Typetxt.equals("D"))
				            {
				            	if (cumulative.equals("1"))
				    			{
					        		sqlQuery = SiteUserSQLC.CHECK_FD_READING_YESTERDAY_BY_MP;
					        		ps2 = conn.prepareStatement(sqlQuery);
					        		ps2.setDate(1,prevdate);
					        		ps2.setObject(2,mpid);
					        		ps2.setObject(3,fdid);
					        		rs2 = ps2.executeQuery();
					        		if(! rs2.next()){
					        			msg=msg+" Yesterdays data of " + MpDesc + " for " + rs.getString("S_FEDER_DESCRIPTION") + " not found<br>";
					        			exists = false;
					        		}else if(rs2.getDouble("N_VALUE") > value)
					        		{
					        			msg=msg+" " + MpDesc + " for " + rs.getString("S_FEDER_DESCRIPTION") + " should be More or equal<br>";
					        			exists = false;
					        		}
					        		rs2.close();
					        		ps2.close();
					            }
							}
			    			if (exists == true)
				    		{
			    				String readid = "";
				        		sqlQuery = SiteUserSQLC.CHECK_FD_READING;				        		
				        		if (Typetxt.equals("I") || Typetxt.equals("D"))
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE IN ('D','I') ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setObject(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,fdid);
				        		}else
				        		{
				        			sqlQuery = SiteUserSQLC.CHECK_FD_READING_OF_MONTH;
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,date);
				            		ps2.setObject(3,mpid);
				            		ps2.setObject(4,fdid);
				        		}
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			readid = rs2.getObject("S_READING_ID").toString();
				        			/*sqlQuery = SiteUserSQLC.UPDATE_FD_READING;
				        			ps3 = conn.prepareStatement(sqlQuery);
				        			ps3.setDouble(1,value);
				        			ps3.setDouble(2,cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
				        			ps3.setObject(3,UserId);
				        			ps3.setObject(4,readid);
				        			ps3.executeUpdate();
				            		ps3.close();*/
				        			double hrs=cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue;
				        			sqlQuery="UPDATE TBL_FEDER_READING SET N_VALUE="+value+" ,N_ACTUAL_VALUE="+hrs+",S_LAST_MODIFIED_BY='"+UserId+"',D_LAST_MODIFIED_DATE=sysdate " +
				        				    	" WHERE S_READING_ID = '"+readid+"'";
				        			
				        			//Update  Query For FD Reading//
				        			
				        			String sqlQuery1 = SiteUserSQLC.INSERT_UPDATE_QUERY;
				        			PreparedStatement ps4 = conn.prepareStatement(sqlQuery1);
				        			ps4.setObject(1,sqlQuery);
				        			ps4.setObject(2,"FEEDER");
				        			ps4.setObject(3,fdid);
				        			ps4.setObject(4,UserId);
				        			ps4.executeUpdate();
				        			
				            		ps4.close();
				            		
				            		
				        			//End of update data query for FD reading??
				        			
				        			//ps3.executeUpdate();
				            		//ps3.close();
				        		}
				        		else
				        		{
				        			readid = CodeGenerate.NewCodeGenerate("TBL_FEDER_READING");
				        			sqlQuery = SiteUserSQLC.INSERT_FEDER_READING;
				        			ps3 = conn.prepareStatement(sqlQuery);
				        			ps3.setObject(1,readid);
				        			ps3.setDate(2,date);
				        			ps3.setObject(3,mpid);
				        			ps3.setObject(4,fdid);
				        			ps3.setObject(5,Typetxt);
				        			ps3.setDouble(6,value);
				        			ps3.setObject(7,UserId);
				        			ps3.setObject(8,UserId);
				        			ps3.setDouble(9,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
				        			ps3.setObject(10,""); //remarks		        			
				        			ps3.executeUpdate();
				            		ps3.close();
				        		}
				        		rs2.close();
				        		ps2.close();
				    		} 
			    			String ApplicationId =  mpid.trim() + "" + fdid.trim() + "," + value + "," + Datetxt.trim() + "," + Typetxt.trim()  + "," +dvalue;
			    			msg=msg+showUpdateEBByfeder(UserId, ApplicationId);
						}
					}
					String remarks = dynaBean.getProperty(fdid + "R") == null ? "" : dynaBean.getProperty(fdid + "R").toString();
					msg = msg + UpdateFDemarks(UserId, fdid+","+remarks+","+Datetxt.trim()+","+Typetxt.trim());
					
					
				}
				rs.close();
				ps.close();
    		}
    		rs1.close();
    		ps1.close();
    		if (msg.equals(""))
    		{
    			msg = "<font class='sucessmsgtext'>Records Update Successfully</font>";
    		}
    		else
    		{
    			msg = "<font class='errormsgtext'>" + msg + "</font>";
    		}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps,ps2,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
        }
    	return msg;
    }
    
    public String NewWECDataEntry(String UserId,DynaBean dynaBean) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	PreparedStatement ps2=null;
    	PreparedStatement ps3=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	ResultSet rs2=null;
    	ResultSet rs3=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString().trim();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString().trim();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString().trim();
    		String Starttxt = dynaBean.getProperty("Starttxt").toString().trim();	
    		String Endtxt = dynaBean.getProperty("Endtxt").toString().trim();
    		if(Typetxt.equals("MI"))
    		{
    			Typetxt="M";
    		}
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
   		
    		java.sql.Date date = new java.sql.Date(dt.getTime());    		
    		java.util.Calendar now = java.util.Calendar.getInstance();
			now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
			String pdate= DateFormat.getDateInstance().format(now.getTime());
			String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(fdate);
            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
    	    
            now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
			String ndate= DateFormat.getDateInstance().format(now.getTime());
			String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(tdate);
            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
            
            sqlQuery = AdminSQLC.SELECT_MP_BY_READ_TYPE_FOR_WEC;
    		ps1 = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ps1.setObject(1,"WEC");
    		ps1.setObject(2,Typetxt);
    		rs1 = ps1.executeQuery();
    		
    		if(rs1.next())
    		{
    			sqlQuery = AdminSQLC.SELECT_PAGED_WEC_MASTER;
	    		ps = conn.prepareStatement(sqlQuery);
	    		ps.setObject(1,SiteIdtxt);
	    		ps.setDate(2,date);
	    		ps.setObject(3,Endtxt);
	    		ps.setObject(4,Starttxt);
				rs = ps.executeQuery();
				while(rs.next())
				{
					String custid = rs.getString("s_customer_id").trim();
					String ebid = rs.getString("s_eb_id").trim();
					String wecid = rs.getString("s_wec_id").trim();
					/*sqlQuery = AdminSQLC.SEARCH_WEC_ID_DETAILS;
					ps2 = conn.prepareStatement(sqlQuery);
					ps2.setObject(1,wecid);
					rs2 = ps2.executeQuery();
		    		if(rs.next()){
		    			ebid = rs2.getObject("s_eb_id").toString().trim();
		    			custid = rs2.getObject("S_CUSTOMER_ID").toString().trim();
		    		}
		    		rs.close();
		    		ps2.close();*/
		    		
		    		rs1.beforeFirst();
					while(rs1.next())
					{
						if (rs1.getObject("s_status").equals("1"))
						{
							boolean exists = true;
							String MpUnittxt = rs1.getObject("s_mp_unit") == null ? "" : rs1.getString("s_mp_unit").trim();	   
							String MacType = rs1.getObject("s_mp_type") == null ? "" : rs1.getString("s_mp_type").trim();	
							String cumulative = rs1.getObject("S_CUMULATIVE") == null ? "1" : rs1.getString("S_CUMULATIVE").trim();
							String MpDesc = rs1.getObject("S_MP_DESCR") == null ? "" : rs1.getString("S_MP_DESCR");
			    			
			    			String mpid = rs1.getObject("s_mp_id").toString().trim();
			    			
			    			String txtvalue = dynaBean.getProperty(mpid + "" + wecid) == null ? "0" : dynaBean.getProperty(mpid + "" + wecid).toString();
			    			String txtdvalue = dynaBean.getProperty(mpid + "" + wecid + "D") == null ? "0" : dynaBean.getProperty(mpid + "" + wecid + "D").toString();
			    			double value = txtvalue.trim().length() > 0 ? Double.parseDouble(txtvalue) : 0;
			    			double dvalue = txtdvalue.trim().length() > 0 ? Double.parseDouble(txtdvalue) : 0;
    		
				    		if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
				    		{
				    			if ((value * 60) > 1440)
				    			{
				    				msg=msg+" " + MpDesc + " Cumulative for " + rs.getString("S_WECSHORT_DESCR") + " is more then 24 Hours<br>";
				        			exists = false;
				    			}
				    		}
				    		if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
				    		{
					    		if ((dvalue * 60) > 1440)
								{
					    			msg=msg+" " + MpDesc + " daily for " + rs.getString("S_WECSHORT_DESCR") + " is more than 24 Hours<br>";
			    	    			exists = false;
								}
				    		}
				    		if (cumulative.equals("1"))
							{
				    			sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
				        		ps2 = conn.prepareStatement(sqlQuery);
				        		ps2.setDate(1,nextdate);
				        		ps2.setObject(2,mpid.trim());
				        		ps2.setObject(3,wecid.trim());
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			if (rs2.getDouble("N_VALUE") < value)
				        			{
				        				msg=msg+" " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " is more than tomorrow i.e. " + String.valueOf(rs2.getDouble("N_VALUE")) + "<br>";
					        			exists = false;
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
							}
				    		if (Typetxt.equals("D"))
				            {
				            	if (cumulative.equals("1"))
								{
					            	sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
					        		ps2 = conn.prepareStatement(sqlQuery);
					        		ps2.setDate(1,prevdate);
					        		ps2.setObject(2,mpid.trim());
					        		ps2.setObject(3,wecid.trim());
					        		rs2 = ps2.executeQuery();
					        		if(! rs2.next()){
					        			msg=msg+" Yesterdays data of " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " not found<br>";
					        			exists = false;
					        		}else if(rs2.getDouble("N_VALUE") > value)
					        		{
					        			msg=msg+" " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " should be More or equal<br>";
					        			exists = false;
					        		}
					        		rs2.close();
					        		ps2.close();
					            }
							}
				    		if (exists == true && ! custid.equals("") && ! ebid.equals(""))
				    		{
			    	    		String readid = "";
				    			sqlQuery = SiteUserSQLC.CHECK_WEC_READING;				        		
				        		if (Typetxt.equals("I") || Typetxt.equals("D"))
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE IN ('D','I') ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,wecid);
				        		}else
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE = ? ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,wecid);
				            		ps2.setObject(4,Typetxt);
				        		}	        		         		
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			//if (value != 0 || dvalue != 0)
				        			//{
				        			// commented the update process as per telecon with mt on 4-feb-2012
					        			/*readid = rs2.getObject("S_READING_ID").toString();				        			
				        				sqlQuery = SiteUserSQLC.UPDATE_WEC_READING;
				            			ps3 = conn.prepareStatement(sqlQuery);
				            			ps3.setDouble(1,value);
				            			ps3.setDouble(2,cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
				            			ps3.setObject(3,UserId);
				            			ps3.setObject(4,readid);
					        			ps3.executeUpdate();
					            		ps3.close();*/
				        			//}
				        		}else
				        		{
				        			if (value > 0 || dvalue > 0)
				        			{
				        				readid = CodeGenerate.NewCodeGenerate("TBL_WEC_READING");
				        				sqlQuery = SiteUserSQLC.INSERT_WEC_READING;
					        			ps3 = conn.prepareStatement(sqlQuery);
					        			ps3.setObject(1,readid);
					        			ps3.setDate(2,date);
					        			ps3.setObject(3,mpid);
					        			ps3.setObject(4,wecid);
					        			ps3.setObject(5,ebid);
					        			ps3.setObject(6,custid);
					        			ps3.setDouble(7,value);
					        			ps3.setObject(8,UserId);
					        			ps3.setObject(9,UserId);
					        			ps3.setDouble(10,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
					        			ps3.setObject(11,Typetxt);	  
					        			ps3.setObject(12,1);
					        			ps3.executeUpdate();
					            		ps3.close();
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
				    		}
				    		if (mpid.equals("0808000022"))
							{
				    			msg=msg+showUpdateEBByWec(UserId, mpid+""+wecid+","+Datetxt);
							}
						}
						
					}
					String remarks = dynaBean.getProperty(wecid + "R") == null ? "" : dynaBean.getProperty(wecid + "R").toString();					
					msg = msg + UpdateWECRemarks(UserId, wecid+","+remarks+","+Datetxt.trim()+","+Typetxt.trim());
        		}        				
        		rs.close();
        		ps.close();
    		} 
    		rs1.close();
    		ps1.close();
    		if (msg.equals(""))
    		{
    			msg = "<font class='sucessmsgtext'>Records Update Successfully.. Please Publish the data!</font>";
    		}
    		else
    		{
    			msg = "<font class='errormsgtext'>" + msg + "</font>";
    		}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps,ps2,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
        }
    	return msg;
    }
    public String NewWECDataEntry_user(String UserId,DynaBean dynaBean) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	PreparedStatement ps2=null;
    	PreparedStatement ps3=null;
    	ResultSet rs=null;
    	ResultSet rs1=null;
    	ResultSet rs2=null;
    	ResultSet rs3=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat formatter = new DecimalFormat("#########0.00");
    	AdminDao ad = new AdminDao();
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		String SiteIdtxt = dynaBean.getProperty("SiteIdtxt").toString().trim();
    		String Datetxt = dynaBean.getProperty("Datetxt").toString().trim();
    		String Typetxt = dynaBean.getProperty("Typetxt").toString().trim();
    		String Starttxt = dynaBean.getProperty("Starttxt").toString().trim();	
    		String Endtxt = dynaBean.getProperty("Endtxt").toString().trim();
    		if(Typetxt.equals("MI"))
    		{
    			Typetxt="M";
    		}
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
   		
    		java.sql.Date date = new java.sql.Date(dt.getTime());    		
    		java.util.Calendar now = java.util.Calendar.getInstance();
			now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
			String pdate= DateFormat.getDateInstance().format(now.getTime());
			String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(fdate);
            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
    	    
            now.setTime(date);		
			now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)+1);
			String ndate= DateFormat.getDateInstance().format(now.getTime());
			String tdate=ad.convertDateFormat(ndate,"MMM dd,yyyy","dd/MM/yyyy");
            dt = format.parse(tdate);
            java.sql.Date nextdate = new java.sql.Date(dt.getTime());
            
            sqlQuery = AdminSQLC.SELECT_MP_BY_READ_TYPE_FOR_WEC;
    		ps1 = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		ps1.setObject(1,"WEC");
    		ps1.setObject(2,Typetxt);
    		rs1 = ps1.executeQuery();
    		
    		if(rs1.next())
    		{
    			sqlQuery = AdminSQLC.SELECT_PAGED_WEC_MASTER;
	    		ps = conn.prepareStatement(sqlQuery);
	    		ps.setObject(1,SiteIdtxt);
	    		ps.setDate(2,date);
	    		ps.setObject(3,Endtxt);
	    		ps.setObject(4,Starttxt);
				rs = ps.executeQuery();
				while(rs.next())
				{
					String custid = rs.getString("s_customer_id").trim();
					String ebid = rs.getString("s_eb_id").trim();
					String wecid = rs.getString("s_wec_id").trim();
					/*sqlQuery = AdminSQLC.SEARCH_WEC_ID_DETAILS;
					ps2 = conn.prepareStatement(sqlQuery);
					ps2.setObject(1,wecid);
					rs2 = ps2.executeQuery();
		    		if(rs.next()){
		    			ebid = rs2.getObject("s_eb_id").toString().trim();
		    			custid = rs2.getObject("S_CUSTOMER_ID").toString().trim();
		    		}
		    		rs.close();
		    		ps2.close();*/
		    		
		    		rs1.beforeFirst();
					while(rs1.next())
					{
						if (rs1.getObject("s_status").equals("1"))
						{
							boolean exists = true;
							String MpUnittxt = rs1.getObject("s_mp_unit") == null ? "" : rs1.getString("s_mp_unit").trim();	   
							String MacType = rs1.getObject("s_mp_type") == null ? "" : rs1.getString("s_mp_type").trim();	
							String cumulative = rs1.getObject("S_CUMULATIVE") == null ? "1" : rs1.getString("S_CUMULATIVE").trim();
							String MpDesc = rs1.getObject("S_MP_DESCR") == null ? "" : rs1.getString("S_MP_DESCR");
			    			
			    			String mpid = rs1.getObject("s_mp_id").toString().trim();
			    			
			    			String txtvalue = dynaBean.getProperty(mpid + "" + wecid) == null ? "0" : dynaBean.getProperty(mpid + "" + wecid).toString();
			    			String txtdvalue = dynaBean.getProperty(mpid + "" + wecid + "D") == null ? "0" : dynaBean.getProperty(mpid + "" + wecid + "D").toString();
			    			double value = txtvalue.trim().length() > 0 ? Double.parseDouble(txtvalue) : 0;
			    			double dvalue = txtdvalue.trim().length() > 0 ? Double.parseDouble(txtdvalue) : 0;
    		
				    		if (MpUnittxt.equals("HOURS") && cumulative.equals("2"))
				    		{
				    			if ((value * 60) > 1440)
				    			{
				    				msg=msg+" " + MpDesc + " Cumulative for " + rs.getString("S_WECSHORT_DESCR") + " is more then 24 Hours<br>";
				        			exists = false;
				    			}
				    		}
				    		if (MpUnittxt.equals("HOURS") && cumulative.equals("1"))
				    		{
					    		if ((dvalue * 60) > 1440)
								{
					    			msg=msg+" " + MpDesc + " daily for " + rs.getString("S_WECSHORT_DESCR") + " is more than 24 Hours<br>";
			    	    			exists = false;
								}
				    		}
				    		if (cumulative.equals("1"))
							{
				    			sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
				        		ps2 = conn.prepareStatement(sqlQuery);
				        		ps2.setDate(1,nextdate);
				        		ps2.setObject(2,mpid.trim());
				        		ps2.setObject(3,wecid.trim());
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			if (rs2.getDouble("N_VALUE") < value)
				        			{
				        				msg=msg+" " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " is more than tomorrow i.e. " + String.valueOf(rs2.getDouble("N_VALUE")) + "<br>";
					        			exists = false;
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
							}
				    		if (Typetxt.equals("D"))
				            {
				            	if (cumulative.equals("1"))
								{
					            	sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
					        		ps2 = conn.prepareStatement(sqlQuery);
					        		ps2.setDate(1,prevdate);
					        		ps2.setObject(2,mpid.trim());
					        		ps2.setObject(3,wecid.trim());
					        		rs2 = ps2.executeQuery();
					        		if(! rs2.next()){
					        			msg=msg+" Yesterdays data of " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " not found<br>";
					        			exists = false;
					        		}else if(rs2.getDouble("N_VALUE") > value)
					        		{
					        			msg=msg+" " + MpDesc + " for " + rs.getString("S_WECSHORT_DESCR") + " should be More or equal<br>";
					        			exists = false;
					        		}
					        		rs2.close();
					        		ps2.close();
					            }
							}
				    		if (exists == true && ! custid.equals("") && ! ebid.equals(""))
				    		{
			    	    		String readid = "";
				    			sqlQuery = SiteUserSQLC.CHECK_WEC_READING;				        		
				        		if (Typetxt.equals("I") || Typetxt.equals("D"))
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE IN ('D','I') ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,wecid);
				        		}else
				        		{
				        			sqlQuery = sqlQuery + " AND S_READING_TYPE = ? ";
				        			ps2 = conn.prepareStatement(sqlQuery);
				        			ps2.setDate(1,date);
				            		ps2.setObject(2,mpid);
				            		ps2.setObject(3,wecid);
				            		ps2.setObject(4,Typetxt);
				        		}	        		         		
				        		rs2 = ps2.executeQuery();
				        		if(rs2.next()){
				        			//if (value != 0 || dvalue != 0)
				        			//{
					        			/*readid = rs2.getObject("S_READING_ID").toString();				        			
				        				sqlQuery = SiteUserSQLC.UPDATE_WEC_READING;
				            			ps3 = conn.prepareStatement(sqlQuery);
				            			ps3.setDouble(1,value);
				            			ps3.setDouble(2,cumulative.equals("2") ? value : MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
				            			ps3.setObject(3,UserId);
				            			ps3.setObject(4,readid);
					        			ps3.executeUpdate();
					            		ps3.close();*/
				        			//}
				        		}else
				        		{
				        			if (value > 0 || dvalue > 0)
				        			{
				        				readid = CodeGenerate.NewCodeGenerate("TBL_WEC_READING");
				        				sqlQuery = SiteUserSQLC.INSERT_WEC_READING;
					        			ps3 = conn.prepareStatement(sqlQuery);
					        			ps3.setObject(1,readid);
					        			ps3.setDate(2,date);
					        			ps3.setObject(3,mpid);
					        			ps3.setObject(4,wecid);
					        			ps3.setObject(5,ebid);
					        			ps3.setObject(6,custid);
					        			ps3.setDouble(7,value);
					        			ps3.setObject(8,UserId);
					        			ps3.setObject(9,UserId);
					        			ps3.setDouble(10,cumulative.equals("2") ? value :  MpUnittxt.equals("HOURS") && dvalue > 24 ? 24 : dvalue);
					        			ps3.setObject(11,Typetxt);	  
					        			ps3.setObject(12,0);
					        			ps3.executeUpdate();
					            		ps3.close();
				        			}
				        		}
				        		rs2.close();
				        		ps2.close();
				    		}
				    		if (mpid.equals("0808000022"))
							{
				    			msg=msg+showUpdateEBByWec(UserId, mpid+""+wecid+","+Datetxt);
							}
						}
						
					}
					String remarks = dynaBean.getProperty(wecid + "R") == null ? "" : dynaBean.getProperty(wecid + "R").toString();					
					msg = msg + UpdateWECRemarks(UserId, wecid+","+remarks+","+Datetxt.trim()+","+Typetxt.trim());
        		}        				
        		rs.close();
        		ps.close();
    		} 
    		rs1.close();
    		ps1.close();
    		if (msg.equals(""))
    		{
    			msg = "<font class='sucessmsgtext'>Records Update Successfully.. Please Publish the data!</font>";
    		}
    		else
    		{
    			msg = "<font class='errormsgtext'>" + msg + "</font>";
    		}
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps,ps2,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
        }
    	return msg;
    }
    
    public String UpdateWECRemarks(String UserId, String Item) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String wecid = txtbox.substring(0, 10);
    		String txtRemarks=comma[1];
    		String Datetxt=comma[2];
    		String Typetxt=comma[3];
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		String readid = "";
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		if (exists == true)
    		{
        		sqlQuery = SiteUserSQLC.CHECK_WEC_READING_FOR_REMARKS;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,date);
        		ps.setObject(2,wecid);
        		//ps.setObject(3,Typetxt);
        		ps.setObject(3,Typetxt.equals("D") || Typetxt.equals("I") ? "D" : "M");
        		ps.setObject(4,Typetxt.equals("D") || Typetxt.equals("I") ? "I" : "Y");
        		rs = ps.executeQuery();
        		if(rs.next()){
        			readid = rs.getObject("S_READING_ID").toString();
        			sqlQuery = SiteUserSQLC.UPDATE_WEC_REMARKS;
        			ps1 = conn.prepareStatement(sqlQuery);
        			ps1.setObject(1,txtRemarks);
        			ps1.setObject(2,UserId);
        			ps1.setObject(3,readid);
            		ps1.executeUpdate();
            		ps1.close();
        		}else
        		{
        			msg = "No Single entry found for remarks to update";
        		}
        		rs.close();
        		ps.close();
    		} 
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String UpdateWECTechRemarks(String UserId, String Item) throws Exception{
	PreparedStatement ps=null;
	PreparedStatement ps1=null;
	ResultSet rs=null;
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	String sqlQuery="";
	//JDBCUtils conmanager = new //JDBCUtils();
    Connection conn = wcareConnector.getConnectionFromPool();
    String msg="";
	try{
		boolean exists = true;
		String s=Item;
		String comma[]=s.split(",");
		String txtbox=comma[0];
		String wecid = txtbox.substring(0, 10);
		String txtRemarks=comma[1];
		String Datetxt=comma[2];
		//String Typetxt=comma[3];
		java.util.Date dt = new java.util.Date();
		dt = format.parse(Datetxt);
		String readid = "";
		java.sql.Date date = new java.sql.Date(dt.getTime());
		
		if (exists == true)
		{
    		sqlQuery = SiteUserSQLC.CHECK_WEC_READING_FOR_TECH_REMARKS;
    		ps = conn.prepareStatement(sqlQuery);
    		ps.setDate(1,date);
    		ps.setObject(2,wecid);
    		//ps.setObject(3,Typetxt);
    		//ps.setObject(3,Typetxt.equals("D") || Typetxt.equals("I") ? "D" : "M");
    		//ps.setObject(4,Typetxt.equals("D") || Typetxt.equals("I") ? "I" : "Y");
    		rs = ps.executeQuery();
    		if(rs.next()){
    			readid = rs.getObject("S_READING_ID").toString();
    			sqlQuery = SiteUserSQLC.UPDATE_WEC_REMARKS;
    			ps1 = conn.prepareStatement(sqlQuery);
    			ps1.setObject(1,txtRemarks);
    			ps1.setObject(2,UserId);
    			ps1.setObject(3,readid);
        		ps1.executeUpdate();
        		ps1.close();
    		}else
    		{
    			msg = "No Single entry found for remarks to update";
    		}
    		rs.close();
    		ps.close();
		} 
	}catch (SQLException sqlExp) {
        logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
        Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
        throw exp;
    } finally {
    	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs) , conn);
    }
	return msg;
}
    
    
    public String UpdateFDemarks(String UserId, String Item) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String fdid = txtbox.substring(0, 10);
    		String txtRemarks=comma[1];
    		String Datetxt=comma[2];
    		String Typetxt=comma[3];
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		String readid = "";
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		sqlQuery = SiteUserSQLC.GET_EB_MASTER_BY_FEDER_ID;
    		ps = conn.prepareStatement(sqlQuery);
    		ps.setObject(1,fdid);
    		rs = ps.executeQuery();
    		while (rs.next())
    		{
    			sqlQuery = rs.getString("s_eb_id") + "," + txtRemarks + "," + Datetxt + "," + Typetxt;
    			msg = UpdateEBemarks(UserId, sqlQuery); 
    		}
    		rs.close();
    		ps.close();
    		if (exists == true)
    		{
        		sqlQuery = SiteUserSQLC.CHECK_FD_READING_FOR_REMARKS;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,date);
        		ps.setObject(2,fdid);
        		ps.setObject(3,Typetxt.equals("D") || Typetxt.equals("I") ? "D" : "M");
        		ps.setObject(4,Typetxt.equals("D") || Typetxt.equals("I") ? "I" : "Y");
        		rs = ps.executeQuery();
        		if(rs.next()){
        			readid = rs.getObject("S_READING_ID").toString();
        			sqlQuery = SiteUserSQLC.UPDATE_FEDER_REMARKS;
        			ps1 = conn.prepareStatement(sqlQuery);
        			ps1.setObject(1,txtRemarks);
        			ps1.setObject(2,UserId);
        			ps1.setObject(3,readid);
            		ps1.executeUpdate();
            		ps1.close();            		
            		
            		//sqlQuery
        		}else
        		{
        			msg = "No Single entry found for remarks to update";
        		}
        		rs.close();
        		ps.close();
    		}     		
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    public String UpdateEBemarks(String UserId, String Item) throws Exception{
    	PreparedStatement ps=null;
    	PreparedStatement ps1=null;
    	ResultSet rs=null;
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    	String sqlQuery="";
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        String msg="";
    	try{
    		boolean exists = true;
    		String s=Item;
    		String comma[]=s.split(",");
    		String txtbox=comma[0];
    		String ebid = txtbox.substring(0, 10);
    		String txtRemarks=comma[1];
    		String Datetxt=comma[2];
    		String Typetxt=comma[3];
    		java.util.Date dt = new java.util.Date();
    		dt = format.parse(Datetxt);
    		String readid = "";
    		java.sql.Date date = new java.sql.Date(dt.getTime());
    		
    		if (exists == true)
    		{
        		sqlQuery = SiteUserSQLC.CHECK_EB_READING_FOR_REMARKS;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,date);
        		ps.setObject(2,ebid);
        		//ps.setObject(3,Typetxt);
        		ps.setObject(3,Typetxt.equals("D") || Typetxt.equals("I") ? "D" : "M");
        		ps.setObject(4,Typetxt.equals("D") || Typetxt.equals("I") ? "I" : "Y");
        		rs = ps.executeQuery();
        		if(rs.next()){
        			readid = rs.getObject("S_READING_ID").toString();
        			sqlQuery = SiteUserSQLC.UPDATE_EB_REMARKS;
        			ps1 = conn.prepareStatement(sqlQuery);
        			ps1.setObject(1,txtRemarks);
        			ps1.setObject(2,UserId);
        			ps1.setObject(3,readid);
            		ps1.executeUpdate();
            		ps1.close();
        		}else
        		{
        			msg = "No Single entry found for remarks to update";
        		}
        		rs.close();
        		ps.close();
    		}  
    	}catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(ps1,ps) , Arrays.asList(rs) , conn);
        }
    	return msg;
    }
    
    
    public String getAjaxDetails(String item,String action,String UserId) throws Exception{
    	logger.debug("Ajax Call(item):::" + item);
		logger.debug("Ajax Call(action):::" + action);
		logger.debug("Ajax Call(userid):::" + UserId);
    	StringBuffer xml = new StringBuffer();
    	//JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String sqlQuery = "";
        // String sqlQuery1 = "";
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        // DecimalFormat formatter = new DecimalFormat("#########0");
        DecimalFormat threedecimal = new DecimalFormat("#########0.###");
        // DecimalFormat twodecimal = new DecimalFormat("#########0.00");
        AdminDao ad = new AdminDao();
        try{
        	if(action.equals("SU_findEBData"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Typetxt=comma[2];
        		String Starttxt = comma[3];
        		String Endtxt = comma[4];
        		String aDatetxt=comma[5];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		java.util.Date adt = new java.util.Date();
        		adt = format.parse(aDatetxt);
        		java.sql.Date adate = new java.sql.Date(adt.getTime());
        		
	            sqlQuery = SiteUserSQLC.GET_EB_MASTER_FOR_READING;
	           
	            sqlQuery = sqlQuery + SiteUserSQLC.GET_EB_MASTER_FOR_READING_PAGED_PART_2;
	            sqlQuery = sqlQuery + "ORDER BY B.S_REMARKS DESC,C.N_SEQ_NO";
	            
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				ps.setObject(3,Typetxt);
				
					ps.setObject(4,SiteIdtxt);
					ps.setObject(5,adate);
					ps.setObject(6,Endtxt);
					ps.setObject(7,Starttxt);
				
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<nvalue>");
					xml.append(rs.getObject("N_VALUE"));
					xml.append("</nvalue>\n");
					xml.append("<dvalue>");
					xml.append(rs.getObject("N_ACTUAL_VALUE"));
					xml.append("</dvalue>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_REMARKS") == null ? "." : rs.getObject("S_REMARKS").toString().replace("&", "And"));
					xml.append("</remarks>\n");
					xml.append("</ebcode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(".");
					xml.append("</ebid>\n");
					xml.append("<mpid>");
					xml.append(".");
					xml.append("</mpid>\n");
					xml.append("</ebcode>\n");
				}
			    xml.append("</ebmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findEBDataMax"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Typetxt=comma[2];
        		String Starttxt = comma[3];
        		String Endtxt = comma[4];
        		String aDatetxt=comma[5];
        		String type=comma[6];
        		String amn=aDatetxt.substring(3,5);
        		String ayr=aDatetxt.substring(6,10);
        		int yr=0;
        		int mn=0;
        		if(type.equals("MI"))
        			{
        				type="M";
        			}
        		if(Typetxt.equals("MI"))
    			{
        			Typetxt="M";
    			}
        		if(type.equals("P"))
        		{
        		if(amn.equals("01"))
        		{
        			yr=Integer.parseInt(ayr)-1;
        			mn=12;
        		}
        		else
        		{
        			
        			mn=Integer.parseInt(amn)-1;
        			yr=Integer.parseInt(ayr);
        		}
        		}
        		else
        		{
        			yr=Integer.parseInt(ayr);
        			mn=Integer.parseInt(amn);
        		}
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		// java.sql.Date date = new java.sql.Date(dt.getTime());
        		java.util.Date adt = new java.util.Date();
        		adt = format.parse(aDatetxt);
        		java.sql.Date adate = new java.sql.Date(adt.getTime());
        		
	            sqlQuery = SiteUserSQLC.GET_EB_MASTER_FOR_READING_MAX;
	            
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,Typetxt);
				ps.setObject(3,Typetxt);
				ps.setObject(4,mn);
				ps.setObject(5,yr);
				ps.setObject(6,Typetxt);
				ps.setObject(7,SiteIdtxt);
				ps.setObject(8,adate);
				ps.setObject(9,Endtxt);
				ps.setObject(10,Starttxt);
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<nvalue>");
					xml.append(rs.getObject("N_VALUE"));
					xml.append("</nvalue>\n");
					xml.append("<dvalue>");
					xml.append(rs.getObject("N_ACTUAL_VALUE"));
					xml.append("</dvalue>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_REMARKS") == null ? "." : rs.getObject("S_REMARKS").toString().replace("&", "And"));
					xml.append("</remarks>\n");
					xml.append("</ebcode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(".");
					xml.append("</ebid>\n");
					xml.append("<mpid>");
					xml.append(".");
					xml.append("</mpid>\n");
					xml.append("</ebcode>\n");
				}
			    xml.append("</ebmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findLL"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Starttxt=comma[2];
        		String Endtxt=comma[3];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		
        		sqlQuery = SiteUserSQLC.GET_EB_KWH_EXP;
        		sqlQuery = sqlQuery + SiteUserSQLC.GET_EB_MASTER_FOR_READING_PAGED_PART_2;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				ps.setObject(3,SiteIdtxt);
				ps.setObject(4,date);
        		ps.setObject(5,Endtxt);
        		ps.setObject(6,Starttxt);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lineloss generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){
					double ebexp = rs.getObject("N_ACTUAL_VALUE") == null ? 0 : rs.getDouble("N_ACTUAL_VALUE"); 
					double wecgen = 0;
					double ll = 0;
					sqlQuery = SiteUserSQLC.GET_WEC_GENERATION;
					ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
					ps1.setObject(2,rs.getObject("s_eb_id"));
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						wecgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");
					}
					rs1.close();
					ps1.close();
					xml.append("<ll>");
					xml.append("<lvalue>");
					if (wecgen <= 0 || ebexp <= 0)
					{
						xml.append("NA");						
					}
					else
					{
						ll = ((wecgen - ebexp)/wecgen) * 100;
						//if (ll < 0)
						//{
						//	xml.append("NA");
						//}
						//else
						//{
							xml.append(threedecimal.format(ll));
						//}
					}
					xml.append("</lvalue>\n");
					xml.append("<ebid>");
					xml.append(rs.getObject("s_eb_id"));
					xml.append("</ebid>\n");
					xml.append("</ll>\n");
				}
				rs.close();
				ps.close();
				xml.append("</lineloss>\n");	
        	}
        	else if(action.equals("SU_findFDLL"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Starttxt=comma[2];
        		String Endtxt=comma[3];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		
        		sqlQuery = SiteUserSQLC.GET_FEDER_KWH_EXP;
        		sqlQuery = sqlQuery + SiteUserSQLC.GET_FEDER_MASTER_FOR_READING_PAGED_PART_2;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				ps.setObject(3,SiteIdtxt);
				ps.setObject(4,date);
        		ps.setObject(5,Endtxt);
        		ps.setObject(6,Starttxt);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lineloss generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){
					double ebexp = rs.getObject("N_ACTUAL_VALUE") == null ? 0 : rs.getDouble("N_ACTUAL_VALUE"); 
					double wecgen = 0;
					double ll = 0;
					sqlQuery = SiteUserSQLC.GET_WEC_GENERATION_BY_FEDER;
					ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
					ps1.setObject(2,rs.getObject("S_FEDER_ID"));
					rs1 = ps1.executeQuery();
					if (rs1.next())
					{
						wecgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");
					}
					rs1.close();
					ps1.close();
					xml.append("<ll>");
					xml.append("<lvalue>");
					if (wecgen <= 0 || ebexp <= 0)
					{
						xml.append("NA");						
					}
					else
					{
						ll = ((wecgen - ebexp)/wecgen) * 100;
						//if (ll < 0)
						//{
						//	xml.append("NA");
						//}
						//else
						//{
							xml.append(threedecimal.format(ll));
						//}												
					}
					xml.append("</lvalue>\n");
					xml.append("<fdid>");
					xml.append(rs.getObject("s_FEDER_id"));
					xml.append("</fdid>\n");
					xml.append("</ll>\n");
				}
				rs.close();
				ps.close();
				xml.append("</lineloss>\n");	
        	}
        	else if(action.equals("SU_findWECMaster"))
        	{
        		sqlQuery = SiteUserSQLC.GET_WEC_BY_CUSTOMER_ID;
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</wecname>\n");
					xml.append("</weccode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append("</wecid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findEBMasterByCust"))
        	{
        		sqlQuery = SiteUserSQLC.GET_EB_BY_CUSTOMER_ID;
        		
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				DaoUtility.displayQueryWithParameter(231, sqlQuery, item);
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecname>");
					xml.append(rs.getObject("S_EBSHORT_DESCR"));
					xml.append("</wecname>\n");
					xml.append("</weccode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append("</wecid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findEBMasterByCust_1"))
        	{
        		sqlQuery = SiteUserSQLC.GET_EB_BY_CUSTOMER_ID_1;
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				int i = 0;
				rs = ps.executeQuery();
				DaoUtility.displayQueryWithParameter(233, sqlQuery, item);
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					DaoUtility.getRowCount(233, rs);
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecname>");
					xml.append(rs.getObject("S_EBSHORT_DESCR"));
					xml.append("</wecname>\n");
					xml.append("</weccode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append("</wecid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findWECMasterByEB"))
        	{
        		sqlQuery = SiteUserSQLC.GET_WEC_BY_EB_ID;
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				int i = 0;
				DaoUtility.displayQueryWithParameter(232, sqlQuery, item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</wecname>\n");
					xml.append("</weccode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append("</wecid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findWECMasterToByEB"))
        	{
        		sqlQuery = SiteUserSQLC.GET_WEC_TO_BY_EB_ID;
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				//ps.setObject(2,item);
				int i = 0;
				DaoUtility.displayQueryWithParameter(234, sqlQuery, item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					DaoUtility.getRowCount(234, rs);
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</wecname>\n");
					xml.append("</weccode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append("</wecid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_showUpdateEBByfeder"))
        	{
        		String msg=showUpdateEBByfeder(UserId, item);
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<updateeb generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<ebcode>");
				xml.append("<msg>");
				xml.append(msg == null || msg.equals("") ? "." : msg);
				xml.append("</msg>\n");
				xml.append("</ebcode>\n");
				xml.append("</updateeb>\n");	
        	}
        	else if(action.equals("SU_showUpdateEBByWec"))
        	{
        		String msg=showUpdateEBByWec(UserId, item);
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<updateeb generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<ebcode>");
				xml.append("<msg>");
				xml.append(msg == null || msg.equals("") ? "." : msg);
				xml.append("</msg>\n");
				xml.append("</ebcode>\n");
				xml.append("</updateeb>\n");	
        	}
        	else if(action.equals("SU_findWECLL"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Starttxt=comma[2];
        		String Endtxt=comma[3];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		//
        		sqlQuery = AdminSQLC.SELECT_PAGED_WEC_MASTER;
        		prepStmt = conn.prepareStatement(sqlQuery);
        		prepStmt.setObject(1, SiteIdtxt);
        		prepStmt.setDate(2,date);
        		prepStmt.setObject(3,Endtxt);
        		prepStmt.setObject(4,Starttxt);
				rs2 = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lineloss generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs2.next())
				{
					sqlQuery = SiteUserSQLC.GET_EB_KWH_EXP_BY_EB;
	        		ps = conn.prepareStatement(sqlQuery);
	        		ps.setObject(1,rs2.getObject("s_eb_id"));
					ps.setObject(2,date);
					rs = ps.executeQuery();
					while(rs.next()){
						double ebexp = rs.getObject("N_ACTUAL_VALUE") == null ? 0 : rs.getDouble("N_ACTUAL_VALUE"); 
						double wecgen = 0;
						double ll = 0;
						sqlQuery = SiteUserSQLC.GET_WEC_GENERATION;
						ps1 = conn.prepareStatement(sqlQuery);
		        		ps1.setDate(1,date);
						ps1.setObject(2,rs.getObject("s_eb_id"));
						rs1 = ps1.executeQuery();
						if (rs1.next())
						{
							wecgen = rs1.getObject("TOTGEN") == null ? 0 : rs1.getDouble("TOTGEN");
						}
						rs1.close();
						ps1.close();
						xml.append("<ll>");
						xml.append("<lvalue>");
						if (wecgen <= 0 || ebexp <= 0)
						{
							xml.append("NA");						
						}
						else
						{
							ll = ((wecgen - ebexp)/wecgen) * 100;
							//if (ll < 0)
							//{
							//	xml.append("NA");
							//}
							//else
							//{
								xml.append(threedecimal.format(ll));
							//}								
						}
						xml.append("</lvalue>\n");
						xml.append("<wecid>");
						xml.append(rs2.getObject("s_wec_id"));
						xml.append("</wecid>\n");
						xml.append("</ll>\n");
					}
					rs.close();
					ps.close();
				}
				rs2.close();
				prepStmt.close();
				xml.append("</lineloss>\n");	
        	}
        	else if(action.equals("SU_findFDData"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Typetxt=comma[2];
        		String Starttxt = comma[3];
        		String Endtxt = comma[4];
        		String aDatetxt=comma[5];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		java.util.Date adt = new java.util.Date();
        		adt = format.parse(aDatetxt);
        		java.sql.Date adate = new java.sql.Date(adt.getTime());
        		
	            sqlQuery = SiteUserSQLC.GET_FD_MASTER_FOR_READING;
	            if (! Typetxt.equals("."))
	            {
	            	sqlQuery = sqlQuery + "AND B.S_READING_TYPE IN(?,'I')";
	            }
	            sqlQuery = sqlQuery + SiteUserSQLC.GET_FEDER_MASTER_FOR_READING_PAGED_PART_2;
	            sqlQuery = sqlQuery + " ORDER BY B.S_REMARKS DESC,C.N_SEQ_NO";
	            
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				ps.setObject(3,Typetxt);
				if (! Typetxt.equals("."))
	            {
					ps.setObject(4,"D");
					ps.setObject(5,SiteIdtxt);
					ps.setObject(6,adate);
					ps.setObject(7,Endtxt);
					ps.setObject(8,Starttxt);
	            }
				else
				{
					ps.setObject(4,SiteIdtxt);
					ps.setObject(5,adate);
					ps.setObject(6,Endtxt);
					ps.setObject(7,Starttxt);
				}
        					
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_feder_ID"));
					xml.append("</fdid>\n");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<nvalue>");
					xml.append(rs.getObject("N_VALUE"));
					xml.append("</nvalue>\n");
					xml.append("<dvalue>");
					xml.append(rs.getObject("N_ACTUAL_VALUE"));
					xml.append("</dvalue>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_REMARKS") == null ? "." : rs.getObject("S_REMARKS").toString().replace("&", "And"));
					xml.append("</remarks>\n");
					xml.append("</fdcode>\n");
					i = 1;
		       }
				if (i == 0)
				{
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(".");
					xml.append("</fdid>\n");
					xml.append("<mpid>");
					xml.append(".");
					xml.append("</mpid>\n");
					xml.append("</fdcode>\n");
				}
			    xml.append("</fdmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findFDDataMax"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Typetxt=comma[2];
        		String Starttxt = comma[3];
        		String Endtxt = comma[4];
        		String aDatetxt=comma[5];
        		String type=comma[6];
        		String amn=aDatetxt.substring(3,5);
        		String ayr=aDatetxt.substring(6,10);
        		int yr=0;
        		int mn=0;
        		if(type.equals("P"))
        		{
        		if(amn.equals("01"))
        		{
        			yr=Integer.parseInt(ayr)-1;
        			mn=12;
        		}
        		else
        		{
        			
        			mn=Integer.parseInt(amn)-1;
        			yr=Integer.parseInt(ayr);
        		}
        		}
        		else
        		{
        			yr=Integer.parseInt(ayr);
        			mn=Integer.parseInt(amn);
        		}
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		java.util.Date adt = new java.util.Date();
        		adt = format.parse(aDatetxt);
        		java.sql.Date adate = new java.sql.Date(adt.getTime());
        		
        		sqlQuery = SiteUserSQLC.GET_FD_MASTER_FOR_READING_MAX;
	            
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1,Typetxt);
				ps.setObject(2,SiteIdtxt);
				ps.setObject(3,Typetxt);
				ps.setObject(4,mn);
				ps.setObject(5,yr);
				ps.setObject(6,Typetxt);
				ps.setObject(7,SiteIdtxt);
				ps.setObject(8,adate);
				ps.setObject(9,Endtxt);
				ps.setObject(10,Starttxt);
        					
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_feder_ID"));
					xml.append("</fdid>\n");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<nvalue>");
					xml.append(rs.getObject("N_VALUE"));
					xml.append("</nvalue>\n");
					xml.append("<dvalue>");
					xml.append(rs.getObject("N_ACTUAL_VALUE"));
					xml.append("</dvalue>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_REMARKS") == null ? "." : rs.getObject("S_REMARKS").toString().replace("&", "And"));
					xml.append("</remarks>\n");
					xml.append("</fdcode>\n");
					i = 1;
		        }
				if (i == 0)
				{
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(".");
					xml.append("</fdid>\n");
					xml.append("<mpid>");
					xml.append(".");
					xml.append("</mpid>\n");
					xml.append("</fdcode>\n");
				}
			    xml.append("</fdmaster>\n");	
			    rs.close();
			    ps.close();
        	}
        	else if(action.equals("SU_findWECData"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String SiteIdtxt=comma[0];
        		String Datetxt=comma[1];
        		String Typetxt=comma[2];
        		String Starttxt = comma[3];
        		String Endtxt = comma[4];
        		String ADatetxt = comma[5];
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		dt = format.parse(ADatetxt);
        		java.sql.Date adate = new java.sql.Date(dt.getTime());
	            sqlQuery = SiteUserSQLC.GET_WEC_MASTER_FOR_READING;
	            
	            
	            sqlQuery = sqlQuery + SiteUserSQLC.GET_WEC_MASTER_FOR_READING_PAGED_PART_2;
	            sqlQuery = sqlQuery + " ORDER BY B.S_REMARKS DESC,A.S_WECSHORT_DESCR";
	            
        		ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				ps.setObject(3,Typetxt);
			//	ps.setObject(4,Typetxt);
				//if (! Typetxt.equals("."))
	        //    {
					
				//	ps.setObject(5,adate);
				//	ps.setObject(6,SiteIdtxt);
				//	ps.setObject(7,Endtxt);
				//	ps.setObject(8,Starttxt);
	           // }
			//	else
			//	{
					ps.setObject(4,adate);
					ps.setObject(5,SiteIdtxt);
					ps.setObject(6,Endtxt);
					ps.setObject(7,Starttxt);
			//	}
				int i = 0;
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){					
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<nvalue>");
					xml.append(rs.getObject("N_VALUE"));
					xml.append("</nvalue>\n");
					xml.append("<dvalue>");
					xml.append(rs.getObject("N_ACTUAL_VALUE"));
					xml.append("</dvalue>\n");
					xml.append("<mpunit>");
					xml.append(rs.getObject("S_MP_UNIT"));
					xml.append("</mpunit>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_REMARKS") == null ? "." : rs.getObject("S_REMARKS").toString().replace("&", "And"));
					xml.append("</remarks>\n");
					xml.append("</weccode>\n");
					i = 1;
		       }
				if (i == 0)
				{
					xml.append("<weccode>");
					xml.append("<wecid>");
					xml.append(".");
					xml.append("</wecid>\n");
					xml.append("<mpid>");
					xml.append(".");
					xml.append("</mpid>\n");
					xml.append("</weccode>\n");
				}
			    xml.append("</wecmaster>\n");	
			    rs.close();
			    ps.close();
        	}        	
        	else if(action.equals("SU_UpdateFDStat"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String status=comma[0];
        		String fdid=comma[1];
        		int apps = 0;
        		if(status == null || status.equals("false")){
        			apps = 1;
        		}
        		else if(status.equals("true")){
        			apps = 2;
        		}
        		sqlQuery = SiteUserSQLC.UPDATE_FD_STATUS;
    			ps1 = conn.prepareStatement(sqlQuery);
    			ps1.setInt(1,apps);
    			ps1.setObject(2,UserId);
    			ps1.setObject(3,fdid);
        		ps1.executeUpdate();
        		ps1.close();
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
        		xml.append("</fdmaster>\n");
        	}
        	else if(action.equals("SU_UpdateEBStat"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String status=comma[0];
        		String ebid=comma[1];
        		int apps = 0;
        		if(status == null || status.equals("false")){
        			apps = 1;
        		}
        		else if(status.equals("true")){
        			apps = 2;
        		}
        		sqlQuery = SiteUserSQLC.UPDATE_EB_STATUS;
    			ps1 = conn.prepareStatement(sqlQuery);
    			ps1.setInt(1,apps);
    			ps1.setObject(2,UserId);
    			ps1.setObject(3,ebid);
        		ps1.executeUpdate();
        		ps1.close();
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
        		xml.append("</ebmaster>\n");
        	}
        	else if(action.equals("SU_UpdateWECStat"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String status=comma[0];
        		String wecid=comma[1];
        		int apps = 0;
        		if(status == null || status.equals("false")){
        			apps = 1;
        		}
        		else if(status.equals("true")){
        			apps = 2;
        		}
        		sqlQuery = SiteUserSQLC.UPDATE_WEC_STATUS;
    			ps1 = conn.prepareStatement(sqlQuery);
    			ps1.setInt(1,apps);
    			ps1.setObject(2,UserId);
    			ps1.setObject(3,wecid);
        		ps1.executeUpdate();
        		ps1.close();
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
        		xml.append("</wecmaster>\n");
        	}          	
        	else if(action.equals("SU_UpdateFD"))
        	{
        		String msg = UpdateFDData(UserId, item,1);       		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<fdentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<fdcode>");
				xml.append("<fdid>");
				xml.append(msg);
				xml.append("</fdid>\n");
				xml.append("</fdcode>\n");
			    xml.append("</fdentry>\n");
        	}
        	else if(action.equals("SU_UpdateEb"))
        	{
        		String msg = UpdateEBData(UserId, item,1,1); //earlier it was zero for publish
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<ebentry generated=\""+System.currentTimeMillis()+"\">\n");
    			xml.append("<ebcode>");
    			xml.append("<ebid>");
    			xml.append(msg);
    			xml.append("</ebid>\n");
    			xml.append("</ebcode>\n");
    		    xml.append("</ebentry>\n");
        	}
        	else if(action.equals("SU_UpdateWEC"))
        	{
        		String msg = UpdateWECData(UserId, item,1,1); //earlier it was zero for publish 
        		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<wecentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<weccode>");
				xml.append("<wecid>");
				xml.append(msg);
				xml.append("</wecid>\n");
				xml.append("</weccode>\n");
			    xml.append("</wecentry>\n");
        	} 
        	else if(action.equals("SU_UpdateWECRemarks"))
        	{
        		String msg = UpdateWECRemarks(UserId, item);       		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<wecentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<weccode>");
				xml.append("<wecid>");
				xml.append(msg);
				xml.append("</wecid>\n");
				xml.append("</weccode>\n");
			    xml.append("</wecentry>\n");
        	}
        	else if(action.equals("SU_UpdateFDemarks"))
        	{
        		String msg = UpdateFDemarks(UserId, item);       		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<fdentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<fdcode>");
				xml.append("<fdid>");
				xml.append(msg);
				xml.append("</fdid>\n");
				xml.append("</fdcode>\n");
			    xml.append("</fdentry>\n");
        	}
        	else if(action.equals("SU_UpdateEBemarks"))
        	{
        		String msg = UpdateEBemarks(UserId, item);      		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<ebentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<ebcode>");
				xml.append("<ebid>");
				xml.append(msg);
				xml.append("</ebid>\n");
				xml.append("</ebcode>\n");
			    xml.append("</ebentry>\n");
        	}
        	else if(action.equals("SU_FDCarryForward"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String fdid = comma[0].trim();
        		String Datetxt=comma[1].trim();
        		String Typetxt=comma[2].trim();
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		String readid = "";
        		String msg="";
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		
        		java.util.Calendar now = java.util.Calendar.getInstance();
				now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
				String pdate= DateFormat.getDateInstance().format(now.getTime());
				String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(fdate);
	            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
	    	    
        		sqlQuery = SiteUserSQLC.GET_FD_READING_YESTERDAY;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,prevdate);
        		ps.setObject(2,fdid);
        		rs = ps.executeQuery();
        		while(rs.next()){
        			String mpid = rs.getObject("S_MP_ID").toString().trim();
        			sqlQuery = SiteUserSQLC.CHECK_FD_READING_YESTERDAY_BY_MP;
	        		ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
	        		ps1.setObject(2,mpid);
	        		ps1.setObject(3,fdid);
	        		rs1 = ps1.executeQuery();
	        		if(rs1.next()){
	        			readid = rs1.getObject("S_READING_ID").toString().trim();
	        			sqlQuery = SiteUserSQLC.UPDATE_FD_READING;
	        			prepStmt = conn.prepareStatement(sqlQuery);
	        			prepStmt.setDouble(1,rs.getDouble("N_VALUE"));
	        			prepStmt.setDouble(2,0);
	        			prepStmt.setObject(3,UserId);
	        			prepStmt.setObject(4,readid);
	        			prepStmt.executeUpdate();
	        			prepStmt.close();
	        		}else
	        		{
	        			//if (rs.getDouble("N_VALUE") != 0)
	        			//{
		        			readid = CodeGenerate.NewCodeGenerate("TBL_FEDER_READING");
		        			sqlQuery = SiteUserSQLC.INSERT_FEDER_READING;
		        			prepStmt = conn.prepareStatement(sqlQuery);
		        			prepStmt.setObject(1,readid);
		        			prepStmt.setDate(2,date);
		        			prepStmt.setObject(3,mpid);
		        			prepStmt.setObject(4,fdid);
		        			prepStmt.setObject(5,Typetxt);
		        			prepStmt.setDouble(6,rs.getDouble("N_VALUE"));
		        			prepStmt.setObject(7,UserId);
		        			prepStmt.setObject(8,UserId);
		        			prepStmt.setDouble(9,0);
		        			prepStmt.setObject(10,""); //remarks
		        			prepStmt.executeUpdate();
		        			prepStmt.close();
	        			//}
	        		}
	        		rs1.close();
	        		ps1.close();
        		}
        		rs.close();
        		ps.close();
	                    		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<fdentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<fdcode>");
				xml.append("<fdid>");
				xml.append(msg);
				xml.append("</fdid>\n");
				xml.append("</fdcode>\n");
			    xml.append("</fdentry>\n");
        	}  
        	else if(action.equals("SU_EBCarryForward"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String ebid = comma[0].trim();
        		String Datetxt=comma[1].trim();
        		String Typetxt=comma[2].trim();
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		String readid = "";
        		String msg="";
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		
        		java.util.Calendar now = java.util.Calendar.getInstance();
				now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
				String pdate= DateFormat.getDateInstance().format(now.getTime());
				String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(fdate);
	            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
	    	    
        		sqlQuery = SiteUserSQLC.GET_EB_READING_YESTERDAY;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,prevdate);
        		ps.setObject(2,ebid);
        		rs = ps.executeQuery();
        		while(rs.next()){
        			String mpid = rs.getObject("S_MP_ID").toString().trim();
        			sqlQuery = SiteUserSQLC.CHECK_EB_READING_YESTERDAY_BY_MP;
	        		ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
	        		ps1.setObject(2,mpid);
	        		ps1.setObject(3,ebid);
	        		rs1 = ps1.executeQuery();
	        		if(rs1.next()){
	        			//if (rs.getDouble("N_VALUE") != 0)
	        			//{
		        			readid = rs1.getObject("S_READING_ID").toString().trim();
		        			sqlQuery = SiteUserSQLC.UPDATE_EB_READING;
		        			prepStmt = conn.prepareStatement(sqlQuery);
		        			prepStmt.setDouble(1,rs.getDouble("N_VALUE"));
		        			prepStmt.setDouble(2,0);
		        			prepStmt.setObject(3,UserId);
		        			prepStmt.setObject(4,readid);
		        			prepStmt.executeUpdate();
		        			prepStmt.close();
	        			//}
	        		}else
	        		{
	        			if (rs.getDouble("N_VALUE") > 0)
	        			{
		        			readid = CodeGenerate.NewCodeGenerate("TBL_EB_READING");
		        			sqlQuery = SiteUserSQLC.INSERT_EB_READING;
		        			prepStmt = conn.prepareStatement(sqlQuery);
		        			prepStmt.setObject(1,readid);
		        			prepStmt.setDate(2,date);
		        			prepStmt.setObject(3,mpid);
		        			prepStmt.setObject(4,ebid);
		        			prepStmt.setObject(5,Typetxt);
		        			prepStmt.setDouble(6,rs.getDouble("N_VALUE"));
		        			prepStmt.setObject(7,UserId);
		        			prepStmt.setObject(8,UserId);
		        			prepStmt.setDouble(9,0);
		        			prepStmt.setObject(10,""); //remarks
		        			prepStmt.setObject(11,rs.getObject("s_customer_id") == null ? "" : rs.getString("s_customer_id").trim());
		        			prepStmt.setObject(12, 0);
		        			prepStmt.executeUpdate();
		        			prepStmt.close();
	        			}
	        		}
	        		rs1.close();
	        		ps1.close();
        		}
        		rs.close();
        		ps.close();
	                    		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<ebentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<ebcode>");
				xml.append("<ebid>");
				xml.append(msg);
				xml.append("</ebid>\n");
				xml.append("</ebcode>\n");
			    xml.append("</ebentry>\n");
        	}   
        	else if(action.equals("SU_WECCarryForward"))
        	{
        		String s=item;
        		String comma[]=s.split(",");
        		String wecid = comma[0].trim();
        		String Datetxt=comma[1].trim();
        		String Typetxt=comma[2].trim();
        		java.util.Date dt = new java.util.Date();
        		dt = format.parse(Datetxt);
        		String readid = "";
        		String msg="";
        		java.sql.Date date = new java.sql.Date(dt.getTime());
        		String ebid = "";
        		String custid = "";
        		java.util.Calendar now = java.util.Calendar.getInstance();
				now.setTime(date);		
				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
				String pdate= DateFormat.getDateInstance().format(now.getTime());
				String fdate=ad.convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
	            dt = format.parse(fdate);
	            java.sql.Date prevdate = new java.sql.Date(dt.getTime());
	    	    
        		sqlQuery = SiteUserSQLC.GET_WEC_READING_YESTERDAY;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setDate(1,prevdate);
        		ps.setObject(2,wecid);
        		rs = ps.executeQuery();
        		while(rs.next()){
        			String mpid = rs.getObject("S_MP_ID").toString().trim();
        			sqlQuery = SiteUserSQLC.CHECK_WEC_READING_YESTERDAY_BY_MP;
	        		ps1 = conn.prepareStatement(sqlQuery);
	        		ps1.setDate(1,date);
	        		ps1.setObject(2,mpid.trim());
	        		ps1.setObject(3,wecid.trim());
	        		rs1 = ps1.executeQuery();
	        		if(rs1.next()){
	        			//if (rs.getDouble("N_VALUE") != 0)
	        			//{
		        			readid = rs1.getObject("S_READING_ID").toString().trim();
		        			sqlQuery = SiteUserSQLC.UPDATE_WEC_READING;
		        			prepStmt = conn.prepareStatement(sqlQuery);
		        			prepStmt.setDouble(1,rs.getDouble("N_VALUE"));
		        			prepStmt.setDouble(2,0);
		        			prepStmt.setObject(3,UserId);
		        			prepStmt.setObject(4,readid);
		        			prepStmt.executeUpdate();
		        			prepStmt.close();
	        			//}
	        		}else
	        		{
	        			if (rs.getDouble("N_VALUE") > 0 || rs.getDouble("N_ACTUAL_VALUE") > 0)
	        			{
	        				sqlQuery = AdminSQLC.SEARCH_WEC_ID_DETAILS;
		        			ps1 = conn.prepareStatement(sqlQuery);
		        			ps1.setObject(1,wecid);
		        			rs1 = ps1.executeQuery();
			        		if(rs1.next()){
			        			ebid = rs1.getObject("s_eb_id").toString().trim();
			        			custid = rs1.getObject("S_CUSTOMER_ID").toString().trim();
			        		}
			        		rs1.close();
			        		ps1.close();
			        		if (! ebid.equals("") && ! custid.equals(""))
			        		{
			        			readid = CodeGenerate.NewCodeGenerate("TBL_WEC_READING");
			        			sqlQuery = SiteUserSQLC.INSERT_WEC_READING;
			        			prepStmt = conn.prepareStatement(sqlQuery);
			        			prepStmt.setObject(1,readid.trim());
			        			prepStmt.setDate(2,date);
			        			prepStmt.setObject(3,mpid);
			        			prepStmt.setObject(4,wecid);
			        			prepStmt.setObject(5,ebid);
			        			prepStmt.setObject(6,custid);
			        			prepStmt.setDouble(7,rs.getDouble("N_VALUE"));
			        			prepStmt.setObject(8,UserId);
			        			prepStmt.setObject(9,UserId);
			        			prepStmt.setDouble(10,0);	
			        			prepStmt.setObject(11,Typetxt);
			        			prepStmt.setObject(12,0);
			        			prepStmt.executeUpdate();
			        			prepStmt.close();
			        		}
	        			}
	        		}
	        		rs1.close();
	        		ps1.close();
        		}
        		rs.close();
        		ps.close();
	                    		
        		xml.append("<?xml version=\"1.0\"?>\n");
        		xml.append("<wecentry generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<weccode>");
				xml.append("<wecid>");
				xml.append(msg);
				xml.append("</wecid>\n");
				xml.append("</weccode>\n");
			    xml.append("</wecentry>\n");
        	}        	     	
        }catch (SQLException sqlExp) {
        	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps1) , Arrays.asList(rs,rs1,rs2) , conn);
           
        }
        logger.debug(xml.toString());
        return xml.toString();
    }
    
    
    public String updatehoursinwec() throws Exception {
    	String msg="";
        //JDBCUtils conmanager = new //JDBCUtils();
        Connection conn = wcareConnector.getConnectionFromPool();
        PreparedStatement prepStmt = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Diff epwd = new Diff();
        String sqlQuery = "";
        try {
        	sqlQuery = "SELECT * FROM tbl_wec_reading where s_mp_id = '0808000022' and N_ACTUAL_VALUE > 0 order by d_reading_date";
            prepStmt = conn.prepareStatement(sqlQuery);
            rs = prepStmt.executeQuery();
            while(rs.next()) {
            	//String SalId = CodeGenerate.NewCodeGenerate("TBL_EB_READING");
                String sqlqString = "update TBL_wec_READING set N_ACTUAL_VALUE = 24 " +
                		" where d_reading_date=? and s_mp_id='0808000023' and N_ACTUAL_VALUE = 0 and s_wec_id = ?";
                ps = conn.prepareStatement(sqlqString);  
                ps.setDate(1, rs.getDate("d_reading_date"));
                ps.setObject(2, rs.getObject("s_wec_id"));
                ps.executeUpdate();
            	ps.close();
            	ps = null;
            }
            rs.close();
            prepStmt.close();
        	msg = "update success";
        } catch (SQLException sqlExp) {
            logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
        	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
        }
        return msg;
    }
}