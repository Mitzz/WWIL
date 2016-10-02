package com.enercon.sqlQuery.sqlQueryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.sqlQuery.AllQueries;

public class FindingQueryDao implements WcareConnector, AllQueries {
	private final static Logger logger = Logger.getLogger(FindingQueryDao.class);
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
			DaoUtility.displayQueryWithParameter(12, query, ebId);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				DaoUtility.getRowCount(12, rs);
				wecIds.add(rs.getString("S_wec_id"));
			}
		} finally {
			 DaoUtility.releaseResources(stmt, rs, connection);

		}
		return wecIds;

	}
}
