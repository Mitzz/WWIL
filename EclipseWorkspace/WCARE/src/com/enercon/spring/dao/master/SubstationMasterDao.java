package com.enercon.spring.dao.master;

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

import com.enercon.connection.WcareConnector;
import com.enercon.dao.DaoUtility;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.master.SubstationMasterVo;
import com.enercon.service.master.AreaMasterService;


@Component
@Scope(value="singleton")
public class SubstationMasterDao implements WcareConnector {

	private static Logger logger = Logger.getLogger(SubstationMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	

	public SubstationMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
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
				vo.setTransformerCount(rs.getInt("N_TTL_TRANSFORMER"));
				vo.setRemark(rs.getString("S_SUBSTATION_REMARKS"));
				vo.setAreaId(rs.getString("S_AREA_ID"));
							
				vos.add(vo);
				
				IAreaMasterVo area = AreaMasterService.getInstance().get(rs.getString("S_AREA_ID"));
				if(!area.getSubstations().contains(vo)){
					vo.setArea(area);
				}
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
	
	
	
	
	public boolean create(SubstationMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_SUBSTATION_MASTER(S_SUBSTATION_ID, S_SUBSTATION_DESC, S_SUBSTATION_OF, S_SUBTATION_CAP, S_SUBSTATION_MVA, S_SUBSTATION_HV, S_SUBSTATION_LV, S_CREATED_BY, S_LAST_MODIFIED_BY, S_AREA_ID, N_TTL_TRANSFORMER, S_SUBSTATION_REMARKS) values " +
																	   "(:id, :name, :owner,:capacity, :mva, :highVoltage, :lowVoltage, :createdBy, :modifiedBy, :areaId, :transformerCount, :remark )", params);
		
		return (rowInserted == 1);
	}
	
	public boolean updateForMaster(SubstationMasterVo vo) throws SQLException, CloneNotSupportedException   {
		
		List<String> columns = new ArrayList<String>();
		columns.add("S_SUBSTATION_DESC");
		columns.add("S_SUBSTATION_OF");
		columns.add("S_SUBTATION_CAP");
		columns.add("S_SUBSTATION_MVA");
		columns.add("S_SUBSTATION_HV");
		columns.add("S_SUBSTATION_LV");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("N_TTL_TRANSFORMER");
		columns.add("S_SUBSTATION_REMARKS");
		
		return partialUpdate(vo, columns);
	}
	
	private boolean partialUpdate(SubstationMasterVo substation, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(substation);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_SUBSTATION_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_SUBSTATION_DESC")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_SUBSTATION_OF")){
				query.append(column + " = :owner, ");
			}else if(column.equals("S_SUBTATION_CAP")){
				query.append(column + " = :capacity, ");
			}else if(column.equals("S_SUBSTATION_MVA")){
				query.append(column + " = :mva, ");
			}else if(column.equals("S_SUBSTATION_HV")){
				query.append(column + " = :highVoltage, ");
			}else if(column.equals("S_SUBSTATION_LV")){
				query.append(column + " = :lowVoltage, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("S_AREA_ID")){
				query.append(column + " = :areaId, ");
			} else if(column.equals("N_TTL_TRANSFORMER")){
				query.append(column + " = :transformerCount, ");
			} else if(column.equals("S_SUBSTATION_REMARKS")){
				query.append(column + " = :remark, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");		
		query.append("WHERE S_SUBSTATION_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		
		return (rowUpdate == 1);
	}
	
	public boolean exist(SubstationMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_SUBSTATION_MASTER WHERE S_SUBSTATION_DESC = :name", params);
		return count == 1;
	}
	
}
