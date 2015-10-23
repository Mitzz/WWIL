package com.enercon.customer.dao;

public class CustomerSQLC 
{
	
	public static final String GET_ALL_CUSTOMER_DETAIL =
    	"SELECT C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,A.*,REPLACE(A.S_CORRES_ADDRES,'<BR>','') S_CORRES_ADD FROM TBL_LOGIN_DETAILS A," +
    	"TBL_LOGIN_MASTER B,TBL_CUSTOMER_MASTER C WHERE "+
    	"A.S_LOGIN_MASTER_ID=B.S_USER_ID AND B.S_LOGIN_MASTER_ID=C.S_LOGIN_MASTER_ID";
	
	public static final String GET_CUSTOMER_DETAIL_BY_CUSTOMERID =
	"SELECT C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,A.*,REPLACE(A.S_CORRES_ADDRES,'<BR>','') S_CORRES_ADD " +
	"FROM TBL_LOGIN_DETAILS A,TBL_LOGIN_MASTER B,TBL_CUSTOMER_MASTER C WHERE "+
	"A.S_LOGIN_MASTER_ID=B.S_USER_ID AND B.S_LOGIN_MASTER_ID=C.S_LOGIN_MASTER_ID AND C.S_CUSTOMER_ID=?";
	
	public static final String GET_LOGIN_HISTORY_DETAIL =
	"SELECT C.S_CUSTOMER_NAME,A.*,B.S_USER_ID,B.S_PASSWORD FROM TBL_LOGIN_STATUS_HISTORY A,TBL_LOGIN_MASTER B,TBL_CUSTOMER_MASTER C " +
	"WHERE 	A.S_LOGIN_MASTER_ID=B.S_LOGIN_MASTER_ID "+
	"AND 	B.S_LOGIN_MASTER_ID=C.S_LOGIN_MASTER_ID ";
	
	public static final String GET_CUSTOMER_DETAIL_BY_STATEID =
	"SELECT  DISTINCT C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,A.*,REPLACE(A.S_CORRES_ADDRES,'<BR>','') S_CORRES_ADD "+
	"FROM TBL_LOGIN_DETAILS A,TBL_LOGIN_MASTER B,TBL_CUSTOMER_MASTER C LEFT OUTER JOIN  TBL_EB_MASTER D ON " +
	"C.S_CUSTOMER_ID=D.S_CUSTOMER_ID  LEFT  JOIN TBL_SITE_MASTER E ON D.S_SITE_ID=E.S_SITE_ID  LEFT  JOIN " +
	" TBL_STATE_MASTER F  ON E.S_STATE_ID=F.S_STATE_ID  WHERE "+
	" A.S_LOGIN_MASTER_ID=B.S_USER_ID AND B.S_LOGIN_MASTER_ID=C.S_LOGIN_MASTER_ID AND F.S_STATE_ID=?";
	
	public static final String GET_CUSTOMER_DETAIL_BY_SITEID =
		"SELECT  DISTINCT C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,A.*,REPLACE(A.S_CORRES_ADDRES,'<BR>','') S_CORRES_ADD "+
		" FROM TBL_LOGIN_DETAILS A,TBL_LOGIN_MASTER B,TBL_CUSTOMER_MASTER C LEFT OUTER JOIN  TBL_EB_MASTER D ON " +
		" C.S_CUSTOMER_ID=D.S_CUSTOMER_ID  LEFT  JOIN TBL_SITE_MASTER E ON D.S_SITE_ID=E.S_SITE_ID WHERE" +
		" A.S_LOGIN_MASTER_ID=B.S_USER_ID AND B.S_LOGIN_MASTER_ID=C.S_LOGIN_MASTER_ID AND E.S_SITE_ID=?";
		
	
	public static final String GET_STANDARD_MESSAGE =
    	"SELECT S_STANDARD_MESSAGE_ID,S_MESSAGE_HEAD,S_MESSAGE_DESCRIPTION FROM TBL_STANDARD_MESSAGE";
	
	
	public static final String SELECT_TOTAL_WEC =
		"select count(*) as cnt from tbl_wec_master  where  S_EB_ID=? AND S_STATUS=1";

	
	
	public static final String SELECT_TOTAL_WEC_ACTUAL =
		"select count(*) as cnt from VW_WEC_DATA where  S_EB_ID=? AND D_READING_DATE=?";
	
	
	public static final String GET_MESSAGE_DETAIL =
    	"SELECT S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE FROM TBL_MESSAGE_DETAIL order by D_FROM_DATE desc";
	

	public static final String GET_SENT_MESSAGE_DETAIL =
    	"SELECT  DISTINCT MP.S_MESSAGE_DETAIL_ID,MP.S_CUSTOMER_ID,CM.S_CUSTOMER_NAME,CM.S_EMAIL FROM TBL_MESSAGE_POST MP,TBL_CUSTOMER_MASTER CM," +
    	" TBL_MESSAGE_DETAIL MD WHERE MP.S_CUSTOMER_ID=CM.S_CUSTOMER_ID and MP.S_MESSAGE_DETAIL_ID= MD.S_MESSAGE_DETAIL_ID and " +
    	" MP.S_MESSAGE_DETAIL_ID=? and MD.D_FROM_DATE <= TO_CHAR(TO_DATE(SYSDATE,'dd-MM-yyyy')) AND " +
    	" MD.D_TO_DATE >= TO_CHAR(TO_DATE(SYSDATE,'dd-MM-yyyy')) ";
	
	public static final String GET_SENTTEMPMESSAGE_DETAIL =
    	"SELECT DISTINCT MP.S_MESSAGE_DETAIL_ID,MP.S_CUSTOMER_ID,CM.S_CUSTOMER_NAME,CM.S_EMAIL FROM TEMP_TBL_MESSAGE_POST MP,TBL_CUSTOMER_MASTER CM," +
    	" TBL_MESSAGE_DETAIL MD WHERE MP.S_CUSTOMER_ID=CM.S_CUSTOMER_ID and MP.S_MESSAGE_DETAIL_ID= MD.S_MESSAGE_DETAIL_ID and " +
    	" MP.S_MESSAGE_DETAIL_ID=? ";
/*	"SELECT MP.S_MESSAGE_DETAIL_ID,MP.S_CUSTOMER_ID,CM.S_CUSTOMER_NAME,CM.S_EMAIL FROM TEMP_TBL_MESSAGE_POST MP,TBL_CUSTOMER_MASTER CM," +
	" TBL_MESSAGE_DETAIL MD WHERE MP.S_CUSTOMER_ID=CM.S_CUSTOMER_ID and MP.S_MESSAGE_DETAIL_ID= MD.S_MESSAGE_DETAIL_ID and " +
	" MP.S_MESSAGE_DETAIL_ID=? ";
*/	
	public static final String GET_MESSAGE_DETAIL_BY_DATE =
    	"SELECT S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE FROM TBL_MESSAGE_DETAIL WHERE " +
    	" D_FROM_DATE <= SYSDATE AND D_TO_DATE >= SYSDATE ";
	
	public static final String GET_MESSAGE_DETAIL_BY_ID =
    	"SELECT S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE FROM TBL_MESSAGE_DETAIL WHERE S_MESSAGE_DETAIL_ID=?";
	
	public static final String DELETE_MESSAGE_DETAIL_BY_ID =
    	"DELETE FROM TBL_MESSAGE_DETAIL WHERE S_MESSAGE_DETAIL_ID=?";
	
	public static final String DELETE_MESSAGE_FROM_CUSTOMER =
    	"delete from TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID  in (select S_MESSAGE_DETAIL_ID from TBL_MESSAGE_DETAIL where " +
    	" S_MESSAGE_DETAIL_ID=? and TBL_MESSAGE_POST.S_MESSAGE_DETAIL_ID= TBL_MESSAGE_DETAIL.S_MESSAGE_DETAIL_ID and "+
    	" TBL_MESSAGE_DETAIL.D_TO_DATE < TO_CHAR(TO_DATE(SYSDATE,'dd-MM-yyyy'))) ";
	
	public static final String DELETE_MESSAGE_DETAIL_FROM_CUSTOMER =
    	"DELETE FROM TBL_MESSAGE_POST WHERE S_MESSAGE_DETAIL_ID=?";
	
	public static final String DELETE_MESSAGE_TEMP =
    	"DELETE FROM TEMP_TBL_MESSAGE_POST WHERE S_EMPLOYEE_ID=?";
	
	public static final String DELETE_MESSAGE_TEMP_BY_ID =
    	"DELETE FROM TEMP_TBL_MESSAGE_POST WHERE S_MESSAGE_DETAIL_ID=? AND S_CUSTOMER_ID=?";
	public static final String DELETE_MESSAGE_TEMP_table =
    	//"DELETE FROM TEMP_TBL_MESSAGE_POST WHERE S_MESSAGE_DETAIL_ID=? and s_customer <>'ALL'";
	      "DELETE FROM TEMP_TBL_MESSAGE_POST WHERE S_MESSAGE_DETAIL_ID=? and S_CUSTOMER_ID=? ";
	public static final String DELETE_MESSAGE_POST=
    	"DELETE FROM  tbl_message_post WHERE S_MESSAGE_DETAIL_ID IN(SELECT S_MESSAGE_DETAIL_ID from temp_tbl_message_post) AND " +
    	"  S_CUSTOMER_ID IN(SELECT S_CUSTOMER_ID from temp_tbl_message_post)";
	
	public static final String INSERT_MESSAGE_TEMP_TO_POST =
    	"insert into tbl_message_post select S_MESSAGE_DETAIL_ID,S_CUSTOMER_ID,S_EMPLOYEE_ID from temp_tbl_message_post";
	
	public static final String INSERT_MESSAGE_TEMP =
    	"INSERT INTO TEMP_TBL_MESSAGE_POST (S_MESSAGE_DETAIL_ID,S_CUSTOMER_ID,S_EMPLOYEE_ID,S_CUSTOMER,S_STATE,S_SITE) values(?,?,?,'','','')";
	
	public static final String INSERT_MESSAGE_TEMP_ALL_CUST =
    	//"insert into temp_tbl_message_post  select ?, s_CUSTOMER_id,?,'ALL','','' from TBL_CUSTOMER_MASTER";
		  "insert into temp_tbl_message_post  select  DISTINCT ?,s_CUSTOMER_id,?,'ALL','','' from TBL_CUSTOMER_MASTER where s_CUSTOMER_id=? ";
	
	public static final String INSERT_MESSAGE_TEMP_SITE =
    	"insert into temp_tbl_message_post  select DISTINCT ?,CM.s_CUSTOMER_id,?,'','',? from TBL_CUSTOMER_MASTER CM,TBL_EB_MASTER EB " +
    	" WHERE EB.s_CUSTOMER_id=CM.s_CUSTOMER_id AND EB.S_SITE_ID=? ";
	
	public static final String INSERT_MESSAGE_TEMP_STATE =
    	"insert into temp_tbl_message_post  select ?,CM.s_CUSTOMER_id,?,'',?,'' from TBL_CUSTOMER_MASTER CM,TBL_EB_MASTER EB," +
    	" TBL_SITE_MASTER SITE WHERE EB.s_CUSTOMER_id=CM.s_CUSTOMER_id AND EB.S_SITE_ID=SITE.S_SITE_ID AND SITE.S_STATE_ID=?";
	
	public static final String CHECK_MESSAGE_TEMP =
    	"select * From TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_CUSTOMER_ID=?";
	
	public static final String CHECK_MESSAGE_TEMP_CUST =
    	//"select * from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_CUSTOMER='ALL'";
			"select * from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_CUSTOMER_ID=?";
	public static final String CHECK_MESSAGE_TEMP_STATE =
		"select DISTINCT S_MESSAGE_DETAIL_ID ,S_CUSTOMER_ID ,S_EMPLOYEE_ID ,S_CUSTOMER ,S_STATE ,S_SITE from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_STATE=? and s_customer <>'ALL'";
    	
			//"select * from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_STATE=? and s_customer <>'ALL'";
	public static final String CHECK_MESSAGE_TEMP_SITE =
    	//"select * from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_SITE=? and S_State=? and s_customer <>'ALL'";
			"select * from TEMP_TBL_MESSAGE_POST where S_MESSAGE_DETAIL_ID=? and S_SITE=? ";
	
	public static final String GET_STANDARD_MESSAGE_BY_ID =
    	"SELECT S_STANDARD_MESSAGE_ID,S_MESSAGE_HEAD,S_MESSAGE_DESCRIPTION FROM TBL_STANDARD_MESSAGE WHERE S_STANDARD_MESSAGE_ID=?";
	public static final String CHECK_MESSAGE_MASTER =
    	"SELECT * FROM TBL_STANDARD_MESSAGE WHERE S_MESSAGE_HEAD = ? AND S_STANDARD_MESSAGE_ID <> ?";
	
	public static final String CHECK_LOGIN_DETAIL =
    	"SELECT * FROM TBL_LOGIN_DETAIL WHERE S_LOGIN_MASTER_ID = ?";
	
