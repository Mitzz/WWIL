package com.enercon.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.ajax.ajaxVo.SelectVo;
import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.struts.dao.query.SelectVoQuerySelector;

public class SelectVoDao implements WcareConnector, SelectVoQuerySelector{
	
	 private final static Logger logger = Logger.getLogger(SelectVoDao.class);
	
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
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		finally{
			 DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return selectVo;
	}
}
