

package com.enercon.customer.dao;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpSessionEvent;

import org.apache.log4j.Logger;

import com.enercon.admin.dao.AdminDao;
import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DatabaseUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.DebuggerUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utility.TimeUtility;
import com.enercon.global.utils.CodeGenerate;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.security.dao.SecuritySQLC;

public class CustomerDao extends DebuggerUtility implements WcareConnector{

	private static Logger logger = Logger.getLogger(CustomerDao.class);

	public CustomerDao() {
	}

	public HashMap<String,ArrayList<String>> getStateIdSiteIdMappingBasedOnCustomerID(String custID) {
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String customerID = custID;
		HashMap<String,ArrayList<String>> stateID_siteId= new HashMap<String,ArrayList<String>>();
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Statemaster.S_State_Id, Sitemaster.S_Site_Id " + 
							"From Tbl_State_Master Statemaster, Tbl_Site_Master Sitemaster " + 
							"Where Statemaster.S_State_Id = Sitemaster.S_State_Id " + 
							"and sitemaster.S_Site_Id In  " + 
							"                (Select S_Site_Id From Tbl_Eb_Master " + 
							"                Where S_Eb_Id In " + 
							"                                (Select Distinct S_Eb_Id From Tbl_Wec_Master  " + 
							"                                where s_customer_id in (?) " + 
							"                                Group By S_Eb_Id)) " + 
							"Order By Statemaster.S_State_Name, Sitemaster.S_Site_Name " ; 
	
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, customerID);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
					String stateID = rs.getString("S_STATE_ID");
					if(stateID_siteId.containsKey(stateID)){
						String siteID = new String();
						siteID = rs.getString("S_SITE_ID");
						ArrayList<String> alreadyPresentSiteId = stateID_siteId.get(stateID);
						alreadyPresentSiteId.add(siteID);
						stateID_siteId.remove(stateID);
						stateID_siteId.put(stateID, alreadyPresentSiteId);
					}
					else{
						ArrayList<String> siteId = new ArrayList<String>();
						siteId.add(rs.getString("S_SITE_ID"));
						stateID_siteId.put(stateID, siteId);
					}
			}
			return stateID_siteId;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return stateID_siteId;
	}
	
	public HashMap<String,ArrayList<String>> getSiteIdSiteDetailsBasedOnCustomerID(String custID) {
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String customerID = custID;
		HashMap<String,ArrayList<String>> siteID_siteDetail= new HashMap<String,ArrayList<String>>();
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"Select Sitemaster.S_Site_Id,Sitemaster.S_Site_Name,Statemaster.S_State_Name,Statemaster.S_State_Id  " + 
					"From Tbl_State_Master Statemaster, Tbl_Site_Master Sitemaster " + 
					"Where Statemaster.S_State_Id = Sitemaster.S_State_Id " + 
					"and sitemaster.S_Site_Id In  " + 
					"                (Select S_Site_Id From Tbl_Eb_Master " + 
					"                Where S_Eb_Id In " + 
					"                                (Select Distinct S_Eb_Id From Tbl_Wec_Master  " + 
					"                                where s_customer_id in (?) " + 
					"                                Group By S_Eb_Id)) " + 
					"Order By Statemaster.S_State_Name,Sitemaster.S_Site_Name " ; 
	
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, customerID);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
					String siteID = rs.getString("S_SITE_ID");
					ArrayList<String> siteDetails = new ArrayList<String>();
					siteDetails.add(rs.getString("S_SITE_NAME"));
					siteDetails.add(rs.getString("S_STATE_NAME"));
					siteDetails.add(rs.getString("S_STATE_ID"));
					siteID_siteDetail.put(siteID, siteDetails);
			}
			
			return siteID_siteDetail;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return siteID_siteDetail;
	}
	
	public List getWECByEbId(String ebId, String rdate) {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		PreparedStatement machinePrepStmt = null;
		ResultSet machineRS = null;
		PreparedStatement gridPrepStmt = null;
		ResultSet gridRS = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		try {
		
		conn = wcareConnector.getConnectionFromPool();
		prepStmt = null ;
		rs = null;
		tranList = new ArrayList();
		sqlQuery = "";

		sqlQuery = "select S_WECSHORT_DESCR " + 
					"from tbl_wec_master " + 
					"where s_eb_id = ? " +
					"order by S_WECSHORT_DESCR ";
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebId);
		
			DaoUtility.displayQueryWithParameter(23, sqlQuery, ebId);
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(23, rs);
				tranList.add((String)rs.getObject(1));
				/*String machineQuery = "Select SUM(n_actual_value) " +
										"From TBL_WEC_READING " +
										"Where to_char(D_Reading_Date) = '18-MAY-13' " +
										"and s_mp_id IN ('0808000025','0808000026') " +
										"and s_wec_id = ? " ;
				
				machinePrepStmt = conn.prepareStatement(machineQuery);
				//machinePrepStmt.setObject(1,rdate);
				machinePrepStmt.setObject(1,rs.getObject(2));
				machineRS = machinePrepStmt.executeQuery();
				if(machineRS.next()){
					if(machineRS.getObject(1) != null){
						tranList.add(machineRS.getObject(1));
					}
					else{
						tranList.add(null);
					}
				}
				else{
					tranList.add(null);
				}
				
				String gridQuery = "Select SUM(n_actual_value) " + 
										"From Tbl_Wec_Reading " +
										"Where to_char(D_Reading_Date) = '18-MAY-13' " +
										"and s_mp_id IN ('1000000001','0808000027','0808000028','0808000029') " +
										"and s_wec_id = ? " ;
				gridPrepStmt = conn.prepareStatement(gridQuery);
				
				//machinePrepStmt.setObject(1,rdate);
				gridPrepStmt.setObject(1,rs.getObject(2));
				gridRS = gridPrepStmt.executeQuery();
				if(gridRS.next()){
					if(gridRS.getObject(1) != null){
						tranList.add(gridRS.getObject(1));
					}
					else{
						tranList.add(null);
					}
				}
				else{
					tranList.add(null);
				}*/
			}
			return tranList;
			

		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,machinePrepStmt,gridPrepStmt) , Arrays.asList(rs,machineRS,gridRS) , conn);
		}
		return tranList;
	}
	
public String getCustemail(String UserId)throws Exception {
	String msg = "";
	String msg1 = "";
//	JDBCUtils conmanager = new JDBCUtils();
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	String sqlQuery = "";
	try {
		conn = wcareConnector.getConnectionFromPool();
		sqlQuery = SecuritySQLC.SELECT_LOGIN_DETAILS1;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, UserId);
		rs = prepStmt.executeQuery();
		if (rs.next()) {
			msg = rs.getObject("S_EMAIL").toString();			
			msg1 = rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND");
			msg = msg+","+msg1;
			 
		} 
		rs.close();
		prepStmt.close();
	
	} catch (SQLException sqlExp) {
		 logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return msg;
}


public static String getCustCapacity(String CustId)throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}
	String msg = "";
//	JDBCUtils conmanager = new JDBCUtils();
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	String sqlQuery = "";
	try {
		conn = wcareConnector.getConnectionFromPool();
		//CustId=CustId.replace(",", "\',\'");
		/*sqlQuery = CustomerSQLC.SELECT_CUSTOMER_CAPACITY;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, CustId);*/
		sqlQuery = "SELECT NVL(N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER "+
		 "WHERE S_CUSTOMER_ID in('"+CustId+"')  and s_status=1 GROUP BY N_WEC_CAPACITY";
		prepStmt = conn.prepareStatement(sqlQuery);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
		}	
		rs = prepStmt.executeQuery();
		if (rs.next()) {
			msg = rs.getObject("lcapacity").toString();
			 
		} 
		rs.close();
		prepStmt.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		 DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return msg;
}

public static String getSiteRemarks(String siteName)throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}
	String msg = "";
//	JDBCUtils conmanager = new JDBCUtils();
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	String sqlQuery = "";
	try {
		conn = wcareConnector.getConnectionFromPool();
		//CustId=CustId.replace(",", "\',\'");
		/*sqlQuery = CustomerSQLC.SELECT_CUSTOMER_CAPACITY;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, CustId);*/
		sqlQuery = "SELECT S_SITE_REMARKS FROM TBL_SITE_MASTER WHERE S_SITE_NAME = '"+siteName+"'";
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
		}
		prepStmt = conn.prepareStatement(sqlQuery);
		
		rs = prepStmt.executeQuery();
		if (rs.next()) {
			msg = rs.getObject("S_SITE_REMARKS")==null?"Data could not be uploaded due to some technical difficulty at site.":rs.getObject("S_SITE_REMARKS").toString();
			 
		} 
		rs.close();
		prepStmt.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		 DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return msg;
}

public String addPostMessage(String UserId, DynaBean dynaBean)
throws Exception {
String msg = "";
//JDBCUtils conmanager = new JDBCUtils();
Connection conn = null;
PreparedStatement prepStmt = null;
PreparedStatement ps = null;
ResultSet rs = null;
String sqlQuery = "";
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

try {
	conn = wcareConnector.getConnectionFromPool();
	String msgdesc = dynaBean.getProperty("MsgDescriptiontxt")
			.toString().replaceAll("<BR>", "");
	msgdesc = msgdesc.toUpperCase();
	String fd = dynaBean.getProperty("FromDatetxt").toString();
	java.util.Date ffd = format.parse(fd);
	java.sql.Date fromdate = new java.sql.Date(ffd.getTime());
	
	String td = dynaBean.getProperty("ToDatetxt").toString();
	java.util.Date ttd = format.parse(td);
	java.sql.Date todate = new java.sql.Date(ttd.getTime());
	String msgid = "";
	if (dynaBean.getProperty("MsgIdtxt") == null || dynaBean.getProperty("MsgIdtxt").equals("")) {
		msgid = CodeGenerate.NewCodeGenerate("TBL_MESSAGE_DETAIL");
		sqlQuery = CustomerSQLC.CHECK_MESSAGE_DETAIL;
		//sqlQuery.replaceAll("<BR>", "");
		prepStmt = conn.prepareStatement(sqlQuery);
		//prepStmt.setObject(1, changestr(msgdesc));
		prepStmt.setObject(1, msgdesc.replaceAll("<BR>", ""));
		prepStmt.setObject(2, msgid);
		rs = prepStmt.executeQuery();
		if (rs.next()) {
			msg = "<font class='errormsgtext'>Message already exists!</font>";
			rs.close();
			prepStmt.close();
		} else {
			sqlQuery = CustomerSQLC.INSERT_MESSAGE_DETAIL;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, msgid);
			ps.setObject(2, changestr(msgdesc));
			ps.setObject(3, fromdate);
			ps.setObject(4, todate);
			ps.setObject(5, UserId);
			ps.setObject(6, UserId);
			int iInserteddRows = ps.executeUpdate();
			// conn.commit();
			if (iInserteddRows != 1)
				throw new Exception("DB_UPDATE_ERROR", null);
			ps.close();
			msg = "<font class='errormsgtext'>Message Added Sucessfully!</font>";
			;
		}
		prepStmt.close();
		rs.close();
	} else {
		msgid = dynaBean.getProperty("MsgIdtxt").toString();
		sqlQuery = CustomerSQLC.CHECK_MESSAGE_DETAIL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, msgdesc.replaceAll("<BR>", ""));
		prepStmt.setObject(2, msgid);
		rs = prepStmt.executeQuery();
		if (rs.next()) {
			msg = "<font class='errormsgtext'>Message already exists!</font>";
			rs.close();
			prepStmt.close();
		} else {
			sqlQuery = CustomerSQLC.UPDATE_MESSAGE_DETAIL;
			ps = conn.prepareStatement(sqlQuery);
	
			ps.setObject(1, changestr(msgdesc));
			ps.setObject(2, fromdate);
			ps.setObject(3, todate);
			ps.setObject(4, UserId);
			ps.setObject(5, msgid);
			ps.executeUpdate();
			// conn.commit();
			ps.close();
			msg = "<font class='errormsgtext'>Message Updated Sucessfully!</font>";
		}
		prepStmt.close();
		rs.close();
	}
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);

	}
	return msg;
	}

	public String addStdMessage(String UserId, DynaBean dynaBean)
			throws Exception {
		String msg = "";
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try {
			conn = wcareConnector.getConnectionFromPool();
			String msghead = dynaBean.getProperty("MsgHeadtxt").toString();

			logger.debug("dynaBean: " + dynaBean);
			String msgdesc = dynaBean.getProperty("MsgDescriptiontxt").toString();
			String msgid = "";
			if (dynaBean.getProperty("MsgIdtxt") == null || dynaBean.getProperty("MsgIdtxt").equals("")) {
				msgid = CodeGenerate.NewCodeGenerate("TBL_STANDARD_MESSAGE");
				sqlQuery = CustomerSQLC.CHECK_MESSAGE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, msghead);
				prepStmt.setObject(2, msgid);
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					msg = "<font class='errormsgtext'>Message Head already exists!</font>";
					rs.close();
					prepStmt.close();
				} else {
					sqlQuery = CustomerSQLC.INSERT_STD_MESSAGE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, msgid);
					ps.setObject(2, changestr(msghead));
					ps.setObject(3, changestr(msgdesc));
					ps.setObject(4, UserId);
					ps.setObject(5, UserId);
					int iInserteddRows = ps.executeUpdate();
					// conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Standard Message Added Successfully!</font>";
				}
				prepStmt.close();
				rs.close();
			} else {
				msgid = dynaBean.getProperty("MsgIdtxt").toString();
				sqlQuery = CustomerSQLC.CHECK_MESSAGE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, msghead);
				prepStmt.setObject(2, msgid);
				rs = prepStmt.executeQuery();
				if (rs.next()) {
					msg = "<font class='errormsgtext'>Message Head already exists!</font>";
					rs.close();
					prepStmt.close();
				} else {
					sqlQuery = CustomerSQLC.UPDATE_STD_MESSAGE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, msghead);
					ps.setObject(2, msgdesc);
					ps.setObject(3, UserId);
					ps.setObject(4, msgid);
					ps.executeUpdate();
					// conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Standard Message Updated Successfully!</font>";
				}
				prepStmt.close();
				rs.close();
			}
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
			
		}
		return msg;
	}

	public String addManageProfile(String UserId, DynaBean dynabean)throws Exception {
			String msg = "";
//			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
			PreparedStatement prepStmt = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sqlQuery = "";
			String lid = "",ccode = "",oname="",ocell="",ophone="",oemail="",ofax="",odob="",odoa="",cphone="",ccellno="",ccontact="",cemail="",ccity="",czip="",cfax="",cdob="",cdoa="";

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			lid=dynabean.getProperty("logIdtxt")==null?"":dynabean.getProperty("logIdtxt").toString();
			oname = dynabean.getProperty("CustOwneretxt")==null?"":dynabean.getProperty("CustOwneretxt").toString();
			oemail = dynabean.getProperty("CustOEmailtxt")==null?"":dynabean.getProperty("CustOEmailtxt").toString();
			ophone = dynabean.getProperty("CustOPhonetxt")==null?"":dynabean.getProperty("CustOPhonetxt").toString();
			ocell = dynabean.getProperty("CustOMobiletxt")==null?"":dynabean.getProperty("CustOMobiletxt").toString();
			ofax = dynabean.getProperty("CustOFaxtxt")==null?"":dynabean.getProperty("CustOFaxtxt").toString();
			odob = dynabean.getProperty("CustODOBtxt")==null?"":dynabean.getProperty("CustODOBtxt").toString();
			odoa = dynabean.getProperty("CustODOAtxt")==null?"":dynabean.getProperty("CustODOAtxt").toString();
			ccode=dynabean.getProperty("CustCNametxt")==null?"":dynabean.getProperty("CustCNametxt").toString();
			ccontact = dynabean.getProperty("CustAddresstxt")==null?"":dynabean.getProperty("CustAddresstxt").toString().trim();
			 ccity= dynabean.getProperty("CustCitytxt")==null?"":dynabean.getProperty("CustCitytxt").toString();
			czip= dynabean.getProperty("CustZiptxt")==null?"":dynabean.getProperty("CustZiptxt").toString();
			cemail = dynabean.getProperty("CustEmailtxt")==null?"":dynabean.getProperty("CustEmailtxt").toString();
			cphone = dynabean.getProperty("CustPhonetxt")==null?"":dynabean.getProperty("CustPhonetxt").toString();
			ccellno = dynabean.getProperty("CustMobiletxt")==null?"":dynabean.getProperty("CustMobiletxt").toString();
			cfax = dynabean.getProperty("CustFaxtxt")==null?"":dynabean.getProperty("CustFaxtxt").toString();
			cdob = dynabean.getProperty("CustDOBtxt")==null?"":dynabean.getProperty("CustDOBtxt").toString();
			cdoa = dynabean.getProperty("CustDOAtxt")==null?"":dynabean.getProperty("CustDOAtxt").toString();
			java.util.Date odobirth = null,odoan=null,cdobirth=null,cdoann=null; 
				if(!odob.equals(""))
				{
					java.util.Date ffd = format.parse(odob);
					odobirth=new java.sql.Date(ffd.getTime());
				}
				
				if(!odoa.equals(""))
				{
					java.util.Date ffd = format.parse(odoa);
					odoan=new java.sql.Date(ffd.getTime());
				}
				
				if(!cdob.equals(""))
				{
					java.util.Date ffd = format.parse(cdob);
					cdobirth=new java.sql.Date(ffd.getTime());
				}
				
				if(!cdoa.equals(""))
				{
					java.util.Date ffd = format.parse(cdoa);
					cdoann=new java.sql.Date(ffd.getTime());
				}

				try {
					//String msghead = dynabean.getProperty("MsgHeadtxt").toString();
				
					//String msgdesc = dynabean.getProperty("MsgDescriptiontxt")
					//		.toString();
					//String lid = "";
					if (dynabean.getProperty("logIdtxt") == null|| dynabean.getProperty("logIdtxt").equals("")) 
					{
							lid = CodeGenerate.NewCodeGenerate("TBL_LOGIN_DETAIL");
							sqlQuery = CustomerSQLC.INSERT_LOGIN_DETAIL;
							ps = conn.prepareStatement(sqlQuery);
							ps.setObject(1, lid);
							ps.setObject(2, UserId);
							ps.setObject(3, oname);
							ps.setObject(4, oemail);
							ps.setObject(5, ophone);
							ps.setObject(6, ocell);
							ps.setObject(7, ofax);
							ps.setObject(8, odobirth);
							ps.setObject(9, odoan);
							ps.setObject(10, ccode);
							ps.setObject(11, ccontact);
							ps.setObject(12, ccity);
							ps.setObject(13, czip);
							ps.setObject(14, cemail);
							ps.setObject(15, cphone);
							ps.setObject(16, ccellno);
							ps.setObject(17, cfax);
							ps.setObject(18, cdobirth);
							ps.setObject(19, cdoann);
							int iInserteddRows = ps.executeUpdate();
							// conn.commit();
							if (iInserteddRows != 1)
								throw new Exception("DB_UPDATE_ERROR", null);
							ps.close();
							msg = "<font class='sucessmsgtext'>Login Detail Added Successfully!</font>";
					}else {
								sqlQuery = CustomerSQLC.update_LOGIN_DETAIL;
								ps = conn.prepareStatement(sqlQuery);
								//ps.setObject(1, UserId);
								ps.setObject(1, oname);
								ps.setObject(2, oemail);
								ps.setObject(3, ophone);
								ps.setObject(4, ocell);
								ps.setObject(5, ofax);
								ps.setObject(6, odobirth);
								ps.setObject(7, odoan);
								ps.setObject(8, ccode);
								ps.setObject(9, ccontact);
								ps.setObject(10, ccity);
								ps.setObject(11, czip);
								ps.setObject(12, cemail);
								ps.setObject(13, cphone);
								ps.setObject(14, ccellno);
								ps.setObject(15, cfax);
								ps.setObject(16, cdobirth);
								ps.setObject(17, cdoann);
								ps.setObject(18, lid);
								ps.executeUpdate();
								// conn.commit();
								ps.close();
								msg = "<font class='sucessmsgtext'>Login Detail Updated Successfully!</font>";
							}
							
				} catch (SQLException sqlExp) {
					logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
					Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
					throw exp;
				} finally {
					DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
					
				}
				return msg;
	}

	public String addCustomerFeedBack(String UserId, DynaBean dynabean)throws Exception {
		String msg = "";
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		////String r1=dynabean.getProperty("r1")==null?"":dynabean.getProperty("r1").toString();
		////String r2=dynabean.getProperty("r2")==null?"":dynabean.getProperty("r2").toString();
		////String r3=dynabean.getProperty("r3")==null?"":dynabean.getProperty("r3").toString();
		////String d1=dynabean.getProperty("d1")==null?"":dynabean.getProperty("d1").toString();
		////String d2=dynabean.getProperty("d2")==null?"":dynabean.getProperty("d2").toString();
		////String d3=dynabean.getProperty("d3")==null?"":dynabean.getProperty("d3").toString();
		
		
		String r4=dynabean.getProperty("r4")==null?"":dynabean.getProperty("r4").toString();
		String r5=dynabean.getProperty("r5")==null?"":dynabean.getProperty("r5").toString();
		String r6=dynabean.getProperty("r6")==null?"":dynabean.getProperty("r6").toString();
		String d4=dynabean.getProperty("d4")==null?"":dynabean.getProperty("d4").toString();
		String d5=dynabean.getProperty("d5")==null?"":dynabean.getProperty("d5").toString();
		String d6=dynabean.getProperty("d6")==null?"":dynabean.getProperty("d6").toString();
		
		
		String r7=dynabean.getProperty("r7")==null?"":dynabean.getProperty("r7").toString();
		String r8=dynabean.getProperty("r8")==null?"":dynabean.getProperty("r8").toString();
		String r9=dynabean.getProperty("r9")==null?"":dynabean.getProperty("r9").toString();
		String d7=dynabean.getProperty("d7")==null?"":dynabean.getProperty("d7").toString();
		String d8=dynabean.getProperty("d8")==null?"":dynabean.getProperty("d8").toString();
		String d9=dynabean.getProperty("d9")==null?"":dynabean.getProperty("d9").toString();
		
		
		String r10=dynabean.getProperty("r10")==null?"":dynabean.getProperty("r10").toString();
		String d10=dynabean.getProperty("d10")==null?"":dynabean.getProperty("d10").toString();
		
		////String r11=dynabean.getProperty("r11")==null?"":dynabean.getProperty("r11").toString();
		////String d11=dynabean.getProperty("d11")==null?"":dynabean.getProperty("d11").toString();
		
		////String r12=dynabean.getProperty("r12")==null?"":dynabean.getProperty("r12").toString();
		//String r13=dynabean.getProperty("r13")==null?"":dynabean.getProperty("r13").toString();
		////String d12=dynabean.getProperty("d12")==null?"":dynabean.getProperty("d12").toString();
		//String d13=dynabean.getProperty("d13")==null?"":dynabean.getProperty("d13").toString();
		
		String r14=dynabean.getProperty("r14")==null?"":dynabean.getProperty("r14").toString();
		String d14=dynabean.getProperty("d14")==null?"":dynabean.getProperty("d14").toString();
		
		String r15=dynabean.getProperty("r15")==null?"":dynabean.getProperty("r15").toString();
		String r16=dynabean.getProperty("r16")==null?"":dynabean.getProperty("r16").toString();
		String r17=dynabean.getProperty("r17")==null?"":dynabean.getProperty("r17").toString();
		String d15=dynabean.getProperty("d15")==null?"":dynabean.getProperty("d15").toString();
		String d16=dynabean.getProperty("d16")==null?"":dynabean.getProperty("d16").toString();
		String d17=dynabean.getProperty("d17")==null?"":dynabean.getProperty("d17").toString();
		
		String r18=dynabean.getProperty("r18")==null?"":dynabean.getProperty("r18").toString();
		String r19=dynabean.getProperty("r19")==null?"":dynabean.getProperty("r19").toString();
		String r20=dynabean.getProperty("r20")==null?"":dynabean.getProperty("r20").toString();
		String d18=dynabean.getProperty("d18")==null?"":dynabean.getProperty("d18").toString();
		String d19=dynabean.getProperty("d19")==null?"":dynabean.getProperty("d19").toString();
		String d20=dynabean.getProperty("d20")==null?"":dynabean.getProperty("d20").toString();
		
		String r21=dynabean.getProperty("r21")==null?"":dynabean.getProperty("r21").toString();
		////String r22=dynabean.getProperty("r22")==null?"":dynabean.getProperty("r19").toString();
		////String r23=dynabean.getProperty("r23")==null?"":dynabean.getProperty("r20").toString();
		String d21=dynabean.getProperty("d21")==null?"":dynabean.getProperty("d21").toString();
		////String d22=dynabean.getProperty("d22")==null?"":dynabean.getProperty("d19").toString();
		////String d23=dynabean.getProperty("d23")==null?"":dynabean.getProperty("d20").toString();
		
		
		//String r24=dynabean.getProperty("r24")==null?"":dynabean.getProperty("r24").toString();
		String r25=dynabean.getProperty("r25")==null?"":dynabean.getProperty("r25").toString();
		String r26=dynabean.getProperty("r26")==null?"":dynabean.getProperty("r26").toString();
		//String d24=dynabean.getProperty("d24")==null?"":dynabean.getProperty("d24").toString();
		String d25=dynabean.getProperty("d25")==null?"":dynabean.getProperty("d25").toString();
		String d26=dynabean.getProperty("d26")==null?"":dynabean.getProperty("d26").toString();
		
		String r27=dynabean.getProperty("r27")==null?"":dynabean.getProperty("r27").toString();
		String r28=dynabean.getProperty("r28")==null?"":dynabean.getProperty("r28").toString();
		String r29=dynabean.getProperty("r29")==null?"":dynabean.getProperty("r29").toString();
		String r30=dynabean.getProperty("r30")==null?"":dynabean.getProperty("r30").toString();
		String d27=dynabean.getProperty("d27")==null?"":dynabean.getProperty("d27").toString();
		String d28=dynabean.getProperty("d28")==null?"":dynabean.getProperty("d28").toString();
		String d29=dynabean.getProperty("d29")==null?"":dynabean.getProperty("d29").toString();
		String d30=dynabean.getProperty("d30")==null?"":dynabean.getProperty("d30").toString();
		
		String sug=dynabean.getProperty("txtsug")==null?"":dynabean.getProperty("txtsug").toString();
	
	
		
		

			try {
				//String msghead = dynabean.getProperty("MsgHeadtxt").toString();
			
				//String msgdesc = dynabean.getProperty("MsgDescriptiontxt")
				//		.toString();
				 String lid = "";
				if (dynabean.getProperty("logIdtxt") == null|| dynabean.getProperty("logIdtxt").equals("")) 
				{
						lid = CodeGenerate.NewCodeGenerate("TBL_CUSTOMER_FEEDBACK");
						sqlQuery = CustomerSQLC.INSERT_CUSTOMER_FEEDBACK;
						ps = conn.prepareStatement(sqlQuery);
						ps.setObject(1, lid);
						ps.setObject(2, UserId);
						////ps.setObject(3, r1);
						////ps.setObject(4, r2);
						////ps.setObject(5, r3);
						////ps.setObject(6, d1);
						////ps.setObject(7, d2);
						////ps.setObject(8, d3);
						
						ps.setObject(3, r4);
						ps.setObject(4, r5);
						ps.setObject(5, r6);
						ps.setObject(6, d4);
						ps.setObject(7, d5);
						ps.setObject(8, d6);
						
						ps.setObject(9, r7);
						ps.setObject(10, r8);
						ps.setObject(11, r9);
						ps.setObject(12, d7);
						ps.setObject(13, d8);
						ps.setObject(14, d9);
						
						ps.setObject(15, r10);
						ps.setObject(16, d10);
						
						////ps.setObject(23, r11);
						////ps.setObject(24, d11);
						
						
						////ps.setObject(25, r12);
						
						////ps.setObject(26, d12);
					
						
						ps.setObject(17, r14);
						ps.setObject(18, d14);
						
						ps.setObject(19, r15);
						ps.setObject(20, r16);
						ps.setObject(21, r17);
						ps.setObject(22, d15);
						ps.setObject(23, d16);
						ps.setObject(24, d17);
						
						ps.setObject(25, r18);
						ps.setObject(26, r19);
						ps.setObject(27, r20);
						ps.setObject(28, d18);
						ps.setObject(29, d19);
						ps.setObject(30, d20);
						
						ps.setObject(31, r21);
						ps.setObject(32, d21);
						
						
						////ps.setObject(41, r22);
						////ps.setObject(42, r23);
						
						////ps.setObject(43, d22);
						////ps.setObject(44, d23);
						
						
						ps.setObject(33, r25);
						ps.setObject(34, r26);
						
						ps.setObject(35, d25);
						ps.setObject(36, d26);
						
						ps.setObject(37, r27);
						ps.setObject(38, d27);
						
						ps.setObject(39, r28);
						ps.setObject(40, r29);
						
						ps.setObject(41, d28);
						ps.setObject(42, d29);
						
						ps.setObject(43, r30);
						ps.setObject(44, d30);
						
						ps.setObject(45, sug);
						
						 ps.executeUpdate();
						// conn.commit();
					
						ps.close();
						msg = "<font class='sucessmsgtext'>Added Successfully!</font>";
				}
						
			} catch (SQLException sqlExp) {
				logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
				Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
				throw exp;
			} finally {
				DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
			}
			return msg;
}

	public List getCustomerDetail(String logiId) throws Exception {
		String msg = "";
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";
		try {

			sqlQuery = CustomerSQLC.Get_Customer_Login;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, logiId);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("s_customer_id"));

				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}

	
	public List getMessage(String custId) throws Exception {
		String msg = "";
//		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";
		try {

			sqlQuery = CustomerSQLC.CHECK_MESSAGE_SEND;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custId);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();
				if (rs.getObject("S_MESSAGE_DESCRIPTION") == null
						|| rs.getObject("S_MESSAGE_DESCRIPTION") == "")
					msgstr = "NIL";
				else

					msgstr = changestr(rs.getObject("S_MESSAGE_DESCRIPTION")
							.toString());

				tranVector.add(msgstr);

				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}

	
	public static List getStateWise(String custId,String rdate) throws Exception {
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		try { 

			sqlQuery = CustomerSQLC.GET_CUST_STATE_WISE_CAPACITY;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custId);
			prepStmt.setObject(2, reportdate);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("s_site_id"));
				tranVector.add(rs.getObject("s_site_name"));
				tranVector.add(rs.getObject("s_state_name"));
				tranVector.add(rs.getObject("cnt"));
				tranVector.add(rs.getObject("lcapacity"));
				tranVector.add(rs.getObject("N_COST_PER_UNIT"));
				tranVector.add(rs.getObject("MAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}
	
	
	public static List getStateWise3(String custId,String rdate) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
		
		AdminDao ad = new AdminDao();
		String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");

		try { 

			/*sqlQuery = CustomerSQLC.GET_CUST_STATE_WISE_CAPACITY3;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custId);
			prepStmt.setObject(2, reportdate);*/
			
			sqlQuery = " SELECT distinct c.s_site_id,d.s_state_name,c.s_site_name,COUNT(*) as cnt " +
			 " FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_cur_data e "+
			 " WHERE a.S_CUSTOMER_ID in('"+custId+"')  " +
			 " and a.s_status=1   " +
			 " and e.D_READING_DATE='"+reportdate+"' " +
			 " and e.s_eb_id=a.s_eb_id " +			 
			 " AND e.MAVIAL<95 "+
		     " and a.s_eb_id=b.s_eb_id "+
		     " and b.s_site_id=c.s_site_id "+
		     " and c.s_state_id=d.s_state_id "+
		     " GROUP BY d.s_state_name, c.s_site_id, c.s_site_name  " +
		     " order by d.s_state_name, c.s_site_name";
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();
				

				tranVector.add(rs.getObject("s_site_id"));
				tranVector.add(rs.getObject("s_site_name"));
				tranVector.add(rs.getObject("s_state_name"));
				tranVector.add(rs.getObject("cnt"));
				tranVector.add("22");
				tranVector.add("3.3");
				tranVector.add("95");
				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}
	
	public static List getStateWise2(String custId,String rdate) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement prepStmt1 = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		ResultSet rs1 = null;
		String sqlQuery = "";
		

		// SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		String rdate1="";
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String month1 = "";
		String day1 = "";
		String curyear = "";
		//int dow = cal.get(Calendar.DAY_OF_WEEK);
		//int dom = cal.get(Calendar.DAY_OF_MONTH);
		//int doy = cal.get(Calendar.DAY_OF_YEAR);
		//curyear = day + "/" + month + "/" + year;
		curyear = month + "/" + year;
		if (month < 10 ||(day < 10)) {
			month1 = "0"+month;
			day1 = "0"+day;
			if(month<10){
			curyear =  month1 + "/" + year;
			}
			if(day<10){
				curyear =  month + "/" + year;
			}
			if((month<10) &&(day<10)){
				curyear = month1 + "/" + year;
			}
		}
		rdate1="01/"+curyear;
		// String yr = curyear;
		// java.util.Date ffd1 = format.parse(rdate1);
		// java.util.Date ffd = format.parse(rdate);
		// if (ffd1>ffd)
		// java.sql.Date reportdate2 = new java.sql.Date(ffd.getTime());
		// java.sql.Date reportdate12 = new java.sql.Date(ffd1.getTime());
		
		// AdminDao ad = new AdminDao();
		String reportdate= AdminDao.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
		String reportdate1= AdminDao.convertDateFormat(rdate1,"dd/MM/yyyy","dd-MMM-yyyy");

		try { 

			/*sqlQuery = CustomerSQLC.GET_CUST_STATE_WISE_CAPACITY2;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custId);
			prepStmt.setObject(2, reportdate1);
			prepStmt.setObject(3, reportdate);*/
			
			sqlQuery = "SELECT distinct c.s_site_id, MAX(a.N_COST_PER_UNIT) AS N_COST_PER_UNIT, d.s_state_name, c.s_site_name, COUNT(*) as cnt " +
			 " FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_cur_data e "+
			 " WHERE a.S_CUSTOMER_ID in('"+custId+"')  " +
			 " and  a.s_status=1   " +
			 " and 	e.D_READING_DATE between '"+reportdate1+"' and '"+reportdate+"' " +
			 " and  e.s_eb_id=a.s_eb_id " +			 
			 " AND  e.MAVIAL<98 "+
		     " and 	a.s_eb_id=b.s_eb_id "+
		     " and 	b.s_site_id=c.s_site_id "+
		     " and 	c.s_state_id=d.s_state_id "+
		     " GROUP BY d.s_state_name, c.s_site_id, c.s_site_name " +
		     " order by d.s_state_name, c.s_site_name";
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				// String msgstr;
				Vector tranVector = new Vector();
				

				tranVector.add(rs.getObject("s_site_id"));
				tranVector.add(rs.getObject("s_site_name"));
				tranVector.add(rs.getObject("s_state_name"));
				tranVector.add(rs.getObject("cnt"));
				
				/*sqlQuery = CustomerSQLC.GET_CUST_STATE_WISE_CAPACITY22;
				prepStmt1 = conn.prepareStatement(sqlQuery);
				prepStmt1.setObject(1, custId);
				prepStmt1.setObject(2, reportdate1);
				prepStmt1.setObject(3, reportdate);*/
				
				
				sqlQuery = " SELECT round(avg(e.MAVIAL),0) as MAVIAL ,COUNT(*) as cnt, NVL(sum(a.N_WEC_CAPACITY)*COUNT(*)/1000,0) as lcapacity " +
				 " FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_cur_data e "+
				 " WHERE a.S_CUSTOMER_ID in ('"+custId+"')  " +
		 		 " and a.s_status=1   " +
		 		 " and e.D_READING_DATE between '"+reportdate1+"' and '"+reportdate+"'  "+
		 		 " and e.s_eb_id=a.s_eb_id " +		 		 
			     " and a.s_eb_id=b.s_eb_id "+
			     " and b.s_site_id=c.s_site_id "+
			     " and c.s_state_id=d.s_state_id "+
			     " GROUP BY d.s_state_name, c.s_site_id, e.MAVIAL, c.s_site_name, a.N_WEC_CAPACITY order by d.s_state_name,  c.s_site_name";
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName());
				}
				prepStmt1 = conn.prepareStatement(sqlQuery);
				
				rs1 = prepStmt1.executeQuery();
				if (rs1.next()) {
					tranVector.add(rs1.getObject("lcapacity"));
					tranVector.add(rs1.getObject("MAVIAL"));
				}
				
				rs1.close();
				prepStmt1.close();
				tranVector.add(rs.getObject("N_COST_PER_UNIT"));
				tranVector.add(rdate1);
				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,prepStmt1,ps) , Arrays.asList(rs,rs1) , conn);
		}
		return tranList;
	}

	public static List getWECWise(String custId) throws Exception {
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";
		try { 

			sqlQuery = CustomerSQLC.GET_CUST_STATE_WISE_CAPACITY;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custId);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("s_site_id"));
				tranVector.add(rs.getObject("s_site_name"));
				tranVector.add(rs.getObject("s_state_name"));
				tranVector.add(rs.getObject("cnt"));
				tranVector.add(rs.getObject("lcapacity"));
				
				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}

	