	public static final String INSERT_LOGIN_DETAIL =
		"Insert into TBL_LOGIN_DETAILS (S_LOGIN_DETAIL_ID,S_LOGIN_MASTER_ID,S_OWNER_NAME,S_OEMAIL,S_OPHONE_NUMBER,S_OCELL_NUMBER," +
		"S_OFAX_NUMBER,D_ODOB_DATE,D_ODOA_DATE,S_CONTACT_PERSON_NAME,S_CORRES_ADDRES,S_CITY,S_ZIP,S_EMAIL,S_PHONE_NUMBER," +
		"S_CELL_NUMBER,S_FAX_NUMBER,D_DOB_DATE,D_DOA_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String update_LOGIN_DETAIL =
		"update TBL_LOGIN_DETAILS set S_OWNER_NAME=?,S_OEMAIL=?,S_OPHONE_NUMBER=?,S_OCELL_NUMBER=?,S_OFAX_NUMBER=?," +
		" D_ODOB_DATE=?,D_ODOA_DATE=?,S_CONTACT_PERSON_NAME=?,S_CORRES_ADDRES=?,S_CITY=?,S_ZIP=?,S_EMAIL=?,S_PHONE_NUMBER=?,S_CELL_NUMBER=?," +
		" S_FAX_NUMBER=?,D_DOB_DATE=?,D_DOA_DATE=? where s_login_detail_id=?";

	public static final String INSERT_CUSTOMER_FEEDBACK =
		"  INSERT INTO  TBL_CUSTOMER_FEEDBACK VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
		",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,SYSDATE,'2012')";
	
	public static final String Get_Customer_Login =
    	"SELECT cs.s_customer_id FROM TBL_customer_master cs,tbl_login_master lm WHERE cs.S_Login_master_id= lm.S_Login_master_id and " +
    	" lm.s_user_id=?";
	
	public static final String CHECK_MESSAGE_DETAIL =
    	"SELECT * FROM TBL_MESSAGE_DETAIL WHERE S_MESSAGE_DESCRIPTION = ? AND S_MESSAGE_DETAIL_ID <> ?";
	
	public static final String CHECK_MESSAGE_SEND=
    	"SELECT MD.S_MESSAGE_DESCRIPTION FROM TBL_MESSAGE_DETAIL MD,TBL_MESSAGE_POST MP WHERE MP.S_CUSTOMER_ID=? AND " +
    	" MP.S_MESSAGE_DETAIL_ID=MD.S_MESSAGE_DETAIL_ID "+
        " AND  SYSDATE BETWEEN MD.D_FROM_DATE AND MD.D_TO_DATE+1";
	
	public static final String GET_CUST_STATE_WISE_CAPACITY=
	 " SELECT distinct c.s_site_id,round(e.MAVIAL,0) as MAVIAL ,a.N_COST_PER_UNIT,d.s_state_name,c.s_site_name,COUNT(*) as cnt, NVL(a.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_data e "+
	 " WHERE a.S_CUSTOMER_ID=?  and a.s_status=1   and e.s_eb_id=a.s_eb_id and e.D_READING_DATE=? AND   e.MAVIAL<95 "+
     " and a.s_eb_id=b.s_eb_id "+
     " and b.s_site_id=c.s_site_id "+
     " and c.s_state_id=d.s_state_id "+
     " GROUP BY a.N_COST_PER_UNIT,d.s_state_name, c.s_site_id, e.MAVIAL, c.s_site_name, a.N_WEC_CAPACITY order by d.s_state_name,  c.s_site_name";

	public static final String GET_CUST_STATE_WISE_CAPACITY3=
		 " SELECT distinct c.s_site_id,d.s_state_name,c.s_site_name,COUNT(*) as cnt FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_data e "+
		 " WHERE a.S_CUSTOMER_ID in(?)  and a.s_status=1   and e.s_eb_id=a.s_eb_id and e.D_READING_DATE=? AND   e.MAVIAL<95 "+
	     " and a.s_eb_id=b.s_eb_id "+
	     " and b.s_site_id=c.s_site_id "+
	     " and c.s_state_id=d.s_state_id "+
	     " GROUP BY d.s_state_name, c.s_site_id, c.s_site_name  order by d.s_state_name,  c.s_site_name";

	
	public static final String GET_CUST_STATE_WISE_CAPACITY2=
		 " SELECT distinct c.s_site_id,a.N_COST_PER_UNIT,d.s_state_name,c.s_site_name,COUNT(*) as cnt FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_data e "+
		 " WHERE a.S_CUSTOMER_ID in(?)  and a.s_status=1   and e.s_eb_id=a.s_eb_id and e.D_READING_DATE between ? and ? AND   e.MAVIAL<98 "+
	     " and a.s_eb_id=b.s_eb_id "+
	     " and b.s_site_id=c.s_site_id "+
	     " and c.s_state_id=d.s_state_id "+
	     " GROUP BY a.N_COST_PER_UNIT,d.s_state_name, c.s_site_id, c.s_site_name order by d.s_state_name,  c.s_site_name";
	
	public static final String GET_CUST_STATE_WISE_CAPACITY22=
		 " SELECT round(avg(e.MAVIAL),0) as MAVIAL ,COUNT(*) as cnt, NVL(sum(a.N_WEC_CAPACITY)*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER a ,tbl_eb_master b,tbl_site_master c, tbl_state_master d,vw_wec_data e "+
		 " WHERE a.S_CUSTOMER_ID=?  and a.s_status=1   and e.s_eb_id=a.s_eb_id and e.D_READING_DATE between ? and ?  "+
	     " and a.s_eb_id=b.s_eb_id "+
	     " and b.s_site_id=c.s_site_id "+
	     " and c.s_state_id=d.s_state_id "+
	     " GROUP BY d.s_state_name, c.s_site_id, e.MAVIAL, c.s_site_name, a.N_WEC_CAPACITY order by d.s_state_name,  c.s_site_name";
		
	
	public static final String CHECK_MESSAGE_SEND_BY_LOG_ID=
			"SELECT distinct MD.S_MESSAGE_DESCRIPTION  " +
			"FROM TBL_MESSAGE_DETAIL MD,TBL_MESSAGE_POST MP,TBL_CUSTOMER_MASTER CM  " +
			"WHERE CM.S_CUSTOMER_ID=MP.S_CUSTOMER_ID  " +
			"AND CM.S_LOGIN_MASTER_ID IN ( SELECT S_LOGIN_MASTER_ID FROM TBL_LOGIN_MASTER  WHERE S_USER_ID=?) " +
			"AND  MP.S_MESSAGE_DETAIL_ID=MD.S_MESSAGE_DETAIL_ID " +
			"AND  SYSDATE BETWEEN MD.D_FROM_DATE AND MD.D_TO_DATE+1 ";   
	
	
	public static final String GET_CUSTOMER_DETAIL =
    	"SELECT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_EMAIL FROM TBL_CUSTOMER_MASTER WHERE S_ACTIVE=1";
	
	public static final String GET_CUSTOMER_EMAIL_DETAIL =
		"select C.S_CUSTOMER_ID,C.S_CUSTOMER_NAME,A.S_EMAIL,A.S_OEMAIL from tbl_login_details a,tbl_login_master b,tbl_customer_master c where " +
		" A.ACTIVATED=1 AND A.EMAILACTIVATED=1 AND a.S_LOGIN_MASTER_ID=b.s_user_id and  c.S_LOGIN_MASTER_ID=b.S_LOGIN_MASTER_ID";
	
	public static final String INSERT_STD_MESSAGE = 
        "INSERT INTO TBL_STANDARD_MESSAGE(S_STANDARD_MESSAGE_ID,S_MESSAGE_HEAD,S_MESSAGE_DESCRIPTION,S_CREATED_BY,S_LAST_MODIFIED_By," +
        " D_CREATED_DATE,D_LAST_MODIFIED_DATE) VALUES(?,?,?,?,?,SYSDATE,SYSDATE)";
	
	public static final String INSERT_MESSAGE_DETAIL = 
        "INSERT INTO TBL_MESSAGE_DETAIL(S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE,S_CREATED_BY,S_LAST_MODIFIED_By," +
        " D_CREATED_DATE,D_LAST_MODIFIED_DATE) VALUES(?,?,?,?,?,?,SYSDATE,SYSDATE)";
	
	public static final String UPDATE_MESSAGE_DETAIL = 
        "UPDATE TBL_MESSAGE_DETAIL SET S_MESSAGE_DESCRIPTION=?,D_FROM_DATE=?,D_TO_DATE=?,S_LAST_MODIFIED_By=?,D_LAST_MODIFIED_DATE=SYSDATE " +
        " WHERE S_MESSAGE_DETAIL_ID=?";
	
	public static final String UPDATE_STD_MESSAGE = 
        "UPDATE TBL_STANDARD_MESSAGE  SET S_MESSAGE_HEAD=? ,S_MESSAGE_DESCRIPTION=? ,d_Last_Modified_Date=sysdate , s_Last_Modified_By=? " +
        " WHERE S_STANDARD_MESSAGE_ID = ? ";
    
	public static final String GET_TOTAL =
		"select eb.S_CUSTOMER_ID,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? " +
		" AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_OVERALL_TOTAL =
		"select eb.S_CUSTOMER_ID,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL, " +
		" round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  " +
		" from VW_WEC_CHKUNDERTRIAL wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? " +
		" AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_OVERALL_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,round(sum(wec.CUM_GENERATION),2) as CUM_GENERATION,round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS, " +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT, " +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) " +
		" as WAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR ,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
		" sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN"+
        " from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? AND " +
        " TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_CUSTOMER_ID";
	
	
	
	public static final String SELECT_EB_DATA_BY_CUSTOMER =
		"SELECT * FROM VW_EB_DATA WHERE S_CUSTOMER_ID=? AND D_READING_DATE BETWEEN ? AND ? ORDER BY S_CUSTOMER_ID,D_READING_DATE";
	
	
	public static final String SELECT_EB_DATA_ALL =
		"SELECT * FROM VW_EB_DATA WHERE  D_READING_DATE BETWEEN ? AND ? ORDER BY S_STATE_ID,S_SITE_ID,S_CUSTOMER_ID,D_READING_DATE";
	
	
	public static final String SELECT_EB_DATA_BY_STATE =
		"SELECT * FROM VW_EB_DATA WHERE S_STATE_ID=? AND D_READING_DATE BETWEEN ? AND ? ORDER BY S_CUSTOMER_ID,D_READING_DATE";
	
	public static final String SELECT_EB_DATA_BY_SITE =
		"SELECT * FROM VW_EB_DATA WHERE S_SITE_ID=? AND D_READING_DATE BETWEEN ? AND ? ORDER BY S_CUSTOMER_ID,D_READING_DATE";
	
	
	public static final String SELECT_MAIL_DATA =
		"select a.d_reading_date,c.s_site_name,a.s_wecshort_descr,a.GENERATION,SECTOTIME(a.OPERATINGHRS) AS OPERATINGHRS," +
		" SECTOTIME(a.EXTERNALFAULT+a.EXTERNALSD) as GRIDDOWN,SECTOTIME(a.MACHINEFAULT+a.MACHINESD) AS MACHINEDOWN  from vw_tabularform_wec a," +
		" tbl_eb_master b, tbl_site_master c where A.D_READING_DATE=?AND a.s_eb_id=b.s_eb_id AND B.S_CUSTOMER_ID=? and b.S_SITE_ID=c.S_SITE_ID " +
		" order by c.s_site_name,a.s_wecshort_descr";
	
	public static final String SELECT_MAIL_DATA_CUSTOMER =
		"select a.d_reading_date,c.s_site_name,a.s_wecshort_descr,a.GENERATION,a.OPERATINGHRS,a.LULLHRS,a.MAVIAL,a.GAVIAL,a.CFACTOR, " +
		"a.MIAVIAL as MIAVIAL,a.GIAVIAL as GIAVIAL " +
		"from VW_WEC_CHKUNDERTRIAL a,tbl_eb_master b, tbl_site_master c "+
		"where A.D_READING_DATE=? " +
		"AND a.s_eb_id=b.s_eb_id " +
		"AND B.S_CUSTOMER_ID=? " +
		"AND b.S_SITE_ID=c.S_SITE_ID " +
		"ORDER BY c.s_site_name,a.s_wecshort_descr ";
	
	public static final String SELECT_STATEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL," +
		" round(avg(wec.GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN " +
		" from VW_WEC_CHKUNDERTRIAL wec, vw_dgr_heading eb " +
		" where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(WEC.D_READING_DATE,'dd-MON-yy')=? " +
        " AND TO_CHAR(WEC.D_COMMISION_DATE,'dd-MON-yy') <= ? "+
        " group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_SUBSTATIONWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN " +
		" from VW_WEC_CHKUNDERTRIAL wec," +
		" vw_dgr_heading eb " +
		" where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";

	public static final String SELECT_STATEWISE_TOTAL_BY_LOG_ID =
		"select eb.S_CUSTOMER_NAME,eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN from VW_WEC_CHKUNDERTRIAL wec," +
		" vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_LOGIN_MASTER_ID=(SELECT S_LOGIN_MASTER_ID FROM TBL_LOGIN_MASTER WHERE S_USER_ID=?) AND TO_CHAR(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_state_ID,eb.S_state_name,eb.S_CUSTOMER_NAME,eb.S_CUSTOMER_ID order by eb.S_state_ID,eb.S_state_name";

	
	
