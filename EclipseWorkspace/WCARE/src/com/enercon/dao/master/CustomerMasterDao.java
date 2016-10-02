package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utility.master.WecMasterUtility;
import com.enercon.model.CustomerLoginVo;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ICustomerMasterVo;
import com.enercon.model.graph.event.CustomerMasterEvent;
import com.enercon.model.graph.listener.CustomerMasterListener;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.model.master.WecMasterVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class CustomerMasterDao{
	private final static Logger logger = Logger.getLogger(CustomerMasterDao.class);

//	private Graph G  = Graph.getInstance();
	private final static String LOGIN_MASTER_ID_NULL = "LoginMasterVo 'id' is null";
	private final static String LOGIN_MASTER_NULL = "LoginMasterVo is null";
	private final static String LOGIN_MASTER_LOGIN_TYPE = "Customers are not assigned to User of Login Type 'E'";
	
	private List<CustomerMasterListener> listeners = new ArrayList<CustomerMasterListener>();
	
	private CustomerMasterDao(){}
	
	private static class SingletonHelper{
		public final static CustomerMasterDao INSTANCE = new CustomerMasterDao();
	}
	
	public static CustomerMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	/*public CustomerMasterVo get(String id) throws SQLException{
		
		CustomerMasterVo customerMasterVo = null;
		String query = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			query = 
					"Select S_customer_id, S_customer_name " + 
					"from tbl_customer_master " + 
					"where S_customer_id = ? "; 
		
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, id);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				customerMasterVo = new CustomerMasterVo(id);
				
			}
			CustomerMasterDao.getInstance().get(customerMasterVo);
			
			return customerMasterVo;
		}
		finally{			
			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
		}
	}*/
	
	public ICustomerMasterVo get(String id){
		return Graph.getInstance().getCustomersM().get(id);
	}
	
//	public List<CustomerMasterVo> get(LoginMasterVo loginMasterVo) throws SQLException{
//		logger.info("Start");
//		validate(loginMasterVo);
//		List<CustomerMasterVo> customers = new ArrayList<CustomerMasterVo>();
//		CustomerMasterVo customer = null;
//		String query = null;
//		PreparedStatement preparedStatement = null;
//		ResultSet resultSet = null;
//		Connection connection = null;
//		
//		try{
//			connection = wcareConnector.getConnectionFromPool();
//			
//			query = 
//					"Select S_customer_id, S_customer_name " + 
//					"from tbl_customer_master " + 
//					"where s_login_master_id = ? "; 
//		
//			preparedStatement = connection.prepareStatement(query);
//			preparedStatement.setString(1, loginMasterVo.getId());
//			
//			resultSet = preparedStatement.executeQuery();
//			
//			while(resultSet.next()){
//				customer = new CustomerMasterVo(resultSet.getString("S_CUSTOMER_ID"));
//				
//				customer.setName(resultSet.getString("S_CUSTOMER_NAME"));
//				customer.setLogin(loginMasterVo);
//				StateMasterDao.getInstance().get(customer);
//				customers.add(customer);
//				
//			}
//			logger.info("End");
//			return customers;
//		}
//		finally{			
//			DaoUtility.releaseResources(preparedStatement, resultSet, connection);
//		}
//	}

	private void validate(LoginMasterVo loginMasterVo) {
		if(loginMasterVo == null) {
			logger.error(LOGIN_MASTER_NULL);
			throw new NullPointerException(LOGIN_MASTER_NULL);
		}
		String loginType = loginMasterVo.getLoginType();
		if(!loginType.equals("C")) {
			logger.error(LOGIN_MASTER_LOGIN_TYPE);
			throw new IllegalArgumentException(LOGIN_MASTER_LOGIN_TYPE);
		}
		
		if(loginMasterVo.getId() == null){
			logger.error(LOGIN_MASTER_ID_NULL);
			throw new NullPointerException(LOGIN_MASTER_ID_NULL);
		}
	}

	public static void main(String[] args) {
		
	}
	
	public List<CustomerMasterVo> get(List<WecMasterVo> wecs){
		
		List<String> wecIds = WecMasterUtility.getWecIds(wecs);
		
		
		
		return null;
	}

	public List<ICustomerMasterVo> get(Set<String> ids) {
		List<ICustomerMasterVo> customers = new ArrayList<ICustomerMasterVo>();
		for(String id: ids){
			customers.add(get(id));
		}
		return customers;
	}
	
public boolean create(com.enercon.model.graph.CustomerMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		com.enercon.model.graph.CustomerMasterVo vo = (com.enercon.model.graph.CustomerMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_CUSTOMER_MASTER(S_CUSTOMER_ID, S_SAP_CUSTOMER_CODE, S_ACTIVE, S_CUSTOMER_NAME, " +
												      " S_CUSTOMER_CONTACT, S_PHONE_NUMBER, S_CELL_NUMBER, S_FAX_NUMBER, " +
													  " S_EMAIL,  S_CREATED_BY, S_LAST_MODIFIED_BY,   S_MARKETING_PERSON ) " +											 
				   						       "values( ?, ?, ?, ?, " +
						   						     "  ?, ?, ?, ?, " +				   						   				   						    
						   						     "  ?, ?, ?, ? ) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getSapCode());
			prepStmt.setObject(3, vo.getActive());
			prepStmt.setObject(4, vo.getName());
			
			
			prepStmt.setObject(5, vo.getContactPerson());			
			prepStmt.setObject(6, vo.getTelephoneNo());			
			prepStmt.setObject(7, vo.getCellNo());
			prepStmt.setObject(8, vo.getFaxNo());
			
			prepStmt.setObject(9, vo.getEmail());
			prepStmt.setObject(10, vo.getCreatedBy());
			prepStmt.setObject(11, vo.getModifiedBy());
			prepStmt.setObject(12, vo.getMarketingPerson());
							
			int rowInserted = prepStmt.executeUpdate();
			
			if(rowInserted == 1)
				Graph.getInstance().customerCreated(vo);
			
            return (rowInserted == 1);	
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(com.enercon.model.graph.CustomerMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		com.enercon.model.graph.CustomerMasterVo vo = (com.enercon.model.graph.CustomerMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_CUSTOMER_MASTER " +
						" SET  S_SAP_CUSTOMER_CODE = ?, S_ACTIVE = ?, S_CUSTOMER_NAME = ?, S_CUSTOMER_CONTACT = ?, " +
						" S_PHONE_NUMBER = ?, S_CELL_NUMBER = ?,  S_FAX_NUMBER = ?, S_EMAIL = ?, " +
						" S_LAST_MODIFIED_BY = ?, D_LAST_MODIFIED_DATE = localtimestamp, S_MARKETING_PERSON = ? " +										
						" WHERE S_CUSTOMER_ID = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getSapCode());
			prepStmt.setObject(2, vo.getActive());
			prepStmt.setObject(3, vo.getName());
			prepStmt.setObject(4, vo.getContactPerson());	
			
			prepStmt.setObject(5, vo.getTelephoneNo());			
			prepStmt.setObject(6, vo.getCellNo());
			prepStmt.setObject(7, vo.getFaxNo());
			prepStmt.setObject(8, vo.getEmail());	
			
			prepStmt.setObject(9, vo.getModifiedBy());
			prepStmt.setObject(10, vo.getMarketingPerson());
			prepStmt.setObject(11, vo.getId());
							
			int rowUpdated = prepStmt.executeUpdate();
			
			if(rowUpdated == 1)
				Graph.getInstance().customerUpdated(vo);
			
			return(rowUpdated == 1);			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public List<ICustomerMasterVo> getAll(){
		
		List<ICustomerMasterVo> customers = new ArrayList<ICustomerMasterVo>();
		for(String custId: Graph.getInstance().getCustomersM().keySet()){
			customers.add(Graph.getInstance().getCustomersM().get(custId));
			//logger.debug("customers :: " + customers);
		}
		
		return customers;
	}
	
	public List<ICustomerMasterVo> populate(List<LoginMasterVo> logins) throws SQLException{
		List<ICustomerMasterVo> customers = new ArrayList<ICustomerMasterVo>();
		Map<String, LoginMasterVo> loginsM = Maps.uniqueIndex(
				logins, new Function<LoginMasterVo, String>() {
					public String apply(LoginMasterVo from) {
						return from.getId();
					}
				});
		
		for(ICustomerMasterVo customer: getAll()){	
			customers.add(customer);
			String loginId = customer.getLoginId();
			if(loginId != null && loginsM.containsKey(loginId))
				loginsM.get(loginId).addCustomer(customer);
		}
		
		return customers;
	}
	
	
	public boolean updateLogin(CustomerLoginVo vo, String loginId) throws SQLException{
		   
	   SortedMap<String, Object> m = new TreeMap<String, Object>();
	   
	   com.enercon.model.graph.CustomerMasterVo updateCustomer = new com.enercon.model.graph.CustomerMasterVo();
	  
	   updateCustomer.setLoginId(vo.getLoginId());
	   m.put("S_LOGIN_MASTER_ID", vo.getLoginId());
	   
	   updateCustomer.setModifiedBy(loginId);
	   m.put("S_LAST_MODIFIED_BY", loginId);
  
	   updateCustomer.setModifiedAt(DateUtility.getTodaysDateInGivenFormat("dd-MMM-yyyy"));
	   m.put("D_LAST_MODIFIED_DATE", "localtimestamp");
	   
	   return partialUpdate(updateCustomer, m);
	  
   }
	   
		private boolean partialUpdate(com.enercon.model.graph.CustomerMasterVo vo, Map<String, Object> m) throws SQLException  {
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
				//Removing Last Comma
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
				
				int rowUpdate = prepStmt.executeUpdate();
				return (rowUpdate == 1);
				
			} finally {
				DaoUtility.releaseResources(prepStmt, rs, conn);
			}
			
		}
		
	public void addCustomerListener(CustomerMasterListener listener){
		listeners.add(listener);
	}
	
	public void fire(CustomerMasterEvent event){
		for(CustomerMasterListener listener: listeners){
			listener.handler(event);
		}
	}
}