//	public List getMessageByLogid(String logid) throws Exception {
//		String msg = "";
//		JDBCUtils conmanager = new JDBCUtils();
//		Connection conn = conmanager.getConnection();
//		PreparedStatement prepStmt = null;
//		PreparedStatement ps = null;
//		List tranList = new ArrayList();
//		ResultSet rs = null;
//		String sqlQuery = "",cname="";
//		try {
//
//			sqlQuery = CustomerSQLC.CHECK_MESSAGE_SEND_BY_LOG_ID;
//			prepStmt = conn.prepareStatement(sqlQuery);
//			prepStmt.setObject(1, logid);
//
//			rs = prepStmt.executeQuery();
//			int i = 0;
//			while (rs.next()) {
//				String msgstr;
//				Vector tranVector = new Vector();
//				if (rs.getObject("S_MESSAGE_DESCRIPTION") == null|| rs.getObject("S_MESSAGE_DESCRIPTION") == "")
//					msgstr = "NIL";
//				else
//					msgstr = rs.getObject("S_MESSAGE_DESCRIPTION").toString();
//
//				cname = changestr(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
//				tranVector.add(cname);
//				tranVector.add(msgstr);
//
//				tranList.add(i, tranVector);
//				i++;
//			}
//
//			prepStmt.close();
//			rs.close();
//		} catch (SQLException sqlExp) {
//			sqlExp.printStackTrace();
//			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
//			throw exp;
//		} finally {
//			try {
//				if (prepStmt != null) {
//					prepStmt.close();
//				}
//				if (rs != null)
//					rs.close();
//				if (conn != null) {
//					conn.close();
//					conn = null;
//
//					conmanager.closeConnection();
//					conmanager = null;
//				}
//			} catch (Exception e) {
//				prepStmt = null;
//				rs = null;
//				if (conn != null) {
//					conn.close();
//					conn = null;
//
//					conmanager.closeConnection();
//					conmanager = null;
//				}
//			}
//		}
//		return tranList;
//	}
	
	public List getMessageByLogid(String logid) throws Exception {
		logger.debug("sdsdf");
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		//String sqlQuery = "",cname="";
		String sqlQuery = "";
		try {

			sqlQuery = CustomerSQLC.CHECK_MESSAGE_SEND_BY_LOG_ID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, logid);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();
				if (rs.getObject("S_MESSAGE_DESCRIPTION") == null|| rs.getObject("S_MESSAGE_DESCRIPTION") == "")
					msgstr = "NIL";
				else
					msgstr = rs.getObject("S_MESSAGE_DESCRIPTION").toString();

				logger.debug(msgstr);
				//cname = changestr(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				//tranVector.add(cname);
				tranVector.add(msgstr);

				tranList.add(i, tranVector);
				i++;
			}

			logger.debug(tranList);
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}

	
	public List getCustList() throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		String msg = "";
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		List tranList = new ArrayList();
		ResultSet rs = null;
		String sqlQuery = "";
		try {

			sqlQuery = CustomerSQLC.GET_CUSTOMER_EMAIL_DETAIL;
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			prepStmt = conn.prepareStatement(sqlQuery);
			//prepStmt.setObject(1, custId);

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				String msgstr;
				Vector tranVector = new Vector();
				

				tranVector.add(rs.getObject("S_CUSTOMER_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				tranVector.add(rs.getObject("S_EMAIL"));
				tranVector.add(rs.getObject("S_OEMAIL"));
				tranList.add(i, tranVector);
				i++;
			}

			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs) , conn);
		}
		return tranList;
	}
	
	public List getOverallTotal(String custid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_OVERALL_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			DaoUtility.displayQueryWithParameter(53, sqlQuery, custid,reportdate);
			/*Vector<Object> v = new Vector<Object>();
			List<Object> overAllTotalInfo = new ArrayList<Object>();
			
			overAllTotalInfo = CustomerMetaInfo.getOverallTotalBaseOnCustomerIdForOneDay(custid, AdminDao.convertDateFormat(rdate, "dd/MM/yyyy", "dd-MMM-yy"));
			overAllTotalInfo = CustomerMetaInfo.getOverallTotalBasedOnCustomerIdForOneDay(custid, AdminDao.convertDateFormat(rdate, "dd/MM/yyyy", "dd-MMM-yy"));
			
				v.add("Overall");
				v.add(overAllTotalInfo.get(6));
				v.add(overAllTotalInfo.get(8));
				v.add(overAllTotalInfo.get(12));
				v.add(overAllTotalInfo.get(13));
				v.add(overAllTotalInfo.get(14));
				v.add(overAllTotalInfo.get(15));
				v.add(overAllTotalInfo.get(17));
				v.add(overAllTotalInfo.get(16));
				
				tranList.add(v);
				
			return tranList;*/
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MOVERALL_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			DaoUtility.displayQueryWithParameter(53, sqlQuery, custid,reportdate,reportdate);
		}
		if (RType.equals("Y")) { // Calendar cal = Calendar.getInstance();
			int day = reportdate.getDay();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YOVERALL_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			DaoUtility.displayQueryWithParameter(53, sqlQuery, custid,pdate,ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(53, rs);
				Vector tranVector = new Vector();
				tranVector.add("OverALL");
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getOverallTotalAdmin(String custid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_OVERALL_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MOVERALL_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) { // Calendar cal = Calendar.getInstance();
			int day = reportdate.getDay();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YOVERALL_TOTAL_ADMIN;

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
		if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.SELECT_YOVERALL_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add("OverALL");
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));

				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));

				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranVector.add(rs.getObject("CUM_GENERATION"));
				tranVector.add(rs.getObject("CUM_OPERATINGHRS"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}	
	
public List getEBDataDetail(String fd,String td,String site, String state, String custid)
	throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(fd); 
java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

java.util.Date tfd = format.parse(td);
java.sql.Date todate = new java.sql.Date(tfd.getTime());

//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
String nfdate=convertDateFormat(fd, "dd/MM/yyyy", "dd-MMM-yyyy");
String ntdate=convertDateFormat(td, "dd/MM/yyyy", "dd-MMM-yyyy");

if (!site.equals("NA")) {
	sqlQuery ="SELECT * FROM VW_EB_DATA WHERE S_SITE_ID IN("+ site +") AND D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' ORDER BY S_CUSTOMER_ID,D_READING_DATE";
	prepStmt = conn.prepareStatement(sqlQuery);		
}

else if (!custid.equals("NA")) { 
	sqlQuery = "SELECT * FROM VW_EB_DATA WHERE S_CUSTOMER_ID IN("+ custid +") AND D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' ORDER BY S_CUSTOMER_ID,D_READING_DATE";
	prepStmt = conn.prepareStatement(sqlQuery);
}


// ps.setObject(1,item.toUpperCase() + "%");

try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("S_EB_DESCRIPTION"));
		tranVector.add(rs.getDate("D_READING_DATE"));
		tranVector.add(rs.getObject("KWHIMPORT"));
		tranVector.add(rs.getObject("KWHEXPORT"));

		tranVector.add(rs.getObject("KVAHIMP"));
		tranVector.add(rs.getObject("KVAHEXP"));
		tranVector.add(rs.getObject("RKVAHIMP"));
		tranVector.add(rs.getObject("RKVAHEXP"));
		tranVector.add(rs.getObject("KVAHIMPLAG"));
		tranVector.add(rs.getObject("KVAHIMPLEAD"));

		tranVector.add(rs.getObject("KVAHEXPLAG"));
		tranVector.add(rs.getObject("KVAHEXPLEAD"));
		tranVector.add(rs.getObject("JRKWHIMP"));
		tranVector.add(rs.getObject("JRKWHEXP"));
		
		tranVector.add(rs.getObject("S_SITE_NAME"));
		tranVector.add(rs.getObject("S_STATE_NAME"));
		tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
	
}
return tranList;
}

public List getBillDataDetail(String month,String year,String site, String state, String custid)
throws Exception {
//public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

PreparedStatement ps = null;
PreparedStatement ps1 = null;
ResultSet rswec = null;
ResultSet res = null;

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
DecimalFormat df = new DecimalFormat("0.##");
SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd");
//java.util.Date ffd = format.parse(rdate);
//java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
Float expr,imp,relead,relag,rilead,rilag,kexp,kimp;

if (!site.equals("NA")) {
sqlQuery = "select a.*,N_MULTI_FACTOR,TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'YYYY') AS MYEAR, C.S_SITE_NAME,b.S_EBSHORT_DESCR,d.s_state_name   from vw_billing_eb "+ 
" a,tbl_eb_master b,TBL_SITE_MASTER C,tbl_state_master d where a.s_customer_id= b.s_customer_id and "+
" a.s_eb_id=b.s_eb_id  and b.s_site_id=C.s_site_id and b.s_site_id IN("+ site +") and c.s_state_id=d.s_state_id  "+
"  and TO_CHAR(a.D_READING_DATE ,'MM') ='"+ month +"' and TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'yyyy')='"+ year +"' ORDER BY "+
" d.s_state_name,C.S_SITE_NAME,A.S_CUSTOMER_NAME,b.S_EBSHORT_DESCR DESC";

prepStmt = conn.prepareStatement(sqlQuery);		
}

else if (!custid.equals("NA")) { 
sqlQuery = "select a.*,N_MULTI_FACTOR,TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD- MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'YYYY') AS MYEAR, C.S_SITE_NAME,b.S_EBSHORT_DESCR,d.s_state_name   from vw_billing_eb "+ 
" a,tbl_eb_master b,TBL_SITE_MASTER C,tbl_state_master d where a.s_customer_id= b.s_customer_id and "+
" a.s_eb_id=b.s_eb_id and a.s_customer_id IN("+ custid +")  and b.s_site_id=C.s_site_id and c.s_state_id=d.s_state_id  "+
"  and TO_CHAR(a.D_READING_DATE ,'MM') ='"+ month +"' and TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'yyyy')='"+ year +"' ORDER BY "+
" A.S_CUSTOMER_NAME,b.S_EBSHORT_DESCR,C.S_SITE_NAME,d.s_state_name";


prepStmt = conn.prepareStatement(sqlQuery);
}

//ps.setObject(1,item.toUpperCase() + "%");

try {
	rs = prepStmt.executeQuery();
	int i = 0;
	double t_exp=0.0,t_imp=0.0,t_exprkvahlag=0.0,t_exprkvahlead=0.0,t_imprkvahlag=0.0,t_imprkvahlead=0.0,t_kvahexp=0.0,t_kvahimp=0.0,totgen=0.0;
	int totcnt=0;
	boolean exist= true;
	while (rs.next()) {
		
		sqlQuery = CustomerSQLC.GET_EB_BILLING_REPORT_DATE_1;
		ps = conn.prepareStatement(sqlQuery);
		ps.setObject(1, rs.getObject("S_EB_ID"));
		
		
			java.util.Date ffd = newformat.parse(rs.getObject("D_READING_DATE").toString());
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		ps.setObject(2, reportdate);
		
		rswec = ps.executeQuery();
		if(rswec.next())
		{
			exist=true;		
		  //System.out.println(expr);
		  //System.out.println(imp);
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_SITE_NAME"));
			tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
			tranVector.add(rs.getObject("MYEAR"));
			
			String padate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
			
			tranVector.add(padate);
			String padate1="";
			if(rswec.getObject("D_READING_DATE")!=null) 
			{
			   padate1 =convertDateFormat(rswec.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
			   
			   tranVector.add(padate1);
			   
				
				double k_exp=Double.parseDouble(rs.getObject("KWHEXPORT").toString());
				double k_imp=Double.parseDouble(rs.getObject("KWHIMPORT").toString());
				
				double k_exprkvahlag=Double.parseDouble(rs.getObject("RKVAHEXPLAG").toString());
				double k_exprkvahlead=Double.parseDouble(rs.getObject("RKVAHEXPLEAD").toString());
				
				double k_imprkvahlag=Double.parseDouble(rs.getObject("RKVAHIMPLAG").toString());
				double k_imprkvahlead=Double.parseDouble(rs.getObject("RKVAHIMPLEAD").toString());
				
				double k_kvahexp=Double.parseDouble(rs.getObject("KVAHEXP").toString());
				double k_kvahimp=Double.parseDouble(rs.getObject("KVAHIMP").toString());
				
			//	 k_exp=k_exp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
			//	 k_imp=k_imp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
			//	 k_exprkvahlag=k_exprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
			//	 k_exprkvahlead=k_exprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
			//	 k_imprkvahlag=k_imprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
			//	 k_imprkvahlead=k_imprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
			//	 k_kvahexp=k_kvahexp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
			//	 k_kvahimp=k_kvahimp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
			//	 t_exp=t_exp+k_exp;
				// t_imp=t_imp+k_imp;
				
			//	 t_exprkvahlag=t_exprkvahlag+k_exprkvahlag;
			//	 t_exprkvahlead=t_exprkvahlead+k_exprkvahlead;
				
			//	t_imprkvahlag=t_imprkvahlag+k_imprkvahlag;
			//	 t_imprkvahlead=t_imprkvahlead+k_imprkvahlead;
				
			//	 t_kvahexp=t_kvahexp+k_kvahexp;
			//	 t_kvahimp=t_kvahimp+k_kvahimp;
				
				tranVector.add(df.format(k_exp));
				tranVector.add(df.format(k_imp));
				tranVector.add(df.format(k_exprkvahlag));
				tranVector.add(df.format(k_exprkvahlead));
				tranVector.add(df.format(k_imprkvahlag));
				tranVector.add(df.format(k_imprkvahlead));
				tranVector.add(df.format(k_kvahexp));
				tranVector.add(df.format(k_kvahimp));
				
				sqlQuery = CustomerSQLC.GET_WEC_GENERATION;
				ps1 = conn.prepareStatement(sqlQuery);
				ps1.setObject(1, rs.getObject("S_EB_ID"));
				ps1.setObject(2, rswec.getObject("D_READING_DATE"));
				ps1.setObject(3, rs.getObject("D_READING_DATE"));
				res = ps1.executeQuery();
				if(res.next())
				{
					tranVector.add(res.getObject("generation") == null ? "0" : res.getObject("generation"));
					totgen=res.getObject("generation") == null ? 0 : Double.parseDouble(res.getObject("generation").toString());
				}
				else
				{ 
					tranVector.add("0");
					
				}
				ps1.close();
				res.close();
				
				
				sqlQuery = CustomerSQLC.GET_WEC_COUNT;
				ps1 = conn.prepareStatement(sqlQuery);
				ps1.setObject(1, rs.getObject("S_EB_ID"));
				res = ps1.executeQuery();
				if(res.next())
				{
					tranVector.add(res.getObject("cnt") == null ? "0": res.getObject("cnt"));
					totcnt=totcnt+ (res.getObject("cnt") == null ? 0 : Integer.parseInt(res.getObject("cnt").toString()));
				
				}
				else
				{
					tranVector.add("0");
					
				}
				ps1.close();
				res.close();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				
				tranList.add(i, tranVector);
				 i++;
				exist=true;	
			}
			   else
				exist=false;
			
		}
		else
		{
			exist=false;
		}
		if(exist==false)
		{
			
			  //System.out.println(expr);
			  //System.out.println(imp);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
				tranVector.add(rs.getObject("MYEAR"));
				
				String padate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
				
				tranVector.add(padate);
				String padate1 ="";
				tranVector.add(padate1);				
				
				double k_exp=Double.parseDouble(rs.getObject("KWHEXPORT").toString());
				double k_imp=Double.parseDouble(rs.getObject("KWHIMPORT").toString());
				
				double k_exprkvahlag=Double.parseDouble(rs.getObject("RKVAHEXPLAG").toString());
				double k_exprkvahlead=Double.parseDouble(rs.getObject("RKVAHEXPLEAD").toString());
				
				double k_imprkvahlag=Double.parseDouble(rs.getObject("RKVAHIMPLAG").toString());
				double k_imprkvahlead=Double.parseDouble(rs.getObject("RKVAHIMPLEAD").toString());
				
				double k_kvahexp=Double.parseDouble(rs.getObject("KVAHEXP").toString());
				double k_kvahimp=Double.parseDouble(rs.getObject("KVAHIMP").toString());
				
				// k_exp=k_exp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				// k_imp=k_imp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
				// k_exprkvahlag=k_exprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				// k_exprkvahlead=k_exprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
				// k_imprkvahlag=k_imprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				// k_imprkvahlead=k_imprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				
				// k_kvahexp=k_kvahexp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				// k_kvahimp=k_kvahimp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
				 //=t_exp+k_exp;
				 //t_imp=t_imp+k_imp;
				
				// t_exprkvahlag=t_exprkvahlag+k_exprkvahlag;
				// t_exprkvahlead=t_exprkvahlead+k_exprkvahlead;
				
				// t_imprkvahlag=t_imprkvahlag+k_imprkvahlag;
				// t_imprkvahlead=t_imprkvahlead+k_imprkvahlead;
				
				// t_kvahexp=t_kvahexp+k_kvahexp;
				// t_kvahimp=t_kvahimp+k_kvahimp;
				
				tranVector.add(df.format(k_exp));
				tranVector.add(df.format(k_imp));
				tranVector.add(df.format(k_exprkvahlag));
				tranVector.add(df.format(k_exprkvahlead));
				tranVector.add(df.format(k_imprkvahlag));
				tranVector.add(df.format(k_imprkvahlead));
				tranVector.add(df.format(k_kvahexp));
				tranVector.add(df.format(k_kvahimp));				
				
				tranVector.add("-");
				
				sqlQuery = CustomerSQLC.GET_WEC_COUNT;
				ps1 = conn.prepareStatement(sqlQuery);
				ps1.setObject(1, rs.getObject("S_EB_ID"));
				res = ps1.executeQuery();
				if(res.next())
				{
					tranVector.add(res.getObject("cnt") == null ? "0" :res.getObject("cnt"));
					//totcnt=totcnt+Integer.parseInt(res.getObject("cnt ").toString());
				}
				else
				{
					tranVector.add("0");
					
				}
				ps1.close();
				res.close();
				
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				
				tranList.add(i, tranVector);
				
			    i++;
		}
		rswec.close();
		ps.close();
	}	
	
	prepStmt.close();
	rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}

public List getStateTotal(String custid, String rdate, String RType)
		throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	try {
	if (RType.equals("D")) {
		sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL; 
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		DaoUtility.displayQueryWithParameter(51,sqlQuery,custid,reportdate,reportdate);
	}
	if (RType.equals("M")) {
		sqlQuery = CustomerSQLC.SELECT_MSTATEWISE_TOTAL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		
		logger.debug("Customer Id : " + custid);
		logger.debug("Report Date : " + reportdate);
		DaoUtility.displayQueryWithParameter(51,sqlQuery,custid,reportdate,reportdate);
	}
	if (RType.equals("Y")) {
		int day = reportdate.getDate();
		int month = reportdate.getMonth() + 1;
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}
		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);
		
		sqlQuery = CustomerSQLC.SELECT_YSTATEWISE_TOTAL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, pdate);
		prepStmt.setObject(3, ndate);
		DaoUtility.displayQueryWithParameter(51,sqlQuery,custid,pdate,ndate);
		
	}
	
	// ps.setObject(1,item.toUpperCase() + "%");

	
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			DaoUtility.getRowCount(51, rs);
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_state_name"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			tranVector.add(rs.getObject("LULLHRS"));
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("S_state_ID"));
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranVector.add(rs.getObject("TRIALRUN"));
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

public List getSubstationTotal(String custid, String rdate, String RType)
	throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	if (RType.equals("D")) {
	sqlQuery = CustomerSQLC.SELECT_SUBSTATIONWISE_TOTAL; 
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, reportdate);
	}
	if (RType.equals("M")) {
	sqlQuery = CustomerSQLC.SELECT_MSTATEWISE_TOTAL;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, reportdate);
	}
	if (RType.equals("Y")) {
	int day = reportdate.getDate();
	int month = reportdate.getMonth() + 1;
	int year = reportdate.getYear() - 100;
	int nyear = year;
	//System.out.println("Month: " + month);
	//System.out.println("Year: " + year);
	if (month >= 4) {
		nyear = year + 1;
	} else {
		nyear = year;
		year = year - 1;
	}
	String pdate = "01-APR-" + Integer.toString(year);
	String ndate = "31-MAR-" + Integer.toString(nyear);
	
	sqlQuery = CustomerSQLC.SELECT_YSTATEWISE_TOTAL;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, pdate);
	prepStmt.setObject(3, ndate);
	}
	
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("S_state_name"));
		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("OPERATINGHRS"));
		tranVector.add(rs.getObject("LULLHRS"));
		tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
		tranVector.add(rs.getObject("S_state_ID"));
		tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
		tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
		tranVector.add(rs.getObject("TRIALRUN"));
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
		
	}
	return tranList;
}

	
	public List getStateTotalByLogid(String custid, String rdate, String RType)
		throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	
		sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL_BY_LOG_ID; 
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, reportdate);
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_state_name"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			tranVector.add(rs.getObject("LULLHRS"));
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("S_state_ID"));
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
			tranVector.add(rs.getObject("S_CUSTOMER_ID").toString());
			tranVector.add(rs.getObject("TRIALRUN").toString());
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

	
	
	
/*	public List getERDADetail(String rdate)
	throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

JDBCUtils conmanager = new JDBCUtils();
Connection conn = conmanager.getConnection();
PreparedStatement prepStmt = null;
PreparedStatement ps = null;
ResultSet rs = null;
ResultSet rs1 = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
String fdate=rdate.substring(2);

fdate="01"+fdate;


java.util.Date ffd = format.parse(rdate);
java.util.Date firstdt = format.parse(fdate);
java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
java.sql.Date firstdate = new java.sql.Date(firstdt.getTime());


try {
sqlQuery = CustomerSQLC.SELECT_GET_ERDADETAIL;
prepStmt = conn.prepareStatement(sqlQuery);
	


// ps.setObject(1,item.toUpperCase() + "%");


	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
		tranVector.add(rs.getObject("S_STATE_NAME"));
		tranVector.add(rs.getObject("S_SITE_NAME"));
		
		
		
		sqlQuery = CustomerSQLC.SELECT_WEC_CAPACITY;;
		ps = conn.prepareStatement(sqlQuery);
		ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
		ps.setObject(2,rs.getObject("S_SITE_ID").toString());
		ps.setObject(3,rs.getObject("S_CUSTOMER_ID").toString());
		rs1 = ps.executeQuery();
		while (rs1.next()) 
		{
			tranVector.add(rs1.getObject("lcapacity"));
		}
		ps.close();
		rs1.close();
		
		
		
		sqlQuery = CustomerSQLC.SELECT_GET_READING;;
		ps = conn.prepareStatement(sqlQuery);
		ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
		ps.setObject(2,reportdate);
		ps.setObject(3,rs.getObject("S_SITE_ID").toString());
		ps.setObject(4,rs.getObject("S_CUSTOMER_ID").toString());
		rs1 = ps.executeQuery();
		while (rs1.next()) 
		{
			tranVector.add(rs1.getObject("A_VAL"));
		}
		ps.close();
		rs1.close();
		
		
		
		sqlQuery = CustomerSQLC.SELECT_GET_READING_CUM;;
		ps = conn.prepareStatement(sqlQuery);
		ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
		ps.setObject(2,firstdate);
		ps.setObject(3,reportdate);
		ps.setObject(4,rs.getObject("S_SITE_ID").toString());
		ps.setObject(5,rs.getObject("S_CUSTOMER_ID").toString());
		rs1 = ps.executeQuery();
		
		while (rs1.next()) 
		{
			tranVector.add(rs1.getObject("A_VAL"));
			
			
		}
		rs1.close();
		ps.close();
		
		
		
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();

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
		if (conn != null) {
			conn.close();
			conn = null;

			conmanager.closeConnection();
			conmanager = null;
		}
	} catch (Exception e) {
		prepStmt = null;
		rs = null;
		if (conn != null) {
			conn.close();
			conn = null;

			conmanager.closeConnection();
			conmanager = null;
		}
	}
}
return tranList;
}*/


	public List getMailData(String custid, String rdate,String Type)
		throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
	
		/*sqlQuery = CustomerSQLC.SELECT_MAIL_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		
		prepStmt.setObject(1, reportdate);
		prepStmt.setObject(2, custid);*/
		
		sqlQuery = "select a.d_reading_date,c.s_site_name,a.s_wecshort_descr,a.GENERATION,SECTOTIME(a.OPERATINGHRS) AS OPERATINGHRS," +
		" SECTOTIME(a.EXTERNALFAULT+a.EXTERNALSD) as GRIDDOWN,SECTOTIME(a.MACHINEFAULT+a.MACHINESD) AS MACHINEDOWN  from vw_tabularform_wec a," +
		" tbl_eb_master b, tbl_site_master c where A.D_READING_DATE='"+reportdate+"' AND a.s_eb_id=b.s_eb_id AND B.S_CUSTOMER_ID in ('"+custid+"') and b.S_SITE_ID=c.S_SITE_ID " +
		" order by c.s_site_name,a.s_wecshort_descr";
		prepStmt = conn.prepareStatement(sqlQuery);
	
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("d_reading_date"));
			tranVector.add(rs.getObject("s_site_name"));
			tranVector.add(rs.getObject("s_wecshort_descr"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			tranVector.add(rs.getObject("GRIDDOWN"));
			tranVector.add(rs.getObject("MACHINEDOWN"));
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

	public List getMailDataCustomer(String custid, String rdate,String Type)
		throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
	
		sqlQuery = CustomerSQLC.SELECT_MAIL_DATA_CUSTOMER;
		prepStmt = conn.prepareStatement(sqlQuery);
		
		prepStmt.setObject(1, reportdate);
		prepStmt.setObject(2, custid);
		
		sqlQuery = "select a.d_reading_date,c.s_site_name,a.s_wecshort_descr,a.GENERATION,a.OPERATINGHRS,a.LULLHRS,a.MAVIAL,a.GAVIAL,a.CFACTOR  " +
		" ,a.MIAVIAL as MIAVIAL,a.GIAVIAL as GIAVIAL from VW_WEC_CHKUNDERTRIAL a,tbl_eb_master b,"+
		 " tbl_site_master c where A.D_READING_DATE='"+reportdate+"' AND a.s_eb_id=b.s_eb_id AND B.S_CUSTOMER_ID in ('"+custid+"') and b.S_SITE_ID=c.S_SITE_ID " +
		 " order by c.s_site_name,a.s_wecshort_descr ";
		prepStmt = conn.prepareStatement(sqlQuery);
	
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("d_reading_date"));
			tranVector.add(rs.getObject("s_site_name"));
			tranVector.add(rs.getObject("s_wecshort_descr"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			tranVector.add(rs.getObject("LULLHRS"));
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

public List getMailDataCLP(String custid, String rdate,String Type)	throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());

AdminDao ad = new AdminDao();
String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");

	/*sqlQuery = CustomerSQLC.SELECT_MAIL_DATA_CUSTOMER;
	prepStmt = conn.prepareStatement(sqlQuery);
	
	prepStmt.setObject(1, reportdate);
	prepStmt.setObject(2, custid);*/
	
	sqlQuery = "select a.d_reading_date,c.s_site_name,a.s_wecshort_descr," +
			"a.TOTAL_GENERATION,(a.TOTAL_GENERATION-a.GENERATION)AS PREV_GENERATION,a.GENERATION," +
			"a.TOTAL_OPERATINGHRS,SECTOTIME(TIMETOSECOND(a.TOTAL_OPERATINGHRS)-TIMETOSECOND(a.OPERATINGHRS))as PREV_OPERATINGHRS,a.OPERATINGHRS,a.LULLHRS,a.MAVIAL,a.GAVIAL,a.CFACTOR, " +
			"a.MIAVIAL as MIAVIAL,a.GIAVIAL as GIAVIAL " +
			"FROM VW_WEC_CHKUNDERTRIAL A, TBL_EB_MASTER B, TBL_SITE_MASTER C "+
			"WHERE A.D_READING_DATE='"+reportdate+"' " +
			"AND A.S_EB_ID = B.S_EB_ID " +
			"AND B.S_CUSTOMER_ID IN ('"+custid+"') " +
			"AND B.S_SITE_ID = c.S_SITE_ID " +
			"ORDER BY C.s_site_name, A.s_wecshort_descr ";
	
	prepStmt = conn.prepareStatement(sqlQuery);

	// ps.setObject(1,item.toUpperCase() + "%");

try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("d_reading_date"));
		tranVector.add(rs.getObject("s_site_name"));
		tranVector.add(rs.getObject("s_wecshort_descr"));
		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("OPERATINGHRS"));
		tranVector.add(rs.getObject("LULLHRS"));
		tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
		tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
		tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
		tranVector.add(rs.getObject("TOTAL_GENERATION"));
		tranVector.add(rs.getObject("TOTAL_OPERATINGHRS"));
		tranVector.add(rs.getObject("PREV_GENERATION"));
		tranVector.add(rs.getObject("PREV_OPERATINGHRS"));
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}	
	
public List getStateTotalAdmin(String custid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
		} 
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MSTATEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YSTATEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		
		if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.SELECT_YSTATEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
		
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_state_name"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));

				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));

				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("S_state_ID"));

				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranVector.add(rs.getObject("CUM_GENERATION"));
				tranVector.add(rs.getObject("CUM_OPERATINGHRS"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	
	
public List getStateWiseDash(String stateid, String fdate, String tdate) 
		 throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	//stateid="1000000038"+""","+"1000000067";
	//stateid=stateid.replace("or","\',\'");
	//stateid="1000000067,1000000038";
	//System.out.println("stateid:"+stateid);
	//stateid="eb.S_STATE_ID IN('1000000067')";
	SimpleDateFormat format = new SimpleDateFormat("");
	
	String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
	String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");
	//java.util.Date ffd = format.parse(fdate);
	//java.sql.Date frdate = new java.sql.Date(ffd.getTime());
	//java.util.Date tfd = format.parse(tdate);
	//java.sql.Date todate = new java.sql.Date(tfd.getTime());
		sqlQuery = "select eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
					"round(avg(wec.WAVIAL),2) as WAVIAL,substr(sectotime(avg(timetosecond(wec.OPERATINGHRS))),0,5) as OPERATINGHRS,substr(sectotime(avg(timetosecond(wec.LULLHRS))),0,5) as LULLHRS," +
					"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
					"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
					"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
					"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
					"where eb.S_EB_ID=wec.S_EB_ID "+ 
					"and eb.S_STATE_ID IN("+stateid+") and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' " +
					"group by eb.S_state_ID,eb.S_state_name";
		prepStmt = conn.prepareStatement(sqlQuery);
		//prepStmt.setObject(1, stateid);
		//prepStmt.setObject(2, frdate);
		//prepStmt.setObject(3, todate);
	
	
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_state_name"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS").toString().replace(":", "."));
			tranVector.add(rs.getObject("LULLHRS").toString().replace(":", "."));
			//System.out.println("liasas"+rs.getObject("LULLHRS").toString().replace(":", "."));
			tranVector.add(rs.getObject("MACHINEFAULT"));
			tranVector.add(rs.getObject("MACHINESD"));
			tranVector.add(rs.getObject("INTERNALFAULT"));
			tranVector.add(rs.getObject("INTERNALSD"));
			tranVector.add(rs.getObject("EXTERNALFAULT"));
			tranVector.add(rs.getObject("EXTERNALSD"));
	
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("S_state_ID"));
	
			tranVector.add(rs.getObject("GIAVIAL"));
			tranVector.add(rs.getObject("WAVIAL"));			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.clearParameters();
		prepStmt.close();
		rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}
public List getStateWiseDashAvg(String stateid, String fdate, String tdate) 
throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";
//stateid="1000000038"+""","+"1000000067";
//stateid=stateid.replace("or","\',\'");
//stateid="1000000067,1000000038";
//System.out.println("stateid:"+stateid);
//stateid="eb.S_STATE_ID IN('1000000067')";
SimpleDateFormat format = new SimpleDateFormat("");

String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");
//java.util.Date ffd = format.parse(fdate);
//java.sql.Date frdate = new java.sql.Date(ffd.getTime());
//java.util.Date tfd = format.parse(tdate);
//java.sql.Date todate = new java.sql.Date(tfd.getTime());

sqlQuery = "select eb.S_state_ID,eb.S_state_name, round(avg(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
			"round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
			"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
			"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
			"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
			"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
			"where eb.S_EB_ID=wec.S_EB_ID "+ 
			"and eb.S_STATE_ID IN("+stateid+") " +
			"and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' " +
			"and NVL(D_TRANSFER_DATE,'"+frdate+"') <= '"+frdate+"' "+
			"group by eb.S_state_ID,eb.S_state_name";
prepStmt = conn.prepareStatement(sqlQuery);

try {
rs = prepStmt.executeQuery();
int i = 0;
while (rs.next()) {
	Vector tranVector = new Vector();
	tranVector.add(rs.getObject("S_state_name"));
	tranVector.add(rs.getObject("GENERATION"));
	tranVector.add(rs.getObject("OPERATINGHRS").toString().replace(":", "."));
	tranVector.add(rs.getObject("LULLHRS").toString().replace(":", "."));
	//System.out.println("liasas"+rs.getObject("LULLHRS").toString().replace(":", "."));
	tranVector.add(rs.getObject("MACHINEFAULT"));
	tranVector.add(rs.getObject("MACHINESD"));
	tranVector.add(rs.getObject("INTERNALFAULT"));
	tranVector.add(rs.getObject("INTERNALSD"));
	tranVector.add(rs.getObject("EXTERNALFAULT"));
	tranVector.add(rs.getObject("EXTERNALSD"));

	tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
	tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
	tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
	tranVector.add(rs.getObject("S_state_ID"));

	tranVector.add(rs.getObject("GIAVIAL"));
	tranVector.add(rs.getObject("WAVIAL"));			
	tranList.add(i, tranVector);
	i++;
}
prepStmt.clearParameters();
prepStmt.close();
rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}

public List getStateWiseAverage(String fdate, String tdate) 
	throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("");
	
	String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
	String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");
	
	sqlQuery = "select S_STATE_ID,S_STATE_NAME,REPLACE(SUBSTR(sectotime(avg(total)),0,5),':','.') as adelay  from ( "+
			   "select S_STATE_ID,S_STATE_NAME,S_SITE_NAME, timetosecond(min(MINTIME)) total from vw_average_delay where  D_READING_DATE BETWEEN '"+frdate+"' AND '"+todate+"'  "+
			   "group by S_STATE_ID,S_STATE_NAME,S_SITE_NAME,d_reading_dATE having min(MINTIME)<>'00:00' order by S_SITE_NAME) group by S_STATE_ID,S_STATE_NAME";
				 			 
	prepStmt = conn.prepareStatement(sqlQuery);
	
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("S_STATE_ID"));
		tranVector.add(rs.getObject("S_state_name"));
		tranVector.add(rs.getObject("adelay"));
		
		
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.clearParameters();
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

