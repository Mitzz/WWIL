package com.enercon.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
 
import com.enercon.dao.DaoUtility;
import com.enercon.model.master.StateMasterVo;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
 
public class ExampleJDBC implements WcareConnector{
	
	private final static Logger logger = Logger.getLogger(ExampleJDBC.class);
 
	/*public static void main(String[] args) {
		BoneCP connectionPool = null;
		Connection connection = null;
 
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		try {

			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl("jdbc:oracle:thin:@172.18.16.108:1521:wcare"); // jdbc url specific to your database, eg jdbc:mysql://127.0.0.1/yourdb
			config.setUsername("ecare"); 
			config.setPassword("customer2011");
			config.setMinConnectionsPerPartition(5);
			config.setMaxConnectionsPerPartition(10);
			config.setPartitionCount(1);
			
			connectionPool = new BoneCP(config); // setup the connection pool
			
			connection = connectionPool.getConnection(); // fetch a connection
			
			if (connection != null){
				System.out.println("Connection successful!");
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT S_state_name FROM tbl_state_master"); // do something with the connection.
				while(rs.next()){
					System.out.println(rs.getString(1)); // should print out "1"'
				}
			}
			connectionPool.shutdown(); // shutdown connection pool.
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}*/
	
	public static void main(String[] args) throws SQLException {

		List<StateMasterVo> stateMasterVos = new ArrayList<StateMasterVo>();

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

//		String wecId = null;
//		String wecName = null;
//		String ebId = null;
//		String ebName = null;
//		String siteId = null;
//		String siteName = null;
//		String areaId = null;
//		String areaName = null;
		String stateId = null;
		String stateName = null;
		try {
			conn = wcareConnector.getConnectionFromPool();

			
				query = "Select S_state_name, S_state_id " +
						"from tbl_state_master " ;
//						"from customer_meta_data " + 
//						"where S_status in ('1')" ;
				prepStmt = conn.prepareStatement(query);
				rs = prepStmt.executeQuery();
				
				while (rs.next()) {
					stateId = rs.getString("S_state_ID");
					stateName = rs.getString("S_state_name");

					StateMasterVo vo = new StateMasterVo(stateId);
					vo.setName(stateName);
					stateMasterVos.add(vo);
				}
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
}