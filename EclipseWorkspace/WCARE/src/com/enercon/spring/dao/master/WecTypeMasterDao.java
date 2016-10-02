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
import com.enercon.model.master.WecTypeMasterVo;
@Component
@Scope(value="singleton")
public class WecTypeMasterDao {
	
	private static Logger logger = Logger.getLogger(WecTypeMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public WecTypeMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
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
	
	public boolean create(WecTypeMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_WEC_TYPE(S_WEC_TYPE_ID, S_WEC_TYPE, N_WEC_CAPACITY, S_CREATED_BY, S_LAST_MODIFIED_BY) values " +
																	"(:id, :description, :capacity, :createdBy, :modifiedBy)", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(WecTypeMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_WEC_TYPE");
		columns.add("N_WEC_CAPACITY");
		columns.add("S_LAST_MODIFIED_BY");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(WecTypeMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_WEC_TYPE SET ");
		
		for(String column: columns){
			if(column.equals("S_WEC_TYPE")){
				query.append(column + " = :description, ");
			} else if(column.equals("N_WEC_CAPACITY")){
				query.append(column + " = :capacity, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_WEC_TYPE_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(WecTypeMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_WEC_TYPE WHERE S_WEC_TYPE = :description", params);
		return count == 1;
	}

	
	
}