public List getSiteWiseAverage(String id,String fdate, String tdate) 
throws Exception {
//public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("");

String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");

sqlQuery = "select S_SITE_NAME,S_STATE_NAME,REPLACE(SUBSTR(sectotime(AVG(total)),0,5),':','.') as adelay  from ( "+
		   "select S_SITE_NAME,S_STATE_NAME, timetosecond(min(MINTIME)) total from vw_average_delay where S_STATE_ID='"+id+"' AND D_READING_DATE BETWEEN '"+frdate+"' AND '"+todate+"'  "+
		   " group by S_SITE_NAME,S_STATE_NAME,d_reading_dATE having min(MINTIME)<>'00:00') group by S_SITE_NAME,S_STATE_NAME order by S_SITE_NAME";
	
	/*sqlQuery = " SELECT S_SITE_NAME,S_STATE_NAME,AVG(adelay)  AS adelay FROM( "+
			" select S_SITE_NAME,S_STATE_NAME,REPLACE(SUBSTR(sectotime(sum(total)),0,5),':','.') as adelay  from ( "+
			" select S_STATE_ID,S_STATE_NAME,S_SITE_NAME, timetosecond(min(MINTIME)) total from vw_average_delay where S_STATE_ID='"+id+"' AND D_READING_DATE BETWEEN '"+frdate+"' AND '"+todate+"'  "+  
		    " group by S_STATE_ID,S_STATE_NAME,S_SITE_NAME,d_reading_dATE having min(MINTIME)<>'00:00' order by S_SITE_NAME) group by S_SITE_NAME,S_STATE_NAME) group by S_SITE_NAME,S_STATE_NAME";
    */	
prepStmt = conn.prepareStatement(sqlQuery);


try {
rs = prepStmt.executeQuery();
int i = 0;
while (rs.next()) {
	Vector tranVector = new Vector();

	tranVector.add(rs.getObject("S_SITE_NAME"));
	tranVector.add(rs.getObject("adelay"));
	tranVector.add(rs.getObject("S_STATE_NAME"));
	
	tranList.add(i, tranVector);
	i++;
}
prepStmt.clearParameters();
prepStmt.close();
rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}
public List getSiteWiseAverageGeneration(String id,String fdate, String tdate) throws Exception {
//public List searchempbyfilter(DynaBean dynaBean) throws Exception {


	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("");

String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");

sqlQuery = "select eb.S_site_name,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
			"round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
			"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
			"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
			"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
			"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
			"where eb.S_EB_ID=wec.S_EB_ID "+ 
			"and eb.S_STATE_ID IN("+id+") and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' " +
			"group by eb.S_site_name,eb.S_state_ID,eb.S_state_name";	
	
prepStmt = conn.prepareStatement(sqlQuery);

try {
rs = prepStmt.executeQuery();
int i = 0;
while (rs.next()) {
	Vector tranVector = new Vector();

	tranVector.add(rs.getObject("S_site_name"));
	tranVector.add(rs.getObject("GENERATION"));
	tranVector.add(rs.getObject("S_state_name"));
	
	tranList.add(i, tranVector);
	i++;
}
prepStmt.clearParameters();
prepStmt.close();
rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}
public List getWECTypeWiseAverageGeneration(String id,String fdate, String tdate) throws Exception {
//	public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";

	SimpleDateFormat format = new SimpleDateFormat("");

	String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
	String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");

	sqlQuery = "select WEC.WECTYPE, eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL, "+
			"round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS, "+
			"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD, "+
			"SECTOTIME(SUM(TIMETOSECOND(WEC.INTERNALFAULT))) AS INTERNALFAULT,SECTOTIME(SUM(TIMETOSECOND(WEC.EXTERNALFAULT))) AS EXTERNALFAULT,SECTOTIME(SUM(TIMETOSECOND(WEC.EXTERNALSD))) AS EXTERNALSD, "+
			"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR, count(wec.WECTYPE)as weccount "+
			"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb "+
			"WHERE EB.S_EB_ID=WEC.S_EB_ID "+
			"and eb.S_STATE_ID IN("+id+") " +
			"and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' " +			
			"GROUP BY WEC.WECTYPE, eb.S_state_ID, eb.S_state_name ORDER BY WEC.WECTYPE";	
		
	prepStmt = conn.prepareStatement(sqlQuery);

	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while (rs.next()) {
		Vector tranVector = new Vector();

		tranVector.add(rs.getObject("WECTYPE"));
		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("S_state_name"));
		tranVector.add(rs.getObject("weccount"));
		
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.clearParameters();
	prepStmt.close();
	rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}
public List getSiteWiseAverageGenerationAvg(String id,String fdate, String tdate) throws Exception {
//	public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";

	SimpleDateFormat format = new SimpleDateFormat("");

	String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
	String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");

	sqlQuery = "select eb.S_site_name,eb.S_state_ID,eb.S_state_name, round(avg(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
				"round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
				"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
				"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
				"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
				"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
				"where eb.S_EB_ID=wec.S_EB_ID "+ 
				"and eb.S_STATE_ID IN("+id+") and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' " +
				"and NVL(D_TRANSFER_DATE,'"+frdate+"') <= '"+frdate+"' "+
				"group by eb.S_site_name,eb.S_state_ID,eb.S_state_name";	
		
	prepStmt = conn.prepareStatement(sqlQuery);

	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
	
			tranVector.add(rs.getObject("S_site_name"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("S_state_name"));
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.clearParameters();
		prepStmt.close();
		rs.close();
	
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}
public List getWECTypeWiseAverageGenerationAvg(String id,String fdate, String tdate) throws Exception {
//	public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";

	SimpleDateFormat format = new SimpleDateFormat("");

	String frdate = convertDateFormat(fdate, "dd/MM/yyyy","dd-MMM-yy");
	String todate = convertDateFormat(tdate, "dd/MM/yyyy","dd-MMM-yy");

	sqlQuery = "select WEC.WECTYPE, eb.S_state_ID,eb.S_state_name, round(avg(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL, "+
			"round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS, "+
			"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD, "+
			"SECTOTIME(SUM(TIMETOSECOND(WEC.INTERNALFAULT))) AS INTERNALFAULT,SECTOTIME(SUM(TIMETOSECOND(WEC.EXTERNALFAULT))) AS EXTERNALFAULT,SECTOTIME(SUM(TIMETOSECOND(WEC.EXTERNALSD))) AS EXTERNALSD, "+
			"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR, count(wec.WECTYPE)as weccount "+
			"from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb "+
			"WHERE EB.S_EB_ID=WEC.S_EB_ID "+
			"and eb.S_STATE_ID IN("+id+") and D_READING_DATE BETWEEN '"+frdate+"' and '"+todate+"' "+
			"and NVL(D_TRANSFER_DATE,'"+frdate+"') <= '"+frdate+"' "+
			"GROUP BY WEC.WECTYPE, eb.S_state_ID, eb.S_state_name ORDER BY WEC.WECTYPE";
		
	prepStmt = conn.prepareStatement(sqlQuery);

	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
	
			tranVector.add(rs.getObject("WECTYPE"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("S_state_name"));
			tranVector.add(rs.getObject("weccount"));
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.clearParameters();
		prepStmt.close();
		rs.close();
	
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
	}
	return tranList;
}

	
	public List getState(String custid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_STATEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MSTATEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YSTATEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_state_name"));
				tranVector.add(rs.getObject("S_state_ID"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getSite(String custid, String rdate, String stateid,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_SITEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, stateid);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MSITEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, stateid);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YSITEWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, stateid);
		}

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_site_name"));

				tranVector.add(rs.getObject("S_site_ID"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWEC(String custid, String rdate, String siteid, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_WECWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, siteid);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MWECWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, siteid);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YWECWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, siteid);
		}

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));

				tranVector.add(rs.getObject("S_wec_ID"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getSiteTotal(String custid, String rdate, String stateid,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {


		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_SITEWISE_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, stateid);
			prepStmt.setObject(4, reportdate);
			DaoUtility.displayQueryWithParameter(52, sqlQuery, custid,reportdate,stateid,reportdate);
		}
		
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MSITEWISE_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, stateid);
			DaoUtility.displayQueryWithParameter(52, sqlQuery, custid,reportdate,reportdate,stateid);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YSITEWISE_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, stateid);
			DaoUtility.displayQueryWithParameter(52, sqlQuery, custid,pdate,ndate,stateid);
			
		}

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(52, rs);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_site_name"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("S_site_ID"));
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranVector.add(rs.getObject("TRIALRUN"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getSiteTotalAdmin(String custid, String rdate, String stateid,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_SITEWISE_TOTAL_admin;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, stateid);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_MSITEWISE_TOTAL_admin;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, stateid);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_YSITEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, stateid);
		}
		if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.SELECT_YSITEWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, stateid);
		}

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_site_name"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));

				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));

				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("S_site_ID"));

				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranVector.add(rs.getObject("CUM_GENERATION"));
				tranVector.add(rs.getObject("CUM_OPERATINGHRS"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getStateTotalByID(String custid, String rdate, String Stateid,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL_BY_ID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, Stateid);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL_BY_ID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_STATEWISE_TOTAL_BY_ID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_state_name"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("S_state_ID"));
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

public List getEBHeading(String custid, String rdate, String Stateid,String Siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		
		if(Siteid.equals("ALL"))
			sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA_STATE_WISE;
		else
			sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA;
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(),custid,Stateid,Siteid);
		}
		/*
Select Substr(S_Ebshort_Descr,Length(S_Ebshort_Descr)-1,Length(S_Ebshort_Descr)) As Phaseread,S_Customer_Name,S_Eb_Id,
  S_Ebshort_Descr,S_Type,S_Site_Name,S_State_Name
From customer_meta_data_trial
Where S_Customer_Id='0905000002' And S_State_Id='1000000036' And S_Site_Id <> 'ALL' 
Group By Substr(S_Ebshort_Descr,Length(S_Ebshort_Descr)-1,Length(S_Ebshort_Descr)),S_Customer_Name,S_Eb_Id,
  S_Ebshort_Descr,S_Type,S_Site_Name,S_State_Name
Order By S_site_name,Substr(S_Ebshort_Descr,Length(S_Ebshort_Descr)-1,Length(S_Ebshort_Descr))
DESC;
		 * */
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, Stateid);
		prepStmt.setObject(3, Siteid);
		DaoUtility.displayQueryWithParameter(10, sqlQuery, custid, Stateid, Siteid);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(10, rs);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
				
				tranVector.add(rs.getObject("S_TYPE"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_EB_ID"));

				sqlQuery = CustomerSQLC.GET_WEC_DATA1;
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(),rs.getObject("S_EB_ID"),rs.getObject("S_EB_ID"));
				}
				/*
Select Max(S_Wec_Type) || 'X' || Count(*) as S_WEC_TYPE,Nvl(N_Wec_Capacity*Count(*)/1000,0) As Lcapacity,Count(*) As Cnt 
From CUSTOMER_META_DATA_TRIAL
Where S_Eb_Id='0905000005' 
And S_Status=1 
Group By N_Wec_Capacity;
				 */
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_EB_ID"));
				ps.setObject(2, rs.getObject("S_EB_ID"));
				DaoUtility.displayQueryWithParameter(11, sqlQuery, rs.getObject("S_EB_ID"), rs.getObject("S_EB_ID"));
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					DaoUtility.getRowCount(11, rswec);
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));
					tranVector.add(rswec.getObject("S_WEC_TYPE"));
				} else {
					DaoUtility.getRowCount(11, rswec);
					tranVector.add("0");
					tranVector.add("0");
					tranVector.add("0");
				}
				tranVector.add(rs.getObject("phaseread"));
				tranList.add(i, tranVector);
				ps.close();
			}
			prepStmt.close();
			rs.close();

			if(rswec != null)rswec.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
public List getWECCurtailmentList(String custid, String fromDate, String toDate) throws Exception {	

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;	
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date fd = format.parse(fromDate);
	java.util.Date td = format.parse(toDate);
	java.sql.Date reportdate1 = new java.sql.Date(fd.getTime());
	java.sql.Date reportdate2 = new java.sql.Date(td.getTime());

	sqlQuery = CustomerSQLC.GET_WEC_CURTAILED_LIST;	
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	//prepStmt.setObject(2, reportdate1);
	//prepStmt.setObject(3, reportdate2);
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_WECSHORT_DESCR"));			
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));			
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL"));			
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR"));			
			tranVector.add(rs.getObject("S_WEC_ID"));			
			tranVector.add(rs.getObject("S_NEARBY_WEC"));	
			tranVector.add(rs.getObject("D_START_DATE").toString());			
			tranVector.add(rs.getObject("D_END_DATE").toString());
			tranVector.add(rs.getObject("N_CURTAILED_CAPACITY"));
			tranVector.add(rs.getObject("DURATION"));
			
			tranList.add(i, tranVector);
		}
		prepStmt.close();
		rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
		
	}
	return tranList;
}
public List getNearByWECCurtailmentList(String wecid, String fromDate, String toDate) throws Exception {	

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;	
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	/*SimpleDateFormat format = new SimpleDateFormat("dd-MON-yyyy");
	java.util.Date fd = format.parse(fromDate);
	java.sql.Date reportdate1 = new java.sql.Date(fd.getTime());
	java.util.Date td = format.parse(toDate);
	java.sql.Date reportdate2 = new java.sql.Date(td.getTime());*/

	sqlQuery = CustomerSQLC.GET_NEARBY_WEC_CURTAILED_LIST;	
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, wecid);	
	prepStmt.setObject(2, fromDate);	
	prepStmt.setObject(3, toDate);	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_WECSHORT_DESCR"));			
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));			
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL"));			
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR"));		
					
			
			tranList.add(i, tranVector);
		}
		prepStmt.close();
		rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}
public List getEBHeadingSite(String custid, String rdate, String Stateid,
			String Siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {


	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA_SITE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);	
		prepStmt.setObject(2, Siteid);
		DaoUtility.displayQueryWithParameter(56, sqlQuery, custid,Siteid);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if (rs.next()) {
				DaoUtility.getRowCount(56, rs);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));				
				
				tranVector.add(rs.getObject("S_TYPE"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				
			    tranVector.add(rs.getObject("cnt"));
			    tranVector.add(rs.getObject("lcapacity"));
					
				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();

			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	
	public List getEBHeadingState(String custid, String rdate, String Stateid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA_STATE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);		
		prepStmt.setObject(2, Stateid);
		DaoUtility.displayQueryWithParameter(64, sqlQuery, custid,Stateid);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if (rs.next()) {
				DaoUtility.getRowCount(64, rs);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				
				
				tranVector.add(".");
				tranVector.add(".");
				tranVector.add(rs.getObject("S_STATE_NAME"));
				
			    tranVector.add(rs.getObject("cnt"));
			    tranVector.add(rs.getObject("lcapacity"));
					
				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	
	public List getEBHeadingSiteWise( String Stateid,
			String Siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(rdate);
		//java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA_SiteWise;
		prepStmt = conn.prepareStatement(sqlQuery);
		//prepStmt.setObject(1, custid);
		prepStmt.setObject(1, Stateid);
		prepStmt.setObject(2, Siteid);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				//tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
				
				//tranVector.add(rs.getObject("S_TYPE"));
				//tranVector.add(rs.getObject("S_SITE_NAME"));
				//tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_EB_ID"));
				
				tranVector.add(rs.getObject("phaseread"));
				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	public List getEBHeadingTotal(String custid, String rdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		sqlQuery = CustomerSQLC.GET_EB_HEADING_DATA_TOTAL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
				
				tranVector.add(rs.getObject("S_TYPE"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_EB_ID"));

				sqlQuery = CustomerSQLC.GET_WEC_DATA1;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_EB_ID"));
				ps.setObject(2, rs.getObject("S_EB_ID"));
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));
					tranVector.add(rswec.getObject("S_WEC_TYPE"));

				} else {
					tranVector.add("0");
					tranVector.add("0");
					tranVector.add("0");
				}
				tranVector.add(rs.getObject("phaseread"));
				tranList.add(i, tranVector);
				ps.close();
			}
			prepStmt.close();
			rs.close();

			rswec.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	public List getBillingDetail(String custid,String month,String year,String site,String state) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();		
		
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rswec = null;
		ResultSet res = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat df = new DecimalFormat("0.##");
		SimpleDateFormat newformat = new SimpleDateFormat("yyyy-MM-dd");
		//java.util.Date ffd = format.parse(rdate);
		//java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
        Float expr,imp,relead,relag,rilead,rilag,kexp,kimp;
        if(site.equals("ALL"))
        {
        	sqlQuery = CustomerSQLC.GET_EB_BILLING_REPORT_STATE;
    		prepStmt = conn.prepareStatement(sqlQuery);
    		prepStmt.setObject(1, custid);
    		prepStmt.setObject(2, state);
    		prepStmt.setObject(3, month);
    		prepStmt.setObject(4, year);
    		
        }
        else
        {
		sqlQuery = CustomerSQLC.GET_EB_BILLING_REPORT;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, site);
		prepStmt.setObject(3, month);
		prepStmt.setObject(4, year);
		
        }
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			double t_exp=0.0,t_imp=0.0,t_exprkvahlag=0.0,t_exprkvahlead=0.0,t_imprkvahlag=0.0,t_imprkvahlead=0.0,t_kvahexp=0.0,t_kvahimp=0.0,totgen=0.0;
			int totcnt=0;
			boolean exist= true;
			while (rs.next()) {
				
				sqlQuery = CustomerSQLC.GET_EB_BILLING_REPORT_DATE_1;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_EB_ID"));
				
				
     			java.util.Date ffd = newformat.parse(rs.getObject("D_READING_DATE").toString());
				java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
				ps.setObject(2, reportdate);
				
				rswec = ps.executeQuery();
				if(rswec.next())
				{
					exist=true;		
				  //System.out.println(expr);
				  //System.out.println(imp);
					Vector tranVector = new Vector();
					tranVector.add(rs.getObject("S_SITE_NAME"));
					tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
					tranVector.add(rs.getObject("MYEAR"));
					
					String padate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
					
					tranVector.add(padate);
					String padate1="";
					if(rswec.getObject("D_READING_DATE")!=null) 
					{
					   padate1 =convertDateFormat(rswec.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
					   
					   tranVector.add(padate1);					   
						
						double k_exp=Double.parseDouble(rs.getObject("KWHEXPORT").toString());
						double k_imp=Double.parseDouble(rs.getObject("KWHIMPORT").toString());
						
						double k_exprkvahlag=Double.parseDouble(rs.getObject("RKVAHEXPLAG").toString());
						double k_exprkvahlead=Double.parseDouble(rs.getObject("RKVAHEXPLEAD").toString());
						
						double k_imprkvahlag=Double.parseDouble(rs.getObject("RKVAHIMPLAG").toString());
						double k_imprkvahlead=Double.parseDouble(rs.getObject("RKVAHIMPLEAD").toString());
						
						double k_kvahexp=Double.parseDouble(rs.getObject("KVAHEXP").toString());
						double k_kvahimp=Double.parseDouble(rs.getObject("KVAHIMP").toString());
						
					//	 k_exp=k_exp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
					//	 k_imp=k_imp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
					//	 k_exprkvahlag=k_exprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
					//	 k_exprkvahlead=k_exprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
					//	 k_imprkvahlag=k_imprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
					//	 k_imprkvahlead=k_imprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
					//	 k_kvahexp=k_kvahexp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
					//	 k_kvahimp=k_kvahimp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
					//	 t_exp=t_exp+k_exp;
					//   t_imp=t_imp+k_imp;
						
					//	 t_exprkvahlag=t_exprkvahlag+k_exprkvahlag;
					//	 t_exprkvahlead=t_exprkvahlead+k_exprkvahlead;
						
					//	 t_imprkvahlag=t_imprkvahlag+k_imprkvahlag;
					//	 t_imprkvahlead=t_imprkvahlead+k_imprkvahlead;
						
					//	 t_kvahexp=t_kvahexp+k_kvahexp;
					//	 t_kvahimp=t_kvahimp+k_kvahimp;						
						
						tranVector.add(df.format(k_exp));
						tranVector.add(df.format(k_imp));
						tranVector.add(df.format(k_exprkvahlag));
						tranVector.add(df.format(k_exprkvahlead));
						tranVector.add(df.format(k_imprkvahlag));
						tranVector.add(df.format(k_imprkvahlead));
						tranVector.add(df.format(k_kvahexp));
						tranVector.add(df.format(k_kvahimp));
						
						sqlQuery = CustomerSQLC.GET_WEC_GENERATION;
						ps1 = conn.prepareStatement(sqlQuery);
						ps1.setObject(1, rs.getObject("S_EB_ID"));
						ps1.setObject(2, rswec.getObject("D_READING_DATE"));
						ps1.setObject(3, rs.getObject("D_READING_DATE"));
						res = ps1.executeQuery();
						if(res.next())
						{
							tranVector.add(res.getObject("generation") == null ? "0" : res.getObject("generation"));
							totgen=res.getObject("generation") == null ? 0 : Double.parseDouble(res.getObject("generation").toString());
						}
						else
						{ 
							tranVector.add("0");
							
						}
						ps1.close();
						res.close();
						
						
						sqlQuery = CustomerSQLC.GET_WEC_COUNT;
						ps1 = conn.prepareStatement(sqlQuery);
						ps1.setObject(1, rs.getObject("S_EB_ID"));
						res = ps1.executeQuery();
						if(res.next())
						{
							tranVector.add(res.getObject("cnt") == null ? "0": res.getObject("cnt"));
							totcnt=totcnt+ (res.getObject("cnt") == null ? 0 : Integer.parseInt(res.getObject("cnt").toString()));
						
						}
						else
						{
							tranVector.add("0");
							
						}
						ps1.close();
						res.close();
						
						
						tranList.add(i, tranVector);
						exist=true;	
					}
					   else
						exist=false;
					
				}
				else
				{
					exist=false;
				}
				if(exist==false)
				{
					
					  //System.out.println(expr);
					  //System.out.println(imp);
						Vector tranVector = new Vector();
						tranVector.add(rs.getObject("S_SITE_NAME"));
						tranVector.add(rs.getObject("S_EBSHORT_DESCR"));
						tranVector.add(rs.getObject("MYEAR"));
						
						String padate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MM-yyyy");
						
						tranVector.add(padate);
						String padate1 ="";
						tranVector.add(padate1);						
						
						double k_exp=Double.parseDouble(rs.getObject("KWHEXPORT").toString());
						double k_imp=Double.parseDouble(rs.getObject("KWHIMPORT").toString());
						
						double k_exprkvahlag=Double.parseDouble(rs.getObject("RKVAHEXPLAG").toString());
						double k_exprkvahlead=Double.parseDouble(rs.getObject("RKVAHEXPLEAD").toString());
						
						double k_imprkvahlag=Double.parseDouble(rs.getObject("RKVAHIMPLAG").toString());
						double k_imprkvahlead=Double.parseDouble(rs.getObject("RKVAHIMPLEAD").toString());
						
						double k_kvahexp=Double.parseDouble(rs.getObject("KVAHEXP").toString());
						double k_kvahimp=Double.parseDouble(rs.getObject("KVAHIMP").toString());
						
						// k_exp=k_exp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						// k_imp=k_imp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
						// k_exprkvahlag=k_exprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						// k_exprkvahlead=k_exprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
						// k_imprkvahlag=k_imprkvahlag*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						// k_imprkvahlead=k_imprkvahlead*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						
						// k_kvahexp=k_kvahexp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						// k_kvahimp=k_kvahimp*Double.parseDouble(rs.getObject("N_MULTI_FACTOR").toString());
						 //=t_exp+k_exp;
						 //t_imp=t_imp+k_imp;
						
						// t_exprkvahlag=t_exprkvahlag+k_exprkvahlag;
						// t_exprkvahlead=t_exprkvahlead+k_exprkvahlead;
						
						// t_imprkvahlag=t_imprkvahlag+k_imprkvahlag;
						// t_imprkvahlead=t_imprkvahlead+k_imprkvahlead;
						
						// t_kvahexp=t_kvahexp+k_kvahexp;
						// t_kvahimp=t_kvahimp+k_kvahimp;
						
						tranVector.add(df.format(k_exp));
						tranVector.add(df.format(k_imp));
						tranVector.add(df.format(k_exprkvahlag));
						tranVector.add(df.format(k_exprkvahlead));
						tranVector.add(df.format(k_imprkvahlag));
						tranVector.add(df.format(k_imprkvahlead));
						tranVector.add(df.format(k_kvahexp));
						tranVector.add(df.format(k_kvahimp));						
						
						tranVector.add("-");
						
						sqlQuery = CustomerSQLC.GET_WEC_COUNT;
						ps1 = conn.prepareStatement(sqlQuery);
						ps1.setObject(1, rs.getObject("S_EB_ID"));
						res = ps1.executeQuery();
						if(res.next())
						{
							tranVector.add(res.getObject("cnt") == null ? "0" :res.getObject("cnt"));
							//totcnt=totcnt+Integer.parseInt(res.getObject("cnt ").toString());
						}
						else
						{
							tranVector.add("0");
							
						}
						ps1.close();
						res.close();
						
						tranList.add(i, tranVector);
					
				}
				rswec.close();
				ps.close();
			}
			
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps1) , Arrays.asList(rs,rswec,res) , conn);
		}
		return tranList;
	}
	public List getMPRHeading(String stateid,String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());

		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		
		sqlQuery = CustomerSQLC.GET_EB_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		
		prepStmt.setObject(1, stateid);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(), stateid);
		}
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_SITE_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_ID"));
				
				if(!wectype.equals("ALL"))
				{
					sqlQuery = CustomerSQLC.GET_WEC_DATA_BY_SITE;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_SITE_ID"));
				ps.setObject(2, rs.getObject("S_CUSTOMER_ID"));
				ps.setObject(3, wectype);
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), rs.getObject("S_SITE_ID"), rs.getObject("S_CUSTOMER_ID"), wectype);
				}
				}
				else
				{
					sqlQuery = CustomerSQLC.GET_WEC_DATA_BY_SITE_ALL;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rs.getObject("S_SITE_ID"));
					ps.setObject(2, rs.getObject("S_CUSTOMER_ID"));
					if(queryToHtml){
						DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_3_" + MethodClass.getMethodName(), rs.getObject("S_SITE_ID"), rs.getObject("S_CUSTOMER_ID"));
					}
				}
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));

				} else {
					tranVector.add("0");
					tranVector.add("0");
					
				}
				tranVector.add(rs.getObject("S_AREA_NAME"));
				
				tranList.add(i, tranVector);
				ps.close();
				rswec.close();
			}
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	public List getSubMPRHeading(String stateid,String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());
		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		
		sqlQuery = CustomerSQLC.GET_SUB_EB_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		
		prepStmt.setObject(1, stateid);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(), stateid);
		}
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_SITE_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_ID"));
				
				if(!wectype.equals("ALL"))
				{
				sqlQuery = CustomerSQLC.GET_SUB_WEC_DATA_BY_SITE;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_SITE_ID"));
				ps.setObject(2, rs.getObject("S_CUSTOMER_ID"));
				ps.setObject(3, wectype);
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), rs.getObject("S_SITE_ID"), rs.getObject("S_CUSTOMER_ID"),wectype);
				}
				}
				else
				{
					sqlQuery = CustomerSQLC.GET_SUB_WEC_DATA_BY_SITE_ALL;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rs.getObject("S_SITE_ID"));
					ps.setObject(2, rs.getObject("S_CUSTOMER_ID"));
					if(queryToHtml){
						DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), rs.getObject("S_SITE_ID"), rs.getObject("S_CUSTOMER_ID"));
					}
					
				}
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));					

				} else {
					tranVector.add("0");
					tranVector.add("0");
					
				}
				tranVector.add(rs.getObject("S_AREA_NAME"));
				tranVector.add(rs.getObject("S_SUBSTATION_DESC"));
				//tranVector.add(rs.getObject("S_FEEDER_DESC"));
				
				tranList.add(i, tranVector);
				ps.close();
				rswec.close();
			}
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	
	public List getCustMPRHeading(String cid,String stateid,String siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		
		/*System.out.println("siteid===="+siteid);
		System.out.println("cid===="+cid);
		System.out.println("stateid===="+stateid);*/
		/*
		cid '0905000002'
		stateid '1000000038'
		site '1108000001'
		*/
		try{
				if(!siteid.equals("ALL") && !siteid.equals(""))
				{
					// sqlQuery = CustomerSQLC.GET_CUSTOMER_DATA_BY_SITE_ALL;
					sqlQuery = "SELECT S_CUSTOMER_NAME,'0' as lcapacity,COUNT(*) AS cnt,DGR.S_SITE_ID,S_SITE_NAME, WM.S_CUSTOMER_ID, DGR.S_STATE_NAME " +
							   " FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID "+ 
							   " AND  WM.S_eb_ID=DGR.s_eb_ID " +
							   " AND DGR.S_SITE_ID IN ("+siteid+") " +
							   " AND WM.S_CUSTOMER_ID IN("+cid+") " +
							   " GROUP BY S_CUSTOMER_NAME,DGR.S_SITE_ID,S_SITE_NAME,WM.S_CUSTOMER_ID,DGR.S_STATE_NAME " +
							   " order by S_CUSTOMER_NAME,S_SITE_NAME ";
					ps = conn.prepareStatement(sqlQuery);
					//ps.setString(1, siteid);
					//ps.setObject(2, cid);
					//System.out.println(sqlQuery);
				}
				else
				{
					
					/*sqlQuery = CustomerSQLC.GET_CUSTOMER_DATA_BY_STATE_ALL;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,stateid);
					ps.setObject(2, cid);*/
					sqlQuery = "SELECT S_CUSTOMER_NAME,'0' as lcapacity,COUNT(*) AS cnt,DGR.S_SITE_ID,S_SITE_NAME,WM.S_CUSTOMER_ID,DGR.S_STATE_NAME " +
							   "FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  " +
							   "WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID "+ 
							   "AND  WM.S_eb_ID=DGR.s_eb_ID " +
							   "AND DGR.S_STATE_ID IN ("+stateid+") " +
							   "AND WM.S_CUSTOMER_ID IN("+cid+") " +
							   "GROUP BY S_CUSTOMER_NAME,DGR.S_SITE_ID,S_SITE_NAME,WM.S_CUSTOMER_ID,DGR.S_STATE_NAME " +
							   "ORDER BY S_CUSTOMER_NAME,S_SITE_NAME";
					ps = conn.prepareStatement(sqlQuery);
					
				}
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				int i = 0;
				while(rswec.next()) {
					Vector tranVector = new Vector();
					tranVector.add(rswec.getObject("S_CUSTOMER_NAME") == null ? "." : rswec.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					tranVector.add(rswec.getObject("S_SITE_NAME"));
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));
					tranVector.add(rswec.getObject("S_SITE_ID"));
					tranVector.add(rswec.getObject("S_CUSTOMER_ID"));
					tranVector.add(rswec.getObject("S_STATE_NAME"));
					tranList.add(i, tranVector);
				} 
				
				ps.close();
				rswec.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	
	public List getMPRHeading1(String wecid,String ebid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());

		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		if(wecid.equals("ALL"))
		{
			sqlQuery = CustomerSQLC.GET_EB_DATA_BY_EBID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(),ebid);
			}
		}
		else
		{
			sqlQuery = CustomerSQLC.GET_EB_DATA_BY_WECID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecid);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(),wecid);
			}
			
		}
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_SITE_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_ID"));
				

				
				if(wecid.equals("ALL"))
				{
					sqlQuery = CustomerSQLC.GET_WEC_DATA_BY_EBID;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rs.getObject("S_EB_ID"));
					if(queryToHtml){
						DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_3_" + MethodClass.getMethodName(),rs.getObject("S_EB_ID"));
					}
					
				}
				else
				{
					sqlQuery = CustomerSQLC.GET_WEC_DATA_BY_WECID;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rs.getObject("S_WEC_ID"));
					if(queryToHtml){
						DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_4_" + MethodClass.getMethodName(),rs.getObject("S_WEC_ID"));
					}
					
				}
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));					

				} else {
					tranVector.add("0");
					tranVector.add("0");
					
				}
				
				tranList.add(i, tranVector);
				ps.close();
				rswec.close();
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	
	
	public List getFindWECByEB(String ebid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());

		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		
			sqlQuery = CustomerSQLC.GET_EB_DATA_BY_EB;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("S_EB_ID"));
				tranVector.add(rs.getObject("S_WEC_ID"));
				tranVector.add(rs.getObject("S_WEC_TYPE"));
				
				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
		}
		return tranList;
	}
	public List getState() throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());

		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		
		sqlQuery = CustomerSQLC.GET_SITE_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
		}
		//prepStmt.setObject(1, stateid);
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_STATE_id"));
				tranVector.add(rs.getObject("S_STATE_NAME"));				

				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();			

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getState(String wecid,String ebid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());
		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		if(wecid.equals("ALL"))
			  	{
			     sqlQuery = CustomerSQLC.GET_SITE_BY_EBID;
			     prepStmt = conn.prepareStatement(sqlQuery);
			     prepStmt.setObject(1, ebid);
			     if(queryToHtml){
			 		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(), ebid);
			 	}
			  	}
		else
		{
			 sqlQuery = CustomerSQLC.GET_SITE_BY_WECID;
		     prepStmt = conn.prepareStatement(sqlQuery);
	         prepStmt.setObject(1, wecid);
	         if(queryToHtml){
	     		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(),wecid);
	     	}
	  	}
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_STATE_id"));
				tranVector.add(rs.getObject("S_STATE_NAME"));				

				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	public List getWECType(String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());

		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		//System.out.println("wectype"+wectype);
		if(wectype.equals("ALL"))
		{
			sqlQuery = CustomerSQLC.GET_WEC_TYPE_ALL;
			prepStmt = conn.prepareStatement(sqlQuery);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			
		}
		else
			
		{
		sqlQuery = CustomerSQLC.GET_WEC_TYPE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, wectype);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), wectype);
		}
		}
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("WECCAPACITY"));

				tranList.add(i, tranVector);
				
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}

	public List getSiteMPRHeading(String stateid,String wectype,String siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rswec = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.util.Date ffd = format.parse(fdate);
		//java.sql.Date frmdate = new java.sql.Date(ffd.getTime());
		
		//java.util.Date ffd1 = format.parse(tdate);
		//java.sql.Date todate = new java.sql.Date(ffd1.getTime());
		
		sqlQuery = CustomerSQLC.GET_EB_DATA_SITE;
		prepStmt = conn.prepareStatement(sqlQuery);
		
		prepStmt.setObject(1, stateid);
		prepStmt.setObject(2, siteid);
		if(queryToHtml){
			DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(), stateid, siteid);
		}
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				tranVector.add(rs.getObject("S_SITE_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_ID"));				
				
				sqlQuery = CustomerSQLC.GET_WEC_DATA_BY_SITE;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_SITE_ID"));
				ps.setObject(2, rs.getObject("S_CUSTOMER_ID"));
				ps.setObject(3, wectype);
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), rs.getObject("S_SITE_ID") ,rs.getObject("S_CUSTOMER_ID"), wectype);
				}
				// ps.setObject(2,reportdate);
				rswec = ps.executeQuery();
				if (rswec.next()) {
					tranVector.add(rswec.getObject("cnt"));
					tranVector.add(rswec.getObject("lcapacity"));

				} else {
					tranVector.add("0");
					tranVector.add("0");
					
				}
				//tranVector.add(rs.getObject("S_AREA_NAME"));

				tranList.add(i, tranVector);
				ps.close();
			}
			prepStmt.close();
			rs.close();

			rswec.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rswec) , conn);
			
		}
		return tranList;
	}
	
	public List getWECDateWise(String ebid, String rdate) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		sqlQuery = CustomerSQLC.GET_WEC_DGR_DATADM;
		
		/*"SELECT  VW_WEC_CHKUNDERTRIAL.*, CASE WHEN(D_READING_DATE-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		" FROM VW_WEC_CHKUNDERTRIAL WHERE  S_EB_ID=? and TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM')" +
		" AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		" Order by D_READING_DATE,S_WECSHORT_DESCR";*/
		prepStmt = conn.prepareStatement(sqlQuery);
		//prepStmt.setObject(1, reportdate);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		DaoUtility.displayQueryWithParameter(54, sqlQuery, ebid,reportdate,reportdate);
