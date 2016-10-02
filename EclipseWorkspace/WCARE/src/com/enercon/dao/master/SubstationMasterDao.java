package com.enercon.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class SubstationMasterDao implements WcareConnector{

	private final static Logger logger = Logger.getLogger(SubstationMasterDao.class);
	
	private static class SingletonHelper{
		public final static SubstationMasterDao INSTANCE = new SubstationMasterDao();
	}
	
	public static SubstationMasterDao getInstance(){
		return SingletonHelper.INSTANCE;
	}
	
	private SubstationMasterDao(){	
	}
	
	public boolean create(SubstationMasterVo vo1) throws SQLException, CloneNotSupportedException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		SubstationMasterVo vo = (SubstationMasterVo) vo1.clone();
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery = "Insert into TBL_SUBSTATION_MASTER( S_SUBSTATION_ID,S_SUBSTATION_DESC,S_SUBSTATION_OF,S_SUBTATION_CAP, " +
					                                      "S_SUBSTATION_MVA,S_SUBSTATION_HV,S_SUBSTATION_LV,S_CREATED_BY, " +
					                                      "S_LAST_MODIFIED_BY,S_AREA_ID, " +
					                                      "N_TTL_TRANSFORMER,S_SUBSTATION_REMARKS ) " +
					   						      "values( ?, ?, ?, ?, " +
							   						    "  ?, ?, ?, ?, " +
							   						    "  ?, ?, " +
							   						    "  ?, ?) ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			prepStmt.setObject(1, vo.getId());
			prepStmt.setObject(2, vo.getName());
			prepStmt.setObject(3, vo.getOwner());
			prepStmt.setObject(4, vo.getCapacity());
			
			prepStmt.setObject(5, vo.getMva());
			prepStmt.setObject(6, vo.getHighVoltage());
			prepStmt.setObject(7, vo.getLowVoltage());
			prepStmt.setObject(8, vo.getCreatedBy());
			
			prepStmt.setObject(9, vo.getModifiedBy());
			prepStmt.setObject(10, vo.getAreaId());
			
			prepStmt.setObject(11, vo.getTransformerCount());
			prepStmt.setObject(12, vo.getRemark());
					
			int rowInserted = prepStmt.executeUpdate();
			
            return (rowInserted == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean update(SubstationMasterVo vo) throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			sqlQuery =  " UPDATE TBL_SUBSTATION_MASTER " +
						" SET  S_SUBSTATION_DESC = ?, S_SUBSTATION_OF = ?, S_SUBTATION_CAP = ?, " +
						" S_SUBSTATION_MVA = ?, S_SUBSTATION_HV = ?, S_SUBSTATION_LV = ?, " +
						" S_LAST_MODIFIED_BY = ?, D_LAST_MODIFIED_DATE = localtimestamp, " +
						" N_TTL_TRANSFORMER = ?, S_SUBSTATION_REMARKS = ? " +
						" WHERE S_SUBSTATION_ID = ?  " ;
	
			prepStmt = conn.prepareStatement(sqlQuery);
			
			prepStmt.setObject(1, vo.getName());
			prepStmt.setObject(2, vo.getOwner());
			prepStmt.setObject(3, vo.getCapacity());
			
			prepStmt.setObject(4, vo.getMva());
			prepStmt.setObject(5, vo.getHighVoltage());
			prepStmt.setObject(6, vo.getLowVoltage());
			
			prepStmt.setObject(7, vo.getModifiedBy());
			
			prepStmt.setObject(8, vo.getTransformerCount());
			prepStmt.setObject(9, vo.getRemark());
			
			prepStmt.setObject(10, vo.getId());
				
			int rowUpdated = prepStmt.executeUpdate();
			logger.debug("Update: " + rowUpdated);  
			return(rowUpdated == 1);
			
		}
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
	}
	
	public List<SubstationMasterVo> getAll() throws SQLException {
		
		Connection conn = null;
		String sqlQuery = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		SubstationMasterVo vo = null;
		List<SubstationMasterVo> vos = new ArrayList<SubstationMasterVo>();
		
		try{
			conn = wcareConnector.getConnectionFromPool();
			
			sqlQuery = "SELECT S_SUBSTATION_ID , S_SUBSTATION_DESC , S_SUBSTATION_OF , S_SUBTATION_CAP , " +
					   "S_SUBSTATION_MVA , S_SUBSTATION_HV , S_SUBSTATION_LV , S_CREATED_BY , " +
					   "D_CREATED_DATE , S_LAST_MODIFIED_BY , D_LAST_MODIFIED_DATE , S_AREA_ID , " +
					   "N_TTL_TRANSFORMER , S_SUBSTATION_REMARKS " +
					   "FROM TBL_SUBSTATION_MASTER ";
			
			prepStmt = conn.prepareStatement(sqlQuery);
			rs = prepStmt.executeQuery();
			
			while (rs.next()) {
				vo = new SubstationMasterVo();
				
				vo.setId(rs.getString("S_SUBSTATION_ID"));
				vo.setName(rs.getString("S_SUBSTATION_DESC"));
				vo.setOwner(rs.getString("S_SUBSTATION_OF"));
				vo.setCapacity(rs.getString("S_SUBTATION_CAP"));
				vo.setMva(rs.getString("S_SUBSTATION_MVA"));
				vo.setHighVoltage(rs.getString("S_SUBSTATION_HV"));
				vo.setLowVoltage(rs.getString("S_SUBSTATION_LV"));
				vo.setCreatedBy(rs.getString("S_CREATED_BY"));
				vo.setCreatedAt(rs.getString("D_CREATED_DATE"));
				vo.setModifiedBy(rs.getString("S_LAST_MODIFIED_BY"));
				vo.setModifiedAt(rs.getString("D_LAST_MODIFIED_DATE"));
				vo.setArea(AreaMasterService.getInstance().get(rs.getString("S_AREA_ID")));
				vo.setTransformerCount(rs.getInt("N_TTL_TRANSFORMER"));
				vo.setRemark(rs.getString("S_SUBSTATION_REMARKS"));
							
				vos.add(vo);	
			}
			
			for(IAreaMasterVo area: AreaMasterService.getInstance().getAll()){
				logger.debug(String.format("%s has %s substations", area.getName(), area.getSubstations().size()));
			}
				
			return vos;	
        }
		finally{
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
		
		
		
	}
	
	public List<SubstationMasterVo> associateArea(List<IAreaMasterVo> areas) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<SubstationMasterVo> substations = new ArrayList<SubstationMasterVo>();
		
		Map<String, IAreaMasterVo> areasM = Maps.uniqueIndex(
				areas, new Function<IAreaMasterVo, String>() {
					
					public String apply(IAreaMasterVo from) {
						from.getSubstations().clear();
						return from.getId();
					}
					
				});
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "Select s_substation_desc, s_substation_id, S_area_id " +
					"from tbl_substation_master";
			
			prepStmt = conn.prepareStatement(query);
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				String areaId = rs.getString("S_area_id");
				String substationId = rs.getString("S_substation_id");
				String substationName = rs.getString("S_substation_desc");
				SubstationMasterVo substation = new SubstationMasterVo.SubstationMasterVoBuilder().id(substationId).name(substationName).build();
				if(areaId != null && areasM.containsKey(areaId)){
					substation.setArea(areasM.get(areaId));
				}
				substations.add(substation);
			}
			
			
			return substations;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public void associate(List<FeederMasterVo> feeders) throws SQLException{
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		Map<String, FeederMasterVo> feedersM = Maps.uniqueIndex(
				feeders, new Function<FeederMasterVo, String>() {
					
					public String apply(FeederMasterVo from) {
						return from.getId();
					}
					
				});
		
		Map<String, SubstationMasterVo> substationsM = new HashMap<String, SubstationMasterVo>();
		
		Set<String> feederIds = feedersM.keySet();
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> ids: GlobalUtils.splitArrayList(feederIds, 950)){
				query = "Select feeder.s_feeder_id, substation.s_substation_id, substation.s_substation_desc " + 
						"from tbl_substation_master substation, tbl_feeder_master feeder " + 
						"where feeder.s_substation_id = substation.s_substation_id(+) " + 
						"and feeder.s_feeder_id in " + GlobalUtils.getStringFromArrayForQuery(ids); 
				
				prepStmt = conn.prepareStatement(query);
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					String feederId = rs.getString("S_feeder_id");
					String substationId = rs.getString("S_substation_id");
					String substationName = rs.getString("S_substation_desc");
					
					if(substationsM.containsKey(substationId)){
						feedersM.get(feederId).setSubstation(substationsM.get(substationId));
					} else {
						SubstationMasterVo substation = new SubstationMasterVo.SubstationMasterVoBuilder().id(substationId).name(substationName).build();
						substationsM.put(substationId, substation);
						feedersM.get(feederId).setSubstation(substation);
					}
				}
			}
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public static void main(String[] args) {
		try {
			List<FeederMasterVo> feeders = FeederMasterDao.getInstance().getAll();
			SubstationMasterDao.getInstance().associate(feeders);
			
			for(FeederMasterVo feeder: feeders){
				logger.debug(feeder.getName() + ":" + feeder.getSubstation().getName());
			}
			
			
		} catch (SQLException e) {
			logger.error("\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);
		}
	}
}
