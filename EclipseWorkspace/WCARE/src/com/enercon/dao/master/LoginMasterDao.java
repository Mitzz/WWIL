package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.comparator.CustomerMasterVoComparator;
import com.enercon.model.comparator.StateMasterVoComparator;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.LoginMasterVo;
import com.enercon.model.master.RoleMasterVo;
import com.enercon.model.master.StateMasterVo;
import com.enercon.model.report.IWecParameterVo;
import com.enercon.model.summaryreport.Parameter;
import com.enercon.model.summaryreport.ParameterEvaluator;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class LoginMasterDao {
	private final static Logger logger = Logger.getLogger(LoginMasterDao.class);

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
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return loginMasterVo;
		} finally {
			try {
				if (conn != null) {
					wcareConnector.returnConnectionToPool(conn);
				}
				if (prepStmt != null) {
					prepStmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

	}
	
	public static void main(String[] args) {
		logger.debug("Start");
		LoginMasterDao dao = new LoginMasterDao();
		LoginMasterVo login = null;
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.MA);
		parameters.add(Parameter.GA);
		
		Map<StateMasterVo, IWecParameterVo> stateWiseCustomerWiseWecParameterVo = new TreeMap<StateMasterVo, IWecParameterVo>(StateMasterVoComparator.CUSTOMER);
		
		try {
			login = dao.get("GPEC", "CLPWIND");
			List<CustomerMasterVo> customers = new CustomerMasterDao().get(login);
//			Collections.sort(customers, CustomerMasterVoComparator.NAME);
			for (CustomerMasterVo customer : customers) {
				List<StateMasterVo> states = customer.getStates();
//				Collections.sort(states, StateMasterVoComparator.NAME);
				Map<StateMasterVo, IWecParameterVo> stateWiseWecParameterVo = new ParameterEvaluator().getStateWiseWecParameterVo(states, "14-OCT-2015", parameters);
				
				for(StateMasterVo state : states){
					stateWiseCustomerWiseWecParameterVo.put(state, stateWiseWecParameterVo.get(state));
				}
				
			}
			
			for(StateMasterVo state: stateWiseCustomerWiseWecParameterVo.keySet()){
				IWecParameterVo wecP = stateWiseCustomerWiseWecParameterVo.get(state);
				logger.debug(wecP.generation() + "  :" + state.getName() + ":" + state.getCustomers().get(0).getName());
			}
			
			logger.debug("End");
			wcareConnector.shutDown();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private static void stateWiseCustomerWiseGenerationUsingLoginMaster() {
		logger.debug("Start");
		LoginMasterDao dao = new LoginMasterDao();
		LoginMasterVo login = null;
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(Parameter.GENERATION);
		parameters.add(Parameter.OPERATING_HOUR);
		parameters.add(Parameter.LULL_HOUR);
		parameters.add(Parameter.CF);
		parameters.add(Parameter.MA);
		parameters.add(Parameter.GA);
		
		Map<StateMasterVo, Map<CustomerMasterVo, IWecParameterVo>> stateWiseCustomerWiseWecParameterVo = new TreeMap<StateMasterVo, Map<CustomerMasterVo, IWecParameterVo>>(StateMasterVoComparator.CUSTOMER);
		
		try {
			login = dao.get("GPEC", "CLPWIND");
			List<CustomerMasterVo> customers = new CustomerMasterDao().get(login);
			Collections.sort(customers, CustomerMasterVoComparator.NAME);
			for (CustomerMasterVo customer : customers) {
//				logger.debug("Customer Name::" + customer.getName());
				List<StateMasterVo> states = customer.getStates();
				Collections.sort(states, StateMasterVoComparator.NAME);
				Map<StateMasterVo, IWecParameterVo> stateWiseWecParameterVo = new ParameterEvaluator().getStateWiseWecParameterVo(states, "14-OCT-2015", parameters);
				
				for(StateMasterVo state : states){
					
					if(stateWiseCustomerWiseWecParameterVo.containsKey(state)){
						Map<CustomerMasterVo, IWecParameterVo> oldInner = stateWiseCustomerWiseWecParameterVo.get(state);
						oldInner.put(customer, stateWiseWecParameterVo.get(state));
					} else {
						Map<CustomerMasterVo, IWecParameterVo> newInner = new HashMap<CustomerMasterVo, IWecParameterVo>();
						newInner.put(customer, stateWiseWecParameterVo.get(state));
						stateWiseCustomerWiseWecParameterVo.put(state, newInner);
					}
				}
				
//				logger.debug("------" + s);
//				logger.debug(new ParameterEvaluator().getStateWiseWecParameterVo(states, "14-OCT-2015", parameters));
//				for(StateMasterVo state : states){
//					List<AreaMasterVo> areas = state.getAreas();
//					logger.debug(new ParameterEvaluator().getAreaWiseWecParameterVo(areas, "14-OCT-2015", parameters));
//					for(AreaMasterVo area : areas){
//						List<SiteMasterVo> sites = area.getSites();
//						logger.debug(new ParameterEvaluator().getSiteWiseWecParameterVo(sites, "14-OCT-2015", parameters));
//						for(SiteMasterVo site : sites){
//							List<EbMasterVo> ebs = site.getEbs();
//							logger.debug(new ParameterEvaluator().getEbWiseWecParameterVo(ebs, "14-OCT-2015", parameters));
//						}
//					}
//				}
			}
			
			for(StateMasterVo state: stateWiseCustomerWiseWecParameterVo.keySet()){
				logger.debug(state.getName());
				Map<CustomerMasterVo, IWecParameterVo> cstomers = stateWiseCustomerWiseWecParameterVo.get(state);
				for(CustomerMasterVo customer: cstomers.keySet()){
					IWecParameterVo wecP = cstomers.get(customer);
					logger.debug(customer.getName() + ":" + wecP.generation());
				}
			}
			logger.debug("End");
			wcareConnector.shutDown();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