//		System.out.println("Eb Id : " + ebid);
//		System.out.println("Report Date : " + reportdate);
		
		//prepStmt.setObject(5, reportdate);
		//System.out.println("reADING dATE"+reportdate);
		//System.out.println("rdate"+rdate);
		// ps.setObject(1,item.toUpperCase() + "%");
		String pdate = "",Remarks = "";
		String repdate = "";
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(54, rs);
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("s_wec_id"));				
				
				repdate=rs.getObject("D_READING_DATE").toString();
				
				if(rs.getObject("D_READING_DATE").toString().charAt(0)=='0')
				{
					repdate = repdate.replaceFirst("0", "2");				
					
				}
				
				pdate = convertDateFormat(repdate, "yyyy-MM-dd", "dd-MM-yyyy");
				
				//System.out.println("pdate"+pdate);
				tranVector.add(pdate);
				tranVector.add(rs.getObject("undertrail"));				
				
			   Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
			   tranVector.add(Remarks);
			   tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			   tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			   tranList.add(i, tranVector);
			   i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWECDataCompare(String wecid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		String cyear = "";
		String adate = convertDateFormat(rdate, "dd/MM/yyyy", "dd-MM-yyyy");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_COMPARE_DATA;   
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
		}

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_MCOMPARE_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year + 1;
			}
			int nyear1=2000 + nyear; 
			cyear = 2000 + year + "-" + nyear1;
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_YCOMAPRE_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("s_wec_id"));
				if (RType.equals("D")) {
					tranVector.add(adate);
				}
				if (RType.equals("M")) {

					tranVector.add(rs.getObject("monthname"));
				}
				if (RType.equals("Y")) {
					tranVector.add(cyear);
				}
				if (RType.equals("D")) 
				{
				tranVector.add(rs.getObject("undertrail"));
				//System.out.println("ada"+rs.getObject("undertrail").toString());
				}
				else
				{
					tranVector.add("0");
				}
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	/*public List getWECData(String ebid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
        
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) 
		{
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
			
		}

		if (RType.equals("M")) 
		{
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			String Remarks="";
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("s_wec_id"));
				if (RType.equals("D")) 
				{
					
					Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
				}
				else
			   {
				Remarks="NIL";
				}
			tranVector.add(Remarks);
			if (RType.equals("D")||RType.equals("M")) 
			{
			tranVector.add(rs.getObject("undertrail"));
			//System.out.println("ada"+rs.getObject("undertrail").toString());
			}
			else
			{
				tranVector.add("0");
			}
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

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
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			}
		}
		return tranList;
	}*/

	public List getWECData(String ebid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		
		boolean isLullHourDash = false;
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_NEW;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
			DaoUtility.displayQueryWithParameter(59, sqlQuery, reportdate,ebid,reportdate,reportdate);
		}

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			DaoUtility.displayQueryWithParameter(59, sqlQuery,ebid,reportdate,reportdate);
		}
		
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			// System.out.println("Month: " + month);
			// System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			DaoUtility.displayQueryWithParameter(59, sqlQuery, ebid,pdate,ndate);
			
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			String Remarks = "";
			
			while (rs.next()) {
				DaoUtility.getRowCount(59, rs);
				//System.out.println("---------New Processing---------");
				String wecId = (String) rs.getObject("S_WEC_ID");
				if (RType.equals("D")) {
					isLullHourDash = checkLullHour(wecId, conn, reportdate);
				}
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				if(RType.equals("D")){
					if (isLullHourDash && RType.equals("D")) {
						tranVector.add("-");
						tranVector.add("-");
						tranVector.add("-");
					} 
					else {
						tranVector.add(rs.getObject("GENERATION"));
						tranVector.add(rs.getObject("OPERATINGHRS"));
						tranVector.add(rs.getObject("LULLHRS"));
					}
				}
				else{
					tranVector.add(rs.getObject("GENERATION"));
					tranVector.add(rs.getObject("OPERATINGHRS"));
					tranVector.add(rs.getObject("LULLHRS"));
				}
				tranVector.add(rs.getObject("MAVIAL") == null ? "0" : rs
						.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL") == null ? "0" : rs
						.getObject("GAVIAL").toString());
				if(RType.equals("D")){
					if (isLullHourDash ) {
						tranVector.add("-");
					} else {
						tranVector.add(rs.getObject("CFACTOR") == null ? "0" : rs
								.getObject("CFACTOR").toString());
					}
				}else{
					tranVector.add(rs.getObject("CFACTOR") == null ? "0" : rs
							.getObject("CFACTOR").toString());
				}

				tranVector.add(rs.getObject("s_wec_id"));
				if (RType.equals("D")) {

					if (!isLullHourDash) {
						Remarks = rs.getObject("S_REMARKS") == null ? "NIL"
								: rs.getObject("S_REMARKS").toString();
					} else {
						Remarks = rs.getObject("S_REMARKS") == null ? 
																	(String) rs.getObject("S_WECSHORT_DESCR") + "-"
																	+ "<span style='color:red;'>Connectivity failure during data transfer</span>"
																	: rs.getObject("S_REMARKS").toString() 
																	/*+ (String) rs.getObject("S_WECSHORT_DESCR") + "-"*/
																	+ "<span style='color:red;'>Connectivity failure during data transfer</span>";
						/*Remarks = (String) rs.getObject("S_WECSHORT_DESCR")
								+ "-"
								+ "<span style='color:red;'>Connectivity failure during data transfer</span>";*/
						/*
						 * Remarks = "<span
						 * class=\"scadaConnectivityFailure\">" +
						 * (String)rs.getObject("S_WECSHORT_DESCR") + ":" +
						 * "Scada Connectivity Failure</span>";
						 */
						/*
						 * Remarks = "<span style='color:red;'>" +
						 * (String)rs.getObject("S_WECSHORT_DESCR") + ":" + "
						 * Scada Connectivity Failure</span>";
						 */
					}
				} else {
					Remarks = "NIL";
				}
				tranVector.add(Remarks);
				if (RType.equals("D") || RType.equals("M")) {
					tranVector.add(rs.getObject("undertrail"));
					// //System.out.println("ada"+rs.getObject("undertrail").toString());
				} else {
					tranVector.add("0");
				}
				tranVector.add(rs.getObject("MIAVIAL") == null ? "0" : rs
						.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL") == null ? "0" : rs
						.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			////System.out.println("no of result:" + i);
			// //System.out.println("tranList:" + tranList);
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
		return tranList;
	}
	
	public List getWECDataNew(String ebid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		
		boolean isLullHourDash = false;
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_NEW;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
			
		}

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			String Remarks = "";
			
			while (rs.next()) {
				//System.out.println("---------New Processing---------");
				String wecId = (String) rs.getObject("S_WEC_ID");
				
				if (RType.equals("D")) {
					isLullHourDash = checkLullHour(wecId, conn, reportdate);
				}
				
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));//0 : Wec_Description
				tranVector.add(rs.getObject("WECTYPE"));//1:Wec_Type
				
				if(RType.equals("D")){
					if (isLullHourDash && RType.equals("D")) {
						tranVector.add("-");
						tranVector.add("-");
						tranVector.add("-");
					} 
					else {
						tranVector.add(rs.getObject("GENERATION"));//2 : Generation
						tranVector.add(rs.getObject("OPERATINGHRS"));//3 : Operating
						tranVector.add(rs.getObject("LULLHRS"));//4 : Lull_Hr
					}
				}
				
				tranVector.add(rs.getObject("MAVIAL") == null ? "0" : rs.getObject("MAVIAL").toString()); // 5 : MAVIAL
				tranVector.add(rs.getObject("GAVIAL") == null ? "0" : rs.getObject("GAVIAL").toString()); // 6 : GAVIAL
				
				if(RType.equals("D")){
					if (isLullHourDash ) {
						tranVector.add("-");
					} else {
						tranVector.add(rs.getObject("CFACTOR") == null ? "0" : rs.getObject("CFACTOR").toString()); //7 : CF
					}
				}

				tranVector.add(rs.getObject("s_wec_id")); // 8 : WEC_ID
				
				if (RType.equals("D")) {

					if (!isLullHourDash) {
						Remarks = rs.getObject("S_REMARKS") == null ? "NIL" : rs.getObject("S_REMARKS").toString();
					} else {
						Remarks = rs.getObject("S_REMARKS") == null ? 
																	(String) rs.getObject("S_WECSHORT_DESCR") + "-"
																	+ "<span style='color:red;'>Connectivity failure during data transfer</span>"
																	: rs.getObject("S_REMARKS").toString() 
																	+ "<span style='color:red;'>Connectivity failure during data transfer</span>";
						
					}
				}
				tranVector.add(Remarks);//9 : Remarks
				if (RType.equals("D") || RType.equals("M")) { 
					tranVector.add(rs.getObject("undertrail")); //10 : Undertrial
				}
				
				tranVector.add(rs.getObject("MIAVIAL") == null ? "0" : rs.getObject("MIAVIAL").toString()); // 11:MIAVIAL
				tranVector.add(rs.getObject("GIAVIAL") == null ? "0" : rs.getObject("GIAVIAL").toString()); // 12:GIAVIAL
				
				tranList.add(i, tranVector);
				i++;
			}
			////System.out.println("no of result:" + i);
			// //System.out.println("tranList:" + tranList);
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}
		return tranList;
	}
	
	/*Returns Value for 'Load Resting' if present in TBL_WEC_Reading Otherwise return "0"*/
	private String getLoadRest(String wecID, java.util.Date date) {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
			
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select Rea.N_Actual_Value " + 
						"From Tbl_Wec_Reading Rea, Tbl_Mp_Master Mp, Tbl_Wec_Master Wecm " + 
						"Where Rea.D_Reading_Date = ? " + 
						"And Rea.S_Mp_Id = Mp.S_Mp_Id " + 
						"And Wecm.S_Wec_Id = Rea.S_Wec_Id " + 
						"And Rea.S_Wec_Id = ? " + 
						"And Mp.S_Mp_Id = '0810000001' " + 
						"order by S_Wecshort_Descr " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, DateUtility.utilDateToSQLDate(date));
			prepStmt.setObject(2, wecID);
			rs = prepStmt.executeQuery();
			if(rs.next()) {
				return rs.getString("N_Actual_Value");	
			}
			return "0";
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return "0";

		
	}

	private boolean checkLullHour(String wecID, Connection conn, java.sql.Date reportdate){	
		boolean makeLullHourDash = false;
		double operatingHourPresent = 0;
		double totalOfMSMFandGSGF = 0;
		String wecScadaConnectivityQuery = CustomerSQLC.CHECK_WEC_STATUS_SCADA_CONNECTIVITY;
		/*"Select s_status,s_scada_flag From Tbl_Wec_Master Where S_Wec_id = ?";*/
		PreparedStatement myPrepareStmt = null;
		PreparedStatement myPrepareStmt1 = null;
		ResultSet myResultSet  = null;
		ResultSet myResultSet1 = null;
		try {
			myPrepareStmt = conn.prepareStatement(wecScadaConnectivityQuery);
			myPrepareStmt.setObject(1, wecID);
			DaoUtility.displayQueryWithParameter(60, wecScadaConnectivityQuery, wecID);
			myResultSet = myPrepareStmt.executeQuery();
			while(myResultSet.next()){
				DaoUtility.getRowCount(60, myResultSet);
				String machineActiveStatus = (String) myResultSet.getObject(1);
				String scadaStatus = (String) myResultSet.getObject(2);
				if(scadaStatus.equals("1") && machineActiveStatus.equals("1")){
				/*if(machineActiveStatus.equals("1")){*/
					makeLullHourDash = true;
					
					/*Getting the actual operating hours for a particular machine*/
					String myQuery1 = 
							"select sum(n_actual_value) from tbl_wec_reading " + 
									"where s_wec_id = ? " +
									"and d_reading_date = ? " +
									"and s_mp_id = '0808000023' ";
					 myPrepareStmt1 = conn.prepareStatement(myQuery1);
					myPrepareStmt1.setObject(2, reportdate);
					myPrepareStmt1.setObject(1, wecID);
					DaoUtility.displayQueryWithParameter(61, myQuery1, reportdate,wecID);
					 myResultSet1 = myPrepareStmt1.executeQuery();
					while(myResultSet1.next()){
						DaoUtility.getRowCount(61, myResultSet1);
						if(myResultSet1.getObject(1) != null){
							operatingHourPresent = myResultSet1.getDouble(1);
						}
					}
					
					/*If actual operating hours is greater than 0 then Lull Hour will be as it is*/
					if(operatingHourPresent > 0){
						makeLullHourDash = false;
						return makeLullHourDash;
					}
					
					/* Adding MF/MS and GS/GF without considering special shutdown and 'Load Rest. Hours/EB' 
					 * of particular day and a particular WEC */
					myQuery1 = 
							"select sum(n_actual_value) from tbl_wec_reading " + 
							"where s_wec_id = ? "+
							"and d_reading_date = ? " +
							"and s_mp_id in (select s_mp_id from tbl_mp_master " +
							                "where S_MP_UNIT = 'HOURS') " + 
							"and s_mp_id not in  ('0808000023','0810000001','0810000002','0808000022')";
					myPrepareStmt1 = conn.prepareStatement(myQuery1);
					myPrepareStmt1.setObject(1, wecID);
					myPrepareStmt1.setObject(2, reportdate);
					DaoUtility.displayQueryWithParameter(62, myQuery1, wecID,reportdate);
					myResultSet1 = myPrepareStmt1.executeQuery();
					
					while(myResultSet1.next()){
						DaoUtility.getRowCount(62, myResultSet1);
						totalOfMSMFandGSGF = myResultSet1.getDouble(1);
					}
					
					double estimatedOperatingHour = 24.0 - totalOfMSMFandGSGF;
					
					/* Operating Hour created by system is ZERO
					 * But estimated operating hour is greater than 20,then Lull Hour will be not shown*/
					if(estimatedOperatingHour >= 20.0){
						makeLullHourDash = true;
						return makeLullHourDash;
					}
					
					if(totalOfMSMFandGSGF > 20){
						makeLullHourDash = false;
						return makeLullHourDash;
					}
					
					/*If estimated operating hour is less than or equal to zero
					 * then MF/MS and GF/GS is 24hr or more*/
					if(estimatedOperatingHour <= 0.0){
						makeLullHourDash = false;
						return makeLullHourDash;
					}
					
					if(estimatedOperatingHour >= 0.0 && estimatedOperatingHour <= 24.0){
						/*Query for checking 'operating hour' created by system*/
						myQuery1 = "Select rea.n_actual_value,rea.s_mp_id,rea.s_created_by, mp.s_mp_descr " + 
									"from TBL_WEC_READING rea, TBL_MP_MASTER mp " +
									"where d_reading_date = ? " +
									"and rea.s_mp_id = mp.s_mp_id " + 
									"and rea.s_wec_id = ? " +
									"and rea.s_mp_id = '0808000023' " +  
									"and rea.s_created_by = 'SYSTEM'";
						myPrepareStmt1 = conn.prepareStatement(myQuery1);
						myPrepareStmt1.setObject(1, reportdate);
						myPrepareStmt1.setObject(2, wecID);
						DaoUtility.displayQueryWithParameter(63, myQuery1, reportdate,wecID);
						myResultSet1 = myPrepareStmt1.executeQuery();
						boolean systemCreatedValue = false;
						
						while(myResultSet1.next()){
							DaoUtility.getRowCount(63, myResultSet1);
							systemCreatedValue = true;
							//System.out.println("888888888888888888888888->" + myResultSet1.getDouble(1));
						}
						
						if(systemCreatedValue){
							makeLullHourDash = false;
							return makeLullHourDash;
						}
					}
					
					/*String[] columnNames;
					ResultSet myResultSet1 = myPrepareStmt1.executeQuery();
					//System.out.println("SQLQuery:" + myQuery1);
					
					columnNames = GetColumnNames.getColumnNames(myResultSet1);
			        
			        HashMap<String, ArrayList<Object>> columnNameWithRowValuesMapping = new HashMap<String, ArrayList<Object>>();
			        
			        int noOfResult = 0;
			        
			        'ColumnNames' as key and 'ColumnValues' as value.
			         * Key(String)-Value(ArrayList) Pair
			        while(myResultSet1.next()){
			        	noOfResult++;
			        	for (String columnName : columnNames) {
			        		if(columnNameWithRowValuesMapping.containsKey(columnName)){
			        			ArrayList<Object> existingColValues = columnNameWithRowValuesMapping.get(columnName);
			        			existingColValues.add(myResultSet1.getObject(columnName));
			        		}
			        		else{
				        		ArrayList<Object> colValue = new ArrayList<Object>();
				        		colValue.add(myResultSet1.getObject(columnName));
				        		columnNameWithRowValuesMapping.put(columnName, colValue);
			        		}
						}
			        }
			        
			        String[] createdBy = new String[noOfResult];
			        String[] mpId = new String[noOfResult];
			        BigDecimal[] actualValue = new BigDecimal[noOfResult];
			        for(int rowSelected = 0; rowSelected < noOfResult; rowSelected++){
			        	for (String columnName : columnNames) {
				        	if(columnName.equalsIgnoreCase("S_CREATED_BY")){
				        		createdBy[rowSelected] = (String) columnNameWithRowValuesMapping.get(columnName).get(rowSelected);
				        	}
				        	if(columnName.equalsIgnoreCase("S_MP_ID")){
				        		mpId[rowSelected] = (String) columnRowMapping.get(columnName).get(rowSelected);
				        	}
				        	if(columnName.equalsIgnoreCase("N_ACTUAL_VALUE")){
				        		actualValue[rowSelected] = (BigDecimal) columnRowMapping.get(columnName).get(rowSelected);
				        	}
				        }
			        }
			        
			        boolean isTwoZero = false;
			        boolean isThreeZero = false;
			        boolean otherMpId = false;
			        makeLullHourZero = true;
			        for (int rowSelected = 0; rowSelected < noOfResult; rowSelected++) {
			        	
			        		if(!(mpId[rowSelected].equals("0808000022") || mpId[rowSelected].equals("0808000023"))){
			        			
				        		otherMpId = true;
				        	}
			        	
							if(mpId[rowSelected].equals("0808000022") && createdBy[rowSelected].equalsIgnoreCase("System")){
								if(actualValue[rowSelected] == new BigDecimal(0) || (actualValue[rowSelected].compareTo(BigDecimal.ZERO) == 0)){
									isTwoZero = true;
								}
							}
							
							if(mpId[rowSelected].equals("0808000023") && createdBy[rowSelected].equalsIgnoreCase("System")){
								if(actualValue[rowSelected] == new BigDecimal(0) || (actualValue[rowSelected].compareTo(BigDecimal.ZERO) == 0)){
									isThreeZero = true;
								}
							}
							
							if(createdBy[rowSelected].equalsIgnoreCase("System")){
								makeLullHourZero = false;
							}
					}*/
			        
			        /*if(isTwoZero && isThreeZero && (!otherMpId)){
			        	makeLullHourZero = true;
			        }*/
				}
			}
		} catch (Exception e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}finally{
			DaoUtility.releaseResources(Arrays.asList(myPrepareStmt,myPrepareStmt1) , Arrays.asList(myResultSet,myResultSet1));
		}
		
		return makeLullHourDash;
	}
	
	public List getWECDataCum(String ebid, String rdate) throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		
		/*ArrayList<ArrayList<Object>> wecInfo = EBMetaInfo.getOneEBWECWiseInfoForOneDay(ebid, AdminDao.convertDateFormat(rdate, "dd/MM/yyyy", "dd-MMM-yy"));
		Vector<Object> v = new Vector<Object>();
		for (ArrayList<Object> wecOneDayInfo : wecInfo) {
			v.add(wecOneDayInfo.get(1));
			v.add(wecOneDayInfo.get(3));
			v.add(wecOneDayInfo.get(6));
			v.add(wecOneDayInfo.get(8));
			v.add(wecOneDayInfo.get(12));
			v.add(wecOneDayInfo.get(13));
			v.add(wecOneDayInfo.get(14));
			v.add(wecOneDayInfo.get(15));
			v.add(wecOneDayInfo.get(0));
			v.add(((String)wecOneDayInfo.get(10)).equals("")?"NIL" : wecOneDayInfo.get(10));
			List<Object> wecMonthTotal = WECMetaInfo.getOneWECTotalForOneMonth((String)v.get(0), DateUtility.gettingMonthFromStringDate(rdate, "dd/MM/yyyy"), DateUtility.gettingYearFromStringDate(rdate, "dd/MM/yyyy"));
			v.add(wecMonthTotal.get(6));
			v.add(wecMonthTotal.get(8));
			v.add(wecOneDayInfo.get(17));
			v.add(wecOneDayInfo.get(16));
			tranList.add(v);
			v = new Vector<Object>();
		}
		return tranList;*/
		List tranList = new ArrayList();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	
		sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, reportdate);		
		prepStmt.setObject(2, ebid);
		prepStmt.setObject(3, reportdate);
		prepStmt.setObject(4, reportdate);	
		
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		String Remarks="";
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
			tranVector.add(rs.getObject("WECTYPE"));
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			
			
			tranVector.add(rs.getObject("LULLHRS"));
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("s_wec_id"));
			
			Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
			
		    tranVector.add(Remarks);
		
		    tranVector.add(rs.getObject("undertrail"));
		    // System.out.println("ada"+rs.getObject("undertrail").toString());
		     
		        sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, rs.getObject("S_WEC_ID"));
				ps.setObject(2, reportdate);
				ps.setObject(3, reportdate);
				ps.setObject(4, reportdate);
				rs1=ps.executeQuery();
				if(rs1.next())
				{
					tranVector.add(rs1.getObject("GENERATION"));
					tranVector.add(rs1.getObject("OPERATINGHRS"));
				}
				else
				{
					tranVector.add("0");
					tranVector.add("0");
				}
				rs1.close();
				ps.close();
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
		return tranList;
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
		
		return tranList;
	}
	
	
}


	public static List getWECWise(String siteid, String rdate,String custid) throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
	
	/*sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_1;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, siteid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setString(3, custid);*/
	
	sqlQuery = "SELECT c.s_site_name, " +
				"	a.*  FROM vw_wec_cur_data a,tbl_eb_master b, tbl_site_master c " +
				"	WHERE b.S_site_ID=c.S_site_ID  " +
				"	and a.s_eb_id=b.s_eb_id  " +
				"	and b.S_site_ID='"+siteid+"' " +
				"	and a.MAVIAL< 95 " +
				" 	and a.D_READING_DATE='"+reportdate+"' " +
				"	and b.s_customer_id in('"+custid+"') " +
				"	Order by a.S_WECSHORT_DESCR";
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	prepStmt = conn.prepareStatement(sqlQuery);
	
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	String Remarks="";
	while (rs.next()) {
		Vector tranVector = new Vector();
		
		tranVector.add(rs.getObject("S_WECSHORT_DESCR"));	
		tranVector.add(rs.getObject("WECTYPE"));
		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("OPERATINGHRS"));
		tranVector.add(rs.getObject("LULLHRS"));
		tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
		tranVector.add(rs.getObject("s_wec_id"));
		
		
	    Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
		
	    tranVector.add(Remarks);
	
	    tranVector.add("1");
	    // System.out.println("ada"+rs.getObject("undertrail").toString());
	     
	        /*sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA_1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1, rs.getObject("S_WEC_ID"));
			ps.setObject(2, reportdate);*/
	    
	    	sqlQuery = "SELECT round(wec.GENERATION,2) as GENERATION,sectotime(timetosecond(wec.OPERATINGHRS)) as OPERATINGHRS " +
	    			"	from vw_wec_cur_data wec "+
	    			" 	WHERE wec.S_WEC_ID='"+rs.getObject("S_WEC_ID")+"'  " +
	    			"	AND D_READING_DATE<='"+reportdate+"'";
	    	if(queryToHtml){
	    		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName());
	    	}
	    	ps = conn.prepareStatement(sqlQuery);
			
			rs1=ps.executeQuery();
			if(rs1.next())
			{
				tranVector.add(rs1.getObject("GENERATION"));
				tranVector.add(rs1.getObject("OPERATINGHRS"));
			}
			else
			{
				tranVector.add("0");
				tranVector.add("0");
			}
			rs1.close();
			ps.close();
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranVector.add(rs.getObject("s_site_name"));
			tranList.add(i, tranVector);
			i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
	}
	return tranList;
}

	public static List getWECWise1(String siteid,String rd, String rdate,String custid) throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate2 = new java.sql.Date(ffd.getTime());
	java.util.Date ffd1 = format.parse(rd);
	java.sql.Date reportdate21 = new java.sql.Date(ffd1.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
	String reportdate1= ad.convertDateFormat(rd,"dd/MM/yyyy","dd-MMM-yyyy");
	
	/*sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_11;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, siteid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, custid);*/
	
	sqlQuery = "SELECT distinct a.s_wec_id,a.S_WECSHORT_DESCR,c.s_site_name  " +
			" FROM vw_wec_cur_data a,tbl_eb_master b, tbl_site_master c " +
			" WHERE b.S_site_ID=c.S_site_ID  and a.s_eb_id=b.s_eb_id  and b.S_site_ID='"+siteid+"' and  " +
			" a.D_READING_DATE='"+reportdate+"' and b.s_customer_id in('"+custid+"')Order by a.S_WECSHORT_DESCR";
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	prepStmt = conn.prepareStatement(sqlQuery);
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	String Remarks="";
	while (rs.next()) {
		Vector tranVector = new Vector();
		
		tranVector.add(rs.getObject("S_WECSHORT_DESCR"));	
		//tranVector.add(rs.getObject("WECTYPE"));
		//tranVector.add(rs.getObject("GENERATION"));
		//tranVector.add(rs.getObject("OPERATINGHRS"));
		//tranVector.add(rs.getObject("LULLHRS"));
		//tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		//tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		//tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
		tranVector.add(rs.getObject("s_wec_id"));
		
	    //Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
		
	   // tranVector.add(Remarks);
	
	   // tranVector.add("1");
	    // System.out.println("ada"+rs.getObject("undertrail").toString());
	     
	       /* sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA_121;
			ps = conn.prepareStatement(sqlQuery);
			
			ps.setObject(1, rs.getObject("S_WEC_ID"));
			ps.setObject(2, reportdate1);
			ps.setObject(3, reportdate);*/
			
			sqlQuery = "  SELECT round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS)))  as  OPERATINGHRS, "+ 
	        " round(avg(MAVIAL),2) as mavial "+		
			" from vw_wec_cur_data wec  " +
			" WHERE wec.S_WEC_ID='"+rs.getObject("S_WEC_ID")+"'  AND D_READING_DATE between '"+reportdate1+"' and '"+reportdate+"' ";
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName());
			}
			ps = conn.prepareStatement(sqlQuery);
			
			rs1=ps.executeQuery();
			if(rs1.next())
			{
				tranVector.add(rs1.getObject("GENERATION"));
				tranVector.add(rs1.getObject("OPERATINGHRS"));
				double ma=Double.parseDouble(rs1.getObject("mavial").toString());
				//if(ma<98){
				tranVector.add(rs1.getObject("mavial"));
			   // }else{
				// tranVector.add("0");
			   // }
	       }
			else
			{
				tranVector.add("0");
				tranVector.add("0");
				tranVector.add("0");
			}
			rs1.close();
			ps.close();
		
			tranVector.add(rs.getObject("s_site_name"));
			tranList.add(i, tranVector);
			i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
	}
	return tranList;
}
public static List getWECSiteWise(String rdate,String custid) throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}

	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	PreparedStatement ps3 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");		
	/*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, reportdate);*/
	
	/*Query to get State_id,Site_id,Site_name,weccount*/
	sqlQuery = "select state.s_state_id, state.s_state_name, st.s_site_id, s_site_name, count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master st, tbl_state_master state "+
	" where wec.s_eb_id=eb.s_eb_id  " +
	" AND  	st.s_site_id=eb.s_site_id " +
	" AND  	state.s_state_id=st.s_state_id " +
	" AND 	eb.s_customer_id in('"+custid+"') " +
	" AND 	wec.s_status IN(1) " +
	" AND 	nvl(wec.D_COMMISION_DATE,'"+reportdate+"')<='"+reportdate+"' " +
	" group by state.s_state_id, state.s_state_name, st.s_site_id, st.s_site_name" +
	" order by state.s_state_id";
	prepStmt = conn.prepareStatement(sqlQuery);
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	int tcount=0,ucount=0,rcount=0;
	String Remarks="";
	while (rs.next()) {
		
		tcount=0;ucount=0;rcount=0;
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("s_site_name"));
		tranVector.add(rs.getObject("weccount"));
		tcount=Integer.parseInt(rs.getObject("weccount").toString());	
		
				 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_2;
				 ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1, rs.getObject("s_site_id"));
				 ps.setObject(2, reportdate);
				 ps.setObject(3, custid);*/
		
			/**/
				sqlQuery = "SELECT d.s_site_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
				" nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
				" round(sum(CFACTOR)/count(a.s_wec_id),1) as CFACTOR ,round(sum(MAVIAL)/count(a.s_wec_id),1) as MAVIAL , " +
				" round(sum(GAVIAL)/count(a.s_wec_id),1)  as GAVIAL  " +
				" FROM  vw_wec_cur_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
				" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
				" and c.S_STATE_ID = d.S_STATE_ID"+
				" and b.s_site_id = d.s_site_id"+
				" and a.D_READING_DATE='"+reportdate+"' and b.s_customer_id in('"+custid+"') group by  d.s_site_id";
				ps = conn.prepareStatement(sqlQuery);
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName());
				}
				 rs1 = ps.executeQuery();
				 if(rs1.next())
				 {  
					String abc=rs1.getObject("CFACTOR").toString();
					// System.out.print(abc);
					 double abcdouble = Double.parseDouble(abc);
					 double gen=0;
					 double genU=0;
					 tranVector.add(rs1.getObject("cnt"));
					 tranVector.add(rs1.getObject("GENERATION"));
					 
					 tranVector.add(rs1.getObject("CFACTOR"));
					 //tranVector.add(abcdouble);
					 tranVector.add(rs1.getObject("MAVIAL"));
					 tranVector.add(rs1.getObject("GAVIAL"));	
					 tranVector.add(rs1.getObject("GENUNIT"));
					 
					 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_22;
					 ps3 = conn.prepareStatement(sqlQuery);
					 ps3.setObject(1, rs.getObject("s_site_id"));
					 ps3.setObject(2, reportdate);	
					 ps3.setObject(3, custid);*/
					 
					 sqlQuery = "SELECT a.s_wec_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
						"  nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
						" round(CFACTOR,0) as CFACTOR , round(MAVIAL,0) as MAVIAL , round(GAVIAL ,0) as GAVIAL  " +
						" FROM vw_wec_cur_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
						" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
						" and c.S_STATE_ID = d.S_STATE_ID"+
						" and b.s_site_id = d.s_site_id"+
						" and a.D_READING_DATE='"+reportdate+"' and b.s_customer_id in('"+custid+"') group by a.s_wec_id, CFACTOR, MAVIAL, GAVIAL ";
					 ps3 = conn.prepareStatement(sqlQuery);
					 if(queryToHtml){
							DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_3_" + MethodClass.getMethodName());
						}
					 
					 rs3 = ps3.executeQuery();
						 while(rs3.next()){						
							
						 /*sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA_11;
						 ps1 = conn.prepareStatement(sqlQuery);
						 ps1.setObject(1, rs3.getObject("s_wec_id"));
						 ps1.setObject(2, reportdate);*/			
							 
						 sqlQuery = "SELECT round(sum(a.GENERATION)/1000,2) as GENERATION," +
							" round(round((nvl(sum(a.GENERATION),2)-(nvl(sum(a.GENERATION),2)*3)/100)*a.N_COST_PER_UNIT,0)/100000,2) as GENUNITALL, " +
							" sectotime(sum(timetosecond(a.OPERATINGHRS))) as OPERATINGHRS " +
							" from vw_wec_cur_data a , tbl_state_master c,tbl_eb_master b,tbl_site_master d "+
							" WHERE a.s_eb_id=b.s_eb_id and c.S_STATE_ID = d.S_STATE_ID and b.s_site_id = d.s_site_id " +
							" and a.S_WEC_ID='"+rs3.getObject("s_wec_id")+"'  AND D_READING_DATE between '01-APR-2013' and '"+reportdate+"' GROUP BY a.N_COST_PER_UNIT";
						 if(queryToHtml){
								DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_4_" + MethodClass.getMethodName());
							}
						 ps1 = conn.prepareStatement(sqlQuery);
							 
						 rs2 = ps1.executeQuery();
							 if(rs2.next()){
								  gen= gen+Double.parseDouble(rs2.getObject("GENERATION").toString());
								  genU=genU+Double.parseDouble(rs2.getObject("GENUNITALL").toString());
									 
							 }
						 rs2.close();
						 ps1.close();
						 
						 }
						
						 tranVector.add(gen);
						 tranVector.add(genU);
					 rs3.close();
					 ps3.close();
					 
					 ucount=Integer.parseInt(rs1.getObject("cnt").toString());
				 }
				 else
				 {
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
				 } 
				
		 rcount=tcount-ucount;
		 tranVector.add(rcount);
		 tranVector.add(rs.getObject("s_site_id"));
		 tranVector.add(rs.getObject("s_state_name"));
		 rs1.close();
		 ps.close();
			tranList.add(i, tranVector);
			i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps1,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
	}
	return tranList;
}
public static int getStateCount(String rdate,String custid, String stateId) throws Exception{
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	String sqlQuery = "";
	int stateCount = 0;
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");	
	
	sqlQuery = "select count(distinct st.s_site_id) as stateCount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master st, tbl_state_master state "+
	" where wec.s_eb_id=eb.s_eb_id  " +
	" AND  	st.s_site_id=eb.s_site_id " +
	" AND  	state.s_state_id=st.s_state_id " +
	" AND 	eb.s_customer_id in('"+custid+"') " +
	" AND 	wec.s_status IN(1,9) " +
	" AND 	nvl(wec.D_COMMISION_DATE,'"+reportdate+"')<='"+reportdate+"' " +
	" AND   state.s_state_name = '"+stateId+"' ";
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	ps = conn.prepareStatement(sqlQuery);
	
	try{
		rs = ps.executeQuery();
		if(rs.next())
			stateCount = rs.getInt("stateCount");
		rs.close();
		ps.close();
		//conn.close();
	}catch(Exception e){
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
	}finally {
		DaoUtility.releaseResources(ps, rs, conn);
		}
	
	return stateCount;
}
public static List getWECSiteWisePhase2( String dateInString,String custid) throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	PreparedStatement ps3 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(dateInString);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(dateInString,"dd/MM/yyyy","dd-MMM-yyyy");
			
	
	/*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, reportdate);*/
	
	sqlQuery = "select state.s_state_id, state.s_state_name, st.s_site_id, s_site_name, count(*) as weccount " +
	"	from 	tbl_wec_master wec, tbl_eb_master eb, tbl_site_master st, tbl_state_master state "+
	"   where 	wec.s_eb_id=eb.s_eb_id  " +
	"	and  	st.s_site_id=eb.s_site_id " +
	"	and     state.s_state_id=st.s_state_id " +
	"	and 	eb.s_customer_id in('"+custid+"') " +
	"	and 	wec.s_status IN(1) AND nvl(wec.D_COMMISION_DATE,'"+reportdate+"')<='"+reportdate+"' " +
	//"	AND 	wec.D_COMMISION_DATE >= '01-SEP-2011' " +
	"	group by state.s_state_id, state.s_state_name, st.s_site_id, st.s_site_name " +
	"	order by state.s_state_id";
	prepStmt = conn.prepareStatement(sqlQuery);
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	int tcount=0,ucount=0,rcount=0;
	String Remarks="";
	while (rs.next()) {
		
		tcount=0;ucount=0;rcount=0;
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("s_site_name"));
		tranVector.add(rs.getObject("weccount"));
		tcount=Integer.parseInt(rs.getObject("weccount").toString());	
		
				 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_2;
				 ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1, rs.getObject("s_site_id"));
				 ps.setObject(2, reportdate);
				 ps.setObject(3, custid);*/
		
				sqlQuery = "SELECT d.s_site_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
				" nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
				" round(sum(CFACTOR)/count(a.s_wec_id),1) as CFACTOR ,round(sum(MAVIAL)/count(a.s_wec_id),1) as MAVIAL , round(sum(GAVIAL)/count(a.s_wec_id),1)  as GAVIAL  " +
				" FROM  vw_wec_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
				" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
				" and c.S_STATE_ID = d.S_STATE_ID"+
				" and b.s_site_id = d.s_site_id"+
				" and a.D_READING_DATE='"+reportdate+"' " +
				" and b.s_customer_id in('"+custid+"') " +
				//" and a.D_COMMISION_DATE >= '01-SEP-2011' " +
				" group by  d.s_site_id";
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName());
				}
				ps = conn.prepareStatement(sqlQuery);
		
				 rs1 = ps.executeQuery();
				 if(rs1.next())
				 {  
					String abc=rs1.getObject("CFACTOR").toString();
					// System.out.print(abc);
					 double abcdouble = Double.parseDouble(abc);
					 double gen=0;
					 double genU=0;
					 tranVector.add(rs1.getObject("cnt"));
					 tranVector.add(rs1.getObject("GENERATION"));
					 
					 tranVector.add(rs1.getObject("CFACTOR"));
					 //tranVector.add(abcdouble);
					 tranVector.add(rs1.getObject("MAVIAL"));
					 tranVector.add(rs1.getObject("GAVIAL"));	
					 tranVector.add(rs1.getObject("GENUNIT"));
					 
					 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_22;
					 ps3 = conn.prepareStatement(sqlQuery);
					 ps3.setObject(1, rs.getObject("s_site_id"));
					 ps3.setObject(2, reportdate);	
					 ps3.setObject(3, custid);*/
					 
					 sqlQuery = "SELECT a.s_wec_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
						"  nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
						" round(CFACTOR,0) as CFACTOR , round(MAVIAL,0) as MAVIAL , round(GAVIAL ,0) as GAVIAL  " +
						" FROM vw_wec_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
						" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
						" and c.S_STATE_ID = d.S_STATE_ID"+
						" and b.s_site_id = d.s_site_id"+
						" and a.D_READING_DATE='"+reportdate+"' " +
						" and b.s_customer_id in('"+custid+"') " +
						//" and a.D_COMMISION_DATE >= '01-SEP-2011' " +
						" group by a.s_wec_id, CFACTOR, MAVIAL, GAVIAL ";
					 
					 if(queryToHtml){
							DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_3_" + MethodClass.getMethodName());
						}
					 
					 ps3 = conn.prepareStatement(sqlQuery);
					 
					 rs3 = ps3.executeQuery();
						 while(rs3.next()){						
							
						 /*sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA_11;
						 ps1 = conn.prepareStatement(sqlQuery);
						 ps1.setObject(1, rs3.getObject("s_wec_id"));
						 ps1.setObject(2, reportdate);*/			
							 
						 sqlQuery = "SELECT round(sum(a.GENERATION)/1000,2) as GENERATION," +
							" round(round((nvl(sum(a.GENERATION),2)-(nvl(sum(a.GENERATION),2)*3)/100)*a.N_COST_PER_UNIT,0)/100000,2) as GENUNITALL, " +
							" sectotime(sum(timetosecond(a.OPERATINGHRS))) as OPERATINGHRS " +
							" from vw_wec_data a , tbl_state_master c,tbl_eb_master b,tbl_site_master d "+
							" WHERE a.s_eb_id=b.s_eb_id and c.S_STATE_ID = d.S_STATE_ID and b.s_site_id = d.s_site_id " +
							" and a.S_WEC_ID='"+rs3.getObject("s_wec_id")+"'  " +
							//" and a.D_COMMISION_DATE >= '01-SEP-2011' " +
							" AND D_READING_DATE between '01-APR-2013' and '"+reportdate+"' " +
							" GROUP BY a.N_COST_PER_UNIT";
						 if(queryToHtml){
								DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_4_" + MethodClass.getMethodName());
							}
						 ps1 = conn.prepareStatement(sqlQuery);
							 
						 rs2 = ps1.executeQuery();
							 if(rs2.next()){
								  gen= gen+Double.parseDouble(rs2.getObject("GENERATION").toString());
								  genU=genU+Double.parseDouble(rs2.getObject("GENUNITALL").toString());
									 
							 }
						 rs2.close();
						 ps1.close();
						 
						 }
						
						 tranVector.add(gen);
						 tranVector.add(genU);
					 rs3.close();
					 ps3.close();
					 
					 ucount=Integer.parseInt(rs1.getObject("cnt").toString());
				 }
				 else
				 {
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
				 } 
				
		rcount=tcount-ucount;
		 tranVector.add(rcount);
		 tranVector.add(rs.getObject("s_site_id"));
		 tranVector.add(rs.getObject("s_state_name"));
		 rs1.close();
		 ps.close();
			tranList.add(i, tranVector);
			i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps1,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
	}
	return tranList;
}

	public static List getWECSiteWisePhase1(String rdate,String custid) throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	PreparedStatement ps1 = null;
	PreparedStatement ps3 = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	ResultSet rs2 = null;
	ResultSet rs3 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate1 = new java.sql.Date(ffd.getTime());
	
	AdminDao ad = new AdminDao();
	String reportdate= ad.convertDateFormat(rdate,"dd/MM/yyyy","dd-MMM-yyyy");
			
	
	/*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, custid);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, reportdate);*/
	
	sqlQuery = "select state.s_state_id, state.s_state_name, st.s_site_id, s_site_name, count(*) as weccount " +
	" 	from tbl_wec_master wec, tbl_eb_master eb, tbl_site_master st, tbl_state_master state "+
	"   where 	wec.s_eb_id=eb.s_eb_id  " +
	"	and 	st.s_site_id=eb.s_site_id " +
	"	and		state.s_state_id = st.s_state_id " +
	"	and 	eb.s_customer_id in('"+custid+"') " +
	"	and 	wec.s_status IN(1,9) AND nvl(wec.D_COMMISION_DATE,'"+reportdate+"')<='"+reportdate+"' " +
	"	AND 	wec.D_COMMISION_DATE < '01-SEP-2011' " +
	"	group by state.s_state_id, state.s_state_name, st.s_site_id, st.s_site_name " +
	"	order by state.s_state_id";
	prepStmt = conn.prepareStatement(sqlQuery);
	
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	int tcount=0,ucount=0,rcount=0;
	String Remarks="";
	while (rs.next()) {
		
		tcount=0;ucount=0;rcount=0;
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("s_site_name"));
		tranVector.add(rs.getObject("weccount"));
		tcount=Integer.parseInt(rs.getObject("weccount").toString());	
		
				 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_2;
				 ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1, rs.getObject("s_site_id"));
				 ps.setObject(2, reportdate);
				 ps.setObject(3, custid);*/
		
				sqlQuery = "SELECT d.s_site_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
				" nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
				" round(sum(CFACTOR)/count(a.s_wec_id),1) as CFACTOR ,round(sum(MAVIAL)/count(a.s_wec_id),1) as MAVIAL , round(sum(GAVIAL)/count(a.s_wec_id),1)  as GAVIAL  " +
				" FROM  vw_wec_cur_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
				" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
				" and c.S_STATE_ID = d.S_STATE_ID"+
				" and b.s_site_id = d.s_site_id"+
				" and a.D_READING_DATE='"+reportdate+"' " +
				" and b.s_customer_id in('"+custid+"') " +
				" and a.D_COMMISION_DATE < '01-SEP-2011' " +
				" group by  d.s_site_id";
				ps = conn.prepareStatement(sqlQuery);
		
				 rs1 = ps.executeQuery();
				 if(rs1.next())
				 {  
					 String abc=rs1.getObject("CFACTOR").toString();
					 // System.out.print(abc);
					 double abcdouble = Double.parseDouble(abc);
					 double gen=0;
					 double genU=0;
					 tranVector.add(rs1.getObject("cnt"));
					 tranVector.add(rs1.getObject("GENERATION"));
					 
					 tranVector.add(rs1.getObject("CFACTOR"));
					 //tranVector.add(abcdouble);
					 tranVector.add(rs1.getObject("MAVIAL"));
					 tranVector.add(rs1.getObject("GAVIAL"));	
					 tranVector.add(rs1.getObject("GENUNIT"));
					 
					 /*sqlQuery = CustomerSQLC.GET_SITE_WISEDATA_22;
					 ps3 = conn.prepareStatement(sqlQuery);
					 ps3.setObject(1, rs.getObject("s_site_id"));
					 ps3.setObject(2, reportdate);	
					 ps3.setObject(3, custid);*/
					 
					 sqlQuery = "SELECT a.s_wec_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
						" nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
						" round(CFACTOR,0) as CFACTOR , round(MAVIAL,0) as MAVIAL , round(GAVIAL ,0) as GAVIAL  " +
						" FROM vw_wec_cur_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
						" WHERE  b.S_site_ID='"+rs.getObject("s_site_id")+"' and a.s_eb_id=b.s_eb_id  " +
						" and c.S_STATE_ID = d.S_STATE_ID"+
						" and b.s_site_id = d.s_site_id"+
						" and a.D_READING_DATE='"+reportdate+"' " +
						" and b.s_customer_id in('"+custid+"') " +
						" and a.D_COMMISION_DATE < '01-SEP-2011' " +
						" group by a.s_wec_id, CFACTOR, MAVIAL, GAVIAL ";
					 ps3 = conn.prepareStatement(sqlQuery);
					 
					 rs3 = ps3.executeQuery();
						 while(rs3.next()){						
							
						 /*sqlQuery = CustomerSQLC.GET_CUM_DGR_DATA_11;
						 ps1 = conn.prepareStatement(sqlQuery);
						 ps1.setObject(1, rs3.getObject("s_wec_id"));
						 ps1.setObject(2, reportdate);*/			
							 
						 sqlQuery = "SELECT round(sum(a.GENERATION)/1000,2) as GENERATION," +
							" round(round((nvl(sum(a.GENERATION),2)-(nvl(sum(a.GENERATION),2)*3)/100)*a.N_COST_PER_UNIT,0)/100000,2) as GENUNITALL, " +
							" sectotime(sum(timetosecond(a.OPERATINGHRS))) as OPERATINGHRS " +
							" from vw_wec_cur_data a , tbl_state_master c,tbl_eb_master b,tbl_site_master d "+
							" WHERE a.s_eb_id=b.s_eb_id and c.S_STATE_ID = d.S_STATE_ID and b.s_site_id = d.s_site_id " +
							" and a.S_WEC_ID='"+rs3.getObject("s_wec_id")+"'  " +
							" and a.D_COMMISION_DATE < '01-SEP-2011' " +
							" AND D_READING_DATE between '01-APR-2013' and '"+reportdate+"' " +
							" GROUP BY a.N_COST_PER_UNIT";
						 ps1 = conn.prepareStatement(sqlQuery);
							 
						 rs2 = ps1.executeQuery();
							 if(rs2.next()){
								  gen= gen+Double.parseDouble(rs2.getObject("GENERATION").toString());
								  genU=genU+Double.parseDouble(rs2.getObject("GENUNITALL").toString());
									 
							 }
						 rs2.close();
						 ps1.close();
						 
						 }
						
						 tranVector.add(gen);
						 tranVector.add(genU);
					 rs3.close();
					 ps3.close();
					 
					 ucount=Integer.parseInt(rs1.getObject("cnt").toString());
				 }
				 else
				 {
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
					 tranVector.add("0");
				 } 
				
		 rcount=tcount-ucount;
		 tranVector.add(rcount);
		 tranVector.add(rs.getObject("s_site_id"));
		 tranVector.add(rs.getObject("s_state_name"));
		 rs1.close();
		 ps.close();
			tranList.add(i, tranVector);
			i++;
	}
	prepStmt.close();
	rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps,ps1,ps3) , Arrays.asList(rs,rs1,rs2,rs3) , conn);
	}
	return tranList;
	}
	
	public List getWECDataUpload(String ebid, String rdate, String RType) throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
