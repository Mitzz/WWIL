package com.enercon.spring.dao.master;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.master.FeederMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

@Component
@Scope(value="singleton")
public class FeederMasterDao implements WcareConnector{

	private static Logger logger = Logger.getLogger(FeederMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public FeederMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}

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
	
	
	
	public boolean create(FeederMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_FEEDER_MASTER(S_FEEDER_ID, S_FEEDER_DESC, S_CREATED_BY, S_MODIFIED_BY, S_SUBSTATION_ID) values " +
																	   "(:id, :name, :createdBy, :modifiedBy, :substationId )", params);
		
		return (rowInserted == 1);
	}
	
	public boolean updateForMaster(FeederMasterVo vo) throws SQLException, CloneNotSupportedException   {
		
		List<String> columns = new ArrayList<String>();
		columns.add("S_FEEDER_DESC");		
		columns.add("S_MODIFIED_BY");
		columns.add("S_SUBSTATION_ID");
		
		return partialUpdate(vo, columns);
	}
	
	private boolean partialUpdate(FeederMasterVo feeder, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(feeder);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_FEEDER_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_FEEDER_DESC")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}else if(column.equals("S_SUBSTATION_ID")){
				query.append(column + " = :substationId, ");
			}
		}
		
		query.append("D_MODIFIED_DATE = localtimestamp ");		
		query.append("WHERE S_FEEDER_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(FeederMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_FEEDER_MASTER WHERE S_FEEDER_DESC = :name", params);
		return count == 1;
	}
	

	
}