	public static final String SELECT_STATEWISE_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		" round(sum(wec.CUM_GENERATION),2) as CUM_GENERATION, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
		" sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_STATEWISE =
		"select DISTINCT eb.S_state_ID,eb.S_state_name  from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? order by eb.S_state_name";
	
	public static final String SELECT_SITEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR, " +
		" round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN  " +
		" from VW_WEC_CHKUNDERTRIAL wec, vw_dgr_heading " +
		" eb where eb.S_EB_ID=wec.S_EB_ID  "+
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE=? and S_STATE_ID=? " +
        " AND WEC.D_COMMISION_DATE <= ? "+
        " group by eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name";
	
	public static final String SELECT_SITEWISE_TOTAL_admin =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		" round(sum(wec.GENERATION),2) as GENERATION, round(sum(wec.CUM_GENERATION),2) as CUM_GENERATION, " +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS, sectotime(sum(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, " +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2)  as CFACTOR," +
		" sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN " +
		" from VW_WEC_ADMINDATA wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
       " AND eb.S_CUSTOMER_ID=? AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? and S_STATE_ID=? " +
       " group by eb.S_CUSTOMER_ID,eb.S_site_ID, eb.S_site_name";
	
	public static final String SELECT_SITEWISE =
		"select DISTINCT eb.S_site_ID,eb.S_site_name from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
       " AND eb.S_CUSTOMER_ID=? AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? and S_STATE_ID=? order by eb.S_site_name";
	
	public static final String SELECT_WECWISE =
		"select DISTINCT  s_wec_id,S_WECSHORT_DESCR  from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
       " AND eb.S_CUSTOMER_ID=? AND TO_DATE(WEC.D_READING_DATE,'dd-MON-yy')=? and S_SITE_ID=? order by S_WECSHORT_DESCR";
	
	public static final String SELECT_STATEWISE_TOTAL_BY_ID =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2)  as CFACTOR,round(avg(wec.MIAVIAL),2)" +
		" as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_DATA wec," +
		" vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE=? and eb.S_state_ID=? group by eb.S_CUSTOMER_ID,eb.S_state_ID," +
        " eb.S_state_name";
	
	public static final String SELECT_MOVERALL_TOTAL =
		"select eb.S_CUSTOMER_ID,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		" from VW_WEC_DATA wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? " +
		" AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
		" TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_MOVERALL_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,round(max(wec.CUM_GENERATION),2) as CUM_GENERATION,round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		" sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
		" round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
		" sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
		" where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = " +
		" TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')" +
		" group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_MSITEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
		",round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,0 AS TRIALRUN  from VW_WEC_DATA wec," +
		" vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
	     " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
	     " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') and S_STATE_ID=? " +
	     " group by eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name";
	
	public static final String SELECT_MSITEWISE_TOTAL_admin =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		" round(max(wec.CUM_GENERATION),2) as CUM_GENERATION, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
		" sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from " +
		" VW_WEC_ADMINDATA  wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
	     " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
	     " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') and S_STATE_ID=? " +
	     " group by eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name";
	
	public static final String SELECT_MSITEWISE =
		"select DISTINCT eb.S_site_ID,eb.S_site_name from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
	     " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
	     " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') and S_STATE_ID=? " +
	     " order by eb.S_site_name";
	
	
	/*
	 public static final String SELECT_GET_ERDADETAIL =
		"SELECT DISTINCT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_STATE_NAME,S_SITE_NAME,S_SITE_ID FROM VW_DGR_HEADING WHERE S_CUSTOMER_ID IN ('1000000774','1000000702','1000000591') " +
		"ORDER BY S_CUSTOMER_NAME,S_STATE_NAME,S_SITE_NAME";
	 */
	
	public static final String SELECT_GET_ERDADETAIL =
		"SELECT DISTINCT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_STATE_NAME,S_STATE_ID FROM VW_DGR_HEADING WHERE S_CUSTOMER_ID IN (?) " +
		"ORDER BY S_CUSTOMER_NAME,S_STATE_NAME";	
	
	public static final String SELECT_GET_READING=
		"SELECT NVL(SUM(N_ACTUAL_VALUE),'0') AS A_VAL FROM TBL_WEC_READING WHERE S_CUSTOMER_ID=? AND S_MP_ID='0808000022' AND D_READING_DATE=? AND S_EB_ID IN" +
		" (SELECT S_EB_ID FROM TBL_EB_MASTER WHERE  S_SITE_ID=? AND S_CUSTOMER_ID=?) ";
	
	public static final String SELECT_WEC_CAPACITY=
	"SELECT NVL(N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER " +
	"WHERE S_CUSTOMER_ID=? and s_status=1   AND S_EB_ID IN (SELECT S_EB_ID FROM TBL_EB_MASTER WHERE  S_SITE_ID=? AND S_CUSTOMER_ID=?) " +
	"group by N_WEC_CAPACITY";
	
	public static final String SELECT_CUSTOMER_CAPACITY=
	"SELECT NVL(N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER "+
	 "WHERE S_CUSTOMER_ID 	in(?)  and s_status=1 GROUP BY N_WEC_CAPACITY";

	public static final String SELECT_GET_READING_CUM=
		"SELECT  NVL(SUM(N_ACTUAL_VALUE),'0') AS A_VAL FROM TBL_WEC_READING WHERE S_CUSTOMER_ID=? AND S_MP_ID='0808000022' AND D_READING_DATE BETWEEN ? AND ? AND S_EB_ID IN" +
		" (SELECT S_EB_ID FROM TBL_EB_MASTER WHERE  S_SITE_ID=? AND S_CUSTOMER_ID=?) ";
	
	
	public static final String SELECT_MWECWISE =
		"select DISTINCT  s_wec_id,S_WECSHORT_DESCR from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID  "+
	     " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
	     " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') and S_SITE_ID=? " +
	     " order by S_WECSHORT_DESCR";
	
	public static final String SELECT_MSTATEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION, " +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		" ,0 AS TRIALRUN from VW_WEC_DATA wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
        " TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
        " group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_MSTATEWISE_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, " +
		" round(max(wec.CUM_GENERATION),2) as CUM_GENERATION, round(sum(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
		" round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		" sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
	    " ,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
	    " sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
        " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
        " group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_STATE_DASH_WISE_TOTAL =
		"select eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
		" round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		" sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
	    ",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR " +
	    " from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND ? AND D_READING_DATE BETWEEN ? AND ? group by eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_MSTATEWISE =
		"select DISTINCT eb.S_state_ID,eb.S_state_name from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
        " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') order by eb.S_state_name";
	
	public static final String SELECT_YOVERALL_TOTAL =
		"select eb.S_CUSTOMER_ID,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		" from VW_WEC_DATA wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? " +
		" AND WEC.D_READING_DATE between ? and ? group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_YOVERALL_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,round(max(wec.CUM_GENERATION),2) as CUM_GENERATION,round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		" sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		" sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL," +
		" round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
		" sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA  wec,vw_dgr_heading eb " +
		" where eb.S_EB_ID=wec.S_EB_ID AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ? group by eb.S_CUSTOMER_ID";
	
	public static final String SELECT_YSTATEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		" ,0 AS TRIALRUN from VW_WEC_DATA wec," +
		" vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ?  group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_YSTATEWISE_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name, " +
		" round(max(wec.CUM_GENERATION),2) as CUM_GENERATION, round(sum(wec.GENERATION),2) as GENERATION," +
		" round(avg(wec.GIAVIAL),2) as GIAVIAL," +
		" round(avg(wec.WAVIAL),2) as WAVIAL," +
		" sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		" sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		" sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
	    " ,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
	    " sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA " +
	    " wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ?  group by eb.S_CUSTOMER_ID,eb.S_state_ID,eb.S_state_name";
	
	public static final String SELECT_YSTATEWISE =
		"select DISTINCT eb.S_state_ID,eb.S_state_name from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ?  order by eb.S_state_name";
	
	public static final String SELECT_YSITEWISE_TOTAL =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(sum(wec.GENERATION),2) as GENERATION," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2)  as CFACTOR " +
		" ,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,0 AS TRIALRUN   from VW_WEC_DATA wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ? and S_STATE_ID=? group by eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name";
	
	public static final String SELECT_YSITEWISE_TOTAL_ADMIN =
		"select eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name, round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		"round(max(wec.CUM_GENERATION),2) as CUM_GENERATION, round(sum(wec.GENERATION),2) as GENERATION," +
		"sectotime(max(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS, sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		"sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2)  as CFACTOR," +
		"sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from " +
		"VW_WEC_ADMINDATA  wec," +
		"vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ? and S_STATE_ID=? group by eb.S_CUSTOMER_ID,eb.S_site_ID,eb.S_site_name";

	public static final String SELECT_YSITEWISE =
		"select DISTINCT eb.S_site_ID,eb.S_site_name  from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ? and S_STATE_ID=? order by eb.S_site_name";
  
	public static final String SELECT_YWECWISE =
		"select DISTINCT s_wec_id,S_WECSHORT_DESCR  from vw_wec_data wec,vw_dgr_heading eb where eb.S_EB_ID=wec.S_EB_ID "+ 
        " AND eb.S_CUSTOMER_ID=? AND WEC.D_READING_DATE between ? and ? and S_SITE_ID=? order by S_WECSHORT_DESCR";
  
	
	public static final String GET_EB_HEADING_DATA=
		" SELECT SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) as phaseread,VW_DGR_HEADING.* FROM VW_DGR_HEADING " +
		" WHERE S_CUSTOMER_ID=? AND S_STATE_ID=? AND S_SITE_ID=? ORDER BY SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) " +
		" DESC";
	
	public static final String GET_EB_HEADING_DATA_STATE_WISE=
		" SELECT SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) as phaseread,VW_DGR_HEADING.* FROM VW_DGR_HEADING " +
		" WHERE S_CUSTOMER_ID=? AND S_STATE_ID=? AND S_SITE_ID <> ? ORDER BY S_SITE_NAME,SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) " +
		" DESC";	
	
	public static final String GET_WEC_CURTAILED_LIST=
		"SELECT     TBL_WEC_CURTAILMENT.S_WEC_ID, TBL_WEC_CURTAILMENT.S_NEARBY_WEC, TO_CHAR(TBL_WEC_CURTAILMENT.D_START_DATE,'DD-MON-RRRR') AS D_START_DATE, TO_CHAR(TBL_WEC_CURTAILMENT.D_END_DATE,'DD-MON-RRRR') AS D_END_DATE, (TBL_WEC_CURTAILMENT.D_END_DATE-TBL_WEC_CURTAILMENT.D_START_DATE) AS DURATION, TBL_WEC_CURTAILMENT.N_CURTAILED_CAPACITY, VW_WEC_ADMINDATA.S_WECSHORT_DESCR, SUM(VW_WEC_ADMINDATA.GENERATION) AS GENERATION, SUM(SUBSTR(VW_WEC_ADMINDATA.OPERATINGHRS,1,LENGTH(VW_WEC_ADMINDATA.OPERATINGHRS)-3)) AS OPERATINGHRS, SUBSTR(AVG(VW_WEC_ADMINDATA.CFACTOR),1,5) AS CFACTOR, SUBSTR(AVG(VW_WEC_ADMINDATA.MAVIAL),1,5) AS MAVIAL "+
		"FROM 	    TBL_WEC_CURTAILMENT, VW_WEC_ADMINDATA, TBL_WEC_MASTER	"+
		"WHERE	    TBL_WEC_MASTER.S_CUSTOMER_ID = ?	  "+
		//"AND       	VW_WEC_ADMINDATA.D_READING_DATE BETWEEN ? AND ?    "+
		"AND       	VW_WEC_ADMINDATA.D_READING_DATE BETWEEN TO_CHAR(TBL_WEC_CURTAILMENT.D_START_DATE,'DD-MON-RRRR') AND TO_CHAR(TBL_WEC_CURTAILMENT.D_END_DATE,'DD-MON-RRRR')    "+
		"AND 	    TBL_WEC_MASTER.S_WEC_ID = TBL_WEC_CURTAILMENT.S_WEC_ID 	"+
		"AND       	VW_WEC_ADMINDATA.S_WEC_ID = TBL_WEC_CURTAILMENT.S_WEC_ID	"+
		"GROUP BY 	TBL_WEC_CURTAILMENT.S_WEC_ID, TBL_WEC_CURTAILMENT.S_NEARBY_WEC, VW_WEC_ADMINDATA.S_WECSHORT_DESCR, TBL_WEC_CURTAILMENT.D_START_DATE, TBL_WEC_CURTAILMENT.D_END_DATE, TBL_WEC_CURTAILMENT.N_CURTAILED_CAPACITY";
	
