package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.global.utility.MethodClass;
import com.enercon.model.master.CustomerMasterVo;
import com.enercon.model.master.WecMasterVo;

public class EbMasterDao{
	private final static Logger logger = Logger.getLogger(EbMasterDao.class);

	public CustomerMasterVo get(CustomerMasterVo customer) throws SQLException{

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		List<WecMasterVo> wecs = customer.getWecs();
		String wecId = null;
		String wecName = null;
		
		try {
			conn = wcareConnector.getConnectionFromPool();

			query = 
					"Select S_wec_id, s_wecshort_descr " + 
					"from tbl_wec_master " + 
					"where S_customer_id = ? " + 
					"and s_status in ('1', '9')  " ; 

			prepStmt = conn.prepareStatement(query);
			prepStmt.setString(1, customer.getId());
			
			rs = prepStmt.executeQuery();
			logger.debug("Done");
			
			while (rs.next()) {
				
				wecId = rs.getString("S_WEC_ID");
				wecName = rs.getString("S_Wecshort_descr");

				WecMasterVo wecMasterVo = new WecMasterVo(wecId);
				wecMasterVo.setName(wecName);
				wecMasterVo.setCustomer(customer);
			}
			
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}

			return customer;
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
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
	}
	
}
