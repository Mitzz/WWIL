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
import com.enercon.model.master.MpMasterVo;
import com.enercon.model.master.WecTypeMasterVo;


public class MpMasterDao {

	private static Logger logger = Logger.getLogger(MpMasterDao.class);
	
	private MpMasterDao(){}
	
	private static class SingletonHelper{
		public final static MpMasterDao INSTANCE = new MpMasterDao();
	}
	
	public static MpMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}

	public List<MpMasterVo> getAll() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<MpMasterVo> vos = new ArrayList<MpMasterVo>();
		MpMasterVo vo = new MpMasterVo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_MP_MASTER " +
					" ORDER BY S_MP_TYPE,N_SEQ_NO ";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new MpMasterVo();
				
				vo.setId(rs.getString("S_MP_ID"));
				vo.setDesc(rs.getString("S_MP_DESCR"));
				vo.setType(rs.getString("S_MP_TYPE"));
				vo.setShow(rs.getString("S_MP_SHOW"));
				vo.setUnit(rs.getString("S_MP_UNIT"));
				vo.setSeqNo(rs.getInt("N_SEQ_NO"));
				vo.setStatus(rs.getString("S_STATUS"));
				vo.setCumulative(rs.getString("S_CUMULATIVE"));
				vo.setReadType(rs.getString("S_READ_TYPE"));
				
				vos.add(vo);
				//logger.debug(vo.getDesc());
					
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(MpMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_MP_MASTER( S_MP_ID, S_MP_DESCR, S_MP_TYPE, S_MP_SHOW,  "+
							                     " S_MP_UNIT, S_CREATED_BY, S_LAST_MODIFIED_BY, N_SEQ_NO, " +
							                     " S_STATUS, S_CUMULATIVE, S_READ_TYPE) " +
							             "values (? , ? , ? , ? ," +
							                     "? , ? , ? , ? ," +
							                     "? , ? , ?)" ;
					   						         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getDesc());
			prepStmt.setObject(3, vo.getType());
			prepStmt.setObject(4, vo.getShow());
			
			prepStmt.setObject(5, vo.getUnit());
			prepStmt.setObject(6, vo.getCreatedBy());
			prepStmt.setObject(7, vo.getModifiedBy());
			prepStmt.setObject(8, vo.getSeqNo());
			
			prepStmt.setObject(9, vo.getStatus());
			prepStmt.setObject(10, vo.getCumulative());
			prepStmt.setObject(11, vo.getReadType());
			
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(MpMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_MP_MASTER  SET S_MP_DESCR = ? , S_MP_TYPE = ? , S_MP_SHOW=?, " +
					    " S_MP_UNIT = ?, S_LAST_MODIFIED_BY = ?, N_SEQ_NO = ?, " +
					    " S_STATUS = ?, S_CUMULATIVE = ?, S_READ_TYPE = ? " +
					    " WHERE S_MP_ID = ? " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			
			prepStmt.setObject(1, vo.getDesc());
			prepStmt.setObject(2, vo.getType());
			prepStmt.setObject(3, vo.getShow());
			
			prepStmt.setObject(4, vo.getUnit());
			prepStmt.setObject(5, vo.getModifiedBy());
			prepStmt.setObject(6, vo.getSeqNo());
			
			prepStmt.setObject(7, vo.getStatus());
			prepStmt.setObject(8, vo.getCumulative());
			prepStmt.setObject(9, vo.getReadType());
			
			prepStmt.setObject(10, vo.getId());

			int rowUpdated = prepStmt.executeUpdate();
			
			return(rowUpdated == 1);		
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean check(MpMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  "SELECT Count(*) as COUNT FROM TBL_MP_MASTER WHERE S_MP_DESCR= ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getDesc());

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
	
	public static void main(String args[]) throws SQLException{
		
		MpMasterDao dao = new MpMasterDao();
		dao.getAll();
	}
	
	public List<MpMasterVo> getAllByWec() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<MpMasterVo> vos = new ArrayList<MpMasterVo>();
		MpMasterVo vo = new MpMasterVo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_MP_MASTER " +
					" WHERE S_MP_SHOW IN ('WEC','BOTH') " +
					" AND S_STATUS = 1 " +
					" ORDER BY N_SEQ_NO ";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new MpMasterVo();
				
				vo.setId(rs.getString("S_MP_ID"));
				vo.setDesc(rs.getString("S_MP_DESCR"));
				vo.setType(rs.getString("S_MP_TYPE"));
				vo.setShow(rs.getString("S_MP_SHOW"));
				vo.setUnit(rs.getString("S_MP_UNIT"));
				vo.setSeqNo(rs.getInt("N_SEQ_NO"));
				vo.setStatus(rs.getString("S_STATUS"));
				vo.setCumulative(rs.getString("S_CUMULATIVE"));
				vo.setReadType(rs.getString("S_READ_TYPE"));
				
				vos.add(vo);
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public List<MpMasterVo> getMpByEb() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<MpMasterVo> vos = new ArrayList<MpMasterVo>();
		MpMasterVo vo = new MpMasterVo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_MP_MASTER " +
					" WHERE S_MP_SHOW IN ('EB','BOTH') " +
					" AND S_STATUS = 1 " +
					" ORDER BY N_SEQ_NO ";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new MpMasterVo();
				
				vo.setId(rs.getString("S_MP_ID"));
				vo.setDesc(rs.getString("S_MP_DESCR"));
				vo.setType(rs.getString("S_MP_TYPE"));
				vo.setShow(rs.getString("S_MP_SHOW"));
				vo.setUnit(rs.getString("S_MP_UNIT"));
				vo.setSeqNo(rs.getInt("N_SEQ_NO"));
				vo.setStatus(rs.getString("S_STATUS"));
				vo.setCumulative(rs.getString("S_CUMULATIVE"));
				vo.setReadType(rs.getString("S_READ_TYPE"));
				
				vos.add(vo);
				//logger.debug(vo.getDesc());
					
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
}
