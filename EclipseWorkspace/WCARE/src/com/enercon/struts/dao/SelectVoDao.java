package com.enercon.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.connection.WcareConnector;
import com.enercon.struts.dao.query.SelectVoQuerySelector;

public class SelectVoDao implements WcareConnector, SelectVoQuerySelector{
	public static List<SelectVo> getSelectVo(String queryResolver){

		List<SelectVo> selectVo = new ArrayList<SelectVo>();
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;

		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = selectVoQueryResolver.get(queryResolver);
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				selectVo.add(new SelectVo(rs.getString(1), rs.getString(2)));
			}
			//System.out.println(queryResolver + " Done!!!");
			return selectVo;
		}
		catch(Exception e){
			//MethodClass.displayMethodClassName();
			e.printStackTrace();
		}
		finally{
			try{
				if(conn != null){
					wcareConnector.returnConnectionToPool(conn);
				}
				if(prepStmt != null){
					prepStmt.close();
				}
				if(rs != null){
					rs.close();
				}
			}
			catch(Exception e){
				//MethodClass.displayMethodClassName();
				e.printStackTrace();
			}
		}
		return selectVo;
	}
}