if (RType.equals("D")) {
	sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_upload;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, reportdate);
	prepStmt.setObject(2, ebid);
	prepStmt.setObject(3, reportdate);
	
}
// ps.setObject(1,item.toUpperCase() + "%");

try {
	rs = prepStmt.executeQuery();
	int i = 0;
	String Remarks="";
	while (rs.next()) {
		Vector tranVector = new Vector();
		tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
		tranVector.add(rs.getObject("WECTYPE"));
		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("OPERATINGHRS"));
		tranVector.add(rs.getObject("LULLHRS"));
		tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
		tranVector.add(rs.getObject("s_wec_id"));
		if (RType.equals("D")) 
		{
			
			Remarks=rs.getObject("S_REMARKS") == null ? "NIL": rs.getObject("S_REMARKS").toString();
		}
		else
	   {
		Remarks="NIL";
		}
	tranVector.add(Remarks);
	if (RType.equals("D")) 
	{
	tranVector.add(rs.getObject("undertrail"));
	//System.out.println("ada"+rs.getObject("undertrail").toString());
	}
	else
	{
		tranVector.add("0");
	}
	
	
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}	
	
	public List getWECDataAdmin(String ebid, String rdate, String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_ADMINDATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
		}
		if (RType.equals("DM")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_ADMINDATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("M")) {
			//System.out.println("Checking111");
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_ADMINDATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_ADMINDATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
          if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_ADMINDATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));//0
				tranVector.add(rs.getObject("WECTYPE"));//1
				tranVector.add(rs.getObject("GENERATION"));//2
				tranVector.add(rs.getObject("OPERATINGHRS"));//3
				tranVector.add(rs.getObject("LULLHRS"));//4
				tranVector.add(rs.getObject("MACHINEFAULT"));//5
				tranVector.add(rs.getObject("MACHINESD"));//6
				tranVector.add(rs.getObject("INTERNALFAULT"));//7
				tranVector.add(rs.getObject("INTERNALSD"));//8
				tranVector.add(rs.getObject("EXTERNALFAULT"));//9
				tranVector.add(rs.getObject("EXTERNALSD"));//10
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());//11
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());//12
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());//13
				tranVector.add(rs.getObject("s_wec_id"));//14
				tranVector.add(rs.getObject("FAULTHRS"));//15
				tranVector.add(rs.getObject("GIAVIAL"));//16
				tranVector.add(rs.getObject("WAVIAL"));//17
				tranVector.add(rs.getObject("WECSPDOWN"));//18
				tranVector.add(rs.getObject("EBSPDOWN"));//19
				tranVector.add(rs.getObject("CUM_GENERATION"));//20
				tranVector.add(rs.getObject("CUM_OPERATINGHRS"));//21
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getFeedbackReport()	throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "";
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
		sqlQuery = CustomerSQLC.GET_FEEDBACK;
		prepStmt = conn.prepareStatement(sqlQuery);
	
	// ps.setObject(1,item.toUpperCase() + "%");
	
	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_FEEDBACK_ID"));
			tranVector.add(rs.getObject("S_LOGIN_MASTER_ID"));
			tranVector.add(rs.getObject("S_PREVENT_RSHE"));
			tranVector.add(rs.getObject("S_PREVENT_REXE"));
			tranVector.add(rs.getObject("S_PREVENT_RQUA"));
			
			tranVector.add(rs.getObject("S_BREAKD_REXE"));
			tranVector.add(rs.getObject("S_BREAKD_RSPE"));
			tranVector.add(rs.getObject("S_BREAKD_REFF"));
			
			tranVector.add(rs.getObject("S_MANP_RCOMP"));
			tranVector.add(rs.getObject("S_MANP_RSUFF"));
			tranVector.add(rs.getObject("S_MANP_RREG"));
			
			tranVector.add(rs.getObject("S_MA_RCON"));
			tranVector.add(rs.getObject("S_GEN_RCON"));
			
			tranVector.add(rs.getObject("S_LIA_RTIME"));
			tranVector.add(rs.getObject("S_LIA_RPERSONAL"));
			
			tranVector.add(rs.getObject("S_GA_RELECT"));
			
			tranVector.add(rs.getObject("S_REPORT_RRLGR"));
			
			tranVector.add(rs.getObject("S_REPORT_RCLR"));
			tranVector.add(rs.getObject("S_REPORT_RADE"));
			
			tranVector.add(rs.getObject("S_INTER_RCONTACT"));
			tranVector.add(rs.getObject("S_INTER_RRESPON"));
			tranVector.add(rs.getObject("S_INTER_RTECH"));
			
			tranVector.add(rs.getObject("S_INTERCNT_RCONTACT"));
			tranVector.add(rs.getObject("S_INTERCNT_RRESPON"));
			tranVector.add(rs.getObject("S_INTERCNT_RTECH"));
			
			tranVector.add(rs.getObject("S_GENIMP_RMH"));
			tranVector.add(rs.getObject("S_GENIMP_RWM"));
			tranVector.add(rs.getObject("S_GENIMP_RQL"));
			
			tranVector.add(rs.getObject("S_SUGGESTION"));
			tranVector.add(rs.getObject("D_CREATED_DATE"));
			
			
			tranVector.add(rs.getObject("S_PREVENT_DSHE"));
			tranVector.add(rs.getObject("S_PREVENT_DEXE"));
			tranVector.add(rs.getObject("S_PREVENT_DQUA"));
			
			tranVector.add(rs.getObject("S_BREAKD_DEXE"));
			tranVector.add(rs.getObject("S_BREAKD_DSPE"));
			tranVector.add(rs.getObject("S_BREAKD_DEFF"));
			
			tranVector.add(rs.getObject("S_MANP_DCOMP"));
			tranVector.add(rs.getObject("S_MANP_DSUFF"));
			tranVector.add(rs.getObject("S_MANP_DREG"));
			
			tranVector.add(rs.getObject("S_MA_DCON"));
			tranVector.add(rs.getObject("S_GEN_DCON"));
			
			tranVector.add(rs.getObject("S_LIA_DTIME"));
			tranVector.add(rs.getObject("S_LIA_DPERSONAL"));
			
			tranVector.add(rs.getObject("S_GA_DELECT"));
			
			tranVector.add(rs.getObject("S_REPORT_DRLGR"));
			
			tranVector.add(rs.getObject("S_REPORT_DCLR"));
			tranVector.add(rs.getObject("S_REPORT_DADE"));
			
			tranVector.add(rs.getObject("S_INTER_DCONTACT"));
			tranVector.add(rs.getObject("S_INTER_DRESPON"));
			tranVector.add(rs.getObject("S_INTER_DTECH"));
			
			tranVector.add(rs.getObject("S_INTERCNT_DCONTACT"));
			tranVector.add(rs.getObject("S_INTERCNT_DRESPON"));
			tranVector.add(rs.getObject("S_INTERCNT_DTECH"));
			
			tranVector.add(rs.getObject("S_GENIMP_RMH"));
			tranVector.add(rs.getObject("S_GENIMP_RWM"));
			tranVector.add(rs.getObject("S_GENIMP_RQL"));
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
	}
	public List getCompareData(String ebid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		String cyear = "";
		String adate = convertDateFormat(rdate, "dd/MM/yyyy", "dd-MM-yyyy");
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, reportdate);
		}

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			////System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			//cyear = year + "-" + nyear;
			int nyear1=2000 + nyear; 
			cyear = 2000 + year + "-" + nyear1;
			//cyear = 200 + year + "-" + 200 + nyear;
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("s_wec_id"));
				if (RType.equals("Y")) {

					tranVector.add(cyear);
				} else
				{
					tranVector.add(adate);
				}
				if (RType.equals("D")) 
				{
				tranVector.add(rs.getObject("undertrail"));
				//System.out.println("ada"+rs.getObject("undertrail").toString());
				}
				else
				{
					tranVector.add("0");
				}

				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getCompareWECData(String ebid, String wecid, String rdate,String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		String cyear = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String adate = convertDateFormat(rdate, "dd/MM/yyyy", "dd-MM-yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATA_WEC;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, reportdate);
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, wecid);
		}

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGRM_DATA_WEC;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, wecid);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1; 
			}
			int nyear1=2000 + nyear; 
			cyear = 2000 + year + "-" + nyear1;
			//cyear = year + "-" + nyear;
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_DATA1;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
			prepStmt.setObject(4, wecid);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("s_wec_id"));
				if (RType.equals("D")) {
					tranVector.add(adate);
				}
				if (RType.equals("M")) {

					tranVector.add(rs.getObject("monthname"));
				}
				if (RType.equals("Y")) {
					tranVector.add(cyear);
				}
				
				if (RType.equals("D")) 
				{
				tranVector.add(rs.getObject("undertrail"));
				//System.out.println("ada"+rs.getObject("undertrail").toString());
				}
				else
				{
					tranVector.add("0");
				}
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}

	
	
	public List getCustomerDetail(String custid,String stateid,String siteid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		String cyear = "";
		
		
		if (!custid.equals("NA")) 
		{
			sqlQuery = CustomerSQLC.GET_CUSTOMER_DETAIL_BY_CUSTOMERID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
		}

		else if (!stateid.equals("NA") && siteid.equals("NA")) 
		{
			sqlQuery = CustomerSQLC.GET_CUSTOMER_DETAIL_BY_STATEID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, stateid);
		}
		else if (!stateid.equals("NA") && !siteid.equals("NA")) {
			sqlQuery = CustomerSQLC.GET_CUSTOMER_DETAIL_BY_SITEID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
		}
		else
		{
			sqlQuery = CustomerSQLC.GET_ALL_CUSTOMER_DETAIL;
			prepStmt = conn.prepareStatement(sqlQuery);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_OWNER_NAME")==null?"":rs.getObject("S_OWNER_NAME").toString());
				tranVector.add(rs.getObject("S_OEMAIL")==null?"":rs.getObject("S_OEMAIL").toString());
				tranVector.add(rs.getObject("S_OPHONE_NUMBER")==null?"":rs.getObject("S_OPHONE_NUMBER").toString());
				tranVector.add(rs.getObject("S_OCELL_NUMBER")==null?"":rs.getObject("S_OEMAIL").toString());
				tranVector.add(rs.getObject("S_OFAX_NUMBER")==null?"":rs.getObject("S_OPHONE_NUMBER").toString());
				String pdate = "",tdate="",cdate="",gdate="";
				if(rs.getObject("D_ODOB_DATE")!=null )
				{
				pdate = convertDateFormat(rs.getObject("D_ODOB_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				if(rs.getObject("D_ODOA_DATE")!=null)
				{
				tdate = convertDateFormat(rs.getObject("D_ODOA_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				tranVector.add(pdate);
				tranVector.add(tdate);
				
				tranVector.add(rs.getObject("S_CONTACT_PERSON_NAME")==null?"":rs.getObject("S_CONTACT_PERSON_NAME").toString());
				tranVector.add(rs.getObject("S_CORRES_ADD")==null?"":rs.getObject("S_CORRES_ADD").toString());
				tranVector.add(rs.getObject("S_CITY")==null?"":rs.getObject("S_CITY").toString());
				tranVector.add(rs.getObject("S_ZIP")==null?"":rs.getObject("S_ZIP").toString());
				tranVector.add(rs.getObject("S_EMAIL")==null?"":rs.getObject("S_EMAIL").toString());
				tranVector.add(rs.getObject("S_PHONE_NUMBER")==null?"":rs.getObject("S_PHONE_NUMBER").toString());
				tranVector.add(rs.getObject("S_CELL_NUMBER")==null?"":rs.getObject("S_CELL_NUMBER").toString());
				tranVector.add(rs.getObject("S_FAX_NUMBER")==null?"":rs.getObject("S_FAX_NUMBER").toString());
				if(rs.getObject("D_DOB_DATE")!=null)
				{
				pdate = convertDateFormat(rs.getObject("D_DOB_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				if(rs.getObject("D_DOA_DATE")!=null )
				{
				tdate = convertDateFormat(rs.getObject("D_DOA_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				
				tranVector.add(cdate);
				tranVector.add(gdate);
				tranVector.add(rs.getObject("ACTIVATED").toString()=="0"?"NO":"YES");
				tranVector.add(rs.getObject("EMAILACTIVATED").toString()=="0"?"NO":"YES");
				
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getLoginHistoryDetail() throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
		String cyear = "";
		sqlQuery = CustomerSQLC.GET_LOGIN_HISTORY_DETAIL;
		prepStmt = conn.prepareStatement(sqlQuery);
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_LOGIN_MASTER_ID"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_USER_ID")==null?"":rs.getObject("S_USER_ID").toString());
				tranVector.add(rs.getObject("S_PASSWORD")==null?"":rs.getObject("S_PASSWORD").toString());
				tranVector.add(rs.getObject("S_REMARKS")==null?"":rs.getObject("S_REMARKS").toString());
				tranVector.add(rs.getObject("S_CREATED_BY")==null?"":rs.getObject("S_CREATED_BY").toString());
				String pdate = "",tdate="";
				if(rs.getObject("D_CREATED_DATE")!=null )
				{
				 pdate = convertDateFormat(rs.getObject("D_CREATED_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				
				tranVector.add(pdate);
				tranVector.add(rs.getObject("S_MODIFIED_BY")==null?"":rs.getObject("S_MODIFIED_BY").toString());
				if(rs.getObject("D_MODIFIED_DATE")!=null )
				{
				 tdate = convertDateFormat(rs.getObject("D_MODIFIED_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				}
				
				tranVector.add(tdate);
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	public List getWECMonthwise(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		int day = reportdate.getDate();
		int month = reportdate.getMonth();
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}

		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);

		sqlQuery = CustomerSQLC.GET_WEC_YEARLY_DATA_MONTHWISE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, wecid);
		prepStmt.setObject(3, pdate);
		prepStmt.setObject(4, ndate);

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("monthname"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	public List getWECSitewise(String siteid, String stid,String custid, String rdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		int day = reportdate.getDate();
		int month = reportdate.getMonth();
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}

		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);

		sqlQuery = CustomerSQLC.GET_WECSITE_MONTHWISE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, siteid);
		prepStmt.setObject(3, pdate);
		prepStmt.setObject(4, ndate);
        DaoUtility.displayQueryWithParameter(57, sqlQuery,custid,siteid,pdate,ndate );
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(57, rs);
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("monthname")+"-"+rs.getObject("REPYR"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	public List getWECStatewise(String stid,String custid, String rdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		int day = reportdate.getDate();
		int month = reportdate.getMonth();
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}

		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);

		sqlQuery = CustomerSQLC.GET_WECSTATE_MONTHWISE;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, custid);
		prepStmt.setObject(2, stid);
		prepStmt.setObject(3, pdate);
		prepStmt.setObject(4, ndate);
        DaoUtility.displayQueryWithParameter(65, sqlQuery, custid,stid,pdate,ndate);
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				DaoUtility.getRowCount(65, rs);
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("monthname")+"-"+rs.getObject("REPYR"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWECMonthwiseAdmin(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		int day = reportdate.getDate();
		int month = reportdate.getMonth();
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}

		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);

		sqlQuery = CustomerSQLC.GET_WEC_YEARLY_admin_monthwise;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, wecid);
		prepStmt.setObject(3, pdate);
		prepStmt.setObject(4, ndate);

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("monthname"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());

				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWECDetail(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATAM;   
			prepStmt = conn.prepareStatement(sqlQuery);
			//prepStmt.setObject(1, reportdate);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
			
		}
		if (RType.equals("T")) {
			sqlQuery = CustomerSQLC.GET_WEC_monthly_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
		}
		if (RType.equals("Y")) {

			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}

			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);

			sqlQuery = CustomerSQLC.GET_WEC_DGRY;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);

		}
		if (RType.equals("YT")) {
			sqlQuery = CustomerSQLC.GET_WEC_YEARLY_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				String pdate = "";
				if (RType.equals("M"))
					pdate = convertDateFormat(rs.getObject("D_READING_DATE").toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
				else
					pdate = rs.getObject("month").toString();

				tranVector.add(pdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				
				
				if (RType.equals("M")) 
				{
				tranVector.add(rs.getObject("undertrail"));
				//System.out.println("ada"+rs.getObject("undertrail").toString());
				tranVector.add("0");
				}
				else
				{
					tranVector.add("0");
				}
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWECDetailTotal(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_monthly_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);

			sqlQuery = CustomerSQLC.GET_WEC_YEARLY_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);
			
			
			
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranVector.add(rs.getObject("TRIALRUN"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getWECDetailAdmin(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {


		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_DGR_DATAADMINM;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
		}

		if (RType.equals("Y")) {

			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}

			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);

			sqlQuery = CustomerSQLC.GET_WEC_DGRY_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);

		}
		if (RType.equals("YD")) {

			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());

			sqlQuery = CustomerSQLC.GET_WEC_DGRY_ADMIN1;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);

		}
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				String pdate = "";
				if (RType.equals("M")||RType.equals("YD"))
				{
					//pdate = convertDateFormat(rs.getObject("D_READING_DATE").toString(), "yyyy-MM-dd","dd-MMM-yyyy"	);
					pdate = convertDateFormat(rs.getObject("D_READING_DATE").toString().toString(), "yyyy-MM-dd", "dd-MMM-yyyy");
					//convertDateFormat(rs.getObject("D_MODIFIED_DATE").toString(), "yyyy-MM-dd", "dd-MM-yyyy");
					//pdate=format.parse(rs.getObject("D_READING_DATE").toString());
				}
				else 
					pdate = rs.getObject("month").toString();

				tranVector.add(pdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}

	public String getEBRemarks(String ebid,String fdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sremarks="";
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

			sqlQuery = CustomerSQLC.GET_EB_EMARKS;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, fromdate);
			
        
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if(rs.next()) {
				
				sremarks=rs.getString("S_REMARKS");
				
				
				
			}
			
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return sremarks;
	}

	public List getMPRDetailAdmin(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		String ofadate = fdate;
		String otdate = tdate;
		AdminDao ad = new AdminDao();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());
		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
		/*List<java.sql.Date> dates = null;
		dates = getDates(fdate,tdate);*/
		fdate= ad.convertDateFormat(fdate,"dd/MM/yyyy","dd-MMM-yyyy");
		tdate= ad.convertDateFormat(tdate,"dd/MM/yyyy","dd-MMM-yyyy");
	
          if(wectype.equals("ALL"))
          	{
        	  
			// sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN_ALL;
        	sqlQuery = "select wec.S_WEC_ID,S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
    		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
    		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
    		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
    		"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
    		"from VW_WEC_MPRDATA wec,vw_dgr_heading dgr "+ 
    		"WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID IN ('"+siteid+"') and dgr.S_CUSTOMER_ID IN('"+customerid+"') "+
    		"AND wec.D_READING_DATE between '"+fdate+"' and '"+tdate+"' " +
    		"group by wec.S_WECSHORT_DESCR,wec.S_WEC_ID ORDER BY wec.S_WECSHORT_DESCR ";
        	//" AND wec.D_READING_DATE between '01-JUN-2011' and '21-JUN-2011' group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
			prepStmt = conn.prepareStatement(sqlQuery);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			
			//System.out.println(sqlQuery);
			//prepStmt.setObject(1, siteid);
			//prepStmt.setObject(2, customerid);
			
			//prepStmt.setObject(3, fromdate);
			//prepStmt.setObject(4, todate);
			
          }
          else{
        	  
        	sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, customerid);
			prepStmt.setObject(3, wectype);
			prepStmt.setObject(4, fromdate);
			prepStmt.setObject(5, todate);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), siteid, customerid, wectype, fromdate, todate);
			}
          }
		// ps.setObject(1,item.toUpperCase() + "%");
		try {
			 
			rs = prepStmt.executeQuery();
			int i = 0;
			int noOfLullHour = 0;
			while (rs.next()) {
				Vector<String> tranVector = new Vector<String>();
				tranVector.add(rs.getString("S_WECSHORT_DESCR"));
				//System.out.println("------" + rs.getObject("S_WECSHORT_DESCR") + "-------");
				tranVector.add(rs.getString("GENERATION"));
				tranVector.add(rs.getString("OPERATINGHRS"));
				//System.out.println("-------------------------" + rs.getString("LULLHRS"));
				/*------------------------------------------------*/
				if(wectype.equalsIgnoreCase("ALL")){
					
					String lullHour = "";
					int totalLullMInute = 0;
					
					//String wecDescription = getWECDescription((String)rs.getObject("S_WECSHORT_DESCR"));
					String wecID = rs.getString("S_WEC_ID");
					
					/*Tested*/
					int totalLoadRestingInMinutesForOneWECs = getTotalLoadResting(fdate,tdate,wecID);
					
					lullHour = rs.getString("LULLHRS");
					//System.out.println("Lull Hour:" + rs.getString("LULLHRS"));
					if(lullHour.equalsIgnoreCase("") || lullHour == null){
						//System.out.println("Lull \"\"");
						tranVector.add("00:00");
					}
					else{
						//System.out.println("Lull Has");
						totalLullMInute = TimeUtility.convertTimeStringToMinutes(lullHour, ":");
					
						//System.out.println("Lull Minute:" + totalLullMInute);
						//System.out.println("Load Minute:" + totalLoadRestingInMinutesForOneWECs);
						String lullHourModified = "";
						if(totalLoadRestingInMinutesForOneWECs > totalLullMInute){
							//System.out.println("Load greater");
							lullHourModified = "00:00";
						}
						else{
							int diffInMinute = totalLullMInute - totalLoadRestingInMinutesForOneWECs;
							int[] hourMinutwwe = TimeUtility.convertMinutesToHHMMArray(diffInMinute);
							lullHourModified = TimeUtility.convertHHMMArrayToTimeString(hourMinutwwe, ":");
							
						}
						//System.out.println("Old Luu hOur:" + lullHour);
						//System.out.println("New Lull Hour:" + lullHourModified);
						tranVector.add(lullHourModified);
					}
				}
				else{
				/*------------------------------------------------*/
					tranVector.add(rs.getString("LULLHRS"));
				}
				
				tranVector.add(rs.getString("MACHINEFAULT"));
				tranVector.add(rs.getString("MACHINESD"));
				
				tranVector.add(rs.getString("EXTERNALFAULT"));
				tranVector.add(rs.getString("EXTERNALSD"));
				tranVector.add(rs.getString("FAULTHRS"));
				tranVector.add(rs.getString("WAVIAL"));
				tranVector.add(rs.getString("CFACTOR"));
				tranVector.add(rs.getString("GIAVIAL"));
				tranVector.add(rs.getString("GAVIAL"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			System.out.println("getMPRDetail Exception");
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	private int getTotalLoadResting(String fdate, String tdate, String WECId) {
		//System.out.println("Inside getTotalLoadResting");
	
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		PreparedStatement prepStmt1 = null;
		ResultSet rs1 = null;
		
		int totalLoadInMinute = 0;
		int tMin = 0;
		int tHour = 0;
		int[] minuteHour = new int[2];
		int recordCount = 0;
		try{
			conn = wcareConnector.getConnectionFromPool();
			//System.out.println("-------" + WECId + " -------");
			sqlQuery = "Select count(*) From Tbl_Wec_Reading Where S_Wec_Id = ? And S_Mp_Id IN ('0810000001') And D_Reading_Date between '"+fdate+"' and '"+tdate+"' order by D_Reading_Date";
			prepStmt1 = conn.prepareStatement(sqlQuery);
			prepStmt1.setObject(1, WECId);
			rs1 = prepStmt1.executeQuery();
			while(rs1.next()){
				recordCount = rs1.getInt(1);
			}
			//System.out.println("Record Count for LOAD resting:" + recordCount);
			if(recordCount != 0){
				sqlQuery = "Select * From Tbl_Wec_Reading Where S_Wec_Id = ? And S_Mp_Id IN ('0810000001') And D_Reading_Date between '"+fdate+"' and '"+tdate+"' order by D_Reading_Date";
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, WECId);
				
				rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					
					//System.out.println("Reading Date:" + rs.getString("D_READING_DATE") + ",N_VAlue:" + rs.getString("N_Value"));
					String n_value = rs.getString("N_Value");
					/*int minnnnn = GlobalUtils.getMinutesModified(n_value);
					minuteHour = GlobalUtils.minuteToHour(minnnnn);
					tHour += minuteHour[0];
					tMin += minuteHour[1];*/					
					int minnnnn = TimeUtility.convertTimeStringToMinutes(n_value, ".");
					minuteHour = TimeUtility.convertMinutesToHHMMArray(minnnnn);
					tHour += minuteHour[1];
					tMin += minuteHour[0];
				}
	
				minuteHour = TimeUtility.convertMinutesToHHMMArray(tMin);
				
				tMin = minuteHour[0];
				tHour += minuteHour[1];
				
				totalLoadInMinute = tHour * 60 + tMin;
				//System.out.println("Return VAlue:" + totalLoadInMinute);
				//System.out.println("Outside getTotalLoadResting");
				return totalLoadInMinute;
			}
			else{
				//System.out.println("Outside getTotalLoadResting");
				return 0;
			}
		}
		catch(Exception e){
			System.out.println("getTotalLoadResting Exception");
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return totalLoadInMinute;
	}
	private int getTotalLullHourInMinutesBasedOnWECIdForParticularDay(String wecID,java.sql.Date date, Connection conn) {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
				
				
				
				int MFMSInMinutes = getMFMSInMinutesForOneWECUsingWECId(wecID,date,conn);
				int GSGFInMinutes = getGFGSInMinutesPerWECUsingId(wecID,date,conn);
				int operatingTimeInMinutes = getOperatingHourInMinutesPerWECUsingId(wecID,date,conn);
				System.out.println("WEC ID: "+wecID+ "  Date:" + date + "   MFMS:" + MFMSInMinutes + "   GFGS:" + GSGFInMinutes) ;
				return 0;
	}

	private int getOperatingHourInMinutesPerWECUsingId(String wecID,
			java.sql.Date date, Connection conn) {

		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int totalHours = 0;
		int totalMinutes = 0;
		int totalTimesInMinutes = 0;
		
		try{
			
			sqlQuery = "Select N_Actual_Value, S_MP_ID " +
						"From Tbl_Wec_Reading " +
						"Where S_Mp_Id In ('0808000023') " +
						"And S_Wec_Id In (?) " +
						"And D_Reading_Date = ?";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecID);
			prepStmt.setObject(2, date);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String actualValue = rs.getString(1);
				if(actualValue.contains(".")){
					String[] splitActualValue = actualValue.split(".");
					totalHours += Integer.parseInt(splitActualValue[0]);
					if(splitActualValue.length > 0){
						totalMinutes += Integer.parseInt(splitActualValue[1]);
					}
				}
				else{
					totalHours += Integer.parseInt(actualValue);
				}
			}
			if(totalMinutes > 0){
				int[] ss = GlobalUtils.minuteToHour(totalMinutes);
				totalMinutes = ss[1];
				totalHours += ss[0];
			}
			totalTimesInMinutes = totalHours * 60 + totalMinutes;
			return totalTimesInMinutes;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, null);
			
		}
		return 0;
	}

	private int getGFGSInMinutesPerWECUsingId(String wecID, java.sql.Date date, Connection conn) {
		
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int totalHours = 0;
		int totalMinutes = 0;
		int totalTimesInMinutes = 0;
		
		try{
			
			sqlQuery = "Select N_Actual_Value, S_MP_ID " +
						"From Tbl_Wec_Reading " +
						"Where S_Mp_Id In ('0808000027','0808000028','0808000029','1000000001') " +
						"And S_Wec_Id In (?) " +
						"And D_Reading_Date = ?";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecID);
			prepStmt.setObject(2, date);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String actualValue = rs.getString(1);
				if(actualValue.contains(".")){
					String[] splitActualValue = actualValue.split(".");
					totalHours += Integer.parseInt(splitActualValue[0]);
					if(splitActualValue.length > 0){
						totalMinutes += Integer.parseInt(splitActualValue[1]);
					}
				}
				else{
					totalHours += Integer.parseInt(actualValue);
				}
			}
			int[] ss = GlobalUtils.minuteToHour(totalMinutes);
			totalMinutes = ss[1];
			totalHours += ss[0];
			totalTimesInMinutes = totalHours * 60 + totalMinutes;
			return totalTimesInMinutes;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, null);
			
		}
		return 0;
	}

	private int getMFMSInMinutesForOneWECUsingWECId(String wecID, java.sql.Date date, Connection conn) {
		
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int totalHours = 0;
		int totalMinutes = 0;
		int totalTimesInMinutes = 0;
		
		try{
			
			sqlQuery = "Select N_Actual_Value, S_MP_ID " +
						"From Tbl_Wec_Reading " +
						"Where S_Mp_Id In ('0808000025','0808000026') " +
						"And S_Wec_Id In (?) " +
						"And D_Reading_Date = ?";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecID);
			prepStmt.setObject(2, date);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String actualValue = rs.getString(1);
				if(actualValue.contains(".")){
					String[] splitActualValue = actualValue.split(".");
					totalHours += Integer.parseInt(splitActualValue[0]);
					if(splitActualValue.length > 0){
						totalMinutes += Integer.parseInt(splitActualValue[1]);
					}
				}
				else{
					totalHours += Integer.parseInt(actualValue);
				}
			}
			int[] ss = GlobalUtils.minuteToHour(totalMinutes);
			totalMinutes = ss[1];
			totalHours += ss[0];
			totalTimesInMinutes = totalHours * 60 + totalMinutes;
			return totalTimesInMinutes;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, null);
			
		}
		return 0;
	}

	private Object JDBCCommonFormat() {
	//	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "";
			prepStmt = conn.prepareStatement(sqlQuery);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				
			}
			return null;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;
		}
	
	private List<java.sql.Date> getDates(String fdate, String tdate) {
		final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date fromDate = null;
		java.util.Date toDate = null;
		try {
			fromDate = format.parse(fdate);
			toDate = format.parse(tdate);
		} catch (ParseException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
		//System.out.println(fdate + " to " + tdate);
		List<java.sql.Date> dateList = new LinkedList<java.sql.Date>();
		for (long t = fromDate.getTime(); t < toDate.getTime() ; t += DAY_IN_MILLIS) {
			java.util.Date utilDate = new java.util.Date(t);
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		    dateList.add(sqlDate);
		}
		//System.out.println(dateList.toString());
		return dateList;
	}

	private String getWECIDFromDescription(String wecDescription) {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String wecID = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select S_WEC_ID From Tbl_Wec_Master Where S_Wecshort_Descr in (?)";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecDescription);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecID = rs.getString("S_WEC_ID");
			}
			return wecID;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;
	}

	private String getWECDescription(String object) {
		String[] a = object.split(" ");
		return a[0];
	}

	public List getSubMPRDetailAdmin(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		AdminDao ad = new AdminDao();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());
		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
		fdate= ad.convertDateFormat(fdate,"dd/MM/yyyy","dd-MMM-yyyy");
		tdate= ad.convertDateFormat(tdate,"dd/MM/yyyy","dd-MMM-yyyy");
		
	
          if(wectype.equals("ALL"))
          {			
        	sqlQuery = "select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
    		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
    		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
    		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
    		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from " +
    		"VW_WEC_MPRDATA wec,vw_sub_dgr_heading dgr  "+ 
    		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID IN ('"+siteid+"') and dgr.S_CUSTOMER_ID IN('"+customerid+"') "+
    		" AND wec.D_READING_DATE between '"+fdate+"' and '"+tdate+"' group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
        	
			prepStmt = conn.prepareStatement(sqlQuery);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
			}
			
          }
          else
          { 
        	sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, customerid);
			prepStmt.setObject(3, wectype);
			prepStmt.setObject(4, fromdate);
			prepStmt.setObject(5, todate);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), siteid, customerid, wectype, fromdate, todate);
			} 
          }
		
     
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	public List getCpmDetailAdmin(String siteid,String customerid,String rdate,String wectype,String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";
        String cyear="";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		String adate = convertDateFormat(rdate, "dd/MM/yyyy", "dd-MM-yyyy");
		
	
          if(wectype.equals("ALL"))
          {
        	  
        	if(RType.equals("D"))
        	{
        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_CMP_ALL;
        		prepStmt = conn.prepareStatement(sqlQuery);
        		prepStmt.setObject(1, siteid);
    			prepStmt.setObject(2, customerid);
    			
    			prepStmt.setObject(3, fromdate);
        	}
        	if(RType.equals("M"))
        	{
        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_CMP_ALL_1;
        		prepStmt = conn.prepareStatement(sqlQuery);
        		prepStmt.setObject(1, siteid);
    			prepStmt.setObject(2, customerid);
    			
    			prepStmt.setObject(3, fromdate);
    			prepStmt.setObject(4, fromdate);
        	}
        	if (RType.equals("Y")) {
    			int day = fromdate.getDate();
    			int month = fromdate.getMonth() + 1;
    			int year = fromdate.getYear() - 100;
    			int nyear = year;
    			//System.out.println("Month: " + month);
    			////System.out.println("Year: " + year);asdas
    			if (month >= 4) {
    				nyear = year + 1;
    			} else {
    				nyear = year;
    				year = year - 1;
    			}
    			//cyear = year + "-" + nyear;
    			int nyear1=2000 + nyear; 
    			cyear = 2000 + year + "-" + nyear1;
    			String pdate = "01-APR-" + Integer.toString(year);
    			String ndate = "31-MAR-" + Integer.toString(nyear);

			 
        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN;
        		prepStmt = conn.prepareStatement(sqlQuery);
        		prepStmt.setObject(1, siteid);
    			prepStmt.setObject(2, customerid);
    			
    			prepStmt.setObject(3, pdate);
    			prepStmt.setObject(3, ndate);
        	}
			
			
		
			
          }
          else
          { 
        	  
        	  
        	  if(RType.equals("D"))
          	{
          		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_CMP_ALL_2;
          		prepStmt = conn.prepareStatement(sqlQuery);
          		prepStmt.setObject(1, siteid);
      			prepStmt.setObject(2, customerid);
      			prepStmt.setObject(3, wectype);
      			prepStmt.setObject(4, fromdate);
          	}
          	if(RType.equals("M"))
          	{
          		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_CMP_ALL_3;
          		prepStmt = conn.prepareStatement(sqlQuery);
          		prepStmt.setObject(1, siteid);
      			prepStmt.setObject(2, customerid);
      			prepStmt.setObject(3, wectype);
      			prepStmt.setObject(4, fromdate);
      			prepStmt.setObject(5, fromdate);
          	}
          	if (RType.equals("Y")) {
      			int day = fromdate.getDate();
      			int month = fromdate.getMonth() + 1;
      			int year = fromdate.getYear() - 100;
      			int nyear = year;
      			//System.out.println("Month: " + month);
      			////System.out.println("Year: " + year);asdas
      			if (month >= 4) {
      				nyear = year + 1;
      			} else {
      				nyear = year;
      				year = year - 1;
      			}
      			//cyear = year + "-" + nyear;
      			int nyear1=2000 + nyear; 
    			cyear = 2000 + year + "-" + nyear1;
      			String pdate = "01-APR-" + Integer.toString(year);
      			String ndate = "31-MAR-" + Integer.toString(nyear);

  			 
          		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN;
          		prepStmt = conn.prepareStatement(sqlQuery);
          		prepStmt.setObject(1, siteid);
      			prepStmt.setObject(2, customerid);
      			prepStmt.setObject(3, wectype);
      			prepStmt.setObject(4, pdate);
      			prepStmt.setObject(5, ndate);
          	}
  			///prepStmt = conn.prepareStatement(sqlQuery);
        	
			
        	  
          }
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				
				if (RType.equals("D")) {
					tranVector.add(adate);
				}
				if (RType.equals("M")) {

					tranVector.add(rs.getObject("monthname"));
				}
				if (RType.equals("Y")) {
					tranVector.add(cyear);
				}
				tranVector.add(rs.getObject("CNT"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getCustList(String uid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {


		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	//	java.util.Date ffd = format.parse(fdate);
		

			sqlQuery = CustomerSQLC.GET_CUSTOMER_LIST;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, uid);
			
			
          
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getSiteList(String uid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	//	java.util.Date ffd = format.parse(fdate);
		

			sqlQuery = CustomerSQLC.GET_CUSTOMER_SITELIST;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, uid);
			
			
          
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_SITE_NAME"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	public List getStateList(String uid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	//	java.util.Date ffd = format.parse(fdate);
		

			sqlQuery = CustomerSQLC.GET_CUSTOMER_STATELIST;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, uid);
			
			
          
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_STATE_NAME"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	public List getWecTypeList(String uid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	//	java.util.Date ffd = format.parse(fdate);
		

			sqlQuery = CustomerSQLC.GET_CUSTOMER_WECTYPE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, uid);
			
			
          
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WEC_TYPE"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getIPAddress(String uid,String rdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		

			sqlQuery = CustomerSQLC.GET_IP_ADDRESS;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, uid);
			prepStmt.setObject(2, reportdate);
			
          
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_IP_ADDRESS"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	
	
	public List getQueryDetail(String userid,String type) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		
	
          if(type.equals("ALL"))
          {
			sqlQuery = CustomerSQLC.GET_QUERY_OF_ALL_CUSTOMER; 
			prepStmt = conn.prepareStatement(sqlQuery);
			
			
          }
          else if(type.equals("C"))
          { 
        	sqlQuery = CustomerSQLC.GET_QUERY_BY_CUSTOMER;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, userid);
			
        	  
          }
          else
          {
        	  sqlQuery = CustomerSQLC.GET_QUERY_BY_CUSTOMER_QUERY_ID;
  				prepStmt = conn.prepareStatement(sqlQuery);
  				prepStmt.setObject(1, userid); 
          }
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_CUSTOMER_QUERY_ID"));
				tranVector.add(rs.getObject("S_MESSAGE_HEAD").toString().trim());
				tranVector.add(rs.getObject("S_MESSAGE_DESC").toString().trim());
				tranVector.add(rs.getObject("S_MESSAGE_SIGN").toString().trim());
				tranVector.add(rs.getObject("S_MESSAGE_REPLYDESC")==null?"NA":rs.getObject("S_MESSAGE_REPLYDESC").toString().trim());
				tranVector.add(rs.getObject("S_STATUS"));
				tranVector.add(rs.getObject("S_SENDER_EMAILID").toString().trim());
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				String submit_date="",reply_date="";
			
				if(rs.getObject("D_SUBMITTED_DATE")!=null )
				{
					submit_date = convertDateFormat(rs.getObject("D_SUBMITTED_DATE").toString(), "yyyy-MM-dd", "dd-MM-yyyy");
				}
				
				if(rs.getObject("D_REPLIED_DATE")!=null )
				{
					reply_date = convertDateFormat(rs.getObject("D_REPLIED_DATE").toString(), "yyyy-MM-dd", "dd-MM-yyyy");
				}
				
				tranVector.add(rs.getObject("S_LOGIN_DESCRIPTION"));
				tranVector.add(submit_date);
				tranVector.add(reply_date);
				
				
				
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
	public List getMPRSerachByWec(String siteid,String customerid,String fdate,String tdate,String wectype,String wecid,String ebid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
	
          if(!wecid.equals("ALL"))
          {
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN_BY_WEC;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, fromdate);
			prepStmt.setObject(4, todate);
			
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName(), siteid, wecid, fromdate, todate);
			}
          }
          else
          
          { 
        	sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_ADMIN_BY_EB;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			
			prepStmt.setObject(2, ebid);
			prepStmt.setObject(3, fromdate);
			prepStmt.setObject(4, todate);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), siteid, ebid, fromdate, todate);
			}  
          }
		
     
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.clearParameters();
			prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	public List getFilterDetail(String stateid,String siteid,String fdate,String tdate,String wectype,String fobject,String ftype,String firstparam,String secparam,String ebobject) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String nfdate=convertDateFormat(fdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		boolean n=true;
	 
          
			sqlQuery = "SELECT  D_READING_DATE,A.OPERATINGHRS,A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,A.GENERATION,A.MAVIAL,A.GAVIAL,A.GIAVIAL,A.CFACTOR,NVL(A.S_REMARKS,'NA') S_REMARKS FROM " +
			" VW_WEC_ADMINDATA A,VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN '"+nfdate+"' AND '"+ntdate+"' AND A.S_EB_ID=B.S_EB_ID ";
			if(!stateid.equals("ALL"))
			{
				sqlQuery = sqlQuery + " AND B.S_STATE_ID='" +stateid + "'";
			}
			if(!siteid.equals("ALL"))
			{
				sqlQuery = sqlQuery + " AND B.S_SITE_ID='" +siteid + "'";
			}
			
			if(!wectype.equals("ALL"))
			{
				sqlQuery = sqlQuery + " AND A.WECTYPE='" +wectype + "'";
			}
			
			if(fobject.equals("MA"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.MAVIAL=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.MAVIAL>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.MAVIAL<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.MAVIAL>=" +firstparam +" AND A.MAVIAL<= " +secparam;
				}
			}
			
			if(fobject.equals("GA"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL>=" +firstparam +" AND A.GAVIAL<= " +secparam;
				}
			}
			if(fobject.equals("CF"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR>=" +firstparam +" AND A.CFACTOR<= " +secparam;
				}
			}
			if(fobject.equals("GN"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.GENERATION=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.GENERATION>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.GENERATION<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.GENERATION>=" +firstparam +" AND A.GENERATION<= " +secparam;
				}
				n=false;
			}
			if(n==true)
			{
				if(ebobject.equals("PM"))
				{
					sqlQuery = sqlQuery + " AND TIMETOSECOND(A.MACHINESD)>0";
				}
				if(ebobject.equals("BD"))
				{
					sqlQuery = sqlQuery + " AND TIMETOSECOND(A.MACHINEFAULT)>0";
				}
			}
			
			
			sqlQuery = sqlQuery + " ORDER BY D_READING_DATE,A.S_EB_ID,A.S_WECSHORT_DESCR";
			  prepStmt = conn.prepareStatement(sqlQuery);
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				String readdate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy");	
		    	   
				tranVector.add(readdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("MAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranVector.add(rs.getObject("S_REMARKS"));
				tranVector.add(rs.getObject("S_EB_ID"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			//prepStmt.clearParameters();
			//prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}

	
	public List getWecSelectedListDetail(String weclist,String fdate,String tdate,String ptype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String nfdate=convertDateFormat(fdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		
	 
          if(ptype.equals("D"))
          {
			sqlQuery = "SELECT D_READING_DATE,A.OPERATINGHRS,A.lullhrs,A.OPERATINGHRS,A.CUM_OPERATINGHRS,A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,A.GENERATION,A.CUM_GENERATION,A.MAVIAL,A.GIAVIAL,A.GAVIAL,A.CFACTOR,NVL(A.S_REMARKS,'NA') S_REMARKS FROM "+
						" VW_WEC_ADMINDATA A,VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' AND A.S_EB_ID=B.S_EB_ID AND A.S_WEC_ID IN("+ weclist +")";
		    sqlQuery = sqlQuery + " ORDER BY D_READING_DATE,A.S_EB_ID,A.S_WECSHORT_DESCR";
          }
          if(ptype.equals("M"))
          {
		    sqlQuery = "SELECT TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'YYYY') AS MYEAR ,sectotime(sum(timetosecond(A.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(A.lullhrs))) AS lullhrs" +
		    		" ,A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,round(sum(A.GENERATION),2) as GENERATION," +
		    		"round(avg(A.MAVIAL),2) as MAVIAL,round(avg(A.GIAVIAL),2) as GIAVIAL,round(avg(A.GAVIAL),2) as GAVIAL,round(avg(A.CFACTOR),2)  as CFACTOR,'NA' AS  S_REMARKS  FROM VW_WEC_ADMINDATA A," +
		    		"VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' AND A.S_EB_ID=B.S_EB_ID AND A.S_WEC_ID IN("+ weclist +")";
		    sqlQuery = sqlQuery + " GROUP  BY TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'YYYY'),A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE";
		    sqlQuery = sqlQuery + " ORDER BY A.S_EB_ID,A.S_WECSHORT_DESCR";
          }
          
			prepStmt = conn.prepareStatement(sqlQuery);
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				String readdate="";
				if(ptype.equals("D"))
				 readdate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"	);	
				else
				 readdate=rs.getObject("MYEAR").toString();
				tranVector.add(readdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("MAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranVector.add(rs.getObject("S_REMARKS"));
				tranVector.add(rs.getObject("S_EB_ID"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("lullhrs"));
				if(ptype.equals("D")){
					tranVector.add(rs.getObject("CUM_GENERATION"));
					tranVector.add(rs.getObject("CUM_OPERATINGHRS"));	
				}
				
				tranList.add(i, tranVector);
				i++;
			}
			//prepStmt.clearParameters();
			//prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	
	public List getEBSelectDetail(String weclist,String fdate,String tdate,String ptype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String nfdate=convertDateFormat(fdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		
	 
          if(ptype.equals("D"))
          {
		    sqlQuery = "SELECT DISTINCT A.S_EB_ID,B.D_READING_DATE,B.S_EB_DESCRIPTION,B.S_CUSTOMER_NAME,B.KWHEXPORT,B.KWHIMPORT,NVL(B.S_REMARKS,'NA') S_REMARKS FROM "+
		    			" TBL_WEC_MASTER A,VW_EB_DATA B WHERE B.D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' AND A.S_EB_ID=B.S_EB_ID AND A.S_WEC_ID IN("+ weclist +")";
		    sqlQuery = sqlQuery + "ORDER BY D_READING_DATE,A.S_EB_ID,B.S_EB_DESCRIPTION";
          }
          if(ptype.equals("M"))
          {
        	  sqlQuery = "SELECT DISTINCT A.S_EB_ID,TO_CHAR(TO_DATE(b.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(B.D_READING_DATE, 'DD-MON-YY') ,'YYYY') AS D_READING_DATE,B.S_EB_DESCRIPTION,B.S_CUSTOMER_NAME,round(sum(B.KWHEXPORT)) KWHEXPORT,round(sum(B.KWHIMPORT)) KWHIMPORT,'NA' S_REMARKS FROM  "+
  			" TBL_WEC_MASTER A,VW_EB_DATA B WHERE B.D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' AND A.S_EB_ID=B.S_EB_ID AND A.S_WEC_ID IN("+ weclist +")";
        	  sqlQuery = sqlQuery + "  GROUP BY A.S_EB_ID,B.S_EB_DESCRIPTION,B.S_CUSTOMER_NAME,TO_CHAR(TO_DATE(b.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(B.D_READING_DATE, 'DD-MON-YY') ,'YYYY')  ORDER BY D_READING_DATE,A.S_EB_ID,B.S_EB_DESCRIPTION";
  }
         
			prepStmt = conn.prepareStatement(sqlQuery);
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_EB_DESCRIPTION"));
				
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
			
				String readdate="";
				if(ptype.equals("D"))
				 readdate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy");	
				else
				 readdate=rs.getObject("D_READING_DATE").toString();
				tranVector.add(readdate);
				tranVector.add(rs.getObject("KWHEXPORT"));
				tranVector.add(rs.getObject("KWHIMPORT"));
				tranVector.add(rs.getObject("S_REMARKS"));
				tranVector.add(rs.getObject("S_EB_ID"));
				
				
				
				tranList.add(i, tranVector);
				i++;
			}
			//prepStmt.clearParameters();
			//prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}
public List getEBSelectedListDetail(String weclist,String fdate,String tdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String nfdate=convertDateFormat(fdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		
	 
          
			sqlQuery = "SELECT D_READING_DATE,A.OPERATINGHRS,A.lullhrs,A.OPERATINGHRS,A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,A.GENERATION,A.MAVIAL,A.GIAVIAL,A.GAVIAL,A.CFACTOR,NVL(A.S_REMARKS,'NA') S_REMARKS FROM "+
						" VW_WEC_ADMINDATA A,VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN '"+ nfdate +"' AND '"+ ntdate +"' AND A.S_EB_ID=B.S_EB_ID AND A.S_WEC_ID IN("+ weclist +")";
		    sqlQuery = sqlQuery + " ORDER BY D_READING_DATE,A.S_EB_ID,A.S_WECSHORT_DESCR";
			prepStmt = conn.prepareStatement(sqlQuery);
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				String readdate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"	);	
		    	   
				tranVector.add(readdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("MAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranVector.add(rs.getObject("S_REMARKS"));
				tranVector.add(rs.getObject("S_EB_ID"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("lullhrs"));
				
				
				tranList.add(i, tranVector);
				i++;
			}
			//prepStmt.clearParameters();
			//prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	return tranList;
}
public List getCustFilterDetail(String fdate,String tdate,String fobject,String ftype,String firstparam,String secparam,String userid,String ebobject) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String nfdate=convertDateFormat(fdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
		

          
			sqlQuery = "SELECT D_READING_DATE,A.OPERATINGHRS,A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,A.GENERATION,A.MAVIAL,A.MIAVIAL,A.GIAVIAL,A.GAVIAL,A.CFACTOR,NVL(A.S_REMARKS,'NA') S_REMARKS FROM " +
			" VW_WEC_CHKUNDERTRIAL A,VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN '"+nfdate+"' AND '"+ntdate+"' AND A.S_EB_ID=B.S_EB_ID "+
			"  AND B.S_CUSTOMER_ID IN (SELECT S_CUSTOMER_ID FROM TBL_CUSTOMER_MASTER WHERE S_LOGIN_MASTER_ID IN(SELECT S_LOGIN_MASTER_ID FROM TBL_LOGIN_MASTER  WHERE S_USER_ID='"+ userid +"'))";
			
			if(fobject.equals("MA"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.MIAVIAL=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.MIAVIAL>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.MIAVIAL<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.MIAVIAL>=" +firstparam +" AND A.MIAVIAL<= " +secparam;
				}
			}
			
			if(fobject.equals("GA"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.GAVIAL>=" +firstparam +" AND A.GAVIAL<= " +secparam;
				}
			}
			if(fobject.equals("CF"))
			{
				if(ftype.equals("EQ"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR=" +firstparam;
				}
				if(ftype.equals("GT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR>" +firstparam;
				}
				if(ftype.equals("LT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR<" +firstparam;
				}
				if(ftype.equals("BT"))
				{
					sqlQuery = sqlQuery + " AND A.CFACTOR>=" +firstparam +" AND A.CFACTOR<= " +secparam;
				}
			}
			
			if(ebobject.equals("PM"))
			{
				sqlQuery = sqlQuery + " AND TIMETOSECOND(A.MACHINESD)>0";
			}
			if(ebobject.equals("BD"))
			{
				sqlQuery = sqlQuery + " AND TIMETOSECOND(A.MACHINEFAULT)>0";
			}		
			
			sqlQuery = sqlQuery + " ORDER BY D_READING_DATE,A.S_EB_ID,A.S_WECSHORT_DESCR";
			prepStmt = conn.prepareStatement(sqlQuery);
		

		    // ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("WECTYPE"));
				tranVector.add(rs.getObject("S_CUSTOMER_NAME"));
				tranVector.add(rs.getObject("S_SITE_NAME"));
				tranVector.add(rs.getObject("S_STATE_NAME"));
				String readdate =convertDateFormat(rs.getObject("D_READING_DATE").toString(),"yyyy-MM-dd","dd-MMM-yyyy"	);	
		    	   
				tranVector.add(readdate);
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("MIAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranVector.add(rs.getObject("S_REMARKS"));
				tranVector.add(rs.getObject("S_EB_ID"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			//prepStmt.clearParameters();
			//prepStmt.clearBatch();
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
public List getMPRDetailAdminTotal(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		if(methodClassName){
			MethodClass.displayMethodClassName();
		}
		AdminDao ad = new AdminDao();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
		fdate= ad.convertDateFormat(fdate,"dd/MM/yyyy","dd-MMM-yyyy");
		tdate= ad.convertDateFormat(tdate,"dd/MM/yyyy","dd-MMM-yyyy");
	
		  if(wectype.equals("ALL"))
          {
			  // sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_admin_total_all;
			  sqlQuery = "select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
				"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
				"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
				"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
				"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
				"from VW_WEC_MPRDATA wec, vw_dgr_heading dgr "+ 
				"WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID IN("+siteid+") and dgr.S_CUSTOMER_ID IN('"+customerid+"') "+
				"AND wec.D_READING_DATE between '"+fdate+"' and '"+tdate+"' ";
				prepStmt = conn.prepareStatement(sqlQuery);
				if(queryToHtml){
					DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
				}
				// prepStmt.setObject(1, siteid);
				// prepStmt.setObject(2, customerid);
				
				// prepStmt.setObject(3, fromdate);
				// prepStmt.setObject(4, todate);
          }
		  else
          {
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_admin_total;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, customerid);
			prepStmt.setObject(3, wectype);
			prepStmt.setObject(4, fromdate);
			prepStmt.setObject(5, todate);
			if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_2_" + MethodClass.getMethodName(), siteid, customerid, wectype, fromdate, todate);
			}
          }
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
public List getSubMPRDetailAdminTotal(String siteid,String customerid,String fdate,String tdate,String wectype) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		AdminDao ad = new AdminDao();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
		fdate= ad.convertDateFormat(fdate,"dd/MM/yyyy","dd-MMM-yyyy");
		tdate= ad.convertDateFormat(tdate,"dd/MM/yyyy","dd-MMM-yyyy");
		
	
		  if(wectype.equals("ALL"))
          {
			  // sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_admin_total_all;
			  sqlQuery = "select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
				"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
				"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
				"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
				",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
				"wec,vw_sub_dgr_heading dgr  "+ 
				"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID IN("+siteid+") and dgr.S_CUSTOMER_ID IN('"+customerid+"') "+
				" AND wec.D_READING_DATE between '"+fdate+"' and '"+tdate+"' ";
				prepStmt = conn.prepareStatement(sqlQuery);
				// prepStmt.setObject(1, siteid);
				// prepStmt.setObject(2, customerid);
				
				// prepStmt.setObject(3, fromdate);
				// prepStmt.setObject(4, todate);
          }
		  else
          {
			sqlQuery = CustomerSQLC.GET_SUB_WEC_DGRY_MPR_admin_total;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, customerid);
			prepStmt.setObject(3, wectype);
			prepStmt.setObject(4, fromdate);
			prepStmt.setObject(5, todate);
          }
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {

			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}	
public List getCMPDetailAdminTotal(String siteid,String customerid,String fdate,String wectype,String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		String cyear="";
		
	
		  if(wectype.equals("ALL"))
          {
			  
				
				
				
		         if(RType.equals("D"))
		        	{
		        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_CMP_admin_total_all;
		        		prepStmt = conn.prepareStatement(sqlQuery);
		        		prepStmt.setObject(1, siteid);
		    			prepStmt.setObject(2, customerid);
		    			
		    			prepStmt.setObject(3, fromdate);
		        	}
		        	if(RType.equals("M"))
		        	{
		        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_CMP_admin_total_all_1;
		        		prepStmt = conn.prepareStatement(sqlQuery);
		        		prepStmt.setObject(1, siteid);
		    			prepStmt.setObject(2, customerid);
		    			
		    			prepStmt.setObject(3, fromdate);
		    			prepStmt.setObject(4, fromdate);
		        	}
		        	if (RType.equals("Y")) {
		    			int day = fromdate.getDate();
		    			int month = fromdate.getMonth() + 1;
		    			int year = fromdate.getYear() - 100;
		    			int nyear = year;
		    			//System.out.println("Month: " + month);
		    			////System.out.println("Year: " + year);asdas
		    			if (month >= 4) {
		    				nyear = year + 1;
		    			} else {
		    				nyear = year;
		    				year = year - 1;
		    			}
		    			//cyear = year + "-" + nyear;
		    			int nyear1=2000 + nyear; 
		    			cyear = 2000 + year + "-" + nyear1;
		    			String pdate = "01-APR-" + Integer.toString(year);
		    			String ndate = "31-MAR-" + Integer.toString(nyear);

					 
		        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_admin_total_all;
		        		prepStmt = conn.prepareStatement(sqlQuery);
		        		prepStmt.setObject(1, siteid);
		    			prepStmt.setObject(2, customerid);
		    			
		    			prepStmt.setObject(3, pdate);
		    			prepStmt.setObject(3, ndate);
		        	}
					
					

					
          }
		  else
          {
			  
			  if(RType.equals("D"))
	        	{
	        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_CMP_admin_total_all_2;
	        		prepStmt = conn.prepareStatement(sqlQuery);
	        		prepStmt.setObject(1, siteid);
	    			prepStmt.setObject(2, customerid);
	    			prepStmt.setObject(3, wectype);
	    			prepStmt.setObject(4, fromdate);
	        	}
	        	if(RType.equals("M"))
	        	{
	        		sqlQuery = CustomerSQLC.GET_WEC_DGRY_CMP_admin_total_all_3;
	        		prepStmt = conn.prepareStatement(sqlQuery);
	        		prepStmt.setObject(1, siteid);
	    			prepStmt.setObject(2, customerid);
	    			prepStmt.setObject(3, wectype);
	    			prepStmt.setObject(4, fromdate);
	    			prepStmt.setObject(5, fromdate);
	        	}
	        	if (RType.equals("Y")) {
	    			int day = fromdate.getDate();
	    			int month = fromdate.getMonth() + 1;
	    			int year = fromdate.getYear() - 100;
	    			int nyear = year;
	    			//System.out.println("Month: " + month);
	    			////System.out.println("Year: " + year);asdas
	    			if (month >= 4) {
	    				nyear = year + 1;
	    			} else {
	    				nyear = year;
	    				year = year - 1;
	    			}
	    			//cyear = year + "-" + nyear;
	    			int nyear1=2000 + nyear; 
	    			cyear = 2000 + year + "-" + nyear1;
	    			String pdate = "01-APR-" + Integer.toString(year);
	    			String ndate = "31-MAR-" + Integer.toString(nyear);

			sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_admin_total;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, customerid);
			prepStmt.setObject(3, wectype);
			prepStmt.setObject(4, pdate);
			prepStmt.setObject(5, ndate);
	        	}
          }
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
public List getMPRSerachByWecTotal(String siteid,String customerid,String fdate,String tdate,String wectype,String wecid,String ebid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdate);
		java.sql.Date fromdate = new java.sql.Date(ffd.getTime());

		java.util.Date ftd = format.parse(tdate);
		java.sql.Date todate  = new java.sql.Date(ftd.getTime());
		
	
		  if(!wecid.equals("ALL"))
          {
			    sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_TOTAL_WEC;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, siteid);
				prepStmt.setObject(2, wecid);
				
				prepStmt.setObject(3, fromdate);
				prepStmt.setObject(4, todate);
          }
		  else 
          {
			sqlQuery = CustomerSQLC.GET_WEC_DGRY_MPR_TOTAL_EB;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, siteid);
			prepStmt.setObject(2, ebid);
			
			prepStmt.setObject(3, fromdate);
			prepStmt.setObject(4, todate);
          }
		

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}
public List getWECDetailTotalAdmin(String ebid, String wecid, String rdate,
			String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.GET_WEC_monthly_DATA_admin;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, reportdate);
			prepStmt.setObject(4, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth() + 1;
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);

			sqlQuery = CustomerSQLC.GET_WEC_YEARLY_DATA_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);
		}
		if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.GET_WEC_YEARLY_DATA_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, wecid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("MAVIAL"));
				tranVector.add(rs.getObject("GAVIAL"));
				tranVector.add(rs.getObject("CFACTOR"));
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

public List getEBData(String ebid, String rdate, String RType) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.GET_EB_DGR_DATA;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			DaoUtility.displayQueryWithParameter(20, sqlQuery, ebid, reportdate);
		}

		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			int i = 0;
			if (RType.equals("D")) {
				rs = prepStmt.executeQuery();

				if (rs.next()) {
					DaoUtility.getRowCount(20, rs);
					Vector tranVector = new Vector();
					tranVector.add("Daily");
					tranVector.add(rs.getObject("KWHEXPORT"));
					tranVector.add(rs.getObject("KWHIMPORT"));
					tranVector.add(rs.getObject("DIF"));
					tranVector.add(rs.getObject("S_REMARKS") == null ? "NIL"
							: rs.getObject("S_REMARKS").toString());
					tranVector.add(rs.getObject("RKVAHIMPLAG"));
					tranVector.add(rs.getObject("RKVAHEXPLAG"));
					tranVector.add(rs.getObject("RKVAHIMPLEAD"));
					tranVector.add(rs.getObject("RKVAHEXPLEAD"));
					tranList.add(i, tranVector);
					i++;
				}
				prepStmt.close();
				rs.close();
			}
			if (RType.equals("M")) {
				sqlQuery = CustomerSQLC.GET_EB_MONTHLY_DGR_DATA;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, ebid);
				prepStmt.setObject(2, reportdate);
				prepStmt.setObject(3, reportdate);
				DaoUtility.displayQueryWithParameter(21, sqlQuery, ebid, reportdate, reportdate);
				rs = prepStmt.executeQuery();

				if (rs.next()) {
					DaoUtility.getRowCount(21, rs);
					Vector tranVector = new Vector();
					tranVector.add("Monthly");
					tranVector.add(rs.getObject("KWHEXPORT")== null ? "0":rs.getObject("KWHEXPORT").toString());
					tranVector.add(rs.getObject("KWHIMPORT")== null ? "0":rs.getObject("KWHIMPORT").toString());
					tranVector.add(rs.getObject("DIF")== null ? "0":rs.getObject("DIF").toString());
					tranVector.add("NO");
					tranList.add(i, tranVector);
					i++;
				}
				prepStmt.close();
				rs.close();
			}
			if (RType.equals("Y")) {
				int day = reportdate.getDate();
				int month = reportdate.getMonth();
				int year = reportdate.getYear() - 100;
				int nyear = year;
				//System.out.println("Month: " + month);
				//System.out.println("Year: " + year);
				if (month >= 4) {
					nyear = year + 1;
				} else {
					nyear = year;
					year = year - 1;
				}

				String pdate = "01-APR-" + Integer.toString(year);
				String ndate = "31-MAR-" + Integer.toString(nyear);

				sqlQuery = CustomerSQLC.GET_EB_YEARLY_DGR_DATA;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, ebid);
				prepStmt.setObject(2, pdate);
				prepStmt.setObject(3, ndate);
				DaoUtility.displayQueryWithParameter(22, sqlQuery, ebid, pdate, ndate);
				rs = prepStmt.executeQuery();

				if (rs.next()) {
					DaoUtility.getRowCount(22, rs);
					Vector tranVector = new Vector();
					tranVector.add("Yearly");
					tranVector.add(rs.getObject("KWHEXPORT")== null ? "0":rs.getObject("KWHEXPORT").toString());
					tranVector.add(rs.getObject("KWHIMPORT")== null ? "0":rs.getObject("KWHIMPORT").toString());
					tranVector.add(rs.getObject("DIF")== null ? "0":rs.getObject("DIF").toString());
					tranVector.add("NO");
					tranList.add(i, tranVector);

				}
				prepStmt.close();
				rs.close();
			}

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return tranList;
	}

	
public List getEBDataCum(String ebid, String rdate)	throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "";

SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

	sqlQuery = CustomerSQLC.GET_EB_DGR_DATA;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, ebid);
	prepStmt.setObject(2, reportdate);
    String Remark="";
// ps.setObject(1,item.toUpperCase() + "%");

try {
	int i = 0;
	
		rs = prepStmt.executeQuery();

		if (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add("Daily");
			tranVector.add(rs.getObject("KWHEXPORT"));
			tranVector.add(rs.getObject("KWHIMPORT"));
			tranVector.add(rs.getObject("DIF"));
			Remark=rs.getObject("S_REMARKS") == null ? "NIL"
					: rs.getObject("S_REMARKS").toString();
			
			tranVector.add(Remark);
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
		sqlQuery = CustomerSQLC.GET_EB_MONTHLY_DGR_DATA_CUM;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		prepStmt.setObject(4, reportdate);
		rs = prepStmt.executeQuery();

		if (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add("Monthly");
			tranVector.add(rs.getObject("KWHEXPORT")== null ? "0":rs.getObject("KWHEXPORT").toString());
			tranVector.add(rs.getObject("KWHIMPORT")== null ? "0":rs.getObject("KWHIMPORT").toString());
			tranVector.add(rs.getObject("DIF")== null ? "0":rs.getObject("DIF").toString());
			tranVector.add(Remark);
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();
	
		int day = reportdate.getDate();
		int month = reportdate.getMonth()+1;
		int year = reportdate.getYear() - 100;
		int nyear = year;
		String pdate="";
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year;
			pdate = "01-APR-" + Integer.toString(nyear);
		} else {
			//nyear = year;
			nyear = year - 1;
			pdate = "01-APR-" + Integer.toString(nyear);
		}

		
		//

		sqlQuery = CustomerSQLC.GET_EB_YEARLY_DGR_DATA;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, pdate);
		prepStmt.setObject(3, reportdate);
		rs = prepStmt.executeQuery();

		if (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add("Yearly");
			tranVector.add(rs.getObject("KWHEXPORT")== null ? "0":rs.getObject("KWHEXPORT").toString());
			tranVector.add(rs.getObject("KWHIMPORT")== null ? "0":rs.getObject("KWHIMPORT").toString());
			tranVector.add(rs.getObject("DIF")== null ? "0":rs.getObject("DIF").toString());
			tranVector.add(Remark);
			tranList.add(i, tranVector);

		}
		prepStmt.close();
		rs.close();
	

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}

	
	
public List getEBWiseTotal(String ebid, String rdate, String RType)	throws Exception {
	// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
			//System.out.println("RDATE: " + rdate);
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
	List tranList = new ArrayList();
	String sqlQuery = "",cnt2="0",trailma="0",trailga="0",trailcf="0",acount="0";


	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_AEBWISE_TOTAL_NEW;
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, ebid);
			//System.out.println("reportdate"+reportdate);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		DaoUtility.displayQueryWithParameter(55, sqlQuery, ebid,reportdate,reportdate);
	}
	if (RType.equals("M")) {
		sqlQuery = CustomerSQLC.SELECT_EBWISEM_TOTAL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		DaoUtility.displayQueryWithParameter(55, sqlQuery, ebid,reportdate,reportdate);
	}
	if (RType.equals("Y")) {
		int day = reportdate.getDate();
		int month = reportdate.getMonth();
		int year = reportdate.getYear() - 100;
		int nyear = year;
		//System.out.println("Month: " + month);
		//System.out.println("Year: " + year);
		if (month >= 4) {
			nyear = year + 1;
		} else {
			nyear = year;
			year = year - 1;
		}
		String pdate = "01-APR-" + Integer.toString(year);
		String ndate = "31-MAR-" + Integer.toString(nyear);
		sqlQuery = CustomerSQLC.SELECT_EBWISEY_TOTAL;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, ebid);
		prepStmt.setObject(2, pdate);
		prepStmt.setObject(3, ndate);
		DaoUtility.displayQueryWithParameter(55, sqlQuery, ebid,pdate,ndate);
	}

	// ps.setObject(1,item.toUpperCase() + "%");

	try {
		rs = prepStmt.executeQuery();
		int i = 0;
		if (rs.next()) {
			DaoUtility.getRowCount(55, rs);
			Vector tranVector = new Vector();
			
			tranVector.add(rs.getObject("cnt"));
	        String cntt=rs.getObject("TRIALRUN").toString();
			tranVector.add(rs.getObject("GENERATION"));
			tranVector.add(rs.getObject("OPERATINGHRS"));
			tranVector.add(rs.getString("LULLHRS"));
			tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
			tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
			tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
			tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
			tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
			tranVector.add(rs.getObject("TRIALRUN"));
			//System.out.println(cntt);
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return tranList;
}

private int getLoadRestForParticularDateWRTEB(String ebid, String rdate) {

	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = null;
	String sqlQuery = "";
	PreparedStatement prepStmt = null;
	ResultSet rs = null;
		
	int totalLoadMinutesForEB = 0;
	try{
		conn = wcareConnector.getConnectionFromPool();
		sqlQuery = "Select S_WEC_ID,S_MP_ID,N_Actual_Value From Tbl_Wec_Reading " +
					"Where D_Reading_Date = ? " +
					"And S_Mp_Id = '0810000001' " +
					"and S_EB_ID = ?" ;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, DateUtility.stringDateFormatToSQLDate(rdate, "dd/MM/yyyy"));
		prepStmt.setObject(2, ebid);
		
		rs = prepStmt.executeQuery();
		while (rs.next()) {
			totalLoadMinutesForEB += TimeUtility.convertTimeStringToMinutes(rs.getString("N_ACTUAL_VALUE"), ".");	
		}
		return totalLoadMinutesForEB;
	}
	catch(Exception e){
		logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
	}
	finally{
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
	return totalLoadMinutesForEB;

}

	
	public List getSiteWiseTotal(String siteid,String custid, String rdate, String RType)
	throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "",cnt2="0",trailma="0",trailga="0",trailcf="0",acount="0";
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		
		
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_SITEWISEY_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, siteid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);
			DaoUtility.displayQueryWithParameter(58, sqlQuery, custid,siteid,pdate,ndate);
		
		// ps.setObject(1,item.toUpperCase() + "%");
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if (rs.next()) {
				DaoUtility.getRowCount(58, rs);
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("cnt"));
		        String cntt=rs.getObject("TRIALRUN").toString();
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranVector.add(rs.getObject("TRIALRUN"));
				System.out.println(cntt);
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();
		
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
return tranList;
}
	
	
	public List getStateWiseTotal(String siteid,String custid, String rdate, String RType)
	throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "",cnt2="0",trailma="0",trailga="0",trailcf="0",acount="0";
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		
		
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_STATEWISEY_TOTAL;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, siteid);
			prepStmt.setObject(3, pdate);
			prepStmt.setObject(4, ndate);
		    DaoUtility.displayQueryWithParameter(66, sqlQuery, custid,siteid,pdate,ndate);
		
		// ps.setObject(1,item.toUpperCase() + "%");
		
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if (rs.next()) {
				DaoUtility.getRowCount(66, rs);
				Vector tranVector = new Vector();
				
				tranVector.add(rs.getObject("cnt"));
		        String cntt=rs.getObject("TRIALRUN").toString();
				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
				tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
				tranVector.add(rs.getObject("TRIALRUN"));
				System.out.println(cntt);
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();
		
		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
return tranList;
}
	
public List getEBWiseTotalCum(String ebid, String rdate)
	throws Exception {
// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;
ResultSet rs1 = null;
PreparedStatement ps = null;
ResultSet rs = null;
List tranList = new ArrayList();
String sqlQuery = "",cnt2="0",trailma="0",trailga="0",trailcf="0",acount="0";


SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
java.util.Date ffd = format.parse(rdate);
java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

	
	sqlQuery = CustomerSQLC.SELECT_AEBWISE_TOTAL;
	prepStmt = conn.prepareStatement(sqlQuery);

	prepStmt.setObject(1, ebid);
	//System.out.println("reportdate"+reportdate);
	prepStmt.setObject(2, reportdate);
	prepStmt.setObject(3, reportdate);
	
	sqlQuery = CustomerSQLC.GET_TOTAL_CUM_DGR_DATA; 
	ps = conn.prepareStatement(sqlQuery);
	ps.setObject(1, ebid);
	ps.setObject(2, reportdate);
	ps.setObject(3, reportdate);
	ps.setObject(4, reportdate);
	rs1=ps.executeQuery();
	
	
// ps.setObject(1,item.toUpperCase() + "%");

try {
	rs = prepStmt.executeQuery();
	int i = 0;
	if (rs.next()) {
		Vector tranVector = new Vector();
		
		tranVector.add(rs.getObject("cnt"));

		tranVector.add(rs.getObject("GENERATION"));
		tranVector.add(rs.getObject("OPERATINGHRS"));
		tranVector.add(rs.getObject("LULLHRS"));
		tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
		tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
		tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
         
		if(rs1.next())
		{
			tranVector.add(rs1.getObject("GENERATION"));
			tranVector.add(rs1.getObject("OPERATINGHRS"));
		}
		else
		{
			tranVector.add("0");
			tranVector.add("0");
		}
		rs1.close();
		ps.close();
		tranVector.add(rs.getObject("MIAVIAL")==null?"0":rs.getObject("MIAVIAL").toString());
		tranVector.add(rs.getObject("GIAVIAL")==null?"0":rs.getObject("GIAVIAL").toString());
		tranList.add(i, tranVector);
		i++;
	}
	prepStmt.close();
	rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
} finally {
	DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
	
}
return tranList;
}

	
	
	
	public List getUploadStatus(String userid, String rdate) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List tranList = new ArrayList();
		String sqlQuery1 = "", sqlQuery = "", cnt2 = "0", trailma = "0", trailga = "0", trailcf = "0";
		DecimalFormat formatter = new DecimalFormat("#########0");

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());

		Map<String, Integer> scadaConnectedStatewiseUploadedCount = getScadaConnectedStatewiseUploadedCount(reportdate);
		sqlQuery = CustomerSQLC.SELECT_StateWiseUpload;
		prepStmt = conn.prepareStatement(sqlQuery);
		prepStmt.setObject(1, reportdate);
		prepStmt.setObject(2, reportdate);
		prepStmt.setObject(3, reportdate);
		prepStmt.setObject(4, reportdate);
		String stateid = "0", upcount = "0", pcount = "0";
		int tweccount = 0, tupcount = 0, tpcount = 0, tdiff = 0, scadaWecCount = 0, tscadaWecCount = 0, tscadaWecConnected = 0, scadaWecConnected = 0, rsScadaDownloadCount = 0, trsScadaDownloadCount = 0;
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("s_state_name"));
				tranVector.add(rs.getObject("weccount"));
				stateid = rs.getObject("s_state_id") == null ? "0" : rs
						.getObject("s_state_id").toString();

				sqlQuery1 = CustomerSQLC.SELECT_Upload;
				ps = conn.prepareStatement(sqlQuery1);
				ps.setObject(1, stateid);
				ps.setObject(2, reportdate);
				ps.setObject(3, reportdate);
				ps.setObject(4, reportdate);
				ps.setObject(5, reportdate);
				ps.setObject(6, reportdate);
				rs1 = ps.executeQuery();
				upcount = "0";
				if (rs1.next()) {
					upcount = rs1.getObject("upcount") == null ? "0" : rs1
							.getObject("upcount").toString();
				}

				tranVector.add(upcount);
				int diff = Integer
						.parseInt(rs.getObject("weccount").toString())
						- Integer.parseInt(upcount);

				tranVector.add(diff);
				tranVector.add(stateid);

				ps.close();
				rs1.close();
				sqlQuery1 = CustomerSQLC.SELECT_Upload_publish;
				ps = conn.prepareStatement(sqlQuery1);
				ps.setObject(1, stateid);
				ps.setObject(2, reportdate);
				ps.setObject(3, reportdate);
				ps.setObject(4, reportdate);
				ps.setObject(5, reportdate);
				ps.setObject(6, reportdate);
				rs1 = ps.executeQuery();
				pcount = "0";
				if (rs1.next()) {
					pcount = rs1.getObject("upcount") == null ? "0" : rs1
							.getObject("upcount").toString();
				}

				// tranVector.add(pcount);
				tranVector.add(Integer.parseInt(upcount)
						- Integer.parseInt(pcount));

				tweccount = tweccount
						+ Integer.parseInt(rs.getObject("weccount").toString());
				tupcount = tupcount + Integer.parseInt(upcount);
				// tpcount=tpcount+Integer.parseInt(pcount);
				tpcount = tpcount
						+ (Integer.parseInt(upcount) - Integer.parseInt(pcount));
				tdiff = tdiff + diff;

				/**************************** WEC Connected to Scada System ******************************/
				AdminDao ad = new AdminDao();
				ResultSet rsScadaConnected = conn
						.createStatement()
						.executeQuery(
								"SELECT COUNT(S_WEC_ID) AS SCADAWECCOUNT FROM TBL_WEC_MASTER WHERE S_SCADA_FLAG='1' AND S_STATUS='1' AND S_EB_ID IN (SELECT S_EB_ID FROM TBL_EB_MASTER WHERE S_SITE_ID IN (SELECT S_SITE_ID FROM TBL_SITE_MASTER WHERE S_STATE_ID='"
										+ stateid + "'))");
				if (rsScadaConnected.next())
					scadaWecConnected = Integer.parseInt(rsScadaConnected
							.getObject("SCADAWECCOUNT").toString());
				tscadaWecConnected = tscadaWecConnected + scadaWecConnected;
				rsScadaConnected.close();
				tranVector.add(scadaWecConnected);
				/**************************** WEC Connected to Scada System ******************************/

				/******************* Generation of WEC Connected to Scada System *************************/
				String scadaReportDate = ad.convertDateFormat(rdate,
						"dd/MM/yyyy", "dd-MMM-yyyy");
				ResultSet rsScadaUpload = conn
						.createStatement()
						.executeQuery(
								"SELECT COUNT(DISTINCT S_WEC_ID) AS SCADAWECCOUNT FROM TBL_WEC_READING WHERE D_READING_DATE='"
										+ scadaReportDate
										+ "' AND S_CREATED_BY='SYSTEM' AND N_PUBLISH=1 AND S_EB_ID IN (SELECT S_EB_ID FROM TBL_EB_MASTER WHERE S_SITE_ID IN (SELECT S_SITE_ID FROM TBL_SITE_MASTER WHERE S_STATE_ID='"
										+ stateid + "'))");
				if (rsScadaUpload.next())
					scadaWecCount = Integer.parseInt(rsScadaUpload.getObject(
							"SCADAWECCOUNT").toString());
				tscadaWecCount = tscadaWecCount + scadaWecCount;
				rsScadaUpload.close();
				tranVector.add(scadaWecCount);
				/******************* Generation of WEC Connected to Scada System *************************/

				/*********************** Total WEC Data Downloaded from Scada DB ***************************/

				/*
				 * sqlQuery1 = CustomerSQLC.SELECT_DOWNLOADED_DATA_TO_SCADADW;
				 * ps = conn.prepareStatement(sqlQuery1);
				 * ps.setObject(1,stateid); ps.setObject(2,reportdate);
				 * 
				 * getUploadedScadaCount(stateid, reportdate);
				 * 
				 * ResultSet rsScadaDownload = ps.executeQuery();
				 * 
				 * if(rsScadaDownload.next()) {
				 * rsScadaDownloadCount=Integer.parseInt
				 * (rsScadaDownload.getObject("TTLSCADACOUNT").toString()); } //
				 * trsScadaDownloadCount =
				 * trsScadaDownloadCount+rsScadaDownloadCount;
				 * rsScadaDownload.close();
				 */

				tranVector.add(scadaConnectedStatewiseUploadedCount
						.get(stateid));
				trsScadaDownloadCount += scadaConnectedStatewiseUploadedCount
						.get(stateid);

				/***************************************************************************************/

				tranList.add(i, tranVector);

				i++;

			}
			prepStmt.close();
			rs.close();
			if (i > 1) { // i=i+1;
				Vector tranVector = new Vector();

				tranVector.add("Total");
				tranVector.add(tweccount);
				tranVector.add(tupcount);
				tranVector.add(tdiff);
				tranVector.add("AS");
				tranVector.add(tpcount);
				tranVector.add(tscadaWecConnected);
				tranVector.add(tscadaWecCount);
				tranVector.add(trsScadaDownloadCount);

				tranList.add(i, tranVector);

			}

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
		}
		return tranList;
	}

