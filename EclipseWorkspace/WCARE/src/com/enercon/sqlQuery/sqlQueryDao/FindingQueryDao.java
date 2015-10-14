package com.enercon.sqlQuery.sqlQueryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.MethodClass;
import com.enercon.sqlQuery.AllQueries;

public class FindingQueryDao implements WcareConnector, AllQueries {

	public static Set<String> getActiveWecIdsBasedOnOneEbId(String ebId)
			throws SQLException {

		Set<String> wecIds = new LinkedHashSet<String>();

		Connection connection = null;

		String query = getActiveWECIdsBasedOnEbIdQuery;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			connection = wcareConnector.getConnectionFromPool();
			stmt = connection.prepareStatement(query);

			stmt.setString(1, ebId);
			rs = stmt.executeQuery();

			while (rs.next()) {
				wecIds.add(rs.getString("S_wec_id"));
			}
		} finally {
			try {
				if (connection != null) {
					wcareConnector.returnConnectionToPool(connection);
				}
				if (stmt != null) {
					stmt.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}

		}
		return wecIds;

	}
}
