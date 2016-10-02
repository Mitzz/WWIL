package com.enercon.spring.dao.master;

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

import com.enercon.model.graph.AreaMasterVo;
import com.enercon.model.graph.Graph;
import com.enercon.model.graph.IAreaMasterVo;
import com.enercon.model.graph.IStateMasterVo;
import com.enercon.model.graph.event.AreaMasterEvent;
import com.enercon.model.graph.listener.AreaMasterListener;
import com.enercon.spring.context.ApplicationContextProvider;

@Component
@Scope(value="singleton")
public class AreaMasterDao {

	private static Logger logger = Logger.getLogger(AreaMasterDao.class);
	private NamedParameterJdbcTemplate jdbc;
	private List<AreaMasterListener> listeners =  new ArrayList<AreaMasterListener>();

	private AreaMasterDao() {
		logger.debug("Loaded Successfully!!!");
	}
	
	public static AreaMasterDao getInstance(){
		ApplicationContextProvider p = ApplicationContextProvider.getInstance();
		ApplicationContext applicationContext = p.getApplicationContext();
		return applicationContext.getBean("areaMasterDao", AreaMasterDao.class);
	}
	
	@Autowired
	public void setDataSource(DataSource jdbc){
		this.jdbc = new NamedParameterJdbcTemplate(jdbc);
	}
	
	public List<IStateMasterVo> getAll() throws SQLException{
		List<IStateMasterVo> states = new ArrayList<IStateMasterVo>(Graph.getInstance().getStatesM().values());
		return states;
	}
	
	public boolean create(AreaMasterVo vo) {
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int rowInserted = jdbc.update("Insert into TBL_AREA_MASTER(S_AREA_ID, S_AREA_NAME, S_STATE_ID, S_CREATED_BY, S_LAST_MODIFIED_BY, S_AREA_CODE, S_AREA_INCHARGE_ID) values " +
																	"(:id, :name, :stateId, :createdBy, :modifiedBy, :code, :inCharge)", params);
		if(rowInserted == 1){
			AreaMasterEvent event = new AreaMasterEvent();
			event.setCreate(true);
			event.setArea(vo);
			fireAreaMasterEvent(event);
		}
		return (rowInserted == 1);
	}

	public boolean updateForMaster(IAreaMasterVo vo) throws SQLException, CloneNotSupportedException {
		List<String> columns = new ArrayList<String>();
		columns.add("S_AREA_NAME");
		columns.add("S_STATE_ID");
		columns.add("S_LAST_MODIFIED_BY");
		columns.add("S_AREA_CODE");
		columns.add("S_AREA_INCHARGE_ID");
		
		return partialUpdate(vo, columns);
		
	}
	
	private boolean partialUpdate(IAreaMasterVo area, List<String> columns) throws SQLException {
		BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(area);
		
		logger.debug(columns);
		StringBuilder query = new StringBuilder();
		
		query.append("UPDATE TBL_AREA_MASTER SET ");
		
		for(String column: columns){
			if(column.equals("S_AREA_NAME")){
				query.append(column + " = :name, ");
			} else if(column.equals("S_STATE_ID")){
				query.append(column + " = :stateId, ");
			} else if(column.equals("S_LAST_MODIFIED_BY")){
				query.append(column + " = :modifiedBy, ");
			} else if(column.equals("S_AREA_CODE")){
				query.append(column + " = :code, ");
			} else if(column.equals("S_AREA_INCHARGE_ID")){
				query.append(column + " = :inCharge, ");
			}
		}
		
		query.append("D_LAST_MODIFIED_DATE = localtimestamp ");
		query.append("WHERE S_AREA_ID = :id ");
		logger.debug(query);
		int rowUpdate = jdbc.update(new String(query), param);
		
		if(rowUpdate == 1) {
			AreaMasterEvent event = new AreaMasterEvent();
			event.setArea(area);
			event.setUpdate(true);
			event.setColumns(columns);
			fireAreaMasterEvent(event);
		}
		return (rowUpdate == 1);
	}
	
	public boolean exist(AreaMasterVo vo){
		BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(vo);
		int count = jdbc.queryForInt("SELECT count(1) FROM TBL_AREA_MASTER WHERE S_AREA_NAME = :name", params);
		return count == 1;
	}

	public void addAreaMasterListener(AreaMasterListener listener){
		listeners.add(listener);
	}
	
	public void fireAreaMasterEvent(AreaMasterEvent event){
		for(AreaMasterListener listener: listeners){
			listener.handler(event);
		}
	}
	
	
}
