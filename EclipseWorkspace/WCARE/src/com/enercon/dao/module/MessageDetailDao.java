package com.enercon.dao.module;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.service.CodeGenerateService;
import com.enercon.struts.form.MessageDetailVo;

public class MessageDetailDao {
	private final static String TABLE = "TBL_MESSAGE_DETAIL";
	private final static Logger logger = Logger.getLogger(MessageDetailDao.class);

	private MessageDetailDao(){}
    
    private static class SingletonHelper{
        private static final MessageDetailDao INSTANCE = new MessageDetailDao();
    }
     
    public static MessageDetailDao getInstance(){
        return SingletonHelper.INSTANCE;
    }
    
    public List<MessageDetailVo> get(){
    	logger.debug("Enter");
    	return null;
    }
    
    public List<MessageDetailVo> getAll() throws SQLException{
    	logger.debug("Enter");
    	
    	Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
    	
		MessageDetailVo vo = new MessageDetailVo();
		List<MessageDetailVo> vos = new ArrayList<MessageDetailVo>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery = "select S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE," +
					   "S_CREATED_BY,D_CREATED_DATE,S_LAST_MODIFIED_BY,D_LAST_MODIFIED_DATE " +
					  " from TBL_MESSAGE_DETAIL order by D_FROM_DATE desc ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				vo = new MessageDetailVo();
				
				vo.setId(rs.getString("S_MESSAGE_DETAIL_ID"));
				vo.setDescription(rs.getString("S_MESSAGE_DESCRIPTION"));
				vo.setFromDate(DateUtility.sqlDateToStringDate(rs.getDate("D_FROM_DATE"), "dd-MMM-yyyy"));
				vo.setToDate(DateUtility.sqlDateToStringDate(rs.getDate("D_TO_DATE"), "dd-MMM-yyyy"));
				vo.setCreatedBy(rs.getString("S_CREATED_BY"));
				vo.setCreatedAt(DateUtility.sqlDateToStringDate(rs.getDate("D_CREATED_DATE"), "dd-MMM-yyyy"));
				vo.setModifiedBy(rs.getString("S_LAST_MODIFIED_BY"));
				vo.setModifiedAt(rs.getString("D_LAST_MODIFIED_DATE"));
				
				vos.add(vo);
				
	
			}
			/*logger.debug(vos.size());
			for (MessageDetailVo list : vos){			
				 logger.debug(list);	
			}*/			
			return vos;	
        }
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
    }
    
    public static void main(String[] args) {
		try {
			new MessageDetailDao().getAll();
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		wcareConnector.shutDown();
	}

	public boolean create(MessageDetailVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String id = CodeGenerateService.getInstance().getId("TBL_MESSAGE_DETAIL");
		vo.setId(id);
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_MESSAGE_DETAIL(S_MESSAGE_DETAIL_ID,S_MESSAGE_DESCRIPTION,D_FROM_DATE,D_TO_DATE," +
					   "S_CREATED_BY,S_LAST_MODIFIED_BY) " +
					   "values(?,?,?,?,?,?) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getDescription());
			prepStmt.setObject(3, vo.getFromDate());
			prepStmt.setObject(4, vo.getToDate());
			prepStmt.setObject(5, vo.getCreatedBy());
			prepStmt.setObject(6, vo.getModifiedBy());
		
			int rowInserted = prepStmt.executeUpdate();
			logger.debug("rowInserted: " + rowInserted);
			return rowInserted == 1;
				
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public boolean delete(MessageDetailVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = 
					"DELETE FROM TBL_MESSAGE_DETAIL " +
					"WHERE S_MESSAGE_DETAIL_ID = ? ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			
			int rowDeleted = prepStmt.executeUpdate();
			logger.debug("rowDeleted: " + rowDeleted);
			if(rowDeleted == 1)
				return true;
			else 
				return false;
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public boolean update(MessageDetailVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  
					"UPDATE TBL_MESSAGE_DETAIL " +
					"SET S_MESSAGE_DESCRIPTION=?, D_FROM_DATE=?, D_TO_DATE=?, S_LAST_MODIFIED_By=?, D_LAST_MODIFIED_DATE=localtimestamp " +
			        "WHERE S_MESSAGE_DETAIL_ID=? ";
			
			prepStmt = conn.prepareStatement(sqlQuery);			
			prepStmt.setObject(1, vo.getDescription());
			prepStmt.setObject(2, vo.getFromDate());
			prepStmt.setObject(3, vo.getToDate());
			prepStmt.setObject(4, vo.getModifiedBy());
			prepStmt.setObject(5, vo.getId());
			
			int rowUpdated = prepStmt.executeUpdate();
			logger.debug("rowUpdated: " + rowUpdated);
			if(rowUpdated == 1)
				return true;
			else 
				return false;
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
}
