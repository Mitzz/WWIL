package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.IWecMasterVo;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.WecTypeMasterVo;

public class WecTypeMasterDao {

	private static Logger logger = Logger.getLogger(WecTypeMasterDao.class);
	
	private WecTypeMasterDao(){}
	
	private static class SingletonHelper{
		public final static WecTypeMasterDao INSTANCE = new WecTypeMasterDao();
	}
	
	public static WecTypeMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}

	
	public List<WecTypeMasterVo> getAll() throws SQLException {
//		testGetAll();
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<WecTypeMasterVo> wecTypes = new ArrayList<WecTypeMasterVo>();
		WecTypeMasterVo wecType = new WecTypeMasterVo();
		
		String id = "";
		String description = "";
		double capacity = -1;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select * " +
					"from tbl_wec_type ";
			
			prepStmt = conn.prepareStatement(query);
			//prepStmt.setObject(1, customerId);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecType = new WecTypeMasterVo();
				
				id = rs.getString("S_wec_Type_ID");
				description = rs.getString("S_wec_Type");
				capacity = rs.getShort("N_wec_capacity");
				
				wecType.setId(id);
				wecType.setDescription(description);
				wecType.setCapacity(capacity);
				
				wecTypes.add(wecType);
			}
			
			return wecTypes;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}


	public static void main(String[] args) {
		WecTypeMasterDao dao = WecTypeMasterDao.getInstance();
		dao.testGetByIds(null);
	}
	
	private void testGetAll() {
		List<WecTypeMasterVo> all = null;
		try {
			all = getAll();
			for(WecTypeMasterVo wecType: all){
				logger.debug(wecType);
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}


	//testGetById("E-53");
	public WecTypeMasterVo get(String id) throws SQLException {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		WecTypeMasterVo wecType = null;
		
		String description = "";
		double capacity = -1;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select * " +
					"from tbl_wec_type " +
					"where S_wec_type_id = ? ";
			
			prepStmt = conn.prepareStatement(query);
			prepStmt.setObject(1, id);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecType = new WecTypeMasterVo();
				
				description = rs.getString("S_wec_Type");
				capacity = rs.getShort("N_wec_capacity");
				
				wecType.setId(id);
				wecType.setDescription(description);
				wecType.setCapacity(capacity);
				
				
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			
			return wecType;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}


	private void testGetById(String string) {
		try {
			WecTypeMasterVo wecTypeMaster = get("E-53");
			logger.debug(wecTypeMaster);
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}


	public List<WecTypeMasterVo> get(ISiteMasterVo site) throws SQLException {
		List<IWecMasterVo> wecs = site.getWecs();
		Set<String> wecTypeIds = new HashSet<String>();
		
		for(IWecMasterVo wec: wecs){
			wecTypeIds.add(wec.getType());
		}
		List<WecTypeMasterVo> wecTypes = get(wecTypeIds);
		
		return wecTypes;
	}


	private List<WecTypeMasterVo> get(Collection<String> wecTypeIds) throws SQLException {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<WecTypeMasterVo> wecTypes = new ArrayList<WecTypeMasterVo>();
		
		WecTypeMasterVo wecType = null;
		
		String id = "";
		String description = "";
		double capacity = -1;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select * " +
					"from tbl_wec_type " +
					"where S_wec_type_id in " + GlobalUtils.getStringFromArrayForQuery(wecTypeIds);
			
			prepStmt = conn.prepareStatement(query);
			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				wecType = new WecTypeMasterVo();
				
				id = rs.getString("S_wec_Type_id");
				description = rs.getString("S_wec_Type");
				capacity = rs.getShort("N_wec_capacity");
				
				wecType.setId(id);
				wecType.setDescription(description);
				wecType.setCapacity(capacity);
				
				wecTypes.add(wecType);
			}
			if (prepStmt != null) {
				prepStmt.close();
			}
			if (rs != null) {
				rs.close();
			}
			
			return wecTypes;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}


	private void testGetByIds(List<String> ids) {
		ids = Arrays.asList("E-53", "E-48");
		try {
			logger.debug(get(ids));
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
		
	}
	
	public boolean create(WecTypeMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "INSERT INTO TBL_WEC_TYPE( S_WEC_TYPE_ID, S_WEC_TYPE, N_WEC_CAPACITY, " +
						     					" S_CREATED_BY, S_LAST_MODIFIED_By ) " +
									    " VALUES( ?, ?, ?, " +
									            " ?, ? ) " ;
					   						         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getDescription());
			prepStmt.setObject(3, vo.getCapacity());
			prepStmt.setObject(4, vo.getCreatedBy());
			prepStmt.setObject(5, vo.getModifiedBy());

			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(WecTypeMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_WEC_TYPE  SET S_WEC_TYPE = ? , N_WEC_CAPACITY = ? , " +
					    " S_LAST_MODIFIED_BY = ? WHERE S_WEC_TYPE_ID = ? " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getDescription());
			prepStmt.setObject(2, vo.getCapacity());
			prepStmt.setObject(3, vo.getModifiedBy());
			prepStmt.setObject(4, vo.getId());

			int rowUpdated = prepStmt.executeUpdate();
			
			return(rowUpdated == 1);		
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean check(WecTypeMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  "select count(*) as COUNT from TBL_WEC_TYPE where S_WEC_TYPE = ? " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);	
			prepStmt.setObject(1, vo.getDescription());

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
