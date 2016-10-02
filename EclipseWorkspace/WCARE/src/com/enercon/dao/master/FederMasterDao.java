package com.enercon.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.enercon.dao.DaoUtility;
import com.enercon.model.master.FederMasterVo;

public class FederMasterDao {
	
	private static Logger logger = Logger.getLogger(FederMasterDao.class);

	private FederMasterDao(){}
	
	private static class SingletonHelper{
		public final static FederMasterDao INSTANCE = new FederMasterDao();
	}
	
	public static FederMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	public List<FederMasterVo> getAll() throws SQLException{
		List<FederMasterVo> feders = new ArrayList<FederMasterVo>();
		FederMasterVo feder = null;
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		String id;
		String name;
		String description;
		String type;
		String subType;
		String workingStatus;
		String siteId;
		String capacity;
		double multiFactor;
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select * " +
					"from tbl_feder_master ";
			
			prepStmt = conn.prepareStatement(query);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				feder = new FederMasterVo();
		
				id = rs.getString("S_FEDER_ID");
				name = rs.getString("S_FEDERSHORT_DESCR");
				description = rs.getString("S_FEDER_DESCRIPTION");
				type = rs.getString("S_TYPE");
				subType = rs.getString("S_SUB_TYPE");
				workingStatus = rs.getString("S_STATUS");
				siteId = rs.getString("S_SITE_ID");
				capacity = rs.getString("S_CAPACITY");
				multiFactor = rs.getDouble("N_MULTI_FACTOR");
				
				feder.setId(id);
				feder.setName(name);
				feder.setDescription(description);
				feder.setType(type);
				feder.setSubType(subType);
				feder.setWorkingStatus(workingStatus);
				feder.setSiteId(siteId);
				feder.setCapacity(capacity);
				feder.setMultiFactor(multiFactor);
				
				feders.add(feder);
			}
			return feders;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
public boolean create(FederMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "INSERT INTO TBL_FEDER_MASTER( S_FEDER_ID,S_FEDERSHORT_DESCR,S_FEDER_DESCRIPTION,S_STATUS," +
													" S_SITE_ID,  S_CREATED_BY, S_LAST_MODIFIED_BY ) " +
				     						" VALUES( ?, ?, ?, ?, " +
				     								" ?, ?, ? ) " ;
					   						         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getDescription());
			prepStmt.setObject(4, vo.getWorkingStatus());
			
			prepStmt.setObject(5, vo.getSiteId());
			prepStmt.setObject(6, vo.getCreatedBy());
			prepStmt.setObject(7, vo.getModifiedBy());

			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(FederMasterVo vo) throws SQLException{
		
		SortedMap<String, Object> m = new TreeMap<String, Object>();
		
		//vo.setModifiedBy(vo.getModifiedBy());		
		m.put("S_FEDERSHORT_DESCR", vo.getName());
		m.put("S_FEDER_DESCRIPTION", vo.getDescription());
		m.put("S_STATUS", vo.getWorkingStatus());
		m.put("S_SITE_ID", vo.getSiteId());
		m.put("S_LAST_MODIFIED_BY", vo.getModifiedBy());
		m.put("D_LAST_MODIFIED_DATE", new java.sql.Timestamp(new Date().getTime()));
		return partialUpdate(vo,m);	
	}
	
	private boolean partialUpdate(FederMasterVo vo , Map<String, Object> m) throws SQLException{
		Connection conn = null;
		StringBuilder query = new StringBuilder();
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		int paramterCount = m.size();
		int index = 0;
		try {
			conn = wcareConnector.getConnectionFromPool();
			query.append("UPDATE TBL_FEDER_MASTER SET ");
			
			for(String column: m.keySet()){
				query.append(column + " = ?, ");
			}
			//Removing Last Comma
			query = new StringBuilder(query).deleteCharAt(query.length() - 2);
			query.append("WHERE S_FEDER_ID = ? ");
			logger.debug("S_FEDER_ID :: "+vo.getId());
			prepStmt = conn.prepareStatement(new String(query));
			logger.debug("query :: "+query);
			for(String column: m.keySet()){
				index++;
				prepStmt.setObject(index, m.get(column));
			}
			prepStmt.setObject(paramterCount + 1, vo.getId());
			
			int rowUpdate = prepStmt.executeUpdate();
			return (rowUpdate == 1);
			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
}
