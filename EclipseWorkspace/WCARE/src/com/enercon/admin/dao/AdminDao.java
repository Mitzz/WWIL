package com.enercon.admin.dao;

import com.enercon.Time24HoursValidator;
import com.enercon.admin.bean.CreateRoleBean;
import com.enercon.admin.bean.RoleMappingBean;
import com.enercon.admin.job.InsertPESData;
import com.enercon.global.utility.DatabaseUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.MethodClass;
import com.enercon.global.utils.CallScheduler;
import com.enercon.global.utils.DynaBean;
import com.enercon.global.utils.GlobalSQLC;
import com.enercon.global.utils.JDBCUtils;
import com.enercon.global.utils.CodeGenerate;
import com.enercon.customer.dao.CustomerDao;
import com.enercon.global.utils.Diff;
import com.enercon.reports.pojo.PESDataStorage;
import com.enercon.reports.pojo.WECReadingTrackerDataStorage;
import com.enercon.struts.CustomerIdName;
import com.enercon.struts.action.submit.InitialReadingUploadHandler;
import com.enercon.struts.exception.GridBifurcationException;
import com.enercon.struts.pojo.LocationMasterMapper;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.lang.reflect.Array;

public class AdminDao {
	private static Logger logger = Logger.getLogger(AdminDao.class);

	public AdminDao() {
	}

	/*public List imageDetailsDisplay() throws Exception
    {
    	JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		ArrayList alist = new ArrayList();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Blob image;
		//String image = "";
		byte[ ] imgData = null ; 

		String sqlQuery = AdminSQLC.GET_IMAGE_DETAIL_DISPLAY;
		ps = conn.prepareStatement(sqlQuery);

		rs = ps.executeQuery();

		while (rs.next()){
			//alist.add(rs.getString(1));
			image = rs.getBlob(1);
			imgData = image.getBytes(1,(int)image.length());
			//alist.add(image.getBytes(1,(int)image.length())) ;
			alist.add(imgData);
			alist.add(rs.getString(2));
		}
		return alist;
    }*/