	public static final String GET_NEARBY_WEC_CURTAILED_LIST=
		"SELECT     VW_WEC_ADMINDATA.S_WECSHORT_DESCR, SUM(VW_WEC_ADMINDATA.GENERATION) AS GENERATION, SUM(SUBSTR(VW_WEC_ADMINDATA.OPERATINGHRS,1,LENGTH(VW_WEC_ADMINDATA.OPERATINGHRS)-3)) AS OPERATINGHRS, SUBSTR(AVG(VW_WEC_ADMINDATA.CFACTOR),1,5) AS CFACTOR, SUBSTR(AVG(VW_WEC_ADMINDATA.MAVIAL),1,5) AS MAVIAL "+
		"FROM 	    VW_WEC_ADMINDATA	"+
		"WHERE	    VW_WEC_ADMINDATA.S_WEC_ID = ?	  "+
		"AND       	VW_WEC_ADMINDATA.D_READING_DATE BETWEEN TO_DATE(?) AND TO_DATE(?)    "+		
		"GROUP BY 	VW_WEC_ADMINDATA.S_WECSHORT_DESCR";
	
	public static final String GET_EB_HEADING_DATA_SITE=
		" SELECT b.S_CUSTOMER_NAME,B.S_TYPE,B.S_STATE_NAME,B.S_SITE_NAME,NVL(D.N_WEC_CAPACITY*COUNT(A.S_WEC_ID)/1000,0) as lcapacity,COUNT(A.S_WEC_ID) AS cnt" +
		" FROM VW_DGR_HEADING B , TBL_WEC_MASTER A, TBL_WEC_TYPE D "+
		" WHERE  B.S_CUSTOMER_ID=? AND B.S_SITE_ID=? AND  A.S_STATUS=1 AND  " +
		" A.S_EB_ID=B.S_EB_ID AND A.S_WEC_TYPE = D.S_WEC_TYPE_ID  group by b.S_CUSTOMER_NAME, B.S_TYPE, B.S_STATE_NAME, B.S_SITE_NAME,d.N_WEC_CAPACITY";
	
	public static final String GET_EB_HEADING_DATA_STATE=
		" SELECT b.S_CUSTOMER_NAME,B.S_STATE_NAME,NVL(D.N_WEC_CAPACITY*COUNT(A.S_WEC_ID)/1000,0) as lcapacity,COUNT(A.S_WEC_ID) AS cnt" +
		" FROM VW_DGR_HEADING B , TBL_WEC_MASTER A, TBL_WEC_TYPE D "+
		" WHERE  B.S_CUSTOMER_ID=? AND B.S_STATE_ID=? AND A.S_STATUS=1 AND  " +
		" A.S_EB_ID=B.S_EB_ID AND A.S_WEC_TYPE = D.S_WEC_TYPE_ID  group by b.S_CUSTOMER_NAME, B.S_STATE_NAME,d.N_WEC_CAPACITY";		
	
	public static final String GET_EB_HEADING_DATA_SiteWise=
		" SELECT SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) as phaseread,VW_DGR_HEADING.* FROM VW_DGR_HEADING " +
		" WHERE S_STATE_ID=? AND S_SITE_ID=? ORDER BY SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) DESC";
	
	public static final String GET_EB_HEADING_DATA_TOTAL=
		" SELECT SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)) as phaseread,VW_DGR_HEADING.* FROM VW_DGR_HEADING " +
		" WHERE S_CUSTOMER_ID=? ORDER BY SUBSTR(S_EBSHORT_DESCR,LENGTH(S_EBSHORT_DESCR)-1,LENGTH(S_EBSHORT_DESCR)),S_STATE_NAME,S_SITE_NAME DESC";
	 
	public static final String GET_EB_BILLING_REPORT=
		" select a.*,N_MULTI_FACTOR,TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ||'-'|| TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'YYYY') AS MYEAR," +
		" C.S_SITE_NAME,b.S_EBSHORT_DESCR  " +
		" from vw_billing_eb a,tbl_eb_master b,TBL_SITE_MASTER C where a.s_customer_id= b.s_customer_id and a.s_eb_id=b.s_eb_id and a.s_customer_id=? " +
		" and b.s_site_id=C.s_site_id and " +
		" b.s_site_id=? and TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'MM') =? and TO_CHAR(TO_DATE(a.D_READING_DATE, 'DD-MON-YY') ,'yyyy')=? ORDER BY C.S_SITE_NAME,b.S_EBSHORT_DESCR DESC";	

	public static final String GET_EB_BILLING_REPORT_STATE=
		" select a.*,N_MULTI_FACTOR,TO_CHAR(a.D_READING_DATE ,'MONTH') ||'-'|| TO_CHAR(a.D_READING_DATE,'YYYY') AS MYEAR," +
		" C.S_SITE_NAME,b.S_EBSHORT_DESCR  " +
		" from vw_billing_eb a,tbl_eb_master b,TBL_SITE_MASTER C where a.s_customer_id= b.s_customer_id and a.s_eb_id=b.s_eb_id " +
		" and a.s_customer_id=? and b.s_site_id=C.s_site_id and " +
		" c.s_state_id=? and TO_CHAR(a.D_READING_DATE ,'MM') =? and TO_CHAR(a.D_READING_DATE ,'yyyy')=? ORDER BY C.S_SITE_NAME,b.S_EBSHORT_DESCR DESC ";
	 
	public static final String GET_EB_BILLING_REPORT_1=
	"select * from vw_billing_eb  where s_eb_id=? and d_reading_date<? and rownum<=1 order by d_reading_date";

	//public static final String GET_EB_BILLING_REPORT_DATE=
	//	"select max(d_reading_date) AS d_reading_date,KWHEXPORT,KWHIMPORT,RKVAHEXPLAG,RKVAHEXPLEAD,RKVAHIMPLAG,RKVAHIMPLEAD,KVAHEXP,KVAHIMP from VW_BILLING_EB  where s_eb_id=? and d_reading_date<?  " +
	//	" GROUP BY KWHEXPORT,KWHIMPORT,RKVAHEXPLAG,RKVAHEXPLEAD,RKVAHIMPLAG,RKVAHIMPLEAD,KVAHEXP,KVAHIMP";
	
	public static final String GET_EB_BILLING_REPORT_DATE=
	"select d_reading_date, KWHEXPORT,KWHIMPORT,RKVAHEXPLAG,RKVAHEXPLEAD,RKVAHIMPLAG,RKVAHIMPLEAD,"+
	"KVAHEXP,KVAHIMP from VW_BILLING_EB  where  s_eb_id=? and "+
	"d_reading_date=(select MAX(d_reading_date) from VW_BILLING_EB  where s_eb_id=? and d_reading_date<? )";

	public static final String GET_EB_BILLING_REPORT_DATE_1=
		"select MAX(d_reading_date)+1 AS d_reading_date from TBL_EB_READING  where s_eb_id=? and d_reading_date<? AND S_READING_TYPE='M'";
	
	
	public static final String GET_WEC_GENERATION=
		"select count(*) as cnt,round(sum(n_actual_value),2) AS generation from tbl_wec_reading where s_mp_id='0808000022' and s_eb_id=? and  " +
		" d_reading_date >=? AND d_reading_date<=?" ;

	
	public static final String GET_WEC_COUNT=
		"select count(S_WEC_ID) as cnt from tbl_wec_MASTER where  s_eb_id=? and  " +
		" S_STATUS='1'" ;
	
	public static final String GET_EB_DATA=
		"SELECT DISTINCT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_AREA_NAME,S_STATE_NAME,S_SITE_ID " +
		"FROM VW_DGR_HEADING WHERE S_STATE_ID=? " +
		"ORDER BY S_AREA_NAME,S_SITE_NAME,S_CUSTOMER_NAME";
	
	public static final String GET_SUB_EB_DATA=
		"SELECT DISTINCT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_SUBSTATION_DESC,S_AREA_NAME,S_STATE_NAME,S_SITE_ID " +
		"FROM VW_SUB_DGR_HEADING WHERE S_STATE_ID=? " +
		"ORDER BY S_AREA_NAME,S_SUBSTATION_DESC,S_SITE_NAME,S_CUSTOMER_NAME";	
	
	public static final String GET_EB_DATA_BY_EBID=
		"SELECT DISTINCT S_EB_ID,S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_STATE_NAME,S_SITE_ID FROM VW_DGR_HEADING WHERE S_EB_ID=? " +
		" ORDER BY S_SITE_NAME ,S_CUSTOMER_NAME desc";
	
	public static final String GET_EB_DATA_BY_EB=
		"SELECT * FROM TBL_WEC_MASTER WHERE S_EB_ID=? AND S_STATUS=1" +
		" ORDER BY S_WECSHORT_DESCR desc";
	
	public static final String GET_EB_DATA_BY_WECID=
		"SELECT DISTINCT B.S_WEC_ID,A.S_CUSTOMER_ID,A.S_CUSTOMER_NAME,A.S_SITE_NAME,A.S_STATE_NAME,A.S_SITE_ID FROM VW_DGR_HEADING A,TBL_WEC_MASTER B WHERE B.S_EB_ID=A.S_EB_ID " +
		" AND B.S_WEC_ID=? ORDER BY A.S_SITE_NAME ,S_CUSTOMER_NAME desc";
	
	public static final String GET_SITE_DATA=
		"SELECT S_STATE_ID,S_STATE_NAME FROM TBL_STATE_MASTER   ORDER BY S_STATE_NAME";
	
	
	public static final String GET_SITE_BY_EBID=
		"SELECT A.S_STATE_ID,S_STATE_NAME FROM TBL_STATE_MASTER A,TBL_SITE_MASTER B,TBL_EB_MASTER C WHERE A.S_STATE_ID=B.S_STATE_ID AND C.S_SITE_ID=B.S_SITE_ID AND  C.S_EB_ID=?  ORDER BY S_STATE_NAME";
	
	
	public static final String GET_SITE_BY_WECID=
		"SELECT A.S_STATE_ID,S_STATE_NAME FROM TBL_STATE_MASTER A,TBL_SITE_MASTER B,TBL_EB_MASTER C,TBL_WEC_MASTER D  WHERE A.S_STATE_ID=B.S_STATE_ID AND C.S_SITE_ID=B.S_SITE_ID AND  C.S_EB_ID=D.S_EB_ID AND D.S_WEC_ID=?  ORDER BY S_STATE_NAME";
	
	
	public static final String GET_WEC_TYPE=
		"SELECT  S_WEC_TYPE||'/'||N_WEC_CAPACITY AS WECCAPACITY  FROM TBL_WEC_TYPE WHERE S_WEC_TYPE_ID=?  ORDER BY S_WEC_TYPE";
	public static final String GET_WEC_TYPE_ALL=
		"SELECT  S_WEC_TYPE||'/'||N_WEC_CAPACITY AS WECCAPACITY  FROM TBL_WEC_TYPE  ORDER BY S_WEC_TYPE DESC";
	
	public static final String GET_EB_DATA_SITE=
		"SELECT DISTINCT S_CUSTOMER_ID,S_CUSTOMER_NAME,S_SITE_NAME,S_STATE_NAME,S_SITE_ID FROM VW_DGR_HEADING WHERE S_STATE_ID=? and " +
		" S_SITE_ID=? ORDER BY S_SITE_NAME,S_CUSTOMER_NAME desc";
	
	public static final String GET_WEC_DATA_BY_SITE=
		"SELECT NVL(WM.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR " +
		" WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID   AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_SITE_ID=? and WM.S_CUSTOMER_ID=? AND WM.S_WEC_TYPE=? " +
		"group by N_WEC_CAPACITY";
	public static final String GET_SUB_WEC_DATA_BY_SITE=
		"SELECT NVL(WM.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_SUB_DGR_HEADING DGR " +
		" WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID   AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_SITE_ID=? and WM.S_CUSTOMER_ID=? AND WM.S_WEC_TYPE=? " +
		"group by N_WEC_CAPACITY";

	public static final String GET_WEC_DATA_BY_SITE_ALL=
		"SELECT '0' as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID  " +
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_SITE_ID=? and WM.S_CUSTOMER_ID=? ";
	public static final String GET_SUB_WEC_DATA_BY_SITE_ALL=
		"SELECT '0' as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_SUB_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID  " +
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_SITE_ID=? and WM.S_CUSTOMER_ID=? ";

	public static final String GET_CUSTOMER_DATA_BY_SITE_ALL=
		"SELECT S_CUSTOMER_NAME,'0' as lcapacity,COUNT(*) AS cnt,DGR.S_SITE_ID,S_SITE_NAME FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID "+ 
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_SITE_ID IN (?) and WM.S_CUSTOMER_ID=? GROUP BY S_CUSTOMER_NAME,DGR.S_SITE_ID,S_SITE_NAME order by S_SITE_NAME,S_CUSTOMER_NAME ";
	
	public static final String GET_CUSTOMER_DATA_BY_STATE_ALL=
		"SELECT S_CUSTOMER_NAME,'0' as lcapacity,COUNT(*) AS cnt,DGR.S_SITE_ID,S_SITE_NAME,WM.S_CUSTOMER_ID,DGR.S_STATE_NAME FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID "+ 
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.S_STATE_ID IN (?) and WM.S_CUSTOMER_ID=? GROUP BY S_CUSTOMER_NAME,DGR.S_SITE_ID,S_SITE_NAME,WM.S_CUSTOMER_ID,DGR.S_STATE_NAME order by S_SITE_NAME,S_CUSTOMER_NAME";
	
	
	