public Map<String, Integer> getScadaConnectedStatewiseUploadedCount(Date reportdate) throws SQLException {
	Map<String, Integer> scadaConnectedStatewiseUploadedCount = new HashMap<String, Integer>();
	
	/*Return All 'Plant Serial No' downloaded in Scada Database*/
	Set<String> allScadaSrNoUploaded = getAllScadaSrNoUploaded(reportdate);
	
	Set<String> stateIds = getAllStateIds();
	Set<String> srNoScadaConnectedInWcareStateWise = null;
	int stateCount = 0;
	for (String stateId : stateIds) {
		srNoScadaConnectedInWcareStateWise = getSrNoScadaConnectedInWcareStateWise(stateId);
		stateCount = 0;
		for (String wcareSrNo : srNoScadaConnectedInWcareStateWise) {
			if(allScadaSrNoUploaded.contains(wcareSrNo)){
				allScadaSrNoUploaded.remove(wcareSrNo);
				stateCount++;
			}
		}
		scadaConnectedStatewiseUploadedCount.put(stateId, stateCount);
	}
	
	return scadaConnectedStatewiseUploadedCount;
}

private Set<String> getSrNoScadaConnectedInWcareStateWise(String stateId) throws SQLException {
	
	Set<String> srNoInWcareStateWise = new HashSet<String>();
	String query = "";
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;

		try {
		conn = wcareConnector.getConnectionFromPool();
		query = "SELECT S_Technical_No " + 
				"FROM TBL_WEC_MASTER " + 
				"WHERE S_EB_ID IN (SELECT S_EB_ID  " + 
				"                  FROM TBL_EB_MASTER  " + 
				"                  WHERE S_SITE_ID IN (SELECT S_SITE_ID  " + 
				"                  						FROM TBL_SITE_MASTER  " + 
				"                                       WHERE S_STATE_ID=?)) " + 
				"and S_Scada_Flag = 1 " +
				"and S_status in ('1') " +
				"group by S_Technical_No "; 
 
		prepStmt = conn.prepareStatement(query);
		prepStmt.setString(1, stateId);
		rs = prepStmt.executeQuery();
		while (rs.next()) {
			srNoInWcareStateWise.add(rs.getString("S_Technical_No"));
		}
		
		return srNoInWcareStateWise;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
}

private Set<String> getAllStateIds() throws SQLException {
	
	Set<String> allStateIds = new HashSet<String>();
	String query = "";
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;

		try {
		conn = wcareConnector.getConnectionFromPool();
		query = "Select S_state_id " +
				"from tbl_state_master "; 
 
		prepStmt = conn.prepareStatement(query);
		rs = prepStmt.executeQuery();
		while (rs.next()) {
			allStateIds.add(rs.getString("S_state_id"));
		}
		
		return allStateIds;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
}

/*Returns a 'Set of Scada Plant Serial No' downloaded in a Scada Database for a given date*/
public Set<String> getAllScadaSrNoUploaded(Date reportdate) throws SQLException {
	
	Set<String> allScadaSrNoUploaded = new HashSet<String>();
	String query = "";
	Connection conn = null;
	PreparedStatement prepStmt = null;
	ResultSet rs = null;

	try {
		conn = wcareConnector.getConnectionFromPool();
		query = "Select plantmas.S_serial_no as Serial_No " + 
				"from scadadw.tbl_generation_min_10 gen, scadadw.tbl_plant_master plantmas " + 
				"where gen.S_location_no = plantmas.S_location_no " + 
				"and gen.S_plant_no  = plantmas.S_plant_no " + 
				"and D_date = ? " + 
				"group by plantmas.S_serial_no " ; 
 
		prepStmt = conn.prepareStatement(query);
		prepStmt.setDate(1, reportdate);
		rs = prepStmt.executeQuery();
		while (rs.next()) {
			allScadaSrNoUploaded.add(rs.getString("Serial_No"));
		}
		
		
		return allScadaSrNoUploaded;
	} finally {
		DaoUtility.releaseResources(prepStmt, rs, conn);
	}
}

public List getStateSiteUploadStatus(String userid, String rdate)
throws Exception {
	//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}

	//JDBCUtils conmanager = new JDBCUtils();
	Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	List tranList = new ArrayList();
	String sqlQuery1 = "",sqlQuery = "",cnt2="0",trailma="0",trailga="0",trailcf="0";
	DecimalFormat formatter = new DecimalFormat("#########0");
	
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	java.util.Date ffd = format.parse(rdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());	
	
	sqlQuery = CustomerSQLC.SELECT_StateSiteWiseUpload;
	if(queryToHtml){
		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
	}
	prepStmt = conn.prepareStatement(sqlQuery);
	//prepStmt.setObject(1,reportdate);
	String stateid="0",siteid="0",upcount="0",pcount="0";
	 int tweccount=0,tupcount=0,tpcount=0,tdiff=0;
	//ps.setObject(1,item.toUpperCase() + "%");
	
	try {
	rs = prepStmt.executeQuery();
	int i = 0;
	while(rs.next()) {
		Vector tranVector = new Vector();
		 tranVector.add(rs.getObject("s_state_name"));
		 tranVector.add(rs.getObject("s_site_name"));
		 tranVector.add(rs.getObject("weccount"));
	     stateid=rs.getObject("s_state_id")==null?"0":rs.getObject("s_state_id").toString();
	     siteid=rs.getObject("s_site_id")==null?"0":rs.getObject("s_site_id").toString();
		
	      sqlQuery1 = CustomerSQLC.SELECT_site_Upload;
	      if(queryToHtml){
	  		DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery1, (++queryCount) + "_2_" + MethodClass.getMethodName(),stateid,siteid,reportdate);
	  	}
	      ps = conn.prepareStatement(sqlQuery1);
		  ps.setObject(1,stateid);
		  ps.setObject(2,siteid);
	      ps.setObject(3,reportdate);
		 
		  rs1 = ps.executeQuery();
		  upcount="0";
		  if(rs1.next())
		   {
		    	upcount=rs1.getObject("upcount")==null?"0":rs1.getObject("upcount").toString();
		   }		
		
		tranVector.add(upcount);
		 int diff=Integer.parseInt(rs.getObject("weccount").toString())-Integer.parseInt(upcount);		 
		
		 tranVector.add(diff);
		 tranVector.add(stateid);
		 
		 ps.close();
		 rs1.close();
		 sqlQuery1 = CustomerSQLC.SELECT_site_publish;
		 if(queryToHtml){
				DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery1, (++queryCount) + "_3_" + MethodClass.getMethodName(), stateid,siteid,reportdate);
			}
	     ps = conn.prepareStatement(sqlQuery1);
		  ps.setObject(1,stateid);
		  ps.setObject(2,siteid);
	      ps.setObject(3,reportdate);
		 
		  rs1 = ps.executeQuery();
		  pcount="0";
		  if(rs1.next())
		   {
			  pcount=rs1.getObject("upcount")==null?"0":rs1.getObject("upcount").toString();
		   }
		
		  //tranVector.add(pcount);
		  tranVector.add(Integer.parseInt(upcount) - Integer.parseInt(pcount));
		 
		 
		tranList.add(i, tranVector);
		
		tweccount=tweccount+Integer.parseInt(rs.getObject("weccount").toString());
		tupcount=tupcount+Integer.parseInt(upcount);
		//tpcount=tpcount+Integer.parseInt(pcount);
		tpcount=tpcount+(Integer.parseInt(upcount) - Integer.parseInt(pcount));
		tdiff=tdiff+diff;
		i++;
		
	}
	prepStmt.close();
	rs.close();
	if(i>1)
	{    //i=i+1;
		 Vector tranVector = new Vector();
		 
		 tranVector.add("Total");
		 tranVector.add("");
		 tranVector.add(tweccount);
		 tranVector.add(tupcount);
		 tranVector.add(tdiff);
		 tranVector.add("AS");
		 tranVector.add(tpcount);
		 
		 tranList.add(i, tranVector);
		 
	}
	
	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
	Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
	throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
	}
	return tranList;
}