	public String blockScheduledMail(String blockSchedule, String schedulerDate) throws Exception{

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String scheduleRemark = "rescheduledMail";
		String msg="";
		try {		
			conn = conmanager.getConnection();

			Statement st = conn.createStatement();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			java.sql.Date schedulerDt;
			java.util.Date fd = new java.util.Date();
			// schedulerDate = "03/04/2013";
			if(schedulerDate.equals(""))				
			{
				fd = new Date();
				schedulerDate = format.format(fd.getTime());
				schedulerDt=new java.sql.Date(fd.getTime());
			}
			else
			{				
				fd = format.parse(schedulerDate);
				schedulerDate = format.format(fd.getTime());
				schedulerDt=new java.sql.Date(fd.getTime());
			}
			// String FromDatetxt = schedulerDate;
			// schedulerDate = convertDateFormat(schedulerDate, "dd/MM/yyyy","dd-MMM-yyyy");
			// fd = format.parse(FromDatetxt);

			// java.sql.Date fromdate = new java.sql.Date(fd.getTime());
			String sqlQuery1 = AdminSQLC.CHECK_SEND_MAIL;
			prepStmt = conn.prepareStatement(sqlQuery1);

			prepStmt.setDate(1, schedulerDt);

			int rcnt=0;
			String blockMailStatus="";
			rs = prepStmt.executeQuery();
			if (rs.next())
			{
				rcnt=rs.getInt("cnt");	
				blockMailStatus=rs.getString("MAIL_TYPE");
				if(blockMailStatus.equalsIgnoreCase("scheduledMail"))
					msg="Mail already sent...it cannot be blocked/unblocked";
				else if(blockMailStatus.equalsIgnoreCase("blockedScheduledMail"))
					msg="Scheduled mail has been already blocked...";
				else if(blockMailStatus.equalsIgnoreCase("rescheduledMail"))
					msg="Mail has been already rescheduled and sent...";
			}

			if(!blockSchedule.equals("")){	

				if(rcnt==0){				
					String sqlQuery = AdminSQLC.BLOCK_MAIL;
					st.executeUpdate(sqlQuery);
					msg="Scheduled mail has been blocked...";
				}else{

				}
			}			
			else {				

				if(blockMailStatus.equalsIgnoreCase("blockedScheduledMail"))
				{
					String sqlQuery = AdminSQLC.UNBLOCK_MAIL;
					ps = conn.prepareStatement(sqlQuery);
					ps.setDate(1, schedulerDt);
					ps.executeUpdate();					
					CallScheduler callScheduler = new CallScheduler();					
					callScheduler.callSchedule(scheduleRemark,schedulerDate);

					msg="Blocked mail has been sent...";
				}
				else
					msg="No mail to be blocked/unblocked";
			}
			st.close();
			conn.commit();
			conn.close();

		} catch (Exception e) {			
			e.printStackTrace();
		}finally{
			try {
				if (ps != null) {
					ps.close();
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null)
					rs.close();
				if (conn != null) {
					conn.close();				

					conmanager.closeConnection();					
				}
			} catch (Exception e) {
				ps = null;
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();

					conmanager.closeConnection();					
				}
			}
		}
		return msg;

	}
	public byte[] imageDetailsDisplay() throws Exception
	{
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		//ArrayList alist = new ArrayList();

		PreparedStatement ps = null;
		ResultSet rs = null;
		Blob image;
		//String image = "";
		byte[ ] imgData = null ; 

		String sqlQuery = AdminSQLC.GET_IMAGE_DETAIL_DISPLAY;
		ps = conn.prepareStatement(sqlQuery);

		rs = ps.executeQuery();

		while (rs.next()){
			//alist.add(rs.getString(1));
			image = rs.getBlob(1);
			imgData = image.getBytes(1,(int)image.length());
			//alist.add(image.getBytes(1,(int)image.length())) ;
			//alist.add(imgData);

		}
		return imgData;
	}

	public String getImageDesc() throws Exception
	{
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String imageDesc = "";

		String sqlQuery = AdminSQLC.GET_IMAGEDESC_DETAIL_DISPLAY;
		ps = conn.prepareStatement(sqlQuery);
		try{
			rs = ps.executeQuery();

			while (rs.next()){

				imageDesc= rs.getString(1)==null?"Wind World (India) Ltd":rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (ps != null) {
					ps.close();
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
				ps = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			}
		}
		return imageDesc;
	}

	public List getLoginDetails()
	{

		List list1 = new ArrayList();
		try{
			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = conmanager.getConnection();
			PreparedStatement prepStmt = null;

			ResultSet rs = null;
			int size1 = 0;

			String query ="SELECT A.*,B.S_ROLE_NAME FROM TBL_LOGIN_MASTER A,TBL_ROLE B WHERE B.S_ROLE_ID=A.S_ROLE_ID ORDER BY A.S_USER_ID";
			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Vector<String> tranList1 = new Vector<String>();
				tranList1.add((String)rs.getObject("S_LOGIN_MASTER_ID"));	
				tranList1.add((String)rs.getObject("S_USER_ID"));
				tranList1.add((String)rs.getObject("S_ROLE_NAME"));
				tranList1.add((String)rs.getObject("S_LOGIN_TYPE"));
				tranList1.add((String)rs.getObject("S_LOGIN_DESCRIPTION"));
				// System.out.println(tranList1.toString());
				list1.add(size1,tranList1);
				size1++;

			}
			prepStmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list1;
	}
	public List findSubstationMaster(String areaid)
	{

		List list1 = new ArrayList();
		try{
			JDBCUtils conmanager = new JDBCUtils();
			Connection conn = conmanager.getConnection();
			PreparedStatement prepStmt = null;

			ResultSet rs = null;
			int size1 = 0;
			String query = "";
			if(areaid.equalsIgnoreCase("")||areaid==null)
			{
				query ="SELECT TBL_SUBSTATION_MASTER.*, (SELECT COUNT(S_FEEDER_ID) FROM TBL_FEEDER_MASTER WHERE S_SUBSTATION_ID=TBL_SUBSTATION_MASTER.S_SUBSTATION_ID) AS TOTAL_FEEDER " +
						"FROM TBL_SUBSTATION_MASTER ORDER BY TBL_SUBSTATION_MASTER.S_SUBSTATION_DESC";
			}
			else{
				query ="SELECT TBL_SUBSTATION_MASTER.*, (SELECT COUNT(S_FEEDER_ID) FROM TBL_FEEDER_MASTER WHERE S_SUBSTATION_ID=TBL_SUBSTATION_MASTER.S_SUBSTATION_ID) AS TOTAL_FEEDER " +
						"FROM TBL_SUBSTATION_MASTER WHERE S_AREA_ID = '"+areaid+"' ORDER BY TBL_SUBSTATION_MASTER.S_SUBSTATION_DESC";
			}
			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while (rs.next())
			{
				Vector<String> tranList1 = new Vector<String>();
				tranList1.add(rs.getObject("S_SUBSTATION_ID") == null ? "." : rs.getObject("S_SUBSTATION_ID").toString());	
				tranList1.add(rs.getObject("S_SUBSTATION_DESC") == null ? "." : rs.getObject("S_SUBSTATION_DESC").toString());
				tranList1.add(rs.getObject("TOTAL_FEEDER") == null ? "." : rs.getObject("TOTAL_FEEDER").toString());
				tranList1.add(rs.getObject("S_SUBSTATION_OF") == null ? "." : rs.getObject("S_SUBSTATION_OF").toString());
				tranList1.add(rs.getObject("S_SUBTATION_CAP") == null ? "." : rs.getObject("S_SUBTATION_CAP").toString());
				tranList1.add(rs.getObject("S_SUBSTATION_MVA") == null ? "." : rs.getObject("S_SUBSTATION_MVA").toString());
				tranList1.add(rs.getObject("S_SUBSTATION_HV") == null ? "." : rs.getObject("S_SUBSTATION_HV").toString());
				tranList1.add(rs.getObject("S_SUBSTATION_LV") == null ? "." : rs.getObject("S_SUBSTATION_LV").toString());
				tranList1.add(rs.getObject("N_TTL_TRANSFORMER") == null ? "." : rs.getObject("N_TTL_TRANSFORMER").toString());
				// System.out.println(tranList1.toString());
				list1.add(size1,tranList1);
				size1++;

			}
			prepStmt.close();
			rs.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return list1;
	}

	public List getAuthDetail(String roleid) throws Exception {
		// public List searchempbyfilter(DynaBean dynaBean) throws Exception {
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();


		PreparedStatement prepStmt = null;
		//PreparedStatement ps = null;
		//PreparedStatement ps1 = null;
		//ResultSet rswec = null;
		//ResultSet res = null;
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery = "";


		if(roleid.equals("ALL"))
		{
			sqlQuery = AdminSQLC.GET_ALL_AUTH_DETAIL;
			prepStmt = conn.prepareStatement(sqlQuery);

		}

		else
		{
			sqlQuery = AdminSQLC.GET_ALL_AUTH_DETAIL_BY_ROLE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1,roleid);    		

		}
		try {
			rs = prepStmt.executeQuery();
			int i=0;
			while (rs.next()) {

				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_USER_ID"));
				tranVector.add(rs.getObject("S_PASSWORD"));
				tranVector.add(rs.getObject("S_LOGIN_TYPE"));
				tranVector.add(rs.getObject("S_LOGIN_DESCRIPTION"));
				tranVector.add(rs.getObject("S_ROLE_NAME"));

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
	}

	public String getMsg(String msgid) throws Exception {
		JDBCUtils conmanager = new JDBCUtils();
		Connection con = conmanager.getConnection();
		PreparedStatement prepStmt = null;

		ResultSet rs = null;

		String msg="";




		String sqlQuery = AdminSQLC.GET_MESSAGE_DETAIL;
		prepStmt = con.prepareStatement(sqlQuery);
		prepStmt.setObject(1, msgid);

		try {

			rs = prepStmt.executeQuery();

			if (rs.next()) {
				msg =rs.getString("S_MESSAGE_DESCRIPTION");





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
		return msg;
	}

	public List getLoginDetail(String fdt,String ldt) throws Exception {
		JDBCUtils conmanager = new JDBCUtils();
		Connection con = conmanager.getConnection();
		PreparedStatement prepStmt = null;

		ResultSet rs = null;

		List tranList = new ArrayList();


		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date ffd = format.parse(fdt);
		java.sql.Date pdt1 = new java.sql.Date(ffd.getTime());

		java.util.Date ffd1 = format.parse(ldt);
		java.sql.Date pdt2 = new java.sql.Date(ffd1.getTime());

		String sqlQuery = AdminSQLC.GET_LoginDetail;
		prepStmt = con.prepareStatement(sqlQuery);
		prepStmt.setObject(1, pdt1);
		prepStmt.setObject(2, pdt2);
		try {

			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next()) {
				Vector tranVector = new Vector();

				tranVector.add(rs.getString("s_user_id"));
				tranVector.add(rs.getString("s_login_description"));
				tranVector.add(rs.getString("LoginTime"));
				tranVector.add(rs.getString("S_IP_ADDRESS"));
				tranVector.add(rs.getString("S_HOST"));

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

	public String addCustomer(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String custname = dynaBean.getProperty("CustNametxt").toString();
			custname=custname.toUpperCase();
			custname.replaceAll("&","AND");
			String custcode = dynaBean.getProperty("CustCodetxt").toString();
			String custcontact = dynaBean.getProperty("CustContacttxt").toString();
			String phone = dynaBean.getProperty("CustPhonetxt").toString();	
			String cellno = dynaBean.getProperty("CustCelltxt") == null ? "0" : dynaBean.getProperty("CustCelltxt").toString();
			String fax = dynaBean.getProperty("CustFaxtxt") == null ? "0" : dynaBean.getProperty("CustFaxtxt").toString();
			String active = dynaBean.getProperty("CustActivetxt") == null ? "0" : dynaBean.getProperty("CustActivetxt").toString();
			String email = dynaBean.getProperty("CustEmailtxt").toString();	

			String Marktpertxt = dynaBean.getProperty("Marktpertxt") == null ? " " :dynaBean.getProperty("Marktpertxt").toString();
			Marktpertxt=Marktpertxt.toUpperCase();
			Marktpertxt.replaceAll("&","AND");
			String custid="";
			if (dynaBean.getProperty("CustIdtxt") == null || dynaBean.getProperty("CustIdtxt").equals(""))

			{
				custid = CodeGenerate.NewCodeGenerate("TBL_CUSTOMER_MASTER");
				sqlQuery = AdminSQLC.CHECK_CUSTOMER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,custname);
				prepStmt.setObject(2,custcode);
				prepStmt.setObject(3,custid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Customer or Customer Code already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_CUSTOMER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, custid);
					ps.setObject(2, custname);
					ps.setObject(3, custcode);
					ps.setObject(4, custcontact);
					ps.setObject(5, phone);
					ps.setObject(6, cellno);
					ps.setObject(7, fax);
					ps.setObject(8, email);

					ps.setObject(9, active);
					ps.setObject(10, UserId);
					ps.setObject(11, UserId);
					ps.setObject(12, Marktpertxt);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Customer Data Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				custid = dynaBean.getProperty("CustIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_CUSTOMER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,custname);
				prepStmt.setObject(2,custcode);
				prepStmt.setObject(3,custid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Customer or Customer Code already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_CUSTOMER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, custname);
					ps.setObject(2, custcode);
					ps.setObject(3, custcontact);
					ps.setObject(4, phone);
					ps.setObject(5, cellno);
					ps.setObject(6, fax);
					ps.setObject(7, email);
					ps.setObject(8, active);
					ps.setObject(9, UserId);
					ps.setObject(10, Marktpertxt);
					ps.setObject(11, custid);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Customer Data Updated Successfully!</font>";
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

	public String addEBMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String ebshort = dynaBean.getProperty("EBShorttxt").toString();
			String ebdesc = dynaBean.getProperty("EBDesctxt").toString();
			String FDDesctxt = dynaBean.getProperty("FDDesc") == null ? "" : dynaBean.getProperty("FDDesc").toString();
			String Customeridtxt = dynaBean.getProperty("Customeridtxt").toString();
			//String ebtype = dynaBean.getProperty("EBTypetxt").toString();
			//String ebstype = dynaBean.getProperty("EBSubTypetxt").toString();	
			//String ebmfactor = dynaBean.getProperty("EBMFactortxt") == null ? "1" : dynaBean.getProperty("EBMFactortxt").toString();	
			String ebstatus = dynaBean.getProperty("EBStatustxt") == null ? "1" : "2";
			//String ebcapacity = dynaBean.getProperty("EBCapacitytxt").toString();
			String ebsite = dynaBean.getProperty("EBSitetxt").toString();	
			String ebid="";
			if (dynaBean.getProperty("EBId") == null || dynaBean.getProperty("EBId").equals(""))
			{
				ebid = CodeGenerate.NewCodeGenerate("TBL_EB_MASTER");
				if(FDDesctxt.equals("")){
					sqlQuery = AdminSQLC.CHECK_EB_MASTER_WITHOUT_FEDER;

					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,ebshort);
					prepStmt.setObject(2,ebdesc);
					prepStmt.setObject(3,ebid);
				}
				else {
					sqlQuery = AdminSQLC.CHECK_EB_MASTER;

					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,ebshort);
					prepStmt.setObject(2,ebdesc);
					prepStmt.setObject(3,ebid);
					prepStmt.setObject(4,FDDesctxt);
				}

				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>EB Location already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_EB;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, ebid);
					ps.setObject(2, ebshort);
					ps.setObject(3, ebdesc);
					ps.setObject(4, ebstatus);
					ps.setObject(5, ebsite);
					ps.setObject(6, UserId);
					ps.setObject(7, UserId);
					ps.setObject(8, FDDesctxt);
					ps.setObject(9, Customeridtxt);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>EB Data Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				ebid = dynaBean.getProperty("EBId").toString();
				sqlQuery = AdminSQLC.UPDATE_CHECK_EB_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,ebshort);
				prepStmt.setObject(2,ebdesc);
				prepStmt.setObject(3,ebid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>EB Location already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_EB;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, ebshort);
					ps.setObject(2, ebdesc);
					ps.setObject(3, ebstatus);
					ps.setObject(4, ebsite);
					ps.setObject(5, UserId);
					ps.setObject(6, FDDesctxt);
					ps.setObject(7, Customeridtxt);
					ps.setObject(8, ebid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>EB Data Updated Successfully!</font>";
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

	public String addEBMFactor(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
			String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();
			String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();
			String EBTypetxt = dynaBean.getProperty("EBTypetxt").toString();
			String EBSubTypetxt = dynaBean.getProperty("EBSubTypetxt").toString();	
			String EBMFactortxt = dynaBean.getProperty("EBMFactortxt") == null ? "1" : dynaBean.getProperty("EBMFactortxt").toString();	
			String EBCapacitytxt = dynaBean.getProperty("EBCapacitytxt") == null ? "0" : dynaBean.getProperty("EBCapacitytxt").toString();
			String EBIdtxt = dynaBean.getProperty("EBIdtxt").toString();	
			String FacId="";
			java.util.Date fd = new java.util.Date();
			fd = format.parse(FromDatetxt);
			java.sql.Date fromdate = new java.sql.Date(fd.getTime());
			java.util.Date td = new java.util.Date();
			td = format.parse(ToDatetxt);
			java.sql.Date todate = new java.sql.Date(td.getTime());
			if (dynaBean.getProperty("FacId") == null || dynaBean.getProperty("FacId").equals(""))
			{
				FacId = CodeGenerate.NewCodeGenerate("TBL_EB_MFACTOR");
				sqlQuery = AdminSQLC.CHECK_EB_MFACTOR;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,EBIdtxt);
				prepStmt.setObject(2,fromdate);
				prepStmt.setObject(3,todate);
				prepStmt.setObject(4,FacId);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>EB Multiplication Factor for the period already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_EB_MFACTOR;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, FacId);
					ps.setObject(2, EBIdtxt);
					ps.setObject(3, fromdate);
					ps.setObject(4, todate);
					ps.setObject(5, UserId);
					ps.setObject(6, UserId);
					ps.setObject(7, EBMFactortxt);
					ps.setObject(8, EBTypetxt);
					ps.setObject(9, EBSubTypetxt);
					ps.setObject(10, EBCapacitytxt);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>EB Multiplication Factor Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				FacId = dynaBean.getProperty("FacId").toString();
				sqlQuery = AdminSQLC.CHECK_EB_MFACTOR;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,EBIdtxt);
				prepStmt.setObject(2,fromdate);
				prepStmt.setObject(3,todate);
				prepStmt.setObject(4,FacId);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>EB Multiplication Factor for the period already exists!</font>";				 				
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_EB_MFACTOR;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, fromdate);
					ps.setObject(2, todate);
					ps.setObject(3, EBTypetxt);
					ps.setObject(4, EBSubTypetxt);
					ps.setObject(5, EBMFactortxt);
					ps.setObject(6, EBCapacitytxt);
					ps.setObject(7, UserId);
					ps.setObject(8, FacId);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>EB Multiplication Factor Updated Successfully!</font>";
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

	public String addFDMFactor(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
			String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();
			String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();
			String FDTypetxt = dynaBean.getProperty("FDTypetxt").toString();
			String FDSubTypetxt = dynaBean.getProperty("FDSubTypetxt").toString();	
			String FDMFactortxt = dynaBean.getProperty("FDMFactortxt") == null ? "1" : dynaBean.getProperty("FDMFactortxt").toString();	
			String FDCapacitytxt = dynaBean.getProperty("FDCapacitytxt") == null ? "0" : dynaBean.getProperty("FDCapacitytxt").toString();
			String FDIdtxt = dynaBean.getProperty("FDIdtxt").toString();	
			String FacId="";
			java.util.Date fd = new java.util.Date();
			fd = format.parse(FromDatetxt);
			java.sql.Date fromdate = new java.sql.Date(fd.getTime());
			java.util.Date td = new java.util.Date();
			td = format.parse(ToDatetxt);
			java.sql.Date todate = new java.sql.Date(td.getTime());
			if (dynaBean.getProperty("FacId") == null || dynaBean.getProperty("FacId").equals(""))
			{
				FacId = CodeGenerate.NewCodeGenerate("TBL_FEDER_MFACTOR");
				sqlQuery = AdminSQLC.CHECK_FD_MFACTOR;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, FDIdtxt);
				prepStmt.setObject(2,fromdate);
				prepStmt.setObject(3,todate);
				prepStmt.setObject(4,FacId);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feder Multiplication Factor for the period already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_FD_MFACTOR;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, FacId);
					ps.setObject(2, FDIdtxt);
					ps.setObject(3, fromdate);
					ps.setObject(4, todate);
					ps.setObject(5, UserId);
					ps.setObject(6, UserId);
					ps.setObject(7, FDMFactortxt);
					ps.setObject(8, FDTypetxt);
					ps.setObject(9, FDSubTypetxt);
					ps.setObject(10, FDCapacitytxt);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Feder Multiplication Factor Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				FacId = dynaBean.getProperty("FacId").toString();
				sqlQuery = AdminSQLC.CHECK_FD_MFACTOR;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, FDIdtxt);
				prepStmt.setObject(2,fromdate);
				prepStmt.setObject(3,todate);
				prepStmt.setObject(4,FacId);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feder Multiplication Factor for the period already exists!</font>";				 				
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_FD_MFACTOR;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, fromdate);
					ps.setObject(2, todate);
					ps.setObject(3, FDTypetxt);
					ps.setObject(4, FDSubTypetxt);
					ps.setObject(5, FDMFactortxt);
					ps.setObject(6, FDCapacitytxt);
					ps.setObject(7, UserId);
					ps.setObject(8, FacId);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Feder Multiplication Factor Updated Successfully!</font>";
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

	public String addFDMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String FDShorttxt = dynaBean.getProperty("FDShorttxt").toString();
			String FDDesctxt = dynaBean.getProperty("FDDesctxt").toString();
			String FDStatustxt = dynaBean.getProperty("FDStatustxt") == null ? "1" : "2";
			String FDSitetxt = dynaBean.getProperty("FDSitetxt").toString();	
			String fdid="";
			if (dynaBean.getProperty("FDIdtxt") == null || dynaBean.getProperty("FDIdtxt").equals(""))
			{
				fdid = CodeGenerate.NewCodeGenerate("TBL_FEDER_MASTER");
				sqlQuery = AdminSQLC.CHECK_FEDER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,FDDesctxt);
				prepStmt.setObject(2,fdid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feder already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_FEDER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, fdid);
					ps.setObject(2, FDShorttxt);
					ps.setObject(3, FDDesctxt);
					ps.setObject(4, FDStatustxt);
					ps.setObject(5, FDSitetxt);
					ps.setObject(6, UserId);
					ps.setObject(7, UserId);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Feder Data Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				fdid = dynaBean.getProperty("FDIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_FEDER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,FDDesctxt);
				prepStmt.setObject(2,fdid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feder already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_FEDER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, FDShorttxt);
					ps.setObject(2, FDDesctxt);
					ps.setObject(3, FDStatustxt);
					ps.setObject(4, FDSitetxt);
					ps.setObject(5, UserId);
					ps.setObject(6, fdid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Feder Updated Successfully!</font>";
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

	public String addNewSite(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String sitename = dynaBean.getProperty("SiteNametxt").toString();
			String sitecode = dynaBean.getProperty("SiteCodetxt").toString();
			String SiteInchargetxt = dynaBean.getProperty("SiteInchargetxt").toString();
			String SiteAddresstxt = dynaBean.getProperty("SiteAddresstxt").toString();
			sitename=sitename.toUpperCase();
			sitecode=sitecode.toUpperCase();
			String stateid = dynaBean.getProperty("Statetxt").toString();
			String areaid = dynaBean.getProperty("Areatxt").toString();
			String siteid="";
			if (dynaBean.getProperty("SiteIdtxt") == null || dynaBean.getProperty("SiteIdtxt").equals(""))
			{
				siteid = CodeGenerate.NewCodeGenerate("TBL_SITE_MASTER");
				sqlQuery = AdminSQLC.CHECK_SITE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,sitename);
				prepStmt.setObject(2,stateid);
				prepStmt.setObject(3,siteid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Site already to this State!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_SITE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, siteid);
					ps.setObject(2, stateid);
					ps.setObject(3, sitename);
					ps.setObject(4, sitecode);
					ps.setObject(5, UserId);
					ps.setObject(6, UserId);
					ps.setObject(7, SiteInchargetxt);
					ps.setObject(8, SiteAddresstxt);
					ps.setObject(9, areaid);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Site Created Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				siteid = dynaBean.getProperty("SiteIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_SITE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,sitename);
				prepStmt.setObject(2,stateid);
				prepStmt.setObject(3,siteid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Site already exists For This state!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_SITE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, sitename);
					ps.setObject(2, sitecode);
					ps.setObject(3, stateid);					
					ps.setObject(4, UserId);
					ps.setObject(5, SiteInchargetxt);					
					ps.setObject(6, SiteAddresstxt);
					ps.setObject(7, areaid);
					ps.setObject(8, siteid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Site Updated Successfully!</font>";
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

	public String addNewFeeder(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String feedername = dynaBean.getProperty("SiteNametxt").toString();    		
			feedername=feedername.toUpperCase();    		
			String substationid = dynaBean.getProperty("Statetxt").toString();    		
			String feederid="";
			if (dynaBean.getProperty("SiteIdtxt") == null || dynaBean.getProperty("SiteIdtxt").equals(""))
			{
				feederid = CodeGenerate.NewCodeGenerate("TBL_FEEDER_MASTER");
				sqlQuery = AdminSQLC.CHECK_FEEDER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,feedername);
				prepStmt.setObject(2,substationid);
				prepStmt.setObject(3,feederid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feeder already to this Substation!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_FEEDER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, feederid);					
					ps.setObject(2, feedername);					
					ps.setObject(3, UserId);
					ps.setObject(4, UserId);
					ps.setObject(5, substationid);

					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Feeder Created Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				feederid = dynaBean.getProperty("SiteIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_FEEDER_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,feedername);
				prepStmt.setObject(2,substationid);
				prepStmt.setObject(3,feederid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Feeder already exists For This Substation!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_FEEDER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, feedername);	    			
					ps.setObject(2, substationid);					
					ps.setObject(3, UserId);	    			
					ps.setObject(4, feederid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Feeder Updated Successfully!</font>";
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

	public String addNewArea(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String areaname = dynaBean.getProperty("AreaNametxt").toString();
			String areacode = dynaBean.getProperty("AreaCodetxt").toString();
			String AreaInchargetxt = dynaBean.getProperty("AreaInchargetxt").toString();    		
			areaname=areaname.toUpperCase();
			areacode=areacode.toUpperCase();
			String stateid = dynaBean.getProperty("Statetxt").toString();
			String areaid="";
			if (dynaBean.getProperty("AreaIdtxt") == null || dynaBean.getProperty("AreaIdtxt").equals(""))
			{
				areaid = CodeGenerate.NewCodeGenerate("TBL_AREA_MASTER");
				sqlQuery = AdminSQLC.CHECK_AREA_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,areaname);
				prepStmt.setObject(2,stateid);
				prepStmt.setObject(3,areaid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Site already to this State!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_AREA;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, areaid);
					ps.setObject(2, stateid);
					ps.setObject(3, areaname);
					ps.setObject(4, areacode);
					ps.setObject(5, UserId);
					ps.setObject(6, UserId);
					ps.setObject(7, AreaInchargetxt);					
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Area Created Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				areaid = dynaBean.getProperty("AreaIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_AREA_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,areaname);
				prepStmt.setObject(2,stateid);
				prepStmt.setObject(3,areaid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Area already exists For This state!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_AREA;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, areaname);
					ps.setObject(2, areacode);
					ps.setObject(3, stateid);					
					ps.setObject(4, UserId);
					ps.setObject(5, AreaInchargetxt);	    			
					ps.setObject(6, areaid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Area Updated Successfully!</font>";
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
	public String addNewSubstation(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String areaid = dynaBean.getProperty("Statetxt").toString();
			String substationname = dynaBean.getProperty("EBSitetxt").toString();
			String substationowner = dynaBean.getProperty("Substationownertxt").toString(); 
			String substationcap = dynaBean.getProperty("Substationcaptxt").toString();
			String substationmva = dynaBean.getProperty("Substationmvatxt").toString();
			String substationvolh = dynaBean.getProperty("Substationvolhtxt").toString();
			String substationvoll = dynaBean.getProperty("Substationvolltxt").toString();
			String totalTransformer = dynaBean.getProperty("Transformertxt").toString();
			String transformerRemarks = dynaBean.getProperty("Remarkstxt").toString();

			substationname=substationname.toUpperCase();   
			substationowner=substationowner.toUpperCase();

			String substationid="";
			if (dynaBean.getProperty("EBId") == null || dynaBean.getProperty("EBId").equals(""))
			{    			
				sqlQuery = AdminSQLC.CHECK_INSERT_SUBSTATION_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,substationname);
				prepStmt.setObject(2,areaid);
				// prepStmt.setObject(3,substationid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Substation already exixts to this Area!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_SUBSTATION;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, substationname);
					ps.setObject(2, substationowner);
					ps.setObject(3, substationcap);
					ps.setObject(4, substationmva);
					ps.setObject(5, substationvolh);
					ps.setObject(6, substationvoll);
					ps.setObject(7, UserId);
					ps.setObject(8, UserId);
					ps.setObject(9, areaid);
					ps.setObject(10, totalTransformer);
					ps.setObject(11, transformerRemarks);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Substation Created Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				substationid = dynaBean.getProperty("EBId").toString();
				sqlQuery = AdminSQLC.CHECK_SUBSTATION_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,substationname);
				prepStmt.setObject(2,areaid);
				prepStmt.setObject(3,substationid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Substation already exists For This Area!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_SUBSTATION;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, substationname);
					ps.setObject(2, substationowner);
					ps.setObject(3, substationcap);					
					ps.setObject(4, substationmva);
					ps.setObject(5, substationvolh);	    			
					ps.setObject(6, substationvoll);	
					ps.setObject(7, UserId);
					ps.setObject(8, areaid);
					ps.setObject(9, totalTransformer);	
					ps.setObject(10, substationid);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Substation Updated Successfully!</font>";
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

	public String addMeterPiont(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{    		
			String mpdesc = dynaBean.getProperty("MPDesctxt").toString();
			String mptype = dynaBean.getProperty("MPTypetxt").toString();
			String mpshow = dynaBean.getProperty("MPShowtxt").toString();	
			String mpunit = dynaBean.getProperty("MPUnittxt").toString();	
			String ReadTypetxt = dynaBean.getProperty("ReadTypetxt").toString();
			String Seqnotxt = dynaBean.getProperty("Seqnotxt").toString();	
			String mpid="";
			int Statustxt = 1;
			if(dynaBean.getProperty("Statustxt") == null){
				Statustxt = 1;
			}
			else {
				Statustxt = 2;
			}
			int Cumtxt = 1;
			if(dynaBean.getProperty("Cumtxt") == null){
				Cumtxt = 2;
			}
			else {
				Cumtxt = 1;
			}
			if (dynaBean.getProperty("MPIdtxt") == null || dynaBean.getProperty("MPIdtxt").equals(""))
			{
				mpid = CodeGenerate.NewCodeGenerate("TBL_MP_MASTER");
				sqlQuery = AdminSQLC.CHECK_MP_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,mpdesc);
				prepStmt.setObject(2,mpid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Measure Point already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_MP;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, mpid);
					ps.setObject(2, mpdesc);
					ps.setObject(3, mptype);
					ps.setObject(4, mpshow);
					ps.setObject(5, mpunit);
					ps.setObject(6, UserId);
					ps.setObject(7, UserId);
					ps.setObject(8, Seqnotxt);
					ps.setObject(9, Statustxt);
					ps.setObject(10, Cumtxt);
					ps.setObject(11, ReadTypetxt);					
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Measure Point Added Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				mpid = dynaBean.getProperty("MPIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_MP_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,mpdesc);
				prepStmt.setObject(2,mpid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Measure Point already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_MP;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, mpdesc);
					ps.setObject(2, mptype);
					ps.setObject(3, mpshow);
					ps.setObject(4, mpunit);
					ps.setObject(5, UserId);
					ps.setObject(6, Seqnotxt);		
					ps.setObject(7, Statustxt);
					ps.setObject(8, Cumtxt);
					ps.setObject(9, ReadTypetxt);	
					ps.setObject(10, mpid);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Measure Point Data Updated Successfully!</font>";
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

	public String addRemarks(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{    		
			String Remarkstxt = dynaBean.getProperty("Remarkstxt").toString();
			String RemarksTypetxt = dynaBean.getProperty("RemarksTypetxt").toString();
			String WECTypetxt = dynaBean.getProperty("WECTypetxt").toString();
			String ErrorNotxt = dynaBean.getProperty("ErrorNotxt").toString();
			String RemarksIdtxt="";
			if (dynaBean.getProperty("RemarksIdtxt") == null || dynaBean.getProperty("RemarksIdtxt").equals(""))
			{
				RemarksIdtxt = CodeGenerate.NewCodeGenerate("TBL_REMARKS");
				sqlQuery = AdminSQLC.CHECK_REMARKS_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,Remarkstxt);
				prepStmt.setObject(2,RemarksTypetxt);
				prepStmt.setObject(3,RemarksIdtxt);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Remarks already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_REMARKS;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, RemarksIdtxt);
					ps.setObject(2, Remarkstxt);
					ps.setObject(3, RemarksTypetxt);
					ps.setObject(4, UserId);
					ps.setObject(5, UserId);
					ps.setObject(6, WECTypetxt);
					ps.setObject(7, ErrorNotxt);

					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Remarks Added Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				RemarksIdtxt = dynaBean.getProperty("RemarksIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_REMARKS_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,Remarkstxt);
				prepStmt.setObject(2,RemarksTypetxt);
				prepStmt.setObject(3,RemarksIdtxt);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Remarks already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_REMARKS;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, Remarkstxt);
					ps.setObject(2, RemarksTypetxt);
					ps.setObject(3, UserId);
					ps.setObject(4, WECTypetxt);
					ps.setObject(5, ErrorNotxt);
					ps.setObject(6, RemarksIdtxt);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Remarks Updated Successfully!</font>";
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

	public String addSiteRemarks(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{    		
			String siteNameTxt = dynaBean.getProperty("siteNameTxt").toString();
			String siteRemarksTxt = dynaBean.getProperty("siteRemarksTxt").toString();				

			sqlQuery = AdminSQLC.CHECK_SITE_REMARKS_MASTER;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1,siteNameTxt);
			prepStmt.setObject(2,siteRemarksTxt);

			rs = prepStmt.executeQuery();
			if (rs.next())
			{
				msg = "<font class='errormsgtext'>Remarks already exists!</font>";				 				
			}
			else
			{
				sqlQuery = AdminSQLC.UPDATE_SITE_REMARKS;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, UserId);
				ps.setObject(2, siteRemarksTxt);
				ps.setObject(3, siteNameTxt);										

				int iInserteddRows = ps.executeUpdate();
				//conn.commit();
				if (iInserteddRows != 1)
					throw new Exception("DB_UPDATE_ERROR", null);
				ps.close();
				msg = "<font class='sucessmsgtext'>Remarks Added Successfully!</font>";
			}	
			prepStmt.close();
			rs.close();

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

	public String addNewStateIn(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String statename = dynaBean.getProperty("StateNametxt").toString();
			String statecode = dynaBean.getProperty("StateCodetxt").toString();
			statename=statename.toUpperCase();
			String stateid = "";
			if (dynaBean.getProperty("StateIdtxt") == null || dynaBean.getProperty("StateIdtxt").equals("")){
				stateid  = CodeGenerate.NewCodeGenerate("TBL_STATE_MASTER");
				sqlQuery = AdminSQLC.CHECK_STATE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,statename);
				prepStmt.setObject(2,stateid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>State already exists!</font>";
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_STATE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, stateid);
					ps.setObject(2, statename);
					ps.setObject(3, statecode);
					ps.setObject(4, UserId);
					ps.setObject(5, UserId);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>State Added Successfull!</font>";
				}
				prepStmt.close();
				rs.close();
			}else{
				stateid = dynaBean.getProperty("StateIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_STATE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,statename);
				prepStmt.setObject(2,stateid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>State already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_STATE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, statename);
					ps.setObject(2, statecode);					
					ps.setObject(3, UserId);
					ps.setObject(4, stateid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>State Updated Successfully!</font>";
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

	public String addTypeMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String TypeNametxt = dynaBean.getProperty("TypeNametxt").toString();
			String Capacitytxt = dynaBean.getProperty("Capacitytxt").toString();
			TypeNametxt=TypeNametxt.toUpperCase();
			String TypeIdtxt = "";
			if (dynaBean.getProperty("TypeIdtxt") == null || dynaBean.getProperty("TypeIdtxt").equals("")){
				TypeIdtxt  = CodeGenerate.NewCodeGenerate("TBL_WEC_TYPE");
				sqlQuery = AdminSQLC.CHECK_TYPE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,TypeNametxt);
				prepStmt.setObject(2,TypeIdtxt);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>WEC Type already exists!</font>";
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_TYPE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, TypeIdtxt);
					ps.setObject(2, TypeNametxt);
					ps.setObject(3, Capacitytxt);
					ps.setObject(4, UserId);
					ps.setObject(5, UserId);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>WEC Type Added Successfull!</font>";
				}
				prepStmt.close();
				rs.close();
			}else{
				TypeIdtxt = dynaBean.getProperty("TypeIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_TYPE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,TypeNametxt);
				prepStmt.setObject(2,TypeIdtxt);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>WEC Type already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_TYPE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, TypeNametxt);
					ps.setObject(2, Capacitytxt);					
					ps.setObject(3, UserId);
					ps.setObject(4, TypeIdtxt);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>WEC Type Updated Successfully!</font>";
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

	public String addLoginMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{	    	    	
			String loginidtxt = dynaBean.getProperty("loginidtxt").toString().trim();
			String active = dynaBean.getProperty("activetxt").toString().trim();
			loginidtxt=loginidtxt.toUpperCase();
			String remarks="",custid="";
			if(dynaBean.getProperty("Messagetxt")!=null)
			{
				remarks=dynaBean.getProperty("Messagetxt").toString().trim();
				remarks=remarks.toUpperCase();
			}
			if(dynaBean.getProperty("Customeridtxt1")!=null)
			{
				custid=dynaBean.getProperty("Customeridtxt1").toString().trim();

			}
			String Passwordtxt = dynaBean.getProperty("Passwordtxt") == null ? "" : dynaBean.getProperty("Passwordtxt").toString().trim(); 
			String logintypetxt = dynaBean.getProperty("logintypetxt").toString();	    	    	
			String	roletxt = dynaBean.getProperty("roletxt").toString();
			String	desctxt = dynaBean.getProperty("desctxt").toString();	    	
			String loginmasterid = "";	
			if (dynaBean.getProperty("loginmasteridtxt") == null || dynaBean.getProperty("loginmasteridtxt").equals("") ){	
				loginmasterid = CodeGenerate.NewCodeGenerate( "TBL_LOGIN_MASTER");	
				sqlQuery = AdminSQLC.CHECK_LOGINMASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,loginidtxt.trim());	
				prepStmt.setObject(2,loginmasterid);	
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>login code already exists!</font>";						    				
				}
				else
				{ 			
					sqlQuery = AdminSQLC.INSERT_LOGIN_MASTER;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, loginmasterid);
					ps.setObject(2, loginidtxt);
					ps.setObject(3, Passwordtxt.trim().length() == 0 ? loginidtxt : Passwordtxt);
					ps.setObject(4, logintypetxt);
					ps.setObject(5, roletxt);
					ps.setObject(6, desctxt);
					ps.setObject(7, UserId);
					ps.setObject(8, UserId);
					ps.setObject(9, active);
					ps.setObject(10, remarks);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>login Information Added Successfully!</font>";
				}
				rs.close();
				prepStmt.close();
			}else
			{
				loginmasterid = dynaBean.getProperty("loginmasteridtxt").toString();
				sqlQuery = AdminSQLC.CHECK_LOGINMASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,loginidtxt);	
				prepStmt.setObject(2,loginmasterid);	
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>User already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_LOGIN_MASTER;
					ps = conn.prepareStatement(sqlQuery);	    			
					ps.setObject(1, loginidtxt);
					ps.setObject(2, Passwordtxt.trim().length() == 0 ? loginidtxt : Passwordtxt);
					ps.setObject(3, logintypetxt);
					ps.setObject(4, roletxt);
					ps.setObject(5, desctxt);
					ps.setObject(6, UserId);
					ps.setObject(7, active);
					ps.setObject(8, remarks);
					ps.setObject(9, loginmasterid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>login Information Updated Successfully!</font>";
				}
				prepStmt.close();
				rs.close();


			}
			/*	if(!custid.equals("") && logintypetxt.equals("C"))
   			{
   				if(loginmasterid !=null || loginmasterid!="" )
   				{

   					sqlQuery = AdminSQLC.UPDATE_CUSTOMER_MASTER;
   					ps = conn.prepareStatement(sqlQuery);	    			
   					ps.setObject(1, loginmasterid);
   					ps.setObject(2, custid);
   					ps.executeUpdate();
   					//conn.commit();
   					ps.close();
   					System.out.print("Update");
   				}

   			}*/
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

	public String UpdateLoginMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{	    	    	
			String custid = dynaBean.getProperty("CustNametxt").toString().trim();
			String loginmasterid = dynaBean.getProperty("LoginIdtxt").toString().trim();


			if(!custid.equals(""))
			{
				if(loginmasterid !=null || loginmasterid!="" )
				{

					sqlQuery = AdminSQLC.UPDATE_CUSTOMER_MASTER;
					ps = conn.prepareStatement(sqlQuery);	    			
					ps.setObject(1, loginmasterid);
					ps.setObject(2, UserId);
					ps.setObject(3, custid);
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg="Customer Assign To Login Successfully";
				}

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
	public String addNews(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{	    	    	
			String NewsTitletxt = dynaBean.getProperty("NewsTitletxt").toString();
			String NewsDescriptiontxt = dynaBean.getProperty("NewsDescriptiontxt").toString();
			String FromDatetxt = dynaBean.getProperty("FromDatetxt").toString();	    	    	
			String ToDatetxt = dynaBean.getProperty("ToDatetxt").toString();    	
			java.util.Date fdt = format.parse(FromDatetxt);
			java.sql.Date fdate = new java.sql.Date(fdt.getTime());
			java.util.Date tdt = format.parse(ToDatetxt);
			java.sql.Date tdate = new java.sql.Date(tdt.getTime());
			String NewsIdtxt = "";	
			if (dynaBean.getProperty("NewsIdtxt") == null || dynaBean.getProperty("NewsIdtxt").equals("") ){	
				NewsIdtxt = CodeGenerate.NewCodeGenerate( "TBL_NEWS");	

				sqlQuery = AdminSQLC.INSERT_NEWS;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, NewsIdtxt);
				ps.setObject(2, NewsTitletxt);
				ps.setObject(3, NewsDescriptiontxt);
				ps.setObject(4, fdate);
				ps.setObject(5, tdate);
				ps.setObject(6, UserId);
				ps.setObject(7, UserId);
				int iInserteddRows = ps.executeUpdate();
				//conn.commit();
				if (iInserteddRows != 1)
					throw new Exception("DB_UPDATE_ERROR", null);
				ps.close();
				msg = "<font class='sucessmsgtext'>News Added Successfully!</font>";

			}else
			{
				NewsIdtxt = dynaBean.getProperty("NewsIdtxt").toString();

				sqlQuery = AdminSQLC.UPDATE_NEWS;
				ps = conn.prepareStatement(sqlQuery);	    			
				ps.setObject(1, NewsTitletxt);
				ps.setObject(2, NewsDescriptiontxt);
				ps.setObject(3, fdate);
				ps.setObject(4, tdate);
				ps.setObject(5, UserId);
				ps.setObject(6, NewsIdtxt);
				ps.executeUpdate();
				//conn.commit();
				ps.close();
				msg = "<font class='sucessmsgtext'>News Updated Successfully!</font>";

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

	public String AddWecMaster(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try{
			String wecsdesc = dynaBean.getProperty("WECShorttxt").toString();
			String custid = dynaBean.getProperty("Customeridtxt").toString();			
			String wectype = dynaBean.getProperty("WECTypetxt").toString();
			String weccapacity = dynaBean.getProperty("Weccapacitytxt").toString();
			String locationtxt = dynaBean.getProperty("locationtxt").toString();
			String feederid = dynaBean.getProperty("Feedertxt") == null ? "0" : dynaBean.getProperty("Feedertxt").toString();
			String ebid = dynaBean.getProperty("EBIdtxt").toString();
			String Datetxt = dynaBean.getProperty("Datetxt").toString();
			String wecstatus = dynaBean.getProperty("WECStatustxt") == null ? "1" : "2";
			String GenComtxt = dynaBean.getProperty("GenComtxt") == null ? "0" : dynaBean.getProperty("GenComtxt").toString();
			String MacAvatxt = dynaBean.getProperty("MacAvatxt") == null ? "0" : dynaBean.getProperty("MacAvatxt").toString();
			String ExtAvatxt = dynaBean.getProperty("ExtAvatxt") == null ? "0" : dynaBean.getProperty("ExtAvatxt").toString();
			String IntAvatxt = dynaBean.getProperty("IntAvatxt") == null ? "0" : dynaBean.getProperty("IntAvatxt").toString();
			String FromDatetxt = dynaBean.getProperty("StartDatetxt").toString();
			String ToDatetxt = dynaBean.getProperty("EndDatetxt").toString();
			String astatus = dynaBean.getProperty("WECAStatustxt") == null ? "1" : "0";
			String formula = dynaBean.getProperty("WECFormulatxt").toString();
			String costPerUnit = dynaBean.getProperty("CostPerUnit") == null ? "1" : dynaBean.getProperty("CostPerUnit").toString();
			String wecSrNo = dynaBean.getProperty("WECSrNoTxt")==null?"0":dynaBean.getProperty("WECSrNoTxt").toString();
			String wecGuaranteeType = dynaBean.getProperty("WECGuaranteeTypeTxt").toString();
			String customerTypeTxt = dynaBean.getProperty("CustomerTypeTxt").toString();
			String scadaDataTxt = dynaBean.getProperty("DataScadatxt").toString();
			java.util.Date fdt = new java.util.Date();
			java.sql.Date fdate = null;
			if (!FromDatetxt.equals(""))
			{
				fdt = format.parse(FromDatetxt);
				fdate = new java.sql.Date(fdt.getTime());
			}
			java.util.Date tdt = new java.util.Date();
			java.sql.Date tdate = null;
			if (!ToDatetxt.equals(""))
			{
				tdt = format.parse(ToDatetxt);
				tdate = new java.sql.Date(tdt.getTime());
			}
			java.util.Date dt = new java.util.Date();
			dt = format.parse(Datetxt);
			java.sql.Date date = new java.sql.Date(dt.getTime());
			String wecmfactor =  dynaBean.getProperty("WECMFactortxt") == null ? "1" : dynaBean.getProperty("WECMFactortxt").toString();

			String wecid="";
			if (dynaBean.getProperty("wecidtxt") == null || dynaBean.getProperty("wecidtxt").equals("")){   		

				wecid = CodeGenerate.NewCodeGenerate("TBL_WEC_MASTER");
				sqlQuery = AdminSQLC.CHECK_WEC_MASTER_I;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,wecsdesc);
				prepStmt.setObject(2,ebid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>WEC Location already exists!</font>";				 				
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_WEC;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, wecid);
					ps.setObject(2, wecsdesc);
					ps.setObject(3, custid);
					ps.setObject(4, ebid);
					ps.setObject(5, locationtxt);	
					ps.setObject(6, wectype);
					ps.setObject(7, wecmfactor);				
					ps.setObject(8, weccapacity);
					ps.setObject(9, UserId);
					ps.setObject(10, UserId);
					ps.setDate(11, date);
					ps.setObject(12, wecstatus);
					ps.setObject(13, GenComtxt);
					ps.setObject(14, MacAvatxt);
					ps.setObject(15, ExtAvatxt);
					ps.setObject(16, IntAvatxt);
					ps.setObject(17, fdate);
					ps.setObject(18, tdate);
					ps.setObject(19, formula);
					ps.setObject(20, astatus);
					ps.setObject(21, costPerUnit);
					ps.setObject(22, wecSrNo);
					ps.setObject(23, wecGuaranteeType);
					ps.setObject(24, customerTypeTxt);
					ps.setObject(25, scadaDataTxt);
					ps.setObject(26, feederid);
					ps.setObject(27, scadaDataTxt);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>WEC Data Inserted Successfully!</font>";
				}	
				prepStmt.close();
				rs.close();
			}
			else{
				wecid = dynaBean.getProperty("wecidtxt").toString();
				sqlQuery = AdminSQLC.CHECK_WEC_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,wecsdesc);
				prepStmt.setObject(2,wecid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>WEC Location already exists!</font>";
				}
				else
				{		
					sqlQuery = AdminSQLC.UPDATE_WEC;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, wecsdesc);	    			
					ps.setObject(2, custid);
					ps.setObject(3, ebid);
					ps.setObject(4, wectype);
					ps.setObject(5, locationtxt);
					ps.setObject(6, wecmfactor);
					ps.setObject(7, weccapacity);
					ps.setObject(8, UserId);	 
					ps.setDate(9, date);
					ps.setObject(10, wecstatus);
					ps.setObject(11, GenComtxt.equals("") ? 0 : GenComtxt);
					ps.setObject(12, MacAvatxt.equals("") ? 0 : MacAvatxt);
					ps.setObject(13, ExtAvatxt.equals("") ? 0 : ExtAvatxt);
					ps.setObject(14, IntAvatxt.equals("") ? 0 : IntAvatxt);
					ps.setObject(15, fdate);
					ps.setObject(16, tdate);
					ps.setObject(17, formula);
					ps.setObject(18, astatus);
					ps.setObject(19, costPerUnit);
					ps.setObject(20, wecSrNo);
					ps.setObject(21, wecGuaranteeType);
					ps.setObject(22, customerTypeTxt);	
					ps.setObject(23, scadaDataTxt);
					ps.setObject(24, feederid);
					ps.setObject(25, scadaDataTxt);
					ps.setObject(26, wecid);	


					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>WEC Data Updated Successfully!</font>";
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
	//Added by new 
	public List searchempbyfilter(String empcode,String firname,String lastname,String location,String bloodgroup) throws Exception {
		//public List searchempbyfilter(DynaBean dynaBean) throws Exception {

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;    	  		
		ResultSet rs = null;
		List tranList = new ArrayList();
		String sqlQuery="";    		
		String QUERY="";  	
		empcode = empcode.toUpperCase();
		lastname = lastname.toUpperCase();
		firname = firname.toUpperCase();
		location = location.toUpperCase();
		bloodgroup = bloodgroup.toUpperCase();
		sqlQuery = "SELECT * FROM TBL_EMPLOYEE a,TBL_LOCATION b where A.S_LOCATION_ID=B.S_LOCATION_ID AND " +
				" (d_date_of_leave is null or to_date(D_DATE_OF_LEAVE,'dd-MON-yy') >= to_date(SYSDATE,'dd-MON-yy')) " +
				" AND S_EMPLOYEE_ID <> '91009001' ";
		if((empcode.equals(""))||(empcode == null)){

		}else{ 			
			QUERY= QUERY + " AND upper(S_EMPLOYEE_ID) like '"+empcode.toUpperCase()+"%'";    			
		}
		if((lastname.equals(""))||(lastname == null)){

		}else{
			QUERY= QUERY + " and upper(S_LAST_NAME) like '"+lastname.toUpperCase()+"%'";
		}
		if((firname.equals(""))||(firname == null)){
		}else{
			QUERY= QUERY + " and upper(S_FIRST_NAME) like '"+firname.toUpperCase()+"%'";
		}
		if((location.equals("") )||(location == null)){

		}else{
			QUERY=QUERY + " and upper(b.S_LOCATION_NAME) like '"+location.toUpperCase()+"%'";
		}
		if((bloodgroup.equals(""))||(bloodgroup == null)){

		}else{
			QUERY=QUERY + " AND upper(S_BLOOD_GROUP) like '"+bloodgroup.toUpperCase()+"%'";
		}
		sqlQuery = sqlQuery + QUERY + " ORDER BY S_EMPLOYEE_ID";
		prepStmt = conn.prepareStatement(sqlQuery);  			
		try {
			rs = prepStmt.executeQuery();
			int i = 0;
			while (rs.next())
			{	
				Vector tranVector = new Vector();
				tranVector.add(rs.getObject("S_EMPLOYEE_ID"));
				tranVector.add(rs.getObject("S_FIRST_NAME"));
				tranVector.add(rs.getObject("S_LAST_NAME"));
				tranVector.add(rs.getObject("S_LOCATION_NAME"));
				tranVector.add(rs.getObject("S_BLOOD_GROUP")==null?"":rs.getString("S_BLOOD_GROUP"));
				tranVector.add(rs.getObject("S_TEMP_OFF_PHONE")==null?"":rs.getString("S_TEMP_OFF_PHONE"));
				tranVector.add(rs.getObject("S_TEMP_RES_PHONE")==null?"":rs.getString("S_TEMP_RES_PHONE"));
				tranVector.add(rs.getObject("S_EMAIL_ID")==null?"":rs.getString("S_EMAIL_ID"));	                 
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
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return tranList;
	}

	public Hashtable getAllTransactionNamesDao() throws Exception { 
		logger.debug("ENERCON_DEBUG: AdminDAO: getAllTransactionNamesDao: Method Invoked. ");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = GlobalSQLC.GET_ALL_TRANSCTION_NAMES;
		Hashtable retHash = new Hashtable();
		try {
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				retHash.put(rs.getString(1),rs.getString(2));
			}
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
				if (rs != null)
					rs.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}

		return retHash;
	}


	public Hashtable getAllUserDao() throws Exception {
		logger.debug("ENERCON_DEBUG: AdminDAO: getAllUserDao: Method Invoked. ");
		// //System.out.println("Enter In to the getAllUser In Global");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = GlobalSQLC.GET_ALL_USER;
		Hashtable retHash = new Hashtable();
		try {
			// ////System.out.println("Enter In to the getAllUser : " + sqlQuery);
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				RoleMappingBean roleMappingBean = new RoleMappingBean();
				roleMappingBean.setsEmployeeId(rs.getString(1).toString());
				roleMappingBean.setsUserName(rs.getString(2).toString() + " " + rs.getString(4).toString());

				retHash.put(roleMappingBean.getsEmployeeId(), 
						roleMappingBean.getsUserName());
			}
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) prepStmt.close();
				if (rs != null) rs.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return retHash;
	}

	public Hashtable getAllRolesDao() throws Exception {
		logger.debug("PWC_ISPAT_DEBUG: AdminDAO: getAllRolesDao: Method Invoked. ");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = GlobalSQLC.GET_ALL_ROLES;
		Hashtable retHash = new Hashtable();
		try {
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				CreateRoleBean createRoleBean = new CreateRoleBean();
				createRoleBean.setsRoleId(String.valueOf(rs.getString(1)));
				createRoleBean.setSRoleName(rs.getString(2));
				retHash.put(createRoleBean.getsRoleId(), createRoleBean.getSRoleName());
			}
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null)
					prepStmt.close();
				if (rs != null)
					rs.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return retHash;
	}


	public boolean checkRoleExists(String roleName) throws Exception {
		logger.debug("ENERCON_DEBUG: AdminDAO: checkRoleExists: Method Invoked. ");
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		boolean roleExists = false;
		try {
			String upperRoleName = roleName.toUpperCase();
			String checkRole = AdminSQLC.CHECK_ROLE;
			prepStmt = conn.prepareStatement(checkRole);
			prepStmt.setObject(1, upperRoleName);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				roleExists = true;
			}
			prepStmt.close();
			rs.close();
		} catch (Exception exp) {
			exp.printStackTrace();
			if (conn != null) {
				conn.close();
				conn = null;

				conmanager.closeConnection();conmanager = null;
			}
		} finally {
			try {
				if (prepStmt != null) prepStmt.close();
				if (rs != null) rs.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return roleExists;
	}

	public String getMaster(String tablename)
	{    	
		String sqlQuery = "";
		if(tablename.equals("TBL_LOCATION_MASTER1")){
			sqlQuery = AdminSQLC.GET_LOCATION_MASTER;
		}
		else if (tablename.equals("TBL_ROLE_MASTER"))
			sqlQuery = AdminSQLC.GET_ROLE;      	
		else if (tablename.equals("TBL_STATE_MASTER"))
			sqlQuery = AdminSQLC.VIEW_STATE_MASTER;
		else if (tablename.equals("TBL_LOGIN_MASTER"))
			sqlQuery = AdminSQLC.VIEW_LOGIN_MASTER;
		else if (tablename.equals("TBL_STATE_MASTER_BY_RIGHTS"))
			sqlQuery = AdminSQLC.VIEW_STATE_MASTER_BY_RIGHTS;
		else if (tablename.equals("TBL_SITE_MASTER_BY_RIGHTS"))
			sqlQuery = AdminSQLC.VIEW_SITE_MASTER_BY_RIGHTS;
		else if (tablename.equals("TBL_SITE_MASTER_BY_RIGHTS1"))
			sqlQuery = AdminSQLC.VIEW_SITE_MASTER_BY_RIGHTS1;
		else if (tablename.equals("TBL_SITE_MASTER"))
			sqlQuery = AdminSQLC.VIEW_SITE_MASTER_BY_ID;
		else if (tablename.equals("VIEW_EB_MASTER"))
			sqlQuery = AdminSQLC.GET_EB_MASTER_BY_ID;
		else if (tablename.equals("SELECT_WEC_MASTER"))
			sqlQuery = AdminSQLC.GET_WEC_MASTER_BY_EBID;
		else if (tablename.equals("GET_TRANSACTION_NOT_ASSIGN_ROLE"))
			sqlQuery = AdminSQLC.GET_TRANSACTION_NOT_ASSIGN_ROLE;  
		else if (tablename.equals("TBL_EB_MASTER"))
			sqlQuery = AdminSQLC.GET_EB_MASTER; 
		else if (tablename.equals("TBL_CUSTOMER_MASTER"))
			sqlQuery = AdminSQLC.GET_CUSTOMER_MASTER; 
		//sqlQuery = AdminSQLC.GET_ALL_STATE;
		else if (tablename.equals("VIEW_SITE_MASTER"))
			sqlQuery = AdminSQLC.VIEW_SITE_MASTER;
		else if (tablename.equals("VIEW_SUBSTATION_MASTER"))
			sqlQuery = AdminSQLC.VIEW_SUBSTATION_MASTER;
		else if (tablename.equals("VIEW_FEEDER_MASTER"))
			sqlQuery = AdminSQLC.VIEW_FEEDER_MASTER;    	
		else if (tablename.equals("VIEW_AREA_MASTER"))
			sqlQuery = AdminSQLC.VIEW_AREA_MASTER;
		else if (tablename.equals("TBL_STATE_SITE_RIGHTS"))
			sqlQuery = AdminSQLC.GET_ALL_SITE_BY_USER;
		else if (tablename.equals("TBL_STATE_AREA_RIGHTS"))
			sqlQuery = AdminSQLC.GET_ALL_AREA_BY_USER;
		else if (tablename.equals("TBL_STATE_AREA_RIGHTS_1"))
			sqlQuery = AdminSQLC.GET_ALL_AREA_BY_USER_1;
		else if (tablename.equals("TBL_FEDER_MASTER"))
			sqlQuery = AdminSQLC.GET_ALL_FEDER_BY_SITE;
		else if (tablename.equals("TBL_STANDARD_MESSAGE"))
			sqlQuery = AdminSQLC.GET_STANDARD_MESSAGE; 
		else if (tablename.equals("TBL_MP_MASTER"))
			sqlQuery = AdminSQLC.SELECT_MP_BY_SHOW_FOR_WEC_ACTIVE;
		else if (tablename.equals("SELECT_CUSTOMER_MASTER_BY_EB"))
			sqlQuery = AdminSQLC.SELECT_CUSTOMER_MASTER_BY_EB;
		else if (tablename.equals("SELECT_EB_MASTER"))
			sqlQuery = AdminSQLC.SELECT_EB_MASTER;
		else if (tablename.equals("SELECT_FD_MASTER"))
			sqlQuery = AdminSQLC.SELECT_FD_MASTER;
		else if (tablename.equals("FIND_WEC_BY_SITE"))
			sqlQuery = AdminSQLC.FIND_WEC_BY_SITE;
		else if (tablename.equals("TBL_DASHBOARD_AUTHORITY"))
			sqlQuery = AdminSQLC.FIND_DASHBOARD_AUTHORITY;
		else if (tablename.equals("TBL_WEC_TYPE"))
			sqlQuery = AdminSQLC.GET_WEC_TYPE;
		else if (tablename.equals("TBL_WEC_TYPE_CAPACITY"))
			sqlQuery = AdminSQLC.GET_WEC_TYPE_CAPACITY;
		else if (tablename.equals("SELECT_EB_MASTER_BY_RIGHTS"))
			sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_RIGHTS;
		else if (tablename.equals("SELECT_WEC_MASTER_BY_RIGHTS"))
			sqlQuery = AdminSQLC.SELECT_WEC_MASTER_BY_RIGHTS;    
		else if (tablename.equals("SELECT_CUSTOMER_MASTER_BY_RIGHTS"))
			sqlQuery = AdminSQLC.SELECT_CUSTOMER_MASTER_BY_RIGHTS;    
		else if (tablename.equals("GET_TRANSACTION_DESCRIPTION_NON_ASSIGN_ROLE"))
			sqlQuery = AdminSQLC.GET_TRANSACTION_DESCRIPTION_NON_ASSIGN_ROLE;
		else if (tablename.equals("TBL_FIN_YEAR"))
			sqlQuery = AdminSQLC.GET_FIN_YEAR;
		else if (tablename.equals("TBL_CUSTOMER_FEEDBACK"))
			sqlQuery = AdminSQLC.VIEW_CUSTOMER_FEEDBACK;
		else if (tablename.equals("SELECT_FEDEER_MASTER_BY_AREA"))
			sqlQuery = AdminSQLC.VIEW_FEEDER_MASTER_BY_AREA;
		else if (tablename.equals("TBL_LOCATION_MASTER"))
			sqlQuery = AdminSQLC.VIEW_LOCATION;
		return sqlQuery;
	}

	public String addNewRoleIn(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String rolename = dynaBean.getProperty("RoleNametxt").toString();
			String roledesc = dynaBean.getProperty("RoleDescriptiontxt").toString();
			String roleid = "";
			if (dynaBean.getProperty("RoleIdtxt") == null || dynaBean.getProperty("RoleIdtxt").equals("")){
				roleid = CodeGenerate.NewCodeGenerate("TBL_ROLE_MASTER");
				sqlQuery = AdminSQLC.CHECK_ROLE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,rolename);
				prepStmt.setObject(2,roleid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Role Name already exists!</font>";
				}
				else
				{
					sqlQuery = AdminSQLC.INSERT_ROLE_NAME;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, roleid);
					ps.setObject(2, rolename);
					ps.setObject(3, roledesc);
					ps.setObject(4, UserId);
					ps.setObject(5, UserId);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					msg = "<font class='sucessmsgtext'>Role Added Successfull!</font>";
				}
				prepStmt.close();
				rs.close();
			}else{
				roleid = dynaBean.getProperty("RoleIdtxt").toString();
				sqlQuery = AdminSQLC.CHECK_ROLE_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,rolename);
				prepStmt.setObject(2,roleid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					msg = "<font class='errormsgtext'>Role Name already exists!</font>";
				}
				else
				{		    			
					sqlQuery = AdminSQLC.UPDATE_ROLE_NAME;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rolename);
					ps.setObject(2, roledesc);					
					ps.setObject(3, UserId);
					ps.setObject(4, roleid);				
					ps.executeUpdate();
					//conn.commit();
					ps.close();
					msg = "<font class='sucessmsgtext'>Role Updated Successfully!</font>";
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

	public String UpdateLoginDetail(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = ""; 
		try{	
			String custid = dynaBean.getProperty("Customeridtxt1").toString();
			String roledesc = dynaBean.getProperty("activetxt").toString();
			String loginid = "";
			if (dynaBean.getProperty("RoleIdtxt") == null || dynaBean.getProperty("RoleIdtxt").equals(""))
			{  
				sqlQuery = AdminSQLC.GET_EMAIL_ACTIVATE_BY_CUSTOMER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,custid);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{  
					loginid=rs.getObject("S_LOGIN_DETAIL_ID").toString();
				}
				else
				{
					msg = "<font class='errormsgtext'>Not Updated Login Detail Of This Customer Not Exist!</font>";
				}
				prepStmt.close();
				rs.close();
			}
			else
			{
				loginid=dynaBean.getProperty("loginmasteridtxt").toString();
			}

			if(!loginid.equals(""))
			{
				sqlQuery = AdminSQLC.UPDATE_LOGIN_DETAIL;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, roledesc);
				ps.setObject(2, loginid);					
				ps.executeUpdate();
				//conn.commit();
				ps.close();
				msg = "<font class='sucessmsgtext'>Updated Successfully!</font>";
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

	public String addNewRoleTran(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String rolename = dynaBean.getProperty("RoleNametxt").toString();
			String tran = dynaBean.getProperty("Transactiontxt").toString();
			String tranroleid = CodeGenerate.NewCodeGenerate("TBL_ROLE_TRAN_MAPPING");
			sqlQuery = AdminSQLC.CHECK_ROLE_TRAN_MASTER;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1,rolename);
			prepStmt.setObject(2,tran);
			prepStmt.setObject(3,tranroleid);
			rs = prepStmt.executeQuery();
			if (rs.next())
			{
				msg = "<font class='errormsgtext'>Transaction already to this Role!</font>";				 				
			}
			else
			{
				sqlQuery = AdminSQLC.ADD_NEW_TRANSACTION;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, tranroleid);
				ps.setObject(2, tran);
				ps.setObject(3, rolename);
				ps.setObject(4, UserId);
				ps.setObject(5, UserId);
				int iInserteddRows = ps.executeUpdate();
				//conn.commit();
				if (iInserteddRows != 1)
					throw new Exception("DB_UPDATE_ERROR", null);
				ps.close();
				msg = "<font class='sucessmsgtext'>Transaction Assigned Successfull!</font>";
			}	
			prepStmt.close();
			rs.close();
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
	public String addNewRoleTranNew(String UserId, DynaBean dynaBean)
			throws Exception {
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try {
			String rolename = dynaBean.getProperty("RoleNametxt").toString();
			String tran = dynaBean.getProperty("Transactions").toString();
			String tranid="";
			int exists = tran.indexOf(",");
			if (exists > -1) {
				String trans[] = tran.split(",");
				for(int i = 0;i < Array.getLength(trans);i++)
				{
					tranid = (String)Array.get(trans, i);
					if (! tranid.equals("."))
					{
						String tranroleid = CodeGenerate.NewCodeGenerate("TBL_ROLE_TRAN_MAPPING");
						sqlQuery = AdminSQLC.CHECK_ROLE_TRAN_MASTER;
						prepStmt = conn.prepareStatement(sqlQuery);
						prepStmt.setObject(1, tran);
						prepStmt.setObject(2, rolename);
						prepStmt.setObject(3, tranroleid);
						rs = prepStmt.executeQuery();
						if (rs.next()) {
							msg = "<font class='errormsgtext'>Transaction already to this Role!</font><br>";
						} else {
							sqlQuery = AdminSQLC.ADD_NEW_TRANSACTION;
							ps = conn.prepareStatement(sqlQuery);
							ps.setObject(1, tranroleid);
							ps.setObject(2, tranid);
							ps.setObject(3, rolename);
							ps.setObject(4, UserId);
							ps.setObject(5, UserId);
							int iInserteddRows = ps.executeUpdate();
							// conn.commit();
							if (iInserteddRows != 1)
								throw new Exception("DB_UPDATE_ERROR", null);
							ps.close();
							msg = "<font class='sucessmsgtext'>Transaction Assigned Successfull!</font><br>";
						}
						prepStmt.close();
						rs.close();
					}
				}
			}
		} catch (SQLException sqlExp) {
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

					conmanager.closeConnection();
					conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				ps = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			}
		}
		return msg;
	}
	public String UserRoleTran(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		String sqlQuery = "";
		try{
			String rolename = dynaBean.getProperty("RoleNametxt").toString();
			String empcode = dynaBean.getProperty("UserId").toString();			
			sqlQuery = AdminSQLC.UPDATE_CUSTOMER_ROLE;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, rolename);
			prepStmt.setObject(2, empcode);
			int iInserteddRows = prepStmt.executeUpdate();
			//conn.commit();
			if (iInserteddRows != 1)
				throw new Exception("DB_UPDATE_ERROR", null);
			prepStmt.close();
			msg = "<font class='sucessmsgtext'>User/Customer Role Update Successfully!</font>";
		}catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) prepStmt.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return msg;
	}

	public String AddLocationRight(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		try{
			String rightsid = dynaBean.getProperty("rightsid").toString().trim();
			String SUserId = dynaBean.getProperty("UserId").toString();
			String StateIdtxt = dynaBean.getProperty("Transactions").toString();
			String Sitelist= dynaBean.getProperty("SiteTransactions").toString().replace("'", "");
			String SiteIdtxt[] =Sitelist.split(",");
			for( int i=0;i<SiteIdtxt.length;i++)
			{
				if (rightsid == null || rightsid.equals("")){

					sqlQuery = AdminSQLC.CHECK_STATE_SITE_RIGHT;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,SUserId);
					prepStmt.setObject(2,StateIdtxt);
					prepStmt.setObject(3,SiteIdtxt[i]);
					//prepStmt.setObject(4,rightsid);
					rs = prepStmt.executeQuery();
					if (rs.next())
					{
						//msg = "<font class='errormsgtext'>State & Site Right for " + SUserId + " already exists!</font>";    				    				
					}
					else
					{    rightsid = CodeGenerate.NewCodeGenerate( "TBL_STATE_SITE_RIGHTS");				
					sqlQuery = AdminSQLC.INSERT_STATE_SITE_RIGHT;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, rightsid);
					ps.setObject(2, SUserId);
					ps.setObject(3, StateIdtxt);
					ps.setObject(4, SiteIdtxt[i]);    				
					ps.setObject(5, UserId);
					ps.setObject(6, UserId);
					int iInserteddRows = ps.executeUpdate();
					//conn.commit();
					if (iInserteddRows != 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					ps.close();
					rightsid="";
					}
					rs.close();
					prepStmt.close();
				}else{
					//rightsid = dynaBean.getProperty("rightsid").toString();    			    			
					sqlQuery = AdminSQLC.CHECK_STATE_SITE_RIGHT ;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,SUserId);
					prepStmt.setObject(2,StateIdtxt);
					prepStmt.setObject(3,SiteIdtxt[i]);
					prepStmt.setObject(4,rightsid);
					rs = prepStmt.executeQuery();
					if (rs.next())
					{
						msg = "<font class='errormsgtext'>State & Site Right for " + SUserId + " already exists!</font>";

					}
					else
					{		    			
						sqlQuery = AdminSQLC.UPDATE_STATE_SITE_RIGHT;
						ps = conn.prepareStatement(sqlQuery);
						ps.setObject(1, SUserId);
						ps.setObject(2, StateIdtxt);
						ps.setObject(3, SiteIdtxt[i]);	
						ps.setObject(4, rightsid);
						int iInserteddRows = ps.executeUpdate();
						//conn.commit();
						if (iInserteddRows != 1)
							//    throw new Exception("DB_UPDATE_ERROR", null);
							ps.close();
						msg = "<font class='sucessmsgtext'>State & Site Right Updated Successfully!</font>";
					}
					rs.close();
					prepStmt.close();
				}	
				msg = "<font class='sucessmsgtext'>State & Site Right Added Successfull!</font>";
			}
		}catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		}finally {
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

	public String ChangePassword(String UserId,DynaBean dynaBean) throws Exception{
		String msg="";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";       
		try{
			Diff epwd = new Diff();
			String op = epwd.encrpt(dynaBean.getProperty("OPasswordtxt").toString());
			String np = epwd.encrpt(dynaBean.getProperty("NPasswordtxt").toString());
			op = dynaBean.getProperty("OPasswordtxt").toString();
			np = dynaBean.getProperty("NPasswordtxt").toString();
			sqlQuery = AdminSQLC.CHANGE_PASSWORD;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1,UserId);
			prepStmt.setObject(2,op);
			rs = prepStmt.executeQuery();

			if (rs.next())
			{
				sqlQuery = AdminSQLC.UPDATE_EMPLOYEE_PASSWORD;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, np);
				ps.setObject(2, UserId);
				ps.setObject(3, UserId);
				int iInserteddRows = ps.executeUpdate();
				//conn.commit();
				if (iInserteddRows != 1)
					ps.close();
				msg = "<font class='sucessmsgtext'>Password Changed Successfully!</font>";								
			}
			else
			{	
				msg = "<font class='errormsgtext'>Old password not Correct!</font>";				    
			}
			rs.close();
			prepStmt.close();
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
	public String wecCurtailment(String UserId, DynaBean dynaBean) throws Exception{
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String myQuery1 = "";
		PreparedStatement myPrepareStatement1 = null;
		ResultSet myResultSet1 = null;
		String myQuery2 = "";
		PreparedStatement myPrepareStatement2 = null;
		ResultSet myResultSet2 = null;
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		boolean conflict = false;
		try{
			String S_WEC_ID = (String) dynaBean.getProperty("WECIdtxt");
			String startDateInString = (String) dynaBean.getProperty("startDatetxt");
			java.util.Date startDateInJavaFormat = dateFormatter.parse(startDateInString);
			java.sql.Date startDateInSQLFormat = new java.sql.Date(startDateInJavaFormat.getTime());
			String endDateInString = (String) dynaBean.getProperty("endDatetxt");
			java.util.Date endDateInJavaFormat = dateFormatter.parse(endDateInString);
			java.sql.Date endDateInSQLFormat = new java.sql.Date(endDateInJavaFormat.getTime());

			myQuery1 = AdminSQLC.START_END_DATE_BY_WECID_CURTAILMENT;
			myPrepareStatement1 = conn.prepareStatement(myQuery1);
			myPrepareStatement1.setObject(1, S_WEC_ID);
			myResultSet1 = myPrepareStatement1.executeQuery();
			while(myResultSet1.next()){
				System.out.println("Start_Date:" + myResultSet1.getObject("D_START_DATE"));
				System.out.println("End_Date:" + myResultSet1.getObject("D_END_DATE"));
				sqlQuery = 	AdminSQLC.CURTAILED_WEC_BETWEEN_START_END_DATE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, S_WEC_ID);
				prepStmt.setObject(2, myResultSet1.getObject("D_START_DATE"));
				prepStmt.setObject(3, startDateInSQLFormat);
				prepStmt.setObject(4, endDateInSQLFormat);
				prepStmt.setObject(5, myResultSet1.getObject("D_END_DATE"));
				prepStmt.setObject(6, startDateInSQLFormat);
				prepStmt.setObject(7, endDateInSQLFormat);
				prepStmt.setObject(8, startDateInSQLFormat);
				prepStmt.setObject(9, myResultSet1.getObject("D_START_DATE"));
				prepStmt.setObject(10, myResultSet1.getObject("D_END_DATE"));
				prepStmt.setObject(11, endDateInSQLFormat);
				prepStmt.setObject(12, myResultSet1.getObject("D_START_DATE"));
				prepStmt.setObject(13, myResultSet1.getObject("D_END_DATE"));
				rs = prepStmt.executeQuery();
				while(rs.next()){
					conflict = true;
					break;
				}
				if(conflict){
					msg = "<font class='sucessmsgtext'>Conflict:Already in Curtailment</font>";
					return msg;
				}
			}
			String S_START_TIME = (String) dynaBean.getProperty("startTime");
			String S_END_TIME = (String) dynaBean.getProperty("endTime");
			int N_CURTAILED_CAPACITY = Integer.parseInt((String)dynaBean.getProperty("curtailCapacity"));
			String S_CURTAILMENT_REMARKS = (String) dynaBean.getProperty("curtailRemark");
			String S_NEARBY_WEC = (String) dynaBean.getProperty("nearWECIdtxt");
			String S_CREATED_BY = UserId;

			java.util.Date today = new Date();
			java.sql.Date D_CREATED_DATE = new java.sql.Date(today.getTime());

			sqlQuery = AdminSQLC.INSERT_WEC_FOR_CURTAILMENT;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1,S_WEC_ID);
			prepStmt.setObject(2,startDateInSQLFormat);
			prepStmt.setObject(3,endDateInSQLFormat);
			prepStmt.setObject(4,S_START_TIME);
			prepStmt.setObject(5,S_END_TIME);
			prepStmt.setObject(6,N_CURTAILED_CAPACITY);
			prepStmt.setObject(7,S_CURTAILMENT_REMARKS);
			prepStmt.setObject(8,S_NEARBY_WEC);
			prepStmt.setObject(9,S_CREATED_BY);
			prepStmt.setObject(10,D_CREATED_DATE);
			int iInserteddRows = prepStmt.executeUpdate();


			//conn.commit();
			if (iInserteddRows != 1)
				throw new Exception("DB_UPDATE_ERROR", null);
			msg = "<font class='sucessmsgtext'>WEC Curtailment Added Successfully!</font>";
		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (myPrepareStatement1 != null)
					myPrepareStatement1.close();
				if(myResultSet1 != null){
					myResultSet1.close();
				}
				if (prepStmt != null)
					prepStmt.close();
				if(rs != null){
					rs.close();
				}
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}

		return msg;
	}
	public String getAjaxDetails(String item,String action,String UserId) throws Exception{
		StringBuffer xml = new StringBuffer();
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		CustomerDao cd = new CustomerDao();
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		String sqlQuery = "";
		String sqlQuery1 = "";
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat formatter = new DecimalFormat("#########0");
		try{
			if(action.equals("getPlantNoBasedOnLocationNo")){
				/*System.out.println("getPlantNoBasedOnLocationNo");
        		System.out.println("item:" + item);*/

				sqlQuery = AdminSQLC.GET_PLANT_NO_BASED_ON_LOCATION_NO;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<plantmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("\t<plantcode>\n");
					xml.append("\t\t<plantno>");
					xml.append(rs.getString("S_PLANT_NO"));
					xml.append("</plantno>\n");
					xml.append("\t\t<plantwec>");
					xml.append(rs.getString(2));
					xml.append("</plantwec>\n");
					xml.append("\t</plantcode>\n");
				}
				xml.append("</plantmaster>\n");
				//System.out.println(xml);
				rs.close();
				ps.close();
			}
			else if (action.equals("getloginmasterbyid"))
			{

				sqlQuery = AdminSQLC.SEARCH_LOGIN_MASTER_ID_DETAILS;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<loginmaster generated=\""+System.currentTimeMillis()+"\">\n");
					xml.append("<logincode>\n");
					xml.append("<loginid>");
					xml.append(rs.getObject("S_LOGIN_MASTER_ID"));
					xml.append("</loginid>\n");
					xml.append("<userid>");
					xml.append(rs.getObject("S_USER_ID"));
					xml.append("</userid>\n");
					xml.append("<password>");
					xml.append(rs.getObject("S_PASSWORD"));
					xml.append("</password>\n");
					xml.append("<rolename>");
					xml.append(rs.getObject("S_ROLE_ID"));
					xml.append("</rolename>\n");
					xml.append("<logintype>");
					xml.append(rs.getObject("S_LOGIN_TYPE").toString().replace("&", "And"));
					xml.append("</logintype>\n");	
					String custid="0";
					/* if(rs.getString("S_LOGIN_TYPE").equals("C"))
			      {
			        sqlQuery = AdminSQLC.CUSTOMER_LOGIN_ID;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,rs.getObject("S_LOGIN_MASTER_ID"));
				    rs1 = ps.executeQuery();
					if (rs1.next())
					{
						custid=rs1.getString("S_CUSTOMER_ID");
					}
					rs1.close();
					ps.close();
				  }*/
					xml.append("<custid>");
					xml.append(custid);
					xml.append("</custid>\n");
					xml.append("<desc>");
					xml.append(rs.getObject("S_LOGIN_DESCRIPTION") == null ? "" : rs.getObject("S_LOGIN_DESCRIPTION").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("<active>");
					xml.append(rs.getObject("S_ACTIVE"));
					xml.append("</active>\n");

					xml.append("<remarks>");
					xml.append(escape(rs.getObject("S_REMARKS") == null ? "NA" : rs.getObject("S_REMARKS").toString().replace("&", "And")));
					xml.append("</remarks>\n");
					xml.append("</logincode>\n");		    
					xml.append("</loginmaster>\n");
				}
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("findStateMaster"))
			{
				sqlQuery = AdminSQLC.SELECT_STATE_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item.toUpperCase() + "%");
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<statemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<statecode>");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</sname>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SAP_STATE_CODE"));
					xml.append("</scode>\n");
					xml.append("</statecode>\n");
				}
				xml.append("</statemaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findStateByID"))
			{
				sqlQuery = AdminSQLC.SELECT_STATE_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<statemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<statecode>");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</sname>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SAP_STATE_CODE"));
					xml.append("</scode>\n");
					xml.append("</statecode>\n");
				}
				xml.append("</statemaster>\n");	
				rs.close();
				ps.close();
			}         	
			else if(action.equals("findWECByCustomerID"))
			{
				sqlQuery = "SELECT * FROM TBL_WEC_MASTER WHERE S_CUSTOMER_ID IN("+item+") AND S_STATUS=1";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}else if(action.equals("getWECBYEBID"))
			{
				sqlQuery = "SELECT * FROM TBL_WEC_MASTER WHERE S_EB_ID IN("+item+") AND S_STATUS=1";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}        	
			else if(action.equals("findWECBySiteID"))
			{
				sqlQuery = "SELECT A.* FROM TBL_WEC_MASTER A,TBL_EB_MASTER B WHERE A.S_EB_ID=B.S_EB_ID AND B.S_SITE_ID IN("+item+") AND A.S_STATUS=1";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_WECSHORT_DESCR"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findSiteByStateID"))
			{
				sqlQuery = "SELECT * FROM TBL_SITE_MASTER WHERE S_STATE_ID IN("+item+")";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findAreaByStateID"))
			{
				sqlQuery = "SELECT * FROM TBL_AREA_MASTER WHERE S_STATE_ID IN("+item+")";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<areamaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<areacode>");
					xml.append("<areaid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</areaid>\n");
					xml.append("<areaname>");
					xml.append(rs.getObject("S_AREA_NAME"));
					xml.append("</areaname>\n");

					xml.append("</areacode>\n");
				}
				xml.append("</areamaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findSiteByAreaID"))
			{
				sqlQuery = "SELECT * FROM TBL_SITE_MASTER WHERE S_AREA_ID IN("+item+")";
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findSiteByState_custID"))
			{
				String comma[]=item.split(";;");
				//sqlQuery = "SELECT * FROM TBL_SITE_MASTER WHERE S_STATE_ID IN("+comma[1]+")";
				sqlQuery = "SELECT DISTINCT A.* FROM TBL_SITE_MASTER A,TBL_EB_MASTER B,TBL_STATE_MASTER C WHERE A.S_SITE_ID=B.S_SITE_ID AND A.S_STATE_ID=C.S_STATE_ID AND C.S_STATE_ID IN("+comma[1]+") AND B.S_CUSTOMER_ID IN("+comma[0]+") AND B.S_STATUS=1";
				// System.out.println("comma[state]--"+comma[1].toString());
				// System.out.println("comma[customer]--"+comma[0].toString());
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");	
				rs.close();
				ps.close();
			}
			else if (action.equals("getSite"))
			{
				sqlQuery = AdminSQLC.GET_SITE_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");	
					xml.append("<sinc>");
					xml.append(rs.getObject("S_SITE_INCHARGE") == null ? "." : rs.getObject("S_SITE_INCHARGE"));
					xml.append("</sinc>\n");
					xml.append("<sadd>");
					xml.append(rs.getObject("S_SITE_ADDRESS") == null ? "." : rs.getObject("S_SITE_ADDRESS"));
					xml.append("</sadd>\n");
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getFeederSite"))
			{
				sqlQuery = AdminSQLC.GET_Feeder_SITE_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<feederhead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<feedercode>\n");
					xml.append("<subdesc>");
					xml.append(rs.getObject("S_SUBSTATION_DESC"));
					xml.append("</subdesc>\n");
					xml.append("<fid>");
					xml.append(rs.getObject("S_FEEDER_ID"));
					xml.append("</fid>\n");
					xml.append("<fdesc>");
					xml.append(rs.getObject("S_FEEDER_DESC"));
					xml.append("</fdesc>\n");					    
					xml.append("</feedercode>\n");
				}			    
				xml.append("</feederhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getAreaByID"))
			{        		
				sqlQuery = AdminSQLC.GET_AREA_BY_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");	
					xml.append("<sinc>");
					xml.append(rs.getObject("S_SITE_INCHARGE") == null ? "." : rs.getObject("S_SITE_INCHARGE"));
					xml.append("</sinc>\n");
					xml.append("<sadd>");
					xml.append(rs.getObject("S_SITE_ADDRESS") == null ? "." : rs.getObject("S_SITE_ADDRESS"));
					xml.append("</sadd>\n");
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getArea"))
			{
				sqlQuery = AdminSQLC.GET_AREA_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<areahead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<areacode>\n");
					xml.append("<aid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</aid>\n");
					xml.append("<adesc>");
					xml.append(rs.getObject("S_AREA_NAME"));
					xml.append("</adesc>\n");
					xml.append("<acode>");
					xml.append(rs.getObject("S_AREA_CODE"));
					xml.append("</acode>\n");	
					xml.append("<ainc>");
					xml.append(rs.getObject("S_AREA_INCHARGE_ID") == null ? "." : rs.getObject("S_AREA_INCHARGE_ID"));
					xml.append("</ainc>\n");				    
					xml.append("</areacode>\n");
				}			    
				xml.append("</areahead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getScadaWECDataToUpload"))
			{ 

				sqlQuery = AdminSQLC.GET_WEC_DATA_TO_UPLOAD;
				prepStmt = conn.prepareStatement(sqlQuery);
				String items[] = item.split(",");

				java.sql.Date schedulerDt;
				java.util.Date wecreadingDate = new java.util.Date();    				
				wecreadingDate = format.parse(items[1]);				
				schedulerDt=new java.sql.Date(wecreadingDate.getTime());

				prepStmt.setObject(1,items[0]);
				prepStmt.setObject(2,schedulerDt);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<stateDataPublish generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<stateDataCode>\n");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<statedesc>");
					xml.append(rs.getString("S_STATE_NAME").replaceAll(" ","~"));
					xml.append("</statedesc>\n");
					xml.append("<areaid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</areaid>\n");
					xml.append("<areadesc>");
					xml.append(rs.getString("S_AREA_NAME").replaceAll(" ","~"));
					xml.append("</areadesc>\n");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sitedesc>");
					xml.append(rs.getString("S_SITE_NAME").replaceAll(" ","~"));
					xml.append("</sitedesc>\n");	
					xml.append("<wecpublished>");
					xml.append(rs.getObject("WEC_YET_TO_PUBLISH") == null ? "." : rs.getObject("WEC_YET_TO_PUBLISH"));
					xml.append("</wecpublished>\n");	
					xml.append("<readingdate>");
					xml.append(rs.getString("D_READING_DATE").replaceAll(" ", ""));
					xml.append("</readingdate>\n");
					xml.append("<scadadata>");
					xml.append(rs.getString("N_SCADA_DATA"));
					xml.append("</scadadata>\n");

					/*********************Total WEC Count********************************/

					sqlQuery = AdminSQLC.GET_WEC_COUNT;
					ps = conn.prepareStatement(sqlQuery);

					SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
					String b =   rs.getObject("D_READING_DATE").toString();

					java.util.Date fd1 = format2.parse(b);
					java.sql.Date fdate = new java.sql.Date(fd1.getTime());		    			

					ps.setObject(1,rs.getObject("S_SITE_ID").toString());
					ps.setObject(2,fdate);
					rs2=ps.executeQuery();

					if(rs2.next())
					{
						xml.append("<wecCount>");
						xml.append(rs2.getString("WECCNT"));
						xml.append("</wecCount>\n");
					}
					else
					{
						xml.append("<wecCount>");
						xml.append("0");
						xml.append("</wecCount>\n");
					}
					rs2.close();
					ps.close();		

					/*********************END of Total WEC Count*********************************/

					/*********************Total WEC Count From SCADA*****************************/

					if(rs.getInt("N_SCADA_DATA")==1)
					{
						sqlQuery = AdminSQLC.GET_WEC_SCADA_DATA;
						ps = conn.prepareStatement(sqlQuery);

						SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
						String a =   rs.getObject("D_READING_DATE").toString();

						java.util.Date fd = format1.parse(a);
						java.sql.Date fromdate = new java.sql.Date(fd.getTime());		    			

						ps.setObject(1,fromdate);
						ps.setObject(2,rs.getObject("S_SITE_ID").toString());
						rs1=ps.executeQuery();
						if(rs1.next())
						{
							xml.append("<scadacnt>");
							xml.append(rs1.getString("SCNT"));
							xml.append("</scadacnt>\n");
						}
						else
						{
							xml.append("<scadacnt>");
							xml.append("0");
							xml.append("</scadacnt>\n");
						}
						rs1.close();
						ps.close();

					}
					else
					{

						xml.append("<scadacnt>");
						xml.append("-");
						xml.append("</scadacnt>\n");

					}


					xml.append("</stateDataCode>\n");
				}			    
				xml.append("</stateDataPublish>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("uploadScadaWECData"))
			{
				JDBCUtils publishconmanager = new JDBCUtils();
				Connection publishconn = publishconmanager.getConnection();

				String pubDetails[] = item.split(",");

				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");    			  
				//java.util.Date date = new Date();       			
				java.util.Date fd = format1.parse(pubDetails[6]);
				java.sql.Date fromdate = new java.sql.Date(fd.getTime());

				sqlQuery = AdminSQLC.GET_SCADA_WEC_RECORDS_BY_SITEID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,pubDetails[4]);
				prepStmt.setDate(2,fromdate);
				rs = prepStmt.executeQuery();

				if(!rs.next())
				{    		 
					CallableStatement calls = publishconn.prepareCall("{call PRC_CALCULATE_WEC_FACT(?,?,?,?,?,?,?,?,?,?)}");
					calls.setObject(1, pubDetails[9]);
					calls.setDate(2, fromdate);
					calls.setObject(3, "0");
					calls.setObject(4, pubDetails[0]);
					calls.setObject(5, pubDetails[1].replaceAll("~", " "));
					calls.setObject(6, pubDetails[2]);
					calls.setObject(7, pubDetails[3].replaceAll("~", " "));
					calls.setObject(8, pubDetails[4]);
					calls.setObject(9, pubDetails[5].replaceAll("~", " "));
					calls.setObject(10,pubDetails[8]);

					int i = calls.executeUpdate();

					// System.out.println("i===="+i);

					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<publishedData generated=\""+System.currentTimeMillis()+"\">\n"); 			    	
					xml.append("<msg>");
					// xml.append("Data Uploaded for "+pubDetails[5].replaceAll("~", " "));
					xml.append("Data Uploaded for "+pubDetails[5].replaceAll("~", " "));
					xml.append("</msg>\n");
					xml.append("<updateflag>");
					xml.append("0");
					xml.append("</updateflag>\n");
					xml.append("</publishedData>\n");			    
					calls.close();
				}
				else
				{
					System.out.println(rs.getString(1));
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<publishedData generated=\""+System.currentTimeMillis()+"\">\n"); 			    	
					xml.append("<msg>");
					xml.append("Data Already Uploaded for "+pubDetails[5].replaceAll("~", " ")+"..Do you want to update.");
					xml.append("</msg>\n");
					xml.append("<updateflag>");
					xml.append("1");
					xml.append("</updateflag>\n");
					xml.append("</publishedData>\n");
				}
			}
			else if (action.equals("updateScadaWECData"))
			{
				JDBCUtils publishconmanager = new JDBCUtils();
				Connection publishconn = publishconmanager.getConnection();

				String pubDetails[] = item.split(",");

				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");    			  
				//java.util.Date date = new Date();       			
				java.util.Date fd = format1.parse(pubDetails[6]);
				java.sql.Date fromdate = new java.sql.Date(fd.getTime());

				CallableStatement calls = publishconn.prepareCall("{call PRC_UPDATE_WEC_FACT(?,?,?,?,?,?,?,?,?,?)}");
				calls.setObject(1, pubDetails[9]);
				calls.setDate(2, fromdate);
				calls.setObject(3, "0");
				calls.setObject(4, pubDetails[0]);
				calls.setObject(5, pubDetails[1].replaceAll("~", " "));
				calls.setObject(6, pubDetails[2]);
				calls.setObject(7, pubDetails[3].replaceAll("~", " "));
				calls.setObject(8, pubDetails[4]);
				calls.setObject(9, pubDetails[5].replaceAll("~", " "));
				calls.setObject(10,pubDetails[8]);

				calls.executeUpdate();

				// System.out.println("i===="+i);

				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<updatedData generated=\""+System.currentTimeMillis()+"\">\n"); 			    	
				xml.append("<msg>");
				xml.append("Data Updated for "+pubDetails[5].replaceAll("~", " "));
				xml.append("</msg>\n");				    
				xml.append("</updatedData>\n");			    
				calls.close();			

			}
			else if (action.equals("getScadaEBDataToUpload"))
			{
				sqlQuery = AdminSQLC.GET_EB_DATA_TO_UPLOAD;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<stateDataPublish generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<stateDataCode>\n");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<statedesc>");
					xml.append(rs.getString("S_STATE_NAME").replaceAll(" ","~"));
					xml.append("</statedesc>\n");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sitedesc>");
					xml.append(rs.getString("S_SITE_NAME").replaceAll(" ","~"));
					xml.append("</sitedesc>\n");	
					xml.append("<ebtoupload>");
					xml.append(rs.getObject("S_EB_TO_UPLOAD") == null ? "." : rs.getObject("S_EB_TO_UPLOAD"));
					xml.append("</ebtoupload>\n");	
					xml.append("<readingdate>");
					xml.append(rs.getString("D_READING_DATE").replaceAll(" ", ""));
					xml.append("</readingdate>\n");				    

					xml.append("</stateDataCode>\n");
				}			    
				xml.append("</stateDataPublish>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("checkScadaWECRecord"))
			{    			    			
				String checkRecord[] = item.split(",");

				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");

				//java.util.Date date = new Date();       			
				java.util.Date fd = format1.parse(checkRecord[1]);
				java.sql.Date fromdate = new java.sql.Date(fd.getTime());   

				sqlQuery = AdminSQLC.GET_SCADA_WEC_RECORDS_BY_SITEID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,checkRecord[0]);
				prepStmt.setDate(1,fromdate);
				rs = prepStmt.executeQuery();
				if(rs.next())
				{
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<checkscadarecord generated=\""+System.currentTimeMillis()+"\">\n");
					xml.append("<msg>");				
					xml.append("Data already uploaded for "+checkRecord[0].replaceAll("~", " "));				
					xml.append("</msg>\n");
					xml.append("</checkscadarecord>\n");	
				}
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getCustomerDetails"))
			{
				sqlQuery = AdminSQLC.GET_CUST_DETAILS_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<custhead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<custcode>\n");
					xml.append("<loginid>");
					xml.append(rs.getObject("S_LOGIN_MASTER_ID"));
					xml.append("</loginid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID") == null ? "." : rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");
					xml.append("<custname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME"));
					xml.append("</custname>\n");
					xml.append("<totalwec>");
					xml.append(rs.getObject("TOTALWEC") == null ? "." : rs.getObject("TOTALWEC"));
					xml.append("</totalwec>\n");
					xml.append("</custcode>\n");
				}			    
				xml.append("</custhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getSiteByCustID"))
			{
				String comma[]=item.split(",");
				sqlQuery = AdminSQLC.GET_SITE_ID_USING_CUSTID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,comma[0]);
				prepStmt.setObject(2,comma[1]);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");				    
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getStateByCustID"))
			{
				sqlQuery = AdminSQLC.GET_STATE_ID_USING_CUSTID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SAP_STATE_CODE"));
					xml.append("</scode>\n");				    
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getStateByCustomerID"))
			{
				sqlQuery = 	" SELECT DISTINCT A.S_STATE_ID,S_STATE_NAME,S_SAP_STATE_CODE FROM TBL_STATE_MASTER A,TBL_SITE_MASTER B "+
						" WHERE A.S_STATE_ID =B.S_STATE_ID and B.s_site_id in(select distinct  s_site_id from tbl_eb_master     "+
						" where s_customer_id in ("+item+") and s_status=1)";
				prepStmt = conn.prepareStatement(sqlQuery);
				// prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SAP_STATE_CODE"));
					xml.append("</scode>\n");				    
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("newsdelete")) {
				sqlQuery = AdminSQLC.DELETE_NEWS_DETAIL_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, item);
				ps.executeQuery();

				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<newsmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");

				xml.append("<ncode>\n");
				xml.append("<newsid>");
				xml.append("Sucessfully Deleted");
				xml.append("</newsid>\n");

				xml.append("</ncode>\n");

				xml.append("</newsmaster>\n");
				//.close();
				ps.close();
			}
			else if (action.equals("newsdetails")) {
				if (item.length() > 0)
				{
					sqlQuery = AdminSQLC.GET_NEWS_DETAIL_BY_ID;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1, item);
				}
				else
				{
					sqlQuery = AdminSQLC.GET_NEWS_DETAIL;
					ps = conn.prepareStatement(sqlQuery);
				}
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<newsmaster generated=\""
						+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<ncode>\n");
					xml.append("<newsid>");
					xml.append(rs.getObject("S_NEWS_ID"));
					xml.append("</newsid>\n");
					xml.append("<title>");
					xml.append(rs.getObject("S_TITLE") == null ? "." : cd.changestr(rs.getObject("S_TITLE").toString()));
					xml.append("</title>\n");
					xml.append("<descr>");
					xml.append(rs.getObject("S_DESCR") == null ? "." : cd.changestr(rs.getObject("S_DESCR").toString()));
					xml.append("</descr>\n");
					xml.append("<fdate>");
					String pfdate = convertDateFormat(rs.getObject("D_FROM_DATE").toString(), "yyyy-MM-dd","dd/MM/yyyy");
					xml.append(pfdate);
					xml.append("</fdate>\n");
					xml.append("<tdate>");
					String ptdate = convertDateFormat(rs.getObject("D_TO_DATE").toString(), "yyyy-MM-dd", "dd/MM/yyyy");
					xml.append(ptdate);
					xml.append("</tdate>\n");
					xml.append("</ncode>\n");
				}
				xml.append("</newsmaster>\n");
				rs.close();
				ps.close();
			}
			else if (action.equals("getSiteBYRIGHTS"))
			{
				//System.out.println(item);
				String s[]=item.split(",");
				//sqlQuery = AdminSQLC.GET_SITE_ID;
				//System.out.println("9"+s[1]);
				//System.out.println("10"+s[0]);
				if(s[1].equals("0000000001"))
				{
					sqlQuery = AdminSQLC.GET_SITE_ID;
					prepStmt = conn.prepareStatement(sqlQuery);
					// System.out.println("s[0]--"+s[0].toString());
					prepStmt.setObject(1,s[0]);

				}
				else
				{
					sqlQuery = AdminSQLC.VIEW_SITE_MASTER_BY_RIGHTS;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,s[0]);
					prepStmt.setObject(2,UserId);
				}

				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");				    
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getSiteByArea"))
			{        		
				sqlQuery = AdminSQLC.VIEW_SITE_MASTER_BY_AREA;
				prepStmt = conn.prepareStatement(sqlQuery);        		
				prepStmt.setObject(1,item);

				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sdesc>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");				    
					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getAreaBYRIGHTS"))
			{
				sqlQuery = AdminSQLC.GET_AREA_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<areahead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<areacode>\n");
					xml.append("<aid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</aid>\n");
					xml.append("<adesc>");
					xml.append(rs.getObject("S_AREA_NAME"));
					xml.append("</adesc>\n");

					xml.append("</areacode>\n");
				}			    
				xml.append("</areahead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getRemarksBySite"))
			{
				sqlQuery = AdminSQLC.GET_REMARKS_BY_SITE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<siteRemarks>");
					xml.append(rs.getObject("S_SITE_REMARKS"));
					xml.append("</siteRemarks>\n");

					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getAllRemarks"))
			{
				sqlQuery = AdminSQLC.GET_ALL_SITE_REMARKS;
				prepStmt = conn.prepareStatement(sqlQuery);				
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sitename>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sitename>\n");
					xml.append("<siteremarks>");
					xml.append(rs.getObject("S_SITE_REMARKS"));
					xml.append("</siteremarks>\n");

					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getFeederByArea"))
			{
				sqlQuery = AdminSQLC.GET_FEEDER_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<feederhead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<feedercode>\n");
					xml.append("<fid>");
					xml.append(rs.getObject("S_FEEDER_ID"));
					xml.append("</fid>\n");
					xml.append("<fdesc>");
					xml.append(rs.getObject("S_FEEDER_DESC"));
					xml.append("</fdesc>\n");

					xml.append("</feedercode>\n");
				}			    
				xml.append("</feederhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getFeder"))
			{
				sqlQuery = AdminSQLC.SELECT_FD_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<federhead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<federcode>\n");
					xml.append("<fid>");
					xml.append(rs.getObject("S_FEDER_ID"));
					xml.append("</fid>\n");
					xml.append("<fdesc>");
					xml.append(rs.getObject("S_FEDERSHORT_DESCR"));
					xml.append("</fdesc>\n");

					xml.append("</federcode>\n");
				}			    
				xml.append("</federhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("geteb"))
			{
				sqlQuery = AdminSQLC.SELECT_EB_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_EBSHORT_DESCR"));
					xml.append("</sdesc>\n");
					xml.append("<weccusid>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</weccusid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");

					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getebbycust"))
			{
				String custSite[]=item.split(",");          		
				if (custSite.length==1)
				{
					sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_CUST1;
					prepStmt = conn.prepareStatement(sqlQuery);        		
					prepStmt.setObject(1,custSite[0]);    				
				}
				else
				{
					sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_CUST;
					prepStmt = conn.prepareStatement(sqlQuery);        		
					prepStmt.setObject(1,custSite[0]);
					prepStmt.setObject(2,custSite[1]);
				}        		
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");
					xml.append("<sid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</sid>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_EBSHORT_DESCR"));
					xml.append("</sdesc>\n");
					xml.append("<weccusid>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</weccusid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");

					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getcust_id"))
			{
				sqlQuery = AdminSQLC.SELECT_CUSTOMER_MASTER_BY_EB;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<sitecode>\n");

					xml.append("<weccusid>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</weccusid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");

					xml.append("</sitecode>\n");
				}			    
				xml.append("</sitehead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getcostbysite"))
			{
				sqlQuery = AdminSQLC.SELECT_COST_PER_UNIT_BY_SITE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<costhead generated=\""+System.currentTimeMillis()+"\">\n");	

				if (rs.next())
				{	
					xml.append("<costcode>\n");

					xml.append("<costid>");
					xml.append(rs.getObject("COST") == null ? "." : rs.getObject("COST").toString());
					xml.append("</costid>\n");
					xml.append("<costunit>");
					xml.append(rs.getObject("COST_PER_UNIT"));
					xml.append("</costunit>\n");

					xml.append("</costcode>\n");
				}			    
				xml.append("</costhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getwectype"))
			{
				if (item.length() > 0)
				{
					sqlQuery = AdminSQLC.GET_WEC_TYPE_BY_ID;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,item);
				}
				else
				{
					sqlQuery = AdminSQLC.GET_WEC_TYPE;
					prepStmt = conn.prepareStatement(sqlQuery);
				}
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<typemaster generated=\""+System.currentTimeMillis()+"\">\n");	

				while (rs.next())
				{	
					xml.append("<typecode>\n");
					xml.append("<id>");
					xml.append(rs.getObject("S_WEC_TYPE_ID"));
					xml.append("</id>\n");
					xml.append("<type>");
					xml.append(rs.getObject("S_WEC_TYPE"));
					xml.append("</type>\n");
					xml.append("<cap>");
					xml.append(rs.getObject("N_WEC_CAPACITY"));
					xml.append("</cap>\n");
					xml.append("</typecode>\n");
				}			    
				xml.append("</typemaster>\n");
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("findSiteMaster"))
			{
				sqlQuery = AdminSQLC.SELECT_SITE_MASTER; 
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item.toUpperCase() + "%");
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<sitecode>");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");
					xml.append("<statename>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</statename>\n");
					xml.append("</sitecode>\n");
				}
				xml.append("</sitemaster>\n");	
				rs.close();
				ps.close();
			}
			else if (action.equals("getEBMask"))
			{
				sqlQuery = AdminSQLC.SELECT_SITE_ID_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<maskhead generated=\""+System.currentTimeMillis()+"\">\n");	
				while (rs.next())
				{	
					xml.append("<maskcode>\n");
					xml.append("<sicode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</sicode>\n");
					xml.append("<stcode>");
					xml.append(rs.getObject("S_SAP_STATE_CODE"));
					xml.append("</stcode>\n");
					xml.append("</maskcode>\n");
				}			    
				xml.append("</maskhead>\n");
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("findSiteByID"))
			{
				sqlQuery = AdminSQLC.SELECT_SITE_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<sitecode>");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_CODE"));
					xml.append("</scode>\n");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<sinc>");
					xml.append(rs.getObject("S_SITE_INCHARGE") == null ? "." : rs.getObject("S_SITE_INCHARGE"));
					xml.append("</sinc>\n");
					xml.append("<sadd>");
					xml.append(rs.getObject("S_SITE_ADDRESS") == null ? "." : rs.getObject("S_SITE_ADDRESS"));
					xml.append("</sadd>\n");
					xml.append("</sitecode>\n");
				}
				xml.append("</sitemaster>\n");	
				rs.close();
				ps.close();
			} 
			else if(action.equals("findFeederSiteByID"))
			{
				sqlQuery = AdminSQLC.SELECT_FEEDER_SITE_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<feedermaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<feedercode>");
					xml.append("<fid>");
					xml.append(rs.getObject("S_FEEDER_ID"));
					xml.append("</fid>\n");
					xml.append("<fname>");
					xml.append(rs.getObject("S_FEEDER_DESC"));
					xml.append("</fname>\n");
					xml.append("<sdesc>");
					xml.append(rs.getObject("S_SUBSTATION_ID"));
					xml.append("</sdesc>\n");					
					xml.append("</feedercode>\n");
				}
				xml.append("</feedermaster>\n");	
				rs.close();
				ps.close();
			} 
			else if(action.equals("findAreaByID"))
			{
				sqlQuery = AdminSQLC.SELECT_AREA_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<areamaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<areacode>");
					xml.append("<areaid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</areaid>\n");
					xml.append("<aname>");
					xml.append(rs.getObject("S_AREA_NAME"));
					xml.append("</aname>\n");
					xml.append("<acode>");
					xml.append(rs.getObject("S_AREA_CODE"));
					xml.append("</acode>\n");
					xml.append("<stateid>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</stateid>\n");
					xml.append("<ainc>");
					xml.append(rs.getObject("S_AREA_INCHARGE_ID") == null ? "." : rs.getObject("S_AREA_INCHARGE_ID"));
					xml.append("</ainc>\n");					
					xml.append("</areacode>\n");
				}
				xml.append("</areamaster>\n");	
				rs.close();
				ps.close();
			} 
			else if(action.equals("findCustDetailsById"))
			{
				sqlQuery = AdminSQLC.SELECT_SITE_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<custmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<custcode>");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<sname>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</sname>\n");
					xml.append("</custcode>\n");
				}
				xml.append("</custmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findMPMaster"))
			{
				if (item.equals(""))
				{
					//System.out.println("444444444444444444444444");
					sqlQuery = AdminSQLC.SELECT_MP_MASTER;
					ps = conn.prepareStatement(sqlQuery);
				}
				else
				{
					if (item.contains(","))
					{
						//System.out.println("*******************");
						String s[] = item.split(",");
						String eb = s[0];
						String type = s[1];
						sqlQuery = AdminSQLC.SELECT_MP_BY_READ_TYPE_FOR_WEC_WITHOUT_GRIDDOWNTIME;
						ps = conn.prepareStatement(sqlQuery);
						ps.setObject(1,eb);
						ps.setObject(2,type);
						
						DatabaseUtility.getSQLQueryResultInHTMLFile(sqlQuery, "1_" + MethodClass.getMethodName(),new Object[]{eb, type});
						
					}
					else
					{
						//System.out.println("------------");
						sqlQuery = AdminSQLC.SELECT_MP_BY_SHOW_FOR_WEC;
						ps = conn.prepareStatement(sqlQuery);
						ps.setObject(1,item);
					}
				}
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<mpmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<mpcode>");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<mpdesc>");
					xml.append(rs.getObject("S_MP_DESCR"));
					xml.append("</mpdesc>\n");
					xml.append("<mptype>");
					xml.append(rs.getObject("S_MP_TYPE"));
					xml.append("</mptype>\n");
					xml.append("<mpshow>");
					xml.append(rs.getObject("S_MP_SHOW"));
					xml.append("</mpshow>\n");
					xml.append("<mpunit>");
					xml.append(rs.getObject("S_MP_UNIT"));
					xml.append("</mpunit>\n");
					xml.append("<status>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</status>\n");
					xml.append("<readtype>");
					xml.append(rs.getObject("S_READ_type") == null ? "A" : rs.getObject("S_READ_type"));
					xml.append("</readtype>\n");
					xml.append("<seqno>");
					xml.append(rs.getObject("N_SEQ_NO"));
					xml.append("</seqno>\n");
					xml.append("<cum>");
					xml.append(rs.getObject("S_CUMULATIVE"));
					xml.append("</cum>\n");
					xml.append("</mpcode>\n");
				}
				xml.append("</mpmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findRemarksMaster"))
			{
				if (item.equals(""))
				{
					sqlQuery = AdminSQLC.SELECT_REMARKS_MASTER;
					ps = conn.prepareStatement(sqlQuery);
				}
				else if (item.equals("EB") || item.equals("WEC"))
				{
					sqlQuery = AdminSQLC.SELECT_REMARKS_MASTER_BY_TYPE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,item);
				}
				else if (item.equals("E-30") || item.equals("E-33") || item.equals("E-40") || item.equals("E-48") || item.equals("E-53"))
				{
					sqlQuery = AdminSQLC.SELECT_REMARKS_MASTER_BY_WEC_TYPE;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,item);
				}
				else
				{
					sqlQuery = AdminSQLC.SELECT_REMARKS_MASTER_BY_ID;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,item);
				}
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<remarksmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<remarkscode>");
					xml.append("<remarksid>");
					xml.append(rs.getObject("S_REMARKS_ID"));
					xml.append("</remarksid>\n");
					xml.append("<remarks>");
					xml.append(rs.getObject("S_DESCRIPTION").toString().replace("&", "AND"));
					xml.append("</remarks>\n");
					xml.append("<rtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</rtype>\n");
					xml.append("<wectype>");
					xml.append(rs.getObject("S_WEC_TYPE") == null ? "." : rs.getObject("S_WEC_TYPE"));
					xml.append("</wectype>\n");
					xml.append("<error>");
					xml.append(rs.getObject("S_ERROR_NO") == null ? "." : rs.getObject("S_ERROR_NO"));
					xml.append("</error>\n");
					xml.append("</remarkscode>\n");
				}
				xml.append("</remarksmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findMPByID"))
			{
				sqlQuery = AdminSQLC.SELECT_MP_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<mpmaster generated=\""+System.currentTimeMillis()+"\">\n");
				if(rs.next()){	
					xml.append("<mpcode>");
					xml.append("<mpid>");
					xml.append(rs.getObject("S_MP_ID"));
					xml.append("</mpid>\n");
					xml.append("<mpdesc>");
					xml.append(rs.getObject("S_MP_DESCR"));
					xml.append("</mpdesc>\n");
					xml.append("<mptype>");
					xml.append(rs.getObject("S_MP_TYPE"));
					xml.append("</mptype>\n");
					xml.append("<mpshow>");
					xml.append(rs.getObject("S_MP_SHOW"));
					xml.append("</mpshow>\n");
					xml.append("<readtype>");
					xml.append(rs.getObject("S_READ_type") == null ? "A" : rs.getObject("S_READ_type"));
					xml.append("</readtype>\n");
					xml.append("<mpunit>");
					xml.append(rs.getObject("S_MP_UNIT"));
					xml.append("</mpunit>\n");
					xml.append("<status>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</status>\n");
					xml.append("<seqno>");
					xml.append(rs.getObject("N_SEQ_NO"));
					xml.append("</seqno>\n");
					xml.append("<cum>");
					xml.append(rs.getObject("S_CUMULATIVE"));
					xml.append("</cum>\n");
					xml.append("</mpcode>\n");
				}
				xml.append("</mpmaster>\n");
				rs.close();
				ps.close();
			}   
			else if(action.equals("findCustomerMasterByID"))
			{
				sqlQuery = AdminSQLC.SELECT_CUSTOMER_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<custmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<custcode>");
					xml.append("<cid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</cid>\n");
					xml.append("<cname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</cname>\n");


					xml.append("<mktperson>");
					xml.append(rs.getObject("S_MARKETING_PERSON") == null ? "-" : rs.getObject("S_MARKETING_PERSON").toString().replace("&", "AND"));
					xml.append("</mktperson>\n");


					xml.append("<ccode>");
					xml.append(rs.getObject("S_SAP_CUSTOMER_CODE") == null ? "." : rs.getObject("S_SAP_CUSTOMER_CODE").toString().replace("&", "AND"));
					xml.append("</ccode>\n");
					xml.append("<ccontact>");
					xml.append(rs.getObject("S_CUSTOMER_CONTACT") == null ? "." : rs.getObject("S_CUSTOMER_CONTACT").toString().replace("&", "AND"));
					xml.append("</ccontact>\n");
					xml.append("<cphone>");
					xml.append(rs.getObject("S_PHONE_NUMBER") == null ? "." : rs.getObject("S_PHONE_NUMBER").toString().replace("&", "AND"));
					xml.append("</cphone>\n");

					xml.append("<ccell>");
					xml.append(rs.getObject("S_CELL_NUMBER") == null ? "." : rs.getObject("S_CELL_NUMBER").toString().replace("&", "AND"));
					xml.append("</ccell>\n");
					xml.append("<cfax>");
					xml.append(rs.getObject("S_FAX_NUMBER") == null ? "." : rs.getObject("S_FAX_NUMBER").toString().replace("&", "AND"));
					xml.append("</cfax>\n");
					xml.append("<cemail>");
					xml.append(rs.getObject("S_EMAIL") == null ? "." : rs.getObject("S_EMAIL").toString().replace("&", "AND"));
					xml.append("</cemail>\n");
					xml.append("<cactive>");
					xml.append(rs.getObject("S_ACTIVE") == null ? "." : rs.getObject("S_ACTIVE").toString().replace("&", "AND"));
					xml.append("</cactive>\n");
					xml.append("<shortname>");
					xml.append(rs.getObject("S_SHORT_NAME") == null ? "." : rs.getObject("S_SHORT_NAME").toString().replace("&", "AND"));
					xml.append("</shortname>\n");					
					xml.append("</custcode>\n");
				}
				xml.append("</custmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findCustomerMaster"))
			{
				sqlQuery = AdminSQLC.SELECT_CUSTOMER_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				//ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<custmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<custcode>");
					xml.append("<cid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</cid>\n");
					xml.append("<cname>");

					xml.append(escape(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND")));
					xml.append("</cname>\n");


					xml.append("<mktperson>");
					xml.append(rs.getObject("S_MARKETING_PERSON") == null ? "." : rs.getObject("S_MARKETING_PERSON").toString().replace("&", "AND"));
					xml.append("</mktperson>\n");


					xml.append("<ccode>");
					xml.append(rs.getObject("S_SAP_CUSTOMER_CODE") == null ? "." : escape(rs.getObject("S_SAP_CUSTOMER_CODE").toString().replace("&", "AND")));
					xml.append("</ccode>\n");
					xml.append("<ccontact>");
					xml.append(rs.getObject("S_CUSTOMER_CONTACT") == null ? "." : escape(rs.getObject("S_CUSTOMER_CONTACT").toString().replace("&", "AND")));
					xml.append("</ccontact>\n");
					xml.append("<cphone>");
					xml.append(rs.getObject("S_PHONE_NUMBER") == null ? "." : rs.getObject("S_PHONE_NUMBER").toString().replace("&", "AND"));
					xml.append("</cphone>\n");

					xml.append("<ccell>");
					xml.append(rs.getObject("S_CELL_NUMBER") == null ? "." : rs.getObject("S_CELL_NUMBER").toString().replace("&", "AND"));
					xml.append("</ccell>\n");
					xml.append("<cfax>");
					xml.append(rs.getObject("S_FAX_NUMBER") == null ? "." : rs.getObject("S_FAX_NUMBER").toString().replace("&", "AND"));
					xml.append("</cfax>\n");
					xml.append("<cemail>");
					xml.append(rs.getObject("S_EMAIL") == null ? "." : escape(rs.getObject("S_EMAIL").toString().replace("&", "AND")));
					xml.append("</cemail>\n");
					xml.append("<cactive>");
					xml.append(rs.getObject("S_ACTIVE") == null ? "." : rs.getObject("S_ACTIVE").toString().replace("&", "AND"));
					xml.append("</cactive>\n");


					xml.append("</custcode>\n");
				}
				xml.append("</custmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findSubstationMaster"))
			{
				sqlQuery = AdminSQLC.SELECT_SUBSTATION_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<substation generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<substationcode>");

					xml.append("<substationid>");
					xml.append(rs.getObject("S_SUBSTATION_ID"));
					xml.append("</substationid>\n");

					xml.append("<substationname>");					
					xml.append(escape(rs.getObject("S_SUBSTATION_DESC") == null ? "." : rs.getObject("S_SUBSTATION_DESC").toString().replace("&", "AND")));
					xml.append("</substationname>\n");					

					xml.append("<subbelongsto>");
					xml.append(rs.getObject("S_SUBSTATION_OF") == null ? "." : rs.getObject("S_SUBSTATION_OF").toString().replace("&", "AND"));
					xml.append("</subbelongsto>\n");	

					xml.append("<substationcap>");
					xml.append(rs.getObject("S_SUBTATION_CAP") == null ? "." : rs.getObject("S_SUBTATION_CAP").toString());
					xml.append("</substationcap>\n");

					xml.append("<substationmva>");
					xml.append(rs.getObject("S_SUBSTATION_MVA") == null ? "." : rs.getObject("S_SUBSTATION_MVA").toString());
					xml.append("</substationmva>\n");

					xml.append("<substationhv>");
					xml.append(rs.getObject("S_SUBSTATION_HV") == null ? "." : rs.getObject("S_SUBSTATION_HV").toString());
					xml.append("</substationhv>\n");

					xml.append("<substationlv>");
					xml.append(rs.getObject("S_SUBSTATION_LV") == null ? "." : rs.getObject("S_SUBSTATION_LV").toString());
					xml.append("</substationlv>\n");

					xml.append("<totalfeeder>");					
					xml.append(rs.getObject("TOTAL_FEEDER") == null ? "." : rs.getObject("TOTAL_FEEDER").toString());
					xml.append("</totalfeeder>\n");

					xml.append("</substationcode>\n");
				}
				xml.append("</substation>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findSubstationMasterByID"))
			{
				sqlQuery = AdminSQLC.SELECT_SUBSTATION_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<substationmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<substationcode>");
					xml.append("<substationid>");
					xml.append(rs.getObject("S_SUBSTATION_ID"));
					xml.append("</substationid>\n");
					xml.append("<substationname>");
					xml.append(rs.getObject("S_SUBSTATION_DESC"));
					xml.append("</substationname>\n");
					xml.append("<substationowner>");
					xml.append(rs.getObject("S_SUBSTATION_OF"));
					xml.append("</substationowner>\n");
					xml.append("<substationcap>");
					xml.append(rs.getObject("S_SUBTATION_CAP"));
					xml.append("</substationcap>\n");
					xml.append("<substationmva>");
					xml.append(rs.getObject("S_SUBSTATION_MVA"));
					xml.append("</substationmva>\n");
					xml.append("<substationhv>");
					xml.append(rs.getObject("S_SUBSTATION_HV"));
					xml.append("</substationhv>\n");
					xml.append("<substationlv>");
					xml.append(rs.getObject("S_SUBSTATION_LV"));
					xml.append("</substationlv>\n");					
					xml.append("<areaid>");
					xml.append(rs.getObject("S_AREA_ID") == null ? "." : rs.getObject("S_AREA_ID"));
					xml.append("</areaid>\n");	
					xml.append("<areadesc>");
					xml.append(rs.getObject("S_AREA_NAME") == null ? "." : rs.getObject("S_AREA_NAME"));
					xml.append("</areadesc>\n");
					xml.append("<totalTransformer>");
					xml.append(rs.getObject("N_TTL_TRANSFORMER") == null ? "." : rs.getObject("N_TTL_TRANSFORMER"));
					xml.append("</totalTransformer>\n");	
					xml.append("<substationRemarks>");
					xml.append(rs.getObject("S_SUBSTATION_REMARKS") == null ? "." : rs.getObject("S_SUBSTATION_REMARKS"));
					xml.append("</substationRemarks>\n");
					xml.append("</substationcode>\n");
				}
				xml.append("</substationmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findEBMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String pdate=".";
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					java.sql.Date date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}    			

				sqlQuery = AdminSQLC.SELECT_EB_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<ebname>");
					xml.append(rs.getObject("S_EB_DESCRIPTION").toString().replace("&", "AND"));
					xml.append("</ebname>\n");
					xml.append("<ebshort>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "AND"));
					xml.append("</ebshort>\n");
					xml.append("<ebtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</ebtype>\n");
					xml.append("<ebstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</ebstype>\n");					
					xml.append("<ebcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</ebcapacity>\n");
					xml.append("<ebmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</ebmfact>\n");
					xml.append("<ebstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</ebstatus>\n");
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<feder>");
					xml.append(rs.getObject("S_FEDERSHORT_DESCR") == null ? "." : rs.getObject("S_FEDERSHORT_DESCR").toString().replace("&", "AND"));
					xml.append("</feder>\n");
					xml.append("<custname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</custname>\n");
					xml.append("</ebcode>\n");
				}
				xml.append("</ebmaster>\n");	
				rs.close();
				ps.close();
			} 
			else if(action.equals("findPagedEBMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String Starttxt = "";
				String Endtxt = "";
				String pdate=".";
				java.sql.Date date = null;
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					Starttxt=comma[2];
					Endtxt=comma[3];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}    			

				sqlQuery = AdminSQLC.SELECT_PAGED_EB_MASTER_NEW;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt.trim());
				ps.setObject(2,date);
				ps.setObject(3,Endtxt.trim());
				ps.setObject(4,Starttxt.trim());
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<ebcode>");
					xml.append("<rowno>");
					xml.append(rs.getObject("R"));
					xml.append("</rowno>\n");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<ebname>");
					xml.append(rs.getObject("S_EB_DESCRIPTION").toString().replace("&", "AND"));
					xml.append("</ebname>\n");
					xml.append("<ebshort>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "AND"));
					xml.append("</ebshort>\n");
					xml.append("<ebtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</ebtype>\n");
					xml.append("<ebstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</ebstype>\n");					
					xml.append("<ebcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</ebcapacity>\n");
					xml.append("<ebmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</ebmfact>\n");
					xml.append("<ebstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</ebstatus>\n");
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<custname>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</custname>\n");
					xml.append("</ebcode>\n");
				}
				xml.append("</ebmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findEBPaged"))
			{
				String SiteIdtxt="", Datetxt = "";
				java.sql.Date date = null;
				int exists = item.indexOf(",");
				if (exists > -1){
					String comma[]=item.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
				}
				sqlQuery = AdminSQLC.SELECT_PAGED_EB_MASTER_COUNT;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebpagemaster generated=\""+System.currentTimeMillis()+"\">\n");
				if (rs.next())
				{					
					if(rs.getObject("cnt") == null)
					{
						xml.append("<pages>");
						xml.append("<pagestart>");
						xml.append(".");
						xml.append("</pagestart>\n");
						xml.append("<pageend>");
						xml.append(".");
						xml.append("</pageend>\n");
						xml.append("</pages>\n");
					}
					else
					{
						double cnt1 = rs.getDouble("cnt") / 5;
						double cnt = java.lang.Math.round(cnt1);
						if (cnt1 > cnt)
							cnt = cnt + 1;
						if (cnt1 < 1) 
						{
							xml.append("<pages>");
							xml.append("<pagestart>");
							xml.append("1");
							xml.append("</pagestart>\n");
							xml.append("<pageend>");
							xml.append("5");
							xml.append("</pageend>\n");
							xml.append("</pages>\n");
						}
						else
						{
							int pages = 1;
							for(int i = 1;i <= cnt;i++)
							{
								xml.append("<pages>");
								xml.append("<pagestart>");
								xml.append(pages);
								xml.append("</pagestart>\n");
								xml.append("<pageend>");
								xml.append(pages + 4);
								xml.append("</pageend>\n");
								xml.append("</pages>\n");
								pages = pages + 5;
							}
						}
					}					
				}
				else
				{
					xml.append("<pages>");
					xml.append("<pagestart>");
					xml.append(".");
					xml.append("</pagestart>\n");
					xml.append("<pageend>");
					xml.append(".");
					xml.append("</pageend>\n");
					xml.append("</pages>\n");
				}				
				xml.append("</ebpagemaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findWECPaged"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				java.sql.Date date = null;
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
					sqlQuery = AdminSQLC.FIND_PAGED_WEC_BY_SITE_COUNT;
					ps = conn.prepareStatement(sqlQuery);
					ps.setObject(1,SiteIdtxt);
					ps.setObject(2,date);
					rs = ps.executeQuery();
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<wecpagemaster generated=\""+System.currentTimeMillis()+"\">\n");
					if (rs.next())
					{					
						if(rs.getObject("cnt") == null)
						{
							xml.append("<pages>");
							xml.append("<pagestart>");
							xml.append(".");
							xml.append("</pagestart>\n");
							xml.append("<pageend>");
							xml.append(".");
							xml.append("</pageend>\n");
							xml.append("</pages>\n");
						}
						else
						{
							double cnt1 = rs.getDouble("cnt") / 5;
							double cnt = java.lang.Math.round(cnt1);
							if (cnt1 > cnt)
								cnt = cnt + 1;
							if (cnt1 < 1) 
							{
								xml.append("<pages>");
								xml.append("<pagestart>");
								xml.append("1");
								xml.append("</pagestart>\n");
								xml.append("<pageend>");
								xml.append("5");
								xml.append("</pageend>\n");
								xml.append("</pages>\n");
							}
							else
							{
								int pages = 1;
								for(int i = 1;i <= cnt;i++)
								{
									xml.append("<pages>");
									xml.append("<pagestart>");
									xml.append(pages);
									xml.append("</pagestart>\n");
									xml.append("<pageend>");
									xml.append(pages + 4);
									xml.append("</pageend>\n");
									xml.append("</pages>\n");
									pages = pages + 5;
								}
							}
						}					
					}
					else
					{
						xml.append("<pages>");
						xml.append("<pagestart>");
						xml.append(".");
						xml.append("</pagestart>\n");
						xml.append("<pageend>");
						xml.append(".");
						xml.append("</pageend>\n");
						xml.append("</pages>\n");
					}				
					xml.append("</wecpagemaster>\n");	
					rs.close();
					ps.close();
				}
			}
			else if(action.equals("findFDMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String pdate=".";
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					java.sql.Date date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}    			
				sqlQuery = AdminSQLC.SELECT_FD_MASTER;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_FEDER_ID"));
					xml.append("</fdid>\n");
					xml.append("<fdname>");
					xml.append(rs.getObject("S_FEDER_DESCRIPTION"));
					xml.append("</fdname>\n");
					xml.append("<fdshort>");
					xml.append(rs.getObject("S_FEDERSHORT_DESCR"));
					xml.append("</fdshort>\n");
					xml.append("<fdtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</fdtype>\n");
					xml.append("<fdstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</fdstype>\n");					
					xml.append("<fdcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</fdcapacity>\n");
					xml.append("<fdstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</fdstatus>\n");
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<fdmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</fdmfact>\n");
					xml.append("</fdcode>\n");
				}
				xml.append("</fdmaster>\n");	
				rs.close();
				ps.close();
			} 
			else if(action.equals("findFDPaged"))
			{
				String SiteIdtxt="", Datetxt = "";
				java.sql.Date date = null;
				int exists = item.indexOf(",");
				if (exists > -1){
					String comma[]=item.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
				}
				sqlQuery = AdminSQLC.FIND_PAGED_FD_BY_SITE_COUNT;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,SiteIdtxt);
				ps.setObject(2,date);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdpagemaster generated=\""+System.currentTimeMillis()+"\">\n");
				if (rs.next())
				{					
					if(rs.getObject("cnt") == null)
					{
						xml.append("<pages>");
						xml.append("<pagestart>");
						xml.append(".");
						xml.append("</pagestart>\n");
						xml.append("<pageend>");
						xml.append(".");
						xml.append("</pageend>\n");
						xml.append("</pages>\n");
					}
					else
					{
						double cnt1 = rs.getDouble("cnt") / 5;
						double cnt = java.lang.Math.round(cnt1);
						if (cnt1 > cnt)
							cnt = cnt + 1;
						if (cnt1 < 1) 
						{
							xml.append("<pages>");
							xml.append("<pagestart>");
							xml.append("1");
							xml.append("</pagestart>\n");
							xml.append("<pageend>");
							xml.append("5");
							xml.append("</pageend>\n");
							xml.append("</pages>\n");
						}
						else
						{
							int pages = 1;
							for(int i = 1;i <= cnt;i++)
							{
								xml.append("<pages>");
								xml.append("<pagestart>");
								xml.append(pages);
								xml.append("</pagestart>\n");
								xml.append("<pageend>");
								xml.append(pages + 4);
								xml.append("</pageend>\n");
								xml.append("</pages>\n");
								pages = pages + 5;
							}
						}
					}					
				}
				else
				{
					xml.append("<pages>");
					xml.append("<pagestart>");
					xml.append(".");
					xml.append("</pagestart>\n");
					xml.append("<pageend>");
					xml.append(".");
					xml.append("</pageend>\n");
					xml.append("</pages>\n");
				}				
				xml.append("</fdpagemaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findPagedFDMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String pdate=".";
				String Starttxt="";
				String Endtxt="";
				java.sql.Date date = null;
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					Starttxt=comma[2];
					Endtxt=comma[3];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}    			
				sqlQuery = AdminSQLC.SELECT_PAGED_FD_MASTER_NEW;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1, SiteIdtxt);
				ps.setObject(2, date);
				ps.setObject(3, Endtxt);
				ps.setObject(4, Starttxt);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<fdcode>");
					xml.append("<rowno>");
					xml.append(rs.getObject("R"));
					xml.append("</rowno>\n");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_FEDER_ID"));
					xml.append("</fdid>\n");
					xml.append("<fdname>");
					xml.append(rs.getObject("S_FEDER_DESCRIPTION"));
					xml.append("</fdname>\n");
					xml.append("<fdshort>");
					xml.append(rs.getObject("S_FEDERSHORT_DESCR"));
					xml.append("</fdshort>\n");
					xml.append("<fdtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</fdtype>\n");
					xml.append("<fdstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</fdstype>\n");					
					xml.append("<fdcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</fdcapacity>\n");
					xml.append("<fdstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</fdstatus>\n");
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<fdmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</fdmfact>\n");
					xml.append("</fdcode>\n");
				}
				xml.append("</fdmaster>\n");	
				rs.close();
				ps.close();
			}
			else if(action.equals("findEBMasterByID"))
			{
				sqlQuery = AdminSQLC.SELECT_EB_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<ebname>");
					xml.append(rs.getObject("S_EB_DESCRIPTION"));
					xml.append("</ebname>\n");
					xml.append("<ebshort>");
					xml.append(rs.getObject("S_EBSHORT_DESCR"));
					xml.append("</ebshort>\n");
					xml.append("<ebtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</ebtype>\n");
					xml.append("<ebstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</ebstype>\n");
					xml.append("<ebcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</ebcapacity>\n");
					xml.append("<ebmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</ebmfact>\n");
					xml.append("<ebstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</ebstatus>\n");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_FEDER_ID") == null ? "." : rs.getObject("S_FEDER_ID"));
					xml.append("</fdid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID") == null ? "." : rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");
					xml.append("</ebcode>\n");
				}
				xml.append("</ebmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findEBFactorByEBID"))
			{
				sqlQuery = AdminSQLC.SELECT_EB_FACTOR_BY_EB_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<facid>");
					xml.append(rs.getObject("S_FACTOR_ID"));
					xml.append("</facid>\n");
					xml.append("<fromdate>");
					xml.append(format.format(rs.getObject("D_FROM_DATE")));
					xml.append("</fromdate>\n");
					xml.append("<todate>");
					xml.append(format.format(rs.getObject("D_TO_DATE")));
					xml.append("</todate>\n");
					xml.append("<ebtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</ebtype>\n");
					xml.append("<ebstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</ebstype>\n");
					xml.append("<ebcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</ebcapacity>\n");
					xml.append("<ebmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</ebmfact>\n");
					xml.append("</ebcode>\n");
				}
				xml.append("</ebmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findFDFactorByFDID"))
			{
				sqlQuery = AdminSQLC.SELECT_FD_FACTOR_BY_FD_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_FEDER_ID"));
					xml.append("</fdid>\n");
					xml.append("<facid>");
					xml.append(rs.getObject("S_FACTOR_ID"));
					xml.append("</facid>\n");
					xml.append("<fromdate>");
					xml.append(format.format(rs.getObject("D_FROM_DATE")));
					xml.append("</fromdate>\n");
					xml.append("<todate>");
					xml.append(format.format(rs.getObject("D_TO_DATE")));
					xml.append("</todate>\n");
					xml.append("<fdtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</fdtype>\n");
					xml.append("<fdstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</fdstype>\n");
					xml.append("<fdcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</fdcapacity>\n");
					xml.append("<fdmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</fdmfact>\n");
					xml.append("</fdcode>\n");
				}
				xml.append("</fdmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findEBFactorByID"))
			{
				sqlQuery = AdminSQLC.SELECT_EB_FACTOR_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<ebmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<ebcode>");
					xml.append("<ebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</ebid>\n");
					xml.append("<facid>");
					xml.append(rs.getObject("S_FACTOR_ID"));
					xml.append("</facid>\n");
					xml.append("<fromdate>");
					xml.append(format.format(rs.getObject("D_FROM_DATE")));
					xml.append("</fromdate>\n");
					xml.append("<todate>");
					xml.append(format.format(rs.getObject("D_TO_DATE")));
					xml.append("</todate>\n");
					xml.append("<ebtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</ebtype>\n");
					xml.append("<ebstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</ebstype>\n");
					xml.append("<ebcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</ebcapacity>\n");
					xml.append("<ebmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</ebmfact>\n");
					xml.append("</ebcode>\n");
				}
				xml.append("</ebmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findFDFactorByID"))
			{
				sqlQuery = AdminSQLC.SELECT_FD_FACTOR_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_FEDER_ID"));
					xml.append("</fdid>\n");
					xml.append("<facid>");
					xml.append(rs.getObject("S_FACTOR_ID"));
					xml.append("</facid>\n");
					xml.append("<fromdate>");
					xml.append(format.format(rs.getObject("D_FROM_DATE")));
					xml.append("</fromdate>\n");
					xml.append("<todate>");
					xml.append(format.format(rs.getObject("D_TO_DATE")));
					xml.append("</todate>\n");
					xml.append("<fdtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</fdtype>\n");
					xml.append("<fdstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</fdstype>\n");
					xml.append("<fdcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</fdcapacity>\n");
					xml.append("<fdmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</fdmfact>\n");
					xml.append("</fdcode>\n");
				}
				xml.append("</fdmaster>\n");	
				rs.close();
				ps.close();
			}

			else if(action.equals("findFDMasterByID"))
			{
				sqlQuery = AdminSQLC.SELECT_FD_MASTER_BY_ID;
				ps = conn.prepareStatement(sqlQuery);
				ps.setObject(1,item);
				rs = ps.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<fdmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<fdcode>");
					xml.append("<fdid>");
					xml.append(rs.getObject("S_feder_ID"));
					xml.append("</fdid>\n");
					xml.append("<fdname>");
					xml.append(rs.getObject("S_FEDER_DESCRIPTION"));
					xml.append("</fdname>\n");
					xml.append("<fdshort>");
					xml.append(rs.getObject("S_FEDERSHORT_DESCR"));
					xml.append("</fdshort>\n");
					xml.append("<fdtype>");
					xml.append(rs.getObject("S_TYPE"));
					xml.append("</fdtype>\n");
					xml.append("<fdstype>");
					xml.append(rs.getObject("S_SUB_TYPE"));
					xml.append("</fdstype>\n");
					xml.append("<fdcapacity>");
					xml.append(rs.getObject("S_CAPACITY"));
					xml.append("</fdcapacity>\n");
					xml.append("<fdstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</fdstatus>\n");
					xml.append("<fdmfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</fdmfact>\n");
					xml.append("</fdcode>\n");
				}
				xml.append("</fdmaster>\n");	
				rs.close();
				ps.close();
			}
			else if (action.equals("getloginemaster"))
			{
				sqlQuery = AdminSQLC.GET_LOGIN_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<loginmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<logincode>\n");
					xml.append("<loginid>");
					xml.append(rs.getObject("S_LOGIN_MASTER_ID"));
					xml.append("</loginid>\n");
					xml.append("<userid>");
					xml.append(rs.getObject("S_USER_ID"));
					xml.append("</userid>\n");
					xml.append("<rolename>");
					xml.append(rs.getObject("S_ROLE_NAME"));
					xml.append("</rolename>\n");
					xml.append("<logintype>");
					xml.append(rs.getObject("S_LOGIN_TYPE").toString().replace("&", "And"));
					xml.append("</logintype>\n");	
					xml.append("<desc>");
					xml.append(rs.getObject("S_LOGIN_DESCRIPTION") == null ? "" : rs.getObject("S_LOGIN_DESCRIPTION").toString().replace("&", "And").replaceAll("-", " "));
					xml.append("</desc>\n");

					xml.append("</logincode>\n");
				}
				xml.append("</loginmaster>\n");								
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getwecmaster_new"))
			{
				sqlQuery = AdminSQLC.GET_WEC_ALL_NEW;
				if (item.contains("|"))
				{
					sqlQuery = sqlQuery + " AND A.S_WEC_ID=?";
				}
				else
				{
					sqlQuery = sqlQuery + " AND A.S_EB_ID=? ORDER BY A.S_WECSHORT_DESCR";
				}
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item.replace("|", ""));
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>\n");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecebid>");
					xml.append(rs.getObject("S_EB_ID"));
					xml.append("</wecebid>\n");
					xml.append("<weccusid>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</weccusid>\n");
					xml.append("<custid>");
					xml.append(rs.getObject("S_CUSTOMER_ID"));
					xml.append("</custid>\n");
					xml.append("<wecebname>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</wecebname>\n");			      
					xml.append("<wectype>");
					xml.append(rs.getObject("S_WEC_TYPE"));
					xml.append("</wectype>\n");
					xml.append("<weccaptype>");
					xml.append(rs.getObject("N_WEC_CAPACITY"));
					xml.append("</weccaptype>\n");
					xml.append("<wecfactor>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</wecfactor>\n");
					xml.append("<desc>");  
					xml.append(rs.getObject("S_WECSHORT_DESCR") == null ? "." : rs.getObject("S_WECSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("<wecstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</wecstatus>\n");
					xml.append("<locno>");
					xml.append(rs.getObject("S_FOUND_LOC") == null ? "." : rs.getObject("S_FOUND_LOC"));
					xml.append("</locno>\n");
					xml.append("<cdate>");
					xml.append(rs.getObject("D_COMMISION_DATE") == null ? "." : format.format(rs.getObject("D_COMMISION_DATE")));
					xml.append("</cdate>\n");
					xml.append("<gencom>");
					xml.append(rs.getObject("N_GEN_COMM"));
					xml.append("</gencom>\n");
					xml.append("<macava>");
					xml.append(rs.getObject("N_MAC_AVA"));
					xml.append("</macava>\n");
					xml.append("<extava>");
					xml.append(rs.getObject("N_EXT_AVA"));
					xml.append("</extava>\n");
					xml.append("<intava>");
					xml.append(rs.getObject("N_INT_AVA"));
					xml.append("</intava>\n");
					xml.append("<fromdate>");
					xml.append(rs.getObject("D_START_DATE") == null ? "." : format.format(rs.getObject("D_START_DATE")));
					xml.append("</fromdate>\n");
					xml.append("<todate>");
					xml.append(rs.getObject("D_END_DATE") == null ? "." : format.format(rs.getObject("D_END_DATE")));
					xml.append("</todate>\n");
					xml.append("<costperunit>");
					xml.append(rs.getObject("N_COST_PER_UNIT") == null ? "1" : rs.getObject("N_COST_PER_UNIT"));
					xml.append("</costperunit>\n");			      
					xml.append("<wecformula>");
					xml.append(rs.getObject("S_FORMULA_NO"));
					xml.append("</wecformula>\n");
					xml.append("<wecastatus>");
					xml.append(rs.getObject("S_SHOW"));
					xml.append("</wecastatus>\n");
					xml.append("<wecserialno>");
					xml.append(rs.getObject("S_TECHNICAL_NO"));
					xml.append("</wecserialno>\n");
					xml.append("<guaranteetype>");
					xml.append(rs.getObject("S_GUARANTEE_TYPE"));
					xml.append("</guaranteetype>\n");
					xml.append("<customertype>");
					xml.append(rs.getObject("S_CUSTOMER_TYPE"));
					xml.append("</customertype>\n");
					xml.append("<scadadata>");
					xml.append(rs.getObject("S_SCADA_FLAG"));
					xml.append("</scadadata>\n");
					xml.append("<feederid>");
					xml.append(rs.getObject("S_FEEDER_ID") == null ? "." : rs.getObject("S_FEEDER_ID").toString());
					xml.append("</feederid>\n");
					xml.append("<siteid>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</siteid>\n");
					xml.append("<areaid>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</areaid>\n");
					xml.append("</weccode>\n");
				}

				xml.append("</wecmaster>\n");								
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("findWECMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String pdate=".";
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					java.sql.Date date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}

				sqlQuery = AdminSQLC.FIND_WEC_BY_SITE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, SiteIdtxt);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>\n");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecebid>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</wecebid>\n");			      
					xml.append("<desc>");  
					xml.append(rs.getObject("S_WECSHORT_DESCR") == null ? "." : rs.getObject("S_WECSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("<wecstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</wecstatus>\n");
					xml.append("<wectype>");
					xml.append(rs.getObject("S_WEC_TYPE"));
					xml.append("</wectype>\n");
					xml.append("<mfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</mfact>\n");
					xml.append("<locno>");
					xml.append(rs.getObject("S_FOUND_LOC") == null ? "." : rs.getObject("S_FOUND_LOC"));
					xml.append("</locno>\n");	
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");			     
					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");								
				rs.close();
				prepStmt.close();
			}    
			else if (action.equals("findWECRemarks"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String pdate=".";
				String Datetxt2 = "";
				String pdate2=".";
				int exists = s.indexOf(",");
				java.sql.Date date = null;
				java.sql.Date date2 = null;
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
					Datetxt2=comma[2];
					java.util.Date dt2 = new java.util.Date();
					dt2 = format.parse(Datetxt2);
					date2 = new java.sql.Date(dt2.getTime());
					/*java.util.Calendar now = java.util.Calendar.getInstance();
    				now.setTime(date);		
    				now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
    				pdate= DateFormat.getDateInstance().format(now.getTime());
    				pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");*/
				}else{
					SiteIdtxt=item;
				}
				if(SiteIdtxt.equals("ALL"))
				{
					sqlQuery = AdminSQLC.FIND_WEC_REMARKS_BY_ALL_SITE;
					prepStmt = conn.prepareStatement(sqlQuery);            		
					prepStmt.setDate(1, date);
					prepStmt.setDate(2, date2);
				}
				else   
				{
					sqlQuery = AdminSQLC.FIND_WEC_REMARKS_BY_SITE;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1, SiteIdtxt);
					prepStmt.setDate(2, date);
					prepStmt.setDate(3, date2);
				}

				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>\n");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecebid>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</wecebid>\n");			      
					xml.append("<desc>");  
					xml.append(rs.getObject("S_WECSHORT_DESCR") == null ? "." : rs.getObject("S_WECSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("<wecstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</wecstatus>\n");
					xml.append("<wectype>");
					xml.append(rs.getObject("S_WEC_TYPE"));
					xml.append("</wectype>\n");
					xml.append("<mfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</mfact>\n");
					xml.append("<locno>");
					xml.append(rs.getObject("S_FOUND_LOC") == null ? "." : rs.getObject("S_FOUND_LOC"));
					xml.append("</locno>\n");	
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<remarksid>");
					xml.append(rs.getObject("S_REMARKS_ID") == null ? "." : rs.getObject("S_REMARKS_ID"));
					xml.append("</remarksid>\n");
					xml.append("<remarksdesc>");
					xml.append(rs.getObject("S_REMARKS_DESC") == null ? "." : rs.getObject("S_REMARKS_DESC"));
					xml.append("</remarksdesc>\n");
					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");								
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("findPagedWECMaster"))
			{
				String s=item;
				String SiteIdtxt = "";
				String Datetxt = "";
				String Starttxt = "";
				String Endtxt = "";
				String pdate=".";
				java.sql.Date date = null;
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					SiteIdtxt=comma[0];
					Datetxt=comma[1];
					Starttxt=comma[2];
					Endtxt=comma[3];
					java.util.Date dt = new java.util.Date();
					dt = format.parse(Datetxt);
					date = new java.sql.Date(dt.getTime());
					java.util.Calendar now = java.util.Calendar.getInstance();
					now.setTime(date);		
					now.set(java.util.Calendar.DAY_OF_MONTH, now.get(java.util.Calendar.DAY_OF_MONTH)-1);
					pdate= DateFormat.getDateInstance().format(now.getTime());
					pdate=convertDateFormat(pdate,"MMM dd,yyyy","dd/MM/yyyy");
				}else{
					SiteIdtxt=item;
				}

				sqlQuery = AdminSQLC.SELECT_PAGED_WEC_MASTER;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, SiteIdtxt);
				prepStmt.setDate(2, date);
				prepStmt.setObject(3,Endtxt);
				prepStmt.setObject(4,Starttxt);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<wecmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<weccode>\n");
					xml.append("<rowno>");
					xml.append(rs.getObject("R"));
					xml.append("</rowno>\n");
					xml.append("<wecid>");
					xml.append(rs.getObject("S_WEC_ID"));
					xml.append("</wecid>\n");
					xml.append("<wecebid>");
					xml.append(rs.getObject("S_EBSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</wecebid>\n");			      
					xml.append("<desc>");  
					xml.append(rs.getObject("S_WECSHORT_DESCR") == null ? "." : rs.getObject("S_WECSHORT_DESCR").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("<wecstatus>");
					xml.append(rs.getObject("S_STATUS"));
					xml.append("</wecstatus>\n");
					xml.append("<wectype>");
					xml.append(rs.getObject("S_WEC_TYPE"));
					xml.append("</wectype>\n");
					xml.append("<mfact>");
					xml.append(rs.getObject("N_MULTI_FACTOR"));
					xml.append("</mfact>\n");
					xml.append("<locno>");
					xml.append(rs.getObject("S_FOUND_LOC") == null ? "." : rs.getObject("S_FOUND_LOC"));
					xml.append("</locno>\n");	
					xml.append("<pdate>");
					xml.append(pdate);
					xml.append("</pdate>\n");
					xml.append("<feeder>");
					xml.append(rs.getObject("S_FEDER_DESC"));
					xml.append("</feeder>\n");

					xml.append("</weccode>\n");
				}
				xml.append("</wecmaster>\n");								
				rs.close();
				prepStmt.close();
			}  
			else if (action.equals("getrolemasterbyid"))
			{
				String roleid = "";
				String roleName = "";
				String roledesc = "";        			
				sqlQuery = AdminSQLC.SEARCH_ROLE_ID_DETAILS;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					roleName = rs.getObject("s_role_name").toString();
					roledesc = rs.getObject("S_role_description") == null ? "..." : rs.getObject("S_role_description").toString();
					roleid = item;					
				}
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<rolemaster generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<rolecode code=\""+roleid+"\">\n");
				xml.append("<roleid>");
				xml.append(roleid);
				xml.append("</roleid>\n");
				xml.append("<name>");
				xml.append(roleName);
				xml.append("</name>\n");
				xml.append("<desc>");
				xml.append(roledesc);
				xml.append("</desc>\n");
				xml.append("</rolecode>\n");			    
				xml.append("</rolemaster>\n");
				rs.close();
				prepStmt.close();
			}

			else if (action.equals("getEmailActivatebyid"))
			{
				String roleid = "";
				String roleName = "";
				String roledesc = "";        			
				sqlQuery = AdminSQLC.GET_EMAIL_ACTIVATE_BY_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				if (rs.next())
				{
					roleName = rs.getObject("S_CUSTOMER_ID").toString();
					roledesc =  rs.getObject("EMAILACTIVATED").toString();
					roleid = item;					
				}
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<rolemaster generated=\""+System.currentTimeMillis()+"\">\n");
				xml.append("<rolecode code=\""+roleid+"\">\n");
				xml.append("<roleid>");
				xml.append(roleid);
				xml.append("</roleid>\n");
				xml.append("<name>");
				xml.append(roleName);
				xml.append("</name>\n");
				xml.append("<desc>");
				xml.append(roledesc);
				xml.append("</desc>\n");
				xml.append("</rolecode>\n");			    
				xml.append("</rolemaster>\n");
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getrolemaster"))
			{
				sqlQuery = AdminSQLC.GET_ROLE;
				prepStmt = conn.prepareStatement(sqlQuery);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<rolemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<rolecode>\n");
					xml.append("<roleid>");
					xml.append(rs.getObject("S_ROLE_ID"));
					xml.append("</roleid>\n");
					xml.append("<rolename>");
					xml.append(rs.getObject("S_ROLE_NAME").toString().replace("&", "And"));
					xml.append("</rolename>\n");	
					xml.append("<desc>");
					xml.append(rs.getObject("S_ROLE_DESCRIPTION") == null ? "" : rs.getObject("S_ROLE_DESCRIPTION").toString().replace("&", "And"));
					xml.append("</desc>\n");
					xml.append("</rolecode>\n");
				}
				xml.append("</rolemaster>\n");								
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("getEmailActive"))
			{
				sqlQuery = AdminSQLC.GET_EMAIL_ACTIVATE_LIST;
				prepStmt = conn.prepareStatement(sqlQuery);
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<rolemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<rolecode>\n");
					xml.append("<roleid>");
					xml.append(rs.getObject("S_LOGIN_DETAIL_ID"));
					xml.append("</roleid>\n");
					xml.append("<rolename>");
					xml.append(rs.getObject("S_CUSTOMER_NAME") == null ? "." : rs.getObject("S_CUSTOMER_NAME").toString().replace("&", "AND"));
					xml.append("</rolename>\n");	
					xml.append("<desc>");
					xml.append(rs.getObject("EMAILACTIVATED") == "0" ? "NO" : "YES");
					xml.append("</desc>\n");
					xml.append("</rolecode>\n");
				}
				xml.append("</rolemaster>\n");								
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getfullrolemaster"))
			{
				sqlQuery = AdminSQLC.GET_ROLE_TRAN_MAPPING;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<rolemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<rolecode code=\""+rs.getObject("S_ROLE_TRAN_MAPPING_ID")+"\">\n");
					xml.append("<roletranid>");
					xml.append(rs.getObject("S_ROLE_TRAN_MAPPING_ID"));
					xml.append("</roletranid>\n");
					xml.append("<tranname>");
					xml.append(rs.getObject("S_TRANSACTION_NAME").toString().replace("&", "And"));
					xml.append("</tranname>\n");	
					xml.append("<under1>");
					xml.append(rs.getObject("S_UNDER_1") == null ? "" : rs.getObject("S_UNDER_1").toString().replace("&", "And"));
					xml.append("</under1>\n");
					xml.append("<under2>");
					xml.append(rs.getObject("S_UNDER_2") == null ? " " : rs.getObject("S_UNDER_2").toString().replace("&", "And"));
					xml.append("</under2>\n");
					xml.append("<under3>");
					xml.append(rs.getObject("S_UNDER_3") == null ? " " : rs.getObject("S_UNDER_3").toString().replace("&", "And"));
					xml.append("</under3>\n");
					xml.append("</rolecode>\n");
				}
				xml.append("</rolemaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}else if(action.equals("getnonrole"))
			{
				sqlQuery = AdminSQLC.GET_TRANSACTION_NOT_ASSIGN_ROLE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<tranmaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<trancode code=\""+rs.getObject("S_TRANSACTION_ID")+"\">\n");
					xml.append("<tranid>");
					xml.append(rs.getObject("S_TRANSACTION_ID"));
					xml.append("</tranid>\n");
					xml.append("<tranname>");
					xml.append(rs.getObject("S_TRANSACTION_NAME").toString().replace("&", "And"));
					xml.append("</tranname>\n");	
					xml.append("<trandesc>");
					xml.append(rs.getObject("S_TRANSACTION_DESCRIPTION").toString().replace("&", "And"));
					xml.append("</trandesc>\n");
					xml.append("</trancode>\n");
				}
				xml.append("</tranmaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}else if(action.equals("getLocationRight"))
			{
				sqlQuery = AdminSQLC.RIGHTS_BY_USER;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lright generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<loc>");
					xml.append("<rcode>");
					xml.append(rs.getObject("S_RIGHTS_ID"));
					xml.append("</rcode>\n");
					xml.append("<empcode>");
					xml.append(rs.getObject("S_USER_ID"));
					xml.append("</empcode>\n");
					xml.append("<state>");
					xml.append(rs.getObject("S_STATE_NAME"));
					xml.append("</state>\n");		
					xml.append("<site>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</site>\n");
					xml.append("</loc>\n");
				}
				xml.append("</lright>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getareabystate"))
			{
				sqlQuery = AdminSQLC.GET_ALL_AREA_BY_STATE;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<areahead generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<areacode>");
					xml.append("<acode>");
					xml.append(rs.getObject("S_AREA_ID"));
					xml.append("</acode>\n");
					xml.append("<area>");
					xml.append(rs.getObject("S_AREA_NAME"));
					xml.append("</area>\n");
					xml.append("</areacode>\n");
				}
				xml.append("</areahead>\n");					
				//}				
				rs.close();
				prepStmt.close();        		
			} 
			else if(action.equals("getsubstationbyarea"))
			{
				sqlQuery = AdminSQLC.GET_ALL_SUBSTATION_BY_AREA;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<substationhead generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<substationcode>");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SUBSTATION_ID"));
					xml.append("</scode>\n");
					xml.append("<substation>");
					xml.append(rs.getObject("S_SUBSTATION_DESC"));
					xml.append("</substation>\n");
					xml.append("</substationcode>\n");
				}
				xml.append("</substationhead>\n");					
				//}				
				rs.close();
				prepStmt.close();        		
			}
			else if(action.equals("getfeederbysub"))
			{
				sqlQuery = AdminSQLC.GET_ALL_FEEDER_BY_SUB;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<feederhead generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<feedercode>");
					xml.append("<fcode>");
					xml.append(rs.getObject("S_FEEDER_ID"));
					xml.append("</fcode>\n");
					xml.append("<feeder>");
					xml.append(rs.getObject("S_FEEDER_DESC"));
					xml.append("</feeder>\n");
					xml.append("</feedercode>\n");
				}
				xml.append("</feederhead>\n");					
				//}				
				rs.close();
				prepStmt.close();        		
			}
			else if(action.equals("getsitebystate"))
			{
				sqlQuery = AdminSQLC.GET_ALL_SITE_BY_STATE;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<sitehead generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<sitecode>");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</scode>\n");
					xml.append("<site>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</site>\n");
					xml.append("</sitecode>\n");
				}
				xml.append("</sitehead>\n");					
				//}				
				rs.close();
				prepStmt.close();        		
			}     
			else if(action.equals("getsitebystateexclude"))
			{
				String s=item;
				String comma[]=s.split(",");
				String state=comma[0];
				String user=comma[1];
				sqlQuery = AdminSQLC.GET_ALL_SITE_BY_STATE_EXCLUDE;        		
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,user);
				prepStmt.setObject(2,state);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lright generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<loc>");
					xml.append("<scode>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</scode>\n");
					xml.append("<site>");
					xml.append(rs.getObject("S_SITE_NAME"));
					xml.append("</site>\n");
					xml.append("</loc>\n");
				}
				xml.append("</lright>\n");					
				//}				
				rs.close();
				prepStmt.close();        		
			} 
			else if(action.equals("getLocationRightemployee"))
			{
				sqlQuery = AdminSQLC.RIGHTS_EMPLOYEE_DETAIL;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<employeemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<employee>");
					xml.append("<empid>");
					xml.append(rs.getObject("S_EMPLOYEE_ID"));
					xml.append("</empid>\n");
					xml.append("<fname>");
					xml.append(rs.getObject("S_FIRST_NAME"));
					xml.append("</fname>\n");
					xml.append("<mname>");
					xml.append(rs.getObject("S_MIDDLE_NAME"));
					xml.append("</mname>\n");
					xml.append("<lname>");
					xml.append(rs.getObject("S_LAST_NAME"));
					xml.append("</lname>\n");			      
					xml.append("</employee>\n");
				}
				xml.append("</employeemaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getEmpId"))
			{
				sqlQuery = AdminSQLC.RIGHTS_EMPLOYEE_DETAIL;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<employeemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<employee>");
					xml.append("<empid>");
					xml.append(rs.getObject("S_EMPLOYEE_ID"));
					xml.append("</empid>\n");
					xml.append("<fname>");
					xml.append(rs.getObject("S_FIRST_NAME"));
					xml.append("</fname>\n");
					xml.append("<mname>");
					xml.append(rs.getObject("S_MIDDLE_NAME"));
					xml.append("</mname>\n");
					xml.append("<lname>");
					xml.append(rs.getObject("S_LAST_NAME"));
					xml.append("</lname>\n");			      
					xml.append("</employee>\n");
				}
				xml.append("</employeemaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getEmpIdLoc"))
			{  
				String s=item;
				String eid="";
				String loc = "";

				String comma[]=s.split(",");
				java.util.Date dt = new java.util.Date();
				java.sql.Date dt1 = new java.sql.Date(dt.getTime());
				loc=comma[0];
				eid=comma[1];
				sqlQuery = AdminSQLC.RIGHTS_EMPLOYEE_DETAIL_LOC_MONTHWISE;
				prepStmt = conn.prepareStatement(sqlQuery);

				prepStmt.setObject(1,loc);				
				prepStmt.setObject(2,eid);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<employeemaster generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<employee>");
					xml.append("<empid>");
					xml.append(rs.getObject("S_EMPLOYEE_ID"));
					xml.append("</empid>\n");
					xml.append("<fname>");
					xml.append(rs.getObject("S_FIRST_NAME"));
					xml.append("</fname>\n");
					xml.append("<mname>");
					xml.append(rs.getObject("S_MIDDLE_NAME") == null ? "." : rs.getObject("S_MIDDLE_NAME"));
					xml.append("</mname>\n");
					xml.append("<lname>");
					xml.append(rs.getObject("S_LAST_NAME"));
					xml.append("</lname>\n");			      
					xml.append("</employee>\n");
				}
				xml.append("</employeemaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getrightdetails"))
			{
				sqlQuery = AdminSQLC.RIGHTS_BY_USER_ID;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<lright generated=\""+System.currentTimeMillis()+"\">\n");
				while(rs.next()){	
					xml.append("<loc>");
					xml.append("<rcode>");
					xml.append(rs.getObject("S_RIGHTS_ID"));
					xml.append("</rcode>\n");
					xml.append("<empcode>");
					xml.append(rs.getObject("S_USER_ID"));
					xml.append("</empcode>\n");
					xml.append("<statecode>");
					xml.append(rs.getObject("S_STATE_ID"));
					xml.append("</statecode>\n");	
					xml.append("<sitecode>");
					xml.append(rs.getObject("S_SITE_ID"));
					xml.append("</sitecode>\n");	
					xml.append("</loc>\n");
				}
				xml.append("</lright>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}        	
			else if (action.equals("findemployee"))
			{
				sqlQuery = AdminSQLC.GET_CUSTOMER_BY_ID;
				prepStmt = conn.prepareStatement(sqlQuery,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String s = item;
				String level = "";
				int exists = s.indexOf(",");
				if (exists > -1){
					String comma[]=s.split(",");
					prepStmt.setObject(1, comma[0]);
					level = comma[1];
				}else{
					prepStmt.setObject(1,item);
				}    			
				rs = prepStmt.executeQuery();
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<customermaster generated=\""+System.currentTimeMillis()+"\">\n");
				if (rs.next()){
					rs.beforeFirst();
					while (rs.next()){	
						xml.append("<customercode>\n");				    	
						xml.append("<customerid>");
						xml.append(rs.getObject("S_USER_ID"));
						xml.append("</customerid>\n");
						xml.append("<desc>");
						xml.append(rs.getObject("S_LOGIN_DESCRIPTION"));
						xml.append("</desc>\n");
						xml.append("</customercode>\n");
					}
				}else{
					xml.append("<customercode>\n");	
					xml.append("<customerid>");
					xml.append("</customerid>\n");
					xml.append("</customercode>\n");
				}
				xml.append("</customermaster>\n");
				rs.close();
				prepStmt.close();
			}
			else if(action.equals("getnonrole"))
			{
				sqlQuery = AdminSQLC.GET_TRANSACTION_NOT_ASSIGN_ROLE;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1,item);
				rs = prepStmt.executeQuery();
				//if (rs.next())
				//{
				xml.append("<?xml version=\"1.0\"?>\n");
				xml.append("<tranmaster generated=\""+ System.currentTimeMillis() + "\">\n");
				while (rs.next()) {
					xml.append("<trancode>\n");
					xml.append("<tranid>");
					xml.append(rs.getObject("S_TRANSACTION_ID"));
					xml.append("</tranid>\n");
					xml.append("<tranname>");
					xml.append(rs.getObject("S_TRANSACTION_NAME").toString().replace("&", "And"));
					xml.append("</tranname>\n");
					xml.append("<descr>");
					xml.append(rs.getObject("S_TRANSACTION_DESCRIPTION").toString().replace("&", "And"));
					xml.append("</descr>\n");
					xml.append("</trancode>\n");
				}
				xml.append("</tranmaster>\n");					
				//}				
				rs.close();
				prepStmt.close();
			}
			else if (action.equals("delroletranmap"))
			{
				sqlQuery = AdminSQLC.DELETE_ROLE_TRAN_MAPPING;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, item);
				int iInserteddRows = prepStmt.executeUpdate();
				//conn.commit();                
				prepStmt.close();
			}   
			else if (action.equals("delocationrights"))
			{
				sqlQuery = AdminSQLC.DELETE_LOCATION_RIGHTS;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, item);
				int iInserteddRows = prepStmt.executeUpdate();
				//conn.commit();                
				prepStmt.close();
			}   
			else if(action.equals("getWECBYSiteID")){
				String s[]=item.split(",");
				if(s[1].equals("0000000001")){
					sqlQuery = 	AdminSQLC.GET_WEC_BY_SITE_ID;
					/*"SELECT * FROM TBL_SITE_MASTER WHERE S_STATE_ID in (?) ORDER BY S_SITE_NAME"*/
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,s[0]);
					rs = prepStmt.executeQuery();
					ResultSetMetaData metaData = rs.getMetaData();

					int noOfColumn = metaData.getColumnCount();
					String[] columnNames = new String[noOfColumn];
					for (int i = 1; i <= noOfColumn; i++){
						columnNames[i-1] = metaData.getColumnName(i);
					}
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<wecroot generated=\""+System.currentTimeMillis()+"\">\n");
					while(rs.next()){
						System.out.println("Processing new row....");
						xml.append("\t<weccode>\n");
						for(String colName:columnNames){
							xml.append("\t\t<" + colName + ">" + rs.getString(colName) + "</" + colName + ">\n");
						}
						xml.append("\t</weccode>\n");
					}
					xml.append("</wecroot>\n");
					System.out.println(xml);

					rs.close();
					prepStmt.close();
				}
				else{
					/*Something for NON-Admin User*/
				}
			}
			else if(action.equals("getCurtailedWECBYSiteID")){
				String s[]=item.split(",");
				if(s[1].equals("0000000001")){
					sqlQuery = 	AdminSQLC.GET_CURTAILED_WEC_BY_SITE_ID;
					prepStmt = conn.prepareStatement(sqlQuery);
					prepStmt.setObject(1,s[0]);
					rs = prepStmt.executeQuery();

					ResultSetMetaData metaData = rs.getMetaData();
					int noOfColumn = metaData.getColumnCount();
					String[] columnNames = new String[noOfColumn];
					for (int i = 1; i <= noOfColumn; i++){
						columnNames[i-1] = metaData.getColumnName(i);
					}
					xml.append("<?xml version=\"1.0\"?>\n");
					xml.append("<weccurtail generated=\""+System.currentTimeMillis()+"\">\n");
					while(rs.next()){        				
						xml.append("\t<wecdetail>\n");
						for(String colName:columnNames){
							/*For formatting Start Date and End Date in DD/MM/YYYY*/
							if(colName.equals("D_START_DATE") || colName.equals("D_END_DATE")){
								StringBuffer dateInUserFormat = new StringBuffer();
								String[] startDateSQLFormatsplit = rs.getString(colName).split("-");
								dateInUserFormat.append(startDateSQLFormatsplit[2].split(" ")[0] + "/" + startDateSQLFormatsplit[1] + "/" + startDateSQLFormatsplit[0]);
								xml.append("\t\t<" + colName + ">" + dateInUserFormat + "</" + colName + ">\n");
								continue;
							}
							xml.append("\t\t<" + colName + ">" + rs.getString(colName) + "</" + colName + ">\n");
						}
						xml.append("\t</wecdetail>\n");
					}
					xml.append("</weccurtail>\n");
					System.out.println(xml);
					rs.close();
					prepStmt.close();
				}
				else{
					/*Something for NON-Admin User*/
				}
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
		return xml.toString();
	}

	public String escape(String s) {
		s = s.replace("&", " amp ");
		s = s.replace("'", " apos ");
		s = s.replace("<", " ltthan ");
		s = s.replace(">", " gtthan ");
		s = s.replace("<BR>", "  ");
		// s = s.replace('"', "quot");

		return s;
	}
	public String  getAllloginhistory() throws Exception {


		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null; 
		String sqlQuery="";	
		String tranList="";

		try {
			//////
			sqlQuery = AdminSQLC.LOGIN_HISTORY; 
			prepStmt = conn.prepareStatement(sqlQuery);		        	
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				tranList= rs.getObject("trans").toString(); 
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

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
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

	public static String Eligible(String loginid) throws Exception {

		String eligible="1";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = conmanager.getConnection();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String sqlQuery = AdminSQLC.GET_ELIGIBLE_CUSTOMER;

		try {
			// ////System.out.println("Enter In to the getAllUser : " + sqlQuery);
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, loginid);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				int days=rs.getInt("days");
				if(days<=0)
				{
					eligible="0";
				}
			}
			prepStmt.close();
			rs.close();
		} catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} finally {
			try {
				if (prepStmt != null) prepStmt.close();
				if (rs != null) rs.close();
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				rs = null;
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}
		return eligible;
	}
	public String getLoginId(String abc){
		String logindetail = "";
		JDBCUtils conmanager = new JDBCUtils();
		try{
			Connection conn = conmanager.getConnection();
			PreparedStatement prepStmt = null;
			ResultSet rs = null;
			String sqlQuery = AdminSQLC.VIEW_LOGIN_MASTER_ID;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, abc);
			rs = prepStmt.executeQuery();
			if(rs.next())
				logindetail=rs.getObject("S_LOGIN_MASTER_ID").toString();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return logindetail;
	}

	public List<PESDataStorage> getPESStatusWECWise(){

		ArrayList<PESDataStorage> pesStatusForWECData = null;
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String wecName = "";
		String locationNo = "";
		String plantNo = "";
		String pesStatus = "";

		try{
			conn = conmanager.getConnection();
			sqlQuery = AdminSQLC.GET_PES_STATUS_REPORT_FOR_WEC;
			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();

			pesStatusForWECData = new ArrayList<PESDataStorage>();

			while (rs.next()) {

				wecName = rs.getString("WEC_NAME");
				locationNo = rs.getString("S_LOCATION_NO");
				plantNo = rs.getString("S_PLANT_NO");
				pesStatus = rs.getString("PES_SCADA_STATUS");

				pesStatusForWECData.add(new PESDataStorage(wecName, locationNo, plantNo, pesStatus));

			}
			return pesStatusForWECData;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return null;

	}

	public String activateWECForPESData(String UserId, DynaBean dynaBean) throws Exception{
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String myQuery1 = "";
		PreparedStatement myPrepareStatement1 = null;
		ResultSet myResultSet1 = null;
		String myQuery2 = "";
		PreparedStatement myPrepareStatement2 = null;
		ResultSet myResultSet2 = null;
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		boolean conflict = false;
		try{
			conn = conmanager.getConnection();
			String locationNo = (String) dynaBean.getProperty("locationNo");
			String plantNo = (String) dynaBean.getProperty("plantNo");

			//System.out.println("Location No:" + locationNo);
			//System.out.println("Plant No:" + plantNo);

			if(locationNo.equals("All")){
				System.out.println("All Location");
				myQuery1 = AdminSQLC.ACTIVATE_ALL_LOCATION_FOR_PESDATA;
				myPrepareStatement1 = conn.prepareStatement(myQuery1);
				int iInserteddRows = myPrepareStatement1.executeUpdate();

				if (iInserteddRows < 1)
					throw new Exception("DB_UPDATE_ERROR", null);
				msg = "<font class='sucessmsgtext'>PES Status for all WECs related to all locations activated Successfully!</font>";
			}
			else{
				if(plantNo.equals("All")){
					System.out.println("One Location.Many Plant");
					myQuery1 = AdminSQLC.ACTIVATE_ONE_LOCATION_ALL_PLANT_FOR_PESDATA;
					myPrepareStatement1 = conn.prepareStatement(myQuery1);
					myPrepareStatement1.setObject(1, locationNo);

					int iInserteddRows = myPrepareStatement1.executeUpdate();

					if (iInserteddRows < 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					msg = "<font class='sucessmsgtext'>PES Status for all WECs related to location no " + locationNo + " activated Successfully!!</font>";
				}
				else{
					System.out.println("One Location.One Plant");
					myQuery1 = AdminSQLC.ACTIVATE_ONE_PLANT_ONE_LOCATION_FOR_PESDATA;
					myPrepareStatement1 = conn.prepareStatement(myQuery1);
					myPrepareStatement1.setObject(1, locationNo);
					myPrepareStatement1.setObject(2, plantNo);
					int iInserteddRows = myPrepareStatement1.executeUpdate();

					if (iInserteddRows < 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					msg = "<font class='sucessmsgtext'>PES Status for one WECs related to Location No " + locationNo + " and Plant No " + plantNo + " activated Successfully!</font>";
				}
			}
			//msg = "<font class='sucessmsgtext'>WEC PES Status Activated Successfully!</font>";

			//conn.commit();

		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (myPrepareStatement1 != null)
					myPrepareStatement1.close();
				if(myResultSet1 != null){
					myResultSet1.close();
				}
				if (prepStmt != null)
					prepStmt.close();
				if(rs != null){
					rs.close();
				}
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				if (conn != null) {

					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}

		return msg;
	}

	public String deactivateWECForPESData(String UserId, DynaBean dynaBean) throws Exception{
		String msg = "";
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		PreparedStatement prepStmt = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sqlQuery = "";
		String myQuery1 = "";
		PreparedStatement myPrepareStatement1 = null;
		ResultSet myResultSet1 = null;
		String myQuery2 = "";
		PreparedStatement myPrepareStatement2 = null;
		ResultSet myResultSet2 = null;
		DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		boolean conflict = false;
		try{
			conn = conmanager.getConnection();
			String locationNo = (String) dynaBean.getProperty("locationNo");
			String plantNo = (String) dynaBean.getProperty("plantNo");

			System.out.println("Location No:" + locationNo);
			System.out.println("Plant No:" + plantNo);

			if(locationNo.equals("All")){
				System.out.println("All Location");
				myQuery1 = AdminSQLC.DEACTIVATE_ALL_LOCATION_FOR_PESDATA;
				myPrepareStatement1 = conn.prepareStatement(myQuery1);
				int iInserteddRows = myPrepareStatement1.executeUpdate();

				if (iInserteddRows < 1)
					throw new Exception("DB_UPDATE_ERROR", null);
				msg = "<font class='sucessmsgtext'>PES Status for all WECs related to all locations deactivated Successfully!</font>";
			}
			else{
				if(plantNo.equals("All")){
					System.out.println("One Location.Many Plant");
					myQuery1 = AdminSQLC.DEACTIVATE_ONE_LOCATION_ALL_PLANT_FOR_PESDATA;
					myPrepareStatement1 = conn.prepareStatement(myQuery1);
					myPrepareStatement1.setObject(1, locationNo);

					int iInserteddRows = myPrepareStatement1.executeUpdate();

					if (iInserteddRows < 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					msg = "<font class='sucessmsgtext'>PES Status for all WECs related to location no " + locationNo + " deactivated Successfully!!</font>";
				}
				else{
					System.out.println("One Location.One Plant");
					myQuery1 = AdminSQLC.DEACTIVATE_ONE_LOCATION_ONE_PLANT_FOR_PESDATA;
					myPrepareStatement1 = conn.prepareStatement(myQuery1);
					myPrepareStatement1.setObject(1, locationNo);
					myPrepareStatement1.setObject(2, plantNo);
					int iInserteddRows = myPrepareStatement1.executeUpdate();

					if (iInserteddRows < 1)
						throw new Exception("DB_UPDATE_ERROR", null);
					msg = "<font class='sucessmsgtext'>PES Status for one WECs related to Location No " + locationNo + " and Plant No " + plantNo + " deactivated Successfully!</font>";
				}
			}
			//msg = "<font class='sucessmsgtext'>WEC PES Status Activated Successfully!</font>";

			//conn.commit();

		}
		catch (SQLException sqlExp) {
			sqlExp.printStackTrace();
			Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
			throw exp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (myPrepareStatement1 != null)
					myPrepareStatement1.close();
				if(myResultSet1 != null){
					myResultSet1.close();
				}
				if (prepStmt != null)
					prepStmt.close();
				if(rs != null){
					rs.close();
				}
				if (conn != null) {
					conn.close();
					conn = null;

					conmanager.closeConnection();
					conmanager = null;
				}
			} catch (Exception e) {
				prepStmt = null;
				if (conn != null) {

					conn.close();
					conn = null;

					conmanager.closeConnection();conmanager = null;
				}
			}
		}

		return msg;
	}

	public Object getCustomerIdName() {

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<CustomerIdName> custNameId = new ArrayList<CustomerIdName>();

		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Select S_customer_id, S_Customer_Name " + 
					"from tbl_customer_master " +
					"order by S_Customer_Name " ; 

			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				custNameId.add(new CustomerIdName(rs.getString("S_Customer_ID"), rs.getString("S_CUSTOMER_NAME")));

			}
			return custNameId;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return null;

	}

	public List<WECReadingTrackerDataStorage> getWECReadingTrackerReport(DynaBean dynaBean) {

		ArrayList<WECReadingTrackerDataStorage> wecReadingTrackerData = null;
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String wecSelectedValue = (String) dynaBean.getProperty("wecSelection");
		String customerSelectedValue = (String) dynaBean.getProperty("customerSelection");
		String dateValue = (String) dynaBean.getProperty("dateSelection");
		dateValue = DateUtility.convertDateFormats(dateValue, "dd/MM/yyyy", "dd-MMM-yyyy");

		/*System.out.println("Customer Id:" + customerSelectedValue);
		System.out.println("WEC Id:" + wecSelectedValue);
		System.out.println("Date Selected:" + dateValue);*/

		boolean forAllCustomer = customerSelectedValue.equalsIgnoreCase("All");
		boolean forAllWECs = wecSelectedValue.equalsIgnoreCase("All");

		String readingDate;
		String wecName;
		String mpDescription;
		String activityType;
		String activityTime;
		String oldValue;
		String newValue;
		String modifiedBy;

		try{
			conn = conmanager.getConnection();
			if(forAllCustomer){
				sqlQuery = AdminSQLC.GET_WEC_READING_HISTORY;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, dateValue);
			}
			else if(forAllWECs){
				sqlQuery = AdminSQLC.GET_WEC_READING_HISTORY_ONE_CUSTOMER_ALL_WEC;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, dateValue);
				prepStmt.setString(2, customerSelectedValue);
			}
			else{
				sqlQuery = AdminSQLC.GET_WEC_READING_HISTORY_FOR_ONE_CUSTOMER_ONE_WEC;
				prepStmt = conn.prepareStatement(sqlQuery);
				prepStmt.setObject(1, dateValue);
				prepStmt.setString(2, customerSelectedValue);
				prepStmt.setString(3, wecSelectedValue);
			}

			rs = prepStmt.executeQuery();

			wecReadingTrackerData = new ArrayList<WECReadingTrackerDataStorage>();

			while (rs.next()) {

				readingDate = rs.getObject("D_READING_DATE").toString();
				wecName = rs.getString("S_WECSHORT_DESCR");
				mpDescription = rs.getString("S_MP_DESCR");
				activityType = rs.getString("S_ACTIVITY_NAME");
				activityTime = rs.getObject("T_ACTIVITY_TIME").toString();
				oldValue = rs.getString("S_OLD_VALUE");
				newValue = rs.getString("S_NEW_VALUE");
				modifiedBy = rs.getString("S_MODIFIED_BY");

				wecReadingTrackerData.add(new WECReadingTrackerDataStorage(readingDate, wecName, mpDescription, activityType, activityTime, oldValue, newValue, modifiedBy));
			}
			if(wecReadingTrackerData.size() == 0){
				wecReadingTrackerData.add(new WECReadingTrackerDataStorage(dateValue, "DATA NA", "DATA NA", "DATA NA", "DATA NA", "DATA NA", "DATA NA", "DATA NA"));
			}
			return wecReadingTrackerData;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return null;

	}

	public Object getLocationNo() {

		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		ArrayList<LocationMasterMapper> locationNo = new ArrayList<LocationMasterMapper>();

		try{
			conn = conmanager.getConnection();
			sqlQuery = 	"Select S_Location_No  " + 
					"From Scadadw.Tbl_Location_Master " + 
					"order by S_Location_no " ;

			prepStmt = conn.prepareStatement(sqlQuery);

			rs = prepStmt.executeQuery();
			while (rs.next()) {
				locationNo.add(new LocationMasterMapper(rs.getString("S_LOCATION_NO")));
			}
			return locationNo;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return null;

	}

	public String callPESDataInsertionProcedure(DynaBean dynaBean) {

		//ArrayList<WECReadingTrackerDataStorage> wecReadingTrackerData = null;
		JDBCUtils conmanager = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String locationNoSelectedValue = (String) dynaBean.getProperty("locationNoSelection");
		String plantNoSelectedValue = (String) dynaBean.getProperty("plantNoSelection");
		String dateValue = (String) dynaBean.getProperty("dateSelection");
		java.sql.Date dateValueInSQL = DateUtility.stringDateFormatToSQLDate(dateValue, "dd/MM/yyyy"); 

		dateValue = DateUtility.convertDateFormats(dateValue, "dd/MM/yyyy", "dd-MMM-yyyy");

		/*System.out.println("Location No:" + locationNoSelectedValue);
		System.out.println("Plant No:" + plantNoSelectedValue);
		System.out.println("Date Selected:" + dateValue);*/

		boolean forAllLocations = locationNoSelectedValue.equalsIgnoreCase("All");
		boolean forAllPlant = plantNoSelectedValue.equalsIgnoreCase("All");

		try{
			conn = conmanager.getConnection();
			if(forAllLocations){
				new InsertPESData().insertParameterDateWiseIntoWECReading(conn, dateValueInSQL);
			}
			else if(forAllPlant){
				new InsertPESData().insertParameterDateLocationWiseIntoWECReading(conn, dateValueInSQL, locationNoSelectedValue);
			}
			else{
				new InsertPESData().insertParameterDateLocationPlantWiseIntoWECReading(conn, dateValueInSQL, locationNoSelectedValue, plantNoSelectedValue);
			}

			//return wecReadingTrackerData;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
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
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return null;

	}

/*------------------------------------------------------------------------------*/	
	
	public void validateData(ArrayList<ArrayList<String>> excelData) throws GridBifurcationException, ParseException, SQLException, Exception {
		
		checkSize(excelData);
		checkDataInRange(excelData);
			//checkDataAlreadyPresent(excelData);
	}

	private void checkSize(ArrayList<ArrayList<String>> excelData) throws GridBifurcationException {
		int recordNo = 0;
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(!(eachRecord.size() == 20)){
				throw new GridBifurcationException("message.err.gridbifurcation.data.properformat", recordNo);
			}
		}
	}

	public void insertIntoWECReading(ArrayList<ArrayList<String>> excelData,String loginId) throws SQLException , Exception{
		int recordNo = 0;
		
		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";
		
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
//			System.out.println("-------9090909090---" + excelData);
			dateValue = DateUtility.convertDateFormats(eachRecord.get(0).trim(), "dd.MM.yyyy", "dd-MMM-yyyy");
//			System.out.println("WEC Name:" + eachRecord.get(1).trim());
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());
//			System.out.println("WEC ID:" + wecId);
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();
			
			insertRecordIntoWECReading(dateValue, wecId, readType, null, cumulativeGeneration, actualGeneration, "0808000022", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, cumulativeOperatingHours, actualOperatingHours, "0808000023", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, machineFault, machineFault, "0808000025", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, machineShutdown, machineShutdown, "0808000026", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultInternal, gridFaultInternal, "0808000027", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmInternal, gridShutdowmInternal, "0808000028", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, remark, gridFaultE1, gridFaultE1, "1401000001", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultE2, gridFaultE2, "1401000003", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridFaultE3, gridFaultE3, "1401000005", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE1, gridShutdowmE1, "1401000002", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE2, gridShutdowmE2, "1401000004", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, gridShutdowmE3, gridShutdowmE3, "1401000006", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, ebLoad, ebLoad, "0810000001", loginId);
			insertRecordIntoWECReading(dateValue, wecId, readType, null, specialShutdown, specialShutdown, "0810000002", loginId);
		}
	}
	
	
	
	public void insertRecordIntoWECReading(String dateValue, String wecId,
			String readType, String remark, String cumulativeValue,
			String actualValue, String mpId, String loginId) throws SQLException ,Exception{

		if("0.0".equals(cumulativeValue) || "0".equals(cumulativeValue) || "".equals(cumulativeValue)){
			if(!(mpId.equals("1401000006") || mpId.equals("1401000005") || mpId.equals("1401000004")|| mpId.equals("1401000003")|| mpId.equals("1401000002")|| mpId.equals("1401000001"))){
				return;
			}
		}
		Connection conn = null;
		JDBCUtils jdbcUtils = new JDBCUtils();
		String customerID = getCustomerIDFromWECId(wecId);
		String ebId = getEbIdFromWECId(wecId);
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
//		System.out.println("Remark:" + remark);
//		System.out.println("Mpid:" +  mpId);
//		System.out.println("Val:" + actualValue);
//		System.out.println("Loginid:" + loginId);
//		System.out.println("Cus:" + customerID );
//		System.out.println("eb:" + ebId);
		//String customerID = null;
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = 	"Merge Into Tbl_Wec_Reading T " + 
						"        Using (Select ? As D_Reading_Date,? As S_Mp_Id, ? As S_Wec_Id From Dual) S " + 
						"        On (T.S_Wec_Id = S.S_Wec_Id And T.D_Reading_Date = S.D_Reading_Date And T.S_Mp_Id = S.S_Mp_Id) " + 
						"        When Matched Then  " + 
						"          Update Set N_Value = ?, S_Last_Modified_By = ? , D_Last_Modified_Date = To_Date(Sysdate),  " + 
						"                    N_Actual_Value = ? , S_Remarks = ? " + 
						"        When Not Matched Then " + 
						"        Insert(S_Reading_Id, D_Reading_Date, S_Mp_Id, S_Wec_Id, S_Eb_Id, S_Customer_Id, N_Value,  " + 
						"              S_Created_By, D_Created_Date, S_Last_Modified_By, D_Last_Modified_Date, N_Actual_Value,  " + 
						"              S_Reading_Type, N_Publish, S_Remarks, N_Publish_Fact)  " + 
						"        Values (Scadadw.WEC_SCADA_READING_ID.Nextval ,?, ?, ?, ?, ?, ?, " + 
						"                ?, To_Date(Sysdate), ?, To_Date(Sysdate), ?, " + 
						"                ?, 1, ?, 0) " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			
			//Source Data
			prepStmt.setObject(1, dateValue);
			prepStmt.setObject(2, mpId);
			prepStmt.setObject(3, wecId);
			
			//Update Data
			prepStmt.setObject(4, cumulativeValue);
			prepStmt.setObject(5, loginId);
			prepStmt.setDouble(6, Double.parseDouble(actualValue));
			prepStmt.setObject(7, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			
			//Insert Data
			prepStmt.setObject(8, dateValue);
			prepStmt.setObject(9, mpId);
			prepStmt.setObject(10, wecId);
			prepStmt.setObject(11, ebId);
			prepStmt.setObject(12, customerID);
			prepStmt.setDouble(13, Double.parseDouble(cumulativeValue));
			
			prepStmt.setObject(14, loginId);
			prepStmt.setObject(15, loginId);
			prepStmt.setDouble(16, Double.parseDouble(actualValue));
			
			prepStmt.setObject(17, readType);
			prepStmt.setObject(18, (remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark);
			//System.out.println("00000000:::" + ((remark == null  || remark.equalsIgnoreCase("NIL")) ? null : remark));
			
			prepStmt.executeUpdate();
			
			prepStmt.close();
			
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public String getEbIdFromWECId(String wecId) throws SQLException , Exception{
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		JDBCUtils jdbcUtils = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String ebId = null;
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = 	"Select S_EB_Id " + 
						"From Customer_Meta_Data " + 
						"where S_wec_id = ? ";  

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				ebId = rs.getString("S_EB_Id");
			}
			return ebId;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getCustomerIDFromWECId(String wecId) throws SQLException, Exception {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		JDBCUtils jdbcUtils = new JDBCUtils();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String customerId = null;
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = 	"Select S_Customer_Id " + 
						"From Customer_Meta_Data " + 
						"where S_wec_id = ? " ; 

			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				customerId = rs.getString("S_Customer_Id");
			}
			return customerId;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void checkDataInRange(ArrayList<ArrayList<String>> excelData) throws ParseException, GridBifurcationException, SQLException, Exception{
		int recordNo = 0;
		
		String dateValue = "";
		String wecId = "";
		String readType = "";
		String remark = "";
		String cumulativeGeneration = "";
		String cumulativeOperatingHours = "";
		String actualGeneration = "";
		String actualOperatingHours = "";
		String machineFault = "";
		String machineShutdown = "";
		String gridFaultInternal = "";
		String gridShutdowmInternal = "";
		String gridFaultE1 = "";
		String gridShutdowmE1 = "";
		String gridFaultE2 = "";
		String gridShutdowmE2 = "";
		String gridFaultE3 = "";
		String gridShutdowmE3 = "";
		String ebLoad = "";
		String specialShutdown = "";
		
		double previousDayCumulativeGeneration = 0;
		double previousDayCumulativeOperatingHours = 0;
		int totalMinutes = 0;
		ArrayList<Double> previousDayCumulativeGenerationOperatingHr = null;
		int wecScadaStatus = 0;
		
		for (ArrayList<String> eachRecord : excelData) {
			recordNo++;
			if(recordNo == 1){
				continue;
			}
			dateValue = eachRecord.get(0).trim();
			wecId = getWECIdFromDescription(eachRecord.get(1).trim());
			
			/*Checking WEC Id for given WEC Description*/
			if(wecId == null){
				throw new GridBifurcationException("message.err.gridbifurcation.wecdata", recordNo);
			}
			readType = eachRecord.get(2).trim();
			remark = eachRecord.get(3).trim();
			cumulativeGeneration = eachRecord.get(4).trim();
			cumulativeOperatingHours = eachRecord.get(5).trim();
			actualGeneration = eachRecord.get(6).trim();
			actualOperatingHours = eachRecord.get(7).trim();
			machineFault = eachRecord.get(8).trim();
			machineShutdown = eachRecord.get(9).trim();
			gridFaultInternal = eachRecord.get(10).trim();
			gridShutdowmInternal = eachRecord.get(11).trim();
			gridFaultE1 = eachRecord.get(12).trim();
			gridShutdowmE1 = eachRecord.get(13).trim();
			gridFaultE2 = eachRecord.get(14).trim();
			gridShutdowmE2 = eachRecord.get(15).trim();
			gridFaultE3 = eachRecord.get(16).trim();
			gridShutdowmE3 = eachRecord.get(17).trim();
			ebLoad = eachRecord.get(18).trim();
			specialShutdown = eachRecord.get(19).trim();
			
			/*Checking date in 'dd.MM.yyyy' format*/
			DateUtility.dateValidatorIndd_MM_yyyy(dateValue);
			
			/*Checking date prior to today*/
			if(DateUtility.compareGivenDateWithTodayInTermsOfDays(dateValue, "dd.MM.yyyy") > -1){
				throw new GridBifurcationException("message.err.gridbifurcation.date.priortoday", recordNo);
			}
			
			/*Checking Scada Status for Cumulative generation and operating hours*/
			wecScadaStatus = getScadaStatus(wecId);

			if(wecScadaStatus == 0){
				previousDayCumulativeGenerationOperatingHr = getPreviousDayCumulativeGenerationOperatingHr(wecId, dateValue, "dd.MM.yyyy");

				//For no previous date data
				if(!(previousDayCumulativeGenerationOperatingHr.size() == 0)){
					previousDayCumulativeGeneration = previousDayCumulativeGenerationOperatingHr.get(0);
					previousDayCumulativeOperatingHours = previousDayCumulativeGenerationOperatingHr.get(1);
					
					if(previousDayCumulativeGeneration > Double.parseDouble(cumulativeGeneration)){
						throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative", recordNo);
					}
					if(previousDayCumulativeOperatingHours > Double.parseDouble(cumulativeOperatingHours)){
						throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative", recordNo);
					}
				}
			}
			else{
				if(Double.parseDouble(cumulativeGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(cumulativeOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.cumulative.scada", recordNo);
				}
				if(Double.parseDouble(actualGeneration) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.generation.oneday.scada", recordNo);
				}
				if(Double.parseDouble(actualOperatingHours) != 0){
					throw new GridBifurcationException("message.err.gridbifurcation.operatingHour.oneday.scada", recordNo);
				}
			}
			
			/*Checking for time in 24 hour format and in range 0.0 to 24.0*/
			for(int i = 8; i < eachRecord.size(); i++){
				if(!Time24HoursValidator.validate(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."))){
					throw new GridBifurcationException("message.err.gridbifurcation.time.properformat", recordNo);
				}
			}
			
			//Operating hour + Fault hour < 30
			for(int i = 7; i < eachRecord.size() - 2; i++){
				totalMinutes += Time24HoursValidator.getMinutesFrom24HrFormat(Time24HoursValidator.getIn24HrFormat(eachRecord.get(i), "."));
				//System.out.println("aaaaaaaa:" + totalMinutes);
				if(totalMinutes > 30 * 60){
					throw new GridBifurcationException("message.err.gridbifurcation.total.thirtyhour", recordNo);
				}
			}
			totalMinutes = 0;
		}
	}
	
	private ArrayList<Double>  getPreviousDayCumulativeGenerationOperatingHr(String wecId,
			String dateValue, String dateFormat) throws SQLException, Exception {

		DateTime givenDate = new DateTime(DateUtility.stringDateFormatToUtilDate(dateValue, dateFormat));
		JDBCUtils jdbcUtils = new JDBCUtils();
		Connection conn = null;
		ArrayList<Double> generationOperatingHour = new ArrayList<Double>();
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = 
					"select N_Value " + 
					"from tbl_wec_reading " + 
					"where D_reading_date between ? and ? " + 
					"and S_mp_id in ('0808000022','0808000023') " + 
					"and S_WEC_ID = ? " + 
					"order by D_reading_date desc, S_MP_ID " ;
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, givenDate.minusDays(7).toString("dd-MMM-yyyy"));
			prepStmt.setObject(2, givenDate.minusDays(1).toString("dd-MMM-yyyy"));
			prepStmt.setObject(3, wecId);
			rs = prepStmt.executeQuery();
			if(rs.next()) {
				generationOperatingHour.add(rs.getDouble(1));
				rs.next();
				generationOperatingHour.add(rs.getDouble(1));
			}
			////System.out.println("8778888888888888:" + generationOperatingHour);
			return generationOperatingHour;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private int getScadaStatus(String wecId) throws SQLException, Exception {
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		//System.out.println("WEC Id:" + wecId);
		int scadaStatus = -1;
		JDBCUtils jdbcUtils = new JDBCUtils();
		Connection conn = null;
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = 
					"Select S_SCADA_FLAG " + 
					"from tbl_wec_master " + 
					"where S_wec_id = ? ";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				scadaStatus = Integer.parseInt(rs.getString("S_SCADA_FLAG"));
			}
			return scadaStatus;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public String getWECIdFromDescription(String wecDescription) throws SQLException, Exception {
		// Select S_Wec_Id From Tbl_Wec_Master Where S_Wecshort_Descr = '&DESCR'
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		JDBCUtils jdbcUtils = new JDBCUtils();
		Connection conn = null;
		String wecID = null;
		try{
			conn = jdbcUtils.getConnection();
			sqlQuery = "Select S_WEC_ID From Customer_meta_data Where S_Wecshort_Descr in (?)";
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, wecDescription);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecID = rs.getString("S_WEC_ID");
			}
			return wecID;
		}
		finally{
			try{
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
				if(conn != null){
					conn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}