	public static final String GET_WEC_DATA_BY_EBID=
		"SELECT NVL(WM.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID  " +
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND DGR.s_eb_ID=? group by N_WEC_CAPACITY";

	public static final String GET_WEC_DATA_BY_WECID=
		"SELECT NVL(WM.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER WM,VW_DGR_HEADING DGR  WHERE WM.S_CUSTOMER_ID=DGR.S_CUSTOMER_ID  " +
		" AND  WM.S_eb_ID=DGR.s_eb_ID AND WM.s_WEC_ID=? group by N_WEC_CAPACITY ";
	
	public static final String GET_WEC_DATA=
		"SELECT NVL(D.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt,D.S_WEC_TYPE FROM TBL_WEC_MASTER A, TBL_WEC_TYPE D" +
		" WHERE A.S_EB_ID=? and A.S_WEC_TYPE = D.S_WEC_TYPE_ID AND " +
		" A.s_status=1 group by D.N_WEC_CAPACITY,D.S_WEC_TYPE";
	public static final String GET_WEC_DATA1=
	   "SELECT WECTYPE(?) as S_WEC_TYPE,NVL(D.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER A, TBL_WEC_TYPE D "+
	   "WHERE A.S_EB_ID=? and A.S_WEC_TYPE = D.S_WEC_TYPE_ID AND "+
	    "A.s_status=1 group by D.N_WEC_CAPACITY ";
	
	
	public static final String GET_WEC_DATA2=
		   "SELECT WECTYPE(?) as S_WEC_TYPE,NVL(D.N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity,COUNT(*) AS cnt FROM TBL_WEC_MASTER A, TBL_WEC_TYPE D,TBL_EB_MASTER E "+
		   "WHERE A.S_EB_ID=E.S_EB_ID AND E.S_CUSTOMER_ID=? AND E.S_SITE_ID=? and A.S_WEC_TYPE = D.S_WEC_TYPE_ID AND "+
		    "A.s_status=1 group by D.N_WEC_CAPACITY ";
	
	
	public static final String GET_WEC_DGR_DATA=
		"SELECT VW_WEC_CHKUNDERTRIAL.*,case WHEN (?-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		"FROM 	VW_WEC_CHKUNDERTRIAL " +
		"WHERE  S_EB_ID=? " +
		"AND 	D_READING_DATE=? " +
		"AND    D_COMMISION_DATE <= ? "+
		"ORDER BY S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGR_DATA_NEW=
			"SELECT VW_WEC_CHKUNDERTRIAL_NEW.*,case WHEN (?-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
			"FROM 	VW_WEC_CHKUNDERTRIAL_NEW " +
			"WHERE  S_EB_ID=? " +
			"AND 	D_READING_DATE=? " +
			"AND    D_COMMISION_DATE <= ? "+
			"ORDER BY S_WECSHORT_DESCR";

	public static final String GET_WEC_DGR_DATA_1=
		"SELECT c.s_site_name, a.*  FROM vw_wec_data a,tbl_eb_master b, tbl_site_master c WHERE b.S_site_ID=c.S_site_ID  and a.s_eb_id=b.s_eb_id  and b.S_site_ID=? and  a.MAVIAL< 95 and " +
		" a.D_READING_DATE=? and b.s_customer_id in(?) Order by a.S_WECSHORT_DESCR";
	public static final String GET_WEC_DGR_DATA_11=
		"SELECT distinct a.s_wec_id,a.S_WECSHORT_DESCR,c.s_site_name  FROM vw_wec_data a,tbl_eb_master b, tbl_site_master c WHERE b.S_site_ID=c.S_site_ID  and a.s_eb_id=b.s_eb_id  and b.S_site_ID=? and  " +
		" a.D_READING_DATE=? and b.s_customer_id in(?)Order by a.S_WECSHORT_DESCR";
	
	
	public static final String GET_SITE_WISEDATA_1=
		"SELECT initcap(c.s_site_name) as s_site_name,count(distinct a.s_wec_id) as cnt,round(sum(a.GENERATION),1) as GENERATION  FROM vw_wec_data a,tbl_eb_master b,TBL_SITE_MASTER c WHERE  b.S_site_ID=C.S_SITE_ID and a.s_eb_id=b.s_eb_id  and " +
		" a.D_READING_DATE=? and b.s_customer_id=? group by c.s_site_name order by c.s_site_name";
	
	/*public static final String GET_SITE_WISEDATA_2=
		" SELECT count(distinct a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION  FROM vw_wec_data a,tbl_eb_master b WHERE  b.S_site_ID=? and a.s_eb_id=b.s_eb_id  and "+
		 " a.D_READING_DATE=? and b.s_customer_id=? ";*/
	
	public static final String GET_SITE_WISEDATA_2=
		"SELECT d.s_site_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
		" nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
		" round(sum(CFACTOR)/count(a.s_wec_id),1) as CFACTOR ,round(sum(MAVIAL)/count(a.s_wec_id),1) as MAVIAL , round(sum(GAVIAL)/count(a.s_wec_id),1)  as GAVIAL  " +
		"  FROM  vw_wec_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
		" WHERE  b.S_site_ID=? and a.s_eb_id=b.s_eb_id  " +
		" and c.S_STATE_ID = d.S_STATE_ID"+
		" and b.s_site_id = d.s_site_id"+
		" and a.D_READING_DATE=? and b.s_customer_id=? group by  d.s_site_id";
	
	
	public static final String GET_SITE_WISEDATA_22=
		"SELECT a.s_wec_id,count(a.s_wec_id) as cnt,nvl(round(sum(a.GENERATION),2),0) as GENERATION," +
		"  nvl(round(sum(a.gen_rev),2),0) as GENUNIT,"+
		" round(CFACTOR,0) as CFACTOR , round(MAVIAL,0) as MAVIAL , round(GAVIAL ,0) as GAVIAL  " +
		" FROM vw_wec_data a,tbl_eb_master b, tbl_state_master c, tbl_site_master d" +
		" WHERE  b.S_site_ID=? and a.s_eb_id=b.s_eb_id  " +
		" and c.S_STATE_ID = d.S_STATE_ID"+
		" and b.s_site_id = d.s_site_id"+
		" and a.D_READING_DATE=? and b.s_customer_id in(?) group by a.s_wec_id, CFACTOR, MAVIAL, GAVIAL ";
	
	public static final String GET_SITE_WISEDATA=
		"select st.s_site_id,s_site_name,count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master st "+
		"   where wec.s_eb_id=eb.s_eb_id  and  st.s_site_id=eb.s_site_id and eb.s_customer_id in(?) and "+
        " wec.s_status IN(1,9) AND nvl(wec.D_COMMISION_DATE,?)<=? group by st.s_site_id, st.s_site_name";
	
	
	public static final String GET_CUM_DGR_DATA=
		"SELECT round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS from vw_wec_data wec "+
		" WHERE wec.S_WEC_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
		" TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') AND D_READING_DATE<=?";
	
	public static final String GET_CUM_DGR_DATA_11=
		"SELECT round(sum(a.GENERATION)/1000,2) as GENERATION," +
		" round(round((nvl(sum(a.GENERATION),2)-(nvl(sum(a.GENERATION),2)*3)/100)*a.N_COST_PER_UNIT,0)/100000,2) as GENUNITALL, " +
		" sectotime(sum(timetosecond(a.OPERATINGHRS))) as OPERATINGHRS " +
		" from vw_wec_data a , tbl_state_master c,tbl_eb_master b,tbl_site_master d "+
		" WHERE a.s_eb_id=b.s_eb_id and c.S_STATE_ID = d.S_STATE_ID and b.s_site_id = d.s_site_id " +
		" and a.S_WEC_ID=?  AND D_READING_DATE between '01-APR-2011' and ? GROUP BY a.N_COST_PER_UNIT";
	
	public static final String GET_CUM_DGR_DATA_1=
		"SELECT round(wec.GENERATION,2) as GENERATION,sectotime(timetosecond(wec.OPERATINGHRS)) as OPERATINGHRS from vw_wec_data wec "+
		" WHERE wec.S_WEC_ID=?  AND D_READING_DATE<=?";
	
	public static final String GET_CUM_DGR_DATA_121=
		"  SELECT round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS)))  as  OPERATINGHRS, "+ 
        "  round(avg(MAVIAL),2) as mavial "+		
		" from vw_wec_data wec  WHERE wec.S_WEC_ID=?  AND D_READING_DATE between ? and ? ";
	
	public static final String GET_TOTAL_CUM_DGR_DATA=
		"select  round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS from vw_wec_data wec "+
		" WHERE wec.S_EB_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
		" TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') AND D_READING_DATE<=?";
	
	
	public static final String GET_WEC_DGR_DATA_upload=
		"SELECT VW_WEC_UPLOAD_DATA.*, CASE WHEN(?-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END " +
		"AS undertrail  FROM VW_WEC_UPLOAD_DATA WHERE  S_EB_ID=? AND D_READING_DATE=? ORDER BY S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGR_ADMINDATA=
		"SELECT * FROM VW_WEC_ADMINDATA WHERE  S_EB_ID=? AND D_READING_DATE=? ORDER BY S_WECSHORT_DESCR";
	
	public static final String GET_WEC_COMPARE_DATA=
		"SELECT VW_WEC_DATA.*,CASE WHEN(?-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		"FROM VW_WEC_DATA WHERE S_WEC_ID=? AND D_READING_DATE=?";
	
	public static final String GET_WEC_DGR_DATA_WEC=
		"SELECT VW_WEC_DATA.*, CASE WHEN(?-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		"FROM VW_WEC_DATA WHERE S_EB_ID=? AND D_READING_DATE=? AND s_wec_id = ?";
	
	
	public static final String GET_WEC_DGR_DATADM=
		"SELECT  VW_WEC_CHKUNDERTRIAL.*, CASE WHEN(D_READING_DATE-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		" FROM VW_WEC_CHKUNDERTRIAL WHERE  S_EB_ID=? and TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM')" +
		" AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		" Order by D_READING_DATE,S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGR_DATAM=
		"SELECT VW_WEC_CHKUNDERTRIAL.*, CASE WHEN(D_READING_DATE-D_COMMISION_DATE)<=30 THEN 1 ELSE 0 END as undertrail " +
		" FROM  VW_WEC_CHKUNDERTRIAL WHERE  S_eb_ID=? and S_wec_ID=? and TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = " +
		" TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		" order by d_reading_date";
	
	public static final String GET_WEC_DGR_DATAADMINM=
		"SELECT * FROM VW_WEC_ADMINDATA  WHERE  S_eb_ID=? and S_wec_ID=? and TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') " +
		" = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = " +
		" TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') order by d_reading_date";

	
	public static final String GET_WEC_DGRM_DATA_WEC=
		"select TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS MONTHNAME,wec.S_WEC_ID,S_WECSHORT_DESCR,wectype, " +
		" round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR from vw_wec_data wec "+ 
		" WHERE wec.S_EB_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		" AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') and wec.S_WEC_ID=? " +
		" group by TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH'),wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype " +
		" order by S_WECSHORT_DESCR";
	 
	public static final String GET_WEC_DGRM_DATA=
		" select  wec.S_WEC_ID,max(wec.TRIALRUN) as undertrail,S_WECSHORT_DESCR,wectype,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) " +
		" as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) " +
		" as GIAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR from VW_WEC_CHKUNDERTRIAL wec "+ 
		" WHERE wec.S_EB_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
		" TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		" group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype order by S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGRM_ADMINDATA=
		" select wec.S_WEC_ID,S_WECSHORT_DESCR,wectype,round(sum(wec.GENERATION),2) as GENERATION, round(sum(wec.CUM_GENERATION),2) as CUM_GENERATION, " +
		" sectotime(sum(timetosecond(wec.FAULTHRS))) " +
		" as FAULTHRS,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		" sectotime(sum(timetosecond(wec.OPERATINGHRS)))as OPERATINGHRS, sectotime(sum(timetosecond(wec.CUM_OPERATINGHRS)))as CUM_OPERATINGHRS, " +
		" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) " +
		" as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		" sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		" sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		" round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
		" sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN " +
		" from VW_WEC_ADMINDATA wec "+ 
		" WHERE wec.S_EB_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND" +
		" TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		" group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype order by S_WECSHORT_DESCR";
	
	public static final String GET_FEEDBACK=
		"select *  from tbl_customer_feedback_2011";
	
