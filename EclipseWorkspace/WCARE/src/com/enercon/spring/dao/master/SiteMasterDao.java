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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.enercon.dao.DaoUtility;
import com.enercon.global.utility.DateUtility;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.ISiteMasterVo;
import com.enercon.model.graph.SiteMasterVo;
import com.enercon.model.graph.event.SiteMasterEvent;
import com.enercon.model.graph.listener.SiteMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;

@Component
@Scope(value="singleton")
public class SiteMasterDao {

	private static Logger logger = Logger.getLogger(SiteMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<SiteMasterListener> listeners =  new ArrayList<SiteMasterListener>();

	private SiteMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static SiteMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("siteMasterDao", SiteMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public boolean create(SiteMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_SITE_MASTER(S_SITE_ID, S_SITE_NAME, S_STATE_ID, S_CREATED_BY, S_LAST_MODIFIED_BY, S_SITE_CODE, S_SITE_INCHARGE, S_SITE_ADDRESS, S_AREA_ID) values " +
																	"(:id, :name, :stateId, :createdBy, :modifiedBy, :code, :incharge, :address, :areaId)", params);
		if(rowInserted == 1){
			SiteMasterEvent event = new SiteMasterEvent();
			event.setCreate(true);
			event.setSite(vo);
			fireSiteMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(ISiteMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_SITE_NAME");
		columns.add("S_STATE_ID");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_SITE_CODE");
		columns.add("S_SITE_INCHARGE");
		columns.add("S_SITE_ADDRESS");
		columns.add("S_AREA_ID");
		
		return partialUpdate(vo, columns);
		
	}
	public boolean remarkUpdateForMaster(ISiteMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		
		columns.add("S_SITE_REMARKS");
		columns.add("S_LAST_MODIFIED_BY");		
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(ISiteMasterVo site, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(site);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_SITE_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_SITE_NAME")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_STATE_ID")){
				query.append(column + " = :stateId, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("S_SITE_CODE")){
				query.append(column + " = :code, ");
			} else if(column.equals("S_SITE_INCHARGE")){
				query.append(column + " = :incharge, ");
			} else if(column.equals("S_SITE_ADDRESS")){
				query.append(column + " = :address, ");
			} else if(column.equals("S_AREA_ID")){
				query.append(column + " = :areaId, ");
			} else if(column.equals("S_SITE_REMARKS")){
				query.append(column + " = :remark, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = '" + DateUtility.getTodaysDateInGivenFormat("dd-MMM-yy")+"' ");
		query.append("WHERE S_SITE_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			SiteMasterEvent event = new SiteMasterEvent();
			event.setSite(site);
			event.setUpdate(true);
			event.setColumns(columns);
			fireSiteMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public boolean exist(SiteMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_SITE_MASTER WHERE S_SITE_NAME = :name", params);
		return count == 1;
	}
	
	public boolean remarkExist(SiteMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_SITE_MASTER  WHERE S_SITE_ID = :id AND D_LAST_MODIFIED_DATE = TO_DATE(SYSDATE) AND S_SITE_REMARKS = :remark ", params);
		return count == 1;
	}

	public List<ISiteMasterVo> getAll() {
		return new ArrayList<ISiteMasterVo>(Graph.getInstance().getSitesM().values());
	}
	
	public List<SiteMasterVo> getRemarks() throws SQLException {
		
		Connection conn = null;
		String query = "";
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		List<SiteMasterVo> vos = new ArrayList<SiteMasterVo>();
		SiteMasterVo vo = null;	
		try {
			conn = wcareConnector.getConnectionFromPool();
			
			query = "SELECT * FROM TBL_SITE_MASTER WHERE S_SITE_REMARKS IS NOT NULL " ; 
		
			prepStmt = conn.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while (rs.next()) {
				vo = new SiteMasterVo();
				
				vo.setId(rs.getString("S_SITE_ID"));
				vo.setName(rs.getString("S_SITE_NAME"));
				vo.setRemark(rs.getString("S_SITE_REMARKS"));
				
				vos.add(vo);
			}
			return vos;
		} finally {
			DaoUtility.releaseResources(prepStmt, rs, conn);
		}
	}

	public void addSiteMasterListener(SiteMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireSiteMasterEvent(SiteMasterEvent event){
		for(SiteMasterListener listener: listeners){
			listener.handler(event);
		}
	}
	
	
}
