package com.enercon.sqlQuery.sqlQueryDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.enercon.connection.WcareConnector;
import com.enercon.global.utility.MethodClass;
import com.enercon.model.datatype.WEC;
import com.enercon.sqlQuery.AllQueries;

public class InfoQueryDao implements WcareConnector, AllQueries{

	public static WEC getWecInfoBasedOnOneWecId(String wecId) throws SQLException{
		
		WEC wec = new WEC();
		
		String query = getWecInfoBasedOnOneWecIdQuery;
		Connection connection = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			connection = wcareConnector.getConnectionFromPool();
			
			stmt = connection.prepareStatement(query);
			stmt.setString(1, wecId);
			 
			rs = stmt.executeQuery();
			
			while(rs.next()){
				wec.setS_WEC_ID(wecId);
				wec.setS_WEC_TYPE(rs.getString("S_WEC_TYPE"));
				wec.setS_WECSHORT_DESCR(rs.getString("s_WECSHORT_DESCR"));
				wec.setS_FOUND_LOC(rs.getString("s_FOUND_LOC"));
			}
		} finally {
			try{
				if(connection != null){
					wcareConnector.returnConnectionToPool(connection);
				}
				if(stmt != null){
					stmt.close();
				}
				if(rs != null){
					rs.close();
				}
			} catch(Exception e){
				MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return wec;
	}
}