	public static final String GET_WEC_MCOMPARE_DATA=
		"select TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS MONTHNAME,wec.S_WEC_ID,S_WECSHORT_DESCR,wectype," +
		"round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		"round(avg(wec.CFACTOR),2) as CFACTOR from VW_WEC_DATA wec "+ 
		" WHERE wec.S_wec_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
		"TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') group by " +
		"TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH'),wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype order by S_WECSHORT_DESCR";
	 
	
	public static final String GET_WEC_DGRY=
		"select wec.S_WEC_ID,S_WECSHORT_DESCR,wectype,TO_CHAR(D_READING_DATE ,'MONTH') as month ,round(sum(wec.GENERATION),2) as GENERATION," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) " +
		" as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		" from VW_WEC_DATA wec "+ 
		" WHERE wec.S_EB_ID=? and wec.S_wec_ID=? AND wec.D_READING_DATE between ? and ? group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR," +
		"wec.wectype,TO_CHAR(D_READING_DATE ,'MONTH')";
	
	public static final String GET_WEC_DGRY_ADMIN=
		"select wec.S_WEC_ID,S_WECSHORT_DESCR,wectype,TO_CHAR(D_READING_DATE ,'MONTH') as month ,round(sum(wec.GENERATION),2) as GENERATION," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		"sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD"+
	    ",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
	    "round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
	    "sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN"+
        " from VW_WEC_ADMINDATA wec "+ 
		" WHERE wec.S_EB_ID=? and wec.S_wec_ID=? AND wec.D_READING_DATE between ? and ? group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR," +
		"wec.wectype,TO_CHAR(D_READING_DATE ,'MONTH')";
	
	public static final String GET_WEC_DGRY_ADMIN1=
		"select wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype,wec.D_READING_DATE,round(sum(wec.GENERATION),2) as GENERATION," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT," +
		"sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD"+
	    ",round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
	    "round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
	    "sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN"+
        " from VW_WEC_ADMINDATA wec "+ 
		" WHERE wec.S_EB_ID=? and wec.S_wec_ID=? AND wec.D_READING_DATE between ? and ? group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR," +
		"wec.wectype,wec.D_READING_DATE order by wec.S_WECSHORT_DESCR,wec.D_READING_DATE";
	
	public static final String GET_QUERY_BY_CUSTOMER=
	     " SELECT LM.S_LOGIN_DESCRIPTION,CQ.* FROM TBL_CUSTOMER_QUERY CQ LEFT JOIN  TBL_LOGIN_MASTER LM ON CQ.S_REPLIED_BY=LM.S_USER_ID WHERE S_SUBMITTED_BY=? ORDER BY CQ.S_STATUS DESC";
	
	public static final String GET_QUERY_BY_CUSTOMER_QUERY_ID=
		"SELECT LM.S_LOGIN_DESCRIPTION,CQ.* FROM TBL_CUSTOMER_QUERY CQ LEFT JOIN  TBL_LOGIN_MASTER LM ON CQ.S_REPLIED_BY=LM.S_USER_ID WHERE S_CUSTOMER_QUERY_ID=? ORDER BY CQ.S_STATUS DESC";
	
	public static final String GET_QUERY_OF_ALL_CUSTOMER=
	     " SELECT LM.S_LOGIN_DESCRIPTION,CQ.* FROM TBL_CUSTOMER_QUERY CQ LEFT JOIN  TBL_LOGIN_MASTER LM ON CQ.S_REPLIED_BY=LM.S_USER_ID  ORDER BY CQ.S_STATUS DESC";
	
	
	public static final String GET_WEC_DGRY_MPR_ADMIN=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		"VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?  "+
		" AND wec.D_READING_DATE between ? and ? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	public static final String GET_SUB_WEC_DGRY_MPR_ADMIN=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		"VW_WEC_MPRDATA wec,vw_sub_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?  "+
		" AND wec.D_READING_DATE between ? and ? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	public static final String GET_WEC_DGRY_MPR_ADMIN_ALL=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from " +
		"VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? "+
		" AND wec.D_READING_DATE between ? and ? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	public static final String GET_WEC_DGRY_MPR_CMP_ALL=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		"VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? "+
		" AND wec.D_READING_DATE =? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	public static final String GET_WEC_DGRY_MPR_CMP_ALL_1=
		"select TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH')||'-'||TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'YY') AS MONTHNAME,S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		" VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? "+
		" AND  TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		" AND TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') group by TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH') ,TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YY'),wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	 
	public static final String GET_WEC_DGRY_MPR_CMP_ALL_2=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		" VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?"+
		" AND wec.D_READING_DATE =? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	public static final String GET_WEC_DGRY_MPR_CMP_ALL_3=
		"select TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH')||'-'||TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YY') AS MONTHNAME,S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,COUNT(*) AS CNT from " +
		" VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?"+
		" AND  TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		" AND TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') group by TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YY') ,'MONTH'),TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YY') ,wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	 
	
	
	public static final String GET_CUSTOMER_LIST=
		"SELECT B.S_CUSTOMER_NAME FROM TBL_CUSTOMER_MASTER B,TBL_LOGIN_MASTER A WHERE A.S_LOGIN_MASTER_ID=B.S_LOGIN_MASTER_ID AND A.S_USER_ID=?";
	
	public static final String GET_CUSTOMER_SITELIST=
	 " SELECT DISTINCT D.S_SITE_NAME FROM TBL_CUSTOMER_MASTER B,TBL_LOGIN_MASTER A,TBL_EB_MASTER C,TBL_SITE_MASTER D " +
	 " WHERE A.S_LOGIN_MASTER_ID=B.S_LOGIN_MASTER_ID AND B.S_CUSTOMER_ID=C.S_CUSTOMER_ID AND C.S_SITE_ID=D.S_SITE_ID AND " +
	 " A.S_USER_ID=? ORDER BY D.S_SITE_NAME";
	
	
	public static final String GET_CUSTOMER_STATELIST=
		 " SELECT DISTINCT E.S_STATE_NAME FROM TBL_CUSTOMER_MASTER B,TBL_LOGIN_MASTER A,TBL_EB_MASTER C,TBL_SITE_MASTER D,TBL_STATE_MASTER E" +
		 " WHERE A.S_LOGIN_MASTER_ID=B.S_LOGIN_MASTER_ID AND B.S_CUSTOMER_ID=C.S_CUSTOMER_ID AND C.S_SITE_ID=D.S_SITE_ID AND D.S_STATE_ID=E.S_STATE_ID AND " +
		 " A.S_USER_ID=? ORDER BY E.S_STATE_NAME";
		
	
	
	public static final String GET_CUSTOMER_WECTYPE=
		"SELECT DISTINCT C.S_WEC_TYPE FROM TBL_CUSTOMER_MASTER B,TBL_LOGIN_MASTER A,TBL_WEC_MASTER C " +
		"WHERE A.S_LOGIN_MASTER_ID=B.S_LOGIN_MASTER_ID AND B.S_CUSTOMER_ID=C.S_CUSTOMER_ID  AND A.S_USER_ID=? ORDER BY C.S_WEC_TYPE";
	
	
	
	public static final String GET_IP_ADDRESS =
		"SELECT DISTINCT S_IP_ADDRESS  FROM TBL_LOGIN_HISTORY  " +
		"  WHERE S_USER_ID=?  AND   TO_DATE(D_LOGIN_DATETIME,'DD-MON-RRRR')=? ";
	
	
	
		public static final String GET_WEC_DGRY_MPR_ADMIN_BY_WEC=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from " +
		"VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=?  "+
		" AND wec.S_WEC_ID=? AND wec.D_READING_DATE between ? and ? group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	public static final String GET_WEC_DGRY_MPR_ADMIN_BY_EB=
		"select S_WECSHORT_DESCR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS, " +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS, " +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD, " +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD, "+
		"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL " +
		"from VW_WEC_MPRDATA wec,vw_dgr_heading dgr "+ 
		"WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? "+
		"AND wec.S_EB_ID=? AND wec.D_READING_DATE between ? and ? " +
		"group by wec.S_WECSHORT_DESCR ORDER BY wec.S_WECSHORT_DESCR ";
	
	
	
	public static final String GET_FILER_DATA=
		"SELECT A.S_EB_ID,B.S_CUSTOMER_NAME,B.S_SITE_NAME,B.S_STATE_NAME,A.S_WECSHORT_DESCR,A.WECTYPE,A.GENERATION,A.MAVIAL," +
		"A.GAVIAL,A.CFACTOR,A.S_REMARKS FROM " +
		" VW_WEC_DATA A,VW_DGR_HEADING B WHERE A.D_READING_DATE BETWEEN ? AND  ? AND A.S_EB_ID=B.S_EB_ID ";
	
	public static final String GET_EB_EMARKS=
		"SELECT NVL(S_REMARKS,'NA') S_REMARKS FROM VW_TABULARFORM_EB WHERE S_EB_ID=? AND D_READING_DATE=?";
		
	public static final String GET_WEC_DGRY_MPR_admin_total=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  from " +
		"VW_WEC_MPRDATA wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?  "+
		" AND wec.D_READING_DATE between ? and ? ";
	public static final String GET_SUB_WEC_DGRY_MPR_admin_total=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  from " +
		"VW_WEC_MPRDATA wec,vw_sub_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=?  "+
		" AND wec.D_READING_DATE between ? and ? ";
	
	public static final String GET_WEC_DGRY_MPR_admin_total_all=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? "+
		" AND wec.D_READING_DATE between ? and ? ";
	
	public static final String GET_WEC_DGRY_CMP_admin_total_all=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=? "+
		" AND wec.D_READING_DATE =? ";
	
	public static final String GET_WEC_DGRY_CMP_admin_total_all_1=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? and wec."+
		" AND  TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		" AND TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')";
	
	public static final String GET_WEC_DGRY_CMP_admin_total_all_2=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=? "+
		" AND wec.D_READING_DATE =? ";
	
	public static final String GET_WEC_DGRY_CMP_admin_total_all_3=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and dgr.S_CUSTOMER_ID=? AND wec.wectype=? "+
		" AND  TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		" AND TO_CHAR(TO_DATE(wec.D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')";
	
	
	public static final String GET_WEC_DGRY_MPR_TOTAL_EB=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and wec.S_EB_ID=? "+
		" AND wec.D_READING_DATE between ? and ? ";
	
	public static final String GET_WEC_DGRY_MPR_TOTAL_WEC=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD "+
		",round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.WAVIAL),2) as WAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_MPRDATA " +
		"wec,vw_dgr_heading dgr  "+ 
		"  WHERE  wec.S_EB_ID=dgr.S_EB_ID  AND dgr.S_SITE_ID=? and wec.S_WEC_ID=? "+
		" AND wec.D_READING_DATE between ? and ? ";
	
	
	public static final String GET_WEC_YEARLY_DATA=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		"round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,0 AS TRIALRUN  " +
		" from VW_WEC_DATA wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND wec.D_READING_DATE between ? and ?";
	 
	public static final String GET_WEC_YEARLY_DATA_ADMIN=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD"+
	    " ,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
	    "round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL"+
        " ,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN" +
        " FROM VW_WEC_ADMINDATA  wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND wec.D_READING_DATE between ? and ?";
	
	
	public static final String GET_WEC_monthly_DATA=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"round(avg(wec.MAVIAL),2)  as MAVIAL," +
		"round(avg(wec.GAVIAL),2)  as GAVIAL," +
		" round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN   " +
		"from VW_WEC_CHKUNDERTRIAL wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		"AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		"group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype, wec.D_COMMISION_DATE";
	
	public static final String GET_WEC_monthly_DATA_admin=
		"select round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
		"round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL"+
        ",sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN," +
        "sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN  from VW_WEC_ADMINDATA wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND " +
		"TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		"AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') " +
		"group by wec.S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype";
	
	
	public static final String GET_WEC_YEARLY_DATA_MONTHWISE=
	"select TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS monthname,To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')," +
	"To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY'),round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) " +
	"as OPERATINGHRS," +
	"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL," +
	"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  " +
	"from VW_WEC_DATA  wec "+
	 " WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND wec.D_READING_DATE between ?  and ?  " +
	 "GROUP BY TO_CHAR(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MONTH'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')," +
	 "To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') ORDER BY  To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY')," +
	 "To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')";
	
	
	public static final String GET_WECSITE_MONTHWISE=
		"select TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS monthname,To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM'), "+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') AS REPYR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) 	as OPERATINGHRS,"+
	" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,"+
	" round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  "+
	" from VW_WEC_CHKUNDERTRIAL  wec,TBL_EB_MASTER EB "+
	"   WHERE EB.S_EB_ID=wec.S_EB_ID  AND EB.S_CUSTOMER_ID=? AND EB.S_SITE_ID=?  AND (wec.D_READING_DATE between ?  and ? ) "+
    " GROUP BY TO_CHAR(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MONTH'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM'),"+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') ORDER BY  To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY'),"+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')";
	
	
	public static final String GET_WECSTATE_MONTHWISE=
		"select TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS monthname,To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM'), "+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') AS REPYR,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) 	as OPERATINGHRS,"+
	" sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,"+
	" round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL  "+
	" from VW_WEC_CHKUNDERTRIAL  wec,TBL_EB_MASTER EB,TBL_SITE_MASTER E "+
	"   WHERE EB.S_EB_ID=wec.S_EB_ID  AND EB.S_CUSTOMER_ID=? AND EB.S_SITE_ID=E.S_SITE_ID AND E.S_STATE_ID=?  AND (wec.D_READING_DATE between ?  and ? ) "+
    " GROUP BY TO_CHAR(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MONTH'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM'),"+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') ORDER BY  To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY'),"+
	" To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')";
	
	public static final String GET_WEC_YEARLY_admin_monthwise =
		"select TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YY') ,'MONTH') AS monthname,To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')," +
		"To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY'),round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.FAULTHRS))) " +
		"as FAULTHRS,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) " +
		"as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) " +
		"as MACHINESD,sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) " +
		"as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) " +
		"as EXTERNALSD,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR," +
		"round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL" +
		",sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN  from VW_WEC_ADMINDATA   " +
		"wec " +
		" WHERE wec.S_EB_ID=? AND wec.S_WEC_ID=? AND wec.D_READING_DATE between ?  and ?  GROUP BY TO_CHAR(TO_DATE(D_READING_DATE,'DD-MON-YY') " +
		",'MONTH'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY') ORDER BY  " +
		"To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'YY'),To_char(TO_DATE(D_READING_DATE,'DD-MON-YY') ,'MM')";
	             
		
	public static final String GET_WEC_DGRY_DATA=
		"select  S_WEC_ID,S_WECSHORT_DESCR,wectype,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) " +
		"as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as " +
		"GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL from VW_WEC_DATA wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.D_READING_DATE between ? and ? group by S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype " +
		"order by wec.S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGRY_DATA1=
		"select S_WEC_ID,S_WECSHORT_DESCR,wectype,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) as " +
		"OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL," +
		"round(avg(wec.CFACTOR),2) as CFACTOR from VW_WEC_DATA wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.D_READING_DATE between ? and ? and S_WEC_ID=? group by S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype " +
		"order by wec.S_WECSHORT_DESCR";
	
	public static final String GET_WEC_DGRY_ADMINDATA=
		"select S_WEC_ID,S_WECSHORT_DESCR,wectype,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		"round(sum(wec.GENERATION),2) as GENERATION,round(sum(wec.CUM_GENERATION),2) as CUM_GENERATION,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS," +
		"sectotime(sum(timetosecond(wec.OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(wec.CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		"round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR" +
		",sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from " +
		"VW_WEC_ADMINDATA wec "+ 
		" WHERE wec.S_EB_ID=? AND wec.D_READING_DATE between ? and ? group by S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype " +
		"order by wec.S_WECSHORT_DESCR";
		
	public static final String GET_WEC_YCOMAPRE_DATA=
		"select S_WEC_ID,S_WECSHORT_DESCR,wectype,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) " +
		"as OPERATINGHRS,sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,round(avg(wec.GAVIAL),2) " +
		"as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR from VW_WEC_DATA wec "+ 
		"WHERE wec.S_WEC_ID=? AND wec.D_READING_DATE between ? and ? group by S_WEC_ID,wec.S_WECSHORT_DESCR,wec.wectype " +
		"order by wec.S_WECSHORT_DESCR";
		
	public static final String SELECT_StateWiseUpload =
		"select st.s_state_id,st.s_state_name,count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master site, " +
		"tbl_state_master st where wec.s_eb_id=eb.s_eb_id  and  site.s_site_id=eb.s_site_id and site.s_state_id=st.s_state_id and " +
		"wec.s_status IN(1,9) AND nvl(wec.D_COMMISION_DATE,?)<=? AND NVL(WEC.D_TRANSFER_DATE,?)>=? " +
		"group by st.s_state_id,st.s_state_name";
	
	public static final String SELECT_StateSiteWiseUpload =
		"select st.s_state_id,st.s_state_name,site.s_site_id,site.s_site_name,count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb, " +
		"tbl_site_master site,	 tbl_state_master st where wec.s_eb_id=eb.s_eb_id  and  site.s_site_id=eb.s_site_id and " +
		"site.s_state_id=st.s_state_id and wec.s_status=1  group by st.s_state_id,st.s_state_name,site.s_site_id,site.s_site_name " +
		"order by st.s_state_name,site.s_site_name";	 
	
	public static final String SELECT_EMPLOYEE_EMAIL_LIST =
		"SELECT S_EMAIL_ID FROM TBL_LOGIN_MASTER WHERE S_LOGIN_TYPE='E' AND S_EMAIL_STATUS='1' AND S_ACTIVE='1'";
	
	public static final String SELECT_StateWiseUploadTotal =
		"select count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master site,tbl_state_master st where " +
		"wec.s_eb_id=eb.s_eb_id  and  site.s_site_id=eb.s_site_id and site.s_state_id=st.s_state_id and wec.s_status=1";
	
	// modified on 23-02-2011 status in (1,9)
	// modified on 21-01-2012 status in (1)
	public static final String SELECT_SiteWiseUpload =
		"select st.s_site_id,st.s_site_name,count(*) as weccount from tbl_wec_master wec,tbl_eb_master eb,tbl_site_master st " +
		"where wec.s_eb_id=eb.s_eb_id  and  st.s_site_id=eb.s_site_id and st.s_state_id=? and wec.s_status in ('1')  group by " +
		"st.s_site_id,st.s_site_name order by s_site_name";
	
	public static final String SELECT_UPLOADTIME =
		"Select s_site_name,s_login_description,min(to_char(d.d_created_date, 'DD-Mon-YYYY Dy HH24:MI:SS')) as mintime," +
		"max(to_char(d.d_created_date, 'DD-Mon-YYYY Dy HH24:MI:SS')) as maxtime from tbl_state_master a,tbl_site_master b," +
		"tbl_eb_master c,tbl_wec_reading d,tbl_login_master e where a.s_state_id=b.s_state_id "+
        "and b.s_site_id=c.s_site_id and c.s_eb_id=d.s_eb_id and  a.s_state_id=? and d.d_reading_date=? and d.s_created_by=e.s_user_id " +
        "group by s_site_name,s_login_description order by s_site_name";
	
	// modified on 21-01-2012 set status to S_STATUS IN(1)
	public static final String SELECT_Upload =
		//	"select count(distinct s_wec_id) as upcount from VW_dgr_selection WHERE s_state_id=? and D_READING_DATE=?";
	    //	"select count(distinct s_wec_id) as upcount from VW_dgr_selection WHERE s_state_id=? and D_READING_DATE=? and s_status IN(1,9) AND nvl(D_COMMISION_DATE,?)<=? AND D_TRANSFER_DATE <= ?";
		"SELECT COUNT(DISTINCT S_WEC_ID) AS UPCOUNT "+
		"FROM  VW_DGR_SELECTION "+ 
		"WHERE S_STATE_ID=? "+ 
		"AND   D_READING_DATE=? "+ 
		"AND   S_STATUS IN(1) "+ 
		"AND   NVL(D_COMMISION_DATE,?)<=? "+
		"AND   NVL(D_TRANSFER_DATE,?)<=?";
	
	// modified on 21-01-2012 set status to S_STATUS IN(1)
	public static final String SELECT_Upload_publish =
		//"select count(distinct s_wec_id) as upcount from VW_dgr_selection WHERE s_state_id=? and TO_DATE(D_READING_DATE,'dd-MON-yy')=? and n_publish=1";
		"select count(distinct s_wec_id) as upcount " +
		"from VW_dgr_selection " +
		"WHERE s_state_id=? " +
		"and D_READING_DATE=? " +
		"and n_publish=0 " +
		"and s_status IN(1) " +
		"AND nvl(D_COMMISION_DATE,?)<=? " +
		"AND NVL(D_TRANSFER_DATE,?)<=?";
	
	public static final String SELECT_DOWNLOADED_DATA_TO_SCADADW =
								"SELECT  COUNT(DISTINCT ECARE.TBL_WEC_MASTER.S_WEC_ID) AS TTLSCADACOUNT "+
								"FROM    ECARE.TBL_WEC_MASTER, ECARE.TBL_EB_MASTER, ECARE.TBL_SITE_MASTER, SCADADW.TBL_GENERATION_MIN_10, SCADADW.TBL_PLANT_MASTER "+
								"WHERE   ECARE.TBL_SITE_MASTER.S_STATE_ID = ? "+
								"AND     SCADADW.TBL_GENERATION_MIN_10.D_DATE=TO_DATE(?) "+
								"AND     ECARE.TBL_WEC_MASTER.S_SCADA_FLAG='1' "+
								"AND     ECARE.TBL_WEC_MASTER.S_TECHNICAL_NO = SCADADW.TBL_PLANT_MASTER.S_SERIAL_NO "+
								"AND     ECARE.TBL_WEC_MASTER.S_EB_ID = ECARE.TBL_EB_MASTER.S_EB_ID "+
								"AND     ECARE.TBL_EB_MASTER.S_SITE_ID = ECARE.TBL_SITE_MASTER.S_SITE_ID "+
								"AND     SCADADW.TBL_PLANT_MASTER.S_LOCATION_NO = SCADADW.TBL_GENERATION_MIN_10.S_LOCATION_NO "+
								"AND     SCADADW.TBL_PLANT_MASTER.S_PLANT_NO = SCADADW.TBL_GENERATION_MIN_10.S_PLANT_NO";								
	
	public static final String SELECT_site_Upload =
		"select count(distinct s_wec_id) as upcount from VW_dgr_selection WHERE s_state_id=? and s_site_id=? and D_READING_DATE=? ";
	 
	public static final String SELECT_site_publish =
		"select count(distinct s_wec_id) as upcount from VW_dgr_selection WHERE s_state_id=? and s_site_id=? and " +
		" D_READING_DATE=? and n_publish=0";
	
	public static final String SELECT_EBWISE_TOTAL =
		"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(LULLHRS))) as LULLHRS," +
		"round((sum(MAVIAL)-?)/(?),2) as MAVIAL,round((sum(GAVIAL)-?)/(?),2) as GAVIAL,round((sum(CFACTOR)-?)/(?),2) as CFACTOR " +
		"from vw_wec_data wec where S_EB_ID=? and D_READING_DATE=?";	
	
	public static final String SELECT_AEBWISE_TOTAL =
		"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(LULLHRS))) as LULLHRS," +
		"round(AVG(MAVIAL),2) as MAVIAL,round(AVG(GAVIAL),2) as GAVIAL,round(AVG(CFACTOR),2) as CFACTOR,round(AVG(MIAVIAL),2) as MIAVIAL,round(AVG(GIAVIAL),2) as GIAVIAL " +
		",SUM(TRIALRUN) AS TRIALRUN from VW_WEC_CHKUNDERTRIAL wec where S_EB_ID=? and D_READING_DATE=? and D_COMMISION_DATE <= ?";
	
	public static final String SELECT_AEBWISE_TOTAL_NEW =
			"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
			"sectotime(sum(timetosecond(LULLHRS))) as LULLHRS," +
			"round(AVG(MAVIAL),2) as MAVIAL,round(AVG(GAVIAL),2) as GAVIAL,round(AVG(CFACTOR),2) as CFACTOR,round(AVG(MIAVIAL),2) as MIAVIAL,round(AVG(GIAVIAL),2) as GIAVIAL " +
			",SUM(TRIALRUN) AS TRIALRUN from VW_WEC_CHKUNDERTRIAL_NEW wec where S_EB_ID=? and D_READING_DATE=? and D_COMMISION_DATE <= ?";
	
	public static final String SELECT_EBWISE_TOTAL_TRAIL =
		"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		" '0' as LULLHRS" +
		",'0' as MAVIAL,'0' as GAVIAL,'0' as CFACTOR from vw_wec_data wec where S_EB_ID=? and D_READING_DATE=?";
		
	public static final String SELECT_EBWISE_TOTAL_ADMIN =
		"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,round(sum(CUM_GENERATION),2) as CUM_GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		"sectotime(sum(timetosecond(CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD,sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT," +
		"sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD," +
		"round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL,round(avg(CFACTOR),2) as CFACTOR," +
		"sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS " +
		" ,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN " +
		" from VW_WEC_ADMINDATA  wec where S_EB_ID=? and D_READING_DATE=?";
	
	public static final String SELECT_TRAILWISE_TOTAL =
		"select count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		" sectotime(sum(timetosecond(LULLHRS))) as LULLHRS,round(SUM(MAVIAL),2) as MAVIAL,round(SUM(GAVIAL),2) as GAVIAL," +
		" round(SUM(CFACTOR),2) as CFACTOR from vw_wec_data wec where  S_EB_ID=? and D_READING_DATE=? AND " +
		" (?-D_COMMISION_DATE)<=30";
	
	public static final String SELECT_EBWISEM_TOTAL =
		/*"select  count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(LULLHRS))) as LULLHRS,round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL,round(avg(CFACTOR),2) " +
		"as CFACTOR,round(AVG(MIAVIAL),2) as MIAVIAL,round(AVG(GIAVIAL),2) as GIAVIAL from VW_WEC_CHKUNDERTRIAL wec where S_EB_ID=? and " +
		"TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		"AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')";
      */
		"select  count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS," +
		"sectotime(sum(timetosecond(LULLHRS))) as LULLHRS,round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL,round(avg(CFACTOR),2) " +
		"as CFACTOR,round(AVG(MIAVIAL),2) as MIAVIAL,round(AVG(GIAVIAL),2) as GIAVIAL,SUM(TRIALRUN) AS TRIALRUN from VW_WEC_CHKUNDERTRIAL wec where S_EB_ID=? and " +
		"TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
		"AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')";
   
	public static final String SELECT_EBWISEM_TOTAL_ADMIN =
		"select  count(*) as cnt, round(sum(CUM_GENERATION),2) as CUM_GENERATION, round(sum(GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL" +
		",sectotime(sum(timetosecond(CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(LULLHRS))) as LULLHRS," +
		"sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD"+
	",round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL,round(avg(CFACTOR),2) as CFACTOR,sectotime(sum(timetosecond(wec.FAULTHRS)))" +
	" as FAULTHRS,sectotime(sum(timetosecond(wec.WECSPDOWN))) as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN " +
	"from VW_WEC_ADMINDATA  wec where S_EB_ID=? and TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') " +
	"AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY')";   
	
	public static final String SELECT_EBWISEY_TOTAL =
		"select  count(*) as cnt, round(sum(GENERATION),2) as GENERATION,sectotime(sum(timetosecond(OPERATINGHRS))) " +
		"as OPERATINGHRS,sectotime(sum(timetosecond(LULLHRS))) as LULLHRS,round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL," +
		"round(avg(CFACTOR),2) as CFACTOR,round(AVG(MIAVIAL),2) as MIAVIAL,round(AVG(GIAVIAL),2) as GIAVIAL,0 as TRIALRUN from VW_WEC_DATA wec where S_EB_ID=? and D_READING_DATE between ? and ?";
   
	public static final String SELECT_SITEWISEY_TOTAL =
		"select count(*) as cnt,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) 	as OPERATINGHRS,"+
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,"+
		"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,0 as TRIALRUN  "+ 
		"from VW_WEC_CHKUNDERTRIAL  wec,TBL_EB_MASTER EB "+
		"WHERE EB.S_EB_ID=wec.S_EB_ID  AND EB.S_CUSTOMER_ID=? AND EB.S_SITE_ID=?  AND (wec.D_READING_DATE between ? and ? )";
	   
	public static final String SELECT_STATEWISEY_TOTAL =
		"select count(*) as cnt,round(sum(wec.GENERATION),2) as GENERATION,sectotime(sum(timetosecond(wec.OPERATINGHRS))) 	as OPERATINGHRS,"+
		"sectotime(sum(timetosecond(wec.LULLHRS))) as LULLHRS,round(avg(wec.MAVIAL),2) as MAVIAL,"+
		"round(avg(wec.GAVIAL),2) as GAVIAL,round(avg(wec.CFACTOR),2) as CFACTOR,round(avg(wec.MIAVIAL),2) as MIAVIAL,round(avg(wec.GIAVIAL),2) as GIAVIAL,0 as TRIALRUN  "+ 
		"from VW_WEC_CHKUNDERTRIAL  wec,TBL_EB_MASTER EB ,TBL_SITE_MASTER E "+
		" WHERE EB.S_EB_ID=wec.S_EB_ID  AND EB.S_CUSTOMER_ID=? AND EB.S_SITE_ID=E.S_SITE_ID AND E.S_STATE_ID=?  AND (wec.D_READING_DATE between ? and ? )";
	
	public static final String SELECT_EBWISEY_TOTAL_ADMIN =
		"select  count(*) as cnt, round(sum(CUM_GENERATION),2) as CUM_GENERATION,round(sum(GENERATION),2) as GENERATION,round(avg(wec.GIAVIAL),2) as GIAVIAL,round(avg(wec.WAVIAL),2) as WAVIAL," +
		"sectotime(sum(timetosecond(CUM_OPERATINGHRS))) as CUM_OPERATINGHRS,sectotime(sum(timetosecond(OPERATINGHRS))) as OPERATINGHRS,sectotime(sum(timetosecond(LULLHRS)))" +
		" as LULLHRS,sectotime(sum(timetosecond(wec.MACHINEFAULT))) as MACHINEFAULT,sectotime(sum(timetosecond(wec.MACHINESD))) as MACHINESD," +
		"sectotime(sum(timetosecond(wec.INTERNALFAULT))) as INTERNALFAULT,sectotime(sum(timetosecond(wec.INTERNALSD))) as INTERNALSD," +
		"sectotime(sum(timetosecond(wec.EXTERNALFAULT))) " +
		"as EXTERNALFAULT,sectotime(sum(timetosecond(wec.EXTERNALSD))) as EXTERNALSD,sectotime(sum(timetosecond(wec.FAULTHRS))) as FAULTHRS "+
	",round(avg(MAVIAL),2) as MAVIAL,round(avg(GAVIAL),2) as GAVIAL,round(avg(CFACTOR),2) as CFACTOR,sectotime(sum(timetosecond(wec.WECSPDOWN))) " +
	" as WECSPDOWN,sectotime(sum(timetosecond(wec.EBSPDOWN))) as EBSPDOWN from VW_WEC_ADMINDATA  wec where S_EB_ID=? and " +
	"D_READING_DATE between ? and ?";
	
	public static final String GET_EB_DGR_DATA=
		"SELECT KWHEXPORT,KWHIMPORT, " +
		"(TO_NUMBER(KWHEXPORT)-TO_NUMBER(KWHIMPORT)) AS DIF,S_REMARKS, RKVAHIMPLAG,RKVAHEXPLAG,RKVAHIMPLEAD,RKVAHEXPLEAD " +
		"FROM VW_TABULARFORM_EB WHERE  S_EB_ID=? " +
		"and D_READING_DATE=?";
	
	public static final String GET_EB_MONTHLY_DGR_DATA =
		"SELECT SUM(KWHEXPORT)  as KWHEXPORT,SUM(KWHIMPORT) as KWHIMPORT,(SUM(TO_NUMBER(KWHEXPORT))-SUM(TO_NUMBER(KWHIMPORT))) AS DIF  " +
		"FROM VW_TABULARFORM_EB WHERE  S_EB_ID=?  "+ 
        " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM') AND " +
        "TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') ";
	
	public static final String GET_EB_MONTHLY_DGR_DATA_CUM =
		"SELECT SUM(KWHEXPORT)  as KWHEXPORT,SUM(KWHIMPORT) as KWHIMPORT,(SUM(TO_NUMBER(KWHEXPORT))-SUM(TO_NUMBER(KWHIMPORT))) AS DIF  " +
		"FROM VW_TABULARFORM_EB WHERE  S_EB_ID=?  "+ 
        " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'MM') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'MM')" +
        " AND TO_CHAR(TO_DATE(D_READING_DATE, 'DD-MON-YYYY') ,'YYYY') = TO_CHAR(TO_DATE(?, 'DD-MON-YYYY') ,'YYYY') AND D_READING_DATE<=? ";
	
	public static final String GET_EB_YEARLY_DGR_DATA =
		"SELECT SUM(KWHEXPORT) as KWHEXPORT,SUM(KWHIMPORT) as KWHIMPORT,(SUM(TO_NUMBER(KWHEXPORT))-SUM(TO_NUMBER(KWHIMPORT))) AS DIF " +
		" FROM VW_TABULARFORM_EB WHERE  S_EB_ID=?  "+ 
        "AND D_READING_DATE between ? and ?";
	 
	public static final String SELECT_CUSTOMER_ACTIVATE_DETAILS =
    	 "SELECT c.s_customer_name,a.s_email,b.s_user_id,a.activated,a.s_login_detail_id from tbl_login_details a,tbl_login_master b," +
    	 "tbl_customer_master c where c.s_login_master_id=b.s_login_master_id and b.s_user_id=a.s_login_master_id order by c.s_customer_name";
	
	public static final String SELECT_CUSTOMER_DETAILS =
		 "SELECT c.s_customer_name,replace(a.S_LOGIN_DETAIL_ID,'-') S_LOGIN_DETAIL_ID,replace(a.S_OWNER_NAME,'-') S_OWNER_NAME," +
		 "replace(a.S_OEMAIL,'-') S_OEMAIL,replace(a.S_OPHONE_NUMBER,'-') S_OPHONE_NUMBER,replace(a.S_OCELL_NUMBER,'-') S_OCELL_NUMBER," +
		 "replace(a.S_OFAX_NUMBER,'-') S_OFAX_NUMBER,a.D_ODOB_DATE,a.D_ODOA_DATE,replace(a.S_CONTACT_PERSON_NAME,'-') S_CONTACT_PERSON_NAME," +
		 "replace(a.S_CORRES_ADDRES,'-') S_CORRES_ADDRES,replace(a.S_CITY,'-') S_CITY,replace(a.S_ZIP,'-') S_ZIP,replace(a.S_EMAIL,'-') S_EMAIL," +
		 "replace(a.S_PHONE_NUMBER,'-') S_PHONE_NUMBER, " +
		 "replace(a.S_CELL_NUMBER,'-') S_CELL_NUMBER ,"+
			"replace(a.S_FAX_NUMBER,'-') S_FAX_NUMBER,"+
			"a.D_DOB_DATE,"+
			"a.D_DOA_DATE,"+
			"replace(a.ACTIVATED,'-') ACTIVATED,"+
			"replace(a.EMAILACTIVATED,'-') EMAILACTIVATED from tbl_login_details a,tbl_login_master b,tbl_customer_master c " +
			"where c.s_login_master_id=b.s_login_master_id and b.s_user_id=a.s_login_master_id and b.s_user_id=?";
	 public static final String UPDATEACTIVATEDSTATUS =
		 "UPDATE TBL_LOGIN_DETAILS SET ACTIVATED=? WHERE S_LOGIN_DETAIL_ID=?";
	 
	 public static final String GET_LOGIN_EMAIL_DATA =
		 "SELECT B.S_EMAIL, B.S_LOGIN_MASTER_ID, A.S_PASSWORD FROM tbl_login_master A, tbl_login_details B where B.S_LOGIN_DETAIL_ID = A.S_LOGIN_MASTER_ID AND S_LOGIN_DETAIL_ID=? ";

	 public static final String SELECT_WEC_CAPACITY1=
			"SELECT NVL(N_WEC_CAPACITY*COUNT(*)/1000,0) as lcapacity FROM TBL_WEC_MASTER " +
			"WHERE S_CUSTOMER_ID=? and s_status=1   AND S_EB_ID IN (SELECT A.S_EB_ID FROM TBL_EB_MASTER A,TBL_SITE_MASTER B WHERE  A.S_SITE_ID=B.S_SITE_ID AND B.S_STATE_ID=? AND  A.S_CUSTOMER_ID=?) " +
			"and d_commision_date <=? group by N_WEC_CAPACITY";
	 
	 public static final String SELECT_GET_READING1=
			"SELECT NVL(SUM(N_ACTUAL_VALUE),'0') AS A_VAL FROM TBL_WEC_READING WHERE S_CUSTOMER_ID=? AND S_MP_ID='0808000022' AND D_READING_DATE=? AND S_EB_ID IN" +
			" (SELECT A.S_EB_ID FROM TBL_EB_MASTER A,TBL_SITE_MASTER B WHERE  A.S_SITE_ID=B.S_SITE_ID AND B.S_STATE_ID=? AND  A.S_CUSTOMER_ID=?) ";
	 
	 public static final String SELECT_GET_READING_CUM1=
			"SELECT  NVL(SUM(N_ACTUAL_VALUE),'0') AS A_VAL FROM TBL_WEC_READING WHERE S_CUSTOMER_ID=? AND S_MP_ID='0808000022' AND D_READING_DATE BETWEEN ? AND ? AND S_EB_ID IN" +
			" (SELECT A.S_EB_ID FROM TBL_EB_MASTER A,TBL_SITE_MASTER B WHERE  A.S_SITE_ID=B.S_SITE_ID AND B.S_STATE_ID=? AND  A.S_CUSTOMER_ID=?) ";
	 
	 public static final String SELECT_GET_ERDADETAIL_WECWISE =
			"SELECT VW_WEC_DATA.S_WEC_ID,VW_WEC_DATA.S_WECSHORT_DESCR, VW_WEC_DATA.GENERATION FROM VW_WEC_DATA  "+
			"WHERE S_EB_ID IN (SELECT S_EB_ID FROM VW_DGR_HEADING  WHERE S_CUSTOMER_ID=? AND S_STATE_ID=?)  "+
	     	"AND VW_WEC_DATA.D_READING_DATE = ?  "+
			"ORDER BY VW_WEC_DATA.S_WECSHORT_DESCR, VW_WEC_DATA.D_READING_DATE";
	 
	 public static final String SELECT_GET_READING_CUM12=
			"SELECT  S_WEC_ID, NVL(SUM(N_ACTUAL_VALUE),'0') AS A_VAL FROM TBL_WEC_READING WHERE S_MP_ID='0808000022' AND D_READING_DATE BETWEEN ? AND ? " +
			" AND S_WEC_ID=? GROUP BY S_WEC_ID";
	 
	 public static String CHECK_WEC_SCADA_CONNECTIVITY = 
			 "Select s_status,s_scada_flag From Tbl_Wec_Master Where S_Wec_id = ?";
}
