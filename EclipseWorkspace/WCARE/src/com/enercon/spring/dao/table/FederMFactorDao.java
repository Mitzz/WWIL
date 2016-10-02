package com.enercon.spring.dao.table;

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
import com.enercon.global.utility.DateUtility;
import com.enercon.global.utils.GlobalUtils;
import com.enercon.model.master.FederMasterVo;
import com.enercon.model.table.FederMFactorVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

@Component
@Scope(value="singleton")
public class FederMFactorDao implements WcareConnector{

	private static Logger logger = Logger.getLogger(FederMFactorDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public FederMFactorDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	
	
	public void associateFederMfs(List<FederMasterVo> feders) throws SQLException{
		
		Map<String, FederMasterVo> federsM = Maps.uniqueIndex(
				feders, new Function<FederMasterVo, String>() {

					public String apply(FederMasterVo from) {
						return from.getId();
					}
					
				});
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Set<String> ids = federsM.keySet();
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> idss:  GlobalUtils.splitArrayList(ids, 800)){
				query = "Select feder.S_FEDER_ID, federmf.S_FACTOR_ID ,federmf.D_FROM_DATE ,federmf.D_TO_DATE, federmf.N_MULTI_FACTOR, federmf.S_TYPE, federmf.S_SUB_TYPE, federmf.S_CAPACITY " + 
						"from tbl_feder_master feder, tbl_feder_mfactor federmf " + 
						"where federmf.S_FEDER_ID(+) = feder.S_FEDER_ID " + 
						"and feder.S_FEDER_ID in " + GlobalUtils.getStringFromArrayForQuery(idss) ;
				
				prepStmt = conn.prepareStatement(query);				
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					String federId = rs.getString("S_FEDER_ID");
					String federmfId = rs.getString("S_FACTOR_ID");
					String fromDate = rs.getString("D_FROM_DATE");
					String toDate = rs.getString("D_TO_DATE");
					//String fromDate = DateUtility.convertDateFormats(rs.getString("D_FROM_DATE"), "yyyy-MM-dd", "dd/MM/yyyy");		
					//String toDate = DateUtility.convertDateFormats(rs.getString("D_TO_DATE"), "yyyy-MM-dd", "dd/MM/yyyy");
					String capacity = rs.getString("S_CAPACITY");
					Double multifactor =rs.getDouble("N_MULTI_FACTOR");
					String type = rs.getString("S_TYPE");
				    String subType = rs.getString("S_SUB_TYPE");
				   // logger.debug("id : "+ebmfId+"fromDate :: "+fromDate+" :toDate:: "+toDate);
					if(federmfId != null){
						
						new FederMFactorVo.FederMFactorVoBuilder()
							.id(federmfId)					
							.federId(federId)
							.fromDate(DateUtility.convertDateFormats(fromDate, "yyyy-MM-dd", "dd/MM/yyyy"))
							.toDate(DateUtility.convertDateFormats(toDate, "yyyy-MM-dd", "dd/MM/yyyy"))
							.capacity(capacity)
							.multiFactor(multifactor)
							.type(type)
							.subType(subType)
							.feder(federsM.get(federId))
							.build();
					}						
				}				
			}			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(FederMFactorVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_FEDER_MFACTOR( S_FACTOR_ID,  S_FEDER_ID,  D_FROM_DATE,  D_TO_DATE, " +
																	" S_CREATED_BY, S_LAST_MODIFIED_BY, N_MULTI_FACTOR, S_SUB_TYPE, S_TYPE, S_CAPACITY ) values " +
																	" (:id, :federId, :fromDate, :toDate," +
																	"  :createdBy, :modifiedBy, :multiFactor, :subType, :type, :capacity)", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(FederMFactorVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_FEDER_ID");
		columns.add("D_FROM_DATE");
		columns.add("D_TO_DATE");		
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("N_MULTI_FACTOR");
		columns.add("S_SUB_TYPE");
		columns.add("S_TYPE");
		columns.add("S_CAPACITY");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(FederMFactorVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_FEDER_MFACTOR SET ");
		
		for(String column: columns){
			if(column.equals("S_FEDER_ID")){
				query.append(column + " = :federId, ");
			} else if(column.equals("D_FROM_DATE")){
				query.append(column + " = :fromDate, ");
			} else if(column.equals("D_TO_DATE")){
				query.append(column + " = :toDate, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("N_MULTI_FACTOR")){
				query.append(column + " = :multiFactor, ");
			} else if(column.equals("S_SUB_TYPE")){
				query.append(column + " = :subType, ");
			} else if(column.equals("S_TYPE")){
				query.append(column + " = :type, ");
			} else if(column.equals("S_CAPACITY")){
				query.append(column + " = :capacity, ");
			}
		}
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");		
		query.append("WHERE S_FACTOR_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(FederMFactorVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		//int count = jdbc.queryForInt("SELECT count(1) FROM TBL_FEDER_MFACTOR   WHERE S_FEDER_ID = :federId and AND (( :fromDate BETWEEN D_FROM_DATE AND D_TO_DATE) OR ( :toDate BETWEEN D_FROM_DATE AND D_TO_DATE)) and AND S_FACTOR_ID <> :id ", params);
		return false;
	}
	
	
	
}
