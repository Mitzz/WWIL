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
import com.enercon.model.master.StandardMessageMasterVo;
import com.enercon.service.CodeGenerateService;
import com.enercon.service.StdMessageService;

public class StdMessageDao {

	private final static String TABLE = "tbl_standard_message";
	private final static Logger logger = Logger.getLogger(StdMessageService.class);
	
	private StdMessageDao(){}
    
    private static class SingletonHelper{
        private static final StdMessageDao INSTANCE = new StdMessageDao();
    }
     
    public static StdMessageDao getInstance(){
        return SingletonHelper.INSTANCE;
    }

    public boolean create(StandardMessageMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String id = CodeGenerateService.getInstance().getId("TBL_STANDARD_MESSAGE");
		vo.setId(id);
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into tbl_standard_message(S_STANDARD_MESSAGE_ID,S_MESSAGE_HEAD,S_MESSAGE_DESCRIPTION,S_CREATED_BY," +
					                                    "S_LAST_MODIFIED_BY) " +
					   "values(?,?,?,?,?) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getMessageHead());
			prepStmt.setObject(3, vo.getMessageDescription());
			prepStmt.setObject(4, vo.getCreatedBy());
			prepStmt.setObject(5, vo.getModifiedBy());
			
			int rowInserted = prepStmt.executeUpdate();
			logger.debug("rowInserted: " + rowInserted);
			if(rowInserted == 1)
				return true;
			else 
				return false;
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public List<StandardMessageMasterVo> getAll() throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		StandardMessageMasterVo vo = new StandardMessageMasterVo();
		List<StandardMessageMasterVo> vos = new ArrayList<StandardMessageMasterVo>();
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery = "Select S_STANDARD_MESSAGE_ID,S_MESSAGE_HEAD,S_MESSAGE_DESCRIPTION,S_CREATED_BY,D_CREATED_DATE,S_LAST_MODIFIED_BY,D_LAST_MODIFIED_DATE " +
						"from TBL_STANDARD_MESSAGE " +
						"order by S_MESSAGE_HEAD ";

			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				
				vo.setId(rs.getString("S_STANDARD_MESSAGE_ID"));
				vo.setMessageHead(rs.getString("S_MESSAGE_HEAD"));
				vo.setMessageDescription(rs.getString("S_MESSAGE_DESCRIPTION"));
				vo.setCreatedBy(rs.getString("S_CREATED_BY"));
				vo.setCreatedAt(rs.getString("D_CREATED_DATE"));
				vo.setModifiedBy(rs.getString("S_LAST_MODIFIED_BY"));
				vo.setModifiedAt(rs.getString("D_LAST_MODIFIED_DATE"));
				
				vos.add(vo);
				vo = new StandardMessageMasterVo();
				    
//				    logger.debug(vos);
							
			} 
			logger.debug(vos.size());
			/*for (StandardMessageMasterVo list : vos){			
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
			new StdMessageDao().getAll();
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		wcareConnector.shutDown();
	}
}
