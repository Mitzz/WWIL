package com.enercon.admin.metainfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;

public class AreaMetaInfoDao implements WcareConnector{
    private final static Logger logger = Logger.getLogger(AreaMetaInfoDao.class);
	public static String getAreaNameBasedOnWecId(String wecId) throws SQLException{
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		String areaName = null;
		String sqlQuery = "";
		Connection connection = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			sqlQuery = 	"Select S_Area_Name " + 
						"from customer_meta_data " +
						"where S_Wec_Id = ? ";
			prepStmt = connection.prepareStatement(sqlQuery);
			prepStmt.setString(1, wecId);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				areaName = rs.getString("S_AREA_NAME");
			}
			return areaName;
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, connection);
		}
	}
}
