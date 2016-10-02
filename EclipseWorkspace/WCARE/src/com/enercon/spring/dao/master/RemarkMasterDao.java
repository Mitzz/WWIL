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
import com.enercon.model.master.RemarkMasterVo;

@Component
@Scope(value="singleton")
public class RemarkMasterDao {

	private static Logger logger = Logger.getLogger(RemarkMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public RemarkMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public List<RemarkMasterVo> getAll() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<RemarkMasterVo> vos = new ArrayList<RemarkMasterVo>();
		RemarkMasterVo vo = new RemarkMasterVo();
	
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_REMARKS " +
					" ORDER BY S_TYPE,S_DESCRIPTION";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new RemarkMasterVo();

				vo.setId(rs.getString("S_REMARKS_ID"));
				vo.setDescription(rs.getString("S_DESCRIPTION"));
				vo.setType(rs.getString("S_TYPE"));
				vo.setWecType(rs.getString("S_WEC_TYPE"));
				vo.setErrorNo(rs.getString("S_ERROR_NO"));
				
				vos.add(vo);
			}
			
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(RemarkMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_REMARKS(S_REMARKS_ID, S_DESCRIPTION, S_TYPE, S_CREATED_BY, S_LAST_MODIFIED_BY, S_WEC_TYPE, S_ERROR_NO) values " +
																	"(:id, :description, :type, :createdBy, :modifiedBy, :wecType, :errorNo )", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(RemarkMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_DESCRIPTION");
		columns.add("S_TYPE");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_WEC_TYPE");
		columns.add("S_ERROR_NO");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(RemarkMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_REMARKS SET ");
		
		for(String column: columns){
			if(column.equals("S_DESCRIPTION")){
				query.append(column + " = :description, ");
			} else if(column.equals("S_TYPE")){
				query.append(column + " = :type, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			}else if(column.equals("S_WEC_TYPE")){
				query.append(column + " = :wecType, ");
			}else if(column.equals("S_ERROR_NO")){
				query.append(column + " = :errorNo, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_REMARKS_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(RemarkMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_REMARKS WHERE S_DESCRIPTION = :description AND S_TYPE = :type", params);
		return count == 1;
	}
	
	
	
	
}
