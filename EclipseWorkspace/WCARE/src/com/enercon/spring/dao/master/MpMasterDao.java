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
import com.enercon.model.master.MpMasterVo;

@Component
@Scope(value="singleton")
public class MpMasterDao {

	private static Logger logger = Logger.getLogger(MpMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public MpMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public boolean create(MpMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_MP_MASTER(S_MP_ID, S_MP_DESCR, S_MP_TYPE, S_MP_SHOW, S_MP_UNIT ,S_CREATED_BY ,S_LAST_MODIFIED_BY ,N_SEQ_NO ,S_STATUS ,S_CUMULATIVE ,S_READ_TYPE ) values " +
															"(:id, :desc, :type, :show, :unit, :createdBy, :modifiedBy, :seqNo, :status, :cumulative, :readType)", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(MpMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_MP_DESCR");
		columns.add("S_MP_TYPE");
		columns.add("S_MP_SHOW");
		columns.add("S_MP_UNIT");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("N_SEQ_NO");
		columns.add("S_STATUS");
		columns.add("S_CUMULATIVE");
		columns.add("S_READ_TYPE");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(MpMasterVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_MP_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_MP_DESCR")){
				query.append(column + " = :desc, ");
			}else if(column.equals("S_MP_TYPE")){
				query.append(column + " = :type, ");
			}else if(column.equals("S_MP_SHOW")){
				query.append(column + " = :show, ");
			}else if(column.equals("S_MP_UNIT")){
				query.append(column + " = :unit, ");
			}else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("N_SEQ_NO")){
				query.append(column + " = :seqNo, ");
			} else if(column.equals("S_STATUS")){
				query.append(column + " = :status, ");
			}else if(column.equals("S_CUMULATIVE")){
				query.append(column + " = :cumulative, ");
			}else if(column.equals("S_READ_TYPE")){
				query.append(column + " = :readType, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_MP_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	public List<MpMasterVo> getAll() throws SQLException {

		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		
		List<MpMasterVo> vos = new ArrayList<MpMasterVo>();
		MpMasterVo vo = new MpMasterVo();
		
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = " SELECT * " +
					" FROM TBL_MP_MASTER " +
					" ORDER BY S_MP_TYPE,N_SEQ_NO ";
			
			prepStmt = conn.prepareStatement(query);			
			
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new MpMasterVo();
				
				vo.setId(rs.getString("S_MP_ID"));
				vo.setDesc(rs.getString("S_MP_DESCR"));
				vo.setType(rs.getString("S_MP_TYPE"));
				vo.setShow(rs.getString("S_MP_SHOW"));
				vo.setUnit(rs.getString("S_MP_UNIT"));
				vo.setSeqNo(rs.getInt("N_SEQ_NO"));
				vo.setStatus(rs.getString("S_STATUS"));
				vo.setCumulative(rs.getString("S_CUMULATIVE"));
				vo.setReadType(rs.getString("S_READ_TYPE"));
				
				vos.add(vo);
				//logger.debug(vo.getDesc());
					
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean exist(MpMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_MP_MASTER WHERE S_MP_DESCR = :desc", params);
		return count == 1;
	}

	
	
}
