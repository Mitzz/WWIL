package com.enercon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.MethodClass;

public class IredaDAO implements WcareConnector{
	
	private final static String GET_STATE_ID_WITH_NAME = 
			"Select cmd.S_state_id, cmd.S_state_name " + 
			"from tbl_project proj, tbl_wec_project_mapping mapper, customer_meta_data cmd " + 
			"where Proj.S_Project_Id = Mapper.S_Project_Id " + 
			"and cmd.S_wec_id = mapper.S_wec_id " + 
			"and proj.S_project_No = ? " + 
			"group by cmd.S_state_id, cmd.S_state_name " + 
			"order by cmd.S_state_name ";
	
	private final static String GET_WEC_IDS_BASED_ON_STATE_ID = 
			"Select Cmd.S_Wec_Id, Cmd.S_Wecshort_Descr  " + 
			"from tbl_project proj, tbl_wec_project_mapping mapper, customer_meta_data cmd " + 
			"where Proj.S_Project_Id = Mapper.S_Project_Id " + 
			"and cmd.S_wec_id = mapper.S_wec_id " + 
			"and proj.S_project_No = ? " + 
			"and Cmd.S_State_Id = ? " + 
			"group by Cmd.S_Wec_Id , Cmd.S_Wecshort_Descr " + 
			"order by Cmd.S_Wecshort_Descr";
	
	private final static String GET_WEC_ID_WITH_NAME =
			"Select wecmaster.S_Wec_id, wecmaster.S_wecshort_descr " + 
			"from tbl_project proj, tbl_wec_project_mapping mapper, tbl_wec_master wecmaster " + 
			"where Proj.S_Project_Id = Mapper.S_Project_Id " + 
			"and wecmaster.S_wec_id = mapper.S_wec_id " + 
			"and proj.S_project_No = ? " + 
			"group by wecmaster.S_Wec_id, wecmaster.S_wecshort_descr " + 
			"order by wecmaster.S_wecshort_descr ";

	private final static String GET_ALL_IREDA_PROJECT_NO = 
			"Select s_project_no " + 
			"from tbl_project " + 
			"order by s_project_no ";
	
	public class DAO{
		private String query;
		private String[] queryParameter;
		
		public DAO(String query, String[] queryParameter) {
			super();
			this.query = query;
			this.queryParameter = queryParameter;
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
					MethodClass.displayMethodClassName();
					e.printStackTrace();
				}
			}
			return result;
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
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					result.put(rs.getString(1), rs.getString(2));
				}
				
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			finally {
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
					MethodClass.displayMethodClassName();
					e.printStackTrace();
				}
			}
			return result;
		}
		
	}

	public Map<String, String> getStateIdNameMapping(String projectNo) throws SQLException{
		return new DAO(GET_STATE_ID_WITH_NAME,new String[]{projectNo}).getResultInMap(); 
	}

	public Set<String> getWecIdsBasedOnStateId(String projectNo, String stateId) throws SQLException {
		return new DAO(GET_WEC_IDS_BASED_ON_STATE_ID, new String[]{projectNo, stateId}).getResultInSet();
	}

	public Map<String, String> getWecIdNameMapping(String projectNo) throws SQLException {
		return new DAO(GET_WEC_ID_WITH_NAME, new String[]{projectNo}).getResultInMap();
	}

	public Set<String> getAllIredaProjectNo() throws SQLException {
		return new DAO(GET_ALL_IREDA_PROJECT_NO, new String[]{}).getResultInSet();
	}
}
