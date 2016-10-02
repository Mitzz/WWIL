package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.model.master.RoleMasterVo;
import com.enercon.model.table.LoginDetailVo;

public class LoginMasterDao implements WcareConnector {
	
	private final static Logger logger = Logger.getLogger(LoginMasterDao.class);

	private static class SingletonHelper{
		public final static LoginMasterDao INSTANCE = new LoginMasterDao();
	}
	
	public static LoginMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private LoginMasterDao(){}
	
	
   public List<LoginMasterVo> getAll() throws SQLException  {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		LoginMasterVo vo = null;
		List<LoginMasterVo> vos = new ArrayList<LoginMasterVo>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery =  " SELECT S_LOGIN_MASTER_ID, S_LOGIN_DESCRIPTION, S_USER_ID, S_LOGIN_TYPE " +
						" FROM TBL_LOGIN_MASTER " +
						" ORDER BY S_LOGIN_DESCRIPTION ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				
				vo = new LoginMasterVo();
				vo.setId(rs.getString("S_LOGIN_MASTER_ID"));
				vo.setLoginDescription(rs.getString("S_LOGIN_DESCRIPTION"));
				vo.setUserId(rs.getString("S_USER_ID"));
				vo.setLoginType(rs.getString("S_LOGIN_TYPE"));
				//vo.setCustomers(CustomerMasterService.getInstance().get(rs.getString("")));
		
				vos.add(vo);
				
			}
			return vos;	
        }
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
   }
   
	
   /*public boolean update(LoginMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		//UPDATE TBL_CUSTOMER_MASTER SET S_LOGIN_MASTER_ID =?,S_LAST_MODIFIED_BY=?,D_LAST_MODIFIED_DATE=SYSDATE WHERE  S_CUSTOMER_ID = ?";
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_CUSTOMER_MASTER " +
						" SET  S_LOGIN_MASTER_ID = ?,  S_LAST_MODIFIED_BY = ?, D_LAST_MODIFIED_DATE = localtimestamp " +										
						" WHERE S_CUSTOMER_ID = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getModifiedBy());
			prepStmt.setObject(3, vo.getCustomerId());
				
			int rowUpdated = prepStmt.executeUpdate();
			
			return(rowUpdated == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
   
   public boolean updateLogin(LoginMasterVo vo) throws SQLException{
	   
	   SortedMap<String, Object> m = new TreeMap<String, Object>();
	   
	   CustomerMasterVo updateCustomer = new CustomerMasterVo();
	  
	   updateCustomer.setLoginId(vo.getId());
	   m.put("S_LOGIN_MASTER_ID", vo.getId());
	   
	   updateCustomer.setModifiedBy(vo.getModifiedBy());
	   m.put("S_LAST_MODIFIED_BY", vo.getModifiedBy());
  
	   updateCustomer.setModifiedAt("localtimestamp");
	   m.put("D_LAST_MODIFIED_DATE", "localtimestamp");
	   
	   updateCustomer.setId(vo.getCustomerId());
	   m.put("S_CUSTOMER_ID", vo.getCustomerId());
	   updateCustomer.setId(vo.getCustomerId());
	   return partialUpdate(updateCustomer, m);
	  
   }
   
	private boolean partialUpdate(CustomerMasterVo vo, Map<String, Object> m) throws SQLException  {
		logger.debug("map : "+m);
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = m.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_CUSTOMER_MASTER SET ");
			for(String column: m.keySet()){
				query.append(column + " = ?, ");
			}
			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("WHERE S_CUSTOMER_ID = ? ");
			logger.debug("S_CUSTOMER_ID :: "+vo.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug("query :: "+query);
			for(String column: m.keySet()){
				index++;
				prepStmt.setObject(index, m.get(column));
			}
			prepStmt.setObject(paramterCount + 1, vo.getId());
			
			return (prepStmt.executeUpdate() == 1);
			//return true;
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}*/
   
	public LoginMasterVo get(String userId, String password)
			throws SQLException {

		LoginMasterVo loginMasterVo = null;

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		String id = null;
		String loginType = null;
		RoleMasterVo role = null;
		String roleId = null;
		String loginDescription = null;
		String active = null;
		String remarks = null;
		String emailId = null;
		String emailStatus = null;
		String createdBy = null;
		String createdAt = null;
		String modifiedBy = null;
		String modifiedAt = null;

		try {
			conn = wcareConnector.getConnectionFromPool();

			query = "SELECT loginmaster.S_LOGIN_MASTER_ID, loginmaster.S_USER_ID, loginmaster.S_PASSWORD, loginmaster.S_LOGIN_TYPE,  "
					+ "               loginmaster.S_ROLE_ID, loginmaster.S_LOGIN_DESCRIPTION, loginmaster.S_CREATED_BY, loginmaster.D_CREATED_DATE,  "
					+ "               loginmaster.S_LAST_MODIFIED_BY, loginmaster.D_LAST_MODIFIED_DATE, loginmaster.S_ACTIVE, loginmaster.S_REMARKS,  "
					+ "               loginmaster.S_EMAIL_ID, loginmaster.S_EMAIL_STATUS,rolemaster.S_ROLE_NAME  "
					+ "FROM TBL_LOGIN_MASTER loginmaster, TBL_ROLE rolemaster "
					+ "WHERE loginmaster.S_ROLE_ID = rolemaster.S_ROLE_ID  "
					+ "AND loginmaster.S_USER_ID = ?  "
					+ "AND loginmaster.S_PASSWORD= ? ";

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, userId);
			prepStmt.setString(2, password);

			rs = prepStmt.executeQuery();
			logger.debug("Done");

			while (rs.next()) {

				id = rs.getString("S_LOGIN_MASTER_ID");
				loginType = rs.getString("S_LOGIN_TYPE");
				roleId = rs.getString("S_ROLE_ID");
				loginDescription = rs.getString("S_LOGIN_DESCRIPTION");
				createdBy = rs.getString("S_CREATED_BY");
				createdAt = rs.getString("D_CREATED_DATE");
				modifiedBy = rs.getString("S_LAST_MODIFIED_BY");
				modifiedAt = rs.getString("D_LAST_MODIFIED_DATE");
				active = rs.getString("S_ACTIVE");
				remarks = rs.getString("S_REMARKS");
				emailId = rs.getString("S_EMAIL_ID");
				emailStatus = rs.getString("S_EMAIL_STATUS");
				
				role = new RoleMasterVo(roleId);

				loginMasterVo = new LoginMasterVo.LoginMasterVoBuilder(id)
						.userId(userId).password(password).loginType(loginType)
						.role(role).loginDescription(loginDescription)
						.createdAt(createdAt).createdBy(createdBy)
						.modifiedAt(modifiedAt).modifiedBy(modifiedBy)
						.active(active).emailId(emailId)
						.emailStatus(emailStatus).remarks(remarks).build();
			}

			logger.debug(loginMasterVo);
			return loginMasterVo;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);

		}

	}

	public List<LoginMasterVo> populate(List<LoginDetailVo> loginDetails) throws SQLException {
		List<LoginMasterVo> logins = getAll();
		
		return logins;
	}
	

}
