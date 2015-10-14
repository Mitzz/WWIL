package com.enercon.security.dao;

public class SecuritySQLC {


    /*Query for getting Employee by his login ID*/
    public static final String GET_LOGIN_BY_ID = 
        "SELECT A.*,H.S_ROLE_NAME FROM TBL_LOGIN_MASTER A, TBL_ROLE H  " +
        "WHERE A.S_ROLE_ID = H.S_ROLE_ID AND A.S_USER_ID = ? AND A.S_PASSWORD=? ";
    public static final String GET_LOGIN_BY_CUSID = 
        "SELECT A.*,H.* FROM TBL_LOGIN_MASTER A, TBL_CUSTOMER_MASTER H  "+
        "WHERE A.S_LOGIN_MASTER_ID = H.S_LOGIN_MASTER_ID AND A.S_USER_ID = ?  AND A.S_ACTIVE = 1 ";
    public static final String INSERT_LOGIN_ROLE_HISTORY = 
        "INSERT INTO TBL_LOGIN_HISTORY(S_LOGIN_HISTORY_ID,S_USER_ID,D_LOGIN_DATETIME,S_IP_ADDRESS,S_HOST)" +
        "VALUES(?,?,SYSDATE,?,?)";
    public static final String GET_ALL_EMPLOYEE =
    	"SELECT * FROM TBL_EMPLOYEE ORDER BY S_EMPLOYEE_ID";
    public static final String GET_TRANSACTION_DETAILS = 
        "SELECT A.S_TRANSACTION_ID,A.S_TRANSACTION_NAME ,A.S_URL, A.S_UNDER_1,A.S_UNDER_2,A.S_UNDER_3, A.S_TRANSACTION_DESCRIPTION " +
        ",S_DISPLAY,S_UNDER1_IMAGE_NORMAL,S_UNDER2_IMAGE_NORMAL,S_UNDER3_IMAGE_NORMAL,S_UNDER1_IMAGE_MOUSE,S_UNDER2_IMAGE_MOUSE," +
        "S_UNDER3_IMAGE_MOUSE,S_UNDER1_IMAGE_EXPAND,S_UNDER2_IMAGE_EXPAND,S_UNDER3_IMAGE_EXPAND,S_LINK_NORMAL,S_LINK_MOUSE,S_LINK_EXPAND" +
        " FROM TBL_ROLE_TRAN_MAPPING B,TBL_TRANSACTION A " +
        "WHERE B.S_ROLE_ID = ? AND A.S_TRANSACTION_ID = B.S_TRANSACTION_ID ORDER BY A.S_UNDER_1,A.N_SEQ_NO";
    
    public static final String UPDATE_EMPLOYEE_PASSWORD =
    	"UPDATE TBL_EMPLOYEE SET S_PASSWORD=? WHERE S_EMPLOYEE_ID=?";
    public static final String SELECT_LOGIN_DETAILS =
    	"SELECT replace(a.S_LOGIN_DETAIL_ID,'-') S_LOGIN_DETAIL_ID,replace(a.S_OWNER_NAME,'-') S_OWNER_NAME,replace(a.S_OEMAIL,'-') S_OEMAIL,replace(a.S_OPHONE_NUMBER,'-') S_OPHONE_NUMBER,replace(a.S_OCELL_NUMBER,'-') S_OCELL_NUMBER,replace(a.S_OFAX_NUMBER,'-') S_OFAX_NUMBER,a.D_ODOB_DATE,a.D_ODOA_DATE,replace(a.S_CONTACT_PERSON_NAME,'-') S_CONTACT_PERSON_NAME,replace(a.S_CORRES_ADDRES,'-') S_CORRES_ADDRES,replace(a.S_CITY,'-') S_CITY,replace(a.S_ZIP,'-') S_ZIP,replace(a.S_EMAIL,'-') S_EMAIL,replace(a.S_PHONE_NUMBER,'-') S_PHONE_NUMBER, " +
		 "replace(a.S_CELL_NUMBER,'-') S_CELL_NUMBER ,"+
			"replace(a.S_FAX_NUMBER,'-') S_FAX_NUMBER,"+
			"a.D_DOB_DATE,"+
			"a.D_DOA_DATE,"+
			"replace(a.ACTIVATED,'-') ACTIVATED,"+
			"replace(a.EMAILACTIVATED,'-') EMAILACTIVATED FROM TBL_LOGIN_DETAILS a WHERE a.S_LOGIN_MASTER_ID=?";
    public static final String SELECT_LOGIN_DETAILS1 =
    	 "SELECT c.s_customer_name,a.s_email from tbl_login_details a,tbl_login_master b,tbl_customer_master c where c.s_login_master_id=b.s_login_master_id and b.s_user_id=a.s_login_master_id"+
        " and a.S_LOGIN_MASTER_ID=?";
     public static final String SELECT_LOGIN_DETAILSINF =
    	"SELECT * FROM TBL_LOGIN_DETAILS WHERE S_LOGIN_MASTER_ID=? AND ACTIVATED=1";
   
     public static final String GET_PWD =
     	"select b.S_PASSWORD,a.ACTIVATED from tbl_login_details a, tbl_login_master b "+
    	" where b.s_user_id=a.s_login_master_id  and "+
    	" lower(a.s_login_master_id)=? and "+
    	 "lower(a.S_EMAIL)=?";    
     
     public static final String SELECT_CUSTOMER_FEEDBACK =
     	"SELECT COUNT(S_FEEDBACK_ID) cnt FROM TBL_CUSTOMER_FEEDBACK WHERE S_LOGIN_MASTER_ID=?";
     
     public static final String SELECT_CUSTOMER_FEEDBACK1 = "SELECT COUNT(1) cnt1 FROM TBL_WEC_MASTER "+
      "  WHERE S_CUSTOMER_ID IN(SELECT S_CUSTOMER_ID FROM TBL_CUSTOMER_MASTER, TBL_LOGIN_MASTER "+
      "  WHERE TBL_CUSTOMER_MASTER.S_LOGIN_MASTER_ID = TBL_LOGIN_MASTER.S_LOGIN_MASTER_ID "+
      "  AND TBL_LOGIN_MASTER.S_USER_ID = ?) "+
      "  AND TBL_WEC_MASTER.D_COMMISION_DATE <= '31-DEC-2009'";
     
     public static final String News_List = "select * from tbl_news where sysdate between  d_from_date and  d_to_date ORDER BY d_last_modified_date DESC";
     
     
     public static final String SELECT_LOGIN_PASSWORD =
     	"SELECT COUNT(S_USER_ID) AS CNT FROM TBL_LOGIN_MASTER WHERE S_USER_ID=? AND SYSDATE-D_LAST_MODIFIED_DATE>90";
    
     
    
    public SecuritySQLC() {
    }
}
