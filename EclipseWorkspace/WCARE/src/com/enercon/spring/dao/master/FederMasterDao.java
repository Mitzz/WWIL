package com.enercon.spring.dao.master;

import static com.enercon.connection.WcareConnector.wcareConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.enercon.dao.DaoUtility;
import com.enercon.model.master.FederMasterVo;
@Component
@Scope(value="singleton")
public class FederMasterDao {

	private static Logger logger = Logger.getLogger(FederMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public FederMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
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
					"from TBL_FEDER_MASTER ";
			
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
	
	public boolean create(FederMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_FEDER_MASTER( S_FEDER_ID, S_FEDERSHORT_DESCR, S_FEDER_DESCRIPTION, S_STATUS," +
																	  " S_SITE_ID, S_CREATED_BY, S_LAST_MODIFIED_BY ) values " +
																	" (:id, :name, :description, :workingStatus," +
																	" :siteId, :createdBy, :modifiedBy)", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(FederMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_FEDERSHORT_DESCR");
		columns.add("S_FEDER_DESCRIPTION");
		columns.add("S_STATUS");
		columns.add("S_SITE_ID");
		columns.add("S_LAST_MODIFIED_BY");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(FederMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_FEDER_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_FEDERSHORT_DESCR")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_FEDER_DESCRIPTION")){
				query.append(column + " = :description, ");
			} else if(column.equals("S_STATUS")){
				query.append(column + " = :workingStatus, ");
			} else if(column.equals("S_SITE_ID")){
				query.append(column + " = :siteId, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}
		}
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		//query.append("D_LAST_MODIFIED_DATE = '" + new java.sql.Timestamp(new Date().getTime())+"' ");		
		query.append("WHERE S_FEDER_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(FederMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_FEDER_MASTER WHERE S_FEDERSHORT_DESCR = :name", params);
		return count == 1;
	}

	
	
	
}
