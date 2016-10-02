package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.enercon.dao.DaoUtility;
import com.enercon.model.master.RemarkMasterVo;

public class RemarkMasterDao {

private static Logger logger = Logger.getLogger(RemarkMasterDao.class);
	
	private RemarkMasterDao(){}
	
	private static class SingletonHelper{
		public final static RemarkMasterDao INSTANCE = new RemarkMasterDao();
	}
	
	public static RemarkMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<RemarkMasterVo> getAll() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<RemarkMasterVo> vos = new ArrayList<RemarkMasterVo>();
		RemarkMasterVo vo = new RemarkMasterVo();
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_REMARKS " +
					" ORDER BY S_TYPE,S_DESCRIPTION";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new RemarkMasterVo();

				vo.setId(rs.getString("S_REMARKS_ID"));
				vo.setDescription(rs.getString("S_DESCRIPTION"));
				vo.setType(rs.getString("S_TYPE"));
				vo.setWecType(rs.getString("S_WEC_TYPE"));
				vo.setErrorNo(rs.getString("S_ERROR_NO"));
				
				vos.add(vo);
			}
			
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(RemarkMasterVo vo) throws SQLException {
		/*
		"Insert into TBL_REMARKS(S_REMARKS_ID,S_DESCRIPTION,S_TYPE,S_CREATED_BY,S_LAST_MODIFIED_BY "+
		",D_CREATED_DATE,D_LAST_MODIFIED_DATE,S_WEC_TYPE,S_ERROR_NO) values "+
		"(?,?,?,?,?,SYSDATE,SYSDATE,?)";*/
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_REMARKS( S_REMARKS_ID, S_DESCRIPTION, S_TYPE," +
											   " S_CREATED_BY, S_LAST_MODIFIED_BY, "+
											   " S_WEC_TYPE, S_ERROR_NO)" +
									  " values ( ? , ? , ? , " +
									  		   " ? , ? , " +
									  		   " ? , ? ) ";							         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getDescription());
			prepStmt.setObject(3, vo.getType());
			prepStmt.setObject(4, vo.getCreatedBy());
			prepStmt.setObject(5, vo.getModifiedBy());
			prepStmt.setObject(6, vo.getWecType());
			prepStmt.setObject(7, vo.getErrorNo());
			
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	public boolean update(RemarkMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_REMARKS  " +
						" SET S_DESCRIPTION = ? , S_TYPE = ? , "+
						" d_Last_Modified_Date = localtimestamp , s_Last_Modified_By = ?," +
						" S_WEC_TYPE = ?, S_ERROR_NO = ? " +
						" WHERE S_REMARKS_ID  = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getDescription());
			prepStmt.setObject(2, vo.getType());
			prepStmt.setObject(3, vo.getModifiedBy());
			prepStmt.setObject(4, vo.getWecType());
			prepStmt.setObject(5, vo.getErrorNo());
			prepStmt.setObject(6, vo.getId());

			int rowUpdated = prepStmt.executeUpdate();
			
			return(rowUpdated == 1);		
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean check(RemarkMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " SELECT *  " +
						" FROM TBL_REMARKS " +
						" WHERE S_DESCRIPTION = ? AND S_TYPE = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getDescription());
			prepStmt.setObject(2, vo.getType());

			rs = prepStmt.executeQuery();
			while (rs.next()) {
			 if( rs.getInt("COUNT") == 1)
				 return true;
			}
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		return false;
	}
	
}
