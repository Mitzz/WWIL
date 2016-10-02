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
import com.enercon.model.graph.IEbMasterVo;
import com.enercon.model.table.EbMFactorVo;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

@Component
@Scope(value="singleton")
public class EbMFactorDao implements WcareConnector{

	private static Logger logger = Logger.getLogger(EbMFactorDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public EbMFactorDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public void associateEbMfs(List<IEbMasterVo> ebs) throws SQLException{
		
		Map<String, IEbMasterVo> ebsM = Maps.uniqueIndex(
				ebs, new Function<IEbMasterVo, String>() {

					public String apply(IEbMasterVo from) {
						return from.getId();
					}
					
				});
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		Set<String> ids = ebsM.keySet();
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			for(List<String> idss:  GlobalUtils.splitArrayList(ids, 800)){
				query = "Select eb.S_EB_ID, ebmf.S_FACTOR_ID ,ebmf.D_FROM_DATE ,ebmf.D_TO_DATE, ebmf.N_MULTI_FACTOR, ebmf.S_TYPE, ebmf.S_SUB_TYPE, ebmf.S_CAPACITY " + 
						"from tbl_eb_master eb, tbl_eb_mfactor ebmf " + 
						"where ebmf.S_EB_ID(+) = eb.S_EB_ID " + 
						"and eb.S_EB_ID in " + GlobalUtils.getStringFromArrayForQuery(idss) ;
				
				prepStmt = conn.prepareStatement(query);				
				
				rs = prepStmt.executeQuery();
				while (rs.next()) {
					String ebId = rs.getString("S_EB_ID");
					String ebmfId = rs.getString("S_FACTOR_ID");
					String fromDate = rs.getString("D_FROM_DATE");
					String toDate = rs.getString("D_TO_DATE");
					//String fromDate = DateUtility.convertDateFormats(rs.getString("D_FROM_DATE"), "yyyy-MM-dd", "dd/MM/yyyy");		
					//String toDate = DateUtility.convertDateFormats(rs.getString("D_TO_DATE"), "yyyy-MM-dd", "dd/MM/yyyy");
					String capacity = rs.getString("S_CAPACITY");
					Double multifactor =rs.getDouble("N_MULTI_FACTOR");
					String type = rs.getString("S_TYPE");
				    String subType = rs.getString("S_SUB_TYPE");
				    //logger.debug("id : "+ebmfId+"fromDate :: "+fromDate+" :toDate:: "+toDate);
					if(ebmfId != null){
						
						new EbMFactorVo.EbMFactorBuilder()
							.id(ebmfId)					
							.ebId(ebId)
							.fromDate(DateUtility.convertDateFormats(fromDate, "yyyy-MM-dd", "dd/MM/yyyy"))
							.toDate(DateUtility.convertDateFormats(toDate, "yyyy-MM-dd", "dd/MM/yyyy"))
							.capacity(capacity)
							.multiFactor(multifactor)
							.type(type)
							.subType(subType)
							.eb(ebsM.get(ebId))
							.build();
					}						
				}				
			}			
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}
	
	public boolean create(EbMFactorVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_EB_MFACTOR ( S_FACTOR_ID,  S_EB_ID,   D_FROM_DATE,  D_TO_DATE, " +
																	" S_CREATED_BY, S_LAST_MODIFIED_BY, N_MULTI_FACTOR, S_SUB_TYPE, S_TYPE, S_CAPACITY ) values " +
																	" (:id, :ebId, :fromDate, :toDate," +
																	"  :createdBy, :modifiedBy, :multiFactor, :subType, :type, :capacity)", params);
		
		return (rowInserted == 1);
	}

	public boolean updateForMaster(EbMFactorVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_EB_ID");
		columns.add("D_FROM_DATE");
		columns.add("D_TO_DATE");		
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("N_MULTI_FACTOR");
		columns.add("S_SUB_TYPE");
		columns.add("S_TYPE");
		columns.add("S_CAPACITY");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(EbMFactorVo vo, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(vo);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_EB_MFACTOR  SET ");
		
		for(String column: columns){
			if(column.equals("S_EB_ID")){
				query.append(column + " = :ebId, ");
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
	
	public boolean exist(EbMFactorVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
//		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_EB_MFACTOR    WHERE S_EB_ID  = :ebId and AND (( :fromDate BETWEEN D_FROM_DATE AND D_TO_DATE) OR ( :toDate BETWEEN D_FROM_DATE AND D_TO_DATE)) and AND S_FACTOR_ID <> :id ", params);
		return false;
	}
	
}