public List getEmployeeEmailList() throws Exception {
//public List searchempbyfilter(DynaBean dynaBean) throws Exception {
	if(methodClassName){
		MethodClass.displayMethodClassName();
	}
	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
PreparedStatement prepStmt = null;

ResultSet rs = null;

List tranList = new ArrayList();
String sqlQuery = "";

sqlQuery = CustomerSQLC.SELECT_EMPLOYEE_EMAIL_LIST;
if(queryToHtml){
	DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, (++queryCount) + "_1_" + MethodClass.getMethodName());
}
prepStmt = conn.prepareStatement(sqlQuery);

try {
rs = prepStmt.executeQuery();
int i = 0;
while(rs.next()) {
	Vector tranVector = new Vector();
	tranVector.add(rs.getObject("S_EMAIL_ID"));
	tranList.add(i, tranVector);
	i++;
}
prepStmt.close();
rs.close();

} catch (SQLException sqlExp) {
	logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
throw exp;
} finally {
	DaoUtility.releaseResources(prepStmt, rs, conn);
}
return tranList;
}
public List getEBWiseTotalAdmin(String ebid, String rdate, String RType)
			throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

	//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(rdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		if (RType.equals("D")) {
			sqlQuery = CustomerSQLC.SELECT_EBWISE_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
		}
		if (RType.equals("M")) {
			sqlQuery = CustomerSQLC.SELECT_EBWISEM_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, reportdate);
			prepStmt.setObject(3, reportdate);
		}
		if (RType.equals("Y")) {
			int day = reportdate.getDate();
			int month = reportdate.getMonth();
			int year = reportdate.getYear() - 100;
			int nyear = year;
			//System.out.println("Month: " + month);
			//System.out.println("Year: " + year);
			if (month >= 4) {
				nyear = year + 1;
			} else {
				nyear = year;
				year = year - 1;
			}
			String pdate = "01-APR-" + Integer.toString(year);
			String ndate = "31-MAR-" + Integer.toString(nyear);
			sqlQuery = CustomerSQLC.SELECT_EBWISEY_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
		if (RType.equals("YD")) {
			
			String comma[] = rdate.split(",");
			
			java.util.Date pdt = format.parse(comma[0]);
			java.util.Date tdt = format.parse(comma[1]);
			java.sql.Date pdate = new java.sql.Date(pdt.getTime());
			java.sql.Date ndate = new java.sql.Date(tdt.getTime());
			
			sqlQuery = CustomerSQLC.SELECT_EBWISEY_TOTAL_ADMIN;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, ebid);
			prepStmt.setObject(2, pdate);
			prepStmt.setObject(3, ndate);
		}
		// ps.setObject(1,item.toUpperCase() + "%");

		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			if (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("cnt"));

				tranVector.add(rs.getObject("GENERATION"));
				tranVector.add(rs.getObject("OPERATINGHRS"));
				tranVector.add(rs.getObject("LULLHRS"));
				tranVector.add(rs.getObject("MACHINEFAULT"));
				tranVector.add(rs.getObject("MACHINESD"));
				tranVector.add(rs.getObject("INTERNALFAULT"));
				tranVector.add(rs.getObject("INTERNALSD"));
				tranVector.add(rs.getObject("EXTERNALFAULT"));
				tranVector.add(rs.getObject("EXTERNALSD"));
				tranVector.add(rs.getObject("MAVIAL")==null?"0":rs.getObject("MAVIAL").toString());
				tranVector.add(rs.getObject("GAVIAL")==null?"0":rs.getObject("GAVIAL").toString());
				tranVector.add(rs.getObject("CFACTOR")==null?"0":rs.getObject("CFACTOR").toString());
				tranVector.add(rs.getObject("FAULTHRS"));
				tranVector.add(rs.getObject("GIAVIAL"));
				tranVector.add(rs.getObject("WAVIAL"));
				tranVector.add(rs.getObject("WECSPDOWN"));
				tranVector.add(rs.getObject("EBSPDOWN"));
				tranVector.add(rs.getObject("CUM_GENERATION")==null?"0":rs.getObject("CUM_GENERATION").toString());
				tranVector.add(rs.getObject("CUM_OPERATINGHRS")==null?"0":rs.getObject("CUM_OPERATINGHRS").toString());
				tranList.add(i, tranVector);
				i++;
			}
			prepStmt.close();
			rs.close();

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
			
		}
		return tranList;
	}

	public String getCUSTAjaxDetails(String item, String action, String UserId)
			throws Exception {
		logger.debug("Ajax Call(item):::" + item);
		logger.debug("Ajax Call(action):::" + action);
		logger.debug("Ajax Call(userid):::" + UserId);
		//System.out.println("Item :: " + item + ", Action :: " + action + ", UserId :: " + UserId);
		StringBuffer xml = new StringBuffer();
		//JDBCUtils conmanager = new JDBCUtils();
				Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		// PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlQuery = "";
		String msg = "";
		try {
			if (action.equals("CUST_DGRVIEW")) {
				// sqlQuery = CustomerSQLC.SELECT_MATERIAL_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				// ps.setObject(1,item.toUpperCase() + "%");
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<mmaster generated=\"" + System.currentTimeMillis()
						+ "\">\n");
				while (rs.next()) {
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
			else if (action.equals("CUST_UPLOADSITE")) 
			{
				
				String stateid="",upcount="0",pcount="0";;
				String comma[] = item.split(",");
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date ffd = format.parse(comma[1]);
				java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
			
				 sqlQuery = CustomerSQLC.SELECT_SiteWiseUpload;
				ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1,comma[0]);
				 //ps.setObject(2,reportdate);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sumaster generated=\"" + System.currentTimeMillis()+ "\">\n");
				while (rs.next()) {
					xml.append("<mtcode>");
					xml.append("<sid>");
					xml.append(rs.getObject("s_site_id"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("s_site_name"));
					xml.append("</sname>\n");
					xml.append("<scnt>");
					xml.append(rs.getObject("weccount"));
					xml.append("</scnt>\n");
					
					stateid=rs.getObject("s_site_id").toString();
					sqlQuery = CustomerSQLC.SELECT_site_Upload;
			          ps1 = conn.prepareStatement(sqlQuery);
					  ps1.setObject(1,comma[0]);
					  ps1.setObject(2,stateid);
				      ps1.setObject(3,reportdate);
					 
					  rs1 = ps1.executeQuery();
					  upcount="0";
					  if(rs1.next())
					   {
					    	upcount=rs1.getObject("upcount")==null?"0":rs1.getObject("upcount").toString();
					   }					
					
					xml.append("<supcnt>");
					xml.append(upcount);
					xml.append("</supcnt>\n");
					 int diff=Integer.parseInt(rs.getObject("weccount").toString())-Integer.parseInt(upcount);
					 
					    xml.append("<diff>");
						xml.append(diff);
						xml.append("</diff>\n");						
						
						 ps1.close();
						 rs1.close();
						 sqlQuery = CustomerSQLC.SELECT_site_publish;
				         ps1 = conn.prepareStatement(sqlQuery);
				         ps1.setObject(1,comma[0]);
						  ps1.setObject(2,stateid);
					      ps1.setObject(3,reportdate);
						 
						  rs1 = ps1.executeQuery();
						  pcount="0";
						  if(rs1.next())
						   {
							  pcount=rs1.getObject("upcount")==null?"0":rs1.getObject("upcount").toString();
						   }
						 
						  xml.append("<pcount>");
						  xml.append(Integer.parseInt(upcount) - Integer.parseInt(pcount));
						  xml.append("</pcount>\n");					
					
					xml.append("</mtcode>\n");					
					
				}
				xml.append("</sumaster>\n");
				rs.close();
				ps.close();
			} 
			
			else if (action.equals("CUST_UPLOAD_TIME")) 
			{
				
				String stateid="",upcount="0",pcount="0";;
				String comma[] = item.split(",");
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date ffd = format.parse(comma[1]);
				java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
			
				 sqlQuery = CustomerSQLC.SELECT_UPLOADTIME;
				ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1,comma[0]);
				 ps.setObject(2,reportdate);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sumaster generated=\"" + System.currentTimeMillis()+ "\">\n");
				while (rs.next()) {
					xml.append("<mtcode>");
					xml.append("<sid>");
					xml.append(rs.getObject("s_site_name"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("s_login_description"));
					xml.append("</sname>\n");
					xml.append("<scnt>");
					xml.append(rs.getObject("mintime"));
					xml.append("</scnt>\n");
					xml.append("<scnt1>");
					xml.append(rs.getObject("maxtime"));
					xml.append("</scnt1>\n");
					xml.append("</mtcode>\n");
					
					
				}
				xml.append("</sumaster>\n");
				rs.close();
				ps.close();
			} 
			
			
			else if (action.equals("CUST_STD_MESSAGE")) {
				sqlQuery = CustomerSQLC.GET_STANDARD_MESSAGE;
				ps = conn.prepareStatement(sqlQuery);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<msgid>");
					xml.append(rs.getObject("S_STANDARD_MESSAGE_ID"));
					xml.append("</msgid>\n");
					xml.append("<msghead>");
					xml.append(rs.getObject("S_MESSAGE_HEAD").toString()
							.replace("&", "And"));
					xml.append("</msghead>\n");
					xml.append("<msgdesc>");
					xml
							.append(rs.getObject("S_MESSAGE_DESCRIPTION") == null ? ""
									: rs.getObject("S_MESSAGE_DESCRIPTION")
											.toString().replace("&", "And"));
					xml.append("</msgdesc>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			} else if (action.equals("CUST_STD_MESSAGE_DETAIL")) {
				sqlQuery = CustomerSQLC.GET_STANDARD_MESSAGE_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<msgid>");
					xml.append(rs.getObject("S_STANDARD_MESSAGE_ID"));
					xml.append("</msgid>\n");
					xml.append("<msghead>");
					xml.append(forXML(rs.getObject("S_MESSAGE_HEAD").toString().replace("&", "And")));
					xml.append("</msghead>\n");
					xml.append("<msgdesc>");
					xml.append(rs.getObject("S_MESSAGE_DESCRIPTION") == null ? "": forXML(rs.getObject("S_MESSAGE_DESCRIPTION").toString().replace("&", "And")));
					xml.append("</msgdesc>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			}
			
			else if (action.equals("CUST_LOGINDETAIL_ID"))
        	{        		

    			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        		sqlQuery = CustomerSQLC.SELECT_CUSTOMER_DETAILS;
        		ps = conn.prepareStatement(sqlQuery);
        		ps.setObject(1, item);
        		rs = ps.executeQuery();
        		xml.append("<?xml version=\"1.0\"?>\n");
			    xml.append("<logindetail generated=\""+System.currentTimeMillis()+"\">\n");
			    xml.append("<lcode>");
        		if (rs.next())
        		{  
        			xml.append("<scustname>");
        			xml.append(rs.getObject("s_customer_name").toString().replaceAll("&","AND"));
        			xml.append("</scustname>");
        			
        			xml.append("<sactive>");
        			xml.append(rs.getObject("ACTIVATED"));
        			xml.append("</sactive>");
        			
        			xml.append("<slogid>");
        			xml.append(changestr(rs.getObject("S_LOGIN_DETAIL_ID").toString()));
        			xml.append("</slogid>");
        			
        			xml.append("<soname>");
        			xml.append(changestr(rs.getObject("S_OWNER_NAME")==null?"0":rs.getObject("S_OWNER_NAME").toString()));
        			xml.append("</soname>");
        			
        			xml.append("<soemail>");
        			xml.append(changestr(rs.getObject("S_OEMAIL")==null?"0":rs.getObject("S_OEMAIL").toString()));
        			xml.append("</soemail>");
        			
        			xml.append("<sophone>");
        			xml.append(changestr(rs.getObject("S_OPHONE_NUMBER")==null?"0":rs.getObject("S_OPHONE_NUMBER").toString()));
        			xml.append("</sophone>");
        			
        			xml.append("<socell>");
        			xml.append(changestr(rs.getObject("S_OCELL_NUMBER")==null?"0":rs.getObject("S_OCELL_NUMBER").toString()));
        			xml.append("</socell>");
        			
        			xml.append("<sofax>");
        			xml.append(changestr(rs.getObject("S_OFAX_NUMBER")==null?"0":rs.getObject("S_OFAX_NUMBER").toString()));
        			xml.append("</sofax>");
        			
        			xml.append("<odoab>");
        			
        			if(rs.getObject("D_ODOB_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_ODOB_DATE")));
        			}
        			xml.append("</odoab>");
        			
        			xml.append("<odoad>");
        			if(rs.getObject("D_ODOA_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_ODOA_DATE")));
        			}
        			xml.append("</odoad>");
        			xml.append("<sperson>");
        			xml.append(changestr(rs.getObject("S_CONTACT_PERSON_NAME").toString()));
        			xml.append("</sperson>");
        			xml.append("<saddr>");
        			xml.append(changestr(rs.getObject("S_CORRES_ADDRES").toString().replace("-", ",")));
        			xml.append("</saddr>");
        			xml.append("<scity>");
        			xml.append(changestr(rs.getObject("S_CITY").toString()));
        			xml.append("</scity>");
        			xml.append("<szip>");
        			xml.append(changestr(rs.getObject("S_ZIP")==null?"0":rs.getObject("S_ZIP").toString()));
        			xml.append("</szip>");
        			xml.append("<semail>");
        			xml.append(changestr(rs.getObject("S_EMAIL")==null?"0":rs.getObject("S_EMAIL").toString()));
        			xml.append("</semail>");
        			xml.append("<sphone>");
        			xml.append(changestr(rs.getObject("S_PHONE_NUMBER")==null?"0":rs.getObject("S_PHONE_NUMBER").toString()));
        			xml.append("</sphone>");
        			xml.append("<scell>");
        			xml.append(changestr(rs.getObject("S_CELL_NUMBER")==null?"0":rs.getObject("S_CELL_NUMBER").toString()));
        			xml.append("</scell>");
        			xml.append("<sfax>");
        			xml.append(changestr(rs.getObject("S_FAX_NUMBER")==null?"0":rs.getObject("S_FAX_NUMBER").toString()));
        			xml.append("</sfax>");
        			xml.append("<dobd>");
        			
        			if(rs.getObject("D_DOB_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_DOB_DATE")));
        			}
        			
        			xml.append("</dobd>");
        			
        			xml.append("<doad>");
        			if(rs.getObject("D_DOA_DATE")==null)
        			{
        				xml.append("0");
        			}
        			else
        			{
        			xml.append(format.format(rs.getObject("D_DOA_DATE")));
        			}
        			
        			
        			xml.append("</doad>");
        			
        			      			
        			
        		}
        		
        		xml.append("</lcode>\n");
        		xml.append("</logindetail>\n");
        		rs.close();
        		ps.close();
        	}
			
			else if (action.equals("CUST_GETALLCUSTSTATUS")) {
				sqlQuery = CustomerSQLC.SELECT_CUSTOMER_ACTIVATE_DETAILS; 
				ps = conn.prepareStatement(sqlQuery);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<lid>");
					xml.append(rs.getObject("s_login_detail_id"));
					xml.append("</lid>\n");
					xml.append("<uid>");
					xml.append(rs.getObject("S_USER_ID").toString().replace("&", "And"));
					xml.append("</uid>\n");
					xml.append("<name>");
					xml.append(rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "And"));
					xml.append("</name>\n");
					xml.append("<email>");
					xml.append(rs.getObject("S_EMAIL"));
					xml.append("</email>");
					xml.append("<activated>");
					xml.append(rs.getObject("ACTIVATED"));
					xml.append("</activated>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			}
			else if (action.equals("CUST_UPDATEACTIVATEDSTATUS")) {
				String comma[] = item.split(",");
				int nStatus=0;
				
				if(comma[0].equals("0"))
				{
					 nStatus=1;
					 String SQLQueryActivateMail = CustomerSQLC.GET_LOGIN_EMAIL_DATA;
					 ps = conn.prepareStatement(SQLQueryActivateMail);
					 System.out.println(comma[1]);
					 ps.setObject(1, comma[1]);
					 rs=ps.executeQuery();
					 if(rs.next())
					 {    
						 com.enercon.security.dao.SecurityDao sm=new com.enercon.security.dao.SecurityDao();
						 sm.sentActivateMsg(rs.getString("S_EMAIL"),rs.getString("S_LOGIN_MASTER_ID"),rs.getString("S_PASSWORD"));						 
					 }
				}
				else{
					 nStatus=0;
				}
				sqlQuery = CustomerSQLC.UPDATEACTIVATEDSTATUS;  
				ps = conn.prepareStatement(sqlQuery);
				ps.setInt(1, nStatus);
				ps.setObject(2, comma[1]);
                int iInserteddRows = ps.executeUpdate();
                //conn.commit();                
                ps.close();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmasterr generated=\""+ System.currentTimeMillis() + "\">\n");
				
					xml.append("<mcodee>\n");
					xml.append("<lid>");
					xml.append("Updated Successfully");
					xml.append("</lid>\n");
					xml.append("</mcodee>\n");
			
				xml.append("</msgmasterr>\n");
				
			}
			
			else if (action.equals("CUST_DETAIL_MESSAGE")) {
				sqlQuery = CustomerSQLC.GET_MESSAGE_DETAIL;
				ps = conn.prepareStatement(sqlQuery);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<msgid>");
					xml.append(rs.getObject("S_MESSAGE_DETAIL_ID"));
					xml.append("</msgid>\n");

					xml.append("<msgdesc>");
					xml.append(rs.getObject("S_MESSAGE_DESCRIPTION") == null ? "": changestr(rs.getObject("S_MESSAGE_DESCRIPTION").toString()));
					xml.append("</msgdesc>\n");
					xml.append("<fdate>");
					String pfdate = convertDateFormat(rs.getObject("D_FROM_DATE").toString(), "yyyy-MM-dd","dd-MMM-yyyy");

					xml.append(pfdate);
					xml.append("</fdate>\n");
					xml.append("<tdate>");
					String ptdate = convertDateFormat(rs.getObject("D_TO_DATE").toString(), "yyyy-MM-dd","dd-MMM-yyyy");

					xml.append(ptdate);
					xml.append("</tdate>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			}
			else if (action.equals("CUST_SENT_MESSAGE_DETAIL")) {

				sqlQuery = CustomerSQLC.DELETE_MESSAGE_FROM_CUSTOMER;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				ps.executeQuery();

				sqlQuery = CustomerSQLC.GET_SENT_MESSAGE_DETAIL;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				rs = ps.executeQuery();
				String Email = "";
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");

					xml.append("<msgid>");
					xml.append(rs.getObject("S_MESSAGE_DETAIL_ID"));
					xml.append("</msgid>\n");
					xml.append("<cid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</cid>\n");
					xml.append("<cname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "NA"
							: rs.getObject("S_CUSTOMER_NAME").toString()
									.replace("&", "And"));
					xml.append("</cname>\n");
					xml.append("<cemail>");
					if (rs.getObject("S_EMAIL") == null
							|| rs.getObject("S_EMAIL") == "") {
						Email = "N.A.";
					}
					xml.append(Email.replace("&", "And"));
					xml.append("</cemail>\n");

					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			}

			else if (action.equals("CUST_SENTTEMPMESSAGE_DETAIL")) {
                
				sqlQuery = CustomerSQLC.GET_SENTTEMPMESSAGE_DETAIL;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				DaoUtility.displayQueryWithParameter(sqlQuery, item);
				rs = ps.executeQuery();
				String Email = "";
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");

					xml.append("<msgid>");
					xml.append(rs.getObject("S_MESSAGE_DETAIL_ID"));
					xml.append("</msgid>\n");
					xml.append("<cid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</cid>\n");
					xml.append("<cname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "NA"
							: rs.getObject("S_CUSTOMER_NAME").toString()
							.replace("&", "And"));
					xml.append("</cname>\n");
					xml.append("<cemail>");
					if (rs.getObject("S_EMAIL") == null
							|| rs.getObject("S_EMAIL") == "") {
						Email = "N.A.";
					}
					xml.append(Email.replace("&", "And"));
					xml.append("</cemail>\n");

					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
				
				
			} else if (action.equals("CUST_DETAIL_MESSAGE_BY_DATE")) {
				sqlQuery = CustomerSQLC.GET_MESSAGE_DETAIL_BY_DATE;
				ps = conn.prepareStatement(sqlQuery);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<msgid>");
					xml.append(rs.getObject("S_MESSAGE_DETAIL_ID"));
					xml.append("</msgid>\n");

					xml.append("<msgdesc>");
					xml
							.append(rs.getObject("S_MESSAGE_DESCRIPTION") == null ? ""
									: rs.getObject("S_MESSAGE_DESCRIPTION")
											.toString().replace("&", "And"));
					xml.append("</msgdesc>\n");
					xml.append("<fdate>");
					String pfdate = convertDateFormat(rs.getObject(
							"D_FROM_DATE").toString(), "yyyy-MM-dd",
							"dd/MM/yyyy");

					xml.append(pfdate);
					xml.append("</fdate>\n");
					xml.append("<tdate>");
					String ptdate = convertDateFormat(rs.getObject("D_TO_DATE")
							.toString(), "yyyy-MM-dd","dd-MMM-yyyy"	);

					xml.append(ptdate);
					xml.append("</tdate>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			} else if (action.equals("CUST_DETAIL_MESSAGE_BY_ID")) {
				sqlQuery = CustomerSQLC.GET_MESSAGE_DETAIL_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				DaoUtility.displayQueryWithParameter(sqlQuery, item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<mcode>\n");
					xml.append("<msgid>");
					xml.append(rs.getObject("S_MESSAGE_DETAIL_ID"));
					xml.append("</msgid>\n");

					xml.append("<msgdesc>");
					xml.append(rs.getObject("S_MESSAGE_DESCRIPTION") == null ? ""
									: rs.getObject("S_MESSAGE_DESCRIPTION")
											.toString().replace("&", "And"));
					xml.append("</msgdesc>\n");
					xml.append("<fdate>");
					String pfdate = convertDateFormat(rs.getObject(
							"D_FROM_DATE").toString(), "yyyy-MM-dd",
							"dd/MM/yyyy");

					xml.append(pfdate);
					xml.append("</fdate>\n");
					xml.append("<tdate>");
					String ptdate = convertDateFormat(rs.getObject("D_TO_DATE")
							.toString(), "yyyy-MM-dd","dd-MMM-yyyy"	);

					xml.append(ptdate);
					xml.append("</tdate>\n");
					xml.append("</mcode>\n");
				}
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();
			}

			else if (action.equals("CUST_DELETE_MESSAGE_DETAIL")) {
				sqlQuery = CustomerSQLC.DELETE_MESSAGE_DETAIL_FROM_CUSTOMER;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				 ps.executeQuery();

				sqlQuery = CustomerSQLC.DELETE_MESSAGE_DETAIL_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
			 ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");

				xml.append("<mcode>\n");
				xml.append("<msgid>");
				xml.append("Sucessfully Deleted");
				xml.append("</msgid>\n");

				xml.append("</mcode>\n");

				xml.append("</msgmaster>\n");
				//.close();
				ps.close();
			}

			else if (action.equals("CUST_TEMP_DELETE")) {
				sqlQuery = CustomerSQLC.DELETE_MESSAGE_TEMP;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, UserId);
				ps.executeQuery();

				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				xml.append("<mcode>\n");
				xml.append("<msgid>");
				xml.append("Sucessfully Deleted");
				xml.append("</msgid>\n");
				xml.append("</mcode>\n");
				xml.append("</msgmaster>\n");

			//s.close();
				ps.close();
			} else if (action.equals("CUST_DELETE_TEMPMSG")) {
				sqlQuery = CustomerSQLC.DELETE_MESSAGE_TEMP_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				String comma[] = item.split(",");
				ps.setObject(1, comma[0]);
				ps.setObject(2, comma[1]);
				DaoUtility.displayQueryWithParameter(sqlQuery, comma[0], comma[1]);
			    ps.executeQuery();

				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				xml.append("<mcode>\n");
				xml.append("<msgid>");
				xml.append("Sucessfully Deleted");
				xml.append("</msgid>\n");
				xml.append("</mcode>\n");
				xml.append("</msgmaster>\n");
				//.close();
				ps.close();		
			}
		    else if (action.equals("CUST_LIST_DELETE_TEMPMSG")) {
		    String comma[] = item.split(":");
		    String[] cust =comma[1].split(",");
			
			  for(int i=0;i<cust.length;i++)
		      { 
				 
				 sqlQuery = CustomerSQLC.DELETE_MESSAGE_TEMP_BY_ID;
				 ps = conn.prepareStatement(sqlQuery);
				 ps.setObject(1, comma[0]);
				 ps.setObject(2, cust[i]);
				 DaoUtility.displayQueryWithParameter(sqlQuery, comma[0], cust[i]);
				 ps.executeQuery();
		       }

			xml.append("<?xml version=\"1.0\"?>\n");
			xml.append("<msgmaster generated=\""
					+ System.currentTimeMillis() + "\">\n");
			xml.append("<mcode>\n");
			xml.append("<msgid>");
			xml.append("Sucessfully Deleted");
			xml.append("</msgid>\n");
			xml.append("</mcode>\n");
			xml.append("</msgmaster>\n");

			//.close();
			ps.close();
			
		} 
			else if (action.equals("CUST_TEMP_TO_POST")) {

				sqlQuery = CustomerSQLC.DELETE_MESSAGE_POST;
				DaoUtility.displayQueryWithParameter(sqlQuery, "NA");
				ps = conn.prepareStatement(sqlQuery);

				 ps.executeQuery();
				//.close();
				ps.close();
				sqlQuery = CustomerSQLC.INSERT_MESSAGE_TEMP_TO_POST;
				DaoUtility.displayQueryWithParameter(sqlQuery, "NA");
				ps = conn.prepareStatement(sqlQuery);
				rs = ps.executeQuery();
				rs.close();
				ps.close();
				sqlQuery = CustomerSQLC.DELETE_MESSAGE_TEMP;
				
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, UserId);
				DaoUtility.displayQueryWithParameter(sqlQuery, UserId);
				 ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				xml.append("<mcode>\n");
				xml.append("<msgid>");
				xml.append("Message Sucessfully Sent");
				xml.append("</msgid>\n");
				xml.append("</mcode>\n");
				xml.append("</msgmaster>\n");

				//.close();
				ps.close();
			}

			else if (action.equals("CUST_ADD_TEMP_MESSAGE")) 
			{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<msgmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				xml.append("<mcode>\n");
				xml.append("<msgid>");
                
				String comma[] = item.split(":");
				logger.debug("comma[]:::" + comma);
				if (comma[1].equals("NA"))				
				{ 				
					if (comma[3] != null)
					{  
						String[] site =comma[3].split(",");
						logger.debug("site[]:::" + site);
						for(int i=0;i<site.length;i++)
						{   
                           
				    		sqlQuery = CustomerSQLC.CHECK_MESSAGE_TEMP_SITE;
				    		logger.debug("sqlQuery:::" + sqlQuery);
					    	ps = conn.prepareStatement(sqlQuery);
						    ps.setObject(1, comma[0]);
					        ps.setObject(2, site[i]);
					        DaoUtility.displayQueryWithParameter(sqlQuery, comma[0], site[i]);
					    	//ps.setObject(3, comma[2]);
						    rs = ps.executeQuery();
						    if (rs.next())
						    {
							 xml.append("Already Exist");
						    } 
						    else
						    {
							sqlQuery = CustomerSQLC.INSERT_MESSAGE_TEMP_SITE;
							logger.debug("sqlQuery:::" + sqlQuery);
							ps1 = conn.prepareStatement(sqlQuery);

							ps1.setObject(1, comma[0]);
							ps1.setObject(2, UserId);
							ps1.setObject(3, site[i]);
							ps1.setObject(4, site[i]);
							DaoUtility.displayQueryWithParameter(sqlQuery, comma[0], UserId, site[i], site[i]);
							rs1 = ps1.executeQuery();
							rs1.close();
							ps1.close();
							xml.append("Sucessfully Inserted");
						    }
					     }//for loop
					}// site loop
					
					
				}// if part of customer
				
				else
				{  
					
					 String[] customer =comma[1].split(",");
				     
				
				    for(int i=0;i<customer.length;i++)
				    {
				      
					  sqlQuery = CustomerSQLC.DELETE_MESSAGE_TEMP_table;
					  logger.debug("sqlQuery:::" + sqlQuery);
					  ps = conn.prepareStatement(sqlQuery);
					  ps.setObject(1, comma[0]);
					  ps.setObject(2, customer[i]);
					  ps.executeQuery();
					  ps.close();
					  
					  sqlQuery = CustomerSQLC.CHECK_MESSAGE_TEMP_CUST;
					  logger.debug("sqlQuery:::" + sqlQuery);
					  ps = conn.prepareStatement(sqlQuery);
					  ps.setObject(1, comma[0]);
					  ps.setObject(2, customer[i]);
					  rs = ps.executeQuery();
					   if (rs.next()) 
					   {
						xml.append("Already Exist");
					   } else
					   {	
						sqlQuery = CustomerSQLC.INSERT_MESSAGE_TEMP_ALL_CUST;
						logger.debug("sqlQuery:::" + sqlQuery);
						ps1 = conn.prepareStatement(sqlQuery); 
						ps1.setObject(1, comma[0]);						  
						ps1.setObject(2, UserId);
						ps1.setObject(3, customer[i]);
						
						rs1 = ps1.executeQuery();
						rs1.close();
						ps1.close();
						xml.append("Sucessfully Inserted");
				        }
				   }   //for loop  
				}	// else part customer loop
				
					

				xml.append("</msgid>\n");
				xml.append("</mcode>\n");
				xml.append("</msgmaster>\n");
				rs.close();
				ps.close();

				
		}//end of if loop

		} catch (SQLException sqlExp) {
			logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			DaoUtility.releaseResources(Arrays.asList(ps,ps1,ps2) , Arrays.asList(rs,rs1,rs2) , conn);
		
		}
		//System.out.println("XML :: " + xml);
		logger.debug(xml.toString());
		return xml.toString();
	}

	public String escape(String s) {
		s = s.replace("&", " amp ");
		s = s.replace("'", " apos ");
		s = s.replace("<", " ltthan ");
		s = s.replace(">", " gtthan ");
		s = s.replace("-", " ");
		s = s.replace("<BR>", "  ");
		// s = s.replace('"', "quot");

		return s;
	}
	public String changestr(String s) {
		s = s.replace("&", " and ");
		s = s.replace("'", "  ");
		s = s.replace("-", "  ");
		s = s.replace("<BR>", "  ");
		s = s.replace("<", "  ");
		s = s.replace(">", "  ");
		
		// s = s.replace('"', "quot");

		return s;
	}

	public static void fetchAll(ResultSet rs) {
		ResultSetMetaData stmtInfo = null;
		try {
			System.out
					.println("=============================================================");

			// retrieve the number, types and properties of the
			// resultset's columns
			stmtInfo = rs.getMetaData();

			int numOfColumns = stmtInfo.getColumnCount();
			int r = 0;

			while (rs.next()) {
				r++;
				//System.out.print("Row: " + r + ": ");
				for (int i = 1; i <= numOfColumns; i++) {

					//System.out.print(rs.getString(i));

					if (i != numOfColumns) {
						//System.out.print(", ");
					}
				}
				//System.out.println();
			}
			
			
		} catch (Exception e) {
			System.out.println("Error: fetchALL: exception");
		    logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
	    
	public static String convertDateFormat(String dateString, String oldFormat,
			String newFormat) {
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

	public static String GetMonthName(String month) {
		String monthname = "";
		if (month.equals("04")) {
			monthname = "APRIL";
		}
		if (month.equals("03")) {
			monthname = "MARCH";
		}
		if (month.equals("01")) {
			monthname = "JANUARY";
		}
		if (month.equals("02")) {
			monthname = "FEBRUARY";
		}

		if (month.equals("05")) {
			monthname = "MAY";
		}

		if (month.equals("06")) {
			monthname = "JUNE";
		}

		if (month.equals("07")) {
			monthname = "JULY";
		}

		if (month.equals("08")) {
			monthname = "AUGUST";
		}

		if (month.equals("09")) {
			monthname = "SEPTEMBER";
		}

		if (month.equals("10")) {
			monthname = "OCTOBER";
		}

		if (month.equals("11")) {
			monthname = "NOVEMBER";
		}

		if (month.equals("12")) {
			monthname = "DECEMBER";
		}
		return monthname;
	}
	public String openFile(String fileName) throws IOException {
		File file = new File(fileName);
		int size = (int) file.length();
		int chars_read = 0;
		FileReader in = new FileReader(file);
		char[] data = new char[size];
		chars_read = in.read(data, 0, size);
		in.close();
		return new String(data, 0, chars_read);
	}
	public static float Round(float Rval, int Rpl) {
		  float p = (float)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  float tmp = Math.round(Rval);
		  return (float)tmp/p;
		    }
	
	 public static String forXML(String aText){
		    final StringBuilder result = new StringBuilder();
		    final StringCharacterIterator iterator = new StringCharacterIterator(aText);
		    char character =  iterator.current();
		    while (character != CharacterIterator.DONE ){
		      if (character == '<') {
		        result.append("lt;");
		      }
		      else if (character == '>') {
		        result.append("gt;");
		      }
		      else if (character == '\"') {
		        result.append("quot;");
		      }
		      else if (character == '\'') {
		        result.append("squot;");
		      }
		      else if (character == '&') {
		         result.append("amp;");
		      }
		      else if (character == '=') {
			         result.append("equal;");
			      }
		      else if (character == '+') {
			         result.append("plus;");
			      }
		      else if (character == '?') {
			         result.append("qmark;");
			      }
		      else if (character == ',') {
			         result.append(",");
			      }
		      else if (character == '-') {
			         result.append("-");
			      }
		     /* else if (character == ')') {
			         result.append("cbrace;");
			      }
		      else if (character == '(') {
			         result.append("0brace;");
			      }
		      else if (character == ',') {
			         result.append("comma;");
			      }*/
		      else if (character == ',') {
			         result.append(" ");
			      }
		      
		      else {
		        //the char is not a special one
		        //add it to the result as is
		        result.append(character);
		      }
		      character = iterator.next();
		    }
		    return result.toString();
		  }
	 public List getERDADetail(String rdate)
		throws Exception {
//	 public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
	PreparedStatement prepStmt = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ResultSet rs1 = null;
	List tranList = new ArrayList();
	String sqlQuery = "";

	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	String fdate=rdate.substring(2);
	

	fdate="01"+fdate;

	String yfdate="01/04/2011";

	java.util.Date ffd = format.parse(rdate);
	java.util.Date firstdt = format.parse(fdate);
	java.util.Date firstydt = format.parse(yfdate);
	java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
	java.sql.Date firstdate = new java.sql.Date(firstdt.getTime());
	java.sql.Date yfirstdate = new java.sql.Date(firstydt.getTime());

	try {
	sqlQuery = CustomerSQLC.SELECT_GET_ERDADETAIL;
	prepStmt = conn.prepareStatement(sqlQuery);
	prepStmt.setObject(1, "1004000026");

		


//	 ps.setObject(1,item.toUpperCase() + "%");


		rs = prepStmt.executeQuery();
		int i = 0;
		while (rs.next()) {
			Vector tranVector = new Vector();
			tranVector.add(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
			tranVector.add(rs.getObject("S_STATE_NAME"));
			tranVector.add(rs.getObject("S_STATE_ID"));
			
			
			
			sqlQuery = CustomerSQLC.SELECT_WEC_CAPACITY1;;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
			ps.setObject(2,rs.getObject("S_STATE_ID").toString());
			ps.setObject(3,rs.getObject("S_CUSTOMER_ID").toString());
			ps.setObject(4,reportdate);
			rs1 = ps.executeQuery();
			if (rs1.next()) 
			{
				tranVector.add(rs1.getObject("lcapacity"));
			}
			else
			{
				tranVector.add("0");
			}
			ps.close();
			rs1.close();
			
			
			
			sqlQuery = CustomerSQLC.SELECT_GET_READING1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
			ps.setObject(2,reportdate);
			ps.setObject(3,rs.getObject("S_STATE_ID").toString());
			ps.setObject(4,rs.getObject("S_CUSTOMER_ID").toString());
			rs1 = ps.executeQuery();
			if (rs1.next()) 
			{
				tranVector.add(rs1.getObject("A_VAL"));
			}
			else
			{
				tranVector.add("0");
			}
			ps.close();
			rs1.close();
			
			
			
			sqlQuery = CustomerSQLC.SELECT_GET_READING_CUM1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
			ps.setObject(2,firstdate);
			ps.setObject(3,reportdate);
			ps.setObject(4,rs.getObject("S_STATE_ID").toString());
			ps.setObject(5,rs.getObject("S_CUSTOMER_ID").toString());
			rs1 = ps.executeQuery();
			
			if (rs1.next()) 
			{
				tranVector.add(rs1.getObject("A_VAL"));
				
				
			}
			else
			{
				tranVector.add("0");
			}
			rs1.close();
			ps.close();
			
			sqlQuery = CustomerSQLC.SELECT_GET_READING_CUM1;
			ps = conn.prepareStatement(sqlQuery);
			ps.setObject(1,rs.getObject("S_CUSTOMER_ID").toString());
			ps.setObject(2,yfirstdate);
			ps.setObject(3,reportdate);
			ps.setObject(4,rs.getObject("S_STATE_ID").toString());
			ps.setObject(5,rs.getObject("S_CUSTOMER_ID").toString());
			rs1 = ps.executeQuery();
			
			if (rs1.next()) 
			{
				tranVector.add(rs1.getObject("A_VAL"));
				
				
			}
			else
			{
				tranVector.add("0");
			}
			rs1.close();
			ps.close();
			tranVector.add(rs.getObject("S_CUSTOMER_ID"));
			
			tranList.add(i, tranVector);
			i++;
		}
		prepStmt.close();
		rs.close();

	} catch (SQLException sqlExp) {
		logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
		Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
		throw exp;
	} finally {
		DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
	}
	return tranList;
	}
	
	 public List getERDAWECWiseDetail(String custid,String stateid,String rdate)
		throws Exception {
		
		//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		List tranList = new ArrayList();
		String sqlQuery = "";

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		String fdate=rdate.substring(2);

		fdate="01"+fdate;

		String yfdate="01/04/2011";

		java.util.Date ffd = format.parse(rdate);
		java.util.Date firstdt = format.parse(fdate);
		java.util.Date firstydt = format.parse(yfdate);
		java.sql.Date reportdate = new java.sql.Date(ffd.getTime());
		java.sql.Date firstdate = new java.sql.Date(firstdt.getTime());
		java.sql.Date yfirstdate = new java.sql.Date(firstydt.getTime());

		try {
				
			sqlQuery = CustomerSQLC.SELECT_GET_ERDADETAIL_WECWISE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, custid);
			prepStmt.setObject(2, stateid);
			prepStmt.setObject(3, reportdate);
			String wecid = "";
			rs = prepStmt.executeQuery();
			int i = 0;
			
			while (rs.next()) {
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_WECSHORT_DESCR"));
				tranVector.add(rs.getObject("GENERATION"));
				wecid=rs.getObject("S_WEC_ID").toString();
				
			
				sqlQuery = CustomerSQLC.SELECT_GET_READING_CUM12;
				ps = conn.prepareStatement(sqlQuery);
				
				ps.setObject(1,firstdate);
				ps.setObject(2,reportdate);
				ps.setObject(3,wecid);
				
				rs1 = ps.executeQuery();
				
				while (rs1.next()) 
				{
					tranVector.add(rs1.getObject("A_VAL"));				
				}
				
				rs1.close();
				ps.close();
			
			
				sqlQuery = CustomerSQLC.SELECT_GET_READING_CUM12;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,yfirstdate);
				ps.setObject(2,reportdate);
				ps.setObject(3,wecid);
				rs1 = ps.executeQuery();
				
				while (rs1.next()) 
				{
					tranVector.add(rs1.getObject("A_VAL"));				
				}
				rs1.close();
				ps.close();
			tranList.add(i, tranVector);
			i++;
			}
			prepStmt.close();
			rs.close();
		}catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(Arrays.asList(prepStmt,ps) , Arrays.asList(rs,rs1) , conn);
		}
		System.out.println("tranList.toString()"+tranList.toString());
		return tranList;
	}

	 public List getMissingScadaDataReport1(String stateName, String areaName, String siteName, String reportDate) throws Exception {
			// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			List tranList = new ArrayList();
			String sqlQuery = "";

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String rdate=convertDateFormat(reportDate, "dd/MM/yyyy", "dd-MMM-yyyy");
			//String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
	          
			/*sqlQuery = "SELECT  DISTINCT TBL_WEC_MASTER.S_WECSHORT_DESCR,  "+
						"        (SELECT S_LOCATION_NO FROM SCADADW.TBL_PLANT_MASTER WHERE TBL_PLANT_MASTER.S_SERIAL_NO = TBL_WEC_MASTER.S_TECHNICAL_NO AND TBL_PLANT_MASTER.N_STATUS = 1) AS S_LOCATION_NO, "+
						"        (SELECT S_PLANT_NO FROM SCADADW.TBL_PLANT_MASTER WHERE TBL_PLANT_MASTER.S_SERIAL_NO = TBL_WEC_MASTER.S_TECHNICAL_NO AND TBL_PLANT_MASTER.N_STATUS = 1) AS S_PLANT_NO, "+
						"        TBL_WEC_MASTER.S_TECHNICAL_NO,  "+
						"        TBL_SITE_MASTER.S_SITE_NAME,  "+
						"        TBL_AREA_MASTER.S_AREA_NAME,  "+
						"        TBL_STATE_MASTER.S_STATE_NAME         "+
						"FROM    TBL_WEC_MASTER, TBL_WEC_READING, TBL_EB_MASTER, TBL_SITE_MASTER, TBL_AREA_MASTER, TBL_STATE_MASTER "+
						"WHERE   TBL_WEC_READING.D_READING_DATE = '"+rdate+"' "+
						"AND     TBL_WEC_MASTER.S_SCADA_FLAG = '1' ";
			
			if(!stateName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_STATE_MASTER.S_STATE_ID = '"+stateName+"' ";
			if(!areaName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_AREA_MASTER.S_AREA_ID = '"+areaName+"' ";
			if(!siteName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_SITE_MASTER.S_SITE_ID = '"+siteName+"' ";
			
			sqlQuery = 	sqlQuery+"AND     TBL_WEC_MASTER.S_EB_ID = TBL_EB_MASTER.S_EB_ID "+
						"AND     TBL_SITE_MASTER.S_SITE_ID = TBL_EB_MASTER.S_SITE_ID "+
						"AND     TBL_AREA_MASTER.S_AREA_ID = TBL_SITE_MASTER.S_AREA_ID "+
						"AND     TBL_STATE_MASTER.S_STATE_ID = TBL_AREA_MASTER.S_STATE_ID "+
						"AND     TBL_WEC_MASTER.S_WEC_ID = TBL_WEC_READING.S_WEC_ID "+
						"AND     TBL_WEC_MASTER.S_WEC_ID NOT IN (SELECT A.S_WEC_ID FROM TBL_WEC_READING A WHERE A.D_READING_DATE = TBL_WEC_READING.D_READING_DATE AND A.S_CREATED_BY = 'SYSTEM') "+
						"ORDER BY TBL_STATE_MASTER.S_STATE_NAME, TBL_AREA_MASTER.S_AREA_NAME, TBL_SITE_MASTER.S_SITE_NAME, TBL_WEC_MASTER.S_WECSHORT_DESCR";*/
			
			sqlQuery = "Select Statemas.S_State_Name As S_State_Name,Areamas.S_Area_Name As S_Area_Name,Sitemas.S_Site_Name As S_Site_Name,  " + 
					"        Plantmas.S_Location_No As S_Location_No, Plantmas.S_Plant_No As S_Plant_No, " + 
					"        Wecmas.S_Wecshort_Descr as S_WECSHORT_DESCR, Wecmas.S_Technical_No as S_TECHNICAL_NO " + 
					"From Tbl_Wec_Master Wecmas,Tbl_Eb_Master Ebmas, Tbl_Site_Master Sitemas, Tbl_State_Master Statemas, TBL_AREA_MASTER AREAMAS, SCADADW.TBL_PLANT_MASTER PLANTMAS " + 
					"Where Wecmas.S_Eb_Id = Ebmas.S_Eb_Id " + 
					"And Ebmas.S_Site_Id = Sitemas.S_Site_Id " + 
					"And Sitemas.S_State_Id = Statemas.S_State_Id " + 
					"And Sitemas.S_Area_Id = Areamas.S_Area_Id " + 
					"And Plantmas.S_Serial_No = Wecmas.S_Technical_No " + 
					"And WECMAS.S_Wec_Id Not In (Select Distinct S_Wec_Id  " + 
					"                      From Tbl_Wec_Reading  " + 
					"                      Where D_Reading_Date = ? AND S_CREATED_BY = 'SYSTEM') " + 
					"AND WECMAS.S_SCADA_FLAG = '1' ";
			if(!stateName.equals(""))
				sqlQuery = sqlQuery + " AND statemas.S_STATE_ID = '"+stateName+"' ";
			if(!areaName.equals(""))
				sqlQuery = sqlQuery + " AND Areamas.S_AREA_ID = '"+areaName+"' ";
			if(!siteName.equals(""))
				sqlQuery = sqlQuery + " AND sitemas.S_SITE_ID = '"+siteName+"' ";
			sqlQuery = sqlQuery + " ORDER BY STATEMAS.S_STATE_NAME, AREAMAS.S_AREA_NAME, SITEMAS.S_SITE_NAME, WECMAS.S_WECSHORT_DESCR";
		    
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, rdate);	
			// ps.setObject(1,item.toUpperCase() + "%");

			try {
				rs = prepStmt.executeQuery();
				int i = 0;
				while (rs.next()) {
					Vector tranVector = new Vector();
					tranVector.add(rs.getObject("S_STATE_NAME")==null?"NA":rs.getObject("S_STATE_NAME").toString());
					tranVector.add(rs.getObject("S_AREA_NAME")==null?"NA":rs.getObject("S_AREA_NAME").toString());
					tranVector.add(rs.getObject("S_SITE_NAME")==null?"NA":rs.getObject("S_SITE_NAME").toString());
					tranVector.add(rs.getObject("S_LOCATION_NO")==null?"NA":rs.getObject("S_LOCATION_NO").toString());
					tranVector.add(rs.getObject("S_PLANT_NO")==null?"NA":rs.getObject("S_PLANT_NO").toString());
					tranVector.add(rs.getObject("S_WECSHORT_DESCR")==null?"NA":rs.getObject("S_WECSHORT_DESCR").toString());					
					tranVector.add(rs.getObject("S_TECHNICAL_NO")==null?"NA":rs.getObject("S_TECHNICAL_NO").toString());
					
					tranList.add(i, tranVector);
					i++;
				}
				//prepStmt.clearParameters();
				//prepStmt.clearBatch();
				prepStmt.close();
				rs.close();

			} catch (SQLException sqlExp) {
				logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
				Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
				throw exp;
			} finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);
			}
		return tranList;
	}
	/*public List getMissingScadaDataReport(String stateId, String areaName, String siteName, String reportDate) throws Exception {
			// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = conmanager.getConnection();
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			List tranList = new ArrayList();
			String sqlQuery = "";

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String rdate=convertDateFormat(reportDate, "dd/MM/yyyy", "dd-MMM-yyyy");
			//String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
	          
			sqlQuery = "SELECT  DISTINCT TBL_WEC_MASTER.S_WECSHORT_DESCR,  "+
						"        (SELECT S_LOCATION_NO FROM SCADADW.TBL_PLANT_MASTER WHERE TBL_PLANT_MASTER.S_SERIAL_NO = TBL_WEC_MASTER.S_TECHNICAL_NO AND TBL_PLANT_MASTER.N_STATUS = 1  AND ROWNUM=1) AS S_LOCATION_NO, "+
						"        (SELECT S_PLANT_NO FROM SCADADW.TBL_PLANT_MASTER WHERE TBL_PLANT_MASTER.S_SERIAL_NO = TBL_WEC_MASTER.S_TECHNICAL_NO AND TBL_PLANT_MASTER.N_STATUS = 1  AND ROWNUM=1) AS S_PLANT_NO, "+
						"        TBL_WEC_MASTER.S_TECHNICAL_NO,  "+
						"        TBL_SITE_MASTER.S_SITE_NAME,  "+
						"        TBL_AREA_MASTER.S_AREA_NAME,  "+
						"        TBL_STATE_MASTER.S_STATE_NAME         "+
						"FROM    TBL_WEC_MASTER, TBL_WEC_READING, TBL_EB_MASTER, TBL_SITE_MASTER, TBL_AREA_MASTER, TBL_STATE_MASTER "+
						"WHERE   TBL_WEC_READING.D_READING_DATE = '"+rdate+"' "+
						"AND     TBL_WEC_MASTER.S_SCADA_FLAG = '1' ";
			
			if(!stateName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_STATE_MASTER.S_STATE_ID = '"+stateName+"' ";
			if(!areaName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_AREA_MASTER.S_AREA_ID = '"+areaName+"' ";
			if(!siteName.equals(""))
				sqlQuery = sqlQuery+"AND TBL_SITE_MASTER.S_SITE_ID = '"+siteName+"' ";
			
			sqlQuery = 	sqlQuery+"AND     TBL_WEC_MASTER.S_EB_ID = TBL_EB_MASTER.S_EB_ID "+
						"AND     TBL_SITE_MASTER.S_SITE_ID = TBL_EB_MASTER.S_SITE_ID "+
						"AND     TBL_AREA_MASTER.S_AREA_ID = TBL_SITE_MASTER.S_AREA_ID "+
						"AND     TBL_STATE_MASTER.S_STATE_ID = TBL_AREA_MASTER.S_STATE_ID "+
						"AND     TBL_WEC_MASTER.S_WEC_ID = TBL_WEC_READING.S_WEC_ID "+
						"AND     TBL_WEC_MASTER.S_WEC_ID NOT IN (SELECT A.S_WEC_ID FROM TBL_WEC_READING A WHERE A.D_READING_DATE = TBL_WEC_READING.D_READING_DATE AND A.S_CREATED_BY = 'SYSTEM') "+
						"ORDER BY TBL_STATE_MASTER.S_STATE_NAME, TBL_AREA_MASTER.S_AREA_NAME, TBL_SITE_MASTER.S_SITE_NAME, TBL_WEC_MASTER.S_WECSHORT_DESCR";
			
			sqlQuery =  "Select Statemas.S_State_Name As S_State_Name,Areamas.S_Area_Name As S_Area_Name,Sitemas.S_Site_Name As S_Site_Name,  " + 
						"        Plantmas.S_Location_No As S_Location_No, Plantmas.S_Plant_No As S_Plant_No, " + 
						"        Wecmas.S_Wecshort_Descr as S_WECSHORT_DESCR, Wecmas.S_Technical_No as S_TECHNICAL_NO " + 
						"From Tbl_Wec_Master Wecmas,Tbl_Eb_Master Ebmas, Tbl_Site_Master Sitemas, Tbl_State_Master Statemas, TBL_AREA_MASTER AREAMAS, SCADADW.TBL_PLANT_MASTER PLANTMAS " + 
						"Where Wecmas.S_Eb_Id = Ebmas.S_Eb_Id " + 
						"And Ebmas.S_Site_Id = Sitemas.S_Site_Id " + 
						"And Sitemas.S_State_Id = Statemas.S_State_Id " + 
						"And Sitemas.S_Area_Id = Areamas.S_Area_Id " + 
						"And Plantmas.S_Serial_No = Wecmas.S_Technical_No " + 
						"And WECMAS.S_Wec_Id Not In (Select Distinct S_Wec_Id  " + 
						"                      From Tbl_Wec_Reading  " + 
						"                      Where D_Reading_Date = ? AND S_CREATED_BY = 'SYSTEM') " + 
						"AND WECMAS.S_SCADA_FLAG = '1' and statemas.s_state_id = "+ stateId +
						"and WECMAS.S_Status not in (2,9) " +
						" ORDER BY STATEMAS.S_STATE_NAME, AREAMAS.S_AREA_NAME, SITEMAS.S_SITE_NAME, WECMAS.S_WECSHORT_DESCR";
			
			sqlQuery = 
					"Select Statemas.S_State_Name As S_State_Name,Areamas.S_Area_Name As S_Area_Name,Sitemas.S_Site_Name As S_Site_Name, " + 
					"      Plantmas.S_Location_No As S_Location_No, Plantmas.S_Plant_No As S_Plant_No, " + 
					"      Wecmas.S_Wecshort_Descr As S_Wecshort_Descr, Wecmas.S_Technical_No As S_Technical_No, " + 
					"      Wecmas.S_Wec_Id " + 
					"From Tbl_Wec_Master Wecmas,Tbl_Eb_Master Ebmas, Tbl_Site_Master Sitemas, Tbl_State_Master Statemas, TBL_AREA_MASTER AREAMAS, SCADADW.TBL_PLANT_MASTER PLANTMAS, Meta_data mdata " + 
					"Where Wecmas.S_Eb_Id = Ebmas.S_Eb_Id " + 
					"And Ebmas.S_Site_Id = Sitemas.S_Site_Id " + 
					"And Sitemas.S_State_Id = Statemas.S_State_Id " + 
					"And Sitemas.S_Area_Id = Areamas.S_Area_Id " + 
					"And Plantmas.S_Serial_No = Wecmas.S_Technical_No " + 
					"And Mdata.S_Wec_Id = Wecmas.S_Wec_Id " + 
					"And Wecmas.S_Wec_Id Not In (Select distinct S_Wec_Id " + 
					"                            From Tbl_Wec_Reading " + 
					"                            Where D_Reading_Date = ? " + 
					"                            And S_Created_By = 'SYSTEM') " + 
					"And Mdata.D_Reading_Date = ? " + 
					"AND WECMAS.S_SCADA_FLAG = '1' " +
					"and statemas.s_state_id = " + stateId + 
					"And Wecmas.S_Status Not In (2,9) " + 
					"and (OPERATINGHRS + LULLHRS + MACHINEFAULT + MACHINESD + EBLOADRST + INTERNALFAULT + INTERNALSD + EXTERNALFAULT + EXTERNALSD + WECLOADRST) < (0 + (60 * 0) + (20 * 60 * 60)) " + 
					"Order By Statemas.S_State_Name, Areamas.S_Area_Name, Sitemas.S_Site_Name, Wecmas.S_Wecshort_Descr " ; 
		    
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, rdate);
			prepStmt.setObject(2, rdate);
			// ps.setObject(1,item.toUpperCase() + "%");

			String plantNo = null;
			String locNo = null;
			try {
				rs = prepStmt.executeQuery();
				int i = 0;
				while (rs.next()) {
					Vector tranVector = new Vector();
					tranVector.add(rs.getObject("S_STATE_NAME")==null?"NA":rs.getObject("S_STATE_NAME").toString());
					tranVector.add(rs.getObject("S_AREA_NAME")==null?"NA":rs.getObject("S_AREA_NAME").toString());
					tranVector.add(rs.getObject("S_SITE_NAME")==null?"NA":rs.getObject("S_SITE_NAME").toString());
					tranVector.add(rs.getObject("S_LOCATION_NO")==null?"NA":rs.getObject("S_LOCATION_NO").toString());
					tranVector.add(rs.getObject("S_PLANT_NO")==null?"NA":rs.getObject("S_PLANT_NO").toString());
					tranVector.add(rs.getObject("S_WECSHORT_DESCR")==null?"NA":rs.getObject("S_WECSHORT_DESCR").toString());					
					tranVector.add(rs.getObject("S_TECHNICAL_NO")==null?"NA":rs.getObject("S_TECHNICAL_NO").toString());
					tranList.add(i, tranVector);
					i++;
				}
				//prepStmt.clearParameters();
				//prepStmt.clearBatch();
				prepStmt.close();
				rs.close();

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
					if (conn != null) {
						conn.close();
						conn = null;

						conmanager.closeConnection();
						conmanager = null;
					}
				} catch (Exception e) {
					prepStmt = null;
					rs = null;
					if (conn != null) {
						conn.close();
						conn = null;

						conmanager.closeConnection();
						conmanager = null;
					}
				}
			}
		return tranList;
	}*/
	 
	 public List getMissingScadaDataReport(String stateId, String areaName, String siteName, String reportDate) throws Exception {
			// public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		//JDBCUtils conmanager = new JDBCUtils();
			Connection conn = wcareConnector.getConnectionFromPool();
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			List tranList = new ArrayList();
			String sqlQuery = "";

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String rdate=convertDateFormat(reportDate, "dd/MM/yyyy", "dd-MMM-yyyy");
			//String ntdate=convertDateFormat(tdate, "dd/MM/yyyy", "dd-MMM-yyyy");
	          
			sqlQuery = 
					"Select Statemas.S_State_Name As S_State_Name,Areamas.S_Area_Name As S_Area_Name,Sitemas.S_Site_Name As S_Site_Name, " + 
					"      Plantmas.S_Location_No As S_Location_No, Plantmas.S_Plant_No As S_Plant_No, " + 
					"      Wecmas.S_Wecshort_Descr As S_Wecshort_Descr, "+
					"get_missing_scada_days(WECMAS.S_Wec_Id,Mdata.D_Reading_Date) as Missing_Days, "+
					"Wecmas.S_Technical_No As S_Technical_No, Wecmas.S_Wec_Id " + 				
					"From Tbl_Wec_Master Wecmas,Tbl_Eb_Master Ebmas, Tbl_Site_Master Sitemas, Tbl_State_Master Statemas, TBL_AREA_MASTER AREAMAS, SCADADW.TBL_PLANT_MASTER PLANTMAS, Meta_data mdata " + 
					"Where Wecmas.S_Eb_Id = Ebmas.S_Eb_Id " + 
					"And Ebmas.S_Site_Id = Sitemas.S_Site_Id " + 
					"And Sitemas.S_State_Id = Statemas.S_State_Id " + 
					"And Sitemas.S_Area_Id = Areamas.S_Area_Id " + 
					"And Plantmas.S_Serial_No = Wecmas.S_Technical_No " + 
					"And Mdata.S_Wec_Id = Wecmas.S_Wec_Id " + 
					"And Wecmas.S_Wec_Id Not In (Select S_Wec_Id " + 
					"  								From Tbl_Wec_Reading " + 
					"                               Where D_Reading_Date = ? " + 
					"                               and S_mp_id = '0808000023' " + 
					"                               and N_actual_Value <> 0 " + 
					"								group by S_wec_id ) " + 
					"And Mdata.D_Reading_Date = ? " + 
					"AND WECMAS.S_SCADA_FLAG = '1' " +
					"and statemas.s_state_id = " + stateId + 
					"And Wecmas.S_Status Not In (2,9) " + 
					"and (MACHINEFAULT + MACHINESD + EBLOADRST + INTERNALFAULT + INTERNALSD + EXTERNALFAULT + EXTERNALSD + WECLOADRST + CUSTOMER_SCOPE) < (0 + (60 * 0) + (4 * 60 * 60)) " +
 					"and OPERATINGHRS = 0 " +
					"Order By Missing_Days desc"; 
		    
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, rdate);
			prepStmt.setObject(2, rdate);
			// ps.setObject(1,item.toUpperCase() + "%");

			/*String plantNo = null;
			String locNo = null;*/
			try {
				rs = prepStmt.executeQuery();
				int i = 0;
				while (rs.next()) {
					Vector tranVector = new Vector();
					tranVector.add(rs.getObject("S_STATE_NAME")==null?"NA":rs.getObject("S_STATE_NAME").toString());
					tranVector.add(rs.getObject("S_AREA_NAME")==null?"NA":rs.getObject("S_AREA_NAME").toString());
					tranVector.add(rs.getObject("S_SITE_NAME")==null?"NA":rs.getObject("S_SITE_NAME").toString());
					tranVector.add(rs.getObject("S_LOCATION_NO")==null?"NA":rs.getObject("S_LOCATION_NO").toString());
					tranVector.add(rs.getObject("S_PLANT_NO")==null?"NA":rs.getObject("S_PLANT_NO").toString());
					tranVector.add(rs.getObject("S_WECSHORT_DESCR")==null?"NA":rs.getObject("S_WECSHORT_DESCR").toString());
					tranVector.add(rs.getObject("Missing_Days")==null?"NA":rs.getObject("Missing_Days").toString());
					tranVector.add(rs.getObject("S_TECHNICAL_NO")==null?"NA":rs.getObject("S_TECHNICAL_NO").toString());
					
					tranList.add(i, tranVector);
					i++;
				}
				//prepStmt.clearParameters();
				//prepStmt.clearBatch();
				prepStmt.close();
				rs.close();

			} catch (SQLException sqlExp) {
				logger.error("\nClass: " + sqlExp.getClass() + "\nMessage: " + sqlExp.getMessage() + "\n", sqlExp);
				Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
				throw exp;
			} finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);
			}
			
		return tranList;
	}

	public ArrayList<String> getStateID() {
		
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<String> stateID = new ArrayList<String>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Select S_State_Id,S_State_Name From Tbl_State_Master " + 
						"WHERE UPPER(S_STATE_NAME) NOT IN (UPPER('DUMMY'))" +
						"ORDER BY S_STATE_NAME";
			prepStmt = conn.prepareStatement(sqlQuery);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				stateID.add(rs.getString("S_STATE_ID"));	
				
			}
			return stateID;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;
	}

	public ArrayList<String> getStateNames() {
		//JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		ArrayList<String> stateNames = new ArrayList<String>();
		
		try{
			conn =wcareConnector.getConnectionFromPool();;
			sqlQuery = "Select S_State_Name From Tbl_State_Master " + 
						"WHERE UPPER(S_STATE_NAME) NOT IN (UPPER('DUMMY'))" +
						"ORDER BY S_STATE_NAME";
			prepStmt = conn.prepareStatement(sqlQuery);
				
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				stateNames.add(rs.getString("S_STATE_NAME"));	
			}
			return stateNames;
		}
		catch(Exception e){
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return null;
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("Session Created");
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Session Destroyed");
	}
	
	public Map<String, String> getStateIdNameMapping(String customerId) throws SQLException{
		return new custDAO(GET_STATE_ID_WITH_CUSTOMER_ID,new String[]{customerId}).getResultInMap(); 
	}
	
	public Map<String, String> getSiteIdNameMapping(String customerId,String stateId) throws SQLException{
		return new custDAO(GET_SITE_ID_WITH_CUSTOMER_ID_AND_STATE_ID,new String[]{customerId, stateId}).getResultInMap(); 
	}
	
	public Set<String> getWecIdsBasedOnStateId(String customerId, String stateId) throws SQLException {
		return new custDAO(GET_WEC_IDS_BASED_ON_CUSTOMER_ID_AND_STATE_ID, new String[]{customerId, stateId}).getResultInSet();
	}
	
	public Set<String> getWecIdsBasedOnSiteId(String customerId, String stateId ,String siteId) throws SQLException {
		return new custDAO(GET_WEC_IDS_BASED_ON_CUSTOMER_ID_AND_STATE_ID_AND_SITE_ID, new String[]{customerId, stateId ,siteId}).getResultInSet();
	}
	
	public Map<String, String> getWecIdNameMapping(String customerId) throws SQLException {
		return new custDAO(GET_WEC_ID_WITH_CUSTOMER_ID, new String[]{customerId}).getResultInMap();
	}
	
	private final static String GET_STATE_ID_WITH_CUSTOMER_ID =
			"Select S_State_Id,S_State_Name " +
			"From Customer_Meta_Data " +
			"where S_Customer_Id = ? " +
		    "Group By S_State_Name,S_State_Id " +  
			"order by S_State_Name " ;
	
	private final static String GET_WEC_IDS_BASED_ON_CUSTOMER_ID_AND_STATE_ID =
			"Select S_WEC_ID , S_Wecshort_Descr " + 
			"From Customer_Meta_Data " + 
			"Where S_Customer_Id = ? " +
			"And S_State_Id = ? " ;
	
	private final static String GET_SITE_ID_WITH_CUSTOMER_ID_AND_STATE_ID =
			"Select S_Site_Id,S_Site_Name " +
			"From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"and S_State_Id = ? " +
			"Group By S_Site_Name,S_Site_Id " +
			"order by S_Site_Name " ;  
	
	private final static String GET_WEC_IDS_BASED_ON_CUSTOMER_ID_AND_STATE_ID_AND_SITE_ID =
			"Select S_Wec_Id, S_Wecshort_Descr " +
			"From Customer_Meta_Data " +
			"Where S_Customer_Id = ? " +
			"And S_State_Id = ? " +
			"And S_Site_Id = ? " +
			"order by S_Wecshort_Descr "  ;
	
	private final static String GET_WEC_ID_WITH_CUSTOMER_ID = 
			"Select S_Wec_Id, S_Wecshort_Descr " +
			"From Customer_Meta_Data " + 
			"Where S_Customer_Id = ? " +
			"order by S_Wecshort_Descr "; 
	
	public class custDAO{
		private String query;
		private String[] queryParameter;
		
		public custDAO(String query, String[] queryParameter) {
			super();
			this.query = query;
			this.queryParameter = queryParameter;
		}
		
		public Map<String, String> getResultInMap() throws SQLException{
			Connection conn = null;
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			
			//LinkedHashMap is same as HashMap instead maintains insertion order
			Map<String, String> result = new LinkedHashMap<String, String>();
			int count = 0;
			try {
				conn = wcareConnector.getConnectionFromPool();
				
				prepStmt = conn.prepareStatement(query);
				
				for (int i = 0; i < queryParameter.length; i++) {
					prepStmt.setString(++count, queryParameter[i]);
				}
//				System.out.println(queryParameter.length+" :: "+query);
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					result.put(rs.getString(1), rs.getString(2));
					//System.out.println(rs.getString(1)+"::"+rs.getString(2));
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);
			}
			return result;
		}
		public Set<String> getResultInSet() throws SQLException{
			Connection conn = null;
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			
			//Maintains insertion order
			Set<String> result = new LinkedHashSet<String>();
			int count = 0;
			try {
				conn = wcareConnector.getConnectionFromPool();
				
				prepStmt = conn.prepareStatement(query);
				
				for (int i = 0; i < queryParameter.length; i++) {
					prepStmt.setString(++count, queryParameter[i]);
				}
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					result.add(rs.getString(1));
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);
			}
			return result;
		}
	}

}