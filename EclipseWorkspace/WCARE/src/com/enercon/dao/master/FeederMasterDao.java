package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.SubstationMasterService;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class FeederMasterDao implements WcareConnector{

	private final static Logger logger = Logger.getLogger(FeederMasterDao.class);
	
	private static class SingletonHelper{
		public final static FeederMasterDao INSTANCE = new FeederMasterDao();
	}
	
	public static FeederMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private FeederMasterDao(){	
	}

	public boolean create(FeederMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_FEEDER_MASTER( S_FEEDER_ID, S_FEEDER_DESC, S_CREATED_BY , " +
					   								 " D_CREATED_DATE   , S_MODIFIED_BY , " +
					   								 " D_MODIFIED_DATE   , S_SUBSTATION_ID ) " +
					   						  "values( ?, ?, ?,localtimestamp, ?, localtimestamp, ? ) " ;
					   						         
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getCreatedBy());
			
			prepStmt.setObject(4, vo.getModifiedBy());
			prepStmt.setObject(5, vo.getSubstationId());
			
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(FeederMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_FEEDER_MASTER " +
						" SET  S_FEEDER_DESC = ?,  S_MODIFIED_BY = ?, D_MODIFIED_DATE = localtimestamp " +										
						" WHERE S_FEEDER_ID = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getName());
			prepStmt.setObject(2, vo.getModifiedBy());
			
	    	prepStmt.setObject(3, vo.getId());
				
			int rowUpdated = prepStmt.executeUpdate();
			
			return(rowUpdated == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	public List<FeederMasterVo> getAll() throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		FeederMasterVo vo = null;
		List<FeederMasterVo> vos = new ArrayList<FeederMasterVo>();
		SubstationMasterVo substationId = null;
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery = " SELECT S_FEEDER_ID, S_FEEDER_DESC, S_CREATED_BY," +
					   " D_CREATED_DATE, S_MODIFIED_BY, D_MODIFIED_DATE, S_SUBSTATION_ID " +
					   " FROM TBL_FEEDER_MASTER ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				vo = new FeederMasterVo();
				
				vo.setId(rs.getString("S_FEEDER_ID"));
				vo.setName(rs.getString("S_FEEDER_DESC"));
				vo.setCreatedBy(rs.getString("S_CREATED_BY"));
				vo.setCreatedAt(rs.getString("D_CREATED_DATE"));
				vo.setModifiedBy(rs.getString("S_MODIFIED_BY"));
				vo.setModifiedAt(rs.getString("D_MODIFIED_DATE"));
				//vo.setSubstation(substationId.getId(rs.getString("S_SUBSTATION_ID")));
							
				vos.add(vo);	
			}
				
			return vos;	
        }
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	//test1();
	public List<FeederMasterVo> associateSubstations(List<SubstationMasterVo> substations) throws SQLException{
		List<FeederMasterVo> feeders = new ArrayList<FeederMasterVo>();
		Map<String, SubstationMasterVo> substationsM = Maps.uniqueIndex(
				substations, new Function<SubstationMasterVo, String>() {

					public String apply(SubstationMasterVo from) {
						return from.getId();
					}
					
				});
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Set<String> ids = substationsM.keySet();
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> idss:  GlobalUtils.splitArrayList(ids, 800)){
				query = "Select substation.s_substation_id, feeder.s_feeder_id, feeder.s_feeder_desc " + 
						"from tbl_substation_master substation, tbl_feeder_master feeder " + 
						"where feeder.s_substation_id(+) = substation.s_substation_id " + 
						"and substation.s_substation_id in " + GlobalUtils.getStringFromArrayForQuery(idss) ;
				
				prepStmt = conn.prepareStatement(query);
				//prepStmt.setObject(1, customerId);
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					String substationId = rs.getString("S_SUBSTATION_ID");
					String feederId = rs.getString("S_FEEDER_ID");
					String feederName = rs.getString("S_FEEDER_DESC");
				
					if(feederId != null){
						feeders.add(new FeederMasterVo.FeederMasterVoBuilder()
										.id(feederId)
										.name(feederName)
										.substation(substationsM.get(substationId))
										.build());
					}
				}
			}
			return feeders;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	

	public static void main(String[] args) {
	
		test1();
	}

	private static void test1() {
		try {
			List<SubstationMasterVo> substations = SubstationMasterService.getInstance().getAll();
			FeederMasterDao.getInstance().associateSubstations(substations);
			
			for(SubstationMasterVo substation: substations){
				logger.debug(substation.getName() + ":" + substation.getFeeders().size());
			}
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
	
}
